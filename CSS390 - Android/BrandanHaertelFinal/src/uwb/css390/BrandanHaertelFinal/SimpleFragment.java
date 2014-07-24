package uwb.css390.BrandanHaertelFinal;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class SimpleFragment extends Fragment {

	final int CAMERA_PIC_REQUEST = 0;
	
	DrawArea mDrawArea;
	Button btnPicture;
	Button btnSave;
	Uri imageUri;
	
	public SimpleFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.simple_fragment_layout, container, false);
		mDrawArea = (DrawArea) rootView.findViewById(R.id.drawArea);	
		mDrawArea.setActivity(getActivity());
		btnPicture = (Button) rootView.findViewById(R.id.btnPicture);
		btnSave = (Button) rootView.findViewById(R.id.btnSave);
		btnPicture.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	            startActivityForResult(takePictureIntent, CAMERA_PIC_REQUEST);
	        }
	    }); 
		btnSave.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				mDrawArea.saveBitmap();
			}
		});
		return rootView;
	}
	
	@Override
	//method tells application what too do when the call backs come from outside applications
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    switch(requestCode){
	        case CAMERA_PIC_REQUEST:
	        	if(resultCode==getActivity().RESULT_OK){
	        		System.out.println("got here");
		           Bitmap LoadedImage = (Bitmap) data.getExtras().get("data");
		           System.out.println("Original Bitmap Size: " + LoadedImage.getWidth() + ", " + LoadedImage.getHeight());
		           mDrawArea.setImage(LoadedImage);
		        }
		        break;
	    }
	}
	
	/*
	String mCurrentPhoto;
	private File createImageFile() throws IOException {
	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = "JPEG_" + timeStamp + "_";
	    File storageDir = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES);
	    File image = File.createTempFile(
	        imageFileName,  // prefix
	        ".jpg",         // suffix
	        storageDir      // directory
	    );

	    // Save a file: path for use with ACTION_VIEW intents
	    mCurrentPhotoPath = "file:" + image.getAbsolutePath();
	    return image;
	}
	*/
	
	/*
	// Get the dimensions of the View
    int targetW = 720;
    int targetH = 1080;

    // Get the dimensions of the bitmap
    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
    bmOptions.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
    int photoW = bmOptions.outWidth;
    int photoH = bmOptions.outHeight;

    // Determine how much to scale down the image
    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

    // Decode the image file into a Bitmap sized to fill the View
    bmOptions.inJustDecodeBounds = false;
    bmOptions.inSampleSize = scaleFactor;
    bmOptions.inPurgeable = true;

    Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
    */
}
