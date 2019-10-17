package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BrandListActivity;
import com.d2cmall.buyer.bean.BrandKindBean;
import com.d2cmall.buyer.holder.BrandSpecificHolder;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.tendcloud.tenddata.TCAgent;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rookie on 2017/10/13.
 */

public class BrandSpecificAdapter extends DelegateAdapter.Adapter<BrandSpecificHolder> {
    private LayoutHelper layoutHelper;
    private List<BrandKindBean.DataBean.DatasBean> list;
    private Context context;
    private String name;

    public BrandSpecificAdapter(LayoutHelper layoutHelper, List<BrandKindBean.DataBean.DatasBean> list, Context context,String name) {
        this.layoutHelper = layoutHelper;
        this.list = list;
        this.context = context;
        this.name=name;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public BrandSpecificHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_brand_specific_item,parent,false);
        return new BrandSpecificHolder(view);
    }

    @Override
    public void onBindViewHolder(BrandSpecificHolder holder, final int position) {
        UniversalImageLoader.displayImage(context, Constants.IMG_HOST+list.get(position).getPic(),holder.imageView,R.mipmap.ic_logo_empty4);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start("V3分类","品牌/"+name+"/"+list.get(position).getName());
                Intent intent=new Intent(context, BrandListActivity.class);
                intent.putExtra("type",1);
                intent.putExtra("tab", (Serializable) list);
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });
    }

    private void start(String event,String label){
        StatService.onEvent(context,event,label);
        TCAgent.onEvent(context,event,label);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
