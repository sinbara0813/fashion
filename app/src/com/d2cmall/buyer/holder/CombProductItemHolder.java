package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 组合商品holder
 * Name: d2c
 * Anthor: lwj
 * Date: 2017/7/28 17:13
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CombProductItemHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.iv_comb_top)
    public ImageView mIvCombTop;
    @Bind(R.id.tv_comb_top_name)
    public TextView mTvCombTopName;
    @Bind(R.id.tv_comb_top_price)
    public TextView mTvCombTopPrice;
    @Bind(R.id.tv_comb_old_price)
    public TextView mTvCombOldPrice;

    public CombProductItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }



}
