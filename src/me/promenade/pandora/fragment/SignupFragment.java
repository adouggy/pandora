package me.promenade.pandora.fragment;

import me.promenade.pandora.R;
import me.promenade.pandora.ShouldKnowActivity;
import me.promenade.pandora.asynjob.HttpJob;
import me.promenade.pandora.bean.HttpBean;
import me.promenade.pandora.bean.HttpMethod;
import me.promenade.pandora.util.Constants;
import me.promenade.pandora.util.EmailUtil;
import me.promenade.pandora.util.NameUtil;
import me.promenade.pandora.util.SharedPreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class SignupFragment extends SherlockFragment implements OnClickListener {
	public static final String TAG = "SignupFragment";

	private EditText mNick = null;
	private EditText mPassword = null;
	private EditText mEmail = null;
//	private DatePicker mBirthday = null;
	private RadioButton mMale = null;
	// private RadioButton mFemale = null;
	private Button mSubmit = null;

	private CheckBox mCheck = null;
	private TextView mShouldKnow = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_signup, container, false);

		mNick = (EditText) view.findViewById(R.id.edt_signup_nickname);
		mPassword = (EditText) view.findViewById(R.id.edt_signup_password);
		mEmail = (EditText) view.findViewById(R.id.edt_signup_email);

//		mBirthday = (DatePicker) view.findViewById(R.id.dp_signup);

		mMale = (RadioButton) view.findViewById(R.id.radio_signup_male);
		// mFemale = (RadioButton) view.findViewById(R.id.radio_signup_female);

		mSubmit = (Button) view.findViewById(R.id.btn_signup_submit);

		this.mSubmit.setOnClickListener(this);

//		final Calendar c = Calendar.getInstance();
//		int year = c.get(Calendar.YEAR);
//		int month = c.get(Calendar.MONTH);
//		int day = c.get(Calendar.DAY_OF_MONTH);

//		mBirthday.init(year, month, day, null);

//		mBirthday.setVisibility(View.GONE);
		mEmail.setVisibility(View.GONE);

		mCheck = (CheckBox) view.findViewById(R.id.chk_should_know);
		mShouldKnow = (TextView) view.findViewById(R.id.txt_should_know_check);

		mShouldKnow.setOnClickListener(this);

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_signup_submit:
			if( mCheck == null || !mCheck.isChecked() ){
				Toast.makeText(getActivity(), "请先阅读并同意条款与隐私策略再继续注册",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if (!EmailUtil.isEmailValid(this.mNick.getText().toString())) {
				Toast.makeText(getActivity(), "请输入合法的Email地址",
						Toast.LENGTH_SHORT).show();
				break;
			}

			String username = NameUtil.INSTANCE.parseName(this.mNick.getText()
					.toString());
			String password = this.mPassword.getText().toString();
			// String email = this.mEmail.getText().toString();

			// int year = this.mBirthday.getYear();
			// int month = this.mBirthday.getMonth();
			// int day = this.mBirthday.getDayOfMonth();
			// long birthday = new GregorianCalendar(year, month,
			// day).getTimeInMillis();

			String gender = "0";
			if (mMale.isChecked()) {
				gender = "1";
			} else {
				gender = "0";
			}

			JSONObject j = new JSONObject();
			try {
				j.put("username", username);
				j.put("password", password);
				j.put("email", username);
				j.put("name", username);
				j.put("birthday", System.currentTimeMillis());
				j.put("gender", gender);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			SharedPreferenceUtil.INSTANCE.setData(Constants.SP_USER_NAME,
					username);
			SharedPreferenceUtil.INSTANCE.setData(Constants.SP_USER_PASSWORD,
					password);

			HttpBean bean = new HttpBean();
			bean.setJson(j);
			bean.setMethod(HttpMethod.POST);
			bean.setUrl(Constants.REGISTER_URL);
			bean.setType(HttpJob.TYPE_REGISTER);
			HttpJob job = new HttpJob();
			job.setContext(getActivity());
			job.execute(bean);

			break;
		case R.id.txt_should_know_check:
			Intent i = new Intent(getActivity(), ShouldKnowActivity.class);
			getActivity().startActivity(i);
			break;
		}
	}

}
