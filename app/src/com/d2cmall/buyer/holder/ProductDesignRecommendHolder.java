package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.ScreenUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/8/8 13:07
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class ProductDesignRecommendHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.image)
    public ImageView image;
    @Bind(R.id.price)
    public TextView price;

    public ProductDesignRecommendHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        RelativeLayout.LayoutParams ll = (RelativeLayout.LayoutParams) image.getLayoutParams();
        ll.width = ScreenUtil.dip2px(94);
        ll.height = ll.width * 1558 / 1000;
    }
}
