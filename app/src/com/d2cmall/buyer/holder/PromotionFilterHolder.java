package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/27.
 * Description : PromotionFilterHolder
 */

public class PromotionFilterHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_comprehensive)
    public TextView tvComprehensive;
    @Bind(R.id.tv_near)
    public TextView tvNear;
    @Bind(R.id.tv_hot)
    public TextView tvHot;
    @Bind(R.id.tv_price)
    public TextView tvPrice;
    @Bind(R.id.iv_price_state)
    public ImageView ivPriceState;
    @Bind(R.id.tv_screen)
    public TextView tvScreen;

    public PromotionFilterHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
