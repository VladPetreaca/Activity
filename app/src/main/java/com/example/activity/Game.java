package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.text.SpannableString;
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
    ArrayList<Integer> team_order;
    static ScrollView scrollView;
    static TextView history_view;
    static int pressed_button, index_teams, index_players;
    static String history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // hide the navigation and the title bar from the phone
        hideNavigationBar();

        //initialize the data
        initialize();

        // put the beginning text in box
        history_view.setText(history);

        // make a string with the order of the teams
        create_order();

        Handler hr = new Handler();
        hr.postDelayed(new Runnable() {
            @Override
            public void run() {
                history_view.setText(history);
            }
        }, 2000);

        for(int i=0;i<Board.Groups.size();i++) {
            pawns.get(i).img_pawn.setBackgroundResource(resids.get(i));
            Board.Groups.get(i).pawn = pawns.get(i);
        }

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                scrollView.scrollTo(0, scrollView.getBottom());
            }
        });

        hr.postDelayed(new Runnable() {
            @Override
            public void run() {
                player_turn();
            }
        }, 2500);

        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressed_button = 3;
                Intent intent = new Intent(getApplicationContext(), Cart_PopUp.class);
                startActivity(intent);
            }
        });

        button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressed_button = 4;
                Intent intent = new Intent(getApplicationContext(), Cart_PopUp.class);
                startActivity(intent);
            }
        });

        button_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    public void create_order() {
        String res = "Ordinea echipelor este: ";
        ArrayList<Integer> lengths = new ArrayList<>();

        for(int i=0;i<Board.Groups.size();i++) {
            int n;

            res += Board.Groups.get(i).name;
            n = Board.Groups.get(i).name.length();

            if(i != Board.Groups.size() - 1) {
                res += ", ";
                n += 2;
            }

            lengths.add(n);
        }

        // https://www.youtube.com/watch?v=tTLmz-JKxsI
        SpannableString ss = new SpannableString(res);

        history = res;
        history += "\n\n";
    }

    public static void player_over() {

        history += Board.Groups.get(index_teams).Players.get(index_players).Name + " si-a terminat randul...\n\n";

        index_teams++;

        if(index_teams == Board.Groups.size()) {
            index_teams = 0;
            index_players++;

            if(index_players == Board.Groups.get(index_teams).Players.size()) {
                index_players = 0;
            }
        }

        history_view.setText(history);
    }

    public static void player_turn() {

        ///scrollView.fullScroll(ScrollView.FOCUS_DOWN);

        history += "Este randul lui " + Board.Groups.get(index_teams).Players.get(index_players).Name + "...\n";

        history_view.setText(history);
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

        pawns = new ArrayList<>();
        pawns.add(new Pawn(pion1_xy, pion1, 63.25f));
        pawns.add(new Pawn(pion2_xy, pion2, 79.25f));
        pawns.add(new Pawn(pion3_xy, pion3, 95.25f));
        pawns.add(new Pawn(pion4_xy, pion4, 111.25f));

        team_order = new ArrayList<>();
        team_order.add(0);
        team_order.add(1);
        team_order.add(2);
        team_order.add(3);

        index_teams = 0;
        index_players = 0;

        for(int i=0;i<Board.Groups.size();i++) {
            Board.Groups.get(i).order = i;
        }

        // shuffle the order teams
        Collections.shuffle(Board.Groups);

        history = "Se asteapta crearea ordinii echipelor...";
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
