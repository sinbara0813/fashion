package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/29 18:14
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class HotTopicsHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.flow1)
    public ImageView flow1;
    @Bind(R.id.flow2)
    public ImageView flow2;
    @Bind(R.id.flow3)
    public ImageView flow3;
    @Bind(R.id.all_tv)
    public TextView allTv;
    @Bind(R.id.flow_tv1)
    public TextView flowTv1;
    @Bind(R.id.flow_rl1)
    public RelativeLayout flowRl1;
    @Bind(R.id.flow_tv2)
    public TextView flowTv2;
    @Bind(R.id.flow_rl2)
    public RelativeLayout flowRl2;
    @Bind(R.id.flow_tv3)
    public TextView flowTv3;
    @Bind(R.id.flow_rl3)
    public RelativeLayout flowRl3;

    public HotTopicsHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
