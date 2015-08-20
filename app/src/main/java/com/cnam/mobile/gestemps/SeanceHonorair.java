package com.cnam.mobile.gestemps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SeanceHonorair extends AppCompatActivity {

    Context ct = this;
    Button btnSeanceNext;
    Button btnSeanceNew;
    Button btnlisteRdv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance_honorair);


        final String tag = "SeanceFin-test";

        TextView prenom = (TextView) findViewById(R.id.prenomPersView);
        TextView nom = (TextView) findViewById(R.id.nomPersView);
        TextView time = (TextView) findViewById(R.id.timeView);
        TextView niveau = (TextView) findViewById(R.id.niveauRdvView);
        TextView duree = (TextView) findViewById(R.id.dureeRdvView);
        TextView pointDeb = (TextView) findViewById(R.id.pointDebRdvView);
        TextView pointFin = (TextView) findViewById(R.id.pointFinRdvView);
        TextView dureeSeance = (TextView) findViewById(R.id.dureeSeanceView);
        TextView montantSeance = (TextView) findViewById(R.id.montantSeanceView);

        btnSeanceNext = (Button) findViewById(R.id.btnSeanceNext);
        btnSeanceNew = (Button) findViewById(R.id.btnSeanceNew);
        btnlisteRdv = (Button) findViewById(R.id.btnListeRdv);

//        Intent i = getIntent();
//        final long iidRdv = i.getLongExtra("idRdv", 1);
//        final String iprenom = i.getStringExtra("prenom");
//        final String inom = i.getStringExtra("nom");
//
//        final String iniveau = i.getStringExtra("niveauRdv");
//        final String idureeRdv = i.getStringExtra("dureeRdv");
//        //final long idureeRdv = i.getLongExtra("dureeRdv", 0);
//        final String ipointDeb = i.getStringExtra("pointDeb");
//        final String ipointFin = i.getStringExtra("pointFin");
//        final String idureeSeance = i.getStringExtra("dureeSeance");
//        final String imontantSeance = i.getStringExtra("montantRdv");
//        final long iidPers = i.getLongExtra("idPers", 1);
//
//
//        prenom.setText(iprenom);
//        nom.setText(inom);
//        niveau.setText(iniveau);
//        //duree.setText(String.valueOf(idureeRdv));
//        duree.setText(idureeRdv);
//        pointDeb.setText(ipointDeb);
//        pointFin.setText(ipointFin);
//        dureeSeance.setText(idureeSeance);
//        montantSeance.setText(imontantSeance);

        //Bouton SEANCE SUIVANTE
        View.OnClickListener ecoute1=new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                //stopService(v);
                Intent i=new Intent(SeanceHonorair.this, RdvListeFutur.class);
                startActivity(i);
                finish();
            }
        };
        btnSeanceNext.setOnClickListener(ecoute1);

        //Bouton SEANCE NON PREVU
        View.OnClickListener ecoute2 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                //stopService(v);
                Intent i=new Intent(SeanceHonorair.this, RdvCreation.class);
                startActivity(i);
                finish();
            }
        };
        btnSeanceNew.setOnClickListener(ecoute2);


        //Bouton LISTE DES RDV
        View.OnClickListener ecoute3 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                //stopService(v);
                Intent i=new Intent(SeanceHonorair.this, RdvListe.class);
                startActivity(i);
            }
        };
        btnlisteRdv.setOnClickListener(ecoute3);

        //Bouton PAIEMENT
        View.OnClickListener ecoute4 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                //stopService(v);
                Intent i=new Intent(SeanceHonorair.this, SeanceHonorair.class);
                startActivity(i);
            }
        };
        btnlisteRdv.setOnClickListener(ecoute4);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_seance_honorair, menu);
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
