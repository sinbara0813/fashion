package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.FindSimilarActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.bean.PromotionBean;
import com.d2cmall.buyer.holder.ProductHolder;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Fixme
 * Author: LWJ
 * desc:   活动列表Adapter
 * Date: 2017/09/06 19:20
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class PromotionAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private ArrayList<GoodsBean.DataBean.ProductsBean.ListBean> listEntities;
    private long startTime;
    private long endTime;
    private int longClickPosition = -1;
    private AnimationSet set;
    private Animation alphaAnimation, scale;

    public void setPromotionBean(PromotionBean promotionBean) {
        this.promotionBean = promotionBean;
        startTime = DateUtil.strToDateLong(promotionBean.getData().getPromotion().getStartTime()).getTime();
        endTime = DateUtil.strToDateLong(promotionBean.getData().getPromotion().getEndTime()).getTime();
    }

    private PromotionBean promotionBean;
    int itemWidth;

    public PromotionAdapter(Context context, ArrayList<GoodsBean.DataBean.ProductsBean.ListBean> listEntities, int itemWidth) {
        mContext = context;
        this.listEntities = listEntities;
        this.itemWidth = itemWidth;
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


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        StaggeredGridLayoutHelper layoutHelper = new StaggeredGridLayoutHelper(2);
        layoutHelper.setPaddingTop(ScreenUtil.dip2px(16));
        layoutHelper.setPaddingLeft(ScreenUtil.dip2px(16));
        layoutHelper.setPaddingRight(ScreenUtil.dip2px(16));
        layoutHelper.setHGap(ScreenUtil.dip2px(16));
        return layoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_product_item, parent, false);
        return new ProductHolder(itemView, itemWidth);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ProductHolder viewHolder = (ProductHolder) holder;
        final GoodsBean.DataBean.ProductsBean.ListBean listEntity = listEntities.get(position);
        int height = (int) (itemWidth * (float) 1558 / 1000);
        Glide.with(mContext)
                .load(Util.getD2cProductPicUrl(listEntity.getImg(), itemWidth, height))
                .placeholder(R.mipmap.ic_logo_empty5)
                .override(itemWidth, height)
                .dontAnimate()
                .error(R.mipmap.ic_logo_empty5)
                .into(viewHolder.productImage);
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listEntity.setLongClick(true);
                if (longClickPosition != -1 && longClickPosition!=position) {
                    listEntities.get(longClickPosition).setLongClick(false);
                }
                longClickPosition = position;
                notifyDataSetChanged();
                return true;
            }
        });
        viewHolder.ivFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FindSimilarActivity.class);
                intent.putExtra("id", listEntity.getId());
                intent.putExtra("data", listEntity);
                mContext.startActivity(intent);
                if (longClickPosition != -1) {
                    listEntities.get(longClickPosition).setLongClick(false);
                    longClickPosition = -1;
                }
                notifyDataSetChanged();
            }
        });
        if (listEntity.isLongClick()) {
            initAnimation();
            viewHolder.viewFind.getLayoutParams().height=viewHolder.rootRl.getHeight();
            viewHolder.viewFind.setVisibility(View.VISIBLE);
            viewHolder.viewFind.startAnimation(alphaAnimation);
            viewHolder.ivFind.setVisibility(View.VISIBLE);
            viewHolder.ivFind.startAnimation(set);
        } else {
            viewHolder.viewFind.setVisibility(View.GONE);
            viewHolder.ivFind.setVisibility(View.GONE);
        }

        if (listEntity.getStore() < 1) {
            viewHolder.tvNoStore.setVisibility(View.VISIBLE);
            viewHolder.viewNoStore.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvNoStore.setVisibility(View.GONE);
            viewHolder.viewNoStore.setVisibility(View.GONE);
        }
        viewHolder.productPrice.setText(Util.getNumberFormat(listEntity.getMinPrice()));
        viewHolder.productName.setText(listEntity.getName());
        //商品活动,勾选了提前显示活动价,当前时间小于结束时间
        if (promotionBean != null && !Util.isEmpty(promotionBean.getData().getPromotion().getPromotionTypeName()) && promotionBean.getData().getPromotion().getAdvance() > 0 && endTime > System.currentTimeMillis()) {
            viewHolder.rlDiscount.setVisibility(View.VISIBLE);
            viewHolder.tvDiscountName.setText(promotionBean.getData().getPromotion().getPrefix());
            viewHolder.tvDiscountPrice.setText("¥" + Util.getNumberFormat(listEntity.getSoonPrice()));
            if (System.currentTimeMillis() > startTime) {//提前显示活动价的活动在进行中时不再显示提前价
                viewHolder.rlDiscount.setVisibility(View.GONE);
            } else {
                viewHolder.rlDiscount.setVisibility(View.VISIBLE);
            }
        }else {
            viewHolder.rlDiscount.setVisibility(View.GONE);
        }
        //显示价格()
        if (listEntity!=null && listEntity.getPromotionId() > 0) {
            if (listEntity.getSalePrice() > listEntity.getMinPrice()) {
                viewHolder.productPrice.setText(Util.getProductPrice(listEntity.getMinPrice(),listEntity.getSalePrice()));
            } else {
                viewHolder.productPrice.setText(Util.getProductPrice(listEntity.getMinPrice()));
            }
        } else {
            if (listEntity.getOriginalPrice() > listEntity.getMinPrice()) {
                viewHolder.productPrice.setText(Util.getProductPrice(listEntity.getMinPrice(),listEntity.getOriginalPrice()));
            } else {
                viewHolder.productPrice.setText(Util.getProductPrice(listEntity.getMinPrice()));
            }
        }


        viewHolder.tagLl.removeAllViews();
        if (listEntity.getFlashPromotionId() != null && listEntity.getFlashPromotionId() > 0) {
            addTag(viewHolder.tagLl,1,"限时购");
        } else if (!Util.isEmpty(listEntity.getPromotionTypeName())) {
            addTag(viewHolder.tagLl,1,listEntity.getPromotionTypeName());
        } else if (!Util.isEmpty(listEntity.getOrderPromotionTypeName())) {
            addTag(viewHolder.tagLl,2,listEntity.getOrderPromotionTypeName());
        }else {
            viewHolder.tagLl.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listEntity != null) {
                    Intent intent = new Intent(mContext, ProductDetailActivity.class);
                    intent.putExtra("id", listEntity.getId());
                    mContext.startActivity(intent);
                    if (longClickPosition != -1) {
                        listEntities.get(longClickPosition).setLongClick(false);
                        longClickPosition = -1;
                        notifyDataSetChanged();
                    }
                }
            }
        });

    }

    public int getLongClickPosition() {
        return longClickPosition;
    }

    public void setLongClickPosition(int longClickPosition) {
        this.longClickPosition = longClickPosition;
    }

    /**
     *
     * @param type 1 是商品活动 2 是订单活动
     * @return
     */
    private TextView getTagTextView(int type, String text){
        TextView textView=new TextView(mContext);
        textView.setPadding(ScreenUtil.dip2px(3),0,ScreenUtil.dip2px(3),0);
        textView.setGravity(Gravity.CENTER);
        if (type==1){
            textView.setBackgroundColor(mContext.getResources().getColor(R.color.color_black));
        }else {
            textView.setBackgroundColor(mContext.getResources().getColor(R.color.color_red));
        }
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);
        textView.setText(text);
        return textView;
    }

    /**
     * 添加商品标签
     * @param tagLl
     * @param type
     */
    private void addTag(LinearLayout tagLl, int type, String text){
        tagLl.setVisibility(View.VISIBLE);
        TextView textView=getTagTextView(type,text);
        LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(-2,ScreenUtil.dip2px(15));
        ll.setMargins(0,0,ScreenUtil.dip2px(4),0);
        tagLl.addView(textView,ll);
    }


    @Override
    public int getItemCount() {
        return listEntities == null ? 0 : listEntities.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }


}
