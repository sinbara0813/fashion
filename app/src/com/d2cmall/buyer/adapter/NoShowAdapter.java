package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.DesignerBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/8/29.
 */

public class NoShowAdapter extends DelegateAdapter.Adapter {
    private Context context;
    private List<DesignerBean.DataEntity.DesignersEntity.ListEntity> list;

    public NoShowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public NoShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_no_show_item, parent, false);
        return new NoShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NoShowViewHolder noShowViewHolder = (NoShowViewHolder) holder;
        noShowViewHolder.imgHint.setImageResource(R.mipmap.ic_empty_sun);
    }


    @Override
    public int getItemCount() {
        return 1;
    }


    static class NoShowViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.img_hint)
        ImageView imgHint;

        NoShowViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
