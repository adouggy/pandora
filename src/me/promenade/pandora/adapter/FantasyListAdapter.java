package me.promenade.pandora.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.promenade.pandora.HolderActivity;
import me.promenade.pandora.R;
import me.promenade.pandora.bean.Fantasy;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FantasyListAdapter extends BaseAdapter {
	public static final String TAG = "FantasyListAdapter";

	private ArrayList<Fantasy> mList;
	private static LayoutInflater mInflater = null;
	 private Context mContext = null;
	private DateFormat format = SimpleDateFormat.getDateTimeInstance();

	public FantasyListAdapter(Context ctx) {
		mInflater = LayoutInflater.from(ctx);
		 mContext = ctx;
	}

	public void setData(
			ArrayList<Fantasy> list) {
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

		Fantasy f = mList.get(position);

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_fantasy_list,
					null);
			holder.fantasyLogo = (ImageView) convertView.findViewById(R.id.img_fantasy_logo);
			holder.time = (TextView) convertView.findViewById(R.id.txt_fantasy_time);
			holder.title = (TextView) convertView.findViewById(R.id.txt_fantasy_title);
			holder.description = (TextView) convertView.findViewById(R.id.txt_fantasy_description);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.time.setText(format.format(new Date(f.getTime())));
		holder.title.setText(f.getTitle());
		holder.description.setText(f.getDescription());

		holder.fantasyLogo.setImageResource(f.getLogoId());
		holder.fantasyLogo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(
					View v) {
				Intent i = new Intent(mContext, HolderActivity.class);
				Bundle b = new Bundle();
				b.putInt("fragment", HolderActivity.FRAGMENT_FANTASY);
				i.putExtras(b);
				mContext.startActivity( i );
			}
		});
		return convertView;
	}

	static final class ViewHolder {
		ImageView fantasyLogo;
		TextView time;
		TextView title;
		TextView description;
	}
}
