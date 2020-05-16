package com.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Pop_up_names extends Activity {

    Button back;
    EditText editText;
    DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        // hide the navigation and the title bar from the phone
        hideNavigationBar();

        // identify the buttons from xml files
        editText = (EditText)findViewById(R.id.name_box);
        back = (Button) findViewById(R.id.button12);

        // initialize and set the DisplayMatrics
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.5), (int)(height*.5));

        //set the favorite name and bo back by press this button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setEnabled(false);

                String new_name = editText.getText().toString();
                if(!new_name.equals("")) {
                    if(!check_name(Board.Players, new_name)) {
                        Board.AddPlayer(new_name);
                    }
                    else {
                        Toast.makeText(Pop_up_names.this, "Avem un astfel de jucator!", Toast.LENGTH_SHORT).show();
                    }

                    Choose_names.players.setText(show_list_of_players(Board.Players));
                }
                finish();
            }
        });
    }

    // show the list_of_players
    public static String show_list_of_players (ArrayList<Player> players) {

        String result = "Jucatorii alesi sunt: ";

        if(players.size() == 1) {
            return "N-avem jucatori :(";
        }
        else {
            result += (players.get(1).Name);
            for(int i=2;i<players.size();i++) {
                result += ", ";
                result += players.get(i).Name;
            }
        }

        return result;
    }

    // check if the introduced name already exists
    public boolean check_name(ArrayList<Player> players, String name) {

        for(int i=0;i<players.size();i++) {
            if(name.equals(players.get(i).Name))
                return true;
        }

        return false;
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
