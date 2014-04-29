using UnityEngine;
using System.Collections;

public class MenuGUI : MonoBehaviour {

    private const float mbuttonWidth = 250f;
    private const float mbuttonHeight = 100f;
    private const string lvl1 = "Level1";
    private const string lvl2 = "Level2";
    private const string credits = "Turtles vs Fish\nCreated April 2014 by:\nBrandan Haertel \nErick House \nGary Mixson";
    public GameObject creditsGui;
    private bool creditsOn = false;
    public Texture2D lvl1Texture;
    public Texture2D lvl2Texture;
    public Texture2D creditsTexture;
    public Texture2D quitTexture;
	public AudioClip sound;
	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}

    void OnGUI()
    {
        float topSixthY = Screen.height / 6;
        float middleX = Screen.width /2;
        float button1X = middleX - (mbuttonWidth * 1.5f);
        float button1Y = topSixthY;

            if (GUI.Button(new Rect(button1X, button1Y, mbuttonWidth, mbuttonHeight), lvl1Texture)){
				audio.PlayOneShot(sound);
                Application.LoadLevel(lvl1);
                FirstGameManager.TheGameState.SetCurrentLevel(lvl1);
                
            }


            if (GUI.Button(new Rect(button1X + 2f * mbuttonWidth, button1Y, mbuttonWidth, mbuttonHeight), lvl2Texture))
            {
				audio.PlayOneShot(sound);
                Application.LoadLevel(lvl2);
                FirstGameManager.TheGameState.SetCurrentLevel(lvl2);
            }

            if (GUI.Button(new Rect(button1X , button1Y + (mbuttonHeight + mbuttonHeight / 2), mbuttonWidth, mbuttonHeight), creditsTexture))
            {
				audio.PlayOneShot(sound);
                if (!creditsOn)
                {
                    creditsGui.guiText.text = credits;
                    creditsOn = true;
                }
                else
                {
                    creditsGui.guiText.text = "";
                    creditsOn = false;
                }
                    
            }

            if (GUI.Button(new Rect(button1X + 2f * mbuttonWidth, button1Y + (mbuttonHeight + mbuttonHeight / 2), mbuttonWidth, mbuttonHeight), quitTexture))
            {
				audio.PlayOneShot(sound);
                Application.Quit();
                
            }
                

        }
    
}
