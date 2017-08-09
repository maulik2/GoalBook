package com.example.mysql;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Splash extends Activity {
	MediaPlayer ourSound;
	@Override
	protected void onCreate(Bundle parameter) {
		// TODO Auto-generated method stub
		super.onCreate(parameter);
		setContentView(R.layout.splash);
		ourSound = MediaPlayer.create(Splash.this, R.raw.splashsound);
		
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean music = getPrefs.getBoolean("checkbox", true);
		if (music == true)
		ourSound.start();
		
		Thread timer = new Thread (){
			public void run(){
				try{
					sleep(500);
				} catch(InterruptedException e){
					e.printStackTrace();
				} finally {
					Intent openStartingPoint = new Intent("com.example.mysql.MAINACT");
					startActivity(openStartingPoint);
				}
			}
			
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ourSound.release();
		finish();
	}

	
}
