using UnityEngine;
using System.Collections;

public class HeroBehaviour : MonoBehaviour {

	public GameObject mProjectile = null;

	public float kHeroSpeed = 20f;
	public float kHeroRotateSpeed = 180f; //90deg in 2 seconds
	private float kProjectileFireCount = 0;
	public float interpolationPeriod = 0.1f;
	public AudioClip sound;

	// Use this for initialization
	void Start () {
		if (mProjectile == null) {
			mProjectile = Resources.Load ("Prefabs/Projectile") as GameObject;
		}
	}
	
	// Update is called once per frame
	void Update () {

		clamp ();

		#region user movement control
		transform.position += Input.GetAxis ("Vertical")  * transform.up * (kHeroSpeed * Time.smoothDeltaTime);
		transform.Rotate(Vector3.forward, -1f * Input.GetAxis("Horizontal") * (kHeroRotateSpeed * Time.smoothDeltaTime));
		#endregion




		if (Input.GetAxis ("Fire1") > 0f) {
			kProjectileFireCount += Time.deltaTime;
			if(kProjectileFireCount >= interpolationPeriod)
			{
				kProjectileFireCount = 0f;
				GameObject e = Instantiate(mProjectile) as GameObject;
				ProjectileBehaviour projectile = e.GetComponent<ProjectileBehaviour>();
				if(projectile != null) {
					e.transform.position = transform.position;
					projectile.SetForwardDirection(transform.up);
					incrementEgg();
					audio.PlayOneShot(sound);
				}
			}
		}
		
		
	}
	
	private void incrementEgg()
	{
		GlobalBehavior globalBehavior = GameObject.Find ("GameManager").GetComponent<GlobalBehavior>();
		globalBehavior.EggCount++;
	}

	private void clamp()
	{
		GlobalBehavior globalBehavior = GameObject.Find ("GameManager").GetComponent<GlobalBehavior>();
		GlobalBehavior.WorldBoundStatus status = globalBehavior.ObjectCollideWorldBound(this.transform.position, this.collider2D);
		if (status != GlobalBehavior.WorldBoundStatus.Inside) {
			if(transform.position.x > globalBehavior.WorldMax.x) {transform.position += new Vector3(-.5f,0);}
			else if(transform.position.y > globalBehavior.WorldMax.y) {transform.position += new Vector3(0,-.5f);}
		}
	}
}
