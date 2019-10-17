package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/25 17:09
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class BrandCouponHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_coupon_amount)
    public TextView tvCouponAmount;
    @Bind(R.id.tv_limit_amount)
    public TextView tvLimitAmount;
    @Bind(R.id.tv_coupon_name)
    public  TextView tvCouponName;
    @Bind(R.id.tv_coupon_date)
    public TextView tvCouponDate;
    @Bind(R.id.iv_receive)
    public ImageView ivReceive;

    public BrandCouponHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

}
