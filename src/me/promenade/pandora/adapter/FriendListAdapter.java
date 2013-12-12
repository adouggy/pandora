package me.promenade.pandora.adapter;

import java.util.ArrayList;

import me.promenade.pandora.HolderActivity;
import me.promenade.pandora.R;
import me.promenade.pandora.bean.Friend;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendListAdapter extends BaseAdapter {

	private ArrayList<Friend> mList;
	private static LayoutInflater mInflater = null;
	private Context mContext = null;

	public FriendListAdapter(Context ctx) {
		mInflater = LayoutInflater.from(ctx);
		mContext = ctx;
	}

	public void addFriend(
			Friend c) {
		this.mList.add(c);
		this.notifyDataSetChanged();
	}

	public void setData(
			ArrayList<Friend> list) {
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

		Friend f = mList.get(position);

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_friend_list,
					null);
			holder.username = (TextView) convertView.findViewById(R.id.txt_friend_name);
			holder.personImage = (ImageView) convertView.findViewById(R.id.img_friend_img);
			holder.chat = (Button) convertView.findViewById(R.id.btn_friend_chat);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.username.setText(f.getUsername());
		holder.chat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(
					View v) {
				Intent i = new Intent(mContext, HolderActivity.class);
				Bundle b = new Bundle();
				b.putInt("fragment", HolderActivity.FRAGMENT_CHAT);
				i.putExtras(b);
				mContext.startActivity(i);
			}
		});

		return convertView;
	}

	static final class ViewHolder {
		ImageView personImage;
		TextView username;
		Button chat;
	}
}
