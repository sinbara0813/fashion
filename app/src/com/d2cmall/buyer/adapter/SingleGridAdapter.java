package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.ScreenBean;

import java.util.List;

/**
 * Created by rookie on 2017/8/22.
 */

public class SingleGridAdapter extends BaseAdapter {

    private Context context;
    private OnSingleItemClickListener onSingleItemClickListener;
    private List<Object> list;
    private int selectedPosition=-1;
    private int thirdSelectedPosition=-1;
    private int secondSelectedPostion=-1;
    private int priceSelectedPosition=-1;

    public SingleGridAdapter(Context context, List<Object> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelectedPosition(int position){
        this.selectedPosition=position;
    }

    public void setSecondSelectedPostion(int position){
        this.secondSelectedPostion=position;
    }

    public void setThirdSelectedPosition(int position){
        this.thirdSelectedPosition=position;
    }

    public void setPriceSelectedPosition(int position){
        this.priceSelectedPosition=position;
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
        if(list.get(position) instanceof ScreenBean.DataBean.CategoryBean){
            ScreenBean.DataBean.CategoryBean firstTier= (ScreenBean.DataBean.CategoryBean) list.get(position);
            holder.tvName.setText(firstTier.getTopCatagory().getName());
            if(selectedPosition==position){
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
                    if(position==selectedPosition){
                        setSelectedPosition(-1);
                    }else {
                        setSelectedPosition(position);
                        onSingleItemClickListener.onSingleItemClick(position);
                    }
                    notifyDataSetChanged();
                }
            });
        }else if(list.get(position) instanceof ScreenBean.DataBean.CategoryBean.ProductCategoryBean){
            ScreenBean.DataBean.CategoryBean.ProductCategoryBean secondTier= (ScreenBean.DataBean.CategoryBean.ProductCategoryBean) list.get(position);
            holder.tvName.setText(secondTier.getName());
            if(secondSelectedPostion==position){
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
                    if(position==secondSelectedPostion){
                        setSecondSelectedPostion(-1);
                    }else {
                        setSecondSelectedPostion(position);
                        onSingleItemClickListener.onSingleItemClick(position);
                    }
                    notifyDataSetChanged();
                }
            });
        }else if(list.get(position) instanceof ScreenBean.DataBean.CategoryBean.ProductCategoryBean.ChildrenBean){
            ScreenBean.DataBean.CategoryBean.ProductCategoryBean.ChildrenBean thirdTier= (ScreenBean.DataBean.CategoryBean.ProductCategoryBean.ChildrenBean) list.get(position);
            holder.tvName.setText(thirdTier.getName());
            if(thirdSelectedPosition==position){
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
                    if(position==thirdSelectedPosition){
                        setThirdSelectedPosition(-1);
                    }else {
                        setThirdSelectedPosition(position);
                        onSingleItemClickListener.onSingleItemClick(position);
                    }
                    notifyDataSetChanged();
                }
            });
        }else if(list.get(position) instanceof String){
            holder.tvName.setText((String)list.get(position));
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
        }
        return convertView;
    }

    public interface OnSingleItemClickListener {
        void onSingleItemClick(int position);
    }

    class ViewHolder {
        ImageView imgName;
        TextView tvName;
    }

    public void clearAll(){
        int selectedPosition=-1;
        thirdSelectedPosition=-1;
        secondSelectedPostion=-1;
        priceSelectedPosition=-1;
        notifyDataSetChanged();
    }

    public void setOnSingleItemClickListener(OnSingleItemClickListener onSingleItemClickListener) {
        this.onSingleItemClickListener = onSingleItemClickListener;
    }
}
