using UnityEngine;
using System.Collections;

public class ProjectileBehaviour : MonoBehaviour {

	public float mSpeed = 100f;

	// Use this for initialization
	void Start () {
	
	}
	
	void Update () {
		transform.position += (mSpeed * Time.smoothDeltaTime) * transform.up;
		
		GlobalBehavior globalBehavior = GameObject.Find ("GameManager").GetComponent<GlobalBehavior>();
		GlobalBehavior.WorldBoundStatus status = globalBehavior.ObjectCollideWorldBound(this.transform.position, this.collider2D);
		if (status != GlobalBehavior.WorldBoundStatus.Inside) {
			Destroy(this.gameObject);
			globalBehavior.EggCount--;
		}
	}
	
	public void SetForwardDirection(Vector3 f)
	{
		transform.up = f;
	}
	
	
}
