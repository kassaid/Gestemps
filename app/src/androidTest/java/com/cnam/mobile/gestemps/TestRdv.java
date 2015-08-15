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
        //persDao.deleteAll();
        //rdvDao.deleteAll();
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
                "2015-08-13","10:30",2,100,200,"Terminale",40,80,320,"ok man",1);
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
//        Cursor c = rdvdao.getRdvAsCursor();
//        Log.i(tag,"cursor RDV"+ c.getColumnCount() +" sur "+ c.getCount());
    }




}
