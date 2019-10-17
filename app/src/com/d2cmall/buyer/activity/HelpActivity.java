package com.d2cmall.buyer.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.zamplus.businesstrack.ZampAppAnalytics;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 客服&售后
 * Author: hrb
 * Date: 2016/09/29 10:40
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class HelpActivity extends BaseActivity {
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.line_layout)
    View lineLayout;
    @Bind(R.id.web_view)
    WebView webView;
    @Bind(R.id.web_layout)
    LinearLayout webLayout;
    @Bind(R.id.tv_self_refund)
    TextView tvSelfRefund;
    @Bind(R.id.tv_self_return)
    TextView tvSelfReturn;
    @Bind(R.id.tv_pross)
    TextView tvPross;
    @Bind(R.id.tv_online)
    TextView tvOnline;
    @Bind(R.id.tv_telphone)
    TextView tvTelphone;
    @Bind(R.id.copy_tv)
    TextView copyTv;
    @Bind(R.id.help_scroll)
    ScrollView helpScroll;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.tv_complaint)
    TextView tvComplaint;
    private UserBean.DataEntity.MemberEntity user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        initTitle();
        helpScroll.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        loadWeb();
        user = Session.getInstance().getUserFromFile(this);
    }

    private void initTitle() {
        nameTv.setText("客服&售后");
    }

    private void loadWeb() {
        WebSettings setting = webView.getSettings();
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);
        setting.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String mUrl) {
                return Util.urlAction(HelpActivity.this, mUrl);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                helpScroll.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                webView.stopLoading();
                webLayout.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            }
        });
        String url = Constants.SHARE_URL + "/page/question";
        String token = "";
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
        if (user != null) {
            token = user.getUserToken();
        }
        webView.loadUrl(String.format(Constants.INVOKE_URL, token, Util.toURLEncode(url)));
    }

    @OnClick({R.id.tv_self_refund, R.id.tv_self_return, R.id.tv_pross, R.id.tv_online, R.id.tv_telphone,
            R.id.copy_tv, R.id.back_iv, R.id.tv_complaint})
    public void onClick(View view) {
        Intent intent = null;
        HashMap<String, String> params = new HashMap<>();
        ClipboardManager cm = null;
        switch (view.getId()) {
            case R.id.tv_complaint:
                intent = new Intent(this, ComplaintActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.tv_self_refund: //自助退款
            case R.id.tv_self_return: //自助退货
                if (user == null) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, Constants.Login.SELF_RETURN_LOGIN);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.activity_anim_default);
                } else if (!user.isD2c()) {
                    intent = new Intent(this, BindPhoneActivity.class);
                    startActivityForResult(intent, Constants.RequestCode.BIND_PHONE_2_ORDERS);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                } else {
                    params.put("tabName", getString(R.string.zam_order));
                    ZampAppAnalytics.onRemarketingEvent(this, "ad-myd2c", params);
                    intent = new Intent(this, MyOrderActivity.class);
                    intent.putExtra("position", 0);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                }
                break;
            case R.id.tv_pross: //进度查询
                if (user == null) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, Constants.Login.PROSS_LOGIN);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.activity_anim_default);
                } else if (!user.isD2c()) {
                    intent = new Intent(this, BindPhoneActivity.class);
                    startActivityForResult(intent, Constants.RequestCode.BIND_PHONE_2_SALE);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                } else {
                    params.put("tabName", getString(R.string.zam_sale_out));
                    ZampAppAnalytics.onRemarketingEvent(this, "ad-myd2c", params);
                    intent = new Intent(this, AllAfterSaleActivity.class);
                    intent.putExtra("position", 0);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                }
                break;
            case R.id.tv_online: //线上客服
                if (Util.loginChecked(this, 999)) {
                    String title = "线上客服";
                    String url = "http://www.d2cmall.com";
                    ConsultSource source = new ConsultSource(url, title, "帮助");
                    source.groupId = Constants.QIYU_AF_GROUP_ID;
                    source.robotFirst = true;
                    Unicorn.openServiceActivity(this, "D2C客服", source);
                    //合力亿捷
//                    intent = new Intent(this,CustomServiceActivity.class);
//                    intent.putExtra("skillGroupId", Constants.HLYJ_BF_AF_GROUP_ID);
//                    startActivity(intent);
                }
                break;
            case R.id.tv_telphone: //电话客服
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                                Constants.RequestCode.PERMISSION);
                    } else {
                        call();
                    }
                } else {
                    call();
                }
                break;
            case R.id.copy_tv:
                cm = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setPrimaryClip(ClipData.newPlainText("url", getString(R.string.label_ceo_owner)));
                Util.showToast(this, R.string.msg_copy_ok);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constants.RequestCode.PERMISSION) {
            int result = grantResults[0];
            if (result == PackageManager.PERMISSION_GRANTED) {
                call();
            }
        }
    }

    private void call() {
        Intent call = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + getString(R.string.label_green_phone));
        call.setData(data);
        startActivity(call);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            Intent intent = null;
            HashMap<String, String> params = new HashMap<>();
            user = Session.getInstance().getUserFromFile(this);
            switch (requestCode) {
                case Constants.Login.SELF_RETURN_LOGIN:
                case Constants.RequestCode.BIND_PHONE_2_ORDERS:
                    params.put("tabName", getString(R.string.zam_order));
                    ZampAppAnalytics.onRemarketingEvent(this, "ad-myd2c", params);
                    intent = new Intent(this, MyOrderActivity.class);
                    intent.putExtra("position", 0);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                    break;
                case Constants.Login.PROSS_LOGIN:
                case Constants.RequestCode.BIND_PHONE_2_SALE:
                    params.put("tabName", getString(R.string.zam_sale_out));
                    ZampAppAnalytics.onRemarketingEvent(this, "ad-myd2c", params);
                    intent = new Intent(this, AllAfterSaleActivity.class);
                    intent.putExtra("position", 0);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        Util.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Util.onPause(this);
        super.onPause();
    }

}
