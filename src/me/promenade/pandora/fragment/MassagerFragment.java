package me.promenade.pandora.fragment;

import java.util.ArrayList;

import me.promenade.pandora.R;
import me.promenade.pandora.adapter.BluetoothListAdapter;
import me.promenade.pandora.bean.Bluetooth;
import me.promenade.pandora.util.BluetoothUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class MassagerFragment extends SherlockFragment implements OnClickListener {
	// private static final String TAG = "MessagerFragment";

	private static ListView mList = null;
	private static BluetoothListAdapter mAdapter = null;
	private Button mBtn = null;
	private EditText mInput = null;
	private Button mBtnSend = null;

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

		mBtn = (Button) view.findViewById(R.id.btn_massager_search);
		mBtn.setOnClickListener(this);

		mInput = (EditText) view.findViewById(R.id.edt_bluetooth_input);
		
		mInput.setText("abcdefabcdef");
		
		mBtnSend = (Button) view.findViewById(R.id.btn_bluetooth_send);
		mBtnSend.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public static void addDevice(
			Bluetooth b) {
		mAdapter.addData(b);
	}

	@Override
	public void onClick(
			View v) {
		switch (v.getId()) {
		case R.id.btn_massager_search:
			BluetoothUtil.INSTANCE.startSearch();
			break;
			
		case R.id.btn_bluetooth_send:
			String text = mInput.getText().toString();
			byte[] bytes = text.getBytes();
			BluetoothUtil.INSTANCE.sendMessage( bytes );
		}
	}

}
