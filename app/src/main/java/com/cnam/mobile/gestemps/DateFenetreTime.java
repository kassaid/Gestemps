package com.cnam.mobile.gestemps;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by mac on 14/08/2015.
 */
public class DateFenetreTime extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    EditText vTime;

    public DateFenetreTime(View v){
        vTime = (EditText)v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar cal = Calendar.getInstance();
        int heure = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        TimePickerDialog dialog;
        dialog = new TimePickerDialog(getActivity(),this,heure,minute,true);
        return dialog;
    }


    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {

        String time = hour+":"+minute;
        vTime.setText(time);
    }
}
