using UnityEngine;
using System.Collections;

public class PadBehavior : MonoBehaviour {

	private float RX  =.15f;
	private float RY  =.15f;
	private float RR  =90f/6f;

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
		transform.position += new Vector3 (RX, RY);
		transform.Rotate(Vector3.forward, -1f * (RR * Time.smoothDeltaTime));

		GlobalBehavior globalBehavior = GameObject.Find ("GameManager").GetComponent<GlobalBehavior>();
		GlobalBehavior.WorldBoundStatus status = globalBehavior.ObjectCollideWorldBound(this.transform.position, this.collider2D);
		if (status != GlobalBehavior.WorldBoundStatus.Inside) {
			NewDirection (status);
		}
	}

	private void NewDirection(GlobalBehavior.WorldBoundStatus status) {
		if (status == GlobalBehavior.WorldBoundStatus.CollideTop) {
			RY *= -1;
		}
		else if (status == GlobalBehavior.WorldBoundStatus.CollideRight) {
			RX *= -1;
		}
		else if (status == GlobalBehavior.WorldBoundStatus.CollideLeft) {
			RX *= -1;
		}
		else if (status == GlobalBehavior.WorldBoundStatus.CollideBottom) {
			RY *= -1;
		}
	}
}
