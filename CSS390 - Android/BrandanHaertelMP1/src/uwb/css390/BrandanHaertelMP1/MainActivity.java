package uwb.css390.BrandanHaertelMP1;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	//MainActivity Objects
	TextView mTextTime;
	TextView mTextSpeed;
	TextView mTextCount;
	ProgressBar mProgressTime;
	SeekBar mSeekSpeed;
	Button mButtonInc;
	Button mButtonDec;
	Button mButtonQuit;
	
	// timer support
	Timer mGlobalTimer;
	MyTimerTask mTimerTask;
	
	//Vars
	int mSpeed = 5;
	int mSecond = 0;
	int mMillisecond = 0;
	int mScale = 100;
	

    @TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mTextTime = (TextView) findViewById(R.id.textTime);
        mTextSpeed = (TextView) findViewById(R.id.textSpeed);
        mTextCount = (TextView) findViewById(R.id.textCount);
        mProgressTime = (ProgressBar) findViewById(R.id.progressTime);
        mSeekSpeed = (SeekBar) findViewById(R.id.seekSpeed);
        mButtonInc = (Button) findViewById(R.id.buttonInc);
        mButtonDec = (Button) findViewById(R.id.buttonDec);
        mButtonQuit = (Button) findViewById(R.id.buttonQuit);
        
        mTimerTask = new MyTimerTask(this, mTextCount, mProgressTime);
		mGlobalTimer = new Timer("GlobalTimer");
		mGlobalTimer.schedule(mTimerTask, 10, 10);
       
		mProgressTime.setOnDragListener(
				new OnDragListener() {

					@Override
					public boolean onDrag(View v, DragEvent event) {
						// TODO Auto-generated method stub
						if(mProgressTime.getProgress() == mProgressTime.getMax()){
							processIncCount(1);
							mProgressTime.setProgress(0);
						}
						return false;
					}
					
				});
		
        mSeekSpeed.setOnSeekBarChangeListener(
        		new OnSeekBarChangeListener() {
					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub
						processSeek(progress);
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
						// Not Used
					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
						// Not Used
					}
        		});
        
        // Initial Values
        mTextCount.setText("" + 0);
        mSeekSpeed.setProgress(5);
        mTextTime.setVisibility(android.view.View.INVISIBLE);
        
        // What to do when QuitButton is clicked?
        mButtonQuit.setOnClickListener(
			new OnClickListener(){
		      public void onClick(View v){
		    	  processQuit();
		      }
		});
		
		// What to do when IncButton is clicked?
		mButtonInc.setOnClickListener(
			new OnClickListener() {
				public void onClick(View v){
					processIncCount(1);
				}
			});
		
		// What to do when IncButton is clicked?
		mButtonDec.setOnClickListener(
			new OnClickListener() {
				public void onClick(View v){
					processIncCount(-1);
				}
			});
    }
    
    private void processIncCount(int i){
    	mTimerTask.incCount(i);
    }
    
    @SuppressWarnings("unused")
	private void processSetTime(int sec, int millsec){
    	mTextTime.setText("" + sec + ":" + millsec);
    }
    
    private void processSeek(int value){
    	mSpeed = value;
    	mProgressTime.setMax(mSpeed * mScale);
    	mTextSpeed.setText("Increment Rate: " + mSpeed + " Seconds");
    }
    
    private void processQuit(){
    	System.exit(0);
		finish();
    }
    
	// this is a separate thread and _CANNOT_ modify UI parameters!
	// Either use a Handler, or run this thread on the main UI thread
	// For details: http://stackoverflow.com/questions/4597690/android-timer-how
	class MyTimerTask extends TimerTask {

		private final int kUpdateInterval = 100;
		private final int kOneSecond = 1000;
		
		// Keeping track of what has been counted
		int mCount = 0;
		
		TextView mEcho;
		ProgressBar mProgress;
		MainActivity mTheActivity;
		
		// COnstructor
		public MyTimerTask(MainActivity gui, TextView v, ProgressBar b) { 
			mEcho = v;
			mProgress = b;
			mTheActivity = gui;
		}
		
		public void incCount(int i){
			mCount += i;
			mEcho.setText("" + mCount);
		}
		
		// This function is called once every our scheduled interval
		@Override
		public void run() {
			
			// Post a run event for UI thread to execute later
			mTheActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					mProgress.incrementProgressBy(1);
					if(mProgress.getProgress() == mProgress.getMax()){
						mProgress.setProgress(0);
						incCount(1);
					}
				}
			});
		}	
	}
}
