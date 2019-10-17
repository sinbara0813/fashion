package com.d2cmall.buyer.holder;

import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 拼团列表holder
 * Name: d2c
 * Anthor: lwj
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CollageListHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.avatar)
    public ImageView avatar;
    @Bind(R.id.name)
    public TextView name;
    @Bind(R.id.num)
    public TextView num;
    @Bind(R.id.time)
    public TextView time;
    @Bind(R.id.join)
    public TextView join;
    @Bind(R.id.line_layout)
    public View lineLayout;
    public CountDownTimer countDownTimer;
    public CollageListHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}
