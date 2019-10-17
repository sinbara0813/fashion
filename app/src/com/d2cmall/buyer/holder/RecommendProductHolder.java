package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.ScreenUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/2 12:01
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class RecommendProductHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.image)
    public ImageView image;
    @Bind(R.id.view_no_store)
    public View viewNoStore;
    @Bind(R.id.tv_no_store)
    public TextView tvNoStore;
    @Bind(R.id.design_name)
    public TextView designName;
    @Bind(R.id.product_name)
    public TextView productName;
    @Bind(R.id.product_price)
    public TextView productPrice;
    @Bind(R.id.product_drop_price)
    public TextView productDropPrice;
    @Bind(R.id.year_tag)
    public ImageView yearTag;
    @Bind(R.id.view_find)
    public RelativeLayout rlFind;
    @Bind(R.id.iv_find)
    public ImageView ivFind;
    @Bind(R.id.ll_promotion_type)
    public LinearLayout llPromotionType;
    @Bind(R.id.tv_order_promotion)
    public TextView tvOrderPromotion;
    @Bind(R.id.tv_good_promotion)
    public TextView tvGoodPromotion;
    @Bind(R.id.tv_global_tag)
    public TextView tvGlobal;
    public RecommendProductHolder(View itemView, int itemWidth) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        RelativeLayout.LayoutParams ll = new RelativeLayout.LayoutParams(-2, -2);
        ll.width = itemWidth- ScreenUtil.dip2px(1);
        ll.height = (int) (ll.width * ((float) 1558 / 1000));
        image.setLayoutParams(ll);
    }
}
