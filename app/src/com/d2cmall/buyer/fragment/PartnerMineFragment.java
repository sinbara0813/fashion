package com.d2cmall.buyer.fragment;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.AddCounselorActivity;
import com.d2cmall.buyer.activity.ApplyCashActivity;
import com.d2cmall.buyer.activity.ComplaintActivity;
import com.d2cmall.buyer.activity.HighIncomeProductListActivity;
import com.d2cmall.buyer.activity.InviteNewActivity;
import com.d2cmall.buyer.activity.PartnerCashActivity;
import com.d2cmall.buyer.activity.PartnerCashIdentificationActivity;
import com.d2cmall.buyer.activity.PartnerCenterActivity1;
import com.d2cmall.buyer.activity.PartnerCertificateActivity;
import com.d2cmall.buyer.activity.PartnerGiftsActivity;
import com.d2cmall.buyer.activity.PartnerInviteRecordActivity;
import com.d2cmall.buyer.activity.PartnerNoticeActivity;
import com.d2cmall.buyer.activity.PartnerPersonInfoActivity;
import com.d2cmall.buyer.activity.SaleSchoolActivity;
import com.d2cmall.buyer.activity.SettingActivity;
import com.d2cmall.buyer.activity.VisitorListActivity;
import com.d2cmall.buyer.activity.WebActivity;
import com.d2cmall.buyer.adapter.SaleSchoolAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.BuyerVisitorApi;
import com.d2cmall.buyer.api.SaleSchoolApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.bean.OrderDayBean;
import com.d2cmall.buyer.bean.PartBillSummaryBean;
import com.d2cmall.buyer.bean.PartnerDayVisitorBean;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.bean.PartnerNoticeBean;
import com.d2cmall.buyer.bean.PartnerSummaryBean;
import com.d2cmall.buyer.bean.SaleSchoolTagsBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.D2CFrameLayout;
import com.d2cmall.buyer.widget.PartnerScrollView;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.d2cmall.buyer.widget.PullToMemberPop;
import com.d2cmall.buyer.widget.RoundedImageView;
import com.d2cmall.buyer.widget.SwitcherView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static com.d2cmall.buyer.Constants.GET_PARTNER_VISITOR_YESTERDAY_TODAY_URL;

/**
 * Created by Administrator on 2018/9/19.
 * Description : PartnerMineFragment
 */

public class PartnerMineFragment extends MineSuperFragment {
    @Bind(R.id.iv_setting)
    ImageView ivSetting;
    @Bind(R.id.text_adviser)
    TextView textAdviser;
    @Bind(R.id.iv_person_head)
    RoundedImageView ivPersonHead;
    @Bind(R.id.tv_red_point)
    TextView tvRedPoint;
    @Bind(R.id.tv_person_name)
    TextView tvPersonName;
    @Bind(R.id.tv_person_phone)
    TextView tvPersonPhone;
    @Bind(R.id.tv_today_profit)
    TextView tvTodayProfit;
    @Bind(R.id.ll_today_profit)
    LinearLayout llTodayProfit;
    @Bind(R.id.tv_today_order)
    TextView tvTodayOrder;
    @Bind(R.id.ll_today_order)
    LinearLayout llTodayOrder;
    @Bind(R.id.tv_today_sale)
    TextView tvTodaySale;
    @Bind(R.id.ll_today_sale1)
    LinearLayout llTodayOrder1;
    @Bind(R.id.ll_today_sale)
    LinearLayout llTodaySale;
    @Bind(R.id.point_progress)
    ProgressBar pointProgress;
    @Bind(R.id.tv_point_progress)
    TextView tvPointProgress;
    @Bind(R.id.tv_point_detial)
    TextView tvPointDetial;
    @Bind(R.id.partner_sale_card)
    RelativeLayout partnerSaleCard;
    @Bind(R.id.tv_notice)
    SwitcherView tvNotice;
    @Bind(R.id.ll_partner_notice)
    LinearLayout llPartnerNotice;
    @Bind(R.id.iv_shakedown)
    ImageView ivShakedown;
    @Bind(R.id.iv_person_type)
    ImageView ivPersonType;
    @Bind(R.id.rl_person_info)
    RelativeLayout rlPersonInfo;
    @Bind(R.id.ll_invite)
    LinearLayout llInvite;
    @Bind(R.id.ll_learn)
    LinearLayout llLearn;
    @Bind(R.id.ll_data)
    LinearLayout llData;
    @Bind(R.id.tv_sale_detial)
    TextView tvSaleDetial;
    @Bind(R.id.iv_arrow_sale)
    ImageView ivArrowSale;
    @Bind(R.id.rl_sale_title)
    RelativeLayout rlSaleTitle;
    @Bind(R.id.tv_total_profit)
    TextView tvTotalProfit;
    @Bind(R.id.ll_totla_sale)
    LinearLayout llTotlaSale;
    @Bind(R.id.tv_total_order)
    TextView tvTotalOrder;
    @Bind(R.id.ll_total_sale)
    LinearLayout llTotalSale;
    @Bind(R.id.tv_total_sale)
    TextView tvTotalSale;
    @Bind(R.id.ll_total_sale_num)
    LinearLayout llTotalSaleNum;
    @Bind(R.id.tv_valid_cash)
    TextView tvValidCash;
    @Bind(R.id.ll_total_cash)
    LinearLayout llTotalCash;
    @Bind(R.id.tv_ongoing_cash)
    TextView tvOngoingCash;
    @Bind(R.id.ll_ongoing_cash)
    LinearLayout llOngoingCash;
    @Bind(R.id.tv_cashed)
    TextView tvCashed;
    @Bind(R.id.ll_cashed)
    LinearLayout llCashed;
    @Bind(R.id.ll_cash)
    LinearLayout llCash;
    @Bind(R.id.tv_sale_detial1)
    TextView tvSaleDetial1;
    @Bind(R.id.iv_arrow_sale1)
    ImageView ivArrowSale1;
    @Bind(R.id.rl_sale_detial)
    RelativeLayout rlSaleDetial;
    @Bind(R.id.tv_ongoing_order)
    TextView tvOngoingOrder;
    @Bind(R.id.ll_ongoing_order)
    LinearLayout llOngoingOrder;
    @Bind(R.id.tv_finish_order)
    TextView tvFinishOrder;
    @Bind(R.id.ll_finish_order)
    LinearLayout llFinishOrder;
    @Bind(R.id.tv_cash_record)
    TextView tvCashRecord;
    @Bind(R.id.iv_arrow_cash)
    ImageView ivArrowCash;
    @Bind(R.id.rl_valid_cash)
    RelativeLayout rlValidCash;
    @Bind(R.id.tv_valid_cash1)
    TextView tvValidCash1;
    @Bind(R.id.tv_open_order)
    TextView tvOpenOrder;
    @Bind(R.id.rl_open_order)
    RelativeLayout rlOpenOrder;
    @Bind(R.id.tv_heigh_rebeat)
    TextView tvHeighRebeat;
    @Bind(R.id.rl_heigh_rebeat)
    RelativeLayout rlHeighRebeat;
    @Bind(R.id.tv_to_school)
    TextView tvToSchool;
    @Bind(R.id.iv_right_arrow6)
    ImageView ivRightArrow6;
    @Bind(R.id.partner_school_container)
    RecyclerView partnerSchoolContainer;
    @Bind(R.id.ll_sale_school)
    LinearLayout llSaleSchool;
    @Bind(R.id.ll_my_gift)
    LinearLayout llMyGift;
    @Bind(R.id.ll_my_team)
    LinearLayout llMyTeam;
    @Bind(R.id.ll_invite_friend)
    LinearLayout llInviteFriend;
    @Bind(R.id.ll_my_adviser)
    LinearLayout llMyAdviser;
    @Bind(R.id.ll_problem)
    LinearLayout llProblem;
    @Bind(R.id.ll_certificate)
    LinearLayout llCertificate;
    @Bind(R.id.scroll_view)
    PartnerScrollView scrollView;
    @Bind(R.id.title_setting_iv)
    ImageView titleSettingIv;
    @Bind(R.id.text_title_name)
    TextView textTitleName;
    @Bind(R.id.text_title_adviser)
    TextView textTitleAdviser;
    @Bind(R.id.title)
    RelativeLayout title;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.tv_enter_mine)
    TextView tvEnterMine;
    @Bind(R.id.ll_go_cash)
    LinearLayout llGoCash;
    @Bind(R.id.tv_today_visitor)
    TextView tvTodayVisitor;
    @Bind(R.id.ll_today_visitor)
    LinearLayout llTodayVisitor;
    @Bind(R.id.ll_enter_mine)
    LinearLayout llEnterMine;

    private boolean hasToOtherActivity = false;
    private PartnerMemberBean.DataBean.PartnerBean partnerBean;
    private List<SaleSchoolTagsBean.DataBean.ThemeTagsBean> themeTags;
    private List<SaleSchoolTagsBean.DataBean.ThemeTagsBean> tempTags;
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
    private double todayProfit = 0.0;//今日收益
    private int todaySaleCount = 0;//今日销售单数
    private double todaySaleAmout = 0.0;//今日销售额
    private boolean isDm;
    private boolean isAm;
    private UserBean.DataEntity.MemberEntity user;
    public static final int IMAGE_SIZE = 32768;
    private float scale = 0.8F;
    private ArrayList<String> notices;
    private boolean backToMine=false;
    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_partner_mine, container, false);
    }

    @Override
    public void doBusiness() {
        if (themeTags == null) {
            themeTags = new ArrayList<>();
        }
        initView();
        loadPartnerData();
        showGuideAnimation();
    }

    private void showGuideAnimation() {
        boolean isGuideToMember = D2CApplication.mSharePref.getSharePrefBoolean("isGuideToMember", false);
        if(!isGuideToMember){
            D2CApplication.mSharePref.putSharePrefBoolean("isGuideToMember", true);
            PullToMemberPop pullToMemberPop = new PullToMemberPop(mContext);
            pullToMemberPop.show(((Activity)mContext).getWindow().getDecorView());
        }
    }

    private void initView() {
        scrollView.setScrollViewListener(new PartnerScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(PartnerScrollView observableScrollView, int x, int y, int oldx, int oldy) {
                if (y <= 0) {
                    title.setBackgroundColor(getResources().getColor(R.color.transparent));
                    title.setClickable(false);
                    titleSettingIv.setVisibility(View.GONE);
                    textTitleAdviser.setVisibility(View.GONE);
                    textTitleName.setVisibility(View.GONE);
                } else {
                    title.setBackgroundColor(Color.parseColor("#FF3C404B"));
                    title.setClickable(true);
                    titleSettingIv.setVisibility(View.VISIBLE);
                    textTitleName.setVisibility(View.VISIBLE);
                    textTitleAdviser.setVisibility(View.VISIBLE);
                }
            }
        });
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //切换我的个人中心
                if (Util.loginChecked((Activity) mContext, 999)) {
                    if (changeFragmentListener != null) {
                        hasToOtherActivity = true;
                        changeFragmentListener.chaneFragment(1);
                    }
                }
                ptr.refreshComplete();
            }
        });
        user = Session.getInstance().getUserFromFile(mContext);
        if (user!=null) {
            if(Util.isEmpty(user.getNickname())){
                tvPersonName.setText("匿名_" + user.getId());
            } else {
                tvPersonName.setText(user.getNickname());
            }
            UniversalImageLoader.displayRoundImage(getContext(), user.getHead(), ivPersonHead, R.mipmap.ic_default_avatar);
        }
        ArrayList<String> strings = new ArrayList<>();
        strings.add("下拉进入个人中心...");
        strings.add("释放进入个人中心...");
        strings.add("进入个人中心...");
        D2CFrameLayout header = ptr.getHeader();
        header.setRefreshLable(strings,"#FF1C1D1D");
    }


    @Override
    public void show() {
        if(hasToOtherActivity && mContext!= null){
            user = Session.getInstance().getUserFromFile(mContext);
            refresh();
        }
        super.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isVisibleToUser){
            user = Session.getInstance().getUserFromFile(mContext);
            //退出登录回到买手界面,切换到我的界面
            if(user==null && changeFragmentListener!=null && !backToMine){
                backToMine=true;
                changeFragmentListener.chaneFragment(1);
                return;
            }else if(user!=null && user.getPartnerId()>0){
                backToMine=false;
            }
            refresh();
        }
    }

    //加载买手信息,设置倒计时等
    private void loadPartnerData() {
        SimpleApi simpleApi = new SimpleApi();
        simpleApi.setInterPath(Constants.GET_PARTNER_CENTER_URL);
        D2CApplication.httpClient.loadingRequest(simpleApi, new BeanRequest.SuccessListener<PartnerMemberBean>() {
            @Override
            public void onResponse(PartnerMemberBean partnerInfoBean) {
                if (progressBar == null) {
                    return;
                }
                scrollView.setVisibility(View.VISIBLE);
                if (partnerInfoBean == null) {
                    Util.showToast(mContext, "信息不存在请稍后重试~");
                    return;
                }
                partnerBean = partnerInfoBean.getData().getPartner();
                if (partnerBean.getLevel() == 0) {//AM
                    isDm = true;
                    isAm = true;
                } else if (partnerBean.getLevel() == 1) { //DM
                    isDm = true;
                    isAm = false;
                } else {
                    isDm = false;
                    isAm = false;
                }
                setDataToView(partnerInfoBean.getData().getPartner());
                //        试用期倒计时
                if (partnerBean.getStatusX() == 0 && !Util.isEmpty(partnerBean.getExpiredDate())) { //试用期
                    ivShakedown.setVisibility(View.VISIBLE);
                }
                startDotAnimation(partnerInfoBean);
                loadBillSummary();
                loadVisitorTextData();
                loadSummary();
                loadToday();
                loadAllDay();
                btnReload.setVisibility(View.GONE);
                imgHint.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                setEmptyView(Constants.NET_DISCONNECT);
                Util.showToast(getContext(), Util.checkErrorType(error));
            }
        });
    }

    private void refresh() {
        if (hasToOtherActivity) {
            if (user!=null && user.getPartnerId()>0) {
                loadPartnerData();
            } else {
                pointProgress.setProgress(0);
                tvPointProgress.setText("0/0");
                UniversalImageLoader.displayImage(mContext, ivPersonHead, R.mipmap.icon_mine_head);
                tvPersonName.setText("登录/注册");
                tvTotalSale.setText("0");
                tvTotalProfit.setText("0");
                performanceFirstConver(tvOngoingOrder, "0元 0单");
                tvCashed.setText("0");
                performanceFirstConver(tvFinishOrder, "0元 0单");
                tvPersonPhone.setText("");
                tvTodayOrder.setText("0");
                tvTodayProfit.setText("0");
                tvTodaySale.setText("0");
                tvTotalOrder.setText("0");
                tvValidCash.setText("0");
                tvValidCash1.setText("0");
                tvOngoingCash.setText("0");
            }
        }
    }


    //今日销售数据
    private void loadToday() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.ORDER_TODAY, partnerBean.getId()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderDayBean>() {
            @Override
            public void onResponse(OrderDayBean response) {
                if(progressBar==null){
                    return;
                }
                if (response != null && response.getList() != null && response.getList().size() > 0) {
                    OrderDayBean.ListBean.SaleStatBean retail = response.getList().get(0).getSaleStat();
                    OrderDayBean.ListBean.SaleStatBean dm = response.getList().get(0).getSaleStatDM();
                    OrderDayBean.ListBean.SaleStatBean sDm = response.getList().get(0).getSaleStatSDM();
                    OrderDayBean.ListBean.SaleStatBean am = response.getList().get(0).getSaleStatAM();
                    todayProfit = 0.0;//今日收益
                    todaySaleCount = 0;//今日销售单数
                    todaySaleAmout = 0.0;//今日销售额

                    if (isAm || isDm) {
                        todaySaleCount = retail.getCount();
                        todaySaleAmout = retail.getPayAmount();
                        todayProfit = retail.getPartnerRebates();
                        if (isAm) {
                            todaySaleCount += dm.getCount() + sDm.getCount() + am.getCount();
                            todaySaleAmout += dm.getPayAmount() + sDm.getPayAmount() + am.getPayAmount();
                            todayProfit += dm.getParentRebates() + sDm.getSuperRebates() + am.getMasterRebates();
                        } else {
                            todaySaleCount += dm.getCount();
                            todaySaleAmout += dm.getPayAmount();
                            todayProfit += dm.getParentRebates();
                            todaySaleCount += sDm.getCount();
                            todaySaleAmout += sDm.getPayAmount();
                            todayProfit += sDm.getSuperRebates();
                        }
                    } else {
                        todaySaleCount = retail.getCount();
                        todaySaleAmout = retail.getPayAmount();
                        todayProfit = retail.getPartnerRebates();
                    }
                }
                //今日销售数据
                tvTodayProfit.setText(Util.getNumberFormat(todayProfit));
                tvTodaySale.setText(Util.getNumberFormat(todaySaleAmout));
                tvTodayOrder.setText(todaySaleCount + "");

            }
        });
    }

    //所有销售数据
    private void loadAllDay() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.ORDER_ALL, partnerBean.getId()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderDayBean>() {
            @Override
            public void onResponse(OrderDayBean response) {
                if(progressBar==null){
                    return;
                }
                if (response != null && response.getList() != null && response.getList().size() > 0) {
                    OrderDayBean.ListBean.SaleStatBean retail = response.getList().get(0).getSaleStat();
                    OrderDayBean.ListBean.SaleStatBean dm = response.getList().get(0).getSaleStatDM();
                    OrderDayBean.ListBean.SaleStatBean sDm = response.getList().get(0).getSaleStatSDM();
                    OrderDayBean.ListBean.SaleStatBean am = response.getList().get(0).getSaleStatAM();
                    int allOrderNum = 0;
                    double allOrderAmount = 0;
                    if (isAm || isDm) {
                        allOrderNum = retail.getCount();
                        allOrderAmount = retail.getPayAmount();
                        if (isAm) {
                            allOrderNum += dm.getCount() + sDm.getCount() + am.getCount();
                            allOrderAmount+=dm.getPayAmount() + sDm.getPayAmount() + am.getPayAmount();
                        } else {
                            allOrderNum += dm.getCount();
                            allOrderNum += sDm.getCount();
                            allOrderAmount+=dm.getPayAmount();
                            allOrderAmount+=sDm.getPayAmount();
                        }
                    } else {
                        allOrderNum += retail.getCount();
                        allOrderAmount+=retail.getPayAmount();
                    }
                    //总订单
                    tvTotalOrder.setText(allOrderNum + "");
                    //销售总额
                    tvTotalSale.setText(Util.getNumberFormat(allOrderAmount));
                }

            }
        });
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
                if(progressBar==null){
                    return;
                }
                if (response != null && response.getData() != null) {
                    billSummaryBean = response;
                    calculateStayData();
                }
                loadNotice();
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
        tvOngoingCash.setText(Util.getNumberFormat(retailRebate + teamRebate));
        StringBuilder builder ;
        //业绩汇总
        builder = new StringBuilder();
        builder.append(Util.getNumberFormat(stayRetailOrderAmount + stayTeamOrderAmount)).append("元  ").append(stayRetailOrderNum + stayTeamOrderNum).append("单");
        performanceFirstConver(tvOngoingOrder, builder.toString());

        builder = new StringBuilder();
        builder.append(Util.getNumberFormat(hasRetailOrderAmount + hasTeamOrderAmount)).append("元  ").append(hasRetailOrderNum + hasTeamOrderNum).append("单");
        performanceFirstConver(tvFinishOrder, builder.toString());
    }

    private void performanceFirstConver(TextView textView, String text) {
        StringBuilder builder = new StringBuilder(text);
        int yuan = builder.toString().indexOf("元");
        int dan = builder.toString().indexOf("单");
        if (yuan < 0)
            return;
        SpannableString spannableString = new SpannableString(builder.toString());
        AbsoluteSizeSpan sizeSpan1 = new AbsoluteSizeSpan(ScreenUtil.dip2px(18));
        spannableString.setSpan(sizeSpan1, 0, yuan, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        AbsoluteSizeSpan sizeSpan11 = new AbsoluteSizeSpan(ScreenUtil.dip2px(18));
        spannableString.setSpan(sizeSpan11, yuan + 1, dan, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        AbsoluteSizeSpan sizeSpan2 = new AbsoluteSizeSpan(ScreenUtil.dip2px(12));
        spannableString.setSpan(sizeSpan2, yuan, yuan + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        AbsoluteSizeSpan sizeSpan22 = new AbsoluteSizeSpan(ScreenUtil.dip2px(12));
        spannableString.setSpan(sizeSpan22, dan, dan + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);
    }


    //加载买手公告
    private void loadNotice() {
        SimpleApi noticeApi = new SimpleApi();
        noticeApi.setInterPath(String.format(Constants.GET_PARTNER_NOTICE_URL, 10));//买手中心公告的id是10
        D2CApplication.httpClient.loadingRequest(noticeApi, new BeanRequest.SuccessListener<PartnerNoticeBean>() {
            @Override
            public void onResponse(PartnerNoticeBean noticeBean) {
                if (progressBar == null) {
                    return;
                }
                if (noticeBean != null && noticeBean.getData().getArticleList().getList() != null && noticeBean.getData().getArticleList().getList().size() > 0) {
                    llPartnerNotice.setVisibility(View.VISIBLE);
                    if (notices == null) {
                        notices = new ArrayList<>();
                    } else {
                        notices.clear();
                    }
                    for (int i = 0; i < noticeBean.getData().getArticleList().getList().size(); i++) {
                        notices.add(getString(R.string.msg_partner_notice_prefix, noticeBean.getData().getArticleList().getList().get(i).getTitle()));
                    }
                    tvNotice.setResource(notices);
                    if (tvNotice != null) {
                        tvNotice.reset();
                        tvNotice.startRolling();
                    }
                }
                loadSaleSchool();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadSaleSchool();
                Util.showToast(getContext(), Util.checkErrorType(error));
            }
        });
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
                    tvCashed.setText(Util.getNumberFormat(response.getData().getPartner().getTotalAmount()));
                }
            }
        });
    }






    private void loadVisitorTextData() {
        BuyerVisitorApi api = new BuyerVisitorApi();
        api.event = "买手店-访问";
        api.fieldName = "targetId";
        api.method = BaseApi.Method.POST;
        user = Session.getInstance().getUserFromFile(mContext);
        api.fieldValue = user.getPartnerId();
        api.isJsonContentType = true;
        api.setInterPath(GET_PARTNER_VISITOR_YESTERDAY_TODAY_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PartnerDayVisitorBean>() {
            @Override
            public void onResponse(PartnerDayVisitorBean partnerDayVisitorBean) {
                if(progressBar==null){
                    return;
                }
                String today = String.format(getString(R.string.label_today_visitor), String.valueOf(partnerDayVisitorBean.getList().get(0).getTodayUv()));
                tvTodayVisitor.setText(today);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    private void loadSaleSchool() {//加载商学院信息
        SaleSchoolApi saleSchoolApi = new SaleSchoolApi();
        saleSchoolApi.setType("WECHAT");
        D2CApplication.httpClient.loadingRequest(saleSchoolApi, new BeanRequest.SuccessListener<SaleSchoolTagsBean>() {
            @Override
            public void onResponse(SaleSchoolTagsBean saleSchoolTagsBean) {
                if (progressBar == null) {
                    return;
                }
                if (themeTags.size() > 0) {
                    themeTags.clear();
                }
                themeTags.addAll(saleSchoolTagsBean.getData().getThemeTags());
                initSaleSchoolView(themeTags);
            }
        });
    }


    private void setDataToView(PartnerMemberBean.DataBean.PartnerBean partner) {
        if (partner == null) {
            return;
        }
        if (!Util.isEmpty(partner.getLoginCode())) {
            String phoneNum = Util.hidePhoneNum(partner.getLoginCode());
            tvPersonPhone.setText(phoneNum);
        }
        if (user!=null) {
            UniversalImageLoader.displayRoundImage(getContext(), user.getHead(), ivPersonHead, R.mipmap.ic_default_avatar);
            if(Util.isEmpty(user.getNickname())){
                tvPersonName.setText("匿名_" + user.getId());
            } else {
                tvPersonName.setText(user.getNickname());
            }
        }
        if (partner.getLevel() == 0) {//AM
            ivPersonType.setImageResource(R.mipmap.icon_am);
            pointProgress.setProgress(100*partner.getPointCount()/75000);
            tvPointProgress.setText(getString(R.string.label_partner_progress,partner.getPointCount(),75000));
            if(partner.getPointCount()<75000){
                tvPointDetial.setText(getString(R.string.label_current_point,partner.getPointCount()));
            }else{
                tvPointDetial.setText("保级中");
            }
        } else if (partner.getLevel() == 1) {//DM
            ivPersonType.setImageResource(R.mipmap.icon_dm_level);
            tvPointProgress.setText(getString(R.string.label_partner_progress,partner.getPointCount(),75000));
            pointProgress.setProgress(100*partner.getPointCount()/75000);
            if(partner.getPointCount()<75000){//DM
                tvPointDetial.setText(getString(R.string.label_partner_update,"AM",75000-partner.getPointCount()));
            }else{
                tvPointDetial.setText(getString(R.string.label_current_point,partner.getPointCount()));
            }

        } else if (partner.getLevel() == 2) {//买手
            ivPersonType.setImageResource(R.mipmap.icon_buyer_level);
            tvPointProgress.setText(getString(R.string.label_partner_progress,partner.getPointCount(),3000));
            pointProgress.setProgress(100*partner.getPointCount()/3000);
            if(partner.getPointCount()<3000){//买手
                tvPointDetial.setText(getString(R.string.label_partner_update,"DM",3000-partner.getPointCount()));
            }else{
                tvPointDetial.setText(getString(R.string.label_current_point,partner.getPointCount()));
            }
        }

        //总收益
        tvTotalProfit.setText(Util.getNumberFormat(partner.getTotalAmount()));
        //申请提现中
        tvOngoingCash.setText(Util.getNumberFormat(partner.getApplyAmount()));
        //已提现
        tvCashed.setText(Util.getNumberFormat(partner.getCashAmount()));
        //字体大小不一样
        String validCashNum = Util.getNumberFormat(partner.getTotalAmount() - partner.getCashAmount() - partner.getApplyAmount());//可提现
        //可提现
        tvValidCash1.setText(validCashNum);
        tvValidCash.setText(validCashNum);
    }




    @OnClick({R.id.iv_setting, R.id.text_adviser, R.id.iv_person_head, R.id.ll_today_profit, R.id.ll_today_order, R.id.ll_today_sale1, R.id.ll_partner_notice,
            R.id.ll_invite, R.id.ll_learn, R.id.ll_data, R.id.rl_sale_title, R.id.ll_totla_sale, R.id.ll_total_sale, R.id.ll_total_sale_num,
            R.id.ll_total_cash, R.id.ll_ongoing_cash, R.id.ll_cashed, R.id.rl_sale_detial, R.id.ll_ongoing_order, R.id.ll_finish_order, R.id.tv_cash_record,
            R.id.rl_open_order, R.id.rl_heigh_rebeat, R.id.ll_sale_school, R.id.ll_my_gift, R.id.ll_my_team, R.id.ll_invite_friend, R.id.ll_my_adviser, R.id.ll_problem, R.id.ll_certificate,
            R.id.title_setting_iv, R.id.text_title_adviser, R.id.tv_enter_mine, R.id.ll_go_cash, R.id.btn_reload,R.id.ll_today_visitor})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_setting:
                hasToOtherActivity = true;
                intent = new Intent(mContext, SettingActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.text_adviser:
                //运营顾问
                hasToOtherActivity = true;
                if (Util.loginChecked((Activity) mContext, 999)) {
                    mContext.startActivity(new Intent(mContext, AddCounselorActivity.class));
                }
                break;
            case R.id.iv_person_head:
                //个人信息
                hasToOtherActivity = true;
                if (Util.loginChecked((Activity) mContext, 999)) {
                    getActivity().startActivityForResult(new Intent(getActivity(), PartnerPersonInfoActivity.class), 456);
                }
                break;
            case R.id.ll_today_profit:
            case R.id.ll_today_order:
            case R.id.ll_today_sale1:
                //售货明细
                if (Util.loginChecked((Activity) mContext, 999)) {
                    intent = new Intent(mContext, PartnerCenterActivity1.class).putExtra("position", 2)
                            .putExtra("isTabGone", true);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.ll_partner_notice:
                //公告列表
                getActivity().startActivity(new Intent(getActivity(), PartnerNoticeActivity.class));
                break;
            case R.id.ll_invite:
                //邀请买手
                hasToOtherActivity = true;
                getInviteUrlToWeb();
                break;
            case R.id.ll_learn:
                //商学院
                getActivity().startActivity(new Intent(getActivity(), SaleSchoolActivity.class));
                break;
            case R.id.ll_data:
                //数据统计跳经营中心
                if (Util.loginChecked((Activity) mContext, 999)) {
                    intent = new Intent(mContext, PartnerCenterActivity1.class)
                            .putExtra("isTabGone", true);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.rl_sale_title:
                //销售概况
                intent = new Intent(mContext, PartnerCenterActivity1.class)
                        .putExtra("isTabGone", true);
                getActivity().startActivity(intent);
                break;
            case R.id.ll_today_visitor:
                //今日访客
                if (Util.loginChecked((Activity) mContext, 999)) {
                    intent = new Intent(mContext, VisitorListActivity.class);
                    intent.putExtra("type", 1);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.ll_totla_sale:
            case R.id.ll_total_sale:
            case R.id.ll_total_sale_num:
            case R.id.ll_total_cash:
            case R.id.ll_ongoing_cash:
            case R.id.ll_cashed:
                if (Util.loginChecked((Activity) mContext, 999)) {
                    intent = new Intent(mContext, PartnerCenterActivity1.class)
                            .putExtra("isTabGone", true);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.ll_ongoing_order:
            case R.id.ll_finish_order:
            case R.id.rl_sale_detial:
                //售货明细
                if (Util.loginChecked((Activity) mContext, 999)) {
                    intent = new Intent(mContext, PartnerCenterActivity1.class).putExtra("position", 2)
                            .putExtra("isTabGone", true);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.btn_reload:
                refresh();
                break;
            case R.id.ll_go_cash:
//                //去提现
                if (Util.loginChecked((Activity) mContext, 999)) {
                    if (partnerBean == null) {
                        Util.showToast(getActivity(), "获取用户信息失败,请稍后重试~");
                        return;
                    }
                    if (Util.isEmpty(partnerBean.getCounselorId()) || "0".equals(partnerBean.getCounselorId())) {//运营顾问
                        new AlertDialog.Builder(mContext)
                                .setMessage("为更好的服务您，需绑定运营顾问后才能提现")
                                .setPositiveButton("添加运营顾问", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mContext.startActivity(new Intent(mContext, AddCounselorActivity.class));
                                    }
                                })
                                .show();
                    } else if (partnerBean.getContract() != 1) {//电签
                        intent = new Intent(getActivity(), PartnerCashIdentificationActivity.class);
                        getActivity().startActivity(intent);
                    } else {
                        intent = new Intent(getActivity(), ApplyCashActivity.class).putExtra("cashAmount", partnerBean.getTotalAmount() - partnerBean.getCashAmount() - partnerBean.getApplyAmount());
                        getActivity().startActivity(intent);
                    }
                }
                break;
            case R.id.tv_cash_record:
                //提现记录
                if (Util.loginChecked((Activity) mContext, 999)) {
                    getActivity().startActivity(new Intent(getActivity(), PartnerCashActivity.class));
                }
                break;
            case R.id.rl_open_order:
                //开单利器
                getActivity().startActivity(new Intent(getActivity(), HighIncomeProductListActivity.class).putExtra("title", "开单利器"));
                break;
            case R.id.rl_heigh_rebeat:
                //高收益商品
                getActivity().startActivity(new Intent(getContext(), HighIncomeProductListActivity.class).putExtra("title", "高收益商品"));
                break;
            case R.id.ll_sale_school:
                //商学院
                getActivity().startActivity(new Intent(getActivity(), SaleSchoolActivity.class));
                break;
            case R.id.ll_my_gift:
                //我的礼包
                if (Util.loginChecked((Activity) mContext, 999)) {
                    intent=new Intent(mContext, PartnerGiftsActivity.class);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.ll_my_team:
                //我的团队
                if (Util.loginChecked((Activity) mContext, 999)) {
                    intent = new Intent(mContext, PartnerInviteRecordActivity.class);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.ll_invite_friend:
                //邀请新人
                hasToOtherActivity = true;
                if (Util.loginChecked((Activity) mContext, 999)) {
                    getActivity().startActivity(new Intent(getActivity(), InviteNewActivity.class));
                }
                break;
            case R.id.ll_my_adviser:
                //运营顾问
                hasToOtherActivity = true;
                if (Util.loginChecked((Activity) mContext, 999)) {
                    mContext.startActivity(new Intent(mContext, AddCounselorActivity.class));
                }
                break;
            case R.id.ll_problem:
                //问题反馈
                hasToOtherActivity = true;
                if (Util.loginChecked((Activity) mContext, 999)) {
                    getActivity().startActivity(new Intent(getActivity(), ComplaintActivity.class));
                }
                break;
            case R.id.ll_certificate:
                //我的证书
                if (Util.loginChecked((Activity) mContext, 999)) {
                    intent = new Intent(mContext, PartnerCertificateActivity.class);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.title_setting_iv:
                hasToOtherActivity = true;
                intent = new Intent(mContext, SettingActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.text_title_adviser:
                //运营顾问
                hasToOtherActivity = true;
                if (Util.loginChecked((Activity) mContext, 999)) {
                    mContext.startActivity(new Intent(mContext, AddCounselorActivity.class));
                }
                break;
            case R.id.tv_enter_mine:
                //切换我的个人中心
                if (Util.loginChecked((Activity) mContext, 999)) {
                    if (changeFragmentListener != null) {
                        hasToOtherActivity = true;
                        changeFragmentListener.chaneFragment(1);
                    }
                }
                break;

        }
    }

    private void initSaleSchoolView(List<SaleSchoolTagsBean.DataBean.ThemeTagsBean> saleSchoolTagsBean) {
        if (themeTags != null && themeTags.size() > 0) {
            llSaleSchool.setVisibility(View.VISIBLE);
            filterList(themeTags);
            SaleSchoolAdapter adapter = new SaleSchoolAdapter(getContext(), tempTags);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            partnerSchoolContainer.setLayoutManager(layoutManager);
            partnerSchoolContainer.setAdapter(adapter);
        } else {
            llSaleSchool.setVisibility(View.GONE);
        }

    }

    //根据身份过滤展示商学院标签
    private void filterList(List<SaleSchoolTagsBean.DataBean.ThemeTagsBean> themeTags) {    //筛选商学院标签等级,买手只能看到买手级别的
        tempTags = new ArrayList<>();
        if (partnerBean.getLevel() == 1 || partnerBean.getLevel() == 0) {//DM,AM都可见
            tempTags.addAll(themeTags);
        } else {
            for (int i = 0; i < themeTags.size(); i++) {
                if (themeTags.get(i).getFix() == 1) {   //fix标示标签的等级(fix==1是买手级别的)
                    tempTags.add(themeTags.get(i));
                }
            }
        }
        if (tempTags.size() == 0) {
            llSaleSchool.setVisibility(View.GONE);
        }
    }

    private void setEmptyView(int type) {
        scrollView.setVisibility(View.GONE);
        if (type == Constants.NO_DATA) {
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.icon_empty_default);
        } else {
            btnReload.setVisibility(View.VISIBLE);
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.ic_no_net);
        }
    }

    //设置用户头像小红点动画
    private void startDotAnimation(PartnerMemberBean partnerInfoBean) {
        if (Util.isEmpty(partnerInfoBean.getData().getPartner().getAddress()) || Util.isEmpty(partnerInfoBean.getData().getPartner().getOccupation())
                || Util.isEmpty(partnerInfoBean.getData().getPartner().getExperience()) || Util.isEmpty(partnerInfoBean.getData().getPartner().getWeiboCount())
                || Util.isEmpty(partnerInfoBean.getData().getPartner().getWeixinCount()) || Util.isEmpty(partnerInfoBean.getData().getPartner().getConsumption())) {
            tvRedPoint.setVisibility(View.VISIBLE);
            ObjectAnimator anim = ObjectAnimator.ofFloat(tvRedPoint, "alpha", 1f, 0.5f, 0f);
            anim.setRepeatCount(ObjectAnimator.INFINITE);
            anim.setRepeatMode(ObjectAnimator.REVERSE);
            anim.setDuration(2000);// 动画持续时间
            anim.start();
        }
    }

    private void getInviteUrlToWeb() {
        if (user == null) {
            user = Session.getInstance().getUserFromFile(mContext);
        }
        String avatar;
        String name = Util.toURLEncode(user.getNickname() == null ? "匿名_" + user.getMemberId() : user.getNickname());
        if (user != null && user.getHead() != null) {
            avatar = Util.toURLEncode(getD2cPicUrl(user.getHead()));
        } else {
            avatar = Util.toURLEncode("http://d2c-app.b0.upaiyun.com/img/logo/android_default_avatar.png");
        }
        toWebActivity("/partner/joinbuyer?parent_id=" + user.getPartnerId() + "&name=" + name + "&avatar=" + avatar, true, true);
    }
    //返回D2C图片路径
    public  String getD2cPicUrl(String url) {
        if (Util.isEmpty(url)) {
            return null;
        } else if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        }  else {
            return "https://img.d2c.cn" + url;
        }
    }

    private void toWebActivity(String weburl, boolean isInvoke, boolean isShareGone) {
        Intent intent = new Intent(getContext(), WebActivity.class);
        String url = null;
        if (isInvoke) {
            intent.putExtra("type", 0);
            url = weburl;
        } else {
            url = Constants.SHARE_URL + weburl;
            intent.putExtra("type", 1);
        }
        intent.putExtra("url", url);
        intent.putExtra("isShareGone", isShareGone);
        getActivity().startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        if (tvNotice != null && notices != null && notices.size() > 0) {
            tvNotice.destroySwitcher();
        }
        changeFragmentListener=null;
        super.onDestroyView();
    }
}
