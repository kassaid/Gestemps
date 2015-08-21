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
    TextView prenomPers;
    Button btnSmsRetard;
    Button btnSmsAnnule;
    Button btnArrive;
    String num_tel = "0623154373";
    String mess_retard = "Désolé je serais en retard";
    String mess_annule = "Désolé, suite à un empêchement j'annule le cours";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance_avant);


        final String tag = "SeanceAvant-test";

        TextView libRdv = (TextView) findViewById(R.id.libRdvView);
        prenomPers = (TextView) findViewById(R.id.prenomPersView);
        TextView nomPers = (TextView) findViewById(R.id.nomPersView);
        TextView niveauRdv = (TextView) findViewById(R.id.niveauRdvView);
        TextView dureeRdv = (TextView) findViewById(R.id.dureeRdvView);
        TextView horaireRdv = (TextView) findViewById(R.id.horaireRdvView);
        TextView adresRdv = (TextView) findViewById(R.id.adresRdvView);

        btnSmsRetard = (Button) findViewById(R.id.btnRetard);
        btnSmsAnnule = (Button) findViewById(R.id.btnAnnule);
        btnArrive = (Button) findViewById(R.id.btnArrive);


        Intent i = getIntent();
        final long iidRdv = i.getLongExtra("idRdv", 1);
        final String ilibRdv = i.getStringExtra("libRdv");
        final String iprenomPers = i.getStringExtra("prenomPers");
        final long idureeRdv = i.getLongExtra("dureeRdv", 0);
        //final String idateRdv = i.getStringExtra("dateRdv");
        final String ihoraireRdv = i.getStringExtra("horaireRdv");
        final String iniveauRdv = i.getStringExtra("niveauRdv");
        final String iadresRdv = i.getStringExtra("adresRdv");
        final long iidPers = i.getLongExtra("idPers", 1);
        final String imontantRdv = i.getStringExtra("montantRdv");

        final String iniveau = "Niveau "+iniveauRdv;
        final String iduree = "Séance de "+String.valueOf(idureeRdv/10000)+" h";
        final String ihoraire = "Début de séance à "+ihoraireRdv;


        PersonneDAO persdao = new PersonneDAO(ct);
        persdao.open();
        Personne pers = persdao.getPersonneById(iidPers);
        String iprenom = pers.getPrenomPers();
        String inom = pers.getNomPers();
        //Float isolde = pers.getSoldePers();

        libRdv.setText(ilibRdv);
        prenomPers.setText(iprenom);
        nomPers.setText(inom);
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
                rdv.setLibRdv("SÉANCE NON TERMINÉE !");
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
                i.putExtra("niveauRdv", iniveau);
                i.putExtra("dureeRdv", iduree);
                i.putExtra("horaireRdv", ihoraire);
                i.putExtra("pointDeb", ipointDeb);
                i.putExtra("montantRdv", imontantRdv);
                i.putExtra("adresRdv", iadresRdv);
                i.putExtra("idPers", iidPers);
                arriveConf(v, i);

            }
        };
        btnArrive.setOnClickListener(ecoute3);

        //Clic sur le prenom
        View.OnClickListener ecoute4 = new  View.OnClickListener(){

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
        prenomPers.setOnClickListener(ecoute4);


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
        }).setTitle("Message de retard").setIcon(R.drawable.autoriser).create();
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
        }).setTitle("Message d'annulation").setIcon(R.drawable.autoriser).create();
        mes.setNegativeButton("Non",null);
        mes.show();
    }

    //Arrivé à destination
    public void arriveConf(View v, final Intent intent){
        AlertDialog.Builder mes = new AlertDialog.Builder(ct);
        mes.setMessage("Vous commencez la séance ?").setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                startActivity(intent);
                dialog.dismiss();
                finish();
            }
        }).setTitle("Message d'arrivée").setIcon(R.drawable.autoriser).create();
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
        }).setTitle("Téléphone").setIcon(R.drawable.autoriser).create();
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

    @Override
    public void onStart(){
        super.onStart();


    }

    public void appelTel(final String num){
       // Intent i = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse("tel:+33623154373"));
        //Intent i = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse("content://contacts/people"));
        Intent i = new Intent(android.content.Intent.ACTION_CALL,Uri.parse("tel:"+num));

        startActivity(i);
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
