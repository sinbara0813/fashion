package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/2/2.
 * Description : PartnerSaleSchoolHolder
 */

public class PartnerSaleSchoolHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.image)
    public ImageView image;
    @Bind(R.id.tv_title)
    public TextView tvTitle;
    @Bind(R.id.tv_date)
    public TextView tvDate;
    @Bind(R.id.tv_type)
    public TextView tvType;

    public PartnerSaleSchoolHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

}
