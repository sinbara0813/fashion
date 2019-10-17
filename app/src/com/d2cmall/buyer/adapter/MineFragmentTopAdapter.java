package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.AboutUsActivity;
import com.d2cmall.buyer.activity.AccountInfoActivity;
import com.d2cmall.buyer.activity.AddCounselorActivity;
import com.d2cmall.buyer.activity.AllAfterSaleActivity;
import com.d2cmall.buyer.activity.ApplyCashActivity;
import com.d2cmall.buyer.activity.CollectActivity;
import com.d2cmall.buyer.activity.ComplaintActivity;
import com.d2cmall.buyer.activity.FocusBrandActivity;
import com.d2cmall.buyer.activity.FootMarkActivity;
import com.d2cmall.buyer.activity.GroupBuyActivity;
import com.d2cmall.buyer.activity.HelpActivity;
import com.d2cmall.buyer.activity.HomeActivity;
import com.d2cmall.buyer.activity.LoginActivity;
import com.d2cmall.buyer.activity.MoreToolsActivity;
import com.d2cmall.buyer.activity.MyCouponsActivity;
import com.d2cmall.buyer.activity.MyOrderActivity;
import com.d2cmall.buyer.activity.PartnerCashIdentificationActivity;
import com.d2cmall.buyer.activity.PartnerCenterActivity1;
import com.d2cmall.buyer.activity.PersonInfoActivity;
import com.d2cmall.buyer.activity.RedPacketActivity;
import com.d2cmall.buyer.activity.WalletActivity;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.bean.AfterSaleCountBean;
import com.d2cmall.buyer.bean.CouponsBean;
import com.d2cmall.buyer.bean.FootMarkCountBean;
import com.d2cmall.buyer.bean.ModuleBean;
import com.d2cmall.buyer.bean.MyAdBean;
import com.d2cmall.buyer.bean.OrderCountBean;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.MineFragmentTopHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.AccountSafePop;
import com.d2cmall.buyer.widget.SimpleDialog;
import com.tendcloud.tenddata.TCAgent;

import q.rorbin.badgeview.QBadgeView;

/**
 * Created by Administrator on 2018/9/7.
 * Description : MineFragmentTopAdapter
 */

public class MineFragmentTopAdapter extends DelegateAdapter.Adapter implements ModuleGridAdapter.OnModuleItemClickListener {
    private Context mContext;
    private ModuleGridAdapter moduleGridAdapter;
    private UserBean.DataEntity.MemberEntity user;
    private QBadgeView unPay, unPost, unReceive, unReview, unAfterBuy, conpouNum, cartNum;
    private Handler mHandler;
    private PartnerMemberBean.DataBean.PartnerBean partnerBean;
    private MineFragmentTopHolder mineFragmentTopHolder;
    private SimpleDialog simpleDialog;
    public boolean isLogin = false;

    private ClickToPartnerListener clickToPartnerListener;
    public MineFragmentTopAdapter(Context context) {
        this.mContext=context;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_mine_top, parent, false);
        return new MineFragmentTopHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,  int position) {
        if(mineFragmentTopHolder==null){
            mineFragmentTopHolder = (MineFragmentTopHolder) holder;
            moduleGridAdapter = new ModuleGridAdapter(mContext, false);
            mineFragmentTopHolder.gridLayout.setAdapter(moduleGridAdapter);
            moduleGridAdapter.setOnModuleItemClickListener(this);
            //防止gridLayout抢焦点导致RecyclerView自动滚动
            mineFragmentTopHolder.gridLayout.setFocusable(false);
            setCount();
            //添加点击事件
            addClickEvent(mineFragmentTopHolder);
            //加载广告
            getAdBean();
            //填充数据
            setUserInfo();
        }
    }
    private void setCount() {
        unPay = (QBadgeView) new QBadgeView(mContext).bindTarget(mineFragmentTopHolder.ivUnPay).setBadgeTextSize(9, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(2, 2, true);
        unPost = (QBadgeView) new QBadgeView(mContext).bindTarget(mineFragmentTopHolder.ivUnDeliver).setBadgeTextSize(9, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(2, 2, true);
        unReceive = (QBadgeView) new QBadgeView(mContext).bindTarget(mineFragmentTopHolder.ivWaitDeliver).setBadgeTextSize(9, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(2, 2, true);
        unReview = (QBadgeView) new QBadgeView(mContext).bindTarget(mineFragmentTopHolder.ivUnReview).setBadgeTextSize(9, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(2, 2, true);
        unAfterBuy = (QBadgeView) new QBadgeView(mContext).bindTarget(mineFragmentTopHolder.ivAfterBuy).setBadgeTextSize(9, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(2, 2, true);
        conpouNum = (QBadgeView) new QBadgeView(mContext).bindTarget(mineFragmentTopHolder.ivCoupon).setBadgeTextSize(9, true).setBadgeGravity(Gravity.END | Gravity.TOP).stroke(Color.parseColor("#fff23365"), 1, true).setBadgeBackgroundColor(Color.parseColor("#ffffffff")).setBadgeTextColor(Color.parseColor("#fff23365")).setGravityOffset(28, -1.5f, true);
        cartNum = (QBadgeView) new QBadgeView(mContext).bindTarget(mineFragmentTopHolder.ivCart).setBadgeTextSize(9, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(2, 2, true);
    }

    private void addClickEvent(MineFragmentTopHolder mineFragmentTopHolder) {
        mineFragmentTopHolder.llDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    if (partnerBean == null) {
                        Util.showToast((Activity) mContext, "获取用户信息失败,请稍后重试~");
                        return;
                    }
                    if ((Util.isEmpty(partnerBean.getCounselorId()) || "0".equals(partnerBean.getCounselorId()))) {//运营顾问
                        new AlertDialog.Builder(mContext)
                                .setMessage("为更好的服务您，需绑定运营顾问后才能提现")
                                .setPositiveButton("添加运营顾问", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mContext.startActivity(new Intent(mContext, AddCounselorActivity.class));
                                    }
                                })
                                .show();
                        return;
                    }
                    if (partnerBean.getContract() != 1) {//电签
                        Intent intent = new Intent(mContext, PartnerCashIdentificationActivity.class);
                        mContext.startActivity(intent);
                        return;
                    } else {
                        Intent intent = new Intent(mContext, ApplyCashActivity.class);
                        mContext.startActivity(intent);
                    }
                }
            }
        });
        mineFragmentTopHolder.llSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    Intent intent = new Intent(mContext, PartnerCenterActivity1.class).putExtra("position", 2)
                            .putExtra("isTabGone", true);
                    mContext.startActivity(intent);
                }
            }
        });
        mineFragmentTopHolder.llBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    Intent intent = new Intent(mContext, PartnerCenterActivity1.class).putExtra("subPosition", 1)
                            .putExtra("isTabGone", true);
                    mContext.startActivity(intent);
                }
            }
        });
        mineFragmentTopHolder.llSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    Intent intent = new Intent(mContext, PartnerCenterActivity1.class)
                            .putExtra("isTabGone", true);;
                    mContext.startActivity(intent);
                }
            }
        });
        mineFragmentTopHolder.rlBuyerTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    Intent intent =new Intent(mContext, PartnerCenterActivity1.class)
                            .putExtra("isTabGone", true);
                    mContext.startActivity(intent);
                }
            }
        });
        mineFragmentTopHolder.llMyFootmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    Intent intent =new Intent(mContext, FootMarkActivity.class);
                    mContext.startActivity(intent);
                    stat("V3我的", "浏览足迹");
                }
            }
        });
        mineFragmentTopHolder.ivLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    Util.urlAction(mContext, "/member/level", true);
                }
            }
        });

        mineFragmentTopHolder.tvUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    Intent intent;
                    if (!isLogin) {
                        intent = new Intent(mContext, LoginActivity.class);
                    } else {
                        intent = new Intent(mContext, AccountInfoActivity.class);
                    }
                    mContext.startActivity(intent);
                }
            }
        });

        mineFragmentTopHolder.tvAllOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    Intent intent = new Intent(mContext, MyOrderActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });
        mineFragmentTopHolder.ivUnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    Intent  intent = new Intent(mContext, MyOrderActivity.class);
                    intent.putExtra("position", 1);
                    mContext.startActivity(intent);
                }
            }
        });
        mineFragmentTopHolder.ivUnDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    Intent  intent = new Intent(mContext, MyOrderActivity.class);
                    intent.putExtra("position", 2);
                    mContext.startActivity(intent);
                }
            }
        });
        mineFragmentTopHolder.ivWaitDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    Intent  intent = new Intent(mContext, MyOrderActivity.class);
                    intent.putExtra("position", 3);
                    mContext.startActivity(intent);
                }
            }
        });
        mineFragmentTopHolder.ivUnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    Intent  intent = new Intent(mContext, MyOrderActivity.class);
                    intent.putExtra("position", 4);
                    mContext.startActivity(intent);
                }
            }
        });
        mineFragmentTopHolder.llEnterPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //切换到买手中心
                if(Util.loginChecked((Activity) mContext,Constants.Login.WEB_LOGIN)){
                    if(clickToPartnerListener!=null){
                        clickToPartnerListener.clickToPartner();
                    }
                }
            }
        });
        mineFragmentTopHolder.ivAfterBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    Intent  intent = new Intent(mContext, AllAfterSaleActivity.class);
                    intent.putExtra("position", 0);
                    mContext.startActivity(intent);
                }
            }
        });
        mineFragmentTopHolder.ivCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    Intent  intent =new Intent(mContext, MyCouponsActivity.class);
                    mContext.startActivity(intent);
                    stat("V3我的", "优惠券");
                }
            }
        });
        mineFragmentTopHolder.ivWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    Intent  intent =new Intent(mContext, WalletActivity.class);
                    mContext.startActivity(intent);
                    stat("V3我的", "钱包");
                }
            }
        });
        mineFragmentTopHolder.ivIntegral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    Util.urlAction(mContext, "/member/sign/records?pageSize=7", true);
                    stat("V3我的", "D币");
                }
            }
        });
        mineFragmentTopHolder.ivRebate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    Intent intent = new Intent(mContext, RedPacketActivity.class);
                    mContext.startActivity(intent);
                    stat("V3我的", "红包");
                }
            }
        });
        mineFragmentTopHolder.llMyCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    mContext.startActivity(new Intent(mContext, CollectActivity.class));
                    stat("V3我的", "我的收藏");
                }
            }
        });
        mineFragmentTopHolder.llMyFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    mContext.startActivity(new Intent(mContext, FocusBrandActivity.class));
                    stat("V3我的", "关注品牌");
                }
            }
        });
        mineFragmentTopHolder.ivUserHeadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                    Intent intent;
                    if (!isLogin) {
                        intent = new Intent(mContext, LoginActivity.class);
                    } else {
                        intent = new Intent(mContext, PersonInfoActivity.class);
                        intent.putExtra("memberId", user.getId());
                    }
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    //工具菜单点击事件
    @Override
    public void onModuleItemClick(ModuleBean moduleBean) {
        Intent intent = null;
        int tagId = moduleBean.getTagId();

        if (tagId > 7) {
            Util.urlAction(mContext, moduleBean.getUrl());
        } else {
            switch (tagId) {
                case 0://会员中心
                    if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                        Util.urlAction(mContext, "/member/level", true);
                        stat("V3我的", "会员中心");
                    }
                    break;
                case 1://预约试衣
                    if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                        Util.urlAction(mContext, "o2oSubscribe/my/list");
                        stat("V3我的", "预约试衣");
                    }
                    break;
                case 2://客服售后
                    intent = new Intent(mContext, HelpActivity.class);
                    mContext.startActivity(intent);
                    stat("V3我的", "售后服务");
                    break;
                case 3://商品报告
                    if (Util.loginChecked((Activity) mContext, Constants.Login.WEB_LOGIN)) {
                        intent = new Intent(mContext, GroupBuyActivity.class).putExtra("position", 1);
                        mContext.startActivity(intent);
                        stat("V3我的", "拼团");
                    }
                    break;
                case 4://入驻合作
                    Util.urlAction(mContext, "/brandapply?isLogin=1");
                    stat("V3我的", "入驻合作");
                    break;
                case 5://意见反馈
                    intent = new Intent(mContext, ComplaintActivity.class);
                    mContext.startActivity(intent);
                    stat("V3我的", "反馈投诉");
                    break;
                case 6://T设置
                    intent = new Intent(mContext, AboutUsActivity.class);
                    mContext.startActivity(intent);
                    stat("V3我的", "关于我们");
                    break;
                case 7://关于我们
                    intent = new Intent(mContext, MoreToolsActivity.class);
                    mContext.startActivity(intent);
                    stat("V3我的", "更多");
                    break;
            }
        }
    }

    public void setUserInfo() {
        if(mineFragmentTopHolder==null){
            return;
        }
        user = Session.getInstance().getUserFromFile(mContext);
        if (user != null) {
            isLogin = true;
            checkLoginState(isLogin);
            if (user.getHasNickName() && !Util.isEmpty(user.getRealHead()) && user.isDanger() == 0 && user.isPayDanger() == 0) {
                mineFragmentTopHolder.tvRedPoint.setVisibility(View.GONE);
            } else {
                mineFragmentTopHolder.tvRedPoint.setVisibility(View.VISIBLE);
            }
            setCartNum();
            UniversalImageLoader.displayRoundImage(mContext, user.getHead(), mineFragmentTopHolder.ivUserHeadPic, R.mipmap.ic_default_avatar);
            mineFragmentTopHolder.tvUserName.setText(user.getNickname());
            switch (user.getType()) {
                case 1://普通会员
                    mineFragmentTopHolder.ivIdentify.setVisibility(View.GONE);
                    break;
                case 2://设计师
                    mineFragmentTopHolder.ivIdentify.setVisibility(View.VISIBLE);
                    mineFragmentTopHolder.ivIdentify.setImageResource(R.mipmap.icon_all_designer);
                    break;
                case 3://D2C官方
                    mineFragmentTopHolder.ivIdentify.setVisibility(View.VISIBLE);
                    mineFragmentTopHolder.ivIdentify.setImageResource(R.mipmap.icon_all_d2c);
                    break;
                case 5://达人标识
                    mineFragmentTopHolder.ivIdentify.setVisibility(View.VISIBLE);
                    mineFragmentTopHolder.ivIdentify.setImageResource(R.mipmap.icon_all_fashion);
                    break;
                default:
                    mineFragmentTopHolder.ivIdentify.setVisibility(View.GONE);
                    break;
            }
            mineFragmentTopHolder.llLevel.setVisibility(View.VISIBLE);
            mineFragmentTopHolder.ivLevel.setImageResource(getLevelImage(user.getLevel()));
            mineFragmentTopHolder.tvLevel.setText(getLevelText(user.getLevel()));
            //我的界面买手中心的入口去掉,先注释掉部分布局,保留点击切换买手Fragment和买手等级,倒计时先去掉
//            showDoorBuyer();
            requestPartnerInfo();
            if (user.isDanger() == 1) {
                showAccountTip();
            }
        } else {
            checkLoginState(false);
        }
    }


    //有无倒计时改变buyerView的高度
    private void showDoorBuyer() {
        //partnerId>0且试用期倒计时没过期
        if (user.getPartnerId() > 0) {
            mineFragmentTopHolder. rlBuyer.setVisibility(View.VISIBLE);
            mineFragmentTopHolder.ivBuyerLevel.setVisibility(View.VISIBLE);
            setRlUserHeight(175);
        } else {
            mineFragmentTopHolder.rlBuyer.setVisibility(View.GONE);
            mineFragmentTopHolder.ivBuyerLevel.setVisibility(View.GONE);
            setRlUserHeight(160);
        }
    }

    private void setRlUserHeight(int height) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mineFragmentTopHolder.rlUserAll.getLayoutParams();
        layoutParams.height = ScreenUtil.dip2px(height);
        mineFragmentTopHolder.rlUserAll.setLayoutParams(layoutParams);
    }

    private void checkLoginState(boolean isLogin) {

        if (isLogin) {
//            imgLoginRegister.setVisibility(View.GONE);
//            imgAvatar.setVisibility(View.VISIBLE);
//            tvName.setVisibility(View.VISIBLE);
//            tvHint.setVisibility(View.VISIBLE);
            if(user!=null && user.getPartnerId()>0){
                mineFragmentTopHolder.llEnterPartner.setVisibility(View.VISIBLE);
            }else{
                mineFragmentTopHolder.llEnterPartner.setVisibility(View.GONE);
            }

        } else {
            this.isLogin = false;
            try {
                unPay.setBadgeNumber(0);
                unAfterBuy.setBadgeNumber(0);
                unPost.setBadgeNumber(0);
                unReceive.setBadgeNumber(0);
                unReview.setBadgeNumber(0);
                conpouNum.setBadgeNumber(0);
                cartNum.setBadgeNumber(0);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            mineFragmentTopHolder.rlBuyer.setVisibility(View.GONE);
            setRlUserHeight(160);
             mineFragmentTopHolder.ivBuyerLevel.setVisibility(View.GONE);
             mineFragmentTopHolder.llEnterPartner.setVisibility(View.GONE);
             mineFragmentTopHolder.tvCollectNum.setText(String.valueOf(0));
             mineFragmentTopHolder.tvFollowNum.setText(String.valueOf(0));
             mineFragmentTopHolder.tvFootmarkNum.setText(String.valueOf(0));
             mineFragmentTopHolder.llLevel.setVisibility(View.GONE);
             mineFragmentTopHolder.ivIdentify.setVisibility(View.GONE);
             mineFragmentTopHolder.tvUserName.setText("登录/注册");
            if (mHandler != null) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler = null;
            }
            //rlTitleHeadPic.setVisibility(View.GONE);
            //textTitleName.setVisibility(View.GONE);
            //ivHeadTitle.setVisibility(View.GONE);
            //ivTitle.setVisibility(View.GONE);
            mineFragmentTopHolder.ivUserHeadPic.setImageResource(R.mipmap.icon_mine_head);
            if (moduleGridAdapter != null) {
                moduleGridAdapter.addItemByTagId(0);
                moduleGridAdapter.setItemCountByTagId(1, "");
                moduleGridAdapter.addItemByTagId(5);
            }
        }
    }


    //不同等级的logo
    private int getLevelImage(int level) {
        switch (level) {
            case 0:
                return R.mipmap.icon_mine_v0;
            case 1:
                return R.mipmap.icon_mine_v1;
            case 2:
                return R.mipmap.icon_mine_v2;
            case 3:
                return R.mipmap.icon_mine_v3;
            case 4:
                return R.mipmap.icon_mine_v4;
            case 5:
                return R.mipmap.icon_mine_v5;
            default:
                return R.mipmap.icon_mine_v0;
        }
    }
    //不同等级的名称
    private String getLevelText(int level) {
        String text;
        switch (level) {
            case 0:
                text = "大众会员";
                break;
            case 1:
                text = "普通会员";
                break;
            case 2:
                text = "银卡会员";
                break;
            case 3:
                text = "黄金会员";
                break;
            case 4:
                text = "铂金会员";
                break;
            case 5:
                text = "钻石会员";
                break;
            default:
                text = "大众会员";
        }
        return text;

    }


    private void showAccountTip() {
        boolean is = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.IS_SHOW_LOGIN_PW_TIP, false);
        if (!is) {
            D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.IS_SHOW_LOGIN_PW_TIP, true);
            AccountSafePop safePop = new AccountSafePop(mContext);
            safePop.setContent(mContext.getResources().getString(R.string.label_change_login_pw), 1);
            safePop.show(mineFragmentTopHolder.ivUserHeadPic);
        }
    }

    public void requestPartnerInfo() {
        if (user.getPartnerId() <= 0 || Session.getInstance().getPartnerBean() != null)
            return;
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.MEMBER_MINE);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PartnerMemberBean>() {
            @Override
            public void onResponse(PartnerMemberBean response) {
                if (response != null && response.getData().getPartner() != null) {
                    Session.getInstance().savePartnerToFile(mContext, response.getData().getPartner());
                    partnerBean = response.getData().getPartner();
                    mineFragmentTopHolder.ivBuyerLevel.setVisibility(View.VISIBLE);
                    mineFragmentTopHolder.ivBuyerLevel.setImageResource(getBuyerLevel(response.getData().getPartner().getLevel()));
                    mineFragmentTopHolder.llEnterPartner.setVisibility(View.VISIBLE);
                    //我的界面的入口暂时隐藏掉,试用期倒计时先注释掉
//                    if (partnerBean.getStatusX() == 0 && !Util.isEmpty(partnerBean.getExpiredDate())) { //试用期
//                        setTime();
//                        if (mHandler == null) {
//                            mHandler = new Handler() {
//                                @Override
//                                public void handleMessage(Message msg) {
//                                    super.handleMessage(msg);
//                                    //更改时间
//                                    setTime();
//                                    mHandler.sendEmptyMessageDelayed(1, 1000);
//                                }
//                            };
//                            mHandler.sendEmptyMessageDelayed(1, 1000);
//                        }
//                    } else {
//                        mineFragmentTopHolder.tvBuyerCount.setVisibility(View.GONE);
//                    }
                }
            }
        });
    }
    //买手等级logo
    private int getBuyerLevel(int level) {
        switch (level) {
            case 0:
                return R.mipmap.icon_am;
            case 1:
                return R.mipmap.icon_dm_level;
            case 2:
                return R.mipmap.icon_buyer_level;
            default:
                return R.mipmap.icon_buyer_level;
        }
    }

    //倒计时
    private void setTime() {
        if (partnerBean == null) {
            return;
        }
        long offer = DateUtil.strToDateLong(partnerBean.getExpiredDate()).getTime() - System.currentTimeMillis();
        if (offer <= 0) { //倒计时到了,先将本地的dialog标识置为true
            if (!D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.HAS_BUYER_COUNT, false)) {
                D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.HAS_BUYER_COUNT, true);
            }
            if ( mineFragmentTopHolder.rlBuyer.getVisibility() == View.VISIBLE) {
                mineFragmentTopHolder.rlBuyer.setVisibility(View.GONE);
                setRlUserHeight(160);
            }
//            if (!D2CApplication.mSharePref.getSharePrefBoolean(HAS_SHOW_BUYER_DIALOG, false) && isVisibleToUser) {
//                simpleDialog.show();
//                simpleDialog.setLeftClick(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        simpleDialog.dismiss();
//                    }
//                }).setRightClick(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(mContext, ReopenStoreActivity.class);
//                        mContext.startActivity(intent);
//                    }
//                }).setTitle("关店通知").setTvMessage(mContext.getResources().getString(R.string.buyer_close_notice))
//                        .setRightText("重新开店").setLeftText("我知道了");
//                D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.HAS_SHOW_BUYER_DIALOG, true);
//            }
            destoryHandle();
            return;
        }
        long day = offer / (24 * 60 * 60 * 1000);
        long hour = offer / (60 * 60 * 1000) % 24;
        long minute = (offer / (60 * 1000)) % 60;
        long mouse = (offer / 1000) % 60;
        if ( mineFragmentTopHolder.tvBuyerCount.getVisibility() == View.GONE) {
            mineFragmentTopHolder.tvBuyerCount.setVisibility(View.VISIBLE);
        }
        String text = String.format(mContext.getString(R.string.buyer_count_down), addZero(day), addZero(hour), addZero(minute), addZero(mouse));
        mineFragmentTopHolder.tvBuyerCount.setText(text);
    }


    //订单数,收藏,关注等数字
    public void setOrderCount(OrderCountBean orderCountBean){
        if (mContext==null) {
            return;
        }
        int waitingPayCount = orderCountBean.getData().getWaitForPay();
        int waitingDeliveredCount = orderCountBean.getData().getWaitForDelivered();
        int receivingCount = orderCountBean.getData().getDelivered();
        int assessCount = orderCountBean.getData().getWaitForComment();
        int collectCount = orderCountBean.getData().getCollectionCount();
        int attentionCount = orderCountBean.getData().getAttentionCount();
        if (unPay == null) {
            unPay = (QBadgeView) new QBadgeView(mContext).bindTarget(mineFragmentTopHolder.ivUnPay).setBadgeTextSize(9, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(2, 2, true);
        }
        if (waitingPayCount > 0) {

            if (waitingPayCount > 9) {
                unPay.setBadgeText(mContext.getResources().getString(R.string.label_number_more2));
            } else {
                unPay.setBadgeNumber(waitingPayCount);
            }

        } else {
            unPay.setBadgeNumber(0);
        }

        if (unPost == null) {
            unPost = (QBadgeView) new QBadgeView(mContext).bindTarget(mineFragmentTopHolder.ivUnDeliver).setBadgeTextSize(9, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(2, 2, true);
        }
        if (waitingDeliveredCount > 0) {
            if (waitingDeliveredCount > 9) {
                unPost.setBadgeText(mContext.getResources().getString(R.string.label_number_more2));
            } else {
                unPost.setBadgeNumber(waitingDeliveredCount);
            }
        } else {
            unPost.setBadgeNumber(0);
        }
        if (unReceive == null) {
            unReceive = (QBadgeView) new QBadgeView(mContext).bindTarget(mineFragmentTopHolder.ivWaitDeliver).setBadgeTextSize(9, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(2, 2, true);
        }
        if (receivingCount > 0) {
            if (receivingCount > 9) {
                unReceive.setBadgeText(mContext.getResources().getString(R.string.label_number_more2));
            } else {
                unReceive.setBadgeNumber(receivingCount);
            }
        } else {
            unReceive.setBadgeNumber(0);
        }

        if (unReview == null) {
            unReview = (QBadgeView) new QBadgeView(mContext).bindTarget(mineFragmentTopHolder.ivUnReview).setBadgeTextSize(9, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(2, 2, true);
        }
        if (assessCount > 0) {
            if (assessCount > 9) {
                unReview.setBadgeText(mContext.getResources().getString(R.string.label_number_more2));
            } else {
                unReview.setBadgeNumber(assessCount);
            }
        } else {
            unReview.setBadgeNumber(0);
        }

        if (collectCount > 0) {
            if (mineFragmentTopHolder.tvCollectNum != null) {
                if (collectCount > 99) {
                    mineFragmentTopHolder.tvCollectNum.setText("99+");
                } else {
                    mineFragmentTopHolder.tvCollectNum.setText(String.valueOf(collectCount));
                }
            }
        }
        if (mineFragmentTopHolder.tvFollowNum != null) {
            if (attentionCount > 99) {
                mineFragmentTopHolder.tvFollowNum.setText("99+");
            } else {
                mineFragmentTopHolder.tvFollowNum.setText(String.valueOf(attentionCount));
            }
        }
    }


    //优惠券数
    public void setCouponCount(CouponsBean couponCountsBean){
        int claimedCount = couponCountsBean.getData().getMyCoupons().getList().size();
        if (conpouNum == null) {
            conpouNum = (QBadgeView) new QBadgeView(mContext).bindTarget(mineFragmentTopHolder.ivCoupon).setBadgeTextSize(9, true).setBadgeGravity(Gravity.END | Gravity.TOP).stroke(Color.parseColor("#fff23365"), 1, true).setBadgeBackgroundColor(Color.parseColor("#ffffffff")).setBadgeTextColor(Color.parseColor("#fff23365")).setGravityOffset(28, -1.5f, true);
        }
        if (claimedCount > 0) {
            if (claimedCount > 9) {
                conpouNum.setBadgeText(mContext.getString(R.string.label_number_more2));
            } else {
                conpouNum.setBadgeNumber(claimedCount);
            }
        }
    }
    //售后数
    public void setAfterCount(AfterSaleCountBean response){
        if (mContext==null) {
            return;
        }
        int exchange = response.getData().getExchangeCount().getProcessCount();
        int reship = response.getData().getReshipCount().getProcessCount();
        int refund = response.getData().getRefundCount().getProcessCount();
        int total = exchange + reship + refund;
        if (unAfterBuy == null) {
            unAfterBuy = (QBadgeView) new QBadgeView(mContext).bindTarget(mineFragmentTopHolder.ivAfterBuy).setBadgeTextSize(9, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(2, 2, true);
        }
        if (total > 9) {
            unAfterBuy.setBadgeText(mContext.getResources().getString(R.string.label_number_more2));
        } else {
            unAfterBuy.setBadgeNumber(total);
        }

    }

    //足迹数
    public void setFootMarkNum(FootMarkCountBean response){
        if (mContext==null) {
            return;
        }
        int num = response.getData().getProducts();
        if (mineFragmentTopHolder.tvFootmarkNum != null) {
            if (num > 99) {
                mineFragmentTopHolder.tvFootmarkNum.setText("99+");
            } else {
                mineFragmentTopHolder.tvFootmarkNum.setText(String.valueOf(num));
            }
        }
    }

    private String addZero(long num) {
        if (num < 0) {
            return "0";
        }
        if (num < 10) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }

    //加载广告
    private void getAdBean() {
        user = Session.getInstance().getUserFromFile(mContext);
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.GET);
        api.setInterPath(Constants.AD_MY_PAGE);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MyAdBean>() {
            @Override
            public void onResponse(final MyAdBean response) {
                final MyAdBean.DataBean.AdResourceBean resourceBean = response.getData().getAdResource();
                if (resourceBean != null) {
                    mineFragmentTopHolder.adView.setVisibility(View.VISIBLE);
                    if (!Util.isEmpty(resourceBean.getPic())) {
                        UniversalImageLoader.displayImage(mContext, resourceBean.getPic(), mineFragmentTopHolder.adView, R.mipmap.ic_logo_empty2);
                    }
                    mineFragmentTopHolder.adView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!Util.isEmpty(resourceBean.getUrl())) {
                                if (user != null) {
                                    String url = resourceBean.getUrl() + "?sojumpparm=" + user.getMemberId();
                                    Util.urlAction(mContext, url);
                                } else {
                                    Util.urlAction(mContext, resourceBean.getUrl());
                                }

                            }
                        }
                    });
                } else {
                    mineFragmentTopHolder.adView.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mineFragmentTopHolder.adView != null) {
                    mineFragmentTopHolder. adView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void stat(String event, String lable) {
        StatService.onEvent(mContext, event, lable);
        TCAgent.onEvent(mContext, event, lable);
    }

    public void setRecommendTitleVisiable() {
        if(mineFragmentTopHolder!=null){
            mineFragmentTopHolder.recommendTitle.setVisibility(View.VISIBLE);
        }
    }

    public void setCartNum() {
        if(mineFragmentTopHolder==null){
            return;
        }
        if(cartNum==null){
            cartNum = (QBadgeView) new QBadgeView(mContext).bindTarget(mineFragmentTopHolder.ivCart).setBadgeTextSize(9, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(2, 2, true);
        }
        if (HomeActivity.count > 0 && mineFragmentTopHolder.ivCart != null) {
            if (mineFragmentTopHolder.ivCart == null) {
                mineFragmentTopHolder.ivCart = (ImageView) new QBadgeView(mContext).bindTarget(mineFragmentTopHolder.ivCart).setBadgeTextSize(10, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(2, 2, true);
            }
            if (HomeActivity.count > 9) {
                cartNum.setBadgeText("9+");
            } else {
                cartNum.setBadgeNumber(HomeActivity.count);
            }
        } else {
            if (cartNum != null) {
                ViewParent parent = cartNum.getParent();
                if (parent != null) {
                    ((ViewGroup) parent).removeView(cartNum);
                    cartNum = null;
                }
            }
        }
    }

    public void removeCartNum() {
        if(cartNum!=null){
            ViewParent parent = cartNum.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(cartNum);
                cartNum = null;
            }
        }
    }

    public void setTopBg(String pic) {
        if(mContext==null){
            return;
        }
        Glide.with(mContext).load(Util.getD2cPicUrl(pic+Constants.MY_SUFFIX)).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(resource);
                if(mineFragmentTopHolder!=null){
                    mineFragmentTopHolder.llMineTopBg.setBackground(bitmapDrawable);
                }
            }
        });
    }


    public interface ClickToPartnerListener{
        void clickToPartner();
    }
    public void setClickToPartnerListener(ClickToPartnerListener clickToPartnerListener) {
        this.clickToPartnerListener = clickToPartnerListener;
    }

    public void destoryHandle(){
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }
}
