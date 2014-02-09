package me.promenade.pandora.util;

import me.promenade.pandora.HolderActivity;
import me.promenade.pandora.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public enum NotificationUtil {
	INSTACE;

	Context mContext = null;
	String ns = Context.NOTIFICATION_SERVICE;
	NotificationManager mNotificationManager = null;

	public void init(Context ctx) {
		this.mContext = ctx;
		mNotificationManager = (NotificationManager) mContext
				.getSystemService(ns);
	}

	public void notification() {
		Long when = System.currentTimeMillis();
		CharSequence tickerText = "你的伙伴给您发来的新的消息";
		Notification notification = new Notification(R.drawable.ic_launcher,
				tickerText, when);
		notification.vibrate = new long[] { 100, 250, 100, 500 };


		Intent i = new Intent(mContext, HolderActivity.class);
		Bundle b = new Bundle();
		b.putInt("fragment", HolderActivity.FRAGMENT_CHAT);
		b.putString("friend", SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_PARTNER_NAME));
		i.putExtras(b);

		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, i, 0);
		notification.setLatestEventInfo(mContext, "有新消息", "点击查看",
				contentIntent);

		mNotificationManager.notify(9999, notification);
	}
}
