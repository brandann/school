using UnityEngine;
using System.Collections;

public class CloudBehavior : MonoBehaviour {

	private float RX  =.01f;
	private float RY  =.01f;
	private float RR  =-1f;
	
	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		transform.position += new Vector3 (RX, RY);
		transform.Rotate(Vector3.forward, -1f * (RR * Time.smoothDeltaTime));
	}
}
