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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        hideNavigationBar();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.5), (int)(height*.5));

        editText = (EditText)findViewById(R.id.name_box);

        back = (Button) findViewById(R.id.button12);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_name = editText.getText().toString();
                if(!new_name.equals("")) {
                    if(!check_name(Choose_names.players_name, new_name)) {
                        Choose_names.players_name.add(new_name);
                    }
                    else {
                        Toast.makeText(Pop_up_names.this, "Avem un astfel de jupan!", Toast.LENGTH_SHORT).show();
                    }

                    Choose_names.players.setText(show_list_of_players(Choose_names.players_name));
                }
                finish();
            }
        });
    }

    public static String show_list_of_players (ArrayList<String> players) {

        String result = "Jupanii alesi sunt: ";

        if(players.size() == 0) {
            return "N-avem jupani :(";
        }
        else {
            result += (players.get(0));
            for(int i=1;i<players.size();i++) {
                result += ", ";
                result += players.get(i);
            }
        }

        return result;
    }

    public boolean check_name(ArrayList<String> players, String name) {

        for(int i=0;i<players.size();i++) {
            if(name.equals(players.get(i)))
                return true;
        }

        return false;
    }

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
