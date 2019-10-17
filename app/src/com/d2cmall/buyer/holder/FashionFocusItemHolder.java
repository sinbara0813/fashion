package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.ShowPopImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/16 17:36
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FashionFocusItemHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.img_avatar)
    public ImageView imgAvatar;
    @Bind(R.id.name_tv)
    public TextView nameTv;
    //开启消息推送行为节点
    @Bind(R.id.focus_iv)
    public ShowPopImageView focusIv;
    @Bind(R.id.image1)
    public ImageView image1;
    @Bind(R.id.image2)
    public ImageView image2;
    @Bind(R.id.image3)
    public ImageView image3;
    @Bind(R.id.image_tag1)
    public ImageView imageTag1;
    @Bind(R.id.image_tag2)
    public ImageView imageTag2;
    @Bind(R.id.image_tag3)
    public ImageView imageTag3;
    @Bind(R.id.ll_head)
    public LinearLayout llTitle;
    @Bind(R.id.tv_title)
    public TextView tvTitle;
    @Bind(R.id.tv_tag)
    public TextView tvTag;

    public FashionFocusItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
