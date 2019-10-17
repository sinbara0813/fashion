package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/8/31 16:16
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainType11Holder extends RecyclerView.ViewHolder {

    @Bind(R.id.top_iv)
    public ImageView topIv;
    @Bind(R.id.bottom_rv)
    public RecyclerView bottomRv;

    public MainType11Holder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
