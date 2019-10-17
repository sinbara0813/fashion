package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/11/1.
 */

public class RedPacketItemHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_name_detail)
    public TextView tvNameDetail;
    @Bind(R.id.tv_time)
    public TextView tvTime;
    @Bind(R.id.tv_money)
    public TextView tvMoney;

    public RedPacketItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
