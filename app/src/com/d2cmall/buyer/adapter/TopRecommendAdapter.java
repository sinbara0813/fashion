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
import com.d2cmall.buyer.bean.TopRecommendBean;
import com.d2cmall.buyer.holder.CollectHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Fixme
 * Author: LWJ
 * desc:   榜单推荐Adapter
 * Date: 2017/09/06 19:20
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class TopRecommendAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private List<TopRecommendBean.ListBean> topRecommendBeanList;
    int itemWidth;
    public TopRecommendAdapter(Context context, List<TopRecommendBean.ListBean> topRecommendBeanList, int itemWidth) {
        mContext = context;
        this.topRecommendBeanList=topRecommendBeanList;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CollectHolder collectHolder = (CollectHolder) holder;
        final TopRecommendBean.ListBean topRecommendBean = topRecommendBeanList.get(position);
        UniversalImageLoader.displayImage(mContext,topRecommendBean.getImg(),collectHolder.mIvCollectGoodsImg);
        collectHolder.mTvCollectAuthorName.setText(topRecommendBean.getDesigner());
        collectHolder.mTvCollectGoodsDesc.setText(topRecommendBean.getName());
        int promotionId = topRecommendBean.getPromotionId();
        //显示价格()
        collectHolder.mTvCollectGoodsPrice.setText("¥"+ Util.getNumberFormat(topRecommendBean.getPrice()));
        if(promotionId>0) {

            collectHolder.mTvCollectGoodsOldPrice.setVisibility(View.VISIBLE);
            collectHolder.mTvCollectGoodsOldPrice.setText("¥"+Util.getNumberFormat(topRecommendBean.getSalePrice()));
            collectHolder.mTvCollectGoodsOldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        }else{
            if(topRecommendBean.getPrice()==topRecommendBean.getOriginalPrice()) {
                collectHolder.mTvCollectGoodsOldPrice.setVisibility(View.GONE);
            }else{
               collectHolder.mTvCollectGoodsOldPrice.setVisibility(View.VISIBLE);
               collectHolder.mTvCollectGoodsOldPrice.setText("¥"+Util.getNumberFormat(topRecommendBean.getOriginalPrice()));
               collectHolder.mTvCollectGoodsOldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            }
        }
        if(topRecommendBean.getStore()>0) {
            collectHolder.mTvNoStore.setVisibility(View.GONE);
            collectHolder.viewNoStore.setVisibility(View.GONE);
        }else{
            collectHolder.mTvNoStore.setVisibility(View.VISIBLE);
            collectHolder.viewNoStore.setVisibility(View.VISIBLE);
        }


        if(!Util.isEmpty(topRecommendBean.getPromotionTypeName()) || !Util.isEmpty(topRecommendBean.getOrderPromotionTypeName()) || (topRecommendBean.getFlashPromotionId()!=null && topRecommendBean.getFlashPromotionId()>0)){ //有商品活动或订单活动
            collectHolder.llPromotionType.setVisibility(View.VISIBLE);
            if(!Util.isEmpty(topRecommendBean.getPromotionTypeName())){
                collectHolder.tvGoodPromotion.setVisibility(View.VISIBLE);
                collectHolder.tvGoodPromotion.setText(topRecommendBean.getPromotionTypeName());
            }else if(topRecommendBean.getFlashPromotionId()!=null && topRecommendBean.getFlashPromotionId()>0){
                collectHolder.tvGoodPromotion.setVisibility(View.VISIBLE);
                collectHolder.tvGoodPromotion.setText("限时购");
            }else{
                collectHolder.tvGoodPromotion.setVisibility(View.GONE);
            }
            if(!Util.isEmpty(topRecommendBean.getOrderPromotionTypeName())){
                collectHolder.tvOrderPromotion.setVisibility(View.VISIBLE);
                collectHolder.tvOrderPromotion.setText(topRecommendBean.getOrderPromotionTypeName());
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
                Long productId = Long.valueOf(topRecommendBean.getId());
                intent.putExtra("id",productId);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topRecommendBeanList.size();
    }

}
