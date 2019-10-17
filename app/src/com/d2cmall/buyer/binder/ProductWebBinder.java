package com.d2cmall.buyer.binder;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseViewBinder;
import com.d2cmall.buyer.holder.ProductWebHolder;
import com.d2cmall.buyer.util.Util;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/23 18:11
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductWebBinder implements BaseViewBinder<ProductWebHolder> {

    private Context mContext;
    private long id;

    public ProductWebBinder(Context context,long id){
        this.mContext=context;
        this.id=id;
    }

    @Override
    public ProductWebHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_web,parent,false);
        return new ProductWebHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductWebHolder productWebHolder, int position) {
        WebSettings setting = productWebHolder.webView.getSettings();
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);
        setting.setAllowFileAccess(true);
        setting.setJavaScriptEnabled(true);
        setting.setSupportZoom(true);
        setting.setAppCacheEnabled(true);
        setting.setDomStorageEnabled(true);
        setting.setJavaScriptCanOpenWindowsAutomatically(true);
        String ua = setting.getUserAgentString();
        setting.setUserAgentString(ua + "d2cmall/Android/" + Util.getPageVersionName(mContext));//d2cmall/Android/3.3.0
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        productWebHolder.webView.setHorizontalScrollBarEnabled(false);
        productWebHolder.webView.setVerticalScrollBarEnabled(false);
        String url = "/product/detail/"+id ;
        String token = "";
        productWebHolder.webView.loadUrl(String.format(Constants.INVOKE_URL, token, Util.toURLEncode(url)));
        productWebHolder.webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return Util.urlAction(mContext,url);
            }
        });
    }

    @Override
    public void onBindViewHolderWithOffer(ProductWebHolder productWebHolder, int position, int offsetTotal) {

    }
}
