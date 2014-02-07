package me.promenade.pandora.util;

import me.promenade.pandora.R;
import me.promenade.pandora.asynjob.ChatSendJob;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;

public enum PopupUtil {
	INSTANCE;

	public static final String TAG = "PopupUtil";

	private View attachView = null;
	private Activity mActivity = null;
	private View mView = null;
	private boolean isShow = false;
	private WindowManager wm = null;

	private int mHeight = 0;

	private ImageView mCamera = null;
	private ImageView mPhoto = null;
	private ImageView mCommand = null;
	
	public boolean isRequestAccept = false;
	private Context mContext = null;
	
	public void init(Context ctx){
		this.mContext = ctx;
	}

	private OnClickListener mListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.img_popup_camera:
//				Toast.makeText(mActivity, "camera", Toast.LENGTH_SHORT).show();
				Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
                mActivity.startActivityForResult(cameraIntent, 2);  
				hide();
				break;
			case R.id.img_popup_photo:
//				Toast.makeText(mActivity, "photo", Toast.LENGTH_SHORT).show();
				hide();
				
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				mActivity.startActivityForResult(intent, 2);
				break;
			case R.id.img_popup_command:
//				Toast.makeText(mActivity, "command", Toast.LENGTH_SHORT).show();
				hide();
				if( isRequestAccept ){
					PopupCommandUtil.INSTANCE.init(attachView, mActivity);
				}else{
					ChatSendJob job = new ChatSendJob();
					job.setContext(mContext);
					job.setType(ChatSendJob.TYPE_COMMAND_REQUEST);
					job.execute(SharedPreferenceUtil.INSTANCE.getData(Constants.SP_USER_ID));
				}
				break;
			}
		}
	};

	public void init(View v, Activity act) {
		mActivity = act;
		attachView = v;

		// lazy init view
		if (mView == null) {
			mView = LayoutInflater.from(mActivity).inflate(R.layout.pop_send,
					null, false);
			wm = (WindowManager) mActivity
					.getSystemService(Context.WINDOW_SERVICE);
			mHeight = Math.round(TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 80, mActivity.getResources()
							.getDisplayMetrics())) + 10;

			mCamera = (ImageView) mView.findViewById(R.id.img_popup_camera);
			mPhoto = (ImageView) mView.findViewById(R.id.img_popup_photo);
			mCommand = (ImageView) mView.findViewById(R.id.img_popup_command);

			mCamera.setOnClickListener(mListener);
			mPhoto.setOnClickListener(mListener);
			mCommand.setOnClickListener(mListener);

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

		params.width = LayoutParams.WRAP_CONTENT;
		params.height = LayoutParams.WRAP_CONTENT;
		params.gravity = Gravity.LEFT | Gravity.TOP;
		params.x = x;
		params.y = y;

		wm.addView(mView, params);
	}

}
