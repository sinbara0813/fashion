package com.d2cmall.buyer.util;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.SendDeviceApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.PushBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.widget.MsgPopNotification;
import com.google.gson.Gson;
import com.igexin.sdk.PushConsts;

import java.util.List;

import de.greenrobot.event.EventBus;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Author: PengHong
 * Date: 2016/11/11 12:50
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class GTPushReceiver extends BroadcastReceiver {
    //通知管理器
    private NotificationManager notificationManager;
    //通知
    private Notification notification;
    NotificationCompat.Builder builder;
    public static int NOTIFY_ID = 0;
    public static int ACTION_ID = 0;
    private PendingIntent pendingIntent;
    PushBean pushBean;//推送Bean
    public static final String id = "channel_1";
    public static final String name = "channel_name_1";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传（payload）数据
                byte[] payload = bundle.getByteArray("payload");
                String data;
                if (payload != null) {
                    data = new String(payload);
//                    Log.d("wym", "发送通知栏消息 " + data);
                } else {
                    return;
                }
                try {
                    Gson gson = new Gson();
                    pushBean = gson.fromJson(data, PushBean.class);
                    if (pushBean.getMessageType().equals("ORDER")) {
                        //下单提示
                        EventBus.getDefault().post(pushBean);
                    } else {
                        //通知栏
                        if (!Util.isEmpty(pushBean.getMessageContent().getMsgContent())) {
                            EventBus.getDefault().post(new ActionBean(Constants.ActionType.MESSAGE_ALL_UNREANED_CHANGE));
                            createNotification(context, pushBean);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case PushConsts.GET_CLIENTID:
                //回传用户信息
                String clientId = bundle.getString("clientid");
                if (Util.isEmpty(clientId)) {
                    return;
                }
                D2CApplication.mSharePref.putSharePrefString(SharePrefConstant.CLIENT_ID, clientId);
                UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(context);
                if (user != null) {
                    SendDeviceApi api = new SendDeviceApi();
                    api.setMemberId(user.getId());
                    api.setClientId(clientId);
                    api.setDeviceLabel(clientId);
                    api.setDevice(Util.getPageVersionName(context));
                    api.setPlatform(Util.getDeviceModel());
                    D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                        @Override
                        public void onResponse(BaseBean baseBean) {
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    public void createNotification(final Context context, PushBean noticPush) {
        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Intent clickIntent = new Intent(context, PushReceiver.class); //点击 Intent
        clickIntent.putExtra("url", noticPush.getMessageContent().getUrl());
        pendingIntent = PendingIntent.getBroadcast(context, ACTION_ID++, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            notification = new Notification.Builder(context, id)
                    .setContentTitle("D2C")
                    .setContentIntent(pendingIntent)
                    .setWhen(System.currentTimeMillis())
                    .setSound(Uri.parse("android.resource://" + "com.d2cmall.buyer" + "/" + R.raw.sound))
                    .setContentText(noticPush.getMessageContent().getMsgContent())
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setSmallIcon(R.mipmap.ic_app)
                    .setAutoCancel(true)
                    .build();
        } else {
            builder = new NotificationCompat.Builder(context);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_app)
                    .setContentTitle("D2C")
                    .setContentText(noticPush.getMessageContent().getMsgContent())
                    .setWhen(System.currentTimeMillis())
                    .setSound(Uri.parse("android.resource://" + "com.d2cmall.buyer" + "/" + R.raw.sound))
                    .setDefaults(Notification.DEFAULT_VIBRATE);
            notification = builder.build();
        }
        //notification.flags=Notification.FLAG_ONLY_ALERT_ONCE;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        boolean isNotification = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.PUSH, true);
        if (isNotification) {
            notificationManager.notify(NOTIFY_ID++, notification);
        }
        //创建消息弹窗
        if (!Util.isEmpty(noticPush.getMessageContent().getSubTitle()) && !Util.isEmpty(noticPush.getMessageContent().getMsgContent())) {
            int msgIcon = choseMsgIcon(noticPush.getMessageContent().getType());
            MsgPopNotification notification =
                    new MsgPopNotification.Builder().setContext(context)
                            .setTime(System.currentTimeMillis())
                            .setImgRes(msgIcon)
                            .setTitle(noticPush.getMessageContent().getSubTitle())
                            .setContent(noticPush.getMessageContent().getMsgContent()).build();
            notification.setUrl(noticPush.getMessageContent().getUrl());
            if (!isBackground(context)) {
                notification.show();
            }
        }
    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                /*
                BACKGROUND=400 EMPTY=500 FOREGROUND=100
                GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
                 */
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private int choseMsgIcon(int type) {
        switch (type) {
            case 11:
            case 12:
                //物流通知
                return R.mipmap.icon_mg_logistics;
            case 21://订单
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27://门店预约
            case 28://调拨单
            case 29:
                return R.mipmap.icon_mg_system;
            case 31:
            case 32:
            case 33://icon_message_wallet,优惠券,资产
            case 34:
            case 35:
                //我的资产
                return R.mipmap.icon_mg_asset;
            case 41:
            case 42:
            case 43:
            case 44:    //41.点赞提醒 42.关注 43.关注用户发布买家秀提醒 44.买家秀评论和回复提醒
            case 45://直播
            case 46://咨询回复
                return R.mipmap.icon_mg_dynamic;
            case 51://开抢
            case 52://货到
                return R.mipmap.icon_mg_goods;
            case 61: //活动精选
                return R.mipmap.icon_mg_activity;
            case 71://设计师上新
                return R.mipmap.icon_mg_brand;
            case 72://品牌推荐
                return R.mipmap.icon_mg_brand;
            default:
                return R.mipmap.icon_mg_system;
        }
    }

}
