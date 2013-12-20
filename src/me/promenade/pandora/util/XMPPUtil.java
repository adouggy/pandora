package me.promenade.pandora.util;

import java.io.IOException;
import me.promenade.pandora.bean.HttpBean;
import me.promenade.pandora.bean.HttpMethod;
import net.synergyinfosys.xmppclient.Constants;
import net.synergyinfosys.xmppclient.NotificationService;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public enum XMPPUtil {
	INSTANCE;

	HttpClient httpclient = null;
	Context mContext = null;

	XMPPUtil() {
		httpclient = new DefaultHttpClient();
	}

	public void init(
			Context ctx) {
		this.mContext = ctx;
		setupXmppPreference();
		Intent intent = new Intent(mContext, NotificationService.class);
		mContext.startService(intent);
	}

	private void setupXmppPreference() {
		SharedPreferences sharedPrefs = mContext.getSharedPreferences("client_preferences",
				Context.MODE_PRIVATE);
		Editor editor = sharedPrefs.edit();
		editor.putString("username",
				"ade");
		editor.putString("password",
				"ade");
		editor.putString("XMPP_USERNAME",
				"ade");
		editor.putString("XMPP_PASSWORD",
				"ade");
		editor.putString("XMPP_HOST",
				"173.255.242.145");
		editor.putInt("XMPP_PORT",
				5222);
		editor.putString(Constants.CALLBACK_ACTIVITY_PACKAGE_NAME,
				"me.promenade.pandora.fragment");
		editor.putString(Constants.CALLBACK_ACTIVITY_CLASS_NAME,
				this.getClass().getName());
		editor.commit();
	}

	public HttpResponse post(
			HttpBean b) {
		if (b == null || b.getUrl() == null || b.getMethod() != HttpMethod.POST) {
			return null;
		}
		HttpPost httppost = new HttpPost(b.getUrl());
		try {
			if (b.getJson() != null) {
				httppost.setEntity(new StringEntity(b.getJson().toString()));

				httppost.setHeader("Accept",
						"application/json");
				httppost.setHeader("Content-type",
						"application/json");

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
}
