package me.promenade.pandora.receiver;

import java.util.HashMap;

import me.promenade.pandora.fragment.MassagerWithVideoFragment;
import me.promenade.pandora.util.BluetoothUtil;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BluetoothReceiver extends BroadcastReceiver {
	public static final String TAG = "BluetoothReceiver";

	public static final String DEVICE_NAME = "HC-05";

	private static HashMap<String, Long> mBLEMap = new HashMap<String, Long>();

	public static HashMap<String, Long> getBLE() {
		return mBLEMap;
	}

	public static boolean addDeviceIfFound(BluetoothDevice device, boolean useRemote) {
		if (device.getName() != null
				&& device.getName().compareTo(DEVICE_NAME) == 0) {
			if( useRemote ){
				device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(device.getAddress());
			}else{
			}
			BluetoothUtil.INSTANCE.setDevice(device);
			MassagerWithVideoFragment.mHandler.obtainMessage(
					MassagerWithVideoFragment.MSG_FOUND).sendToTarget();
			return true;
		}
		return false;
	}

	public void onReceive(Context paramAnonymousContext,
			Intent paramAnonymousIntent) {
		String str = paramAnonymousIntent.getAction();
		if ("android.bluetooth.device.action.FOUND".equals(str)) {

			BluetoothDevice device = paramAnonymousIntent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

			Log.i(TAG,"name:" + device.getName() + ", mac:" + device.getAddress());

			boolean found = addDeviceIfFound(device, false);
			if (found) {
				BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
			}

		} else if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED"
				.equals(str)) {
			Log.i(TAG, "discovery finished");

//			MassagerWithVideoFragment.mHandler.obtainMessage(
//					MassagerWithVideoFragment.MSG_DONE).sendToTarget();
		} else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(str)) {
			BluetoothDevice device = paramAnonymousIntent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			if (device.getName().equalsIgnoreCase(DEVICE_NAME)) {
				int connectState = device.getBondState();
				switch (connectState) {
				case BluetoothDevice.BOND_NONE:
					Log.i(TAG, "bond none");
					MassagerWithVideoFragment.mHandler.obtainMessage(
							MassagerWithVideoFragment.MSG_DISCONNECTED).sendToTarget();
					break;
				case BluetoothDevice.BOND_BONDING:
					Log.i(TAG, "bonding..");
					break;
				case BluetoothDevice.BOND_BONDED:
					Log.i(TAG, "bonded..");
					MassagerWithVideoFragment.mHandler.obtainMessage(
							MassagerWithVideoFragment.MSG_FOUND).sendToTarget();
					break;
				}
			}
		}
	}

}
