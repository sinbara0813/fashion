package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.ShareBean;
import com.d2cmall.buyer.holder.ShowRelationHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.tendcloud.tenddata.TCAgent;

import java.util.List;

/**
 * Created by rookie on 2017/9/12.
 */

public class ShowRelationAdapter extends RecyclerView.Adapter<ShowRelationHolder> {
    private Context context;
    private List<ShareBean.DataEntity.MemberSharesEntity.ListEntity.ProductRelationsBean> list;

    public ShowRelationAdapter(Context context, List<ShareBean.DataEntity.MemberSharesEntity.ListEntity.ProductRelationsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ShowRelationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_relation_product_item, parent, false);
        return new ShowRelationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShowRelationHolder holder, final int position) {
        if (list.size() == 1) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            layoutParams.height = ScreenUtil.dip2px(120);
            layoutParams.rightMargin = ScreenUtil.dip2px(16);
            layoutParams.leftMargin = ScreenUtil.dip2px(16);
            holder.itemView.setLayoutParams(layoutParams);
        }
        holder.tvDesigner.setText(list.get(position).getDesigner());
        holder.tvProductName.setText(list.get(position).getName());
        holder.tvPrice.setText("¥" + Util.getNumberFormat(list.get(position).getPrice()));
        UniversalImageLoader.displayImage(context, list.get(position).getImg(), holder.ivProduct);
        if (list.get(position).getStore() < 1) {
            holder.tv_no_store.setVisibility(View.VISIBLE);
            holder.view_no_store.setVisibility(View.VISIBLE);
        } else {
            holder.tv_no_store.setVisibility(View.GONE);
            holder.view_no_store.setVisibility(View.GONE);
        }
        if (list.get(position).getMark() == 0) {
            holder.tv_no_store.setText("已下架");
            holder.tv_no_store.setVisibility(View.VISIBLE);
            holder.view_no_store.setVisibility(View.VISIBLE);
            holder.tvPrice.setTextColor(Color.parseColor("#61000000"));
            holder.tvPrice.setTextSize(14);
            holder.tvPrice.setText("暂无报价");
        } else {
            if (list.get(position).getStore() <= 0) {
                holder.tv_no_store.setText("已售罄");
                holder.tv_no_store.setVisibility(View.VISIBLE);
                holder.view_no_store.setVisibility(View.VISIBLE);
            } else {
                holder.tv_no_store.setVisibility(View.GONE);
                holder.view_no_store.setVisibility(View.GONE);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", (long) list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
