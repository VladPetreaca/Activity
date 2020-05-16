package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Preferences_Team extends AppCompatActivity {

    Button back, add_teams, next, remove_teams, remove_player;
    TextView view;
    @SuppressLint("StaticFieldLeak")
    static TextView show_teams;
    static ArrayList<String> players_in_timp = null;
    private long lastClickTime;
    private String FILE_NAME = "save_state.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences_teams);

        lastClickTime = 0;
//        FILE_NAME = "";
//        FILE_NAME += getFilesDir();
//        FILE_NAME += "/" + "save_state.txt";

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
                back.setEnabled(false);
                // show the list of players by going back
                Choose_names.players.setText(Pop_up_names.show_list_of_players(Board.Players));
                finish();
            }
        });

        // press this button to create a team
        add_teams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(getApplicationContext(), CreateTeam_Pop_up.class);
                startActivity(intent);
            }
        });

        // press this button to begin the game
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();

                if(Board.Groups.size() >= 2) {
                    int count = Board.Groups.get(0).Players.size();

                    for(int i=1;i<Board.Groups.size();i++) {
                        if(count != Board.Groups.get(i).Players.size()) {
                            count = -1;
                            break;
                        }
                    }

                    if(count != -1) {
                        FileOutputStream fos = null;

                        try {
                            fos = getApplicationContext().openFileOutput(FILE_NAME, MODE_PRIVATE);
                            fos.write('\n');

                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if(fos != null) {
                                try {
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        MainActivity.save_state = false;
                        Intent intent = new Intent(getApplicationContext(), Game.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(Preferences_Team.this, "Echipele trebuie sa aiba acelasi numar de jucatori " , Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(Preferences_Team.this, "Prea putine echipe. Trebuie sa fie minim 2!" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        remove_teams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(getApplicationContext(), Delete_team_PopUp.class);
                startActivity(intent);
            }
        });

        remove_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();

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
