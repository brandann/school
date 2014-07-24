package uwb.css390.GraphicsSupport;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

public class Hero extends BitmapShape {
	Rect mWorldBound;
	static final int kInsideWorldBound = 0x0;
	static final int kOutsideWorldBound = 0x10;
	static final int kCollideLeft = 0x1;
	static final int kCollideRight = 0x2;
	static final int kCollideTop = 0x4;
	static final int kCollideBottom = 0x8;
	static final float mult = 2;
	
	public Hero(Context context, int drawableResource, int atX, int atY, int w, int h) {
		super(context, drawableResource, atX, atY, w, h);
	}
	
	public Hero(Context context, String fileInAssets, int atX, int atY, int w, int h) {
		super(context, fileInAssets, atX, atY, w, h);
	}
	
	public Hero(Context context, Bitmap b, int atX, int atY, int w, int h) {
		super(context, b, atX, atY, w, h);
	}
	
	public void setWorld(int w, int h){
		mWorldBound = new Rect(0,0,w,h);
	}
	
	// World Boundry
	// -------------------------------------------------------------------------------------------------
	public void updateMode() {
		updateShapeWithVelocity();
		int status = collideWithWorld(getBound());
		if((status & kCollideBottom) == kCollideBottom){
			updateVelocity(0, -getVelocityY() * mult);
		}
		if((status & kCollideTop) == kCollideTop){
			updateVelocity(0, -getVelocityY() * mult);
		}
		if((status & kCollideLeft) == kCollideLeft){
			updateVelocity(-getVelocityX() * mult, 0);
		}
		if((status & kCollideRight) == kCollideRight){
			updateVelocity(-getVelocityX() * mult, 0);
		}
		if((status & kOutsideWorldBound) == kOutsideWorldBound){
			updateVelocity(-getVelocityX() * mult, -getVelocityY() * mult);
		}
	}
	
	private int collideWithWorld(Rect r) {
		if (mWorldBound.contains(r))
			return kInsideWorldBound;
		Rect intersect = new Rect();
		int returnStatus = kOutsideWorldBound; 
		if (intersect.setIntersect(mWorldBound, r)) { // returns the intersection of worldBound and r
			returnStatus = kInsideWorldBound;
			if (intersect.left == 0) 
				returnStatus |= kCollideLeft; 
			if (intersect.right == mWorldBound.right) 
				returnStatus |= kCollideRight;
			if (intersect.top == mWorldBound.top) 
				returnStatus |= kCollideTop;
			if (intersect.bottom == mWorldBound.bottom) 
				returnStatus |= kCollideBottom;
		}	
		return returnStatus; 
	}
}
