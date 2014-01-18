package me.promenade.pandora.asynjob;

import java.io.IOException;

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
import android.util.Log;
import android.widget.Toast;

public class RemovePartnerJob extends AsyncTask<JSONObject, Integer, String> {
	public static final String TAG = "RemovePartnerJob";

	private Context mContext = null;

	private JSONObject json;

	public void setContext(
			Context ctx) {
		this.mContext = ctx;
	}

	@Override
	protected String doInBackground(
			JSONObject... param) {
		Log.d(TAG,
				"retriving...");
		if (param == null || param.length != 1)
			return null;

		json = param[0];
		
		String userId = "-1";
		try {
			userId = json.getString("userId");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String url = Constants.UPDATE_USER_URL + "/" + userId;

		HttpResponse res = XMPPUtil.INSTANCE.post(url, json);
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
					Toast.makeText(mContext, "伙伴已解除", Toast.LENGTH_SHORT).show();
					
					SharedPreferenceUtil.INSTANCE.setData(Constants.SP_PARTNER_ID, "");
					SharedPreferenceUtil.INSTANCE.setData(Constants.SP_PARTNER_NAME, "");
				}else{
					Toast.makeText(mContext, "伙伴不存在", Toast.LENGTH_SHORT).show();
				}
			}
		}

		super.onPostExecute(result);
	}
}
