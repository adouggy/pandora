package me.promenade.pandora.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public enum NetworkUtil {
	INSTANCE;

	private Context mContext = null;

	public void setContext(Context ctx) {
		mContext = ctx;
	}

	public boolean isNetworkConnected() {
		if (mContext != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
}
