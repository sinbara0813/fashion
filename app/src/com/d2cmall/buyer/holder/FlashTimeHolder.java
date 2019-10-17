package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/12/12 15:44
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FlashTimeHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.soon_content)
    public LinearLayout soonContent;
    @Bind(R.id.horizontal_scroll)
    public HorizontalScrollView horizontalScroll;
    @Bind(R.id.flash_tag_tv)
    public TextView flashTagTv;
    @Bind(R.id.flash_hour_tv)
    public TextView flashHourTv;
    @Bind(R.id.flash_minute_tv)
    public TextView flashMinuteTv;
    @Bind(R.id.flash_mouse_tv)
    public TextView flashMouseTv;
    @Bind(R.id.flash_time_ll)
    public LinearLayout flashTimeLl;
    @Bind(R.id.time_ll)
    public LinearLayout timeLl;
    @Bind(R.id.tag)
    public TextView tag;

    public FlashTimeHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
