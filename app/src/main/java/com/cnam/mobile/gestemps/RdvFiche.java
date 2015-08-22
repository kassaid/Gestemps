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

import java.text.SimpleDateFormat;
import java.util.Date;

public class RdvFiche extends AppCompatActivity {

    Context ct = this;
    Button btnCreationRdv;
    Button btnModifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdv_fiche);

        TextView dateRdv = (TextView) findViewById(R.id.dateRdvView);
        TextView nomPers = (TextView) findViewById(R.id.nomPersView);
        TextView prenomPers = (TextView) findViewById(R.id.prenomPersView);
        TextView adresRdv = (TextView) findViewById(R.id.adresRdvView);
        TextView telPers = (TextView) findViewById(R.id.telPersView);
        TextView niveauRdv = (TextView) findViewById(R.id.niveauRdvView);
        TextView tarifRdv = (TextView) findViewById(R.id.tarifRdvView);
        TextView dureeRdv = (TextView) findViewById(R.id.dureeRdvView);
        TextView paiementRdv = (TextView) findViewById(R.id.paiementRdvView);
        TextView infoRdv = (TextView) findViewById(R.id.infoRdvView);
        TextView soldePers = (TextView) findViewById(R.id.soldePersView);
        TextView rdvPers = (TextView) findViewById(R.id.rdvPersView);
//        TextView montantSeance = (TextView) findViewById(R.id.montantSeanceView);

        btnCreationRdv = (Button) findViewById(R.id.btnCreationRdv);
        btnModifier = (Button) findViewById(R.id.btnModifier);

        Intent i = getIntent();
        //final String ilibRdv = i.getStringExtra("libRdv");
        final long iidRdv = i.getLongExtra("idRdv", 1);
        final long iidPers = i.getLongExtra("idPers", 1);
        final long idateRdv = i.getLongExtra("dateRdv", 0);
        final String iadresRdv = i.getStringExtra("adresRdv");
        final String iniveauRdv = i.getStringExtra("niveauRdv");
        final float itarifRdv = i.getFloatExtra("tarifRdv", 0);
        final long idureeRdv = i.getLongExtra("dureeRdv", 0);
        final String iinfoRdv = i.getStringExtra("infoRdv");
        //final String ihoraireRdv = i.getStringExtra("horaireRdv");
        final String ipaiementRdv = i.getStringExtra("paiementRdv");




        PersonneDAO persdao = new PersonneDAO(ct);
        persdao.open();
        Personne pers = persdao.getPersonneById(iidPers);

        String inom = pers.getNomPers();
        String iprenom = pers.getPrenomPers();
        String itel = pers.getTelPers();
        Float isolde = pers.getSoldePers();

        final String iidateRdv = "RDV du "+changeDate(idateRdv);
        final String iiniveauRdv = "Niveau "+iniveauRdv;
        //final String iduree = "Séance de "+String.valueOf(idureeRdv/10000)+" h";
        final String iitarifRdv = "Tarif : "+String.valueOf(itarifRdv)+" euros/h";
        final String iidureeRdv = "Durée prévue de "+String.valueOf(idureeRdv/10000)+" h";
        final String iipaiementRdv = "Paiement reçu : "+String.valueOf(ipaiementRdv)+" euros";
        final String iisolde = "Solde du compte : "+String.valueOf(isolde)+" euros";


        //libRdv.setText(ilibRdv);
        dateRdv.setText(iidateRdv);
        prenomPers.setText(iprenom);
        nomPers.setText(inom);
        adresRdv.setText(iadresRdv);
        telPers.setText(itel);
        niveauRdv.setText(iiniveauRdv);
        tarifRdv.setText(iitarifRdv);
        dureeRdv.setText(iidureeRdv);
        paiementRdv.setText(iipaiementRdv);

        //horaireRdv.setText(ihoraire);
        infoRdv.setText(iinfoRdv);
        soldePers.setText(iisolde);

        //Bouton MODIFIER
        View.OnClickListener ecoute2 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
//                Intent i=new Intent(RdvFiche.this, PersonneModif.class);
//                i.putExtra("idRdv", iidRdv);
//                i.putExtra("nomPers", inom);
//                i.putExtra("prenomPers", iprenom);
//                i.putExtra("adresPers", iadresse);
//                i.putExtra("telPers", itel);
//                i.putExtra("mailPers", imail);
//                //i.putExtra("soldePers",isolde);
//                i.putExtra("infoPers", iinfo);
//
//                startActivity(i);
                finish();
            }
        };
        btnModifier.setOnClickListener(ecoute2);



    }

    //Change date en string
    public String changeDate(long d) {
        final Date date = new Date();
        date.setTime(d);
        return new SimpleDateFormat("dd/MM/yyyy 'à' hh:mm").format(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rdv_fiche, menu);
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
