package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/18 10:43
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LiveItemHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.img_avatar)
    public ImageView imgAvatar;
    @Bind(R.id.name)
    public TextView name;
    @Bind(R.id.watch_count)
    public TextView watchCount;
    @Bind(R.id.image)
    public ImageView image;
    @Bind(R.id.tag_tv)
    public TextView tagTv;
    @Bind(R.id.des)
    public TextView des;

    public LiveItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
