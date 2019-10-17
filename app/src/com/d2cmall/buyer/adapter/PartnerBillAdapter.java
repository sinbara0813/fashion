package com.d2cmall.buyer.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.PartnerPersonCenterActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.bean.BuyerBean;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.bean.PartnerSaleListBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.PartnerBillHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.Calendar;
import java.util.List;

/**
 * Name: d2c
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class PartnerBillAdapter extends DelegateAdapter.Adapter {

    private Context mContext;
    public boolean hasPadding;
    public int padTop;
    private List<PartnerSaleListBean.DataBean.PartnerBillBean.ListBean> datas;
    private UserBean.DataEntity.MemberEntity user;
    private final PartnerMemberBean.DataBean.PartnerBean partnerBean;

    public void setOrderType(int orderType) {
        this.orderType = orderType;
        notifyDataSetChanged();
    }

    private int orderType;

    public String getExpireDay() {
        return expireDay;
    }

    public void setExpireDay(String expireDay) {
        this.expireDay = expireDay;
    }

    private String expireDay;
    private boolean mIsLookBuyerGone;

    public PartnerBillAdapter(Context context, List<PartnerSaleListBean.DataBean.PartnerBillBean.ListBean> datas, boolean isLookBuyerGone) {
        this.mContext = context;
        this.datas = datas;
        user = Session.getInstance().getUserFromFile(context);
        partnerBean = Session.getInstance().getPartnerFromFile(mContext);
        this.mIsLookBuyerGone=isLookBuyerGone;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setPaddingTop(ScreenUtil.dip2px(6));
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_buyer_order_item, new LinearLayout(mContext), false);
        return new PartnerBillHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final PartnerBillHolder billHolder = (PartnerBillHolder) holder;
        final PartnerSaleListBean.DataBean.PartnerBillBean.ListBean listBean = datas.get(position);
        UniversalImageLoader.displayImage(mContext, Util.getD2cProductPicUrl(listBean.getProductImg(),ScreenUtil.dip2px(70),ScreenUtil.dip2px(105)), billHolder.ivProduct,R.mipmap.ic_logo_empty5);
        String rebeat = null;
        billHolder.tvProductName.setText(listBean.getProductName());
        billHolder.tvProductPrice.setText(Util.getNumberFormat(listBean.getProductPrice()));
        billHolder.tvProductNum.setText("x"+listBean.getQuantity());
        if (listBean.getCreateDate() != null) {
            billHolder.tvDate.setText(DateUtil.ConverToString(listBean.getCreateDate()));
        } else {
            billHolder.tvDate.setText(" ");
        }

        if(mIsLookBuyerGone){
            billHolder.tvLookBuyer.setVisibility(View.GONE);
        }else{
            billHolder.tvLookBuyer.setVisibility(View.VISIBLE);
        }
        billHolder.tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isEmpty(listBean.getOrderSn())) {
                    ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText("orderId", listBean.getOrderSn()));
                    Util.showToast(mContext, R.string.label_order_id_copy);
                }
            }
        });
        billHolder.tvLookBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, PartnerPersonCenterActivity.class).putExtra("id",listBean.getPartnerId()));
            }
        });
        billHolder.ivLookBuyer.setOnClickListener(new View.OnClickListener() {      //点击小头像图标查看买手
            @Override
            public void onClick(View v) {
                SimpleApi simpleApi = new SimpleApi();
                simpleApi.setInterPath(String.format(Constants.BUYER_LOOK_BUYER,listBean.getMemberId()));
                D2CApplication.httpClient.loadingRequest(simpleApi, new BeanRequest.SuccessListener<BuyerBean>() {
                    @Override
                    public void onResponse(BuyerBean buyerBean) {
                        LinearLayout lookBuyerPop = (LinearLayout) LayoutInflater.from(mContext).inflate(
                                R.layout.layout_look_buyer_pop, null);
                        ImageView ivBuyerHead = (ImageView) lookBuyerPop.findViewById(R.id.iv_buyer_headpic);
                        UniversalImageLoader.displayRoundImage(mContext, buyerBean.getData().getMember().getHead(), ivBuyerHead, R.mipmap.ic_default_avatar);
                        TextView tvBuyerName = (TextView) lookBuyerPop.findViewById(R.id.iv_buyer_name);
                        tvBuyerName.setText(buyerBean.getData().getMember().getNickname()+"");
                        final PopupWindow popupWindow = new PopupWindow(lookBuyerPop, ScreenUtil.dip2px(120),ScreenUtil.dip2px(50));
                        popupWindow.setOutsideTouchable(true);
                        popupWindow.showAsDropDown(billHolder.ivLookBuyer,ScreenUtil.dip2px(-60),ScreenUtil.dip2px(8));
                        billHolder.ivLookBuyer.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(popupWindow!=null){
                                    popupWindow.dismiss();
                                    billHolder.ivLookBuyer.setEnabled(true);
                                }
                            }
                        },1000);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Util.showToast(mContext, Util.checkErrorType(error));
                    }
                });

            }
        });
        if(partnerBean!=null && listBean.getMemberId()==partnerBean.getMemberId()){
            billHolder.tvSelfFlag.setVisibility(View.VISIBLE);
        }else{
            billHolder.tvSelfFlag.setVisibility(View.GONE);
        }
        billHolder.tvOrderCode.setText(mContext.getString(R.string.label_order_id_simple, listBean.getOrderSn()));
        billHolder.tvPhone.setText(mContext.getString(R.string.label_order_buyer_code, listBean.getLoginCode()));
        billHolder.tvOrderStatus.setText(listBean.getItemStatus());
        if(listBean.getTaxAmount()>0){
            billHolder.ivTaxTip.setVisibility(View.VISIBLE);
            billHolder.ivTaxTip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.showToast(mContext,mContext.getString(R.string.label_tax_tip, Util.getNumberFormat(listBean.getTaxAmount())));
                }
            });
        }else{
            billHolder.ivTaxTip.setVisibility(View.GONE);
        }
        //字体不一样大
        String str ="实付款: ¥"+String.valueOf(Util.getNumberFormat(listBean.getActualAmount()));
        int length = str.length();
        SpannableString textSpan = new SpannableString(str);
        textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)),0,5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(9)),5,6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)),6,length,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        billHolder.tvPayMoney.setText(textSpan);
        if (listBean.getDeliverySn() != null) {
            billHolder.llLogisticsInfo.setVisibility(View.VISIBLE);
            billHolder.lineLayout.setVisibility(View.VISIBLE);
            billHolder.tvLogisticsName.setText(listBean.getDeliveryCorpName() + " "+listBean.getDeliverySn());
        } else {
            billHolder.llLogisticsInfo.setVisibility(View.GONE);
            billHolder.lineLayout.setVisibility(View.GONE);
        }
        rebeat =  getRebeat(listBean);
        if (listBean.getItemStatusCode() <0 ) { //订单关闭
//            checkIsMyself(billHolder,rebeat,listBean);      //根据身份是不是自己改变Tag的显示
            billHolder.tvRebeatDesc.setTextColor(mContext.getResources().getColor(R.color.color_black60));
            String string ;
            if(user!=null && listBean.getMemberId() == user.getMemberId()){
                string = mContext.getString(R.string.msg_sale_lose_tip, rebeat);
            }else{
                string = mContext.getString(R.string.msg_sale_lose_tip, rebeat);
            }
            SpannableString backMoneySpan = new SpannableString(string);
            backMoneySpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(14)),0,7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            backMoneySpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(18)),7,string.length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            billHolder.tvRebeatDesc.setText(backMoneySpan);
            billHolder.llExpectTip.setVisibility(View.GONE);
        } else {    //订单非关闭状态
            String backMoney =null;
//            checkIsMyself(billHolder,rebeat,listBean);      //根据身份是不是自己改变Tag的显示
            //自买且大于指定时间显示已享自买优惠
            if(user!=null && listBean.getMemberId() == user.getMemberId() && listBean.getCreateDate().getTime()>DateUtil.strToDateLong("2018/12/01 00:00:00").getTime()){
                billHolder.llExpectTip.setVisibility(View.VISIBLE);
                billHolder.tvExpectTip.setText("已享买手自买优惠");
                backMoney = mContext.getString(R.string.label_order_rebeat, rebeat);
            }else{
                backMoney = mContext.getString(R.string.label_order_rebeat, rebeat);
                billHolder.llExpectTip.setVisibility(View.GONE);
                if (Util.isEmpty(getStatusTip(listBean, rebeat)) || "0".equals(rebeat)) { //没有对应状态或返利为0不展示提示
                    billHolder.llExpectTip.setVisibility(View.GONE);
                } else {
                    billHolder.llExpectTip.setVisibility(View.VISIBLE);
                    billHolder.tvExpectTip.setText(getStatusTip(listBean, rebeat));
                }
            }
            int backMoneyLength = backMoney.length();
            SpannableString backMoneySpan = new SpannableString(backMoney);
            backMoneySpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(14)),0,6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            backMoneySpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)),6,7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            backMoneySpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(18)),7,backMoneyLength,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            billHolder.tvRebeatDesc.setText(backMoneySpan);

        }


        billHolder.llLogisticsInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("KAOLA".equals(listBean.getProductSource())){
                    Util.urlAction(mContext, String.format(Constants.WULIU_URL,
                            listBean.getDeliverySn(), listBean.getDeliveryCorpCode(), listBean.getProductImg(),listBean.getOrderId()));
                }else{
                    Util.urlAction(mContext, String.format(Constants.WULIU_URL,
                            listBean.getDeliverySn(), listBean.getDeliveryCorpCode(), listBean.getProductImg()));
                }

            }
        });

        billHolder.ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("id", (long) listBean.getProductId());
                mContext.startActivity(intent);
            }
        });

    }

    private String getRebeat(PartnerSaleListBean.DataBean.PartnerBillBean.ListBean listBean) {
        if(orderType ==0){  //零售
            return  Util.getNumberFormat((listBean.getActualAmount()-listBean.getTaxAmount()) * listBean.getPartnerRatio());
        }else if(orderType==1){//团队
            if(partnerBean.getLevel()==0){
                return  Util.getNumberFormat((listBean.getActualAmount()-listBean.getTaxAmount())  * listBean.getMasterRatio() );
            }else{
                return  Util.getNumberFormat((listBean.getActualAmount()-listBean.getTaxAmount()) * listBean.getParentRatio());
            }
        } else if(orderType==2){//DM
            return  Util.getNumberFormat((listBean.getActualAmount()-listBean.getTaxAmount()) * listBean.getSuperRatio());
        }
        return  Util.getNumberFormat((listBean.getActualAmount()-listBean.getTaxAmount())  * listBean.getPartnerRatio());
    }



    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    public String getStatusTip(PartnerSaleListBean.DataBean.PartnerBillBean.ListBean listBean, String rebeat) {
//        INIT(0, "待付款"), NORMAL(1, "待发货"), DELIVERED(2, "已发货"), SIGNED(7, "已签收"),
//
//                CLOSE(-1, "用户取消"), MALLCLOSE(-3, "平台取消"), AFTERCLOSE(-2, "售后完成"), SUCCESS(8, "交易完成");
        String statusTip = null;
        if (rebeat == null) {
            return statusTip;
        }
        switch (listBean.getItemStatusCode()) {
            case 0:
                statusTip = mContext.getString(R.string.msg_sale_wait_pay_tip);
                break;
            case 1:
                if(!Util.isEmpty(expireDay)){
                    statusTip = mContext.getString(R.string.msg_sale_wait_send_tip, expireDay);
                }
                if (!Util.isEmpty(hasAftering(listBean, rebeat))) {
                    statusTip = hasAftering(listBean, rebeat);
                }
                break;
            case 2:
                if(!Util.isEmpty(expireDay)){
                    statusTip = mContext.getString(R.string.msg_sale_wait_send_tip, expireDay);
                }
                if (!Util.isEmpty(hasAftering(listBean, rebeat))) {
                    statusTip = hasAftering(listBean, rebeat);
                }
                break;
            case 7://预计完结时间加一天
                if (listBean.getExpectDate() == null) {
                    return statusTip;
                }
                Calendar c = Calendar.getInstance();
                c.setTime(listBean.getExpectDate());
                c.add(Calendar.DAY_OF_MONTH, 1);
                String time = DateUtil.getFriendlyTime8(DateUtil.ConverToString(c.getTime()));
                statusTip = mContext.getString(R.string.msg_sale_wait_sign_tip, time);
                if (!Util.isEmpty(hasAftering(listBean, rebeat))) {
                    statusTip = hasAftering(listBean, rebeat);
                }
                break;
            case 8:
                statusTip = "";
                if (listBean.getDiffAmount() > 0) {
                    statusTip = mContext.getString(R.string.msg_sale_aftering_difamount);
                }
                break;
        }
        return statusTip;
    }

    private String hasAftering(PartnerSaleListBean.DataBean.PartnerBillBean.ListBean listBean, String rebeat) {//根据售后状态是否改变提示语
        String tip = null;
        if ("reship".equalsIgnoreCase(listBean.getAftering())) {
            tip = mContext.getString(R.string.msg_sale_aftering_tip);
            return tip;
        }
        if ("refund".equalsIgnoreCase(listBean.getAftering())) {
            tip = mContext.getString(R.string.msg_sale_aftering_tip);
            if (listBean.getDiffAmount() > 0) {//退款类型为退差价
                tip = mContext.getString(R.string.msg_sale_aftering_difamount);
            }
            return tip;
        }
        if ("exchange".equalsIgnoreCase(listBean.getAftering())) {
            tip = mContext.getString(R.string.msg_sale_aftering_exchange);
            return tip;
        }
        return tip;
    }

//    public void checkIsMyself(PartnerBillHolder billHolder, String rebeat, PartnerSaleListBean.DataBean.PartnerBillBean.ListBean listBean){
//        if (user != null && "1".equals(user.getPartnerLevel()) ) {  //合伙人
//            billHolder.tvBuyerTag.setVisibility(View.VISIBLE);
//            if (listBean.getMemberId() == user.getMemberId() && listBean.getPartnerAmount()>0) {
//                billHolder.tvBuyerTag.setText("自己");
//                billHolder.llExpectTip.setVisibility(View.GONE);
//                billHolder.tvBackMoney.setText("已享买手优惠");           //自己的订单隐藏提示
//            } else if(listBean.getMemberId() == user.getMemberId() ){
//                billHolder.tvBuyerTag.setText("自己");
//                billHolder.llExpectTip.setVisibility(View.GONE);
//            }else{
//                billHolder.llExpectTip.setVisibility(View.VISIBLE);
//                billHolder.tvBackMoney.setText("返利: ¥" + rebeat);
//                billHolder.tvBuyerTag.setText("团队");
//            }
//        } else if(user != null &&  "2".equals(user.getPartnerLevel())){    //买手
//                billHolder.tvBuyerTag.setVisibility(View.GONE);
//            if (listBean.getMemberId() == user.getMemberId() && listBean.getPartnerAmount()>0) {
//                billHolder.tvBackMoney.setText("已享买手优惠");
//                billHolder.llExpectTip.setVisibility(View.GONE);        //自己的订单隐藏提示
//            } else {
//                billHolder.llExpectTip.setVisibility(View.VISIBLE);
//                billHolder.tvBackMoney.setText("返利: ¥" + rebeat);
//            }
//        }else{
//            billHolder.tvBuyerTag.setVisibility(View.GONE);
//        }
//    }
}
