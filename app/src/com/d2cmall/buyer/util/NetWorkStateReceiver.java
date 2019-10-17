package com.d2cmall.buyer.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.bean.GlobalTypeBean;

import de.greenrobot.event.EventBus;

public class NetWorkStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()) {
            EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.NOT_NETWORK));
        } else if (networkInfo.isAvailable()) {
            EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.COME_NETWORK));
        }
    }
}
