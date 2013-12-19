package me.promenade.pandora.fragment;

import me.promenade.pandora.R;
import me.promenade.pandora.bean.Vibration;
import me.promenade.pandora.view.MyVibrateView;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class VibrateFragment extends SherlockFragment {
	public static final String TAG = "ViberateFragment";

	private Vibration mVibration = null;
	
	private MyVibrateView mView = null;
	
	public void setVibration(Vibration v){
		this.mVibration = v;
	}
	
	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_vibrate,
				container,
				false);
		
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏

		mView = (MyVibrateView) view.findViewById(R.id.viberate_fragment);
		if( mVibration != null ){
			mView.setData( mVibration.getPattern() );
		}
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
}
