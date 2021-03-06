package me.promenade.pandora.receiver;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import me.promenade.pandora.asynjob.ChatSendJob;
import me.promenade.pandora.fragment.ChatFragment;
import me.promenade.pandora.util.Constants;
import me.promenade.pandora.util.NotificationUtil;
import me.promenade.pandora.util.SharedPreferenceUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class PushReceiver extends BroadcastReceiver {
	private static final String TAG = "PushReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		Log.d(TAG, "action=" + action);

		if ("org.androidpn.client.SHOW_NOTIFICATION".equals(action)) {
			Log.d(TAG, intent.toString());
			Bundle b = intent.getExtras();
			Iterator<String> iter = b.keySet().iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				Object value = b.get(key);
				if (key != null) {
					Log.d(TAG, key + "->");
				}
				if (value != null) {
					Log.d(TAG, value.toString());
				}
			}

			// String id = intent.getStringExtra("NOTIFICATION_ID");
			String notificationData = intent.getStringExtra("data");
			String username = intent.getStringExtra("devId");

			try {
//				byte[] dataByte = Base64.decode(notificationData,
//						Base64.DEFAULT);
				byte[] dataByte = notificationData.getBytes();
				
				if (dataByte != null) {
					String data = new String(dataByte);
					Log.d(TAG, "Receive Message:" + data);

					String realMsg = "";
					String type = "";
					try {
						JSONObject j = new JSONObject(data);
						realMsg = j.getString("b");
						type = j.getString("t");
					} catch (JSONException e) {
						e.printStackTrace();
					}

					Bundle bundle = new Bundle();
					bundle.putString("username", username);
					bundle.putString("message", realMsg);

					if (type.length() == 0)
						return;

					if (type.compareTo("image") == 0) {
						bundle.putInt("type", ChatSendJob.TYPE_PHOTO);
					} else if (type.compareTo("text") == 0) {
						bundle.putInt("type", ChatSendJob.TYPE_TEXT);
					} else if (type.compareTo("command") == 0) {
						bundle.putInt("type", ChatSendJob.TYPE_COMMAND);
					} else if (type.compareTo("partnerRequest") == 0) {
						bundle.putInt("type", ChatSendJob.TYPE_COMMAND_REQUEST);
					} else if (type.compareTo("partnerFeedback") == 0) {
						bundle.putInt("type", ChatSendJob.TYPE_COMMAND_RESPONSE);
					} 

					Message msg = new Message();
					msg.what = ChatFragment.MSG_RECEIVE;
					msg.setData(bundle);
					ChatFragment.mHandler.sendMessage(msg);
					
					String showStringStr = SharedPreferenceUtil.INSTANCE.getData(Constants.SHOW_NOTIFICATION);
					boolean show = true;
					if( showStringStr != null && showStringStr.length()>0 ){
						show = Boolean.parseBoolean(showStringStr);
					}
					
					if( show ){
					NotificationUtil.INSTACE.notification();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
