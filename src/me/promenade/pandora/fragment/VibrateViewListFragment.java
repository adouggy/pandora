package me.promenade.pandora.fragment;

import java.util.ArrayList;

import me.promenade.pandora.HolderActivity;
import me.promenade.pandora.R;
import me.promenade.pandora.adapter.VibrateListAdapter;
import me.promenade.pandora.bean.Vibration;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class VibrateViewListFragment extends SherlockFragment implements OnItemLongClickListener {
	public static final String TAG = "VibrateViewListFragment";
	public static ListView mList = null;
	public static ArrayList<Vibration> mVibrationList = null;
	private VibrateListAdapter mAdapter = null;

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_vibrate_list,
				container,
				false);
		mList = (ListView) view.findViewById(R.id.list_vibrate);

		mAdapter = new VibrateListAdapter(this.getActivity());

		mVibrationList = initData();
		mAdapter.setData(mVibrationList);
		mList.setAdapter(mAdapter);

		mList.setOnItemLongClickListener(this);

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private ArrayList<Vibration> initData() {
		ArrayList<Vibration> list = new ArrayList<Vibration>();
		Vibration v1 = new Vibration();
		Vibration v2 = new Vibration();
		Vibration v3 = new Vibration();
		Vibration v4 = new Vibration();
		Vibration v5 = new Vibration();
		Vibration v6 = new Vibration();

		v1.setPattern(new int[] { 0, 1, 2, 3, 4, 5, 4, 3, 2, 1, 0, 1, 2, 3 });
		v2.setPattern(new int[] { 1, 3, 2, 1, 3, 2, 1, 3, 2, 1, 3, 2, 1, 3 });
		v3.setPattern(new int[] { 2, 1, 2, 3, 4, 5, 0, 3, 2, 1, 0, 1, 2, 3 });
		v4.setPattern(new int[] { 3, 1, 2, 3, 4, 0, 4, 3, 2, 1, 0, 8, 2, 3 });
		v5.setPattern(new int[] { 4, 1, 2, 3, 0, 5, 4, 3, 2, 1, 7, 1, 2, 3 });
		v6.setPattern(new int[] { 0, 1, 2, 0, 4, 5, 4, 3, 2, 6, 0, 1, 2, 3 });
		list.add(v1);
		list.add(v2);
		list.add(v3);
		list.add(v4);
		list.add(v5);
		list.add(v6);
		return list;
	}

	@Override
	public boolean onItemLongClick(
			AdapterView<?> arg0,
			View arg1,
			int position,
			long id) {
		int realPosition = (int) id;

		Intent i = new Intent(getActivity(), HolderActivity.class);
		Bundle b = new Bundle();
		b.putInt("fragment",
				HolderActivity.FRAGMENT_VIBRATE);
		b.putInt("position",
				realPosition);
		i.putExtras(b);
		getActivity().startActivity(i);
		return false;
	}

}
