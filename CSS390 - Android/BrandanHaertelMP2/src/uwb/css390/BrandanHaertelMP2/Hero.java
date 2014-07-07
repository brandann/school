package uwb.css390.BrandanHaertelMP2;

import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Hero extends ImageView{

	String mHeroImagePath = "duck.jpg";
	Bitmap mBitmap = null;
	int kDefaultSize;
	MainActivity sMainActivity = null;
	
	public Hero(Context context, MainActivity v) {
		this(context, v, 0, 0);
	}
	
	public Hero(Context context, MainActivity v, float x, float y){
		super(context);
		sMainActivity = v;
		loadBitmap();
		initImageView(context);
		setLocation(x, y);
	}
		
	@SuppressLint("NewApi") public void setLocation(float x, float y){
		this.setX(x - (mBitmap.getWidth() / 2) );
		this.setY(y - (mBitmap.getWidth() / 2) );
	}
	
	@SuppressLint("NewApi") public void moveTo(float x, float y){
		//setLocation(this.getX() + x, this.getY() + y);
		this.setX(this.getX() + x);
		this.setY(this.getY() + y);
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
