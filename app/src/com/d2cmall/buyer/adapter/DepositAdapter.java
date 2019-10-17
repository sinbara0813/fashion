package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.DepositDetailBean;
import com.d2cmall.buyer.holder.DepositHolder;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Created by rookie on 2017/9/6.
 */

public class DepositAdapter extends DelegateAdapter.Adapter<DepositHolder> {
    private Context context;
    private List<DepositDetailBean.DataEntity.PayItemsEntity.ListEntity> list;

    public DepositAdapter(Context context, List<DepositDetailBean.DataEntity.PayItemsEntity.ListEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public DepositHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_item_deposit_detail, parent, false);
        return new DepositHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DepositHolder holder, int position) {
        DepositDetailBean.DataEntity.PayItemsEntity.ListEntity listEntity = list.get(position);
        if (listEntity.getOutAmount() + listEntity.getOutGiftAmount() >= 0) {
            if (listEntity.getOutAmount() + listEntity.getOutGiftAmount() > 0) {
                //退款收入
                holder.tvBusinessType.setText(R.string.label_pay_in);
                holder.tvMoney.setTextColor(context.getResources().getColor(R.color.color_black2));
                if (listEntity.getLimitGiftAmount() > 0) {
                    holder.tvMoney.setText(Util.getNumberFormat(listEntity.getOutAmount() + listEntity.getOutGiftAmount(), true, true) + String.format(context.getString(R.string.label_gift_money), String.valueOf(listEntity.getLimitGiftAmount())));
                } else {
                    holder.tvMoney.setText(Util.getNumberFormat(listEntity.getOutAmount() + listEntity.getOutGiftAmount(), true, true));
                }
            } else {
                if (listEntity.getInAmount() + listEntity.getInGiftAmount() > 0) {//存入的钱
                    holder.tvBusinessType.setText(R.string.label_pay_in);
                    holder.tvMoney.setTextColor(context.getResources().getColor(R.color.color_black2));
                    if (listEntity.getLimitGiftAmount() > 0) {
                        holder.tvMoney.setText(Util.getNumberFormat(listEntity.getInAmount() + listEntity.getInGiftAmount(), true, true) + String.format(context.getString(R.string.label_gift_money), String.valueOf(listEntity.getLimitGiftAmount())));
                    } else {
                        holder.tvMoney.setText(Util.getNumberFormat(listEntity.getInAmount() + listEntity.getInGiftAmount(), true, true));
                    }
                } else {//财务扣款
                    holder.tvBusinessType.setText(R.string.label_pay_out);
                    holder.tvMoney.setTextColor(Color.parseColor("#FFF23365"));
                    if (listEntity.getLimitGiftAmount() > 0) {
                        holder.tvMoney.setText(Util.getNumberFormat(listEntity.getInAmount() + listEntity.getInGiftAmount(), true, true) + String.format(context.getString(R.string.label_gift_money), String.valueOf(listEntity.getLimitGiftAmount())));
                    } else {
                        holder.tvMoney.setText(Util.getNumberFormat(listEntity.getInAmount() + listEntity.getInGiftAmount(), true, true));
                    }
                    //holder.tvMoney.setTextColor(context.getResources().getColor(R.color.color_red));
                }
            }
        } else {
            holder.tvBusinessType.setText(R.string.label_pay_out);
            //holder.tvMoney.setTextColor(context.getResources().getColor(R.color.color_red));
            holder.tvMoney.setTextColor(Color.parseColor("#FFF23365"));
            if (listEntity.getLimitGiftAmount() > 0) {
                holder.tvMoney.setText(Util.getNumberFormat(listEntity.getOutAmount() + listEntity.getOutGiftAmount(), true, true) + String.format(context.getString(R.string.label_gift_money), String.valueOf(listEntity.getLimitGiftAmount())));
            } else {
                holder.tvMoney.setText(Util.getNumberFormat(listEntity.getOutAmount() + listEntity.getOutGiftAmount(), true, true));
            }
        }
        if (!Util.isEmpty(listEntity.getMemo())) {
            holder.tvRemark.setText(listEntity.getMemo());
        }
        if (!Util.isEmpty(listEntity.getTransactionTime())) {
            String date[] = listEntity.getTransactionTime().split(" ");
            holder.tvDate.setText(date[0]);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
