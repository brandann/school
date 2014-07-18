package uwb.css390.BrandanHaertelFinal;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class DrawArea extends SurfaceView implements SurfaceHolder.Callback, OnTouchListener, SensorEventListener {
	
	// Constructor
	// -------------------------------------------------------------------------------------------------
	public DrawArea(Context context) {
		super(context);
		initSurface(context);	
	}
	
	public DrawArea(Context context, AttributeSet attrs) {
		super(context, attrs);
		initSurface(context);
	}
		
	private void initSurface(Context context) {	
		mDrawSurface = getHolder();
		mDrawSurface.addCallback(this); // enable the calling of SurfaceHolder.Callback functions!
		mContext = context; // initialize Context
		enableAccelerometer(true);
		invalidate();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		setWillNotDraw(false);			
		setBackgroundColor(Color.GRAY);
		setOnTouchListener(this); // enable onTouch
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		mWidth = width;
		mHeight = height;
	}
	
	
	// Touch Listener
	// -------------------------------------------------------------------------------------------------
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {		
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:		
		case MotionEvent.ACTION_POINTER_UP:			
		}
	
		//invalidate();  // forces redraw!
		return false; // event handled
	}
	
	
	// Game Behavior
	// -------------------------------------------------------------------------------------------------
	@Override
	protected void onDraw(Canvas c) {
		if (this.isInEditMode())
			return;
		
	}

	
	// Accelerometer
	// -------------------------------------------------------------------------------------------------
	private void enableAccelerometer(boolean enabled) {
		SensorManager accelManager = (SensorManager) getContext().getSystemService("sensor");
		if (enabled) { 
			//get a sensor manager for accelerometer
			accelManager.registerListener(this, 
	    				accelManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
	    				SensorManager.SENSOR_DELAY_FASTEST);
		} else {
			accelManager.unregisterListener(this);
		}
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            invalidate();
        }
	}
	
	
	// Members
	// -------------------------------------------------------------------------------------------------

	
	SurfaceHolder mDrawSurface = null;
	Context mResources = null;
	int mWidth, mHeight; // Width and Height of the drawing area
	final int kShapeSizeW = 48;
	final int kShapeSizeH = 100;
	static final String PLAY = "Play";
	static final String DELETE = "Delete";
	static final String CREATE = "Create";
	String GameState = CREATE;
	Handler mHandler = new Handler();
	final int TIME = 100;
	Context mContext;
	GestureDetectorCompat mFlingDetector; 

	//Game Objects
	final String mTurtle = "hero.png";
	final String mFish = "fish.png";
	int missed = 0, caught = 0, onScreen = 0;
	private TextView mMissedEcho;
	private TextView mCaughtEcho;
	private TextView mScreenEcho;
	void setMissedEcho(TextView e)   { mMissedEcho = e; }
	void setCaughtEcho(TextView e)   { mCaughtEcho = e; }
	void setOnScreenEcho(TextView e) { mScreenEcho = e; }
	void echoStats(){
		mMissedEcho.setText("Missed: " + missed);
		mCaughtEcho.setText("Caught: " + caught);
		mScreenEcho.setText("OnScreen: " + onScreen);
	}
	
	
	// Notused
	// -------------------------------------------------------------------------------------------------
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {}
}
