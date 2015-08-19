package com.cnam.mobile.gestemps;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mac on 18/08/2015.
 */
public class MonServiceAlarm extends Service {
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


//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
        Notification notif = new Notification();
        notif.vibrate = vib;
        notif.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager notifMan = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notifMan.notify(1, notif);



//        int icon = R.drawable.bt_retirer;
//        String tickerText = "Mes contacts";
//        long timeMilli = System.currentTimeMillis();
//
//        Notification notification = new Notification();
//
//        notification.icon=icon;
//        notification.tickerText=tickerText;
//        notification.when=timeMilli;
//
//        Intent monIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));
//        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, monIntent, Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, monIntent,PendingIntent.FLAG_UPDATE_CURRENT);
//
//        notification.setLatestEventInfo(this, getText(R.string.app_name), tickerText, pendingIntent);
//
//        NotificationManager mNM = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        mNM.notify(1, notification);
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);

        Log.d(tag, "service MonServiceAlarm onStart");
        mManager = (NotificationManager) this.getApplicationContext()
                .getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this.getApplicationContext(),SeanceDebut.class);

        Notification notification = new Notification(R.drawable.autoriser,"Un message de Gestemps !",
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
                "GESTEMPS",
                "La séance est terminée",
                pendingNotificationIntent);
        mManager.notify(0, notification);

//        Notification.Builder notification= new Notification.Builder(this.getApplicationContext())
//                .setSmallIcon(R.drawable.autoriser)
//                .setAutoCancel(true)
//                .setTicker("Gestemps ici").setContentText("c est fini!")
//                .setContentIntent(penIn)
//                .build();
//
//
//        PendingIntent penIn = PendingIntent.getActivity(
//                this.getApplicationContext(),
//                0,
//                intent1,
//                PendingIntent.FLAG_UPDATE_CURRENT);

        //notification.flags |= Notification.FLAG_AUTO_CANCEL;
//        notification.setLatestEventInfo(this.getApplicationContext(),
//                "GESTEMPS",
//                "La séance est terminée",
//                penIn);


       // mManager.notify(0, notification);
       // messAlert();

        Log.d(tag, "service effectué par MonServiceAlarm");
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


    public void messAlert(View v){
        AlertDialog.Builder mes = new AlertDialog.Builder(this.getApplicationContext());
        mes.setMessage("La séance est terminée !").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setTitle("Alerte de fin").setIcon(R.drawable.autoriser).create();
        mes.show();
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

//    public void afficheNotif(CharSequence texte){
//        Notification notif = new Notification.Builder(this)
//                .setTicker(texte).setContentTitle(texte)
//                .setContentIntent(pendingNotificationIntent)
//                .setSmallIcon(R.drawable.autoriser)
//                .build();
//
//        mManager.notify(0,notif);
//    }
}
