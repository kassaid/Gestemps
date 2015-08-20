package com.cnam.mobile.gestemps;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by mac on 07/08/2015.
 */
public class Rdv {

    private long idRdv;
    private String libRdv;
    private String adresRdv;
    private float longitRdv;
    private float latitRdv;
    private long dateRdv;
    private String horaireRdv;
    private long dureeRdv;
    private long pointDebRdv;
    private long pointFinRdv;
    private String niveauRdv;
    private float tarifRdv;
    private float montantRdv;
    private float soldeRdv;
    private String infoRdv;
    private long idPers;

    public Rdv() {
    }


    public Rdv(String libRdv, String adresRdv, float longitRdv, float latitRdv, long dateRdv, String horaireRdv,
               long dureeRdv, long pointDebRdv, long pointFinRdv, String niveauRdv, float tarifRdv,
               float montantRdv, float soldeRdv, String infoRdv, long idPers) {
        this.libRdv = libRdv;
        this.adresRdv = adresRdv;
        this.longitRdv = longitRdv;
        this.latitRdv = latitRdv;
        this.dateRdv = dateRdv;
        this.horaireRdv = horaireRdv;
        this.dureeRdv = dureeRdv;
        this.pointDebRdv = pointDebRdv;
        this.pointFinRdv = pointFinRdv;
        this.niveauRdv = niveauRdv;
        this.tarifRdv = tarifRdv;
        this.montantRdv = montantRdv;
        this.soldeRdv = soldeRdv;
        this.infoRdv = infoRdv;
        this.idPers = idPers;
    }

    public long getIdRdv() {
        return idRdv;
    }

    public void setIdRdv(long idRdv) {
        this.idRdv = idRdv;
    }

    public String getLibRdv() {
        return libRdv;
    }

    public void setLibRdv(String libRdv) {
        this.libRdv = libRdv;
    }

    public String getAdresRdv() {
        return adresRdv;
    }

    public void setAdresRdv(String adresRdv) {
        this.adresRdv = adresRdv;
    }

    public float getLongitRdv() {
        return longitRdv;
    }

    public void setLongitRdv(float longitRdv) {
        this.longitRdv = longitRdv;
    }

    public float getLatitRdv() {
        return latitRdv;
    }

    public void setLatitRdv(float latitRdv) {
        this.latitRdv = latitRdv;
    }

    public long getDateRdv() {
        return dateRdv;
    }

    public void setDateRdv(long dateRdv) {
        this.dateRdv = dateRdv;
    }

    public String getHoraireRdv() {
        return horaireRdv;
    }

    public void setHoraireRdv(String horaireRdv) {
        this.horaireRdv = horaireRdv;
    }

    public long getDureeRdv() {
        return dureeRdv;
    }

    public void setDureeRdv(long dureeRdv) {
        this.dureeRdv = dureeRdv;
    }

    public long getPointDebRdv() {
        return pointDebRdv;
    }

    public void setPointDebRdv(long pointDebRdv) {
        this.pointDebRdv = pointDebRdv;
    }

    public long getPointFinRdv() {
        return pointFinRdv;
    }

    public void setPointFinRdv(long pointFinRdv) {
        this.pointFinRdv = pointFinRdv;
    }

    public String getNiveauRdv() {
        return niveauRdv;
    }

    public void setNiveauRdv(String niveauRdv) {
        this.niveauRdv = niveauRdv;
    }

    public float getTarifRdv() {
        return tarifRdv;
    }

    public void setTarifRdv(float tarifRdv) {
        this.tarifRdv = tarifRdv;
    }

    public float getMontantRdv() {
        return montantRdv;
    }

    public void setMontantRdv(float montantRdv) {
        this.montantRdv = montantRdv;
    }

    public float getSoldeRdv() {
        return soldeRdv;
    }

    public void setSoldeRdv(float soldeRdv) {
        this.soldeRdv = soldeRdv;
    }

    public String getInfoRdv() {
        return infoRdv;
    }

    public void setInfoRdv(String infoRdv) {
        this.infoRdv = infoRdv;
    }

    public long getIdPers() {
        return idPers;
    }

    public void setIdPers(long idPers) {
        this.idPers = idPers;
    }


    public long dureeSeance (long fin, long debut){
        return fin - debut;
    }

    public long montantSeance (long fin, long debut){
        float m = ((float) (fin - debut) * getTarifRdv()) / (1000 * 60 * 60);
        long ma = Math.round(m);
        return ma;
    }


    public String diffDateTime(long recent,long ancien){
        long differnce = recent - ancien;
        long seconds = 0;
        long minutes = 0;
        long hours = 0;

        while(differnce>1000){
            differnce = differnce - 1000;
            seconds++;
            if(seconds==60){
                seconds = 0;
                minutes++;
            }

            if(minutes==60){
                minutes = 0;
                hours++;
            }

        }

        String intervalle = ""+hours+" h "+minutes+" min "+seconds+" s";
        return intervalle;

    }

    public  long changeDate(String s) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm");
        Date d = sdf.parse(s);
        return d.getTime();
    }

    //Ch
    public String changeDate(long d) {
        final Date date = new Date();
        date.setTime(d);
        return new SimpleDateFormat("dd/MM/yyyy 'à' hh:mm").format(date);
    }

    public float soldeTotal(long tps, float m){
        float montantSeance = getTarifRdv() * tps;
        float montantRecu = m;
        return montantRecu - montantSeance;
    }

    @Override
    public String toString() {
        return "\nRdv{" +
                "idRdv=" + idRdv +
                ", libRdv='" + libRdv + '\'' +
                ", adresRdv='" + adresRdv + '\'' +
                ", longitRdv=" + longitRdv +
                ", latitRdv=" + latitRdv +
                ", dateRdv='" + dateRdv + '\'' +
                ", horaireRdv='" + horaireRdv + '\'' +
                ", dureeRdv='" + dureeRdv + '\'' +
                ", pointDebRdv='" + pointDebRdv + '\'' +
                ", pointFinRdv='" + pointFinRdv + '\'' +
                ", niveauRdv='" + niveauRdv + '\'' +
                ", tarifRdv=" + tarifRdv +
                ", montantRdv=" + montantRdv +
                ", soldeRdv=" + soldeRdv +
                ", infoRdv='" + infoRdv + '\'' +
                ", idPers=" + idPers +
                '}';
    }
}
