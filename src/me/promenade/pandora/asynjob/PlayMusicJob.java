package me.promenade.pandora.asynjob;

import me.promenade.pandora.fragment.FantasyFragment;
import me.promenade.pandora.util.MusicUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class PlayMusicJob extends AsyncTask<Integer, Integer, String> {
	public static final String TAG = "PlayMusicJob";

	@Override
	protected String doInBackground(
			Integer... param) {
		Log.d(TAG,
				"retriving...");
		if (param == null || param.length != 2)
			return null;

		int musicId = param[0];
		int currentProgress = param[1];

		MusicUtil.INSTANCE.setId(musicId).play(currentProgress);

		int duration = MusicUtil.INSTANCE.getTime();
		int durationSec = duration / 1000;

		for (int i = currentProgress; i <= durationSec; i++) {

			if (isCancelled()) {
				break;
			}

			Message msg = new Message();
			msg.what = FantasyFragment.WHAT_DURATION_REFRESH;
			Bundle b = new Bundle();
			b.putInt("duration",
					duration / 1000);
			b.putString("time",
					i + "/" + durationSec);
			msg.setData(b);
			FantasyFragment.mHandler.sendMessage(msg);

			publishProgress(i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		MusicUtil.INSTANCE.setId(musicId).stop();

		return "";
	}

	@Override
	protected void onProgressUpdate(
			Integer... values) {
		Log.i(TAG,
				values[0] + "");
		Message msg = new Message();
		msg.what = FantasyFragment.WHAT_POSITION_REFRESH;
		Bundle b = new Bundle();
		b.putInt("progress",
				values[0]);
		msg.setData(b);
		FantasyFragment.mHandler.sendMessage(msg);

		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(
			String result) {
		Message msg = FantasyFragment.mHandler.obtainMessage();
		msg.what = FantasyFragment.WHAT_FINISH;
		msg.sendToTarget();

		super.onPostExecute(result);
	}
}
