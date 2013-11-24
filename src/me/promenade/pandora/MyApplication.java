package me.promenade.pandora;

import me.promenade.pandora.util.VibrateUtil;
import android.app.Application;

public class MyApplication extends Application {

	@Override
	public void onCreate() {

		VibrateUtil.INSTANCE.init(this);
		super.onCreate();
	}

}
