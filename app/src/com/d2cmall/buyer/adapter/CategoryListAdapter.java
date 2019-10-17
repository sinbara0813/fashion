package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.NavigationLeftBean;

import java.util.List;

/**
 * Created by rookie on 2017/7/28.
 */

public class CategoryListAdapter extends BaseAdapter {

    private Context context;
    private List<NavigationLeftBean.DataBean.NavigationsBean> list;
    private int type=0;
    private int defItem;

    public CategoryListAdapter(Context context, List<NavigationLeftBean.DataBean.NavigationsBean> list) {
        this.context =context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setDefItem(int position){
        this.defItem=position;
        notifyDataSetChanged();
    }

    public void setType(int type){
        this.type=type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (type){
            case 0:
                ViewHolder mHolder;
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(context);
                    convertView = inflater.inflate(R.layout.layout_category_list_item, null, true);
                    mHolder = new ViewHolder();
                    mHolder.textView = (TextView) convertView.findViewById(R.id.text);
                    mHolder.textViewaz= (TextView) convertView.findViewById(R.id.text_az);
                    mHolder.selected=convertView.findViewById(R.id.selected);
                    mHolder.selected_line=convertView.findViewById(R.id.selected_line);
                    mHolder.top_line=convertView.findViewById(R.id.top_line);
                    mHolder.bottom_line=convertView.findViewById(R.id.bottom_line);
                    convertView.setTag(mHolder);
                } else {
                    mHolder = (ViewHolder) convertView.getTag();
                }
                NavigationLeftBean.DataBean.NavigationsBean cl=list.get(position);
                convertView.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                mHolder.textView.setText(cl.getName());
                if(defItem==position){
                    mHolder.selected.setVisibility(View.VISIBLE);
                    mHolder.selected_line.setVisibility(View.VISIBLE);
                    mHolder.bottom_line.setVisibility(View.VISIBLE);
                    mHolder.top_line.setVisibility(View.VISIBLE);
                    mHolder.textView.setTextColor(Color.parseColor("#DE000000"));
                    mHolder.textView.setTextSize(18);
                }else{
                    mHolder.selected.setVisibility(View.INVISIBLE);
                    mHolder.selected_line.setVisibility(View.GONE);
                    mHolder.bottom_line.setVisibility(View.GONE);
                    mHolder.top_line.setVisibility(View.GONE);
                    mHolder.textView.setTextColor(Color.parseColor("#8A000000"));
                    mHolder.textView.setTextSize(14);
                }
                break;
            case 1:
                ViewHolder mHolder2;
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(context);
                    convertView = inflater.inflate(R.layout.layout_category_list_item, null, true);
                    mHolder2 = new ViewHolder();
                    mHolder2.selected=convertView.findViewById(R.id.selected);
                    mHolder2.textView = (TextView) convertView.findViewById(R.id.text);
                    mHolder2.textViewaz = (TextView) convertView.findViewById(R.id.text_az);
                    mHolder2.selected_line=convertView.findViewById(R.id.selected_line);
                    mHolder2.top_line=convertView.findViewById(R.id.top_line);
                    mHolder2.bottom_line=convertView.findViewById(R.id.bottom_line);
                    mHolder2.textViewaz.setVisibility(View.GONE);
                    convertView.setTag(mHolder2);
                } else {
                    mHolder2 = (ViewHolder) convertView.getTag();
                }
                NavigationLeftBean.DataBean.NavigationsBean cl2=list.get(position);
                convertView.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                mHolder2.textView.setText(cl2.getName());
                if(defItem==position){
                    mHolder2.selected.setVisibility(View.VISIBLE);
                    mHolder2.selected_line.setVisibility(View.VISIBLE);
                    mHolder2.bottom_line.setVisibility(View.VISIBLE);
                    mHolder2.top_line.setVisibility(View.VISIBLE);
                    mHolder2.textView.setTextColor(Color.parseColor("#DE000000"));
                    mHolder2.textView.setTextSize(18);
                }else{
                    mHolder2.selected.setVisibility(View.INVISIBLE);
                    mHolder2.bottom_line.setVisibility(View.GONE);
                    mHolder2.top_line.setVisibility(View.GONE);
                    mHolder2.selected_line.setVisibility(View.INVISIBLE);
                    mHolder2.textView.setTextColor(Color.parseColor("#8A000000"));
                    mHolder2.textView.setTextSize(14);
                }
                break;
        }
        return convertView;
    }

    class ViewHolder{
        private TextView textView,textViewaz;
        private View selected,selected_line,top_line,bottom_line;
    }
}
