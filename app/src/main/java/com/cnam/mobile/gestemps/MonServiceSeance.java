package com.cnam.mobile.gestemps;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.net.BindException;

/**
 * Created by mac on 21/08/2015.
 */
public class MonServiceSeance extends Service {

    private final IBinder bin = new LeBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return bin;
    }

    public class LeBinder extends Binder {
        public MonServiceSeance getService(){
            return MonServiceSeance.this;
        }
    }

    public float montantSolde (float m){
        return 0;
    }

    public String textNbdeSeance(){
        String st = "";
        return st;
    }

}
