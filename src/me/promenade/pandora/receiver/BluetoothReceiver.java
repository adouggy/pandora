package me.promenade.pandora.receiver;

import java.util.HashMap;

import me.promenade.pandora.fragment.MassagerFragment;
import me.promenade.pandora.util.BluetoothUtil;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

public class BluetoothReceiver extends BroadcastReceiver {
	public static final String TAG = "BluetoothReceiver";

	public static final String DEVICE_NAME = "HC-05";

	private static HashMap<String, Long> mBLEMap = new HashMap<String, Long>();

	public static HashMap<String, Long> getBLE() {
		return mBLEMap;
	}

	public void onReceive(Context paramAnonymousContext,
			Intent paramAnonymousIntent) {
		String str = paramAnonymousIntent.getAction();
		if ("android.bluetooth.device.action.FOUND".equals(str)) {

			BluetoothDevice device = paramAnonymousIntent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

			Log.i(TAG,
					"name:" + device.getName() + ", mac:" + device.getAddress());

			if (device.getName() != null && device.getName().contains("HC")) {
				BluetoothUtil.INSTANCE.setDevice(device);

				// Bluetooth b = new Bluetooth();
				// b.setName(device.getName() + "(" + device.getAddress() +
				// ")");

				// Message msg = MassagerFragment.mHandler.obtainMessage();
				// msg.what = MassagerFragment.MSG_DONE;
				// msg.sendToTarget();
				Message msg = new Message();
				msg.what = MassagerFragment.MSG_FOUND;
				MassagerFragment.mHandler.sendMessage(msg);
			}
		} else if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED"
				.equals(str)) {
			Log.i(TAG, "discovery finished");

			// Message msg = MassagerFragment.mHandler.obtainMessage();
			// msg.what = MassagerFragment.MSG_DONE;
			// msg.sendToTarget();
			Message msg = new Message();
			msg.what = MassagerFragment.MSG_DONE;
			MassagerFragment.mHandler.sendMessage(msg);
		} else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(str)) {
			BluetoothDevice device = paramAnonymousIntent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			if (device.getName().equalsIgnoreCase(DEVICE_NAME)) {
				int connectState = device.getBondState();
				switch (connectState) {
				case BluetoothDevice.BOND_NONE:
					Log.i(TAG, "bond none");
					break;
				case BluetoothDevice.BOND_BONDING:
					Log.i(TAG, "bonding..");
					break;
				case BluetoothDevice.BOND_BONDED:
					Log.i(TAG, "bonded..");
					break;
				}
			}
		}
	}

}
