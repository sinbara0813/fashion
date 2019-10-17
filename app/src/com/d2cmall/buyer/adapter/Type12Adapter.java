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

import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.MainBean;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.tendcloud.tenddata.TCAgent;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/18 13:01
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class Type12Adapter extends RecyclerView.Adapter<Type12Adapter.ViewHolder> {

    private Context context;
    private List<MainBean.DataEntity.ContentEntity.SectionValuesEntity.SectionValuesListEntity> items;
    private OnItemClickListener onItemClickListener;
    private String tag;
    private String name;

    public Type12Adapter(Context context, List<MainBean.DataEntity.ContentEntity.SectionValuesEntity.SectionValuesListEntity> items,String name,String tag) {
        this.context = context;
        this.items = items;
        this.name=name;
        this.tag=tag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_type12_item_vew,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MainBean.DataEntity.ContentEntity.SectionValuesEntity.SectionValuesListEntity listEntity=items.get(position);
        UniversalImageLoader.displayImage(context,Util.getD2cProductPicUrl(listEntity.getFrontPic(), ScreenUtil.dip2px(120), ScreenUtil.dip2px(180)),holder.image,R.mipmap.ic_logo_empty5,R.mipmap.ic_logo_empty5);
        holder.designName.setVisibility(View.GONE);
        holder.title.setVisibility(View.GONE);
        if (listEntity.getSalePrice()>listEntity.getPrice()){
            holder.dropPrice.setVisibility(View.VISIBLE);
            holder.dropPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            holder.dropPrice.setText("¥"+ Util.getNumberFormat(listEntity.getSalePrice()));
        }else {
            holder.dropPrice.setVisibility(View.GONE);
        }
        holder.price.setText("¥"+ Util.getNumberFormat(listEntity.getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id",listEntity.getId());
                context.startActivity(intent);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(Object item);
    }

    public void setOnItemClickListener(Type12Adapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return items==null?0:items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image)
        ImageView image;
        @Bind(R.id.design_name)
        TextView designName;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.price)
        TextView price;
        @Bind(R.id.drop_price)
        TextView dropPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
