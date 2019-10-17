package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayer;
import com.d2cmall.buyer.widget.ninegrid.NineGridView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/9/5.
 */

public class PersonInfoShowItemHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_nick_name)
    public TextView tvNickName;
    @Bind(R.id.tv_create_time)
    public TextView tvCreateTime;
    @Bind(R.id.content_tv)
    public TextView contentTv;
    @Bind(R.id.nineGrid)
    public NineGridView nineGrid;
    @Bind(R.id.nice_video_player)
    public NiceVideoPlayer niceVideoPlayer;
    @Bind(R.id.video_layout)
    public RelativeLayout videoLayout;
    @Bind(R.id.like_num)
    public TextView likeNum;
    @Bind(R.id.comment_num)
    public TextView commentNum;
    @Bind(R.id.info_tv)
    public TextView infoTv;

    public PersonInfoShowItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
