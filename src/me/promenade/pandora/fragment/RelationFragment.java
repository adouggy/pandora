package me.promenade.pandora.fragment;

import me.promenade.pandora.R;
import me.promenade.pandora.asynjob.GetPhotoJob;
import me.promenade.pandora.bean.Profile;
import me.promenade.pandora.util.ImageUtil;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class RelationFragment extends SherlockFragment implements OnClickListener {
	public static final String TAG = "RelationFragment";

	private static TextView mNick;
	private static ImageView mGender;
	private static ImageView mPhoto;

	private RelativeLayout mLayoutNick;
	private RelativeLayout mLayoutGender;
	private RelativeLayout mLayoutPhoto;
	private RelativeLayout mLayoutRelation;

	public static Profile mProfile;
	
	public static Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			String imgStr = msg.getData().getString("photo");
			if( imgStr != null ){
				Bitmap bmp = ImageUtil.INSTANCE.String2Bitmap(imgStr);
				mPhoto.setImageBitmap(bmp);
				if( mProfile!=null ){
					mProfile.setPhoto(bmp);
				}
			}
			
		};
	};

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile,
				container,
				false);
		
		GetPhotoJob job = new GetPhotoJob();
		job.execute(1);

		mNick = (TextView) view.findViewById(R.id.txt_profile_nickname);
		mGender = (ImageView) view.findViewById(R.id.img_profile_gender);
		mPhoto = (ImageView) view.findViewById(R.id.img_profile_photo);

		mLayoutNick = (RelativeLayout) view.findViewById(R.id.layout_profile_nick);
		mLayoutGender = (RelativeLayout) view.findViewById(R.id.layout_profile_gender);
		mLayoutPhoto = (RelativeLayout) view.findViewById(R.id.layout_profile_photo);
		mLayoutRelation = (RelativeLayout) view.findViewById(R.id.layout_profile_relation);

		mLayoutNick.setOnClickListener(this);
		mLayoutGender.setOnClickListener(this);
		mLayoutPhoto.setOnClickListener(this);
		mLayoutRelation.setOnClickListener(this);
		
		Profile p = new Profile();
		p.setNickName("xxx");
		p.setMale(true);
		mProfile = p;
		refreshProfile(p);
				
		return view;
	}

	public void refreshProfile(
			Profile p) {
		mNick.setText(p.getNickName());
		if (p.isMale()) {
			mGender.setImageResource(R.drawable.male_head_loading);
		} else {
			mGender.setImageResource(R.drawable.female_head_loading);
		}
		mPhoto.setImageBitmap(p.getPhoto());
	}

	@Override
	public void onClick(
			View v) {
		switch (v.getId()) {
		case R.id.layout_profile_nick:

			break;
		case R.id.layout_profile_gender:
			Dialog alertDialog = new AlertDialog.Builder(getActivity()).setTitle("性别").setMessage("请选择性别").setIcon(R.drawable.ic_launcher).setPositiveButton("男",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(
								DialogInterface arg0,
								int arg1) {
							mProfile.setMale(true);
							refreshProfile(mProfile);
						}

					}).setNegativeButton("女",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(
								DialogInterface dialog,
								int which) {
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
			startActivityForResult(intent,
					1);
			break;
		case R.id.layout_profile_relation:
			break;
		}
	}

}
