package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.activity.R;

public class MainActivity extends AppCompatActivity {

	ImageButton setting_btn, info_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

		setting_btn = (ImageButton) findViewById(R.id.button2);
		info_btn = (ImageButton) findViewById(R.id.imageButton2);

		setting_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Setting Button Clicked.", Toast.LENGTH_SHORT).show();
			}
		});

		info_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "The Rules.", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
