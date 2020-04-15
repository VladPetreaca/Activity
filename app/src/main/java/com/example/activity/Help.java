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

	Handler mHand;
	ImageButton btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);

		hideNavigationBar();

		//TextView TitleWindow = (TextView) findViewById(R.id.titleWindow);
		mHand = new Handler();
		mHand.postDelayed(mRun, 2300);

		btn = (ImageButton) findViewById(R.id.imageButton2);

		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private Runnable mRun = new Runnable() {
		@Override
		public void run() {
			TextView MessageWindow = (TextView) findViewById(R.id.messageWindow);
			StringBuilder strBuilder = new StringBuilder();
			String text = "";

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

			MessageWindow.setText(strBuilder.toString());
		}
	};

	public void onResume() {
		super.onResume();

		hideNavigationBar();
	}

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
