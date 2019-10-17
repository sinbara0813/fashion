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
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/12 16:18
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainBrandHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.title_tv)
    public TextView titleTv;
    @Bind(R.id.title_all)
    public TextView titleAll;
    @Bind(R.id.title_ll)
    public LinearLayout titleLl;
    @Bind(R.id.brand_recycle_view)
    public RecyclerView brandRecycleView;
    @Bind(R.id.first_image)
    public ImageView firstImage;
    @Bind(R.id.first_brand_head)
    public ImageView firstBrandHead;
    @Bind(R.id.first_brand_name)
    public TextView firstBrandName;
    @Bind(R.id.first_brand_recycle_view)
    public RecyclerView firstBrandRecycleView;
    @Bind(R.id.second_image)
    public ImageView secondImage;
    @Bind(R.id.second_brand_head)
    public ImageView secondBrandHead;
    @Bind(R.id.second_brand_name)
    public TextView secondBrandName;
    @Bind(R.id.second_brand_recycle_view)
    public RecyclerView secondBrandRecycleView;

    public MainBrandHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
