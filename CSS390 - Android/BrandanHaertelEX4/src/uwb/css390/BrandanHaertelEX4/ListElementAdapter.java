package uwb.css390.BrandanHaertelEX4;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListElementAdapter extends BaseAdapter {
	
	public ListElementAdapter() {}
	
	public static void SetLayoutInflater(Activity a) {
		sInflater = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	// bitmap loader
	static private Bitmap loadBitmap(AssetManager loader, String file)
	{
		Bitmap result = null;
		InputStream bitmapStream=null;
		try {
			//open the file from the assets folder with the given name
			bitmapStream = loader.open(file);
			//decode the stream as a bitmap
			result = BitmapFactory.decodeStream(bitmapStream);
			//set up an inputStream	  
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			if(bitmapStream!=null) {
				try {
					//close the inputstream if it was loaded successfully
					bitmapStream.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static void initialzeData(AssetManager loader) {
		mAllPictures = getCameraImages();
		kPretendData1 = loadBitmap(loader, "duck.jpg");
		kPretendData2 = loadBitmap(loader, "kid.png");
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View useView = convertView;
		if (null == convertView) 
			useView = sInflater.inflate(R.layout.list_element, null);
		TextView nameEcho = (TextView) useView.findViewById(R.id.listName);
		TextView dateEcho = (TextView) useView.findViewById(R.id.listDate);
		ImageView img = (ImageView) useView.findViewById(R.id.listImage);
		nameEcho.setText("Name: " + position + ".name");
		dateEcho.setText("Date: " + position + "." + position*2 + "." + position*3);
		img.setImageBitmap(kPretendData1);
		return useView;
	}
	
	public static String getBucketId(String path) {
	    return String.valueOf(path.toLowerCase().hashCode());
	}
	
	private static List<String> getCameraImages(Context context) {
	    final String[] projection = { MediaStore.Images.Media.DATA };
	    final String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
	    final String[] selectionArgs = { CAMERA_IMAGE_BUCKET_ID };
	    final Cursor cursor = context.getContentResolver().query(Images.Media.EXTERNAL_CONTENT_URI, 
	            projection, 
	            selection, 
	            selectionArgs, 
	            null);
	    ArrayList<String> result = new ArrayList<String>(cursor.getCount());
	    if (cursor.moveToFirst()) {
	        final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        do {
	            final String data = cursor.getString(dataColumn);
	            result.add(data);
	        } while (cursor.moveToNext());
	    }
	    cursor.close();
	    return result;
	}
	
	private static LayoutInflater sInflater = null;
	private static Bitmap kPretendData1 = null;
	private static Bitmap kPretendData2 = null;
	public static final String CAMERA_IMAGE_BUCKET_NAME = Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera";
	public static final String CAMERA_IMAGE_BUCKET_ID =	getBucketId(CAMERA_IMAGE_BUCKET_NAME);
	private static ArrayList<String> mAllPictures;

	
}
