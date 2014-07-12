package uwb.css390.BrandanHaertelEX4;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnSystemUiVisibilityChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

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
		//addFrag(mSelectFrag = new SelectImageFragment());
		addFrag(mSelectFrag);
		mImageMain.setVisibility(View.INVISIBLE);
	}
	
	private void ShowSelected(boolean checked){
		if(checked){
			addFrag(mShowFrag);
			removeFrag(mSelectFrag);
			mImageMain.setVisibility(View.INVISIBLE);
			//ImageView img = (ImageView) findViewById(R.id.imageShow);
			//TextView txt = (TextView) findViewById(R.id.textShow);
			//img.setImageBitmap(mSelectFrag.getThumbnailImage());
			//txt.setText(mSelectFrag.getImageName());
			//TextView txt = (TextView) mShowFrag..findViewById(R.id.textShow);
			mShowFrag.setShowable(mSelectFrag.getThumbnailImage(), mSelectFrag.getImageName());
			//txt.setText("Brandan");
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
		
		mButtonImage = (Button) findViewById(R.id.buttonImage);
		mShowSelected = (CheckBox) findViewById(R.id.checkSelected);
		mImageMain = (ImageView) findViewById(R.id.imageSelect);
		
		SelectImageFragment.OnSimpleFragmentCallbackListener listener = 
				new SelectImageFragment.OnSimpleFragmentCallbackListener() {
					@Override
					public void onSimpleFragmentCallback() {
						removeFrag(mSelectFrag);
						//mShowFrag.setShowable(mSelectFrag.getThumbnailImage(), mSelectFrag.getImageName());
						//mShowFrag.setName(mSelectFrag.getImageName());
						//mImageMain.setVisibility(View.VISIBLE);
						System.out.println("GOT CALL BACK: " + mSelectFrag.getImageName());
						//mShowFrag.setName(mSelectFrag.getImageName());
						//ImageView img = (ImageView) findViewById(R.id.imageShow);
						//TextView txt = (TextView) findViewById(R.id.textShow);
						//img.setImageBitmap(mSelectFrag.getThumbnailImage());
						//txt.setText(mSelectFrag.getImageName());
					}
			};
			
		
		mSelectFrag = new SelectImageFragment();
		mSelectFrag.setSimpleFragmentCallbackListener(listener);
		
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
	
	public void setImage(int id){
		
	}
	
	private SelectImageFragment mSelectFrag = null;
	private ShowImageFragment mShowFrag = null;
	private Button mButtonImage;
	private CheckBox mShowSelected;
	private ImageView mImageMain;
	
}
