package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/9/11.
 */

public class RelationProductHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.iv_product)
    public ImageView ivProduct;
    @Bind(R.id.tv_product_name)
    public TextView tvProductName;
    @Bind(R.id.tv_price)
    public TextView tvPrice;
    @Bind(R.id.iv_select)
    public ImageView ivSelect;

    public RelationProductHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
