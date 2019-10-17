package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/4 14:37
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductShareAdHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.ad_image)
    public ImageView ivAd;

    public ProductShareAdHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
