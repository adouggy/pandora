package me.promenade.pandora.asynjob;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.promenade.pandora.util.BluetoothUtil;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

public class BluetoothConnectJob extends AsyncTask<BluetoothDevice, Integer, String> {
	public static final String TAG = "BluetoothConnectJob";
	private BluetoothSocket btSocket = null;

	@Override
	protected String doInBackground(
			BluetoothDevice... param) {
		if (param == null)
			return null;
		
		BluetoothDevice device = param[0];

		Method m = null;
		
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
		
		
		return "";
	}

	@Override
	protected void onPostExecute(
			String result) {
		super.onPostExecute(result);
		if( BluetoothUtil.INSTANCE.mConnectedThread != null ){
			BluetoothUtil.INSTANCE.mConnectedThread.cancel();
		}
		BluetoothUtil.INSTANCE.mConnectedThread = new ConnectedThread( btSocket );
		BluetoothUtil.INSTANCE.mConnectedThread.start();
	}
}
