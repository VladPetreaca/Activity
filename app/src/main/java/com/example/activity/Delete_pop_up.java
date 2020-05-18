package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Delete_pop_up extends Activity {

    Button back;
    EditText editText;
    DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        // hide the navigation and the title bar from the phone
        hideNavigationBar();

        // identify the buttons from xml files
        back = findViewById(R.id.button16);
        editText = (EditText)findViewById(R.id.delete_name);

        // initialize and set the DisplayMatrics
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.5), (int)(height*.5));

        // remove a name from the players_list
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setEnabled(false);

                for(int i=0;i<Board.Players.size();i++) {
                    if(editText.getText().toString().equals(Board.Players.get(i).Name)) {
                        Board.Players.remove(i);
                        break;
                    }
                    else if(i == Board.Players.size() - 1) {
                        Toast.makeText(Delete_pop_up.this, "Nu exista acest jucator!", Toast.LENGTH_SHORT).show();
                    }
                }
                Choose_names.players.setText(Pop_up_names.show_list_of_players(Board.Players));
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
