package me.promenade.pandora.util;

import java.io.IOException;
import me.promenade.pandora.bean.HttpBean;
import me.promenade.pandora.bean.HttpMethod;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public enum XMPPUtil {
	INSTANCE;

	HttpClient httpclient = null;

	XMPPUtil() {
		httpclient = new DefaultHttpClient();
	}
	
	public HttpResponse post(HttpBean b) {
		if( b == null || b.getUrl() == null || b.getMethod() != HttpMethod.POST ){
			return null;
		}
		HttpPost httppost = new HttpPost(b.getUrl());
		try {
			if( b.getJson() != null ){
				httppost.setEntity( new StringEntity( b.getJson().toString()) );
				
				httppost.setHeader("Accept", "application/json");
			    httppost.setHeader("Content-type", "application/json");
			    
			}else if( b.getParam() != null ){
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
