package me.promenade.pandora.util;

import me.promenade.pandora.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ToggleButton;

public enum PopupSettingUtil {
	INSTANCE;

	public static final String TAG = "PopupSettingUtil";

	private Activity mActivity = null;
	private View mView = null;
	private boolean isShow = false;
	private WindowManager wm = null;

	private Button mDelete = null;
	private ToggleButton mNotification = null;
	private Button mClose = null;

	private OnClickListener mListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_setting_delete_chat_log:
				ChatUtil.INSTANCE.clear();
				hide();
				break;
			case R.id.btn_setting_show_notification:
				if (mNotification.isChecked()) {
					SharedPreferenceUtil.INSTANCE.setData(
							Constants.SHOW_NOTIFICATION,
							Boolean.TRUE.toString());
				} else {
					SharedPreferenceUtil.INSTANCE.setData(
							Constants.SHOW_NOTIFICATION,
							Boolean.FALSE.toString());
				}
				break;
			case R.id.btn_setting_close:
				hide();
				break;
			}
		}
	};

	public void init(View v, Activity act) {
		mActivity = act;
		// lazy init view
		if (mView == null) {
			mView = LayoutInflater.from(mActivity).inflate(
					R.layout.pop_setting, null, false);
			wm = (WindowManager) mActivity
					.getSystemService(Context.WINDOW_SERVICE);

			mDelete = (Button) mView
					.findViewById(R.id.btn_setting_delete_chat_log);
			mNotification = (ToggleButton) mView
					.findViewById(R.id.btn_setting_show_notification);
			mClose = (Button) mView.findViewById(R.id.btn_setting_close);

			String showStringStr = SharedPreferenceUtil.INSTANCE
					.getData(Constants.SHOW_NOTIFICATION);
			boolean show = true;
			if (showStringStr != null && showStringStr.length() > 0) {
				show = Boolean.parseBoolean(showStringStr);
			}
			mNotification.setChecked(show);

			mDelete.setOnClickListener(mListener);
			mNotification.setOnClickListener(mListener);
			mClose.setOnClickListener(mListener);
		}

		if (isShow) {
			wm.removeView(mView);
			isShow = false;
		} else {
			int[] location = new int[2];
			v.getLocationOnScreen(location);
			Log.i(TAG, location[0] + "," + location[1]);
			addView(location[0] + 100, location[1]); // - mHeight
			isShow = true;
		}

	}

	public void hide() {
		if (wm != null && mView != null) {
			wm.removeView(mView);
			isShow = false;
		}
	}

	private void addView(int x, int y) {
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		/*
		 * TYPE_SYSTEM_ALERT 如果设置为params.type =
		 * WindowManager.LayoutParams.TYPE_PHONE; 那么优先级会降低一些, 即拉下通知栏不可见
		 */
		params.type = WindowManager.LayoutParams.TYPE_PHONE;
		params.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
		// 设置Window flag
		params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		/*
		 * 下面的flags属性的效果形同“锁定”。 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
		 * wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL |
		 * LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE;
		 */

		params.width = LayoutParams.WRAP_CONTENT;
		params.height = LayoutParams.WRAP_CONTENT;
		params.gravity = Gravity.LEFT | Gravity.TOP;
		params.x = x;
		params.y = y;

		wm.addView(mView, params);
	}

}
