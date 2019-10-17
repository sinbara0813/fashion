package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.FocusBrandBean1;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fixme
 * Author: LWJ
 * desc:    关注品牌的横向滑动列表的adapter
 * Date: 2017/09/15 10:22
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class FocusBrandItemAdapter extends RecyclerView.Adapter {
    private Context mContext;
    List<FocusBrandBean1.DataBean.MyAttentionsBean.ListBean.ProductsBean> products;
    public FocusBrandItemAdapter(Context context, List<FocusBrandBean1.DataBean.MyAttentionsBean.ListBean.ProductsBean> products) {
        mContext=context;
        this.products=products;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_focus_brand_product_item, parent, false);
        return new FocusBrandItem(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        FocusBrandItem focusBrandItem = (FocusBrandItem) holder;
        final FocusBrandBean1.DataBean.MyAttentionsBean.ListBean.ProductsBean productsBean = products.get(position);
        UniversalImageLoader.displayImage(mContext,productsBean.getMainPic(),focusBrandItem.mImage1);
        focusBrandItem.mTvPrice.setText("¥"+ Util.getNumberFormat(productsBean.getMinPrice()));
        if(productsBean.getPromotionId()>0) {
            if(productsBean.getMinPrice()<productsBean.getOriginalPrice()) {
                focusBrandItem.mTvOldPrice.setVisibility(View.VISIBLE);
                focusBrandItem.mTvOldPrice.setText("¥"+Util.getNumberFormat(productsBean.getOriginalPrice()));
                focusBrandItem.mTvOldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            }else{
                focusBrandItem.mTvOldPrice.setVisibility(View.GONE);
            }
        }else{
            if(productsBean.getMinPrice()<productsBean.getOriginalPrice()) {
                focusBrandItem.mTvOldPrice.setVisibility(View.VISIBLE);
                focusBrandItem.mTvOldPrice.setText("¥"+Util.getNumberFormat(productsBean.getOriginalPrice()));
                focusBrandItem.mTvOldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            }else{
                focusBrandItem.mTvOldPrice.setVisibility(View.GONE);
            }
        }

        focusBrandItem.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("id",Long.valueOf(productsBean.getProductId()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class FocusBrandItem extends RecyclerView.ViewHolder{
        @Bind(R.id.image1)
        ImageView mImage1;
        @Bind(R.id.tv_price)
        TextView mTvPrice;
        @Bind(R.id.tv_old_price)
        TextView mTvOldPrice;
        FocusBrandItem(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
