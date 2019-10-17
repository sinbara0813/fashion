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
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/8/31 18:36
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class DCoinShopProductHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.iv_collect_goods_img)
    public ImageView ivCollectGoodsImg;
    @Bind(R.id.view_no_store)
    public  View viewNoStore;
    @Bind(R.id.tv_no_store)
    public TextView tvNoStore;
    @Bind(R.id.tv_product_name)
    public TextView tvProductName;
    @Bind(R.id.tv_product_price)
    public TextView tvProductPrice;

    public DCoinShopProductHolder(View itemView,int itemWidth) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(-2, -2);
        rl.width = itemWidth;
        rl.height = itemWidth;
        ivCollectGoodsImg.setLayoutParams(rl);
    }
}
