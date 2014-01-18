package me.promenade.pandora.asynjob;

import java.io.IOException;

import me.promenade.pandora.RelationActivity;
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
import android.util.Log;
import android.widget.Toast;

public class GetPhotoJobForRelation extends AsyncTask<Integer, Integer, String> {
	public static final String TAG = "GetPhotoJobForRelation";
	
	private boolean isMalePhoto = false;
	
	private Context mContext = null;

	public void setContext(
			Context ctx) {
		this.mContext = ctx;
	}

	@Override
	protected String doInBackground(
			Integer... param) {
		Log.d(TAG,
				"retriving...");
		if (param == null || param.length != 2)
			return null;

		int userId = param[0];
		int isMale = param[1];
		if( isMale == 1 ){
			isMalePhoto = true;
		}else{
			isMalePhoto = false;
		}
		
		if( userId <=0 ){
			return null;
		}
		
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
			
			if( j == null ){
				Toast.makeText(mContext, "网络错误", Toast.LENGTH_SHORT).show();
				return;
			}

			if (j != null) {
				Log.i(TAG,
						j.toString());

				Message msg = RelationActivity.mHandler.obtainMessage(RelationActivity.WHAT_REFRESH_PHOTO);
				Bundle b = new Bundle();
				try {
					b.putString("photo",
							j.getString("photo"));
					b.putBoolean("isMale", isMalePhoto);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				msg.setData(b);
				msg.sendToTarget();
			}
		}

		super.onPostExecute(result);
	}
}
