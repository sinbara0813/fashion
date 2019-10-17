package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.HotTopicActivity;
import com.d2cmall.buyer.activity.RankActivity;
import com.d2cmall.buyer.activity.SearchAdressActivity;
import com.d2cmall.buyer.activity.TopicDetailActivity;
import com.d2cmall.buyer.bean.AddressEntity;
import com.d2cmall.buyer.bean.FashionShowHeadBean;
import com.d2cmall.buyer.bean.TopicBean;
import com.d2cmall.buyer.fragment.FashionSubFragment;
import com.d2cmall.buyer.holder.BannerHolder;
import com.d2cmall.buyer.holder.HotTopicsHolder;
import com.d2cmall.buyer.holder.ProgressHolder;
import com.d2cmall.buyer.holder.SuperGatherHolder;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.InitializeService;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.flyco.banner.widget.Banner.base.BaseBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/31 15:13
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FashionHeadAdapter extends DelegateAdapter.Adapter {

    private Context mContext;
    private List<View> views=new ArrayList<>();
    private FashionShowHeadBean headBean;
    public int progress;
    public String url;
    public boolean showProgress;
    public int type; //0成功 1 失败
    public long duration;
    public String content;
    public TopicBean.DataBean.TopicsBean.ListBean topicBean;
    public AddressEntity addressEntity;
    private ProgressBar progressBar;
    private TextView progressTv;

    public FashionHeadAdapter(Context context){
        this.mContext=context;
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
                view=LayoutInflater.from(mContext).inflate(R.layout.layout_banner,parent,false);
                return new BannerHolder(view);
            case 2:
                view=LayoutInflater.from(mContext).inflate(R.layout.layout_progress_view,parent,false);
                return new ProgressHolder(view);
            case 3:
                view=LayoutInflater.from(mContext).inflate(R.layout.layout_hot_tocpic,parent,false);
                return new HotTopicsHolder(view);
            case 4:
                view=LayoutInflater.from(mContext).inflate(R.layout.layout_super_gather,parent,false);
                return new SuperGatherHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case 1:
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
                break;
            case 2:
                ProgressHolder progressHolder= (ProgressHolder) holder;
                progressHolder.llProgress.setVisibility(View.VISIBLE);
                if (type==1){
                    progressHolder.progressLl.setVisibility(View.GONE);
                    progressHolder.llRelease.setVisibility(View.VISIBLE);
                    progressHolder.ivRelease.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showProgress=false;
                            notifyDataSetChanged();

                            Intent intent=new Intent(mContext, InitializeService.class);
                            intent.putExtra("url",url);
                            intent.putExtra("duration",duration);
                            intent.putExtra("content",content);
                            if (topicBean!=null){
                                intent.putExtra("topicBean",topicBean);
                            }
                            if (addressEntity!=null){
                                intent.putExtra(SearchAdressActivity.ADDRESS,addressEntity);
                            }
                            intent.setAction("upload");
                            mContext.startService(intent);
                        }
                    });
                    progressHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FashionSubFragment.isUpload=false;
                            showProgress=false;
                            notifyDataSetChanged();
                        }
                    });
                }else {
                    progressHolder.llProgress.setVisibility(View.VISIBLE);
                    progressHolder.progressLl.setVisibility(View.VISIBLE);
                    progressHolder.llRelease.setVisibility(View.GONE);
                    progressHolder.uploadProgress.setProgress(progress);
                    progressHolder.tvProgress.setText(progress+"%");
                    if (progress==100){
                        progressHolder.tvProgress.setText("上传成功");
                        //PictureUtils.deleteFile(new File(VideoEditActivity.mFinalPath));
                    }
                    progressBar=progressHolder.uploadProgress;
                    progressTv=progressHolder.tvProgress;
                    if (progressHolder.image.getVisibility()==View.GONE||(progressHolder.image.getTag()!=null&&!progressHolder.image.getTag().equals(url))){
                        Bitmap bitmap= ThumbnailUtils.createVideoThumbnail(url, MediaStore.Video.Thumbnails.MINI_KIND);
                        int bitmapWidth=bitmap.getWidth();
                        int bitmapHeight=bitmap.getHeight();
                        int scanWidth= ScreenUtil.dip2px(20);
                        int scale=Math.min(bitmapWidth/scanWidth,bitmapHeight/scanWidth);
                        Bitmap scalBitmap= BitmapUtils.getScaleBitmap(bitmap,scale,scale);
                        bitmap.recycle();
                        if (scalBitmap!=null){
                            progressHolder.image.setVisibility(View.VISIBLE);
                            progressHolder.image.setBackground(new BitmapDrawable(scalBitmap));
                            progressHolder.image.setTag(url);
                        }
                    }
                }
                break;
            case 3:
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
                break;
            case 4:
                SuperGatherHolder superGatherHolder=(SuperGatherHolder) holder;
                setView();
                superGatherHolder.upMarquee.removeAllViews();
                superGatherHolder.upMarquee.setViews(views);
                break;
        }
    }

    private void setView() {
        views.clear();
        if (headBean.getData().getHot().getHotMember().size()>2){
            //设置滚动的单个布局
            RelativeLayout moreView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_ur_item, null);
            //初始化布局的控件
            ImageView tv1 = (ImageView) moreView.findViewById(R.id.tv_rank_name);
            ImageView iv1 = (ImageView) moreView.findViewById(R.id.iv_1);
            UniversalImageLoader.displayRoundImage(mContext,headBean.getData().getHot().getHotMember().get(0).getHeadPic(),iv1,R.mipmap.ic_default_avatar);
            ImageView iv2 = (ImageView) moreView.findViewById(R.id.iv_2);
            UniversalImageLoader.displayRoundImage(mContext,headBean.getData().getHot().getHotMember().get(1).getHeadPic(),iv2,R.mipmap.ic_default_avatar);
            ImageView iv3 = (ImageView) moreView.findViewById(R.id.iv_3);
            UniversalImageLoader.displayRoundImage(mContext,headBean.getData().getHot().getHotMember().get(2).getHeadPic(),iv3,R.mipmap.ic_default_avatar);
            //进行对控件赋值
            tv1.setImageResource(R.mipmap.bg_toplist_user);
            moreView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, RankActivity.class);
                    intent.putExtra("position",0);
                    mContext.startActivity(intent);
                }
            });
            views.add(moreView);
        }
        if (headBean.getData().getHot().getHotDesigner().size()>2){
            //设置滚动的单个布局
            RelativeLayout moreView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_ur_item, null);
            //初始化布局的控件
            ImageView tv1 = (ImageView) moreView.findViewById(R.id.tv_rank_name);
            ImageView iv1 = (ImageView) moreView.findViewById(R.id.iv_1);
            UniversalImageLoader.displayRoundImage(mContext,headBean.getData().getHot().getHotDesigner().get(0).getHeadPic(),iv1,R.mipmap.ic_default_avatar);
            ImageView iv2 = (ImageView) moreView.findViewById(R.id.iv_2);
            UniversalImageLoader.displayRoundImage(mContext,headBean.getData().getHot().getHotDesigner().get(1).getHeadPic(),iv2,R.mipmap.ic_default_avatar);
            ImageView iv3 = (ImageView) moreView.findViewById(R.id.iv_3);
            UniversalImageLoader.displayRoundImage(mContext,headBean.getData().getHot().getHotDesigner().get(2).getHeadPic(),iv3,R.mipmap.ic_default_avatar);
            //进行对控件赋值
            tv1.setImageResource(R.mipmap.bg_toplist_designer);
            moreView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, RankActivity.class);
                    intent.putExtra("position",1);
                    mContext.startActivity(intent);
                }
            });
            views.add(moreView);
        }
        if (headBean.getData().getHot().getHotPic().size()>2){
            //设置滚动的单个布局
            RelativeLayout moreView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_ur_item, null);
            //初始化布局的控件
            ImageView tv1 = (ImageView) moreView.findViewById(R.id.tv_rank_name);
            ImageView iv1 = (ImageView) moreView.findViewById(R.id.iv_1);
            UniversalImageLoader.displayRoundImage(mContext,headBean.getData().getHot().getHotPic().get(0).getMemberHead(),iv1,R.mipmap.ic_default_avatar);
            ImageView iv2 = (ImageView) moreView.findViewById(R.id.iv_2);
            UniversalImageLoader.displayRoundImage(mContext,headBean.getData().getHot().getHotPic().get(1).getMemberHead(),iv2,R.mipmap.ic_default_avatar);
            ImageView iv3 = (ImageView) moreView.findViewById(R.id.iv_3);
            UniversalImageLoader.displayRoundImage(mContext,headBean.getData().getHot().getHotPic().get(2).getMemberHead(),iv3,R.mipmap.ic_default_avatar);
            //进行对控件赋值
            tv1.setImageResource(R.mipmap.bg_toplist_picture);
            moreView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, RankActivity.class);
                    intent.putExtra("position",2);
                    mContext.startActivity(intent);
                }
            });
            views.add(moreView);
        }
        if (headBean.getData().getHot().getHotVideo().size()>2){
            //设置滚动的单个布局
            RelativeLayout moreView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_ur_item, null);
            //初始化布局的控件
            ImageView tv1 = (ImageView) moreView.findViewById(R.id.tv_rank_name);
            ImageView iv1 = (ImageView) moreView.findViewById(R.id.iv_1);
            UniversalImageLoader.displayRoundImage(mContext,headBean.getData().getHot().getHotVideo().get(0).getMemberHead(),iv1,R.mipmap.ic_default_avatar);
            ImageView iv2 = (ImageView) moreView.findViewById(R.id.iv_2);
            UniversalImageLoader.displayRoundImage(mContext,headBean.getData().getHot().getHotVideo().get(1).getMemberHead(),iv2,R.mipmap.ic_default_avatar);
            ImageView iv3 = (ImageView) moreView.findViewById(R.id.iv_3);
            UniversalImageLoader.displayRoundImage(mContext,headBean.getData().getHot().getHotVideo().get(2).getMemberHead(),iv3,R.mipmap.ic_default_avatar);
            //进行对控件赋值
            tv1.setImageResource(R.mipmap.bg_toplist_video);
            moreView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, RankActivity.class);
                    intent.putExtra("position",3);
                    mContext.startActivity(intent);
                }
            });
            views.add(moreView);
        }
        if (headBean.getData().getHot().getHotLive().size()>2){
            //设置滚动的单个布局
            RelativeLayout moreView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_ur_item, null);
            //初始化布局的控件
            ImageView tv1 = (ImageView) moreView.findViewById(R.id.tv_rank_name);
            ImageView iv1 = (ImageView) moreView.findViewById(R.id.iv_1);
            UniversalImageLoader.displayRoundImage(mContext,headBean.getData().getHot().getHotLive().get(0).getHeadPic(),iv1,R.mipmap.ic_default_avatar);
            ImageView iv2 = (ImageView) moreView.findViewById(R.id.iv_2);
            UniversalImageLoader.displayRoundImage(mContext,headBean.getData().getHot().getHotLive().get(1).getHeadPic(),iv2,R.mipmap.ic_default_avatar);
            ImageView iv3 = (ImageView) moreView.findViewById(R.id.iv_3);
            UniversalImageLoader.displayRoundImage(mContext,headBean.getData().getHot().getHotLive().get(2).getHeadPic(),iv3,R.mipmap.ic_default_avatar);
            //进行对控件赋值
            tv1.setImageResource(R.mipmap.bg_toplist_live);
            moreView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, RankActivity.class);
                    intent.putExtra("position",4);
                    mContext.startActivity(intent);
                }
            });
            views.add(moreView);
        }
    }

    public void setData(FashionShowHeadBean headBean){
        this.headBean=headBean;
    }

    @Override
    public int getItemCount() {
        return headBean==null?0:showProgress?3:2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return 1;
        }else if (position==1&&showProgress){
            return 2;
        }else if ((position==1&&!showProgress)||position==2){
            return 4;
        }else {
            return 0;
        }
    }

    public void updateProgress(int progress){
        if (progressBar!=null){
            progressBar.setProgress(progress);
        }
        if (progressTv!=null){
            progressTv.setText(progress+"%");
            if (progress==100){
                progressTv.setText("上传成功");
            }
        }
    }
}
