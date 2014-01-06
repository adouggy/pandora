package me.promenade.pandora.util;

import android.content.Context;
import android.content.SharedPreferences;

public enum SharedPreferenceUtil {
	INSTANCE;

	private Context mContext = null;
	private SharedPreferences mSharedPreferences = null;

	public void init(
			Context ctx) {
		this.mContext = ctx;

		mSharedPreferences = mContext.getSharedPreferences("pandora",
				0);
	}
	
	public void setData(String key, String value){
		SharedPreferences.Editor e = mSharedPreferences.edit();
		e.putString( key, value);
		e.commit();
	}

	public String getData(String key){
		return mSharedPreferences.getString(key, "");
	}
}
