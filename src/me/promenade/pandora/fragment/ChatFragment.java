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
import me.promenade.pandora.util.Constants;
import me.promenade.pandora.util.SharedPreferenceUtil;
import net.synergyinfosys.xmppclient.NotificationService;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
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
	
	private String myName = null;
	private String friendName = null;

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
		
		friendName = getActivity().getIntent().getExtras().getString("friend");
		myName = SharedPreferenceUtil.INSTANCE.getData(Constants.SP_USER_NAME);
		Log.i( TAG, friendName + "<->" + myName );

		mList = (ListView) view.findViewById(R.id.list_chat);
		mList.setDivider(null);

		this.mBtnSend = (Button) view.findViewById(R.id.btn_send);
		this.mEdtText = (EditText) view.findViewById(R.id.edt_chat);
		this.mBtnType = (Button) view.findViewById(R.id.btn_sendType);

		this.mBtnSend.setOnClickListener(this);
		this.mBtnType.setOnClickListener(this);

		mAdapter = new ChatListAdapter(this.getActivity());
		mAdapter.setData(new ArrayList<Chat>());

		mList.setAdapter(mAdapter);

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(
			View v) {
		switch (v.getId()) {
		case R.id.btn_send:
			String text = this.mEdtText.getText().toString().trim();

			JSONObject j = new JSONObject();
			try {
				j.put("devId",
						friendName);
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
						myName);
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
