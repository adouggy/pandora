package me.promenade.pandora.asynjob;

import java.util.Set;

import me.promenade.pandora.fragment.MassagerWithVideoFragment;
import me.promenade.pandora.receiver.BluetoothReceiver;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;

public class BluetoothSearchJob extends
		AsyncTask<String/* BLE or notBLE */, Integer, String> {

	public static final String TAG = "BluetoothSearchJob";
	public static final String TYPE_BLE = "BLE";
	public static final String TYPE_3_0 = "notBLE";

	@Override
	protected String doInBackground(String... param) {
		Log.d(TAG, "retriving...");
		if (param == null)
			return null;

		if (param[0].compareTo(TYPE_BLE) == 0) {
			// for BLE
			// register broadcast receiver to receive scan results
			IntentFilter localIntentFilter = new IntentFilter(
					"android.bluetooth.device.action.FOUND");
			localIntentFilter
					.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
			// begin scan
			// BluetoothAdapterHidden.startLeDiscovery(
			// BluetoothAdapter.getDefaultAdapter() );

		} else if (param[0].compareTo(TYPE_3_0) == 0) {

			Set<BluetoothDevice> pairedDevices = BluetoothAdapter
					.getDefaultAdapter().getBondedDevices();
			
			boolean isFound = false;
			if (pairedDevices.size() > 0) {
				// Loop through paired devices
				for (BluetoothDevice device : pairedDevices) {
					isFound = BluetoothReceiver
							.addDeviceIfFound(device, true);
					if (isFound) {
						Log.d( TAG, "found from paired devices");
						break;
					}
				}
			}
			
			if( !isFound ){
				Log.d(TAG, "not found from paired devices, so, discovery now...");
				MassagerWithVideoFragment.mHandler.obtainMessage(MassagerWithVideoFragment.MSG_DISCONNECTED).sendToTarget();
				BluetoothAdapter.getDefaultAdapter().startDiscovery();
			}
		}

		return "";
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}
}
