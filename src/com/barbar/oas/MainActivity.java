package com.barbar.oas;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private GestureDetectorCompat mDetector; 
	private ImageView mImageView;
	private int mAnimalIndex;
	private MediaPlayer mPlayer;

	private static final Animal[] sAnimals = new Animal[] {
		new Animal(R.drawable.bee, R.raw.bee),	
		new Animal(R.drawable.cat, R.raw.cat),
		new Animal(R.drawable.cow, R.raw.cow),
		new Animal(R.drawable.dog, R.raw.dog),
		new Animal(R.drawable.donkey, R.raw.donkey),
		new Animal(R.drawable.duck, R.raw.duck),
		new Animal(R.drawable.goat, R.raw.goat),
		new Animal(R.drawable.goose, R.raw.goose),
		new Animal(R.drawable.horse, R.raw.horse),
		new Animal(R.drawable.mouse, R.raw.mouse),
		new Animal(R.drawable.pig, R.raw.pig),
		new Animal(R.drawable.rooster, R.raw.rooster),
		new Animal(R.drawable.sheep, R.raw.sheep),
		new Animal(R.drawable.sparrow, R.raw.sparrow)
	};
	
	private void UpdateAnimal() {
		MediaPlayer player = mPlayer;
		if (player != null && player.isPlaying()) {
			player.stop();
			mPlayer = null;
		}
		
		int id = sAnimals[mAnimalIndex].getDrawableID();
		
		Options opts = new Options();
		opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), id, opts); 
        
        int width = opts.outWidth;
        int height = opts.outHeight;
        		
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        
        int ratio = 1;
        while (true) {
        	int nextRatio = ratio * 2;
        	if (width * nextRatio > size.x  || height * nextRatio > size.y) {
        		break;
        	}
        	ratio = nextRatio;
        }
        
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = ratio;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        Bitmap bitmap =	BitmapFactory.decodeResource(getResources(), id, opts);
		mImageView.setImageBitmap(bitmap);
	}
	
	private void PlaySound() {
		MediaPlayer player = mPlayer;
		if (player != null && player.isPlaying()) {
			player.stop();
		}
		
		int id = sAnimals[mAnimalIndex].getRawID();
		player = MediaPlayer.create(getApplicationContext(), id);
		player.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				
			}
		});
		player.start();
		mPlayer = player;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	    outState.putInt("AnimalIndex", mAnimalIndex);
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
        mImageView = (ImageView)findViewById(R.id.imageView);
        if (savedInstanceState != null) {
        	mAnimalIndex = savedInstanceState.getInt("AnimalIndex", 0); 
        }
        
        UpdateAnimal();
        
        SharedPreferences preferences = getSharedPreferences("Settings", MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean("IsFirstRun", true);
        if (isFirstRun) {
        	SharedPreferences.Editor edit = preferences.edit();
        	edit.putBoolean("IsFirstRun", false);
        	edit.commit();
        	Intent intent = new Intent(this, TutorialActivity.class);
        	startActivityForResult(intent, 0);
        }
    }

    @Override 
    public boolean onTouchEvent(MotionEvent event){ 
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, 
                float velocityX, float velocityY) {
            Log.d("DEBUG", "onFling: " + Float.toString(velocityX)+","+ Float.toString(velocityY));
            if (velocityX > 100f) {
            	mAnimalIndex = (mAnimalIndex + 1) % sAnimals.length;
            	UpdateAnimal();
            }
            if (velocityX < 100f) {
            	mAnimalIndex = ((mAnimalIndex - 1) + sAnimals.length) % sAnimals.length;
            	UpdateAnimal();
            }
            return true;
        }    	
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
        	PlaySound();
        	return true;
        }
    }
}
