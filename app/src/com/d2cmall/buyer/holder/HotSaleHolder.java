package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2018/3/22 10:59
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class HotSaleHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.hot_name)
    public TextView hotName;
    @Bind(R.id.first_iv)
    public ImageView firstIv;
    @Bind(R.id.second_iv)
    public ImageView secondIv;
    @Bind(R.id.three_iv)
    public ImageView threeIv;
    @Bind(R.id.four_iv)
    public ImageView fourIv;
    @Bind(R.id.more_iv)
    public ImageView moreIv;

    public HotSaleHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
