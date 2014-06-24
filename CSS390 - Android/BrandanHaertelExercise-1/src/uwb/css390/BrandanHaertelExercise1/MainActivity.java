package uwb.css390.BrandanHaertelExercise1;

import uwb.css390.BrandanHaertelExercise1.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	// define a instant variable so that we can have access to the echoString
	TextView mEchoLabel;
	Button mQuitButton;
	Button mGoodbyeButton;
	int mCount = 5;
	
	// this variable remembers what is the current message in mEchoLabel
	boolean mIsHello = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// connect instance variables to the UI ids
		mEchoLabel = (TextView) findViewById(R.id.echoString);
		mQuitButton = (Button) findViewById(R.id.quitButton);
		mGoodbyeButton = (Button) findViewById(R.id.goodbyeButton);
		
		// initilize the new quit button text
		mQuitButton.setText("Quit in: " + mCount);
		
		// What to do when QuitButton is clicked?
		mQuitButton.setOnClickListener(
			new OnClickListener()
		    {
		      public void onClick(View v)
		      {
		    	  quitApp();
		      }
		});
		
		// What to do when GoodbyeButton is clicked?
		mGoodbyeButton.setOnClickListener(
			new OnClickListener() 
			{
				public void onClick(View v)
				{
					processGoodByeButton();
				}
			});
	}
	
	// logic for goodbye button
	private void processGoodByeButton()
	{
		mCount--;
		if(mCount > 0) {
			
			// change the text of the quit button to show
			// how many more clicks until app closes!
			mQuitButton.setText("Quit in: " + mCount);
		}
		else {
			quitApp();
		}
		if (mIsHello) {
			// if currently seeing "Hello World", 
			// 		change the message to "Goodbye"
			//		and change the button to say "Hello"
			mEchoLabel.setText("Good Bye World!");
			mGoodbyeButton.setText("Hello");
		} else {
			// currently seeing goodbye, let's flip everyting 
			mEchoLabel.setText("Hello World");
			mGoodbyeButton.setText("GoodBye");
		}
		mIsHello = !mIsHello;
	}
	
	// Private function to quit the application
	private void quitApp()
	{
		System.exit(0);
		finish();
	}
}
