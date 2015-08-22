package com.cnam.mobile.gestemps;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    final int FROM_FIRST_ACTIVITY_REQUEST=0;
    private static final String tag = "main";
    Button btnListe;
    Button btnAjouter;

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    Context ct=this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView dateDuJour = (TextView) findViewById(R.id.dateDuJourView);
        TextView nombreRdv = (TextView) findViewById(R.id.nb_rdvView);

        btnListe = (Button) findViewById(R.id.btnListe);
        btnAjouter = (Button) findViewById(R.id.btnAjouter);
        //btnListeRdv = (Button) findViewById(R.id.button3);
        //btnListeEleves = (Button) findViewById(R.id.btnListeEleves);
        //btnAjouter = (Button) findViewById(R.id.button4);

        dateDuJour.setText(lejour());


        RdvDAO rdvdao = new RdvDAO(ct);
        rdvdao.open();
        long nbDAO = rdvdao.nbRdvJour(rdvdao.timeStamp()-2*60*60*1000);

        final String nb_rdv;
        if (nbDAO!=0) {
            if (nbDAO<2){
                 nb_rdv = "Vous avez une seule séance à venir !";
            }else{
                 nb_rdv = "Vous avez " + nbDAO + " séances à venir !";}
        }else{
             nb_rdv = "Vous n'avez aucune séance à venir !";
        }

        nombreRdv.setText(nb_rdv);



        View.OnClickListener ecoute1=new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {

                PopupMenu pop = new PopupMenu(ct,v);
                pop.inflate(R.menu.menu_main_liste);
                pop.show();
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.liste_rdvFutur:
                                Intent i = new Intent(MainActivity.this, RdvListeFutur.class);
                                startActivity(i);
                                return false;
                            case R.id.liste_rdvPasse:
                                Intent i2 = new Intent(MainActivity.this, RdvListePasse.class);
                                startActivity(i2);
                                return false;
                            case R.id.liste_personne:
                                Intent i3 = new Intent(MainActivity.this, PersonneListe.class);
                                startActivity(i3);
                                return false;
                            default:
                                return false;
                        }
                    }
                });
            }
        };
        btnListe.setOnClickListener(ecoute1);


        View.OnClickListener ecoute2 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {

                PopupMenu pop = new PopupMenu(ct,v);
                pop.inflate(R.menu.menu_main_ajouter);
                pop.show();
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.ajouter_rdv:
                                Intent i = new Intent(MainActivity.this, RdvCreation.class);
                                startActivity(i);
                                finish();
                                return false;
                            case R.id.ajouter_personne:
                                Intent i2 = new Intent(MainActivity.this, PersonneCreation.class);
                                startActivity(i2);
                                return false;
                            default:
                                return false;
                        }
                    }
                });
            }
        };
        btnAjouter.setOnClickListener(ecoute2);


    }





    protected void onDestroy(){
        super.onDestroy();
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FROM_FIRST_ACTIVITY_REQUEST)
        {
            switch (resultCode)
            {
                case RESULT_OK:
                    String ilibRdv = data.getStringExtra("libRdv");
                    String idateRdv = data.getStringExtra("dateRdv");

                    ///textnom.setText(result1);
                    //textprenom.setText(result2);
                    break;

                case RESULT_CANCELED:
                    //textlogin.setText("");
                    break;
            }
        }

    }

    //Date du jour au format alphabétique
    public String lejour() {
        final Date date = new Date();
        return new SimpleDateFormat("EEEE d MMM yyyy").format(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
//            case R.id.action_accueil:
//                //Bouton "Accueil"
//                Intent i=new Intent(RdvListeFutur.this, MainActivity.class);
//                startActivity(i);
//                return true;
//            case R.id.menu_help:
//                // Comportement du bouton "Aide"
//                return true;
//            case R.id.menu_refresh:
//                // Comportement du bouton "Rafraichir"
//                return true;
            case R.id.action_alarme:
                // Comportement du bouton "Recherche"
                //setAlarme();
                return true;
            case R.id.action_quitter:
                // Comportement du bouton "Paramètres"
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
    }

    public void messAlert(View v){
        AlertDialog.Builder mes = new AlertDialog.Builder(ct);
        mes.setMessage("c'est un messag ok!!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setTitle("Bienvenue").setIcon(R.drawable.autoriser).create();
        mes.show();

    }


}
