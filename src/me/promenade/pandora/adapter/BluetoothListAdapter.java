package me.promenade.pandora.adapter;

import java.util.ArrayList;

import me.promenade.pandora.R;
import me.promenade.pandora.bean.Bluetooth;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class BluetoothListAdapter extends BaseAdapter {
	public static final String TAG = "BluetoothListAdapter";

	private ArrayList<Bluetooth> mList;
	private static LayoutInflater mInflater = null;

	public BluetoothListAdapter(Context ctx) {
		mInflater = LayoutInflater.from(ctx);
	}

	public void setData(
			ArrayList<Bluetooth> list) {
		this.mList = list;
	}
	
	public void addData(Bluetooth b){
		this.mList.add(b);
		this.notifyDataSetChanged();
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
			convertView = mInflater.inflate(R.layout.item_bluetooth_list,
					null);
			holder.name = (TextView) convertView.findViewById(R.id.txt_bluetooth_name);
			holder.play = (Button) convertView.findViewById(R.id.btn_connect_bluetooth);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Bluetooth b = mList.get(position);
		holder.name.setText(b.getName());
		holder.play.setTag(b);
		holder.play.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(
					View view) {
//				Bluetooth b= (Bluetooth) view.getTag();
				
			}
		});

		return convertView;
	}

	static final class ViewHolder {
		TextView name;
		Button play;
	}
}
