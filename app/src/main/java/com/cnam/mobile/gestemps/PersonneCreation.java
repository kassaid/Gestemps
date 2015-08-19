package com.cnam.mobile.gestemps;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonneCreation extends AppCompatActivity {

    public final String tag = "PersonneCreation-test";
    String nomPers, prenomPers, adressePers, telPers, mailPers, infoPers;
    Context ct = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personne_creation);

        final EditText nom = (EditText) findViewById(R.id.nomEdit);
        final EditText prenom = (EditText) findViewById(R.id.prenomEdit);
        final EditText adresse = (EditText) findViewById(R.id.adresseEdit);
        final EditText tel = (EditText) findViewById(R.id.telEdit);
        final EditText mail = (EditText) findViewById(R.id.mailEdit);
        final EditText info = (EditText) findViewById(R.id.infoEdit);
        final Button btValider=(Button) findViewById(R.id.button1);
        final Button btAnnuler=(Button) findViewById(R.id.button2);




        View.OnClickListener ecoute1 = new  View.OnClickListener() {

            @Override
            public void onClick(View vu) {
                nomPers = nom.getText().toString();
                prenomPers = prenom.getText().toString();
                adressePers = adresse.getText().toString();
                telPers = tel.getText().toString();
                mailPers = mail.getText().toString();
                infoPers = info.getText().toString();

                //Contrôle du mail
                Pattern pat = Pattern.compile(".+@.+\\.[a-z]+");
                Matcher m = pat.matcher(mailPers);


                if (nomPers.equals("")||prenomPers.equals("")||adressePers.equals("")||telPers.equals("")
                        ||mailPers.equals("")){
                    Toast.makeText(getBaseContext(), "Veuillez remplir les champs obligatoires !", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (!m.matches()) {
                        Toast.makeText(getBaseContext(), "L'adresse mail saisie n'est pas conforme !",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Personne p = new Personne(nomPers, prenomPers, adressePers, 123, 456, telPers, mailPers, 54, infoPers);
                    PersonneDAO persdao = new PersonneDAO(ct);
                    persdao.open();
//                        if (persdao.selectByLogin(clogin)!=null)
//                        {
//                            Toast.makeText(getBaseContext(),"Login non accepté!",Toast.LENGTH_LONG).show();
//                            pass.setText("");
//                            conpass.setText("");
//                            login.setText("");
//                        }

//                        else
//                        {
                            persdao.save(p);
                            Log.i(tag, "Un compte a été ajouté");
                            Toast.makeText(getBaseContext(),"L'élève "+prenomPers+" "+nomPers+", a été créé!",Toast.LENGTH_LONG).show();
                            //Toast.makeText(getBaseContext(), "Bienvenue "+cprenom+" "+cnom, Toast.LENGTH_LONG).show();
                            Intent i=new Intent();
                            i.putExtra("ret1", nomPers);
                            i.putExtra("ret2", prenomPers);
                            setResult(RESULT_OK,i);
                            finish();
 //                       }
 //                   }
                }


            }

            private Context getContext() {

                return null;
            }
        };
        btValider.setOnClickListener(ecoute1);

        View.OnClickListener ecoute2 = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }

        };
        btAnnuler.setOnClickListener(ecoute2);
        //String data=getIntent().getStringExtra("myParameter");
        //textvu = (EditText) findViewById(R.id.EditText01);
        //textvu.setText(data);
    }



//}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_personne_creation, menu);
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
