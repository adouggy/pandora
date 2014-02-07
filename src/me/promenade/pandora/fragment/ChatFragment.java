package me.promenade.pandora.fragment;

import me.promenade.pandora.R;
import me.promenade.pandora.adapter.ChatListAdapter;
import me.promenade.pandora.asynjob.ChatSendJob;
import me.promenade.pandora.asynjob.VibrateJob;
import me.promenade.pandora.bean.Chat;
import me.promenade.pandora.bean.MessageType;
import me.promenade.pandora.bean.RunningBean;
import me.promenade.pandora.bean.SendStatus;
import me.promenade.pandora.util.Constants;
import me.promenade.pandora.util.PopupUtil;
import me.promenade.pandora.util.SharedPreferenceUtil;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class ChatFragment extends SherlockFragment implements OnClickListener {
	public static final String TAG = "ChatFragment";

	public static final int MSG_RECEIVE = 1;
	public static final int MSG_SEND = 2;

	private static ListView mList = null;
	public static ChatListAdapter mAdapter = null;
	private Button mBtnSend = null;
	private Button mBtnType = null;
	private EditText mEdtText = null;

	private String myName = null;
	private String friendName = null;

	private static Context me = null;

	private static VibrateJob mVibrateJob = null;

	public static Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			Bundle b = msg.getData();
			String message = b.getString("message");
			int type = b.getInt("type");

			Chat c = new Chat();

			c.setTimestamp(System.currentTimeMillis());

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

			switch (type) {
			case ChatSendJob.TYPE_TEXT:
				c.setMessage(message);
				c.setMessageType(MessageType.Message);
				break;
			case ChatSendJob.TYPE_PHOTO:
				// Bitmap bmp = ImageUtil.INSTANCE.String2Bitmap(message);
				c.setSendPhoto(message);
				c.setMessageType(MessageType.Image);
				break;
			case ChatSendJob.TYPE_COMMAND:
				String str = RunningBean.INSTANCE.getVibration()
						.get(Integer.parseInt(message)).getTitle();
				c.setMessage(str);
				c.setMessageType(MessageType.Command);

				if (mVibrateJob != null) {
					mVibrateJob.cancel(true);
				}

				mVibrateJob = new VibrateJob();
				mVibrateJob.execute(Integer.parseInt(message));
				break;
			case ChatSendJob.TYPE_COMMAND_REQUEST:
				if (msg.what == MSG_RECEIVE) {
					c.setMessage("［对方请求控制您的设备］");
					c.setMessageType(MessageType.Message);

					new AlertDialog.Builder(me)
							.setTitle("是否同意控制请求")
							.setPositiveButton("是",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface di,
												int index) {
											ChatSendJob job = new ChatSendJob();
											job.setContext(me);
											job.setType(ChatSendJob.TYPE_COMMAND_RESPONSE);
											job.execute(1 + "");
										}
									})
							.setNegativeButton("否",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface di,
												int index) {
											ChatSendJob job = new ChatSendJob();
											job.setContext(me);
											job.setType(ChatSendJob.TYPE_COMMAND_RESPONSE);
											job.execute(0 + "");
										}
									}).show();
				} else {
					c.setMessage("［已发送请求，请稍后］");
					c.setMessageType(MessageType.Message);
				}
				break;
			case ChatSendJob.TYPE_COMMAND_RESPONSE:
				if (msg.what == MSG_RECEIVE) {
					c.setMessage("［对方回复："
							+ (message.compareTo("1") == 0 ? "同意" : "拒绝") + "］");
					c.setMessageType(MessageType.Message);
					if( message.compareTo("1") == 0 ){
						PopupUtil.INSTANCE.isRequestAccept = true;
					}
				} else {
					c.setMessage("［已回复对方］");
					c.setMessageType(MessageType.Message);
				}

				break;
			}

			if (mAdapter != null) {
				mAdapter.addChat(c);
				mList.post(new Runnable() {
					public void run() {
						mList.setSelection(mList.getCount() - 1);
					}
				});
			}

		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_chat, container, false);

		getActivity().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

		me = getActivity();

		friendName = getActivity().getIntent().getExtras().getString("friend");
		myName = SharedPreferenceUtil.INSTANCE.getData(Constants.SP_USER_NAME);
		Log.i(TAG, friendName + "<->" + myName);

		mList = (ListView) view.findViewById(R.id.list_chat);
		mList.setDivider(null);

		this.mBtnSend = (Button) view.findViewById(R.id.btn_send);
		this.mEdtText = (EditText) view.findViewById(R.id.edt_chat);
		this.mBtnType = (Button) view.findViewById(R.id.btn_sendType);

		this.mBtnSend.setOnClickListener(this);
		this.mBtnType.setOnClickListener(this);

		mAdapter = new ChatListAdapter(this.getActivity());

		mList.setAdapter(mAdapter);

		mList.post(new Runnable() {
			public void run() {
				mList.setSelection(mList.getCount() - 1);
			}
		});

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send:
			String text = this.mEdtText.getText().toString().trim();

			ChatSendJob job = new ChatSendJob();
			job.setContext(getActivity());
			job.setType(ChatSendJob.TYPE_TEXT);
			job.execute(text);

			this.mEdtText.setText("");
			break;
		case R.id.btn_sendType:
			PopupUtil.INSTANCE.init(v, getActivity());

			break;
		}
	}
}
