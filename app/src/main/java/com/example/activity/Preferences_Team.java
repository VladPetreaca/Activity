package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Preferences_Team extends AppCompatActivity {

    Button back, add_teams, next, remove_teams, remove_player;
    TextView view;
    @SuppressLint("StaticFieldLeak")
    static TextView show_teams;
    static ArrayList<String> players_in_timp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences_teams);

        // hide the navigation and the title bar from the phone
        hideNavigationBar();

        if(players_in_timp == null) {
            players_in_timp = Board.getPlayers();
        }

        // identify the buttons from xml files
        view = findViewById(R.id.title_content_2);
        add_teams = findViewById(R.id.button18);
        show_teams = findViewById(R.id.show_teams);
        back = findViewById(R.id.button17);
        next = findViewById(R.id.button22);
        remove_teams = findViewById(R.id.button23);
        remove_player = findViewById(R.id.button25);

        // show the list of players from the beginning
        show_teams.setText(Board.show_group());

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

        //do an action and go back by press this button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show the list of players by going back
                Choose_names.players.setText(Pop_up_names.show_list_of_players(Board.Players));
                finish();
            }
        });

        // press this button to create a team
        add_teams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateTeam_Pop_up.class);
                startActivity(intent);
            }
        });

        // press this button to begin the game
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Preferences_Team.this, "Start the game (To be continued...)", Toast.LENGTH_SHORT).show();
            }
        });

        remove_teams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Delete_team_PopUp.class);
                startActivity(intent);
            }
        });

        remove_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Delete_PlayerFromTeam_PopUp.class);
                startActivity(intent);
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
