using UnityEngine;
using System.Collections;

public class GlobalGameManager : MonoBehaviour {

	private string mCurrentLevel = "MenuLevel";  //  
    private int mHighScore = 0;

	// Use this for initialization
	void Start () {
		DontDestroyOnLoad(this);
	}

	// 
	public void SetCurrentLevel(string level) {
		mCurrentLevel = level;
	}

	public void PrintCurrentLevel()
	{
		Debug.Log("Current Level is: " + mCurrentLevel);
	}

    public int getHighScore()
    {
        return mHighScore;
    }

    public void incrementHighScore()
    {
		mHighScore += 100;
        //++mHighScore;
    }
}
