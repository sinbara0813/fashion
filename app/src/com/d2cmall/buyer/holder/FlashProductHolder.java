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
 * Date: 2017/12/12 14:10
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FlashProductHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.product_iv)
    public ImageView productIv;
    @Bind(R.id.sale_out_tag)
    public ImageView saleOutTag;
    @Bind(R.id.product_info_tv)
    public TextView productInfoTv;
    @Bind(R.id.product_notice_tv)
    public TextView productNoticeTv;
    @Bind(R.id.product_price)
    public TextView productPrice;
    @Bind(R.id.product_sub_name)
    public TextView productSubName;

    public FlashProductHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
