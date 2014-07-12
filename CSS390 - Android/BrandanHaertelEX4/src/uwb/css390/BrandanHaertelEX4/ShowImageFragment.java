package uwb.css390.BrandanHaertelEX4;

import android.app.Fragment;
import android.graphics.Bitmap;
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
		root = inflater.inflate(R.layout.show_image_fragment, container, false); // Inflate the layout for this fragment
        mImageShow = (ImageView) root.findViewById(R.id.imageShow);
        mTextShow = (TextView) root.findViewById(R.id.textShowfrag);
        mTextShow.setText("Sorry Show is not\nfully implemented and will\nnot display information ");
		return root;
    }
	
	public void setImage(String img){
	}
	
	public void setShowable(Bitmap b, String n){
		//mImageShow.setImageBitmap(b);
		System.out.println("Brandan is tired");
		//mTextShow = (TextView) .findViewById(R.id.textShow);
		//mTextShow.setText("nananan");
		
		/*
		 * I CANNOT EDIT ANY OF MY FRAGMENT VIEWS... I DONT KNOW WHY.
		 */
	}
	
	public void setName(String s){
		mTextShow = (TextView) getActivity().findViewById(R.id.textShowfrag);
		mTextShow.setText("nananan");
        //mTextShow.setText(s);
	}
	
	View root;
	ImageView mImageShow;
	TextView mTextShow;
}
