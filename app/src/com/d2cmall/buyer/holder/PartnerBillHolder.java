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
 * Fixme
 * Author: LWJ
 * desc:    商品报告holder
 * Date: 2017/10/12 14:43
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class PartnerBillHolder extends RecyclerView.ViewHolder {


    @Bind(R.id.tv_order_code)
    public TextView tvOrderCode;
    @Bind(R.id.tv_copy)
    public TextView tvCopy;
    @Bind(R.id.tv_order_status)
    public TextView tvOrderStatus;
    @Bind(R.id.ll_order_code)
    public LinearLayout llOrderCode;
    @Bind(R.id.iv_product)
    public  ImageView ivProduct;
    @Bind(R.id.tv_product_name)
    public TextView tvProductName;
    @Bind(R.id.tv_product_price)
    public TextView tvProductPrice;
    @Bind(R.id.tv_product_num)
    public TextView tvProductNum;
    @Bind(R.id.tv_phone)
    public  TextView tvPhone;
    @Bind(R.id.tv_date)
    public  TextView tvDate;
    @Bind(R.id.tv_look_buyer)
    public  TextView tvLookBuyer;
    @Bind(R.id.tv_logistics_name)
    public  TextView tvLogisticsName;
    @Bind(R.id.ll_logistics_info)
    public  LinearLayout llLogisticsInfo;
    @Bind(R.id.tv_pay_money)
    public  TextView tvPayMoney;
    @Bind(R.id.tv_expect_tip)
    public TextView tvExpectTip;
    @Bind(R.id.ll_expect_tip)
    public  LinearLayout llExpectTip;
    @Bind(R.id.line_layout)
    public  View lineLayout;
    @Bind(R.id.iv_look_buyer)
    public  ImageView ivLookBuyer;
    @Bind(R.id.tv_rebeat_desc)
    public TextView tvRebeatDesc;
    @Bind(R.id.tv_self_flag)
    public TextView tvSelfFlag;
    @Bind(R.id.iv_tax_tip)
    public  ImageView ivTaxTip;
    public PartnerBillHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
