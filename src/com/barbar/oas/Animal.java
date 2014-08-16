package com.barbar.oas;

public class Animal {
	private int mDrawableID;
	private int mRawID;
	
	public Animal(int drawableID, int rawID) {
		mDrawableID = drawableID;
		mRawID = rawID;
	}
	
	public int getDrawableID() {
		return mDrawableID;
	}
	
	public int getRawID() {
		return mRawID;
	}
}
