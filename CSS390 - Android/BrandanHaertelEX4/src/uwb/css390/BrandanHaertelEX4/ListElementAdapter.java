package uwb.css390.BrandanHaertelEX4;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListElementAdapter extends BaseAdapter {
	
	public ListElementAdapter() {}
	
	public ListElementAdapter(Cursor c, Context con){
		mMainActivity = con;
		mImageRef = c;
	    mFileIDIndex = mImageRef.getColumnIndex(MediaStore.Images.Media._ID);
		mFileIndex = mImageRef.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
		mDateIndex = mImageRef.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);
	}
	
	public static void SetLayoutInflater(Activity a) {
		sInflater = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		//return 5;
		return mImageRef.getCount();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View useView = convertView;
		if (null == convertView) {
			useView = sInflater.inflate(R.layout.list_element, null);
		}
		
		//manage Cursor
		mImageRef.moveToPosition(position);
		System.out.println("Position: " + position);
		
		//set name
		TextView nameEcho = (TextView) useView.findViewById(R.id.listName);
		nameEcho.setText("Name: " + mImageRef.getString(mFileIndex));
		
		//set date
		TextView dateEcho = (TextView) useView.findViewById(R.id.listDate);
		long milliSince1970 = mImageRef.getLong(mDateIndex);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(milliSince1970);
		java.text.DateFormat f = SimpleDateFormat.getDateTimeInstance();
		dateEcho.setText("Date: " + f.format(cal.getTime()));
		
		//set image
		ImageView img = (ImageView) useView.findViewById(R.id.listImage);
		long fileID = mImageRef.getLong(mFileIDIndex);
		Bitmap thumbNail = MediaStore.Images.Thumbnails.getThumbnail(
				mMainActivity.getContentResolver(), 
				fileID, 
				MediaStore.Images.Thumbnails.MINI_KIND, // or MICRO_KIND: too small? 
				null);			
		img.setImageBitmap(thumbNail);
		
		return useView;
	}
	
	
	private static LayoutInflater sInflater = null;
	static Cursor mImageRef;
	static int mFileIDIndex, mDateIndex, mFileIndex;
	static Context mMainActivity;
}
