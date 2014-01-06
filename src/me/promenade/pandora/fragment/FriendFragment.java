package me.promenade.pandora.fragment;

import java.util.ArrayList;

import me.promenade.pandora.HolderActivity;
import me.promenade.pandora.R;
import me.promenade.pandora.adapter.FriendListAdapter;
import me.promenade.pandora.bean.Friend;
import me.promenade.pandora.util.Constants;
import me.promenade.pandora.util.SharedPreferenceUtil;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class FriendFragment extends SherlockFragment {
	public static final String TAG = "FriendFragment";

	private static ListView mList = null;
	private static FriendListAdapter mAdapter = null;
	
	private static FriendFragment me = null;

	public static final int WHAT_REFRESH_MENU = 1;
	public static Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch( msg.what ){
			case WHAT_REFRESH_MENU:
				me.getActivity().supportInvalidateOptionsMenu();
				break;
			}
		};
	};

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_friend,
				container,
				false);
		setHasOptionsMenu(true);
		me = this;

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

	private ArrayList<Friend> initFriend() {
		ArrayList<Friend> list = new ArrayList<Friend>();

		Friend f = new Friend();
		f.setUsername("ade");

		Friend f2 = new Friend();
		f2.setUsername("test");

		list.add(f);
		list.add(f2);

		return list;
	}

	@Override
	public void onCreateOptionsMenu(
			Menu menu,
			MenuInflater inflater) {

		if (SharedPreferenceUtil.INSTANCE.getData("isLogin").length() == 0) {
			MenuItem actionItem = menu.add("登录");
			actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

			MenuItem actionItem2 = menu.add("注册");
			actionItem2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		} else {
			MenuItem actionItem2 = menu.add("注销");
			actionItem2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}
		super.onCreateOptionsMenu(menu,
				inflater);
	}

	@Override
	public boolean onOptionsItemSelected(
			MenuItem item) {
//		Toast.makeText(getActivity(),
//				item.getTitle().toString(),
//				Toast.LENGTH_SHORT).show();

		if (item.getTitle().toString().contains("注销")) {
			SharedPreferenceUtil.INSTANCE.setData(Constants.SP_IS_LOGIN,
					"");
			SharedPreferenceUtil.INSTANCE.setData(Constants.SP_USER_ID,
					"");
			getActivity().supportInvalidateOptionsMenu();
		} else if (item.getTitle().toString().compareTo("登录") == 0) {
			Intent i = new Intent(getActivity(), HolderActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Bundle b = new Bundle();
			b.putInt("fragment",
					HolderActivity.FRAGMENT_LOGIN);
			i.putExtras(b);
			getActivity().startActivity(i);
		} else if (item.getTitle().toString().compareTo("注册") == 0) {
			Intent i = new Intent(getActivity(), HolderActivity.class);
			Bundle b = new Bundle();
			b.putInt("fragment",
					HolderActivity.FRAGMENT_SIGNUP);
			b.putString("title",
					"注册");
			i.putExtras(b);
			getActivity().startActivity(i);
		}

		return super.onOptionsItemSelected(item);
	}
}
