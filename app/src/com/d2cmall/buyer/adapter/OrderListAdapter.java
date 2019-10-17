package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.AllAfterSaleActivity;
import com.d2cmall.buyer.activity.AnotherPayActivity;
import com.d2cmall.buyer.activity.ApplyAfterSaleActivity;
import com.d2cmall.buyer.activity.CartActivity;
import com.d2cmall.buyer.activity.CashierActivity;
import com.d2cmall.buyer.activity.MyOrderActivity;
import com.d2cmall.buyer.activity.OrderDetailActivity;
import com.d2cmall.buyer.activity.OrderItemDetailActivity;
import com.d2cmall.buyer.activity.ReviewOrderActivity;
import com.d2cmall.buyer.activity.ShowCommendActivity;
import com.d2cmall.buyer.api.CancelOrderApi;
import com.d2cmall.buyer.api.ConfirmReceiveApi;
import com.d2cmall.buyer.api.DeleteOrderApi;
import com.d2cmall.buyer.api.InsertCartApi;
import com.d2cmall.buyer.api.OrderAddCart;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.CheckOrderSkuBean;
import com.d2cmall.buyer.bean.OrderInfoBean;
import com.d2cmall.buyer.bean.OrdersBean;
import com.d2cmall.buyer.fragment.Order1Fragment;
import com.d2cmall.buyer.holder.OrderItemHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.DialogListener;
import com.d2cmall.buyer.util.CenterAlignImageSpan;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.AddCartDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * Created by rookie on 2017/8/31.
 */

public class OrderListAdapter extends DelegateAdapter.Adapter<OrderItemHolder> {

    private Context context;
    private List<OrdersBean.DataEntity.OrdersEntity.ListEntity> list;
    private DialogListener dialogListener;
    private Order1Fragment.ConfirmRefresh confirmRefresh;
    private Handler handler_timeCurrent = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.getData() != null) {
                Bundle bundle = msg.getData();
                OrdersBean.DataEntity.OrdersEntity.ListEntity item = (OrdersBean.DataEntity.OrdersEntity.ListEntity) bundle.getSerializable("ListEntity");
                OrderItemHolder holder1 = (OrderItemHolder) msg.obj;
                showTimeDown(holder1, item);
                Message msg2 = Message.obtain();
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("ListEntity", item);
                msg2.obj = holder1;
                msg2.setData(bundle2);
                handler_timeCurrent.sendMessageDelayed(msg2,1000);
            }
        }
    };

    public OrderListAdapter(Context context, List<OrdersBean.DataEntity.OrdersEntity.ListEntity> list, DialogListener dialogListener) {
        this.context = context;
        this.list = list;
        this.dialogListener = dialogListener;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public OrderItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_order_item, parent, false);
        return new OrderItemHolder(itemView);
    }

    public void destroy() {
        if (handler_timeCurrent != null) {
            handler_timeCurrent.removeCallbacksAndMessages(null);
            handler_timeCurrent = null;
        }
    }

    private void requestCancelOrderTask(OrdersBean.DataEntity.OrdersEntity.ListEntity listEntity) {
        CancelOrderApi api = new CancelOrderApi();
        api.setOrderSn(listEntity.getOrderSn());
        dialogListener.showDialog();
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderInfoBean>() {
            @Override
            public void onResponse(OrderInfoBean orderInfoBean) {
                dialogListener.dismissDialog();
                Util.showToast(context, R.string.msg_cancel_order_ok);
                EventBus.getDefault().post(orderInfoBean.getData().getOrder());
                notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogListener.dismissDialog();
                Util.showToast(context, Util.checkErrorType(error));
            }
        });
    }


    private void showTimeDown(OrderItemHolder holder, OrdersBean.DataEntity.OrdersEntity.ListEntity listEntity) {
        if (holder == null) {
            return;
        }
        if (listEntity != null) {
            if (listEntity.getEndTime() != null) {//订单超时还没结束
                Date date = new Date();
                Date timeoutDate = Util.getDate(listEntity.getEndTime());
                if (timeoutDate == null) {
                    return;
                }
                if (date.before(timeoutDate)) {
                    holder.llMayBeGoneBottom.setVisibility(View.VISIBLE);
                    long millisUntil = timeoutDate.getTime() - date.getTime();
                    int hours = (int) (millisUntil / (60 * 60 * 1000));
                    int minutes = (int) ((millisUntil / (60 * 1000)) % 60);
                    int seconds = (int) ((millisUntil / 1000) % 60);
                    holder.tvCounter.setVisibility(View.VISIBLE);
                    holder.tvCounter.setText(context.getString(R.string.label_order1_timeout,
                            hours, minutes / 10, minutes % 10, seconds / 10, seconds % 10));
                    holder.anotherPayTv.setVisibility(View.VISIBLE);
                } else {//已超时，隐藏底部两个按钮
                    holder.tvCounter.setVisibility(View.GONE);
                    holder.llMayBeGoneBottom.setVisibility(View.GONE);
                    holder.tvOrderTimeout.setText(R.string.label_order_timeover);
                    listEntity.setOrderStatusCode(-3);
                    holder.anotherPayTv.setVisibility(View.GONE);
                }
            } else {//没有超时字段返回，走正常路径
                holder.timeoutLayout.setVisibility(View.GONE);
                holder.anotherPayTv.setVisibility(View.GONE);
                if (listEntity.getOrderStatusName().equals(context.getString(R.string.label_waiting_payment)) ||
                        listEntity.isCancel()) {
                    holder.llMayBeGoneBottom.setVisibility(View.VISIBLE);
                    if (listEntity.getOrderStatusName().equals(context.getString(R.string.label_waiting_payment))) {
                        holder.rightButton.setVisibility(View.VISIBLE);
                    } else {
                        holder.rightButton.setVisibility(View.GONE);
                    }
                    if (listEntity.isCancel()) {
                        holder.leftButton.setVisibility(View.VISIBLE);
                    } else {
                        holder.leftButton.setVisibility(View.GONE);
                    }
                } else {
                    holder.llMayBeGoneBottom.setVisibility(View.GONE);
                }
            }
        }
    }

    public void setConfirmRefresh(Order1Fragment.ConfirmRefresh confirmRefresh) {
        this.confirmRefresh = confirmRefresh;
    }


    @Override
    public void onBindViewHolder(final OrderItemHolder holder, int position) {
        holder.orderContainerLayout.removeAllViews();
        holder.setIsRecyclable(false);
        final OrdersBean.DataEntity.OrdersEntity.ListEntity data = list.get(position);
        List<OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity> orders = data.getItems();
        holder.tvOrderId.setText(context.getString(R.string.label_order_id, data.getOrderSn()));

        if (data.getTotalPay() < 0) {
            holder.tvOrderTotal.setText(context.getString(R.string.label_product_total, String.valueOf(data.getItemTotal()),
                    Util.getNumberFormat(0)));
        } else {
            holder.tvOrderTotal.setText(context.getString(R.string.label_product_total, String.valueOf(data.getItemTotal()),
                    Util.getNumberFormat(data.getTotalPay())));
        }
        showTimeDown(holder, data);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data != null) {
                    //if (data.getOrderStatusName().equals("待付款") || data.getOrderStatusName().equals("用户取消") || data.getOrderStatusName().equals("平台取消")) {
                    if (!data.isExcrete()) {
                        Intent intent = new Intent(context, OrderDetailActivity.class);
                        //代付款砍价代付款吧砍价的倒计时带到详情页,详情页暂时没有砍价的支付的截止时间
                        if("bargain".equals(data.getType()) && data.getOrderStatusCode()==1){
                            intent.putExtra("bargainEndTime", data.getEndTime());
                        }
                        intent.putExtra("orderSn", data.getOrderSn());
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, OrderItemDetailActivity.class);
                        intent.putExtra("orderSn", String.valueOf(data.getItems().get(0).getId()));
                        intent.putExtra("orderDetail", data);
                        context.startActivity(intent);
                    }
                }
            }
        });
        //获取每个商品的skuId,拼接成一个字符串以逗号相隔
        StringBuilder skuBuffer = new StringBuilder();
        final List<String> imgs = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            skuBuffer.append(String.valueOf(orders.get(i).getSkuId()) + ",");
            imgs.add(orders.get(i).getProductImg());
        }
        skuBuffer.deleteCharAt(skuBuffer.length() - 1);
        final String skuIds = skuBuffer.toString();
        //再次购买
        holder.btnBuyAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkOrderSku(data);
            }
        });
        int rightButtonCount = 0;
        String status;
        final int statusCode = data.getOrderStatusCode();
        switch (statusCode) {
            case -3:
                status = "超时关闭";
                holder.llMayBeGoneBottom.setVisibility(View.GONE);
                break;
            case -1:
                status = "用户取消";
                holder.llMayBeGoneBottom.setVisibility(View.VISIBLE);
                holder.leftButton.setText("删除订单");
                holder.rightButton.setVisibility(View.GONE);
                //holder.llMayBeGoneBottom.setVisibility(View.GONE);
                break;
            case 0:
                status = "未处理";
                holder.llMayBeGoneBottom.setVisibility(View.GONE);
                break;
            case 1:
                status = "待付款";
                if (data.getOrderStatusName().equals("待付款")) {
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ListEntity", data);
                    msg.obj = holder;
                    msg.setData(bundle);
                    handler_timeCurrent.sendMessageDelayed(msg,1000);
                }
                holder.llMayBeGoneBottom.setVisibility(View.VISIBLE);
                holder.leftButton.setText("取消订单");
                holder.rightButton.setText("立即付款");
                break;
            case 3:
                status = "待发货";
                if (data.getItems().get(0).getAfter() == 1 && data.getItems().get(0).getAftering().equals("none")) {
                    if (data.getItems().get(0).getAfterApply().getRefund() > 0 || data.getItems().get(0).getAfterApply().getReship() > 0) {
                        holder.llMayBeGoneBottom.setVisibility(View.VISIBLE);
                        holder.leftButton.setText("申请售后");
                        holder.rightButton.setVisibility(View.GONE);
                    }
                }
                if (data.getItems().get(0).getAfter() == 1 && !data.getItems().get(0).getAftering().equals("none")) {
                    holder.leftButton.setText("售后跟踪");
                }
                // holder.llMayBeGoneBottom.setVisibility(View.GONE);
                break;
            case 4:
                status = "已发货";
                holder.llMayBeGoneBottom.setVisibility(View.VISIBLE);
                holder.leftButton.setText("查看物流");
                holder.rightButton.setText("确认收货");
                holder.rightButton.setVisibility(View.VISIBLE);
                if (data.getItems().get(0).getItemStatus().equals("已签收")) {
                    holder.rightButton.setText("评价晒单");
                }

                if (data.getItems().get(0).getIsComment() == 1) {
                    holder.rightButton.setText("评价晒单");
                }

                if (!data.getItems().get(0).getAftering().equals("none")) {
                    holder.rightButton.setText("售后跟踪");
                }
                if (data.getItems().get(0).getCommentId() > 0) {
                    holder.rightButton.setText("查看评价");
                    holder.rightButton.setVisibility(View.VISIBLE);
                }

                if (data.getItems().get(0).getCommentId() > 0) {
                    holder.rightButton.setText("查看评价");
                    holder.rightButton.setVisibility(View.VISIBLE);
                }

                //holder.llMayBeGoneBottom.setVisibility(View.GONE);
                break;
            case 8:
                status = "订单已完成";
                holder.llMayBeGoneBottom.setVisibility(View.VISIBLE);
                holder.leftButton.setVisibility(View.GONE);
                if (data.getItems().get(0).getAfter() == 1 && data.getItems().get(0).getAftering().equals("none")) {
                    if (data.getItems().get(0).getAfterApply().getRefund() > 0 || data.getItems().get(0).getAfterApply().getReship() > 0) {
                        holder.leftButton.setVisibility(View.VISIBLE);
                        holder.leftButton.setText("申请售后");
                    }
                }
                holder.rightButton.setText("评价晒单");
                if (data.getItems().get(0).getIsComment() != 1 || !data.getItems().get(0).getAftering().equals("none")) {
                    holder.rightButton.setVisibility(View.GONE);
                }
                if (data.getItems().get(0).getCommentId() > 0) {
                    holder.rightButton.setText("查看评价");
                    holder.rightButton.setVisibility(View.VISIBLE);
                }
                if (data.getItems().get(0).getAfter() == 1 && !data.getItems().get(0).getAftering().equals("none")) {
                    holder.leftButton.setText("售后跟踪");
                }
                //holder.llMayBeGoneBottom.setVisibility(View.GONE);
                break;
            default:
                status = "未知";

        }

        if (data.getItems().get(0).getIsComment() > 0 && data.getItems().get(0).getAftering().equals("none")) {
            if(MyOrderActivity.point!=null && MyOrderActivity.point>0){
                ViewGroup.LayoutParams layoutParams = holder.rightButton.getLayoutParams();
                layoutParams.width= ScreenUtil.dip2px(100);
                holder.rightButton.setText(context.getString(R.string.label_comment_reward,MyOrderActivity.point));
                holder.rightButton.setLayoutParams(layoutParams);
            }else{
                ViewGroup.LayoutParams layoutParams = holder.rightButton.getLayoutParams();
                layoutParams.width= ScreenUtil.dip2px(80);
                holder.rightButton.setLayoutParams(layoutParams);
                holder.rightButton.setText(context.getString(R.string.label_goto_comment));
            }
        }

        holder.leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (statusCode) {
                    case -1:
                        requestDeleteOrderTask(data);
                        break;
                    case 1://取消订单
                        requestCancelOrderTask(data);
                        break;
                    case 4://查看物流
                        if("KAOLA".equals(data.getItems().get(0).getProductSource())){
                            Util.urlAction(context, String.format(Constants.KAOLA_WULIU_URL,
                                    data.getDeliverySn(), data.getItems().get(0).getDeliveryCorpCode(), data.getItems().get(0).getProductImg(),data.getItems().get(0).getId()));
                        }else{
                            Util.urlAction(context, String.format(Constants.WULIU_URL,
                                    data.getDeliverySn(), data.getItems().get(0).getDeliveryCorpCode(), data.getItems().get(0).getProductImg()));
                        }
                        break;
                    case 3://申请售后
                    case 8:
                        if (holder.leftButton.getText().toString().equals("申请售后")) {
                            Intent intent = new Intent(context, ApplyAfterSaleActivity.class);
                            intent.putExtra("orderSn", data.getItems().get(0).getOrderSn());
                            intent.putExtra("orderId", data.getItems().get(0).getId());
                            intent.putExtra("statusCode", data.getItems().get(0).getItemStatusCode());
                            intent.putExtra("bean", data.getItems().get(0));
                            intent.putExtra("intentFlag", 0);
                            intent.putExtra("isKaoLa", "KAOLA".equals(data.getItems().get(0).getProductSource()));//是不是考拉
                            context.startActivity(intent);
                        } else if (holder.leftButton.getText().toString().equals("售后跟踪")) {
                            if (data.getItems().get(0).getAftering().equals("exchange")) {
                                gotoAfterSale(1);
                            } else if (data.getItems().get(0).getAftering().equals("reship")) {
                                gotoAfterSale(0);
                            } else if (data.getItems().get(0).getAftering().equals("refund")) {
                                gotoAfterSale(2);
                            }
                        }
                        break;
                }
            }
        });
        holder.anotherPayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AnotherPayActivity.class);
                intent.putExtra("orderSn",data.getOrderSn());
                context.startActivity(intent);
            }
        });
        holder.rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (statusCode) {
                    case 1://立即付款
                        gotoBuy(data);
                        break;
                    case 4://确认收货
                        if (holder.rightButton.getText().toString().equals("确认收货")) {
                            requestConfirmReceiveTask(data);
                        } else if (holder.rightButton.getText().toString().equals("评价晒单")) {
                            Intent intent = new Intent(context, ReviewOrderActivity.class);
                            intent.putExtra("id", data.getItems().get(0).getId());
                            intent.putExtra("productUrl", data.getItems().get(0).getProductImg());
                            intent.putExtra("productInfo", data.getItems().get(0).getProductName());
                            intent.putExtra("price", data.getItems().get(0).getProductPrice());
                            intent.putExtra("style", context.getString(R.string.label_kongge, data.getItems().get(0).getColor(),
                                    data.getItems().get(0).getSize()));
                            intent.putExtra("intentFlag", 0);
                            context.startActivity(intent);
                        } else if (holder.rightButton.getText().toString().equals("查看评价")) {
                            Intent intent = new Intent(context, ShowCommendActivity.class);
                            intent.putExtra("id", data.getItems().get(0).getCommentId());
                            intent.putExtra("productId", data.getItems().get(0).getProductId());
                            intent.putExtra("price", String.valueOf(data.getItems().get(0).getTotalPrice()));
                            context.startActivity(intent);
                        } else if (holder.rightButton.getText().toString().equals("售后跟踪")) {
                            if (data.getItems().get(0).getAftering().equals("exchange")) {
                                gotoAfterSale(1);
                            } else if (data.getItems().get(0).getAftering().equals("reship")) {
                                gotoAfterSale(0);
                            } else if (data.getItems().get(0).getAftering().equals("refund")) {
                                gotoAfterSale(2);
                            }
                        }
                        break;
                    case 8://评价晒单
                        if (holder.rightButton.getText().toString().equals("评价晒单")) {
                            Intent intent = new Intent(context, ReviewOrderActivity.class);
                            intent.putExtra("id", data.getItems().get(0).getId());
                            intent.putExtra("productUrl", data.getItems().get(0).getProductImg());
                            intent.putExtra("productInfo", data.getItems().get(0).getProductName());
                            intent.putExtra("price", data.getItems().get(0).getProductPrice());
                            intent.putExtra("style", context.getString(R.string.label_kongge, data.getItems().get(0).getColor(),
                                    data.getItems().get(0).getSize()));
                            intent.putExtra("intentFlag", 0);
                            context.startActivity(intent);
                        } else if (holder.rightButton.getText().toString().equals("查看评价")) {
                            Intent intent = new Intent(context, ShowCommendActivity.class);
                            intent.putExtra("id", data.getItems().get(0).getCommentId());
                            intent.putExtra("productId", data.getItems().get(0).getProductId());
                            intent.putExtra("price", String.valueOf(data.getItems().get(0).getTotalPrice()));
                            context.startActivity(intent);
                        }
                        break;
                }
            }

        });
        holder.tvStatus.setText(data.getItems().get(0).getItemStatus());
        if (orders != null && orders.size() > 0) {
            holder.orderContainerLayout.setVisibility(View.VISIBLE);
            for (int i = 0; i < orders.size(); i++) {
                OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity itemsEntity = orders.get(i);
                View itemView = LayoutInflater.from(context).inflate(R.layout.layout_order_content, holder.orderContainerLayout, false);
                ImageView imgItemPic = (ImageView) itemView.findViewById(R.id.iv_image);
                TextView tvProductTitle = (TextView) itemView.findViewById(R.id.tv_product_name);
                TextView tvProductStyle = (TextView) itemView.findViewById(R.id.tv_product_style);
                View emptyView = itemView.findViewById(R.id.empty_view);
                TextView tvProductPrice = (TextView) itemView.findViewById(R.id.tv_price);
                TextView tvProductQuantity = (TextView) itemView.findViewById(R.id.tv_count);
                if (itemsEntity.getFlashPromotionId() != null && itemsEntity.getFlashPromotionId() > 0) {
                    StringBuilder builder = new StringBuilder();
                    if (!Util.isEmpty(itemsEntity.getProductName())) {
                        builder.append("   " + itemsEntity.getProductName());
                    }
                    SpannableString sb = new SpannableString(builder.toString());
                    Drawable d = context.getResources().getDrawable(R.mipmap.icon_shopcart_xsg);
                    d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                    sb.setSpan(new CenterAlignImageSpan(d), 0, 1, ImageSpan.ALIGN_BASELINE);
                    tvProductTitle.setText(sb);
                } else if (itemsEntity.getType().equals("collage")) {
                    StringBuilder builder = new StringBuilder();
                    if (!Util.isEmpty(itemsEntity.getProductName())) {
                        builder.append("   " + itemsEntity.getProductName());
                    }
                    SpannableString sb = new SpannableString(builder.toString());
                    Drawable d;
                    //参与拼团就有订单但是不一定成团
                    if(itemsEntity.getCollageStatus()==1  && !(itemsEntity.getItemStatusCode()<0) ){
                        d = context.getResources().getDrawable(R.mipmap.icon_pintuanzhong);
                        holder.orderBottomLayout.setVisibility(View.GONE);
                    }else{
                        d = context.getResources().getDrawable(R.mipmap.icon_pintuan);
                        holder.orderBottomLayout.setVisibility(View.VISIBLE);
                    }
                    d.setBounds(0, -ScreenUtil.dip2px(6), d.getIntrinsicWidth()-ScreenUtil.dip2px(4), d.getIntrinsicHeight()-ScreenUtil.dip2px(10));
                    sb.setSpan(new CenterAlignImageSpan(d), 0, 1, ImageSpan.ALIGN_BASELINE);
                    tvProductTitle.setText(sb);
                } else {
                    tvProductTitle.setText(itemsEntity.getProductName());
                }
                if (orders.size() > 1) {
                    if (i != 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    }
                }
                Glide.with(context)
                        .load(Util.getD2cProductPicUrl(itemsEntity.getProductImg(), ScreenUtil.dip2px(72), ScreenUtil.dip2px(108)))
                        .placeholder(R.mipmap.ic_logo_empty5)
                        .override(ScreenUtil.dip2px(72), ScreenUtil.dip2px(108))
                        .dontAnimate()
                        .error(R.mipmap.ic_logo_empty5)
                        .into(imgItemPic);
                //当同时满足不可七天退货，优先显示预计发货时间
                if ((data.getOrderStatusCode() == 2 || data.getOrderStatusCode() == 3) && !Util.isEmpty(itemsEntity.getEstimateDate())) {
                    Date d = DateUtil.strToDateLong(itemsEntity.getEstimateDate());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(d);
                } else {
                    if (!itemsEntity.isAfterAvail()) {
                    } else {
                    }
                }
                tvProductStyle.setText(context.getString(R.string.label_kongge, itemsEntity.getColor(),
                        itemsEntity.getSize()));
                if (itemsEntity.getPromotionPrice() > 0) {
                    tvProductPrice.setText(context.getString(R.string.label_price, Util.getNumberFormat(itemsEntity.getProductPrice() -
                            itemsEntity.getPromotionPrice() / itemsEntity.getQuantity())));
                } else {
                    tvProductPrice.setText(context.getString(R.string.label_price, Util.getNumberFormat(itemsEntity.getProductPrice())));
                }
                tvProductQuantity.setText(context.getString(R.string.label_product_quantity, itemsEntity.getQuantity()));
                holder.orderContainerLayout.addView(itemView);
            }
        } else {
            holder.orderContainerLayout.setVisibility(View.GONE);
            holder.llMayBeGoneBottom.setVisibility(View.GONE);
        }
        if (data.getOrderStatusCode() != 1) {
            holder.tvCounter.setVisibility(View.GONE);
        }
    }

    private void checkOrderSku(final OrdersBean.DataEntity.OrdersEntity.ListEntity listEntity) {
        if (listEntity.getItems().size() == 1) {//直接加入购物车
            InsertCartApi api = new InsertCartApi();
            api.setNum(listEntity.getItems().get(0).getQuantity());
            api.setSkuId(listEntity.getItems().get(0).getSkuId());
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    Intent intent = new Intent(context, CartActivity.class);
                    final ArrayList<Integer> skuids = new ArrayList<>();
                    skuids.add((int) listEntity.getItems().get(0).getSkuId());
                    intent.putIntegerArrayListExtra("skuid", skuids);
                    context.startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Util.showToast(context, Util.checkErrorType(error));
                }
            });
        } else {
            SimpleApi api = new SimpleApi();
            api.setInterPath(String.format(Constants.CHECK_ORDER_SKU, listEntity.getOrderSn()));
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CheckOrderSkuBean>() {
                @Override
                public void onResponse(CheckOrderSkuBean response) {
                    if (response.getData().getDisable() != null && response.getData().getDisable().size() > 0) {
                        if (response.getData().getAvailable() == null || response.getData().getAvailable().size() == 0) {
                            Toast.makeText(context, "订单中的商品卖光啦，再看看其它商品吧~", Toast.LENGTH_SHORT).show();
                        } else {
                            int size = response.getData().getDisable().size();
                            int size1 = response.getData().getAvailable().size();
                            List<String> imgs = new ArrayList<>();
                            final ArrayList<Integer> skuids = new ArrayList<>();
                            final ArrayList<Integer> nums = new ArrayList<>();
                            for (int i = 0; i < size; i++) {
                                imgs.add(response.getData().getDisable().get(i).getImg());
                            }
                            for (int i = 0; i < size1; i++) {
                                skuids.add((int) response.getData().getAvailable().get(i).getSkuId());
                                nums.add(getNum(listEntity, response.getData().getAvailable().get(i).getSkuId()));
                            }
                            AddCartDialog addCartDialog = new AddCartDialog(context);
                            addCartDialog.show();
                            addCartDialog.setImageUrl(imgs);
                            addCartDialog.setDialogListener(new DialogListener() {
                                @Override
                                public void showDialog() {
                                    addCartForOrder(nums, skuids);
                                }

                                @Override
                                public void dismissDialog() {

                                }
                            });
                        }
                    } else {
                        int size = response.getData().getAvailable().size();
                        final ArrayList<Integer> skuids = new ArrayList<>();
                        final ArrayList<Integer> nums = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
                            skuids.add((int) response.getData().getAvailable().get(i).getSkuId());
                            nums.add(getNum(listEntity, response.getData().getAvailable().get(i).getSkuId()));
                        }
                        addCartForOrder(nums, skuids);
                    }
                }
            });
        }
    }

    private int getNum(final OrdersBean.DataEntity.OrdersEntity.ListEntity listEntity, long skuid) {
        int size = listEntity.getItems().size();
        for (int i = 0; i < size; i++) {
            if (skuid == listEntity.getItems().get(i).getSkuId()) {
                return listEntity.getItems().get(i).getQuantity();
            }
        }
        return 1;
    }

    private void addCartForOrder(final ArrayList<Integer> nums, final ArrayList<Integer> skuids) {
        OrderAddCart addCart = new OrderAddCart();
        StringBuilder builder = new StringBuilder();
        StringBuilder builder1 = new StringBuilder();
        int size = nums.size();
        for (int i = 0; i < size; i++) {
            builder.append(nums.get(i));
            builder1.append(skuids.get(i));
            if (i != size - 1) {
                builder.append(",");
                builder1.append(",");
            }
        }
        addCart.nums = builder.toString();
        addCart.skuIds = builder1.toString();
        D2CApplication.httpClient.loadingRequest(addCart, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                Intent intent = new Intent(context, CartActivity.class);
                intent.putIntegerArrayListExtra("skuid", skuids);
                context.startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(context, Util.checkErrorType(error));
            }
        });
    }

    private void gotoAfterSale(int position) {
        Intent intent = new Intent(context, AllAfterSaleActivity.class);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }


    private void requestDeleteOrderTask(final OrdersBean.DataEntity.OrdersEntity.ListEntity data) {
        DeleteOrderApi api = new DeleteOrderApi();
        api.setOrderId((int) data.getId());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                list.remove(data);
                notifyDataSetChanged();
                Util.showToast(context, "订单已删除");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(context, Util.checkErrorType(error));
            }
        });
    }

    private void gotoBuy(OrdersBean.DataEntity.OrdersEntity.ListEntity listEntity) {
        Intent intent = new Intent(context, CashierActivity.class);
        StringBuilder designId = new StringBuilder();
        if (listEntity.getItems() != null && listEntity.getItems().size() > 0) {
            for (int i = 0; i < listEntity.getItems().size(); i++) {
                designId.append(listEntity.getItems().get(i).getDesignerId());
                if (i != listEntity.getItems().size() - 1) {
                    designId.append(",");
                }
            }
        }
        boolean isCod = false;
        if (listEntity.getItems() != null && listEntity.getItems().size() > 0) {
            for (int i = 0; i < listEntity.getItems().size(); i++) {
                if (listEntity.getItems().get(i).getIsCod() == 0) {
                    isCod = true;
                }
            }
        }
        if (listEntity.getAddress().matches("(台湾" + "|香港" + "|澳门" + "|国外)\\S+")) {
            intent.putExtra("isSupportAddress", true);
        }
        intent.putExtra("isCod", isCod);
        intent.putExtra("designId", designId.toString());
        intent.putExtra("id", listEntity.getOrderSn());
        intent.putExtra("price", listEntity.getTotalPay());
        intent.putExtra("subject", getBillSubject(listEntity));
        intent.putExtra("payStyle", listEntity.getPayParams());
        context.startActivity(intent);
    }

    public String getBillSubject(OrdersBean.DataEntity.OrdersEntity.ListEntity listEntity) {
        String productName = listEntity.getItems().get(0).getProductName();
        if (productName.length() > 20) {
            productName = productName.substring(0, 20);
        }
        String productSn = listEntity.getItems().get(0).getProductSn();
        String subject = "D2C-" + productSn + "-" + productName;
        return subject;
    }

    private void requestConfirmReceiveTask(final OrdersBean.DataEntity.OrdersEntity.ListEntity listEntity) {
        notifyDataSetChanged();
        ConfirmReceiveApi api = new ConfirmReceiveApi();
        api.setOrderItemId(listEntity.getItems().get(0).getId());
        dialogListener.showDialog();
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderInfoBean>() {
            @Override
            public void onResponse(OrderInfoBean orderInfoBean) {
                dialogListener.dismissDialog();
                Util.showToast(context, R.string.msg_confirm_receive_ok);
                confirmRefresh.clickRefresh();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogListener.dismissDialog();
                Util.showToast(context, Util.checkErrorType(error));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
