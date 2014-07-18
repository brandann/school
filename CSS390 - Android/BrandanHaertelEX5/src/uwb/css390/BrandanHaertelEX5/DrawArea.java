package uwb.css390.BrandanHaertelEX5;

import java.util.ArrayList;
import java.util.Random;

import uwb.css390.GraphicsSupport.BitmapShape;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
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
		mFlingDetector = new GestureDetectorCompat(context,	// context 
				   new MySimpleGestureListener());	// listener
		Heros = new ArrayList();
		mHandler.postDelayed(updateHeros, TIME);
		mHero = new Hero(mContext, mTurtle,(int) 400,(int) 500, 71*2, 100*2);
		setLongClickable(true);
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
		Log.i("MyDebug", "onTouchEvent-" + event.toString());
		mFlingDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {		
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			createEnemy(event.getX(), event.getY());
			break;
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
		for(Enemy bs : Heros){
			if(mHero.intersects(bs)){
				removeHero(bs);
				caught++; 
				break;
			}
			if(bs.getCount() >= 5){
				System.out.println("Enemy Count: " + bs.getCount());
				removeHero(bs);
				missed++;
				break;
			}
		}
		
		for(Enemy bs : Heros){
			bs.updateMode();
			bs.updateShapeWithVelocity();
			bs.Draw(c);
		}
		
		mHero.setWorld(getWidth(), getHeight());
		
		mHero.updateMode();
		mHero.updateShapeWithVelocity();
		mHero.Draw(c);
		echoStats();
	}
	
	private void createEnemy(float x, float y){
		Random rand = new Random();
		float rx = (float) rand.nextInt(10);
		float ry = (float) rand.nextInt(10);
		if(rand.nextBoolean()){
			rx *= -1;
		}
		if(rand.nextBoolean()){
			ry *= -1;
		}
		//createEnemy(x, y, rx, ry);
	}
	
	private void createEnemy(float x, float y, float vx, float vy){
		Enemy hero = new Enemy(mContext, mFish,(int) x,(int) y, kShapeSizeW, kShapeSizeH);
		hero.setVelocity(vx, vy);
		hero.setWorld(getWidth(), getHeight());
		Heros.add(hero);
		onScreen++;
		echoStats();
		invalidate();
	}
	
	private void removeHero(Enemy bs){
		Heros.remove(bs);
		onScreen--;
		echoStats();
	}
	
	
	
	
	// Runnable
	// -------------------------------------------------------------------------------------------------
	private Runnable updateHeros = new Runnable() {
		public void run() {
			invalidate();
			mHandler.postDelayed(this, 0);
		}
	};
	
	
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
            //mHero.moveTo(mHero.getX() - event.values[0], mHero.getY() + event.values[1]);
			mHero.setVelocity(-event.values[0], event.values[1]);
			//mHero.updateShapeWithVelocity();
            invalidate();
        }
	}
	
	
	// SimpleGestureListener class
	// @author ksung
	// -------------------------------------------------------------------------------------------------
	private class MySimpleGestureListener extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onDown(MotionEvent e) {
			Log.i("MyDebug", "Gesture: Down");
			return true;  // continue to propagate this event (otherwise will not propergate to the rest of the services)
		}
	
		@Override
		public void onShowPress(MotionEvent e) {
			Log.i("MyDebug", "Gesture: ShowPress");
		}
	
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			Log.i("MyDebug", "Gesture: SingleTapUp");
			return true;  // continue to propergate
		}
	
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			Log.i("MyDebug", "Gesture: Scroll");
			return true;
		}
	
		@Override
		public void onLongPress(MotionEvent e) {
			Log.i("MyDebug", "Gesture: LongPress");
		}
	
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,	float velocityY) {
			Log.i("MyDebug", "Gesture: Fling" + velocityX + ":" + velocityY);
			float VX = velocityX / 800;
			float VY = velocityY / 800;
			
			System.out.println("VX: " + VX + "\nVY: " + VY);
			
			createEnemy(e2.getX(), e2.getY(), VX, VY);
			return false; // do not propergate anymore, we are handling the event
		}
	};
	
	
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
	Hero mHero;
	ArrayList<Enemy> Heros;
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
