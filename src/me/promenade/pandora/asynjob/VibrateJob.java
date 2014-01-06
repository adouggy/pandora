package me.promenade.pandora.asynjob;

import me.promenade.pandora.bean.RunningBean;
import me.promenade.pandora.bean.Vibration;
import me.promenade.pandora.fragment.FantasyFragment;
import me.promenade.pandora.util.BluetoothUtil;
import me.promenade.pandora.util.VibrateUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;

public class VibrateJob extends AsyncTask<Integer, Integer, String> {
	
	public static final int WITH_UI = 1;
	public static final int WITHOUT_UI = 2;
	
	private int ui = 2;

	@Override
	protected String doInBackground(
			Integer... param) {
		if (param == null || param.length != 2) {
			return null;
		}

		int patternIndex = param[0];
		ui = param[1];
		
		Vibration v = RunningBean.INSTANCE.getVibration().get(patternIndex);
		int[] pattern = v.getPattern();

		if (ui == WITH_UI) {
			Message msg = FantasyFragment.mHandler.obtainMessage();
			msg.what = FantasyFragment.WHAT_START_V;
			Bundle b = new Bundle();
			b.putString("currentV",
					v.getTitle());
			msg.setData(b);
			msg.sendToTarget();
		}

		if (BluetoothUtil.INSTANCE.isConntected()) {
			byte[] bArr = new byte[pattern.length + 1];
			int i = 0;
			for (int p : pattern) {
				if (isCancelled())
					return "";

				if (p == 0) {
					bArr[i] = 't';
				} else if (p == 1) {
					bArr[i] = 'a';
				} else if (p == 2) {
					bArr[i] = 'b';
				} else if (p == 3) {
					bArr[i] = 'c';
				} else if (p == 4) {
					bArr[i] = 'd';
				} else if (p == 5) {
					bArr[i] = 'e';
				} else if (p == 6) {
					bArr[i] = 'f';
				}

				i++;
			}

			bArr[i] = 't';

			BluetoothUtil.INSTANCE.sendMessage(bArr,
					VibrateUtil.INTERVAL);
		} else {
			for (int level : pattern) {
				if (isCancelled())
					return "";

				long startTime = System.currentTimeMillis();

				long[] singlePattern = VibrateUtil.INSTANCE.v(level);

				VibrateUtil.INSTANCE.getViberator().vibrate(singlePattern,
						-1);

				long currentTime = System.currentTimeMillis();
				while (currentTime - startTime < VibrateUtil.INTERVAL) {
					try {
						Thread.sleep(VibrateUtil.INTERVAL - (currentTime - startTime));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					currentTime = System.currentTimeMillis();
				}
			}
		}

		return null;
	}

	@Override
	protected void onPostExecute(
			String result) {
		if (ui == WITH_UI) {
			Message msg = FantasyFragment.mHandler.obtainMessage();
			msg.what = FantasyFragment.WHAT_STOP_V;
			msg.sendToTarget();
		}

		super.onPostExecute(result);
	}

}
