package me.promenade.pandora.asynjob;

import me.promenade.pandora.bean.RunningBean;
import me.promenade.pandora.util.BluetoothUtil;
import me.promenade.pandora.util.VibrateUtil;
import android.os.AsyncTask;

public class VibrateJob extends AsyncTask<Integer, Integer, String> {

	@Override
	protected String doInBackground(
			Integer... param) {
		if( param == null || param.length != 1 ){
			return null;
		}
		
//		MyVibrateView vView = (MyVibrateView) view.getTag();
		int patternIndex = param[0];

		int[] pattern = RunningBean.INSTANCE.getVibration().get(patternIndex).getPattern();

		if (BluetoothUtil.INSTANCE.isConntected()) {
			byte[] bArr = new byte[pattern.length + 1];
			int i = 0;
			for (int p : pattern) {
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
					500);
		} else {
			VibrateUtil.INSTANCE.vibrate(pattern);
		}
		
		return null;
	}

}
