package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Game extends AppCompatActivity {

    Button back, button_3, button_4, button_5, settings, info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // hide the navigation and the title bar from the phone
        hideNavigationBar();

        back = findViewById(R.id.button34);
        settings = findViewById(R.id.button39);
        info = findViewById(R.id.button38);
        button_3 = findViewById(R.id.button35);

        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Game.this, "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // aici se vor scrie in fisier datele despre jocul curent
                // inainte de a se inchide, cum ar fi: jucatorii, echipele, pozitii, etc

                Intent intent = new Intent(getApplicationContext(), Exit_PopUp.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Settings.class);
                startActivity(intent);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Help.class);
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
