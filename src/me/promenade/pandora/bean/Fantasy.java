package me.promenade.pandora.bean;

public class Fantasy {

	private long time;
	
	private String title;
	
	private String description;
	
	private int logoId;
	
	private int imageId;
	
	private int musicId;

	public long getTime() {
		return time;
	}

	public void setTime(
			long time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(
			String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(
			String description) {
		this.description = description;
	}

	public int getLogoId() {
		return logoId;
	}

	public void setLogoId(
			int logoId) {
		this.logoId = logoId;
	}

	public int getMusicId() {
		return musicId;
	}

	public void setMusicId(
			int musicId) {
		this.musicId = musicId;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(
			int imageId) {
		this.imageId = imageId;
	}
}
