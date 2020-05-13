package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Choose_names extends AppCompatActivity {

	Button back, add_player, next, remove_player;
	TextView view;
	public static TextView players;
	private long lastClickTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_name);

		lastClickTime = 0;

		// hide the navigation and the title bar from the phone
		hideNavigationBar();

		// identify the buttons from xml files
		view = findViewById(R.id.title_content);
		players = findViewById(R.id.players);
		back = (Button) findViewById(R.id.button3);
		next = findViewById(R.id.button14);
		add_player = (Button) findViewById(R.id.button13);
		remove_player = findViewById(R.id.button15);

		// set the background by the case
		if(Settings.choice == 1){
			view.setBackgroundResource(R.drawable.euro_1);
		} else if(Settings.choice == 2) {
			view.setBackgroundResource(R.drawable.euro_2);
		} else if(Settings.choice == 3) {
			view.setBackgroundResource(R.drawable.euro_3);
		} else {
			view.setBackgroundResource(R.drawable.euro_4);
		}

		// show the list of players at the beginning
		players.setText(Pop_up_names.show_list_of_players(Board.Players));

		// go back by press this button
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				back.setEnabled(false);
				finish();
			}
		});

		// press this button to add a player
		add_player.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(SystemClock.elapsedRealtime() - lastClickTime < 1000) {
					return;
				}

				lastClickTime = SystemClock.elapsedRealtime();
				Intent intent = new Intent(getApplicationContext(), Pop_up_names.class);
				startActivity(intent);
			}
		});

		// pres this button to remove a player
		remove_player.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(SystemClock.elapsedRealtime() - lastClickTime < 1000) {
					return;
				}

				lastClickTime = SystemClock.elapsedRealtime();

				Intent intent = new Intent(getApplicationContext(), Delete_pop_up.class);
				startActivity(intent);
			}
		});

		// go to the next step for the game
		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				next.setEnabled(false);
				if(Board.Players.size() < 5) {
					Toast.makeText(Choose_names.this, "Nu sunt suficienti jucatori!", Toast.LENGTH_SHORT).show();
				}
				else if(Board.Players.size() > 17) {
					Toast.makeText(Choose_names.this, "Prea multi jucatori!", Toast.LENGTH_SHORT).show();
				}
				else {
					Intent intent = new Intent(getApplicationContext(), Choose_your_destiny.class);
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
}
