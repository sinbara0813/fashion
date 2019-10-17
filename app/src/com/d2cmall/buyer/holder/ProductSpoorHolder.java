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
 * Date: 2018/3/6 10:39
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductSpoorHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.zu_iv)
    public ImageView zuIv;
    @Bind(R.id.ding_iv)
    public ImageView dingIv;

    public ProductSpoorHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
