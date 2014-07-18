package uwb.css390.BrandanHaertelEX5;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SimpleFragment extends Fragment {

	DrawArea mDrawArea;

	private TextView mMissed;
	private TextView mCaught;
	private TextView mScreen;
	
	public SimpleFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.simple_fragment_layout, container, false);

		// initilize views
		mMissed = (TextView) rootView.findViewById(R.id.txtMiss);
		mCaught = (TextView) rootView.findViewById(R.id.txtCaught);
		mScreen = (TextView) rootView.findViewById(R.id.txtScreen); 
		
		// initilize draw area
		mDrawArea = (DrawArea) rootView.findViewById(R.id.drawArea);
		mDrawArea.setMissedEcho(mMissed);
		mDrawArea.setCaughtEcho(mCaught);
		mDrawArea.setOnScreenEcho(mScreen);		
				
		return rootView;
	}
}
