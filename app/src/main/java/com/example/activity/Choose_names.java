package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Choose_names extends AppCompatActivity {

	Button back, add_player;
	TextView view;
	public static TextView players;
	public static ArrayList<String> players_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_joc);
		hideNavigationBar();

		view = findViewById(R.id.title_content);
		players = findViewById(R.id.players);

		//initialize the players_list
		players_name = new ArrayList<>();

		if(Settings.choice == 1){
			view.setBackgroundResource(R.drawable.euro_1);
		} else if(Settings.choice == 2) {
			view.setBackgroundResource(R.drawable.euro_2);
		} else if(Settings.choice == 3) {
			view.setBackgroundResource(R.drawable.euro_3);
		} else {
			view.setBackgroundResource(R.drawable.euro_4);
		}

		back = (Button) findViewById(R.id.button3);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		add_player = (Button) findViewById(R.id.button13);
		add_player.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), Pop_up_names.class);
				startActivity(intent);
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
