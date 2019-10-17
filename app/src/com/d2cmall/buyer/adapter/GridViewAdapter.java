package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.LivePresentsBean;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    private List<LivePresentsBean.DataBean.PresentsBean> mDatas;
    private int curIndex;
    private int choosedIndex;
    private int pageSize;
    private Context context;

    public GridViewAdapter(Context context, List<LivePresentsBean.DataBean.PresentsBean> mDatas, int curIndex, int pageSize) {
        this.mDatas = mDatas;
        this.curIndex = curIndex;
        this.pageSize = pageSize;
        this.context = context;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页？mDatas.size() > (curIndex+1)*pageSize,
     * 如果够，则直接返回每一页显示的最大条目个数pageSize,
     * 如果不够，则有几项返回几,(mDatas.size() - curIndex * pageSize);(也就是最后一页的时候就显示剩余item)
     */
    @Override
    public int getCount() {
        return mDatas.size() > (curIndex + 1) * pageSize ? pageSize : (mDatas.size() - curIndex * pageSize);
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position + curIndex * pageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + curIndex * pageSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_present, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imgGift = (ImageView) convertView.findViewById(R.id.img_gift);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            viewHolder.imgCheck = (ImageView) convertView.findViewById(R.id.img_check);
            viewHolder.ll_present= (LinearLayout) convertView.findViewById(R.id.ll_present);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        int pos = position + curIndex * pageSize;
        LivePresentsBean.DataBean.PresentsBean bean = mDatas.get(pos);
        UniversalImageLoader.displayImage(context,Util.getD2cPicUrl(bean.getPic()),viewHolder.imgGift);
        viewHolder.tvName.setText(bean.getName());
        if (bean.getPrice() > 0) {
            viewHolder.tvPrice.setTextColor(ContextCompat.getColor(context, R.color.color_white));
            viewHolder.tvPrice.setText(R.string.label_gift_free);
        } else {
            viewHolder.tvPrice.setTextColor(ContextCompat.getColor(context, R.color.color_white));
            viewHolder.tvPrice.setText(R.string.label_gift_free);
        }
        if (choosedIndex == -1) {
            viewHolder.ll_present.setBackground(null);
            viewHolder.tvName.setTextColor(Color.WHITE);
            viewHolder.tvPrice.setTextColor(Color.WHITE);
        } else if (choosedIndex == pos) {
            viewHolder.ll_present.setBackgroundResource(R.drawable.bg_present_select);
            viewHolder.tvName.setTextColor(Color.parseColor("#F371A9"));
            viewHolder.tvPrice.setTextColor(Color.parseColor("#F371A9"));
        } else {
            viewHolder.ll_present.setBackground(null);
            viewHolder.tvName.setTextColor(Color.WHITE);
            viewHolder.tvPrice.setTextColor(Color.WHITE);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView imgGift;
        TextView tvName;
        TextView tvPrice;
        ImageView imgCheck;
        LinearLayout ll_present;
    }

    public void setSelectedPosition(int pageIndex, int index, boolean isCheck) {
        if (curIndex == pageIndex && choosedIndex == index) {
            return;
        }
        this.curIndex = pageIndex;
        if (isCheck) {
            this.choosedIndex = index;
        } else {
            this.choosedIndex = -1;
        }
        notifyDataSetChanged();
    }
}
