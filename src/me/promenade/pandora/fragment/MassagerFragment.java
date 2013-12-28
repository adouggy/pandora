package me.promenade.pandora.fragment;

import me.promenade.pandora.R;
import me.promenade.pandora.util.BluetoothUtil;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.actionbarsherlock.app.SherlockFragment;

public class MassagerFragment extends SherlockFragment implements OnClickListener {
	public static final String TAG = "MessagerFragment";

	private VideoView mVideo = null;
	private Uri mUri = null;

	private RelativeLayout mSearchLayout = null;
	private static TextView mName = null;
	private static TextView mSearch = null;
	private static ImageView mImg = null;

	public static final int MSG_FOUND = 0;
	public static final int MSG_DONE = 1;
	public static final int MSG_DISCONNECTED = 2;

	public static Handler mHandler = new Handler() {
		@Override
		public void handleMessage(
				Message msg) {
			switch (msg.what) {
			case MSG_FOUND:
				mName.setText(R.string.txt_bluetooth_name_yes);
				mImg.setImageResource(R.drawable.icon_bluetooth);
				mSearch.setText(R.string.txt_bluetooth_search_hint);
				break;
			case MSG_DONE:
				mSearch.setText(R.string.txt_bluetooth_search_hint);
				break;
			case MSG_DISCONNECTED:
				mName.setText(R.string.txt_bluetooth_name_no);
				mImg.setImageResource(R.drawable.icon_bluetooth_disabled);
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

		View view = inflater.inflate(R.layout.fragment_massager,
				container,
				false);

		mUri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.pandora);

		mVideo = (VideoView) view.findViewById(R.id.videoView);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mVideo.setVideoURI(mUri);
			mVideo.setOnPreparedListener(new OnPreparedListener() {
				@Override
				public void onPrepared(
						MediaPlayer mp) {
					mp.setLooping(true);
					startVideo();
				}
			});
		}

		mSearchLayout = (RelativeLayout) view.findViewById(R.id.layout_massaer_search);
		mSearchLayout.setOnClickListener(this);

		mName = (TextView) view.findViewById(R.id.txt_bluetooth_name);
		mSearch = (TextView) view.findViewById(R.id.txt_bluetooth_search_hint);

		mImg = (ImageView) view.findViewById(R.id.img_bluetooth);

		return view;
	}

	@Override
	public void onStart() {
		startVideo();
		super.onStart();
	}

	@Override
	public void onResume() {
		startVideo();
		super.onResume();
	}

	@Override
	public void onPause() {
		stopVideo();
		super.onPause();
	}

	@Override
	public void onStop() {
		stopVideo();
		super.onStop();
	}

	private void startVideo() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mVideo.start();
			mVideo.setVisibility(View.VISIBLE);
		}
	}

	private void stopVideo() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mVideo.suspend();
			mVideo.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(
			View v) {
		switch (v.getId()) {
		case R.id.layout_massaer_search:
			BluetoothUtil.INSTANCE.startSearch();
			mSearch.setText(R.string.txt_bluetooth_search_hint_ongoing);
			break;
		}
	}

}
