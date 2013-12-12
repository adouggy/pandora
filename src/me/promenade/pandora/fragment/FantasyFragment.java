package me.promenade.pandora.fragment;

import me.promenade.pandora.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class FantasyFragment extends SherlockFragment {
	public static final String TAG = "FantasyFragment";

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_fantasy,
				container,
				false);

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
