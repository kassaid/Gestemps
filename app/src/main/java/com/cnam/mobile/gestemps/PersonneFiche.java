package com.cnam.mobile.gestemps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
    String inom,iprenom;

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
        inom = i.getStringExtra("nomPers");
        iprenom = i.getStringExtra("prenomPers");
        final String iadresse = i.getStringExtra("adresPers");
        final String itel = i.getStringExtra("telPers");
        final String imail = i.getStringExtra("mailPers");
        final float isolde = i.getFloatExtra("soldePers", 0);
        final String iinfo = i.getStringExtra("infoPers");

        String iisolde = "Le solde du compte est de  "+String.valueOf(isolde)+" euros";

        rdvdao = new RdvDAO(ct);
        rdvdao.open();
        rdv = rdvdao.getRdvByIdPersAndTime(iidPers,rdvdao.timeStamp());
        if (rdv!=null){
            irdvPers = "La prochaine séance aura lieu le  "+rdv.changeDate(rdv.getDateRdv())+".";
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

        //Bouton MODIFIER
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
                i.putExtra("soldePers",isolde);
                i.putExtra("infoPers", iinfo);

                startActivity(i);
                finish();
            }
        };
        btnModifier.setOnClickListener(ecoute2);

        //Clic sur le numéro de téléphone
        View.OnClickListener ecoute3 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                appelConf(itel);
            }
        };
        tel.setOnClickListener(ecoute3);

        //Clic sur l'adresse
        View.OnClickListener ecoute4 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                addressConf(iadresse);
            }
        };
        adresse.setOnClickListener(ecoute4);



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
//    //Date au format alphabétique
//    public String lejour() {
//        final Date date = new Date();
//        return new SimpleDateFormat("EEEE d MMM yyyy").format(date);
//    }

    //Navigation adresse
    public void addressConf(final String adress){
        AlertDialog.Builder mes = new AlertDialog.Builder(ct);
        mes.setMessage("Souhaitez-vous allez vers " + iprenom + " " + inom + "?").setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AdressUri(adress);
                dialog.dismiss();
            }
        }).setTitle("Adresse").setIcon(R.drawable.logo_a6t_48).create();
        mes.setNegativeButton("Non",null);
        mes.show();
    }

    // conversion adresse vers uri
    public void AdressUri(String sEdit) {
        Uri uri;
        Uri uriDefault = Uri.parse("geo:0,0?q=Place+Charles+de+Gaulle+%2C+PARIS");
        if(sEdit.length()!=0) {
            String geo = "geo:0,0?q=" +
                    sEdit.replace(" ", "+");
            uri = Uri.parse(geo);
        }
        else uri = uriDefault;
        //Toast toast = Toast.makeText(getApplicationContext(), "(" + sEdit.length() + ") " + uri.toString(), Toast.LENGTH_SHORT);
        //toast.show();
        showMap(uri);
    }

    // envoi d'uri au système
    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

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
