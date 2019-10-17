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
 * Date: 2017/8/31 18:36
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class DCoinRecordConversionHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_title)
    public TextView tvTitle;
    @Bind(R.id.tv_price)
    public TextView tvPrice;
    @Bind(R.id.tv_conversion_code)
    public TextView tvConversionCode;
    @Bind(R.id.tv_date)
    public TextView tvDate;
    @Bind(R.id.image)
    public ImageView image;

    public DCoinRecordConversionHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
