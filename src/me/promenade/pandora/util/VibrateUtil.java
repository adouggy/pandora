package me.promenade.pandora.util;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;

public enum VibrateUtil {
	
	INSTANCE;

	public static final String TAG = "VibrateUtil"; 
	private Context mContext = null;
	Vibrator mVibrator = null;
	
	public static final int TOTAL_DURATION = 5 * 1000;
	public static final int TIMES = 14;
	public static final int INTERVAL = (int)((float)TOTAL_DURATION/TIMES);
	public static final int PIECE_COUNT = 50;
	public static final int PIECE_INTERVAL = (int)((float)INTERVAL/PIECE_COUNT);
	
	
	public void init(
			Context ctx) {
		this.mContext = ctx;

		// Get instance of Vibrator from current Context
		mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

	}

	public void test() {
		// Vibrate for 300 milliseconds
		mVibrator.vibrate(300);
	}
	
	/**
	 * 0~5
	 * 比如等级为3时，我们3/6的时间震动，剩下时间不震动
	 * 同理，等级为4， 我们4/6的时间震动，2/6的时间不震动
	 * @param level
	 */
	public long[] v( int level ){
		Log.i( TAG, "v->" + level);
		
		if( level > 5 )
			level = 5;
		if( level < 0 )
			level = 0;
		
		int silentCount = 5 - level;
		
		int vibrateTime = PIECE_INTERVAL*level;
		int silentTime = PIECE_INTERVAL*silentCount;
		
		long[] v = new long[PIECE_COUNT];
		for( int i=0; i<v.length; i++ ){
			//set delay
			if( i == 0 ){
				v[i] = 0;
			}else{
				if( i%2 == 1 ){
					v[i] = vibrateTime;
				}else{
					v[i] = silentTime;
				}
			}
		}
		
//		mVibrator.vibrate(v, -1);
		return v;
	}
	
	public void vibrate(int[] pattern){
		long[] finalPattern = new long[TIMES*PIECE_COUNT];
		int index = 0;
		
		for( int level : pattern ){
			long[] singlePattern = v( level );
			for( long l: singlePattern ){
				finalPattern[index++] = l;
			}
		}
		
		mVibrator.vibrate(finalPattern, -1);
	}
	
}
