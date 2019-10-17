package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BrandDetailActivity;
import com.d2cmall.buyer.bean.SectionBean;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.headerview.StickyListHeadersAdapter;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;


public class LettreAdapter extends BaseAdapter implements StickyListHeadersAdapter, SectionIndexer {
    private LayoutInflater inflater;
    private ArrayList<SectionBean> list = new ArrayList<>();
    private Context context;
    private int width;
    private boolean isP=false;

    public LettreAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        Point point = Util.getDeviceSize(context);
        width = point.x;
    }

    public void refresh(ArrayList<SectionBean> sectionBeanArrayList) {
        list.clear();
        list.addAll(sectionBeanArrayList);
        notifyDataSetChanged();
    }

    public void setKeyword(boolean isP) {
        this.isP = isP;
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
        final ViewHolder holder;
        final SectionBean sectionBean = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_sticky_child, parent, false);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvHot = (ImageView) convertView.findViewById(R.id.tv_hot);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(Html.fromHtml(context.getString(R.string.label_designer_brand_name,
                sectionBean.getName(), sectionBean.getBrand())));
        if (sectionBean.getRecommend() == 1) {
            holder.tvName.setMaxWidth(width - Util.dip2px(context,40 + 54));
            holder.tvHot.setVisibility(View.VISIBLE);
        } else {
            holder.tvHot.setVisibility(View.GONE);
            holder.tvName.setMaxWidth(width - Util.dip2px(context,54));
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isP) {

                } else {
                    TCAgent.onEvent(context,"V3分类","品牌/A-Z/"+sectionBean.getName()+"/"+sectionBean.getBrand());
                    StatService.onEvent(context,"V3分类","品牌/A-Z/"+sectionBean.getName()+"/"+sectionBean.getBrand());
                    Intent intent=new Intent(context, BrandDetailActivity.class);
                    intent.putExtra("id",(int)sectionBean.getId());
                    context.startActivity(intent);
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
    }
}
