package com.fulai.myapplication.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.fulai.myapplication.MainActivity;
import com.fulai.myapplication.MyServiceAIDL;
import com.fulai.myapplication.R;

public class MainService extends Service {
    private boolean flag;
    private final static String TAG = "MainService";
    private Myservice myservice;

    public MainService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myservice = new Myservice();
        Log.d(TAG, "++MainService onCreate++");
        Notification no = new Notification(R.mipmap.ic_launcher, "有通知到来", System.currentTimeMillis());
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        no.setLatestEventInfo(this, "AIDLDemo", "running", pi);
        startForeground(1, no);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Bundle bundle = intent.getExtras();
        flag = bundle.getBoolean("flag");
        return myservice;
    }

    private class Myservice extends MyServiceAIDL.Stub {

        @Override
        public String getString(String key) throws RemoteException {
            return "key " + key;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "++MainService onDestroy++");
        flag = false;
    }
}
