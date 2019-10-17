package com.d2cmall.buyer.base;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.util.Base64;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.GuideLayout;
import com.igexin.sdk.PushManager;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.tendcloud.tenddata.TCAgent;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class BaseActivity extends AppCompatActivity implements WbShareCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(mHomeKeyEventReceiver, new IntentFilter(
                Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        EventBus.getDefault().register(this);
        PushManager.getInstance().initialize(this.getApplicationContext(), com.d2cmall.buyer.util.GTPushService.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(mHomeKeyEventReceiver);
    }

    @Override
    protected void onResume() {
        if (Util.isLowThanAndroid5()){
            View view=findViewById(R.id.tag);
            if (view!=null){
                view.setVisibility(View.VISIBLE);
            }
        }
        Util.onResume(this);
        Util.onPageStart(this,getClass().getSimpleName());
        super.onResume();
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
    protected void onRestart() {
//        checkShareCode();
        super.onRestart();
    }

    private void checkShareCode() {
        ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        //判断剪切版时候有内容
        if(!clipboardManager.hasPrimaryClip())
            return;

        ClipData clipData = clipboardManager.getPrimaryClip();
        //获取 text
        if(clipData==null ||clipData.getItemAt(0).getText()==null){
            return;
        }
        String text = clipData.getItemAt(0).getText().toString();
        if(!Util.isEmpty(text)){
            checkIsShareCode(text);
        }
    }

    private void checkIsShareCode(String text) {
        if(text.contains("¥")){
            String substring = text.substring(0, text.lastIndexOf("¥"));
            if(substring.contains("¥") ){
                String shareCode = substring.substring(substring.lastIndexOf("¥")+1, substring.length());
                if(!Util.isEmpty(shareCode)){
                    byte[] bytes = Base64.decode(shareCode);
                    for (int i=0; i<bytes.length; i++){
                        bytes[i]-=2;
                    }
                    String code = new String(bytes);
                    if(!Util.isEmpty(code) && (code.contains("buyerId=") || code.contains("partnerId="))){
                        Matcher matcher1 = Pattern.compile("buyerId=(\\d+)").matcher(code);
                        if (matcher1.find()) {
                            //是自己则不跳转
                            UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
                            if(user!=null){
                                long partnerId =user.getPartnerId();
                                long shareId = Long.parseLong(matcher1.group(1));
                                if(partnerId==shareId ){
                                    return;
                                }
                            }
                        }
                        Matcher matcher2 = Pattern.compile("partnerId=(\\d+)").matcher(code);
                        if (matcher2.find()) {
                            //是自己则不跳转
                            UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
                            if(user!=null){
                                long partnerId =user.getPartnerId();
                                long shareId = Long.parseLong(matcher1.group(1));
                                if(partnerId==shareId ){
                                    return;
                                }
                            }
                        }
                        if(code.contains("buyerId=")||code.contains("partnerId=")){
                            Util.urlAction(this,code);
                        }
                    }

                }
            }
        }
    }

    public void stat(String event, String label, Map<String,String> map) {
        if (!event.startsWith("V3")){
            event="V3"+event;
        }
        if (map!=null){
            TCAgent.onEvent(this, event, label,map);
        }else {
            TCAgent.onEvent(this,event,label);
            StatService.onEvent(this, event, label);
        }
    }

    @Override
    protected void onPause() {
        Util.onPause(this);
        Util.onPageEnd(this,getClass().getSimpleName());
        super.onPause();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
    }

    @Subscribe
    public void onEventMainThread(ActionBean bean){
        if (bean.type== Constants.ActionType.CLEAR_ALL_ACTIVITY){
            super.finish();
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_right);
    }

    public void onOkButtonClick() {
    }

    public void hideKeyboard(View v) {
        if (this.getCurrentFocus() != null) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {
        String SYSTEM_REASON = "reason";
        String SYSTEM_HOME_KEY = "homekey";
        String SYSTEM_HOME_KEY_LONG = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_REASON);
                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                    //表示按了home键,程序到了后台
                    GuideLayout.back();
                } else if (TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)) {
                    //表示长按home键,显示最近使用的程序列表
                }
            }
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            if (bundle.containsKey("_weibo_resp_errcode")){
                int resultCode = bundle.getInt("_weibo_resp_errcode");
                switch(resultCode) {
                    case 0:
                        onWbShareSuccess();
                        break;
                    case 1:
                        onWbShareCancel();
                        break;
                    case 2:
                        onWbShareFail();
                }
            }
        }
    }

    @Override
    public void onWbShareSuccess() {
        Util.showToast(this,"微博分享成功");
    }

    @Override
    public void onWbShareCancel() {
    }

    @Override
    public void onWbShareFail() {
        Util.showToast(this,"微博分享失败");
    }
}
