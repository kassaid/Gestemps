package com.cnam.mobile.gestemps;

/**
 * Created by Maverick on 25/08/2015.
 */
public class PersonneNom extends Personne {

    public PersonneNom(int id, String nomPers, String prenomPers) {
        this.idPers = id;
        this.nomPers = nomPers;
        this.prenomPers = prenomPers;
    }

    @Override
    public String toString() {
        return nomPers + " "
                + prenomPers;
    }
}
