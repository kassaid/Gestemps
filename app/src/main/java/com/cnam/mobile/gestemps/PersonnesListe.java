package com.cnam.mobile.gestemps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by eymbla on 20/08/2015.
 */
public class PersonnesListe extends AppCompatActivity {
    /** Contient une liste de personnes **/
    private String[] listPers;
    /** Affichage de la liste des personnes **/
    private ListView lvPersonnes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personne_liste);
        // Ajout de personnes tests
        listPers = new String[]{"TRUC", "MACHIN", "LAUTRE"};
        // recupération de la vue du layout
        lvPersonnes = (ListView)findViewById(R.id.lvPersonnes);
        //On ajoute un adaptateur
        lvPersonnes.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listPers));
    }
}
