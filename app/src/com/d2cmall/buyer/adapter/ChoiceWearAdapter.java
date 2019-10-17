package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.LooksDetailActivity;
import com.d2cmall.buyer.bean.MyWearCollocationBean;
import com.d2cmall.buyer.holder.WearChioceHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.nicevideo.TxVideoPlayerController;

import java.util.List;


/**
 * Fixme
 * Author: LWJ
 * desc:   我的穿搭-精选穿搭
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class ChoiceWearAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private int itemWidth;
    private List<MyWearCollocationBean.DataBean.MyWardrobeCollocationsBean.ListBean> recommendList;
    public ChoiceWearAdapter(Context context,int itemWidth,List<MyWearCollocationBean.DataBean.MyWardrobeCollocationsBean.ListBean> recommendList) {
        mContext = context;
        this.recommendList=recommendList;
        this.itemWidth=itemWidth;
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setAutoExpand(false);
        gridLayoutHelper.setPaddingTop(ScreenUtil.dip2px(16));
        gridLayoutHelper.setPaddingLeft(ScreenUtil.dip2px(8));
        return gridLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_wear_item, parent, false);
        return new WearChioceHolder(itemView,itemWidth);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WearChioceHolder wearChioceHolder = (WearChioceHolder) holder;
        MyWearCollocationBean.DataBean.MyWardrobeCollocationsBean.ListBean listBean = recommendList.get(position);
        if(listBean.getPics()!=null && listBean.getPics().size()>0){
            UniversalImageLoader.displayImage(mContext,listBean.getPics().get(0),wearChioceHolder.ivPic,R.mipmap.ic_logo_empty5);
        }else{
            UniversalImageLoader.displayImage(mContext,wearChioceHolder.ivPic,R.mipmap.ic_logo_empty5);
        }
        if(!Util.isEmpty(listBean.getVideo())){
            wearChioceHolder.ivPic.setVisibility(View.GONE);
            wearChioceHolder.niceVideoPlayer.setVisibility(View.VISIBLE);
            wearChioceHolder.roundVideo.setVisibility(View.VISIBLE);
            String videoUrl = listBean.getVideo();
            if (!videoUrl.startsWith("http")) {
                videoUrl = Constants.IMG_HOST + videoUrl;
            }
            TxVideoPlayerController txVideoPlayerController = new TxVideoPlayerController(mContext);
            txVideoPlayerController.setBanFullScreen(true);
            wearChioceHolder.niceVideoPlayer.setController(txVideoPlayerController);
            if (listBean.getPics() != null && listBean.getPics().size() > 0) {
                UniversalImageLoader.displayImage(mContext, Util.getD2cPicUrl(listBean.getPics().get(0)), txVideoPlayerController.getImage());
            }
            wearChioceHolder.niceVideoPlayer.setUp(videoUrl, null);
        }else{
            wearChioceHolder.roundVideo.setVisibility(View.GONE);
            wearChioceHolder.niceVideoPlayer.setVisibility(View.GONE);
            wearChioceHolder.ivPic.setVisibility(View.VISIBLE);
        }
        wearChioceHolder.tvDesc.setText(listBean.getDescription());
        wearChioceHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, LooksDetailActivity.class);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(listBean.getTransactionTime().substring(0, listBean.getTransactionTime().indexOf(" ") + 1));
                stringBuilder.append("00:00:00");
                intent.putExtra("date",stringBuilder.toString());
                intent.putExtra("id",listBean.getId());
                intent.putExtra("memberId",listBean.getMemberId());
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return recommendList==null?0:recommendList.size();
    }





}
