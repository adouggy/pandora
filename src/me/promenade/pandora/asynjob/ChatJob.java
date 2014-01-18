package me.promenade.pandora.asynjob;

import java.io.IOException;

import me.promenade.pandora.bean.RunningBean;
import me.promenade.pandora.fragment.ChatFragment;
import me.promenade.pandora.util.Constants;
import me.promenade.pandora.util.XMPPUtil;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

public class ChatJob extends AsyncTask<String, Integer, String> {
	public static final String TAG = "ChatJob";
	
	private String content;

	private Context mContext = null;

	public void setContext(
			Context ctx) {
		this.mContext = ctx;
	}

	@Override
	protected String doInBackground(
			String... param) {
		Log.d(TAG,
				"retriving...");
		if (param == null || param.length != 1)
			return null;

		 content = param[0];
		String partnerName = RunningBean.INSTANCE.getPartnerName();

		JSONObject json = new JSONObject();
		JSONObject textJson = new JSONObject();
		try {
			json.put("devId",
					partnerName);
			textJson.put("t",
					"text");
			textJson.put("b",
					content);
			json.put("data",
					Base64.encodeToString(textJson.toString().getBytes(),
							Base64.DEFAULT));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		String url = Constants.PUSH_URL;

		HttpResponse res = XMPPUtil.INSTANCE.post(url,
				json);
		if (res == null)
			return null;

		try {
			String response = EntityUtils.toString(res.getEntity());
			return response;
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(
			String result) {

		if (result != null) {
			Log.i(TAG,
					"content:" + result);
			JSONObject j = null;
			try {
				j = new JSONObject(result);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			if (j == null) {
				Toast.makeText(mContext,
						"网络错误",
						Toast.LENGTH_SHORT).show();
				return;
			}

			if (j != null) {
				Log.i(TAG,
						j.toString());

				String statusStr = null;
				try {
					statusStr = j.getString("status");
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				if (statusStr != null && statusStr.compareTo("ok") == 0) {
					Message msg = ChatFragment.mHandler.obtainMessage(ChatFragment.MSG_SEND);
					Bundle bundle = new Bundle();
					bundle.putString("message",
							content);
					msg.setData(bundle);
					msg.sendToTarget();
				} else {
					Toast.makeText(mContext,
							"发送失败",
							Toast.LENGTH_SHORT).show();
				}
			}
		}

		super.onPostExecute(result);
	}
}
