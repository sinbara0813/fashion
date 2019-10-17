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
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/24 15:55
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainType7ViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.img_type7_item_pic)
    public ImageView imgType7ItemPic;
    @Bind(R.id.tv_type7_item_title)
    public TextView tvType7ItemTitle;
    @Bind(R.id.tv_type7_item_content)
    public TextView tvType7ItemContent;
    @Bind(R.id.tv_type7_item_price)
    public TextView tvType7ItemPrice;
    @Bind(R.id.tv_type7_item_origin_price)
    public TextView tvType7ItemOriginPrice;
    @Bind(R.id.type7_item_price_layout)
    public LinearLayout type7ItemPriceLayout;
    @Bind(R.id.rv_type7_item)
    public RecyclerView rvType7Item;
    @Bind(R.id.title_tv)
    public TextView titleTv;
    @Bind(R.id.title_all)
    public TextView titleAll;
    @Bind(R.id.title_ll)
    public LinearLayout titleLl;

    public MainType7ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
