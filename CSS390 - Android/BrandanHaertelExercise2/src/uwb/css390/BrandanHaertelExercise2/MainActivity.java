package uwb.css390.BrandanHaertelExercise2;

import uwb.css390.BrandanHaertelExercise2.R;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	// define a instant variable so that we can have access to the echoString
	TextView mEchoLabel;
	Button mQuitButton;
	Button mDecButton;
	Button mIncButton;
	int mCount = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// connect instance variables to the UI ids
		mQuitButton = (Button) findViewById(R.id.buttonQuit);
		mDecButton = (Button) findViewById(R.id.buttonDec);
		mIncButton = (Button) findViewById(R.id.buttonInc);
		mEchoLabel = (TextView) findViewById(R.id.textView1);
		
		updateText("");
		
		// What to do when QuitButton is clicked?
		mQuitButton.setOnClickListener(
			new OnClickListener(){
		      public void onClick(View v){
		    	  quitApp();
		      }
		});
		
		// What to do when IncButton is clicked?
		mIncButton.setOnClickListener(
			new OnClickListener() {
				public void onClick(View v){
					processIncButton();
				}
			});
		
		// What to do when IncButton is clicked?
		mDecButton.setOnClickListener(
			new OnClickListener() {
				public void onClick(View v){
					processDecButton();
				}
			});
	}
	
	// logic for IncButton button
	private void processIncButton(){
		mCount++;
		mEchoLabel.setTextColor(Color.BLUE);
		updateText("Increment Pressed");
	}
	
	// logic for decButton
	private void processDecButton(){
		mCount--;
		mEchoLabel.setTextColor(Color.RED);
		updateText("Decrement Pressed");
	}
	
	private void updateText(String i){
		mEchoLabel.setText(i + "\n" + "Current Count = " + mCount);
	}
	
	// Private function to quit the application
	private void quitApp()
	{
		System.exit(0);
		finish();
	}
}
