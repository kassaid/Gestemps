package com.cnam.mobile.gestemps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class RdvListeAttente extends AppCompatActivity implements RdvAdapter.RdvAdapterListener {

    private ArrayList<Rdv> listRdv;
    Context ct=this;



    public void onClickNom(final Rdv item, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("RDV du "+item.changeDate(item.getDateRdv())).setIcon(R.drawable.logo_a6t_48).create();

        builder.setMessage("Voulez-vous voir la fiche ?");
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


                Intent i = new Intent(RdvListeAttente.this, RdvFiche.class);
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
        setContentView(R.layout.activity_rdv_liste_attente);
        RdvDAO rdvdao = new RdvDAO(getBaseContext());
        rdvdao.open();


        listRdv = (ArrayList<Rdv>) rdvdao.getAllRdvAttente();


        RdvAdapter adapter = new RdvAdapter(this, listRdv);

        adapter.addListener((RdvAdapter.RdvAdapterListener) this);

        ListView list = (ListView)findViewById(R.id.listRdvView);

        list.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rdv_liste_attente, menu);
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
