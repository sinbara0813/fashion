package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.Banner;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/28 9:55
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class BannerHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.banner)
    public Banner banner;
    public BannerHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
