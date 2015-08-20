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
 * Created by mac on 20/08/2015.
 */
public class PersonneAdapter extends BaseAdapter {

    private List<Personne> list;

    private Context ct;

    private LayoutInflater affiche;

    public PersonneAdapter(Context context, List<Personne> laliste){
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

            item = (LinearLayout) affiche.inflate(R.layout.personne_liste, parent, false);
        } else {
            item = (LinearLayout) convertView;
        }

        TextView pers_nom = (TextView)item.findViewById(R.id.pers_nom);
        TextView pers_prenom = (TextView)item.findViewById(R.id.pers_prenom);

        pers_nom.setText(list.get(position).getNomPers());
        pers_prenom.setText((list.get(position).getPrenomPers()));



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

    public interface PersonneAdapterListener {
        public void onClickNom(Personne item, int position);
    }


    private ArrayList<PersonneAdapterListener> listListener = new ArrayList<PersonneAdapterListener>();

    public void addListener(PersonneAdapterListener list) {
        listListener.add(list);
    }


    private void sendListener(Personne item, int position) {
        for(int i = listListener.size()-1; i >= 0; i--) {
            listListener.get(i).onClickNom(item, position);
        }
    }
}
