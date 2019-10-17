package com.d2cmall.buyer.binder;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseViewBinder;
import com.d2cmall.buyer.holder.BannerHolder;
import com.d2cmall.buyer.listener.OnItemClickListener;
import com.flyco.banner.anim.BaseAnimator;
import com.flyco.banner.anim.select.ZoomInEnter;
import com.flyco.banner.widget.Banner.base.BaseBanner;

import java.util.List;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/28 9:58
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class BannerBinder implements BaseViewBinder<BannerHolder> {

    private Context mContext;
    private int loadingId;
    private List<String> source;
    private boolean isAutoScroll;
    private boolean isLoop;
    private Class<? extends ViewPager.PageTransformer> transformerClass;
    private Class<? extends BaseAnimator> selectAnimClass;
    private boolean needSetBottom;
    private OnItemClickListener onItemClickListener;

    public BannerBinder(Context context, int loadingId, List<String> source) {
        this.mContext = context;
        this.loadingId = loadingId;
        this.source = source;
    }

    @Override
    public BannerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (isLoop) {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_loop_banner, null);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_banner, null);
        }
        return new BannerHolder(view);
    }

    @Override
    public void onBindViewHolder(final BannerHolder bannerHolder, int position) {
        if (needSetBottom){
            bannerHolder.banner.setBottomPadding(48).setIndicatorType(2)
                    .setLoadingPic(loadingId).setSource(source).setAutoScrollEnable(isAutoScroll)
                    .setSelectAnimClass(ZoomInEnter.class)
                    .setSelectAnimClass(selectAnimClass).setTransformerClass(transformerClass).startScroll();
            bannerHolder.banner.setOnItemClickL(new BaseBanner.OnItemClickL() {
                @Override
                public void onItemClick(int position) {
                    if (onItemClickListener!=null){
                        onItemClickListener.itemClick(bannerHolder.banner,position);
                    }
                }
            });
        }else {
            bannerHolder.banner.setLoadingPic(loadingId).setSource(source).setAutoScrollEnable(isAutoScroll)
                    .setSelectAnimClass(selectAnimClass).setTransformerClass(transformerClass).startScroll();
        }
    }

    @Override
    public void onBindViewHolderWithOffer(BannerHolder productBannerHolder, int position, int offsetTotal) {

    }

    /**
     * 是否自动滚动
     * @param is
     */
    public void setIsAutoScroll(boolean is) {
        isAutoScroll = is;
    }

    /**
     * 轮播图切换动画
     * @param transformerClass
     */
    public void setTransformerClass(Class<? extends ViewPager.PageTransformer> transformerClass) {
        this.transformerClass = transformerClass;
    }

    /**
     * 指示器动画
     * @param selectAnimClass
     */
    public void setSelectAnimClass(Class<? extends BaseAnimator> selectAnimClass) {
        this.selectAnimClass = selectAnimClass;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    /**
     * 是否无限滚动
     * @param isLoop
     */
    public void setIsLoop(boolean isLoop) {
        this.isLoop = isLoop;
    }

    public void needSetBottom(boolean is){
        needSetBottom=is;
    }

}
