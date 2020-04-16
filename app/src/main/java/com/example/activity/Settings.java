package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

	Switch sw;
	Button btn, up, down;
	SeekBar seekBar;
	AudioManager audioM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		// hide the navigation and the title bar from the phone
		hideNavigationBar();
		btn = (Button) findViewById(R.id.button4);
		sw = (Switch) findViewById(R.id.switch1);
		up = (Button) findViewById(R.id.button7);
		down = (Button) findViewById(R.id.button6);

		seekBar = (SeekBar) findViewById(R.id.seekBar);
		audioM = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
		seekBar.setMax(audioM.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
		seekBar.setProgress(audioM.getStreamVolume(AudioManager.STREAM_MUSIC));

		sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(!isChecked) {
					MainActivity.mySong.pause();
				}
				else {
					MainActivity.mySong.start();
				}
			}
		});

		up.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				audioM.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
				seekBar.setProgress(audioM.getStreamVolume(AudioManager.STREAM_MUSIC));
				Toast.makeText(Settings.this, "Volume up.", Toast.LENGTH_SHORT).show();
			}
		});

		down.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				audioM.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
				seekBar.setProgress(audioM.getStreamVolume(AudioManager.STREAM_MUSIC));
				Toast.makeText(Settings.this, "Volume down.", Toast.LENGTH_SHORT).show();
			}
		});

		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				audioM.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public void onResume() {
		super.onResume();
		hideNavigationBar();
	}

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
}
