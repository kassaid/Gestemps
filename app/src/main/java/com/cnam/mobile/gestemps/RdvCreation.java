package com.cnam.mobile.gestemps;

import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class RdvCreation extends AppCompatActivity {


    private Personne nomPosition;
    private List<PersonneNom> allNomPers;
    private List<Personne> listPers;
    private Personne pers;
    private long idPersRdv = 1;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    public final String tag = "rdvCreation-test";
    String libRdv, adresseRdv, dateRdv, horaireRdv, dureeRdv, niveauRdv, tarifRdv, infoRdv;
    Context ct = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdv_creation);


        //final EditText lib = (EditText) findViewById(R.id.libEdit);
        final EditText adresse = (EditText) findViewById(R.id.adresseEdit);
        final EditText date = (EditText) findViewById(R.id.dateEdit);
        final EditText horaire = (EditText) findViewById(R.id.horaireEdit);
        final EditText duree = (EditText) findViewById(R.id.dureeEdit);
        //final EditText niveau = (EditText) findViewById(R.id.niveauEdit);
        final EditText tarif = (EditText) findViewById(R.id.tarifEdit);
        final EditText info = (EditText) findViewById(R.id.infoEdit);
        final Button btValider=(Button) findViewById(R.id.button1);
        final Button btAnnuler=(Button) findViewById(R.id.button2);

        final Spinner spinlistPers = (Spinner) findViewById(R.id.personneSpinner);


        final PersonneDAO persdao = new PersonneDAO(ct);
        persdao.open();
        allNomPers = persdao.allPersNames();

        ArrayAdapter<PersonneNom> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,allNomPers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinlistPers.setAdapter(adapter);
        spinlistPers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nomPosition = allNomPers.get(position);
                pers = persdao.getPersonneById(nomPosition.getIdPers());
                adresse.setText(pers.getAdresPers());
                idPersRdv = pers.getIdPers();
                if (position!=0){
                    Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " est choisi",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Spinner spinNiv = (Spinner) findViewById(R.id.niveauSpinner);
        final ArrayAdapter<CharSequence> adaptNiveau = ArrayAdapter.createFromResource(this,R.array.spinNiveau,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinNiv.setAdapter(adaptNiveau);
        spinNiv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                niveauRdv = parent.getItemAtPosition(position).toString();
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
                adresseRdv = adresse.getText().toString();
                dateRdv = date.getText().toString();
                horaireRdv = horaire.getText().toString();
                dureeRdv = duree.getText().toString();
                tarifRdv = tarif.getText().toString();
                infoRdv = info.getText().toString();
                libRdv = "OK";


                if (adresseRdv.equals("")||dateRdv.equals("")||horaireRdv.equals("")||
                        dureeRdv.equals("")){
                    Toast.makeText(getBaseContext(), "Veuillez remplir les champs obligatoires!",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Rdv rdv = null;
                    Log.i(tag, "envoie creation");

                    try {
                        Log.i(tag, "Un compte creation en cours");
                        rdv = new Rdv(libRdv,
                                adresseRdv,
                                123,
                                456,
                                changeDate(dateRdv+" "+horaireRdv),
                                horaireRdv,
                                changeLong(dureeRdv),
                                0,
                                0,
                                niveauRdv,
                                changeFloat(tarifRdv),
                                0,
                                0f,
                                infoRdv,
                                idPersRdv);

                        setAlerte(changeDate(dateRdv + " " + horaireRdv));

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
                    i.putExtra("libRdv", libRdv);
                    i.putExtra("dateRdv", dateRdv);
                    setResult(RESULT_OK,i);
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

    public  long changeDate(String s) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
        Date d = sdf.parse(s);
        long res = d.getTime();
        Log.d(tag, "Resultat: " + res);
        return res;
    }

    public float changeFloat(String s){
        return parseFloat(s);
    }

    public long changeLong(String s){
        return parseLong(s, 10);
    }

    //Pointage horaire
    public long timeStamp(){
        final Date date = new Date();
        return date.getTime();
    }

    private void setAlerte(long date){
        alarmMgr = (AlarmManager)ct.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(ct, MonRecepteurDeb.class);
        alarmIntent = PendingIntent.getBroadcast(ct, 1, intent, 0);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, date, alarmIntent);
        Log.d(tag, "alarme déclenchée par setAlerte RdvCreation");
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

    public void onDestroy(){
        super.onDestroy();
            Intent i = new Intent(RdvCreation.this, MainActivity.class);
            startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rdv_creation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}