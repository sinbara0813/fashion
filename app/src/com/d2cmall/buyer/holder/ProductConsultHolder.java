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
 * Date: 2017/9/5 16:30
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductConsultHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.img_avatar)
    public ImageView imgAvatar;
    @Bind(R.id.name)
    public TextView name;
    @Bind(R.id.time_tv)
    public TextView timeTv;
    @Bind(R.id.answer_tv)
    public TextView answerTv;
    @Bind(R.id.da_tv)
    public TextView daTv;
    @Bind(R.id.first_ll)
    public LinearLayout firstLl;
    @Bind(R.id.second_rl)
    public RelativeLayout secondRl;
    @Bind(R.id.third_rl)
    public RelativeLayout thirdRl;

    public ProductConsultHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
