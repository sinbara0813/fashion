package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/8/29.
 */

public class PaySuccessTopHolder extends ViewHolder {
    @Bind(R.id.iv_success)
    public ImageView ivSuccess;
    @Bind(R.id.tv_success)
    public TextView tvSuccess;
    @Bind(R.id.tv_explain)
    public TextView tvExplain;
    @Bind(R.id.ll_back_main)
    public LinearLayout llBackMain;
    @Bind(R.id.ll_look_order)
    public LinearLayout llLookOrder;
    @Bind(R.id.view_divider)
    public View viewDivider;
    @Bind(R.id.ad_image)
    public ImageView adImage;
    @Bind(R.id.ll)
    public LinearLayout ll;
    @Bind(R.id.ll_divider)
    public LinearLayout llDivider;

    public PaySuccessTopHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
