package com.cnam.mobile.gestemps;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;

/**
 * Created by mac on 07/08/2015.
 */
public class ModelSQLite extends SQLiteOpenHelper {

    String tag = "model";

    private final static int VERSION_BD = 12;
    private final static String NOM_BD = "BDGest";


    public static final String CREATE_PERSONNE = ""
            + "CREATE TABLE table_personne (idPers INTEGER,"
            + "nomPers TEXT,"
            + "prenomPers TEXT,"
            + "adresPers TEXT,"
            + "longitPers TEXT,"
            + "latitPers TEXT,"
            + "telPers TEXT,"
            + "mailPers TEXT,"
            + "soldePers TEXT,"
            + "infoPers TEXT,"
            + "PRIMARY KEY (idPers));";

    public static final String DROP_PERSONNE = ""
            + "DROP TABLE IF EXISTS table_personne ;";

    public static final String CREATE_RDV = ""
            + "CREATE TABLE table_rdv (idRdv INTEGER,"
            + "libRdv TEXT,"
            + "adresRdv TEXT,"
            + "longitRdv TEXT,"
            + "latitRdv TEXT,"
            + "dateRdv TEXT,"
            + "horaireRdv TEXT,"
            + "dureeRdv TEXT,"
            + "pointDebRdv TEXT,"
            + "pointFinRdv TEXT,"
            + "niveauRdv TEXT,"
            + "tarifRdv TEXT,"
            + "soldeRdv TEXT,"
            + "infoRdv TEXT,"
            + "idPers TEXT,"
            + "PRIMARY KEY (idRdv));"
            + "FOREIGN KEY (idPers) REFERENCES table_personne (idPers),";

    public static final String DROP_RDV = ""
            + "DROP TABLE IF EXISTS table_rdv ;";


    public ModelSQLite(Context context) {
        super(context, NOM_BD, null, VERSION_BD);
        Log.i(tag,"La base est ouverte!");
    }

    public ModelSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.i(tag, "constructeur");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(tag, "creation de base");
        db.execSQL(CREATE_PERSONNE);
        db.execSQL(CREATE_RDV);
        Log.i(tag, "fin creation d base");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(tag, "upgrade base");
        db.execSQL(DROP_PERSONNE);
        db.execSQL(DROP_RDV);
        onCreate(db);
        Log.i(tag, "fin upgrade base");
    }



}
