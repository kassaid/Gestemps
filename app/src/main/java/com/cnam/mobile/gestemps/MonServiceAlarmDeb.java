package com.cnam.mobile.gestemps;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mac on 18/08/2015.
 */
public class MonServiceAlarmDeb extends Service {
    private static final String tag = "sam";

    private NotificationManager mManager;
    private Timer compteur = new Timer();
    private long sec = 0L;
    private TimerTask horl = null;
    private PendingIntent pendingNotificationIntent = null;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(tag, "service MonServiceAlarm onBlind");
        return null;
    }

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.d(tag, "service MonServiceAlarm onCreate");
        long vib[]={1000, 2000, 500, 1000, 150, 250, 250, 150};

        horl = new TimerTask() {
            @Override
            public void run() {
                sec++;
            }
        };
        startCompteur();

        Notification notif = new Notification();
        notif.vibrate = vib;
        notif.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager notifMan = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notifMan.notify(1, notif);
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);

        Log.d(tag, "service MonServiceAlarm onStart");
        mManager = (NotificationManager) this.getApplicationContext()
                .getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this.getApplicationContext(),RdvListeFutur.class);

        Notification notification = new Notification(R.drawable.logo_a6t_48,"Un message de Gestemps !",
                System.currentTimeMillis());

        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        pendingNotificationIntent = PendingIntent.getActivity(
                this.getApplicationContext(),
                0,
                intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.setLatestEventInfo(
                this.getApplicationContext(),
                "A6Temps",
                "La séance doit commencé",
                pendingNotificationIntent);
        mManager.notify(0, notification);

        Log.d(tag, "service effectué par MonServiceAlarmDeb");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        stopCompteur();
        mManager.cancelAll();
        sec = 0L;
        Log.d(tag, "service MonServiceAlarm onDestroy");
    }


    public void startCompteur(){
        compteur.scheduleAtFixedRate(horl, 0, 10L);
        Log.d(tag, "service MonServiceAlarm départ compteur");
    }

    public void stopCompteur(){
        if (compteur != null){
            compteur.cancel();
            Log.d(tag, "service MonServiceAlarm stop compteur");
        }
    }
}
