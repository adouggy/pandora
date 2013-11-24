package me.promenade.pandora.fragment;

import java.util.ArrayList;

import me.promenade.pandora.R;
import me.promenade.pandora.adapter.ChatListAdapter;
import me.promenade.pandora.bean.Chat;
import me.promenade.pandora.bean.SendStatus;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class ChatFragment extends SherlockFragment {
	private static final String TAG = "ChatFragment";
	private ListView mList = null;
	private ChatListAdapter mAdapter = null;

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_chat,
				container,
				false);
		mList = (ListView) view.findViewById(R.id.list_chat);
		mList.setDivider(null);
		

		ArrayList<Chat> list = new ArrayList<Chat>();
		Chat c1 = new Chat();
		Chat c2 = new Chat();
		Chat c3 = new Chat();
		Chat c4 = new Chat();
		Chat c5 = new Chat();

		c1.setMessage("blah");
		c1.setRemote(true);
		c1.setSendStatus(SendStatus.Sent);
		c1.setTimestamp(System.currentTimeMillis());

		c2.setMessage("blah b;aj.///");
		c2.setSendStatus(SendStatus.SentFailed);
		c2.setRemote(false);
		c2.setTimestamp(System.currentTimeMillis());

		c3.setMessage("中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文");
		c3.setRemote(true);
		c3.setSendStatus(SendStatus.Sent);
		c3.setTimestamp(System.currentTimeMillis());

		c4.setMessage("长，很长很航。长，很长很航。长，很长很航。长，很长很航。长，很长很航。长，很长很航。长，很长很航。长，很长很航。长，很长很航。长，很长很航。长，很长很航。长，很长很航。长，很长很航。长，很长很航。长，很长很航。长，很长很航。");
		c4.setSendStatus(SendStatus.Sent);
		c4.setRemote(false);
		c4.setTimestamp(System.currentTimeMillis());

		c5.setMessage("正在转");
		c5.setRemote(true);
		c5.setSendStatus(SendStatus.SentReceived);
		c5.setTimestamp(System.currentTimeMillis()+2000000);

		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		list.add(c5);

		mAdapter = new ChatListAdapter(this.getActivity());
		mAdapter.setData(list);

		mList.setAdapter(mAdapter);

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
