package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.MainBean;
import com.d2cmall.buyer.holder.StarItemHolder;
import com.d2cmall.buyer.util.UniversalImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Fixme
 * Author: LWJ
 * desc:   男/女士页:明星风范Adapter
 * Date: 2017/09/06 19:20
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

class StarAdapter extends RecyclerView.Adapter<StarItemHolder> {
    private List<MainBean.DataEntity.ContentEntity.SectionValuesEntity> mList = new ArrayList<>();
    private Context context;
    private int currentCenterPosition=0;

    public StarAdapter(List<MainBean.DataEntity.ContentEntity.SectionValuesEntity> mList,Context context) {
        this.mList = mList;
        this.context=context;
    }

    @Override
    public StarItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.view_card_item, parent, false);
        return new StarItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StarItemHolder holder,  int position) {
        position%=mList.size();
        MainBean.DataEntity.ContentEntity.SectionValuesEntity data=mList.get(position);
        UniversalImageLoader.displayImage(context,data.getFrontPic(),holder.imageView);
        if(position==currentCenterPosition){
            holder.imageCover.setVisibility(View.GONE);
        }else{
            holder.imageCover.setVisibility(View.VISIBLE);
        }
        holder.llText.setVisibility(View.VISIBLE);
        holder.tvIndex.setText(String.valueOf(position+1));
        holder.tvTotal.setText(String.valueOf("/"+(mList.size())));
    }

    @Override
    public int getItemCount() {
        return  mList==null?0:mList.size()+2;
    }


    public void dissmissCover(int adapterPosition) {
        currentCenterPosition = adapterPosition;
        if(adapterPosition==mList.size()){
            currentCenterPosition=0;
        }
        notifyDataSetChanged();
    }
//    public interface ChangPositionListener{
//        void changePosition(int position);
//    }
//    public void setChangPositionListener(ChangPositionListener changPositionListener) {
//        this.changPositionListener = changPositionListener;
//    }
}
