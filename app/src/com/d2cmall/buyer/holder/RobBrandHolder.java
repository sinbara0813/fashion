package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2018/5/4.
 */

public class RobBrandHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.iv_brand_bg)
    public ImageView ivBrandBg;
    @Bind(R.id.iv_brand_img)
    public ImageView ivBrandImg;
    @Bind(R.id.iv_rob_btn)
    public ImageView ivRobBtn;
    @Bind(R.id.rl_top)
    public RelativeLayout rlTop;
    @Bind(R.id.recycle_view)
    public RecyclerView recycleView;
    @Bind(R.id.rl_all)
    public RelativeLayout rlAll;
    @Bind(R.id.tv_top)
    public TextView tvTop;
    @Bind(R.id.iv_bottom_cover)
    public ImageView ivBottomCover;

    public RobBrandHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
