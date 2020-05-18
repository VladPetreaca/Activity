package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

public class Random_Teams extends AppCompatActivity {

    Button next, back, change_name, create_teams;
    TextView view;
    EditText editText;
    static TextView textView;
    private long lastClickTime;
    private String FILE_NAME = "save_state.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random__teams);

        lastClickTime = 0;
//        FILE_NAME = "";
//        FILE_NAME += getFilesDir();
//        FILE_NAME += "/" + "save_state.txt";

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
            view.setBackgroundResource(R.drawable.back_g1);
        } else if(Settings.choice == 2) {
            view.setBackgroundResource(R.drawable.back_g5);
        } else if(Settings.choice == 3) {
            view.setBackgroundResource(R.drawable.back_g3);
        } else {
            view.setBackgroundResource(R.drawable.back_g7);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setEnabled(false);
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();

                if(Board.Groups.size() >= 2) {
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
                    MainActivity.change_board_bk = 1;
                    Intent intent = new Intent(Random_Teams.this, Game.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Random_Teams.this, "Prea putine echipe. Trebuie sa fie minim 2!" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        change_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();

                if(Board.Groups.size() >= 2) {
                    Intent intent = new Intent(getApplicationContext(), Change_Team_Name.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Random_Teams.this, "Prea putine echipe. Trebuie sa fie minim 2!" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        create_teams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();

                if(!editText.getText().toString().equals("") && Integer.parseInt(editText.getText().toString()) >= 2) {
                    if((Board.Players.size() - 1) % Integer.parseInt(editText.getText().toString()) == 0) {
                        if(Board.CreateRandomGroups(Integer.parseInt(editText.getText().toString()))) {
                            textView.setText(Board.show_group());
                        }
                        else {
                            Toast.makeText(Random_Teams.this, "Trebuie sa fie minim 2 jucatori intr-o echipa" , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(Random_Teams.this, "Numarul de jucatori trebuie sa fie multiplu de " +  editText.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Random_Teams.this, "Prea putine echipe. Trebuie sa fie minim 2!" , Toast.LENGTH_SHORT).show();
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
