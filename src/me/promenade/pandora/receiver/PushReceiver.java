package me.promenade.pandora.receiver;


import org.jivesoftware.smack.util.Base64;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PushReceiver extends BroadcastReceiver {
    private static final String TAG = "PushReceiver";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "action=" + action);

        if ("org.androidpn.client.SHOW_NOTIFICATION".equals(action)) {
            String id = intent.getStringExtra("NOTIFICATION_ID");
            Log.d(TAG, "id=" + id);
            String notificationData = intent.getStringExtra("data");
            String jsonStr = new String( Base64.decode(notificationData) );
            Log.d(TAG, "data == " + jsonStr);
            
           
        }
    }
    
}
