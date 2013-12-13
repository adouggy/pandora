package me.promenade.pandora.asynjob;

import me.promenade.pandora.fragment.FantasyFragment;

import org.apache.http.HttpResponse;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class PlayMusicJob extends AsyncTask<Integer, Integer, HttpResponse> {
	public static final String TAG = "PlayMusicJob";

	@Override
	protected HttpResponse doInBackground(
			Integer... param) {
		Log.d(TAG,
				"retriving...");
		if (param == null)
			return null;

		for (int i = 0; i <= 100; i++) {

			publishProgress(i);			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	@Override
	protected void onProgressUpdate(
			Integer... values) {
		Log.i( TAG, values[0] + "" );
		Message msg = new Message();
		Bundle b = new Bundle();
		b.putInt("progress", values[0]);
		msg.setData(b);
		FantasyFragment.mHandler.sendMessage(msg);
		
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(
			HttpResponse result) {

		super.onPostExecute(result);
	}
}
