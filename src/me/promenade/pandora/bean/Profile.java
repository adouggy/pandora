package me.promenade.pandora.bean;

import android.graphics.Bitmap;

public class Profile {

	private String nickName;
	private boolean isMale;
	private Bitmap photo;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(
			String nickName) {
		this.nickName = nickName;
	}

	public boolean isMale() {
		return isMale;
	}

	public void setMale(
			boolean isMale) {
		this.isMale = isMale;
	}

	public Bitmap getPhoto() {
		return photo;
	}

	public void setPhoto(
			Bitmap photo) {
		this.photo = photo;
	}

}
