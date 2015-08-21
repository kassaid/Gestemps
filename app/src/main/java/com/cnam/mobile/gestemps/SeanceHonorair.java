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
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SeanceHonorair extends AppCompatActivity {

    Context ct = this;
    Button btnValider;
    Button btnQuitter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance_honorair);


        final String tag = "SeanceFin-test";

        TextView prenom = (TextView) findViewById(R.id.prenomPersView);
        TextView nom = (TextView) findViewById(R.id.nomPersView);
        TextView tarif = (TextView) findViewById(R.id.tarifView);
        TextView dureeRdv = (TextView) findViewById(R.id.dureeRdvView);
        TextView solde = (TextView) findViewById(R.id.soldeView);
        TextView dureeSeance = (TextView) findViewById(R.id.dureeSeanceView);
        TextView montantSeance = (TextView) findViewById(R.id.montantSeanceView);
        EditText paiement = (EditText) findViewById(R.id.paiementEdit);

        btnValider = (Button) findViewById(R.id.btnValider);
        btnQuitter = (Button) findViewById(R.id.btnQuitter);


        Intent i = getIntent();
        final long iidRdv = i.getLongExtra("idRdv", 1);
        final long iidPers = i.getLongExtra("idPers", 1);
//        final String iprenom = i.getStringExtra("prenom");
//        final String inom = i.getStringExtra("nom");
//        //final float isolde = i.getFloatExtra("solde", 0);
//        //final String iniveau = i.getStringExtra("niveauRdv");
//        //final float itarif = i.getFloatExtra("tarif", 0);
        final String idureeRdv = i.getStringExtra("dureeRdv");
//        //final long idureeRdv = i.getLongExtra("dureeRdv", 0);
//        //final String ipointDeb = i.getStringExtra("pointDeb");
//        //final String ipointFin = i.getStringExtra("pointFin");
        final String idureeSeance = i.getStringExtra("dureeSeance");
        final String imontantSeance = i.getStringExtra("montantSeance");
//        final long iidPers = i.getLongExtra("idPers", 1);

        RdvDAO rdvdao = new RdvDAO(ct);
        rdvdao.open();
        Rdv r = (Rdv) rdvdao.getRdvById(iidRdv);
        PersonneDAO persdao = new PersonneDAO(ct);
        persdao.open();
        Personne p = (Personne) persdao.getPersonneById(iidPers);
        String iprenom = p.getPrenomPers();
        String inom = p.getNomPers();
        float isolde = p.getSoldePers();
        float itarif = r.getTarifRdv();
       // long iduree = r.getDureeRdv();

        String iitarif = "Tarif : "+String.valueOf(itarif)+" euros/h";
        String iisolde = "Solde du compte : "+String.valueOf(isolde)+" euros";
        String iidureeSeance = "Durée : "+idureeSeance;
        String iimontantSeance = "Montant de la séance : "+imontantSeance;

        prenom.setText(iprenom);
        nom.setText(inom);

        dureeRdv.setText(idureeRdv);
        tarif.setText(iitarif);
        solde.setText(iisolde);
        dureeSeance.setText(iidureeSeance);
        montantSeance.setText(iimontantSeance);

        //Bouton SEANCE SUIVANTE
        View.OnClickListener ecoute1=new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                //stopService(v);
//                Intent i=new Intent(SeanceHonorair.this, RdvListeFutur.class);
//                startActivity(i);
                finish();
            }
        };
        btnValider.setOnClickListener(ecoute1);

        //Bouton SEANCE NON PREVU
        View.OnClickListener ecoute2 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                //stopService(v);
//                Intent i=new Intent(SeanceHonorair.this, RdvListe.class);
//                startActivity(i);
                finish();
            }
        };
        btnQuitter.setOnClickListener(ecoute2);




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
