package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.bean.ThemeBean;
import com.d2cmall.buyer.holder.TopicHolder;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.tendcloud.tenddata.TCAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rookie on 2017/8/18.
 */

public class TopicRecyclerViewAdapter extends DelegateAdapter.Adapter {
    private LayoutHelper layoutHelper;
    private Context context;
    private List<ThemeBean.DataBean.ThemesBean.ListBean> list;
    private DisplayMetrics dm;
    private Point point;
    private int picWidth;
    private String name;

    public TopicRecyclerViewAdapter(Context context, LayoutHelper layoutHelper, List<ThemeBean.DataBean.ThemesBean.ListBean> list){
        this.context=context;
        this.layoutHelper=layoutHelper;
        this.list=list;
        dm = context.getResources().getDisplayMetrics();
        point = Util.getDeviceSize(context);
        picWidth=Math.round((point.x - 16 * dm.density * 2 - 8 * dm.density) / 2);
    }

    public void setName(String name){
        this.name=name;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(com.d2cmall.buyer.R.layout.layout_topic_item,parent,false);
        return new TopicHolder(itemView,picWidth);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TopicHolder topicHolder= (TopicHolder) holder;
        String url=list.get(position).getPic();
        UniversalImageLoader.displayImage(context, Constants.IMG_HOST+url,topicHolder.iv_topic);
        topicHolder.iv_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap <String,String> map=new HashMap<>();
                map.put("位置","位置"+(position+1));
                map.put("url",list.get(position).getUrl());
                map.put("综合","位置"+(position+1)+list.get(position).getUrl());
                stat("专题","专题",map);
                Util.urlAction(context,list.get(position).getUrl());
            }
        });
    }


    private void stat(String event, String label, Map map){
        TCAgent.onEvent(context,event,label,map);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
