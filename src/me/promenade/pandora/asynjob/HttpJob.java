package me.promenade.pandora.asynjob;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import me.promenade.pandora.bean.HttpBean;
import me.promenade.pandora.bean.HttpMethod;
import me.promenade.pandora.util.XMPPUtil;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

public class HttpJob extends AsyncTask<HttpBean, Integer, HttpResponse> {
	public static final String TAG = "HttpJob";

	private HttpBean mHttpBean = null;

	@Override
	protected HttpResponse doInBackground(
			HttpBean... param) {
		Log.d(TAG,
				"retriving...");
		if (param == null)
			return null;

		this.mHttpBean = param[0];

		if (this.mHttpBean.getMethod() == HttpMethod.POST) {
			Log.i(TAG,
					"Start to post->" + mHttpBean.toString());
			return XMPPUtil.INSTANCE.post(this.mHttpBean);
		}

		return null;
	}

	@Override
	protected void onPostExecute(
			HttpResponse result) {

		if (result != null) {
			Log.i(TAG,
					"resposne:" + result.toString());
			Log.i(TAG,
					"status:" + result.getStatusLine());
			Log.i(TAG,
					"content-type:" + result.getEntity().getContentType());

			// try {
			// InputStream is = result.getEntity().getContent();
			// ByteArrayOutputStream bao = new ByteArrayOutputStream();
			// byte[] buffer = new byte[1024];
			// int length = 0;
			// while( (length=is.read(buffer)) != -1 ){
			// bao.write(buffer, 0, length);
			// }
			// String text = bao.toString();
			// Log.i( TAG, "contnt" + text );
			// } catch (IllegalStateException e) {
			// e.printStackTrace();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			try {
				String response = EntityUtils.toString(result.getEntity());
				Log.i( TAG, "content:" + response );
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		super.onPostExecute(result);
	}
}
