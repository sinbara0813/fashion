package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/8/22.
 */

public class ScreenSecondHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tv_cash)
    public TextView tvCash;
    @Bind(R.id.iv_cash_mark)
    public ImageView ivCashMark;
    @Bind(R.id.tv_pre_sell)
    public TextView tvPreSell;
    @Bind(R.id.iv_pre_sell_mark)
    public ImageView ivPreSellMark;
    @Bind(R.id.tv_made)
    public TextView tvMade;
    @Bind(R.id.iv_made_mark)
    public ImageView ivMadeMark;
    public boolean isCashSelected,isPreSellSelected,isMadeSelected;

    public ScreenSecondHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
