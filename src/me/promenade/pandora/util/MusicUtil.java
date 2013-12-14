package me.promenade.pandora.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.media.MediaPlayer;

public enum MusicUtil {
	INSTANCE;

	private Context mContext = null;

	private MediaPlayer mMediaPlayer = null;
	
	private byte[] mBytes = null;

	MusicUtil() {

	}

	public void init(
			Context ctx) {
		mContext = ctx;
	}

	public MusicUtil setId(
			int id) {
		InputStream inStream = this.mContext.getResources().openRawResource(id);

		try {
			mBytes =  convertStreamToByteArray(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if( inStream != null ){
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return this;
	}

	private static byte[] convertStreamToByteArray(
			InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buff = new byte[10240];
		int i = Integer.MAX_VALUE;
		while ((i = is.read(buff,
				0,
				buff.length)) > 0) {
			baos.write(buff,
					0,
					i);
		}

		return baos.toByteArray(); // be sure to close InputStream in calling
									// function
	}

	public void play() {
		FileInputStream fis = null;
		try {
			// create temp file that will hold byte array
			File tempMp3 = File.createTempFile("temp",
					"mp3",
					this.mContext.getCacheDir());
			tempMp3.deleteOnExit();
			FileOutputStream fos = new FileOutputStream(tempMp3);
			fos.write(mBytes);
			fos.close();

			// Tried reusing instance of media player
			// but that resulted in system crashes...

			// lazy initial
			if (this.mMediaPlayer == null)
				this.mMediaPlayer = new MediaPlayer();
			else
				this.mMediaPlayer.reset();

			// Tried passing path directly, but kept getting
			// "Prepare failed.: status=0x1"
			// so using file descriptor instead
			fis = new FileInputStream(tempMp3);
			mMediaPlayer.setDataSource(fis.getFD());

			mMediaPlayer.prepare();
			mMediaPlayer.start();
		} catch (IOException ex) {
			// String s = ex.toString();
			ex.printStackTrace();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public void stop() {
		if (mMediaPlayer != null)
			mMediaPlayer.stop();
	}
	
	public int getTime() {
		return mMediaPlayer.getDuration()/1000;
	}
	
	public int getCurrentPosition(){
		return mMediaPlayer.getCurrentPosition();
	}
	
	public void setPosition(int pos){
	}
}
