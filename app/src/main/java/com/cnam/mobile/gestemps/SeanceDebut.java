package com.cnam.mobile.gestemps;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class SeanceDebut extends AppCompatActivity {

    Context ct = this;
    Button btnTerminer;
    Button btnAnnuler;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private PendingIntent pendingIntent;

    private static final String tag = "sam";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance_debut);


        TextView prenom = (TextView) findViewById(R.id.prenomPersView);
        TextView nom = (TextView) findViewById(R.id.nomPersView);
        TextView duree = (TextView) findViewById(R.id.dureeRdvView);
        TextView niveau = (TextView) findViewById(R.id.niveauRdvView);
        TextView solde = (TextView) findViewById(R.id.soldeView);
        //final TextView time = (TextView) findViewById(R.id.timeView);
        TextView pointDeb = (TextView) findViewById(R.id.pointDebRdvView);

        btnTerminer = (Button) findViewById(R.id.btnTerminer);
        btnAnnuler = (Button) findViewById(R.id.btnAnnuler);



        Intent i = getIntent();
        final long iidRdv = i.getLongExtra("idRdv", 1);
        final String iprenom = i.getStringExtra("prenom");
        final String inom = i.getStringExtra("nom");
        final String idureeRdv = i.getStringExtra("dureeRdv");
        final String iniveau = i.getStringExtra("niveauRdv");
        final float isolde = i.getFloatExtra("solde",0);
        //final long idureeRdv = i.getLongExtra("dureeRdv", 0);
        final String ipointDeb = i.getStringExtra("pointDeb");
        final String imontantRdv = i.getStringExtra("montantRdv");
        final long iidPers = i.getLongExtra("idPers", 1);

        String iisolde = "Solde du compte : "+String.valueOf(isolde)+" euros";

        prenom.setText(iprenom);
        nom.setText(inom);
        niveau.setText(iniveau);

        solde.setText(iisolde);
        //duree.setText(String.valueOf(idureeRdv));
        duree.setText(idureeRdv);
        pointDeb.setText(ipointDeb);


//        //Alarme service
//        Calendar calendar = Calendar.getInstance();
////
//////        calendar.set(Calendar.MONTH, 9);
//////        calendar.set(Calendar.YEAR, 2015);
//////        calendar.set(Calendar.DAY_OF_MONTH, 18);
////
//        calendar.set(Calendar.HOUR_OF_DAY, 5);
//        calendar.set(Calendar.MINUTE, 30);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.AM_PM,Calendar.AM);
//
//        Intent myIntent = new Intent(SeanceDebut.this, MonRecepteur.class);
//        pendingIntent = PendingIntent.getBroadcast(SeanceDebut.this, 0, myIntent,0);
//
//        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

        RdvDAO rdvdao = new RdvDAO(ct);
        rdvdao.open();
        Rdv r = (Rdv) rdvdao.getRdvById(iidRdv);

        final long dur = r.getDureeRdv();
        final long tps = r.getPointDebRdv()+dur-timeStamp();

        if (tps<0){
            Log.d(tag, "temps écoulé");
            Toast.makeText(getBaseContext(), "Séance est terminée!",
                    Toast.LENGTH_LONG).show();
            finish();

        }

        setAlerte(dur);

        //Bouton TERMINE LA SEANCE
        View.OnClickListener ecoute1 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                final String ipointFin = timeJour();

                stopService(v);

                RdvDAO rdvdao = new RdvDAO(ct);
                rdvdao.open();
                Rdv r = (Rdv) rdvdao.getRdvById(iidRdv);
                r.setPointFinRdv(timeStamp());
                rdvdao.modifier(r);

                Rdv rdv2 = (Rdv) rdvdao.getRdvById(iidRdv);
                long pointF = rdv2.getPointFinRdv();
                long pointD = rdv2.getPointDebRdv();
                String idureeSeance = "rien";

                idureeSeance = rdv2.diffDateTime(pointF, pointD);
                long m = rdv2.montantSeance(pointF,pointD);
                String iimontantRdv = String.valueOf(m)+" euros";

                Intent i=new Intent(SeanceDebut.this, SeanceFin.class);
                i.putExtra("idRdv", iidRdv);
//                i.putExtra("libRdv", ilibRdv);
                i.putExtra("prenom", iprenom);
                i.putExtra("nom", inom);
                i.putExtra("niveauRdv", iniveau);
                i.putExtra("dureeRdv", idureeRdv);
                i.putExtra("solde",isolde);
                i.putExtra("pointDeb", ipointDeb);
                i.putExtra("pointFin", ipointFin);
                i.putExtra("dureeSeance", idureeSeance);
                i.putExtra("montantRdv", iimontantRdv);
                i.putExtra("idPers", iidPers);
                startActivity(i);
                finish();
            }
        };
        btnTerminer.setOnClickListener(ecoute1);

        View.OnClickListener ecoute2 = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //setResult(RESULT_CANCELED);
                //startActivity(SeanceDebut.this, SeanceDebut.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                stopService(v);
                finish();
            }

        };
        btnAnnuler.setOnClickListener(ecoute2);


//        alarmMgr = (AlarmManager)ct.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(ct, MonRecepteur.class);
//        alarmIntent = PendingIntent.getBroadcast(ct, 0, intent, 0);
//
//        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime() +
//                        30 * 1000, alarmIntent);

    }

//    public void onResume(){
//        super.onResume();
//
//
//        alarmMgr = (AlarmManager)ct.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(ct, MainActivity.class);
//        alarmIntent = PendingIntent.getBroadcast(ct, 0, intent, 0);
//
//        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime() +
//                        60 * 1000, alarmIntent);
//    }

    private void setAlerte(long duree){
        alarmMgr = (AlarmManager)ct.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(ct, MonRecepteur.class);
        alarmIntent = PendingIntent.getBroadcast(ct, 0, intent, 0);

        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        duree, alarmIntent);
        Log.d(tag, "alarme déclenchée par setAlerte SeanceDebut");
    }

    public long changeEnInt(String s){
        return parseInt(s,10);
    }

    //Pointage
    public long timeStamp(){
        final Date date = new Date();
        return date.getTime();
    }

    //Date du jour au format alphabétique
    public String timeJour() {
        final Date date = new Date();
        return new SimpleDateFormat("dd/MM/yyyy 'à' hh:mm").format(date);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_seance_debut, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_accueil:

                finish();

            case R.id.action_settings:
                // Comportement du bouton "Paramètres"
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void startService(View v){
        startService(new Intent(getBaseContext(),MonServiceAlarm.class));

    }

    public void stopService(View v){
        stopService(new Intent(getBaseContext(),MonServiceAlarm.class));
    }
}
