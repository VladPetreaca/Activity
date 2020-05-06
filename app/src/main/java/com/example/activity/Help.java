package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

public class Help extends AppCompatActivity {

	Button btn;
	TextView MessageWindow;
	StringBuilder strBuilder;
	String text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);

		// hide the navigation and the title bar from the phone
		hideNavigationBar();

		// initialize the variables
		strBuilder = new StringBuilder();
		text = "";

		//// identify the buttons from xml files
		btn = (Button) findViewById(R.id.imageButton2);
		MessageWindow = (TextView) findViewById(R.id.messageWindow);

		// try to read the rules from "rules.txt"
		try {
			InputStream in = getAssets().open("rules.txt");
			int size = in.available();
			byte[] buffer = new byte[size];
			in.read(buffer);
			in.close();
			text = new String(buffer);
			strBuilder.append(text);

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// set the text in specified TextView
		MessageWindow.setText(strBuilder.toString());

		// go back by press this button
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
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
