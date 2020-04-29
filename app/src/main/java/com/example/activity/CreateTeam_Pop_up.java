package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class CreateTeam_Pop_up extends AppCompatActivity {

    EditText editText;
    Spinner dropdown;
    ArrayList<String> players_in_timp;
    Button add, done;
    static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team__pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.8));
        players_in_timp = new ArrayList<>();

        dropdown = findViewById(R.id.spinner);
        add = findViewById(R.id.button20);
        done = findViewById(R.id.button21);

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Choose_names.players_name);
        dropdown.setAdapter(adapter);

        editText = (EditText)findViewById(R.id.team_name);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {
               name = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                players_in_timp.add(name);
                Choose_names.players_name.remove(name);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Board.Groups.add(new Group(editText.getText().toString(), players_in_timp));
                finish();
            }
        });
    }
}
