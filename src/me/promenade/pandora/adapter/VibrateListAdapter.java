package me.promenade.pandora.adapter;

import java.util.ArrayList;

import me.promenade.pandora.R;
import me.promenade.pandora.asynjob.VibrateJob;
import me.promenade.pandora.bean.Vibration;
import me.promenade.pandora.view.MyVibrateView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VibrateListAdapter extends BaseAdapter {
	public static final String TAG = "VibrateListAdapter";

	private ArrayList<Vibration> mList;
	private static LayoutInflater mInflater = null;
	int color1, color2;

	public VibrateListAdapter(Context ctx) {
		mInflater = LayoutInflater.from(ctx);
		color1 = ctx.getResources().getColor( R.color.dodgerblue_trans );
		color2 = ctx.getResources().getColor( R.color.midnightblue_trans );
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
			holder.play = (ImageView) convertView.findViewById(R.id.img_play_v);
			holder.index = (TextView) convertView.findViewById(R.id.txt_vibrate_index);
			holder.title = (TextView) convertView.findViewById(R.id.txt_vibrate_title);
			holder.layout = (LinearLayout) convertView.findViewById(R.id.layout_v_list);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Vibration v = mList.get(position);
		holder.vibrateView.setData(v.getPattern());
		holder.vibrateView.setVibrateKey( "vibrate" + position );
		holder.play.setTag(v.getIndex()-1);
		holder.index.setText(v.getIndex() + "");
		holder.title.setText(v.getTitle());
		
		if( position%2 == 0 ){
			holder.layout.setBackgroundColor( color1 );
		}else{
			holder.layout.setBackgroundColor( color2 );
		}

		holder.play.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(
					View view) {
				VibrateJob j = new VibrateJob();
				j.execute((Integer)view.getTag(), VibrateJob.WITHOUT_UI);

			}
		});

		return convertView;
	}

	static final class ViewHolder {
		LinearLayout layout;
		TextView index;
		TextView title;
		MyVibrateView vibrateView;
		ImageView play;
	}
}
