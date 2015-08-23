package com.cnam.mobile.gestemps;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class RdvModif extends AppCompatActivity{



    private String nomPosition;
//    private List<String> allNomPers;
//    private List<Personne> listPers;
    private Personne pers;
    private long idPersRdv = 1;

    public final String tag = "rdvCreation-test";
    String lib, adres, date, horaire, duree, niveau, tarif, info;
    Context ct = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdv_modif);



        //final EditText lib = (EditText) findViewById(R.id.libEdit);
       // final EditText prenom = (EditText) findViewById(R.id.prenomEdit);
        final TextView nomPers = (TextView) findViewById(R.id.nomPersView);
        final TextView prenomPers = (TextView) findViewById(R.id.prenomPersView);
        final EditText adresRdv = (EditText) findViewById(R.id.adresRdvEdit);
        final EditText dateRdv = (EditText) findViewById(R.id.dateEdit);
        final EditText horaireRdv = (EditText) findViewById(R.id.horaireEdit);
        final EditText dureeRdv = (EditText) findViewById(R.id.dureeEdit);
        //final EditText niveau = (EditText) findViewById(R.id.niveauEdit);
        final EditText tarifRdv = (EditText) findViewById(R.id.tarifEdit);
        final EditText infoRdv = (EditText) findViewById(R.id.infoEdit);

        final Button btValider=(Button) findViewById(R.id.button1);
        final Button btAnnuler=(Button) findViewById(R.id.button2);

        //final Spinner spinlistPers = (Spinner) findViewById(R.id.personneSpinner);


        Intent i = getIntent();
        //final String ilibRdv = i.getStringExtra("libRdv");
        final long iidRdv = i.getLongExtra("idRdv", 1);
        final long iidPers = i.getLongExtra("idPers", 1);
        final String inomPers = i.getStringExtra("nomPers");
        final String iprenomPers = i.getStringExtra("prenomPers");
        final String idateRdv = i.getStringExtra("dateRdv");
        final String iadresRdv = i.getStringExtra("adresRdv");
//        final String iniveauRdv = i.getStringExtra("niveauRdv");
        final float itarifRdv = i.getFloatExtra("tarifRdv", 0);
        final long idureeRdv = i.getLongExtra("dureeRdv", 0);
        final String iinfoRdv = i.getStringExtra("infoRdv");
       final String ihoraireRdv = i.getStringExtra("horaireRdv");
//        final String ipaiementRdv = i.getStringExtra("paiementRdv");




//        PersonneDAO persdao = new PersonneDAO(ct);
//        persdao.open();
//        Personne pers = persdao.getPersonneById(iidPers);
//
//        String inom = pers.getNomPers();
//        String iprenom = pers.getPrenomPers();
//        String itel = pers.getTelPers();
//        Float isolde = pers.getSoldePers();

        //final String iidateRdv = "RDV du "+changeDate(idateRdv);
//        final String iiniveauRdv = "Niveau "+iniveauRdv;
//        //final String iduree = "Séance de "+String.valueOf(idureeRdv/10000)+" h";
//        final String iitarifRdv = "Tarif : "+String.valueOf(itarifRdv)+" euros/h";
//        final String iidureeRdv = "Durée prévue de "+String.valueOf(idureeRdv/10000)+" h";
//        final String iipaiementRdv = "Paiement reçu : "+String.valueOf(ipaiementRdv)+" euros";
//        final String iisolde = "Solde du compte : "+String.valueOf(isolde)+" euros";


        //libRdv.setText(ilibRdv);
        dateRdv.setText(idateRdv);
        horaireRdv.setText(ihoraireRdv);
        prenomPers.setText(iprenomPers);
        nomPers.setText(inomPers);
        adresRdv.setText(iadresRdv);
//        niveauRdv.setText(iiniveauRdv);
        tarifRdv.setText(String.valueOf(itarifRdv));
        dureeRdv.setText(String.valueOf(idureeRdv));
        //paiementRdv.setText(iipaiementRdv);
         infoRdv.setText(iinfoRdv);
//        soldePers.setText(iisolde);




        final Spinner spinNiv = (Spinner) findViewById(R.id.niveauSpinner);
        final ArrayAdapter<CharSequence> adaptNiveau = ArrayAdapter.createFromResource(this,R.array.spinNiveau,android.R.layout.simple_spinner_item);
        adaptNiveau.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinNiv.setAdapter(adaptNiveau);
        spinNiv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                niveau = parent.getItemAtPosition(position).toString();
                if (position!=0){
                    Toast.makeText(getBaseContext(), parent.getItemAtPosition(position)+" est choisi",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //Bouton VALIDER
        View.OnClickListener ecoute1 = new  View.OnClickListener() {

            @Override
            public void onClick(View vu) {
                //libRdv = lib.getText().toString();

                adres = adresRdv.getText().toString();
                date = dateRdv.getText().toString();
                horaire = horaireRdv.getText().toString();
                duree = dureeRdv.getText().toString();
                tarif = tarifRdv.getText().toString();
                info = infoRdv.getText().toString();
//                libRdv = "OK";


                if (adres.equals("")||date.equals("")||horaire.equals("")||
                        duree.equals("")){
                    Toast.makeText(getBaseContext(), "Veuillez remplir les champs obligatoires!",
                            Toast.LENGTH_LONG).show();
                }
                else
                {

                    RdvDAO rdvdao = new RdvDAO(ct);
                    rdvdao.open();
                    Rdv r = (Rdv) rdvdao.getRdvById(iidRdv);
                    try {
                        r.setDateRdv(changeDate(date+" "+horaire));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    r.setAdresRdv(adres);
                    r.setHoraireRdv(horaire);
                    r.setDureeRdv(changeInt(duree));
                    r.setTarifRdv(changeFloat(tarif));
                    r.setInfoRdv(info);
                    rdvdao.modifier(r);




                    finish();
                }

            }

            private Context getContext() {
                return null;
            }
        };
        btValider.setOnClickListener(ecoute1);


        //bouton ANNULER
        View.OnClickListener ecoute2 = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }

        };
        btAnnuler.setOnClickListener(ecoute2);


    }


    public float changeFloat(String s){
        return parseFloat(s);
    }

    public long changeInt(String s){
        return parseInt(s, 10);
    }

    public  long changeDate(String s) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm");
        Date d = sdf.parse(s);
        return d.getTime();
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
        getMenuInflater().inflate(R.menu.menu_rdv_modif, menu);
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
