package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.HotTopicActivity;
import com.d2cmall.buyer.activity.TopicDetailActivity;
import com.d2cmall.buyer.bean.FashionShowHeadBean;
import com.d2cmall.buyer.holder.BannerHolder;
import com.d2cmall.buyer.holder.HotTopicsHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.flyco.banner.widget.Banner.base.BaseBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:Created by sinbara on 2019/1/14.
 * 邮箱:hrb940258169@163.com
 */

public class FocusHeadAdapter extends DelegateAdapter.Adapter  {

    private Context mContext;
    private FashionShowHeadBean headBean;

    public FocusHeadAdapter(Context context){
        mContext=context;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper=new LinearLayoutHelper();
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        switch (viewType){
            case 1:
                view= LayoutInflater.from(mContext).inflate(R.layout.layout_banner,parent,false);
                return new BannerHolder(view);
            case 2:
                view=LayoutInflater.from(mContext).inflate(R.layout.layout_hot_tocpic,parent,false);
                return new HotTopicsHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==1){
            BannerHolder bannerHolder=(BannerHolder) holder;
            List<String> bannerItems = new ArrayList<>();
            if (headBean.getData().getContent().size()>0){
                int size=headBean.getData().getContent().get(0).getSectionValues().size();
                for (int i = 0; i < size; i++) {
                    String imageUrl=headBean.getData().getContent().get(0).getSectionValues().get(i).getFrontPic();
                    bannerItems.add(imageUrl);
                }
                int height= ScreenUtil.dip2px(128);
                VirtualLayoutManager.LayoutParams Ll=new VirtualLayoutManager.LayoutParams(-1,-2);
                Ll.mAspectRatio=(float)ScreenUtil.getDisplayWidth()/height;
                bannerHolder.itemView.setLayoutParams(Ll);
                bannerHolder.banner.setSource(bannerItems).setIndicatorHeight(0).setAutoScrollEnable(true).startScroll();
                bannerHolder.banner.setOnItemClickL(new BaseBanner.OnItemClickL() {
                    @Override
                    public void onItemClick(int position) {
                        Util.urlAction(mContext,headBean.getData().getContent().get(0).getSectionValues().get(position).getUrl());
                    }
                });
            }
        }else {
            HotTopicsHolder hotTopicsHolder=(HotTopicsHolder) holder;
            hotTopicsHolder.allTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, HotTopicActivity.class);
                    mContext.startActivity(intent);
                }
            });
            if (headBean.getData().getTopics()!=null&&headBean.getData().getTopics().size()>0){
                int size=headBean.getData().getTopics().size();
                for (int i=0;i<size;i++){
                    if (i==0){
                        hotTopicsHolder.flowRl1.setVisibility(View.VISIBLE);
                        final FashionShowHeadBean.DataBean.TopicsBean data=headBean.getData().getTopics().get(i);
                        hotTopicsHolder.flowRl1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(mContext, TopicDetailActivity.class);
                                intent.putExtra("id",(long)data.getId());
                                mContext.startActivity(intent);
                            }
                        });
                        UniversalImageLoader.displayImage(mContext,headBean.getData().getTopics().get(i).getPic(),hotTopicsHolder.flow1);
                        hotTopicsHolder.flowTv1.setText(headBean.getData().getTopics().get(i).getTitle());
                    }else if (i==1){
                        hotTopicsHolder.flowRl2.setVisibility(View.VISIBLE);
                        final FashionShowHeadBean.DataBean.TopicsBean data=headBean.getData().getTopics().get(i);
                        hotTopicsHolder.flowRl2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(mContext, TopicDetailActivity.class);
                                intent.putExtra("id",(long)data.getId());
                                mContext.startActivity(intent);
                            }
                        });
                        UniversalImageLoader.displayImage(mContext,headBean.getData().getTopics().get(i).getPic(),hotTopicsHolder.flow2);
                        hotTopicsHolder.flowTv2.setText(headBean.getData().getTopics().get(i).getTitle());
                    }else if (i==2){
                        hotTopicsHolder.flowRl3.setVisibility(View.VISIBLE);
                        final FashionShowHeadBean.DataBean.TopicsBean data=headBean.getData().getTopics().get(i);
                        hotTopicsHolder.flowRl3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(mContext, TopicDetailActivity.class);
                                intent.putExtra("id",(long)data.getId());
                                mContext.startActivity(intent);
                            }
                        });
                        UniversalImageLoader.displayImage(mContext,headBean.getData().getTopics().get(i).getPic(),hotTopicsHolder.flow3);
                        hotTopicsHolder.flowTv3.setText(headBean.getData().getTopics().get(i).getTitle());
                    }
                }
            }
        }
    }

    public void setHeadBean(FashionShowHeadBean headBean) {
        this.headBean = headBean;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return 1;
        }else {
            return 2;
        }
    }
}
