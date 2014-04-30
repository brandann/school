using UnityEngine;
using System.Collections;

public class GlobalBehavior : MonoBehaviour {

	public enum EnemyState
	{
		Still,
		Ambient
	};

	public EnemyState currentEnemyState = EnemyState.Still;

	#region World Bound support
	private Bounds mWorldBound;  // this is the world bound
	private Vector2 mWorldMin;	// Better support 2D interactions
	private Vector2 mWorldMax;
	private Vector2 mWorldCenter;
	private Camera mMainCamera;
	#endregion
	
	#region  support runtime enemy creation
	// to support time ...
	public float InitialEnemys = 5;
	private float mPreEnemySpawnTime = -1f; // 
	private const float kEnemySpawnInterval = 3.0f; // in seconds
	// spwaning enemy ...
	public GameObject mEnemyToSpawn = null;
	#endregion

	public int EnemyCount = 0;
	public int EggCount = 0;
	private float jumpInterval = -1;
	public Texture2D scoreBox;
	private float labelWidth = 125f;
	private float labelHeight = 50f;
	private float wintime = 0;
	private bool gameover;
	public GUIText g;

	// Use this for initialization
	void Start () {
	
		gameover = false;

		#region world bound support
		mMainCamera = Camera.main;
		mWorldBound = new Bounds(Vector3.zero, Vector3.one);
		UpdateWorldWindowBound();
		#endregion
		
		#region initialize enemy spawning
		if (null == mEnemyToSpawn) 
			mEnemyToSpawn = Resources.Load("Prefabs/Enemy") as GameObject;
		#endregion

		for (int i = 0; i < InitialEnemys; i++) {
			SpawnInitialEnemy();
		}
	}
	
	// Update is called once per frame
	void Update () {
		if(EnemyCount == 0)
		{
			if(wintime == 0)
				wintime = Time.realtimeSinceStartup;
			displayCongrats();
		}
		

		if(Input.GetKeyUp(KeyCode.Escape)){
			Application.LoadLevel("Menu");
		}
		//SpawnInitialEnemy();
/*
		if(Application.loadedLevelName == "Level1" && EnemyCount < 1)
		{
			Application.LoadLevel("Level2");
		}		
		if(Application.loadedLevelName == "Level2" && EnemyCount < 1)
		{
			Application.LoadLevel("Menu");
		}
*/
		if (Input.GetAxis ("Jump") > 0f) {	
			if ((Time.realtimeSinceStartup - jumpInterval) > 1) {
				if(currentEnemyState == EnemyState.Still) {
					currentEnemyState = EnemyState.Ambient;
				}
				else if(currentEnemyState == EnemyState.Ambient) {
					currentEnemyState = EnemyState.Still;
				}
				jumpInterval = Time.realtimeSinceStartup;
			}
		}
		
		//update text
		GameObject echoObject = GameObject.Find("EnemyText");
		GUIText enemyText = echoObject.GetComponent<GUIText>();
		enemyText.text = "Enemys: " + EnemyCount; 
		echoObject = GameObject.Find("EggText");
		GUIText eggText = echoObject.GetComponent<GUIText>();
		eggText.text = "Eggs: " + EggCount;
	}
	
	void displayCongrats()
	{
		if ((Time.realtimeSinceStartup - wintime) <= 3f) {
			gameover = true;
		}
		else{
			if(Application.loadedLevelName == "Level1"){
				Application.LoadLevel("Level2");
			}
			else{
				Application.LoadLevel("Menu");
			}
		}
	}

	void OnGUI(){
		GUI.Label(new Rect(Screen.width/2 - labelWidth/2, 0, labelWidth, labelHeight), scoreBox);
		GUI.Label(new Rect((Screen.width/2 - labelWidth/2) + 5, 5, labelWidth, labelHeight), "High Score: " + FirstGameManager.TheGameState.getHighScore().ToString());
		if(gameover)
		{
			if(Application.loadedLevelName == "Level1"){
					g.guiText.text = "GOOD JOB! LOADING LEVEL 1...";
				
				//GUI.Label(new Rect(0,0,Screen.width,Screen.height), "GOOD JOB!\nLOADING LEVEL 2");
			}
			else{
					g.guiText.text = "GOOD JOB! LOADING MENU...";
				
				//GUI.Label(new Rect(0,0,Screen.width,Screen.height), "GOOD JOB!\nLOADING MENU");
			}
		}		
		
	}
	
	#region Game Window World size bound support
	public enum WorldBoundStatus {
		CollideTop,
		CollideLeft,
		CollideRight,
		CollideBottom,
		Outside,
		Inside
	};
	
	/// <summary>
	/// This function must be called anytime the MainCamera is moved, or changed in size
	/// </summary>
	public void UpdateWorldWindowBound()
	{
		// get the main 
		if (null != mMainCamera) {
			float maxY = mMainCamera.orthographicSize;
			float maxX = mMainCamera.orthographicSize * mMainCamera.aspect;
			float sizeX = 2 * maxX;
			float sizeY = 2 * maxY;
			float sizeZ = Mathf.Abs(mMainCamera.farClipPlane - mMainCamera.nearClipPlane);
			
			// Make sure z-component is always zero
			Vector3 c = mMainCamera.transform.position;
			c.z = 0.0f;
			mWorldBound.center = c;
			mWorldBound.size = new Vector3(sizeX, sizeY, sizeZ);
			
			mWorldCenter = new Vector2(c.x, c.y);
			mWorldMin = new Vector2(mWorldBound.min.x, mWorldBound.min.y);
			mWorldMax = new Vector2(mWorldBound.max.x, mWorldBound.max.y);
		}
	}
	
	public Vector2 WorldCenter { get { return mWorldCenter; } }
	public Vector2 WorldMin { get { return mWorldMin; }} 
	public Vector2 WorldMax { get { return mWorldMax; }}
	
	public WorldBoundStatus ObjectCollideWorldBound(Vector3 objPos, Collider2D objCollider)
	{
		WorldBoundStatus status = WorldBoundStatus.Inside;
		
		CircleCollider2D objCircleCollider = (null != objCollider) ? (objCollider as CircleCollider2D) : null;
		if (null != objCircleCollider) {
			Vector3 center = objPos + new Vector3 (objCircleCollider.center.x, objCircleCollider.center.y, 0f);
			// actual position of the collider
			
			float size = objCircleCollider.radius * 2f; // this is the actual size of the circle
			
			Vector3 bSize = new Vector3 (size, size, size);
			Bounds objBound = new Bounds (center, bSize);
			// Create the bound with the proper size and center location
			
			if (mWorldBound.Intersects(objBound)) {
				if (objBound.max.x > mWorldBound.max.x)
					status = WorldBoundStatus.CollideRight;
				else if (objBound.min.x < mWorldBound.min.x)
					status = WorldBoundStatus.CollideLeft;
				else if (objBound.max.y > mWorldBound.max.y)
					status = WorldBoundStatus.CollideTop;
				else if (objBound.min.y < mWorldBound.min.y)
					status = WorldBoundStatus.CollideBottom;
				else if ( (objBound.min.z < mWorldBound.min.z) || (objBound.max.z > mWorldBound.max.z))
					status = WorldBoundStatus.Outside;
			} else 
				status = WorldBoundStatus.Outside;
		} else {
		}
		return status;
		
	}
	#endregion 
	
	#region enemy spawning support
	private void SpawnInitialEnemy()
	{
		GameObject e = Instantiate(mEnemyToSpawn) as GameObject;
		EnemyBehavior spawnedEnemy = e.GetComponent<EnemyBehavior>();
		if(spawnedEnemy != null) {
			e.transform.position = new Vector3(Random.Range(-133,133),Random.Range(-100,100),0f);
			spawnedEnemy.transform.Rotate(new Vector3(0,0,Random.Range(0,360)));
			EnemyCount++;
		}
	}
	#endregion

	
}
