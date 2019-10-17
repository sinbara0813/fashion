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
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2018/1/3 16:03
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class BrandUpdateHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.img_avatar)
    public ImageView imgAvatar;
    @Bind(R.id.name_tv)
    public TextView nameTv;
    @Bind(R.id.num)
    public TextView num;
    @Bind(R.id.name_ll)
    public LinearLayout nameLl;
    //开启消息推送行为节点
    @Bind(R.id.focus_iv)
    public ShowPopImageView focusIv;
    @Bind(R.id.image1)
    public ImageView image1;
    @Bind(R.id.image2)
    public ImageView image2;
    @Bind(R.id.image3)
    public ImageView image3;
    @Bind(R.id.text1)
    public TextView text1;
    @Bind(R.id.text2)
    public TextView text2;
    @Bind(R.id.text3)
    public TextView text3;

    public BrandUpdateHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
