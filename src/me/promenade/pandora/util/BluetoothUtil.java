package me.promenade.pandora.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import me.promenade.pandora.asynjob.BluetoothSearchJob;
import me.promenade.pandora.asynjob.ConnectedThread;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public enum BluetoothUtil {
	INSTANCE;

	public static final String TAG = "BluetoothUtil";

	private Context mContext = null;
	
	ConnectedThread mConnectedThread = null;

	BluetoothUtil() {
	}

	public void init(
			Context ctx) {
		this.mContext = ctx;
	}

//	public ArrayList<BluetoothDevice> getBondedBluetooth() {
//		ArrayList<BluetoothDevice> list = new ArrayList<BluetoothDevice>();
//
//		if (BluetoothAdapter.getDefaultAdapter() != null) {
//			// 调用isEnabled()方法判断当前蓝牙设备是否可用
//			if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
//				// 如果蓝牙设备不可用的话,创建一个intent对象,该对象用于启动一个Activity,提示用户启动蓝牙适配器
//				// Intent intent = new
//				// Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//				// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				// mContext.startActivity(intent);
//			}
//			// 得到所有已经配对的蓝牙适配器对象
//			Set<?> devices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
//			if (devices.size() > 0) {
//				for (Iterator<?> iterator = devices.iterator(); iterator.hasNext();) {
//					// 得到BluetoothDevice对象,也就是说得到配对的蓝牙适配器
//					BluetoothDevice device = (BluetoothDevice) iterator.next();
//					list.add(device);
//				}
//			}
//		}
//		return list;
//	}

	public void startSearch() {
		Log.i( TAG, "startSearch" );
		if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(intent);
		}

		// if( BluetoothUtil.isBLE() ){
		// BluetoothSearchJob j = new BluetoothSearchJob();
		// j.execute(BluetoothSearchJob.TYPE_BLE);
		// }else{
		BluetoothSearchJob j2 = new BluetoothSearchJob();
		j2.execute(BluetoothSearchJob.TYPE_3_0);
		// }

	}

	public void stopSearch() {
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if ((bluetoothAdapter != null) && (bluetoothAdapter.isDiscovering()))
			bluetoothAdapter.cancelDiscovery();
	}

	public void setDevice( BluetoothDevice device ){
		Log.i( TAG, "setDevice");
		Method m = null;
		BluetoothSocket btSocket = null;
		Log.i(TAG,
				"get createRfcommSocket method");
		try {
			m = device.getClass().getMethod("createRfcommSocket",
					new Class[] { int.class });
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		Log.i(TAG,
				"get socket");
		try {
			btSocket = (BluetoothSocket) m.invoke(device,
					Integer.valueOf(1));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		Log.i(TAG,
				"connect");
		try {
			if (btSocket != null)
				btSocket.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (btSocket == null) {
			Log.i(TAG,
					"not connected");
		}
		Log.i(TAG,
				"connected");
		
		mConnectedThread = new ConnectedThread( btSocket );
		mConnectedThread.start();
	}
	
	public void sendMessage( byte[] msg ){
		Log.i( TAG, "sendMessage" );
		if( mConnectedThread != null ){
			for( byte b : msg ){
				Log.i( TAG, "->" + b );
				
				mConnectedThread.write(new byte[]{ b });
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
}
