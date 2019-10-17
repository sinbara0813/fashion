package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.ClassifyScreenBean;
import com.d2cmall.buyer.bean.ScreenBrandBean;
import com.d2cmall.buyer.bean.ScreenNewBean;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Created by rookie on 2018/3/23.
 */

public class ClassifyItemGridAdapter extends android.widget.BaseAdapter {

    private Context context;
    private List<ScreenNewBean.DataBean.FilterBean.CategorysBean> list;
    private ClassifyScreenAdapter adapter;

    public ClassifyItemGridAdapter(Context context, List<ScreenNewBean.DataBean.FilterBean.CategorysBean> list, ClassifyScreenAdapter adapter) {
        this.context = context;
        this.list = list;
        this.adapter = adapter;
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
    public View getView(int i, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final ScreenNewBean.DataBean.FilterBean.CategorysBean data = list.get(i);
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
                    adapter.removeSelected(data);
                } else {
                    if (adapter.getSelectedList().size() < 3) {
                        data.setSelected(true);
                        adapter.addSelected(data);
                    } else {
                        Util.showToast(context, "最多只能选择3个哦~");
                    }
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView imgName;
        TextView tvName;
    }
}
