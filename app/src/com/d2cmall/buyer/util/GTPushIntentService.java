package com.d2cmall.buyer.util;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.SendDeviceApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.PopBean;
import com.d2cmall.buyer.bean.PushBean;
import com.d2cmall.buyer.bean.PushHomeBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.google.gson.Gson;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by rookie on 2018/7/13.
 */

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */

public class GTPushIntentService extends GTIntentService {

    PushBean pushBean;//推送Bean
    //通知管理器
    private NotificationManager notificationManager;
    //通知
    private Notification notification;
    NotificationCompat.Builder builder;
    public static int NOTIFY_ID = 0;
    public static int ACTION_ID = 0;
    private PendingIntent pendingIntent;
    public static final String id = "channel_1";
    public static final String name = "channel_name_1";

    @Override
    public void onReceiveServicePid(Context context, int i) {

    }

    @Override
    public void onReceiveClientId(Context context, String clientId) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientId);
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
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        String appid = msg.getAppid();
        String taskid = msg.getTaskId();
        String messageid = msg.getMessageId();
        byte[] payload = msg.getPayload();
        String pkg = msg.getPkgName();
        String cid = msg.getClientId();
        String data;
        if (payload != null) {
            data = new String(payload);
            Log.e("han", "发送通知栏消息: " + data);
        } else {
            return;
        }
        try {
            Gson gson = new Gson();
            pushBean = gson.fromJson(data, PushBean.class);
            if (pushBean.getMessageType().equals("ORDER")) {
                //下单提示
                EventBus.getDefault().post(pushBean);
            }else if (pushBean.getMessageType().equals("POP")){
                PopBean popBean=new PopBean();
                popBean.setUrl(pushBean.getMessageContent().getUrl());
                popBean.setPic(pushBean.getMessageContent().getPic());
                popBean.setTitle(pushBean.getMessageContent().getTitle());
                Session.getInstance().savePopToFile(context,popBean);
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
    }

    public void createNotification(final Context context, PushBean noticPush) {
        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Intent clickIntent = new Intent(context, PushReceiver.class); //点击 Intent
        pendingIntent = PendingIntent.getBroadcast(context, ACTION_ID++, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        clickIntent.putExtra("url", noticPush.getMessageContent().getUrl());
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
            Log.e("tag", "弹窗intentService");
            PushHomeBean pushHomeBean=new PushHomeBean();
            pushHomeBean.setMsgContent(noticPush.getMessageContent().getMsgContent());
            pushHomeBean.setType(noticPush.getMessageContent().getType());
            pushHomeBean.setSubTitle(noticPush.getMessageContent().getSubTitle());
            pushHomeBean.setUrl(noticPush.getMessageContent().getUrl());
            EventBus.getDefault().post(pushHomeBean);
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



    @Override
    public void onReceiveOnlineState(Context context, boolean b) {

    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {

    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {

    }
}
