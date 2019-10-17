package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayer;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者:Created by sinbara on 2018/11/21.
 * 邮箱:hrb940258169@163.com
 */

public class ImageItemHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.content_iv)
    public ImageView contentIv;
    @Bind(R.id.back_iv)
    public ImageView backIv;
    @Bind(R.id.more_iv)
    public ImageView moreIv;
    @Bind(R.id.introduce_tv)
    public TextView introduceTv;
    @Bind(R.id.date_iv)
    public TextView dateIv;
    @Bind(R.id.city_tv)
    public TextView cityTv;
    @Bind(R.id.page_number_tv)
    public TextView pageNumberTv;
    @Bind(R.id.bg)
    public View bg;
    @Bind(R.id.pb)
    public ProgressBar pb;
    @Bind(R.id.nice_video_player)
    public NiceVideoPlayer niceVideoPlayer;


    public ImageItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
