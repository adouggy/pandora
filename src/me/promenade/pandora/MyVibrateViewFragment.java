package me.promenade.pandora;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class MyVibrateViewFragment extends SherlockFragment {
	private static final String TAG = "MyVibrateViewFragment";

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_vibrate,
				container,
				false);
		return view;

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
