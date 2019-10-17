package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/28 17:13
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CombProductPopBottomHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.minus)
    public ImageView mMinus;
    @Bind(R.id.cart_num)
    public TextView mCartNum;
    @Bind(R.id.add)
    public ImageView mAdd;
    public CombProductPopBottomHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }



}
