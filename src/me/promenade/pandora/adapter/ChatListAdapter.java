package me.promenade.pandora.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import me.promenade.pandora.R;
import me.promenade.pandora.bean.Chat;
import me.promenade.pandora.bean.MessageType;
import me.promenade.pandora.util.ChatUtil;
import me.promenade.pandora.util.Constants;
import me.promenade.pandora.util.ImageUtil;
import me.promenade.pandora.util.SharedPreferenceUtil;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	private Bitmap userBmp = null;
	private Bitmap partnerBmp = null;

	public ChatListAdapter(Context ctx) {
		mInflater = LayoutInflater.from(ctx);
		mFormatter = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT);

		String userPhotoStr = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_USER_PHOTO);
		String partnerPhotoStr = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_PARTNER_PHOTO);
		if (userPhotoStr != null && userPhotoStr.length() > 0) {
			userBmp = ImageUtil.INSTANCE.String2Bitmap(userPhotoStr);
		}
		if (partnerPhotoStr != null && partnerPhotoStr.length() > 0) {
			partnerBmp = ImageUtil.INSTANCE.String2Bitmap(partnerPhotoStr);
		}
		
		if( userBmp == null ){
			userBmp = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.empty_profile);
		}
		if( partnerBmp == null ){
			partnerBmp = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.empty_profile);
		}
		
		mList = ChatUtil.INSTANCE.retrieve();
		if( mList == null ){
			mList = new ArrayList<Chat>();
		}else{
			notifyDataSetChanged();
		}
	}

	public void addChat(Chat c) {
		this.mList.add(c);
		this.notifyDataSetChanged();
		
		ChatUtil.INSTANCE.store(mList);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		Chat c = mList.get(position);

		// if (convertView == null) {
		holder = new ViewHolder();

		if (c.isRemote()) {
			convertView = mInflater.inflate(R.layout.item_chat_list, null);
		} else {
			convertView = mInflater
					.inflate(R.layout.item_chat_list_right, null);
		}

		holder.personImage = (ImageView) convertView
				.findViewById(R.id.img_chat_person);
		holder.message = (TextView) convertView.findViewById(R.id.txt_chat_msg);
		holder.sendStatus = (TextView) convertView
				.findViewById(R.id.txt_chat_send_status);
		holder.timeStamp = (TextView) convertView
				.findViewById(R.id.txt_chat_timestamp);
		holder.sendImage = (ImageView) convertView.findViewById(R.id.img_chat_photo);
		convertView.setTag(holder);

		holder.sendStatus.setText(c.getSendStatus().toString());
		holder.timeStamp.setText(mFormatter.format(c.getTimestamp()));
		
		if (c.isRemote()) {
			holder.personImage.setImageBitmap(partnerBmp);
		} else {
			holder.personImage.setImageBitmap(userBmp);
		}
		
		if( c.getMessageType() == MessageType.Image ){
			holder.sendImage.setVisibility(View.VISIBLE);
			holder.message.setVisibility(View.GONE);
			String bitmapStr = c.getSendPhoto();
			
			holder.sendImage.setImageBitmap( ImageUtil.INSTANCE.String2Bitmap(bitmapStr) );
		}else if (c.getMessageType() == MessageType.Message){
			holder.sendImage.setVisibility(View.GONE);
			holder.message.setVisibility(View.VISIBLE);
			
			holder.message.setText(c.getMessage());
		}else if( c.getMessageType() == MessageType.Command ){
			holder.sendImage.setVisibility(View.GONE);
			holder.message.setVisibility(View.VISIBLE);
			
			holder.message.setText(c.getMessage());
		}

		return convertView;
	}

	static final class ViewHolder {
		ImageView personImage;
		TextView message;
		TextView sendStatus;
		TextView timeStamp;
		ImageView sendImage;
	}
}
