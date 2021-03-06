package com.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateTeam_Pop_up extends Activity {

    EditText editText;
    Spinner dropdown;
    Button add, done;
    DisplayMetrics dm;
    static String name;
    static ArrayAdapter<String> adapter;
    ArrayList<String> aux_list;
    boolean check;
    private long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team__pop_up);

        // hide the navigation and the title bar from the phone
        hideNavigationBar();

        // initialize the variables
        aux_list = new ArrayList<>();
        check = false;
        lastClickTime = 0;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Preferences_Team.players_in_timp);

        // identify the buttons from xml files
        dropdown = findViewById(R.id.spinner);
        add = findViewById(R.id.button20);
        done = findViewById(R.id.button21);
        editText = (EditText)findViewById(R.id.team_name);

        // initialize and set the DisplayMatrics
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.5), (int)(height*.5));

        // set the list of players to the drop_down bar
        dropdown.setAdapter(adapter);

        // select a name and do an action with it
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {
                name = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // add a player_name in a auxiliary list
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();

                if(!editText.getText().toString().equals("")) {
                    if(!name.equals("Alege jucator")) {
                        aux_list.add(name);
                        adapter.remove(name);
                        dropdown.setAdapter(adapter);
                    }
                }
                else {
                    Toast.makeText(CreateTeam_Pop_up.this, "Ai uitat sa scrii numele!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // the team is done and create it by press this button
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done.setEnabled(false);

                if(!editText.getText().toString().equals("")) {
                    if(!aux_list.isEmpty()) {
                        for(int i=0;i<Board.Groups.size();i++) {
                            if(editText.getText().toString().equals(Board.Groups.get(i).name)) {
                                check = true;
                                break;
                            }
                        }

                        if(!check) {
                            Board.Groups.add(new Group(editText.getText().toString(),aux_list));
                            Preferences_Team.show_teams.setText(Board.show_group());
                            finish();
                        }
                        else {
                            Toast.makeText(CreateTeam_Pop_up.this, "Aceasta echipa deja exista!", Toast.LENGTH_SHORT).show();
                            check = false;
                        }
                    }
                }
                else {
                    Toast.makeText(CreateTeam_Pop_up.this, "Ai uitat sa scrii numele!" , Toast.LENGTH_SHORT).show();
                }

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
