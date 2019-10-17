package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.CountryBean;
import com.d2cmall.buyer.widget.headerview.StickyListHeadersAdapter;

import java.util.ArrayList;
import java.util.List;


public class CountryAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private LayoutInflater inflater;
    private List<CountryBean> list = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;

    public CountryAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void refresh(List<CountryBean> alist) {
        list.clear();
        list.addAll(alist);
        notifyDataSetChanged();
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final CountryBean countryBean = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_sticky_child2, parent, false);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvCountryCode = (TextView) convertView.findViewById(R.id.tv_country_code);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(String.format(context.getString(R.string.label_kongge), countryBean.getEnName(), countryBean.getCnName()));
        holder.tvCountryCode.setText(countryBean.getMobileCode());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.OnItemClickListener(countryBean, position);
                }
            }
        });
        return convertView;
    }

    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.list_item_sticky_group, parent, false);
            holder.headView = (TextView) convertView.findViewById(R.id.tv_alpha);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        holder.headView.setText(list.get(position).getGroup());
        return convertView;
    }

    public long getHeaderId(int position) {
        char[] array = list.get(position).getGroup().toCharArray();
        return (long) array[0];
    }

    class HeaderViewHolder {
        TextView headView;
    }

    class ViewHolder {
        TextView tvName;
        TextView tvCountryCode;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void OnItemClickListener(CountryBean countryBean, int position);
    }
}
