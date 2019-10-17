package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2018/4/17.
 */

public class PerfomanceItemHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_month_number)
    public TextView tvMonthNumber;
    @Bind(R.id.tv_month_eng)
    public TextView tvMonthEng;
    @Bind(R.id.ll_title)
    public LinearLayout llTitle;
    @Bind(R.id.tv_sell)
    public TextView tvSell;
    @Bind(R.id.ll_sell)
    public LinearLayout llSell;
    @Bind(R.id.tv_sell_earning)
    public TextView tvSellEarning;
    @Bind(R.id.ll_sell_earning)
    public LinearLayout llSellEarning;
    @Bind(R.id.tv_order)
    public TextView tvOrder;
    @Bind(R.id.ll_order)
    public LinearLayout llOrder;
    @Bind(R.id.ll_content)
    public LinearLayout llContent;

    public PerfomanceItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
