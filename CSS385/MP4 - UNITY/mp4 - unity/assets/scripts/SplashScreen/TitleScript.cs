using UnityEngine;
using System.Collections;

public class TitleScript : MonoBehaviour
{

    public GameObject cube;
    public GameObject cam;
    private const int cubeYSpeed = 2;
    private const int cubeZSpeed = 2;
    private const float camSpeed = 0.025f;
    // Use this for initialization
    void Start()
    {
        cube = GameObject.Find("Cube");
        StartCoroutine(SpinZoom());


    }



    IEnumerator SpinZoom()
    {


        for (int f = 0; f <= 360; ++f)
        {
            cube.transform.Rotate(Vector3.up, cubeYSpeed);
            
            cam.transform.position = Vector3.MoveTowards(cam.transform.position, cube.transform.position, camSpeed);
            yield return null;

        }

        yield return new WaitForSeconds(3f);
        FirstGameManager.TheGameState.SetCurrentLevel("Menu");
        FirstGameManager.TheGameState.PrintCurrentLevel();
        Application.LoadLevel("Menu");

    }

}
