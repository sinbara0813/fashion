package com.d2cmall.buyer.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.d2cmall.buyer.activity.HomeActivity;

/**
 * Author: PengHong
 * Date: 2016/11/23 19:44
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class PushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //通知栏消息点击
        String url = intent.getStringExtra("url");
        Intent homeIntent = new Intent(context, HomeActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        homeIntent.putExtra("url",url);
        context.startActivity(homeIntent);
        /*if (!Util.isEmpty(url)) {
            EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.PUSH, url));
        }*/
    }
}
