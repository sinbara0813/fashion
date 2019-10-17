package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2018/5/2.
 */

public class FlashFirstProductHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.iv_product)
    public ImageView ivProduct;
    @Bind(R.id.tv_name)
    public TextView tvName;
    @Bind(R.id.tv_description)
    public TextView tvDescription;
    @Bind(R.id.iv_button)
    public ImageView ivButton;
    @Bind(R.id.tv_min_price)
    public TextView tvMinPrice;
    @Bind(R.id.tv_high_price)
    public TextView tvHighPrice;
    @Bind(R.id.tv_order_promotion)
    public TextView tvOrderPromotion;

    public FlashFirstProductHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
