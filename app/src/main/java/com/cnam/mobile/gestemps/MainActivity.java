package com.cnam.mobile.gestemps;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends ActionBarActivity {

    final int FROM_FIRST_ACTIVITY_REQUEST=0;
    Button btnRdvPrevu;
    Button btnRdvNonPrevu;
    Button btnListeRdv;
    Button btnAjouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar =   (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        //ActionBar actionBar = getSupportActionBar();
        //Permet d'afficher le bouton de navigation up sur l'application
        //actionBar.setDisplayHomeAsUpEnabled(true);

        TextView dateDuJour = (TextView) findViewById(R.id.dateDuJourView);

        btnRdvPrevu = (Button) findViewById(R.id.button1);
        btnRdvNonPrevu = (Button) findViewById(R.id.button2);
        btnListeRdv = (Button) findViewById(R.id.button3);
        btnAjouter = (Button) findViewById(R.id.button4);

        dateDuJour.setText(lejour());



        //Bouton RDV PREVU
        View.OnClickListener ecoute1=new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);
            }
        };
        btnRdvPrevu.setOnClickListener(ecoute1);


        //Bouton RDV NON PREVU
        View.OnClickListener ecoute2 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(MainActivity.this, RdvCreation.class);
                startActivity(i);
            }
        };
        btnRdvNonPrevu.setOnClickListener(ecoute2);

        //Bouton LISTE DES RDV
        View.OnClickListener ecoute3 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(MainActivity.this, RdvListe.class);
                startActivity(i);
            }
        };
        btnListeRdv.setOnClickListener(ecoute3);

        //Ajouter une personne
        View.OnClickListener ecoute4 = new  View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(MainActivity.this, PersonneCreation.class);
                startActivity(i);
            }
        };
        btnAjouter.setOnClickListener(ecoute4);

    }


    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FROM_FIRST_ACTIVITY_REQUEST)
        {
            switch (resultCode)
            {
                case RESULT_OK:
                    String result1 = data.getStringExtra("ret1");
                    String result2 = data.getStringExtra("ret2");
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
