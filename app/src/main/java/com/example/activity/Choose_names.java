package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Choose_names extends AppCompatActivity {

	Button back, add_player, next, remove_player;
	TextView view;
	public static TextView players;
	public static ArrayList<String> players_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_name);
		hideNavigationBar();

		view = findViewById(R.id.title_content);
		players = findViewById(R.id.players);
		back = (Button) findViewById(R.id.button3);
		next = findViewById(R.id.button14);

		//initialize the players_list
		players_name = new ArrayList<>();
		players_name.add("Alege jucator");
		players_name.add("vlad");
		players_name.add("petreaca");
		players_name.add("negru");
		players_name.add("Ser");

		if(Settings.choice == 1){
			view.setBackgroundResource(R.drawable.euro_1);
		} else if(Settings.choice == 2) {
			view.setBackgroundResource(R.drawable.euro_2);
		} else if(Settings.choice == 3) {
			view.setBackgroundResource(R.drawable.euro_3);
		} else {
			view.setBackgroundResource(R.drawable.euro_4);
		}

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

		remove_player = findViewById(R.id.button15);
		remove_player.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), Delete_pop_up.class);
				startActivity(intent);
			}
		});

		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(players_name.size() < 4) {
					Toast.makeText(Choose_names.this, "Nu sunt suficienti jupani!", Toast.LENGTH_SHORT).show();
				}
				else {
					//Toast.makeText(Choose_names.this, "Acum urmeaza sa facem brigazile!", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getApplicationContext(), Teams.class);
					startActivity(intent);
				}
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
