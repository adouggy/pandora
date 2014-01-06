package me.promenade.pandora.asynjob;

import java.io.IOException;

import me.promenade.pandora.HolderActivity;
import me.promenade.pandora.bean.HttpBean;
import me.promenade.pandora.bean.HttpMethod;
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

public class HttpJob extends AsyncTask<HttpBean, Integer, String> {
	public static final String TAG = "HttpJob";

	private HttpBean mHttpBean = null;

	public static final int TYPE_LOGIN = 1;
	public static final int TYPE_REGISTER = 2;
	public static final int TYPE_GET_USER = 3;

	private Context mContext = null;

	public void setContext(
			Context ctx) {
		this.mContext = ctx;
	}

	@Override
	protected String doInBackground(
			HttpBean... param) {
		Log.d(TAG,
				"retriving...");
		if (param == null)
			return null;

		this.mHttpBean = param[0];

		if (this.mHttpBean.getMethod() == HttpMethod.POST) {
			Log.i(TAG,
					"Start to post->" + mHttpBean.toString());
			HttpResponse res = XMPPUtil.INSTANCE.post(this.mHttpBean);

			try {
				String response = EntityUtils.toString(res.getEntity());

				return response;
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
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

			if (mHttpBean.getType() == TYPE_LOGIN) {
				try {
					if (j.getString("status").compareTo("ok") == 0 && j.getString("id") != null) {
						SharedPreferenceUtil.INSTANCE.setData(Constants.SP_IS_LOGIN,
								"true");
						SharedPreferenceUtil.INSTANCE.setData(Constants.SP_USER_ID,
								j.getString("id"));

						Toast.makeText(mContext,
								"登录成功",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(mContext,
								"登录失败",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				closeHolderRefreshMenu();
			} else if (mHttpBean.getType() == TYPE_REGISTER) {
				try {
					if (j.getString("status").compareTo("ok") == 0 && j.getString("id") != null) {
						SharedPreferenceUtil.INSTANCE.setData(Constants.SP_IS_LOGIN,
								"true");
						SharedPreferenceUtil.INSTANCE.setData(Constants.SP_USER_ID,
								j.getString("id"));

						Toast.makeText(mContext,
								"注册成功",
								Toast.LENGTH_SHORT).show();
						
						closeHolderRefreshMenu();
					} else if( j.getString("status").compareTo("duplicate") == 0 ){
						Toast.makeText(mContext,
								"用户名重复",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(mContext,
								"注册失败",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

		super.onPostExecute(result);
	}
	
	private void closeHolderRefreshMenu(){
		Message msg = HolderActivity.mHandler.obtainMessage();
		msg.what = HolderActivity.WHAT_FINISH;
		msg.sendToTarget();

		Message msg2 = FriendFragment.mHandler.obtainMessage();
		msg2.what = FriendFragment.WHAT_REFRESH_MENU;
		msg2.sendToTarget();
	}
}
