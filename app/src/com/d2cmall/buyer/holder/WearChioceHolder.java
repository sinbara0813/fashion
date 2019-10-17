package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.RoundLayout;
import com.d2cmall.buyer.widget.RoundedImageView;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayer;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/11/19.
 * Description : WardrobeTopHolder
 */

public class WearChioceHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.iv_pic)
    public RoundedImageView ivPic;
    @Bind(R.id.tv_desc)
    public TextView tvDesc;
    @Bind(R.id.nice_video_player)
    public NiceVideoPlayer niceVideoPlayer;
    @Bind(R.id.rl_video)
    public RoundLayout roundVideo;

    public WearChioceHolder(View itemView,int itemWidth) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        ViewGroup.LayoutParams layoutParams = ivPic.getLayoutParams();
        layoutParams.width = itemWidth;
        layoutParams.height = (int) (layoutParams.width * ((float) 238 / 168));
        ivPic.setLayoutParams(layoutParams);
        ViewGroup.LayoutParams layoutParams1 = niceVideoPlayer.getLayoutParams();
        layoutParams1.width = itemWidth;
        layoutParams1.height = (int) (layoutParams1.width * ((float) 238 / 168));
        niceVideoPlayer.setLayoutParams(layoutParams1);
    }
}
