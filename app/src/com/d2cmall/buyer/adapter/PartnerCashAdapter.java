package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.PartnerCashListBean;
import com.d2cmall.buyer.bean.PartnerSaleListBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.PartnerBillHolder;
import com.d2cmall.buyer.holder.PartnerCashHolder;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/31 15:52
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class PartnerCashAdapter extends DelegateAdapter.Adapter {

    private Context mContext;
    public boolean hasPadding;
    public int padTop;
    private List<PartnerCashListBean.DataBean.PartnerCashBean.ListBean> datas;
    private UserBean.DataEntity.MemberEntity user;
    private long status;
    public PartnerCashAdapter(Context context, List<PartnerCashListBean.DataBean.PartnerCashBean.ListBean> datas, long id) {
        this.mContext = context;
        this.datas = datas;
        user = Session.getInstance().getUserFromFile(context);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper=new LinearLayoutHelper();
                linearLayoutHelper.setPaddingTop(ScreenUtil.dip2px(6));
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_partner_cash_item, new LinearLayout(mContext), false);
                return new PartnerCashHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        PartnerCashHolder cashHolder = (PartnerCashHolder) holder;
        PartnerCashListBean.DataBean.PartnerCashBean.ListBean listBean = datas.get(position);
        cashHolder.tvAccountType.setText(listBean.getStatusName());
        cashHolder.ivAccountType.setImageResource(R.mipmap.icon_mine_cash);
        cashHolder.tvAccountCode.setText("编号: "+listBean.getSn());
        cashHolder.tvAccountDate.setText(DateUtil.ConverToString(listBean.getCreateDate()));
        cashHolder.tvAccountMoney.setText("¥"+ Util.getNumberFormat(listBean.getApplyAmount()));
        if(listBean.getStatusX()==8 && !Util.isEmpty(listBean.getPayType())){
            cashHolder.tvPayType.setVisibility(View.VISIBLE);
            if("bank".equals(listBean.getPayType())){
                cashHolder.tvPayType.setText("提现方式: 银行卡");
            }else if("wallet".equals(listBean.getPayType())){
                cashHolder.tvPayType.setText("提现方式: 钱包");
            }else if("alipay".equals(listBean.getPayType())){
                cashHolder.tvPayType.setText("提现方式: 支付宝");
            }else{
                cashHolder.tvPayType.setVisibility(View.GONE);
            }
        }else{
            cashHolder.tvPayType.setVisibility(View.GONE);
        }
        if(listBean.getStatusX()==8){
            cashHolder.llRefuseReason.setVisibility(View.GONE);
            cashHolder.llBottom.setVisibility(View.VISIBLE);
            if(listBean.getPayDate()!=null){
                cashHolder.tvPayTime.setText("支付时间: "+DateUtil.ConverToString(listBean.getPayDate()));
            }else{
                cashHolder.tvPayTime.setText("");
            }
            cashHolder.tvPayCode.setText("支付流水: "+listBean.getPaySn());
            if(!Util.isEmpty(listBean.getApplyAccount())){
                cashHolder.tvPayAccount.setText("支付账号: "+listBean.getApplyAccount());
            }else{
                cashHolder.tvPayAccount.setText("支付账号: ");
            }
        }else if(listBean.getStatusX()==-1){
            cashHolder.llBottom.setVisibility(View.GONE);
            cashHolder.llRefuseReason.setVisibility(View.VISIBLE);
            cashHolder.tvRefuseReason.setText("拒绝原因: "+listBean.getRefuseReason());
        } else{
            cashHolder.llBottom.setVisibility(View.GONE);
        }
        if(listBean.getApplyTaxAmount()==0){    //原来未计税时候的数据
            cashHolder.llTax.setVisibility(View.GONE);
            cashHolder.tvAccountMoney.setText("¥"+ Util.getNumberFormat(listBean.getApplyAmount()));
        }else{              //计税提现单展示税前税后级税费
            cashHolder.llTax.setVisibility(View.VISIBLE);
            cashHolder.tvApplyAmount.setText("申请金额: ¥"+ Util.getNumberFormat(listBean.getApplyAmount()));       //申请金额
            cashHolder.tvTaxAmount.setText("扣税金额: ¥"+ (Util.getNumberFormat(listBean.getApplyAmount()-listBean.getApplyTaxAmount()))); //税费
            cashHolder.tvAccountMoney.setText("¥"+ Util.getNumberFormat(listBean.getApplyTaxAmount()));        //税后金额
        }
}





    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


}
