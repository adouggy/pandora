package me.promenade.pandora.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import me.promenade.pandora.R;
import me.promenade.pandora.RelationActivity;
import me.promenade.pandora.asynjob.HttpJob;
import me.promenade.pandora.bean.HttpBean;
import me.promenade.pandora.bean.HttpMethod;
import me.promenade.pandora.bean.Profile;
import me.promenade.pandora.bean.RunningBean;
import me.promenade.pandora.util.Constants;
import me.promenade.pandora.util.ImageUtil;
import me.promenade.pandora.util.NameUtil;
import me.promenade.pandora.util.SharedPreferenceUtil;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class ProfileFragment extends SherlockFragment implements
		OnClickListener {
	public static final String TAG = "ProfileFragment";

	private static TextView mNick;
	private static ImageView mGender;
	private static ImageView mPhoto;

	private RelativeLayout mLayoutNick;
	private RelativeLayout mLayoutGender;
	private RelativeLayout mLayoutPhoto;
	private RelativeLayout mLayoutRelation;

	public static Profile mProfile;

	public static Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Log.i(TAG, "hadle message");
			String imgStr = SharedPreferenceUtil.INSTANCE
					.getData(Constants.SP_USER_PHOTO);
			if (imgStr != null && imgStr.length() > 0) {
				Log.i(TAG, "set photo");
				Bitmap bmp = ImageUtil.INSTANCE.String2Bitmap(imgStr);
				mPhoto.setImageBitmap(bmp);
				if (mProfile != null) {
					mProfile.setPhoto(bmp);
				}
			}

		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile, container,
				false);

		int userId = RunningBean.INSTANCE.getUserId();

		if (userId == -1) {
			Toast.makeText(getActivity(), "尚未登录", Toast.LENGTH_SHORT).show();
		}

		mNick = (TextView) view.findViewById(R.id.txt_profile_nickname);
		mGender = (ImageView) view.findViewById(R.id.img_profile_gender);
		mPhoto = (ImageView) view.findViewById(R.id.img_profile_photo);

		mLayoutNick = (RelativeLayout) view
				.findViewById(R.id.layout_profile_nick);
		mLayoutGender = (RelativeLayout) view
				.findViewById(R.id.layout_profile_gender);
		mLayoutPhoto = (RelativeLayout) view
				.findViewById(R.id.layout_profile_photo);
		mLayoutRelation = (RelativeLayout) view
				.findViewById(R.id.layout_profile_relation);

		mLayoutNick.setOnClickListener(this);
		mLayoutGender.setOnClickListener(this);
		mLayoutPhoto.setOnClickListener(this);
		mLayoutRelation.setOnClickListener(this);

		Profile p = new Profile();
		p.setNickName(SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_USER_NICK));
		p.setMale(Boolean.parseBoolean(SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_USER_GENDER)));
		mProfile = p;
		refreshProfile(p);

		mHandler.obtainMessage().sendToTarget();

		return view;
	}

	public void refreshProfile(Profile p) {
		mNick.setText(p.getNickName());
		if (p.isMale()) {
			mGender.setImageResource(R.drawable.male_head_loading);
		} else {
			mGender.setImageResource(R.drawable.female_head_loading);
		}
		mPhoto.setImageBitmap(p.getPhoto());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_profile_nick:
			if (RunningBean.INSTANCE.getUserId() == -1) {
				Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT)
						.show();
				break;
			}

			final EditText input = new EditText(getActivity());

			new AlertDialog.Builder(getActivity())
					.setTitle("输入昵称")
					.setMessage("昵称")
					.setView(input)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									Editable value = input.getText();
									String nickname = value.toString();
									updateNickname(nickname);
									mNick.setText(nickname);
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
								}
							}).show();

			break;
		case R.id.layout_profile_gender:
			Dialog alertDialog = new AlertDialog.Builder(getActivity())
					.setTitle("性别")
					.setMessage("请选择性别")
					.setIcon(R.drawable.ic_launcher)
					.setPositiveButton("男",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									updateGender(true);
									mProfile.setMale(true);
									refreshProfile(mProfile);
								}

							})
					.setNegativeButton("女",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									updateGender(false);
									mProfile.setMale(false);
									refreshProfile(mProfile);

								}
							}).create();
			alertDialog.show();

			break;
		case R.id.layout_profile_photo:
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intent, 1);
			break;
		case R.id.layout_profile_relation:
			Intent i = new Intent(getActivity(), RelationActivity.class);
			getActivity().startActivity(i);
			break;
		}
	}

	private void updateNickname(String nickname) {
		int userId = Integer.parseInt(SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_USER_ID));
		String password = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_USER_PASSWORD);
		String username =SharedPreferenceUtil.INSTANCE
						.getData(Constants.SP_USER_NAME);

		JSONObject json = new JSONObject();
		try {
			json.put("username", username);
			json.put("password", password);
			json.put("name", nickname);
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
			// e.printStackTrace();
		}

		Log.d(TAG, json.toString());

		HttpBean h = new HttpBean();
		h.setJson(json);
		h.setMethod(HttpMethod.POST);
		h.setUrl(Constants.UPDATE_URL + "/" + userId);
		h.setType(HttpJob.TYPE_UPDATE);

		HttpJob job = new HttpJob();
		job.setContext(getActivity());
		job.execute(h);
	}

	private void updateGender(boolean isMale) {
		int userId = Integer.parseInt(SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_USER_ID));
		String password = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_USER_PASSWORD);
		String username =SharedPreferenceUtil.INSTANCE
						.getData(Constants.SP_USER_NAME);

		JSONObject json = new JSONObject();
		try {
			json.put("username", username);
			json.put("password", password);
			json.put("gender", isMale ? "1" : "0");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		HttpBean h = new HttpBean();
		h.setJson(json);
		h.setMethod(HttpMethod.POST);
		h.setUrl(Constants.UPDATE_URL + "/" + userId);
		h.setType(HttpJob.TYPE_UPDATE);

		HttpJob job = new HttpJob();
		job.setContext(getActivity());
		job.execute(h);
	}

}
