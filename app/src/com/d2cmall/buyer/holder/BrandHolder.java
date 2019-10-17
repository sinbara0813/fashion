package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/25 16:55
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class BrandHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.text_view)
    public TextView mTextView;
    @Bind(R.id.linear_layout)
    public LinearLayout mLinearLayout;
    @Bind(R.id.text_seemore)
    public TextView mTextSeemore;
    @Bind(R.id.message_brand_arrow)
    public ImageView mMessageBrandArrow;

    public BrandHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

}
