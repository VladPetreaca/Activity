package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Cart_PopUp extends Activity {

    Button back, start;
    TextView textView;
    Chronometer chrom;
    boolean running;
    long pauseOffset;
    int mode;
    static int finish_game;
    static int moves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart__pop_up);

        // hide the navigation and the title bar from the phone
        hideNavigationBar();

        back = findViewById(R.id.button41);
        textView = findViewById(R.id.cart_view);
        start = findViewById(R.id.button42);
        chrom = findViewById(R.id.chronometer);
        mode = 0;
        finish_game = 0;
        moves = 0;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setEnabled(false);
                if(mode == 1) {

                    Handler hand = new Handler();
                    hand.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(Board.Groups.get(Game.index_teams).pawn.nr_box + Game.pressed_button > 48) {
                                Game.pressed_button = 49 - Board.Groups.get(Game.index_teams).pawn.nr_box;
                            }

                            for(int i=0;i<=Game.pressed_button + 1;i++) {

                                if(i <= Game.pressed_button - 1) {
                                    Handler h = new Handler();
                                    h.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            move_pawn_forward(Board.Groups.get(Game.index_teams).name);
                                        }
                                    }, i * 900);
                                }
                               else if(i == Game.pressed_button) {
                                    moves = 0;

                                    Handler h = new Handler();
                                    h.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            for(int c=0; c < 3; c++) {
                                                Handler h = new Handler();
                                                h.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        moves = 0;

                                                        for(int j=0;j<Board.Groups.size();j++) {
                                                            for(int k=0;k<Board.Groups.size();k++) {
                                                                if(Board.Groups.get(j).pawn.nr_box == Board.Groups.get(k).pawn.nr_box && j != k && Board.Groups.get(j).pawn.nr_box != 0 && Board.Groups.get(k).pawn.nr_box != 0) {
                                                                    moves++;
                                                                    if(Board.Groups.get(j).pawn.order < Board.Groups.get(k).pawn.order) {
                                                                        go_back_numbers(j);
                                                                    }
                                                                    else {
                                                                        go_back_numbers(k);
                                                                    }
                                                                    break;
                                                                }
                                                            }

                                                            if(moves == 1) {
                                                                break;
                                                            }
                                                        }
                                                    }
                                                },c * 900);
                                            }
                                        }
                                    }, i * 900);
                                }
                               else if(i == Game.pressed_button + 1){

                                    Handler h = new Handler();
                                    h.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            if(finish_game == 1) {
                                                SpannableString ss = new SpannableString("\n\nJocul s-a terminat! Felicitari echipei " + Board.Groups.get(Game.index_teams).name + "!\n");
                                                ForegroundColorSpan color = new ForegroundColorSpan(Game.colors.get(Game.index_teams));
                                                ss.setSpan(color, 0, ("\n\nJocul s-a terminat! Felicitari echipei " + Board.Groups.get(Game.index_teams).name + "!\n").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                                Game.ff.append(ss);
                                                Game.history_view.setText(Game.ff);
                                            }
                                            else {
                                                Game.player_over();
                                                Game.player_turn();
                                            }
                                        }
                                    }, i * 1000 + 900);

                                    Handler hand = new Handler();
                                    hand.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Game.scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                            Game.scrollView.scrollTo(0, Game.scrollView.getBottom());
                                        }
                                    },i * 1000 + 1000 + 1000);

                                }

                            }
                        }
                    }, 850);
                }
                else {
                    Game.player_over();
                    Game.player_turn();

                    Game.scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    Game.scrollView.scrollTo(0, Game.scrollView.getBottom());
                }
                finish();
            }
        });

        Handler hand = new Handler();
        hand.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setBackgroundResource(R.drawable.carte_de_joc);
            }
        }, 3000);

        chrom.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 5000) {
                    chronometer.stop();
                    mode = 2;
                    start.setEnabled(false);
                    start.setBackgroundColor(Color.parseColor("#333333"));
                }
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!running) {
                    chrom.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chrom.start();
                    running = true;
                    start.setBackgroundResource(R.drawable.stop_button_2);
                }
                else if(running) {
                    chrom.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chrom.getBase();
                    mode = 1;
                }
            }
        });
    }

    // move forward a specified pawn on the table
    public void move_pawn_forward(String team_name) {

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

                    anim.setDuration(1000);
                    anim.setFillAfter(true);

                    Game.order++;
                    Board.Groups.get(i).pawn.order = Game.order;
                    Board.Groups.get(i).pawn.pawn_xy.startAnimation(anim);
                    Board.Groups.get(i).pawn.start = true;
                    Board.Groups.get(i).pawn.nr_box = (4 * Board.Groups.get(i).pawn.count_lines) + (Board.Groups.get(i).pawn.count_columns + 1);
                }

                //end of the game
                else if(Board.Groups.get(i).pawn.count_lines == 11 && Board.Groups.get(i).pawn.count_columns >= 3) {
                    Board.Groups.get(i).pawn.x_min = Board.Groups.get(i).pawn.x_max;
                    Board.Groups.get(i).pawn.x_max += 94.5f;

                    TranslateAnimation anim = new TranslateAnimation(
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.x_min , getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.x_max , getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.y_max , getResources().getDisplayMetrics()),
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.y_max , getResources().getDisplayMetrics()));

                    anim.setDuration(1000);
                    anim.setFillAfter(true);

                    Board.Groups.get(i).pawn.pawn_xy.startAnimation(anim);

                    finish_game = 1;

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
                    }, 6500);
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

                    anim.setDuration(1000);
                    anim.setFillAfter(true);

                    Game.order++;
                    Board.Groups.get(i).pawn.order = Game.order;
                    Board.Groups.get(i).pawn.pawn_xy.startAnimation(anim);
                    Board.Groups.get(i).pawn.count_columns++;
                    Board.Groups.get(i).pawn.nr_box = 4 * Board.Groups.get(i).pawn.count_lines + (Board.Groups.get(i).pawn.count_columns + 1);
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

                    anim.setDuration(1000);
                    anim.setFillAfter(true);

                    Game.order++;
                    Board.Groups.get(i).pawn.order = Game.order;
                    Board.Groups.get(i).pawn.pawn_xy.startAnimation(anim);
                    Board.Groups.get(i).pawn.nr_box = 4 * Board.Groups.get(i).pawn.count_lines + (Board.Groups.get(i).pawn.count_columns + 1);
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

                    anim.setDuration(1000);
                    anim.setFillAfter(true);

                    Game.order++;
                    Board.Groups.get(i).pawn.order = Game.order;
                    Board.Groups.get(i).pawn.pawn_xy.startAnimation(anim);
                    Board.Groups.get(i).pawn.count_columns++;
                    Board.Groups.get(i).pawn.nr_box = 4 * Board.Groups.get(i).pawn.count_lines + (Board.Groups.get(i).pawn.count_columns + 1);
                }

                break;
            }
        }
    }

    public void go_back_numbers(int i) {
        if(Board.Groups.get(i).pawn.count_columns == 0) {
            Board.Groups.get(i).pawn.y_min = Board.Groups.get(i).pawn.y_max;
            Board.Groups.get(i).pawn.y_max -= 31f;

            TranslateAnimation anim = new TranslateAnimation(
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.x_max , getResources().getDisplayMetrics()),
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.x_max , getResources().getDisplayMetrics()),
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.y_min , getResources().getDisplayMetrics()),
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.y_max , getResources().getDisplayMetrics()));

            anim.setDuration(800);
            anim.setFillAfter(true);

            Board.Groups.get(i).pawn.pawn_xy.startAnimation(anim);
            Board.Groups.get(i).pawn.count_columns = 3;
            Board.Groups.get(i).pawn.count_lines--;
            Board.Groups.get(i).pawn.nr_box = 4 * Board.Groups.get(i).pawn.count_lines + (Board.Groups.get(i).pawn.count_columns + 1);
        }

        // the pawn remaining on the same line (odd line)
        else if(Board.Groups.get(i).pawn.count_columns <= 3 && Board.Groups.get(i).pawn.count_lines % 2 == 1) {
            Board.Groups.get(i).pawn.x_min = Board.Groups.get(i).pawn.x_max;
            Board.Groups.get(i).pawn.x_max -= 94.5f;

            TranslateAnimation anim = new TranslateAnimation(
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.x_min , getResources().getDisplayMetrics()),
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.x_max , getResources().getDisplayMetrics()),
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.y_max , getResources().getDisplayMetrics()),
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.y_max , getResources().getDisplayMetrics()));

            anim.setDuration(800);
            anim.setFillAfter(true);

            Board.Groups.get(i).pawn.pawn_xy.startAnimation(anim);
            Board.Groups.get(i).pawn.count_columns--;
            Board.Groups.get(i).pawn.nr_box = 4 * Board.Groups.get(i).pawn.count_lines + (Board.Groups.get(i).pawn.count_columns + 1);
        }

        // the pawn remaining on the same line (even line)
        else if(Board.Groups.get(i).pawn.count_columns <= 3 && Board.Groups.get(i).pawn.count_lines % 2 == 0) {
            Board.Groups.get(i).pawn.x_min = Board.Groups.get(i).pawn.x_max;
            Board.Groups.get(i).pawn.x_max += 94.5f;

            TranslateAnimation anim = new TranslateAnimation(
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.x_min , getResources().getDisplayMetrics()),
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.x_max , getResources().getDisplayMetrics()),
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.y_max , getResources().getDisplayMetrics()),
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Board.Groups.get(i).pawn.y_max , getResources().getDisplayMetrics()));

            anim.setDuration(800);
            anim.setFillAfter(true);

            Board.Groups.get(i).pawn.pawn_xy.startAnimation(anim);
            Board.Groups.get(i).pawn.count_columns--;
            Board.Groups.get(i).pawn.nr_box = 4 * Board.Groups.get(i).pawn.count_lines + (Board.Groups.get(i).pawn.count_columns + 1);
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
