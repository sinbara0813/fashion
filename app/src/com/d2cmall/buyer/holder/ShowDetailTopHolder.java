package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.RoundedImageView;
import com.d2cmall.buyer.widget.ShowPopImageView;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayer;
import com.d2cmall.buyer.widget.ninegrid.NineGridView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/8/24.
 */

public class ShowDetailTopHolder extends RecyclerView.ViewHolder {


    @Bind(R.id.iv_user_head_pic)
    public RoundedImageView ivUserHeadPic;
    @Bind(R.id.tv_user_name)
    public TextView tvUserName;
    @Bind(R.id.tv_post_time)
    public TextView tvPostTime;
    //开启消息推送行为节点
    @Bind(R.id.iv_user_focus)
    public ShowPopImageView ivUserFocus;
    @Bind(R.id.content_tv)
    public TextView contentTv;
    @Bind(R.id.nineGrid)
    public NineGridView nineGrid;
    @Bind(R.id.nice_video_player)
    public NiceVideoPlayer niceVideoPlayer;
    @Bind(R.id.video_layout)
    public RelativeLayout videoLayout;
    @Bind(R.id.tv_date)
    public TextView tvDate;
    @Bind(R.id.tv_location)
    public TextView tvLocation;
    @Bind(R.id.tv_post_address)
    public TextView tvPostAddress;
    @Bind(R.id.ll_position)
    public LinearLayout llPosition;
    @Bind(R.id.focus_pics_layout)
    public LinearLayout focusPicsLayout;
    @Bind(R.id.tv_all_focus)
    public TextView tvAllFocus;
    @Bind(R.id.fans_layout)
    public LinearLayout fansLayout;
    @Bind(R.id.recycle_view)
    public RecyclerView recycleView;
    @Bind(R.id.line_layout)
    public View lineLayout;
    @Bind(R.id.ll_product)
    public LinearLayout llRecycler;
    @Bind(R.id.empty_image)
    public ImageView emptyImage;
    @Bind(R.id.ll_empty_image)
    public LinearLayout llEmptyImage;
    @Bind(R.id.tv_text)
    TextView tvText;

    public ShowDetailTopHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
