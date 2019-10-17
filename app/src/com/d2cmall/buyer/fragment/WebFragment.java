package com.d2cmall.buyer.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;

import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.util.WebHandler;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.d2cmall.buyer.widget.web.DefaultWebViewClient;
import com.d2cmall.buyer.widget.web.MyWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tendcloud.tenddata.TCAgent;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class WebFragment extends RefreshFragment{

    public MyWebView webView;
    private PtrStoreHouseFrameLayout ptr;
    private static final String ARG_PARAM1 = "param1";
    private String url;
    private String token = "";
    private String desc;
    private String imgUrl = null;
    private WebHandler webHandler;
    private String webTitle;
    private boolean intercept;

    public static WebFragment newInstance(String url) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_webview, container, false);
        webView = (MyWebView) rootView.findViewById(R.id.webView);
        ptr = (PtrStoreHouseFrameLayout) rootView.findViewById(R.id.ptr);

        if (getArguments() != null) {
            url = getArguments().getString(ARG_PARAM1);
        }
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        String cacheDirPath = getActivity().getFilesDir().getAbsolutePath() + "webCache";
        webView.getSettings().setAppCachePath(cacheDirPath);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDatabasePath(cacheDirPath);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new JsInterface(), "D2C");
        String ua = webView.getSettings().getUserAgentString();
        webView.getSettings().setUserAgentString(ua + String.format(Constants.USER_AGENT_URL,
                Util.getPageVersionName(getActivity()), Util.getNetType(getActivity())));
        webView.setWebViewClient(new MyWebViewClient(webView));
        webHandler=new WebHandler(getActivity());
        webView.registerHandler("d2cinit", webHandler);
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return webView.getWebScrollY()==0;
            }
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                webView.stopLoading();
                loadUrl();
            }
        });
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        loadUrl();
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                webView.requestDisallowInterceptTouchEvent(intercept);
                return false;
            }
        });
        return rootView;
    }

    class MyWebViewClient extends DefaultWebViewClient {

        MyWebViewClient(MyWebView webView) {
            super(webView);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:window.D2C.showSource(document.getElementsByName(\"description\")[0].attributes.getNamedItem(\"content\").value" +
                    "+'||'+document.getElementsByTagName(\"img\")[0].attributes.getNamedItem(\"src\").value)");
            webTitle=view.getTitle();
            if (!Util.isEmpty(webTitle)){
                StatService.onPageStart(getActivity(),webTitle);
                TCAgent.onPageEnd(getActivity(),webTitle);
            }
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            ptr.refreshComplete();
            //((MainFragment) getParentFragment()).progressBar.setVisibility(View.GONE);
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
                return Util.urlAction(getActivity(), mUrl, Constants.Login.WEB_LOGIN);
            }
        }
    }

    @Override
    public void refresh(Object... params) {
        webView.stopLoading();
        loadUrl();
    }

    private void loadUrl() {
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(getActivity());
        if (user != null) {
            token = user.getUserToken();
        } else {
            token = "";
        }
        webView.loadUrl(String.format(Constants.INVOKE_URL, token, Util.toURLEncode(url)));
    }

    @Subscribe
    public void onEvent(GlobalTypeBean bean) {
        if (bean.getType() == Constants.GlobalType.MANI_WEB_FRAMENT_LOGIN) {
            loadUrl();
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            map.put("type", "login");
            Gson gson = new Gson();
            webView.callHandler("b_login", gson.toJson(map), new CallBackFunction() {
                @Override
                public void onCallBack(String data) {

                }
            });
        } else if (bean.getType() == Constants.GlobalType.SHARE_OK) {
            Object func= bean.getValue("func");
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            map.put("type", "share");
            map.put("status", "1");
            Gson gson = new Gson();
            webView.callHandler((func!=null)?(String)func:"b_share", gson.toJson(map), new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                }
            });
        } else if (bean.getType() == Constants.GlobalType.LOGOUT) {
            loadUrl();
        }
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

        @JavascriptInterface
        public void getH5ViewPagerInfo(int top , int height){
            /*startY=top*ScreenUtil.density;
            endY=(top+height)*ScreenUtil.density;*/
        }

        @JavascriptInterface
        public void requestEvent(boolean is){
            intercept=is;
        }
    }

    @Override
    public void onPause() {
        if (!Util.isEmpty(webTitle)){
            StatService.onPageEnd(getActivity(),webTitle);
            TCAgent.onPageEnd(getActivity(),webTitle);
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        ViewParent parent = webView.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(webView);
        }
        if (webView != null) {
            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}