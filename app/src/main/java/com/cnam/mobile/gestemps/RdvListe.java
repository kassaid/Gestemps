package com.cnam.mobile.gestemps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RdvListe extends AppCompatActivity implements RdvAdapter.RdvAdapterListener {

    private ArrayList<Rdv> listOf;

    //TextView dateDuJour = (TextView) findViewById(R.id.dateDuJourView);

    public void onClickNom(final Rdv item, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rdv");

        builder.setMessage("Voulez-vous aller à ce rdv n° " + item.getIdRdv() +" ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int whichButton)
            {

//                RdvDAO rdvdao = new RdvDAO(getBaseContext());
//                rdvdao.open();
                //rdvdao.delete(item.getIdRdv());

                long iidRdv = item.getIdRdv();
                String ilibRdv = item.getLibRdv();
                String iadresRdv = item.getAdresRdv();
               // float ilongitRdv =item.getLongitRdv()
                //float ilatitRdv=item.getLatitRdv()
                long idateRdv = item.getDateRdv();
                String ihoraireRdv = item.getHoraireRdv();
                long idureeRdv=item.getDureeRdv();
                // long ipointDebRdv = item.getPointDebRdv();
//                long ipointFinRdv = item.getPointFinRdv();
                String iniveauRdv = item.getNiveauRdv();
                float itarifRdv = item.getTarifRdv();
                float imontantRdv=item.getMontantRdv();
//                float isoldeRdv=item.getSoldeRdv();
//                String iinfoRdv=item.getInfoRdv();

                long iidPers = item.getIdPers();


//                finish();
//                startActivity(getIntent());
                //startActivity(ListOffre.this, ListOffre.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                Intent i=new Intent(RdvListe.this, SeanceAvant.class);
                i.putExtra("idRdv", iidRdv);
                i.putExtra("libRdv", ilibRdv);
                i.putExtra("dateRdv", idateRdv);
                i.putExtra("horaireRdv", ihoraireRdv);
                i.putExtra("dureeRdv", idureeRdv);
                i.putExtra("niveauRdv", iniveauRdv);
                i.putExtra("tarifRdv", itarifRdv);
                i.putExtra("montantRdv", imontantRdv);
                i.putExtra("adresRdv", iadresRdv);
                i.putExtra("idPers", iidPers);

                startActivity(i);
                finish();


            }
        });
        builder.setNegativeButton("Non", null);
        builder.show();
    }

    //Date du jour au format alphabétique
    public String lejour() {
        final Date date = new Date();
        return new SimpleDateFormat("EEEE d MMM yyyy").format(date);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdv_liste);

//        Intent i=getIntent();
//        final long res0 = i.getLongExtra("id", 1);
//        final String res1 = i.getStringExtra("nom");
//        final String res2 = i.getStringExtra("prenom");
//        final String choix = i.getStringExtra("choix");
//        final long idRdv = i.getLongExtra("idRdv", 1);

        TextView dateDuJour = (TextView) findViewById(R.id.dateDuJourView);
        dateDuJour.setText(lejour());

        RdvDAO rdvdao = new RdvDAO(getBaseContext());
        rdvdao.open();

//        if (choix.equals("act2")){
//            listOf = (ArrayList<Rdv>) rdvdao.getOffreByIdcom(res0);
//        }
//        else{
            listOf = (ArrayList<Rdv>) rdvdao.getAllRdv();
            //listOf = (ArrayList<Rdv>) rdvdao.getAllRdvFutur(rdvdao.timeStamp()-2*60*60*1000);
//        }
//
        RdvAdapter adapter = new RdvAdapter(this, listOf);

        adapter.addListener((RdvAdapter.RdvAdapterListener) this);

        ListView list = (ListView)findViewById(R.id.listView1);

        list.setAdapter(adapter);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rdv_liste, menu);
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
