package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	ImageButton setting_btn, info_btn;
	Button joc_nou;
	public static MediaPlayer mySong;
	public static ArrayList<String> players_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// hide the navigation and the title bar from the phone
		hideNavigationBar();

		// identify the buttons from xml files
		setting_btn = (ImageButton) findViewById(R.id.button2);
		info_btn = (ImageButton) findViewById(R.id.imageButton2);
		joc_nou = (Button) findViewById(R.id.button);

		// play music
//		mySong = MediaPlayer.create(MainActivity.this, R.raw.vremea);
//		mySong.setLooping(true);
//		mySong.start();

		//initialize the players_list
//		players_name = new ArrayList<>();
//		players_name.add("Alege jucator");
//		players_name.add("vlad");
//		players_name.add("petreaca");
//		players_name.add("negru");
//		players_name.add("Ser");
		Board.AddPlayer("Alege jucator");

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

		// if the joc_nou button is clicked
		joc_nou.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openJocNou();
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
