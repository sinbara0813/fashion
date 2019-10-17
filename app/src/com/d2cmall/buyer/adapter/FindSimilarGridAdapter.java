package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.holder.ProductHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;

/**
 * Created by rookie on 2018/3/21.
 */

public class FindSimilarGridAdapter extends DelegateAdapter.Adapter<ProductHolder> {

    private Context context;
    private ArrayList<GoodsBean.DataBean.ProductsBean.ListBean> list;
    private LayoutHelper mLayoutHelper;
    private int itemWidth;

    public FindSimilarGridAdapter(Context context, ArrayList<GoodsBean.DataBean.ProductsBean.ListBean> list, int itemWidth) {
        this.context = context;
        this.list = list;
        this.itemWidth = itemWidth;
    }

    public FindSimilarGridAdapter(Context context, ArrayList<GoodsBean.DataBean.ProductsBean.ListBean> list,LayoutHelper layoutHelper, int itemWidth) {
        this.context = context;
        this.list = list;
        this.itemWidth = itemWidth;
        this.mLayoutHelper=layoutHelper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        if (mLayoutHelper!=null){
            return mLayoutHelper;
        }else {
            GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
            gridLayoutHelper.setAutoExpand(false);
            gridLayoutHelper.setPaddingLeft(ScreenUtil.dip2px(16));
            gridLayoutHelper.setPaddingRight(ScreenUtil.dip2px(16));
            gridLayoutHelper.setHGap(ScreenUtil.dip2px(16));
            return gridLayoutHelper;
        }
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_product_item, parent, false);
        return new ProductHolder(view, itemWidth);
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {
        final GoodsBean.DataBean.ProductsBean.ListBean data = list.get(position);
        if (!Util.isEmpty(data.getProductTradeType())) {
            if (data.getProductTradeType().equals("CROSS")) {//跨境商品
                holder.tvGlobalTag.setVisibility(View.VISIBLE);
            } else {
                holder.tvGlobalTag.setVisibility(View.GONE);
            }
        } else {
            holder.tvGlobalTag.setVisibility(View.GONE);
        }
        Glide.with(context)
                .load(Util.getD2cPicUrl(data.getImg()))
                .placeholder(R.mipmap.ic_logo_empty5)
                .crossFade()
                .error(R.mipmap.ic_logo_empty5)
                .override(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL, com.bumptech.glide.request.target.Target.SIZE_ORIGINAL)
                .into(holder.productImage);
        holder.productName.setText(data.getName());
        if (data.getSoonPromotion() != null && data.getSoonPromotion().getSoonPromotionDate() != null) {//提前显示活动价
            if (System.currentTimeMillis() < data.getSoonPromotion().getSoonPromotionDate()) {//还没开始了
                holder.rlDiscount.setVisibility(View.VISIBLE);
                holder.tvDiscountPrice.setText("¥" + data.getSoonPromotion().getSoonPromotionPrice());
                holder.tvDiscountName.setText(data.getSoonPromotion().getSoonPromotionPrefix());
            }
        } else {
            holder.rlDiscount.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", data.getId());
                context.startActivity(intent);
            }
        });
        if (data.getMark() == 0) {
            holder.tvNoStore.setText("已下架");
            holder.tvNoStore.setVisibility(View.VISIBLE);
            holder.viewNoStore.setVisibility(View.VISIBLE);
            holder.productPrice.setTextColor(Color.parseColor("#61000000"));
            holder.productPrice.setTextSize(14);
            holder.productPrice.setText("暂无报价");
            return;
        }
        Integer promotionId = data.getPromotionId();
        //显示价格()
        if (promotionId != null && promotionId > 0) {
            if (data.getSalePrice() > data.getMinPrice()) {
                holder.productPrice.setText(Util.getProductPrice(data.getMinPrice(),data.getSalePrice()));
            } else {
                holder.productPrice.setText(Util.getProductPrice(data.getMinPrice()));
            }
        } else {
            if (data.getOriginalPrice() > data.getMinPrice()) {
                holder.productPrice.setText(Util.getProductPrice(data.getMinPrice(),data.getOriginalPrice()));
            } else {
                holder.productPrice.setText(Util.getProductPrice(data.getMinPrice()));
            }
        }
        if (data.getStore() < 1) {
            holder.tvNoStore.setVisibility(View.VISIBLE);
            holder.viewNoStore.setVisibility(View.VISIBLE);
        } else {
            holder.tvNoStore.setVisibility(View.GONE);
            holder.viewNoStore.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
