package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2018/5/18.
 */

public class AddCartImageItemHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.image)
    public ImageView image;

    public AddCartImageItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
