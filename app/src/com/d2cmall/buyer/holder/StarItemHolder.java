package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/8/31.
 */

public class StarItemHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.imageView)
    public ImageView imageView;
    @Bind(R.id.tv_index)
    public TextView tvIndex;
    @Bind(R.id.tv_total)
    public TextView tvTotal;
    @Bind(R.id.ll_text)
    public LinearLayout llText;
    @Bind(R.id.imageCover)
    public ImageView imageCover;

    public StarItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
