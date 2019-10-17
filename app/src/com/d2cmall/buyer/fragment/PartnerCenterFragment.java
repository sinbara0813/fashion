package com.d2cmall.buyer.fragment;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.AddCounselorActivity;
import com.d2cmall.buyer.activity.AllBuyerSaleActivity;
import com.d2cmall.buyer.activity.ApplyCashActivity;
import com.d2cmall.buyer.activity.ComplaintActivity;
import com.d2cmall.buyer.activity.HighIncomeProductListActivity;
import com.d2cmall.buyer.activity.InviteNewActivity;
import com.d2cmall.buyer.activity.PartnerCashActivity;
import com.d2cmall.buyer.activity.PartnerCashIdentificationActivity;
import com.d2cmall.buyer.activity.PartnerInviteRecordActivity;
import com.d2cmall.buyer.activity.PartnerNoticeActivity;
import com.d2cmall.buyer.activity.PartnerPersonInfoActivity;
import com.d2cmall.buyer.activity.SaleSchoolActivity;
import com.d2cmall.buyer.activity.WebActivity;
import com.d2cmall.buyer.adapter.SaleSchoolAdapter;
import com.d2cmall.buyer.api.SaleSchoolApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseTabFragment;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.bean.PartnerNoticeBean;
import com.d2cmall.buyer.bean.SaleSchoolTagsBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PartnerInvitePop;
import com.d2cmall.buyer.widget.PartnerScrollView;
import com.d2cmall.buyer.widget.RoundedImageView;
import com.d2cmall.buyer.widget.SharePop;
import com.d2cmall.buyer.widget.SwitcherView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Dec: 买手-买手中心
 * Author: hrb
 * Date: 2018/4/13 13:45
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class PartnerCenterFragment extends BaseTabFragment {

    @Bind(R.id.iv_person_head)
    RoundedImageView ivPersonHead;
    @Bind(R.id.tv_red_point)
    TextView tvRedPoint;
    @Bind(R.id.tv_person_name)
    TextView tvPersonName;
    @Bind(R.id.tv_person_phone)
    TextView tvPersonPhone;
    @Bind(R.id.iv_person_type)
    ImageView ivPersonType;
    @Bind(R.id.tv_person_type)
    TextView tvPersonType;
    @Bind(R.id.rl_person_info)
    RelativeLayout rlPersonInfo;
    @Bind(R.id.tv_notice)
    SwitcherView tvNotice;
    @Bind(R.id.ll_partner_notice)
    LinearLayout llPartnerNotice;
    @Bind(R.id.tv_open_order)
    TextView tvOpenOrder;
    @Bind(R.id.tv_hige_back)
    TextView tvHigeBack;
    @Bind(R.id.tv_partner_cash_record)
    TextView tvPartnerCashRecord;
    @Bind(R.id.rl_desposit_info)
    RelativeLayout rlDespositInfo;
    @Bind(R.id.tv_cash_num)
    TextView tvCashNum;
    @Bind(R.id.tv_apply_cash)
    TextView tvApplyCash;
    @Bind(R.id.tv_income_detail)
    TextView tvIncomeDetail;
    @Bind(R.id.tv_to_school)
    TextView tvToSchool;
    @Bind(R.id.partner_school_container)
    RecyclerView partnerSchoolContainer;
    @Bind(R.id.ll_sale_school)
    LinearLayout llSaleSchool;
    @Bind(R.id.ll_invite_friend)
    LinearLayout llInviteFriend;
    @Bind(R.id.ll_invite_history)
    LinearLayout llInviteHistory;
    @Bind(R.id.ll_sale_detail)
    LinearLayout llSaleDetail;
    @Bind(R.id.ll_partner_help)
    LinearLayout llPartnerHelp;
    @Bind(R.id.ll_contact_us)
    LinearLayout llContactUs;
    @Bind(R.id.ll_partner)
    LinearLayout llPartner;
    @Bind(R.id.iv_buyer_guide)
    ImageView ivBuyerGuide;
    @Bind(R.id.scroll_view)
    PartnerScrollView scrollView;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.time_day)
    TextView timeDay;
    @Bind(R.id.time_hour)
    TextView timeHour;
    @Bind(R.id.time_minute)
    TextView timeMinute;
    @Bind(R.id.time_mouse)
    TextView timeMouse;
    @Bind(R.id.ll_buyer_down_time)
    LinearLayout llBuyerDownTime;
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.iv_shakedown)
    ImageView ivShakedown;
    @Bind(R.id.rl_adviser)
    RelativeLayout rlAdviser;
    @Bind(R.id.ll_add_adviser)
    LinearLayout llAddAdviser;
    @Bind(R.id.title_back_iv)
    ImageView titleBackIv;
    @Bind(R.id.text_title_name)
    TextView textTitleName;
    @Bind(R.id.title)
    RelativeLayout title;
    @Bind(R.id.ll_invite_new)
    LinearLayout llInviteNew;
    private List<SaleSchoolTagsBean.DataBean.ThemeTagsBean> themeTags;
    private List<SaleSchoolTagsBean.DataBean.ThemeTagsBean> tempTags;
    private ArrayList<String> notices;
    private PartnerMemberBean.DataBean.PartnerBean partnerBean;
    private Handler mHandler;
    private PartnerInvitePop partnerInvitePop;
    private boolean isOpendGongmao;//是否打开过工猫
    private Bitmap headBitmap;
    public static final int IMAGE_SIZE = 32768;
    private float scale = 0.8F;
    private UserBean.DataEntity.MemberEntity user;

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return inflater.inflate(R.layout.fragment_buyer_center, container, false);
    }

    @Override
    public void doBusiness() {
        themeTags = new ArrayList<>();      //商学院标签
        setListener();
        loadPartnerData(false);
    }

    private void setListener() {
        scrollView.setScrollViewListener(new PartnerScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(PartnerScrollView observableScrollView, int x, int y, int oldx, int oldy) {
                if (y == 0) {
                    title.setBackgroundColor(getResources().getColor(R.color.transparent));
                    title.setClickable(false);
                    titleBackIv.setVisibility(View.GONE);
                    textTitleName.setVisibility(View.GONE);
                } else {
                    title.setBackgroundColor(getResources().getColor(R.color.trans_70_color_black));
                    title.setClickable(true);
                    titleBackIv.setVisibility(View.VISIBLE);
                    textTitleName.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onResume() {
        if (isOpendGongmao) {//用户打开过工猫电签网页刷新下用户信息,防止点击提现跳错界面
            loadPartnerData(true);
        }
        super.onResume();
    }

    //加载买手信息,设置倒计时等
    private void loadPartnerData(final boolean isRefresh) {       //1.true网络异常重新加载数据 2.false去提现界面提现成功
        progressBar.setVisibility(View.VISIBLE);
        SimpleApi simpleApi = new SimpleApi();
        simpleApi.setInterPath(Constants.GET_PARTNER_CENTER_URL);
        D2CApplication.httpClient.loadingRequest(simpleApi, new BeanRequest.SuccessListener<PartnerMemberBean>() {
            @Override
            public void onResponse(PartnerMemberBean partnerInfoBean) {
                if (!isVisibleToUser || progressBar == null) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                if (partnerInfoBean == null) {
                    Util.showToast(mContext, "信息不存在请稍后重试~");
                    return;
                }
                partnerBean = partnerInfoBean.getData().getPartner();
                setDataToView(partnerInfoBean.getData().getPartner());
                if (isRefresh) {      //申请提现成功
                    Session.getInstance().savePartnerToFile(getContext(), partnerBean);      //提现成功更新本地Partner信息
                } else {
                    if (Util.isEmpty(partnerBean.getCounselorId()) || "0".equals(partnerBean.getCounselorId())) {//运营顾问
                        rlAdviser.setVisibility(View.VISIBLE);
                    }
                //        试用期倒计时
                    if (partnerBean.getStatusX() == 0 && !Util.isEmpty(partnerBean.getExpiredDate())) { //试用期
                        //试用期修改下头部的UI样式
                        ViewGroup.LayoutParams layoutParams = rlPersonInfo.getLayoutParams();
                        layoutParams.height = ScreenUtil.dip2px(200);
                        rlPersonInfo.setLayoutParams(layoutParams);
                        rlPersonInfo.setBackgroundResource(R.mipmap.bg_buyercenter_topbg_high);
                        llBuyerDownTime.setVisibility(View.VISIBLE);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(llBuyerDownTime.getLayoutParams());
                        lp.setMargins(0, ScreenUtil.dip2px(-50), 0, 0);
                        llBuyerDownTime.setLayoutParams(lp);
                        ivShakedown.setVisibility(View.VISIBLE);
                        if (mHandler != null) {
                            mHandler.removeCallbacksAndMessages(null);
                        }
                        setTime();
                        if (mHandler == null) {
                            mHandler = new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    super.handleMessage(msg);
                                    //更改时间
                                    setTime();
                                    mHandler.sendEmptyMessageDelayed(1, 1000);
                                }
                            };
                            mHandler.sendEmptyMessageDelayed(1, 1000);
                        }
                    }
                    startDotAnimation(partnerInfoBean);
                    loadNotice();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                setEmptyView(Constants.NET_DISCONNECT);
                Util.showToast(getContext(), Util.checkErrorType(error));
            }
        });
    }

    //加载买手公告
    private void loadNotice() {
        progressBar.setVisibility(View.VISIBLE);
        SimpleApi noticeApi = new SimpleApi();
        noticeApi.setInterPath(String.format(Constants.GET_PARTNER_NOTICE_URL, 10));//买手中心公告的id是10
        D2CApplication.httpClient.loadingRequest(noticeApi, new BeanRequest.SuccessListener<PartnerNoticeBean>() {

            @Override
            public void onResponse(PartnerNoticeBean noticeBean) {
                if (!isVisibleToUser || progressBar == null) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
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
                    tvNotice.startRolling();
                }
                loadSaleSchool();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                llPartnerNotice.setVisibility(View.GONE);
                loadSaleSchool();
                progressBar.setVisibility(View.GONE);
                Util.showToast(getContext(), Util.checkErrorType(error));
            }
        });
    }

    private void loadSaleSchool() {//加载商学院信息
        progressBar.setVisibility(View.VISIBLE);
        SaleSchoolApi saleSchoolApi = new SaleSchoolApi();
        saleSchoolApi.setType("WECHAT");
        D2CApplication.httpClient.loadingRequest(saleSchoolApi, new BeanRequest.SuccessListener<SaleSchoolTagsBean>() {
            @Override
            public void onResponse(SaleSchoolTagsBean saleSchoolTagsBean) {
                if (!isVisibleToUser || progressBar == null) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                if (themeTags.size() > 0) {
                    themeTags.clear();
                }
                themeTags.addAll(saleSchoolTagsBean.getData().getThemeTags());
                initSaleSchoolView(themeTags);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        });
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

    private void setDataToView(PartnerMemberBean.DataBean.PartnerBean partnerInfoBean) {
        UniversalImageLoader.displayRoundImage(getContext(), partnerInfoBean.getHeadPic(), ivPersonHead, R.mipmap.ic_default_avatar);
        if (!Util.isEmpty(partnerInfoBean.getLoginCode())) {
            String phoneNum = Util.hidePhoneNum(partnerInfoBean.getLoginCode());
            tvPersonPhone.setText(phoneNum);
        }
        if (!Util.isEmpty(partnerInfoBean.getName())) {
            tvPersonName.setText(partnerInfoBean.getName());
        } else {
            tvPersonName.setText("匿名_" + partnerInfoBean.getId());
        }

        if (partnerInfoBean.getLevel() == 0) {//AM
            ivPersonType.setImageResource(R.mipmap.icon_am);
        } else if (partnerInfoBean.getLevel() == 1) {//DM
            ivPersonType.setImageResource(R.mipmap.icon_dm_level);
        } else if (partnerInfoBean.getLevel() == 2) {//买手
            ivPersonType.setImageResource(R.mipmap.icon_buyer_level);
        }
        tvIncomeDetail.setText(getString(R.string.msg_partner_reward_detail, Util.getNumberFormat(partnerInfoBean.getCashAmount()), Util.getNumberFormat(partnerInfoBean.getTotalAmount())));//已提现,总金额
        //字体大小不一样
        String str = "¥" + Util.getNumberFormat(partnerInfoBean.getTotalAmount() - partnerInfoBean.getCashAmount() - partnerInfoBean.getApplyAmount());//可提现
        int length = str.length();
        SpannableString textSpan = new SpannableString(str);
        textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(28)), 1, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvCashNum.setText(textSpan);
    }

    @OnClick({R.id.iv_person_head, R.id.tv_notice, R.id.tv_open_order, R.id.tv_hige_back, R.id.tv_partner_cash_record,
            R.id.tv_apply_cash, R.id.tv_income_detail, R.id.tv_to_school, R.id.ll_invite_friend, R.id.ll_invite_history,
            R.id.ll_sale_detail, R.id.ll_partner_help, R.id.ll_contact_us, R.id.iv_buyer_guide, R.id.ll_add_adviser,
            R.id.btn_reload, R.id.back_iv, R.id.rl_adviser, R.id.title_back_iv,R.id.ll_invite_new})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv://
            case R.id.title_back_iv:
                getActivity().finish();
                break;
            case R.id.tv_notice://公告列表
                getActivity().startActivity(new Intent(getActivity(), PartnerNoticeActivity.class));
                break;
            case R.id.tv_income_detail:
                break;
            case R.id.tv_to_school://商学院
                getActivity().startActivity(new Intent(getActivity(), SaleSchoolActivity.class));
                break;
            case R.id.ll_contact_us://反馈投诉
                if(Util.loginChecked((Activity) mContext,999)) {
                    getActivity().startActivity(new Intent(getActivity(), ComplaintActivity.class));
                }
                break;
            case R.id.ll_invite_new://邀请新人
                if(Util.loginChecked((Activity) mContext,999)) {
                    getActivity().startActivity(new Intent(getActivity(), InviteNewActivity.class));
                }
                break;
            case R.id.ll_invite_friend://邀请朋友
                if (Util.loginChecked((Activity) mContext, 999)) {
                    getInviteUrlToWeb();
//                    inviteBuyer();
                }
//                if (partnerBean.getLevel() == 0) {
//                    showInviteBuyerPop();
//                } else {
//
//                }
//                getActivity().startActivity(new Intent(getActivity(), InvitePartnerActivity.class));
                break;
            case R.id.ll_invite_history://邀请记录
                if(Util.loginChecked((Activity) mContext,999)) {
                    getActivity().startActivity(new Intent(getActivity(), PartnerInviteRecordActivity.class));
                }
                break;
            case R.id.iv_person_head://合伙人个人信息
                if(Util.loginChecked((Activity) mContext,999)) {
                    getActivity().startActivityForResult(new Intent(getActivity(), PartnerPersonInfoActivity.class), 456);
                }
                break;
            case R.id.ll_sale_detail://售货中心
                if(Util.loginChecked((Activity) mContext,999)) {
                    getActivity().startActivity(new Intent(getActivity(), AllBuyerSaleActivity.class));
                }
                break;
            case R.id.ll_partner_help://问题帮助
                if (partnerBean == null) {
                    return;
                }
                if (partnerBean.getLevel() == 0 || partnerBean.getLevel() == 1) {
                    toWebActivity("/page/partnerhelp", true);
                } else {
                    toWebActivity("/page/buyerhelp", true);
                }
                break;
            case R.id.iv_buyer_guide:
                //新手指引
                toWebActivity("/page/maishouzhinan", true);
                break;
            case R.id.tv_hige_back:
                //高收益商品
                getActivity().startActivity(new Intent(getContext(), HighIncomeProductListActivity.class).putExtra("title", "高收益商品"));
                break;
            case R.id.tv_open_order:
                //开单利器
                getActivity().startActivity(new Intent(getActivity(), HighIncomeProductListActivity.class).putExtra("title", "开单利器"));
                break;
            case R.id.tv_partner_cash_record:
                //提现记录
                if(Util.loginChecked((Activity) mContext,999)) {
                    getActivity().startActivity(new Intent(getActivity(), PartnerCashActivity.class));
                }
                break;
            case R.id.tv_apply_cash:
                //去提现

                if(Util.loginChecked((Activity) mContext,999)) {
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
                        Intent intent = new Intent(getActivity(), PartnerCashIdentificationActivity.class);
                        getActivity().startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), ApplyCashActivity.class).putExtra("cashAmount", partnerBean.getTotalAmount() - partnerBean.getCashAmount() - partnerBean.getApplyAmount());
                        getActivity().startActivity(intent);
                    }
                }
                break;
            case R.id.btn_reload:
                imgHint.setVisibility(View.GONE);
                btnReload.setVisibility(View.GONE);
                loadPartnerData(false);
                break;
            case R.id.rl_adviser://运营顾问
            case R.id.ll_add_adviser:
                if(Util.loginChecked((Activity) mContext,999)) {
                    mContext.startActivity(new Intent(mContext, AddCounselorActivity.class));
                }
                break;

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
        toWebActivity("/partner/joinbuyer?parent_id=" + user.getPartnerId() + "&name=" + name + "&avatar=" + avatar,true);
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

    private void showInviteBuyerPop() { //邀请买手
        if (partnerInvitePop == null) {
            partnerInvitePop = new PartnerInvitePop(getContext());
        }
        partnerInvitePop.show(getActivity().getWindow().getDecorView());
    }


    private void toWebActivity(String weburl, boolean isInvoke) {
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
        intent.putExtra("isShareGone", false);
        getActivity().startActivity(intent);
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

    //设置试用期倒计时
    private void setTime() {
        long offer = DateUtil.strToDateLong(partnerBean.getExpiredDate()).getTime() - System.currentTimeMillis();
        long day = offer / (24 * 60 * 60 * 1000);
        long hour = offer / (60 * 60 * 1000) % 24;
        long minute = (offer / (60 * 1000)) % 60;
        long mouse = (offer / 1000) % 60;
        timeDay.setText(addZero((int) day, false));
        timeHour.setText(addZero((int) hour, true));
        timeMinute.setText(addZero((int) minute, true));
        timeMouse.setText(addZero((int) mouse, true));
    }

    private String addZero(int num, boolean addSign) {//天数不加冒号
        if (num < 0) {
            return "0";
        }
        if (num < 10) {
            if (addSign) {
                return ": 0" + num;
            } else {
                return "0" + num;
            }
        } else {
            if (addSign) {
                return ": " + String.valueOf(num);
            } else {
                return String.valueOf(num);
            }
        }
    }

    @Subscribe
    public void onEvent(GlobalTypeBean bean) {
        if (bean.getType() == Constants.GlobalType.PARTNER_APPLY_CASH) {//提交申请提现刷新用户账户信息
            loadPartnerData(true);
        }
        if (bean.getType() == Constants.GlobalType.PARTNER_OPEN_GONGMAO) {//用户打开了电签界面
            isOpendGongmao = true;
        }
    }

    @Override
    public void onDestroyView() {
        if (tvNotice != null && notices != null && notices.size() > 0) {
            tvNotice.destroySwitcher();
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void inviteBuyer() {

            SharePop sharePop1 = new SharePop(mContext);
            sharePop1.show(((Activity) mContext).getWindow().getDecorView());
            sharePop1.setTitle("邀你成为D2C时尚买手，分享好物，轻松赚钱");
            sharePop1.setShareMini(true);
            sharePop1.setDescription("邀你成为D2C时尚买手，分享好物，轻松赚钱");
            sharePop1.setImage("http://img.d2c.cn/app/a/18/05/08/mini.png", true);
            if (user == null) {
                user = Session.getInstance().getUserFromFile(mContext);
            }
            String avatar;
            String name = Util.toURLEncode(user.getNickname() == null ? "匿名_" + user.getMemberId() : user.getNickname());
            if (user != null && user.getHead() != null) {
                avatar = Util.toURLEncode(Constants.IMG_HOST + user.getHead());
            } else {
                avatar = Util.toURLEncode("http://d2c-app.b0.upaiyun.com/img/logo/android_default_avatar.png");
            }
            //设置小程序页面
            sharePop1.setMiniProjectPath("/pages/intro/joinRecommender?parent_id=" + user.getPartnerId() + "&name=" + name + "&avatar=" + avatar);
            //设置小程序低版本兼容网页
            sharePop1.setMiniWebUrl("/partner/joinbuyer?parent_id=" + user.getPartnerId() + "&name=" + name + "&avatar=" + avatar);
            //设置分享链接(短链)
            sharePop1.setWebUrl("/partner/joinbuyer?name=" + name + "&avatar=" + avatar);
            //加载小程序分享的图片
            Glide.with(this).load(Util.getD2cPicUrl("http://img.d2c.cn/app/a/18/05/08/mini.png", ScreenUtil.getDisplayWidth(), ScreenUtil.getDisplayHeight())).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    byte[] bitmapData = BitmapUtils.getBitmapData(BitmapUtils.getScaleBitmap(resource, scale, scale));
                    while (bitmapData.length > IMAGE_SIZE) {
                        scale -= 0.1;
                        Bitmap scaleBitmap = BitmapUtils.getScaleBitmap(resource, scale, scale);
                        bitmapData = BitmapUtils.getBitmapData(scaleBitmap);
                        scaleBitmap.recycle();
                    }
                    sharePop1.setMiniPicData(bitmapData);
                    sharePop1.show(((Activity) mContext).getWindow().getDecorView());
                }
            });
    }

}
