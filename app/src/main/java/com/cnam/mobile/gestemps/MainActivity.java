package com.cnam.mobile.gestemps;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    final int FROM_FIRST_ACTIVITY_REQUEST=0;
    private static final String tag = "main";
    Button btnRdvPrevu;
    Button btnRdvNonPrevu;
    Button btnListeRdv;
    Button btnAjouter;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    Context ct=this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar =   (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        //ActionBar actionBar = getSupportActionBar();
        //Permet d'afficher le bouton de navigation up sur l'application
        //actionBar.setDisplayHomeAsUpEnabled(true);

        TextView dateDuJour = (TextView) findViewById(R.id.dateDuJourView);

        btnRdvPrevu = (Button) findViewById(R.id.button1);
        btnRdvNonPrevu = (Button) findViewById(R.id.button2);
        btnListeRdv = (Button) findViewById(R.id.button3);
        btnAjouter = (Button) findViewById(R.id.button4);

        dateDuJour.setText(lejour());



        //Bouton LISTE RDV futur
        View.OnClickListener ecoute1=new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                //setAlarme();
                Intent i=new Intent(MainActivity.this, RdvListeFutur.class);
                startActivity(i);
                //finish();
            }
        };
        btnRdvPrevu.setOnClickListener(ecoute1);


        //Bouton NEW RDV
        View.OnClickListener ecoute2 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(MainActivity.this, RdvCreation.class);
                startActivity(i);
                //finish();
            }
        };
        btnRdvNonPrevu.setOnClickListener(ecoute2);

        //Bouton LISTE DES RDV
        View.OnClickListener ecoute3 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(MainActivity.this, RdvListe.class);
                startActivity(i);
                //finish();
            }
        };
        btnListeRdv.setOnClickListener(ecoute3);

        //Ajouter une personne
        View.OnClickListener ecoute4 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {

                Intent i=new Intent(MainActivity.this, PersonneCreation.class);
                startActivity(i);
                //finish();
            }
        };
        btnAjouter.setOnClickListener(ecoute4);

    }


//    private void setAlarme(){
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.SECOND,cal.get(Calendar.SECOND)+70);
//
//        Intent i = new Intent(MainActivity.this,MonRecepteur.class);
//        PendingIntent pen =PendingIntent.getBroadcast(MainActivity.this,0,i,PendingIntent.FLAG_CANCEL_CURRENT);
//        AlarmManager al = (AlarmManager) getSystemService(MainActivity.ALARM_SERVICE);
//        al.setInexactRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),
//                1000*30,pen);
//        Log.d(tag, "alarme déclenchée par setAlarme");
//    }
//
//    private void setAlerte(){
//        alarmMgr = (AlarmManager)ct.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(ct, MonRecepteur.class);
//        alarmIntent = PendingIntent.getBroadcast(ct, 0, intent, 0);
//
//        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime() +
//                        30 * 1000, alarmIntent);
//        Log.d(tag, "alarme déclenchée par setAlerte");
//    }


    protected void onDestroy(){
        super.onDestroy();
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FROM_FIRST_ACTIVITY_REQUEST)
        {
            switch (resultCode)
            {
                case RESULT_OK:
                    String ilibRdv = data.getStringExtra("libRdv");
                    String idateRdv = data.getStringExtra("dateRdv");

                    ///textnom.setText(result1);
                    //textprenom.setText(result2);
                    break;

                case RESULT_CANCELED:
                    //textlogin.setText("");
                    break;
            }
        }

    }

    //Date du jour au format alphabétique
    public String lejour() {
        final Date date = new Date();
        return new SimpleDateFormat("EEEE d MMM yyyy").format(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
//            case R.id.action_accueil:
//                //Bouton "Accueil"
//                Intent i=new Intent(RdvListeFutur.this, MainActivity.class);
//                startActivity(i);
//                return true;
//            case R.id.menu_help:
//                // Comportement du bouton "Aide"
//                return true;
//            case R.id.menu_refresh:
//                // Comportement du bouton "Rafraichir"
//                return true;
            case R.id.action_alarme:
                // Comportement du bouton "Recherche"
                //setAlarme();
                return true;
            case R.id.action_quitter:
                // Comportement du bouton "Paramètres"
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
    }

    public void messAlert(View v){
        AlertDialog.Builder mes = new AlertDialog.Builder(ct);
        mes.setMessage("c'est un messag ok!!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setTitle("Bienvenue").setIcon(R.drawable.autoriser).create();
        mes.show();

    }


}
