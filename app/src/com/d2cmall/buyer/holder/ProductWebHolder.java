package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/23 18:13
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductWebHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.web_view)
    public WebView webView;

    public ProductWebHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
