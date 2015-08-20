package com.cnam.mobile.gestemps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by eymbla on 20/08/2015.
 */
public class PersonneListe extends AppCompatActivity implements PersonneAdapter.PersonneAdapterListener{
    /** Contient une liste de personnes **/
    private ArrayList<Personne> listPers;
    /** Affichage de la liste des personnes **/
    //private ListView lvPersonnes;

    public void onClickNom(final Personne item, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Personne");

        builder.setMessage("Voulez-vous voir " + item.getNomPers() +" ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int whichButton)
            {
                long iidPers = item.getIdPers();
                String inomPers = item.getNomPers();
                String iprenomPers = item.getPrenomPers();


                Intent i=new Intent(PersonneListe.this, SeanceAvant.class);
                i.putExtra("idPers", iidPers);
                i.putExtra("nomPers", inomPers);
                i.putExtra("prenomPers", iprenomPers);


                startActivity(i);
                finish();

            }
        });
        builder.setNegativeButton("Non", null);
        builder.show();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personne_liste);
        // Ajout de personnes tests
        //listPers = new String[]{"TRUC", "MACHIN", "LAUTRE"};
        // recupération de la vue du layout
       // lvPersonnes = (ListView)findViewById(R.id.lvPersonnes);
        //On ajoute un adaptateur
        //lvPersonnes.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listPers));



        PersonneDAO persdao = new PersonneDAO(getBaseContext());
        persdao.open();

        listPers = (ArrayList<Personne>) persdao.getAllPersonne();

        PersonneAdapter adapter = new PersonneAdapter(this, listPers);

        adapter.addListener((PersonneAdapter.PersonneAdapterListener) this);

        ListView list = (ListView)findViewById(R.id.personneListView);

        list.setAdapter(adapter);

    }
}
