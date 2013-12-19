package me.promenade.pandora.fragment;

import java.util.ArrayList;

import me.promenade.pandora.HolderActivity;
import me.promenade.pandora.R;
import me.promenade.pandora.adapter.FantasyListAdapter;
import me.promenade.pandora.bean.Fantasy;
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

		mFantasyList = initFantasy();

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

	private ArrayList<Fantasy> initFantasy() {
		ArrayList<Fantasy> list = new ArrayList<Fantasy>();

		Fantasy f1 = new Fantasy();
		f1.setTime(System.currentTimeMillis());
		f1.setTitle("airplane");
		f1.setDescription("非自由~~飞飞~非自由~~");
		f1.setLogoId(R.drawable.img_airplane_logo);
		f1.setImageId(R.drawable.img_airplane);
		f1.setMusicId(R.raw.amb_airplane);
		list.add(f1);

		Fantasy f2 = new Fantasy();
		f2.setTime(System.currentTimeMillis());
		f2.setTitle("beach");
		f2.setDescription("blah..xxx");
		f2.setLogoId(R.drawable.img_beach_logo);
		f2.setImageId(R.drawable.img_beach);
		f2.setMusicId(R.raw.amb_beach);
		list.add(f2);
		
		Fantasy f3 = new Fantasy();
		f3.setTime(System.currentTimeMillis());
		f3.setTitle("breathing");
		f3.setDescription("吐息了……速跑");
		f3.setLogoId(R.drawable.img_breathing_logo);
		f3.setImageId(R.drawable.img_breathing);
		f3.setMusicId(R.raw.amb_breathing);
		list.add(f3);
		
		Fantasy f4 = new Fantasy();
		f4.setTime(System.currentTimeMillis());
		f4.setTitle("camping");
		f4.setDescription("身在曹营心在汉");
		f4.setLogoId(R.drawable.img_camping_logo);
		f4.setImageId(R.drawable.img_camping);
		f4.setMusicId(R.raw.amb_camping);
		list.add(f4);
		
		Fantasy f5 = new Fantasy();
		f5.setTime(System.currentTimeMillis());
		f5.setTitle("park at night");
		f5.setDescription("悄悄地，打枪的不要");
		f5.setLogoId(R.drawable.img_park_at_night_logo);
		f5.setImageId(R.drawable.img_park_at_night);
		f5.setMusicId(R.raw.amb_park_at_night);
		list.add(f5);
		
		Fantasy f6 = new Fantasy();
		f6.setTime(System.currentTimeMillis());
		f6.setTitle("rain");
		f6.setDescription("雨一直下，气氛不太融洽……");
		f6.setLogoId(R.drawable.img_rain_logo);
		f6.setImageId(R.drawable.img_rain);
		f6.setMusicId(R.raw.amb_rain);
		list.add(f6);
		
		Fantasy f7 = new Fantasy();
		f7.setTime(System.currentTimeMillis());
		f7.setTitle("waterfall");
		f7.setDescription("我去……瀑布！");
		f7.setLogoId(R.drawable.img_waterfall_logo);
		f7.setImageId(R.drawable.img_waterfall);
		f7.setMusicId(R.raw.amb_waterfall);
		list.add(f7);
		
		Fantasy f8 = new Fantasy();
		f8.setTime(System.currentTimeMillis());
		f8.setTitle("woods");
		f8.setDescription("老虎|伍兹");
		f8.setLogoId(R.drawable.img_woods_logo);
		f8.setImageId(R.drawable.img_woods);
		f8.setMusicId(R.raw.amb_woods);
		list.add(f8);

		return list;
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
