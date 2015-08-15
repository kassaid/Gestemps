package com.cnam.mobile.gestemps;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SeanceDebut extends ActionBarActivity {

    Context ct = this;
    Button btnTerminer;
    Button btnAnnuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance_debut);

        final String tag = "SeanceDebut-test";

        TextView prenom = (TextView) findViewById(R.id.prenomPersView);
        TextView nom = (TextView) findViewById(R.id.nomPersView);
        final TextView time = (TextView) findViewById(R.id.timeView);
        TextView niveau = (TextView) findViewById(R.id.niveauRdvView);
        TextView duree = (TextView) findViewById(R.id.dureeRdvView);
        TextView pointDeb = (TextView) findViewById(R.id.pointDebRdvView);

        btnTerminer = (Button) findViewById(R.id.btnTerminer);
        btnAnnuler = (Button) findViewById(R.id.btnAnnuler);



        Intent i = getIntent();
        final long iidRdv = i.getLongExtra("idRdv", 1);
        final String iprenom = i.getStringExtra("prenom");
        final String inom = i.getStringExtra("nom");
        final String iniveau = i.getStringExtra("niveauRdv");
        final String iduree = i.getStringExtra("dureeRdv");
        final String ipointDeb = i.getStringExtra("pointDeb");
        final long iidPers = i.getLongExtra("idPers", 1);



        prenom.setText(iprenom);
        nom.setText(inom);
        niveau.setText(iniveau);
        duree.setText(iduree);
        pointDeb.setText(ipointDeb);


        //Bouton TERMINE LA SEANCE
        View.OnClickListener ecoute1 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                final String ipointFin = timeJour();

                RdvDAO rdvdao = new RdvDAO(ct);
                rdvdao.open();
                Rdv rdv = rdvdao.getRdvById(iidRdv);
                rdv.setPointFinRdv(timeStamp());

                rdvdao.modifier(rdv);
//                Rdv rdv2 = rdvdao.getRdvById(iidRdv);
                long pointF = timeStamp()+1000;
                long pointD = timeStamp()-25600;
                String idureeSeance = null;
//                try {
                    //idureeSeance = rdv2.diffDateTime(rdv2.changeDate(ipointFin),rdv2.changeDate(ipointDeb));
                    idureeSeance = diffDateTime(pointF, pointD);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }


                Intent i=new Intent(SeanceDebut.this, SeanceFin.class);
                i.putExtra("idRdv", iidRdv);
//                i.putExtra("libRdv", ilibRdv);
                i.putExtra("prenom", iprenom);
                i.putExtra("nom", inom);
                i.putExtra("niveauRdv", iniveau);
                i.putExtra("dureeRdv", iduree);
                i.putExtra("pointDeb", ipointDeb);
                i.putExtra("pointFin", ipointFin);
                i.putExtra("dureeSeance", idureeSeance);
                i.putExtra("idPers", iidPers);
                startActivity(i);
                finish();
            }
        };
        btnTerminer.setOnClickListener(ecoute1);

        View.OnClickListener ecoute2 = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                RdvDAO rdvdao = new RdvDAO(ct);
//                rdvdao.open();
//                Rdv rdv = rdvdao.getRdvById(iidRdv);
//                rdv.setPointDebRdv(timeStamp());
//                rdvdao.modifier(rdv);
//                pointDeb.setText(ipointDeb);

                //setResult(RESULT_CANCELED);
                finish();
            }

        };
        btnAnnuler.setOnClickListener(ecoute2);

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

    public String diffDateTime(long recent,long ancien){
        long diff = recent - ancien;
        long seconds=0;
        long minutes=0;
        long hours=0;
        long days=0;

        while(diff>1000){
            diff=diff-1000;
            seconds++;
            if(seconds==60){
                seconds=0;
                minutes++;
            }

            if(minutes==60){
                minutes=0;
                hours++;
            }

            if(hours==24){
                hours=0;
                days++;
            }
        }

        String inter=""+days+"jours "+hours+"h "+minutes+"min "+seconds+"s";
        return inter;

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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
