package uwb.css390.BrandanHaertelEX4;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SelectImageFragment extends Fragment{
	
	public SelectImageFragment(){
		mMainActivity = getActivity();
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.select_image_fragment, container, false);
        mList = (ListView) rootView.findViewById(R.id.listSelect);
        populateList(); // this crashes
        return rootView;
    }
	
	private void populateList(){
		ListElementAdapter.SetLayoutInflater(getActivity());
		ListElementAdapter.initialzeData(getActivity().getAssets(), mMainActivity);
		ListElementAdapter adapter = new ListElementAdapter();
		mList.setAdapter(adapter);
	}
	
	public void setImage(String s){
		
	}
	
	ListView mList;
	Activity mMainActivity;
}
