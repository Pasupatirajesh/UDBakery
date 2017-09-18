package com.example.android.udbakery;

import android.app.Application;

/**
 * Created by SSubra27 on 9/7/17.
 */

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized  MyApplication getInstance()
    {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener)
    {
        ConnectivityReceiver.mConnectivityReceiverListener = listener;
    }
}
