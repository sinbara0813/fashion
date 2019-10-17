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
 * Date: 2017/7/31 13:59
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FocusHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.image1)
    public ImageView image1;
    @Bind(R.id.tv1)
    public TextView tv1;
    @Bind(R.id.focus1)
    public ShowPopImageView focus1;
    @Bind(R.id.image2)
    public ImageView image2;
    @Bind(R.id.tv2)
    public TextView tv2;
    @Bind(R.id.focus2)
    public ShowPopImageView focus2;
    @Bind(R.id.image3)
    public ImageView image3;
    @Bind(R.id.tv3)
    public TextView tv3;
    @Bind(R.id.focus3)
    public ShowPopImageView focus3;
    @Bind(R.id.image4)
    public ImageView image4;
    @Bind(R.id.tv4)
    public TextView tv4;
    //开启消息推送行为节点
    @Bind(R.id.focus4)
    public ShowPopImageView focus4;
    @Bind(R.id.focus_ll)
    public LinearLayout focusLL;
    @Bind(R.id.recommend_member_tv)
    public TextView recommendMemberTv;
    @Bind(R.id.focus1_ll)
    public LinearLayout focus1Ll;
    @Bind(R.id.focus2_ll)
    public LinearLayout focus2Ll;
    @Bind(R.id.focus3_ll)
    public LinearLayout focus3Ll;
    @Bind(R.id.focus4_ll)
    public LinearLayout focus4Ll;

    public FocusHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
