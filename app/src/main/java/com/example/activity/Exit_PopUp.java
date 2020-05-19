package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

public class Exit_PopUp extends AppCompatActivity {

    public static final String FILE_NAME = "save_state.txt";
    DisplayMetrics dm;
    Button yes, no, remain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit__pop_up);

        // hide the navigation and the title bar from the phone
        hideNavigationBar();

        // identify the buttons from xml files
        yes = findViewById(R.id.button_yes);
        no = findViewById(R.id.button_no);
        remain = findViewById(R.id.button43);

        // initialize and set the DisplayMatrics
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.6), (int)(height*.6));

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no.setEnabled(false);
                Board.Groups.clear();
                Board.Players.clear();

                MainActivity.stop_song = true;
                MainActivity.change_board_bk = 0;

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yes.setEnabled(false);
                //aici se vor salva datele jocului in fisier
                write_to_file();

                Board.Groups.clear();
                Board.Players.clear();

                MainActivity.stop_song = true;
                MainActivity.save_state = true;
                MainActivity.change_board_bk = 0;

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        remain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remain.setEnabled(false);
                finish();
            }
        });
    }

    public void write_to_file() {

        FileOutputStream fos = null;

        try {
            fos = getApplicationContext().openFileOutput(FILE_NAME, MODE_PRIVATE);

            fos.write(String.valueOf(Board.Groups.size()).getBytes());
            fos.write('\n');

            for(int i=0;i<Board.Groups.size();i++) {

                // write the name of the team
                fos.write(Board.Groups.get(i).name.getBytes());
                fos.write('\n');

                // write the players of the team
                for(int j=0;j<Board.Groups.get(i).Players.size();j++) {
                    fos.write(Board.Groups.get(i).Players.get(j).Name.getBytes());

                    if(j == Board.Groups.get(i).Players.size() - 1) {
                        fos.write('\n');
                    }
                    else {
                        fos.write(',');
                    }
                }

                //write nr_columns
                fos.write(String.valueOf(Board.Groups.get(i).pawn.count_columns).getBytes());;
                fos.write('\n');

                // write line_numbers
                fos.write(String.valueOf(Board.Groups.get(i).pawn.count_lines).getBytes());;
                fos.write('\n');

                // write nr_box
                fos.write(String.valueOf(Board.Groups.get(i).pawn.nr_box).getBytes());;
                fos.write('\n');

                // write order
                fos.write(String.valueOf(Board.Groups.get(i).pawn.order).getBytes());;
                fos.write('\n');

                // write over_start
                fos.write(String.valueOf(Board.Groups.get(i).pawn.over_start).getBytes());;
                fos.write('\n');

                // write x_min
                fos.write(String.valueOf(Board.Groups.get(i).pawn.x_min).getBytes());;
                fos.write('\n');

                // write x_max
                fos.write(String.valueOf(Board.Groups.get(i).pawn.x_max).getBytes());;
                fos.write('\n');

                // write y_min
                fos.write(String.valueOf(Board.Groups.get(i).pawn.y_min).getBytes());;
                fos.write('\n');

                // write y_max
                fos.write(String.valueOf(Board.Groups.get(i).pawn.y_max).getBytes());;
                fos.write('\n');

                if(Board.Groups.get(i).pawn.start) {
                    fos.write(String.valueOf(1).getBytes());;
                    fos.write('\n');
                }
                else {
                    fos.write(String.valueOf(0).getBytes());;
                    fos.write('\n');
                }
                // order of writing: team_name, players, nr_columns, line_numbers, nr_box, order, over_start, x_min, x_max, y_min, y_max, start
            }

            for(int i=0;i<Board.Used_Cards.size();i++) {
                fos.write(String.valueOf(Board.Used_Cards.get(i).id).getBytes());

                if(i == Board.Used_Cards.size() - 1) {
                    fos.write('\n');
                }
                else {
                    fos.write(',');
                }
            }

            fos.write(String.valueOf(Game.index_teams).getBytes());
            fos.write('\n');

            fos.write(String.valueOf(Game.index_players).getBytes());
            fos.write('\n');

            fos.write(String.valueOf(Game.order).getBytes());
            fos.write('\n');

            MainActivity.save_state = true;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
