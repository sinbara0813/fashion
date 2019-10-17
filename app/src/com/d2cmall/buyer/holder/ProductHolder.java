package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.ScreenUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/8/4.
 */

public class ProductHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.product_image)
    public ImageView productImage;
    @Bind(R.id.year_tag)
    public ImageView yearTag;
    @Bind(R.id.tv_global_tag)
    public TextView tvGlobalTag;
    @Bind(R.id.tv_discount_price)
    public TextView tvDiscountPrice;
    @Bind(R.id.tv_discount_name)
    public TextView tvDiscountName;
    @Bind(R.id.rl_discount)
    public LinearLayout rlDiscount;
    @Bind(R.id.view_no_store)
    public View viewNoStore;
    @Bind(R.id.tv_no_store)
    public TextView tvNoStore;
    @Bind(R.id.product_name)
    public TextView productName;
    @Bind(R.id.product_price)
    public TextView productPrice;
    @Bind(R.id.tag_ll)
    public LinearLayout tagLl;
    @Bind(R.id.iv_find)
    public ImageView ivFind;
    @Bind(R.id.view_find)
    public RelativeLayout viewFind;
    @Bind(R.id.root_rl)
    public RelativeLayout rootRl;

    public ProductHolder(View itemView, int itemWith) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        RelativeLayout.LayoutParams ll = (RelativeLayout.LayoutParams) productImage.getLayoutParams();
        ll.width = itemWith- ScreenUtil.dip2px(1);
        ll.height = ll.width * 1558 / 1000;
    }
}
