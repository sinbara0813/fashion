package com.d2cmall.buyer.activity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.BuildConfig;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.api.UploadDeviceInfoApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.BottomPicBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.LastestSignRecord;
import com.d2cmall.buyer.bean.PushBean;
import com.d2cmall.buyer.bean.PushHomeBean;
import com.d2cmall.buyer.bean.SplashBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.fragment.FashionSubFragment;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Base64;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.FileUtil;
import com.d2cmall.buyer.util.GTPushIntentService;
import com.d2cmall.buyer.util.GTPushService;
import com.d2cmall.buyer.util.PushReceiver;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.UpdateManager;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.GuidePop;
import com.d2cmall.buyer.widget.HomeTabLayout;
import com.d2cmall.buyer.widget.MsgPopNotification;
import com.d2cmall.buyer.widget.OpenMsgPushPop;
import com.d2cmall.buyer.widget.SocialPop;
import com.d2cmall.buyer.widget.UpdatePop;
import com.d2cmall.buyer.widget.VideoPop;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager;
import com.igexin.sdk.PushManager;
import com.qiyukf.nimlib.sdk.NimIntent;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.ProductDetail;
import com.qiyukf.unicorn.api.Unicorn;
import com.tendcloud.tenddata.TCAgent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

//主页面

public class HomeActivity extends AppCompatActivity implements UpdatePop.CallBack {

    @Bind(R.id.tab_layout)
    HomeTabLayout tabLayout;
    @Bind(R.id.push_toast)
    View pushToast;
    @Bind(R.id.line)
    View line;
    @Bind(R.id.fragment_container)
    FrameLayout fragmentContainer;

    private long exitTime;
    private SocialPop socialPop;
    private UpdatePop updatePop;
    private boolean isMust;
    public static int count;
    private String downUrl;
    private ProgressDialog progressDialog;
    private String updateapkurl = "";
    private static final int DOWN_OK = 1;
    /**
     * 下载失败
     */
    private static final int DOWN_ERROR = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.remove("android:support:fragments");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);
        ButterKnife.bind(this);
        init();
        initListener();
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), GTPushIntentService.class);
    }

    private void initDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
        progressDialog.setCancelable(false);// 设置是否可以通过点击Back键取消
        progressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        progressDialog.setIcon(R.mipmap.ic_app);// 设置提示的title的图标，默认是没有的
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在下载......");
        progressDialog.setMax(100);
    }

    private void initListener() {
        tabLayout.setSelectListener(new HomeTabLayout.TabSelectListener() {
            @Override
            public boolean onTabSelected(int index) {
                switch (index) {
                    case 1:
                        stat("V3底部导航栏", "首页");
                        break;
                    case 2:
                        stat("V3底部导航栏", "专题");
                        break;
                    case 3:
                        stat("V3底部导航栏", "时尚圈");
                        break;
                    case 4:
                        stat("V3底部导航栏", "分类");
                        break;
                    case 5:
                        stat("V3底部导航栏", "我的");
                        break;
                }
                return true;
            }

            @Override
            public void onTabReselected(int index) {
                if (index == 3) {
                    UserBean.DataEntity.MemberEntity memberEntity = Session.getInstance().getUserFromFile(HomeActivity.this);
                    if (memberEntity != null) {
                        if (memberEntity.getHasNickName() && !Util.isEmpty(memberEntity.getRealHead())) {
                            if (memberEntity.getDesignerId() > 0 || memberEntity.getType() == 3) {
                                socialPop = new SocialPop(HomeActivity.this, true);
                                socialPop.show(tabLayout);
                            } else {
                                if (FashionSubFragment.isUpload) {
                                    VideoPop videoPop = new VideoPop(HomeActivity.this);
                                    videoPop.show(getWindow().getDecorView());
                                }else{
                                    Intent intent = new Intent(HomeActivity.this, VideoRecordActivity.class);
                                    intent.putExtra("channel","social");
                                    startActivity(intent);
                                }
                            }
                        } else {
                            Intent intent = new Intent(HomeActivity.this, CompleteInfoActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_up, R.anim.activity_anim_default);
                        }
                    } else {
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_up, R.anim.activity_anim_default);
                    }
                } else if (index == 1) {
                    EventBus.getDefault().post(new ActionBean(Constants.ActionType.HOME_UP));
                } else if (index == 2) {
                    EventBus.getDefault().post(new ActionBean(Constants.ActionType.SPECAIL_UP));
                }
            }
        });
    }

    private void init() {
        EventBus.getDefault().register(this);
        //tabLayout.init(1);
        getBottomPic();
        getDeviceId();//获取手机串号,设备唯一表示
        upLoadDeviceInfo();
        parseIntent();
        startPush();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUpdate();
                checkMsgOpen();
                getSplash();
                UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(HomeActivity.this);
                if (user != null) {
                    refreshMemberInfo();
                }
                NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                boolean isOpened = manager.areNotificationsEnabled();
                Boolean isSigneTipOpen = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.IS_OPEN_TIP_SIGNE, true);
                if (isOpened && isSigneTipOpen) {//系统消息通知和签到提醒都打开再去检测是否签到和提醒
                    checkSignedToday();
                }
//                checkShareCode();
            }
        }, 3000);
    }

    //检查今天有没有签到
    private void checkSignedToday() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.DCION_SIGNE_HISTORY);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<LastestSignRecord>() {
            @Override
            public void onResponse(LastestSignRecord lastestSignRecord) {
                long laestSigneTime = 0;
                if (lastestSignRecord.getData().getMemberDailySign() != null && lastestSignRecord.getData().getMemberDailySign().getSignDate() != null) {
                    laestSigneTime = lastestSignRecord.getData().getMemberDailySign().getSignDate().getTime();
                }
                long currentTimeMillis = System.currentTimeMillis();
                Notification notification;
                Intent clickIntent = new Intent(HomeActivity.this, PushReceiver.class); //点击 Intent
                clickIntent.putExtra("url", "/member/sign/records?pageSize=7");//签到的网页
                PendingIntent pendingIntent = PendingIntent.getBroadcast(HomeActivity.this, 123321, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                long lastTimeAlertSign = D2CApplication.mSharePref.getSharePrefLong("lastTimeAlertSign", 0);
                //上次签到时间小于当前系统时间且不是今天,或没有签到过,且今天没有提醒过
                if (((currentTimeMillis > laestSigneTime && DateUtil.compareToday(laestSigneTime) != 0) || laestSigneTime == 0) && (lastTimeAlertSign == 0 || DateUtil.compareToday(lastTimeAlertSign) != 0)) {
                    NotificationManager notificationManager = (NotificationManager) HomeActivity.this.getSystemService(NOTIFICATION_SERVICE);
                    D2CApplication.mSharePref.putSharePrefLong("lastTimeAlertSign", System.currentTimeMillis());
                    if (Build.VERSION.SDK_INT >= 26) {
                        NotificationChannel channel = new NotificationChannel("channel_1", "channel_name_1", NotificationManager.IMPORTANCE_HIGH);
                        notificationManager.createNotificationChannel(channel);
                        notification = new Notification.Builder(HomeActivity.this, "channel_1")
                                .setContentTitle(getString(R.string.label_sign_title))
                                .setWhen(System.currentTimeMillis())
                                .setContentIntent(pendingIntent)
                                .setContentText(getString(R.string.label_sign_content))
                                .setDefaults(Notification.DEFAULT_VIBRATE)
                                .setSmallIcon(R.mipmap.ic_app)
                                .setAutoCancel(true)
                                .build();
                    } else {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(HomeActivity.this);
                        builder.setAutoCancel(true)
                                .setSmallIcon(R.mipmap.ic_app)
                                .setContentIntent(pendingIntent)
                                .setContentTitle(getString(R.string.label_sign_title))
                                .setContentText(getString(R.string.label_sign_content))
                                .setWhen(System.currentTimeMillis())
                                .setDefaults(Notification.DEFAULT_VIBRATE);
                        notification = builder.build();
                    }
                    notification.flags = Notification.FLAG_AUTO_CANCEL;
                    notificationManager.notify(123321, notification);
                }
            }
        });
    }


    //签到接口,用来刷新用户数据
    private void refreshMemberInfo() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.GET_PARTNER_REFRESH_ARRIVAL_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<UserBean>() {
            @Override
            public void onResponse(UserBean userBean) {
                Session.getInstance().saveUserToFile(HomeActivity.this, userBean.getData().getMember());
            }
        });
    }


    private void getBottomPic() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.MAIN_BOTTOM_PIC);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BottomPicBean>() {
            @Override
            public void onResponse(BottomPicBean response) {
                if (response != null && response.getData().getAppNavigation() != null) {
                    if(response.getData().getAppNavigation().size()>0 && !Util.isEmpty(response.getData().getAppNavigation().get(0).getBackColor())){
                        String colorStr = getColorStr(response.getData().getAppNavigation().get(0).getBackColor());
                        if(!Util.isEmpty(colorStr)){
                            tabLayout.setBackgroundColor(Color.parseColor(colorStr));
                        }
                    }
                    Collections.sort(response.getData().getAppNavigation(), new Comparator<BottomPicBean.DataBean.AppNavigationBean>() {
                        @Override
                        public int compare(BottomPicBean.DataBean.AppNavigationBean o1, BottomPicBean.DataBean.AppNavigationBean o2) {
                            return o1.getSort() - o2.getSort();
                        }
                    });
                    int size = response.getData().getAppNavigation().size();
                    for (int i = 0; i < size; i++) {
                        switch (response.getData().getAppNavigation().get(i).getSort()) {
                            case 0:
                                tabLayout.setMainColor(new String[]{response.getData().getAppNavigation().get(i).getPic(), "/2018/10/29/10001.png"});
                                break;
                            case 1:
                                tabLayout.setSpecialColor(new String[]{response.getData().getAppNavigation().get(i).getPic(), "/2018/10/29/10002.png"});
                                break;
                            case 2:
                                tabLayout.setFashionColor(new String[]{response.getData().getAppNavigation().get(i).getPic(), "/2018/10/29/10003.png"});
                                break;
                            case 3:
                                tabLayout.setShopColor(new String[]{response.getData().getAppNavigation().get(i).getPic(), "/2018/10/29/10004.png"});
                                break;
                            case 4:
                                tabLayout.setMineColor(new String[]{response.getData().getAppNavigation().get(i).getPic(), "/2018/10/29/10005.png"});
                                break;
                        }
                    }
                    if (size == 0) {
                        line.setVisibility(View.VISIBLE);
                    } else {
                        line.setVisibility(View.GONE);
                        RelativeLayout.LayoutParams rl= (RelativeLayout.LayoutParams) fragmentContainer.getLayoutParams();
                        rl.setMargins(0,0,0, ScreenUtil.dip2px(46));
                    }
                }
                tabLayout.init(1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tabLayout.init(1);
            }
        });
    }

    //上传用户设备信息,后台统计后续的用户行为
    private void upLoadDeviceInfo() {
        UploadDeviceInfoApi uploadDeviceInfoApi = new UploadDeviceInfoApi();
        uploadDeviceInfoApi.setLpx(Util.getDeviceSize(this).x);//水平像素
        uploadDeviceInfoApi.setHpx(Util.getDeviceSize(this).y);//垂直像素
        uploadDeviceInfoApi.setResolution(getString(R.string.msg_device_resolution_info, Util.getDeviceSize(this).x, Util.getDeviceSize(this).y));//分辨率
        uploadDeviceInfoApi.setDeviceBrand(Util.getDeviceBrand());//手机厂商
        uploadDeviceInfoApi.setDeviceModel(Util.getSystemModel());//手机型号
        uploadDeviceInfoApi.setLanguage(Util.getSystemLanguage());//手机语言
        uploadDeviceInfoApi.setTimezone(Util.getCurrentTimeZone());//手机时区
        uploadDeviceInfoApi.setVersion(Util.getDeviceVersion());//手机系统版本号
        uploadDeviceInfoApi.setMac(Util.getDeviceId(this));//获取手机的网卡唯一标示
        D2CApplication.httpClient.loadingRequest(uploadDeviceInfoApi, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {//重新打开homeActivity
        setIntent(intent);
        parseIntent();
        super.onNewIntent(intent);
    }


    private void getSplash() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.SPLASH_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<SplashBean>() {
            @Override
            public void onResponse(SplashBean splashBean) {
                saveSplash(splashBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                D2CApplication.mSharePref.removeKey(SharePrefConstant.SPLASH_URL);
            }
        });
    }

    private void saveSplash(SplashBean splashBean) {
        if (splashBean.getData().getSplashscreen() != null && splashBean.getData().getSplashscreen().getPics() != null && splashBean.getData().getSplashscreen().getPics().size() > 0) {
            int size = splashBean.getData().getSplashscreen().getPics().size();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < size; i++) {
                builder.append(splashBean.getData().getSplashscreen().getPics().get(i));
                if (i != size - 1) {
                    builder.append(",");
                }
            }
            String splashSp = D2CApplication.mSharePref.getSharePrefString(SharePrefConstant.SPLASH_URL, "");
            if (!builder.toString().equals(splashSp)) {
                D2CApplication.mSharePref.putSharePrefString(SharePrefConstant.SPLASH_URL, builder.toString());
            }
            D2CApplication.mSharePref.putSharePrefString(SharePrefConstant.SPLASH, splashBean.getData().getSplashscreen().getUrl());
        } else {
            D2CApplication.mSharePref.removeKey(SharePrefConstant.SPLASH_URL);
        }
    }
    private String getColorStr(String color){
        String result=null;
        if (color.startsWith("#")){
            if (color.length()==7||color.length()==9){
                return color;
            }else {
                result=null;
            }
        }else {
            if (color.length()==6||color.length()==8){
                return "#"+color;
            }else {
                result=null;
            }
        }
        return result;
    }
    private void startPush() {
        // 读写 sd card 权限非常重要, android6.0默认禁止的,
        String[] mPermissionGroup = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE};

        // 过滤已持有的权限
        List<String> mRequestList = new ArrayList<>();
        for (String permission : mPermissionGroup) {
            if ((ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED)) {
                mRequestList.add(permission);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !mRequestList.isEmpty()) {
            ActivityCompat.requestPermissions(this, mRequestList.toArray(
                    new String[mRequestList.size()]), Constants.RequestCode.REQUEST_PERMISSION);
        } else {
            //PushManager.getInstance().initialize(getApplicationContext());
            PushManager.getInstance().initialize(this.getApplicationContext(), GTPushService.class);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.RequestCode.REQUEST_PERMISSION) {
            if (checkGranted(grantResults)) {
            }
            //无论授权
            //PushManager.getInstance().initialize(getApplicationContext());
            PushManager.getInstance().initialize(this.getApplicationContext(), GTPushService.class);
            //PushManager.getInstance().initialize(this.getApplicationContext(), PushService.class);
        } else if (requestCode == 1) {
            if (!checkGranted(grantResults)) {
                Toast.makeText(this, "请打开允许安装D2C应用", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                startActivity(intent);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private boolean checkGranted(int[] result) {
        boolean is = true;
        for (int item : result) {
            if (item != PackageManager.PERMISSION_GRANTED) {
                is = false;
                break;
            }
        }
        return is;
    }

    private void parseIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)) {
            consultService(this, null, null, null);
            setIntent(new Intent());
        } else {
            String url = getIntent().getStringExtra("url");
            if (!Util.isEmpty(url)) {
                Util.urlAction(this, url);
            }
        }
    }

    private void checkShareCode() {             //解析分享口令
        ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        //判断剪切版时候有内容
        if (!clipboardManager.hasPrimaryClip())
            return;

        ClipData clipData = clipboardManager.getPrimaryClip();
        //获取 text
        if (clipData == null || clipData.getItemAt(0).getText() == null) {
            return;
        }
        String text = clipData.getItemAt(0).getText().toString();
        if (!Util.isEmpty(text)) {
            checkIsShareCode(text);
        }

    }

    private void checkIsShareCode(String text) {
        if (text.contains("¥")) {
            String substring = text.substring(0, text.lastIndexOf("¥"));
            if (substring.contains("¥")) {
                String shareCode = substring.substring(substring.lastIndexOf("¥") + 1, substring.length());
                if (!Util.isEmpty(shareCode)) {
                    byte[] bytes = Base64.decode(shareCode);
                    for (int i = 0; i < bytes.length; i++) {
                        bytes[i] -= 2;
                    }
                    String code = new String(bytes);
                    if (!Util.isEmpty(code) && (code.contains("buyerId=") || code.contains("partnerId="))) {
                        Matcher matcher1 = Pattern.compile("buyerId=(\\d+)").matcher(code);
                        if (matcher1.find()) {
                            //是自己则不跳转
                            UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
                            if (user != null) {
                                long partnerId = user.getPartnerId();
                                long shareId = Long.parseLong(matcher1.group(1));
                                if (partnerId == shareId) {
                                    return;
                                }
                            }
                        }
                        Matcher matcher2 = Pattern.compile("partnerId=(\\d+)").matcher(code);
                        if (matcher2.find()) {
                            //是自己则不跳转
                            UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
                            if (user != null) {
                                long partnerId = user.getPartnerId();
                                long shareId = Long.parseLong(matcher1.group(1));
                                if (partnerId == shareId) {
                                    return;
                                }
                            }
                        }
                        if (code.contains("buyerId=") || code.contains("partnerId=")) {
                            Util.urlAction(this, code);
                        }
                    }
                }
            }
        }
    }

    private void consultService(final Context context, String uri, String title, ProductDetail productDetail) {
        if (!Unicorn.isServiceAvailable()) {
            Util.showToast(context, R.string.msg_kefu_error_now);
            return;
        }
        ConsultSource source = new ConsultSource(uri, title, "主页");
        source.productDetail = productDetail;
        source.robotFirst = true;
        Unicorn.openServiceActivity(context, "D2C客服", source);
    }

    private void checkMsgOpen() {
        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        boolean isOpened = manager.areNotificationsEnabled();
        Boolean isMsgPushOpen = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.IS_MSG_POP_HAS_SHOW, false);
        boolean isFirst = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.IS_HOME_FIRST, true);
        D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.IS_HOME_FIRST, false);
        if (!isOpened && !isMsgPushOpen && !isFirst) {
            showOpenPushPop();
        }
    }

    private void showOpenPushPop() {
        D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.IS_MSG_POP_HAS_SHOW, true);
        OpenMsgPushPop openMsgPushPop = new OpenMsgPushPop(this);
        openMsgPushPop.show(getWindow().getDecorView());

    }

    private void checkUpdate() {
        UpdateManager manager = new UpdateManager();
        manager.addBack(new UpdateManager.Back() {
            @Override
            public void back(String name, boolean is, String url) {
                downUrl = url;
                boolean isUpdate = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.UPDATE, true);
                boolean mustTip = false;
                long lastTime = D2CApplication.mSharePref.getSharePrefLong(SharePrefConstant.LAST_UPDATE_TIME, 0);
                if (lastTime > 0 && System.currentTimeMillis() - lastTime > 7 * 24 * 60 * 60 * 1000) { //7 * 24 * 60 * 60 * 1000
                    mustTip = true;
                }
                if (isUpdate || mustTip) {
                    HomeActivity.this.isMust = is;
                    if (updatePop != null && updatePop.isShow()) {
                        return;
                    }
                    updatePop = new UpdatePop(HomeActivity.this);
                    if (is) {
                        updatePop.forceUpdate();
                    }
                    updatePop.setContent(name, true);
                    updatePop.setBack(HomeActivity.this);
                    updatePop.show(getWindow().getDecorView());
                    D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.GUIDE_SHOW, false);
                } else {
                    if (D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.GUIDE_SHOW, true)) {
                        if (Session.getInstance().getUserFromFile(HomeActivity.this) == null) {
                            GuidePop guidePop = new GuidePop(HomeActivity.this);
                            guidePop.show(getWindow().getDecorView());
                            D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.GUIDE_SHOW, false);
                        }
                    }
                }
            }

            @Override
            public void noUpdate() {
                if (D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.GUIDE_SHOW, true)) {
                    if (Session.getInstance().getUserFromFile(HomeActivity.this) == null) {
                        GuidePop guidePop = new GuidePop(HomeActivity.this);
                        guidePop.show(getWindow().getDecorView());
                        D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.GUIDE_SHOW, false);
                    }
                }
            }
        });
        manager.checkHomeUpdate();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            EventBus.getDefault().post(new ActionBean(Constants.ActionType.KEY_BACK));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(ActionBean bean) {
        if (bean.type == Constants.ActionType.KEY_BACK) {
            if (socialPop != null && socialPop.isShow()) {
                socialPop.dismiss();
            } else {
                exit();
            }
        } else if (bean.type == Constants.ActionType.CHANGE_PAGE) {
            if (bean.hasKey("firstIndex")) {
                Object first = bean.get("firstIndex");
                if (bean.hasKey("secondIndex")) {
                    Object second = bean.get("secondIndex");
                    tabLayout.init((int) first, (int) second);
                } else {
                    tabLayout.init((int) first);
                }
            }
        } else if (bean.type == Constants.ActionType.LIVE_LIST) {
            tabLayout.init(3, 2);
        } else if (bean.type == Constants.ActionType.XINREN) {
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra("url", "/page/xinren");
            intent.putExtra("type", 0);//type=0调invoke接口，type=1保持url原样
            intent.putExtra("isShareGone", true);
            startActivity(intent);
        } else if (bean.type == Constants.ActionType.POP) {
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onPushBeanCome(PushHomeBean pushHomeBean) {
        int msgIcon = choseMsgIcon(pushHomeBean.getType());
        MsgPopNotification notification =
                new MsgPopNotification.Builder().setContext(this)
                        .setTime(System.currentTimeMillis())
                        .setImgRes(msgIcon)
                        .setTitle(pushHomeBean.getSubTitle())
                        .setContent(pushHomeBean.getMsgContent()).build();
        notification.setUrl(pushHomeBean.getUrl());
        notification.show();
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

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(PushBean pushBean) {
        if (tabLayout != null && tabLayout.getLastShowIndex() == 1 && pushBean != null && !Util.isEmpty(pushBean.getMessageContent().getUrl())) {
            Util.showPush(this, pushToast, pushBean);
        }
    }

    private void exit() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finishApp();
        }
    }

    private void finishApp() {
        finish();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        Glide.with(this).onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        Glide.with(this).onTrimMemory(level);
        super.onTrimMemory(level);
    }

    @Override
    public void sure() {
        D2CApplication.mSharePref.removeKey(SharePrefConstant.LAST_UPDATE_VERSION);
        //Intent intent = new Intent(this, DownloadService.class);
        String[] channels = getResources().getStringArray(R.array.label_channel);
        String channel = "";
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            channel = appInfo.metaData.getString("TD_CHANNEL_ID");
            if (BuildConfig.DEBUG) {
                Util.showToast(this, channel);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int size = channels.length;
        boolean hasContain = false;
        for (int i = 0; i < size; i++) {
            if (channel.equals(channels[i])) {
                hasContain = true;
                downUrl = downUrl.replace("{channel}", channel);
            }
        }
        if (!hasContain) {
            downUrl = downUrl.replace("{channel}", "install");
        }
        startDownLoad();
//        startService(intent);
//        if (isMust) {
//            Toast.makeText(this, "开始下载", Toast.LENGTH_SHORT).show();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    finishApp();
//                }
//            }, 1000);
//        }
    }

    private void startDownLoad() {
        if (progressDialog == null) {
            initDialog();
            progressDialog.show();
        }
        File root = getExternalCacheDir();
        File file = new File(root, "apk");
        if (!file.exists()) {
            if (file.mkdirs()) {
                updateFile(file);
            }
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                f.delete();
            }
            file.listFiles();
            updateFile(file);
        }
    }

    private void updateFile(File file) {
        updateapkurl = file.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".apk";
        FileUtil.createFile(updateapkurl);
        createThread();
    }

    private void InstallationAPK() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        publicApk();
    }

    private void publicApk() {
        Uri uri = null;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(new File(updateapkurl));
        } else {
            File file = new File(updateapkurl);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(this, "com.d2cmall.buyer.fileprovider", file);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(intent);
    }

    /***
     * 开线程下载
     */
    public void createThread() {
        /***
         * 更新UI
         */
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case DOWN_OK:
                        InstallationAPK();
                        break;
                }

            }

        };

        final Message message = new Message();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    long downloadSize = downloadUpdateFile(downUrl, updateapkurl);
                    if (downloadSize > 0) {
                        // 下载成功
                        message.what = DOWN_OK;
                        handler.sendMessage(message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    message.what = DOWN_ERROR;
                    handler.sendMessage(message);
                }

            }
        }).start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean b = getPackageManager().canRequestPackageInstalls();
            if (!b) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.REQUEST_INSTALL_PACKAGES)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, 1);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, 1);
                }
            }
        }
    }

    public long downloadUpdateFile(String down_url, String file) throws Exception {
        int down_step = 1;// 提示step
        int totalSize;// 文件总大小
        long downloadCount = 0;// 已经下载好的大小
        int updateCount = 0;// 已经上传的文件大小
        InputStream inputStream;
        OutputStream outputStream;
        URL url = new URL(down_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(10 * 1000);
        httpURLConnection.setReadTimeout(10 * 1000);
        httpURLConnection.setUseCaches(false);
        // 获取下载文件的size
        totalSize = httpURLConnection.getContentLength();
        Log.d("han", "totalsize==" + totalSize);
        if (httpURLConnection.getResponseCode() == 404) {
            throw new Exception("fail!");
        }
        inputStream = httpURLConnection.getInputStream();
        File file2 = new File(file);
        outputStream = new FileOutputStream(file2);// 文件存在则覆盖掉
        byte buffer[] = new byte[1024];
        int readsize = 0;
        while ((readsize = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, readsize);
            downloadCount = downloadCount + readsize;// 时时获取下载到的大小
            long progress = Math.abs(downloadCount * 100) / totalSize;
            if (progressDialog != null) {
                progressDialog.setProgress((int) progress);
            }
            /**
             * 每次增张5%
             */
            if (updateCount == 0 || (downloadCount * 100 / totalSize - down_step) >= updateCount) {
                updateCount += down_step;

            }
        }
        Log.d("han", "file.size==" + file2.length());
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        inputStream.close();
        outputStream.flush();
        outputStream.close();

        return downloadCount;

    }

    @Override
    public void cancel() {
        if (isMust) {
            finishApp();
        } else {
            D2CApplication.mSharePref.putSharePrefLong(SharePrefConstant.LAST_UPDATE_TIME, System.currentTimeMillis());
            D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.UPDATE, false);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
    }

    private void stat(String event, String label) {
        StatService.onEvent(this, event, label);
        TCAgent.onEvent(this, event, label);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.Login.WEB_LOGIN && resultCode == RESULT_OK) {
            EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.MANI_WEB_FRAMENT_LOGIN));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void getDeviceId() {
        String deviceId = Util.getDeviceId(this);
        D2CApplication.mSharePref.putSharePrefString(SharePrefConstant.DEVICE_IMEI, deviceId);
    }


}
