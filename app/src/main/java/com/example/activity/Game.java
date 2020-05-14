package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Game extends Activity {

    Button back, button_3, button_4, button_5, settings, info, test;
    ImageView pion1, pion2, pion3, pion4;
    ArrayList<Integer> resids;
    FrameLayout pion1_xy ,pion2_xy, pion3_xy, pion4_xy;
    ArrayList<Pawn> pawns;
    static ScrollView scrollView;
    static TextView history_view;
    static int pressed_button, index_teams, index_players;
    private long lastClickTime;
    static int order;
    static ArrayList<Integer> colors;
    static SpannableStringBuilder ff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // hide the navigation and the title bar from the phone
        hideNavigationBar();

        //initialize the data
        initialize();

        // put the beginning text in box
        history_view.setText("Se asteapta crearea ordinii echipelor...");

        Handler hr = new Handler();
        hr.postDelayed(new Runnable() {
            @Override
            public void run() {
                create_order();
            }
        }, 2000);

        hr.postDelayed(new Runnable() {
            @Override
            public void run() {
                player_turn();
            }
        }, 2500);

        for(int i=0;i<Board.Groups.size();i++) {
            pawns.get(i).img_pawn.setBackgroundResource(resids.get(i));
            Board.Groups.get(i).pawn = pawns.get(i);
            //Board.Groups.get(i).color = R.drawable.pion_1;
        }

        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();

                pressed_button = 3;
                Intent intent = new Intent(getApplicationContext(), Cart_PopUp.class);
                startActivity(intent);
            }
        });

        button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();

                pressed_button = 4;
                Intent intent = new Intent(getApplicationContext(), Cart_PopUp.class);
                startActivity(intent);
            }
        });

        button_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();

                pressed_button = 5;
                Intent intent = new Intent(getApplicationContext(), Cart_PopUp.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // aici se vor scrie in fisier datele despre jocul curent
                // inainte de a se inchide, cum ar fi: jucatorii, echipele, pozitii, etc
                if(SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(getApplicationContext(), Exit_PopUp.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(getApplicationContext(), Settings.class);
                startActivity(intent);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(getApplicationContext(), Help.class);
                startActivity(intent);
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move_pawn_forward(Board.Groups.get(0).name);
            }
        });
    }

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

                    Cart_PopUp.finish_game = 1;

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
                    }, 4000);
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

    public void create_order() {
        String aux = "Ordinea echipelor este: ";
        ArrayList<Integer> lengths = new ArrayList<>();

        for(int i=0;i<Board.Groups.size();i++) {
            String res = "";

            res += Board.Groups.get(i).name;
            res += "(";

            for(int j=0;j<Board.Groups.get(i).Players.size();j++) {
                res += Board.Groups.get(i).Players.get(j).Name;

                if(j != Board.Groups.get(i).Players.size() - 1) {
                    res += ", ";
                }
            }

            res += ")";

            if(i != Board.Groups.size() - 1) {
                res += ", ";
            }
            else{
                res += "\n\n";
            }

            lengths.add(res.length());
            aux += res;
        }

        SpannableString ss = new SpannableString(aux);

        for(int i=0;i<Board.Groups.size();i++) {
            ForegroundColorSpan color = new ForegroundColorSpan(colors.get(i));

            if(i == 0) {
                ss.setSpan(color, "Ordinea echipelor este: ".length(), "Ordinea echipelor este: ".length() + lengths.get(i) + 1 , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            else {
                int n = 0;
                for(int j=0;j<i;j++) {
                    n += lengths.get(j);
                }
                ss.setSpan(color, "Ordinea echipelor este: ".length() + n,"Ordinea echipelor este: ".length() + n + lengths.get(i), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        ff.append(ss);
        history_view.setText(ff);
    }

    public static void player_over() {

        String aux = Board.Groups.get(index_teams).Players.get(index_players).Name + "(" + Board.Groups.get(index_teams).name + ")" + " si-a terminat randul...\n\n";
        int n = (Board.Groups.get(index_teams).Players.get(index_players).Name + "(" + Board.Groups.get(index_teams).name + ")").length();

        SpannableString ss = new SpannableString(aux);
        ForegroundColorSpan color = new ForegroundColorSpan(colors.get(index_teams));

        ss.setSpan(color, 0, n, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ff.append(ss);
        history_view.setText(ff);

        index_teams++;

        if(index_teams == Board.Groups.size()) {
            index_teams = 0;
            index_players++;

            if(index_players == Board.Groups.get(index_teams).Players.size()) {
                index_players = 0;
            }
        }
    }

    public static void player_turn() {

        String aux = "Este randul lui " + Board.Groups.get(index_teams).Players.get(index_players).Name + "(" + Board.Groups.get(index_teams).name + ")...\n";
        int n = (Board.Groups.get(index_teams).Players.get(index_players).Name + "(" + Board.Groups.get(index_teams).name + ")").length();

        SpannableString ss = new SpannableString(aux);
        ForegroundColorSpan color = new ForegroundColorSpan(colors.get(index_teams));

        ss.setSpan(color, "Este randul lui ".length(), "Este randul lui ".length() + n, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ff.append(ss);
        history_view.setText(ff);
    }

    public void initialize() {
        back = findViewById(R.id.button34);
        settings = findViewById(R.id.button39);
        info = findViewById(R.id.button38);
        button_3 = findViewById(R.id.button35);
        button_4 = findViewById(R.id.button36);
        button_5 = findViewById(R.id.button37);
        pion1 = findViewById(R.id.pion1);
        pion2 = findViewById(R.id.pion2);
        pion3 = findViewById(R.id.pion3);
        pion4 = findViewById(R.id.pion4);
        pion1_xy = findViewById(R.id.pion1_xy);
        pion2_xy = findViewById(R.id.pion2_xy);
        pion3_xy = findViewById(R.id.pion3_xy);
        pion4_xy = findViewById(R.id.pion4_xy);
        history_view = findViewById(R.id.history_view);
        scrollView = findViewById(R.id.scroll_view);
        test = findViewById(R.id.button40);

        resids = new ArrayList<>();
        resids.add(R.drawable.pion_1);
        resids.add(R.drawable.pion_2);
        resids.add(R.drawable.pion_3);
        resids.add(R.drawable.pion_4);

        colors = new ArrayList<>();
        colors.add(Color.parseColor("#AF002A"));
        colors.add(Color.parseColor("#0048BA"));
        colors.add(Color.parseColor("#B3FF66"));
        colors.add(Color.parseColor("#E566FF"));

        pawns = new ArrayList<>();
        pawns.add(new Pawn(pion1_xy, pion1, 63.25f));
        pawns.add(new Pawn(pion2_xy, pion2, 79.25f));
        pawns.add(new Pawn(pion3_xy, pion3, 95.25f));
        pawns.add(new Pawn(pion4_xy, pion4, 111.25f));

        ff = new SpannableStringBuilder();

        index_teams = 0;
        index_players = 0;
        lastClickTime = 0;
        order = 0;

        // shuffle the order teams
        Collections.shuffle(Board.Groups);
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
