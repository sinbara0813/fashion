package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2018/5/3.
 */

public class FlashRobBrandHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_brand_name)
    public TextView tvBrandName;
    @Bind(R.id.iv_button)
    public ImageView ivButton;
    @Bind(R.id.recycle_view)
    public RecyclerView recycleView;
    @Bind(R.id.iv_top)
    public ImageView ivTop;

    public FlashRobBrandHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
