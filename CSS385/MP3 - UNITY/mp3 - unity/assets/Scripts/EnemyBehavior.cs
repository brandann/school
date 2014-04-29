using UnityEngine;
using System.Collections;

public class EnemyBehavior : MonoBehaviour {

	private enum State{
		Still,
		Ambient,
		Stunned
	};
	
	private const float kReferenceSpeed = 20f;
	public float mSpeed = kReferenceSpeed;
	public float mTowardsCenter = 0.5f;
	private State currentState;
	private int hitCount;
	public const int hitMax = 2;
	private float stunnedTime;
	private float stunnedInterval = 5;
	
	public Sprite StillSprite;
	public Sprite AmbientSprite;
	public Sprite RunSprite;
	public Sprite StunnedSprite;

	void Start () {
		hitCount = 0;
		setGlobalState ();
	}

	// Update is called once per frame
	void Update () {

		setGlobalState();
		
		if (run ()) {
			clamp ();
			return;
		}
		
		if (currentState == State.Still) {
			StillUpdate();
		} else if (currentState == State.Ambient) {
			AmbientUpdate();
		} else if (currentState == State.Stunned) {
			StunnedUpdate();
		}
	}

	private void setGlobalState()
	{
		if (currentState == State.Stunned) { //if stunned do not set to global setting
			return;
		}

		GlobalBehavior globalBehavior = GameObject.Find ("GameManager").GetComponent<GlobalBehavior>();
		if (globalBehavior.currentEnemyState == GlobalBehavior.EnemyState.Ambient) {
			currentState = State.Ambient;
			SpriteRenderer r = GetComponent<SpriteRenderer>();
			r.sprite = AmbientSprite;
		} 
		else {
			currentState = State.Still;
			SpriteRenderer r = GetComponent<SpriteRenderer>();
			r.sprite = StillSprite;
		}
	}

	private void clamp()
	{
		GlobalBehavior globalBehavior = GameObject.Find ("GameManager").GetComponent<GlobalBehavior>();
		GlobalBehavior.WorldBoundStatus status = globalBehavior.ObjectCollideWorldBound(this.transform.position, this.collider2D);
		if (status != GlobalBehavior.WorldBoundStatus.Inside) {
			NewDirection ();
		}
	}

	private bool run()
	{
		if (currentState == State.Stunned) { //if stunned do not run
			return false;
		}

		//check distance and if this object is in front of hero
		GameObject hero = GameObject.Find("hero");
		if (hero != null) {
			Vector3 v = new Vector3(transform.position.x - hero.transform.position.x, 
			                               transform.position.y - hero.transform.position.y, 0f);
			float d = v.magnitude;
			v.Normalize();
			Vector3 cross = Vector3.Cross(hero.transform.up, v);
			if(d < 30 && Vector3.Dot(hero.transform.up, v) > 0){
				float runRotate = 360f;
				float runSpeed = 19f;
				SpriteRenderer renderer = GetComponent<SpriteRenderer>();
				if (null != renderer) {
					renderer.sprite = RunSprite;
				}
				if(cross.z > 0f && Vector3.Dot(transform.up, v) > 0){
					transform.Rotate(Vector3.forward, 0f * (runRotate * Time.smoothDeltaTime)); //1
				}
				else if(cross.z < 0f && Vector3.Dot(transform.up, v) < 0){
					transform.Rotate(Vector3.forward, 1f * (runRotate * Time.smoothDeltaTime)); //1
				}
				else if(cross.z < 0f && Vector3.Dot(transform.up, v) > 0){
					transform.Rotate(Vector3.forward, 0f * (runRotate * Time.smoothDeltaTime)); //-1
				}
				else if(cross.z > 0f && Vector3.Dot(transform.up, v) < 0){
					transform.Rotate(Vector3.forward, -1f * (runRotate * Time.smoothDeltaTime)); //-1
				}
				transform.position += transform.up * (runSpeed * Time.smoothDeltaTime);
				return true;
			}
		}
		return false;
	}

	private void StillUpdate()
	{

	}

	private void AmbientUpdate()
	{
		transform.position += (mSpeed * Time.smoothDeltaTime) * transform.up;
		clamp ();
	}

	private void StunnedUpdate()
	{
		transform.Rotate(Vector3.forward, 1 * (9f * Time.smoothDeltaTime));
		if ((Time.realtimeSinceStartup - stunnedTime) > stunnedInterval) {
			currentState = State.Still;
			setGlobalState();
		}
	}
	
	// New direction will be something randomly within +- 45-degrees away from the direction
	// towards the center of the world
	//
	// To find an angle within +-45 degree of a direction: 
	//     1. consider the simplist case of 45-degree above or below the x-direction
	//	   2. we compute random.X: a randomly generate x-value between +1 and -1
	//     3. To ensure within 45 degrees, we simply need to make sure generating a y-value that is within the (-random.X to +random.X) range
	//     4. Now a direction towards the (random.X, random.Y) is guaranteed to be within 45-degrees from x-direction
	// Apply the above logic, only now:
	//		X-direciton is V (from current direciton towards the world center)
	//		Y-direciton is (V.y, -V.x)
	//
	// Lastly, 45-degree is nice because X=Y, we can do this for any angle that is less than 90-degree
	private void NewDirection() {
		GlobalBehavior globalBehavior = GameObject.Find ("GameManager").GetComponent<GlobalBehavior>();
		
		// we want to move towards the center of the world
		Vector2 v = globalBehavior.WorldCenter - new Vector2(transform.position.x, transform.position.y);  
		// this is vector that will take us back to world center
		v.Normalize();
		Vector2 vn = new Vector2(v.y, -v.x); // this is a direciotn that is perpendicular to V
		
		float useV = 1.0f - Mathf.Clamp(mTowardsCenter, 0.01f, 1.0f);
		float tanSpread = Mathf.Tan( useV * Mathf.PI / 2.0f );
		
		float randomX = Random.Range(0f, 1f);
		float yRange = tanSpread * randomX;
		float randomY = Random.Range (-yRange, yRange);
		
		Vector2 newDir = randomX * v + randomY * vn;
		newDir.Normalize();
		transform.up = newDir;
	}

	void OnTriggerEnter2D(Collider2D other)
	{
		
		if (other.gameObject.name == "Projectile(Clone)") {
			DestroyEgg(other);
			if (currentState == State.Stunned) {
				hitCount++;
				if(hitCount > hitMax){
					hitCount = 0;
					DestroyEnemy();
				}
			}
			else {//if (currentState == State.Ambient) {
				currentState = State.Stunned;
				stunnedTime = Time.realtimeSinceStartup;
				SpriteRenderer r = GetComponent<SpriteRenderer>();
				r.sprite = StunnedSprite;
			}
		}
	}
	
	private void DestroyEnemy()
	{
		Destroy(this.gameObject);
		GlobalBehavior globalBehavior = GameObject.Find ("GameManager").GetComponent<GlobalBehavior>();
		globalBehavior.EnemyCount--;
	}
	
	private void DestroyEgg(Collider2D other)
	{
		Destroy(other.gameObject);
		GlobalBehavior globalBehavior = GameObject.Find ("GameManager").GetComponent<GlobalBehavior>();
		print ("eggHit");
		globalBehavior.EggCount--;
	}
}

