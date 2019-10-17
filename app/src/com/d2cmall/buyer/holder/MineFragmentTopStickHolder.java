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
 * Created by Administrator on 2018/9/7.
 * Description : MineFragmentTopHolder
 */

public class MineFragmentTopStickHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.iv_setting)
    public ImageView ivSetting;
    @Bind(R.id.tv_red_point)
    public TextView tvRedPoint;
    @Bind(R.id.iv_cart)
    public  ImageView ivCart;
    @Bind(R.id.top_title)
    public RelativeLayout topTitle;

    public MineFragmentTopStickHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
