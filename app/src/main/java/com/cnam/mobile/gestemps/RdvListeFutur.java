package com.cnam.mobile.gestemps;

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
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RdvListeFutur extends AppCompatActivity implements RdvAdapter.RdvAdapterListener {

    private ArrayList<Rdv> listRdv;
    Context ct=this;
    private static final String tag = "liste";



    public void onClickNom(final Rdv item, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("RDV du "+item.changeDate(item.getDateRdv())).setIcon(R.drawable.logo_a6t_48).create();

        builder.setMessage("Voulez-vous aller à ce rdv ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                long iidRdv = item.getIdRdv();
                String ilibRdv = item.getLibRdv();
                String iadresRdv = item.getAdresRdv();
                long idateRdv = item.getDateRdv();
                String ihoraireRdv = item.getHoraireRdv();
                long idureeRdv = item.getDureeRdv();
                String iniveauRdv = item.getNiveauRdv();
                float itarifRdv = item.getTarifRdv();
                long ipaiementRdv = item.getPaiementRdv();
                String iinfoRdv = item.getInfoRdv();
                long iidPers = item.getIdPers();




                //Preparation et envoie de l'intent
                Intent i = new Intent(RdvListeFutur.this, SeanceAvant.class);
                i.putExtra("idRdv", iidRdv);
                i.putExtra("libRdv", ilibRdv);
                i.putExtra("dateRdv", idateRdv);
                i.putExtra("horaireRdv", ihoraireRdv);
                i.putExtra("dureeRdv", idureeRdv);
                i.putExtra("niveauRdv", iniveauRdv);
                i.putExtra("tarifRdv", itarifRdv);
                i.putExtra("paiementRdv", ipaiementRdv);
                i.putExtra("infoRdv",iinfoRdv);
                i.putExtra("adresRdv", iadresRdv);
                i.putExtra("idPers", iidPers);
                startActivity(i);
                finish();

            }
        });
        builder.setNegativeButton("Non", null);
        builder.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdv_liste_futur);

        Log.d(tag, "liste rdv futur : onCreate");
        RdvDAO rdvdao = new RdvDAO(getBaseContext());
        rdvdao.open();


        listRdv = (ArrayList<Rdv>) rdvdao.getAllRdvFutur(rdvdao.timeStamp()-(1000*20));


        RdvAdapter adapter = new RdvAdapter(this, listRdv);

        adapter.addListener((RdvAdapter.RdvAdapterListener) this);

        ListView list = (ListView)findViewById(R.id.listRdvView);

        list.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rdv_liste_futur, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_accueil:
                //Bouton "Accueil"
//                Intent i=new Intent(RdvListeFutur.this, MainActivity.class);
//                startActivity(i);
                finish();
                return true;
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

    //Date du jour au format alphabétique
    public String lejour() {
        final Date date = new Date();
        return new SimpleDateFormat("EEEE d MMM yyyy").format(date);
    }



}