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

import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonneFiche extends ActionBarActivity {

    Context ct = this;
    Button btnCreationRdv;
    Button btnModifier;
    RdvDAO rdvdao;
    Rdv rdv;
    String irdvPers = "Il n'y a aucun RDV de prévu.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personne_fiche);

        final String tag = "personneFiche-test";


        TextView nom = (TextView) findViewById(R.id.nomPersView);
        TextView prenom = (TextView) findViewById(R.id.prenomPersView);
        TextView adresse = (TextView) findViewById(R.id.adresPersView);
        TextView tel = (TextView) findViewById(R.id.telPersView);
        TextView mail = (TextView) findViewById(R.id.mailPersView);
        TextView solde = (TextView) findViewById(R.id.soldePersView);
        TextView info = (TextView) findViewById(R.id.infoPersView);
        TextView rdvPers = (TextView) findViewById(R.id.rdvPersView);
//        TextView montantSeance = (TextView) findViewById(R.id.montantSeanceView);

        btnCreationRdv = (Button) findViewById(R.id.btnCreationRdv);
        btnModifier = (Button) findViewById(R.id.btnModifier);


        Intent i = getIntent();
        final long iidPers = i.getLongExtra("idPers", 1);
        final String inom = i.getStringExtra("nomPers");
        final String iprenom = i.getStringExtra("prenomPers");
        final String iadresse = i.getStringExtra("adresPers");
        final String itel = i.getStringExtra("telPers");
        final String imail = i.getStringExtra("mailPers");
        final long isolde = i.getLongExtra("soldePers", 0);
        final String iinfo = i.getStringExtra("infoPers");

        String iisolde = "Le solde du compte est de "+String.valueOf(isolde)+" euros";

        rdvdao = new RdvDAO(ct);
        rdvdao.open();
        rdv = rdvdao.getRdvByIdPersAndTime(iidPers,rdvdao.timeStamp());
        if (rdv!=null){
            irdvPers = "La prochaine séance aura lieu le "+rdv.changeDate(rdv.getDateRdv())+".";
        }

        nom.setText(inom);
        prenom.setText(iprenom);
        adresse.setText(iadresse);
        tel.setText(itel);
        mail.setText(imail);
        solde.setText(iisolde);
        rdvPers.setText(irdvPers);
        info.setText(iinfo);

        //Bouton AJOUTER UN RDV
        View.OnClickListener ecoute1 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(PersonneFiche.this, RdvCreation.class);
                //i.putExtra("idPers", iidPers);
                startActivity(i);
                finish();
            }
        };
        btnCreationRdv.setOnClickListener(ecoute1);

        //Bouton AJOUTER UN RDV
        View.OnClickListener ecoute2 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(PersonneFiche.this, PersonneModif.class);
                i.putExtra("idPers", iidPers);
                i.putExtra("nomPers", inom);
                i.putExtra("prenomPers", iprenom);
                i.putExtra("adresPers", iadresse);
                i.putExtra("telPers", itel);
                i.putExtra("mailPers", imail);
                //i.putExtra("soldePers",isolde);
                i.putExtra("infoPers", iinfo);

                startActivity(i);
                finish();
            }
        };
        btnModifier.setOnClickListener(ecoute2);



    }

//    //Date au format alphabétique
//    public String lejour() {
//        final Date date = new Date();
//        return new SimpleDateFormat("EEEE d MMM yyyy").format(date);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_personne_fiche, menu);
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
