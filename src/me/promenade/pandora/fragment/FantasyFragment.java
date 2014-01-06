package me.promenade.pandora.fragment;

import java.util.concurrent.Executors;

import me.promenade.pandora.R;
import me.promenade.pandora.asynjob.PlayMusicJob;
import me.promenade.pandora.asynjob.VibrateAllJob;
import me.promenade.pandora.asynjob.VibrateJob;
import me.promenade.pandora.bean.Fantasy;
import me.promenade.pandora.bean.RunningBean;
import me.promenade.pandora.util.MusicUtil;
import me.promenade.pandora.view.PlayButton;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class FantasyFragment extends SherlockFragment implements OnClickListener, OnSeekBarChangeListener, OnGlobalLayoutListener {
	public static final String TAG = "FantasyFragment";
	
	private static SeekBar mPb = null;
	private static PlayButton mBtn = null;
	private static TextView mTime = null;
	private static ImageView mImage = null;
	private static TextView mCurrentVibration = null;
	private static Button[] mButtonList = new Button[12];

	private Fantasy mFantasy = null;

	public static final int WHAT_POSITION_REFRESH = 1;
	public static final int WHAT_DURATION_REFRESH = 2;
	public static final int WHAT_FINISH = 3;
	public static final int WHAT_START_V = 4;
	public static final int WHAT_STOP_V = 5;

	private PlayMusicJob mPlayJob = null;
	private VibrateAllJob mVibrateALlJob = null;
	private VibrateJob mVibrateJob = null;

	private static int currentProgress = 0;
	
	public static boolean isPrepared = false;

	public void setFantasy(
			Fantasy f) {
		this.mFantasy = f;
	}

	public static Handler mHandler = new Handler() {
		@Override
		public void handleMessage(
				Message msg) {
			switch (msg.what) {
			case WHAT_POSITION_REFRESH:
				Bundle b = msg.getData();
				int p = b.getInt("progress");
				currentProgress = p;
				Log.i(TAG,
						p + "");
				mPb.setProgress(p);

				break;
			case WHAT_DURATION_REFRESH:
				Bundle bundle = msg.getData();
				int duration = bundle.getInt("duration");
				String time = bundle.getString("time");
				mTime.setText(time);
				mPb.setMax(duration);
				break;
			case WHAT_FINISH:
				currentProgress = 0;
				mPb.setProgress(0);
				MusicUtil.INSTANCE.stop();
				mBtn.setPlaying(false);
				break;
			case WHAT_START_V:
				mCurrentVibration.setVisibility(View.VISIBLE);
				String currentV = msg.getData().getString("currentV");
				Log.i(TAG,
						currentV);
				mCurrentVibration.setText(currentV);
				break;
			case WHAT_STOP_V:
				mCurrentVibration.setVisibility(View.INVISIBLE);
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_fantasy,
				container,
				false);

		mPb = (SeekBar) view.findViewById(R.id.pb_fantasy);
		mBtn = (PlayButton) view.findViewById(R.id.btn_play_fantasy);
		mTime = (TextView) view.findViewById(R.id.txt_fantasy_time);
		mImage = (ImageView) view.findViewById(R.id.img_fantasy);
		mCurrentVibration = (TextView) view.findViewById(R.id.txt_current_v);
		mCurrentVibration.setVisibility(View.INVISIBLE);

		mButtonList[0] = (Button) view.findViewById(R.id.btn_0);
		mButtonList[1] = (Button) view.findViewById(R.id.btn_1);
		mButtonList[2] = (Button) view.findViewById(R.id.btn_2);
		mButtonList[3] = (Button) view.findViewById(R.id.btn_3);
		mButtonList[4] = (Button) view.findViewById(R.id.btn_4);
		mButtonList[5] = (Button) view.findViewById(R.id.btn_5);
		mButtonList[6] = (Button) view.findViewById(R.id.btn_6);
		mButtonList[7] = (Button) view.findViewById(R.id.btn_7);
		mButtonList[8] = (Button) view.findViewById(R.id.btn_8);
		mButtonList[9] = (Button) view.findViewById(R.id.btn_9);
		mButtonList[10] = (Button) view.findViewById(R.id.btn_10);
		mButtonList[11] = (Button) view.findViewById(R.id.btn_11);

		for (int i = 0; i < mButtonList.length; i++) {
			mButtonList[i].setOnClickListener(this);

			mButtonList[i].getViewTreeObserver().addOnGlobalLayoutListener(this);
		}

		if (mFantasy != null) {
			mImage.setImageResource(mFantasy.getImageId());
		}

		mBtn.setOnClickListener(this);

		mPb.setOnSeekBarChangeListener(this);
		return view;
	}

	private void stopAll() {
		mBtn.setPlaying(false);

		if (mPlayJob != null) {
			mPlayJob.cancel(true);
		}
		if (mVibrateALlJob != null) {
			mVibrateALlJob.cancel(true);
		}
		if (mVibrateJob != null) {
			mVibrateJob.cancel(true);
		}

		MusicUtil.INSTANCE.stop();
	}

	@Override
	public void onStart() {
		currentProgress = 0;
		isPrepared = true;
		super.onStart();
	}

	@Override
	public void onStop() {
		stopAll();
		isPrepared = false;
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopAll();
	}

	@Override
	public void onClick(
			View v) {
		switch (v.getId()) {
		case R.id.btn_play_fantasy:
			if (mBtn.isPlaying()) {
				Log.i(TAG,
						"stop..");
				stopAll();
			} else {
				Log.i(TAG,
						"start..");
				mBtn.setPlaying(true);

				mVibrateALlJob = new VibrateAllJob();
				mPlayJob = new PlayMusicJob();

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
					mVibrateALlJob.execute(0);
					mPlayJob.execute(mFantasy.getMusicId(),
							currentProgress);
				} else {
					mVibrateALlJob.executeOnExecutor(Executors.newCachedThreadPool(),
							0);// .execute(0);
					mPlayJob.executeOnExecutor(Executors.newCachedThreadPool(),
							mFantasy.getMusicId(),
							currentProgress);// .execute(mFantasy.getMusicId(),currentProgress);
				}
			}
			break;

		case R.id.btn_0:
			if (mVibrateALlJob != null)
				mVibrateALlJob.cancel(true);
			if (mVibrateJob != null)
				mVibrateJob.cancel(true);
			break;
		case R.id.btn_1:
		case R.id.btn_2:
		case R.id.btn_3:
		case R.id.btn_4:
		case R.id.btn_5:
		case R.id.btn_6:
		case R.id.btn_7:
		case R.id.btn_8:
		case R.id.btn_9:
		case R.id.btn_10:
		case R.id.btn_11:
			int index = Integer.parseInt(((TextView) v).getText().toString());
			if (mVibrateALlJob != null)
				mVibrateALlJob.cancel(true);
			mCurrentVibration.setVisibility(View.VISIBLE);
			mCurrentVibration.setText(RunningBean.INSTANCE.getVibration().get(index - 1).getTitle());
			mVibrateJob = new VibrateJob();
			mVibrateJob.execute(index - 1, VibrateJob.WITH_UI);
			break;
		}
	}

	@Override
	public void onProgressChanged(
			SeekBar seekBar,
			int progress,
			boolean fromUser) {
	}

	@Override
	public void onStartTrackingTouch(
			SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(
			SeekBar seekBar) {

	}

	@Override
	public void onGlobalLayout() {
		int width = mButtonList[0].getWidth();
		LayoutParams lp = mButtonList[0].getLayoutParams();
		lp.height = width;
		for (Button b : mButtonList) {
			b.setLayoutParams(lp);
		}
	}

}
