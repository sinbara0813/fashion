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
 * Date: 2017/8/31 18:46
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainLiveHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.title_tv)
    public TextView titleTv;
    @Bind(R.id.title_all)
    public TextView titleAll;
    @Bind(R.id.title_ll)
    public LinearLayout titleLl;
    @Bind(R.id.iv_big_image)
    public ImageView ivBigImage;
    @Bind(R.id.tv_big_left_top)
    public TextView tvBigLeftTop;
    @Bind(R.id.tv_big_right_top)
    public TextView tvBigRightTop;
    @Bind(R.id.view_big_gradient)
    public View viewBigGradient;
    @Bind(R.id.tv_big_bottom_left)
    public TextView tvBigBottomLeft;
    @Bind(R.id.rl_big)
    public RelativeLayout rlBig;
    @Bind(R.id.iv_small_1)
    public ImageView ivSmall1;
    @Bind(R.id.tv_small_1_left_top)
    public TextView tvSmall1LeftTop;
    @Bind(R.id.tv_small_1_bottom)
    public TextView tvSmall1Bottom;
    @Bind(R.id.rl_small_1)
    public RelativeLayout rlSmall1;
    @Bind(R.id.iv_small_2)
    public ImageView ivSmall2;
    @Bind(R.id.tv_small_2_left_top)
    public TextView tvSmall2LeftTop;
    @Bind(R.id.tv_small_2_bottom)
    public TextView tvSmall2Bottom;
    @Bind(R.id.rl_small_2)
    public RelativeLayout rlSmall2;

    public MainLiveHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
