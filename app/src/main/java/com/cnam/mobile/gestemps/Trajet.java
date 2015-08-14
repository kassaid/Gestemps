package com.cnam.mobile.gestemps;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Trajet extends ActionBarActivity {

    Context ct = this;
    Button btnSmsRetard;
    Button btnSmsAnnule;
    Button btnArrive;
    String num_tel = "0623154373";
    String mess_retard = "Désolé je serais en retard";
    String mess_annule = "Désolé, suite à un empêchement j'annule le cours";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trajet);


        final String tag = "Trajet-test";

        TextView libRdv = (TextView) findViewById(R.id.libRdvView);
        TextView niveauRdv = (TextView) findViewById(R.id.niveauRdvView);
        TextView dureeRdv = (TextView) findViewById(R.id.dureeRdvView);
        TextView dateRdv = (TextView) findViewById(R.id.horaireRdvView);
        TextView adresRdv = (TextView) findViewById(R.id.adresRdvView);

        btnSmsRetard = (Button) findViewById(R.id.btnRetard);
        btnSmsAnnule = (Button) findViewById(R.id.btnAnnule);
        btnArrive = (Button) findViewById(R.id.btnArrive);


        Intent i = getIntent();
        final long iidRdv = i.getLongExtra("idRdv", 1);
        final String ilibRdv = i.getStringExtra("libRdv");
        final String iniveauRdv = i.getStringExtra("niveauRdv");
        final String idureeRdv = i.getStringExtra("dureeRdv");
        final String idateRdv = i.getStringExtra("dateRdv");
        final String iadresRdv = i.getStringExtra("adresRdv");
        final long iidPers = i.getLongExtra("idPers", 1);

        //libRdv.setText(String.valueOf(res0));
        libRdv.setText(ilibRdv);
        niveauRdv.setText(iniveauRdv);
        dureeRdv.setText(idureeRdv);
        dateRdv.setText(idateRdv);
        adresRdv.setText(iadresRdv);


        //Bouton envoyer SMS RETARD
        View.OnClickListener ecoute1 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                smsTransmis(num_tel, mess_retard);
            }
        };
        btnSmsRetard.setOnClickListener(ecoute1);

        //Bouton envoyer SMS ANNULATION
        View.OnClickListener ecoute2 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                smsTransmis(num_tel, mess_annule);
            }
        };
        btnSmsAnnule.setOnClickListener(ecoute2);

        //Bouton ARRIVÉ À DESTINATION
        View.OnClickListener ecoute3 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(Trajet.this, SeanceDebut.class);
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
        };
        btnArrive.setOnClickListener(ecoute3);


    }

    //Transmettre un SMS
    protected void smsTransmis (String numero, String message){
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(numero, null, message, null, null);
        Toast.makeText(getBaseContext(), "Le message a été envoyé !",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trajet, menu);
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
