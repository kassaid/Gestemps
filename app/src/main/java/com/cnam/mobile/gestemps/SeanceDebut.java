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

import com.google.android.gms.plus.model.people.Person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Float.parseFloat;
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



        TextView titre = (TextView) findViewById(R.id.titreView);
        TextView prenom = (TextView) findViewById(R.id.prenomPersView);
        TextView nom = (TextView) findViewById(R.id.nomPersView);
        TextView duree = (TextView) findViewById(R.id.dureeRdvView);
        TextView heureFin = (TextView) findViewById(R.id.heureFinView);
        TextView niveau = (TextView) findViewById(R.id.niveauRdvView);
        TextView solde = (TextView) findViewById(R.id.soldeView);
        TextView info = (TextView) findViewById(R.id.infoRdvView);
        //final TextView time = (TextView) findViewById(R.id.timeView);
        TextView pointDeb = (TextView) findViewById(R.id.pointDebRdvView);

        btnTerminer = (Button) findViewById(R.id.btnTerminer);
        btnAnnuler = (Button) findViewById(R.id.btnAnnuler);


        Intent i = getIntent();
        final long iidRdv = i.getLongExtra("idRdv", 1);
        final String iadresRdv = i.getStringExtra("adresRdv");
        final String iprenom = i.getStringExtra("prenom");
        final String inom = i.getStringExtra("nom");
        final long idateRdv = i.getLongExtra("dateRdv", 0);
        final long idureeRdv = i.getLongExtra("dureeRdv", 0);
        //final String idureeRdv = i.getStringExtra("dureeRdv");
        final String iniveauRdv = i.getStringExtra("niveauRdv");
        final float isolde = i.getFloatExtra("solde", 0);
        final String iinfo = i.getStringExtra("info");
        //final long idureeRdv = i.getLongExtra("dureeRdv", 0);
        final String ipointDeb = i.getStringExtra("pointDeb");
        //final String ipaiementRdv = i.getStringExtra("paiementRdv");
        final long iidPers = i.getLongExtra("idPers", 1);

        final String ititre = "RDV du "+changeDateTime(idateRdv);
        final String iniveau = "Niveau d'étude : "+iniveauRdv;
        final String iduree = "Durée prévue : "+String.valueOf(idureeRdv)+" h";
        String iisolde = "Solde du compte : "+String.valueOf(isolde)+" euros";


        titre.setText(ititre);
        prenom.setText(iprenom);
        nom.setText(inom);
        niveau.setText(iniveau);
        duree.setText(iduree);
        solde.setText(iisolde);
        info.setText(iinfo);
        //duree.setText(String.valueOf(idureeRdv));
        pointDeb.setText(ipointDeb);


        RdvDAO rdvdao = new RdvDAO(ct);
        rdvdao.open();
        Rdv r = (Rdv) rdvdao.getRdvById(iidRdv);

        //dur: duree de la séance
        //Pour la démo 1h = 10 s
        final long dur = r.getDureeRdv()*1000*10;
        final long tps = r.getPointDebRdv()+dur - timeStamp();
        final long horaireFin = r.getPointDebRdv()+r.getDureeRdv()*1000*60*60;

        String iheureFin = "Fin de séance prévue à "+changeTime(horaireFin);
        heureFin.setText(iheureFin);



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

                //Arret du service alerte
                stopService(v);

                RdvDAO rdvdao = new RdvDAO(ct);
                rdvdao.open();
                Rdv r = (Rdv) rdvdao.getRdvById(iidRdv);
                r.setPointFinRdv(timeStamp());
                r.setLibRdv("SÉANCE TERMINÉE");
                rdvdao.modifier(r);

                Rdv rdv2 = (Rdv) rdvdao.getRdvById(iidRdv);
                long pointF = rdv2.getPointFinRdv();
                long pointD = rdv2.getPointDebRdv();
                String idureeSeance = "rien";

                idureeSeance = rdv2.diffDateTime(pointF, pointD);
                long m = rdv2.montantSeance(pointF,pointD);
                PersonneDAO persdao = new PersonneDAO(ct);
                persdao.open();
                Personne p = persdao.getPersonneById(iidPers);
                p.setSoldePers(p.debitSolde(m));
                persdao.modifier(p);
                //rdv2.setPaiementRdv(m);
                String imontantSeance = String.valueOf(m)+" euros";

                Intent i=new Intent(SeanceDebut.this, SeanceFin.class);
                i.putExtra("idRdv", iidRdv);
//                i.putExtra("libRdv", ilibRdv);
                i.putExtra("titre",ititre);
                i.putExtra("prenom", iprenom);
                i.putExtra("nom", inom);
                i.putExtra("niveauRdv", iniveauRdv);
                //i.putExtra("dateRdv", idateRdv);
                i.putExtra("dureeRdv", idureeRdv);
                i.putExtra("solde",isolde);
                i.putExtra("pointDeb", ipointDeb);
                i.putExtra("pointFin", ipointFin);
                i.putExtra("dureeSeance", idureeSeance);
                i.putExtra("montantSeance", imontantSeance);
                i.putExtra("infoRdv",iinfo);
                i.putExtra("idPers", iidPers);
                startActivity(i);
                finish();
            }
        };
        btnTerminer.setOnClickListener(ecoute1);

        View.OnClickListener ecoute2 = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //final String ipointFin = timeJour();

                //Arret du service alerte
                stopService(v);

                RdvDAO rdvdao = new RdvDAO(ct);
                rdvdao.open();
                Rdv r = (Rdv) rdvdao.getRdvById(iidRdv);
                r.setPointDebRdv(0);
                r.setLibRdv("SÉANCE NON TERMINÉE");
                rdvdao.modifier(r);


                Intent i=new Intent(SeanceDebut.this, SeanceAvant.class);
                i.putExtra("idRdv", iidRdv);
//                i.putExtra("libRdv", ilibRdv);
                i.putExtra("titre",ititre);
                i.putExtra("prenom", iprenom);
                i.putExtra("nom", inom);
                i.putExtra("niveauRdv", iniveauRdv);
                i.putExtra("dateRdv", idateRdv);
                i.putExtra("dureeRdv", idureeRdv);
                i.putExtra("solde",isolde);
                i.putExtra("pointDeb", ipointDeb);
                i.putExtra("adresRdv", iadresRdv);
//                i.putExtra("pointFin", ipointFin);
//                i.putExtra("dureeSeance", idureeSeance);
//                i.putExtra("montantSeance", imontantSeance);
                i.putExtra("infoRdv",iinfo);
                i.putExtra("idPers", iidPers);

                startActivity(i);
                finish();
            }

        };
        btnAnnuler.setOnClickListener(ecoute2);
    }


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
        return new SimpleDateFormat("dd/MM/yyyy 'à' HH:mm").format(date);
    }

    //Change date-heure en string
    public String changeDateTime(long d) {
        final Date date = new Date();
        date.setTime(d);
        return new SimpleDateFormat("dd/MM/yyyy 'à' HH:mm").format(date);
    }
    //Change heure seule en string
    public String changeTime(long d) {
        final Date date = new Date();
        date.setTime(d);
        return new SimpleDateFormat("HH:mm").format(date);
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
