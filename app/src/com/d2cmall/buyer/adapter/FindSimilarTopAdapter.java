package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.holder.FindSimilarTopHolder;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

/**
 * Created by rookie on 2018/3/21.
 */

public class FindSimilarTopAdapter extends DelegateAdapter.Adapter<FindSimilarTopHolder> {

    private Context context;
    private GoodsBean.DataBean.ProductsBean.ListBean data;

    public FindSimilarTopAdapter(Context context, GoodsBean.DataBean.ProductsBean.ListBean data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }

    @Override
    public FindSimilarTopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_find_similar_top, parent, false);
        return new FindSimilarTopHolder(view);
    }

    @Override
    public void onBindViewHolder(FindSimilarTopHolder holder, int position) {
        UniversalImageLoader.displayImage(context, data.getImg(), holder.ivProductTop, R.mipmap.ic_logo_empty5);
        holder.tvNameProduct.setText(data.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", data.getId());
                context.startActivity(intent);
            }
        });
        Integer promotionId = data.getPromotionId();
        //显示价格()
        holder.tvMinPrice.setText("¥" + Util.getNumberFormat(data.getMinPrice()));
        if (promotionId != null && promotionId > 0) {
            if (data.getMinPrice() < data.getSalePrice()) {
                holder.tvHighPrice.setVisibility(View.VISIBLE);
                holder.tvHighPrice.setText("¥" + Util.getNumberFormat(data.getSalePrice()));
                holder.tvHighPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                holder.tvHighPrice.setVisibility(View.GONE);
            }
        } else {
            if (data.getMinPrice() < data.getOriginalPrice()) {
                holder.tvHighPrice.setVisibility(View.VISIBLE);
                holder.tvHighPrice.setText("¥" + Util.getNumberFormat(data.getOriginalPrice()));
                holder.tvHighPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                holder.tvHighPrice.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
