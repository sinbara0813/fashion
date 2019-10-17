package com.d2cmall.buyer.binder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseViewBinder;
import com.d2cmall.buyer.holder.KindNameHolder;

/**
 * Created by rookie on 2017/8/1.
 */

public class KindNameBinder implements BaseViewBinder<KindNameHolder> {
    private Context mContext;
    private String name;
    public KindNameBinder(Context mContext, String name){
        this.mContext=mContext;
        this.name=name;
    }

    @Override
    public KindNameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_type_name_item,parent,false);
        return new KindNameHolder(view);
    }

    @Override
    public void onBindViewHolder(KindNameHolder kindNameHolder, int position) {
        kindNameHolder.textView.setText(name);
    }

    @Override
    public void onBindViewHolderWithOffer(KindNameHolder kindNameHolder, int position, int offsetTotal) {

    }
}
