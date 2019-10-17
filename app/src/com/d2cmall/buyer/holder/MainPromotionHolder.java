package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/24 16:56
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainPromotionHolder extends RecyclerView.ViewHolder {


    @Bind(R.id.iv_main_image)
    public ImageView ivMainImage;
    @Bind(R.id.iv_inner_image)
    public ImageView ivInnerImage;
    @Bind(R.id.image_rl)
    public RelativeLayout imageRl;
    @Bind(R.id.rv_main_item)
    public RecyclerView rvMainItem;
    @Bind(R.id.title_tv)
    public TextView titleTv;
    @Bind(R.id.title_all)
    public TextView titleAll;
    @Bind(R.id.title_ll)
    public LinearLayout titleLl;

    public MainPromotionHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
