package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Delete_team_PopUp extends AppCompatActivity {

    EditText editText;
    Button back;
    DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_team__pop_up);

        // hide the navigation and the title bar from the phone
        hideNavigationBar();

        // identify the buttons from xml files
        editText = findViewById(R.id.name_box_team);
        back = findViewById(R.id.button24);

        // initialize and set the DisplayMatrics
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.5), (int)(height*.5));

        // press the button for remove a team
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<Board.Groups.size();i++) {
                    if(editText.getText().toString().equals(Board.Groups.get(i).name)) {
                        for(int j=0;j<Board.Groups.get(i).Players.size();j++) {
                            //Board.Players.add(Board.Groups.get(i).Players.get(j).Name);
                            Board.AddPlayer(Board.Groups.get(i).Players.get(j).Name);
                        }
                        Board.Groups.get(i).Players.clear();
                        Board.Groups.remove(Board.Groups.get(i));
                    }
                    else if(i == Board.Groups.size() - 1) {
                        Toast.makeText(Delete_team_PopUp.this, "Aceasta brigada nu exista!", Toast.LENGTH_SHORT).show();
                    }
                }
                Teams.show_teams.setText(Board.show_group());
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
