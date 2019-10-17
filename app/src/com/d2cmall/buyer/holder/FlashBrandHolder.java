package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/12/13 15:57
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FlashBrandHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.brand_iv)
    public ImageView brandIv;
    @Bind(R.id.recycle_view)
    public RecyclerView recycleView;

    public FlashBrandHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
