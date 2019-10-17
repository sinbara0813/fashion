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
 * Date: 2017/12/18 9:55
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainFlashHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tag)
    public TextView tag;
    @Bind(R.id.flash_hour_tv)
    public TextView flashHourTv;
    @Bind(R.id.flash_minute_tv)
    public TextView flashMinuteTv;
    @Bind(R.id.flash_mouse_tv)
    public TextView flashMouseTv;
    @Bind(R.id.time_ll)
    public LinearLayout timeLl;
    @Bind(R.id.flash_iv)
    public ImageView flashIv;
    @Bind(R.id.flash_rl)
    public RelativeLayout flashRl;

    public MainFlashHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView );
    }
}
