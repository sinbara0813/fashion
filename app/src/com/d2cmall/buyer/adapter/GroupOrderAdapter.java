package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.CashierActivity;
import com.d2cmall.buyer.activity.CollageDetailActivity;
import com.d2cmall.buyer.activity.OrderBalanceActivity;
import com.d2cmall.buyer.activity.OrderDetailActivity;
import com.d2cmall.buyer.activity.OrderItemDetailActivity;
import com.d2cmall.buyer.bean.GroupOrderListBean;
import com.d2cmall.buyer.bean.OrdersBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.GroupOrderHolder;
import com.d2cmall.buyer.holder.OrderItemHolder;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.Date;
import java.util.List;

/**
 * Created by rookie on 2018/6/25.
 */

public class GroupOrderAdapter extends RecyclerView.Adapter<GroupOrderHolder> {

    private Context context;
    private List<GroupOrderListBean.DataBean.CollageOrders.ListBean> list;
    private Handler handler_timeCurrent = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.getData() != null) {
                Bundle bundle = msg.getData();
                GroupOrderListBean.DataBean.CollageOrders.ListBean item = (GroupOrderListBean.DataBean.CollageOrders.ListBean) bundle.getSerializable("ListEntity");
                GroupOrderHolder holder1 = (GroupOrderHolder) msg.obj;
                showTimeDown(holder1, item);
                Message msg2 = Message.obtain();
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("ListEntity", item);
                msg2.obj = holder1;
                msg2.setData(bundle2);
                handler_timeCurrent.sendMessage(msg2);
            }
        }
    };

    private void showTimeDown(GroupOrderHolder holder, GroupOrderListBean.DataBean.CollageOrders.ListBean listEntity) {
        if (holder == null) {
            return;
        }
        if (listEntity != null) {
            if (listEntity.getEndPayTime() != null) {//订单超时还没结束
                long nowTime = System.currentTimeMillis();
                if (listEntity.getEndPayTime() > nowTime) {
                    holder.tvCount.setVisibility(View.VISIBLE);
                    long millisUntil = listEntity.getEndPayTime() - nowTime;
                    int hours = (int) (millisUntil / (60 * 60 * 1000));
                    int minutes = (int) ((millisUntil / (60 * 1000)) % 60);
                    int seconds = (int) ((millisUntil / 1000) % 60);
                    holder.tvCount.setText(context.getString(R.string.label_order1_timeout,
                            hours, minutes / 10, minutes % 10, seconds / 10, seconds % 10));
                } else {//已超时，隐藏底部两个按钮
                    holder.tvCount.setVisibility(View.INVISIBLE);
                    holder.btnGoToBuy.setVisibility(View.GONE);
                }
            }
        }
    }

    public GroupOrderAdapter(Context context, List<GroupOrderListBean.DataBean.CollageOrders.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public GroupOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_group_order_item, parent, false);
        return new GroupOrderHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupOrderHolder holder, int position) {
        final GroupOrderListBean.DataBean.CollageOrders.ListBean data = list.get(position);
        holder.setIsRecyclable(false);
        holder.tvStatus.setText(data.getStatusName());
        holder.tvProductName.setText(data.getProductName());
        holder.tvOrderId.setText("订单编号:" + data.getOrderSn());
        holder.tvProductStyle.setText(data.getColor() + " " + data.getSize());
        holder.tvGroupInfo.setText(data.getCollageGroup().getMemberCount() + "人团" + "  ¥" + data.getTotalAmount());
        UniversalImageLoader.displayImage(context, data.getProductImage(), holder.ivImage);
        switch (data.getStatusX()) {
            case 1://待支付
                holder.btnGoToBuy.setVisibility(View.VISIBLE);
                holder.btnGroupDetail.setVisibility(View.VISIBLE);
                holder.btnOrderDetail.setVisibility(View.GONE);

                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putSerializable("ListEntity", data);
                msg.obj = holder;
                msg.setData(bundle);
                handler_timeCurrent.sendMessage(msg);
                break;
            case 4://拼团中
                int dpcm = data.getCollageGroup().getMemberCount() - data.getCollageGroup().getCurrentMemberCount();
                int payNot = data.getCollageGroup().getCurrentMemberCount() - data.getCollageGroup().getPayMemberCount();
                StringBuilder builder=new StringBuilder();
                builder.append(dpcm > 0 ? "还差" + dpcm + "人" :"已满员");
                builder.append(payNot>0? ","+payNot + "人正在支付":"");
                holder.tvStatus.setText(builder.toString());
                setButtonStatus(holder);
                break;
            case -2://超时关闭
                setButtonStatus(holder);
                break;
            case -1://待退款
                holder.tvStatus.setText("拼团失败,退款中");
                setButtonStatus(holder);
                break;
            case -8://退款成功
                holder.tvStatus.setText("拼团失败,退款成功");
                setButtonStatus(holder);
                break;
            case 8://拼团成功
                holder.btnGoToBuy.setVisibility(View.GONE);
                holder.btnGroupDetail.setVisibility(View.VISIBLE);
                holder.btnOrderDetail.setVisibility(View.VISIBLE);
                break;
        }
        holder.btnGoToBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//立即购买  需要订单号以及payType,subject,payStyle
                Intent intent = new Intent(context, CashierActivity.class);
                intent.putExtra("subject", data.getProductName());
                intent.putExtra("payType", "collage");
                intent.putExtra("payStyle", data.getPayParams());
                intent.putExtra("id", data.getOrderSn());
                intent.putExtra("orderId",data.getId()+"");
                context.startActivity(intent);
            }
        });
        holder.btnGroupDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//拼团详情
                Intent intent = new Intent(context, CollageDetailActivity.class);
                if (data.getStatusX() == 1) {//待支付
                    intent.putExtra("subject", data.getProductName());
                    intent.putExtra("payType", "collage");
                    intent.putExtra("payStyle", data.getPayParams());
                    intent.putExtra("id", data.getOrderSn());
                    intent.putExtra("orderId",data.getId()+"");
                }
                if (data.getStatusX() == -2) {
                    intent.putExtra("isOver", true);
                    intent.putExtra("id", data.getOrderSn());
                } else {
                    intent.putExtra("isOver", false);
                    intent.putExtra("id", data.getOrderSn());
                }
                intent.putExtra("collageId", data.getGroupId());
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//拼团详情
                Intent intent = new Intent(context, CollageDetailActivity.class);
                if (data.getStatusX() == 1) {//待支付
                    intent.putExtra("subject", data.getProductName());
                    intent.putExtra("payType", "collage");
                    intent.putExtra("payStyle", data.getPayParams());
                    intent.putExtra("id", data.getOrderSn());
                }
                if (data.getStatusX() == -2) {
                    intent.putExtra("isOver", true);
                    intent.putExtra("id", data.getOrderSn());
                } else {
                    intent.putExtra("isOver", false);
                    intent.putExtra("id", data.getOrderSn());
                }
                intent.putExtra("collageId", data.getGroupId());
                context.startActivity(intent);
            }
        });
        holder.btnOrderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//订单详情
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("orderSn", data.getOrderSn());
                context.startActivity(intent);
            }
        });
    }

    private void setButtonStatus(GroupOrderHolder holder) {
        holder.btnGoToBuy.setVisibility(View.GONE);
        holder.btnGroupDetail.setVisibility(View.VISIBLE);
        holder.btnOrderDetail.setVisibility(View.GONE);
    }

    public void destroy() {
        if (handler_timeCurrent != null) {
            handler_timeCurrent.removeCallbacksAndMessages(null);
            handler_timeCurrent = null;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
