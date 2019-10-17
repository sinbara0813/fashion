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

public class BrandPromotionArticleHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.image_view)
    public ImageView mImageView;

    public BrandPromotionArticleHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

}
