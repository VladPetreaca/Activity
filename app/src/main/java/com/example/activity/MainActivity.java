package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	Button joc_nou, exit, info_btn, setting_btn;
	public static Button continue_game;
	public static MediaPlayer mySong;
	public static boolean stop_song = false;
	public static boolean save_state = false;
	private long lastClickTime;
	private String FILE_NAME;
	public static int change_board_bk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// hide the navigation and the title bar from the phone
		hideNavigationBar();

		lastClickTime = 0;
		FILE_NAME = "";
		FILE_NAME += getFilesDir();
		FILE_NAME += "/" + "save_state.txt";

		change_board_bk = 0;

		// identify the buttons from xml files
		setting_btn = (Button) findViewById(R.id.button2);
		info_btn = (Button) findViewById(R.id.imageButton2);
		joc_nou = (Button) findViewById(R.id.button);
		exit = findViewById(R.id.button8);
		continue_game = findViewById(R.id.button5);

		// play music
		if(!stop_song) {
			mySong = MediaPlayer.create(MainActivity.this, R.raw.vremea);
			mySong.setLooping(true);
			mySong.start();
		}

		//initialize the players_list
		Board.AddPlayer("Alege jucator");

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(FILE_NAME);

			if(fis.available() > 2) {
				save_state = true;
			}
			else {
				save_state = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		//if setting_button is clicked
		setting_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(SystemClock.elapsedRealtime() - lastClickTime < 1000) {
					return;
				}

				lastClickTime = SystemClock.elapsedRealtime();

				openSettings();
			}
		});

		// if the info_button is clicked
		info_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(SystemClock.elapsedRealtime() - lastClickTime < 1000) {
					return;
				}

				lastClickTime = SystemClock.elapsedRealtime();

				openHelp();
			}
		});

		// if the joc_nou button is clicked
		joc_nou.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(SystemClock.elapsedRealtime() - lastClickTime < 1000) {
					return;
				}

				lastClickTime = SystemClock.elapsedRealtime();

				openJocNou();
			}
		});

		//exit from the application
		exit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				exit.setEnabled(false);
				finish();
				System.exit(0);
			}
		});

		if(save_state) {
			//Toast.makeText(MainActivity.this, "Click pe button!", Toast.LENGTH_SHORT).show();
			continue_game.setBackgroundColor(Color.parseColor("#ffff8800"));
		}
		else {
			continue_game.setBackgroundColor(Color.parseColor("#8B0000"));
		}

		// continue the last game
		continue_game.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(SystemClock.elapsedRealtime() - lastClickTime < 1000) {
					return;
				}

				lastClickTime = SystemClock.elapsedRealtime();
				change_board_bk = 1;

				if(save_state) {
					Intent intent = new Intent(getApplicationContext(), Game.class);
					startActivity(intent);
				}
			}
		});
	}

	// hide bar function after re-enter in it
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

	// start settings page
	public void openSettings() {
		Intent intent = new Intent(this, Settings.class);
		startActivity(intent);
	}

	// start help page
	public void openHelp() {
		Intent intent = new Intent(this, Help.class);
		startActivity(intent);
	}

	// start joc_nou page
	public void openJocNou() {
		Intent intent = new Intent(this, Choose_names.class);
		startActivity(intent);
	}
}
