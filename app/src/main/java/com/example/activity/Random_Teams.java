package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Random_Teams extends AppCompatActivity {

    Button next, back, change_name, create_teams;
    TextView view;
    EditText editText;
    static TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random__teams);

        // hide the navigation and the title bar from the phone
        hideNavigationBar();

        // identify the buttons from xml files;
        back = findViewById(R.id.button30);
        next = findViewById(R.id.button31);
        change_name = findViewById(R.id.button19);
        textView = findViewById(R.id.show_random_teams);
        view = findViewById(R.id.title_content_2);
        create_teams = findViewById(R.id.button32);
        editText = findViewById(R.id.nr_groups);

        // show the list of players from the beginning
        textView.setText(Board.show_group());

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Random_Teams.this, "Start the game (To be continued...)", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Random_Teams.this, Game.class);
                startActivity(intent);
            }
        });

        change_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Change_Team_Name.class);
                startActivity(intent);
            }
        });

        create_teams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((Board.Players.size() - 1) % Integer.parseInt(editText.getText().toString()) == 0) {
                    if(Board.CreateRandomGroups(Integer.parseInt(editText.getText().toString()))) {
                        textView.setText(Board.show_group());
                    }
                    else {
                        Toast.makeText(Random_Teams.this, "Eroare ca esti prost!!!!" , Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Random_Teams.this, "Nu sunt jucatori" , Toast.LENGTH_SHORT).show();
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
