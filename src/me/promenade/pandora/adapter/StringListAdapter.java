package me.promenade.pandora.adapter;

import java.util.ArrayList;

import me.promenade.pandora.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StringListAdapter extends BaseAdapter {

	private ArrayList<String> mList;
	private static LayoutInflater mInflater = null;

	public StringListAdapter(Context ctx, ArrayList<String> list) {
		mInflater = LayoutInflater.from(ctx);
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
			convertView = mInflater.inflate(R.layout.item_more_list,
					null);
			holder.item = (TextView) convertView.findViewById(R.id.txt_more_list_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String s = mList.get(position);
		holder.item.setText( s );
		return convertView;
	}

	static final class ViewHolder {
		TextView item;
	}
}
