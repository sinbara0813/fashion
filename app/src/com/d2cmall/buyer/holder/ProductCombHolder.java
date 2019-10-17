package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/13 13:59
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductCombHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.top_line)
    public View topLine;
    @Bind(R.id.title_name)
    public TextView titleName;
    @Bind(R.id.title_more)
    public TextView titleMore;
    @Bind(R.id.line)
    public View line;
    @Bind(R.id.tv_comb_title)
    public TextView tvCombTitle;
    @Bind(R.id.tv_comb_price)
    public  TextView tvCombPrice;
    @Bind(R.id.tv_origin_price)
    public  TextView tvOriginPrice;
    @Bind(R.id.tv_gap_price)
    public  TextView tvGapPrice;
    @Bind(R.id.product_comb_rv)
    public RecyclerView productCombRv;
    @Bind(R.id.rl_title)
    public RelativeLayout rlTitle;
    @Bind(R.id.ll_comb)
    public LinearLayout llComb;


    public ProductCombHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
