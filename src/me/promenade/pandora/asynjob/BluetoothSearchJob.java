package me.promenade.pandora.asynjob;

import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;

public class BluetoothSearchJob extends AsyncTask<String/* BLE or notBLE */, Integer, String/*
																							 * return
																							 * nothing
																							 * actually
																							 */> {

	public static final String TAG = "BluetoothSearchJob";
	public static final String TYPE_BLE = "BLE";
	public static final String TYPE_3_0 = "notBLE";

	@Override
	protected String doInBackground(
			String... param) {
		Log.d(TAG,
				"retriving...");
		if (param == null)
			return null;

		if (param[0].compareTo(TYPE_BLE) == 0) {
			// for BLE
			// register broadcast receiver to receive scan results
			IntentFilter localIntentFilter = new IntentFilter("android.bluetooth.device.action.FOUND");
			localIntentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
			// begin scan
			// BluetoothAdapterHidden.startLeDiscovery(
			// BluetoothAdapter.getDefaultAdapter() );

		} else if (param[0].compareTo(TYPE_3_0) == 0) {
			BluetoothAdapter.getDefaultAdapter().startDiscovery(); // take 12
																	// seconds.
		}

		return "";
	}

	@Override
	protected void onPostExecute(
			String result) {
		super.onPostExecute(result);
	}
}
