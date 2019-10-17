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
 * Date: 2017/9/11 14:52
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainSpecialLiveHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.title_tv)
    public TextView titleTv;
    @Bind(R.id.title_all)
    public TextView titleAll;
    @Bind(R.id.title_ll)
    public LinearLayout titleLl;
    @Bind(R.id.img_avatar)
    public ImageView imgAvatar;
    @Bind(R.id.name)
    public TextView name;
    @Bind(R.id.watch_count)
    public TextView watchCount;
    @Bind(R.id.top)
    public RelativeLayout top;
    @Bind(R.id.image)
    public ImageView image;
    @Bind(R.id.tag_tv)
    public TextView tagTv;
    @Bind(R.id.des)
    public TextView des;

    public MainSpecialLiveHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
