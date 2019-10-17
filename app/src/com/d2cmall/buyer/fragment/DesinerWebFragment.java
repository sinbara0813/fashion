package com.d2cmall.buyer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.util.Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.Bind;

/**
 * Created by rookie on 2017/8/3.
 * 店铺主页Fragment
 */

public class DesinerWebFragment extends BaseFragment {
    @Bind(R.id.webView)
    WebView webView;
    private String url;

    private boolean isLoad;
    private String h5;

    public static DesinerWebFragment newInstance(String h5) {
        DesinerWebFragment brandFashionFragment = new DesinerWebFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", h5);
        brandFashionFragment.setArguments(bundle);
        return brandFashionFragment;
    }


    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        h5=getArguments().getString("id");
        return inflater.inflate(R.layout.fragment_desiner_web1, container, false);
    }

    @Override
    public void prepareView() {

    }


    @Override
    public void doBusiness() {

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWebView();
        loadWeb();
    }

    private void initWebView() {
        WebSettings setting = webView.getSettings();
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);
        setting.setAllowFileAccess(true);
        setting.setJavaScriptEnabled(true);
    }

    private void loadWeb() {
        webView.setVisibility(View.VISIBLE);
        if (isLoad){
            return;
        }

        String token = "";
        webView.loadDataWithBaseURL(null,h5,"text/html","utf-8",null);
        //webView.loadUrl(String.format(Constants.INVOKE_URL, token, Util.toURLEncode(url)));
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return Util.urlAction(getActivity(),url);
            }
        });
        isLoad=true;
    }

    private String fmtString(String string){
        String notice = "";
        try{
            if(!Util.isEmpty(string)){
                notice = URLEncoder.encode(string, "utf-8");
            }
        }catch(UnsupportedEncodingException ex){

        }
        return notice;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //webRecycle();
    }

    @Override
    public void releaseOnInVisible() {

    }

    public void webRecycle(){
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
