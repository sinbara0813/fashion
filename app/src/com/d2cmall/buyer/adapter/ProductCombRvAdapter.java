package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.bean.ProductRelationBean;
import com.d2cmall.buyer.holder.ProductCombItemHolder;
import com.d2cmall.buyer.holder.ProductDesignRecommendHolder;
import com.d2cmall.buyer.holder.ProductHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * 商品详情推荐商品
 * Author: hrb
 * Date: 2016/11/01 15:07
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductCombRvAdapter extends RecyclerView.Adapter<ProductCombItemHolder> {

    private Context context;
    private List<ProductRelationBean.DataBean.ProductCombBean.ProductsBean> recommends;
    private int width;
    private int height;

    public ProductCombRvAdapter(Context context, List<ProductRelationBean.DataBean.ProductCombBean.ProductsBean> data) {
        this.context = context;
        this.recommends = data;
        width = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(48)) / 2;
        height = width * 1558 / 1000;
    }

    @Override
    public ProductCombItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_product_comb_item, parent, false);
        return new ProductCombItemHolder(v,width);
    }

    @Override
    public void onBindViewHolder(final ProductCombItemHolder holder, final int position) {
        UniversalImageLoader.displayImage(context, Util.getD2cProductPicUrl(recommends.get(position).getImg(),width,height), holder.productImage, R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        holder.productName.setText(recommends.get(position).getName());
        //显示价格()
            if (recommends.get(position).getOriginalPrice() > recommends.get(position).getMinPrice()) {
                holder.productPrice.setText(Util.getProductPrice(recommends.get(position).getMinPrice(),recommends.get(position).getOriginalPrice()));
            } else {
                holder.productPrice.setText(Util.getProductPrice(recommends.get(position).getMinPrice()));
            }
        if(position==recommends.size()-1){
            holder.tvAdd.setVisibility(View.GONE);
        }else{
            holder.tvAdd.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", (long) recommends.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recommends == null ? 0 : recommends.size();
    }

}
