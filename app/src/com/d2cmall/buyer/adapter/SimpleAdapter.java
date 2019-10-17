package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import java.util.ArrayList;

public class SimpleAdapter extends BaseAdapter {

    private ArrayList<String> mData;
    private Context mContext;

    public SimpleAdapter(Context mContext, ArrayList<String> phones) {
        this.mContext = mContext;
        this.mData = phones;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public String getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.list_item_simple, viewGroup, false);
            ViewHolder holder = new ViewHolder();
            holder.tvSimple = (TextView) view.findViewById(R.id.tv_simple);
            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder != null) {
            holder.tvSimple.setText(getItem(i));
        }
        return view;
    }

    private class ViewHolder {
        TextView tvSimple;
    }
}
