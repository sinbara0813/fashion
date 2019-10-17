package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/4/18 14:15
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class VisitorItemHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.head_iv)
    public ImageView headIv;
    @Bind(R.id.name)
    public TextView name;
    @Bind(R.id.target)
    public TextView target;
    @Bind(R.id.time)
    public TextView time;
    @Bind(R.id.content_rl)
    public RelativeLayout contentRl;
    @Bind(R.id.tag)
    public TextView tag;

    public VisitorItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
