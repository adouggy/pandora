package me.promenade.pandora.util;

import me.promenade.pandora.R;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;

public enum PopupUtil {
	INSTANCE;

	public static final String TAG = "PopupUtil";

	private Context mContext = null;
	private View mView = null;
	private boolean isShow = false;
	private WindowManager wm = null;

	public void init(View v, Context context) {
		mContext = context;

		if (isShow) {
			wm.removeView(mView);
			isShow = false;
		} else {
			Rect r = new Rect();
			v.getGlobalVisibleRect(r);

			int[] location = new int[2];
			v.getLocationOnScreen(location);

			Log.i(TAG, r.top + "," + r.right + "," + r.bottom + "," + r.left);
			Log.i(TAG, location[0] + "," + location[1]);

			mView = LayoutInflater.from(mContext).inflate(R.layout.pop_send,
					null, false);

			wm = (WindowManager) mContext
					.getSystemService(Context.WINDOW_SERVICE);
			
			isShow = true;
			
			int height = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, context.getResources().getDisplayMetrics()));
			
			addView(location[0], location[1]-height-10);
		}

	}
	
	private void addView(int x, int y){
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
		 * LayoutParams.FLAG_NOT_FOCUSABLE |
		 * LayoutParams.FLAG_NOT_TOUCHABLE;
		 */

		params.width = LayoutParams.WRAP_CONTENT;
		params.height = LayoutParams.WRAP_CONTENT;
		params.gravity = Gravity.LEFT | Gravity.TOP;
		params.x = x;
		params.y = y;

		wm.addView(mView, params);
	}

}
