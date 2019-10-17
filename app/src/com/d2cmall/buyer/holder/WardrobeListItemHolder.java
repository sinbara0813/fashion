package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/11/21.
 * Description : WardrobeListItemHolder
 */

public class WardrobeListItemHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.iv_image)
    public ImageView ivImage;

    public WardrobeListItemHolder(View itemView,int itemWidth) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(-2, -2);
        rl.width = itemWidth;
        rl.height = (int) (rl.width * ((float) 238 / 168));
        ivImage.setLayoutParams(rl);
    }
}
