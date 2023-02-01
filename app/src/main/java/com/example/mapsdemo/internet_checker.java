package com.example.mapsdemo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.security.Provider;

public class internet_checker extends Service
{
    @Override
    public  void onCreate()
    {
        super.onCreate();
        Log.d("inside","oncreate");
    }
    @Nullable
    @Override
    public IBinder onBind(Intent i)
    {
        throw new UnsupportedOperationException("not yet done");
    }

    @Override
    public  int onStartCommand(Intent intent,int flags, int startid)
    {
        handler.post(periodicUpdate);
        return START_STICKY;
    }
    public  boolean isOnline(Context c)
    {
        ConnectivityManager c_m = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo n_i = c_m.getActiveNetworkInfo();

        if(n_i!=null && n_i.isConnected())
        {
            return true;
        }
        else{
            return false;
        }
    }
    Handler handler = new Handler();
    private Runnable periodicUpdate = new Runnable()
    {
        @Override
        public void run()
        {
            handler.postDelayed(periodicUpdate,1*1000);
            Intent b_c_i = new Intent();
            b_c_i.setAction(MapsActivity.B_S_F_A);
            b_c_i.putExtra("online_status",""+isOnline(internet_checker.this));
            //b_c_i.putExtra("online_or_offline",)
            sendBroadcast(b_c_i);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("inside","destroyed");
    }
}
