package me.promenade.pandora.util;

import me.promenade.pandora.asynjob.BluetoothConnectJob;
import me.promenade.pandora.asynjob.ConnectedThread;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;

public enum BluetoothUtil {
	INSTANCE;

	public static final String TAG = "BluetoothUtil";
	public ConnectedThread mConnectedThread = null;

	BluetoothUtil() {
	}

	public void init(
			Context ctx) {
	}

	public void stopSearch() {
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if ((bluetoothAdapter != null) && (bluetoothAdapter.isDiscovering()))
			bluetoothAdapter.cancelDiscovery();
	}

	public void setDevice(
			BluetoothDevice device) {
		Log.i(TAG,
				"setDevice");

		BluetoothConnectJob job = new BluetoothConnectJob();
		job.execute(device);
	}

	public boolean isConntected() {
		if (mConnectedThread != null) {
			return true;
		}
		return false;
	}

	public void sendMessage(
			byte[] msg,
			long interval) {
		Log.i(TAG,
				"sendMessage");
		if (mConnectedThread != null) {
			for (byte b : msg) {
				Log.i(TAG,
						"->" + b);
				mConnectedThread.write(new byte[] { b });
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public void sendMessage(
			byte[] input) {
		Log.i(TAG,
				"sendMessage>>>>>");
		
		if( input == null || input.length != 16 ){
			Log.e(TAG, "vibrate message format error");
			return;
		}
		
//		byte[] msg = new byte[]{ 'b', 'x', '3', '2', '1', '3', '2', '1','3', '2', '1','3', '2', '1','3', '2'  };
		
		if (mConnectedThread != null) {
			for( byte b : input ){
				Log.i(TAG,  b + "" );
			}
			
			mConnectedThread.write(input);
		}
	}
}
