package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.ThemeTagBean;
import com.d2cmall.buyer.listener.OnItemClickListener;
import com.d2cmall.buyer.util.UniversalImageLoader;

import java.util.List;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/12 16:45
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class SpecialGalleryAdapter extends RecyclerView.Adapter<SpecialGalleryAdapter.SpecailViewHolder> {

    Context context;
    List<ThemeTagBean.DataBean.CountTagsBean> datas;
    private OnItemClickListener onItemClickListner;

    public SpecialGalleryAdapter(Context context,List<ThemeTagBean.DataBean.CountTagsBean> datas,OnItemClickListener onItemClickListner){
        this.context=context;
        this.datas=datas;
        this.onItemClickListner=onItemClickListner;
    }

    @Override
    public SpecailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_select_special_item,parent,false);
        return new SpecailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SpecailViewHolder holder, final int position) {
        holder.specailNum.setText(datas.get(position)+"");
        UniversalImageLoader.displayImage(context,datas.get(position).getPic(),holder.imageView,R.mipmap.ic_logo_empty1,R.mipmap.ic_logo_empty1);
        holder.specailName.setText(datas.get(position).getName());
        holder.specailNum.setText(String.valueOf(datas.get(position).getCount()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListner.itemClick(v,position);
            }
        });
    }



    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class SpecailViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView specailName;
        TextView specailNum;

        public SpecailViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.item_special_image);
            specailName= (TextView) itemView.findViewById(R.id.item_special_name);
            specailNum= (TextView) itemView.findViewById(R.id.item_special_num);
        }
    }
}
