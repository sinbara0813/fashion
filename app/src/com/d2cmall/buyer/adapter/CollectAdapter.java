package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.FindSimilarActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.api.MyCollectProductBean;
import com.d2cmall.buyer.api.ProductCollectApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.holder.CollectHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;

/**
 * Fixme
 * Author: LWJ
 * desc:   我的收藏Adapter
 * Date: 2017/09/06 19:20
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class CollectAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private ArrayList<MyCollectProductBean.DataBean.MyCollectionsBean.ListBean> collectProductList;



    private int longClickPosition = -1;
    int itemWidth;
    private AnimationSet set;
    private Animation alphaAnimation, scale;
    private int itemHeight;//长按找相似的蒙层高度,未完全展示的item直接调用view.getHeight可能为0,所以做了个全局变量
    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    private boolean isCollect;//布局复用很多,标示是否是收藏界面,显示收藏按钮

    public CollectAdapter(Context context, ArrayList<MyCollectProductBean.DataBean.MyCollectionsBean.ListBean> collectProductList, int itemWidth) {
        mContext = context;
        this.collectProductList = collectProductList;
        this.itemWidth = itemWidth;
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper layoutHelper = new GridLayoutHelper(2);
        layoutHelper.setPaddingTop(ScreenUtil.dip2px(16));
        layoutHelper.setPaddingBottom(ScreenUtil.dip2px(16));
        layoutHelper.setGap(ScreenUtil.dip2px(16));
        layoutHelper.setAutoExpand(false);
        return layoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_collect_item, parent, false);
        return new CollectHolder(itemView, itemWidth);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final CollectHolder collectHolder = (CollectHolder) holder;
        final MyCollectProductBean.DataBean.MyCollectionsBean.ListBean collectBean = collectProductList.get(position);
        //修正长按找相似的View高度
        if(itemHeight==0){
                itemHeight=collectHolder.itemView.getHeight();
        }
        int height = (int) (itemWidth * (float) 1558 / 1000);
        Glide.with(mContext)
                .load(Util.getD2cProductPicUrl(collectBean.getProductPic(), itemWidth, height))
                .placeholder(R.mipmap.ic_logo_empty5)
                .override(itemWidth, height)
                .dontAnimate()
                .error(R.mipmap.ic_logo_empty5)
                .into(collectHolder.mIvCollectGoodsImg);
        collectHolder.mTvCollectAuthorName.setText(collectBean.getDesigners());
        collectHolder.mTvCollectGoodsDesc.setText(collectBean.getProductName());
        if (collectBean.getSoonPromotion() != null && collectBean.getSoonPromotion().getSoonPromotionDate() != null) {//提前显示活动价
            if (System.currentTimeMillis() < collectBean.getSoonPromotion().getSoonPromotionDate()) {//还没开始了
                collectHolder.rlDiscount.setVisibility(View.VISIBLE);
                collectHolder.tvDiscountPrice.setText("¥" + collectBean.getSoonPromotion().getSoonPromotionPrice());
                collectHolder.tvDiscountName.setText(collectBean.getSoonPromotion().getSoonPromotionPrefix());
            }
        } else {
            collectHolder.rlDiscount.setVisibility(View.GONE);
        }
        if (!Util.isEmpty(collectBean.getProductTradeType())) {
            if (collectBean.getProductTradeType().equals("CROSS")) {//跨境商品
                collectHolder.tvGlobal.setVisibility(View.VISIBLE);
            } else {
                collectHolder.tvGlobal.setVisibility(View.GONE);
            }
        } else {
            collectHolder.tvGlobal.setVisibility(View.GONE);
        }
        int goodPromotionId = collectBean.getGoodPromotionId();
        if (isCollect) {
            collectHolder.mCollectState.setVisibility(View.VISIBLE);
        } else {
            collectHolder.mCollectState.setVisibility(View.GONE);
        }
        if (collectBean.getHasCollected() == 1) {
            collectHolder.mCollectState.setImageResource(R.mipmap.icon_collect_s);
        } else {
            collectHolder.mCollectState.setImageResource(R.mipmap.icon_collect_u);
        }

        collectHolder.mTvCollectGoodsPrice.setText("¥" + Util.getNumberFormat(collectBean.getCurrentPrice()));

        if (goodPromotionId > 0 ) {     //价格显示
            collectHolder.mTvCollectGoodsOldPrice.setVisibility(View.VISIBLE);
            collectHolder.mTvCollectGoodsOldPrice.setText("¥" + Util.getNumberFormat(collectBean.getSalePrice()));
            collectHolder.mTvCollectGoodsOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            if (collectBean.getCurrentPrice() < collectBean.getOriginalPrice() ) {
                collectHolder.mTvCollectGoodsOldPrice.setVisibility(View.VISIBLE);
                collectHolder.mTvCollectGoodsOldPrice.setText("¥" + Util.getNumberFormat(collectBean.getOriginalPrice()));
                collectHolder.mTvCollectGoodsOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else{
                collectHolder.mTvCollectGoodsOldPrice.setVisibility(View.GONE);
            }
        }

        if(isCollect && collectBean.getCollectionPrice()>collectBean.getCurrentPrice()){
            //收藏后已降价
            collectHolder.mTvCollectGoodsOldPrice.setVisibility(View.VISIBLE);
            collectHolder.mTvCollectGoodsOldPrice.setText("已降价¥" + Util.getNumberFormat(collectBean.getCollectionPrice()-collectBean.getCurrentPrice()));
            //取消横线
            collectHolder.mTvCollectGoodsOldPrice.setPaintFlags( collectHolder.mTvCollectGoodsOldPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        if (isCollect) {
            collectHolder.llPromotionType.setVisibility(View.GONE);
        }
        if (collectBean.getProductMark() > 0) {    //售罄下架显示
            collectHolder.mTvNoStore.setVisibility(View.GONE);
            collectHolder.viewNoStore.setVisibility(View.GONE);
            if (collectBean.getStore() > 0) {
                collectHolder.mTvNoStore.setVisibility(View.GONE);
                collectHolder.viewNoStore.setVisibility(View.GONE);
            } else {
                collectHolder.mTvNoStore.setText("已售罄");
                collectHolder.mTvNoStore.setVisibility(View.VISIBLE);
                collectHolder.viewNoStore.setVisibility(View.VISIBLE);
            }
        } else {
            collectHolder.mTvCollectGoodsPrice.setText("暂无报价");
            collectHolder.mTvCollectGoodsOldPrice.setVisibility(View.GONE);
            collectHolder.mTvNoStore.setText("已下架");
            collectHolder.mTvNoStore.setVisibility(View.VISIBLE);
            collectHolder.viewNoStore.setVisibility(View.VISIBLE);
        }


        collectHolder.mCollectState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collect(collectHolder.mCollectState, collectBean);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转收藏商品详情
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                Long productId = Long.valueOf(collectBean.getProductId());
                intent.putExtra("id", productId);
                mContext.startActivity(intent);
                if (longClickPosition != -1) {
                    collectProductList.get(longClickPosition).setLongClick(false);
                    longClickPosition = -1;
                    notifyDataSetChanged();
                }
            }
        });
        if (collectBean.isSpot()) {
            collectHolder.yearTag.setVisibility(View.VISIBLE);
            UniversalImageLoader.displayImage(mContext, "https://static.d2c.cn/img/promo/icon_mark_bigspring.png", collectHolder.yearTag);
        } else {
            collectHolder.yearTag.setVisibility(View.GONE);
        }
        if(isCollect){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    collectBean.setLongClick(true);
                    if (longClickPosition != -1 && longClickPosition!=position) {
                        collectProductList.get(longClickPosition).setLongClick(false);
                    }
                    longClickPosition = position;
                    notifyDataSetChanged();
                    return true;
                }
            });
            collectHolder.ivFind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, FindSimilarActivity.class);
                    intent.putExtra("id", collectBean.getProductId());
                    GoodsBean.DataBean.ProductsBean.ListBean data=new GoodsBean.DataBean.ProductsBean.ListBean();
                    data.setId(collectBean.getProductId());
                    data.setName(collectBean.getProductName());
                    data.setImg(collectBean.getProductPic());
                    int promotionId=0;
                    try {
                        promotionId=Integer.valueOf(collectBean.getGoodPromotionId());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    data.setPromotionId(promotionId);
                    data.setMinPrice(collectBean.getCurrentPrice());
                    data.setSalePrice(collectBean.getSalePrice());
                    data.setOriginalPrice(collectBean.getOriginalPrice());
                    intent.putExtra("data", data);
                    mContext.startActivity(intent);
                    if (longClickPosition != -1) {
                        collectProductList.get(longClickPosition).setLongClick(false);
                        longClickPosition = -1;
                    }
                    notifyDataSetChanged();
                }
            });
            if (collectBean.isLongClick()) {
                initAnimation();
                RelativeLayout.LayoutParams ll2 = new RelativeLayout.LayoutParams(-2, -2);
                ll2.height =  itemHeight;
                ll2.width = itemWidth;
                collectHolder.rlFind.setLayoutParams(ll2);
                collectHolder.rlFind.setVisibility(View.VISIBLE);
                collectHolder.rlFind.startAnimation(alphaAnimation);
                collectHolder.ivFind.setVisibility(View.VISIBLE);
                collectHolder.ivFind.startAnimation(set);
            } else {
                collectHolder.rlFind.setVisibility(View.GONE);
                collectHolder.ivFind.setVisibility(View.GONE);
            }
        }

    }

    private void initAnimation(){
        if (alphaAnimation==null){
            alphaAnimation = new AlphaAnimation(0f, 1.0f);
            alphaAnimation.setDuration(350);
            scale = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scale.setDuration(350);
            set = new AnimationSet(true);
            set.addAnimation(alphaAnimation);
            set.addAnimation(scale);
            set.setDuration(350);
            set.setFillAfter(true);
            set.setInterpolator(new OvershootInterpolator());
        }
    }

    private void collect(final ImageView imageView, final MyCollectProductBean.DataBean.MyCollectionsBean.ListBean collectBean) {
        imageView.setEnabled(false);
        final boolean is;
        ProductCollectApi api = new ProductCollectApi();
        api.productId = collectBean.getProductId();
        if (collectBean.getHasCollected() == 0) {
            api.setInterPath(Constants.COLLECT_PRODUCT_URL);
            is = true;
        } else {
            api.setInterPath(Constants.CANCEL_COLLECT_URL);
            is = false;
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                imageView.setEnabled(true);
                if (is) {
                    collectBean.setHasCollected(1);
                    imageView.setImageResource(R.mipmap.icon_collect_s);
                    Util.showToast(mContext, "收藏成功");
                } else {
                    imageView.setImageResource(R.mipmap.icon_collect_u);
                    collectBean.setHasCollected(0);
                    Util.showToast(mContext, "取消收藏成功");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imageView.setEnabled(true);
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }
    public int getLongClickPosition() {
        return longClickPosition;
    }

    public void setLongClickPosition(int longClickPosition) {
        this.longClickPosition = longClickPosition;
    }

    @Override
    public int getItemCount() {
        return collectProductList==null?0:collectProductList.size();
    }
}
