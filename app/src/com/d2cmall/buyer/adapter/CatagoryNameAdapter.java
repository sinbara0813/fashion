package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.holder.KindNameHolder;
import com.d2cmall.buyer.util.Util;

/**
 * Created by rookie on 2017/9/8.
 */

public class CatagoryNameAdapter extends DelegateAdapter.Adapter<KindNameHolder> {
    private Context mContext;
    private String name;
    private String url;
    private LayoutHelper layoutHelper;

    public CatagoryNameAdapter(Context mContext,String name,String url,LayoutHelper layoutHelper){
        this.mContext=mContext;
        this.name=name;
        this.url=url;
        this.layoutHelper=layoutHelper;
    }
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public KindNameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_type_name_item,parent,false);
        return new KindNameHolder(view);
    }

    @Override
    public void onBindViewHolder(KindNameHolder holder, int position) {
        holder.textView.setText(name);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.urlAction(mContext,url);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return 10;
    }
}
