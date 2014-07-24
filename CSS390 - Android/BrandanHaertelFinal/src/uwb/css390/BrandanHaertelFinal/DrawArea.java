package uwb.css390.BrandanHaertelFinal;

import java.text.SimpleDateFormat;
import java.util.Date;

import uwb.css390.GraphicsSupport.BitmapShape;
import uwb.css390.GraphicsSupport.Hero;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

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
		
		invalidate();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		setWillNotDraw(false);			
		setBackgroundColor(Color.WHITE);
		setOnTouchListener(this); // enable onTouch
		
		invalidate();
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		mWidth = width;
		mHeight = height;
	}
	
	public void setActivity(Activity a){
		activity = a;
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
			if(paused){
				makeToast("Tap The Screen To Pause The Game", Toast.LENGTH_SHORT);
			}
			else {
				makeToast("Tap The Screen To Continue The Game", Toast.LENGTH_SHORT);
			}
			
			paused = !paused;
			ToggleMusic();
			break;
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:		
		case MotionEvent.ACTION_POINTER_UP:			
		}
	
		//invalidate();  // forces redraw!
		return false; // event handled
	}
	
	final static String songName = "background.wav";
	private final static int kMaxVolume = 100;
	private int mCurrentVolume = 50;
	
	private void ToggleMusic(){
			
	    if (paused) { 	// We want to stop the player
	    	//stop the mediaPlayer
	        mPlayer.pause();
	    }
	    // not playing, we want to start a new player
	    else {
	    	//setupMediaPlayer();
	        mPlayer.start();
	    }
	}
	
	/*
	 private void ToggleMusic(){
		try {	
		    if (mPlayer.isPlaying()) { 	// We want to stop the player
		    	//stop the mediaPlayer
		        mPlayer.stop();
		        //get rid of mediaPlayer and get a new one standing by
		        mPlayer.release();
		        mPlayer = new MediaPlayer();
		    }
		    // not playing, we want to start a new player
		    else {
		    	//open up the song from the assets folder
		        AssetFileDescriptor descriptor = activity.getAssets().openFd(songName);
		        //load the location of the song to the mediaplayer
		        mPlayer.setDataSource(	descriptor.getFileDescriptor(), 
		        						descriptor.getStartOffset(), 
		        						descriptor.getLength());
		        descriptor.close();
		       
		        //prepare the mediaPlayer, "initialized but stopped"
		        mPlayer.prepare();
		        //float vol = (float) (1 - (Math.log(kMaxVolume - mCurrentVolume) / Math.log(kMaxVolume)));
		        float vol = kMaxVolume;
				mPlayer.setVolume(vol,vol); // left and right volume
		        
		        mPlayer.setLooping(true);
		        //start the song
		        mPlayer.start();
		    }
	    } 
	    catch (Exception e) { }
	}
	 */
	
	private void setupMediaPlayer(){
		mPlayer = new MediaPlayer();
		try {	
	    	//open up the song from the assets folder
	        AssetFileDescriptor descriptor = activity.getAssets().openFd(songName);
	        //load the location of the song to the mediaplayer
	        mPlayer.setDataSource(	descriptor.getFileDescriptor(), 
	        						descriptor.getStartOffset(), 
	        						descriptor.getLength());
	        descriptor.close();
	       
	        //prepare the mediaPlayer, "initialized but stopped"
	        mPlayer.prepare();
	        //float vol = (float) (1 - (Math.log(kMaxVolume - mCurrentVolume) / Math.log(kMaxVolume)));
	        float vol = kMaxVolume;
			mPlayer.setVolume(vol,vol); // left and right volume
	        
	        mPlayer.setLooping(true);
	    } 
	    catch (Exception e) { }
	}
	
	private void makeToast(String s, int length){
		Context context = mContext;
		CharSequence text = s;
		int duration = length;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	
	public void saveBitmap(){
		
		if(mUser == null || mMustache == null){
			makeToast("Take a picture before saving your game image!", Toast.LENGTH_SHORT);
			return;
		}
		
		try {

	        Bitmap bitmap = Bitmap.createBitmap(
	        		(int) (mUser.getBitmap().getWidth() * scale),
	        		(int) (mUser.getBitmap().getHeight() * scale),
	        		Config.ARGB_8888);
	        Canvas c = new Canvas(bitmap);
	        Resources res = getResources();


	        Bitmap bitmap1 = mUser.getBitmap(); //blue

	        Bitmap bitmap2 = mMustache.getBitmap(); //green
	        Drawable drawable1 = new BitmapDrawable(bitmap1);
	        Drawable drawable2 = new BitmapDrawable(bitmap2);


	        drawable1.setBounds(mUser.getBound());
	        drawable2.setBounds(mMustache.getBound());
	        drawable1.draw(c);
	        drawable2.draw(c);
	        
	        //create a name for the saved bitmap
	        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	        String name = "MyPic_" + timeStamp + ".jpg";
	        				     
	        Activity myActivity = activity;
	        ContentResolver resolver = myActivity.getContentResolver();
			String result = MediaStore.Images.Media.insertImage( resolver, bitmap, name, "Image created by Brandan" );
			
			makeToast("Congradulations! Your Image Has Been Saved", Toast.LENGTH_LONG);

	    } catch (Exception e) {
	    }
	}
	
	
	// Game Behavior
	// -------------------------------------------------------------------------------------------------
	@Override
	protected void onDraw(Canvas c) {
		if (this.isInEditMode())
			return;
		if(mUser != null){
			mUser.Draw(c);
			mMustache.updateMode();
			mMustache.updateShapeWithVelocity();
			mMustache.Draw(c);
		}
	}
	
	float scale;
	
	public void setImage(Bitmap b){
		System.out.println("Bitmap Size: " + b.getHeight() + ", " + b.getWidth());
		mUser = new BitmapShape(mContext, b, mWidth/2,mHeight/2, getWidth(), getHeight());
		float sw = mWidth / b.getWidth();
		float sh = mHeight / b.getHeight();
		scale = (sw<sh)? sw:sh;
		mUser.setScale( scale );
		//mMustache = new BitmapShape(mContext, MUSTACHE, mWidth/2, mHeight/2, 2006,665);
		System.out.println("background size: " + mWidth + ", " + mHeight );
		int sW = getWidth()/3;
		int sH = (int) (sW * .3315);
		System.out.println("Mustache Size: " + sW + ", " + sH );
		mMustache = new Hero(mContext, MUSTACHE, getWidth()/2, getHeight()/2, 2006, 665);
		mMustache.setScale(.3f);
		enableAccelerometer(true);
		mMustache.setWorld(getWidth(), getHeight());
		//paused = false;
		makeToast("Tap The Screen To Begin", Toast.LENGTH_SHORT);
		setupMediaPlayer();
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
			if(!paused){
				mMustache.setVelocity(-event.values[0], event.values[1]);
	            invalidate();
			}
        }
	}
	
	// Members
	// -------------------------------------------------------------------------------------------------

	private boolean paused = true;
	SurfaceHolder mDrawSurface = null;
	Context mResources = null;
	int mWidth, mHeight; // Width and Height of the drawing area
	final int kShapeSizeW = 48;
	final int kShapeSizeH = 100;
	static final String PLAY = "Play";
	static final String DELETE = "Delete";
	static final String CREATE = "Create";
	String GameState = CREATE;
	final int TIME = 100;
	Context mContext;
	static final String MUSTACHE = "mustache.png";
	
	BitmapShape mUser = null;
	Hero mMustache = null;
	
	Activity activity;
	MediaPlayer mPlayer;
	
	// Notused
	// -------------------------------------------------------------------------------------------------
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {}
}
