﻿using UnityEngine;
using System.Collections;
using System.Collections.Generic;
namespace Global {
    public enum ownerShip { Neutral, Player1, Player2 };
    
    public class GameManager : MonoBehaviour
    {
        #region LevelLayoutVariables
        
        //location and type of towermarkers placed on the scene in the heiarchy
        private Dictionary<Vector3, ownerShip> mappedTowers = new Dictionary<Vector3, ownerShip>();
        private Dictionary<Vector3, ownerShip> mappedShocks = new Dictionary<Vector3, ownerShip>();
        private Dictionary<Vector3, ownerShip> mappedRoots = new Dictionary<Vector3, ownerShip>();
        
        //List of actuall ingame towers and their positions
        public Dictionary<Vector3, Tower> towerLookup = new Dictionary<Vector3, Tower>();
        
        //Default unit count for each of the 3 factions        
        public int NeutralStartingUnits;
        public int Player1StartingUnits;
        public int Player2StartingUnits;
        
        private GameObject towerPrefab;
        private GameObject shockPrefab;
        private GameObject root1Prefab;
        private GameObject root2Prefab;
        private GameObject plusTenPrefab;
        private GameObject shieldP1;
        private GameObject shieldP2;
        #endregion

        //Each Player's score
        public float player1Score = 0;
        public float player2Score = 0;
        private float fequency = 5;
        private float lastCalc;
        public bool player1HasAllTowers = false;
        public bool player2HasAllTowers = false;
        StateManager stateManager;

        //Set from inspector to have selections cleared after attacks
        public bool ClearSelectionAfterAttack = true;
        
        void Start() {			
            towerPrefab = Resources.Load("Prefabs/tower") as GameObject;
            shockPrefab = Resources.Load("Prefabs/ShockTower") as GameObject;
            root1Prefab = Resources.Load ("Prefabs/DeathRayPlayer1") as GameObject;
            root2Prefab = Resources.Load ("Prefabs/DeathRayPlayer2") as GameObject;
            shieldP1 = Resources.Load ("Prefabs/ShieldPlayer1") as GameObject;
            shieldP2 = Resources.Load ("Prefabs/ShieldPlayer2") as GameObject;
            GameObject manager = GameObject.FindGameObjectsWithTag("MainCamera")[0];
            stateManager = manager.GetComponent<StateManager>();

            //Find all the locations that towers should spawn at from markers
            BuildTowerLocations();
        }
      
        public void resetScore(){
            if (Network.isServer)
                player1Score = 0;
            else if(Network.isClient)
                networkView.RPC("RPCresetScore", RPCMode.Server);
        }

        [RPC]
        private void RPCresetScore(){
            player2Score = 0;
        }
		
        private void winCondition(){
            int player1Count = 0;
            int player2Count = 0;
            foreach (var tower in towerLookup) {

                if(tower.Value.myOwner == ownerShip.Player1)
                    player1Count++;
                else if(tower.Value.myOwner == ownerShip.Player2)
                    player2Count++;
            }
            if (player1Count == 0 ) {
                player2HasAllTowers = true;
                stateManager.status = WorldGameState.EndGame;
                return;
            }
            if (player2Count == 0){
                player1HasAllTowers = true;
                stateManager.status = WorldGameState.EndGame;
                return;
            }
        }

        #region updateScore
        private Queue<GameObject> TowerQ = new Queue<GameObject>();
        private float regenSpeed = 1f;
        private float lastUpdate;

        public void calculateScore() {
            //int test = 0;
            GameObject[] rootArray = GameObject.FindGameObjectsWithTag("RootNode");

            if (Time.realtimeSinceStartup - lastCalc > fequency) {
                lastCalc = Time.realtimeSinceStartup;
                if (rootArray != null) {
                    BFS(rootArray[0]);
                    BFS(rootArray[1]);
                }

                GameObject[] towerArray = GameObject.FindGameObjectsWithTag("Tower");
                foreach (GameObject go in towerArray) {
                    go.GetComponent<Tower>().Visited = false;
                }
            }
        }

        public void BFS(GameObject root) {
            Dictionary<GameObject, LineRenderer> rootAdjacent = root.GetComponentInChildren<Connection>().connections;
            //todo reduce the .getComponent<> calls they are expensive
            foreach (var node in rootAdjacent) {
                if (root.GetComponent<DeathRay>().myOwner == node.Key.GetComponent<Tower>().myOwner) {
                    TowerQ.Enqueue(node.Key);
                    node.Key.GetComponent<Tower>().Visited = true;
                    if (root.GetComponent<DeathRay>().myOwner == ownerShip.Player1) {
                        player1Score += 10;
                        node.Key.GetComponent<Tower>().PlayPlusTen();
                    }
                    if (root.GetComponent<DeathRay>().myOwner == ownerShip.Player2) {
                        node.Key.GetComponent<Tower>().networkView.RPC("PlayPlusTen", RPCMode.Others);
                        player2Score += 10;
                    }
                }
            }

            while (TowerQ.Count != 0) {
                GameObject currentNode = TowerQ.Dequeue(); //remove the first element
                ownerShip myOwner = currentNode.GetComponent<Tower>().myOwner; // get the owner of current
                if (!currentNode.GetComponent<Tower>().Visited) {
                    if (root.GetComponent<DeathRay>().myOwner == ownerShip.Player1) {
                        player1Score += 10;
                        currentNode.GetComponent<Tower>().PlayPlusTen();
                    }
                    if (root.GetComponent<DeathRay>().myOwner == ownerShip.Player2) {
                        currentNode.GetComponent<Tower>().networkView.RPC("PlayPlusTen", RPCMode.Others);
                        player2Score += 10;
                    }
                    currentNode.GetComponent<Tower>().Visited = true;
                }
                Dictionary<GameObject, LineRenderer> adjacent = currentNode.GetComponentInChildren<Connection>().connections;
                foreach (var node in adjacent) {
                    ownerShip childOwner = node.Key.GetComponent<Tower>().myOwner; // get the owner of the child node
                    if (myOwner == childOwner && node.Key.GetComponent<Tower>().Visited == false) {
                        TowerQ.Enqueue(node.Key);
                    }
                }
            }
        }
        #endregion

        //Adds up all of the units in each players' towers to calculate score
        void FixedUpdate() {
            if (Input.GetKey(KeyCode.E))
                SelectAll(Network.isServer);
            if (Network.isClient){
                if(!player2HasAllTowers && !player1HasAllTowers)
                    return;
                else
                    winCondition();
            } //////////////////////////////NO Client past here
            if(stateManager.status == WorldGameState.InGame)
                calculateScore ();
            if(towerLookup.Count > 1 )
              winCondition ();
        }

        //Called from individual towers to notify all of the same player's towers to attack a certain location
        public void AttackToward(Vector3 targetPosition, ownerShip attackingPlayer) {
            foreach (KeyValuePair<Vector3, Tower> entry in towerLookup) {
                if (entry.Value.selected && entry.Value.myOwner == attackingPlayer)
                    StartCoroutine(entry.Value.SpawnAttack(targetPosition));
            }
            if(ClearSelectionAfterAttack)
                RPCDeselectTowers(attackingPlayer == ownerShip.Player1);
        }

        //Called from individual towers to notify all of the same player's towers to deselect a certain location
        [RPC]
        public void RPCDeselectTowers(bool isPlayer1) {
            //print("deselecting towers");
            ownerShip playerToDeselect = (isPlayer1 == true) ? ownerShip.Player1 : ownerShip.Player2;

            foreach (KeyValuePair<Vector3, Tower> entry in towerLookup) {
                if (entry.Value.selected && entry.Value.myOwner == playerToDeselect) {
                    entry.Value.ToggleSelect();
                    entry.Value.updateSprite();
                }
            }
        }

        public void DeselectTowers(bool isPlayer1) {
            if(Network.isServer)
                RPCDeselectTowers(true);
            else
                networkView.RPC("RPCDeselectTowers", RPCMode.Server, false);
        }

        //-----------------------------------------------------------------------------------------------------------------------------
        #region SelectAll

        //Called from individual towers to notify all of the same player's towers to deselect a certain location
        [RPC]
        public void RPCSelectAll(bool isPlayer1) {
            ownerShip playerToDeselect = (isPlayer1 == true) ? ownerShip.Player1 : ownerShip.Player2;
            foreach (KeyValuePair<Vector3, Tower> entry in towerLookup) {
                if (!entry.Value.selected && entry.Value.myOwner == playerToDeselect) {
                    entry.Value.ToggleSelect();
                    entry.Value.updateSprite();
                }
            }
        }

        public void SelectAll(bool isPlayer1) {
            print("calling deselecttowers");
            if (Network.isServer)
                RPCSelectAll(true);
            else
                networkView.RPC("RPCSelectAll", RPCMode.Server, false);
        }

        #endregion

        void BuildTowerLocations() {
            GameObject[] towerMarkers = GameObject.FindGameObjectsWithTag("towerMarker");
            foreach (GameObject tm in towerMarkers) {
                switch (tm.name) {
                    case "towerMarkerNeutral":
                        mappedTowers.Add(tm.transform.position, ownerShip.Neutral);
                        break;
                    case "towerMarkerPlayer1":
                        mappedTowers.Add(tm.transform.position, ownerShip.Player1);
                        break;
                    case "towerMarkerPlayer2":
                        mappedTowers.Add(tm.transform.position, ownerShip.Player2);
                        break;
                    case "shockMarkerPlayer1":
                        mappedShocks.Add(tm.transform.position, ownerShip.Player1);
                        break;
                    case "shockMarkerPlayer2":
                        mappedShocks.Add(tm.transform.position, ownerShip.Player2);
                        break;
                    case "shockMarkerPlayerNeutral":
                        mappedShocks.Add(tm.transform.position, ownerShip.Neutral);
                        break;
                    default:
                        Debug.LogError("Invalid towerMarker type in buildTowerLocations");
                        break;
                }
                Destroy(tm);
            }   

            GameObject[] rootMarkers = GameObject.FindGameObjectsWithTag("RootMarker");
            foreach (GameObject rm in rootMarkers)
            {
                if (rm.name == "RootMarkerPlayer1")
                    mappedRoots.Add(rm.transform.position, ownerShip.Player1);
                else
                    mappedRoots.Add(rm.transform.position, ownerShip.Player2);
                Destroy(rm);
            }
        }

        //Instanciates the towers in all the locations specified by BuildTowerLocations()
        public void SpawnTowers() {
            GameObject one = (GameObject)Network.Instantiate (shieldP1, new Vector3(182,0,0), Quaternion.Euler (0, 0, 0), 0);
            GameObject two = (GameObject)Network.Instantiate (shieldP1, new Vector3(174,11,0), Quaternion.Euler (0, 0, 0), 0);
            GameObject three = (GameObject)Network.Instantiate (shieldP1, new Vector3(174,-11,0), Quaternion.Euler (0, 0, 0), 0);
            GameObject four = (GameObject)Network.Instantiate (shieldP2, new Vector3(218,0,0), Quaternion.Euler (0, 0, 0), 0);
            GameObject five = (GameObject)Network.Instantiate (shieldP2, new Vector3(226,11,0), Quaternion.Euler (0, 0, 0), 0);
            GameObject six = (GameObject)Network.Instantiate (shieldP2, new Vector3(226,-11,0), Quaternion.Euler (0, 0, 0), 0);
   
            foreach (KeyValuePair<Vector3, ownerShip> r in mappedRoots) {
                if(r.Value == ownerShip.Player1){
                          GameObject aRoot = (GameObject)Network.Instantiate(root1Prefab, r.Key, Quaternion.Euler(0, 0, 0), 0);
                }
                else{
                  GameObject aRoot = (GameObject)Network.Instantiate(root2Prefab, r.Key, Quaternion.Euler(0, 0, 0), 0);
                }
            }

            foreach (KeyValuePair<Vector3, ownerShip> entry in mappedTowers) {
              GameObject aTower = (GameObject)Network.Instantiate(towerPrefab, entry.Key, Quaternion.Euler(0, 0, 0), 0);
              Tower tScript = aTower.GetComponent<Tower>();
              tScript.SwitchOwner(entry.Value);
              switch (entry.Value) {
                  case ownerShip.Neutral:
                      tScript.units = NeutralStartingUnits;
                      break;
                  case ownerShip.Player1:
                      tScript.units = Player1StartingUnits;
                      break;
                  case ownerShip.Player2:
                      tScript.units = Player2StartingUnits;
                      break;
                  default:
                      Debug.LogError("Invalid Ownership type");
                      break;
              }
              towerLookup.Add(entry.Key, aTower.GetComponent<Tower>());
            }
            
            foreach (KeyValuePair<Vector3, ownerShip> entry in mappedShocks) {
                GameObject aTower = (GameObject)Network.Instantiate(shockPrefab, entry.Key, Quaternion.Euler(0, 0, 0), 0);
                Tower tScript = aTower.GetComponent<Tower>();
                tScript.SwitchOwner(entry.Value);
                switch (entry.Value) {
                    case ownerShip.Neutral:
                        tScript.units = NeutralStartingUnits * 3;
                        break;
                    case ownerShip.Player1:
                        tScript.units = Player1StartingUnits;
                        break;
                    case ownerShip.Player2:
                        tScript.units = Player2StartingUnits;
                        break;
                    default:
                        Debug.LogError("Invalid Ownership type");
                        break;
                }
                towerLookup.Add(entry.Key, aTower.GetComponent<Tower>());
            }
        }

        void OnSerializeNetworkView(BitStream stream, NetworkMessageInfo info) {
            if (stream.isWriting) {
                int p1score = (int)player1Score;
                stream.Serialize(ref p1score);
                int p2score = (int)player2Score;
                stream.Serialize(ref p2score);
                stream.Serialize(ref player1HasAllTowers);
                stream.Serialize(ref player2HasAllTowers);
            }
            else {
                int p1score = 0;
                stream.Serialize(ref p1score);
                player1Score = p1score;
                int p2score = 0;
                stream.Serialize(ref p2score);
                player2Score = p2score;
                stream.Serialize(ref player1HasAllTowers);
                stream.Serialize(ref player2HasAllTowers);
            }
        }
    }
}