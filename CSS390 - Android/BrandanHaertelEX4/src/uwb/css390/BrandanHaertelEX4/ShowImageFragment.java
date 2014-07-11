package uwb.css390.BrandanHaertelEX4;

import uwb.css390.BrandanHaertelEX4.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowImageFragment extends Fragment{

public ShowImageFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.show_image_fragment, container, false); // Inflate the layout for this fragment
        return rootView;
    }
	
	public void setImage(String img){
	}
	
	ImageView mImageShow;
	TextView mTextShow;
}
