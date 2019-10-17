package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BrandDetailActivity;
import com.d2cmall.buyer.bean.HotBrandBean;
import com.d2cmall.buyer.util.UniversalImageLoader;

import java.util.List;

/**
 * Created by rookie on 2017/9/13.
 */

public class HotBrandAdapter extends DelegateAdapter.Adapter<HotBrandAdapter.ViewHolder> {
    private Context context;
    private List<HotBrandBean.DataBean.DesignersBean.ListBean> list;
    private LayoutHelper layoutHelper;

    public HotBrandAdapter(Context context, List<HotBrandBean.DataBean.DesignersBean.ListBean> list, LayoutHelper layoutHelper) {
        this.context = context;
        this.list = list;
        this.layoutHelper = layoutHelper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.layout_brand_hot,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        UniversalImageLoader.displayImage(context,list.get(position).getHeadPic(),holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, BrandDetailActivity.class);
                intent.putExtra("id",(int)list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img= (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
