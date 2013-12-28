package me.promenade.pandora.fragment;

import me.promenade.pandora.R;
import me.promenade.pandora.asynjob.PlayMusicJob;
import me.promenade.pandora.bean.Fantasy;
import me.promenade.pandora.view.PlayButton;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class FantasyFragment extends SherlockFragment implements OnClickListener, OnSeekBarChangeListener {
	public static final String TAG = "FantasyFragment";

	private static SeekBar mPb = null;
	private static PlayButton mBtn = null;
	private static TextView mTime = null;
	private static ImageView mImage= null;
	
	private Fantasy mFantasy = null;
	
	public static final int WHAT_POSITION_REFRESH = 1;
	public static final int WHAT_DURATION_REFRESH = 2;
	
	private PlayMusicJob mPlayJob = null;
	
	public void setFantasy( Fantasy f ){
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
				Log.i(TAG,
						p + "");
				mPb.setProgress(p);
				
				break;
			case WHAT_DURATION_REFRESH:
				Bundle bundle = msg.getData();
//				int duration = bundle.getInt("duration");
				String time = bundle.getString("time");
				mTime.setText(time);
				
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
		
		if( mFantasy != null ){
			Log.i(TAG, mFantasy.getLogoId() + "<--");
			mImage.setImageResource(mFantasy.getImageId());
		}

		mBtn.setOnClickListener(this);
		
		mPb.setOnSeekBarChangeListener(this);
		return view;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		if( mPlayJob != null ){
			mPlayJob.cancel(true);
		}
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
				mPlayJob.cancel(true);
			} else {
				Log.i(TAG,
						"start..");
				mBtn.setPlaying(true);
				
				mPlayJob = new PlayMusicJob();
				mPlayJob.execute(mFantasy.getMusicId());
			}
			break;
		}
	}

	@Override
	public void onProgressChanged(
			SeekBar seekBar,
			int progress,
			boolean fromUser) {
		
//		mTime.setText( progress + "" );
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
