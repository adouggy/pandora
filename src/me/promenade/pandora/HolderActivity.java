package me.promenade.pandora;

import me.promenade.pandora.bean.Fantasy;
import me.promenade.pandora.bean.Vibration;
import me.promenade.pandora.fragment.ChatFragment;
import me.promenade.pandora.fragment.FantasyFragment;
import me.promenade.pandora.fragment.FantasyListFragment;
import me.promenade.pandora.fragment.LoginFragment;
import me.promenade.pandora.fragment.SignupFragment;
import me.promenade.pandora.fragment.VibrateFragment;
import me.promenade.pandora.fragment.VibrateViewListFragment;
import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class HolderActivity extends SherlockFragmentActivity {

	public static final int FRAGMENT_FANTASY = 1;
	public static final int FRAGMENT_CHAT = 2;
	public static final int FRAGMENT_VIBRATE = 3;
	public static final int FRAGMENT_LOGIN = 4;
	public static final int FRAGMENT_SIGNUP = 5;

	@Override
	protected void onCreate(
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.activity_holder);
//		setupActionBar();
		
		Bundle b = getIntent().getExtras();
		int fId = b.getInt("fragment");

		Fragment f = null;
		switch (fId) {
		case FRAGMENT_CHAT:
			f = new ChatFragment();
			break;
		case FRAGMENT_FANTASY:
			int fantasyIndex = b.getInt("position");
			Fantasy fantasy = FantasyListFragment.mFantasyList.get(fantasyIndex);

			f = new FantasyFragment();
			((FantasyFragment) f).setFantasy(fantasy);
			break;
		case FRAGMENT_VIBRATE:
			int vibrateIndex = b.getInt("position");
			Vibration v = VibrateViewListFragment.mVibrationList.get(vibrateIndex);

			f = new VibrateFragment();
			((VibrateFragment) f).setVibration(v);
			break;
		case FRAGMENT_LOGIN:
			f = new LoginFragment();
			break;
		case FRAGMENT_SIGNUP:
			f = new SignupFragment();
			break;
		}

		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_holder,
				f);

		// ---add to the back stack---
		// fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

}
