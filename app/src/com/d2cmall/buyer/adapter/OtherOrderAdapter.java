package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.d2cmall.buyer.activity.ApplyAfterSaleActivity;
import com.d2cmall.buyer.activity.CartActivity;
import com.d2cmall.buyer.activity.MyOrderActivity;
import com.d2cmall.buyer.activity.OrderItemDetailActivity;
import com.d2cmall.buyer.activity.ReviewOrderActivity;
import com.d2cmall.buyer.api.ConfirmReceiveApi;
import com.d2cmall.buyer.api.InsertCartApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.OrderInfoBean;
import com.d2cmall.buyer.bean.OrdersBean;
import com.d2cmall.buyer.holder.SingleOrderHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.DialogListener;
import com.d2cmall.buyer.util.CenterAlignImageSpan;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rookie on 2018/3/20.
 */

public class OtherOrderAdapter extends DelegateAdapter.Adapter<SingleOrderHolder> {

    private List<OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity> list;
    private Context context;
    private DialogListener dialogListener;
    private int index;

    public OtherOrderAdapter(List<OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity> list, Context context, DialogListener dialogListener) {
        this.list = list;
        this.context = context;
        this.dialogListener = dialogListener;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public SingleOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_single_order, parent, false);
        return new SingleOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(SingleOrderHolder holder, int position) {
        final OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity listEntity = list.get(position);
        Glide.with(context)
                .load(Util.getD2cProductPicUrl(listEntity.getProductImg(), ScreenUtil.dip2px(72), ScreenUtil.dip2px(108)))
                .placeholder(R.mipmap.ic_logo_empty5)
                .override(ScreenUtil.dip2px(72), ScreenUtil.dip2px(108))
                .dontAnimate()
                .error(R.mipmap.ic_logo_empty5)
                .into(holder.ivImage);
        if (listEntity.getFlashPromotionId() != null && listEntity.getFlashPromotionId() > 0) {
            StringBuilder builder = new StringBuilder();
            if (!Util.isEmpty(listEntity.getProductName())) {
                builder.append("   " + listEntity.getProductName());
            }
            SpannableString sb = new SpannableString(builder.toString());
            Drawable d = context.getResources().getDrawable(R.mipmap.icon_shopcart_xsg);
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            sb.setSpan(new CenterAlignImageSpan(d), 0, 1, ImageSpan.ALIGN_BASELINE);
            holder.tvProductName.setText(sb);
        } else if (listEntity.getType().equals("collage")) {
            StringBuilder builder = new StringBuilder();
            if (!Util.isEmpty(listEntity.getProductName())) {
                builder.append("   " + listEntity.getProductName());
            }
            SpannableString sb = new SpannableString(builder.toString());
            Drawable d;
            //参与拼团就有订单但是不一定成团
            if(listEntity.getCollageStatus()==1 && !(listEntity.getItemStatusCode()<0) ){
                d = context.getResources().getDrawable(R.mipmap.icon_pintuanzhong);
            }else{
                d = context.getResources().getDrawable(R.mipmap.icon_pintuan);
            }
            d.setBounds(0, -ScreenUtil.dip2px(6), d.getIntrinsicWidth()-ScreenUtil.dip2px(4), d.getIntrinsicHeight()-ScreenUtil.dip2px(10));
            sb.setSpan(new CenterAlignImageSpan(d), 0, 1, ImageSpan.ALIGN_BASELINE);
            holder.tvProductName.setText(sb);
        } else {
            holder.tvProductName.setText(listEntity.getProductName());
        }
        holder.tvProductStyle.setText(context.getString(R.string.label_kongge, listEntity.getColor(),
                listEntity.getSize()));
        holder.btnBuyAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkOrderSku(listEntity);
            }
        });
        String status;
        final int code = listEntity.getItemStatusCode();
        switch (code) {
            case -3:
                status = "超时关闭";
                break;
            case -1:
                status = "用户取消";
                break;
            case 0:
                status = "未处理";
                break;
            case 1:
                status = "待付款";
                break;
            case 3:
                status = "待发货";
                break;
            case 4:
                status = "已发货";
                break;
            case 8:
                status = "订单已完成";
                break;
            default:
                status = "未知";

        }
        holder.tvStatus.setText(listEntity.getItemStatus());
        double lessPrice = listEntity.getProductPrice() - (listEntity.getPromotionPrice() / listEntity.getQuantity());
        if (lessPrice < listEntity.getProductPrice()) {
            if (lessPrice < 0) {
                holder.tvPrice.setText(String.valueOf(0));
            } else {
                holder.tvPrice.setText(String.format(context.getString(R.string.label_price), Util.getNumberFormat(lessPrice)));
            }
        } else {
            holder.tvPrice.setText(String.format(context.getString(R.string.label_price), Util.getNumberFormat(listEntity.getProductPrice())));
        }
        holder.tvOrderId.setText(context.getString(R.string.label_order_id, listEntity.getOrderSn()));
        holder.tvOrderTotal.setText(context.getString(R.string.label_product_total, listEntity.getQuantity() + "",
                context.getString(R.string.label_no_sign_price, Util.getNumberFormat(listEntity.getActualAmount()))));
        if(listEntity.getAfter()==0){
            holder.tvCounter.setText("不支持七天无理由退换货");
            holder.tvCounter.setVisibility(View.VISIBLE);
        }else{
            holder.tvCounter.setVisibility(View.GONE);
        }
        holder.tvCount.setText(context.getString(R.string.label_product_quantity, listEntity.getQuantity()));
        holder.btnShowLogistical.setVisibility(View.GONE);
        holder.btnConfirmReceive.setVisibility(View.GONE);
        holder.btnGotoComment.setVisibility(View.GONE);
        holder.btnShowAfterSale.setVisibility(View.GONE);
        holder.btnApplyAfterSale.setVisibility(View.GONE);

        int i = 0;
        if (index == 2 || index == 3) {//待发货和待收货不展示再次购买
            holder.btnBuyAgain.setVisibility(View.GONE);
        } else {
            holder.btnBuyAgain.setVisibility(View.VISIBLE);
            i++;
        }
        //确认收货
        if (listEntity.getItemStatusCode() == 2) {
            i++;
            holder.btnConfirmReceive.setVisibility(View.VISIBLE);
        }
        //查看物流
        if (!Util.isEmpty(listEntity.getDeliverySn())) {//换货成功时候隐藏查看物流,显示评价晒单
            i++;
            holder.btnShowLogistical.setVisibility(View.VISIBLE);
        }
        //评价晒单
        if (listEntity.getIsComment() == 1 && listEntity.getAftering().equals("none")) {
            //可以评价晒单了,就隐藏查看物流
            i++;
            if(MyOrderActivity.point != null && MyOrderActivity.point >0){
                ViewGroup.LayoutParams layoutParams = holder.btnGotoComment.getLayoutParams();
                layoutParams.width= ScreenUtil.dip2px(100);
                holder.btnGotoComment.setText(context.getString(R.string.label_comment_reward,MyOrderActivity.point));
                holder.btnGotoComment.setLayoutParams(layoutParams);
            }else{
                ViewGroup.LayoutParams layoutParams = holder.btnGotoComment.getLayoutParams();
                layoutParams.width= ScreenUtil.dip2px(80);
                holder.btnGotoComment.setLayoutParams(layoutParams);
                holder.btnGotoComment.setText(context.getString(R.string.label_goto_comment));
            }
            holder.btnGotoComment.setVisibility(View.VISIBLE);
            holder.btnShowLogistical.setVisibility(View.GONE);
        }

        //代发货的考拉订单判断不了是否支持售后,展示申请售后让用户自己操作
        if ("KAOLA".equals(listEntity.getProductSource()) && listEntity.getItemStatusCode() == 1) {
            i++;
            holder.btnApplyAfterSale.setVisibility(View.VISIBLE);
        }

        if (!listEntity.getType().equals("distribution")) {
            //售后跟踪
            if (!Util.isEmpty(listEntity.getAftering()) && !listEntity.getAftering().equals("none")) {
                holder.btnShowAfterSale.setVisibility(View.VISIBLE);
                holder.btnApplyAfterSale.setVisibility(View.GONE);      //避免申请售后和售后跟踪同时出现
                i++;
            } else if (listEntity.getItemStatusCode() >= 1 && listEntity.getItemStatusCode() != 8 && listEntity.getAfter() == 1 && listEntity.getAftering().equals("none")) {
                holder.btnApplyAfterSale.setVisibility(View.VISIBLE);   //避免申请售后和售后跟踪同时出现
                holder.btnShowAfterSale.setVisibility(View.GONE);
                i++;
            }
        }

        if (i > 0) {
            holder.orderBottomLayout.setVisibility(View.VISIBLE);
            //拼团单拼团中底部按钮不可操作
            if (listEntity.getType().equals("collage")) {
                if(listEntity.getCollageStatus()==1){
                    holder.orderBottomLayout.setVisibility(View.GONE);
                }
            }
        } else {
            holder.orderBottomLayout.setVisibility(View.GONE);
        }


        if ("KAOLA".equals(listEntity.getProductSource()) && listEntity.getIsComment()==1) {
            holder.btnApplyAfterSale.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderItemDetailActivity.class);
                intent.putExtra("orderSn", String.valueOf(listEntity.getId()));
                context.startActivity(intent);
            }
        });

        holder.btnConfirmReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setMessage(R.string.msg_confirm_receive)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestConfirmReceiveTask(listEntity);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
        holder.btnShowLogistical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("KAOLA".equals(listEntity.getProductSource())){
                    Util.urlAction(context, String.format(Constants.KAOLA_WULIU_URL,
                            listEntity.getDeliverySn(), listEntity.getDeliveryCorpCode(), listEntity.getProductImg(),listEntity.getId()));
                }else{
                    Util.urlAction(context, String.format(Constants.WULIU_URL,
                            listEntity.getDeliverySn(), listEntity.getDeliveryCorpCode(), listEntity.getProductImg()));
                }
            }
        });
        holder.btnGotoComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReviewOrderActivity.class);
                intent.putExtra("id", listEntity.getId());
                intent.putExtra("productUrl", listEntity.getProductImg());
                intent.putExtra("productInfo", listEntity.getProductName());
                intent.putExtra("intentFlag", 0);
                intent.putExtra("price", listEntity.getProductPrice());
                intent.putExtra("style", context.getString(R.string.label_kongge, listEntity.getColor(),
                        listEntity.getSize()));
                context.startActivity(intent);
            }
        });
        holder.btnApplyAfterSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ApplyAfterSaleActivity.class);
                intent.putExtra("orderSn", listEntity.getOrderSn());
                intent.putExtra("orderId", listEntity.getId());
                intent.putExtra("bean", listEntity);
                intent.putExtra("statusCode", listEntity.getItemStatusCode());
                intent.putExtra("isKaoLa", "KAOLA".equals(listEntity.getProductSource()));//是不是考拉
                intent.putExtra("intentFlag", 0);
                context.startActivity(intent);
            }
        });
        holder.btnShowAfterSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listEntity.getAftering().equals("exchange")) {
                    gotoAfterSale(1);
                } else if (listEntity.getAftering().equals("reship")) {
                    gotoAfterSale(0);
                } else if (listEntity.getAftering().equals("refund")) {
                    gotoAfterSale(2);
                }
            }
        });
    }

    private void checkOrderSku(final OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity itemsEntity) {
        InsertCartApi api = new InsertCartApi();
        api.setNum(itemsEntity.getQuantity());
        api.setSkuId(itemsEntity.getSkuId());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                Intent intent = new Intent(context, CartActivity.class);
                final ArrayList<Integer> skuids = new ArrayList<>();
                skuids.add((int) itemsEntity.getSkuId());
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

    private void requestConfirmReceiveTask(final OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity itemEntity) {
        ConfirmReceiveApi api = new ConfirmReceiveApi();
        api.setOrderItemId(itemEntity.getId());
        dialogListener.showDialog();
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderInfoBean>() {
            @Override
            public void onResponse(OrderInfoBean orderInfoBean) {
                dialogListener.dismissDialog();
                list.remove(itemEntity);
                Util.showToast(context, R.string.msg_confirm_receive_ok);
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

    @Override
    public int getItemCount() {
        return list.size();
    }

}
