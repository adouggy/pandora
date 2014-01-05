package me.promenade.pandora.asynjob;

import java.util.ArrayList;

import me.promenade.pandora.bean.RunningBean;
import me.promenade.pandora.bean.Vibration;
import me.promenade.pandora.fragment.FantasyFragment;
import me.promenade.pandora.util.VibrateUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class VibrateAllJob extends AsyncTask<Integer, Integer, String> {
	public static final String TAG = "VibrateAllJob";

	@Override
	protected String doInBackground(
			Integer... param) {
		if (param == null || param.length != 1) {
			return null;
		}

		int index = param[0];
		ArrayList<Vibration> list = RunningBean.INSTANCE.getVibration();
		for (int i = index; i < list.size(); i++) {
			if (isCancelled())
				break;

			Message msg = FantasyFragment.mHandler.obtainMessage();
			msg.what = FantasyFragment.WHAT_START_V;
			Bundle b = new Bundle();
			b.putString("currentV",
					list.get(i).getTitle());
			Log.i(TAG,
					list.get(i).getTitle());
			msg.setData(b);
			msg.sendToTarget();

			VibrateJob j = new VibrateJob();
			j.execute(i);

			try {
				Thread.sleep(VibrateUtil.TOTAL_DURATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	@Override
	protected void onPostExecute(
			String result) {

		Message msg = FantasyFragment.mHandler.obtainMessage();
		msg.what = FantasyFragment.WHAT_STOP_V;
		msg.sendToTarget();

		super.onPostExecute(result);
	}
}
