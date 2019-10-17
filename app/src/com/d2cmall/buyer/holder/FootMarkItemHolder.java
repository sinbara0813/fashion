package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.swipeLayout.SwipeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2018/3/7.
 */

public class FootMarkItemHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.iv_product)
    public ImageView ivProduct;
    @Bind(R.id.tv_product_name)
    public TextView tvProductName;
    @Bind(R.id.tv_price)
    public TextView tvPrice;
    @Bind(R.id.rl_item)
    public RelativeLayout rl_item;
    @Bind(R.id.tv_high_price)
    public TextView tvHighPrice;
    @Bind(R.id.swipe_menu)
    public SwipeLayout sideslipDelet;
    @Bind(R.id.bt_remove)
    public TextView btnRemove;
    public FootMarkItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
