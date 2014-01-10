package me.promenade.pandora.fragment;

import java.util.ArrayList;

import me.promenade.pandora.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class MoreFragment extends SherlockFragment implements OnClickListener {
	public static final String TAG = "MoreFragment";

	private ListView mProfileList;
	private ListView mAboutList;
	private ListView mLoginList;

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_more,
				container,
				false);

		mProfileList = (ListView) view.findViewById(R.id.list_user_profile);
		mAboutList = (ListView) view.findViewById(R.id.list_about);
		mLoginList = (ListView) view.findViewById(R.id.list_login);

		ArrayAdapter<String> profileAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getProfile());
		ArrayAdapter<String> aboutAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getAbout());
		ArrayAdapter<String> loginAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getLogin());
		
		mProfileList.setAdapter(profileAdapter);
		mAboutList.setAdapter(aboutAdapter);
		mLoginList.setAdapter(loginAdapter);

		return view;
	}
	
	private ArrayList<String> getProfile()
    {
		ArrayList<String> list = new ArrayList<String>();
        list.add("我的资料");
        list.add("隐私设定");
        return list;
    }
	
	private ArrayList<String> getAbout(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("使用指南");
		list.add("分享给朋友");
		list.add("意见反馈");
		list.add("评价我们的应用");
		list.add("检查更新");
		list.add("提示设定");
		list.add("版权信息");
		list.add("版本信息");
		return list;
	}
	
	private ArrayList<String> getLogin(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("登入/登出");
		return list;
	}

	@Override
	public void onClick(
			View v) {

	}

}
