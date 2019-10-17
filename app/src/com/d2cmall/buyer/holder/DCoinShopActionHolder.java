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
 * Date: 2017/8/31 18:36
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class DCoinShopActionHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tv_dcoin_amount)
    public TextView tvDcoinAmount;
    @Bind(R.id.tv_record_conversion)
    public TextView tvRecordConversion;
    public DCoinShopActionHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
