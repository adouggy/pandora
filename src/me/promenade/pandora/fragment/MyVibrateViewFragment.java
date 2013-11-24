package me.promenade.pandora.fragment;

import java.util.ArrayList;

import me.promenade.pandora.R;
import me.promenade.pandora.adapter.VibrateListAdapter;
import me.promenade.pandora.bean.Vibration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class MyVibrateViewFragment extends SherlockFragment {
	private static final String TAG = "MyVibrateViewFragment";
	private ListView mList = null;
	private VibrateListAdapter mAdapter = null;

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_vibrate,
				container,
				false);
		mList = (ListView) view.findViewById(R.id.list_vibrate);

		ArrayList<Vibration> list = new ArrayList<Vibration>();
		Vibration v1 = new Vibration();
		Vibration v2 = new Vibration();
		Vibration v3 = new Vibration();
		Vibration v4 = new Vibration();
		Vibration v5 = new Vibration();
		Vibration v6 = new Vibration();

		v1.setPattern(new int[] { 0, 1, 2, 3, 4, 5, 4, 3, 2, 1, 0, 1, 2, 3 });
		v2.setPattern(new int[] { 0, 3, 2, 1, 3, 2, 1, 3, 2, 1, 3, 2, 1, 3 });
		v3.setPattern(new int[] { 0, 1, 2, 3, 4, 5, 0, 3, 2, 1, 0, 1, 2, 3 });
		v4.setPattern(new int[] { 0, 1, 2, 3, 4, 0, 4, 3, 2, 1, 0, 8, 2, 3 });
		v5.setPattern(new int[] { 0, 1, 2, 3, 0, 5, 4, 3, 2, 1, 7, 1, 2, 3 });
		v6.setPattern(new int[] { 0, 1, 2, 0, 4, 5, 4, 3, 2, 6, 0, 1, 2, 3 });
		list.add(v1);
		list.add(v2);
		list.add(v3);
		list.add(v4);
		list.add(v5);
		list.add(v6);

		mAdapter = new VibrateListAdapter(this.getActivity());
		mAdapter.setData(list);

		mList.setAdapter(mAdapter);

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
