package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.CheckBox;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/2 18:13
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CartItemHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.checkbox)
    CheckBox checkbox;
    @Bind(R.id.cart_iv)
    public ImageView cartIv;
    @Bind(R.id.sale_out_tag)
    TextView saleOutTag;
    @Bind(R.id.cart_info_tv)
    public TextView cartInfoTv;
    @Bind(R.id.cart_info_size_tv)
    public TextView cartInfoSizeTv;
    @Bind(R.id.cart_info_price)
    public TextView cartInfoPrice;
    @Bind(R.id.cart_info_org_price)
    TextView cartInfoOrgPrice;
    @Bind(R.id.cart_info_num)
    public TextView cartInfoNum;
    @Bind(R.id.cart_info)
    RelativeLayout cartInfo;
    @Bind(R.id.edit_size_tv)
    TextView editSizeTv;
    @Bind(R.id.edit_size_ll)
    LinearLayout editSizeLl;
    @Bind(R.id.minus)
    ImageView minus;
    @Bind(R.id.cart_num)
    TextView cartNum;
    @Bind(R.id.add)
    ImageView add;
    @Bind(R.id.cart_info_edit)
    RelativeLayout cartInfoEdit;
    @Bind(R.id.middle_ll)
    RelativeLayout middleLl;
    @Bind(R.id.promotion_name)
    TextView promotionName;
    @Bind(R.id.promotion_ll)
    LinearLayout promotionLl;

    public CartItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
