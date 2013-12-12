package me.promenade.pandora.fragment;

import java.util.ArrayList;

import me.promenade.pandora.R;
import me.promenade.pandora.adapter.BluetoothListAdapter;
import me.promenade.pandora.bean.Bluetooth;
import me.promenade.pandora.util.BluetoothUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class MassagerFragment extends SherlockFragment {
//	private static final String TAG = "MessagerFragment";

	private static ListView mList = null;
	private static BluetoothListAdapter mAdapter = null;

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_massager,
				container,
				false);

		mList = (ListView) view.findViewById(R.id.list_massager);
		mList.setDivider(null);
		mAdapter = new BluetoothListAdapter(this.getActivity());
		mAdapter.setData(new ArrayList<Bluetooth>());
		mList.setAdapter(mAdapter);

		BluetoothUtil.INSTANCE.startSearch();
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	public static void addDevice(Bluetooth b){
		mAdapter.addData(b);
	}

}
