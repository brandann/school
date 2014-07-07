package uwb.css390.BrandanHaertelEX3;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {

	// GUI Elements
	Button mQuit;
	Button mUp;
	Button mDown;
	Button mLeft;
	Button mRight;
	Button mStop;
	Button mCamera;
	SeekBar mSize;
	ImageView mHero;
	
	// Movement Variables
	final float SPEED = .25f;
	float kup = -SPEED;
	float kdown = SPEED;
	float kright = SPEED;
	float kleft = -SPEED;
	float knone = 0;
	
	// Transform Vaiables
	float kTransformX, kTransformY = 0;
	
	// Scale Variables
	final float SCALE = .05F;
	
	// Camera Variables
	final int CAMERA_PIC_REQUEST = 0;
	
	// Timmer Variables
	Handler mHandler = new Handler();
	final int TIME = 100;

	/**
	 * onCreate
	 */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load GUI Elements
        mQuit = (Button) findViewById(R.id.buttonQuit);
        mUp = (Button) findViewById(R.id.buttonUp);
        mDown = (Button) findViewById(R.id.buttonDown);
        mLeft = (Button) findViewById(R.id.buttonLeft);
        mRight = (Button) findViewById(R.id.buttonRight);
        mStop = (Button) findViewById(R.id.buttonStop);
        mCamera = (Button) findViewById(R.id.buttonCamera);
        mSize = (SeekBar) findViewById(R.id.seekSize);
        mHero = (ImageView) findViewById(R.id.hero);

        //handle updatetime
        mHandler.postDelayed(updateHeroThread, TIME);
        setListeners();
        
        // Set initial SeekBar Value
        mSize.setProgress(mSize.getMax()/2);
        
    }
    
    /**
     * Set all the GUI Element Listeners and Actions
     */
    private void setListeners(){
    	// What to do when QuitButton is clicked?
        mQuit.setOnClickListener(
			new OnClickListener(){
		      public void onClick(View v){
		    	  processQuit();
		      }
		});
        
        mUp.setOnClickListener(new OnClickListener() { 
        	public void onClick(View v) {
        		transform(kup, knone);
        	}
        });
        
        mDown.setOnClickListener(new OnClickListener() { 
        	public void onClick(View v) {
        		transform(kdown, knone);
        	}
        });
        
        mLeft.setOnClickListener(new OnClickListener() { 
        	public void onClick(View v) {
        		transform(knone, kleft);
        	}
        });
        
        mRight.setOnClickListener(new OnClickListener() { 
        	public void onClick(View v) {
        		transform(knone, kright);
        	}
        });
        
        mStop.setOnClickListener(new OnClickListener() { 
        	public void onClick(View v) {
        		kTransformY = knone;
        		kTransformX = knone;
        	}
        });
        
        mSize.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				scale(progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});
        
       //create onClickListener for camera button
	    mCamera.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	//create an intent to use the phones camera application
	            Intent camera_intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	            //start activity and wait for specific call back
	            startActivityForResult(camera_intent, CAMERA_PIC_REQUEST);
	        }
	    });
        
    }
    
    
    @Override
	/**
	 * method tells application what too do when the call backs come from outside applications
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    switch(requestCode){
	        case CAMERA_PIC_REQUEST:
		        if(resultCode==RESULT_OK){
		        	//getting the raw bitmap data from the image that was taken
		           Bitmap yourSelectedImage = (Bitmap) data.getExtras().get("data");
		           //setting the bitmap to the image view in the application
		           mHero.setImageBitmap(yourSelectedImage);
		        }
		        break;
	    }
	}
    
    /**
     * Change the Scale of the Hero
     * @param progress Scale factor for Hero
     */
    private void scale(int progress){
    	Log.d("MyDebug", "Size Scale: " + progress);
    	mHero.setScaleX(progress * SCALE);
    	mHero.setScaleY(progress * SCALE);
    }

    /**
     * Change Delta X and Y
     * @param deltaY add to kTransformY
     * @param deltaX add to kTransformX
     */
    private void transform(float deltaY, float deltaX){
    	kTransformY += deltaY;
    	kTransformX += deltaX;
    }
    
    /**
     * Move the Hero
     */
    private void transform(){
    	mHero.setTranslationY(mHero.getTranslationY() + kTransformY);
    	mHero.setTranslationX(mHero.getTranslationX() + kTransformX);
    }
    
    /**
     * Quit the application
     */
    private void processQuit(){
    	System.exit(0);
		finish();
    }
    
    /**
     * Handles the Running update loop
     */
    private Runnable updateHeroThread = new Runnable() {
    	
    	/**
    	 * Runs at each update
    	 */
        public void run() {
            transform();
            mHandler.postDelayed(this, 0);
        }
    };
}
