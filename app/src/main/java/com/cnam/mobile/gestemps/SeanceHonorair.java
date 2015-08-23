package com.cnam.mobile.gestemps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static java.lang.Float.parseFloat;

public class SeanceHonorair extends AppCompatActivity {

    Context ct = this;
    Button btnValider;
    Button btnQuitter;
    String paiementRec;
    PersonneDAO persdao;
    Personne p;


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
        final EditText paiementRdv = (EditText) findViewById(R.id.paiementEdit);

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
        persdao = new PersonneDAO(ct);
        persdao.open();
        p = (Personne) persdao.getPersonneById(iidPers);
        String iprenom = p.getPrenomPers();
        String inom = p.getNomPers();
        float isolde = p.getSoldePers();
        float itarif = r.getTarifRdv();
        // long iduree = r.getDureeRdv();

        String iitarif = "Tarif : "+String.valueOf(itarif)+" euros/h";
        String iisolde = "Solde du compte : "+String.valueOf(isolde)+" euros";
        String iidureeSeance = "Durée de la séance : "+idureeSeance;
        String iimontantSeance = "Montant de la séance : "+imontantSeance;

        prenom.setText(iprenom);
        nom.setText(inom);

        dureeRdv.setText(idureeRdv);
        tarif.setText(iitarif);
        solde.setText(iisolde);
        dureeSeance.setText(iidureeSeance);
        montantSeance.setText(iimontantSeance);

        //Bouton VALIDER
        //Valider le montant reçu par la personne
        View.OnClickListener ecoute1=new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                paiementRec = paiementRdv.getText().toString();

                if (paiementRec.equals("")){
                    Toast.makeText(getBaseContext(), "Il n'y a aucun paiement !",
                            Toast.LENGTH_LONG).show();
                }else{
//                    p.setSoldePers(p.totalSolde(parseFloat(paiementRec)));
//                    persdao.modifier(p);
                    paiementConf(v);
                    Log.d(tag, "Le solde de " + p.getNomPers() + " est modifié");
                    //finish();
                }

            }
        };
        btnValider.setOnClickListener(ecoute1);

        //Bouton QUITTER
        View.OnClickListener ecoute2 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                paiementRec = "0";
                nouveauSolde(paiementRec);
                finish();
            }
        };
        btnQuitter.setOnClickListener(ecoute2);




    }


    //Calcul du nouveau solde
    public void paiementConf(View v){
        AlertDialog.Builder mes = new AlertDialog.Builder(ct);
        mes.setMessage("Vous avez reçu un paiement ?").setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nouveauSolde(paiementRec);
                dialog.dismiss();
                finish();
            }
        }).setTitle("Confirmation paiement").setIcon(R.drawable.autoriser).create();
        mes.setNegativeButton("Non",null);
        mes.show();
    }

    public void nouveauSolde(String versement){
        p.setSoldePers(p.creditSolde(parseFloat(versement)));
        persdao.modifier(p);

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
