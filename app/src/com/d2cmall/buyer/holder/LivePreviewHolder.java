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
 * Date: 2017/8/18 10:55
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LivePreviewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.image)
    public ImageView image;
    @Bind(R.id.img_avatar)
    public ImageView imgAvatar;
    @Bind(R.id.name)
    public TextView name;
    @Bind(R.id.info)
    public TextView info;
    @Bind(R.id.time_tv)
    public TextView timeTv;
    @Bind(R.id.notice_iv)
    public ImageView noticeIv;
    @Bind(R.id.tv_start)
    public TextView tvStart;
    public LivePreviewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
