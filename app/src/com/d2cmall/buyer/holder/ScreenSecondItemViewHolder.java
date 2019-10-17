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

public class ScreenSecondItemViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_text)
    public TextView tvText;
    @Bind(R.id.iv_screen_mark)
    public ImageView ivScreenMark;

    public ScreenSecondItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}


