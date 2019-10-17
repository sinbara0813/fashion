package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/8/17.
 */

public class CouponHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_price)
    public TextView tvPrice;
    @Bind(R.id.tv_discount)
    public TextView tvDiscount;
    @Bind(R.id.tv_limit)
    public TextView tvLimit;
    @Bind(R.id.tv_title)
    public TextView tvTitle;
    @Bind(R.id.tv_date)
    public TextView tvDate;
    @Bind(R.id.img_arrow)
    public ImageView imgArrow;
    @Bind(R.id.tv_get)
    public TextView tvGet;
    @Bind(R.id.iv_state)
    public ImageView ivState;
    @Bind(R.id.rl_main)
    public RelativeLayout rlMain;
    @Bind(R.id.tv_describe)
    public TextView tvDescribe;
    @Bind(R.id.ll_remind)
    public LinearLayout llRemind;
    @Bind(R.id.view_bottom)
    public View viewBottom;
//    @Bind(R.id.tv_price)
//    public TextView tvPrice;
//    @Bind(R.id.tv_use_condition)
//    public TextView tvUseCondition;
//    @Bind(R.id.tv_coupon_name)
//    public TextView tvCouponName;
//    @Bind(R.id.tv_limit)
//    public TextView tvLimit;
//    @Bind(R.id.iv_open)
//    public ImageView ivOpen;
//    @Bind(R.id.tv_get)
//    public TextView tvGet;
//    @Bind(R.id.iv_state)
//    public ImageView ivState;
//    @Bind(R.id.tv_remind)
//    public TextView tvRemind;
//    @Bind(R.id.ll_remind)
//    public LinearLayout llRemind;
//    @Bind(R.id.rl_main)
//    public RelativeLayout rlMain;
//    @Bind(R.id.view_bottom)
//    public View viewBottom;
//    public boolean isOpen=false;

    public CouponHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
