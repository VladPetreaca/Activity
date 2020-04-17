package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.SharedPreferences;
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

	SwitchCompat sw;
	Button btn, up, down, back_50, back_100, back_200, back_500;
	SeekBar seekBar;
	AudioManager audioM;
	public static int choice = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		// hide the navigation and the title bar from the phone
		hideNavigationBar();
		btn = (Button) findViewById(R.id.button4);
		sw = (SwitchCompat) findViewById(R.id.switch1);
		up = (Button) findViewById(R.id.button7);
		down = (Button) findViewById(R.id.button6);
		back_50 = (Button) findViewById(R.id.button8);
		back_100 = (Button) findViewById(R.id.button9);
		back_200 = (Button) findViewById(R.id.button10);
		back_500 = (Button) findViewById(R.id.button11);

		seekBar = (SeekBar) findViewById(R.id.seekBar);
		audioM = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
		seekBar.setMax(audioM.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
		seekBar.setProgress(audioM.getStreamVolume(AudioManager.STREAM_MUSIC));


		SharedPreferences shar = getSharedPreferences("save", MODE_PRIVATE);
		sw.setChecked(shar.getBoolean("value", true));

		sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(!isChecked) {
					MainActivity.mySong.pause();
					SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
					editor.putBoolean("value", false);
					editor.apply();
					sw.setChecked(false);
				}
				else {
					SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
					editor.putBoolean("value", true);
					editor.apply();
					sw.setChecked(true);
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

		back_50.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(Settings.this, "Changed 50.", Toast.LENGTH_SHORT).show();
				choice = 1;
			}
		});

		back_100.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(Settings.this, "Changed 100.", Toast.LENGTH_SHORT).show();
				choice = 2;
			}
		});

		back_200.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(Settings.this, "Changed 200.", Toast.LENGTH_SHORT).show();
				choice = 3;
			}
		});

		back_500.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(Settings.this, "Changed 500.", Toast.LENGTH_SHORT).show();
				choice = 4;
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
