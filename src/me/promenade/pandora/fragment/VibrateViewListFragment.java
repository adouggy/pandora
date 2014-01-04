package me.promenade.pandora.fragment;

import java.util.ArrayList;

import me.promenade.pandora.HolderActivity;
import me.promenade.pandora.R;
import me.promenade.pandora.adapter.VibrateListAdapter;
import me.promenade.pandora.bean.RunningBean;
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

		mVibrationList = RunningBean.INSTANCE.getVibration();
		mAdapter.setData(mVibrationList);
		mList.setAdapter(mAdapter);

		mList.setOnItemLongClickListener(this);

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
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
		
		return true;
	}

}
