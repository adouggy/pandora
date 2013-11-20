package me.promenade.pandora;

import android.app.Application;

public class MyApplication extends Application {

	@Override
	public void onCreate() {

		VibrateUtil.INSTANCE.init(this);
		super.onCreate();
	}

}
