package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.VideoBanner;
import com.d2cmall.buyer.widget.VideoBanner1;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/23 19:28
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class VideoBannerHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.banner)
    public VideoBanner1 banner;

    public VideoBannerHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
