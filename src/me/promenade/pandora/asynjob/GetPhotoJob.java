package me.promenade.pandora.asynjob;

import java.io.IOException;

import me.promenade.pandora.R;
import me.promenade.pandora.util.Constants;
import me.promenade.pandora.util.ImageUtil;
import me.promenade.pandora.util.SharedPreferenceUtil;
import me.promenade.pandora.util.XMPPUtil;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class GetPhotoJob extends AsyncTask<Integer, Integer, String> {
	public static final String TAG = "GetPhotoJob";

	private Context mContext = null;

	public static final int PHOTO_FOR_USER = 1;
	public static final int PHOTO_FOR_PARTNER = 2;

	private int mUserForWho = -1;

	public void setContext(Context ctx) {
		this.mContext = ctx;
	}

	@Override
	protected String doInBackground(Integer... param) {
		Log.d(TAG, "retriving...");
		if (param == null || param.length != 2)
			return null;

		int userId = param[0];
		mUserForWho = param[1];

		HttpResponse res = XMPPUtil.INSTANCE.get(Constants.GET_PHOTO_URL + "/"
				+ userId);
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

				String userPhotoStr = "";
				try {
					userPhotoStr = j.getString("photo");
				} catch (JSONException e) {
					e.printStackTrace();
				}

				if (userPhotoStr == null || userPhotoStr.length() == 0) {
					userPhotoStr = ImageUtil.INSTANCE
							.Bitmap2String(BitmapFactory.decodeResource(
									mContext.getResources(),
									R.drawable.empty_profile));
				}

				switch (mUserForWho) {
				case PHOTO_FOR_USER:
					SharedPreferenceUtil.INSTANCE.setData(
							Constants.SP_USER_PHOTO, userPhotoStr);
					break;
				case PHOTO_FOR_PARTNER:
					SharedPreferenceUtil.INSTANCE.setData(
							Constants.SP_PARTNER_PHOTO, userPhotoStr);
					break;
				}
			}
		}

		super.onPostExecute(result);
	}
}
