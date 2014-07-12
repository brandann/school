package uwb.css390.BrandanHaertelEX4;

import uwb.css390.BrandanHaertelEX4.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initComponents();
		
	}
	
	private void SelectImage(){
		if(mShowSelected.isChecked())
			mShowSelected.setChecked(false);
		mImageMain.setVisibility(View.INVISIBLE);
		removeFrag(mSelectFrag);
		addFrag(mSelectFrag = new SelectImageFragment());
	}
	
	private void ShowSelected(boolean checked){
		if(checked){
			addFrag(mShowFrag);
			removeFrag(mSelectFrag);
			mImageMain.setVisibility(View.INVISIBLE);
		}
		else{
			removeFrag(mShowFrag);
			mImageMain.setVisibility(View.VISIBLE);
		}
	}
	
	private void addFrag(Fragment f){
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.mainContainer, f);
		fragmentTransaction.commit();
	}
	
	private void removeFrag(Fragment f){
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.remove(f);
		fragmentTransaction.commit();
	}
	
	private void initComponents(){
		mShowFrag = new ShowImageFragment();
		mSelectFrag = new SelectImageFragment();
		mButtonImage = (Button) findViewById(R.id.buttonImage);
		mShowSelected = (CheckBox) findViewById(R.id.checkSelected);
		mImageMain = (ImageView) findViewById(R.id.imageSelect);
		
		mButtonImage.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				SelectImage();
			}
		});
		
		mShowSelected.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				ShowSelected(arg1);
			}
		});
	}

	
	private SelectImageFragment mSelectFrag = null;
	private ShowImageFragment mShowFrag = null;
	private Button mButtonImage;
	private CheckBox mShowSelected;
	private ImageView mImageMain;
	
}
