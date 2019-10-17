package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.graphics.Point;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.SectionBean;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.headerview.StickyListHeadersAdapter;

import java.util.ArrayList;


public class FiltrationLetterAdapter extends BaseAdapter implements StickyListHeadersAdapter, SectionIndexer {
    private LayoutInflater inflater;
    private ArrayList<SectionBean> list;
    private Context context;
    private int width;
    private ArrayList<SectionBean> selectedList;
    private TextView tvTitleLabel;

    public FiltrationLetterAdapter(Context context, TextView tvTitleLabel) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.tvTitleLabel = tvTitleLabel;
        list = new ArrayList<>();
        selectedList = new ArrayList<>();
        Point point = Util.getDeviceSize(context);
        width = Math.round(point.x * 4 / 5);
    }

    public void refresh(ArrayList<SectionBean> sectionBeanArrayList, ArrayList<SectionBean> mlist) {
        list.clear();
        list.addAll(sectionBeanArrayList);
        selectedList.clear();
        selectedList.addAll(mlist);
        notifyDataSetChanged();
    }

    public ArrayList<SectionBean> getSelectedList() {
        return selectedList;
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
        final SectionBean sectionBean = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_sticky_child, parent, false);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvHot = (ImageView) convertView.findViewById(R.id.tv_hot);
            holder.imgCP = (ImageView) convertView.findViewById(R.id.img_cp);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(Html.fromHtml(context.getString(R.string.label_designer_brand_name,
                sectionBean.getName(), sectionBean.getBrand())));
        if (sectionBean.getRecommend() == 1) {
            holder.tvName.setMaxWidth(width - Util.dip2px(context, 70 + 54));
            holder.tvHot.setVisibility(View.VISIBLE);
        } else {
            holder.tvHot.setVisibility(View.GONE);
            holder.tvName.setMaxWidth(width - Util.dip2px(context, 30 + 54));
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sectionBean.isChecked()) {
                    selectedList.remove(sectionBean);
                    sectionBean.setChecked(false);
                    list.set(position, sectionBean);
                    notifyDataSetChanged();
                    tvTitleLabel.setText(context.getString(R.string.label_xiegang, selectedList.size(), 3));
                } else {
                    if (selectedList.size() < 3) {
                        selectedList.add(sectionBean);
                        sectionBean.setChecked(true);
                        list.set(position, sectionBean);
                        notifyDataSetChanged();
                        tvTitleLabel.setText(context.getString(R.string.label_xiegang, selectedList.size(), 3));
                    } else {
                        Util.showToast(context, R.string.msg_three_most);
                    }
                }

            }
        });
        holder.imgCP.setVisibility(View.VISIBLE);
        if (sectionBean.isChecked()) {
            holder.imgCP.setImageResource(R.mipmap.icon_shopcart_bselected);
        } else {
            holder.imgCP.setImageResource(R.mipmap.ic_unchecked);
        }
        return convertView;
    }

    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.layout_list_parent2, parent, false);
            holder.headView = (TextView) convertView.findViewById(R.id.tv_alpha);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        holder.headView.setText(list.get(position).getLettre());
        return convertView;
    }

    private int get0To9Count() {
        int count = 0;
        if (!list.isEmpty()) {
            for (int i = list.size() - 1; i > -1; i--) {
                if (list.get(i).getLettre().equals("0-9")) {
                    count++;
                } else {
                    break;
                }
            }
        }
        return count;
    }

    public long getHeaderId(int position) {
        return list.get(position).getGroupId();
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        if (list != null) {
            int size = list.size();
            if (size > 0) {
                if (sectionIndex == 35) {
                    return size - get0To9Count();
                } else {
                    for (int i = 0; i < size; i++) {
                        String l = list.get(i).getLettre();
                        char firstChar = 0;
                        if (l.length() != 0) {
                            firstChar = l.toUpperCase().charAt(0);
                        } else {
                            firstChar = "#".charAt(0);
                        }
                        if (firstChar == sectionIndex) {
                            return i;
                        }
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    class HeaderViewHolder {
        TextView headView;
    }

    class ViewHolder {
        TextView tvName;
        ImageView tvHot;
        ImageView imgCP;
    }

}
