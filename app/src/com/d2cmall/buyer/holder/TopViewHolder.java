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

public class TopViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tv_sale)
    public TextView tvSale;
    @Bind(R.id.iv_sale_mark)
    public ImageView ivSaleMark;
    @Bind(R.id.tv_try)
    public TextView tvTry;
    @Bind(R.id.iv_try_mark)
    public ImageView ivTryMark;
    @Bind(R.id.tv_no_reason_change)
    public TextView tvNoReasonChange;
    @Bind(R.id.iv_no_reason_change_mark)
    public ImageView ivNoReasonChangeMark;
    public boolean isSaleSelected,isTrySelected,isNoReasonSelected;

    public TopViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
