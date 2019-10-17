package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.holder.ProductDesignRecommendHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/11/27 10:47
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductSimpleAdapter extends RecyclerView.Adapter<ProductDesignRecommendHolder> {


    private List<ProductDetailBean.DataBean.RecommendProductsBean> recommends;
    private Context context;
    private int width;
    private int height;

    public ProductSimpleAdapter(Context context, List<ProductDetailBean.DataBean.RecommendProductsBean> data) {
        this.context = context;
        recommends = data;
        width = ScreenUtil.dip2px(84);
        height = width * 1558 / 1000;
    }

    @Override
    public ProductDesignRecommendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_product_design_recommend_item, parent, false);
        return new ProductDesignRecommendHolder(v);
    }

    @Override
    public void onBindViewHolder(final ProductDesignRecommendHolder holder, final int position) {
        RecyclerView.LayoutParams rl= (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        rl.setMargins(0,0,ScreenUtil.dip2px(8),0);
        UniversalImageLoader.displayImage(context, Util.getD2cProductPicUrl(recommends.get(position).getImg(),width,height), holder.image, R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        holder.price.setText("¥ " + Util.getNumberFormat(recommends.get(position).getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", recommends.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recommends == null ? 0 : recommends.size();
    }
}
