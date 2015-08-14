package com.cnam.mobile.gestemps;

/**
 * Created by mac on 07/08/2015.
 */
public class Rdv {

    private long idRdv;
    private String libRdv;
    private String adresRdv;
    private float longitRdv;
    private float latitRdv;
    private String dateRdv;
    private String horaireRdv;
    private String dureeRdv;
    private String pointDebRdv;
    private String pointFinRdv;
    private String niveauRdv;
    private float tarifRdv;
    private float montantRdv;
    private float soldeRdv;
    private String infoRdv;
    private long idPers;

    public Rdv() {
    }


    public Rdv(String libRdv, String adresRdv, float longitRdv, float latitRdv, String dateRdv, String horaireRdv,
               String dureeRdv, String pointDebRdv, String pointFinRdv, String niveauRdv, float tarifRdv,
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

    public String getDateRdv() {
        return dateRdv;
    }

    public void setDateRdv(String dateRdv) {
        this.dateRdv = dateRdv;
    }

    public String getHoraireRdv() {
        return horaireRdv;
    }

    public void setHoraireRdv(String horaireRdv) {
        this.horaireRdv = horaireRdv;
    }

    public String getDureeRdv() {
        return dureeRdv;
    }

    public void setDureeRdv(String dureeRdv) {
        this.dureeRdv = dureeRdv;
    }

    public String getPointDebRdv() {
        return pointDebRdv;
    }

    public void setPointDebRdv(String pointDebRdv) {
        this.pointDebRdv = pointDebRdv;
    }

    public String getPointFinRdv() {
        return pointFinRdv;
    }

    public void setPointFinRdv(String pointFinRdv) {
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

    @Override
    public String toString() {
        return "Rdv{" +
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
