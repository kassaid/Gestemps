package com.cnam.mobile.gestemps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SeanceAvant extends AppCompatActivity {

    Context ct = this;
    TextView nomPers;
    Button btnSmsRetard;
    Button btnSmsAnnule;
    Button btnArrive;
    Button btnModifier;
    String num_tel = "0623154373";
    String mess_retard = "Désolé je serais en retard";
    String mess_annule = "Désolé, suite à un empêchement j'annule le cours";
    String iprenom,inom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance_avant);


        final String tag = "seanceAvant-test";

        //TextView libRdv = (TextView) findViewById(R.id.libRdvView);
        nomPers = (TextView) findViewById(R.id.nomPersView);
        TextView prenomPers = (TextView) findViewById(R.id.prenomPersView);
        TextView niveauRdv = (TextView) findViewById(R.id.niveauRdvView);
        TextView dureeRdv = (TextView) findViewById(R.id.dureeRdvView);
        TextView horaireRdv = (TextView) findViewById(R.id.horaireRdvView);
        TextView adresRdv = (TextView) findViewById(R.id.adresRdvView);

        btnSmsRetard = (Button) findViewById(R.id.btnRetard);
        btnSmsAnnule = (Button) findViewById(R.id.btnAnnule);
        btnArrive = (Button) findViewById(R.id.btnArrive);
        btnModifier = (Button) findViewById(R.id.btnModifier);


        Intent i = getIntent();
        final long iidRdv = i.getLongExtra("idRdv", 1);
        final String ilibRdv = i.getStringExtra("libRdv");
        final String iprenomPers = i.getStringExtra("prenomPers");
        final long idureeRdv = i.getLongExtra("dureeRdv", 0);
        final long idateRdv = i.getLongExtra("dateRdv", 0);
        final String ihoraireRdv = i.getStringExtra("horaireRdv");
        final String iniveauRdv = i.getStringExtra("niveauRdv");
        final String iadresRdv = i.getStringExtra("adresRdv");
        final String iinfo = i.getStringExtra("infoRdv");
        final long iidPers = i.getLongExtra("idPers", 1);
        final String ipaiementRdv = i.getStringExtra("paiementRdv");

        final String iniveau = "Niveau d'étude : "+iniveauRdv;
        final String iduree = "Durée prévue : "+String.valueOf(idureeRdv)+" h";
        final String ihoraire = "La séance débutera à "+changeTime(idateRdv);


        PersonneDAO persdao = new PersonneDAO(ct);
        persdao.open();
        Personne pers = persdao.getPersonneById(iidPers);
        inom = pers.getNomPers();
        iprenom = pers.getPrenomPers();
        //Float isolde = pers.getSoldePers();

        final String iidateRdv2 = changeDate(idateRdv);
        final float itarifRdv = i.getFloatExtra("tarifRdv", 0);
        final String iinfoRdv = i.getStringExtra("infoRdv");

        //libRdv.setText(ilibRdv);
        nomPers.setText(inom);
        prenomPers.setText(iprenom);
        //solde.setText(isolde);
        niveauRdv.setText(iniveau);
        dureeRdv.setText(iduree);
        horaireRdv.setText(ihoraire);
        adresRdv.setText(iadresRdv);


        //Bouton envoyer SMS RETARD
        View.OnClickListener ecoute1 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                PersonneDAO persdao = new PersonneDAO(ct);
                persdao.open();
                Personne pers = persdao.getPersonneById(iidPers);
                String tel = pers.getTelPers();
                retardConf(v,tel);
            }
        };
        btnSmsRetard.setOnClickListener(ecoute1);




        //Bouton envoyer SMS ANNULATION
        View.OnClickListener ecoute2 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                RdvDAO rdvdao = new RdvDAO(ct);
                rdvdao.open();
                Rdv r = (Rdv) rdvdao.getRdvById(iidRdv);
                r.setLibRdv("SÉANCE ANNULÉE");
                rdvdao.modifier(r);

                PersonneDAO persdao = new PersonneDAO(ct);
                persdao.open();
                Personne pers = persdao.getPersonneById(iidPers);
                String tel = pers.getTelPers();
                annuleConf(v, tel);
            }
        };
        btnSmsAnnule.setOnClickListener(ecoute2);


        //Bouton ARRIVÉ À DESTINATION
        View.OnClickListener ecoute3 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {

                final String ipointDeb = timeJour();
                RdvDAO rdvdao = new RdvDAO(ct);
                rdvdao.open();
                Rdv rdv = rdvdao.getRdvById(iidRdv);
                rdv.setLibRdv("SÉANCE NON TERMINÉE");
                rdv.setPointDebRdv(timeStamp());
                rdvdao.modifier(rdv);
                PersonneDAO persdao = new PersonneDAO(ct);
                persdao.open();
                Personne pers = persdao.getPersonneById(iidPers);

                final String iprenomPers = pers.getPrenomPers();
                final String inomPers = pers.getNomPers();
                final String itelPers = pers.getTelPers();
                final float isolde = pers.getSoldePers();

                Intent i=new Intent(SeanceAvant.this, SeanceDebut.class);
                i.putExtra("idRdv", iidRdv);
                i.putExtra("libRdv", ilibRdv);
                i.putExtra("prenom", iprenomPers);
                i.putExtra("nom", inomPers);
                i.putExtra("solde",isolde);
                i.putExtra("niveauRdv", iniveauRdv);
                i.putExtra("dateRdv", idateRdv);
                i.putExtra("dureeRdv", idureeRdv);
                i.putExtra("horaireRdv", ihoraire);
                i.putExtra("pointDeb", ipointDeb);
                i.putExtra("paiementRdv", ipaiementRdv);
                i.putExtra("adresRdv", iadresRdv);
                i.putExtra("infoRdv",iinfo);
                i.putExtra("idPers", iidPers);
                arriveConf(v, i);

            }
        };
        btnArrive.setOnClickListener(ecoute3);

        //Bouton MODIFIER
        View.OnClickListener ecouteMod = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(SeanceAvant.this, RdvModif.class);
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
        btnModifier.setOnClickListener(ecouteMod);

        //Clic sur le NOM
        View.OnClickListener ecouteAdress = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                PersonneDAO persdao = new PersonneDAO(ct);
                persdao.open();
                Personne pers = persdao.getPersonneById(iidPers);
                String tel = pers.getTelPers();
                appelConf(tel);
            }
        };
        nomPers.setOnClickListener(ecouteAdress);

        //Clic sur l'adresse
        View.OnClickListener ecoute4 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                addressConf(iadresRdv);
            }
        };
        adresRdv.setOnClickListener(ecoute4);


    }

    //Envoie message de retard à la séance
    public void retardConf(View v,final String num){
        AlertDialog.Builder mes = new AlertDialog.Builder(ct);
        mes.setMessage("Vous arriverez en retard ?").setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                smsTransmis(num, mess_retard);
                dialog.dismiss();
            }
        }).setTitle("Message de retard").setIcon(R.drawable.logo_a6t_48).create();
        mes.setNegativeButton("Non",null);
        mes.show();
    }

    //Envoie message d'annulation de la séance
    public void annuleConf(View v,final String num){
        AlertDialog.Builder mes = new AlertDialog.Builder(ct);
        mes.setMessage("Vous annulez la séance ?").setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                smsTransmis(num, mess_annule);

                dialog.dismiss();
            }
        }).setTitle("Message d'annulation").setIcon(R.drawable.logo_a6t_48).create();
        mes.setNegativeButton("Non",null);
        mes.show();
    }

    //Arrivé à destination
    public void arriveConf(View v, final Intent intent){
        AlertDialog.Builder mes = new AlertDialog.Builder(ct);
        mes.setMessage("Voulez-vous débuter la séance ?").setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                startActivity(intent);
                dialog.dismiss();
                finish();
            }
        }).setTitle("Arrivée à "+timeHeure()).setIcon(R.drawable.logo_a6t_48).create();
        mes.setNegativeButton("Non",null);
        mes.show();
    }

    //Appel téléphonique
    public void appelConf(final String num){
        AlertDialog.Builder mes = new AlertDialog.Builder(ct);
        mes.setMessage("Vous souhaitez appeler ?").setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                appelTel(num);
                dialog.dismiss();
            }
        }).setTitle("Téléphone").setIcon(R.drawable.logo_a6t_48).create();
        mes.setNegativeButton("Non",null);
        mes.show();
    }

    //Transmettre un SMS
    protected void smsTransmis (String numero, String message){
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(numero, null, message, null, null);
        Toast.makeText(getBaseContext(), "Le message a été envoyé !",
                Toast.LENGTH_SHORT).show();
    }


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
    //Heure du jour au format alphabétique
    public String timeHeure() {
        final Date date = new Date();
        return new SimpleDateFormat("HH:mm").format(date);
    }

    //Change date seule en string
    public String changeDate(long d) {
        final Date date = new Date();
        date.setTime(d);
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public void appelTel(final String num){
       // Intent i = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse("tel:+33623154373"));
        //Intent i = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse("content://contacts/people"));
        Intent i = new Intent(android.content.Intent.ACTION_CALL,Uri.parse("tel:"+num));

        startActivity(i);
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
        getMenuInflater().inflate(R.menu.menu_seance_avant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_accueil:
                //Bouton "Accueil"
//                Intent i = new Intent(SeanceAvant.this, MainActivity.class);
//                startActivity(i);
                finish();

//            case R.id.menu_help:
//                // Comportement du bouton "Aide"
//                return true;
//            case R.id.menu_refresh:
//                // Comportement du bouton "Rafraichir"
//                return true;
//            case R.id.menu_search:
//                // Comportement du bouton "Recherche"
//                return true;
            case R.id.action_settings:
                // Comportement du bouton "Paramètres"
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
