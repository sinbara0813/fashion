package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.flowLayout.TagFlowLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 组合商品弹窗holder
 * Name: d2c
 * Anthor: lwj
 * Date: 2017/7/28 17:13
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CombProductPopItemHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.iv_comb_top)
    public ImageView ivComb;
    @Bind(R.id.tv_comb_top_name)
    public TextView tvCombName;
    @Bind(R.id.tv_comb_top_price)
    public TextView tvCombPrice;
    @Bind(R.id.tv_comb_old_price)
    public  TextView tvCombOldPrice;
    @Bind(R.id.color_tv)
    public TextView colorTv;
    @Bind(R.id.color_layout)
    public TagFlowLayout colorLayout;
    @Bind(R.id.size_tv)
    public TextView sizeTv;
    @Bind(R.id.size_layout)
    public TagFlowLayout sizeLayout;
    @Bind(R.id.progressBar)
    public ProgressBar progressBar;
    @Bind(R.id.tv_comb_subtitle)
    public TextView tvCombSubtitle;

    public CombProductPopItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}
