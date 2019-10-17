package com.d2cmall.buyer.activity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.AppProfile;
import com.d2cmall.buyer.BuildConfig;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.Manifest;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.UpdateBean;
import com.d2cmall.buyer.bean.UpdateInfo;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Base64;
import com.d2cmall.buyer.util.FileUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.UpdatePop;
import com.google.gson.Gson;
import com.igexin.sdk.PushManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;


/**
 * Created by rookie on 2017/8/26.
 * 设置页面
 */

public class SettingActivity extends BaseActivity implements UpdatePop.CallBack {
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.person_info_layout)
    LinearLayout personInfoLayout;
    @Bind(R.id.receive_address_layout)
    LinearLayout receiveAddressLayout;
    @Bind(R.id.account_security_layout)
    LinearLayout accountSecurityLayout;
    @Bind(R.id.togglebutton)
    Switch togglebutton;
    @Bind(R.id.message_notification_layout)
    LinearLayout messageNotificationLayout;
    @Bind(R.id.cache_size)
    TextView cacheSize;
    @Bind(R.id.clear_cache_layout)
    LinearLayout clearCacheLayout;
    @Bind(R.id.update_tag_tv)
    TextView updateTagTv;
    @Bind(R.id.tv_red_point)
    TextView tvRedPoint;
    @Bind(R.id.tv_new_version)
    TextView tvNewVersion;
    @Bind(R.id.img_arrow)
    ImageView imgArrow;
    @Bind(R.id.check_version_ll)
    LinearLayout checkVersionLl;
    @Bind(R.id.praise_layout)
    LinearLayout praiseLayout;
    @Bind(R.id.btn_login_out)
    Button btnLoginOut;
    @Bind(R.id.tv_red_point_info)
    TextView redInfoPoint;
    @Bind(R.id.safe_tip_tv)
    TextView safeTipTv;
    @Bind(R.id.ll_certification)
    LinearLayout llCertification;
    private File appCacheDir = null;
    private boolean hasStore;
    private UserBean.DataEntity.MemberEntity user;
    private String info;
    private boolean hasUpdate;
    private UpdatePop updatePop;
    private ProgressDialog progressDialog;
    private String downUrl;
    private String updateapkurl = "";
    private static final int TIMEOUT = 10 * 1000;
    private static final int DOWN_OK = 1;
    /**
     * 下载失败
     */
    private static final int DOWN_ERROR = 0;
    private boolean isJumping;
    private CompoundButton.OnCheckedChangeListener checkedChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting1);
        ButterKnife.bind(this);
        doBusiness();
        checkUpdate();
        initDialog();
        togglebutton.setChecked(D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.PUSH, true));
        checkedChangeListener=new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                    boolean isOpened = manager.areNotificationsEnabled();
                    if (!isOpened){
                        isJumping=true;
                        togglebutton.setOnCheckedChangeListener(null);
                        togglebutton.setChecked(false);
                        jumbSetting();
                    }else {
                        messageNotify();
                    }
                } else {
                    D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.PUSH, false);//停止推送
                    // PushManager.getInstance().turnOffPush(SettingActivity.this);
                    EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.COLSE_MESSAGE_NOTIFY));
                }
            }
        };
        togglebutton.setOnCheckedChangeListener(checkedChangeListener);
    }

    private void jumbSetting(){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
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

    @Override
    protected void onResume() {
        if (isJumping){
            isJumping=false;
            NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
            boolean isOpened = manager.areNotificationsEnabled();
            if (isOpened){
                togglebutton.setChecked(true);
                D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.PUSH, true);//开启推送
                PushManager.getInstance().turnOnPush(SettingActivity.this);
                messageNotify();
                EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.OPEN_MESSAGE_NOTIFY));
            }
            togglebutton.setOnCheckedChangeListener(checkedChangeListener);
        }
        super.onResume();
    }

    private void messageNotify(){
        SimpleApi api=new SimpleApi();
        api.setInterPath("/v3/api/member/task/done/FRIST_MESSAGE_LIST");
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {

            }
        });
    }

    public void doBusiness() {
        nameTv.setText("设置");
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
            appCacheDir = new File(new File(dataDir, this.getPackageName()), "cache");
            cacheSize.setText(FileUtil.getFormatSize(FileUtil.getFolderSize(appCacheDir)));
            if (FileUtil.getFolderSize(appCacheDir) > 0) {
                hasStore = true;
            }
        } else {
            hasStore = false;
        }
        user = Session.getInstance().getUserFromFile(this);
        if (user == null) {
            btnLoginOut.setVisibility(View.GONE);
            safeTipTv.setVisibility(View.GONE);
        } else {
            btnLoginOut.setVisibility(View.VISIBLE);
            if (user.getHasNickName() && !Util.isEmpty(user.getHead())) {
                redInfoPoint.setVisibility(View.GONE);
            } else {
                redInfoPoint.setVisibility(View.VISIBLE);
            }
            if (user.isDanger() == 1 || user.isPayDanger() == 1) {
                safeTipTv.setVisibility(View.VISIBLE);
            } else {
                safeTipTv.setVisibility(View.GONE);
            }
        }
    }

    private void checkUpdate() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.UPDATE_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<UpdateBean>() {
            @Override
            public void onResponse(UpdateBean response) {
                if (response.getData().getIsUpgrade() == 1) {
                    String second = response.getData().getValue();
                    Gson json = new Gson();
                    UpdateInfo updateInfo = null;
                    try {
                        updateInfo = json.fromJson(second, UpdateInfo.class);
                    } catch (Exception e) {
                        return;
                    }
                    if (updateInfo != null) {
                        tvRedPoint.setVisibility(View.VISIBLE);
                        tvNewVersion.setVisibility(View.VISIBLE);
                        info = updateInfo.getInfo();
                        downUrl = updateInfo.getUrl();
                        hasUpdate = true;
                    } else {
                        tvNewVersion.setVisibility(View.VISIBLE);
                        tvNewVersion.setText("当前已是最新版本");
                    }
                }
            }
        });
    }

    @Subscribe
    public void onEventReceive(UserBean.DataEntity.MemberEntity user) {
        if (user != null) {
            if (user.getHasNickName() && !Util.isEmpty(user.getHead())) {
                redInfoPoint.setVisibility(View.GONE);
            } else {
                redInfoPoint.setVisibility(View.VISIBLE);
            }
        }
    }


    @OnClick({R.id.back_iv, R.id.person_info_layout, R.id.receive_address_layout, R.id.account_security_layout, R.id.togglebutton,
            R.id.message_notification_layout, R.id.clear_cache_layout, R.id.check_version_ll, R.id.praise_layout, R.id.btn_login_out,R.id.ll_certification})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.person_info_layout:
                if (Util.loginChecked(this, Constants.Login.ACCOUNT_INFO_LOGIN)) {
                    intent = new Intent(this, AccountInfoActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                }
                break;
            case R.id.receive_address_layout:
                if (Util.loginChecked(this, Constants.Login.ACCOUNT_INFO_LOGIN)) {
                    intent = new Intent(this, ReceiveAddressActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                }
                break;
            case R.id.account_security_layout:
                if (Util.loginChecked(this, Constants.Login.SECURITY_INFO_LOGIN)) {
                    intent = new Intent(this, AccountSafeActivity.class);
                    startActivityForResult(intent, 100);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                }
                break;
            case R.id.togglebutton:
                break;
            case R.id.message_notification_layout:
                break;
            case R.id.clear_cache_layout:
                if (hasStore && appCacheDir != null) {
                    new AlertDialog.Builder(this)
                            .setMessage(R.string.msg_clear_memery)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (FileUtil.deleteFolderFile(appCacheDir.getAbsolutePath(), false)) {
                                        cacheSize.setText("0M");
                                        hasStore = false;
                                    }
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                    break;
                }
                break;
            case R.id.check_version_ll:
                if (hasUpdate) {
                    if (updatePop == null) {
                        updatePop = new UpdatePop(this);
                        updatePop.setContent(info, true);
                        updatePop.setBack(this);
                    }
                    updatePop.show(view);
                } else {
                    Util.showToast(SettingActivity.this, "当前已是最新版本");
                }
                break;
            case R.id.praise_layout:
                try {
                    String marketUri = "market://details?id=" + getPackageName();
                    Uri uri = Uri.parse(marketUri);
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    SettingActivity.this.startActivity(intent);
                } catch (Exception e) {
                    Util.showToast(SettingActivity.this, R.string.msg_praise_error);
                }
                break;
            case R.id.btn_login_out:
                new AlertDialog.Builder(this)
                        .setMessage(R.string.msg_logout)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestLogoutTask();//调退出登录接口
                                if (user != null) {
                                    PushManager.getInstance().unBindAlias(AppProfile.getContext(), String.valueOf(user.getMemberId()), true);
                                }
                                Session.getInstance().logout(SettingActivity.this);
                                clearShareCode();
                                Intent intent = new Intent();
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.ll_certification://实名认证
                if(Util.loginChecked(this,111)){
                    startActivity(new Intent(this,CertificationActivity.class));
                }
                break;
        }
    }

    //如果剪切板是邀请买手的口令就清空
    private void clearShareCode() {
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
                        if (!Util.isEmpty(code)) {
                            if (code.contains("buyerId=") || code.contains("partnerId=")) {
                                //是分享口令,清除
                                //清空剪切板
                                if (clipboardManager != null) {
                                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, ""));
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    private void requestLogoutTask() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.LOGOUT_URL);
        api.setMethod(BaseApi.Method.POST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                UserBean.DataEntity.MemberEntity userBean = Session.getInstance().getUser();
                if (userBean != null) {
                    PushManager.getInstance().unBindAlias(SettingActivity.this, String.valueOf(userBean.getMemberId()), true);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    @Override
    public void sure() {
        D2CApplication.mSharePref.removeKey(SharePrefConstant.LAST_UPDATE_VERSION);
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
    }

    private void startDownLoad() {
        if (progressDialog != null) {
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
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.REQUEST_INSTALL_PACKAGES)){
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.REQUEST_INSTALL_PACKAGES}, 1);
                }else {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.REQUEST_INSTALL_PACKAGES}, 1);
                }
            }
        }
    }

    private void InstallationAPK() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        publicApk();
    }

    private void publicApk(){
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

    public long downloadUpdateFile(String down_url, String file) throws Exception {
        int down_step = 1;// 提示step
        int totalSize;// 文件总大小
        long downloadCount = 0;// 已经下载好的大小
        int updateCount = 0;// 已经上传的文件大小
        InputStream inputStream;
        OutputStream outputStream;
        URL url = new URL(down_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(TIMEOUT);
        httpURLConnection.setReadTimeout(TIMEOUT);
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 100) {
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode==1){
            if (grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"请打开允许安装D2C应用",Toast.LENGTH_SHORT).show();
                Intent intent =  new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                startActivity(intent);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
