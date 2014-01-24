package me.promenade.pandora;

import java.io.FileNotFoundException;
import java.util.TreeMap;

import me.promenade.pandora.asynjob.ChatSendJob;
import me.promenade.pandora.asynjob.HttpJob;
import me.promenade.pandora.bean.Fantasy;
import me.promenade.pandora.bean.HttpBean;
import me.promenade.pandora.bean.HttpMethod;
import me.promenade.pandora.bean.RunningBean;
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

import org.json.JSONException;
import org.json.JSONObject;

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

	private Fragment f = null;

	public static Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
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
	protected void onCreate(Bundle savedInstanceState) {
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

		switch (fId) {
		case FRAGMENT_CHAT:
			f = new ChatFragment();
			break;
		case FRAGMENT_FANTASY:
			int fantasyIndex = b.getInt("position");
			Fantasy fantasy = (Fantasy) FantasyListFragment.mAdapter
					.getItem(fantasyIndex);
			TreeMap<Integer, Integer> vMap = RunningBean.INSTANCE
					.getFantasyData().get(fantasyIndex);

			f = new FantasyFragment();
			((FantasyFragment) f).setFantasy(fantasyIndex, fantasy, vMap);

			break;
		case FRAGMENT_VIBRATE:
			int vibrateIndex = b.getInt("position");
			Vibration v = VibrateViewListFragment.mVibrationList
					.get(vibrateIndex);

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
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_holder, f).commit();
		;

		// ---add to the back stack---
		// fragmentTransaction.addToBackStack(null);
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Uri uri = data.getData();
			Log.e("uri", uri.toString());
			ContentResolver cr = this.getContentResolver();
			JSONObject j = null;
			Bitmap bitmap = null;
			String imgStr = null;
			try {
				bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
				imgStr = ImageUtil.INSTANCE.compressImageToString(bitmap);
				bitmap.recycle();

				Log.d(TAG, imgStr);

				j = new JSONObject();
				try {
					j.put("photo", imgStr);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				Log.e("Exception", e.getMessage(), e);
			}

			switch (requestCode) {
			case 1:
				if (j != null) {
					HttpBean hb = new HttpBean();
					hb.setMethod(HttpMethod.POST);
					hb.setJson(j);
					hb.setUrl(Constants.UPLOAD_PHOTO_URL + "/"
							+ RunningBean.INSTANCE.getUserId());

					HttpJob job = new HttpJob();
					job.execute(hb);
				}
				if (bitmap != null) {
					ImageView imageView = (ImageView) findViewById(R.id.img_profile_photo);
					// Bitmap bmp = ImageUtil.INSTANCE.String2Bitmap(imgStr);
					imageView.setImageBitmap(bitmap);
					if (ProfileFragment.mProfile != null) {
						ProfileFragment.mProfile.setPhoto(bitmap);
					}
				}
				break;
			case 2:
				ChatSendJob job = new ChatSendJob();
				job.setContext(this);
				job.setType(ChatSendJob.TYPE_PHOTO);
				job.execute(imgStr);
				
//				Message msg = ChatFragment.mHandler
//						.obtainMessage(ChatFragment.MSG_SEND);
//				Bundle b = new Bundle();
//				b.putString("message", imgStr);
//				b.putInt("type", ChatSendJob.TYPE_PHOTO);
//				msg.setData(b);
//				msg.sendToTarget();
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		if (f instanceof FantasyFragment) {
			// Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
			if (f != null) {
				((FantasyFragment) f).stopAll(0);
			}
		}
	}
}
