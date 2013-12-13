package me.promenade.pandora.fragment;

import me.promenade.pandora.R;
import me.promenade.pandora.asynjob.PlayMusicJob;
import me.promenade.pandora.view.PlayButton;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class FantasyFragment extends SherlockFragment implements OnClickListener, OnSeekBarChangeListener {
	public static final String TAG = "FantasyFragment";

	private static SeekBar mPb = null;
	private static PlayButton mBtn = null;
	private static TextView mTime = null;

	public static Handler mHandler = new Handler() {
		@Override
		public void handleMessage(
				Message msg) {
			Bundle b = msg.getData();
			int p = b.getInt("progress");
			Log.i(TAG,
					p + "");
			mPb.setProgress(p);
			
			mTime.setText( p + "" );

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

		mBtn.setOnClickListener(this);
		
		mPb.setOnSeekBarChangeListener(this);
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(
			View v) {
		switch (v.getId()) {
		case R.id.btn_play_fantasy:
			if (mBtn.isPlaying()) {
				Log.i(TAG,
						"stop..");
				mBtn.setPlaying(false);
			} else {
				Log.i(TAG,
						"start..");
				mBtn.setPlaying(true);
				PlayMusicJob j = new PlayMusicJob();
				j.execute();
			}
			break;
		}
	}

	@Override
	public void onProgressChanged(
			SeekBar seekBar,
			int progress,
			boolean fromUser) {
		
		mTime.setText( progress + "" );
	}

	@Override
	public void onStartTrackingTouch(
			SeekBar seekBar) {
		
	}

	@Override
	public void onStopTrackingTouch(
			SeekBar seekBar) {
		
	}

}
