package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fixme
 * Author: LWJ
 * desc:    消息物流通知holder
 * Date: 2017/09/25 17:09
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class LogisticsViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.text_view)
    public TextView mTextView;
    @Bind(R.id.image_view)
    public ImageView mImageView;
    @Bind(R.id.detailtext_view)
    public TextView mDetailtextView;
    @Bind(R.id.image_arrow)
    public ImageView mImageArrow;

    public LogisticsViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

}
