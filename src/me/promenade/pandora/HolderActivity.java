package me.promenade.pandora;

import java.io.FileNotFoundException;

import org.json.JSONException;
import org.json.JSONObject;

import me.promenade.pandora.asynjob.HttpJob;
import me.promenade.pandora.bean.Fantasy;
import me.promenade.pandora.bean.HttpBean;
import me.promenade.pandora.bean.HttpMethod;
import me.promenade.pandora.bean.Vibration;
import me.promenade.pandora.fragment.ChatFragment;
import me.promenade.pandora.fragment.FantasyFragment;
import me.promenade.pandora.fragment.FantasyListFragment;
import me.promenade.pandora.fragment.LoginFragment;
import me.promenade.pandora.fragment.ProfileFragment;
import me.promenade.pandora.fragment.SignupFragment;
import me.promenade.pandora.fragment.VibrateFragment;
import me.promenade.pandora.fragment.VibrateViewListFragment;
import me.promenade.pandora.util.Constants;
import me.promenade.pandora.util.ImageUtil;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class HolderActivity extends SherlockFragmentActivity {
	public static final String TAG = "HolderActivity";
	
	public static final int FRAGMENT_FANTASY = 1;
	public static final int FRAGMENT_CHAT = 2;
	public static final int FRAGMENT_VIBRATE = 3;
	public static final int FRAGMENT_LOGIN = 4;
	public static final int FRAGMENT_SIGNUP = 5;
	public static final int FRAGMENT_PROFILE = 6;

	public static final int WHAT_FINISH = 1;

	private static HolderActivity me;

	public static Handler mHandler = new Handler() {
		public void handleMessage(
				android.os.Message msg) {
			switch (msg.what) {
			case WHAT_FINISH:
				me.finish();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		me = this;
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().hide();
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.activity_holder);
		// setupActionBar();

		Bundle b = getIntent().getExtras();
		int fId = b.getInt("fragment");

		Fragment f = null;
		switch (fId) {
		case FRAGMENT_CHAT:
			f = new ChatFragment();
			break;
		case FRAGMENT_FANTASY:
			int fantasyIndex = b.getInt("position");
			Fantasy fantasy = (Fantasy)FantasyListFragment.mAdapter.getItem(fantasyIndex);

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
		case FRAGMENT_PROFILE:
			f = new ProfileFragment();
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

	@Override
	protected void onActivityResult(
			int requestCode,
			int resultCode,
			Intent data) {
		if (resultCode == RESULT_OK) {
			Uri uri = data.getData();
			Log.e("uri",
					uri.toString());
			ContentResolver cr = this.getContentResolver();
			try {
				Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

				String imgStr = ImageUtil.INSTANCE.compressImageToString(bitmap);
				bitmap.recycle();

				Log.i(TAG, imgStr);
				
				JSONObject j = new JSONObject();
				try {
					j.put("photo", imgStr);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				HttpBean hb = new HttpBean();
				hb.setMethod(HttpMethod.POST);
				hb.setJson(j);
				hb.setUrl(Constants.UPLOAD_PHOTO_URL+"/"+1);
				
				HttpJob job = new HttpJob();
				job.execute(hb);
				
				ImageView imageView = (ImageView) findViewById(R.id.img_profile_photo);
				Bitmap bmp = ImageUtil.INSTANCE.String2Bitmap( imgStr );
				imageView.setImageBitmap( bmp );
				if( ProfileFragment.mProfile != null){
					ProfileFragment.mProfile.setPhoto(bmp);
				}
				
			} catch (FileNotFoundException e) {
				Log.e("Exception",
						e.getMessage(),
						e);
			}
		}
		super.onActivityResult(requestCode,
				resultCode,
				data);
	}

}
