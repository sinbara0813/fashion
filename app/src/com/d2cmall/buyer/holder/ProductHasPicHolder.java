package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/4 15:26
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductHasPicHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.all_tv)
    public TextView allTv;
    @Bind(R.id.pic_tv)
    public TextView picTv;
    @Bind(R.id.show_tv)
    public TextView showTv;

    public ProductHasPicHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
