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
public class RdvDAO {

    public static final String tag = "RdvDAO-test";

    private Context ct;
    private SQLiteDatabase db;
    private ModelSQLite md;

    public static final String T_RDV = "table_rdv";
    public static final String ID = "idRdv";
    public static final String LIBELLE = "libRdv";
    public static final String ADRESSE = "adresRdv";
    public static final String LONGITUDE = "longitRdv";
    public static final String LATITUDE = "latitRdv";
    public static final String DATETIME = "dateRdv";
    public static final String HORAIRE = "horaireRdv";
    public static final String DUREE = "dureeRdv";
    public static final String POINTDEB = "pointDebRdv";
    public static final String POINTFIN = "pointFinRdv";
    public static final String NIVEAU = "niveauRdv";
    public static final String SOLDE = "soldeRdv";
    public static final String INFO = "infoRdv";
    public static final String IDPERS = "idPers";



    public RdvDAO(Context context) {
        this.ct = context;
        this.md = new ModelSQLite(ct);
    }

    public void open() {
        this.db = md.getWritableDatabase();
    }

    public Cursor getRdvAsCursor(){
        return db.rawQuery(
                "SELECT * FROM "
                        + T_RDV,
                null);
    }

    //Ajouter un tuple RDV dans la table RDV
    public void save(Rdv rdv){
        ContentValues v = new ContentValues();
        v.put(LIBELLE, rdv.getLibRdv());
        v.put(ADRESSE, rdv.getAdresRdv());
        v.put(LONGITUDE, rdv.getLongitRdv());
        v.put(LATITUDE, rdv.getLatitRdv());
        v.put(DATETIME, rdv.getDateRdv());
        v.put(HORAIRE, rdv.getHoraireRdv());
        v.put(DUREE, rdv.getDureeRdv());
        v.put(POINTDEB, rdv.getPointDebRdv());
        v.put(POINTFIN, rdv.getPointFinRdv());
        v.put(NIVEAU, rdv.getNiveauRdv());
        v.put(SOLDE, rdv.getSoldeRdv());
        v.put(INFO, rdv.getInfoRdv());
        v.put(IDPERS, rdv.getIdPers());
        db.insert(T_RDV, null, v);
        Log.i(tag, "ajout RDV réussi !");

    }

    //Supprimer un tuple RDV par son 'idRdv'
    public void delete(long idRdv)
    {
        db.delete(T_RDV, ID + " = ?", new String[] {String.valueOf(idRdv)});
        Log.i(tag,"Suppression réussi!");
    }

    //Supprimer un tuple RDV par l'objet RDV
    public void delete(Rdv rdv)
    {
        int i = db.delete(T_RDV, LIBELLE +" =?", new String[] {String.valueOf(rdv.getLibRdv())});
        Log.i(tag,"Suppression réussi de "+i+" tuple");
    }

    //Supprimer tous les tuples de la table RDV
    public void deleteAll()
    {
        db.delete(T_RDV, null, null);
        Log.i(tag,"Suppression totale RDV réussi !");
    }

    //Liste de tous les tuples PERSONNE
    public List<Rdv> getAllRdv(){
        List<Rdv> result = new ArrayList<Rdv>();
        Cursor c = getRdvAsCursor();
        if (c.moveToFirst()){
            do{
                Rdv rdv = new Rdv();
                long id = c.getLong(c.getColumnIndex(ID));
                rdv.setIdRdv(id);
                rdv.setLibRdv(c.getString(c.getColumnIndex(LIBELLE)));
                rdv.setAdresRdv(c.getString(c.getColumnIndex(ADRESSE)));
                rdv.setLongitRdv(c.getFloat(c.getColumnIndex(LONGITUDE)));
                rdv.setLatitRdv(c.getFloat(c.getColumnIndex(LATITUDE)));
                rdv.setDateRdv(c.getString(c.getColumnIndex(DATETIME)));
                rdv.setHoraireRdv(c.getString(c.getColumnIndex(HORAIRE)));
                rdv.setDureeRdv(c.getString(c.getColumnIndex(DUREE)));
                rdv.setPointDebRdv(c.getString(c.getColumnIndex(POINTDEB)));
                rdv.setPointFinRdv(c.getString(c.getColumnIndex(POINTFIN)));
                rdv.setNiveauRdv(c.getString(c.getColumnIndex(NIVEAU)));
                rdv.setSoldeRdv(c.getFloat(c.getColumnIndex(SOLDE)));
                rdv.setInfoRdv(c.getString(c.getColumnIndex(INFO)));
                rdv.setIdPers(c.getLong(c.getColumnIndex(IDPERS)));
                Log.i(tag,"Personne recupere de la BD : "+ rdv);
                result.add(rdv);
            }while(c.moveToNext());
        }
        return result;
    }

    //Nombre de tuple de la table RDV
    public int nbRdv()
    {
        Cursor c = getRdvAsCursor();
        return c.getCount();
    }

    //Conversion d'un Cursor RDV en objet RDV
    private Rdv cursorToRdv(Cursor c)
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
        Rdv retRdv = new Rdv();
        // Extraction des valeurs depuis le curseur
        retRdv.setIdRdv(Integer.parseInt(c.getString(0)));
        retRdv.setLibRdv(c.getString(1));
        retRdv.setAdresRdv(c.getString(2));
        retRdv.setLongitRdv(c.getFloat(3));
        retRdv.setLatitRdv(c.getFloat(4));
        retRdv.setDateRdv(c.getString(5));
        retRdv.setHoraireRdv(c.getString(6));
        retRdv.setDureeRdv(c.getString(7));
        retRdv.setPointDebRdv(c.getString(8));
        retRdv.setPointFinRdv(c.getString(9));
        retRdv.setNiveauRdv(c.getString(10));
        retRdv.setSoldeRdv(c.getFloat(11));
        retRdv.setInfoRdv(c.getString(12));
        retRdv.setIdPers(Integer.parseInt(c.getString(13)));
        // Ferme le curseur pour libérer les ressources
        c.close();
        Log.i(tag,"Cursor converti en objet RDV");
        return retRdv;
    }

    //Selection d'un objet RDV par son 'idRdv'
    public Rdv getRdvById (Long idRdv){
        Cursor c = db.query(
                T_RDV,
                null,
                ID + " =?",
                new String[]{String.valueOf(idRdv)},
                null, null, null, null);
        Log.i(tag,"RDV selectionnée par son id");

        return cursorToRdv(c);
    }

    //Selection des Cursor RDV par 'idPers'
    public Cursor getRdvAsCursByIdPers(long idPers){
        //open();
        Cursor c = db.query(
                T_RDV,
                null,
                IDPERS + " =?",
                new String[] {String.valueOf(idPers)},
                null, null, null, null);
        Log.i(tag,"nombre de tuples: "+ c.getCount());
        return c;
    }

}
