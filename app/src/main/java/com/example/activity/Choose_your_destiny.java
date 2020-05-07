package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Choose_your_destiny extends Activity {

    TextView view;
    Button back, random_team, preferences_team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_your_destiny);

        // hide the navigation and the title bar from the phone
        hideNavigationBar();

        // identify the buttons from xml files
        view = findViewById(R.id.title_content_3);
        back = findViewById(R.id.button29);
        random_team = findViewById(R.id.button28);
        preferences_team = findViewById(R.id.button27);

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

        preferences_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Preferences_Team.class);
                startActivity(intent);
            }
        });

        random_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Random_Teams.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
