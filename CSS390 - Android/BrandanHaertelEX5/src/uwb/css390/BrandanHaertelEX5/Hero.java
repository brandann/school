package uwb.css390.BrandanHaertelEX5;

import uwb.css390.GraphicsSupport.BitmapShape;
import android.content.Context;
import android.graphics.Rect;

public class Hero extends BitmapShape{
	
	int mCount = 0;
	Rect mWorldBound;
	static final int kInsideWorldBound = 0x0;
	static final int kOutsideWorldBound = 0x10;
	static final int kCollideLeft = 0x1;
	static final int kCollideRight = 0x2;
	static final int kCollideTop = 0x4;
	static final int kCollideBottom = 0x8;
	
	public Hero(Context context, int drawableResource, int atX, int atY, int w, int h) {
		super(context, drawableResource, atX, atY, w, h);
	}
	
	public Hero(Context context, String fileInAssets, int atX, int atY, int w, int h) {
		super(context, fileInAssets, atX, atY, w, h);
	}
	
	public void setWorld(int w, int h){mWorldBound = new Rect(0,0,w,h);}
	public int getCount(){return mCount;}
	public void setCount(int i){mCount = i;}
	public int setDeltaCount(int i){return (mCount += i);}
	
	float mult = 2;
	
	// World Boundry
	// -------------------------------------------------------------------------------------------------
	public void updateMode() {
		updateShapeWithVelocity();
		int status = collideWithWorld(getBound());
		if((status & kCollideBottom) == kCollideBottom){
			updateVelocity(0, -getVelocityY() * mult);
			mCount++;
			//System.out.println("kCollideBottom");
		}
		if((status & kCollideTop) == kCollideTop){
			updateVelocity(0, -getVelocityY() * mult);
			mCount++;
			//System.out.println("kCollideTop");
		}
		if((status & kCollideLeft) == kCollideLeft){
			updateVelocity(-getVelocityX() * mult, 0);
			mCount++;
			//System.out.println("kCollideLeft");
		}
		if((status & kCollideRight) == kCollideRight){
			updateVelocity(-getVelocityX() * mult, 0);
			mCount++;
			//System.out.println("kCollideRightside");
		}
		if((status & kOutsideWorldBound) == kOutsideWorldBound){
			updateVelocity(-getVelocityX() * mult, -getVelocityY() * mult);
			mCount++;
			//System.out.println("kOutsideWorldBound");
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
