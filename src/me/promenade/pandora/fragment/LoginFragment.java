package me.promenade.pandora.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import me.promenade.pandora.HolderActivity;
import me.promenade.pandora.R;
import me.promenade.pandora.asynjob.HttpJob;
import me.promenade.pandora.bean.HttpBean;
import me.promenade.pandora.bean.HttpMethod;
import me.promenade.pandora.util.Constants;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;

public class LoginFragment extends SherlockFragment implements OnClickListener {
	public static final String TAG = "LoginFragment";
	
	private EditText mNick = null;
	private EditText mPassword = null;
	private Button mLogin = null;
	private Button mSignup = null;
	
	private Context mContext = null;

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login,
				container,
				false);
		
		mContext = getActivity();
		
		mNick = (EditText) view.findViewById(R.id.edt_login_nickname);
		mPassword = (EditText) view.findViewById(R.id.edt_login_password);
		
		mLogin = (Button) view.findViewById(R.id.btn_login);
		mSignup = (Button) view.findViewById(R.id.btn_signup);
		
		mLogin.setOnClickListener(this);
		mSignup.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(
			View v) {
		switch( v.getId() ){
		case R.id.btn_login:
			String username = this.mNick.getText().toString();
			String password = this.mPassword.getText().toString();
			
			JSONObject j = new JSONObject();
			try {
				j.put("username", username);
				j.put("password", password);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			HttpBean h = new HttpBean();
			h.setJson(j);
			h.setMethod(HttpMethod.POST);
			h.setUrl(Constants.LOGIN_URL);
			
			HttpJob job = new HttpJob();
			job.execute(h);
			
			
			break;
		case R.id.btn_signup:
			Intent i = new Intent(mContext, HolderActivity.class);
			Bundle b = new Bundle();
			b.putInt("fragment", HolderActivity.FRAGMENT_SIGNUP);
			b.putString("title", "注册");
			i.putExtras(b);
			mContext.startActivity(i);
			break;
		}
	}

}
