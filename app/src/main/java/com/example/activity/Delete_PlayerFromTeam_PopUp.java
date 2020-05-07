package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Delete_PlayerFromTeam_PopUp extends AppCompatActivity {

    EditText editText_team, editText_player;
    Button back;
    DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete__player_from_team__pop_up);

        // hide the navigation and the title bar from the phone
        hideNavigationBar();

        // identify the buttons from xml files
        editText_team = findViewById(R.id.specified_team);
        editText_player = findViewById(R.id.delete_player_team);
        back = findViewById(R.id.button26);

        // initialize and set the DisplayMatrics
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.6), (int)(height*.6));

        // press the button for remove a player from a team
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ok ;

                for(int i=0;i<Board.Groups.size();i++) {
                    ok = -1;
                    if(editText_team.getText().toString().equals(Board.Groups.get(i).name)) {
                        for(int j=0;j<Board.Groups.get(i).Players.size();j++) {
                            if(editText_player.getText().toString().equals(Board.Groups.get(i).Players.get(j).Name)) {
                                Preferences_Team.players_in_timp.add(Board.Groups.get(i).Players.get(j).Name);
                                Board.Groups.get(i).Players.remove(Board.Groups.get(i).Players.get(j));

                                // get the size of the teams
                                ok = Board.Groups.get(i).Players.size();
                                break;
                            }
                        }

                        // didn't find the player
                        if(ok == -1) {
                            Toast.makeText(Delete_PlayerFromTeam_PopUp.this, "Acest jucator nu exista!", Toast.LENGTH_SHORT).show();
                            break;
                        }

                        // the team hasn't any player
                        else if(ok == 0) {
                            Board.Groups.remove(Board.Groups.get(i));
                            break;
                        }
                    }
                    else if(i == Board.Groups.size() - 1) {
                        Toast.makeText(Delete_PlayerFromTeam_PopUp.this, "Aceasta echipa nu exista!", Toast.LENGTH_SHORT).show();
                    }
                }

                Preferences_Team.show_teams.setText(Board.show_group());
                finish();
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
