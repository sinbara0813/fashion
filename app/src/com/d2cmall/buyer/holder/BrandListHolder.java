package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.ShowPopImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/7/31.
 */

public class BrandListHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.img_pic)
    public ImageView imgPic;
    @Bind(R.id.tv_name)
    public TextView tvName;
    //开启消息推送行为节点
    @Bind(R.id.img_focus)
    public ShowPopImageView imgFocus;
    @Bind(R.id.img_avatar)
    public ImageView imgAvatar;
    @Bind(R.id.tv_content)
    public TextView tvContent;
    @Bind(R.id.recycler_view)
    public RecyclerView recyclerView;

    public BrandListHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

    }
}
