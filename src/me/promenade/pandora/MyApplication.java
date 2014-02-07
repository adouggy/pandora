package me.promenade.pandora;

import me.promenade.pandora.util.BluetoothUtil;
import me.promenade.pandora.util.ChatUtil;
import me.promenade.pandora.util.MusicUtil;
import me.promenade.pandora.util.NotificationUtil;
import me.promenade.pandora.util.PopupUtil;
import me.promenade.pandora.util.SharedPreferenceUtil;
import me.promenade.pandora.util.VibrateUtil;
import me.promenade.pandora.util.XMPPUtil;
import android.app.Application;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		SharedPreferenceUtil.INSTANCE.init(this);

		VibrateUtil.INSTANCE.init(this);
		XMPPUtil.INSTANCE.init(this);
		BluetoothUtil.INSTANCE.init(this);
		MusicUtil.INSTANCE.init(this);
		ChatUtil.INSTANCE.init();
		NotificationUtil.INSTACE.init(this);
		PopupUtil.INSTANCE.init(this);
		super.onCreate();
	}

}
