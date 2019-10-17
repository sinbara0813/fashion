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
 * Fixme
 * Author: LWJ
 * desc:    我的收藏holder
 * Date: 2017/09/06 19:42
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class CollectHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.iv_collect_goods_img)
    public ImageView mIvCollectGoodsImg;
    @Bind(R.id.iv_collect_state)
    public ImageView mCollectState;
    @Bind(R.id.tv_collect_author_name)
    public TextView mTvCollectAuthorName;
    @Bind(R.id.tv_collect_goods_desc)
    public TextView mTvCollectGoodsDesc;
    @Bind(R.id.tv_collect_goods_price)
    public TextView mTvCollectGoodsPrice;
    @Bind(R.id.ll_promotion_type)
    public LinearLayout llPromotionType;
    @Bind(R.id.tv_order_promotion)
    public TextView tvOrderPromotion;
    @Bind(R.id.tv_good_promotion)
    public TextView tvGoodPromotion;
    @Bind(R.id.tv_collect_goods_oldPrice)
    public TextView mTvCollectGoodsOldPrice;
    @Bind(R.id.tv_no_store)
    public TextView mTvNoStore;
    @Bind(R.id.view_no_store)
    public View viewNoStore;
    @Bind(R.id.year_tag)
    public ImageView yearTag;
    @Bind(R.id.tv_global_tag)
    public TextView tvGlobal;
    @Bind(R.id.rl_discount)
    public RelativeLayout rlDiscount;
    @Bind(R.id.tv_discount_price)
    public TextView tvDiscountPrice;
    @Bind(R.id.tv_discount_name)
    public TextView tvDiscountName;
    @Bind(R.id.iv_find)
    public ImageView ivFind;
    @Bind(R.id.view_find)
    public RelativeLayout rlFind;

    public CollectHolder(View view, int itemWidth) {
        super(view);
        ButterKnife.bind(this, view);
        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(-2, -2);
        rl.width = itemWidth;
        rl.height = (int) (rl.width * ((float) 1558 / 1000));
        mIvCollectGoodsImg.setLayoutParams(rl);
    }
}
