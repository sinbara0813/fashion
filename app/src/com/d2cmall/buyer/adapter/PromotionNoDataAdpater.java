package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/28.
 * Description : PromotionNoDataAdpater
 */

public class PromotionNoDataAdpater extends DelegateAdapter.Adapter {
    private Context mContext;

    public PromotionNoDataAdpater(Context context) {
        this.mContext = context;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_empty_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.imgHint.setVisibility(View.VISIBLE);
        viewHolder.emptyHintLayout.setPadding(0, Util.dip2px(mContext,40),0,0);
        viewHolder.imgHint.setImageResource(R.mipmap.icon_empty_default);
        viewHolder.btnReload.setVisibility(View.VISIBLE);
        viewHolder.btnReload.setText("暂无数据");
        viewHolder.btnReload.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.img_hint)
        ImageView imgHint;
        @Bind(R.id.btn_reload)
        TextView btnReload;
        @Bind(R.id.empty_hint_layout)
        LinearLayout emptyHintLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
