package me.promenade.pandora;

import me.promenade.pandora.bean.RunningBean;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {

	private static final long SPLASH_DELAY_MILLIS = 2000;

	@Override
	protected void onCreate(
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_splash);

		new Handler().postDelayed(new Runnable() {
			public void run() {
				goHome();
			}
		},
				SPLASH_DELAY_MILLIS);
	}

	private void goHome() {
		
		if (RunningBean.INSTANCE.getUserId() == -1) {
			Intent i = new Intent(this, NewUserActivity.class);
			startActivity(i);
		} else {
			Intent intent = new Intent(SplashActivity.this, StubActivity.class);
			SplashActivity.this.startActivity(intent);
		}
			
		SplashActivity.this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(
			Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash,
				menu);
		return true;
	}

}
