package me.promenade.pandora.fragment;

import java.util.ArrayList;

import me.promenade.pandora.HolderActivity;
import me.promenade.pandora.R;
import me.promenade.pandora.adapter.StringListAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class MoreFragment extends SherlockFragment implements OnItemClickListener {
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

		StringListAdapter profileAdapter = new StringListAdapter(getActivity(), getProfile());
		StringListAdapter aboutAdapter = new StringListAdapter(getActivity(), getAbout());
		StringListAdapter loginAdapter = new StringListAdapter(getActivity(), getLogin());
		
		
		mProfileList.setAdapter(profileAdapter);
		mAboutList.setAdapter(aboutAdapter);
		mLoginList.setAdapter(loginAdapter);
		
		mProfileList.setOnItemClickListener(this);
		mAboutList.setOnItemClickListener(this);
		mLoginList.setOnItemClickListener(this);

		return view;
	}
	
	private ArrayList<String> getProfile()
    {
		ArrayList<String> list = new ArrayList<String>();
        list.add("我的资料");
        return list;
    }
	
	private ArrayList<String> getAbout(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("使用指南");
		list.add("分享给朋友");
		list.add("意见反馈");
//		list.add("评价我们的应用");
		list.add("检查更新");
//		list.add("提示设定");
//		list.add("版权信息");
		list.add("版本信息");
		return list;
	}
	
	private ArrayList<String> getLogin(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("登出");
		return list;
	}

	@Override
	public void onItemClick(
			AdapterView<?> av,
			View view,
			int arg2,
			long position) {
//		Toast.makeText(getActivity(), "pressed" + position, Toast.LENGTH_SHORT).show();
		switch( av.getId() ){
		case R.id.list_user_profile:
//			Toast.makeText(getActivity(), "profile", Toast.LENGTH_SHORT).show();
			if( position == 0 ){
				Intent i = new Intent( getActivity(), HolderActivity.class );
				i.putExtra("fragment", HolderActivity.FRAGMENT_PROFILE);
				i.putExtra("title", "我的账户");
				getActivity().startActivity(i);
			}
			break;
		case R.id.list_about:
//			Toast.makeText(getActivity(), "about", Toast.LENGTH_SHORT).show();
			break;
		case R.id.list_login:
//			Toast.makeText(getActivity(), "login", Toast.LENGTH_SHORT).show();
			break;
		}
	}

}