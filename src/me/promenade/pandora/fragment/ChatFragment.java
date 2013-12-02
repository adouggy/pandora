package me.promenade.pandora.fragment;

import java.util.ArrayList;

import net.synergyinfosys.xmppclient.Constants;
import net.synergyinfosys.xmppclient.NotificationService;
import net.synergyinfosys.xmppclient.ServiceManager;

import me.promenade.pandora.R;
import me.promenade.pandora.adapter.ChatListAdapter;
import me.promenade.pandora.bean.Chat;
import me.promenade.pandora.bean.SendStatus;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
		c5.setTimestamp(System.currentTimeMillis() + 2000000);

		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		list.add(c5);

		mAdapter = new ChatListAdapter(this.getActivity());
		mAdapter.setData(list);

		mList.setAdapter(mAdapter);

		setupXmppPreference();
		Intent intent = new Intent(getActivity(), NotificationService.class);
		getActivity().startService(intent);

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void setupXmppPreference() {
		SharedPreferences sharedPrefs = getActivity().getSharedPreferences("client_preferences",
				Context.MODE_PRIVATE);
		Editor editor = sharedPrefs.edit();
		editor.putString("username",
				"ade");
		editor.putString("password",
				"ade");
		editor.putString("XMPP_USERNAME",
				"ade");
		editor.putString("XMPP_PASSWORD",
				"ade");
		editor.putString("XMPP_HOST",
				"www.promenade.me");
		editor.putInt("XMPP_PORT",
				5222);
		editor.putString(Constants.CALLBACK_ACTIVITY_PACKAGE_NAME,
				"me.promenade.pandora.fragment");
		editor.putString(Constants.CALLBACK_ACTIVITY_CLASS_NAME,
				this.getClass().getName());
		editor.commit();
	}
}
