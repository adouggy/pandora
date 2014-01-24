package me.promenade.pandora.fragment;

import java.util.ArrayList;
import java.util.TreeMap;

import me.promenade.pandora.R;
import me.promenade.pandora.asynjob.VibrateJob;
import me.promenade.pandora.bean.Fantasy;
import me.promenade.pandora.bean.RunningBean;
import me.promenade.pandora.util.MusicUtil;
import me.promenade.pandora.view.PlayButton;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
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

@SuppressLint("HandlerLeak")
public class FantasyFragment extends SherlockFragment implements OnClickListener, OnSeekBarChangeListener, OnGlobalLayoutListener {
	public static final String TAG = "FantasyFragment";

	private SeekBar mSeekbar = null;
	private PlayButton mBtn = null;
	private TextView mTime = null;
	private ImageView mImage = null;

	private Button[] mButtonList = new Button[12];

	private Fantasy mFantasy = null;
	private TreeMap<Integer, Integer> mVibrateData = null;

	private VibrateJob mVibrateJob = null;

	private Timer mTimer = null;
	private boolean running = true;
	private int mFantasyIndex;
	private int currentProgress = 0;
	private TextView mCurrentVibration = null;
	private int currentVibrateIndex = 0;

	private class Timer extends Thread {
		@Override
		public void run() {
			long time = 0;
			while (running) {
				try {
					time = System.currentTimeMillis();
					mHandler.obtainMessage(MSG_WHAT_UPDATE_TIME).sendToTarget();
					Integer vibrateIndex = mVibrateData.get(currentProgress);
					if (vibrateIndex != null) {
						mVibrateJob = new VibrateJob();
						mVibrateJob.setHandler(mHandler);
						mVibrateJob.execute(vibrateIndex);
						currentVibrateIndex = vibrateIndex;
						mHandler.obtainMessage(MSG_WHAT_SHOW_VIBRATE).sendToTarget();
					}
					time = System.currentTimeMillis() - time;
					if (time < 1000)
						Thread.sleep(1000 - time);
					currentProgress++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static final int MSG_WHAT_UPDATE_TIME = 1;
	public static final int MSG_WHAT_SHOW_VIBRATE = 2;
	public static final int MSG_WHAT_CLEAR_SHOW_VIBRATE = 3;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(
				android.os.Message msg) {
			switch (msg.what) {
			case MSG_WHAT_UPDATE_TIME:
				mTime.setText("" + currentProgress);
				mSeekbar.setProgress(currentProgress);
				break;
			case MSG_WHAT_SHOW_VIBRATE:
				mCurrentVibration.setVisibility(View.VISIBLE);
				mCurrentVibration.setText(RunningBean.INSTANCE.getVibration().get(currentVibrateIndex).getTitle());
				break;
			case MSG_WHAT_CLEAR_SHOW_VIBRATE:
				mCurrentVibration.setVisibility(View.INVISIBLE);
				mCurrentVibration.setText("");
				break;
			}
		};
	};

	public void setFantasy(
			int index,
			Fantasy f,
			TreeMap<Integer, Integer> vMap) {
		this.mFantasyIndex = index;
		this.mFantasy = f;
		this.mVibrateData = vMap;
	}

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_fantasy,
				container,
				false);

		mSeekbar = (SeekBar) view.findViewById(R.id.pb_fantasy);
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
		mSeekbar.setOnSeekBarChangeListener(this);

		return view;
	}

	public void stopAll(int position) {
		mBtn.setPlaying(false);
		currentProgress = position;

		if (mVibrateJob != null) {
			mVibrateJob.cancel(true);
			mVibrateJob = null;
		}

		running = false;
		mTimer = null;
		MusicUtil.INSTANCE.stop();
	}

	private void startAll(
			int position) {
		running = true;
		mBtn.setPlaying(true);
		currentProgress = position;

		mTimer = new Timer();
		mTimer.start();
		MusicUtil.INSTANCE.setId(mFantasy.getMusicId()).play(position);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		stopAll(0);
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopAll(0);
	}

	@Override
	public void onClick(
			View v) {
		switch (v.getId()) {
		case R.id.btn_play_fantasy:
			if (mBtn.isPlaying()) {
				stopAll(currentProgress);
			} else {
				startAll(0);
			}
			break;

		case R.id.btn_0:
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
			mCurrentVibration.setVisibility(View.VISIBLE);
			mCurrentVibration.setText(RunningBean.INSTANCE.getVibration().get(index - 1).getTitle());
			if( mVibrateJob != null ){
				mVibrateJob.cancel(true);
				mVibrateJob = null;
			}
			mVibrateJob = new VibrateJob();
			mVibrateJob.execute(index - 1);
			
			ArrayList<TreeMap<Integer, Integer>> list = RunningBean.INSTANCE.getFantasyData();
			list.get( mFantasyIndex ).put(currentProgress, index-1);
			RunningBean.INSTANCE.storeFantasyData(list);
			
			break;
		}
	}

	@Override
	public void onProgressChanged(
			SeekBar seekBar,
			int progress,
			boolean fromUser) {
		currentProgress = progress;
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
