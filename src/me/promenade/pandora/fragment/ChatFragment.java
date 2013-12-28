package me.promenade.pandora.fragment;

import java.util.ArrayList;

import me.promenade.pandora.R;
import me.promenade.pandora.adapter.ChatListAdapter;
import me.promenade.pandora.asynjob.HttpJob;
import me.promenade.pandora.bean.Chat;
import me.promenade.pandora.bean.HttpBean;
import me.promenade.pandora.bean.HttpMethod;
import me.promenade.pandora.bean.MessageType;
import me.promenade.pandora.bean.SendStatus;
import net.synergyinfosys.xmppclient.NotificationService;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class ChatFragment extends SherlockFragment implements OnClickListener {
	public static final String TAG = "ChatFragment";

	public static final int MSG_RECEIVE = 1;
	public static final int MSG_SEND = 2;

	private static ListView mList = null;
	private static ChatListAdapter mAdapter = null;
	private Button mBtnSend = null;
	private Button mBtnType = null;
	private EditText mEdtText = null;

	public static Handler mHandler = new Handler() {
		@Override
		public void handleMessage(
				Message msg) {

			Bundle b = msg.getData();
			String username = b.getString("username");
			String message = b.getString("message");

			Chat c = new Chat();
			c.setMessage(message);

			c.setTimestamp(System.currentTimeMillis());
			c.setMessageType(MessageType.Message);

			switch (msg.what) {
			case MSG_RECEIVE:
				c.setSendStatus(SendStatus.Received);
				c.setRemote(true);
				break;
			case MSG_SEND:
				c.setSendStatus(SendStatus.Sent);
				c.setRemote(false);
				break;
			}

			mAdapter.addChat(c);
			mList.post(new Runnable() {
				public void run() {
					mList.setSelection(mList.getCount() - 1);
				}
			});
		}
	};

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
		
		this.mBtnSend = (Button) view.findViewById(R.id.btn_send);
		this.mEdtText = (EditText) view.findViewById(R.id.edt_chat);
		this.mBtnType = (Button) view.findViewById(R.id.btn_sendType);
		
		this.mBtnSend.setOnClickListener(this);
		this.mBtnType.setOnClickListener(this);

		mAdapter = new ChatListAdapter(this.getActivity());
		mAdapter.setData(initChat());

		mList.setAdapter(mAdapter);

//		setupXmppPreference();
		Intent intent = new Intent(getActivity(), NotificationService.class);
		getActivity().startService(intent);

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private ArrayList<Chat> initChat() {
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
		return list;
	}

//	private void setupXmppPreference() {
//		SharedPreferences sharedPrefs = getActivity().getSharedPreferences("client_preferences",
//				Context.MODE_PRIVATE);
//		Editor editor = sharedPrefs.edit();
//		editor.putString("username",
//				"ade");
//		editor.putString("password",
//				"ade");
//		editor.putString("XMPP_USERNAME",
//				"ade");
//		editor.putString("XMPP_PASSWORD",
//				"ade");
//		editor.putString("XMPP_HOST",
//				"192.168.0.133");
//		editor.putInt("XMPP_PORT",
//				5222);
//		editor.putString(Constants.CALLBACK_ACTIVITY_PACKAGE_NAME,
//				"me.promenade.pandora.fragment");
//		editor.putString(Constants.CALLBACK_ACTIVITY_CLASS_NAME,
//				this.getClass().getName());
//		editor.commit();
//	}

	@Override
	public void onClick(
			View v) {
		switch (v.getId()) {
		case R.id.btn_send:
			String text = this.mEdtText.getText().toString().trim();

			JSONObject j = new JSONObject();
			try {
				j.put("devId",
						"ade");
				j.put("data",
						Base64.encodeToString(text.getBytes(),
								Base64.NO_WRAP | Base64.URL_SAFE));

				HttpBean b = new HttpBean();
				b.setUrl("http://173.255.242.145:28080/MyPush/resources/xmpp/push");
				b.setJson(j);
				b.setMethod(HttpMethod.POST);
				HttpJob job = new HttpJob();
				job.execute(b);

				Bundle bundle = new Bundle();
				bundle.putString("username",
						"me");
				bundle.putString("message",
						text);

				Message msg = new Message();
				msg.what = ChatFragment.MSG_SEND;
				msg.setData(bundle);
				ChatFragment.mHandler.sendMessage(msg);

			} catch (JSONException e) {
				e.printStackTrace();
			}

			break;
		case R.id.btn_sendType:
			break;
		}
	}
}
