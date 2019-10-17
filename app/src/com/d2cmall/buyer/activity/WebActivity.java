package com.d2cmall.buyer.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.LogApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.JsFuncBean;
import com.d2cmall.buyer.bean.Poster;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.HttpUtils;
import com.d2cmall.buyer.util.LogDB;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.util.WebHandler;
import com.d2cmall.buyer.widget.OpenSignPop;
import com.d2cmall.buyer.widget.SharePop;
import com.d2cmall.buyer.widget.web.DefaultWebViewClient;
import com.d2cmall.buyer.widget.web.MyWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tendcloud.tenddata.TCAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.Subscribe;

public class WebActivity extends BaseActivity {
    @Bind(R.id.webView)
    MyWebView webView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.img_share)
    ImageView imgShare;
    @Bind(R.id.img_finish)
    ImageView imgFinish;
    @Bind(R.id.tv_desc)
    ImageView mIvQuestion;
    @Bind(R.id.open_iv)
    ImageView openIv;
    @Bind(R.id.message_tip_rl)
    RelativeLayout messageTipRl;
    @Bind(R.id.close_iv)
    ImageView closeIv;
    private String url;
    private int type;
    private String token = "";
    private String log;
    private String desc;
    private String imgUrl = null;
    private MyWebHandler webHandler;
    private SharePop sharePop;
    private boolean isPage = false;
    private String promotionId;
    private long pageId;
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;
    public ValueCallback<Uri[]> mUploadMessageForAndroid5;
    private String jsFuncName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        type = getIntent().getIntExtra("type", 0);
        log = getIntent().getStringExtra("log");
        boolean isShareGone = getIntent().getBooleanExtra("isShareGone", false);
        //会员中心,显示说明按钮,积分,显示说明按钮
        imgFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if ("/member/level".equals(url) || "/member/sign/records?pageSize=7".equals(url)) {
            mIvQuestion.setVisibility(View.VISIBLE);
            imgShare.setVisibility(View.GONE);
        }
        if (isShareGone) {
            imgShare.setVisibility(View.GONE);
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        if (type != 2) {
            webView.getSettings().setUseWideViewPort(true);
        }
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new JsInterface(), "jsinterface");

        String ua = webView.getSettings().getUserAgentString();
        /*webView.getSettings().setUserAgentString(ua + String.format(Constants.USER_AGENT_URL,
                "Android-"+Util.getPageVersionName(this), Util.getNetType(this)));*/
        webView.getSettings().setUserAgentString(ua + "d2cmall/Android/" + Util.getPageVersionName(this));//d2cmall/Android/3.3.0
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new MyWebViewClient(webView));
        webView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (progressBar.getVisibility() == View.GONE)
                        progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                mUploadMessageForAndroid5 = filePathCallback;
                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");

                startActivityForResult(chooserIntent,
                        FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
                return true;
            }
        });
        webHandler = new MyWebHandler(this);
        webView.registerHandler("d2cinit", webHandler);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        loadUrl();
        mIvQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //会员等级,积分说明
                if ("/member/level".equals(url)) {
                    //会员
                    Util.urlAction(WebActivity.this, "/page/userlevel");
                } else if ("/member/sign/records?pageSize=7".equals(url)) {
                    //积分
                    Util.urlAction(WebActivity.this, "/page/coindescription");
                }

            }
        });
    }

    public class MyWebHandler extends WebHandler {

        private OpenSignPop openSignPop;

        public MyWebHandler(Context context) {
            super(context);
        }

        @Override
        public void handler(String data, CallBackFunction function) {
            super.handler(data, function);
            NotificationManagerCompat manager;
            boolean isOpened;
            //data为json格式
            JsFuncBean jsFuncBean = null;
            try {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                jsFuncBean = gson.fromJson(data, JsFuncBean.class);
            } catch (Exception e) {

            }

            switch (functionName) {
                case "w_sign_open"://打开页面
                    manager = NotificationManagerCompat.from(context.getApplicationContext());
                    isOpened = manager.areNotificationsEnabled();
                    Boolean open = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.IS_OPEN_TIP_SIGNE, true);//标示是否打开签到提醒
                    if (isOpened && open) {
                        if (jsFuncBean != null) {
                            callBack(jsFuncBean.getFunc(), "open");
                        }
                    } else {
                        if (jsFuncBean != null) {
                            callBack(jsFuncBean.getFunc(), "close");
                        }
                    }
                    break;
                case "w_notice_open"://签到按钮打开
                    boolean isOpenTip = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.IS_OPEN_TIP_SIGNE, true);
                    manager = NotificationManagerCompat.from(context.getApplicationContext());
                    isOpened = manager.areNotificationsEnabled();
                    if (!isOpenTip) {
                        D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.IS_OPEN_TIP_SIGNE, true);//标示是否打开签到提醒
                    } else {
                        D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.IS_OPEN_TIP_SIGNE, false);//标示是否打开签到提醒
                    }
                    if (!isOpened || !isOpenTip) {
                        if (jsFuncBean != null) {
                            jsFuncName = jsFuncBean.getFunc();
                            callBack(jsFuncBean.getFunc(), "open");
                        }
                    } else {
                        if (jsFuncBean != null) {
                            jsFuncName = jsFuncBean.getFunc();
                            callBack(jsFuncBean.getFunc(), "close");
                        }
                    }
                    if (!isOpened) {
                        if (openSignPop == null) {
                            openSignPop = new OpenSignPop(context);
                        }
                        openSignPop.show(((Activity) context).getWindow().getDecorView());
                    }
                    break;
            }
        }


    }


    class MyWebViewClient extends DefaultWebViewClient {


        public MyWebViewClient(MyWebView webView) {
            super(webView);
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            /*CookieManager cookieManager = CookieManager.getInstance();
            String CookieStr = cookieManager.getCookie(url);*/
            view.loadUrl("javascript:window.jsinterface.showSource(document.getElementsByName(\"description\")[0].attributes.getNamedItem(\"content\").value" +
                    "+'||'+document.getElementsByTagName(\"img\")[0].attributes.getNamedItem(\"src\").value)");
            super.onPageFinished(view, url);
            if (isPage) {
                upLoadBehaviorEvent(view);//上传用户打开网页的行为信息(埋点)
            }
            Matcher matLogistics = Pattern.compile("/logistics/info").matcher(url);
            if (matLogistics.find()) {//加载是物流界面
                //开启消息推送行为节点
                NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                boolean isOpened = manager.areNotificationsEnabled();
                Boolean isFirstOpenLogistics = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.IS_FIRST_OPEN_LOGISTICS, true);
                if (!isOpened && isFirstOpenLogistics) {
                    messageTipRl.setVisibility(View.VISIBLE);
                    closeIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.IS_FIRST_OPEN_LOGISTICS, false);
                            messageTipRl.setVisibility(View.GONE);
                        }
                    });
                    openIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });
                }
            }
            if (type != 2) {
                tvTitle.setText(view.getTitle());
                StatService.onEvent(WebActivity.this, "V3网页", url);
                TCAgent.onEvent(WebActivity.this, "V3网页", url);
            }
            imgFinish.setVisibility(webView.canGoBack() ? View.VISIBLE : View.INVISIBLE);

            if (!Util.isEmpty(url) && url.contains("/member/sign/records")) {
                NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                boolean isOpened = manager.areNotificationsEnabled();
                boolean isOpenTip = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.IS_OPEN_TIP_SIGNE, true);
                if (isOpened && isOpenTip) {
                    callBack("noticeback", "open");
                } else {
                    callBack("noticeback", "close");
                }
            }

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            webView.stopLoading();
            view.loadUrl("file:///android_asset/www/error.html");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String mUrl) {
            if (mUrl.startsWith("yy://")) {
                return super.shouldOverrideUrlLoading(view, mUrl);
            } else {
                return Util.urlAction(WebActivity.this, mUrl, Constants.Login.WEB_LOGIN, true, log);
            }
        }

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.proceed();//忽略证书错误
        }
    }

    private void upLoadBehaviorEvent(WebView view) {
        String url = Constants.API_URL + Constants.POST_BEHAVIOR_EVENT_URL;
        JSONObject tmpObj = null;
        tmpObj = new JSONObject();
        JSONObject data = null;
        data = new JSONObject();
        try {
            tmpObj.put("event", "打开文章");
            data.put("targetId", pageId);
            data.put("targetName", view.getTitle());
            data.put("targetPath", url);
            tmpObj.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String personInfos = tmpObj.toString(); // 将JSONArray转换得到String
        Charset chrutf = Charset.forName("UTF-8");
        String params = new String(personInfos.getBytes(), chrutf);
        try {
            HttpUtils.doPostAsyn(url, params, new HttpUtils.CallBack() {
                @Override
                public void onRequestComplete(final String result) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed(View v) {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public void onFinishThis(View v) {
        super.onBackPressed();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            Session.getInstance().setPosterBean(null);
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onShare(View v) {
        String webViewUrl = webView.getUrl();
        if (webViewUrl.isEmpty())
            return;
        int index = webViewUrl.indexOf("isLogin=1");
        if (index > 0 && !Util.loginChecked(this, 1)) return;
        sharePop = new SharePop(WebActivity.this);
        sharePop.setTitle(tvTitle.getText().toString());

        if (desc != null) {
            sharePop.setDescription(desc);
        } else {
            sharePop.setDescription("D2C这个活动太赞了，现在分享给你，赶紧来看看吧！");
        }
        if (webViewUrl.contains("bargain")) {
            sharePop.setPromotionLink(true, false);
        }
        if (imgUrl != null) {
            sharePop.setImage(Util.getD2cPicUrl(imgUrl, 100, 100), false);
            sharePop.setImage(Util.getD2cPicUrl(imgUrl, 360, 500), true);
        }

        if (webViewUrl.startsWith(Constants.SHARE_URL)) {
            sharePop.setWebUrl(webViewUrl);
        } else {
            sharePop.setWebUrl(webViewUrl);
            sharePop.setShareUrl(webViewUrl);
        }
        if (Session.getInstance().getPosterBean() != null) {
            if (Session.getInstance().getPosterBean().type.equals("page")) {
                sharePop.setPoster(getPagePosterView(webViewUrl));
                sharePop.addPoster();
            } else {
                sharePop.setPoster(getPosterView(webViewUrl));
            }
            sharePop.setBgImageUrl(webViewUrl);
        }
        sharePop.show(getWindow().getDecorView());
    }

    private Poster getPagePosterView(String url) {
        final Poster poster = new Poster();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_page_poster_view, new RelativeLayout(this), false);
        final ImageView bgIv = (ImageView) view.findViewById(R.id.bg_iv);
        final RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        Glide.with(this).load(Util.getD2cPicUrl(Session.getInstance().getPosterBean().pageBackGround, ScreenUtil.getDisplayWidth(), ScreenUtil.getDisplayHeight())).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                poster.baseMap = true;
                RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
                rl.width = resource.getWidth();
                bgIv.setImageBitmap(resource);
            }
        });
        poster.productMap = true;
        ImageView rq = (ImageView) view.findViewById(R.id.rq);
        rq.setImageBitmap(BitmapUtils.createWhiteQRImage(url, ScreenUtil.dip2px(62), ScreenUtil.dip2px(62), 0));
        poster.posterView = view;
        return poster;
    }

    private Poster getPosterView(String url) {
        final Poster poster = new Poster();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_poster_view, new RelativeLayout(this), false);
        final LinearLayout ll = (LinearLayout) view.findViewById(R.id.product_ll);
        final ImageView bgIv = (ImageView) view.findViewById(R.id.bg_iv);
        final ImageView rq = (ImageView) view.findViewById(R.id.rq);
        final ImageView avatar = (ImageView) view.findViewById(R.id.avatar);
        Glide.with(this).load(Util.getD2cPicUrl("https://static.d2c.cn/other/bill/billbgs.png")).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                poster.baseMap = true;
                RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) ll.getLayoutParams();
                rl.width = resource.getWidth() * 13 / 18;
                rl.height = resource.getWidth() * 14 / 9;
                RelativeLayout.LayoutParams rql = (RelativeLayout.LayoutParams) rq.getLayoutParams();
                rql.width = resource.getWidth() * 5 / 18;
                rql.height = resource.getWidth() * 5 / 18;
                LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) avatar.getLayoutParams();
                ll.width = resource.getWidth() / 9;
                ll.height = resource.getWidth() / 9;
                bgIv.setImageBitmap(resource);
            }
        });
        Glide.with(this).load(Util.getD2cPicUrl(Session.getInstance().getPosterBean().pic, 70, 70)).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Bitmap bitmap = BitmapUtils.getScaleBitmap(resource, (float) ScreenUtil.dip2px(40) / resource.getWidth(), (float) ScreenUtil.dip2px(40) / resource.getHeight());
                avatar.setImageBitmap(BitmapUtils.getCircleBitmap(bitmap));
            }
        });
        //UniversalImageLoader.displayRoundImage(this,Session.getInstance().getPosterBean().pic,avatar,R.mipmap.ic_default_avatar);
        final ImageView productImage = (ImageView) view.findViewById(R.id.product_image);
        Glide.with(this).load(Util.getD2cPicUrl(Session.getInstance().getPosterBean().productPic, 550, 1200)).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                poster.productMap = true;
                productImage.setImageBitmap(resource);
            }
        });
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(getText());
        TextView priceTv = (TextView) view.findViewById(R.id.price_tv);
        priceTv.setText(getPriceTv());
        rq.setImageBitmap(BitmapUtils.createWhiteQRImage(url, ScreenUtil.dip2px(94), ScreenUtil.dip2px(94), 0));
        poster.posterView = view;
        return poster;
    }

    private SpannableString getText() {
        StringBuilder builder = new StringBuilder();
        builder.append("我已砍至").append(Util.getNumberFormat(Session.getInstance().getPosterBean().price))
                .append("元，就差你一刀!");
        SpannableString sb = new SpannableString(builder.toString());
        int one = builder.toString().indexOf("至");
        int two = builder.toString().indexOf("元");

        ForegroundColorSpan span1 = new ForegroundColorSpan(Color.parseColor("#fff23365"));
        sb.setSpan(span1, one + 1, two, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return sb;
    }

    private SpannableString getPriceTv() {
        StringBuilder builder = new StringBuilder();
        builder.append("底价：").append("¥").append(Util.getNumberFormat(Session.getInstance().getPosterBean().minPrice))
                .append("  原价：").append(Util.getNumberFormat(Session.getInstance().getPosterBean().originalPrice));
        SpannableString sb = new SpannableString(builder.toString());
        int one = builder.toString().indexOf("¥");
        int two = builder.toString().indexOf("原");

        ForegroundColorSpan span1 = new ForegroundColorSpan(Color.parseColor("#fff23365"));
        sb.setSpan(span1, one, two, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan span2 = new ForegroundColorSpan(Color.parseColor("#4D000000"));
        sb.setSpan(span2, two, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan span3 = new RelativeSizeSpan((float) 1.8);
        sb.setSpan(span3, one + 1, two, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return sb;
    }

    private void loadUrl() {
        if (type == 0) {
            UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
            if (user != null) {
                token = user.getUserToken();
            } else {
                token = "";
            }
            webView.loadUrl(String.format(Constants.INVOKE_URL, token, Util.toURLEncode(url)));
        } else if (type == 1) {
            webView.loadUrl(url);
        } else {
            //消息详情web显示
            webView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
            tvTitle.setText(R.string.label_message_detail);
        }
        if (!Util.isEmpty(url)) {
            Matcher matcher = Pattern.compile("/page/").matcher(url);
            if (matcher.find()) {
                isPage = true;
                try {
                    pageId = Long.parseLong(matcher.group(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!Util.isEmpty(log)) {
                    log = System.currentTimeMillis() + "," + log + "V_ARTICLE:" + url.substring(matcher.end(), url.length());
                    postAppLog(log);
                }
            }
        }
    }

    private void postAppLog(String log) {
        LogApi api = new LogApi();
        api.setLog(log);
        if (Session.getInstance().getUserFromFile(this) == null) {
            LogDB.getInstance(this).insert(log);
            return;
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {

            }
        });
    }

    class JsInterface {

        @JavascriptInterface
        public void showSource(String content) {
            String s[] = content.split("\\|\\|");
            desc = s[0];
            imgUrl = s[1];
        }

        @JavascriptInterface
        public void errorReload() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    webView.clearHistory();
                    loadUrl();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        if (!Util.isEmpty(url) && url.contains("/member/sign/records")) {
            if (!Util.isEmpty(jsFuncName) && webView != null) {
                NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                boolean isOpened = manager.areNotificationsEnabled();
                boolean isOpenTip = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.IS_OPEN_TIP_SIGNE, true);
                if (isOpened && isOpenTip) {
                    D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.IS_OPEN_TIP_SIGNE, true);
                    callBack(jsFuncName, "open");
                } else {
                    D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.IS_OPEN_TIP_SIGNE, false);
                    callBack(jsFuncName, "close");
                }
            }
            if (progressBar.getVisibility()==View.GONE){
                //webView.reload();
                webView.loadUrl("javascript:ajaxRefresh()");
            }
        }

        Util.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Util.onPause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (webHandler != null) {
            webHandler.context = null;
            webHandler = null;
        }
        Session.getInstance().setPosterBean(null);
        destroy();
        super.onDestroy();
    }

    public void destroy() {
        if (webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }

            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();

            try {
                webView.destroy();
            } catch (Throwable ex) {

            }
        }
    }

    @Subscribe
    public void onEvent(GlobalTypeBean bean) {
        if (bean.getType() == Constants.GlobalType.AUCTIONPAY) {
            if (bean.getIntValue() == 1) { //重新加载webview换成finish
                finish();
                //webView.reload();
            }
        } else if (bean.getType() == Constants.GlobalType.SHARE_OK) {
            Object func = bean.getValue("func");
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            map.put("type", "share");
            map.put("status", "1");
            Gson gson = new Gson();
            webView.callHandler((func != null) ? (String) func : "b_share", gson.toJson(map), new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                }
            });
        } else if (bean.getType() == Constants.GlobalType.LOGOUT) {
            loadUrl();
        } else if (bean.getType() == Constants.GlobalType.REFRESH_WEB) {
            if (!Util.isEmpty(url) && url.contains("/collection/card/home")) {
                loadUrl();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.Login.WEB_LOGIN && resultCode == RESULT_OK) {
            loadUrl();
        }
        if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == mUploadMessageForAndroid5)
                return;
            Uri result = (data == null || resultCode != RESULT_OK) ? null
                    : data.getData();
            if (result != null) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
            }
            mUploadMessageForAndroid5 = null;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onWbShareSuccess() {
        super.onWbShareSuccess();
        if (sharePop != null) {
            sharePop.shareOut();
        }
    }

    private void callBack(String funcName, String value) {
        if (webView != null) {
            webView.callHandler(funcName, value, new CallBackFunction() {
                @Override
                public void onCallBack(String data) {

                }
            });
        }
    }
}
