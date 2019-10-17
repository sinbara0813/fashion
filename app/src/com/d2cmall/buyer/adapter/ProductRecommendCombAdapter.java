package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.CombProductActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.ProductRelationBean;
import com.d2cmall.buyer.holder.ProductCombHolder;
import com.d2cmall.buyer.util.FullyLinearLayoutManager;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/13 13:54
 * Copyright (c) 2016 d2cmall. All rights reserved.
 * 商品详情页搭配的组合商品
 */
public class ProductRecommendCombAdapter extends DelegateAdapter.Adapter {

    private Context context;
    private List<ProductRelationBean.DataBean.ProductCombBean> combList;

    public ProductRecommendCombAdapter(Context context,List<ProductRelationBean.DataBean.ProductCombBean> combList){
        this.context=context;
        this.combList=combList;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper layoutHelper=new LinearLayoutHelper();
        return layoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_product_comb,parent,false);
        return new ProductCombHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ProductCombHolder combHolder= (ProductCombHolder) holder;
        combHolder.llComb.setVisibility(View.VISIBLE);
        combHolder.tvCombTitle.setText(combList.get(position).getName());
        combHolder.tvCombTitle.setTextSize(16);
        combHolder.rlTitle.setVisibility(View.GONE);
        combHolder.tvCombPrice.setText("¥"+ Util.getNumberFormat(combList.get(position).getPrice()));
        if (combList.get(position).getOriginalCost()>combList.get(position).getPrice()){
            combHolder.tvOriginPrice.setVisibility(View.VISIBLE);
            combHolder.tvOriginPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
            combHolder.tvOriginPrice.setText("¥"+ Util.getNumberFormat(combList.get(position).getOriginalCost()));
            combHolder.tvGapPrice.setText("立省"+(combList.get(position).getOriginalCost()-combList.get(position).getPrice())+">");
        }else{
            combHolder.tvOriginPrice.setVisibility(View.INVISIBLE);
            combHolder.tvGapPrice.setText("立即购买");
        }
        if (combList.get(position).getProducts()==null){
            return;
        }

        if (combList.get(position).getProducts().size() > 0) {
            ProductCombRvAdapter adapter = new ProductCombRvAdapter(context, combList.get(position).getProducts());
            FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            combHolder.productCombRv.requestDisallowInterceptTouchEvent(true);
            combHolder.productCombRv.setLayoutManager(layoutManager);
            combHolder.productCombRv.setAdapter(adapter);
        }
        combHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, CombProductActivity.class);
                intent.putExtra("productCombId",combList.get(position).getId());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return combList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 2;
    }
}
