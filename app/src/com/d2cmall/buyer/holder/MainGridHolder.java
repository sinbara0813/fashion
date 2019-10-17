package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/26 18:05
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainGridHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.title_tv)
    public TextView titleTv;
    @Bind(R.id.title_all)
    public TextView titleAll;
    @Bind(R.id.title_ll)
    public LinearLayout titleLl;
    @Bind(R.id.grid_layout)
    public GridLayout gridLayout;

    public MainGridHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
