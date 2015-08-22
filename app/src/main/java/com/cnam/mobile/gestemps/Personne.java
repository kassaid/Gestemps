package com.cnam.mobile.gestemps;

/**
 * Created by mac on 06/08/2015.
 */
public class Personne {

    private long idPers;
    private String nomPers;
    private String prenomPers;
    private String adresPers;
    private float longitPers;
    private float latitPers;
    private String telPers;
    private String mailPers;
    private float soldePers;
    private String infoPers;

    public Personne (){}

    public Personne(String nomPers,
                    String prenomPers,
                    String adresPers,
                    float longitPers,
                    float latitPers,
                    String telPers,
                    String mailPers,
                    float soldePers,
                    String infoPers) {
        this.nomPers = nomPers;
        this.prenomPers = prenomPers;
        this.adresPers = adresPers;
        this.longitPers = longitPers;
        this.latitPers = latitPers;
        this.telPers = telPers;
        this.mailPers = mailPers;
        this.soldePers = soldePers;
        this.infoPers = infoPers;
    }

    public long getIdPers() {
        return idPers;
    }

    public void setIdPers(long idPers) {
        this.idPers = idPers;
    }

    public String getNomPers() {
        return nomPers;
    }

    public void setNomPers(String nomPers) {
        this.nomPers = nomPers;
    }

    public String getPrenomPers() {
        return prenomPers;
    }

    public void setPrenomPers(String prenomPers) {
        this.prenomPers = prenomPers;
    }

    public String getAdresPers() {
        return adresPers;
    }

    public void setAdresPers(String adresPers) {
        this.adresPers = adresPers;
    }

    public float getLongitPers() {
        return longitPers;
    }

    public void setLongitPers(float longitPers) {
        this.longitPers = longitPers;
    }

    public float getLatitPers() {
        return latitPers;
    }

    public void setLatitPers(float latitPers) {
        this.latitPers = latitPers;
    }

    public String getTelPers() {
        return telPers;
    }

    public void setTelPers(String telPers) {
        this.telPers = telPers;
    }

    public String getMailPers() {
        return mailPers;
    }

    public void setMailPers(String mailPers) {
        this.mailPers = mailPers;
    }

    public float getSoldePers() {
        return soldePers;
    }

    public void setSoldePers(float soldePers) {
        this.soldePers = soldePers;
    }

    public String getInfoPers() {
        return infoPers;
    }

    public void setInfoPers(String infoPers) {
        this.infoPers = infoPers;
    }

    public float creditSolde(float m){
        return m + getSoldePers();
    }

    public float debitSolde(long m){
        return m - getSoldePers();
    }

    @Override
    public String toString() {
        return "\nPersonne{" +
                "idPers=" + idPers +
                ", nomPers='" + nomPers + '\'' +
                ", prenomPers='" + prenomPers + '\'' +
                ", adresPers='" + adresPers + '\'' +
                ", longitPers=" + longitPers +
                ", latitPers=" + latitPers +
                ", telPers='" + telPers + '\'' +
                ", mailPers='" + mailPers + '\'' +
                ", soldePers=" + soldePers +
                '}';
    }
}
