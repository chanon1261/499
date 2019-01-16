package com.example.m.mproject499;

/**
 * Created by chamnarn on 3/17/18.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by chamnarn on 3/17/18.
 */

public class BootReceiver extends BroadcastReceiver {

    private static final String LOG_TAG = BootReceiver.class.toString();


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(LOG_TAG, "Received action: " + intent.getAction());
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}