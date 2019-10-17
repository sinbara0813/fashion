package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/9/5.
 */

public class PersonShopHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.image)
    public ImageView image;

    public PersonShopHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
