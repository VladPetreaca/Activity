package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Joc extends AppCompatActivity {

	View view;
	Button back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_joc);

		hideNavigationBar();

		view = this.getWindow().getDecorView();
		if(Settings.choice == 1){
			view.setBackgroundResource(R.drawable.euro_1);
		} else if(Settings.choice == 2) {
			view.setBackgroundResource(R.drawable.euro_2);
		} else if(Settings.choice == 3) {
			view.setBackgroundResource(R.drawable.euro_3);
		} else {
			view.setBackgroundResource(R.drawable.euro_4);
		}

		back = (Button) findViewById(R.id.button3);

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

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
