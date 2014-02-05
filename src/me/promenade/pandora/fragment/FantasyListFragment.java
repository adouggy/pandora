package me.promenade.pandora.fragment;

import java.util.ArrayList;
import java.util.TreeMap;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class FantasyListFragment extends SherlockFragment implements OnItemClickListener, OnItemLongClickListener {
	public static final String TAG = "FantasyListFragment";

	private static ListView mList = null;
	public static FantasyListAdapter mAdapter = null;

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {
		ArrayList<Fantasy> list = new ArrayList<Fantasy>();
		
//		(ArrayList<Fantasy>) RunningBean.INSTANCE.getFantasy().clone();
		list.add( RunningBean.INSTANCE.getFantasy().get(0) );
		list.add( RunningBean.INSTANCE.getFantasy().get(1) );
		list.add( RunningBean.INSTANCE.getFantasy().get(2) );
		
		View view = inflater.inflate(R.layout.fragment_fantasy_list,
				container,
				false);
		setHasOptionsMenu(true);

		mList = (ListView) view.findViewById(R.id.list_home);
		mList.setDivider(null);

		mAdapter = new FantasyListAdapter(this.getActivity());
		mAdapter.setData(list);

		mList.setAdapter(mAdapter);

		mList.setOnItemClickListener(this);
		mList.setOnItemLongClickListener(this);

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onCreateOptionsMenu(
			Menu menu,
			MenuInflater inflater) {
		MenuItem actionItem = menu.add("新建");
		actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		super.onCreateOptionsMenu(menu,
				inflater);
	}

	@Override
	public boolean onOptionsItemSelected(
			MenuItem item) {
		final String[] arr = new String[] { "机场", "海滩", "呼吸", "露营", "公园", "细雨", "瀑布", "森林" };
		new AlertDialog.Builder(getActivity()).setTitle("选择幻想").setSingleChoiceItems(arr,
				0,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(
							DialogInterface di,
							int index) {
						String title = RunningBean.INSTANCE.getFantasy().get(index).getTitle();
						boolean exists = false;
						for (int i = 0; i < mAdapter.getCount(); i++) {
							if (((Fantasy) mAdapter.getItem(i)).getTitle().compareTo(title) == 0) {
								exists = true;
								break;
							}
						}
//
						if (exists) {
							Toast.makeText(getActivity(),
									arr[index] + "已存在",
									Toast.LENGTH_SHORT).show();
						} else {
							mAdapter.addData(index, (Fantasy)RunningBean.INSTANCE.getFantasy().get(index));
							di.dismiss();
						}
					}
				}).setNegativeButton("取消",
				null).show();

		return super.onOptionsItemSelected(item);
	}

	protected void delDialog(
			final int position) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("确认删除吗？");

		builder.setTitle("提示");

		builder.setPositiveButton("确认",
				new OnClickListener() {

					@Override
					public void onClick(
							DialogInterface dialog,
							int which) {

						mAdapter.delData(position);
						
						ArrayList<TreeMap<Integer, Integer>> list = RunningBean.INSTANCE.getFantasyData();
						list.set(position, new TreeMap<Integer, Integer>());
						RunningBean.INSTANCE.storeFantasyData(list);
						
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
			long position) {
		delDialog((int) position);
		return false;
	}

	@Override
	public void onItemClick(
			AdapterView<?> arg0,
			View arg1,
			int position,
			long id) {

		int realPosition = (int) id;

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
