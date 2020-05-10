package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Game extends Activity {

    Button back, button_3, button_4, button_5, settings, info, test;
    ImageView pion1, pion2, pion3, pion4;
    ArrayList<Integer> resids;
    FrameLayout pion1_xy ,pion2_xy, pion3_xy, pion4_xy;
    ArrayList<Pawn> pawns;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // hide the navigation and the title bar from the phone
        hideNavigationBar();

        //initialize the data
        initialize();

        for(int i=0;i<Board.Groups.size();i++) {
            pawns.get(i).img_pawn.setBackgroundResource(resids.get(i));
            Board.Groups.get(i).pawn = pawns.get(i);
        }

        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Game.this, "Clicked!", Toast.LENGTH_SHORT).show();
                move_pawn(Board.Groups.get(1).name);
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

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move_pawn(Board.Groups.get(0).name);
            }
        });
    }

    // move a specified pawn on the table
    public void move_pawn(String team_name) {

        for(int i=0;i<Board.Groups.size();i++) {

            // find the team who does the action
            if(Board.Groups.get(i).name.equals(team_name)) {

                // it's the begin of the game
                if(!Board.Groups.get(i).pawn.start) {
                    Board.Groups.get(i).pawn.x_min = 0;
                    Board.Groups.get(i).pawn.x_max = -Board.Groups.get(i).pawn.over_start;

                    TranslateAnimation anim = new TranslateAnimation(
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.x_min , getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.x_max , getResources().getDisplayMetrics()),
                            Animation.ABSOLUTE,
                            Animation.ABSOLUTE);

                    anim.setDuration(2000);
                    anim.setFillAfter(true);

                    Board.Groups.get(i).pawn.pawn_xy.startAnimation(anim);
                    Board.Groups.get(i).pawn.start = true;
                }

                //end of the game
                else if(Board.Groups.get(i).pawn.count_lines == 11 && Board.Groups.get(i).pawn.count_columns == 3) {
                    Board.Groups.get(i).pawn.x_min = Board.Groups.get(i).pawn.x_max;
                    Board.Groups.get(i).pawn.x_max += 94.5f;

                    TranslateAnimation anim = new TranslateAnimation(
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.x_min , getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.x_max , getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.y_max , getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.y_max , getResources().getDisplayMetrics()));

                    anim.setDuration(2000);
                    anim.setFillAfter(true);

                    Board.Groups.get(i).pawn.pawn_xy.startAnimation(anim);

                    Handler hand = new Handler();
                    hand.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Game.this, "Jocul s-a terminat!", Toast.LENGTH_SHORT).show();
                        }
                    }, 2800);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Board.Groups.clear();
                            Board.Players.clear();

                            MainActivity.stop_song = true;

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }, 3800);
                }

                // even lines
                else if(Board.Groups.get(i).pawn.count_columns < 3 && Board.Groups.get(i).pawn.count_lines % 2 == 0){
                    Board.Groups.get(i).pawn.x_min = Board.Groups.get(i).pawn.x_max;
                    Board.Groups.get(i).pawn.x_max -= 94.5f;

                    TranslateAnimation anim = new TranslateAnimation(
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.x_min , getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.x_max , getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.y_max , getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.y_max , getResources().getDisplayMetrics()));

                    anim.setDuration(2000);
                    anim.setFillAfter(true);

                    Board.Groups.get(i).pawn.pawn_xy.startAnimation(anim);
                    Board.Groups.get(i).pawn.count_columns++;

                }

                // go to the next line
                else if(Board.Groups.get(i).pawn.count_columns == 3) {
                    Board.Groups.get(i).pawn.count_lines++;
                    Board.Groups.get(i).pawn.count_columns = 0;

                    Board.Groups.get(i).pawn.y_min = Board.Groups.get(i).pawn.y_max;
                    Board.Groups.get(i).pawn.y_max = Board.Groups.get(i).pawn.count_lines * 31f;

                    TranslateAnimation anim = new TranslateAnimation(
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.x_max , getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.x_max , getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.y_min , getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.y_max , getResources().getDisplayMetrics()));

                    anim.setDuration(2000);
                    anim.setFillAfter(true);

                    Board.Groups.get(i).pawn.pawn_xy.startAnimation(anim);
                }

                // odd lines
                else if(Board.Groups.get(i).pawn.count_columns < 3 && Board.Groups.get(i).pawn.count_lines % 2 == 1) {
                    Board.Groups.get(i).pawn.x_min = Board.Groups.get(i).pawn.x_max;
                    Board.Groups.get(i).pawn.x_max += 94.5f;

                    TranslateAnimation anim = new TranslateAnimation(
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.x_min , getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.x_max , getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.y_max , getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.y_max , getResources().getDisplayMetrics()));

                    anim.setDuration(2000);
                    anim.setFillAfter(true);

                    Board.Groups.get(i).pawn.pawn_xy.startAnimation(anim);
                    Board.Groups.get(i).pawn.count_columns++;
                }

                break;
            }
        }
    }

    public void initialize() {
        back = findViewById(R.id.button34);
        settings = findViewById(R.id.button39);
        info = findViewById(R.id.button38);
        button_3 = findViewById(R.id.button35);
        pion1 = findViewById(R.id.pion1);
        pion2 = findViewById(R.id.pion2);
        pion3 = findViewById(R.id.pion3);
        pion4 = findViewById(R.id.pion4);
        test = findViewById(R.id.button40);
        pion1_xy = findViewById(R.id.pion1_xy);
        pion2_xy = findViewById(R.id.pion2_xy);
        pion3_xy = findViewById(R.id.pion3_xy);
        pion4_xy = findViewById(R.id.pion4_xy);

        resids = new ArrayList<>();
        resids.add(R.drawable.pion_1);
        resids.add(R.drawable.pion_2);
        resids.add(R.drawable.pion_3);
        resids.add(R.drawable.pion_4);

        pawns = new ArrayList<>();
        pawns.add(new Pawn(pion1_xy, pion1, 63.25f));
        pawns.add(new Pawn(pion2_xy, pion2, 79.25f));
        pawns.add(new Pawn(pion3_xy, pion3, 95.25f));
        pawns.add(new Pawn(pion4_xy, pion4, 111.25f));
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
