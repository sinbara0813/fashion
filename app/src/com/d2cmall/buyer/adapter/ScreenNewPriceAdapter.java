package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import java.util.List;

/**
 * Created by rookie on 2018/3/26.
 */

public class ScreenNewPriceAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private int priceSelectedPosition=-1;
    private OnSingleItemClickListener onSingleItemClickListener;

    public ScreenNewPriceAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
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
        holder.tvName.setText(list.get(position));
        if(priceSelectedPosition==position){
            holder.tvName.setTextColor(context.getResources().getColor(R.color.color_black87));
            holder.tvName.setBackgroundResource(R.drawable.border_selected);
            holder.imgName.setVisibility(View.VISIBLE);
        }else {
            holder.tvName.setTextColor(context.getResources().getColor(R.color.color_black60));
            holder.tvName.setBackgroundResource(R.drawable.border_unselect);
            holder.imgName.setVisibility(View.GONE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==priceSelectedPosition){
                    setPriceSelectedPosition(-1);
                    onSingleItemClickListener.onSingleItemClick(-1);
                }else {
                    setPriceSelectedPosition(position);
                    onSingleItemClickListener.onSingleItemClick(position);
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public interface OnSingleItemClickListener {
        void onSingleItemClick(int position);
    }

    public void setOnSingleItemClickListener(OnSingleItemClickListener onSingleItemClickListener) {
        this.onSingleItemClickListener = onSingleItemClickListener;
    }

    public void setPriceSelectedPosition(int position){
        this.priceSelectedPosition=position;
    }

    class ViewHolder {
        ImageView imgName;
        TextView tvName;
    }
}
