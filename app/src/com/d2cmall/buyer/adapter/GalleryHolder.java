package com.d2cmall.buyer.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.SquareImageView;


/**
 * Created by Administrator on 2017/3/14.
 */

public class GalleryHolder extends RecyclerView.ViewHolder {
    public SquareImageView thumbIv;
    public ImageView appCompatCheckBox;
    public TextView tvVideoDuration;
    public ImageView ivVideoFlag;

    public GalleryHolder(View itemView) {
        super(itemView);
        itemView.setClickable(true);
        thumbIv = (SquareImageView) itemView.findViewById(R.id.iv_thumb);
        thumbIv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        appCompatCheckBox = (ImageView) itemView.findViewById(R.id.select_tag);
        appCompatCheckBox.setClickable(false);
        tvVideoDuration = (TextView) itemView.findViewById(R.id.tv_video_duration);
        ivVideoFlag = (ImageView) itemView.findViewById(R.id.iv_video_flag);
        thumbIv.setShade(new ColorDrawable(Color.parseColor("#92000000")));
    }
}