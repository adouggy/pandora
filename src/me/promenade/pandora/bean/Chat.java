package me.promenade.pandora.bean;

import java.io.Serializable;

import android.graphics.Bitmap;

public class Chat implements Serializable {

	private static final long serialVersionUID = -1232923641636978760L;
	private String message;
	private long timestamp;
	private boolean isRemote;
	private SendStatus sendStatus;
	private MessageType messageType;
	private String sendPhotoStr;

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

	public String getSendPhoto() {
		return sendPhotoStr;
	}

	public void setSendPhoto(String sendPhoto) {
		this.sendPhotoStr = sendPhoto;
	}

	@Override
	public String toString() {
		return "[" + message + "" + timestamp + "," + isRemote + ","
				+ sendStatus + "," + messageType + "," + sendPhotoStr + "]";
	}

}
