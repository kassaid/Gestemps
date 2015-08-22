package com.cnam.mobile.gestemps;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import java.text.ParseException;
import java.util.List;

/**
 * Created by mac on 15/08/2015.
 */
public class TestRdv extends AndroidTestCase {

    String tag = "testBDRdv";

    //Context ct;
    PersonneDAO persDao;
    RdvDAO rdvDao;

    @Override
    public void setUp(){
        Log.i(tag, "deleteAll setUp");
        persDao = new PersonneDAO(this.getContext());
        rdvDao = new RdvDAO(this.getContext());

        persDao.open();
        rdvDao.open();
        persDao.deleteAll();
        rdvDao.deleteAll();
    }

    public void testDateRdv() throws ParseException {
        Rdv rdv = new Rdv();
        String sd1 = "23/03/2015";
        String sd2 = "23/3/2015 11:30";
        String sd3 = "11:30";
        String sd4 = "23/03/2015";
        String sd5 = "23/03/2015 10:00";
        String sd6 = "21/03/2015 10:00";
        String sd7 = "21/03/2015 9:50";
        long d2 = rdv.changeDate(sd2);
        Log.i(tag, "date string "+sd2+" devient : "+d2);
        long d3 = rdv.changeDate(sd4+" "+sd3);
        Log.i(tag, "date string "+sd4+" "+sd3+" devient : "+d3);
        long d5 = rdv.changeDate(sd5);
        long d6 = rdv.changeDate(sd6);
        long d7 = rdv.changeDate(sd7);
        long d = d5-d6;
        String dateStr = rdv.diffDateTime(d5, d6);
        Log.i(tag, "diff date "+d+" devient : "+dateStr);
        long d67 = d6-d7;
        String dateStr2 = rdv.diffDateTime(d6,d7);
        Log.i(tag, "diff date "+d67+" devient : "+dateStr2);

    }

    public void testModifRdv(){
        RdvDAO rdvdao = new RdvDAO(this.getContext());
        rdvdao.open();
        //Log.i(tag, "liste3 des tables " + rdvdao.allTableNames());
        Rdv rdv = new Rdv("RDV MAKOSSA","25 rue Papin 25000 Besançon",67,89,
                234455,"10:30",10000,100,200,"Terminale",40,80,320,"ok man",1);
        rdvdao.save(rdv);
        Log.i(tag,"Un RDV est créé: "+ rdv);

        //Rdv rdv2 = rdvdao.getRdvById()
        //Log.i(tag,"Le RDV selectionné est: "+ rdv2);
            rdv.setPointDebRdv(1969);
        rdvdao.modifier(rdv);
        Log.i(tag, "Un RDV est modifié: " + rdv);
        long id = 2;
        Rdv rdv2 = rdvdao.getRdvById(id);
        Log.i(tag,"Le RDV selectionné est: "+ rdv2);

        List<Rdv> list1 = rdvdao.getAllRdv();
        Log.i(tag, "Liste RDV : " + list1);
    }

    public void testMontantRdv(){
        RdvDAO rdvdao = new RdvDAO(this.getContext());
        rdvdao.open();
        rdvDao.deleteAll();
        //Log.i(tag, "liste3 des tables " + rdvdao.allTableNames());
        Rdv rdv = new Rdv("RDV KOPASTRUM","252 rue Papino 25000 Besançon",67,89,
                1456789856,"10:30",2,1000,1801000,"Terminale",100,80,320,"ok man",1);
        rdvdao.save(rdv);
        Log.i(tag,"Un RDV est créé: "+ rdv);

        long m = rdv.montantSeance(rdv.getPointFinRdv(), rdv.getPointDebRdv());
        rdv.setMontantRdv(m);
        rdvdao.modifier(rdv);
        Log.i(tag, "Un RDV est modifié: " + rdv);

        long id = rdv.getIdRdv();
        Rdv rdv2 = rdvdao.getRdvById(id);
        Log.i(tag,"Le RDV selectionné est: "+ rdv2);

//        Rdv rdv3 = rdvdao.getRdvById(id);
//        String date = rdv3.changeDate(rdv3.getDateRdv());
//        Log.i(tag,"La date rdv selectionné est: "+date);

    }

    public void testDatedeRdv(){
        RdvDAO rdvdao = new RdvDAO(this.getContext());
        rdvdao.open();
        rdvDao.deleteAll();
        Rdv dernier = null;
            for (int i=0;i<4;i++) {
                Rdv rdv = new Rdv("RDV VOPASTRUM"+i, i+"19 rue Papino 25000 Besançon", 67, 89,
                        1456789856, "10:30", 10000, 1000, 1801000, "Terminale", 100, 80, 320, "ok man"+i, 1);
                rdvdao.save(rdv);
                dernier = rdv;
            }
        List<Rdv> list1 = rdvdao.getAllRdv();
        Log.i(tag, "Liste VOPASTRUM avant modification " + list1);

        String date = dernier.changeDate(dernier.getDateRdv());
        Log.i(tag,"La date rdv selectionné est: "+date);


    }

    public void testListRdvFutur(){
        PersonneDAO persdao = new PersonneDAO(this.getContext());
        persdao.open();
        persdao.deleteAll();
        for (int i=0;i<10;i++){
            Personne p = new Personne("JEFFROIS"+i,"Sandra"+i, i+"16 rue Papin 25000 Besançon",27,82,
                    "067856489"+i,"sandra.jeffrois@mail.fr"+i,98,"cours avec sandra"+i);
            persdao.save(p);
        }

        RdvDAO rdvdao = new RdvDAO(this.getContext());
        rdvdao.open();
        rdvDao.deleteAll();
        Rdv dernier = null;
        for (int i=0;i<9;i++) {
            Rdv rdv = new Rdv("RDV URGENT"+i, i+"19 rue Papino 25000 Besançon", 67, 89,1456789851, "10:30", 10000, 0, 0, "Terminale", 100, 80, 320, "ok man"+i, i+1);
            rdv.setDateRdv(rdvdao.timeStamp()+i*24*60*60*1000);
            rdvdao.save(rdv);
            dernier = rdv;
        }
        List<Personne> list = persdao.getAllPersonne();
        Log.i(tag, "Liste PERSONNE " + list);

        List<Rdv> list2 = rdvdao.getAllRdv();
        Log.i(tag, "Liste RDV " + list2);

        int nombPers = persdao.nbPersonne();
        Log.i(tag, "Nombre de PERSONNE : " + nombPers);

        int nombRdv = rdvdao.nbRdv();
        Log.i(tag, "Nombre de RDV : " + nombRdv);

        long t = rdvdao.timeStamp()+4*24*60*60*1000;

        List<Rdv> list3 = rdvdao.getAllRdvFutur(t);
        Log.i(tag, "Liste RDV FUTUR : " + list3);

        Cursor c = rdvdao.getRdvFuturAsCurs(t);
        Log.i(tag,"cursor RDV futur "+ c.getColumnCount() +" sur "+ c.getCount());

    }


    public void testUnRdvFuturByIdPers(){
        PersonneDAO persdao = new PersonneDAO(this.getContext());
        persdao.open();
        persdao.deleteAll();
        for (int i=0;i<10;i++){
            Personne p = new Personne("JEFFROIS"+i,"Sandra"+i, i+"16 rue Papin 25000 Besançon",27,82,
                    "067856489"+i,"sandra.jeffrois@mail.fr"+i,98+i*100,"cours avec sandra"+i);
            persdao.save(p);
        }

        RdvDAO rdvdao = new RdvDAO(this.getContext());
        rdvdao.open();
        rdvDao.deleteAll();
        Rdv dernier = null;
        for (int i=0;i<4;i++) {
            Rdv rdv = new Rdv("RDV URGENT"+i, i+"19 rue Papino 25000 Besançon", 67, 89,1456789851, "10:30", 10000, 0, 0, "Terminale", 100, 80, 320, "ok man"+i, 5);
            rdv.setDateRdv(rdvdao.timeStamp()+i*24*60*60*1000);
            rdvdao.save(rdv);
            dernier = rdv;
        }
        List<Personne> list = persdao.getAllPersonne();
        Log.i(tag, "Liste PERSONNE " + list);

        List<Rdv> list2 = rdvdao.getAllRdv();
        Log.i(tag, "Liste RDV " + list2);

        Rdv rdv = rdvdao.getRdvByIdPersAndTime(5,rdvdao.timeStamp());
        Log.i(tag,"Le premier RDV trouvé: "+ rdv);


    }

}
