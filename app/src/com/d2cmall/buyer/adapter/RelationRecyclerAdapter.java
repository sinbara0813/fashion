package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * 商品详情里的搭配商品适配器
 * Author: hrb
 * Date: 2016/11/01 14:33
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class RelationRecyclerAdapter extends RecyclerView.Adapter<RelationRecyclerAdapter.RelationViewHolder>{

    private List<ProductDetailBean.DataBean.RelationProductsBean> relationProducts;
    private Context context;

    public RelationRecyclerAdapter(Context context,List<ProductDetailBean.DataBean.RelationProductsBean> data) {
        this.context = context;
        relationProducts=data;
    }

    public void setData(List<ProductDetailBean.DataBean.RelationProductsBean> data) {
        if (data != null && data.size() > 0) {
            relationProducts.clear();
            relationProducts.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public RelationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.relation_item, parent, false);
        return new RelationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RelationViewHolder holder, final int position) {
        UniversalImageLoader.displayImage(context,Util.getD2cPicUrl(relationProducts.get(position).getImg()), holder.image, R.mipmap.ic_logo_empty7, R.mipmap.ic_logo_empty7);
        holder.tv.setText(context.getString(R.string.label_price, Util.getNumberFormat(relationProducts.get(position).getPrice())));
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", relationProducts.get(position).getId());
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            }
        });
    }

    @Override
    public int getItemCount() {
        return relationProducts == null ? 0 : relationProducts.size();
    }

    class RelationViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tv;

        public RelationViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
