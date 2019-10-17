package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/29.
 * Description : ReopenStoreHolder
 */

public class ReopenStoreHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_head)
        public ImageView ivHead;
        @Bind(R.id.iv_bg)
        public ImageView ivBg;
        @Bind(R.id.iv_product)
        public ImageView ivProduct;
        @Bind(R.id.tv_product_name)
        public TextView tvProductName;
        @Bind(R.id.tv_product_price)
        public TextView tvProductPrice;
        public @Bind(R.id.btn_buy)
        Button btnBuy;

    public ReopenStoreHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        }
}
