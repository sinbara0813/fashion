package com.d2cmall.buyer.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.PartnerAccountDetailActivity;
import com.d2cmall.buyer.activity.PerformanceSummaryActivity;
import com.d2cmall.buyer.api.OrderDataApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.OrderDayBean;
import com.d2cmall.buyer.bean.PartBillSummaryBean;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.bean.PartnerSummaryBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Dec: 买手中心
 * Author: hrb
 * Date: 2018/4/16 14:00
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class BuyerSurveyFragment extends BaseFragment {

    @Bind(R.id.income_check_all)
    TextView incomeCheckAll;
    @Bind(R.id.received_amount)
    TextView receivedAmount;
    @Bind(R.id.stay_amount)
    TextView stayAmount;
    @Bind(R.id.deposit_amount)
    TextView depositAmount;
    @Bind(R.id.earning_iv)
    ImageView earningIv;
    @Bind(R.id.retail_amount)
    TextView retailAmount;
    @Bind(R.id.team_amount)
    TextView teamAmount;
    @Bind(R.id.other_amount)
    TextView otherAmount;
    @Bind(R.id.deduction_amount)
    TextView deductionAmount;
    @Bind(R.id.performance_check_all)
    TextView performanceCheckAll;
    @Bind(R.id.has_settle)
    TextView hasSettle;
    @Bind(R.id.stay_settle)
    TextView staySettle;
    @Bind(R.id.performance_iv)
    ImageView performanceIv;
    @Bind(R.id.performance_second_ll)
    LinearLayout performanceSecondLl;
    @Bind(R.id.retail_performance)
    TextView retailPerformance;
    @Bind(R.id.team_performance)
    TextView teamPerformance;
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
    @Bind(R.id.sale_iv)
    ImageView saleIv;
    @Bind(R.id.order_ll)
    LinearLayout orderLl;

    private int width;

    private int dmItemWidth;
    private int dmOffer;

    private int itemWidth;
    private int offer;

    private boolean isDm;
    private boolean isAm;
    private TextView lastDay;
    private PartnerMemberBean partnerBean;//可提现

    private PartnerSummaryBean summaryBean; //已到账
    private double hasRetailRebate;
    private double hasTeamRebate;
    private double hasOtherRebate;
    private double hasReduceRebate;

    private PartBillSummaryBean billSummaryBean;//待到账
    private double retailRebate;//零售奖励
    private double teamRebate;//团队奖励

    private int stayRetailOrderNum;//待结算 零售订单数
    private double stayRetailOrderAmount; //待结算 零售订单金额
    private int hasRetailOrderNum;//已结算 零售订单数
    private double hasRetailOrderAmount; //已结算 零售订单金额

    private int stayTeamOrderNum;//待结算 团队订单数
    private double stayTeamOrderAmount; //待结算 团队订单金额
    private int hasTeamOrderNum;//已结算 团队订单数
    private double hasTeamOrderAmount; //已结算 团队订单金额

    private double[][] todayData;
    private double[][] yesterdayData;
    private double[][] sevenData;
    private double[][] thirtyData;
    private double[][] allData;

    public static BuyerSurveyFragment newInstance(boolean is) {
        BuyerSurveyFragment buyerSurveyFragment = new BuyerSurveyFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isDm", is);
        buyerSurveyFragment.setArguments(bundle);
        return buyerSurveyFragment;
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buyer_survey, container, false);
    }

    @Override
    public void prepareView() {
        if (getArguments() != null) {
            isDm = getArguments().getBoolean("isDm");
        }
        width = ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(32);
        initIncomeLayout();
        initPerformanceLayout();
        initSaleLayout();
    }

    /**
     * 布局收益汇总
     */
    private void initIncomeLayout() {
        RelativeLayout.LayoutParams r1 = (RelativeLayout.LayoutParams) receivedAmount.getLayoutParams();
        RelativeLayout.LayoutParams r2 = (RelativeLayout.LayoutParams) stayAmount.getLayoutParams();
        RelativeLayout.LayoutParams r3 = (RelativeLayout.LayoutParams) depositAmount.getLayoutParams();
        r1.width = width / 3;
        r2.width = width / 3;
        r3.width = width / 3;
        RelativeLayout.LayoutParams ivRl = (RelativeLayout.LayoutParams) earningIv.getLayoutParams();
        ivRl.setMargins((width / 3 - earningIv.getWidth()) / 2, 0, 0, 0);
    }

    /**
     * 布局业绩汇总
     */
    private void initPerformanceLayout() {
        RelativeLayout.LayoutParams r4 = (RelativeLayout.LayoutParams) hasSettle.getLayoutParams();
        RelativeLayout.LayoutParams r5 = (RelativeLayout.LayoutParams) staySettle.getLayoutParams();
        r4.width = width / 2;
        r5.width = width / 2;
        if (isDm) {
            performanceIv.setVisibility(View.VISIBLE);
            performanceSecondLl.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams ivRl = (RelativeLayout.LayoutParams) performanceIv.getLayoutParams();
            ivRl.setMargins((width / 2 - performanceIv.getWidth()) / 2, 0, 0, 0);
        } else {
            performanceIv.setVisibility(View.GONE);
            performanceSecondLl.setVisibility(View.GONE);
        }
    }

    /**
     * 布局销售概况
     */
    private void initSaleLayout() {
        RelativeLayout.LayoutParams r1 = (RelativeLayout.LayoutParams) today.getLayoutParams();
        RelativeLayout.LayoutParams r2 = (RelativeLayout.LayoutParams) yesterday.getLayoutParams();
        RelativeLayout.LayoutParams r3 = (RelativeLayout.LayoutParams) sevenDay.getLayoutParams();
        RelativeLayout.LayoutParams r4 = (RelativeLayout.LayoutParams) thirtyDay.getLayoutParams();
        RelativeLayout.LayoutParams r5 = (RelativeLayout.LayoutParams) all.getLayoutParams();
        r1.width = width / 5;
        r2.width = width / 5;
        r3.width = width / 5;
        r4.width = width / 5;
        r5.width = width / 5;
        RelativeLayout.LayoutParams ivRl = (RelativeLayout.LayoutParams) saleIv.getLayoutParams();
        ivRl.setMargins((width / 5 - saleIv.getWidth()) / 2, 0, 0, 0);
        lastDay = today;
        today.requestLayout();
        yesterday.requestLayout();
        sevenDay.requestLayout();
        thirtyDay.requestLayout();
        all.requestLayout();
    }

    @Override
    public void doBusiness() {
        if (partnerBean == null) {
            loadPartnerInfo();
            loadSummary();
            loadBillSummary();
        }
    }

    /**
     * 提现信息
     */
    private void loadPartnerInfo() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.MEMBER_MINE);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PartnerMemberBean>() {
            @Override
            public void onResponse(PartnerMemberBean response) {
                if (!isVisibleToUser){
                    return;
                }
                if (response != null && response.getData().getPartner() != null) {
                    partnerBean = response;
                        StringBuilder builder = new StringBuilder();
                        builder.append(Util.getNumberFormat(partnerBean.getData().getPartner().getTotalAmount() - response.getData().getApplyCashAmount() - partnerBean.getData().getPartner().getCashAmount())).append("元").append("\n").append("可提现");
                        earningFirstConver(depositAmount, builder.toString());
                        if (partnerBean.getData().getPartner().getLevel() == 0) {//AM
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
                        } else if (partnerBean.getData().getPartner().getLevel() == 1) { //DM
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
                        loadToday();
                        Session.getInstance().savePartnerToFile(mContext, response.getData().getPartner());
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mContext!=null){
                    Util.showToast(mContext, Util.checkErrorType(error));
                }
            }
        });
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
     * 已到账
     */
    private void loadSummary() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.PARTNER_SUMMARY);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PartnerSummaryBean>() {
            @Override
            public void onResponse(PartnerSummaryBean response) {
                if (!isVisibleToUser){
                    return;
                }
                if (response != null && response.getData().getIncomeData() != null) {
                    summaryBean = response;
                    StringBuilder builder = new StringBuilder();
                    builder.append(Util.getNumberFormat(response.getData().getPartner().getTotalAmount())).append("元").append("\n").append("已到账");
                    earningFirstConver(receivedAmount, builder.toString());
                    calculateIncomeData();
                }
            }
        });
    }

    /**
     * 计算已到账数据
     */
    private void calculateIncomeData() {
        hasReduceRebate = 0;
        hasOtherRebate = 0;
        hasTeamRebate = 0;
        hasRetailRebate = 0;
        int size = summaryBean.getData().getIncomeData().size();
        for (int i = 0; i < size; i++) {
            PartnerSummaryBean.DataBean.IncomeDataBean dataBean = summaryBean.getData().getIncomeData().get(i);
            if (dataBean.getType().equals("BILL")) {//零售
                hasRetailRebate += dataBean.getAmount();
            } else if (dataBean.getType().equals("TEAM")) {//团队
                hasTeamRebate += dataBean.getAmount();
            } else if (dataBean.getType().equals("INVITE") || dataBean.getType().equals("MASTER")) {//其他
                hasOtherRebate += dataBean.getAmount();
            } else if (dataBean.getType().equals("DIFF")||dataBean.getType().equals("WITHHOLD")) {//扣减
                hasReduceRebate += dataBean.getAmount();
            }
        }
        setHasEarning();
    }

    /**
     * 设置已到账二级数据
     */
    private void setHasEarning() {
        retailAmount.setVisibility(View.VISIBLE);
        StringBuilder builder = new StringBuilder();
        builder.append(Util.getNumberFormat(hasRetailRebate)).append("元").append("\n").append("零售奖励");
        earningSecondConver(retailAmount, builder.toString());
        if (isDm) {
            teamAmount.setVisibility(View.VISIBLE);
            builder = new StringBuilder();
            builder.append(Util.getNumberFormat(hasTeamRebate)).append("元").append("\n").append("团队奖励");
            earningSecondConver(teamAmount, builder.toString());
        } else {
            teamAmount.setVisibility(View.GONE);
        }
        otherAmount.setVisibility(View.VISIBLE);
        builder = new StringBuilder();
        builder.append(Util.getNumberFormat(hasOtherRebate)).append("元").append("\n").append("其他奖励");
        earningSecondConver(otherAmount, builder.toString());
        deductionAmount.setVisibility(View.VISIBLE);
        builder = new StringBuilder();
        builder.append(Util.getNumberFormat(hasReduceRebate)).append("元").append("\n").append("扣减");
        earningSecondConver(deductionAmount, builder.toString());
    }

    /**
     * 设置待到账二级数据
     */
    private void setStayEarning() {
        retailAmount.setVisibility(View.VISIBLE);
        teamAmount.setVisibility(View.VISIBLE);
        otherAmount.setVisibility(View.GONE);
        deductionAmount.setVisibility(View.GONE);
        StringBuilder builder = new StringBuilder();
        builder.append(Util.getNumberFormat(retailRebate)).append("元").append("\n").append("零售奖励");
        earningSecondConver(retailAmount, builder.toString());
        builder = new StringBuilder();
        builder.append(Util.getNumberFormat(teamRebate)).append("元").append("\n").append("团队奖励");
        earningSecondConver(teamAmount, builder.toString());
    }

    /**
     * 设置提现二级数据
     */
    private void setDepositEarning() {
        retailAmount.setVisibility(View.VISIBLE);
        teamAmount.setVisibility(View.VISIBLE);
        otherAmount.setVisibility(View.GONE);
        deductionAmount.setVisibility(View.GONE);
        StringBuilder builder = new StringBuilder();
        builder.append(Util.getNumberFormat(partnerBean.getData().getPartner().getCashAmount())).append("元").append("\n").append("已提现");
        earningSecondConver(retailAmount, builder.toString());
        builder = new StringBuilder();
        builder.append(Util.getNumberFormat(partnerBean.getData().getApplyCashAmount())).append("元").append("\n").append("提现中");
        earningSecondConver(teamAmount, builder.toString());
    }

    /**
     * 待到账 业绩汇总
     */
    private void loadBillSummary() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.PARTNER_BILL_SUMMARY);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PartBillSummaryBean>() {
            @Override
            public void onResponse(PartBillSummaryBean response) {
                if (!isVisibleToUser){
                    return;
                }
                if (response != null && response.getData() != null) {
                    billSummaryBean = response;
                    calculateStayData();
                }
            }
        });
    }

    /**
     * 计算待到账数据
     */
    private void calculateStayData() {
        retailRebate = 0;
        teamRebate = 0;

        stayRetailOrderNum = 0;
        stayRetailOrderAmount = 0;
        hasRetailOrderNum = 0;
        hasRetailOrderAmount = 0;

        stayTeamOrderNum = 0;
        stayTeamOrderAmount = 0;
        hasTeamOrderNum = 0;
        hasTeamOrderAmount = 0;
        if (billSummaryBean.getData().getPartnerData() != null) {//直接
            int size = billSummaryBean.getData().getPartnerData().size();
            for (int i = 0; i < size; i++) {
                PartBillSummaryBean.DataBean.PartnerDataBean partnerDataBean = billSummaryBean.getData().getPartnerData().get(i);
                if (partnerDataBean.getStatusX() == 1) { //待
                    retailRebate += partnerDataBean.getRebate();
                    stayRetailOrderNum += partnerDataBean.getCount();
                    stayRetailOrderAmount += partnerDataBean.getAmount();
                } else if (partnerDataBean.getStatusX() == 8) { //已
                    hasRetailOrderNum += partnerDataBean.getCount();
                    hasRetailOrderAmount += partnerDataBean.getAmount();
                }
            }
        }
        if (billSummaryBean.getData().getParentData() != null) {
            int size = billSummaryBean.getData().getParentData().size();
            for (int i = 0; i < size; i++) {
                PartBillSummaryBean.DataBean.PartnerDataBean partnerDataBean = billSummaryBean.getData().getParentData().get(i);
                if (partnerDataBean.getStatusX() == 1) { //待
                    teamRebate += partnerDataBean.getRebate();
                    stayTeamOrderNum += partnerDataBean.getCount();
                    stayTeamOrderAmount += partnerDataBean.getAmount();
                } else if (partnerDataBean.getStatusX() == 8) { //已
                    hasTeamOrderNum += partnerDataBean.getCount();
                    hasTeamOrderAmount += partnerDataBean.getAmount();
                }
            }
        }
        if (billSummaryBean.getData().getSuperData() != null) {
            int size = billSummaryBean.getData().getSuperData().size();
            for (int i = 0; i < size; i++) {
                PartBillSummaryBean.DataBean.PartnerDataBean partnerDataBean = billSummaryBean.getData().getSuperData().get(i);
                if (partnerDataBean.getStatusX() == 1) { //待
                    teamRebate += partnerDataBean.getRebate();
                    stayTeamOrderNum += partnerDataBean.getCount();
                    stayTeamOrderAmount += partnerDataBean.getAmount();
                } else if (partnerDataBean.getStatusX() == 8) { //已
                    hasTeamOrderNum += partnerDataBean.getCount();
                    hasTeamOrderAmount += partnerDataBean.getAmount();
                }
            }
        }
        if (billSummaryBean.getData().getMasterData() != null) {
            int size = billSummaryBean.getData().getMasterData().size();
            for (int i = 0; i < size; i++) {
                PartBillSummaryBean.DataBean.PartnerDataBean partnerDataBean = billSummaryBean.getData().getMasterData().get(i);
                if (partnerDataBean.getStatusX() == 1) { //待
                    teamRebate += partnerDataBean.getRebate();
                    stayTeamOrderNum += partnerDataBean.getCount();
                    stayTeamOrderAmount += partnerDataBean.getAmount();
                } else if (partnerDataBean.getStatusX() == 8) { //已
                    hasTeamOrderNum += partnerDataBean.getCount();
                    hasTeamOrderAmount += partnerDataBean.getAmount();
                }
            }
        }
        //待到账
        StringBuilder builder = new StringBuilder();
        builder.append(Util.getNumberFormat(retailRebate + teamRebate)).append("元").append("\n").append("待到账");
        earningFirstConver(stayAmount, builder.toString());
        //业绩汇总
        builder = new StringBuilder();
        builder.append(Util.getNumberFormat(stayRetailOrderAmount + stayTeamOrderAmount)).append("元  ").append(stayRetailOrderNum + stayTeamOrderNum).append("单")
                .append("\n").append("待结算");
        performanceFirstConver(staySettle, builder.toString());
        builder = new StringBuilder();
        builder.append(Util.getNumberFormat(hasRetailOrderAmount + hasTeamOrderAmount)).append("元  ").append(hasRetailOrderNum + hasTeamOrderNum).append("单")
                .append("\n").append("已结算");
        performanceFirstConver(hasSettle, builder.toString());
        setSecondPerformanceData(hasRetailOrderAmount, hasRetailOrderNum, hasTeamOrderAmount, hasTeamOrderNum);
    }

    private void setSecondPerformanceData(double retailAmount, int retailNum, double teamAmount, int teamNum) {
        StringBuilder builder = new StringBuilder();
        builder.append(Util.getNumberFormat(retailAmount))
                .append("元  ").append(retailNum).append("单").append("\n").append("零售业绩");
        performanceSecondConver(retailPerformance, builder.toString());
        builder = new StringBuilder();
        builder.append(Util.getNumberFormat(teamAmount))
                .append("元  ").append(teamNum).append("单").append("\n").append("团队业绩");
        performanceSecondConver(teamPerformance, builder.toString());
    }

    private void loadToday() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.ORDER_TODAY, partnerBean.getData().getPartner().getId()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderDayBean>() {
            @Override
            public void onResponse(OrderDayBean response) {
                if (!isVisibleToUser){
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
        api.setInterPath(String.format(Constants.ORDER_YESTERDAY, partnerBean.getData().getPartner().getId()));
        api.day = 1;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderDayBean>() {
            @Override
            public void onResponse(OrderDayBean response) {
                if (!isVisibleToUser){
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
        api.setInterPath(String.format(Constants.ORDER_SOMEDAY, partnerBean.getData().getPartner().getId()));
        api.day = dayNum;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderDayBean>() {
            @Override
            public void onResponse(OrderDayBean response) {
                if (!isVisibleToUser){
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
        api.setInterPath(String.format(Constants.ORDER_ALL, partnerBean.getData().getPartner().getId()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderDayBean>() {
            @Override
            public void onResponse(OrderDayBean response) {
                if (!isVisibleToUser){
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
                //addTodayData(allData);
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
        if(orderLl ==null){
            return;
        }
        orderLl.removeAllViews();
        orderLl.setOrientation(LinearLayout.HORIZONTAL);
        orderLl.setPadding(0, 0, 0, 0);
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(-2, ScreenUtil.dip2px(80));
        ll.weight = 1;
        for (int i = 0; i < 3; i++) {
            TextView textView = new TextView(mContext);
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
            LinearLayout linearLayout = new LinearLayout(mContext);
            if (i == 0) {
                linearLayout.setPadding(0, 0, 0, ScreenUtil.dip2px(12));
                LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ScreenUtil.dip2px(88) + dmItemWidth, -2);
                TextView textView = new TextView(mContext);
                textView.setText("零售业绩");
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                textView.setTextColor(getResources().getColor(R.color.color_black38));
                textView.setPadding(ScreenUtil.dip2px(88), 0, 0, 0);
                linearLayout.addView(textView, ll);
                ll = new LinearLayout.LayoutParams(dmItemWidth, -2);
                TextView textView1 = new TextView(mContext);
                textView1.setText("买手业绩");
                textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                textView1.setTextColor(getResources().getColor(R.color.color_black38));
                textView1.setGravity(Gravity.CENTER);
                linearLayout.addView(textView1, ll);
                TextView textView2 = new TextView(mContext);
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
                    TextView textView = new TextView(mContext);
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
                        TextView textView1 = new TextView(mContext);
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

    private void addAmOrderView(double[][] data) {
        orderLl.removeAllViews();
        calculateAmLocation();
        for (int i = 0; i < 4; i++) {
            LinearLayout linearLayout = new LinearLayout(mContext);
            if (i == 0) {
                linearLayout.setPadding(0, 0, 0, ScreenUtil.dip2px(12));
                LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(2 * itemWidth, -2);
                TextView textView = new TextView(mContext);
                textView.setText("零售业绩");
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                textView.setTextColor(getResources().getColor(R.color.color_black38));
                textView.setPadding(itemWidth + offer, 0, 0, 0);
                linearLayout.addView(textView, ll);
                ll = new LinearLayout.LayoutParams(itemWidth, -2);
                TextView textView2 = new TextView(mContext);
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
                    TextView textView = new TextView(mContext);
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
                        TextView textView1 = new TextView(mContext);
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
            paint.setTextSize(Util.dip2px(getActivity(), 10));
            Rect rect = new Rect();
            paint.getTextBounds("零售业绩", 0, 4, rect);
            dmOffer = (dmItemWidth - rect.width()) / 2;
        }
    }

    private void calculateAmLocation() {
        if (itemWidth == 0) {
            itemWidth = (width - ScreenUtil.dip2px(32)) / 3;
            TextPaint paint = new TextPaint();
            paint.setTextSize(Util.dip2px(getActivity(), 10));
            Rect rect = new Rect();
            paint.getTextBounds("零售业绩", 0, 4, rect);
            offer = (itemWidth - rect.width()) / 2;
        }
    }

    private void earningFirstConver(TextView textView, String text) {
        StringBuilder builder = new StringBuilder(text);
        int yuan = builder.toString().indexOf("元");
        if (yuan < 0)
            return;
        SpannableString spannableString = new SpannableString(builder.toString());

        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor("#ffffff"));
        spannableString.setSpan(colorSpan1, 0, yuan + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(2.0f);
        spannableString.setSpan(sizeSpan1, 0, yuan, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan sizeSpan2 = new RelativeSizeSpan(0.9f);
        spannableString.setSpan(sizeSpan2, yuan, yuan + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);
    }

    private void earningSecondConver(TextView textView, String text) {
        StringBuilder builder = new StringBuilder(text);
        int yuan = builder.toString().indexOf("元");
        if (yuan < 0) {
            yuan = builder.toString().indexOf("单");
        }
        if (yuan < 0)
            return;
        SpannableString spannableString = new SpannableString(builder.toString());

        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor("#e0000000"));
        spannableString.setSpan(colorSpan1, 0, yuan + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(1.4f);
        spannableString.setSpan(sizeSpan1, 0, yuan, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan sizeSpan2 = new RelativeSizeSpan(0.9f);
        spannableString.setSpan(sizeSpan2, yuan, yuan + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);
    }

    private void performanceFirstConver(TextView textView, String text) {
        StringBuilder builder = new StringBuilder(text);
        int yuan = builder.toString().indexOf("元");
        int dan = builder.toString().indexOf("单");
        if (yuan < 0)
            return;
        SpannableString spannableString = new SpannableString(builder.toString());

        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor("#ffffff"));
        spannableString.setSpan(colorSpan1, 0, dan + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(2.0f);
        spannableString.setSpan(sizeSpan1, 0, yuan, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan sizeSpan11 = new RelativeSizeSpan(2.0f);
        spannableString.setSpan(sizeSpan11, yuan + 1, dan, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan sizeSpan2 = new RelativeSizeSpan(0.9f);
        spannableString.setSpan(sizeSpan2, yuan, yuan + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan sizeSpan22 = new RelativeSizeSpan(0.9f);
        spannableString.setSpan(sizeSpan22, dan, dan + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);
    }

    private void performanceSecondConver(TextView textView, String text) {
        StringBuilder builder = new StringBuilder(text);
        int yuan = builder.toString().indexOf("元");
        int dan = builder.toString().indexOf("单");
        if (yuan < 0)
            return;
        SpannableString spannableString = new SpannableString(builder.toString());

        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor("#e0000000"));
        spannableString.setSpan(colorSpan1, 0, dan + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(1.4f);
        spannableString.setSpan(sizeSpan1, 0, yuan, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan sizeSpan11 = new RelativeSizeSpan(1.4f);
        spannableString.setSpan(sizeSpan11, yuan + 1, dan, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan sizeSpan2 = new RelativeSizeSpan(0.9f);
        spannableString.setSpan(sizeSpan2, yuan, yuan + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan sizeSpan22 = new RelativeSizeSpan(0.9f);
        spannableString.setSpan(sizeSpan22, dan, dan + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);
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

    @OnClick({R.id.income_check_all, R.id.received_amount, R.id.stay_amount, R.id.deposit_amount, R.id.performance_check_all, R.id.has_settle, R.id.stay_settle, R.id.market_question, R.id.today, R.id.yesterday, R.id.seven_day, R.id.thirty_day, R.id.all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.income_check_all://查看收益汇总明细
                Intent intent1=new Intent(mContext,PartnerAccountDetailActivity.class);
                mContext.startActivity(intent1);
                break;
            case R.id.received_amount://已到账
                selectEarning(0);
                setHasEarning();
                break;
            case R.id.stay_amount: //待到账
                if (isDm) {
                    selectEarning(1);
                    setStayEarning();
                }
                break;
            case R.id.deposit_amount: //已提现
                selectEarning(2);
                setDepositEarning();
                break;
            case R.id.performance_check_all://查看业绩汇总明细
                Intent intent = new Intent(mContext, PerformanceSummaryActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.has_settle://已结算
                if (isDm) {
                    selectPerformance(0);
                    setSecondPerformanceData(hasRetailOrderAmount, hasRetailOrderNum, hasTeamOrderAmount, hasTeamOrderNum);
                }
                break;
            case R.id.stay_settle://待结算
                if (isDm) {
                    selectPerformance(1);
                    setSecondPerformanceData(stayRetailOrderAmount, stayRetailOrderNum, stayTeamOrderAmount, stayTeamOrderNum);
                }
                break;
            case R.id.market_question://销售概况提示
                showToast(getString(R.string.sale_question_desc));
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
        }
    }

    private void selectEarning(int index) {
        RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) earningIv.getLayoutParams();
        int x = (width / 3 - earningIv.getWidth()) / 2;
        int y = index * width / 3;
        int z = (x + y);
        rl.setMargins(z, 0, 0, 0);
        earningIv.requestLayout();
    }

    private void selectPerformance(int index) {
        RelativeLayout.LayoutParams ivR4 = (RelativeLayout.LayoutParams) performanceIv.getLayoutParams();
        ivR4.setMargins(((width / 2 - performanceIv.getWidth()) / 2 + index * width / 2), 0, 0, 0);
        performanceIv.requestLayout();
    }

    private void selectDay(TextView textView, int index) {
        if (lastDay != null) {
            lastDay.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            lastDay.setTextColor(getResources().getColor(R.color.color_black38));
        }
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        textView.setTextColor(getResources().getColor(R.color.trans_87_color_white));
        RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) saleIv.getLayoutParams();
        rl.setMargins((width / 5 - saleIv.getWidth()) / 2 + index * width / 5, 0, 0, 0);
        lastDay = textView;
    }

    private void showToast(String text){
        //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
        LayoutInflater inflater = getActivity().getLayoutInflater();//调用Activity的getLayoutInflater()
        View view = inflater.inflate(R.layout.layout_toast, null); //加載layout下的布局
        TextView title = (TextView) view.findViewById(R.id.textView);
        title.setText(text); //toast的标题
        Toast toast = new Toast(mContext.getApplicationContext());
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.setView(view); //添加视图文件
        toast.show();
    }
}
