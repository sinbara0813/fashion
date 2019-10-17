package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
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
public class CouponRelationHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.time_tag)
    public TextView timeTag;
    @Bind(R.id.time_day)
    public TextView timeDay;
    @Bind(R.id.time_hour)
    public TextView timeHour;
    @Bind(R.id.time_minute)
    public TextView timeMinute;
    @Bind(R.id.time_mouse)
    public TextView timeMouse;
    @Bind(R.id.time_ms)
    public  TextView timeMs;
    @Bind(R.id.ll_down_time)
    public LinearLayout llDownTime;
    @Bind(R.id.tv_coupon_name)
    public  TextView tvCouponName;

    public CouponRelationHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
