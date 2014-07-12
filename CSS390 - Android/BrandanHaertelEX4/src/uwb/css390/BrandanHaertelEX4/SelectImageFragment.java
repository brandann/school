package uwb.css390.BrandanHaertelEX4;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
	
	private void killmyself(){
		getActivity().getFragmentManager().beginTransaction().remove(this).commit();
	}
	
	private Bitmap imageBitmap;
	public Bitmap getThumbnailImage(){
		return imageBitmap;
	}
	
	public String getImageName(){
		return imageName;
	}
	
	private String imageName;
	
//	public Bitmap getHiResImage(){
//		mImageRef.moveToPosition(imagePos);
//		long fileID = mImageRef.getLong(mImageRef.getColumnIndex(MediaStore.Images.Media._ID));
//		System.out.println("FileID: " + fileID);
//		Context c = getActivity().getApplicationContext();
//		return MediaStore.Images.Media.getBitmap(c.getContentResolver(), fileID);
//						//c.getContentResolver(), 
//						//fileID, 
//						//MediaStore.Images.Thumbnails.MINI_KIND, // or MICRO_KIND: too small? 
//						//null);
//	}
	
	OnSimpleFragmentCallbackListener mCallbackObject = null;
	public interface OnSimpleFragmentCallbackListener {
		public void onSimpleFragmentCallback();
	}
	// allow setting of the listener
	public void setSimpleFragmentCallbackListener(OnSimpleFragmentCallbackListener listener) {
		mCallbackObject = listener;
	}
	
	private void populateList(){
		ListElementAdapter.SetLayoutInflater(getActivity());
		ListElementAdapter adapter = new ListElementAdapter(initilizeData(), getActivity().getApplicationContext());
		mList.setAdapter(adapter);
		
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				ImageView img = (ImageView) getActivity().findViewById(R.id.imageSelect);
				mImageRef.moveToPosition(position);
				long fileID = mImageRef.getLong(mImageRef.getColumnIndex(MediaStore.Images.Media._ID));
				System.out.println("FileID: " + fileID);
				Context c = getActivity().getApplicationContext();
				img.setImageBitmap(MediaStore.Images.Thumbnails.getThumbnail(
								c.getContentResolver(), 
								fileID, 
								MediaStore.Images.Thumbnails.MINI_KIND, // or MICRO_KIND: too small? 
								null)
						);
				
				imageBitmap = MediaStore.Images.Thumbnails.getThumbnail(
						c.getContentResolver(), 
						fileID, 
						MediaStore.Images.Thumbnails.MINI_KIND, // or MICRO_KIND: too small? 
						null);

				img.setVisibility(View.VISIBLE);
				//killmyself();
				
				//ImageView image = (ImageView) mMainActivity.findViewById(R.id.imageSelect);
				
				// let's try to get the values back ...
				TextView name = (TextView) view.findViewById(R.id.listName);
				TextView date = (TextView) view.findViewById(R.id.listDate);
				
				System.out.println("Pos=" + position + "\nN=" + name.getText().toString() 
												+ "\nD=" + date.getText().toString());
				imageName = name.getText().toString();
				mCallbackObject.onSimpleFragmentCallback();
			}
			
		});
	}
	
	private Cursor initilizeData(){
	    // what are the data we want to access
	    String[] dataToAccess = new String[]{
		    	MediaStore.Images.Media._ID,			// This is image file ID
		        MediaStore.Images.Media.DISPLAY_NAME,   // filename
		        MediaStore.Images.Media.DATE_TAKEN		// creation date
		    };
	    String limitSize = MediaStore.Images.Media.DATE_TAKEN + " desc limit 25"; // desc: says descending order (in reverse newest first), limit 5: 5, e.g., can also do: "%s limit 5"
	    
	    // Make the query.
	    System.out.println(dataToAccess.toString());
	    Context c = getActivity().getApplicationContext();
	    mImageRef = c.getContentResolver().query(
	    		MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // Get the base URI for all of the images
	            dataToAccess, 	// Which columns to return
	            "",         	// Which rows to return (all rows)
	            null,       	// Selection arguments (none)
	            limitSize       // Ordering
	            	);
	    
		// make sure there is something to move to! 
		// 	(otherwise, this will crash if there are no photos) 
	    System.out.println("Count: " + mImageRef.getCount());
		if (mImageRef.getCount() > 0)
			mImageRef.moveToFirst();
		
		
		return mImageRef;
	}
	
	
	public ListView mList;
	Activity mMainActivity;
	Cursor mImageRef;
}
