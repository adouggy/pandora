package me.promenade.pandora.fragment;

import me.promenade.pandora.R;
import me.promenade.pandora.asynjob.BluetoothSearchJob;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class MassagerWithVideoFragment extends SherlockFragment implements
		OnClickListener, OnCompletionListener, OnErrorListener, OnInfoListener,
		OnPreparedListener, OnSeekCompleteListener, OnVideoSizeChangedListener,
		SurfaceHolder.Callback {
	public static final String TAG = "MassagerWithVideoFragment";
	public static final int REQUEST_OPEN_BLUETOOTH = 1;

	private SurfaceView surfaceView;
	private MediaPlayer player;
	private SurfaceHolder holder;
	private static ImageView mImgMasagger = null;

	private RelativeLayout mSearchLayout = null;
	private static TextView mName = null;
	private static TextView mSearch = null;
	private static ImageView mImg = null;

	public static final int MSG_FOUND = 0;
	public static final int MSG_DONE = 1;
	public static final int MSG_DISCONNECTED = 2;

	public static Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_massager_with_video,
				container, false);

		getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);

		surfaceView = (SurfaceView) view.findViewById(R.id.fragment_massager);
		mImgMasagger = (ImageView) view.findViewById(R.id.img_massager);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			surfaceView.setVisibility(View.VISIBLE);
			mImgMasagger.setVisibility(View.GONE);

			holder = surfaceView.getHolder();
			holder.addCallback(this);

			player = MediaPlayer.create(getActivity(), R.raw.pandora);
			player.setOnCompletionListener(this);
			player.setOnErrorListener(this);
			player.setOnInfoListener(this);
			player.setOnPreparedListener(this);
			player.setOnSeekCompleteListener(this);
			player.setOnVideoSizeChangedListener(this);
		} else {
			surfaceView.setVisibility(View.GONE);
			mImgMasagger.setVisibility(View.VISIBLE);
		}

		mSearchLayout = (RelativeLayout) view
				.findViewById(R.id.layout_massaer_search);
		mSearchLayout.setOnClickListener(this);

		mName = (TextView) view.findViewById(R.id.txt_bluetooth_name);
		mSearch = (TextView) view.findViewById(R.id.txt_bluetooth_search_hint);

		mImg = (ImageView) view.findViewById(R.id.img_bluetooth);

		return view;
	}

	@Override
	public void onPause() {
		if (player != null) {
			player.pause();
		}

		super.onPause();
	}

	@Override
	public void onResume() {
		if (player != null) {
			player.start();
		}
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_massaer_search:
			Log.i(TAG, "startSearch");
			
			MassagerWithVideoFragment.mHandler.obtainMessage(MassagerWithVideoFragment.MSG_DISCONNECTED).sendToTarget();
			BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
			
			if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
				Intent intent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivityForResult(intent, REQUEST_OPEN_BLUETOOTH);
			} else {
				searchBluetooth();
			}

			break;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		player.setDisplay(holder);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

	}

	@Override
	public void onSeekComplete(MediaPlayer mp) {
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		mp.start();
	}

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		// 当一些特定信息出现或者警告时触发
		switch (what) {
		case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
			break;
		case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:
			break;
		case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
			break;
		case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
			break;
		}
		return false;
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Log.v(TAG, "onError called");
		switch (what) {
		case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
			Log.v(TAG, "MEDIA_ERROR_SERVER_DIED");
			break;
		case MediaPlayer.MEDIA_ERROR_UNKNOWN:
			Log.v(TAG, "MEDIA_ERROR_UNKNOWN");
			break;
		default:
			Log.v(TAG, "others error:" + what + "," + extra);
			break;
		}
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		mp.start();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_OPEN_BLUETOOTH:
//			if( resultCode == Activity.RESULT_OK ){
//				Toast.makeText(getActivity(), "成功打开蓝牙", Toast.LENGTH_SHORT).show();
//			}else if (resultCode == Activity.RESULT_CANCELED){
//				Toast.makeText(getActivity(), "打开蓝牙取消", Toast.LENGTH_SHORT).show();
//			}
			searchBluetooth();
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void searchBluetooth() {
		mSearch.setText(R.string.txt_bluetooth_search_hint_ongoing);
		BluetoothSearchJob j2 = new BluetoothSearchJob();
		j2.execute(BluetoothSearchJob.TYPE_3_0);
		
	}
}
