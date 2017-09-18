package com.example.android.udbakery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by SSubra27 on 9/7/17.
 */

public class ConnectivityReceiver extends BroadcastReceiver {

    public static ConnectivityReceiverListener mConnectivityReceiverListener;

    public ConnectivityReceiver()
    {
        super();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netowrkInfo = cm.getActiveNetworkInfo();

        boolean isConnected = netowrkInfo != null && netowrkInfo.isConnectedOrConnecting();

        if(mConnectivityReceiverListener != null)
        {
            mConnectivityReceiverListener.onNetworkConnectionChanged(isConnected);
        }
    }

    public static boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) MyApplication.getInstance().getApplicationContext()
                                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public interface ConnectivityReceiverListener
    {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
