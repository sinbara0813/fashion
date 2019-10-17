package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

/**
 * Created by rookie on 2017/7/28.
 */

public class CategoryDetailHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public TextView text;
    public CategoryDetailHolder(View itemView) {
        super(itemView);
        image= (ImageView) itemView.findViewById(R.id.image);
        text= (TextView) itemView.findViewById(R.id.text);

    }
}
