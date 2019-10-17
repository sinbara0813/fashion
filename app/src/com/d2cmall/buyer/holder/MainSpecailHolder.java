package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/11 14:39
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainSpecailHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.title_tv)
    public TextView titleTv;
    @Bind(R.id.title_all)
    public TextView titleAll;
    @Bind(R.id.title_ll)
    public LinearLayout titleLl;
    @Bind(R.id.recycle_view)
    public RecyclerView recycleView;
    @Bind(R.id.horizontal_view)
    public HorizontalScrollView scrollView;
    @Bind(R.id.content_ll)
    public LinearLayout contentLl;

    public MainSpecailHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
