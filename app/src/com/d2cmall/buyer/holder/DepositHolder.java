package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/9/6.
 */

public class DepositHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tv_business_type)
    public TextView tvBusinessType;
    @Bind(R.id.tv_remark)
    public TextView tvRemark;
    @Bind(R.id.tv_date)
    public TextView tvDate;
    @Bind(R.id.tv_money)
    public TextView tvMoney;
    @Bind(R.id.line_layout)
    public View lineLayout;

    public DepositHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
