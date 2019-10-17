package com.d2cmall.buyer.adapter;

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
import com.d2cmall.buyer.bean.MainBrandBean;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/12 16:45
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainBrandProductAdapter extends RecyclerView.Adapter<MainBrandProductAdapter.ViewHolder> {

    private Context context;
    public List<MainBrandBean.DataBean.UpMarketBrandsBean.ProductsBean> list;

    public MainBrandProductAdapter(Context context, List<MainBrandBean.DataBean.UpMarketBrandsBean.ProductsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MainBrandProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_main_brand_product_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainBrandProductAdapter.ViewHolder holder, final int position) {
        UniversalImageLoader.displayImage(context,Util.getD2cProductPicUrl(list.get(position).getMainPic(), ScreenUtil.dip2px(120),ScreenUtil.dip2px(182)),holder.image,R.mipmap.ic_logo_empty5);
        MainBrandBean.DataBean.UpMarketBrandsBean.ProductsBean productsBean = list.get(position);
        holder.price.setText("¥"+Util.getNumberFormat(list.get(position).getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = list.get(position).getId();
                Intent intent=new Intent(context, ProductDetailActivity.class);
                Long productId = Long.valueOf(id);
                intent.putExtra("id",productId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView price;

        public ViewHolder(View itemView) {
            super(itemView);
            image= (ImageView) itemView.findViewById(R.id.image);
            price= (TextView) itemView.findViewById(R.id.price);
        }
    }
}
