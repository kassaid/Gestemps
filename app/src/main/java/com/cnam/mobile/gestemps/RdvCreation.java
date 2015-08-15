package com.cnam.mobile.gestemps;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RdvCreation extends FragmentActivity {

    public final String tag = "RdvCreation-test";
    String libRdv, adresseRdv, dateRdv, horaireRdv, dureeRdv, niveauRdv, tarifRdv, infoRdv;
    Context ct = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdv_creation);

        final EditText lib = (EditText) findViewById(R.id.libEdit);
        final EditText adresse = (EditText) findViewById(R.id.adresseEdit);
        final EditText date = (EditText) findViewById(R.id.dateEdit);
        final EditText horaire = (EditText) findViewById(R.id.horaireEdit);
        final EditText duree = (EditText) findViewById(R.id.dureeEdit);
        final EditText niveau = (EditText) findViewById(R.id.niveauEdit);
        final EditText tarif = (EditText) findViewById(R.id.tarifEdit);
        final EditText info = (EditText) findViewById(R.id.infoEdit);
        final Button btValider=(Button) findViewById(R.id.button1);
        final Button btAnnuler=(Button) findViewById(R.id.button2);




        View.OnClickListener ecoute1 = new  View.OnClickListener() {

            @Override
            public void onClick(View vu) {
                libRdv = lib.getText().toString();
                adresseRdv = adresse.getText().toString();
                dateRdv = date.getText().toString();
                horaireRdv = horaire.getText().toString();
                dureeRdv = duree.getText().toString();
                niveauRdv = niveau.getText().toString();
                tarifRdv = tarif.getText().toString();
                infoRdv = info.getText().toString();


                if (libRdv.equals("")||adresseRdv.equals("")||dateRdv.equals("")||horaireRdv.equals("")||
                        dureeRdv.equals("")){
                    Toast.makeText(getBaseContext(), "Veuillez remplir les champs obligatoires!",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Rdv rdv = null;
                    try {
                        rdv = new Rdv(libRdv, adresseRdv, 123, 456, dateRdv, horaireRdv,
                                5, changeDate(dateRdv+" "+horaireRdv), timeStamp(), niveauRdv, 40,
                                345, 678,  infoRdv, 1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    RdvDAO rdvdao = new RdvDAO(ct);
                    rdvdao.open();
                    rdvdao.save(rdv);
                    Log.i(tag, "Un compte a été ajouté");
                    Toast.makeText(getBaseContext(),"Le RDV "+libRdv+" du "+dateRdv+" à "+horaireRdv+", a été créé!",
                            Toast.LENGTH_LONG).show();
                    Intent i=new Intent();
                    i.putExtra("ret1", libRdv);
                    i.putExtra("ret2", dateRdv);
                    setResult(RESULT_OK,i);
                    finish();
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

    }

    public  long changeDate(String s) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm");
        Date d = sdf.parse(s);
        return d.getTime();
    }

    //Pointage
    public long timeStamp(){
        final Date date = new Date();
        return date.getTime();
    }


    @Override
    public void onStart(){
        super.onStart();
        EditText vDate = (EditText)findViewById(R.id.dateEdit);
        EditText vTime = (EditText)findViewById(R.id.horaireEdit);

        vDate.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    DateFenetre fen = new DateFenetre(v);
                    FragmentTransaction frag = getFragmentManager().beginTransaction();
                    fen.show(frag,"DateFenetre");
                }
            }
        });

        vTime.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    DateFenetreTime fenTime = new DateFenetreTime(v);
                    FragmentTransaction frag = getFragmentManager().beginTransaction();
                    fenTime.show(frag,"TimeFenetre");
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rdv_creation, menu);
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
