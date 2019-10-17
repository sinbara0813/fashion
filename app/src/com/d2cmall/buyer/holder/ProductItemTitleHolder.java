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
 * Date: 2017/9/4 15:00
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductItemTitleHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.title_name)
    public TextView titleName;
    @Bind(R.id.title_more)
    public TextView titleMore;
    @Bind(R.id.top_line)
    public View topLine;
    @Bind(R.id.line)
    public View line;

    public ProductItemTitleHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
