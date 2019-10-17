package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.UPMarqueeView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/31 10:26
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class SuperGatherHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.up_marquee)
    public UPMarqueeView upMarquee;
    @Bind(R.id.more)
    public ImageView more;

    public SuperGatherHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
