package me.promenade.pandora.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.os.Environment;

import me.promenade.pandora.bean.Chat;

public enum ChatUtil {
	INSTANCE;

	private File mFile;

	public void init() {
		File dir = new File(Environment.getExternalStorageDirectory()
				+ java.io.File.separator + "lavo");
		dir.mkdir();
		mFile = new File(Environment.getExternalStorageDirectory()
				+ java.io.File.separator + "lavo" + java.io.File.separator
				+ Constants.CHAT_LOG);
	}

	public void store(ArrayList<Chat> list) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {
			fos = new FileOutputStream(mFile);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(list);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (oos != null)
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public ArrayList<Chat> retrieve() {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(mFile);
			ois = new ObjectInputStream(fis);
			@SuppressWarnings("unchecked")
			ArrayList<Chat> list = (ArrayList<Chat>) ois.readObject();
			return list;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (ois != null)
				try {
					ois.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}
}
