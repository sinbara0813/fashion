package com.d2cmall.buyer.binder;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bumptech.glide.RequestManager;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseViewBinder;
import com.d2cmall.buyer.bean.VideoSource;
import com.d2cmall.buyer.holder.VideoBannerHolder;
import com.d2cmall.buyer.listener.OnItemClickListener;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.VideoBanner1;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager;
import com.flyco.banner.anim.BaseAnimator;
import com.flyco.banner.anim.select.ZoomInEnter;
import com.flyco.banner.widget.Banner.base.BaseBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/23 19:27
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class VideoBannerBinder implements BaseViewBinder<VideoBannerHolder> {

    private Context mContext;
    private RequestManager requestManager;
    private int loadingId;
    private List<VideoSource> source;
    private Class<? extends ViewPager.PageTransformer> transformerClass;
    private Class<? extends BaseAnimator> selectAnimClass;
    private boolean needSetBottom;
    private OnItemClickListener onItemClickListener;
    private boolean showNewYear;
    private VideoBanner1 banner;
    private float scrolledMargin;
    private float multiplier=(float)1.0;
    private double collagePrice;
    private int collageId;
    private long productId;
    private String contentStr;

    public VideoBannerBinder(Context context, int loadingId, List<String> list) {
        this.mContext = context;
        this.loadingId = loadingId;
        final int size = list.size();
        source = new ArrayList<VideoSource>();
        for (int i = 0; i < size; i++) {
            VideoSource videoSource = new VideoSource();
            videoSource.picUrl = list.get(i);
            videoSource.loading = loadingId;
            source.add(videoSource);
        }
    }

    public VideoBannerBinder(Context context, RequestManager requestManager, int loadingId, List<String> list) {
        this.mContext = context;
        this.requestManager=requestManager;
        this.loadingId = loadingId;
        final int size = list.size();
        source = new ArrayList<VideoSource>();
        for (int i = 0; i < size; i++) {
            VideoSource videoSource = new VideoSource();
            videoSource.picUrl = list.get(i);
            videoSource.loading = loadingId;
            source.add(videoSource);
        }
    }

    @Override
    public VideoBannerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_video_banner, parent,false);
        return new VideoBannerHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoBannerHolder bannerHolder, int position) {
        banner=bannerHolder.banner;
        if (needSetBottom){
            bannerHolder.banner.setNewYear(showNewYear).setBottomPadding(10).setIndicatorType(2).setSource(source)
                    .setRequestManager(requestManager)
                    .setCollageId(collageId)
                    .setCollagePrice(collagePrice)
                    .setContentStr(contentStr)
                    .setProductId(productId)
                    .setSelectAnimClass(ZoomInEnter.class)
                    .setSelectAnimClass(selectAnimClass).setTransformerClass(transformerClass).setAutoScrollEnable(false).startScroll();
            bannerHolder.banner.setOnItemClickL(new BaseBanner.OnItemClickL() {
                @Override
                public void onItemClick(int position) {
                    if (onItemClickListener!=null){
                        onItemClickListener.itemClick(bannerHolder.banner,position);
                    }
                }
            });
            bannerHolder.banner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (!Util.isEmpty(source.get(position).videoUrl) && positionOffset > 0.8) {
                        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                    }
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }else {
            bannerHolder.banner.setSource(source).setRequestManager(requestManager)
                    .setSelectAnimClass(selectAnimClass).setTransformerClass(transformerClass).startScroll();
        }
    }

    @Override
    public void onBindViewHolderWithOffer(VideoBannerHolder productBannerHolder, int position, int offsetTotal) {

    }

    /**
     * 轮播图切换动画
     * @param transformerClass
     */
    public void setTransformerClass(Class<? extends ViewPager.PageTransformer> transformerClass) {
        this.transformerClass = transformerClass;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setVideoUrl(String videoUrl) {
        if (!Util.isEmpty(videoUrl)&&source!=null&&source.size()>0){
            source.get(0).videoUrl=videoUrl;
        }
    }

    /**
     * 指示器动画
     * @param selectAnimClass
     */
    public void setSelectAnimClass(Class<? extends BaseAnimator> selectAnimClass) {
        this.selectAnimClass = selectAnimClass;
    }

    public void needSetBottom(boolean is){
        needSetBottom=is;
    }

    public void setShowNewYear(boolean showNewYear) {
        this.showNewYear = showNewYear;
    }

    public void setTranslationY(float distance){
        if (banner!=null){
            if (distance >= banner.getHeight()) {
                distance = banner.getHeight();
            }
            VirtualLayoutManager.LayoutParams layoutParams = (VirtualLayoutManager.LayoutParams) banner.getLayoutParams();
            //重新赋值给底部边距
            scrolledMargin = -distance + scrolledMargin;
            if (scrolledMargin > 0) {
                scrolledMargin = 0;
            }
            layoutParams.setMargins(0, 0, 0, (int) (multiplier * scrolledMargin));
            banner.setLayoutParams(layoutParams);
        }
    }

    public void setCollagePrice(double collagePrice) {
        this.collagePrice = collagePrice;
    }

    public void setCollageId(int collageId) {
        this.collageId = collageId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setContentStr(String contentStr) {
        this.contentStr = contentStr;
    }
}
