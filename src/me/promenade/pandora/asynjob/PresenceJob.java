package me.promenade.pandora.asynjob;

import java.io.IOException;

import me.promenade.pandora.fragment.FriendFragment;
import me.promenade.pandora.util.Constants;
import me.promenade.pandora.util.XMPPUtil;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class PresenceJob extends AsyncTask<String, Integer, String> {
	public static final String TAG = "PresenceJob";
	
	private Context mContext = null;
	
	public void setContext(Context ctx){
		mContext = ctx;
	}

	@Override
	protected String doInBackground(String... param) {
		Log.d(TAG, "retriving...");
		if (param == null || param.length != 1)
			return null;
		
		String userIdStr = param[0];

		HttpResponse res = XMPPUtil.INSTANCE.get(Constants.USER_URL + "/" + userIdStr);
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
	protected void onPostExecute(String result) {

		if (result != null) {
			Log.i(TAG, "content:" + result);
			JSONObject j = null;
			try {
				j = new JSONObject(result);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			if (j == null) {
				Toast.makeText(mContext, "网络错误", Toast.LENGTH_SHORT).show();
				return;
			}

			if (j != null) {
				Log.i(TAG, j.toString());

				String statusStr = null;
				try {
					statusStr = j.getString("status");
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				if (statusStr != null && statusStr.compareTo("ok") == 0) {
					
					String presence = null;
					try {
						presence = j.getString("presence");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					Message msg = FriendFragment.mHandler.obtainMessage(FriendFragment.WHAT_REFRESH_PRESENCE);
					if( presence != null && presence.compareTo("online") == 0 ){
						msg.arg1 = 1;
					}else{
						msg.arg1 = 0;
					}
					msg.sendToTarget();
					
				} 
			}
		}

		super.onPostExecute(result);
	}
}
