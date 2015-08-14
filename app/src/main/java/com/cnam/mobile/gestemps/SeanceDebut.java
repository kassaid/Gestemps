package com.cnam.mobile.gestemps;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class SeanceDebut extends ActionBarActivity {

    Button btnTerminer;
    Button btnAnnuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance_debut);

        final String tag = "Seance-test";

        TextView prenom = (TextView) findViewById(R.id.prenomPersView);
        TextView nom = (TextView) findViewById(R.id.nomPersView);
        TextView time = (TextView) findViewById(R.id.timeView);
        TextView niveau = (TextView) findViewById(R.id.niveauRdvView);
        TextView duree = (TextView) findViewById(R.id.dureeRdvView);
        //TextView adresRdv = (TextView) findViewById(R.id.adresRdvView);

        btnTerminer = (Button) findViewById(R.id.btnTerminer);
        btnAnnuler = (Button) findViewById(R.id.btnAnnuler);



        Intent i = getIntent();
        final long iidRdv = i.getLongExtra("idRdv", 1);
        final String iprenom = i.getStringExtra("libRdv");
        final String inom = i.getStringExtra("niveauRdv");
        final String itime = i.getStringExtra("dureeRdv");
        final String iniveau = i.getStringExtra("dateRdv");
        final String iduree = i.getStringExtra("adresRdv");
        final long iidPers = i.getLongExtra("idPers", 1);

        //libRdv.setText(String.valueOf(res0));
        prenom.setText(iprenom);
        nom.setText(inom);
        time.setText(itime);
        niveau.setText(iniveau);
        duree.setText(iduree);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_seance_debut, menu);
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
