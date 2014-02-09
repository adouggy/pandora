package me.promenade.pandora.fragment;

import me.promenade.pandora.R;
import me.promenade.pandora.util.ChatUtil;
import me.promenade.pandora.util.Constants;
import me.promenade.pandora.util.SharedPreferenceUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockFragment;

public class MoreSettingFragment extends SherlockFragment {
	public static final String TAG = "MoreSettingFragment";

	private Button mDelete = null;
	private ToggleButton mNotification = null;
	private Button mClose = null;

	private OnClickListener mListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_setting_delete_chat_log:
				ChatUtil.INSTANCE.clear();
				Toast.makeText(getActivity(), "已清除", Toast.LENGTH_SHORT).show();
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
				getActivity().finish();
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pop_setting, container, false);

		mDelete = (Button) view.findViewById(R.id.btn_setting_delete_chat_log);
		mNotification = (ToggleButton) view
				.findViewById(R.id.btn_setting_show_notification);
		mClose = (Button) view.findViewById(R.id.btn_setting_close);

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

		return view;
	}

}
