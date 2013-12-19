package me.promenade.pandora.adapter;

import java.util.ArrayList;

import me.promenade.pandora.R;
import me.promenade.pandora.bean.Vibration;
import me.promenade.pandora.util.VibrateUtil;
import me.promenade.pandora.view.MyVibrateView;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class VibrateListAdapter extends BaseAdapter {
	public static final String TAG = "VibrateListAdapter";

	private ArrayList<Vibration> mList;
	private static LayoutInflater mInflater = null;

	public VibrateListAdapter(Context ctx) {
		mInflater = LayoutInflater.from(ctx);
	}

	public void setData(
			ArrayList<Vibration> list) {
		this.mList = list;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(
			int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(
			int position) {
		return position;
	}

	@Override
	public View getView(
			final int position,
			View convertView,
			ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_vibrate_list,
					null);
			holder.vibrateView = (MyVibrateView) convertView.findViewById(R.id.vibrateview);
			holder.play = (Button) convertView.findViewById(R.id.btn_play_v);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Vibration v = mList.get(position);
		holder.vibrateView.setData(v.getPattern());
		holder.play.setTag(holder.vibrateView);
		
		holder.play.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(
					View view) {
				MyVibrateView vView = (MyVibrateView) view.getTag();
				
				int[] pattern = vView.getData();
				
				VibrateUtil.INSTANCE.vibrate(pattern, vView);
			}
		});

		return convertView;
	}

	static final class ViewHolder {
		MyVibrateView vibrateView;
		Button play;
	}
}
