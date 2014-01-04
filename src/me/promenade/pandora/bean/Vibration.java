package me.promenade.pandora.bean;

public class Vibration {
	private int index;
	private String title;
	private int[] pattern;

	public int[] getPattern() {
		return pattern;
	}

	public void setPattern(
			int[] pattern) {
		this.pattern = pattern;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(
			int index) {
		this.index = index;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(
			String title) {
		this.title = title;
	}
	
	
}
