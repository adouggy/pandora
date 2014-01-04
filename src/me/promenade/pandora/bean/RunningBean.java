package me.promenade.pandora.bean;

import java.util.ArrayList;

public enum RunningBean {
	INSTANCE;
	
	private ArrayList<Vibration> vData = null;
	
	RunningBean(){
	}
	
	public ArrayList<Vibration> getVibration(){
		if( vData == null )
			vData = initVibrateData();
		
		return vData;
	}
	
	private ArrayList<Vibration> initVibrateData() {
		ArrayList<Vibration> list = new ArrayList<Vibration>();
		Vibration v1 = new Vibration();
		Vibration v2 = new Vibration();
		Vibration v3 = new Vibration();
		Vibration v4 = new Vibration();
		Vibration v5 = new Vibration();
		Vibration v6 = new Vibration();
		Vibration v7 = new Vibration();
		Vibration v8 = new Vibration();
		Vibration v9 = new Vibration();
		Vibration v10 = new Vibration();
		Vibration v11 = new Vibration();

		v1.setPattern(new int[] { 0, 1, 2, 3, 4, 5, 4, 3, 2, 1, 0, 1, 2, 3 });
		v2.setPattern(new int[] { 1, 3, 2, 1, 3, 2, 1, 3, 2, 1, 3, 2, 1, 3 });
		v3.setPattern(new int[] { 2, 1, 2, 3, 4, 5, 0, 3, 2, 1, 0, 1, 2, 3 });
		v4.setPattern(new int[] { 3, 1, 2, 3, 4, 0, 4, 3, 2, 1, 0, 8, 2, 3 });
		v5.setPattern(new int[] { 4, 1, 2, 3, 0, 5, 4, 3, 2, 1, 7, 1, 2, 3 });
		v6.setPattern(new int[] { 0, 1, 2, 0, 4, 5, 4, 3, 2, 6, 0, 1, 2, 3 });
		v7.setPattern(new int[] { 0, 1, 2, 0, 4, 5, 4, 3, 2, 6, 0, 1, 2, 3 });
		v8.setPattern(new int[] { 0, 1, 2, 0, 4, 5, 4, 3, 2, 6, 0, 1, 2, 3 });
		v9.setPattern(new int[] { 0, 1, 2, 0, 4, 5, 4, 3, 2, 6, 0, 1, 2, 3 });
		v10.setPattern(new int[] { 0, 1, 2, 0, 4, 5, 4, 3, 2, 6, 0, 1, 2, 3 });
		v11.setPattern(new int[] { 0, 1, 2, 0, 4, 5, 4, 3, 2, 6, 0, 1, 2, 3 });

		list.add(v1);
		list.add(v2);
		list.add(v3);
		list.add(v4);
		list.add(v5);
		list.add(v6);
		list.add(v7);
		list.add(v8);
		list.add(v9);
		list.add(v10);
		list.add(v11);

		String[] title = { "Gentle Touch", "Soft Lick", "Whispering", "Tickle", "Flick", "Deep Kiss", "Ocean Wave", "Mountain Top", "Summer Air", "Shooting Star", "Rock Me Hard" };
		for (int i = 0; i < title.length; i++) {
			list.get(i).setIndex(i + 1);
			list.get(i).setTitle(title[i]);
		}

		return list;
	}
}
