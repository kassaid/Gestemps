package com.cnam.mobile.gestemps;

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

public class SeanceFin extends AppCompatActivity {


    Context ct = this;
    Button btnSeanceNext;
    Button btnSeanceNew;
    Button btnListeRdv;
    Button btnPaiement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance_fin);

        final String tag = "seanceFin-test";

        TextView titre = (TextView) findViewById(R.id.titreView);
        TextView prenom = (TextView) findViewById(R.id.prenomPersView);
        TextView nom = (TextView) findViewById(R.id.nomPersView);
       // TextView time = (TextView) findViewById(R.id.timeView);
        TextView niveau = (TextView) findViewById(R.id.niveauRdvView);
        TextView duree = (TextView) findViewById(R.id.dureeRdvView);
        TextView solde = (TextView) findViewById(R.id.soldeView);
        TextView pointDeb = (TextView) findViewById(R.id.pointDebRdvView);
        TextView pointFin = (TextView) findViewById(R.id.pointFinRdvView);
        TextView dureeSeance = (TextView) findViewById(R.id.dureeSeanceView);
        TextView montantSeance = (TextView) findViewById(R.id.montantSeanceView);

        btnSeanceNext = (Button) findViewById(R.id.btnSeanceNext);
        btnSeanceNew = (Button) findViewById(R.id.btnSeanceNew);
        btnListeRdv = (Button) findViewById(R.id.btnListeRdv);
        btnPaiement = (Button) findViewById(R.id.btnPaiement);

        Intent i = getIntent();
        final long iidRdv = i.getLongExtra("idRdv", 1);
        final String ititre = i.getStringExtra("titre");
        final String iprenom = i.getStringExtra("prenom");
        final String inom = i.getStringExtra("nom");

        final String iniveau = i.getStringExtra("niveauRdv");
        final String idureeRdv = i.getStringExtra("dureeRdv");
        final float isolde = i.getFloatExtra("solde", 0);
        //final long idureeRdv = i.getLongExtra("dureeRdv", 0);
        final String ipointDeb = i.getStringExtra("pointDeb");
        final String ipointFin = i.getStringExtra("pointFin");
        final String idureeSeance = i.getStringExtra("dureeSeance");
        final String imontantSeance = i.getStringExtra("montantSeance");
        final long iidPers = i.getLongExtra("idPers", 1);


        //String ititre = "RDV du "+changeDateTime(idateRdv);
        String iisolde = "Solde du compte : "+String.valueOf(isolde)+" euros";

        titre.setText(ititre);
        prenom.setText(iprenom);
        nom.setText(inom);
        niveau.setText(iniveau);
        duree.setText(idureeRdv);
        solde.setText(iisolde);
        pointDeb.setText(ipointDeb);
        pointFin.setText(ipointFin);
        dureeSeance.setText(idureeSeance);
        montantSeance.setText(imontantSeance);


        //Bouton SEANCE SUIVANTE
        View.OnClickListener ecoute1=new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                stopService(v);
                Intent i=new Intent(SeanceFin.this, RdvListeFutur.class);
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
                stopService(v);
                Intent i=new Intent(SeanceFin.this, RdvCreation.class);
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
                Intent i=new Intent(SeanceFin.this, RdvListe.class);
                startActivity(i);
            }
        };
        btnListeRdv.setOnClickListener(ecoute3);

        //Bouton PAIEMENT
        View.OnClickListener ecoute4 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                stopService(v);
                Intent i=new Intent(SeanceFin.this, SeanceHonorair.class);
                i.putExtra("idRdv",iidRdv);
                i.putExtra("idPers",iidPers);
                i.putExtra("dureeRdv",idureeRdv);
                i.putExtra("dureeSeance",idureeSeance);
                i.putExtra("montantSeance",imontantSeance);
                startActivity(i);
                finish();
            }
        };
        btnPaiement.setOnClickListener(ecoute4);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_seance_fin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_accueil:
                //Bouton "Accueil"
                finish();

            case R.id.action_settings:
                // Comportement du bouton "Paramètres"
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void stopService(View v){
        stopService(new Intent(getBaseContext(),MonServiceAlarm.class));
    }
}
