package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2018/6/21.
 */

public class GroupMarketHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.iv_product)
    public ImageView ivProduct;
    @Bind(R.id.tv_product_name)
    public TextView tvProductName;
    @Bind(R.id.tv_sub_title)
    public TextView tvSubTitle;
    @Bind(R.id.tv_group_num)
    public TextView tvGroupNum;
    @Bind(R.id.tv_price)
    public TextView tvPrice;
    @Bind(R.id.tv_origin_price)
    public TextView tvOriginPrice;
    @Bind(R.id.tv_button)
    public  TextView tvButton;

    public GroupMarketHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
