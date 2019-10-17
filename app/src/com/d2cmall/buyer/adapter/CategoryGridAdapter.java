package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.KindBean;
import com.d2cmall.buyer.holder.CategoryDetailHolder;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Created by rookie on 2017/9/8.
 */

public class CategoryGridAdapter extends DelegateAdapter.Adapter<CategoryDetailHolder> {
    private Context context;
    private List<KindBean.DataEntity.NavigationsEntity.ItemsEntity> list;
    private LayoutHelper layoutHelper;

    public CategoryGridAdapter(Context context,List<KindBean.DataEntity.NavigationsEntity.ItemsEntity> list,LayoutHelper layoutHelper){
        this.context=context;
        this.list=list;
        this.layoutHelper=layoutHelper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public CategoryDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_category_item,parent,false);
        return new CategoryDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryDetailHolder categoryDetailHolder, final int position) {
        UniversalImageLoader.displayImage(context, Constants.IMG_HOST+list.get(position).getPic(),categoryDetailHolder.image);
        categoryDetailHolder.text.setText(list.get(position).getTitle());
        categoryDetailHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.urlAction(context,list.get(position).getUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 20;
    }
}
