package me.promenade.pandora.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import me.promenade.pandora.R;
import me.promenade.pandora.bean.Chat;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatListAdapter extends BaseAdapter {

	private ArrayList<Chat> mList;
	private static LayoutInflater mInflater = null;
	private DateFormat mFormatter = null;

	public ChatListAdapter(Context ctx) {
		mInflater = LayoutInflater.from(ctx);
		mFormatter = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT);// SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT,
																				// SimpleDateFormat.SHORT);
	}
	
	public void addChat( Chat c ){
		this.mList.add(c);
		this.notifyDataSetChanged();
	}

	public void setData(
			ArrayList<Chat> list) {
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

		Chat c = mList.get(position);

		// if (convertView == null) {
		holder = new ViewHolder();

		if (c.isRemote()) {
			convertView = mInflater.inflate(R.layout.item_chat_list,
					null);
		} else {
			convertView = mInflater.inflate(R.layout.item_chat_list_right,
					null);
		}

		holder.personImage = (ImageView) convertView.findViewById(R.id.img_chat_person);
		holder.message = (TextView) convertView.findViewById(R.id.txt_chat_msg);
		holder.sendStatus = (TextView) convertView.findViewById(R.id.txt_chat_send_status);
		holder.timeStamp = (TextView) convertView.findViewById(R.id.txt_chat_timestamp);
		convertView.setTag(holder);
		// } else {
		// holder = (ViewHolder) convertView.getTag();
		// }

		holder.message.setText(c.getMessage());
		holder.sendStatus.setText(c.getSendStatus().toString());
		holder.timeStamp.setText(mFormatter.format(c.getTimestamp()));

		return convertView;
	}

	static final class ViewHolder {
		ImageView personImage;
		TextView message;
		TextView sendStatus;
		TextView timeStamp;
	}
}
