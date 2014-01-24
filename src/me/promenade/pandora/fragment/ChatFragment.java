package me.promenade.pandora.fragment;

import java.util.ArrayList;

import me.promenade.pandora.R;
import me.promenade.pandora.adapter.ChatListAdapter;
import me.promenade.pandora.asynjob.ChatSendJob;
import me.promenade.pandora.asynjob.VibrateJob;
import me.promenade.pandora.bean.Chat;
import me.promenade.pandora.bean.MessageType;
import me.promenade.pandora.bean.RunningBean;
import me.promenade.pandora.bean.SendStatus;
import me.promenade.pandora.util.Constants;
import me.promenade.pandora.util.ImageUtil;
import me.promenade.pandora.util.PopupUtil;
import me.promenade.pandora.util.SharedPreferenceUtil;
import android.graphics.Bitmap;
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
	
	private static VibrateJob mVibrateJob = null;

	public static Handler mHandler = new Handler() {
		@Override
		public void handleMessage(
				Message msg) {

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
			
			switch( type ){
			case ChatSendJob.TYPE_TEXT:
				c.setMessage(message);
				c.setMessageType(MessageType.Message);
				break;
			case ChatSendJob.TYPE_PHOTO:
				Bitmap bmp = ImageUtil.INSTANCE.String2Bitmap(message);
				c.setSendPhoto(bmp);
				c.setMessageType(MessageType.Image);
				break;
			case ChatSendJob.TYPE_COMMAND:
				String str = RunningBean.INSTANCE.getVibration().get( Integer.parseInt( message ) ).getTitle();
				c.setMessage(str );
				c.setMessageType(MessageType.Command);
				
				if( mVibrateJob != null ){
					mVibrateJob.cancel(true);
				}
				
				mVibrateJob = new VibrateJob();
				mVibrateJob.execute(Integer.parseInt(message));
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
		
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);  
		
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
