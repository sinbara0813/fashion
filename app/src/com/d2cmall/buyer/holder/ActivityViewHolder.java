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
 * desc:
 * Date: 2017/09/25 17:09
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class ActivityViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.text_view)
    public TextView mTextView;
    @Bind(R.id.image_view)
    public ImageView mImageView;
    @Bind(R.id.detailtext_view)
    public TextView mDetailtextView;

    public ActivityViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

}
