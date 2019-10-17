package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2018/1/30.
 */

public class SearchBrandAllHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.logo)
    public ImageView logo;
    @Bind(R.id.tv_name)
    public TextView tvName;
    @Bind(R.id.tv_num)
    public TextView tvNum;

    public SearchBrandAllHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
