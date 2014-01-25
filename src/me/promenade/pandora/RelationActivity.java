package me.promenade.pandora;

import me.promenade.pandora.asynjob.GetPhotoJobForRelation;
import me.promenade.pandora.asynjob.RemovePartnerJob;
import me.promenade.pandora.bean.RunningBean;
import me.promenade.pandora.util.ImageUtil;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class RelationActivity extends SherlockActivity implements OnClickListener {

	private static ImageView mMalePhoto;
	private static ImageView mFemalePhoto;
	private static TextView mMaleName;
	private static TextView mFemaleName;
	private static Button mUnRelation;

	public static final int WHAT_REFRESH_PHOTO = 1;
	public static final int WHAT_PARTNER_REMOVED = 2;

	public static Handler mHandler = new Handler() {
		public void handleMessage(
				android.os.Message msg) {
			switch (msg.what) {
			case WHAT_REFRESH_PHOTO:
				boolean isMale = msg.getData().getBoolean("isMale");
				String imgStr = msg.getData().getString("photo");
				if (imgStr != null) {
					Bitmap bmp = ImageUtil.INSTANCE.String2Bitmap(imgStr);
					if (isMale) {
						mMalePhoto.setImageBitmap(bmp);
					} else {
						mFemalePhoto.setImageBitmap(bmp);
					}
				}
				break;
			case WHAT_PARTNER_REMOVED:
				
				break;
			}
		};
	};

	@Override
	protected void onCreate(
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_relation);
		
		getSupportActionBar().show();
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffdb2672));

		mMalePhoto = (ImageView) findViewById(R.id.img_relation_male);
		mFemalePhoto = (ImageView) findViewById(R.id.img_relation_female);

		mMaleName = (TextView) findViewById(R.id.txt_relation_male_name);
		mFemaleName = (TextView) findViewById(R.id.txt_relation_female_name);

		mUnRelation = (Button) findViewById(R.id.btn_un_relation);

		int userId = RunningBean.INSTANCE.getUserId();
		int partnerId = RunningBean.INSTANCE.getPartnerId();
		if (userId != -1 && partnerId != -1) {
			GetPhotoJobForRelation job1 = new GetPhotoJobForRelation();
			GetPhotoJobForRelation job2 = new GetPhotoJobForRelation();
			job1.setContext(this);
			job2.setContext(this);
			job1.execute(userId,
					1);
			job2.execute(partnerId,
					0);
			
			mMaleName.setText(RunningBean.INSTANCE.getUserName());
			mFemaleName.setText(RunningBean.INSTANCE.getPartnerName());
		} else {
			Toast.makeText(this,
					"请先选择您的伙伴",
					Toast.LENGTH_LONG).show();
			finish();
		}
		

		mUnRelation.setOnClickListener(this);
	}

	@Override
	public void onClick(
			View v) {
		RemovePartnerJob job = new RemovePartnerJob();
		
		JSONObject j = new JSONObject();
		try {
			j.put("userId", RunningBean.INSTANCE.getUserId());
			j.put("username", RunningBean.INSTANCE.getUserName());
			j.put("password", RunningBean.INSTANCE.getUserPassword());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		job.execute(j);
		job.setContext(this);
		finish();
	}

}
