package com.cnam.mobile.gestemps;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 11/08/2015.
 */
public class PersonneDAO {

    public static final String tag = "PersonneDAO-test";

    private Context ct;
    private SQLiteDatabase db;
    private ModelSQLite md;

    public static final String T_PERSONNE = "table_personne";
    public static final String ID = "idPers";
    public static final String NOM = "nomPers";
    public static final String PRENOM = "prenomPers";
    public static final String ADRESSE = "adresPers";
    public static final String LONGITUDE = "longitPers";
    public static final String LATITUDE = "latitPers";
    public static final String TEL = "telPers";
    public static final String MAIL = "mailPers";
    public static final String SOLDE = "soldePers";
    public static final String INFO = "infoPers";

    public PersonneDAO(Context context) {
        //super(context);
        this.ct = context;
        this.md = new ModelSQLite(ct);
    }

    public void open() {
        this.db = md.getWritableDatabase();
    }

    public Cursor getPersonneAsCursor(){
        return db.rawQuery("select * from " + T_PERSONNE, null);
    }


    public List<String> allTableNames(){
        List<String> result = new ArrayList<String>();
        String selectQuery = "SELECT NAME FROM sqlite_master WHERE type = 'table'";
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()){
            do{
                String n = c.getString(c.getColumnIndex("name"));
                result.add(n);
            }while (c.moveToNext());
        }
        return result;
    }

    public List<String> allPersNames(){
        List<String> result = new ArrayList<String>();
        String selectQuery = "SELECT "+NOM+" FROM "+T_PERSONNE;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()){
            do{
                String n = c.getString(c.getColumnIndex(NOM));
                result.add(n);
            }while (c.moveToNext());
        }
        return result;
    }

    //Création d'une nouvelle PERSONNE
    public void save(Personne p){
        ContentValues v = new ContentValues();
        v.put(NOM,p.getNomPers());
        v.put(PRENOM,p.getPrenomPers());
        v.put(ADRESSE,p.getAdresPers());
        v.put(LONGITUDE,p.getLongitPers());
        v.put(LATITUDE,p.getLatitPers());
        v.put(TEL,p.getTelPers());
        v.put(MAIL,p.getMailPers());
        v.put(SOLDE,p.getSoldePers());
        v.put(INFO, p.getInfoPers());
        Long id = db.insert(T_PERSONNE, null, v);
        Log.i(tag, "ajout PERSONNE "+id+" réussi!");
    }

    //Supprimer tous les tuples PERSONNE de la table
    public void deleteAll()
    {
        db.delete(T_PERSONNE, null, null);
        //close();
        Log.i(tag,"Suppression totale PERSONNE réussi !");
    }

    //Modifier une PERSONNE
    public void modifier(Personne p)
    {
        //open();
        ContentValues v = new ContentValues();
        v.put(NOM,p.getNomPers());
        v.put(PRENOM,p.getPrenomPers());
        v.put(ADRESSE,p.getAdresPers());
        v.put(LONGITUDE,p.getLongitPers());
        v.put(LATITUDE,p.getLatitPers());
        v.put(TEL, p.getTelPers());
        v.put(MAIL, p.getMailPers());
        v.put(SOLDE, p.getSoldePers());
        v.put(INFO, p.getInfoPers());
        db.update(T_PERSONNE, v, ID + " = ?", new String[]{String.valueOf(p.getIdPers())});
        Log.i(tag, "modification PERSONNE "+" réussi!");
    }

    //Supprimer une PERSONNE
    public void delete(long idPers)
    {
        //db = covoit.getWritableDatabase();
        db.delete(T_PERSONNE, ID + " = ?", new String[]{String.valueOf(idPers)});
        Log.i(tag,"Suppression réussi!");
    }

    public void delete(Personne p)
    {
        //open();
        int i = db.delete(T_PERSONNE, NOM +" =?", new String[] {String.valueOf(p.getNomPers())});
        Log.i(tag,"Suppression réussi de "+i+" tuple");
        //close();
    }

    //Liste de tous les tuples PERSONNE
    public List<Personne> getAllPersonne(){
        //open();
        List<Personne> result = new ArrayList<Personne>();
        Cursor c = getPersonneAsCursor();
        if (c.moveToFirst()){
            do{
                Personne p = new Personne();
                //long id = c.getLong(c.getColumnIndex(ID));
                long id = c.getLong(c.getColumnIndex(ID));
                p.setIdPers(id);
                p.setNomPers(c.getString(c.getColumnIndex(NOM)));
                p.setPrenomPers(c.getString(c.getColumnIndex(PRENOM)));
                p.setAdresPers(c.getString(c.getColumnIndex(ADRESSE)));
                p.setLongitPers(c.getFloat(c.getColumnIndex(LONGITUDE)));
                p.setLatitPers(c.getFloat(c.getColumnIndex(LATITUDE)));
                p.setTelPers(c.getString(c.getColumnIndex(TEL)));
                p.setMailPers(c.getString(c.getColumnIndex(MAIL)));
                p.setSoldePers(c.getFloat(c.getColumnIndex(SOLDE)));
                p.setInfoPers(c.getString(c.getColumnIndex(INFO)));
                Log.i(tag,"Personne "+id+" recupere de la BD : "+ p);
                result.add(p);
            }while(c.moveToNext());
        }
        return result;
    }

    private Personne cursorToPersonne(Cursor c)
    {
        // Si la requête ne renvoie pas de résultat
        if (c.getCount() == 0)
        {
            Log.i(tag,"Aucun tuple");
            return null;
        }
        if (c.getCount() >1)
        {
            Log.i(tag,"Plusieurs tuples");
            return null;
        }
        c.moveToFirst();
        Personne retPersonne = new Personne();
        // Extraction des valeurs depuis le curseur
        retPersonne.setIdPers(Integer.parseInt(c.getString(0)));
        retPersonne.setNomPers(c.getString(1));
        retPersonne.setPrenomPers(c.getString(2));
        retPersonne.setAdresPers(c.getString(3));
        retPersonne.setLongitPers(c.getFloat(4));
        retPersonne.setLatitPers(c.getFloat(5));
        retPersonne.setTelPers(c.getString(6));
        retPersonne.setMailPers(c.getString(7));
        retPersonne.setSoldePers(c.getFloat(8));
        retPersonne.setInfoPers(c.getString(9));
        // Ferme le curseur pour libérer les ressources
        c.close();
        Log.i(tag,"Cursor converti en objet PERSONNE");
        return retPersonne;
    }


    //Selection d'un objet PERSONNE par son 'idPers'
    public Personne getPersonneById (Long idPers){
        Cursor c = db.query(
                T_PERSONNE,
                null,
                ID + " =?",
                new String[]{String.valueOf(idPers)},
                null, null, null, null);
        Log.i(tag,"PERSONNE selectionnée par son id");

        return cursorToPersonne(c);
    }

    //Selection d'un objet PERSONNE par son 'tel'
    public Personne getPersonneByTel (String tel){
        Cursor c = db.query(
                T_PERSONNE,
                null,
                TEL + " =?",
                new String[] {String.valueOf(tel)},
                null, null, null, null);
        Log.i(tag,"PESONNE selectionnée par son tél");

        return cursorToPersonne(c);
    }

    //Selection d'un objet PERSONNE par son 'mail'
    public Personne getPersonneByMail (String mail){
        Cursor c = db.query(
                T_PERSONNE,
                null,
                MAIL + " =?",
                new String[] {String.valueOf(mail)},
                null, null, null, null);
        Log.i(tag,"PESONNE selectionnée par son mail");

        return cursorToPersonne(c);
    }



    //Selection d'un objet PERSONNE par son 'nom'
    public Personne getPersonneByNomPrenom (String nom){
//        String req = "SELECT  * FROM "+
//                T_PERSONNE +" WHERE "+
//                NOM +" = "+ nom +" AND "+
//                PRENOM +" = "+ prenom;
//        Cursor c = db.rawQuery(req, null);

        Cursor c = db.query(
                T_PERSONNE,
                null,
                NOM + " =?",
                new String[] {String.valueOf(nom)},
                null, null, null, null);
        Log.i(tag,"PESONNE selectionnée par son mail");
        return cursorToPersonne(c);
    }

    public int nbPersonne()
    {
        //String req = "SELECT  * FROM " + T_PERSONNE;
        //db = covoit.getWritableDatabase();
        Cursor c = getPersonneAsCursor();
//        Cursor cur = db.rawQuery(req, null);
//        cur.close();

        return c.getCount();
    }


}
