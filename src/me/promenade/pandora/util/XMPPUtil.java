package me.promenade.pandora.util;

import java.io.IOException;

import me.promenade.pandora.bean.HttpBean;
import me.promenade.pandora.bean.HttpMethod;
import me.promenade.pandora.bean.RunningBean;
import net.synergyinfosys.xmppclient.Constants;
import net.synergyinfosys.xmppclient.NotificationService;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public enum XMPPUtil {
	INSTANCE;

	public static final String TAG = "XMPPUtil";

	Context mContext = null;

	XMPPUtil() {
//		HttpParams params = new BasicHttpParams();
//		HttpProtocolParams.setVersion(params,
//				HttpVersion.HTTP_1_1);
//		HttpProtocolParams.setContentCharset(params,
//				"utf-8");
//		params.setBooleanParameter("http.protocol.expect-continue",
//				false);

		
	}

	public void init(
			Context ctx) {
		Log.i(TAG,
				"init...");
		this.mContext = ctx;
		if (RunningBean.INSTANCE.getUserId() != -1) {
			setupXmppPreference(SharedPreferenceUtil.INSTANCE.getData(me.promenade.pandora.util.Constants.SP_USER_NAME),
					SharedPreferenceUtil.INSTANCE.getData(me.promenade.pandora.util.Constants.SP_USER_PASSWORD));
			Intent intent = new Intent(mContext, NotificationService.class);
			mContext.startService(intent);
		}
	}

	public void stop() {
		Intent intent = new Intent(mContext, NotificationService.class);
		mContext.stopService(intent);
	}

	private void setupXmppPreference(
			String username,
			String password) {
		Log.i(TAG,
				username);
		Log.i(TAG,
				password);
		SharedPreferences sharedPrefs = mContext.getSharedPreferences("client_preferences",
				Context.MODE_PRIVATE);
		Editor editor = sharedPrefs.edit();
		
		editor.putString("username",
				username);
		editor.putString("password",
				password);
		editor.putString("XMPP_USERNAME",
				username);
		editor.putString("XMPP_PASSWORD",
				password);
		editor.putString("XMPP_HOST",
				me.promenade.pandora.util.Constants.XMPP_HOST);
		editor.putInt("XMPP_PORT",
				5222);
		editor.putString(Constants.CALLBACK_ACTIVITY_PACKAGE_NAME,
				"me.promenade.pandora.fragment");
		editor.putString(Constants.CALLBACK_ACTIVITY_CLASS_NAME,
				this.getClass().getName());
		editor.commit();
	}

	public HttpResponse post(
			String url,
			JSONObject json) {
		if (url == null || json == null) {
			return null;
		}
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		try {
			StringEntity entity = new StringEntity(json.toString(), HTTP.UTF_8);

			httppost.setHeader("Accept",
					"application/json");
			httppost.setHeader("Content-Type",
					"application/json; charset=utf-8");

			httppost.setEntity(entity);

			HttpResponse response = httpclient.execute(httppost);
			return response;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public HttpResponse post(
			HttpBean b) {
		if (b == null || b.getUrl() == null || b.getMethod() != HttpMethod.POST) {
			return null;
		}
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(b.getUrl());
		try {
			if (b.getJson() != null) {
				StringEntity entity = new StringEntity(b.getJson().toString(), HTTP.UTF_8);

				httppost.setHeader("Accept",
						"application/json");
				httppost.setHeader("Content-Type",
						"application/json; charset=utf-8");

				httppost.setEntity(entity);

			} else if (b.getParam() != null) {
				httppost.setEntity(new UrlEncodedFormEntity(b.getParam()));
			}
			HttpResponse response = httpclient.execute(httppost);
			return response;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public HttpResponse get(
			String url) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse response = httpclient.execute(get);
			return response;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
