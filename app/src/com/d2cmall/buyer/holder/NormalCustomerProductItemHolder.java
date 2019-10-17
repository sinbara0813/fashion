package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/19.
 * Description : NormalCustomerProductItemHolder
 */

public class NormalCustomerProductItemHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.iv_product)
    public ImageView ivProduct;
    @Bind(R.id.tv_product_name)
    public  TextView tvProductName;
    @Bind(R.id.tv_price)
    public TextView tvPrice;
    @Bind(R.id.tv_old_price)
    public TextView tvOldPrice;
    public NormalCustomerProductItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
