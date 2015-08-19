package com.cnam.mobile.gestemps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by mac on 18/08/2015.
 */
public class MonRecepteur extends BroadcastReceiver {

    private static final String tag = "sam";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(tag, "alarme en fonction depuis MonRecpteur");

        Intent service1 = new Intent(context, MonServiceAlarm.class);
        context.startService(service1);
    }

    public void messAlerte(){

    }
}
