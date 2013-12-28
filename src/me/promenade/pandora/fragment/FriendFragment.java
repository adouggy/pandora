package me.promenade.pandora.fragment;

import java.util.ArrayList;

import me.promenade.pandora.R;
import me.promenade.pandora.adapter.FriendListAdapter;
import me.promenade.pandora.bean.Friend;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class FriendFragment extends SherlockFragment {
	public static final String TAG = "FriendFragment";

	private static ListView mList = null;
	private static FriendListAdapter mAdapter = null;

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_friend,
				container,
				false);
		mList = (ListView) view.findViewById(R.id.list_friend);
		mList.setDivider(null);

		mAdapter = new FriendListAdapter(this.getActivity());
		mAdapter.setData(initFriend());

		mList.setAdapter(mAdapter);

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	private ArrayList<Friend> initFriend(){
		ArrayList<Friend> list = new ArrayList<Friend>();
		
		Friend f = new Friend();
		f.setUsername("ade");
		
		Friend f2 = new Friend();
		f2.setUsername("test");
		
		list.add(f);
		list.add(f2);
		
		return list;
	}
}
