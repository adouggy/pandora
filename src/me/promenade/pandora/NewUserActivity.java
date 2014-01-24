package me.promenade.pandora;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class NewUserActivity extends Activity implements OnClickListener {

	private Button mLogin = null;
	private Button mSignUp = null;
	private Button mGuest = null;
	
	private static NewUserActivity me;
	
	public static final int MSG_WHAT_CLOSE = 1;
	
	public static Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch( msg.what ){
			case MSG_WHAT_CLOSE:
				me.finish();
				
				Intent i = new Intent(me, StubActivity.class);
				me.startActivity( i );
				break;
			}
		};
	};

	@Override
	protected void onCreate(
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_new_user);
		
		me = this;

		mLogin = (Button) findViewById(R.id.btn_newuser_login);
		mSignUp = (Button) findViewById(R.id.btn_newuser_signup);
		mGuest = (Button) findViewById(R.id.btn_newuser_guest);

		mLogin.setOnClickListener(this);
		mSignUp.setOnClickListener(this);
		mGuest.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(
			Menu menu) {
		getMenuInflater().inflate(R.menu.new_user,
				menu);
		return true;
	}

	@Override
	public void onClick(
			View v) {
		switch (v.getId()) {
		case R.id.btn_newuser_login:
			Intent i = new Intent(this, HolderActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Bundle b = new Bundle();
			b.putInt("fragment",
					HolderActivity.FRAGMENT_LOGIN);
			i.putExtras(b);
			startActivity(i);
			
			break;
		case R.id.btn_newuser_signup:
			Intent i2 = new Intent(this, HolderActivity.class);
			Bundle b2 = new Bundle();
			b2.putInt("fragment",
					HolderActivity.FRAGMENT_SIGNUP);
			b2.putString("title",
					"注册");
			i2.putExtras(b2);
			startActivity(i2);
			break;
		case R.id.btn_newuser_guest:
			finish();
			Intent i3 = new Intent(this, StubActivity.class);
			startActivity( i3 );
			break;
		}
	}

}
