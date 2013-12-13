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
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockFragment;

public class FantasyFragment extends SherlockFragment implements OnClickListener{;
	public static final String TAG = "FantasyFragment";
	
	private static ProgressBar mPb = null;
	private PlayButton mBtn = null;
	
	public static Handler mHandler = new Handler(){
		@Override
		public void handleMessage(
				Message msg) {
			Bundle b = msg.getData();
			int p = b.getInt("progress");
			Log.i( TAG, p + "" );
			mPb.setProgress(p);
			
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
		
		mPb = (ProgressBar) view.findViewById( R.id.pb_fantasy );
		mBtn = (PlayButton) view.findViewById( R.id.btn_play_fantasy );

		mBtn.setOnClickListener(this);
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(
			View v) {
		switch( v.getId() ){
		case R.id.btn_play_fantasy:
			if( mBtn.isPlaying() ){
				Log.i( TAG, "stop.." );
				mBtn.setPlaying(false);
			}else{
				Log.i( TAG, "start.." );
				mBtn.setPlaying(true);
				PlayMusicJob j = new PlayMusicJob();
				j.execute();
			}
			break;
		}
	}

}
