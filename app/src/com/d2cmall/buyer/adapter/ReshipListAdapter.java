package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ReshipDetailActivity;
import com.d2cmall.buyer.bean.ReshipsBean;
import com.d2cmall.buyer.holder.AfterSaleHolder;
import com.d2cmall.buyer.listener.ReshipListener;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;


/**
 * Created by rookie on 2017/9/9.
 */

public class ReshipListAdapter extends DelegateAdapter.Adapter<AfterSaleHolder> {
    private Context context;
    private List<ReshipsBean.DataBean.ReshipsDataBean.ListBean> list;
    private ReshipListener reshipListener;

    public ReshipListAdapter(Context context, List<ReshipsBean.DataBean.ReshipsDataBean.ListBean> list, ReshipListener reshipListener) {
        this.context = context;
        this.list = list;
        this.reshipListener = reshipListener;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public AfterSaleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_after_sale_item, parent, false);
        return new AfterSaleHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AfterSaleHolder holder, int position) {
        final ReshipsBean.DataBean.ReshipsDataBean.ListBean listEntity = list.get(position);
        holder.tvOrderId.setText(context.getString(R.string.label_sale_id, listEntity.getReshipSn()));
        UniversalImageLoader.displayImage(context,Util.getD2cPicUrl(listEntity.getProductImg()), holder.ivImage
                , R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        holder.tvOrderId.setText(context.getString(R.string.label_order_id, listEntity.getOrderSn()));
        holder.tvProductName.setText(listEntity.getProductName());
        holder.tvProductStyle.setText(context.getString(R.string.label_kongge, listEntity.getProductColor(),
                listEntity.getProductSize()));
        holder.tvStatus.setText(listEntity.getStatusName());
        holder.tvOrderTotal.setVisibility(View.VISIBLE);
        holder.tvPrice.setText("¥" + Util.getNumberFormat(listEntity.getTradeAmount()));
        holder.tvCount.setText("x1");
        if (listEntity.getPayType().equals("货到付款")) {
            //不显示退回方式
            holder.tvOrderTotal.setText(context.getString(R.string.label_after_sale_payment2,
                    Util.getNumberFormat(listEntity.getTotalAmount())));
        } else {
            holder.tvOrderTotal.setText(context.getString(R.string.label_after_sale_payment,
                    Util.getNumberFormat(listEntity.getTotalAmount())));
        }
        int i = 0;
        if (listEntity.getStatusCode() == 2) {
            holder.btnEditAfterSale.setVisibility(View.VISIBLE);
            i++;
        } else {
            holder.btnEditAfterSale.setVisibility(View.GONE);
        }
        if (listEntity.getStatusCode() < 8 && listEntity.getStatusCode() > -1) {
            holder.btnCancelAfterSale.setVisibility(View.VISIBLE);
            i++;
        } else {
            holder.btnCancelAfterSale.setVisibility(View.GONE);
        }
        if (i > 0) {
            holder.orderBottomLayout.setVisibility(View.VISIBLE);
        } else {
            holder.orderBottomLayout.setVisibility(View.GONE);
        }
        if(listEntity.getReceived() ==1 && listEntity.getStatusCode()==2){
            holder.btnEditAfterSale.setVisibility(View.VISIBLE);
        }else{
            holder.btnEditAfterSale.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listEntity != null) {
                        context.startActivity(new Intent(context, ReshipDetailActivity.class)
                                .putExtra("reshipId",listEntity.getId()));
//                        Util.urlAction(context, String.format(Constants.REFUND_DETAIL_URL, listEntity.getRefundId()), true);
//                        Util.urlAction(context, String.format(Constants.RESHIP_DETAIL_URL, listEntity.getId()), true);
                }
            }
        });
        holder.btnCancelAfterSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reshipListener.cancelReshipClick(listEntity);
            }
        });
        holder.btnEditAfterSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reshipListener.editReshipClick(listEntity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
