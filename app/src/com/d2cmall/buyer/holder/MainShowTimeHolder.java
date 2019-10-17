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
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/8/30 16:20
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainShowTimeHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.iv_big_image)
    public ImageView ivBigImage;
    @Bind(R.id.iv_small_1_image)
    public ImageView ivSmall1Image;
    @Bind(R.id.time_tv1)
    public TextView timeTv1;
    @Bind(R.id.time_tag_tv1)
    public TextView timeTagTv1;
    @Bind(R.id.iv_small_2_image)
    public ImageView ivSmall2Image;
    @Bind(R.id.time_tag_tv)
    public TextView timeTagTv;
    @Bind(R.id.time_tv)
    public TextView timeTv;
    @Bind(R.id.small_relative_rl)
    public RelativeLayout smallRelativeRl;
    @Bind(R.id.iv_match_width_image)
    public ImageView ivMatchWidthImage;
    @Bind(R.id.title_tv)
    public TextView titleTv;
    @Bind(R.id.title_all)
    public TextView titleAll;
    @Bind(R.id.title_ll)
    public LinearLayout titleLl;

    public MainShowTimeHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
