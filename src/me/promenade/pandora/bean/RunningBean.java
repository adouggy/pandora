package me.promenade.pandora.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import me.promenade.pandora.R;
import me.promenade.pandora.util.ChatUtil;
import me.promenade.pandora.util.Constants;
import me.promenade.pandora.util.SharedPreferenceUtil;

public enum RunningBean {
	INSTANCE;

	private ArrayList<Vibration> vData = null;
	private ArrayList<Fantasy> vFantasy = null;

	RunningBean() {
	}

	public void logout() {
		SharedPreferenceUtil.INSTANCE.setData(Constants.SP_USER_ID, "");
		SharedPreferenceUtil.INSTANCE.setData(Constants.SP_USER_NAME, "");
		SharedPreferenceUtil.INSTANCE.setData(Constants.SP_USER_GENDER, "");
		SharedPreferenceUtil.INSTANCE.setData(Constants.SP_USER_NICK, "");
		SharedPreferenceUtil.INSTANCE.setData(Constants.SP_USER_PASSWORD, "");
		SharedPreferenceUtil.INSTANCE.setData(Constants.SP_PARTNER_ID, "");
		SharedPreferenceUtil.INSTANCE.setData(Constants.SP_PARTNER_NAME, "");
//		SharedPreferenceUtil.INSTANCE.setData(Constants.CHAT_LOG, "");
		SharedPreferenceUtil.INSTANCE.setData(Constants.SP_USER_PHOTO, "");
		SharedPreferenceUtil.INSTANCE.setData(Constants.SP_PARTNER_PHOTO, "");
		
		ChatUtil.INSTANCE.clear();
	}

	public int getUserId() {
		String idStr = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_USER_ID);
		int id = -1;

		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return id;
	}

	public String getUserName() {
		String name = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_USER_NAME);
		return name;
	}

	public String getUserPassword() {
		String pass = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_USER_PASSWORD);
		return pass;
	}

	public int getPartnerId() {
		String idStr = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_PARTNER_ID);
		int id = -1;

		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return id;
	}

	public String getPartnerName() {
		String name = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_PARTNER_NAME);
		return name;
	}

	public ArrayList<Vibration> getVibration() {
		if (vData == null)
			vData = initVibrateData();

		return vData;
	}

	private int[] parseVibrateData(String data) {
		if (data != null && data.length() > 0) {
			String[] strArr = data.split(",");
			if (strArr.length == 14) {
				int[] dataArr = new int[14];
				for (int i = 0; i < dataArr.length; i++) {
					dataArr[i] = Integer.parseInt(strArr[i]);
				}
				return dataArr;
			}
		}
		return new int[] { 0, 1, 2, 3, 4, 5, 4, 3, 2, 1, 0, 1, 2, 3 };
	}

	public ArrayList<int[]> getVibrateData() {
		ArrayList<int[]> list = new ArrayList<int[]>();

		String s1 = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_VIBRATE_0);
		String s2 = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_VIBRATE_1);
		String s3 = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_VIBRATE_2);
		String s4 = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_VIBRATE_3);
		String s5 = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_VIBRATE_4);
		String s6 = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_VIBRATE_5);
		String s7 = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_VIBRATE_6);
		String s8 = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_VIBRATE_7);
		String s9 = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_VIBRATE_8);
		String s10 = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_VIBRATE_9);
		String s11 = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_VIBRATE_10);

		s1 = s1.compareTo("") == 0 ? "1,0,0,0,1,0,1,0,1,0,0,0,1,0" : s1;
		s2 = s2.compareTo("") == 0 ? "1,3,3,0,2,2,1,0,1,1,0,3,1,0" : s2;
		s3 = s3.compareTo("") == 0 ? "1,2,3,2,1,2,3,2,1,2,3,2,1,0" : s3;
		s4 = s4.compareTo("") == 0 ? "2,0,4,0,1,0,6,0,4,0,2,0,1,0" : s4;
		s5 = s5.compareTo("") == 0 ? "2,0,4,0,2,0,5,0,1,0,2,3,4,0" : s5;
		s6 = s6.compareTo("") == 0 ? "3,0,0,3,3,0,3,3,3,0,0,3,3,0" : s6;
		s7 = s7.compareTo("") == 0 ? "1,0,3,0,5,0,6,0,4,0,3,0,2,0" : s7;
		s8 = s8.compareTo("") == 0 ? "1,0,2,0,3,0,4,0,5,0,4,0,3,0" : s8;
		s9 = s9.compareTo("") == 0 ? "3,3,0,4,4,0,3,3,0,4,4,0,3,3" : s9;
		s10 = s10.compareTo("") == 0 ? "5,0,5,5,0,6,0,6,1,5,0,6,6,0" : s10;
		s11 = s11.compareTo("") == 0 ? "6,6,6,0,6,6,6,0,6,6,6,0,6,6" : s11;

		int[] v1 = parseVibrateData(s1);
		int[] v2 = parseVibrateData(s2);
		int[] v3 = parseVibrateData(s3);
		int[] v4 = parseVibrateData(s4);
		int[] v5 = parseVibrateData(s5);
		int[] v6 = parseVibrateData(s6);
		int[] v7 = parseVibrateData(s7);
		int[] v8 = parseVibrateData(s8);
		int[] v9 = parseVibrateData(s9);
		int[] v10 = parseVibrateData(s10);
		int[] v11 = parseVibrateData(s11);

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
		return list;
	}

	private TreeMap<Integer, Integer> parseFantasyString(String str) {
		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
		if (str != null && str.length() > 0) {
			String[] pairStr = str.split(";");
			for (String p : pairStr) {
				String[] kv = p.split(",");
				int k = Integer.parseInt(kv[0]);
				int v = Integer.parseInt(kv[1]);
				map.put(k, v);
			}
		}
		return map;
	}

	private String fantasyToString(TreeMap<Integer, Integer> f) {
		StringBuilder sb = new StringBuilder();
		Iterator<Integer> iter = f.keySet().iterator();
		while (iter.hasNext()) {
			int key = iter.next();
			int value = f.get(key);
			sb.append(key + "," + value + ";");
		}
		String str = sb.toString();

		if (str.length() > 0)
			str = str.substring(0, str.length() - 1);
		return str;
	}

	public ArrayList<TreeMap<Integer, Integer>> getFantasyData() {
		ArrayList<TreeMap<Integer, Integer>> list = new ArrayList<TreeMap<Integer, Integer>>();
		String s1 = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_FANTASY_0);
		String s2 = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_FANTASY_1);
		String s3 = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_FANTASY_2);
		String s4 = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_FANTASY_3);
		String s5 = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_FANTASY_4);
		String s6 = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_FANTASY_5);
		String s7 = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_FANTASY_6);
		String s8 = SharedPreferenceUtil.INSTANCE
				.getData(Constants.SP_FANTASY_7);

		String defaultFantasy = "0,0;10,1;20,2;30,3;40,4;50,5;60,6;70,7;80,8;90,9;100,10";
		if (s1 == null || s1.length() == 0) {
			s1 = defaultFantasy;
		}
		if (s2 == null || s2.length() == 0) {
			s2 = defaultFantasy;
		}
		if (s3 == null || s3.length() == 0) {
			s3 = defaultFantasy;
		}
		if (s4 == null || s4.length() == 0) {
			s4 = defaultFantasy;
		}
		if (s5 == null || s5.length() == 0) {
			s5 = defaultFantasy;
		}
		if (s6 == null || s6.length() == 0) {
			s6 = defaultFantasy;
		}
		if (s7 == null || s7.length() == 0) {
			s7 = defaultFantasy;
		}
		if (s8 == null || s8.length() == 0) {
			s8 = defaultFantasy;
		}

		list.add(parseFantasyString(s1));
		list.add(parseFantasyString(s2));
		list.add(parseFantasyString(s3));
		list.add(parseFantasyString(s4));
		list.add(parseFantasyString(s5));
		list.add(parseFantasyString(s6));
		list.add(parseFantasyString(s7));
		list.add(parseFantasyString(s8));

		return list;
	}

	public void storeFantasyData(ArrayList<TreeMap<Integer, Integer>> list) {
		if (list == null || list.size() != 8)
			return;

		int i = 0;
		for (TreeMap<Integer, Integer> map : list) {
			String str = fantasyToString(map);
			SharedPreferenceUtil.INSTANCE.setData("fantasy" + (i++), str);
		}
	}

	public ArrayList<Fantasy> getFantasy() {
		if (vFantasy == null)
			vFantasy = initFantasy();

		return vFantasy;
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

		ArrayList<int[]> storedData = getVibrateData();
		v1.setPattern(storedData.get(0));
		v2.setPattern(storedData.get(1));
		v3.setPattern(storedData.get(2));
		v4.setPattern(storedData.get(3));
		v5.setPattern(storedData.get(4));
		v6.setPattern(storedData.get(5));
		v7.setPattern(storedData.get(6));
		v8.setPattern(storedData.get(7));
		v9.setPattern(storedData.get(8));
		v10.setPattern(storedData.get(9));
		v11.setPattern(storedData.get(10));

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
		
//		Fantasy: 梦境
//		Gentle Touch: 温柔触感
//		Soft Lick:渐进
//		Whispering：轻吟
//		Tickle：挑逗
//		Flick：轻抚
//		Deep Kiss：深吻
//		Ocean Wave：浪涛
//		Moutain Top：山巅
//		Summer Air：迷幻
//		Shooting Star：流星雨
//		Rock Me Hard：如痴如狂

		String[] title = { "温柔触感", "渐进", "轻吟", "挑逗",
				"轻抚", "深吻", "浪涛", "山巅",
				"迷幻", "流星雨", "如痴如狂" };
		for (int i = 0; i < title.length; i++) {
			list.get(i).setIndex(i + 1);
			list.get(i).setTitle(title[i]);
		}

		return list;
	}

	private ArrayList<Fantasy> initFantasy() {
		ArrayList<Fantasy> list = new ArrayList<Fantasy>();

		Fantasy f1 = new Fantasy();
		f1.setTime(System.currentTimeMillis());
		f1.setTitle("机场");
		f1.setDescription("飞机场的遐想");
		f1.setLogoId(R.drawable.img_airplane_logo);
		f1.setImageId(R.drawable.img_airplane);
		f1.setMusicId(R.raw.amb_airplane);
		list.add(f1);

		Fantasy f2 = new Fantasy();
		f2.setTime(System.currentTimeMillis());
		f2.setTitle("沙滩");
		f2.setDescription("阳光、沙滩、海浪");
		f2.setLogoId(R.drawable.img_beach_logo);
		f2.setImageId(R.drawable.img_beach);
		f2.setMusicId(R.raw.amb_beach);
		list.add(f2);

		Fantasy f3 = new Fantasy();
		f3.setTime(System.currentTimeMillis());
		f3.setTitle("呼吸");
		f3.setDescription("RooM");
		f3.setLogoId(R.drawable.img_breathing_logo);
		f3.setImageId(R.drawable.img_breathing);
		f3.setMusicId(R.raw.amb_breathing);
		list.add(f3);

		Fantasy f4 = new Fantasy();
		f4.setTime(System.currentTimeMillis());
		f4.setTitle("露营");
		f4.setDescription("深夜、篝火、营地");
		f4.setLogoId(R.drawable.img_camping_logo);
		f4.setImageId(R.drawable.img_camping);
		f4.setMusicId(R.raw.amb_camping);
		list.add(f4);

		Fantasy f5 = new Fantasy();
		f5.setTime(System.currentTimeMillis());
		f5.setTitle("公园");
		f5.setDescription("这里的夜晚、虫鸣");
		f5.setLogoId(R.drawable.img_park_at_night_logo);
		f5.setImageId(R.drawable.img_park_at_night);
		f5.setMusicId(R.raw.amb_park_at_night);
		list.add(f5);

		Fantasy f6 = new Fantasy();
		f6.setTime(System.currentTimeMillis());
		f6.setTitle("雨");
		f6.setDescription("细雨绵绵");
		f6.setLogoId(R.drawable.img_rain_logo);
		f6.setImageId(R.drawable.img_rain);
		f6.setMusicId(R.raw.amb_rain);
		list.add(f6);

		Fantasy f7 = new Fantasy();
		f7.setTime(System.currentTimeMillis());
		f7.setTitle("瀑布");
		f7.setDescription("森林、流水");
		f7.setLogoId(R.drawable.img_waterfall_logo);
		f7.setImageId(R.drawable.img_waterfall);
		f7.setMusicId(R.raw.amb_waterfall);
		list.add(f7);

		Fantasy f8 = new Fantasy();
		f8.setTime(System.currentTimeMillis());
		f8.setTitle("森林");
		f8.setDescription("树木、鸟鸣");
		f8.setLogoId(R.drawable.img_woods_logo);
		f8.setImageId(R.drawable.img_woods);
		f8.setMusicId(R.raw.amb_woods);
		list.add(f8);

		return list;
	}
}
