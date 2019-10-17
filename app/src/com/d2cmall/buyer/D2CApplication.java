package com.d2cmall.buyer;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.support.multidex.MultiDex;

import com.d2cmall.buyer.activity.HomeActivity;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.PresentMessage;
import com.d2cmall.buyer.bean.WebMessage;
import com.d2cmall.buyer.http.HttpClient;
import com.d2cmall.buyer.util.InitializeService;
import com.d2cmall.buyer.util.SharePref;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.MsgPopNotification;
import com.qiniu.pili.droid.streaming.StreamingEnv;
import com.qiyukf.unicorn.api.OnMessageItemClickListener;
import com.qiyukf.unicorn.api.SavePowerConfig;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnreadCountChangeListener;
import com.qiyukf.unicorn.api.YSFOptions;
import com.qiyukf.unicorn.api.msg.UnicornMessage;
import com.zxinsight.MLink;
import com.zxinsight.MWConfiguration;
import com.zxinsight.MagicWindowSDK;
import com.zxinsight.mlink.MLinkCallback;

import java.util.Map;

import de.greenrobot.event.EventBus;
import io.rong.imlib.AnnotationNotFoundException;
import io.rong.imlib.RongIMClient;

public class D2CApplication extends Application {

    public static HttpClient httpClient = null;
    public static SharePref mSharePref = null;
    public static YSFOptions ysfOptions;
    private boolean isInitLive;

    AppProfile appProfile;
    UnreadCountChangeListener unreadCountChangeListener;

    @Override
    public void onCreate() {
        super.onCreate();
        StreamingEnv.init(getApplicationContext());
        String name=getCurProcessName(this);
        if (name.equals("com.d2cmall.buyer")){
            if (BuildConfig.DEBUG) {
                Thread.setDefaultUncaughtExceptionHandler(new CrashExceptionHandler(this));
            }
            appProfile = new AppProfile();
            appProfile.onCreate(this);
            initCommend();
            // UniversalImageLoader.getInstance().init(getApplicationContext());
            options();
            addUnreadListener(true);
            try {
                InitializeService.start(this);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        MWConfig();
    }

    public void initLiveSDK(){
        if (!isInitLive){
            if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                    "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {
                RongIMClient.init(this);
                try {
                    RongIMClient.registerMessageType(PresentMessage.class);
                    RongIMClient.registerMessageType(WebMessage.class);
                } catch (AnnotationNotFoundException e) {
                    e.printStackTrace();
                }
            }
            isInitLive=true;
        }
    }

    private void MWConfig() {
        MWConfiguration config = new MWConfiguration(this);
        config.setDebugModel(BuildConfig.DEBUG).setPageTrackWithFragment(true);
        MagicWindowSDK.initSDK(config);
        MLink.getInstance(this).registerDefault(new MLinkCallback() {

            @Override
            public void execute(Map<String, String> map, Uri uri, Context context) {
                GlobalTypeBean globalTypeBean = new GlobalTypeBean(Constants.GlobalType.MMLINK);
                globalTypeBean.putValue("uri", uri);
                EventBus.getDefault().post(globalTypeBean);
            }
        });
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    private YSFOptions options() {
        ysfOptions = new YSFOptions();
        StatusBarNotificationConfig statusBarNotificationConfig = new StatusBarNotificationConfig();
        statusBarNotificationConfig.notificationEntrance = HomeActivity.class;
        ysfOptions.statusBarNotificationConfig = statusBarNotificationConfig;
        ysfOptions.savePowerConfig = new SavePowerConfig();
        ysfOptions.uiCustomization = new UICustomization();
        ysfOptions.onMessageItemClickListener = new OnMessageItemClickListener() {
            @Override
            public void onURLClicked(Context context, String url) {
                Util.urlAction(context, url);
            }
        };
        return ysfOptions;
    }

    public void addUnreadListener(boolean is){
        if (is){
            if (unreadCountChangeListener==null)
                unreadCountChangeListener = new UnreadCountChangeListener() {
                    @Override
                    public void onUnreadCountChange(int count) {
                        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                                .getSystemService(Context.ACTIVITY_SERVICE);
                        String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
                        mSharePref.putSharePrefString("recentCustomMsgContent", Unicorn.queryLastMessage().getContent());
                        mSharePref.putSharePrefBoolean("hasQiYuUnreadMsg", true);
                        if (!"com.qiyukf.unicorn.ui.activity.ServiceMessageActivity".equals(runningActivity)) {
                            UnicornMessage message = Unicorn.queryLastMessage();
                            if (!Util.isEmpty(message.getContent())) {
                                //创建消息弹窗
                                MsgPopNotification notification =
                                        new MsgPopNotification.Builder().setContext(D2CApplication.this)
                                                .setTime(System.currentTimeMillis())
                                                .setImgRes(R.mipmap.icon_mg_service)
                                                .setTitle("客服")
                                                .setContent(message.getContent()).build();
                                notification.setUrl("/customer/service");//点击跳转时区别是否为客服;
                                notification.show();
                            }
                        }
                    }
                };
            Unicorn.addUnreadCountChangeListener(unreadCountChangeListener,true);
        }else {
            if (unreadCountChangeListener!=null){
                Unicorn.addUnreadCountChangeListener(unreadCountChangeListener,false);
            }
        }
    }


    public void initCommend(){
        httpClient = HttpClient.newInstance(this);
        mSharePref = SharePref.getInstance(SharePrefConstant.PREF_NAME, this);
    }

    @Override
    public void onTrimMemory(int level) {
        if (level>=TRIM_MEMORY_UI_HIDDEN){
            addUnreadListener(false);
        }
        super.onTrimMemory(level);
    }

}