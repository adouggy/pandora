package me.promenade.pandora.util;

import android.content.Context;
import android.os.Vibrator;

public enum VibrateUtil {
	INSTANCE;

	private Context mContext = null;
	Vibrator mVibrator = null;

	public void init(
			Context ctx) {
		this.mContext = ctx;

		// Get instance of Vibrator from current Context
		mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

	}

	public void test() {
		// Vibrate for 300 milliseconds
		mVibrator.vibrate(300);
	}
}
