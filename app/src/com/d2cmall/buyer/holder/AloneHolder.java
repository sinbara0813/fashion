package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者:Created by sinbara on 2019/9/12.
 * 邮箱:hrb940258169@163.com
 */
public class AloneHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tv1)
    public TextView tv1;
    @Bind(R.id.time_tv)
    public TextView timeTv;
    @Bind(R.id.iv)
    public ImageView iv;
    @Bind(R.id.tv_name)
    public TextView tvName;
    @Bind(R.id.promotion_tv)
    public TextView promotionTv;
    @Bind(R.id.num_tv)
    public TextView numTv;
    @Bind(R.id.price_tv)
    public TextView priceTv;
    @Bind(R.id.promotion_price_tv)
    public TextView promotionPriceTv;
    @Bind(R.id.buy_tv)
    public TextView buyTv;

    public AloneHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
