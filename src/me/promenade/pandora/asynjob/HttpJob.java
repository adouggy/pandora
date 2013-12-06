package me.promenade.pandora.asynjob;

import me.promenade.pandora.bean.HttpBean;
import me.promenade.pandora.bean.HttpMethod;
import me.promenade.pandora.util.XMPPUtil;

import org.apache.http.HttpResponse;

import android.os.AsyncTask;
import android.util.Log;

public class HttpJob extends AsyncTask<HttpBean, Integer, HttpResponse> {
	public static final String TAG = "HttpJob";
	
	private HttpBean mHttpBean = null;
	
	@Override
	protected HttpResponse doInBackground(
			HttpBean... param) {
		Log.d( TAG, "retriving..." );
		if( param == null )
			return null;
		
		this.mHttpBean = param[0];
		
		if( this.mHttpBean.getMethod() == HttpMethod.POST ){
			Log.i(  TAG, "Start to post->" + mHttpBean.toString());
			return XMPPUtil.INSTANCE.post( this.mHttpBean );
		}
		
		return null;
	}

	@Override
	protected void onPostExecute(
			HttpResponse result) {
		
		if( result != null ){
			Log.i( TAG, "resposne:" + result.toString() );
		}
		
		super.onPostExecute(result);
	}
}
