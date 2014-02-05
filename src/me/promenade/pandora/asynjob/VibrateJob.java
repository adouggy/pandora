package me.promenade.pandora.asynjob;

import me.promenade.pandora.bean.RunningBean;
import me.promenade.pandora.bean.Vibration;
import me.promenade.pandora.fragment.FantasyFragment;
import me.promenade.pandora.util.BluetoothUtil;
import me.promenade.pandora.util.VibrateUtil;
import android.os.AsyncTask;
import android.os.Handler;

public class VibrateJob extends AsyncTask<Integer, Integer, String> {
	
	private Handler mHandler = null;
	
	public void setHandler(Handler h){
		this.mHandler = h;
	}
	
	@Override
	protected String doInBackground(
			Integer... param) {
		if (param == null || param.length != 1) {
			return null;
		}

		int patternIndex = param[0];
		
		Vibration v = RunningBean.INSTANCE.getVibration().get(patternIndex);
		int[] pattern = v.getPattern();

		if (BluetoothUtil.INSTANCE.isConntected()) {
			byte[] bArr = new byte[2 + 14];
			bArr[0] = 'b';
			bArr[1] = 'd'; // single, x for loop
			int i=2;
			for( int p : pattern ){
				bArr[i++] = String.valueOf(p).getBytes()[0];
			}
			BluetoothUtil.INSTANCE.sendMessage(bArr);
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
		super.onPostExecute(result);
		if( mHandler != null ){
//			mHandler.obtainMessage(FantasyFragment.MSG_WHAT_CLEAR_SHOW_VIBRATE).sendToTarget();
		}
	}

}
