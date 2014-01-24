package me.promenade.pandora.bean;

import android.graphics.Bitmap;

public class Chat {

	private String message;
	private long timestamp;
	private boolean isRemote;
	private SendStatus sendStatus;
	private MessageType messageType;
	private Bitmap sendPhoto;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isRemote() {
		return isRemote;
	}

	public void setRemote(boolean isRemote) {
		this.isRemote = isRemote;
	}

	public SendStatus getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(SendStatus sendStatus) {
		this.sendStatus = sendStatus;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public Bitmap getSendPhoto() {
		return sendPhoto;
	}

	public void setSendPhoto(Bitmap sendPhoto) {
		this.sendPhoto = sendPhoto;
	}

}
