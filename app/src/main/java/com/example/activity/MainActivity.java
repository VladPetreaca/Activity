package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.activity.R;

public class MainActivity extends AppCompatActivity {

	ImageButton setting_btn, info_btn;
	Button asd;
	public static MediaPlayer mySong;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

		// hide the navigation and the title bar from the phone
		hideNavigationBar();

		// take the buttons
		setting_btn = (ImageButton) findViewById(R.id.button2);
		info_btn = (ImageButton) findViewById(R.id.imageButton2);

		//play music
		mySong = MediaPlayer.create(MainActivity.this, R.raw.vremea);
		mySong.setLooping(true);
		mySong.start();

		//if setting_button is clicked
		setting_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openSettings();
			}
		});

		// if the info_button is clicked
		info_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openHelp();
			}
		});
	}

	public void onResume() {
		super.onResume();
		hideNavigationBar();
	}

	//hide bar function
	public void hideNavigationBar() {
		this.getWindow().getDecorView()
				.setSystemUiVisibility(
						View.SYSTEM_UI_FLAG_FULLSCREEN |
								View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
								View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
								View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
								View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
								View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				);
	}

	public void openSettings() {
		Intent intent = new Intent(this, Settings.class);
		startActivity(intent);
	}

	//start help page
	public void openHelp() {
		Intent intent = new Intent(this, Help.class);
		startActivity(intent);
	}
}
