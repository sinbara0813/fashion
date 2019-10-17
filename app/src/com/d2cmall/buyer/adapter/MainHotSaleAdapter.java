package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.TopRecommendActivity;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.tendcloud.tenddata.TCAgent;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/11 16:58
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainHotSaleAdapter extends RecyclerView.Adapter<MainHotSaleAdapter.ViewHolder>{

    private Context context;
    private List<ProductDetailBean.DataBean.RecommendProductsBean> list;

    public MainHotSaleAdapter(Context context, List<ProductDetailBean.DataBean.RecommendProductsBean> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_main_top,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        UniversalImageLoader.displayImage(context,list.get(position).getImg(),holder.image);
        holder.categoryName.setText(list.get(position).getCategoryName());
        holder.topTv.setText("排行榜");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转商品详情
                Intent intent=new Intent(context, TopRecommendActivity.class);
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView categoryName;
        public TextView topTv;

        public ViewHolder(View itemView) {
            super(itemView);
            image= (ImageView) itemView.findViewById(R.id.image);
            categoryName= (TextView) itemView.findViewById(R.id.category_name);
            topTv= (TextView) itemView.findViewById(R.id.top_tv);
        }
    }
}
