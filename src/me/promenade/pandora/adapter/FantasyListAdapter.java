package me.promenade.pandora.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.promenade.pandora.R;
import me.promenade.pandora.bean.Fantasy;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FantasyListAdapter extends BaseAdapter {
	public static final String TAG = "FantasyListAdapter";

	private ArrayList<Fantasy> mList;
	private static LayoutInflater mInflater = null;
	private Context mContext = null;
	private DateFormat format = SimpleDateFormat.getDateTimeInstance();
	
	private static int color1 = -1;
	private static int color2 = -1;

	public FantasyListAdapter(Context ctx) {
		mInflater = LayoutInflater.from(ctx);
		mContext = ctx;
		
		color1 = mContext.getResources().getColor( R.color.dodgerblue_trans );
		color2 = mContext.getResources().getColor( R.color.midnightblue_trans );
	}

	public void setData(
			ArrayList<Fantasy> list) {
		this.mList = list;
		this.notifyDataSetChanged();
	}
	
	public void addData(int index, Fantasy f){
		this.mList.add(index, f);
		this.notifyDataSetChanged();
	}
	
	public void delData(int index){
		this.mList.remove(index);
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

		Fantasy f = mList.get(position);

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_fantasy_list,
					null);
			holder.fantasyLogo = (ImageView) convertView.findViewById(R.id.img_fantasy_logo);
			holder.time = (TextView) convertView.findViewById(R.id.txt_fantasy_time);
			holder.title = (TextView) convertView.findViewById(R.id.txt_fantasy_title);
			holder.description = (TextView) convertView.findViewById(R.id.txt_fantasy_description);
			holder.layout = (RelativeLayout) convertView.findViewById(R.id.layout_item_fantasy_list);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.time.setText(format.format(new Date(f.getTime())));
		holder.title.setText(f.getTitle());
		holder.description.setText(f.getDescription());
		holder.fantasyLogo.setImageResource(f.getLogoId());
		if( position%2 == 0 ){
			holder.layout.setBackgroundColor( color1 );
		}else{
			holder.layout.setBackgroundColor( color2 );
		}
		return convertView;
	}

	static final class ViewHolder {
		ImageView fantasyLogo;
		TextView time;
		TextView title;
		TextView description;
		RelativeLayout layout;
	}
}
