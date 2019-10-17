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
import com.d2cmall.buyer.activity.RefundDetailActivity;
import com.d2cmall.buyer.bean.RefundsBean;
import com.d2cmall.buyer.holder.AfterSaleHolder;
import com.d2cmall.buyer.listener.RefundListener;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;


/**
 * Created by rookie on 2017/9/9.
 */

public class RefundListAdapter extends DelegateAdapter.Adapter<AfterSaleHolder> {
    private Context context;
    private List<RefundsBean.DataEntity.RefundsEntity.ListEntity> list;
    private RefundListener refundListener;

    public RefundListAdapter(Context context, List<RefundsBean.DataEntity.RefundsEntity.ListEntity> list, RefundListener refundListener) {
        this.context = context;
        this.list = list;
        this.refundListener = refundListener;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public AfterSaleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.layout_after_sale_item,parent,false);
        return new AfterSaleHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AfterSaleHolder holder, int position) {
        final RefundsBean.DataEntity.RefundsEntity.ListEntity listEntity=list.get(position);
        holder.tvOrderId.setText(context.getString(R.string.label_sale_id, listEntity.getRefundSn()));
        UniversalImageLoader.displayImage(context,Util.getD2cPicUrl(listEntity.getProductImg()), holder.ivImage
                , R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        holder.tvOrderId.setText(context.getString(R.string.label_order_id, listEntity.getOrderSn()));
        holder.tvProductName.setText(listEntity.getProductName());
        holder.tvProductStyle.setText(context.getString(R.string.label_kongge, listEntity.getProductColor(),
                listEntity.getProductSize()));
        holder.tvStatus.setText(listEntity.getStatusName());
        holder.tvOrderTotal.setVisibility(View.VISIBLE);
        if (listEntity.getPayType().equals("货到付款")) {
            //不显示退回方式
            holder.tvOrderTotal.setText(context.getString(R.string.label_after_sale_payment2,
                    Util.getNumberFormat(listEntity.getTotalAmount())));
        } else {
            holder.tvOrderTotal.setText(context.getString(R.string.label_after_sale_payment,
                    Util.getNumberFormat(listEntity.getTotalAmount())));
        }
        holder.btnCancelAfterSale.setVisibility(View.VISIBLE);
        if (listEntity.getStatusCode() == 1) {
            holder.orderBottomLayout.setVisibility(View.VISIBLE);
        } else {
            holder.orderBottomLayout.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listEntity != null) {
//                    Util.urlAction(context, String.format(Constants.REFUND_DETAIL_URL, listEntity.getId()), true);
                    context.startActivity(new Intent(context, RefundDetailActivity.class)
                            .putExtra("refundId",listEntity.getId()));
                }
            }
        });
        holder.btnCancelAfterSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refundListener.cancelRefund(listEntity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  list==null?0:list.size();
    }
}
