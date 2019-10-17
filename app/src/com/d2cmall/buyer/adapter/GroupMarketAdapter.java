package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.GroupListBean;
import com.d2cmall.buyer.holder.GroupMarketHolder;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.tendcloud.tenddata.TCAgent;

import java.util.List;

/**
 * Created by rookie on 2018/6/21.
 */

public class GroupMarketAdapter extends RecyclerView.Adapter<GroupMarketHolder> {

    private Context context;
    private List<GroupListBean.DataBean.CollageListBean.ListBean> list;
    private GroupBuyCallBack groupBuyCallBack;

    public GroupMarketAdapter(Context context, List<GroupListBean.DataBean.CollageListBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setGroupBuyCallBack(GroupBuyCallBack groupBuyCallBack) {
        this.groupBuyCallBack = groupBuyCallBack;
    }

    public interface GroupBuyCallBack {
        void notice(int id);
    }

    @Override
    public GroupMarketHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_group_buy_item, parent, false);
        return new GroupMarketHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupMarketHolder holder, int position) {
        final GroupListBean.DataBean.CollageListBean.ListBean data = list.get(position);
        holder.tvProductName.setText(data.getProduct().getName());
        holder.tvSubTitle.setText(data.getProduct().getSubTitle());
        holder.tvGroupNum.setText(data.getMemberCount() + "人团:");
        holder.tvPrice.setText("¥" + data.getProduct().getCollagePrice());
        holder.tvOriginPrice.setText("原价:¥" + data.getProduct().getMinPrice());
        UniversalImageLoader.displayImage(context, data.getProduct().getImg(), holder.ivProduct);
        final boolean hasStart;
        if (data.getPromotionStatus() == 0) {//还没有开始
            hasStart = false;
            holder.tvButton.setText("提醒我");
            holder.tvButton.setBackgroundColor(Color.parseColor("#FF232427"));
        } else {//已经开始
            hasStart = true;
            holder.tvButton.setText("去拼团");
            holder.tvButton.setBackgroundColor(Color.parseColor("#FFF23365"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", (long) data.getProduct().getId());
                intent.putExtra("collageId", data.getId());
                context.startActivity(intent);
            }
        });
        holder.tvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasStart) {//已经开始跳转商品详情页,多传一个拼团id
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("id", (long) data.getProduct().getId());
                    intent.putExtra("collageId", data.getId());
                    context.startActivity(intent);
                } else {//没开始就是提醒我
                    if (groupBuyCallBack != null) {
                        groupBuyCallBack.notice(data.getId());
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
