package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.ShowPopImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: d2c
 * Anthor: 组合商品名称和固定文本holder
 * Date: 2017/7/28 17:13
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CombProductInfoHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.product_remark)
    public TextView productRemark;
    @Bind(R.id.black_fl)
    public FrameLayout blackFl;
    @Bind(R.id.product_price)
    public TextView productPrice;
    @Bind(R.id.product_name)
    public TextView productName;


    public CombProductInfoHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}
