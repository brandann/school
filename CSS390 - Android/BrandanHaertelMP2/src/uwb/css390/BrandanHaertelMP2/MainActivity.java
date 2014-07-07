package uwb.css390.BrandanHaertelMP2;

import java.io.IOException;
import java.io.InputStream;

import uwb.css390.BrandanHaertelMP2.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
	
	ImageView goal;
	
	TextView mAccelEchoX;
	TextView mAccelEchoY;
	TextView mAccelEchoZ;

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
 		goal = (ImageView) findViewById(R.id.goal); // load goal
 		//mAccelEchoX = (TextView) findViewById(R.id.AccelEchoX);
 		//mAccelEchoY = (TextView) findViewById(R.id.AccelEchoY);
 		//mAccelEchoZ = (TextView) findViewById(R.id.AccelEchoZ);
    }
    
    private void manageHero(){
    	// setup main view's touch service
		mMainView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						mCurrentHero = new Hero(mMainView.getContext(), mMainActivity, event.getX(), event.getY());
						mMainView.addView(mCurrentHero);
						mCurrentHero.setLocation(event.getX(), event.getY());
						Log.d("MyDebug", "Done!");
						break;
					case MotionEvent.ACTION_MOVE:
						if (null != mCurrentHero) {
							Log.d("MyDebug", "MovingCreated!");
							mCurrentHero.setLocation(event.getX(),  event.getY());
							//mCurrentHero.setX(event.getX()-mCurrentHero.getWidth()/2);
							//mCurrentHero.setY(event.getY()-mCurrentHero.getHeight()/2);
						}
						break;
					case MotionEvent.ACTION_UP:
						break;
					
				}
				return true;
			}
		});
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
					mCurrentHero.moveTo(-removeNoise(ax), removeNoise(ay));
					Log.d("MyDebug", "Accel X: " + ax + " Y: " + ay);
				}
				
				
			}
			
			
                 
             //mAccelEchoX.setText("X: " + String.valueOf(ax));
             //mAccelEchoY.setText("Y: " + String.valueOf(ay));
             //mAccelEchoZ.setText("Z: " + String.valueOf(az));
         }
         else{
         }
 	}
 	
 	private float removeNoise(float i){
 		float buffer = .2f;
 		if(i > buffer || i < (-buffer)){
 			return i*3;
 		}
 		return 0f;
 	}
 	
 	@Override
 	public void onAccuracyChanged(Sensor arg0, int arg1) {
 		// NOT IMPLEMENTED, BUT REQUIRED
 	}
 	// EndRegion 
}
