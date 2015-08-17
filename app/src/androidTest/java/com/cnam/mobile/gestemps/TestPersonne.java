package com.cnam.mobile.gestemps;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import java.util.List;

/**
 * Created by mac on 07/08/2015.
 */
public class TestPersonne extends AndroidTestCase {

    String tag = "testBDPers";

    //Context ct;
    PersonneDAO persDao;
    RdvDAO rdvDao;

    @Override
    public void setUp(){
        Log.i(tag,"deleteAll setUp");
        persDao = new PersonneDAO(this.getContext());
        rdvDao = new RdvDAO(this.getContext());

        persDao.open();
        rdvDao.open();
        persDao.deleteAll();
        rdvDao.deleteAll();
    }

    public void testCreation(){
//        ModelSQLite m = new ModelSQLite(this.getContext());
//        Log.i(tag, "liste1 des tables " + m.allTableNames());
        PersonneDAO persdao = new PersonneDAO(this.getContext());
        persdao.open();
        Log.i(tag, "liste1 des tables " + persdao.allTableNames());
    }


    public void testCreaPersonneAndSave(){
//        ModelSQLite m = new ModelSQLite(this.getContext());
//        Log.i(tag, "liste2 des tables " + m.allTableNames());
        PersonneDAO persdao = new PersonneDAO(this.getContext());
        persdao.open();
        Log.i(tag, "liste2 des tables " + persdao.allTableNames());
        Personne p = new Personne("DRATATION","Daisy","15 rue Papin 25000 Besançon",236,458,"0672264323","daisy.dratation@mailfr",65,"cours avec daisy");
        persdao.save(p);
    }


    public void testCreaPersonneAndSaveAsCursor(){
        PersonneDAO persdao = new PersonneDAO(this.getContext());
        persdao.open();
        Log.i(tag, "liste3 des tables " + persdao.allTableNames());
        Personne p = new Personne("JEFFROIS","Sandra","12 rue Papin 25000 Besançon",67,89,
                "0678564323","sandra.jeffrois@mail.fr",90,"cours avec sandra");
        persdao.save(p);

        Cursor c = persdao.getPersonneAsCursor();
        Log.i(tag, "cursor PERSONNE " + c.getColumnCount() + " sur " + c.getCount());
    }

    public void testCreaRdvAndSaveAsCursor(){
        RdvDAO rdvdao = new RdvDAO(this.getContext());
        rdvdao.open();
        //Log.i(tag, "liste3 des tables " + rdvdao.allTableNames());
        Rdv rdv = new Rdv("RDV Martin","12 rue Papin 25000 Besançon",67,89,
                123435535,"10:30",2,745,4677,"Terminale",40,80,320,"ok man",1);
        rdvdao.save(rdv);

        Cursor c = rdvdao.getRdvAsCursor();
        Log.i(tag,"cursor RDV"+ c.getColumnCount() +" sur "+ c.getCount());
    }

    public void testCreaPersonneAndSaveAsGetAllAsObject(){
        PersonneDAO persdao = new PersonneDAO(this.getContext());
        persdao.open();
        Log.i(tag, "liste4 des tables" + persdao.allTableNames());
        Personne p = new Personne("BIEN","Samira","16 rue Papin 25000 Besançon",27,82,
                "0678564893","sandra.jeffrois@mail.fr",98,"cours avec sandra");
        persdao.save(p);
        List<Personne> list = persdao.getAllPersonne();
        Log.i(tag, "Liste " + list);
    }

    public void testSupprimer(){
        PersonneDAO persdao = new PersonneDAO(this.getContext());
        persdao.open();
        Log.i(tag, "liste5 des tables" + persdao.allTableNames());
        Personne dernier = null;
        for (int i=0;i<5;i++){
            Personne p = new Personne("MICHOU"+i,"lili"+i, "16 rue Papin 25000 Besançon",27,82,
                "067856489"+i,"sandra.jeffrois@mail.fr",98,"cours avec sandra");
        persdao.save(p);
            dernier = p;
        }
        List<Personne> list1 = persdao.getAllPersonne();
        Log.i(tag, "Liste MICHOU avant suppression " + list1);

        persdao.delete(dernier);
        List<Personne> list2 = persdao.getAllPersonne();
        Log.i(tag, "Liste MICHOU après suppression " + list2);
    }

    public void testCreaPersonneAndRdvAsList(){
        PersonneDAO persdao = new PersonneDAO(this.getContext());
        persdao.open();
        for (int i=0;i<5;i++){
            Personne p = new Personne("MICHOU"+i,"lili"+i, "16 rue Papin 25000 Besançon",27,82,
                    "067856489"+i,"sandra.jeffrois@mail.fr",98,"cours avec sandra");
            persdao.save(p);
        }
        RdvDAO rdvdao = new RdvDAO(this.getContext());
        rdvdao.open();
        Log.i(tag, "liste6 des tables " + persdao.allTableNames());
        Rdv rdv = new Rdv("RDV Martin","12 rue Papin 25000 Besançon",67,89,
                1234455563,"10:30",3,5678,23457,"Terminale",40,80,320,"ok man",1);
        rdvdao.save(rdv);

        List<Personne> list = persdao.getAllPersonne();
        Log.i(tag, "Liste PERSONNE " + list);

        List<Rdv> list2 = rdvdao.getAllRdv();
        Log.i(tag, "Liste RDV " + list2);

        int nombPers = persdao.nbPersonne();
        Log.i(tag, "Nombre de PERSONNE : " + nombPers);

        int nombRdv = rdvdao.nbRdv();
        Log.i(tag, "Nombre de RDV : " + nombRdv);
    }

    public void testSelectPersByMail(){
        PersonneDAO persdao = new PersonneDAO(this.getContext());
        persdao.open();
        Log.i(tag, "liste7 des tables" + persdao.allTableNames());
        Personne p = new Personne("ARMONDO","Ali","19 rue Papin 25000 Besançon",27,82,
                "0678565611","ali.armondo@mail.fr",98,"Elève ali");
        persdao.save(p);
        Log.i(tag, "Une personne est créée: " + p);

        int nombPers = persdao.nbPersonne();
        Log.i(tag, "Nombre de PERSONNE : " + nombPers);

        Personne selection = persdao.getPersonneByMail(p.getMailPers());
        Log.i(tag,"La personne selectionnée est: "+ selection);
    }

    public void testSelectPersByNomPrenom(){
        PersonneDAO persdao = new PersonneDAO(this.getContext());
        persdao.open();
        Log.i(tag, "liste8 des tables" + persdao.allTableNames());
        Personne p = new Personne("BERMUDA","Bill","29 rue Papin 25000 Besançon",27,82,
                "0678565622","bill.bermuda@mail.fr",98,"Elève bill");
        persdao.save(p);
        Log.i(tag, "Une personne est créée: " + p);

        int nombPers = persdao.nbPersonne();
        Log.i(tag, "Nombre de PERSONNE : " + nombPers);

        Personne selection = persdao.getPersonneByNomPrenom(p.getNomPers());
        Log.i(tag,"La personne selectionnée est: "+ selection);
    }

    public void testListeNomPersonne(){
        PersonneDAO persdao = new PersonneDAO(this.getContext());
        persdao.open();
        for (int i=0;i<7;i++){
            Personne p = new Personne("PIPOLINO"+i,"lili"+i, i+"16 rue Papin 25000 Besançon",27,82,
                    "067856489"+i,"lili.pipo@mail.fr"+i,98,"cours avec lili"+i);
            persdao.save(p);
        }
        persdao.allPersNames();

        Log.i(tag, "liste des nom des personnes : " + persdao.allPersNames());


        List<Personne> list = persdao.getAllPersonne();
        Log.i(tag, "Liste PERSONNE " + list);


        int nombPers = persdao.nbPersonne();
        Log.i(tag, "Nombre de PERSONNE : " + nombPers);

    }

}
