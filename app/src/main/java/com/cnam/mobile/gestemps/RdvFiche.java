package com.cnam.mobile.gestemps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
    Button btnAnnuler;
    Button btnModifier;
    String iprenom,inom;

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

        btnAnnuler = (Button) findViewById(R.id.btnAnnuler);
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
        final long ipaiementRdv = i.getLongExtra("paiementRdv",0);




        PersonneDAO persdao = new PersonneDAO(ct);
        persdao.open();
        Personne pers = persdao.getPersonneById(iidPers);

        inom = pers.getNomPers();
        iprenom = pers.getPrenomPers();
        final String itel = pers.getTelPers();
        Float isolde = pers.getSoldePers();

        final String iidateRdv = "RDV du "+changeDateTime(idateRdv);
        final String iidateRdv2 = changeDate(idateRdv);
        final String ihoraireRdv = changeTime(idateRdv);
        final String iiniveauRdv = "Niveau "+iniveauRdv;
        //final String iduree = "Séance de "+String.valueOf(idureeRdv/10000)+" h";
        final String iitarifRdv = "Tarif : "+String.valueOf(itarifRdv)+" euros/h";
        final String iidureeRdv = "Durée prévue de "+String.valueOf(idureeRdv)+" h";
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
        View.OnClickListener ecoute1 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(RdvFiche.this, RdvModif.class);
                i.putExtra("idRdv", iidRdv);
                //i.putExtra("libRdv", ilibRdv);
                i.putExtra("dateRdv", iidateRdv2);
                i.putExtra("horaireRdv", ihoraireRdv);
                i.putExtra("nomPers",inom);
                i.putExtra("prenomPers",iprenom);
                i.putExtra("dureeRdv", idureeRdv);
                i.putExtra("niveauRdv", iniveauRdv);
                i.putExtra("tarifRdv", itarifRdv);
                i.putExtra("paiementRdv", ipaiementRdv);
                i.putExtra("adresRdv", iadresRdv);
                i.putExtra("infoRdv", iinfoRdv);
                i.putExtra("idPers", iidPers);
                startActivity(i);
                finish();
            }
        };
        btnModifier.setOnClickListener(ecoute1);

        //Bouton ANNULER
        View.OnClickListener ecoute2 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                finish();
            }
        };
        btnAnnuler.setOnClickListener(ecoute2);

        //Clic sur le numéro de téléphone
        View.OnClickListener ecoute3 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                appelConf(itel);
            }
        };
        telPers.setOnClickListener(ecoute3);


    }

    //Change date-heure en string
    public String changeDateTime(long d) {
        final Date date = new Date();
        date.setTime(d);
        return new SimpleDateFormat("dd/MM/yyyy 'à' hh:mm").format(date);
    }

    //Change date seule en string
    public String changeDate(long d) {
        final Date date = new Date();
        date.setTime(d);
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    //Change heure seule en string
    public String changeTime(long d) {
        final Date date = new Date();
        date.setTime(d);
        return new SimpleDateFormat("hh:mm").format(date);
    }

    //Appel téléphonique
    public void appelConf(final String num){
        AlertDialog.Builder mes = new AlertDialog.Builder(ct);
        mes.setMessage("Souhaitez-vous appeler "+iprenom+" "+inom+" ?").setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                appelTel(num);
                dialog.dismiss();
            }
        }).setTitle("Téléphone").setIcon(R.drawable.logo_a6t_48).create();
        mes.setNegativeButton("Non",null);
        mes.show();
    }

    //Creation de l'intent appel telephonique
    public void appelTel(final String num){
        Intent i = new Intent(android.content.Intent.ACTION_CALL, Uri.parse("tel:" + num));
        startActivity(i);
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
