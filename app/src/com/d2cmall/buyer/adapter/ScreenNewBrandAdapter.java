package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.BaseAdapter;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.ScreenBrandBean;
import com.d2cmall.buyer.bean.ScreenNewBean;
import com.d2cmall.buyer.listener.OnItemClickListener;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rookie on 2018/3/26.
 */

public class ScreenNewBrandAdapter extends BaseAdapter {
    private Context context;
    private List<ScreenNewBean.DataBean.FilterBean.DesignersBean> list;
    private List<ScreenNewBean.DataBean.FilterBean.DesignersBean> useList = new ArrayList<>();
    private int selectedCount = 0;
    private List<String> selectList;
    private OnItemClickListener onItemClickListener;
    private OnItemDeleteListener onItemDeleteListener;

    public ScreenNewBrandAdapter(Context context, List<ScreenNewBean.DataBean.FilterBean.DesignersBean> list2) {
        this.context = context;
        selectList = new ArrayList<>();
        this.list = list2;
        if (list.size() >= 9) {
            for (int i = 0; i < 9; i++) {
                useList.add(list.get(i));
            }
        } else {
            useList.addAll(list);
        }

    }

    public void clearAll() {
        if (selectList != null) {
            selectList.clear();
        }
        selectedCount = 0;
    }

    public interface OnItemDeleteListener {
        void OnItemDelete(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }


    @Override
    public int getCount() {
        return useList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addMoreList() {
        useList.clear();
        if (list.size() >= 18) {
            for (int i = 0; i < 18; i++) {
                useList.add(list.get(i));
            }
        } else {
            useList.addAll(list);
        }

        notifyDataSetChanged();
    }

    public void cutLessList() {
        useList.clear();
        if (list.size() >= 9) {
            for (int i = 0; i < 9; i++) {
                useList.add(list.get(i));
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                useList.add(list.get(i));
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final ScreenNewBean.DataBean.FilterBean.DesignersBean data = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_screen_item, parent, false);
            holder.imgName = (ImageView) convertView.findViewById(R.id.iv_screen_mark);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(data.getName());
        if (data.isSelected()) {
            holder.tvName.setTextColor(context.getResources().getColor(R.color.color_black87));
            holder.tvName.setBackgroundResource(R.drawable.border_selected);
            holder.imgName.setVisibility(View.VISIBLE);
        } else {
            holder.tvName.setTextColor(context.getResources().getColor(R.color.color_black60));
            holder.tvName.setBackgroundResource(R.drawable.border_unselect);
            holder.imgName.setVisibility(View.GONE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.isSelected()) {
                    data.setSelected(false);
                    selectedCount--;
                    selectList.remove(String.valueOf(data.getSourceId()));
                    onItemDeleteListener.OnItemDelete(v, position);
                } else {
                    if (selectedCount < 3) {
                        data.setSelected(true);
                        selectList.add(String.valueOf(data.getSourceId()));
                        selectedCount++;
                        onItemClickListener.itemClick(v, position);
                    } else {
                        Util.showToast(context, "最多只能选择3个哦~");
                    }
                }

                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public void deleteSelectedItem(ScreenNewBean.DataBean.FilterBean.DesignersBean typedesignerBean){
        selectedCount--;
        for(int i=0;i<list.size();i++){
            if(typedesignerBean.getSourceId()==list.get(i).getSourceId()){
                list.get(i).setSelected(false);
                return;
            }
        }
    }

    public void clearAllSelect() {
        this.selectedCount=0;
        selectList.clear();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setSelected(false);
        }
    }

    public void setSelectedCount(int num) {
        this.selectedCount = num;
//        for(int i=0;i<list.size();i++){
//            list.get(i).isSelected=false;
//        }
        notifyDataSetChanged();
    }

    public List<String> getSelectList() {
        return selectList;
    }

    class ViewHolder {
        ImageView imgName;
        TextView tvName;
        boolean isSeleced = false;
    }
}
