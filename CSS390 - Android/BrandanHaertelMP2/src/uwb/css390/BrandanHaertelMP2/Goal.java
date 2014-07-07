package uwb.css390.BrandanHaertelMP2;

import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Goal extends ImageView{

	String mHeroImagePath = "duck.jpg";
	Bitmap mBitmap = null;
	int kDefaultSize;
	MainActivity sMainActivity = null;
	
	public Goal(Context context, MainActivity v){
		super(context);
		sMainActivity = v;
		loadBitmap();
		initImageView(context);
	}
	
	public void setLocation(Point p){
		setX( (p.x/2) - (kDefaultSize/2) );
		setY( p.y - (float) (kDefaultSize*1.5));
		Log.d("MyDebug", "Point Width: " + p.x + "**************");
		Log.d("MyDebug", "Point Height: " + p.y + "**************");
		//setX(100);
		//setY(100);
	}
	private void initImageView(Context context)	{
		if (isInEditMode())
			return;
		
		// default behavior only if no image is defined yet
		setImageBitmap(mBitmap);

		// now set the size
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(kDefaultSize, kDefaultSize);
		setLayoutParams(layoutParams);
	}
	
	// Region loading the bitmap
	private void loadBitmap()
	{
		InputStream bitmapStream=null;
		try {
			//open the file from the assets folder with the given name
			bitmapStream = sMainActivity.getAssets().open(mHeroImagePath);
			// getAssets: is the utility for accessing the Asset folder!

			//decode the stream as a bitmap
			mBitmap=BitmapFactory.decodeStream(bitmapStream);
			kDefaultSize = mBitmap.getHeight();
			
			//set up an inputStream	  
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(bitmapStream!=null)
				try {
					//close the inputstream if it was loaded successfully
					bitmapStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	// EndRegion

}
