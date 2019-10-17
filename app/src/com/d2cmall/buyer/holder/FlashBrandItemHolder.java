package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/12/28 15:06
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FlashBrandItemHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.bg)
    public ImageView bg;
    @Bind(R.id.name)
    public TextView name;
    @Bind(R.id.image1)
    public ImageView image1;
    @Bind(R.id.price1)
    public TextView price1;
    @Bind(R.id.rl1)
    public LinearLayout rl1;
    @Bind(R.id.image2)
    public ImageView image2;
    @Bind(R.id.price2)
    public TextView price2;
    @Bind(R.id.rl2)
    public LinearLayout rl2;
    @Bind(R.id.image3)
    public ImageView image3;
    @Bind(R.id.price3)
    public TextView price3;
    @Bind(R.id.rl3)
    public LinearLayout rl3;

    public FlashBrandItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
