package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.holder.BannerHolder;
import com.d2cmall.buyer.util.Util;
import com.flyco.banner.widget.Banner.base.BaseBanner;

import java.util.List;

/**
 * Created by rookie on 2017/9/8.
 */

public class CategoryBannerAdapter extends DelegateAdapter.Adapter<BannerHolder> {
    private Context context;
    private int loadingId;
    private List<String> source;
    private VirtualLayoutManager.LayoutParams layoutParams;
    private LayoutHelper layoutHelper;
    private String url;

    public CategoryBannerAdapter(Context context, int loadingId, List<String> source,VirtualLayoutManager.LayoutParams layoutParams,LayoutHelper layoutHelper,String url){
        this.layoutHelper=layoutHelper;
        this.context = context;
        this.loadingId = loadingId;
        this.source = source;
        this.layoutParams=layoutParams;
        this.url=url;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public BannerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_banner, parent,false);
        return new BannerHolder(view);
    }

    @Override
    public void onBindViewHolder(BannerHolder holder, int position) {
        holder.itemView.setLayoutParams(layoutParams);
        holder.banner.setLoadingPic(loadingId)
                .setScaleType(true)
                .setSource(source)
                .setIndicatorHeight(0)
                .startScroll();
        holder.banner.setOnItemClickL(new BaseBanner.OnItemClickL() {
            @Override
            public void onItemClick(int position) {
                if(!Util.isEmpty(url)){
                    Util.urlAction(context,url);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
