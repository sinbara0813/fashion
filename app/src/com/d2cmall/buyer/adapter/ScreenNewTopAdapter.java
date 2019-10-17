package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.ScreenNewBean;
import com.d2cmall.buyer.listener.OnItemClickListener;

import java.util.ArrayList;

/**
 * Created by rookie on 2018/3/26.
 */

public class ScreenNewTopAdapter extends android.widget.BaseAdapter {
    private ArrayList<ScreenNewBean.DataBean.FilterBean.TopsBean> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public ScreenNewTopAdapter(ArrayList<ScreenNewBean.DataBean.FilterBean.TopsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_screen_item, parent, false);
            holder.imgName = (ImageView) convertView.findViewById(R.id.iv_screen_mark);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ScreenNewBean.DataBean.FilterBean.TopsBean topsBean = list.get(position);
        holder.tvName.setText(topsBean.getName());
//        if (topsBean.isSelected()) {
//            holder.tvName.setTextColor(context.getResources().getColor(R.color.color_black87));
//            holder.tvName.setBackgroundResource(R.drawable.border_selected);
//            holder.imgName.setVisibility(View.VISIBLE);
//        } else {
        holder.tvName.setTextColor(context.getResources().getColor(R.color.color_black60));
        holder.tvName.setBackgroundResource(R.drawable.border_unselect);
        holder.imgName.setVisibility(View.GONE);
        //}
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.itemClick(view, position);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView imgName;
        TextView tvName;
    }
}
