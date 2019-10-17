package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.StarStyleBean;
import com.d2cmall.buyer.holder.ProductHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Fixme
 * Author: LWJ
 * desc:   明星风范Adapter
 * Date: 2017/09/06 19:20
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class StarStyleAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private List<StarStyleBean.DataBean.ProductsBean.ListBean> starStlyeBeanList;
    int itemWidth;
    public StarStyleAdapter(Context context, List<StarStyleBean.DataBean.ProductsBean.ListBean> starStlyeBeanList, int itemWidth) {
        mContext = context;
        this.starStlyeBeanList=starStlyeBeanList;
        this.itemWidth=itemWidth;
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        StaggeredGridLayoutHelper layoutHelper = new StaggeredGridLayoutHelper(2);
        layoutHelper.setGap(ScreenUtil.dip2px(16));
        layoutHelper.setPaddingBottom(ScreenUtil.dip2px(16));
        layoutHelper.setPaddingTop(ScreenUtil.dip2px(16));
        return layoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_product_item, parent, false);
        return new ProductHolder(itemView,itemWidth);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProductHolder productHolder = (ProductHolder) holder;
        final StarStyleBean.DataBean.ProductsBean.ListBean starStyleBean = starStlyeBeanList.get(position);
        int height = (int) (itemWidth * (float) 1558 / 1000);
        Glide.with(mContext)
                .load(Util.getD2cProductPicUrl(starStyleBean.getImg(), itemWidth, height))
                .placeholder(R.mipmap.ic_logo_empty5)
                .override(itemWidth, height)
                .dontAnimate()
                .error(R.mipmap.ic_logo_empty5)
                .into(productHolder.productImage);
        if (!Util.isEmpty(starStyleBean.getProductTradeType())) {
            if (starStyleBean.getProductTradeType().equals("CROSS")) {//跨境商品
                productHolder.tvGlobalTag.setVisibility(View.VISIBLE);
            } else {
                productHolder.tvGlobalTag.setVisibility(View.GONE);
            }
        } else {
            productHolder.tvGlobalTag.setVisibility(View.GONE);
        }
        if (starStyleBean.getSoonPromotion() != null && starStyleBean.getSoonPromotion().getSoonPromotionDate() != null) {//提前显示活动价
            if (System.currentTimeMillis() < starStyleBean.getSoonPromotion().getSoonPromotionDate()) {//还没开始了
                productHolder.rlDiscount.setVisibility(View.VISIBLE);
                productHolder.tvDiscountPrice.setText("¥" + starStyleBean.getSoonPromotion().getSoonPromotionPrice());
                productHolder.tvDiscountName.setText(starStyleBean.getSoonPromotion().getSoonPromotionPrefix());
            }
        } else {
            productHolder.rlDiscount.setVisibility(View.GONE);
        }
        productHolder.productName.setText(starStyleBean.getName());
        productHolder.tagLl.removeAllViews();
        if (starStyleBean.getFlashPromotionId() != null && starStyleBean.getFlashPromotionId() > 0) {
            addTag(productHolder.tagLl,1,"限时购");
        } else if (!Util.isEmpty(starStyleBean.getPromotionTypeName())) {
            addTag(productHolder.tagLl,1,starStyleBean.getPromotionTypeName());
        }else if (!Util.isEmpty(starStyleBean.getOrderPromotionTypeName())){
            addTag(productHolder.tagLl,2,starStyleBean.getOrderPromotionTypeName());
        } else {
            productHolder.tagLl.setVisibility(View.GONE);
        }
        Integer promotionId = starStyleBean.getPromotionId();
        //显示价格()
        if (promotionId != null && promotionId > 0) {
            if (starStyleBean.getSalePrice() > starStyleBean.getMinPrice()) {
                productHolder.productPrice.setText(Util.getProductPrice(starStyleBean.getMinPrice(),starStyleBean.getSalePrice()));
            } else {
                productHolder.productPrice.setText(Util.getProductPrice(starStyleBean.getMinPrice()));
            }
        } else {
            if (starStyleBean.getOriginalPrice() > starStyleBean.getMinPrice()) {
                productHolder.productPrice.setText(Util.getProductPrice(starStyleBean.getMinPrice(),starStyleBean.getOriginalPrice()));
            } else {
                productHolder.productPrice.setText(Util.getProductPrice(starStyleBean.getMinPrice()));
            }
        }
        if (starStyleBean.getStore() < 1) {
            productHolder.tvNoStore.setVisibility(View.VISIBLE);
            productHolder.viewNoStore.setVisibility(View.VISIBLE);
        } else {
            productHolder.tvNoStore.setVisibility(View.GONE);
            productHolder.viewNoStore.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转商品详情
                Intent intent=new Intent(mContext, ProductDetailActivity.class);
                Long productId = Long.valueOf(starStyleBean.getId());
                intent.putExtra("id",productId);
                mContext.startActivity(intent);
            }
        });
        if (starStyleBean.isIsSpot()){
            productHolder.yearTag.setVisibility(View.VISIBLE);
        }else {
            productHolder.yearTag.setVisibility(View.GONE);
        }
    }

    /**
     * 添加商品标签
     * @param tagLl
     * @param type
     */
    private void addTag(LinearLayout tagLl, int type, String text){
        tagLl.setVisibility(View.VISIBLE);
        TextView textView=getTagTextView(type,text);
        LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(-2, ScreenUtil.dip2px(15));
        ll.setMargins(0,0, ScreenUtil.dip2px(4),0);
        tagLl.addView(textView,ll);
    }

    /**
     *
     * @param type 1 是商品活动 2 是订单活动
     * @return
     */
    private TextView getTagTextView(int type, String text){
        TextView textView=new TextView(mContext);
        textView.setPadding(ScreenUtil.dip2px(3),0, ScreenUtil.dip2px(3),0);
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

    @Override
    public int getItemCount() {
        return starStlyeBeanList==null?0:starStlyeBeanList.size();
    }

}
