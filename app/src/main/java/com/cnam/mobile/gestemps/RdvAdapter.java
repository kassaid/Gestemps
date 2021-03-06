package com.cnam.mobile.gestemps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 11/08/2015.
 */
public class RdvAdapter extends BaseAdapter{

    private List<Rdv> list;

    private Context ct;

    private LayoutInflater affiche;

    private String nomPers, prenomPers, soldePers;


    public RdvAdapter(Context context, List<Rdv> laliste) {
        ct = context;
        list = laliste;
        affiche = LayoutInflater.from(ct);
    }


    @Override
    public int getCount() {
        int nb = list.size();
        return nb;
    }

    @Override
    public Object getItem(int position) {
        Object ob = list.get(position);
        return ob;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout item;

        if (convertView == null) {

            item = (LinearLayout) affiche.inflate(R.layout.rdv_liste, parent, false);
        } else {
            item = (LinearLayout) convertView;
        }



        //TextView rdv_libelle = (TextView)item.findViewById(R.id.rdv_libelle);
        TextView rdv_nomPers = (TextView)item.findViewById(R.id.rdv_nomPers);
        TextView rdv_prenomPers = (TextView)item.findViewById(R.id.rdv_prenomPers);
        TextView rdv_date = (TextView)item.findViewById(R.id.rdv_date);
//        TextView rdv_pointDeb = (TextView)item.findViewById(R.id.rdv_pointDeb);
//        TextView rdv_pointFin = (TextView)item.findViewById(R.id.rdv_pointFin);
        //TextView rdv_horaire = (TextView)item.findViewById(R.id.rdv_horaire);
        TextView rdv_dest = (TextView)item.findViewById(R.id.rdv_dest);
        TextView rdv_duree = (TextView)item.findViewById(R.id.rdv_duree);
        TextView rdv_niveau = (TextView)item.findViewById(R.id.rdv_niveau);
        TextView rdv_tarif = (TextView)item.findViewById(R.id.rdv_tarif);
        TextView rdv_soldePers = (TextView)item.findViewById(R.id.rdv_solde);
        //TextView rdv_idPers = (TextView)item.findViewById(R.id.rdv_idPers);
        //TextView rdv_montant = (TextView)item.findViewById(R.id.rdv_montant);

        final String iduree = "Durée prévue de la séance : "+String.valueOf(list.get(position).getDureeRdv())+" h";
        final String iniveau = "Niveau d'étude : "+list.get(position).getNiveauRdv();
        //final String ihoraire = "Début de séance à "+list.get(position).getHoraireRdv();
        final String itarif = "Tarif : "+String.valueOf(list.get(position).getTarifRdv())+" euros/h";


        PersonneDAO persdao = new PersonneDAO(ct);
        persdao.open();
        Personne p = persdao.getPersonneById(list.get(position).getIdPers());
        nomPers = p.getNomPers();
        prenomPers = p.getPrenomPers();
        soldePers = String.valueOf(p.getSoldePers());


        String strdate = list.get(position).changeDate(list.get(position).getDateRdv());
        final String isolde = "Solde du compte : "+soldePers+" euros";

        //rdv_libelle.setText(list.get(position).getLibRdv());
        rdv_nomPers.setText(nomPers);
        rdv_prenomPers.setText(prenomPers);
        //rdv_date.setText(String.valueOf(list.get(position).getDateRdv()));
        rdv_date.setText(strdate);
        //rdv_pointDeb.setText(String.valueOf(list.get(position).getPointDebRdv()));
        //rdv_pointFin.setText(String.valueOf(list.get(position).getPointFinRdv()));
       // rdv_horaire.setText(ihoraire);
        rdv_dest.setText(list.get(position).getAdresRdv());
        //rdv_duree.setText(String.valueOf(list.get(position).getNbplace()));
        rdv_duree.setText(iduree);
        rdv_niveau.setText(iniveau);
        rdv_tarif.setText(itarif);
        rdv_soldePers.setText(isolde);
        //rdv_idPers.setText(String.valueOf(list.get(position).getIdPers()));
       // rdv_montant.setText(String.valueOf(list.get(position).getPaiementRdv()));


        item.setTag(position);

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer position = (Integer)v.getTag();
                sendListener(list.get(position), position);
            }

        });

        return item;
    }


    public interface RdvAdapterListener {
        public void onClickNom(Rdv item, int position);
    }


    private ArrayList<RdvAdapterListener> listListener = new ArrayList<RdvAdapterListener>();

    public void addListener(RdvAdapterListener list) {
        listListener.add(list);
    }


    private void sendListener(Rdv item, int position) {
        for(int i = listListener.size()-1; i >= 0; i--) {
            listListener.get(i).onClickNom(item, position);
        }
    }
}
