package com.cnam.mobile.gestemps;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Float.parseFloat;

public class PersonneModif extends ActionBarActivity {

    public final String tag = "personneModif-test";


    Context ct = this;
    Button btnValider;
    Button btnAnnuler;
    String nomPers, prenomPers, adressePers, telPers, mailPers, soldePers, infoPers;
    Personne p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personne_modif);


        final TextView idPers = (TextView) findViewById(R.id.idPersView);
        final EditText nom = (EditText) findViewById(R.id.nomEdit);
        final EditText prenom = (EditText) findViewById(R.id.prenomEdit);
        final EditText adresse = (EditText) findViewById(R.id.adresseEdit);
        final EditText tel = (EditText) findViewById(R.id.telEdit);
        final EditText mail = (EditText) findViewById(R.id.mailEdit);
        final EditText solde = (EditText) findViewById(R.id.soldeEdit);
        final EditText info = (EditText) findViewById(R.id.infoEdit);

        btnValider = (Button) findViewById(R.id.btnValider);
        btnAnnuler = (Button) findViewById(R.id.btnAnnuler);

        Intent i = getIntent();
        final long iidPers = i.getLongExtra("idPers", 1);
        final String inom = i.getStringExtra("nomPers");
        final String iprenom = i.getStringExtra("prenomPers");
        final String iadresse = i.getStringExtra("adresPers");
        final String itel = i.getStringExtra("telPers");
        final String imail = i.getStringExtra("mailPers");
        final float isolde = i.getFloatExtra("soldePers", 0);
        final String iinfo = i.getStringExtra("infoPers");

        idPers.setText(String.valueOf(iidPers));
        nom.setText(inom);
        prenom.setText(iprenom);
        adresse.setText(iadresse);
        tel.setText(itel);
        mail.setText(imail);
        solde.setText(String.valueOf(isolde));
////        rdvPers.setText(irdvPers);
        info.setText(iinfo);

        View.OnClickListener ecoute1 = new  View.OnClickListener() {

            @Override
            public void onClick(View vu) {


                nomPers = nom.getText().toString();
                prenomPers = prenom.getText().toString();
                adressePers = adresse.getText().toString();
                telPers = tel.getText().toString();
                mailPers = mail.getText().toString();
                soldePers = solde.getText().toString();
                infoPers = info.getText().toString();

                //Contrôle du mail
                Pattern pat = Pattern.compile(".+@.+\\.[a-z]+");
                Matcher m = pat.matcher(mailPers);


                if (nomPers.equals("")||prenomPers.equals("")||adressePers.equals("")||telPers.equals("")||soldePers.equals("")){
                    Toast.makeText(getBaseContext(), "Veuillez remplir les champs obligatoires !", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (!m.matches()) {
                        Toast.makeText(getBaseContext(), "L'adresse mail saisie n'est pas conforme !",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }


                    PersonneDAO persdao = new PersonneDAO(ct);
                    persdao.open();
                    p = persdao.getPersonneById(iidPers);
                    p.setNomPers(nomPers);
                    p.setPrenomPers(prenomPers);
                    p.setAdresPers(adressePers);
                    p.setTelPers(telPers);
                    p.setMailPers(mailPers);
                    p.setSoldePers(parseFloat(soldePers));
                    p.setInfoPers(infoPers);
                    persdao.modifier(p);
                    finish();

            }


            }
        };
        btnValider.setOnClickListener(ecoute1);

        View.OnClickListener ecoute2 = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }

        };
        btnAnnuler.setOnClickListener(ecoute2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_personne_modif, menu);
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
