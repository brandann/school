package uwb.css390.BrandanHaertelMP2;

import java.io.IOException;
import java.io.InputStream;

import uwb.css390.BrandanHaertelMP2.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
	
	RelativeLayout mMainView;
	MainActivity mMainActivity;
	Hero mCurrentHero = null;
	String mHeroImagePath = "duck.png";
	
	Bitmap mBitmap;
	
	Goal goal;
	
	TextView mAccelEchoX;
	TextView mAccelEchoY;
	TextView mAccelEchoZ;
	
	// Scores
	TextView mTextHeroCount;
	TextView mTextScoreCount;
	int mHeroCount = 0;
	int mScoreCount = 0;
	
	// params
	int mSWidth; // screen width
	int mSHeight; // screen Height
	
	int mMoveFactor = 1;
	float buffer = 0f;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        init();
 		manageHero();
 		enableAccelerometer(true);
 		
    }
    
    private void init(){
    	mMainActivity = this;
 		mMainView = (RelativeLayout) findViewById(R.id.mainView); // get access to the main layout
 		//goal = (ImageView) findViewById(R.id.goal); // load goal
 		goal = new Goal(mMainView.getContext(), mMainActivity);
 		
 		mMainView.addView(goal);
 		Display display = getWindowManager().getDefaultDisplay();
 		Point size = new Point();
 		display.getSize(size);
 		goal.setLocation(size);
 		
 		
 		mTextHeroCount = (TextView) findViewById(R.id.textHeroCount);
 		mTextScoreCount = (TextView) findViewById(R.id.textScoreCount);
 		updateHeroCount(0);
 		updateScoreCount(0);
 		
// 		goal.setX(mMainView.getWidth()/2 - goal.getWidth()/2);
// 		goal.setY(mMainView.getHeight() - goal.getHeight());
    }
    
    private void manageHero(){
    	// setup main view's touch service
		mMainView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						makeHero(event.getX(), event.getY());
						break;
					case MotionEvent.ACTION_MOVE:
						if (null != mCurrentHero) {
							Log.d("MyDebug", "MovingCreated!");
							mCurrentHero.setLocation(event.getX(),  event.getY());
						}
						break;
					case MotionEvent.ACTION_UP:
						break;
					
				}
				return true;
			}
		});
		
		
    }
    
    private void makeHero(float x, float y){
    	mCurrentHero = new Hero(mMainView.getContext(), mMainActivity, x, y);
		mMainView.addView(mCurrentHero);
		mCurrentHero.setLocation(x, y);
		updateHeroCount(1);
		Log.d("MyDebug", "Done!");
    }
    
    private void updateHeroCount(int i){
    	mHeroCount += i;
    	mTextHeroCount.setText("Hero Count: " + mHeroCount + " Heros!");
    }
    
    private void updateScoreCount(int i){
    	mScoreCount += i;
    	mTextScoreCount.setText("Score: " + mScoreCount);
    }
    
 // Region: Accelerometer support
    private void enableAccelerometer(boolean enabled) {
 		SensorManager accelManager = (SensorManager) getSystemService(SENSOR_SERVICE);
 		
 		if (enabled) { 
 			//get a sensor manager for accelerometer
 			accelManager.registerListener(this, 
 	    				accelManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
 	    				SensorManager.SENSOR_DELAY_NORMAL);
 		} else {
 			accelManager.unregisterListener(this);
 		}
 	}
 	
 	//getting accelerometer information
 	@Override
 	public void onSensorChanged(SensorEvent event) {
         if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
         	//accelerometer readings: http://developer.android.com/reference/android/hardware/SensorEvent.html#values
			float ax=event.values[0];
			float ay=event.values[1];
			float az=event.values[2];
			
			if(mCurrentHero != null){
				float x = removeNoise(ax);
				float y = removeNoise(ay);
				if(x != 0 && y != 0){
					moveTo(removeNoise(ax), removeNoise(ay));
					Log.d("MyDebug", "Accel X: " + ax + " Y: " + ay);
				}
			}
         }
         else{
         }
 	}
 	
 	private float removeNoise(float i){
 		
 		if(i > buffer || i < (-buffer)){
 			return i * mMoveFactor;
 		}
 		return 0f;
 	}
 	
 	private void moveTo(float x, float y){
 		float centerX = mCurrentHero.getX() + (mCurrentHero.getWidth()/2);
 		float centerY = mCurrentHero.getY() + (mCurrentHero.getHeight()/2);
 		if(collision()){
 			updateScoreCount(1);
 			removeView(mCurrentHero);
 			return;
 		}
 		// clamp left and right side
 		if(centerX < 0){
 			mCurrentHero.setX(0 - mCurrentHero.getWidth()/2);
 			x = 0;
 		}
 		else if(centerX > mMainView.getWidth()){
 			mCurrentHero.setX(mMainView.getWidth() - mCurrentHero.getWidth()/2);
 			x = 0;
 		}
 		if(centerY < 0 || centerY > mMainView.getHeight()){
 			removeView(mCurrentHero);
 			return;
 		}
 		mCurrentHero.moveTo(-removeNoise(x), removeNoise(y));
		Log.d("MyDebug", "Accel X: " + x + " Y: " + y);
 	}
 	
 	private boolean collision(){
 		float centerX = mCurrentHero.getX() + (mCurrentHero.getWidth()/2);
 		float centerY = mCurrentHero.getY() + (mCurrentHero.getHeight()/2);
 		
 		if(centerX > goal.getX() && centerX < (goal.getX() + goal.getWidth())){
 			if(centerY > goal.getY() && centerY < (goal.getY() + goal.getHeight())){
 	 			return true;
 	 		}
 		}
 		return false;
 	}
 	public void makeCurrent(View v){
 		mCurrentHero = (Hero) v;
 	}
 	
 	public void removeView(View v){
 		mMainView.removeView(v);
 		mCurrentHero = null;
 		updateHeroCount(-1);
 	}
 	
 	@Override
 	public void onAccuracyChanged(Sensor arg0, int arg1) {
 		// NOT IMPLEMENTED, BUT REQUIRED
 	}
 	// EndRegion 
}
