package com.d2cmall.buyer.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.Util;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/26 16:11
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductWebFragment extends com.d2cmall.buyer.base.BaseFragment {

    private WebView webView;

    private String url;

    private boolean isLoad;
    private long id;

    public static ProductWebFragment newInstance(long id){
        ProductWebFragment webFragment=new ProductWebFragment();
        Bundle bundle=new Bundle();
        bundle.putLong("id",id);
        webFragment.setArguments(bundle);
        return webFragment;
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_web,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments()!=null){
            id=getArguments().getLong("id");
        }
    }

    @Override
    public void prepareView() {

    }

    private void initWebView(){
        webView= (WebView) rootView.findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        String ua = webView.getSettings().getUserAgentString();
        webView.getSettings().setUserAgentString(ua + "d2cmall/Android/" + Util.getPageVersionName(getActivity()));//d2cmall/Android/3.3.0
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    public void doBusiness() {
        loadWeb();
    }

    private void loadWeb() {
        initWebView();
        webView.setVisibility(View.VISIBLE);
        if (isLoad){
            return;
        }
        url = "/product/detail/"+id ;
        String token = "";
        String loadUrl=String.format(Constants.INVOKE_URL, token, Util.toURLEncode(url));
        webView.loadUrl(loadUrl);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("yy://")) {
                    return super.shouldOverrideUrlLoading(view, url);
                } else {
                    return Util.urlAction(getActivity(),url);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        isLoad=true;
    }

    @Override
    public void releaseOnInVisible() {
        if (webView!=null){
            webView.setVisibility(View.GONE);
        }
        super.releaseOnInVisible();
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
