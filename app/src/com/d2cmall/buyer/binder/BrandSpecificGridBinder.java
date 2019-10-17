package com.d2cmall.buyer.binder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseViewBinder;
import com.d2cmall.buyer.bean.BrandKindBean;
import com.d2cmall.buyer.holder.BrandSpecificHolder;

import java.util.List;

/**
 * Created by rookie on 2017/7/29.
 */

public class BrandSpecificGridBinder implements BaseViewBinder<BrandSpecificHolder> {
    private Context context;
    private List<BrandKindBean.DataBean.DatasBean> source;

    public BrandSpecificGridBinder(Context context, List<BrandKindBean.DataBean.DatasBean> source){
        this.context=context;
        this.source=source;
    }

    @Override
    public BrandSpecificHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_brand_specific_item,null);
        return new BrandSpecificHolder(view);
    }

    @Override
    public void onBindViewHolder(BrandSpecificHolder brandSpecificHolder, int position) {

    }

    @Override
    public void onBindViewHolderWithOffer(BrandSpecificHolder brandSpecificHolder, int position, int offsetTotal) {

    }
}
