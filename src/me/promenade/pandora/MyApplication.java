package me.promenade.pandora;

import me.promenade.pandora.util.VibrateUtil;
import me.promenade.pandora.util.XMPPUtil;
import android.app.Application;

public class MyApplication extends Application {

	@Override
	public void onCreate() {

		VibrateUtil.INSTANCE.init(this);
		XMPPUtil.INSTANCE.init(this);
		super.onCreate();
	}

}
