package me.promenade.pandora.receiver;

import java.util.HashMap;

import me.promenade.pandora.bean.Bluetooth;
import me.promenade.pandora.fragment.MassagerFragment;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BluetoothReceiver extends BroadcastReceiver {

	public static final String TAG = "BluetoothLEReceiver";

	private static HashMap<String, Long> mBLEMap = new HashMap<String, Long>();

	public static HashMap<String, Long> getBLE() {
		return mBLEMap;
	}

	public void onReceive(
			Context paramAnonymousContext,
			Intent paramAnonymousIntent) {
		String str = paramAnonymousIntent.getAction();
		if ("android.bluetooth.device.action.FOUND".equals(str)) {
//			BluetoothDevice localBluetoothDevice = (BluetoothDevice) paramAnonymousIntent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
//			short s = paramAnonymousIntent.getShortExtra("android.bluetooth.device.extra.RSSI",
//					(short) 0);

			BluetoothDevice device = paramAnonymousIntent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

			Bluetooth b = new Bluetooth();
			b.setName(device.getName() + "(" + device.getAddress() + ")");
			MassagerFragment.addDevice(b);

			Log.i(TAG,
					"找到:" + (device.getName() == null ? "无名设备" : device.getName()));
			// }
		}
		if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(str)) {
			Log.i(TAG,
					"discovery finished");
		}
	}
}
