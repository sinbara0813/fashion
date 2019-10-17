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
 * Date: 2017/12/14 9:58
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FlashProductHeadHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.head_iv)
    public ImageView headIv;
    @Bind(R.id.tag)
    public TextView tag;
    @Bind(R.id.flash_hour_tv)
    public TextView flashHourTv;
    @Bind(R.id.flash_minute_tv)
    public TextView flashMinuteTv;
    @Bind(R.id.flash_mouse_tv)
    public TextView flashMouseTv;
    @Bind(R.id.flash_time_ll)
    public LinearLayout flashTimeLl;

    public FlashProductHeadHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
