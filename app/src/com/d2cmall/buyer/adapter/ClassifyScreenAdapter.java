package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.BaseAdapter;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.ClassifyScreenBean;
import com.d2cmall.buyer.bean.ScreenNewBean;
import com.d2cmall.buyer.holder.ClassifyScreenHolder;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rookie on 2018/3/23.
 */

public class ClassifyScreenAdapter extends RecyclerView.Adapter<ClassifyScreenHolder> {
    private Context context;
    private List<ScreenNewBean.DataBean.FilterBean.TopsBean> list;
    private List<ScreenNewBean.DataBean.FilterBean.CategorysBean> selectedList;
    private int selectedNum = 0;

    public ClassifyScreenAdapter(Context context, List<ScreenNewBean.DataBean.FilterBean.TopsBean> list) {
        this.context = context;
        this.list = list;
        selectedList = new ArrayList<>();
    }

    public List<ScreenNewBean.DataBean.FilterBean.TopsBean> getTopList() {
        return list;
    }

    public List<ScreenNewBean.DataBean.FilterBean.CategorysBean> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<ScreenNewBean.DataBean.FilterBean.CategorysBean> list){
        selectedList.clear();
        selectedList.addAll(list);
    }

    public void addSelected(ScreenNewBean.DataBean.FilterBean.CategorysBean data) {
        if (selectedNum < 3) {
            selectedList.add(data);
            selectedNum++;
        } else {
            Util.showToast(context, "最多只能选择3个哦~");
        }
    }


    public void removeSelected(ScreenNewBean.DataBean.FilterBean.CategorysBean data) {
        if (selectedList.contains(data)) {
            selectedList.remove(data);
            selectedNum--;
        }
    }

    @Override
    public ClassifyScreenHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_classify_item, parent, false);
        return new ClassifyScreenHolder(view);
    }

    @Override
    public void onBindViewHolder(final ClassifyScreenHolder holder, int position) {
        final ScreenNewBean.DataBean.FilterBean.TopsBean data = list.get(position);
        holder.tvName.setText(data.getName());
        ClassifyItemGridAdapter itemGridAdapter = new ClassifyItemGridAdapter(context, data.getList(), this);
        holder.itemClassifyGrid.setAdapter(itemGridAdapter);
        if (data.isSelected()) {
            holder.itemClassifyGrid.setVisibility(View.VISIBLE);
            holder.ivArrow.setImageResource(R.mipmap.icon_nav_arrow_up);
        } else {
            holder.itemClassifyGrid.setVisibility(View.GONE);
            holder.ivArrow.setImageResource(R.mipmap.ic_arrow_down);
        }
        holder.llTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.isSelected()) {
                    data.setSelected(false);
                } else {
                    data.setSelected(true);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
