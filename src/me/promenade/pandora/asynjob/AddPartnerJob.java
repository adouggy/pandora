package me.promenade.pandora.asynjob;

import java.io.IOException;

import me.promenade.pandora.fragment.FriendFragment;
import me.promenade.pandora.util.Constants;
import me.promenade.pandora.util.SharedPreferenceUtil;
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

public class AddPartnerJob extends AsyncTask<String, Integer, String> {
	public static final String TAG = "AddPartnerJob";

	private Context mContext = null;

	private String myName;
	private String partnerName;

	public void setContext(
			Context ctx) {
		this.mContext = ctx;
	}

	@Override
	protected String doInBackground(
			String... param) {
		Log.d(TAG,
				"retriving...");
		if (param == null || param.length != 2)
			return null;

		myName = param[0];
		partnerName = param[1];

		HttpResponse res = XMPPUtil.INSTANCE.get(Constants.ADD_PARTNER_URL + "/" + myName + "/" + partnerName);
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
					SharedPreferenceUtil.INSTANCE.setData(Constants.SP_PARTNER_NAME,
							partnerName);
					
					String partnerId = "-1";
					try {
						partnerId = j.getString("partner");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					SharedPreferenceUtil.INSTANCE.setData(Constants.SP_PARTNER_ID,
							partnerId);
					Message msg = FriendFragment.mHandler.obtainMessage(FriendFragment.WHAT_REFRESH_FRIEND);
					msg.sendToTarget();
				}else{
					Toast.makeText(mContext, "伙伴不存在", Toast.LENGTH_SHORT).show();
				}
			}
		}

		super.onPostExecute(result);
	}
}
