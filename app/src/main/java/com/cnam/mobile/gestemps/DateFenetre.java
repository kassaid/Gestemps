package com.cnam.mobile.gestemps;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by mac on 14/08/2015.
 */
@SuppressLint("ValidFragment")
public class DateFenetre extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    EditText vDate;
    EditText vTime;
    public DateFenetre(View v){
        vDate = (EditText)v;
        vTime = (EditText)v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar cal = Calendar.getInstance();
        int jour = cal.get(Calendar.DAY_OF_MONTH);
        int mois = cal.get(Calendar.MONTH);
        int annee = cal.get(Calendar.YEAR);
        DatePickerDialog dialog;
        dialog = new DatePickerDialog(getActivity(),this,annee,mois,jour);
        return dialog;
    }
    

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date = day+"/"+(month+1)+"/"+year;
        vDate.setText(date);
    }
}