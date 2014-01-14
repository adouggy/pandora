package me.promenade.pandora.asynjob;

import java.io.IOException;

import me.promenade.pandora.fragment.ProfileFragment;
import me.promenade.pandora.util.Constants;
import me.promenade.pandora.util.XMPPUtil;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class GetPhotoJob extends AsyncTask<Integer, Integer, String> {
	public static final String TAG = "GetPhotoJob";

	@Override
	protected String doInBackground(
			Integer... param) {
		Log.d(TAG,
				"retriving...");
		if (param == null)
			return null;

		int userId = param[0];

		HttpResponse res = XMPPUtil.INSTANCE.get(Constants.GET_PHOTO_URL + "/" + userId);
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
			
			Log.i(TAG, j.toString());
			
			Message msg = ProfileFragment.mHandler.obtainMessage();
			Bundle b = new Bundle();
			try {
				b.putString("photo", j.getString("photo"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			msg.setData(b);
			msg.sendToTarget();
		}

		super.onPostExecute(result);
	}
}
