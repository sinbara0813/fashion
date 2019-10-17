package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.RefundBeanData;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.TimelineVerticalView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HP-HP on 05-12-2015.
 */
public class RefundProgressAdapter extends RecyclerView.Adapter<RefundProgressAdapter.TimeLineViewHolder> {

    private Context mContext;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;
    private List<RefundBeanData.DataBean.RefundLogsBean> logs;

    public RefundProgressAdapter(Context context, boolean withLinePadding, List<RefundBeanData.DataBean.RefundLogsBean> logs) {
        this.mContext = context;
        mWithLinePadding = withLinePadding;
        this.logs=logs;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineVerticalView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_item_trace, parent, false);
        return new TimeLineViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {
        if (position == 0) {
            // 第一行头的竖线不显示
            holder.tvTopLine.setVisibility(View.INVISIBLE);
            holder.tvAcceptTime.setText( DateUtil.ConverToString(logs.get(position).getCreateDate()));
            holder.tvAcceptStation.setText(logs.get(position).getInfo());
            if( Util.isEmpty(logs.get(position).getCreator())){
                holder.tvOperator.setVisibility(View.GONE);
            }else{
                holder.tvOperator.setVisibility(View.VISIBLE);
                holder.tvOperator.setText("经办人:"+logs.get(position).getCreator());
            }
            holder.tvDot.setBackgroundResource(R.drawable.timelline_dot_first);
        } else {
            holder.tvTopLine.setVisibility(View.VISIBLE);
            holder.tvAcceptTime.setText( DateUtil.ConverToString(logs.get(position).getCreateDate()));
            holder.tvAcceptStation.setText(logs.get(position).getInfo());
            if( Util.isEmpty(logs.get(position).getCreator())){
                holder.tvOperator.setVisibility(View.GONE);
            }else{
                holder.tvOperator.setVisibility(View.VISIBLE);
                holder.tvOperator.setText("经办人:"+logs.get(position).getCreator());
            }
            holder.tvDot.setBackgroundResource(R.drawable.timelline_dot_normal);
        }

    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    static class TimeLineViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tvTopLine)
        TextView tvTopLine;
        @Bind(R.id.tvDot)
        TextView tvDot;
        @Bind(R.id.rlTimeline)
        RelativeLayout rlTimeline;
        @Bind(R.id.tv_accept_station)
        TextView tvAcceptStation;
        @Bind(R.id.tv_operator)
        TextView tvOperator;
        @Bind(R.id.tvAcceptTime)
        TextView tvAcceptTime;
        @Bind(R.id.rlCenter)
        RelativeLayout rlCenter;

        TimeLineViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
