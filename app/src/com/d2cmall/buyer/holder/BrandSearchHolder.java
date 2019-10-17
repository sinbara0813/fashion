package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/12/12.
 */

public class BrandSearchHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.item_image)
    public ImageView itemImage;
    @Bind(R.id.image_brand_logo)
    public ImageView imageBrandLogo;
    @Bind(R.id.name_tv)
    public TextView nameTv;
    @Bind(R.id.count_tv)
    public TextView countTv;

    public BrandSearchHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
