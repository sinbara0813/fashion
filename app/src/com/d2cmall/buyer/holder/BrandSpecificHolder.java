package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.d2cmall.buyer.R;

/**
 * Created by rookie on 2017/7/29.
 */

public class BrandSpecificHolder extends RecyclerView.ViewHolder  {
    public ImageView imageView;
    public BrandSpecificHolder(View itemView) {
        super(itemView);
        imageView= (ImageView) itemView.findViewById(R.id.imageView);
    }
}
