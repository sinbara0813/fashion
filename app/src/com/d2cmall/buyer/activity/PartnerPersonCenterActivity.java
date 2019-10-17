package com.d2cmall.buyer.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.BuyerVisitorApi;
import com.d2cmall.buyer.api.OrderDataApi;
import com.d2cmall.buyer.api.PartnerChildrenDataApi;
import com.d2cmall.buyer.api.PartnerPersonalCenterApi;
import com.d2cmall.buyer.api.PartnerSaleListApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.BuyerBean;
import com.d2cmall.buyer.bean.BuyerTeamBean;
import com.d2cmall.buyer.bean.OrderDayBean;
import com.d2cmall.buyer.bean.PartnerDayVisitorBean;
import com.d2cmall.buyer.bean.PartnerSaleListBean;
import com.d2cmall.buyer.bean.PartnerSummaryBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.RoundedImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.d2cmall.buyer.Constants.GET_PARTNER_VISITOR_YESTERDAY_TODAY_URL;

//买手个人中心界面
public class PartnerPersonCenterActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.iv_buyer_head)
    RoundedImageView ivBuyerHead;
    @Bind(R.id.tv_buyer_name)
    TextView tvBuyerName;
    @Bind(R.id.tv_buyer_code)
    TextView tvBuyerCode;
    @Bind(R.id.iv_buyer_level)
    ImageView ivBuyerLevel;
    @Bind(R.id.tv_enter_date)
    TextView tvEnterDate;
    @Bind(R.id.tv_income_money)
    TextView tvIncomeMoney;
    @Bind(R.id.tv_visitor_recode)
    TextView tvVisitorRecode;
    @Bind(R.id.tv_today_visitor)
    TextView tvTodayVisitor;
    @Bind(R.id.tv_yesterday_visitor)
    TextView tvYesterdayVisitor;
    @Bind(R.id.tv_30day_visitor)
    TextView tv30dayVisitor;
    @Bind(R.id.tv_invite_recode)
    TextView tvInviteRecode;
    @Bind(R.id.tv_invite_total)
    TextView tvInviteTotal;
    @Bind(R.id.tv_today_invite)
    TextView tvTodayInvite;
    @Bind(R.id.tv_test_invite)
    TextView tvTestInvite;
    @Bind(R.id.market_question)
    ImageView marketQuestion;
    @Bind(R.id.today)
    TextView today;
    @Bind(R.id.yesterday)
    TextView yesterday;
    @Bind(R.id.seven_day)
    TextView sevenDay;
    @Bind(R.id.thirty_day)
    TextView thirtyDay;
    @Bind(R.id.all)
    TextView all;
    @Bind(R.id.order_ll)
    LinearLayout orderLl;
    @Bind(R.id.tv_update_dm)
    TextView tvUpdateDm;
    @Bind(R.id.ll_invite_dm)
    LinearLayout llInviteDm;
    @Bind(R.id.ll_order_list)
    LinearLayout llOrderList;
    @Bind(R.id.tv_more_order)
    TextView tvMoreOrder;
    @Bind(R.id.ll_order_title)
    LinearLayout llOrderTitle;
    @Bind(R.id.iv_shakedown)
    ImageView ivShakedown;
    private int width;
    private int itemWidth;
    private int offer;
    private double[][] todayData;
    private double[][] yesterdayData;
    private double[][] sevenData;
    private double[][] thirtyData;
    private double[][] allData;

    private boolean isDm;
    private boolean isAm;
    private int textWidth;
    private int textSpace;
    private int dmItemWidth;
    private int dmOffer;
    private TextView lastDay;
    private PartnerSummaryBean.DataBean.PartnerBean partnerBean;
    private long id;
    private String expireDay;
    private UserBean.DataEntity.MemberEntity user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity_refresh_recyclerview
        setContentView(R.layout.activity_partner_person_sale_info);
        ButterKnife.bind(this);
        id = getIntent().getLongExtra("id", 0);
        width = ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(32);
        user = Session.getInstance().getUserFromFile(this);
        init();
    }

    private void init() {
        nameTv.setText("客户中心");
        lastDay = today;
        loadPartnerInfo();
    }

    private void loadPartnerInfo() {
        PartnerPersonalCenterApi api = new PartnerPersonalCenterApi();
        if (id > 0) {
            api.setPartnerId(id);
        } else {
            return;
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PartnerSummaryBean>() {
            @Override
            public void onResponse(PartnerSummaryBean response) {
                if (response != null && response.getData().getPartner() != null) {
                    if (isFinishing()) {
                        return;
                    }
                    partnerBean = response.getData().getPartner();
                    UniversalImageLoader.displayRoundImage(PartnerPersonCenterActivity.this, partnerBean.getHeadPic(), ivBuyerHead, R.mipmap.ic_default_avatar);
                    if(!Util.isEmpty(partnerBean.getName())){
                        tvBuyerName.setText(partnerBean.getName());
                    }else{
                        tvBuyerName.setText("匿名_"+partnerBean.getMemberId());
                    }
                    tvEnterDate.setText(getString(R.string.buyer_enter_date, DateUtil.getFriendlyTime9(DateUtil.ConverToString(partnerBean.getCreateDate()))));
                    String str = "¥" + Util.getNumberFormat(partnerBean.getTotalAmount());
                    int length = str.length();
                    SpannableString textSpan = new SpannableString(str);
                    textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(20)), 1, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    tvIncomeMoney.setText(textSpan);
                    tvBuyerCode.setText(getString(R.string.buyer_code, partnerBean.getMemberId()));
                    if (partnerBean.getLevel() == 0) {//AM
                        ivBuyerLevel.setImageResource(R.mipmap.icon_am);
                    } else if (partnerBean.getLevel() == 1) {//DM
                        ivBuyerLevel.setImageResource(R.mipmap.icon_dm_level);
                    } else if (partnerBean.getLevel() == 2) {//买手
                        llInviteDm.setVisibility(View.GONE);
                        ivBuyerLevel.setImageResource(R.mipmap.icon_buyer_level);
                    }
                    if (partnerBean.getStatusX() == 0 && !Util.isEmpty(partnerBean.getExpiredDate())) {//试用期
                        ivShakedown.setVisibility(View.VISIBLE);
                    }
                        if (partnerBean.getLevel() == 0) {//AM
                            todayData = new double[3][2];
                            yesterdayData = new double[3][2];
                            sevenData = new double[3][2];
                            thirtyData = new double[3][2];
                            allData = new double[3][2];
                            initArray(todayData);
                            initArray(yesterdayData);
                            initArray(sevenData);
                            initArray(thirtyData);
                            initArray(allData);
                            isDm = true;
                            isAm = true;
                        } else if (partnerBean.getLevel() == 1) { //DM
                            todayData = new double[3][3];
                            yesterdayData = new double[3][3];
                            sevenData = new double[3][3];
                            thirtyData = new double[3][3];
                            allData = new double[3][3];
                            initArray(todayData);
                            initArray(yesterdayData);
                            initArray(sevenData);
                            initArray(thirtyData);
                            initArray(allData);
                            isDm = true;
                            isAm = false;
                        } else {
                            todayData = new double[1][3];
                            yesterdayData = new double[1][3];
                            sevenData = new double[1][3];
                            thirtyData = new double[1][3];
                            allData = new double[1][3];
                            initArray(todayData);
                            initArray(yesterdayData);
                            initArray(sevenData);
                            initArray(thirtyData);
                            initArray(allData);
                            isDm = false;
                            isAm = false;
                        }
                        loadVisitorTextData();
                        loadToday();
                        loadOrderList();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(PartnerPersonCenterActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void loadOrderList() {//加载显示零售订单,最多显示五条
        PartnerSaleListApi partnerSaleListApi = new PartnerSaleListApi();
        partnerSaleListApi.setPartnerId(String.valueOf(partnerBean.getId()));
        partnerSaleListApi.setP(1);
        partnerSaleListApi.setPageSize(20);
        D2CApplication.httpClient.loadingRequest(partnerSaleListApi, new BeanRequest.SuccessListener<PartnerSaleListBean>() {
            @Override
            public void onResponse(PartnerSaleListBean partnerSaleListBean) {
                if (isFinishing()) {
                    return;
                }
                if (partnerSaleListBean.getData().getPartnerBill().getList() == null || partnerSaleListBean.getData().getPartnerBill().getList().size() == 0) {
                    return;
                }
                llOrderList.removeAllViews();
                expireDay = partnerSaleListBean.getData().getExpireDay();
                llOrderTitle.setVisibility(View.VISIBLE);
                llOrderList.setVisibility(View.VISIBLE);
                final List<PartnerSaleListBean.DataBean.PartnerBillBean.ListBean> list = partnerSaleListBean.getData().getPartnerBill().getList();
                int count = list.size() > 5 ? 5 : list.size();
                for (int i = 0; i < count; i++) {
                    View view = LayoutInflater.from(PartnerPersonCenterActivity.this).inflate(R.layout.layout_buyer_order_item, llOrderList, false);
                    TextView tvCode = (TextView) view.findViewById(R.id.tv_order_code);
                    tvCode.setText(list.get(i).getOrderSn());
                    TextView tvCopy = (TextView) view.findViewById(R.id.tv_copy);
                    final int finalI = i;
                    tvCopy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!Util.isEmpty(list.get(finalI).getOrderSn())) {
                                ClipboardManager cm = (ClipboardManager) PartnerPersonCenterActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                                cm.setPrimaryClip(ClipData.newPlainText("orderId", list.get(finalI).getOrderSn()));
                                Util.showToast(PartnerPersonCenterActivity.this, R.string.label_order_id_copy);
                            }
                        }
                    });
                    TextView tvOrderStatus = (TextView) view.findViewById(R.id.tv_order_status);
                    tvOrderStatus.setText(list.get(i).getItemStatus());
                    ImageView ivProduct = (ImageView) view.findViewById(R.id.iv_product);
                    ImageView ivTaxTip = (ImageView) view.findViewById(R.id.iv_tax_tip);
                    UniversalImageLoader.displayImage(PartnerPersonCenterActivity.this, list.get(i).getProductImg(), ivProduct, R.mipmap.ic_logo_empty5);
                    TextView tvProductName = (TextView) view.findViewById(R.id.tv_product_name);
                    tvProductName.setText(list.get(i).getProductName());
                    tvOrderStatus.setText(list.get(i).getItemStatus());
                    TextView tvProductPrice = (TextView) view.findViewById(R.id.tv_product_price);
                    tvProductPrice.setText(Util.getNumberFormat(list.get(i).getProductPrice()));
                    TextView tvProductNum = (TextView) view.findViewById(R.id.tv_product_num);
                    tvProductNum.setText("x" + list.get(i).getQuantity());
                    TextView tvPhone = (TextView) view.findViewById(R.id.tv_phone);
                    tvPhone.setText(getString(R.string.label_order_buyer_code, list.get(i).getLoginCode()));
                    TextView tvDate = (TextView) view.findViewById(R.id.tv_date);
                    if (list.get(i).getCreateDate() != null) {
                        tvDate.setText(DateUtil.ConverToString(list.get(i).getCreateDate()));
                    } else {
                        tvDate.setText(" ");
                    }
                    TextView tvLookBuyer = (TextView) view.findViewById(R.id.tv_look_buyer);
                    tvLookBuyer.setVisibility(View.GONE);
                    final ImageView ivLookBuyer = (ImageView) view.findViewById(R.id.iv_look_buyer);
                    ivLookBuyer.setOnClickListener(new View.OnClickListener() {      //点击小头像图标查看买手
                        @Override
                        public void onClick(View v) {
                            SimpleApi simpleApi = new SimpleApi();
                            simpleApi.setInterPath(String.format(Constants.BUYER_LOOK_BUYER, list.get(finalI).getMemberId()));
                            D2CApplication.httpClient.loadingRequest(simpleApi, new BeanRequest.SuccessListener<BuyerBean>() {
                                @Override
                                public void onResponse(BuyerBean buyerBean) {
                                    LinearLayout lookBuyerPop = (LinearLayout) LayoutInflater.from(PartnerPersonCenterActivity.this).inflate(
                                            R.layout.layout_look_buyer_pop, null);
                                    ImageView ivBuyerHead = (ImageView) lookBuyerPop.findViewById(R.id.iv_buyer_headpic);
                                    UniversalImageLoader.displayRoundImage(PartnerPersonCenterActivity.this, buyerBean.getData().getMember().getHead(), ivBuyerHead, R.mipmap.ic_default_avatar);
                                    TextView tvBuyerName = (TextView) lookBuyerPop.findViewById(R.id.iv_buyer_name);
                                    tvBuyerName.setText(buyerBean.getData().getMember().getNickname() + "");
                                    final PopupWindow popupWindow = new PopupWindow(lookBuyerPop, ScreenUtil.dip2px(120), ScreenUtil.dip2px(50));
                                    popupWindow.setOutsideTouchable(true);
                                    popupWindow.showAsDropDown(ivLookBuyer, ScreenUtil.dip2px(-60), ScreenUtil.dip2px(8));
                                    ivLookBuyer.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (popupWindow != null) {
                                                popupWindow.dismiss();
                                                ivLookBuyer.setEnabled(true);
                                            }
                                        }
                                    }, 1000);
                                }
                            });

                        }
                    });
                    if (list.get(i).getDeliverySn() != null) {
                        LinearLayout llLogisticsInfo = (LinearLayout) view.findViewById(R.id.ll_logistics_info);
                        llLogisticsInfo.setVisibility(View.VISIBLE);
                        TextView tvLogisticsName = (TextView) view.findViewById(R.id.tv_logistics_name);
                        tvLogisticsName.setText(list.get(i).getDeliveryCorpName() + " " + list.get(i).getDeliverySn());
                        llLogisticsInfo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if("KAOLA".equals(list.get(finalI).getProductSource())){
                                    Util.urlAction(PartnerPersonCenterActivity.this, String.format(Constants.KAOLA_WULIU_URL,
                                            list.get(finalI).getDeliverySn(), list.get(finalI).getDeliveryCorpCode(), list.get(finalI).getProductImg(),list.get(finalI).getOrderId()));
                                }else{
                                    Util.urlAction(PartnerPersonCenterActivity.this, String.format(Constants.WULIU_URL,
                                            list.get(finalI).getDeliverySn(), list.get(finalI).getDeliveryCorpCode(), list.get(finalI).getProductImg()));
                                }

                            }
                        });
                    }

                    TextView tvPayMoney = (TextView) view.findViewById(R.id.tv_pay_money);
                    if(list.get(i).getTaxAmount()>0){
                        ivTaxTip.setVisibility(View.VISIBLE);
                        int finalI1 = i;
                        ivTaxTip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Util.showToast(PartnerPersonCenterActivity.this,getString(R.string.label_tax_tip, Util.getNumberFormat(list.get(finalI1).getTaxAmount())));
                            }
                        });
                    }else{
                        ivTaxTip.setVisibility(View.GONE);
                    }
                    //字体不一样大
                    String str = "实付款: ¥" + String.valueOf(Util.getNumberFormat(list.get(i).getActualAmount()));
                    int length = str.length();
                    SpannableString textSpan = new SpannableString(str);
                    textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)), 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(9)), 5, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)), 6, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    tvPayMoney.setText(textSpan);
                    String rebeat = Util.getNumberFormat((list.get(i).getActualAmount()-list.get(i).getTaxAmount()) * list.get(i).getPartnerRatio());

                    TextView tvExpectTip = (TextView) view.findViewById(R.id.tv_expect_tip);

                    LinearLayout llExpectTip = (LinearLayout) view.findViewById(R.id.ll_expect_tip);
                    TextView tvRebeatDesc = (TextView) view.findViewById(R.id.tv_rebeat_desc);
                    TextView tvSelfFlag = (TextView) view.findViewById(R.id.tv_self_flag);
                    if (partnerBean != null && list.get(i).getMemberId() == partnerBean.getMemberId()) {
                        tvSelfFlag.setVisibility(View.VISIBLE);
                    } else {
                        tvSelfFlag.setVisibility(View.GONE);
                    }
                    if (Util.isEmpty(getStatusTip(list.get(i), rebeat)) || "0".equals(rebeat)) { //没有对应状态或返利为0不展示提示
                        llExpectTip.setVisibility(View.GONE);
                    } else {
                        llExpectTip.setVisibility(View.VISIBLE);
                        tvExpectTip.setText(getStatusTip(list.get(i), rebeat));
                    }

                    if (list.get(i).getItemStatusCode() < 0) { //订单关闭
                        tvRebeatDesc.setText(" ");
                        String string ;
                        if(user!=null && list.get(i).getMemberId() == user.getMemberId()){
                            string = getString(R.string.msg_sale_lose_tip, rebeat);
                        }else{
                            string = getString(R.string.msg_sale_lose_tip, rebeat);
                        }
                        SpannableString backMoneySpan = new SpannableString(string);
                        backMoneySpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(14)), 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        backMoneySpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(18)), 7, string.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        tvRebeatDesc.setText(backMoneySpan);
                        llExpectTip.setVisibility(View.GONE);
                    } else {    //订单非关闭状态
                        llExpectTip.setVisibility(View.VISIBLE);
                        //字体不一样大
                        String backMoney = getString(R.string.label_order_rebeat, rebeat);

                        if(user!=null && list.get(i).getMemberId() == user.getMemberId() && list.get(i).getCreateDate().getTime()>DateUtil.strToDateLong("2018/12/01 00:00:00").getTime()){
                            llExpectTip.setVisibility(View.VISIBLE);
                            tvExpectTip.setText("已享买手自买优惠");
                            backMoney = getString(R.string.label_order_rebeat, rebeat);
                        }else {
                            backMoney = getString(R.string.label_order_rebeat, rebeat);
                            llExpectTip.setVisibility(View.GONE);
                            if (Util.isEmpty(getStatusTip(list.get(i), rebeat)) || "0".equals(rebeat)) { //没有对应状态或返利为0不展示提示
                                llExpectTip.setVisibility(View.GONE);
                            } else {
                                llExpectTip.setVisibility(View.VISIBLE);
                                tvExpectTip.setText(getStatusTip(list.get(i), rebeat));
                            }
                        }
                        int backMoneyLength = backMoney.length();
                        SpannableString backMoneySpan = new SpannableString(backMoney);
                        backMoneySpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(14)),0,6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        backMoneySpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)),6,7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        backMoneySpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(18)),7,backMoneyLength,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        tvRebeatDesc.setText(backMoneySpan);
                    }
                    llOrderList.addView(view);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
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
                statusTip = PartnerPersonCenterActivity.this.getString(R.string.msg_sale_wait_pay_tip);
                break;
            case 1:
                if (!Util.isEmpty(expireDay)) {
                    statusTip = PartnerPersonCenterActivity.this.getString(R.string.msg_sale_wait_send_tip, expireDay);
                }
                if (!Util.isEmpty(hasAftering(listBean, rebeat))) {
                    statusTip = hasAftering(listBean, rebeat);
                }
                break;
            case 2:
                if (!Util.isEmpty(expireDay)) {
                    statusTip = PartnerPersonCenterActivity.this.getString(R.string.msg_sale_wait_send_tip, expireDay);
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
                statusTip = PartnerPersonCenterActivity.this.getString(R.string.msg_sale_wait_sign_tip, time);
                if (!Util.isEmpty(hasAftering(listBean, rebeat))) {
                    statusTip = hasAftering(listBean, rebeat);
                }
                break;
            case 8:
                if (listBean.getDiffAmount() > 0) {
                    statusTip = PartnerPersonCenterActivity.this.getString(R.string.msg_sale_aftering_difamount);
                }
                break;
        }
        return statusTip;
    }

    private String hasAftering(PartnerSaleListBean.DataBean.PartnerBillBean.ListBean listBean, String rebeat) {//根据售后状态是否改变提示语
        String tip = null;
        if ("reship".equalsIgnoreCase(listBean.getAftering())) {
            tip = PartnerPersonCenterActivity.this.getString(R.string.msg_sale_aftering_tip);
            return tip;
        }
        if ("refund".equalsIgnoreCase(listBean.getAftering())) {
            tip = PartnerPersonCenterActivity.this.getString(R.string.msg_sale_aftering_tip);
            if (listBean.getDiffAmount() > 0) {//退款类型为退差价
                tip = PartnerPersonCenterActivity.this.getString(R.string.msg_sale_aftering_difamount);
            }
            return tip;
        }
        if ("exchange".equalsIgnoreCase(listBean.getAftering())) {
            tip = PartnerPersonCenterActivity.this.getString(R.string.msg_sale_aftering_exchange);
            return tip;
        }
        return tip;
    }

    private void initArray(double[][] data) {
        for (int i = 0; i < data.length; i++) {
            double[] a = data[i];
            for (int j = 0; j < a.length; j++) {
                data[i][j] = 0;
            }
        }
    }

    /**
     * 布局销售概况
     */
    private void loadVisitorTextData() {
        BuyerVisitorApi api = new BuyerVisitorApi();
        api.event = "买手店-访问";
        api.fieldName = "targetId";
        api.fieldValue = partnerBean.getId();
        api.isJsonContentType = true;
        api.setInterPath(GET_PARTNER_VISITOR_YESTERDAY_TODAY_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PartnerDayVisitorBean>() {
            @Override
            public void onResponse(PartnerDayVisitorBean partnerDayVisitorBean) {
                if (isFinishing()) {
                    return;
                }
                tvTodayVisitor.setText(String.valueOf(partnerDayVisitorBean.getList().get(0).getTodayUv()));
                tvYesterdayVisitor.setText(String.valueOf(partnerDayVisitorBean.getList().get(0).getLastDayUv()));
                tv30dayVisitor.setText(String.valueOf(partnerDayVisitorBean.getList().get(0).getUv()));
                loadTeamData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadTeamData();
                Util.showToast(PartnerPersonCenterActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void loadTeamData() {
        PartnerChildrenDataApi api = new PartnerChildrenDataApi();
        api.setPartnerId(partnerBean.getId());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BuyerTeamBean>() {
            @Override
            public void onResponse(BuyerTeamBean response) {
                if (isFinishing()) {
                    return;
                }
                List<BuyerTeamBean.DataBean.BuyerDataBean> useList = new ArrayList<>();
                List<BuyerTeamBean.DataBean.TodayDataBean> todayList = new ArrayList<>();

                if (response.getData().getAmData() != null) {//AM数据
                    useList.addAll(response.getData().getAmData());
                } else if (response.getData().getDmData() != null) {//DM数据
                    useList.addAll(response.getData().getDmData());
                } else if (response.getData().getBuyerData() != null) {
                    useList.addAll(response.getData().getBuyerData());
                }
                if (response.getData().getTodayData() != null) {
                    todayList.addAll(response.getData().getTodayData());
                }

                //试用期的买手
                int tryBuyerCount = 0;
                for (int i = 0; i < useList.size(); i++) {
                    BuyerTeamBean.DataBean.BuyerDataBean bean = useList.get(i);
                    if (bean.getStatusX() == 0 && bean.getLevel() == 2) {
                        tryBuyerCount = tryBuyerCount + bean.getCount();
                    }
                }
                tvTestInvite.setText(String.valueOf(tryBuyerCount));

                //今日新增买手
                int todayAddBuyerCount = 0;
                for (int i = 0; i < todayList.size(); i++) {
                    BuyerTeamBean.DataBean.TodayDataBean bean = todayList.get(i);
                    if (bean.getLevel() == 2) {
                        todayAddBuyerCount = bean.getCount();
                    }
                }
                tvTodayInvite.setText(String.valueOf(todayAddBuyerCount));

                //总数买手
                int totalBuyerCount = 0;
                for (int i = 0; i < useList.size(); i++) {
                    BuyerTeamBean.DataBean.BuyerDataBean bean = useList.get(i);
                    if (bean.getLevel() == 2 && bean.getStatusX() != -9) {
                        totalBuyerCount = totalBuyerCount + bean.getCount();
                    }
                }
                tvInviteTotal.setText(String.valueOf(totalBuyerCount));

                //DM总数(升级成为DM)
                int totalDmCount = 0;
                for (int i = 0; i < useList.size(); i++) {
                    BuyerTeamBean.DataBean.BuyerDataBean bean = useList.get(i);
                    if (bean.getLevel() == 1 && bean.getStatusX() != -9) {
                        totalDmCount = totalDmCount + bean.getCount();
                    }
                }
                tvUpdateDm.setText(String.valueOf(totalDmCount));
                if (partnerBean.getLevel() == 0 || partnerBean.getLevel() == 1) {
                    tvUpdateDm.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(PartnerPersonCenterActivity.this, Util.checkErrorType(error));
            }
        });
    }


    @OnClick({R.id.back_iv, R.id.tv_visitor_recode, R.id.tv_invite_recode, R.id.market_question, R.id.today, R.id.yesterday, R.id.seven_day, R.id.thirty_day, R.id.all, R.id.tv_more_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.tv_visitor_recode:
                break;
            case R.id.tv_invite_recode:
                startActivity(new Intent(PartnerPersonCenterActivity.this, BuyerListActivity.class)
                        .putExtra("level", 2)//查看买手
                        .putExtra("isJump", false)//是否可以跳转
                        .putExtra("isAm", partnerBean.getLevel() == 0)
                        .putExtra("partnerId", partnerBean == null ? -1 : partnerBean.getId()));
                break;
            case R.id.market_question:
                Util.showToast(PartnerPersonCenterActivity.this, getString(R.string.sale_question_desc));
                break;
            case R.id.today://今天
                selectDay(today, 0);
                setTodayData();
                break;
            case R.id.yesterday://昨天
                selectDay(yesterday, 1);
                setYesterdayData();
                break;
            case R.id.seven_day://7天
                selectDay(sevenDay, 2);
                setSevenData();
                break;
            case R.id.thirty_day://30天
                selectDay(thirtyDay, 3);
                setThirtyData();
                break;
            case R.id.all://所有
                selectDay(all, 4);
                setAllData();
                break;
            case R.id.tv_more_order://所有零售订单
                startActivity(new Intent(PartnerPersonCenterActivity.this, SellRetailListActivity.class).putExtra("partnerId", partnerBean.getId()));
                break;
        }
    }

    private void selectDay(TextView textView, int index) {
        if (lastDay != null) {
            lastDay.setTextColor(getResources().getColor(R.color.color_black38));
            lastDay.setBackgroundResource(R.drawable.sp_line);
        }
        textView.setTextColor(getResources().getColor(R.color.trans_90_color_white));
        textView.setBackgroundColor(getResources().getColor(R.color.trans_30_color_black));
        lastDay = textView;
    }

    private void loadToday() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.ORDER_TODAY, partnerBean.getId()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderDayBean>() {
            @Override
            public void onResponse(OrderDayBean response) {
                if (isFinishing()) {
                    return;
                }
                if (response != null && response.getList() != null && response.getList().size() > 0) {
                    OrderDayBean.ListBean.SaleStatBean retail = response.getList().get(0).getSaleStat();
                    OrderDayBean.ListBean.SaleStatBean dm = response.getList().get(0).getSaleStatDM();
                    OrderDayBean.ListBean.SaleStatBean sDm = response.getList().get(0).getSaleStatSDM();
                    OrderDayBean.ListBean.SaleStatBean am = response.getList().get(0).getSaleStatAM();
                    if (isAm || isDm) {
                        todayData[0][0] = retail.getCount();
                        todayData[1][0] = retail.getPayAmount();
                        todayData[2][0] = retail.getPartnerRebates();
                        if (isAm) {
                            todayData[0][1] = dm.getCount() + sDm.getCount() + am.getCount();
                            todayData[1][1] = dm.getPayAmount() + sDm.getPayAmount() + am.getPayAmount();
                            todayData[2][1] = dm.getParentRebates() + sDm.getSuperRebates() + am.getMasterRebates();
                        } else {
                            todayData[0][1] = dm.getCount();
                            todayData[1][1] = dm.getPayAmount();
                            todayData[2][1] = dm.getParentRebates();
                            todayData[0][2] = sDm.getCount();
                            todayData[1][2] = sDm.getPayAmount();
                            todayData[2][2] = sDm.getSuperRebates();
                        }
                    } else {
                        todayData[0][0] = retail.getCount();
                        todayData[0][1] = retail.getPayAmount();
                        todayData[0][2] = retail.getPartnerRebates();
                    }
                    loadYesterday();
                    loadSomeDay(6);
                    loadSomeDay(29);
                    loadAllDay();
                }
                setTodayData();
            }
        });
    }

    /**
     * 设置今日订单数据
     */
    private void setTodayData() {
        if (isAm) {
            addAmOrderView(todayData);
        } else if (isDm) {
            addDmOrderView(todayData);
        } else {
            addPartnerOrderView(todayData[0][0], todayData[0][1], todayData[0][2]);
        }
    }

    /**
     * 设置昨日订单数据
     */
    private void setYesterdayData() {
        if (isAm) {
            addAmOrderView(yesterdayData);
        } else if (isDm) {
            addDmOrderView(yesterdayData);
        } else {
            addPartnerOrderView(yesterdayData[0][0], yesterdayData[0][1], yesterdayData[0][2]);
        }
    }

    /**
     * 设置7日订单数据
     */
    private void setSevenData() {
        if (isAm) {
            addAmOrderView(sevenData);
        } else if (isDm) {
            addDmOrderView(sevenData);
        } else {
            addPartnerOrderView(sevenData[0][0], sevenData[0][1], sevenData[0][2]);
        }
    }

    /**
     * 设置30日订单数据
     */
    private void setThirtyData() {
        if (isAm) {
            addAmOrderView(thirtyData);
        } else if (isDm) {
            addDmOrderView(thirtyData);
        } else {
            addPartnerOrderView(thirtyData[0][0], thirtyData[0][1], thirtyData[0][2]);
        }
    }

    /**
     * 设置全部订单数据
     */
    private void setAllData() {
        if (isAm) {
            addAmOrderView(allData);
        } else if (isDm) {
            addDmOrderView(allData);
        } else {
            addPartnerOrderView(allData[0][0], allData[0][1], allData[0][2]);
        }
    }

    private void loadYesterday() {
        OrderDataApi api = new OrderDataApi();
        api.setInterPath(String.format(Constants.ORDER_YESTERDAY, partnerBean.getId()));
        api.day = 1;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderDayBean>() {
            @Override
            public void onResponse(OrderDayBean response) {
                if (isFinishing()) {
                    return;
                }
                if (response != null && response.getList() != null && response.getList().size() > 0) {
                    OrderDayBean.ListBean.SaleStatBean retail = response.getList().get(0).getSaleStat();
                    OrderDayBean.ListBean.SaleStatBean dm = response.getList().get(0).getSaleStatDM();
                    OrderDayBean.ListBean.SaleStatBean sDm = response.getList().get(0).getSaleStatSDM();
                    OrderDayBean.ListBean.SaleStatBean am = response.getList().get(0).getSaleStatAM();
                    if (isAm || isDm) {
                        yesterdayData[0][0] = retail.getCount();
                        yesterdayData[1][0] = retail.getPayAmount();
                        yesterdayData[2][0] = retail.getPartnerRebates();
                        if (isAm) {
                            yesterdayData[0][1] = dm.getCount() + sDm.getCount() + am.getCount();
                            yesterdayData[1][1] = dm.getPayAmount() + sDm.getPayAmount() + am.getPayAmount();
                            yesterdayData[2][1] = dm.getParentRebates() + sDm.getSuperRebates() + am.getMasterRebates();
                        } else {
                            yesterdayData[0][1] = dm.getCount();
                            yesterdayData[1][1] = dm.getPayAmount();
                            yesterdayData[2][1] = dm.getParentRebates();
                            yesterdayData[0][2] = sDm.getCount();
                            yesterdayData[1][2] = sDm.getPayAmount();
                            yesterdayData[2][2] = sDm.getSuperRebates();
                        }
                    } else {
                        yesterdayData[0][0] = retail.getCount();
                        yesterdayData[0][1] = retail.getPayAmount();
                        yesterdayData[0][2] = retail.getPartnerRebates();
                    }
                }
            }
        });
    }


    private void loadSomeDay(final int dayNum) {
        OrderDataApi api = new OrderDataApi();
        api.setInterPath(String.format(Constants.ORDER_SOMEDAY, partnerBean.getId()));
        api.day = dayNum;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderDayBean>() {
            @Override
            public void onResponse(OrderDayBean response) {
                if (isFinishing()) {
                    return;
                }
                double[][] result = null;
                if (dayNum == 6) {
                    result = sevenData;
                } else {
                    result = thirtyData;
                }
                if (response != null && response.getList() != null && response.getList().size() > 0) {
                    OrderDayBean.ListBean.SaleStatBean retail = response.getList().get(0).getSaleStat();
                    OrderDayBean.ListBean.SaleStatBean dm = response.getList().get(0).getSaleStatDM();
                    OrderDayBean.ListBean.SaleStatBean sDm = response.getList().get(0).getSaleStatSDM();
                    OrderDayBean.ListBean.SaleStatBean am = response.getList().get(0).getSaleStatAM();
                    if (isAm || isDm) {
                        result[0][0] = retail.getCount();
                        result[1][0] = retail.getPayAmount();
                        result[2][0] = retail.getPartnerRebates();
                        if (isAm) {
                            result[0][1] = dm.getCount() + sDm.getCount() + am.getCount();
                            result[1][1] = dm.getPayAmount() + sDm.getPayAmount() + am.getPayAmount();
                            result[2][1] = dm.getParentRebates() + sDm.getSuperRebates() + am.getMasterRebates();
                        } else {
                            result[0][1] = dm.getCount();
                            result[1][1] = dm.getPayAmount();
                            result[2][1] = dm.getParentRebates();
                            result[0][2] = sDm.getCount();
                            result[1][2] = sDm.getPayAmount();
                            result[2][2] = sDm.getSuperRebates();
                        }
                    } else {
                        result[0][0] = retail.getCount();
                        result[0][1] = retail.getPayAmount();
                        result[0][2] = retail.getPartnerRebates();
                    }
                }
                addTodayData(result);
            }
        });
    }

    private void loadAllDay() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.ORDER_ALL, partnerBean.getId()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderDayBean>() {
            @Override
            public void onResponse(OrderDayBean response) {
                if (isFinishing()) {
                    return;
                }
                if (response != null && response.getList() != null && response.getList().size() > 0) {
                    OrderDayBean.ListBean.SaleStatBean retail = response.getList().get(0).getSaleStat();
                    OrderDayBean.ListBean.SaleStatBean dm = response.getList().get(0).getSaleStatDM();
                    OrderDayBean.ListBean.SaleStatBean sDm = response.getList().get(0).getSaleStatSDM();
                    OrderDayBean.ListBean.SaleStatBean am = response.getList().get(0).getSaleStatAM();
                    if (isAm || isDm) {
                        allData[0][0] = retail.getCount();
                        allData[1][0] = retail.getPayAmount();
                        allData[2][0] = retail.getPartnerRebates();
                        if (isAm) {
                            allData[0][1] = dm.getCount() + sDm.getCount() + am.getCount();
                            allData[1][1] = dm.getPayAmount() + sDm.getPayAmount() + am.getPayAmount();
                            allData[2][1] = dm.getParentRebates() + sDm.getSuperRebates() + am.getMasterRebates();
                        } else {
                            allData[0][1] = dm.getCount();
                            allData[1][1] = dm.getPayAmount();
                            allData[2][1] = dm.getParentRebates();
                            allData[0][2] = sDm.getCount();
                            allData[1][2] = sDm.getPayAmount();
                            allData[2][2] = sDm.getSuperRebates();
                        }
                    } else {
                        allData[0][0] = retail.getCount();
                        allData[0][1] = retail.getPayAmount();
                        allData[0][2] = retail.getPartnerRebates();
                    }
                }
//                addTodayData(allData);   //全部不加今天的数据
            }
        });
    }


    /**
     * 添加今日数据
     */
    private void addTodayData(double[][] data) {
        int length = data.length;
        for (int i = 0; i < length; i++) {
            double[] a = data[i];
            int size = a.length;
            for (int j = 0; j < size; j++) {
                data[i][j] += todayData[i][j];
            }
        }
    }

    private void addPartnerOrderView(double num, double amount, double earning) {
        orderLl.removeAllViews();
        orderLl.setOrientation(LinearLayout.HORIZONTAL);
        orderLl.setPadding(0, 0, 0, 0);
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(-2, ScreenUtil.dip2px(80));
        ll.weight = 1;
        for (int i = 0; i < 3; i++) {
            TextView textView = new TextView(this);
            textView.setLineSpacing(ScreenUtil.dip2px(5), 1);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(R.color.color_black38));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
            StringBuilder builder = new StringBuilder();
            if (i == 0) {
                builder.append((int) num).append("单").append("\n").append("订单数");
            } else if (i == 1) {
                builder.append(Util.getNumberFormat(amount)).append("元").append("\n").append("成交额");
            } else {
                builder.append(Util.getNumberFormat(earning)).append("元").append("\n").append("预计收益");
            }
            earningSecondConver(textView, builder.toString());
            orderLl.addView(textView, ll);
        }
    }

    private void addDmOrderView(double[][] data) {
        orderLl.removeAllViews();
        calculateLocation();
        for (int i = 0; i < 4; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            if (i == 0) {
                linearLayout.setPadding(0, 0, 0, ScreenUtil.dip2px(12));
                LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ScreenUtil.dip2px(88) + dmItemWidth, -2);
                TextView textView = new TextView(this);
                textView.setText("零售业绩");
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                textView.setTextColor(getResources().getColor(R.color.color_black38));
                textView.setPadding(ScreenUtil.dip2px(88), 0, 0, 0);
                linearLayout.addView(textView, ll);
                ll = new LinearLayout.LayoutParams(dmItemWidth, -2);
                TextView textView1 = new TextView(this);
                textView1.setText("买手业绩");
                textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                textView1.setTextColor(getResources().getColor(R.color.color_black38));
                textView1.setGravity(Gravity.CENTER);
                linearLayout.addView(textView1, ll);
                TextView textView2 = new TextView(this);
                textView2.setGravity(Gravity.RIGHT);
                textView2.setText("DM业绩");
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                textView2.setTextColor(getResources().getColor(R.color.color_black38));
                linearLayout.addView(textView2, ll);
            } else {
                if (i != 3) {
                    linearLayout.setPadding(0, 0, 0, ScreenUtil.dip2px(12));
                }
                for (int j = 0; j < 4; j++) {
                    TextView textView = new TextView(this);
                    if (j == 0) {
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                        textView.setTextColor(getResources().getColor(R.color.color_black38));
                        switch (i) {
                            case 1:
                                textView.setText("订单数/单");
                                break;
                            case 2:
                                textView.setText("销售额/元");
                                break;
                            case 3:
                                textView.setText("预计收益/元");
                                break;
                        }
                        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ScreenUtil.dip2px(88), -2);
                        linearLayout.addView(textView, ll);
                    } else {
                        TextView textView1 = new TextView(this);
                        textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                        textView.setTextColor(getResources().getColor(R.color.color_black87));
                        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(dmItemWidth, -2);
                        if (i == 1) {
                            textView1.setText((int) data[i - 1][j - 1] + "");
                        } else {
                            textView1.setText(wipePoint(data[i - 1][j - 1]));
                        }
                        if (j == 3) {
                            textView1.setGravity(Gravity.RIGHT);
                        } else if (j == 2) {
                            textView1.setPadding(dmOffer, 0, 0, 0);
                        }
                        linearLayout.addView(textView1, ll);
                    }
                }
            }
            orderLl.addView(linearLayout);
        }
    }

    private String wipePoint(double amount) {
        String s = String.valueOf(amount);
        int index = s.indexOf(".");
        if (index > 0 && s.length() - index > 3) {
            int a = (int) (amount * 100 + 0.5);
            return String.valueOf((double) a / 100);
        } else {
            return String.valueOf(amount);
        }
    }


    private void addAmOrderView(double[][] data) {
        orderLl.removeAllViews();
        calculateAmLocation();
        for (int i = 0; i < 4; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            if (i == 0) {
                linearLayout.setPadding(0, 0, 0, ScreenUtil.dip2px(12));
                LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(2 * itemWidth, -2);
                TextView textView = new TextView(this);
                textView.setText("零售业绩");
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                textView.setTextColor(getResources().getColor(R.color.color_black38));
                textView.setPadding(itemWidth + offer, 0, 0, 0);
                linearLayout.addView(textView, ll);
                ll = new LinearLayout.LayoutParams(itemWidth, -2);
                TextView textView2 = new TextView(this);
                textView2.setGravity(Gravity.RIGHT);
                textView2.setText("团队业绩");
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                textView2.setTextColor(getResources().getColor(R.color.color_black38));
                linearLayout.addView(textView2, ll);
            } else {
                if (i != 3) {
                    linearLayout.setPadding(0, 0, 0, ScreenUtil.dip2px(12));
                }
                for (int j = 0; j < 3; j++) {
                    TextView textView = new TextView(this);
                    if (j == 0) {
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                        textView.setTextColor(getResources().getColor(R.color.color_black38));
                        switch (i) {
                            case 1:
                                textView.setText("订单数/单");
                                break;
                            case 2:
                                textView.setText("销售额/元");
                                break;
                            case 3:
                                textView.setText("预计收益/元");
                                break;
                        }
                        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(itemWidth, -2);
                        linearLayout.addView(textView, ll);
                    } else {
                        TextView textView1 = new TextView(this);
                        textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                        textView.setTextColor(getResources().getColor(R.color.color_black87));
                        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(itemWidth, -2);
                        if (i == 1) {
                            textView1.setText((int) data[i - 1][j - 1] + "");
                        } else {
                            textView1.setText(wipePoint(data[i - 1][j - 1]));
                        }
                        if (j == 1) {
                            textView1.setPadding(offer, 0, 0, 0);
                        }
                        if (j == 2) {
                            textView1.setGravity(Gravity.RIGHT);
                        }
                        linearLayout.addView(textView1, ll);
                    }
                }
            }
            orderLl.addView(linearLayout);
        }
    }

    private void calculateLocation() {
        if (dmItemWidth == 0) {
            dmItemWidth = (width - ScreenUtil.dip2px(120)) / 3;
            TextPaint paint = new TextPaint();
            paint.setTextSize(Util.dip2px(this, 10));
            Rect rect = new Rect();
            paint.getTextBounds("零售业绩", 0, 4, rect);
            dmOffer = (dmItemWidth - rect.width()) / 2;
        }
    }

    private void calculateAmLocation() {
        if (itemWidth == 0) {
            itemWidth = (width - ScreenUtil.dip2px(32)) / 3;
            TextPaint paint = new TextPaint();
            paint.setTextSize(Util.dip2px(this, 10));
            Rect rect = new Rect();
            paint.getTextBounds("零售业绩", 0, 4, rect);
            offer = (itemWidth - rect.width()) / 2;
        }
    }

    private void earningSecondConver(TextView textView, String text) {
        StringBuilder builder = new StringBuilder(text);
        int yuan = builder.toString().indexOf("元");
        if (yuan < 0) {
            yuan = builder.toString().indexOf("单");
        }
        if (yuan < 0) {
            return;
        }
        SpannableString spannableString = new SpannableString(builder.toString());

        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor("#e0000000"));
        spannableString.setSpan(colorSpan1, 0, yuan + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(1.4f);
        spannableString.setSpan(sizeSpan1, 0, yuan, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan sizeSpan2 = new RelativeSizeSpan(0.9f);
        spannableString.setSpan(sizeSpan2, yuan, yuan + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);
    }

}
