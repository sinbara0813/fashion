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
 * Created by rookie on 2018/3/21.
 */

public class FindSimilarTopHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.iv_product_top)
    public ImageView ivProductTop;
    @Bind(R.id.tv_name_product)
    public TextView tvNameProduct;
    @Bind(R.id.tv_min_price)
    public TextView tvMinPrice;
    @Bind(R.id.tv_high_price)
    public TextView tvHighPrice;
    @Bind(R.id.product_rl)
    public RelativeLayout productRl;
    @Bind(R.id.tv_tag)
    public TextView tvTag;

    public FindSimilarTopHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
