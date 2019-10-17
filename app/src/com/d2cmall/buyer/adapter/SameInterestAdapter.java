package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.MainSpecailBean2;
import com.d2cmall.buyer.holder.CollectHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Fixme
 * Author: LWJ
 * desc:   同兴趣的人Adapter
 * Date: 2017/09/06 19:20
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class SameInterestAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private List<MainSpecailBean2.DataBean.RecentlySalesProductBean> mRecentlySalesProductBeanList;
    private int itemWidth;
    public SameInterestAdapter(Context context, List<MainSpecailBean2.DataBean.RecentlySalesProductBean> recentlySalesProductBeanList, int itemWidth) {
        mContext = context;
        this.mRecentlySalesProductBeanList=recentlySalesProductBeanList;
        this.itemWidth=itemWidth;
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        StaggeredGridLayoutHelper layoutHelper = new StaggeredGridLayoutHelper(2);
        layoutHelper.setGap(ScreenUtil.dip2px(16));
        return layoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_collect_item, parent, false);
        return new CollectHolder(itemView,itemWidth);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CollectHolder collectHolder = (CollectHolder) holder;
        final MainSpecailBean2.DataBean.RecentlySalesProductBean productBean = mRecentlySalesProductBeanList.get(position);
        UniversalImageLoader.displayImage(mContext,productBean.getImg(),collectHolder.mIvCollectGoodsImg);
        collectHolder.mTvCollectAuthorName.setText(productBean.getBrand());
        collectHolder.mTvCollectGoodsDesc.setText(productBean.getName());
        long promotionId = productBean.getPromotionId();
        //显示价格()
        collectHolder.mTvCollectGoodsPrice.setText("¥"+ Util.getNumberFormat(productBean.getPrice()));
        if(promotionId>0) {
            collectHolder.mTvCollectGoodsOldPrice.setVisibility(View.VISIBLE);
            collectHolder.mTvCollectGoodsOldPrice.setText("¥"+Util.getNumberFormat(productBean.getSalePrice()));
            collectHolder.mTvCollectGoodsOldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        }else{
            if(productBean.getPrice()==productBean.getOriginalPrice()) {
                collectHolder.mTvCollectGoodsOldPrice.setVisibility(View.GONE);
            }else{
                collectHolder.mTvCollectGoodsOldPrice.setVisibility(View.VISIBLE);
                collectHolder.mTvCollectGoodsOldPrice.setText("¥"+Util.getNumberFormat(productBean.getOriginalPrice()));
                collectHolder.mTvCollectGoodsOldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            }
        }

        if(productBean.getMark()>0) {    //售罄下架显示
            collectHolder.mTvNoStore.setVisibility(View.GONE);
            collectHolder.viewNoStore.setVisibility(View.GONE);
            if(productBean.getStore()>0) {
                collectHolder.mTvNoStore.setVisibility(View.GONE);
                collectHolder.viewNoStore.setVisibility(View.GONE);
            }else{
                collectHolder.mTvNoStore.setText("已售罄");
                collectHolder.mTvNoStore.setVisibility(View.VISIBLE);
                collectHolder.viewNoStore.setVisibility(View.VISIBLE);
            }
        }else{
            collectHolder.mTvCollectGoodsPrice.setText("暂无报价");
            collectHolder.mTvCollectGoodsOldPrice.setVisibility(View.GONE);
            collectHolder.mTvNoStore.setText("已下架");
            collectHolder.mTvNoStore.setVisibility(View.VISIBLE);
            collectHolder.viewNoStore.setVisibility(View.VISIBLE);
        }

        if(!Util.isEmpty(productBean.getPromotionTypeName()) || !Util.isEmpty(productBean.getOrderPromotionTypeName()) || (productBean.getFlashPromotionId()!=null && productBean.getFlashPromotionId()>0)){ //有商品活动或订单活动
            collectHolder.llPromotionType.setVisibility(View.VISIBLE);
            if(productBean.getPromotionId()>0){
                collectHolder.tvGoodPromotion.setVisibility(View.VISIBLE);
                collectHolder.tvGoodPromotion.setText(productBean.getPromotionTypeName());
            }else if(productBean.getFlashPromotionId()!=null && productBean.getFlashPromotionId()>0){
                collectHolder.tvGoodPromotion.setVisibility(View.VISIBLE);
                collectHolder.tvGoodPromotion.setText("限时购");
            }else{
                collectHolder.tvGoodPromotion.setVisibility(View.GONE);
            }
            if(!Util.isEmpty(productBean.getOrderPromotionTypeName())){
                collectHolder.tvOrderPromotion.setVisibility(View.VISIBLE);
                collectHolder.tvOrderPromotion.setText(productBean.getOrderPromotionTypeName());
            }else{
                collectHolder.tvOrderPromotion.setVisibility(View.INVISIBLE);
            }
        }else{  //没有活动
            collectHolder.llPromotionType.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转商品详情
                Intent intent=new Intent(mContext, ProductDetailActivity.class);
                Long productId = Long.valueOf(mRecentlySalesProductBeanList.get(position).getId());
                intent.putExtra("id",productId);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecentlySalesProductBeanList.size();
    }


}
