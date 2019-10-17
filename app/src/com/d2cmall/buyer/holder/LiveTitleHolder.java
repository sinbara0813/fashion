package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/18 10:41
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LiveTitleHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.title_tv)
    public TextView titleTv;
    @Bind(R.id.title_all)
    public TextView titleAll;

    public LiveTitleHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
