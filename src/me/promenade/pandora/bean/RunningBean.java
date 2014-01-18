package me.promenade.pandora.bean;

import java.util.ArrayList;

import me.promenade.pandora.R;
import me.promenade.pandora.util.Constants;
import me.promenade.pandora.util.SharedPreferenceUtil;

public enum RunningBean {
	INSTANCE;

	private ArrayList<Vibration> vData = null;
	private ArrayList<Fantasy> vFantasy = null;
//	private ArrayList<Friend> vFriend = null;

	RunningBean() {
	}
	
	public void logout() {
		SharedPreferenceUtil.INSTANCE.setData(Constants.SP_USER_ID, "");
		SharedPreferenceUtil.INSTANCE.setData(Constants.SP_USER_NAME, "");
		SharedPreferenceUtil.INSTANCE.setData(Constants.SP_USER_PASSWORD, "");
		SharedPreferenceUtil.INSTANCE.setData(Constants.SP_PARTNER_ID, "");
		SharedPreferenceUtil.INSTANCE.setData(Constants.SP_PARTNER_NAME, "");
	}

	public int getUserId() {
		String idStr = SharedPreferenceUtil.INSTANCE.getData(Constants.SP_USER_ID);
		int id = -1;

		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return id;
	}

	public String getUserName() {
		String name = SharedPreferenceUtil.INSTANCE.getData(Constants.SP_USER_NAME);
		return name;
	}
	
	public String getUserPassword() {
		String pass = SharedPreferenceUtil.INSTANCE.getData(Constants.SP_USER_PASSWORD);
		return pass;
	}

	public int getPartnerId() {
		String idStr = SharedPreferenceUtil.INSTANCE.getData(Constants.SP_PARTNER_ID);
		int id = -1;

		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return id;
	}

	public String getPartnerName() {
		String name = SharedPreferenceUtil.INSTANCE.getData(Constants.SP_PARTNER_NAME);
		return name;
	}

	// public ArrayList<Friend> getFriend() {
	// if (vFriend == null)
	// vFriend = initFriend();
	//
	// return vFriend;
	// }

	// public void reloadFriend() {
	// vFriend = initFriend();
	// }
	//
	// private ArrayList<Friend> initFriend() {
	// ArrayList<Friend> list = new ArrayList<Friend>();
	//
	// String friendName =
	// SharedPreferenceUtil.INSTANCE.getData(Constants.SP_FRIEND);
	// if (friendName.length() != 0) {
	// Friend f = new Friend();
	// f.setUsername(friendName);
	// list.add(f);
	// }
	//
	// return list;
	// }

	public ArrayList<Vibration> getVibration() {
		if (vData == null)
			vData = initVibrateData();

		return vData;
	}

	private int[] parseVibrateData(
			String data) {
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
		list.add(parseVibrateData(SharedPreferenceUtil.INSTANCE.getData(Constants.SP_VIBRATE_0)));
		list.add(parseVibrateData(SharedPreferenceUtil.INSTANCE.getData(Constants.SP_VIBRATE_1)));
		list.add(parseVibrateData(SharedPreferenceUtil.INSTANCE.getData(Constants.SP_VIBRATE_2)));
		list.add(parseVibrateData(SharedPreferenceUtil.INSTANCE.getData(Constants.SP_VIBRATE_3)));
		list.add(parseVibrateData(SharedPreferenceUtil.INSTANCE.getData(Constants.SP_VIBRATE_4)));
		list.add(parseVibrateData(SharedPreferenceUtil.INSTANCE.getData(Constants.SP_VIBRATE_5)));
		list.add(parseVibrateData(SharedPreferenceUtil.INSTANCE.getData(Constants.SP_VIBRATE_6)));
		list.add(parseVibrateData(SharedPreferenceUtil.INSTANCE.getData(Constants.SP_VIBRATE_7)));
		list.add(parseVibrateData(SharedPreferenceUtil.INSTANCE.getData(Constants.SP_VIBRATE_8)));
		list.add(parseVibrateData(SharedPreferenceUtil.INSTANCE.getData(Constants.SP_VIBRATE_9)));
		list.add(parseVibrateData(SharedPreferenceUtil.INSTANCE.getData(Constants.SP_VIBRATE_10)));
		return list;
	}

	public ArrayList<String> getFantasyData() {
		ArrayList<String> list = new ArrayList<String>();
		list.add(SharedPreferenceUtil.INSTANCE.getData(Constants.SP_FANTASY_0));
		list.add(SharedPreferenceUtil.INSTANCE.getData(Constants.SP_FANTASY_1));
		list.add(SharedPreferenceUtil.INSTANCE.getData(Constants.SP_FANTASY_2));
		list.add(SharedPreferenceUtil.INSTANCE.getData(Constants.SP_FANTASY_3));
		list.add(SharedPreferenceUtil.INSTANCE.getData(Constants.SP_FANTASY_4));
		list.add(SharedPreferenceUtil.INSTANCE.getData(Constants.SP_FANTASY_5));
		list.add(SharedPreferenceUtil.INSTANCE.getData(Constants.SP_FANTASY_6));
		list.add(SharedPreferenceUtil.INSTANCE.getData(Constants.SP_FANTASY_7));
		return list;
	}

	// public void saveFantasyData(ArrayList<String> list){
	// SharedPreferenceUtil.INSTANCE.setData(Constants.SP_FANTASY_0,
	// list.get(0));
	// SharedPreferenceUtil.INSTANCE.setData(Constants.SP_FANTASY_1,
	// list.get(1));
	// SharedPreferenceUtil.INSTANCE.setData(Constants.SP_FANTASY_2,
	// list.get(2));
	// SharedPreferenceUtil.INSTANCE.setData(Constants.SP_FANTASY_3,
	// list.get(3));
	// SharedPreferenceUtil.INSTANCE.setData(Constants.SP_FANTASY_4,
	// list.get(4));
	// SharedPreferenceUtil.INSTANCE.setData(Constants.SP_FANTASY_5,
	// list.get(5));
	// SharedPreferenceUtil.INSTANCE.setData(Constants.SP_FANTASY_6,
	// list.get(6));
	// SharedPreferenceUtil.INSTANCE.setData(Constants.SP_FANTASY_7,
	// list.get(7));
	// }

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

		// v1.setPattern(new int[] { 0, 1, 2, 3, 4, 5, 4, 3, 2, 1, 0, 1, 2, 3
		// });
		// v2.setPattern(new int[] { 1, 3, 2, 1, 3, 2, 1, 3, 2, 1, 3, 2, 1, 3
		// });
		// v3.setPattern(new int[] { 2, 1, 2, 3, 4, 5, 0, 3, 2, 1, 0, 1, 2, 3
		// });
		// v4.setPattern(new int[] { 3, 1, 2, 3, 4, 0, 4, 3, 2, 1, 0, 8, 2, 3
		// });
		// v5.setPattern(new int[] { 4, 1, 2, 3, 0, 5, 4, 3, 2, 1, 7, 1, 2, 3
		// });
		// v6.setPattern(new int[] { 0, 1, 2, 0, 4, 5, 4, 3, 2, 6, 0, 1, 2, 3
		// });
		// v7.setPattern(new int[] { 0, 1, 2, 0, 4, 5, 4, 3, 2, 6, 0, 1, 2, 3
		// });
		// v8.setPattern(new int[] { 0, 1, 2, 0, 4, 5, 4, 3, 2, 6, 0, 1, 2, 3
		// });
		// v9.setPattern(new int[] { 0, 1, 2, 0, 4, 5, 4, 3, 2, 6, 0, 1, 2, 3
		// });
		// v10.setPattern(new int[] { 0, 1, 2, 0, 4, 5, 4, 3, 2, 6, 0, 1, 2, 3
		// });
		// v11.setPattern(new int[] { 0, 1, 2, 0, 4, 5, 4, 3, 2, 6, 0, 1, 2, 3
		// });

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

	private ArrayList<Fantasy> initFantasy() {
		ArrayList<Fantasy> list = new ArrayList<Fantasy>();

		Fantasy f1 = new Fantasy();
		f1.setTime(System.currentTimeMillis());
		f1.setTitle("airplane");
		f1.setDescription("飞机场的遐想");
		f1.setLogoId(R.drawable.img_airplane_logo);
		f1.setImageId(R.drawable.img_airplane);
		f1.setMusicId(R.raw.amb_airplane);
		list.add(f1);

		Fantasy f2 = new Fantasy();
		f2.setTime(System.currentTimeMillis());
		f2.setTitle("beach");
		f2.setDescription("阳光、沙滩、海浪");
		f2.setLogoId(R.drawable.img_beach_logo);
		f2.setImageId(R.drawable.img_beach);
		f2.setMusicId(R.raw.amb_beach);
		list.add(f2);

		Fantasy f3 = new Fantasy();
		f3.setTime(System.currentTimeMillis());
		f3.setTitle("breathing");
		f3.setDescription("RooM");
		f3.setLogoId(R.drawable.img_breathing_logo);
		f3.setImageId(R.drawable.img_breathing);
		f3.setMusicId(R.raw.amb_breathing);
		list.add(f3);

		Fantasy f4 = new Fantasy();
		f4.setTime(System.currentTimeMillis());
		f4.setTitle("camping");
		f4.setDescription("深夜、篝火、营地");
		f4.setLogoId(R.drawable.img_camping_logo);
		f4.setImageId(R.drawable.img_camping);
		f4.setMusicId(R.raw.amb_camping);
		list.add(f4);

		Fantasy f5 = new Fantasy();
		f5.setTime(System.currentTimeMillis());
		f5.setTitle("park at night");
		f5.setDescription("这里的夜晚、虫鸣");
		f5.setLogoId(R.drawable.img_park_at_night_logo);
		f5.setImageId(R.drawable.img_park_at_night);
		f5.setMusicId(R.raw.amb_park_at_night);
		list.add(f5);

		Fantasy f6 = new Fantasy();
		f6.setTime(System.currentTimeMillis());
		f6.setTitle("rain");
		f6.setDescription("细雨绵绵");
		f6.setLogoId(R.drawable.img_rain_logo);
		f6.setImageId(R.drawable.img_rain);
		f6.setMusicId(R.raw.amb_rain);
		list.add(f6);

		Fantasy f7 = new Fantasy();
		f7.setTime(System.currentTimeMillis());
		f7.setTitle("waterfall");
		f7.setDescription("森林、流水");
		f7.setLogoId(R.drawable.img_waterfall_logo);
		f7.setImageId(R.drawable.img_waterfall);
		f7.setMusicId(R.raw.amb_waterfall);
		list.add(f7);

		Fantasy f8 = new Fantasy();
		f8.setTime(System.currentTimeMillis());
		f8.setTitle("woods");
		f8.setDescription("树木、鸟鸣");
		f8.setLogoId(R.drawable.img_woods_logo);
		f8.setImageId(R.drawable.img_woods);
		f8.setMusicId(R.raw.amb_woods);
		list.add(f8);

		return list;
	}
}
