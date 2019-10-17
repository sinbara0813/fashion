package com.d2cmall.buyer.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.util.HttpUtils;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
//合力亿捷客服聊天界面
public class CustomServiceActivity extends BaseActivity {

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
    @Bind(R.id.web_view)
    WebView webView;
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private final static int FILE_CHOOSER_RESULT_CODE = 10000;
    private UserBean.DataEntity.MemberEntity user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_service);
        ButterKnife.bind(this);
        assert webView != null;
        initWebView();
        loadUrl();
        nameTv.setText("D2C客服");
    }
    //设置webView,保证网页聊天可以正常上传图片
    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            // For Android  >= 3.0
            public void openFileChooser(ValueCallback valueCallback, String acceptType) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            //For Android  >= 4.1
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            // For Android >= 5.0
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                uploadMessageAboveL = filePathCallback;
                openImageChooserActivity();
                return true;
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return false;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }
    private void loadUrl() {
        String businessParam = getIntent().getStringExtra("businessParam");
        String skillGroupId = getIntent().getStringExtra("skillGroupId");
        user = Session.getInstance().getUserFromFile(this);
        String nickName = getIntent().getStringExtra("nickName");
        if(user==null || Util.isEmpty(user.getNickname())){
            nickName="尊敬的D2C用户";
        }else{
            nickName=user.getNickname();
        }
        StringBuilder stringBuilder = new StringBuilder();
        //accountId	是	账户编号 chatId	是	聊天渠道标识 这两个参数在合力提供的聊天链接地址中，不需更改
        stringBuilder.append("https://im.7x24cc.com/phone_webChat.html?accountId=N000000006249&chatId=ea307499-ab03-403f-8127-af8faf016a31");
        stringBuilder.append("&nickName=");
        stringBuilder.append(nickName);
        //技能组ID 售前售后分组等
        if(Util.isEmpty(skillGroupId)){
            stringBuilder.append("&skillGroupId=");
            stringBuilder.append(skillGroupId);
        }
        //企业业务系统用户标识	您的业务系统客户的唯一标识，客户在合力系统唯一标识
        if(user!=null){
            stringBuilder.append("&visitorId=");
            stringBuilder.append(user.getMemberId());
            if(user!=null && !Util.isEmpty(user.getLoginCode()) && !user.getLoginCode().contains("*")){
                stringBuilder.append("&phone=");
                stringBuilder.append(user.getLoginCode());
            }
        }

        //	业务参数（如：businessParam=key1:val1,key2:val2,key3:val3） 企业业务相关参数，支持多个参数，可用于'企业系统对接'等相关业务
        if(!Util.isEmpty(businessParam)){
            stringBuilder.append("&businessParam=");
            stringBuilder.append(businessParam);
//            requestImageTextMsg(businessParam);
        }
        webView.loadUrl(stringBuilder.toString());
    }

    private void requestImageTextMsg(String businessParam) {
        StringBuilder params = new StringBuilder();
        params.append("flag:808,businessParam:'");
        params.append(businessParam);
        params.append("',visitorId:");
        params.append(user.getMemberId());
        try {
            HttpUtils.doPostAsyn("http://www.hollycrm.com/commoninter", params.toString(), new HttpUtils.CallBack() {
                @Override
                public void onRequestComplete(String result) {
                    Log.d("result",result);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openImageChooserActivity() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == uploadMessage && null == uploadMessageAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }

    @OnClick({R.id.back_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
        }
    }
}
