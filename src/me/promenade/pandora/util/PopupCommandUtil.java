package me.promenade.pandora.util;

import me.promenade.pandora.R;
import me.promenade.pandora.asynjob.ChatSendJob;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public enum PopupCommandUtil {
	INSTANCE;

	public static final String TAG = "PopupCommandUtil";

	private Activity mActivity = null;
	private View mView = null;
	private boolean isShow = false;
	private WindowManager wm = null;

	private int mHeight = 0;

	private Button[] mBtns = new Button[12];

	private OnClickListener mListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_0:
				hide();
				break;

			case R.id.btn_1:
			case R.id.btn_2:
			case R.id.btn_3:
			case R.id.btn_4:
			case R.id.btn_5:
			case R.id.btn_6:
			case R.id.btn_7:
			case R.id.btn_8:
			case R.id.btn_9:
			case R.id.btn_10:
			case R.id.btn_11:
				int index = Integer.parseInt(((TextView) v).getText()
						.toString());
				ChatSendJob job = new ChatSendJob();
				job.setContext(mActivity);
				job.setType(ChatSendJob.TYPE_COMMAND);
				job.execute( (index-1) + "" );
				
				break;
			}
		}
	};

	private OnGlobalLayoutListener mGlobalListener = new OnGlobalLayoutListener() {

		@Override
		public void onGlobalLayout() {
			int width = mBtns[0].getWidth();
			LayoutParams lp = mBtns[0].getLayoutParams();
			lp.height = width;
			for (Button b : mBtns) {
				b.setLayoutParams(lp);
			}
		}
	};

	public void init(View v, Activity act) {
		mActivity = act;

		// lazy init view
		if (mView == null) {
			mView = LayoutInflater.from(mActivity).inflate(
					R.layout.pop_command, null, false);
			wm = (WindowManager) mActivity
					.getSystemService(Context.WINDOW_SERVICE);
			mHeight = Math.round(TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 200, mActivity.getResources()
							.getDisplayMetrics())) + 10;

			mBtns[0] = (Button) mView.findViewById(R.id.btn_0);
			mBtns[1] = (Button) mView.findViewById(R.id.btn_1);
			mBtns[2] = (Button) mView.findViewById(R.id.btn_2);
			mBtns[3] = (Button) mView.findViewById(R.id.btn_3);
			mBtns[4] = (Button) mView.findViewById(R.id.btn_4);
			mBtns[5] = (Button) mView.findViewById(R.id.btn_5);
			mBtns[6] = (Button) mView.findViewById(R.id.btn_6);
			mBtns[7] = (Button) mView.findViewById(R.id.btn_7);
			mBtns[8] = (Button) mView.findViewById(R.id.btn_8);
			mBtns[9] = (Button) mView.findViewById(R.id.btn_9);
			mBtns[10] = (Button) mView.findViewById(R.id.btn_10);
			mBtns[11] = (Button) mView.findViewById(R.id.btn_11);

			for (Button b : mBtns) {
				b.setOnClickListener(mListener);
				b.getViewTreeObserver().addOnGlobalLayoutListener(
						mGlobalListener);
			}
		}

		if (isShow) {
			wm.removeView(mView);
			isShow = false;
		} else {
			// Rect r = new Rect();
			// v.getGlobalVisibleRect(r);
			int[] location = new int[2];
			v.getLocationOnScreen(location);
			// Log.i(TAG, r.top + "," + r.right + "," + r.bottom + "," +
			// r.left);
			Log.i(TAG, location[0] + "," + location[1]);
			addView(location[0], location[1] - mHeight);
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

		params.width = mHeight;
		params.height = mHeight;
		params.gravity = Gravity.LEFT | Gravity.TOP;
		params.x = x;
		params.y = y;

		wm.addView(mView, params);
	}

}
