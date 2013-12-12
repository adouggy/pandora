package me.promenade.pandora.asynjob;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;

public class BluetoothConnectJob extends AsyncTask<BluetoothDevice, Integer, String> {

	public static final String TAG = "BluetoothConnectJob";

	private BluetoothSocket mmSocket;
	private BluetoothDevice mmDevice;

	@Override
	protected String doInBackground(
			BluetoothDevice... param) {
		Log.d(TAG,
				"retriving...");
		if (param == null)
			return null;

		BluetoothDevice device = param[0];

		// Use a temporary object that is later assigned to mmSocket,
		// because mmSocket is final
		BluetoothSocket tmp = null;
		mmDevice = device;

		// Get a BluetoothSocket to connect with the given BluetoothDevice
		try {
			// MY_UUID is the app's UUID string, also used by the server code
			tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("blah.."));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mmSocket = tmp;

		// Cancel discovery because it will slow down the connection
		BluetoothAdapter.getDefaultAdapter().cancelDiscovery();

		try {
			// Connect the device through the socket. This will block
			// until it succeeds or throws an exception
			mmSocket.connect();
		} catch (IOException connectException) {
			// Unable to connect; close the socket and get out
			try {
				mmSocket.close();
			} catch (IOException closeException) {
			}
		}

		// Do work to manage the connection (in a separate thread)
//		manageConnectedSocket(mmSocket);

		return "";
	}

	@Override
	protected void onPostExecute(
			String result) {
		super.onPostExecute(result);
	}
}
