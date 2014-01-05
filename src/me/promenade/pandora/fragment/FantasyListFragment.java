package me.promenade.pandora.fragment;

import java.util.ArrayList;

import me.promenade.pandora.HolderActivity;
import me.promenade.pandora.R;
import me.promenade.pandora.adapter.FantasyListAdapter;
import me.promenade.pandora.bean.Fantasy;
import me.promenade.pandora.bean.RunningBean;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class FantasyListFragment extends SherlockFragment implements OnItemClickListener, OnItemLongClickListener {
	public static final String TAG = "FantasyListFragment";

	private static ListView mList = null;
	private static FantasyListAdapter mAdapter = null;

	public static ArrayList<Fantasy> mFantasyList = null;

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {

		mFantasyList = RunningBean.INSTANCE.getFantasy();

		View view = inflater.inflate(R.layout.fragment_fantasy_list,
				container,
				false);
		mList = (ListView) view.findViewById(R.id.list_home);
		mList.setDivider(null);

		mAdapter = new FantasyListAdapter(this.getActivity());
		mAdapter.setData(mFantasyList);

		mList.setAdapter(mAdapter);

		mList.setOnItemClickListener(this);
		mList.setOnItemLongClickListener(this);

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	protected void dialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("确认删除吗？");

		builder.setTitle("提示");

		builder.setPositiveButton("确认",
				new OnClickListener() {

					@Override
					public void onClick(
							DialogInterface dialog,
							int which) {
						dialog.dismiss();

					}
				});

		builder.setNegativeButton("取消",
				new OnClickListener() {

					@Override
					public void onClick(
							DialogInterface dialog,
							int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();
	}

	@Override
	public boolean onItemLongClick(
			AdapterView<?> arg0,
			View arg1,
			int arg2,
			long arg3) {
		dialog();
		return false;
	}

	@Override
	public void onItemClick(
			AdapterView<?> arg0,
			View arg1,
			int position,
			long id) {
		
		int realPosition=(int)id;
		
		Log.i( TAG, realPosition + "<--" );
		
		Intent i = new Intent(getActivity(), HolderActivity.class);
		Bundle b = new Bundle();
		b.putInt("fragment",
				HolderActivity.FRAGMENT_FANTASY);
		b.putInt("position",
				realPosition);
		i.putExtras(b);
		getActivity().startActivity(i);
	}
}
