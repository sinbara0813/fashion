package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/9/12.
 */

public class ShowRelationHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.iv_product)
    public ImageView ivProduct;
    @Bind(R.id.tv_designer)
    public TextView tvDesigner;
    @Bind(R.id.tv_product_name)
    public TextView tvProductName;
    @Bind(R.id.tv_price)
    public TextView tvPrice;
    @Bind(R.id.view_no_store)
    public View view_no_store;
    @Bind(R.id.tv_no_store)
    public TextView tv_no_store;

    public ShowRelationHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
