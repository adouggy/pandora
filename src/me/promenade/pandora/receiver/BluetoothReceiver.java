package me.promenade.pandora.receiver;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import me.promenade.pandora.asynjob.ConnectedThread;
import me.promenade.pandora.bean.Bluetooth;
import me.promenade.pandora.fragment.MassagerFragment;
import me.promenade.pandora.util.BluetoothUtil;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BluetoothReceiver extends BroadcastReceiver {

	public static final String TAG = "BluetoothReceiver";

	private static HashMap<String, Long> mBLEMap = new HashMap<String, Long>();

	public static HashMap<String, Long> getBLE() {
		return mBLEMap;
	}

	public void onReceive(
			Context paramAnonymousContext,
			Intent paramAnonymousIntent) {
		String str = paramAnonymousIntent.getAction();
		if ("android.bluetooth.device.action.FOUND".equals(str)) {
			// BluetoothDevice localBluetoothDevice = (BluetoothDevice)
			// paramAnonymousIntent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
			// short s =
			// paramAnonymousIntent.getShortExtra("android.bluetooth.device.extra.RSSI",
			// (short) 0);

			BluetoothDevice device = paramAnonymousIntent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

			Bluetooth b = new Bluetooth();
			b.setName(device.getName() + "(" + device.getAddress() + ")");
			MassagerFragment.addDevice(b);

			Log.i(TAG,
					"找到:" + (device.getName() == null ? "无名设备" : device.getName()));

//			if( device.getName() != null && device.getName().contains("HC") ){
				BluetoothUtil.INSTANCE.setDevice(device);
//			}
			
		}
		if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(str)) {
			Log.i(TAG,
					"discovery finished");
		}
	}
	
}
