package com.cnam.mobile.gestemps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class RdvListe extends ActionBarActivity implements RdvAdapter.RdvAdapterListener {

    private ArrayList<Rdv> listOf;


    public void onClickNom(final Rdv item, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rdv");

        builder.setMessage("Voulez-vous aller à ce rdv n° " + item.getIdRdv() +" ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int whichButton)
            {
                //I get a compile error here, it wants result to be final.
                //ListOffre.this.finish();
                RdvDAO rdvdao = new RdvDAO(getBaseContext());
                rdvdao.open();
                //rdvdao.delete(item.getIdRdv());
                long iidRdv = item.getIdRdv();
                String ilibRdv = item.getLibRdv();
                String iniveauRdv = item.getNiveauRdv();
                String idureeRdv = item.getDureeRdv();
                String idateRdv = item.getDateRdv();
                String iadresRdv = item.getAdresRdv();
                long iidPers = item.getIdPers();
//                finish();
//                startActivity(getIntent());
                //startActivity(ListOffre.this, ListOffre.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                Intent i=new Intent(RdvListe.this, Trajet.class);
                i.putExtra("idRdv", iidRdv);
                i.putExtra("libRdv", ilibRdv);
                i.putExtra("niveauRdv", iniveauRdv);
                i.putExtra("dureeRdv", idureeRdv);
                i.putExtra("dateRdv", idateRdv);
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
        setContentView(R.layout.activity_rdv_liste);

        Intent i=getIntent();
        final long res0 = i.getLongExtra("id", 1);
        final String res1 = i.getStringExtra("nom");
        final String res2 = i.getStringExtra("prenom");
        final String choix = i.getStringExtra("choix");
        final long idRdv = i.getLongExtra("idRdv", 1);

        RdvDAO rdvdao = new RdvDAO(getBaseContext());
        rdvdao.open();

//        if (choix.equals("act2")){
//            listOf = (ArrayList<Rdv>) rdvdao.getOffreByIdcom(res0);
//        }
//        else{
            listOf = (ArrayList<Rdv>) rdvdao.getAllRdv();
//        }
//
        RdvAdapter adapter = new RdvAdapter(this, listOf);


        adapter.addListener((RdvAdapter.RdvAdapterListener) this);

        ListView list = (ListView)findViewById(R.id.listView1);



        list.setAdapter(adapter);




//        final Button retour=(Button) findViewById(R.id.button1);
//        OnClickListener ecoute = new OnClickListener ()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                //ValidationOffre.this.finish();
//                Intent i=new Intent(ListOffre.this,MonMenu.class);
//                i.putExtra("id", res0);
//                i.putExtra("nom",res1);
//                i.putExtra("prenom",res2);
//                i.putExtra("idOff", idOff);
//                startActivity(i);
//            }
//        };
//        retour.setOnClickListener(ecoute);
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
