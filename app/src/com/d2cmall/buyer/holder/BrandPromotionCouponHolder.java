package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.ShowPopImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fixme
 * Author: LWJ
 * desc:       店铺活动优惠券
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class BrandPromotionCouponHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_price)
    public TextView tvPrice;
    @Bind(R.id.ll_coupon_left)
    public LinearLayout llCouponLeft;
    @Bind(R.id.tv_limit)
    public  TextView tvLimit;

    public BrandPromotionCouponHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
