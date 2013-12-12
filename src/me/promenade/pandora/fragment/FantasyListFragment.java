package me.promenade.pandora.fragment;

import java.util.ArrayList;

import me.promenade.pandora.R;
import me.promenade.pandora.adapter.FantasyListAdapter;
import me.promenade.pandora.bean.Fantasy;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class FantasyListFragment extends SherlockFragment {
	public static final String TAG = "HomeFragment";

	private static ListView mList = null;
	private static FantasyListAdapter mAdapter = null;

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_fantasy_list,
				container,
				false);
		mList = (ListView) view.findViewById(R.id.list_home);
		mList.setDivider(null);

		mAdapter = new FantasyListAdapter(this.getActivity());
		mAdapter.setData(initFantasy());

		mList.setAdapter(mAdapter);

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	private ArrayList<Fantasy> initFantasy(){
		ArrayList<Fantasy> list = new ArrayList<Fantasy>();
		
		Fantasy f1 = new Fantasy();
		f1.setTime(System.currentTimeMillis());
		f1.setTitle("Woods");
		f1.setDescription("blah..");
		f1.setLogoId(R.drawable.img_woods);
		list.add(f1);
		
		Fantasy f2 = new Fantasy();
		f2.setTime(System.currentTimeMillis());
		f2.setTitle("Sea");
		f2.setDescription("blah..xxx");
		f2.setLogoId(R.drawable.img_beach);
		list.add(f2);
		
		return list;
	}
}
