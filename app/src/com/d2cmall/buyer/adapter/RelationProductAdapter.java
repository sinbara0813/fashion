package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.RelationProductBean;
import com.d2cmall.buyer.holder.RelationProductHolder;
import com.d2cmall.buyer.listener.OnAddressDeleteClickListener;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rookie on 2017/9/11.
 */

public class RelationProductAdapter extends RecyclerView.Adapter<RelationProductHolder> {

    private int hasSelectedCount = 0;
    private Context context;
    private List<RelationProductBean.DataBean.ProductsBean.ListBean> list;
    private List<RelationProductBean.DataBean.ProductsBean.ListBean> selectList;
    private OnAddressDeleteClickListener onAddressDeleteClickListener;
    private int type;

    public RelationProductAdapter(Context context, List<RelationProductBean.DataBean.ProductsBean.ListBean> list, int type) {
        this.context = context;
        this.list = list;
        this.type = type;
        selectList = new ArrayList<>();
    }

    public void setHasSelectedCount(int hasSelectedCount) {
        this.hasSelectedCount = hasSelectedCount;
    }

    public void setHasSelectedList(List<RelationProductBean.DataBean.ProductsBean.ListBean> list) {
        selectList.addAll(list);
    }

    public void setOnDeleteListener(OnAddressDeleteClickListener onAddressDeleteClickListener) {
        this.onAddressDeleteClickListener = onAddressDeleteClickListener;
    }

    @Override
    public RelationProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_relation_item, parent, false);
        return new RelationProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RelationProductHolder holder, final int position) {
        final RelationProductBean.DataBean.ProductsBean.ListBean data = list.get(position);
        if (type == 0) {
            UniversalImageLoader.displayImage(context, data.getImg(), holder.ivProduct);
            if (data.isSelected()) {
                holder.ivSelect.setImageResource(R.mipmap.icon_shopcart_bselected);
            } else {
                holder.ivSelect.setImageResource(R.mipmap.icon_shopcart_unbselected);
            }
            holder.tvPrice.setText(Util.getNumberFormat(data.getPrice()));
            holder.tvProductName.setText(data.getName());
            if (data.isSelected()) {
                holder.ivSelect.setImageResource(R.mipmap.icon_shopcart_bselected);
            } else {
                holder.ivSelect.setImageResource(R.mipmap.icon_shopcart_unbselected);
            }
            holder.ivSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.isSelected()) {
                        data.setSelected(false);
                        hasSelectedCount--;
                        holder.ivSelect.setImageResource(R.mipmap.icon_shopcart_unbselected);
                        for(int i=0;i<selectList.size();i++){
                            if(data.getId()==selectList.get(i).getId()){
                                selectList.remove(i);
                            }
                        }
                    } else {
                        if (hasSelectedCount >= 9) {
                            Util.showToast(context, "最多只能选9个哦~");
                            return;
                        }
                        data.setSelected(true);
                        hasSelectedCount++;
                        holder.ivSelect.setImageResource(R.mipmap.icon_shopcart_bselected);
                        selectList.add(data);
                    }
                }
            });
        } else {
            UniversalImageLoader.displayImage(context, data.getImg(), holder.ivProduct);
            holder.tvPrice.setText(Util.getNumberFormat(data.getPrice()));
            holder.tvProductName.setText(data.getName());
            holder.ivSelect.setImageResource(R.mipmap.icon_fashion_delete);
            holder.ivSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(data);
                    if (onAddressDeleteClickListener != null) {
                        onAddressDeleteClickListener.clickDelete(v, position);
                    }
                    notifyDataSetChanged();
                }
            });
        }
        holder.ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", (long) data.getId());
                context.startActivity(intent);
            }
        });
    }

    public List<RelationProductBean.DataBean.ProductsBean.ListBean> getSelectList() {
        return selectList;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
