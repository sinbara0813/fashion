package com.d2cmall.buyer.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.BuildConfig;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.CheckPayPwdApi;
import com.d2cmall.buyer.api.CreateCouponOrderApi;
import com.d2cmall.buyer.api.PayOrderApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.api.UserinfoApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.BuyCouponBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.MeWalletBean;
import com.d2cmall.buyer.bean.MyPacketBean;
import com.d2cmall.buyer.bean.OrderInfoBean;
import com.d2cmall.buyer.bean.PayModeBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.bean.UserSignBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.http.HttpError;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.PayResult;
import com.d2cmall.buyer.util.PayUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;
import com.d2cmall.buyer.widget.MyCheckBox;
import com.d2cmall.buyer.widget.SelectHbPop;
import com.d2cmall.buyer.widget.TipPop;
import com.d2cmall.buyer.widget.TransparentPop;
import com.d2cmall.buyer.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tendcloud.appcpa.TalkingDataAppCpa;
import com.tendcloud.tenddata.TCAgent;
import com.zamplus.businesstrack.ZampAppAnalytics;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * 收银台
 * Author: Blend
 * Date: 16/6/3 10:05
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CashierActivity extends BaseActivity {
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
    @Bind(R.id.tv_account)
    TextView tvAccount;
    @Bind(R.id.alipay_box)
    MyCheckBox alipayBox;
    @Bind(R.id.alipay_ll)
    LinearLayout alipayll;
    @Bind(R.id.ali_pay_line)
    View aliPayLine;
    @Bind(R.id.wxpay_box)
    MyCheckBox wxpayBox;
    @Bind(R.id.wxpay_ll)
    LinearLayout wxpayll;
    @Bind(R.id.wx_pay_line)
    View wxPayLine;
    @Bind(R.id.god_box)
    MyCheckBox godBox;
    @Bind(R.id.god_ll)
    LinearLayout godLl;
    @Bind(R.id.d2cpay_box)
    MyCheckBox d2cpayBox;
    @Bind(R.id.d2c_balance)
    TextView d2cBalance;
    @Bind(R.id.d2cpay_ll)
    LinearLayout d2cpayLl;
    @Bind(R.id.d2c_pay_line)
    View d2cPayLine;
    @Bind(R.id.stages_pay_box)
    MyCheckBox stagesPayBox;
    @Bind(R.id.stage_item_info)
    TextView stageItemInfo;
    @Bind(R.id.select_stages_ll)
    LinearLayout selectStagesLl;
    @Bind(R.id.stages_pay_ll)
    LinearLayout stagesPayLl;
    @Bind(R.id.stages_pay_line)
    View stagesPayLine;
    @Bind(R.id.hb_pay_box)
    MyCheckBox hbPayBox;
    @Bind(R.id.hb_pay_ll)
    LinearLayout hbPayLl;
    @Bind(R.id.hb_pay_line)
    View hbPayLine;
    @Bind(R.id.hb_count)
    TextView hbCount;
    @Bind(R.id.select_hb_rl)
    RelativeLayout selectHbRl;
    @Bind(R.id.hide_layout_tag)
    TextView hideLayoutTag;
    @Bind(R.id.open_handle)
    LinearLayout openHandle;
    @Bind(R.id.btn_pay)
    Button btnPay;
    @Bind(R.id.content_layout)
    LinearLayout contentLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private IWXAPI mApp;
    private double totalPrice;
    private String orderSn;
    private Dialog loadingDialog;
    private String subject;
    private String payType ;
    private boolean isCouponPay;
    private int couponId;
    private static final int SDK_PAY_FLAG = 1;
    private UserBean.DataEntity.MemberEntity memberEntity;
    private double availableAmount;
    private Dialog pwdDialog;
    private boolean isApply = true;
    private String orderId;
    private ArrayList<String> channels = new ArrayList<>();
    private boolean isSellPayCharge;
    private double hbPrice;
    private int hbNum;
    private int passwordErrorNum;
    private String payStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);
        ButterKnife.bind(this);
        initTitle();

        openHandle.setVisibility(View.GONE);
        orderSn = getIntent().getStringExtra("id");
        orderId = getIntent().getStringExtra("orderId");
        isCouponPay = getIntent().getBooleanExtra("isCouponPay", false);
        subject = getIntent().getStringExtra("subject");
        if (!Util.isEmpty(subject)){
            subject=subject.replace("&","a");
        }
        payType = getIntent().getStringExtra("payType");
        if (payType==null){
            payType="ORDER";
        }else {
            payType=payType.toUpperCase();
        }
        payStyle = getIntent().getStringExtra("payStyle");
        isApply = getIntent().getBooleanExtra("isApply", true);
        couponId = getIntent().getIntExtra("couponId", 0);
        totalPrice = getIntent().getDoubleExtra("price", 0);
        mApp = WXAPIFactory.createWXAPI(this, Constants.WEIXINAPPID, false);
        mApp.registerApp(Constants.WEIXINAPPID);
        if (!mApp.isWXAppInstalled()) {
            wxpayll.setVisibility(View.GONE);
            wxPayLine.setVisibility(View.GONE);
        }
        contentLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        getPrice();
        loadingDialog = DialogUtil.createLoadingDialog(this);
        initListener();
    }

    public void getPrice() {
        if (couponId > 0) {
            CreateCouponOrderApi api = new CreateCouponOrderApi();
            api.defId = couponId;
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BuyCouponBean>() {
                @Override
                public void onResponse(BuyCouponBean response) {
                    orderSn = response.getData().getCouponOrder().getOrderSn();
                    subject = response.getData().getCouponOrder().getCouponName();
                    payStyle = response.getData().getCouponOrder().getPayParams();
                    totalPrice = response.getData().getCouponOrder().getTotalAmount();
                    tvAccount.setText(getString(R.string.label_price, getNumberFormat(totalPrice)));
                    //获取订单号及价格
                    getPayMode(false);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CashierActivity.this, Util.checkErrorType(error), Toast.LENGTH_SHORT).show();
                    getWindow().getDecorView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1500);
                }
            });
        } else {
            SimpleApi api = new SimpleApi();
            if ("COLLAGE".equals(payType)) {
                api.setMethod(BaseApi.Method.GET);
                api.setInterPath(String.format(Constants.COLLAGEDETAIL_URL, orderId));
            } else {
                api.setMethod(BaseApi.Method.POST);
                api.setInterPath(String.format(Constants.ORDER_INFO_URL, orderSn));
            }
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderInfoBean>() {

                @Override
                public void onResponse(OrderInfoBean response) {
                    if ("COLLAGE".equals(payType)){
                        orderId = response.getData().getCollageOrder().getId() + "";
                        totalPrice = response.getData().getCollageOrder().getTotalPay();
                    }else {
                        orderId = response.getData().getOrder().getId() + "";
                        totalPrice = response.getData().getOrder().getTotalPay();
                    }
                    tvAccount.setText(getString(R.string.label_price, getNumberFormat(totalPrice)));
                    if (response.getData().getOrder()!=null){
                        getPayMode(response.getData().getOrder().getCrossBorder()==1);
                    }else {
                        getPayMode(false);
                    }
                }
            });
        }
    }

    private void initTitle() {
        TitleUtil.setBack(this);
        TitleUtil.setTitle(this, R.string.label_cashier);
    }

    private void initListener() {
        alipayll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alipayBox.isChecked()) {
                    return;
                }
                alipayBox.setChecked(true);
                changePayStyle(1);
            }
        });

        wxpayll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wxpayBox.isChecked()) {
                    return;
                }
                wxpayBox.setChecked(true);
                changePayStyle(2);
            }
        });

        godLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (godBox.isChecked()) {
                    return;
                }
                godBox.setChecked(true);
                changePayStyle(3);
            }
        });

        d2cpayLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (d2cpayBox.isChecked()) {
                    return;
                }
                d2cpayBox.setChecked(true);
                changePayStyle(4);
            }
        });

        stagesPayLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stagesPayBox.isChecked()) {
                    return;
                }
                stagesPayBox.setChecked(true);
                changePayStyle(5);
            }
        });

        hbPayLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hbPayBox.isChecked()) {
                    return;
                }
                hbPayBox.setChecked(true);
                changePayStyle(6);
            }
        });

        selectHbRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectHB(null);
            }
        });
    }

    private void changePayStyle(int type) {
        switch (type) { //1支付宝 2 微信 3货到付款 4 微信支付 5 分期付款 6蚂蚁花呗
            case 1:
                wxpayBox.setChecked(false);
                d2cpayBox.setChecked(false);
                godBox.setChecked(false);
                stagesPayBox.setChecked(false);
                hbPayBox.setChecked(false);
                break;
            case 2:
                alipayBox.setChecked(false);
                d2cpayBox.setChecked(false);
                godBox.setChecked(false);
                stagesPayBox.setChecked(false);
                hbPayBox.setChecked(false);
                break;
            case 3:
                alipayBox.setChecked(false);
                wxpayBox.setChecked(false);
                d2cpayBox.setChecked(false);
                stagesPayBox.setChecked(false);
                hbPayBox.setChecked(false);
                break;
            case 4:
                alipayBox.setChecked(false);
                wxpayBox.setChecked(false);
                godBox.setChecked(false);
                stagesPayBox.setChecked(false);
                hbPayBox.setChecked(false);
                d2cBalance.setText("支付后还剩 ￥" + getNumberFormat(availableAmount - totalPrice));
                break;
            case 5:
                alipayBox.setChecked(false);
                wxpayBox.setChecked(false);
                godBox.setChecked(false);
                d2cpayBox.setChecked(false);
                hbPayBox.setChecked(false);
                break;
            case 6:
                alipayBox.setChecked(false);
                wxpayBox.setChecked(false);
                godBox.setChecked(false);
                d2cpayBox.setChecked(false);
                stagesPayBox.setChecked(false);
                if (hbNum == 0) {
                    showSelectHB(hbCount);
                }
                selectHbRl.setVisibility(View.VISIBLE);
                break;
        }
        if (type != 4 && d2cpayLl.getVisibility() == View.VISIBLE) {
            if (availableAmount < totalPrice) {
                d2cBalance.setText(getString(R.string.label_money_less,
                        getNumberFormat(availableAmount)));
            } else {
                d2cBalance.setText("￥" + getNumberFormat(availableAmount));
            }
        }
        if (type != 6 && selectHbRl.getVisibility() == View.VISIBLE) {
            selectHbRl.setVisibility(View.GONE);
        }
    }

    private void showSelectHB(TextView textView) {
        final SelectHbPop selectHbPop = new SelectHbPop(CashierActivity.this, totalPrice, isSellPayCharge, hbPrice, hbNum, textView);
        selectHbPop.setDissMissListener(new TransparentPop.DismissListener() {
            @Override
            public void dismissStart() {

            }

            @Override
            public void dismissEnd() {
                hbNum = selectHbPop.getCurrentCount();
                hbPrice = selectHbPop.getCurrentPrice();
                if (hbNum > 0) {
                    hbCount.setText(Util.getNumberFormat(hbPrice) + "元" + " x " + hbNum + "期");
                }
            }
        });
        selectHbPop.show(getWindow().getDecorView());
    }

    /**
     * 获取禁用支付模式
     */
    private void getPayMode(boolean isCross) {
        List<String> list = new ArrayList<>();
        list.add("ALIPAY");
        list.add("WXAPPPAY");
        list.add("WALLET");
        list.add("MIMEPAY");
        list.add("HBPAY");
        if (!Util.isEmpty(payStyle)) {
            String[] styles = payStyle.split(",");
            int size = styles.length;
            for (int i = 0; i < list.size(); i++) {
                boolean isContain = false;
                for (int j = 0; j < size; j++) {
                    if (list.get(i).equals(styles[j])) {
                        if (isCross&&list.get(i).equals("WALLET")){
                            isContain=false;
                        }else {
                            isContain=true;
                        }
                        break;
                    }
                }
                if (!isContain) {
                    channels.add(list.get(i));
                    checkChannel(list.get(i));
                }
            }
            UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(CashierActivity.this);
            if (user.isD2c()&&!isCross) {
                requestMyPacketInfoTask();
            } else {
                progressBar.setVisibility(View.GONE);
                d2cpayLl.setVisibility(View.GONE);
                d2cPayLine.setVisibility(View.GONE);
                contentLayout.setVisibility(View.VISIBLE);
            }
        } else {
            SimpleApi api = new SimpleApi();
            api.setInterPath(Constants.GET_PAY_MODE_URL);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PayModeBean>() {
                @Override
                public void onResponse(PayModeBean response) {
                    if (response.getData().getChannels() != null && response.getData().getChannels().size() > 0) {
                        int size = response.getData().getChannels().size();
                        for (int i = 0; i < size; i++) {
                            String channel = response.getData().getChannels().get(i);
                            checkChannel(channel);
                        }
                        if (isCross){
                            checkChannel("WALLET");
                        }
                    }
                    UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(CashierActivity.this);
                    if (user.isD2c()&&!isCross) {
                        requestMyPacketInfoTask();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        d2cpayLl.setVisibility(View.GONE);
                        d2cPayLine.setVisibility(View.GONE);
                        contentLayout.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    private void checkChannel(String channel) {
        if (channel.equals("ALIPAY")) {
            alipayll.setVisibility(View.GONE);
            aliPayLine.setVisibility(View.GONE);
        } else if (channel.equals("WXAPPPAY")) {
            wxpayll.setVisibility(View.GONE);
            wxPayLine.setVisibility(View.GONE);
        } else if (channel.equals("WALLET")) {
            d2cpayLl.setVisibility(View.GONE);
            d2cPayLine.setVisibility(View.GONE);
        } else if (channel.equals("MIMEPAY")) {
            stagesPayLl.setVisibility(View.GONE);
            stagesPayLine.setVisibility(View.GONE);
        } else if (channel.equals("HBPAY")) {
            hbPayLl.setVisibility(View.GONE);
            hbPayLine.setVisibility(View.GONE);
            selectHbRl.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_pay)
    void onPay() {
        hideKeyboard(null);
        HashMap<String,String> map=new HashMap<>();
        if (alipayBox.isChecked()) {
            map.put("立即付款_支付方式","支付宝");
            pay(null, "ALIPAY", 1);
        } else if (wxpayBox.isChecked()) {
            map.put("立即付款_支付方式","微信");
            pay(null, "WXAPPPAY", 2);
        } else if (d2cpayBox.isChecked()) {
            map.put("立即付款_支付方式","钱包");
            showDialog();
        } else if (stagesPayBox.isChecked()) {
            map.put("立即付款_支付方式","分期");
            queryWallet();
        } else if (hbPayBox.isChecked()) {
            map.put("立即付款_支付方式","花呗");
            if (hbNum == 0) {
                Util.showToast(CashierActivity.this, "请先选择期数");
                return;
            }
            pay(null, "ALIPAY", 4);
        }
        TCAgent.onEvent(this,"收银台下单","立即付款",map);
    }

    /**
     * 提交支付
     *
     * @param passWord
     * @param paymentType
     * @param type        1 表示支付宝 2微信 3钱包 4花呗
     */
    private void pay(String passWord, String paymentType, final int type) {
        if (type == 1 || type == 2 || type == 4) {
            switch (type) {
                case 1:
                    if (totalPrice > 0) {
                        loadingDialog.show();
                        alipay(orderSn, subject, subject, totalPrice, false);
                    } else if (totalPrice == 0) {
                        gotoActivityByType(1);
                    }
                    break;
                case 2:
                    if (totalPrice > 0) {
                        loadingDialog.show();
                        wxpay(orderSn, subject, (int) (totalPrice * 100+0.5));
                    } else if (totalPrice == 0) {
                        gotoActivityByType(1);
                    }
                    break;
                case 4:
                    if (totalPrice > 0) {
                        loadingDialog.show();
                        alipay(orderSn, subject, subject, totalPrice, true);
                    } else if (totalPrice == 0) {
                        gotoActivityByType(1);
                    }
            }
        } else {
            loadingDialog.show();
            PayOrderApi payOrderApi = new PayOrderApi();
            payOrderApi.setLoginCode(Session.getInstance().getUserFromFile(this).getLoginCode());
            payOrderApi.setOrderSn(orderSn);
            payOrderApi.setPaymentType(paymentType);
            if (couponId > 0) {
                payOrderApi.setOrderType("COUPON");
            } else if ("COLLAGE".equals(payType)) {
                payOrderApi.setOrderType("COLLAGE");
            } else {
                payOrderApi.setOrderType("ORDER");
            }
            if (!Util.isEmpty(passWord)) {
                payOrderApi.setPassword(passWord);
            }
            D2CApplication.httpClient.loadingRequest(payOrderApi, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    loadingDialog.dismiss();
                    gotoActivityByType(1);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Util.showToast(CashierActivity.this, Util.checkErrorType(error));
                    loadingDialog.dismiss();
                }
            });
        }
    }

    private void queryWallet() {
        loadingDialog.show();
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.POST);
        api.setInterPath(Constants.QUERY_MEME_WALLET_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MeWalletBean>() {
            @Override
            public void onResponse(MeWalletBean response) {
                String code = response.getData().getCode();
                switch (code) {
                    case "1001":
                    case "1002":
                    case "1003":
                    case "1004":
                    case "1006":
                    case "1005":
//                        stagesPay();
                        break;
                    case "1007":
                        loadingDialog.dismiss();
                        Toast.makeText(CashierActivity.this, "您的申请在审核中,请耐心等待", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Util.showToast(CashierActivity.this, Util.checkErrorType(error));
            }
        });
    }

//    private void stagesPay() {
//        BaseApi api;
//        if (!isApply) {//去申请
//            api = new PayOrderApi();
//            ((PayOrderApi) api).setPaymentType("MIMEPAY");
//            ((PayOrderApi) api).setOrderType(payType);
//            ((PayOrderApi) api).setLoginCode(Session.getInstance().getUserFromFile(this).getLoginCode());
//        } else {
//            api = new UserinfoApi();
//            ((UserinfoApi) api).loginCode = Session.getInstance().getUserFromFile(this).getLoginCode();
//        }
//        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<UserSignBean>() {
//            @Override
//            public void onResponse(UserSignBean response) {
//                loadingDialog.dismiss();
//                String userId = response.getDatas().getThdUserId();
//                if (Util.isEmpty(userId))
//                    return;
//                if (BuildConfig.DEBUG) {
//                    WalletManager.enableDebug();
//                }
//                IWalletCore mWalletCore = WalletManager.createWalletCore(CashierActivity.this);
//                IWalletListener iWalletListener = new IWalletListener() {
//                    @Override
//                    public void onComplete(Bundle bundle) {
//                        /*code：结果状态码（String）；1 成功，2 失败，3 流程取消
//                        scene：调用场景（String）； 11 申请，12 激活，13 支付，14 登录，15忘记密码，16 未知
//                        desc：结果描述信息（String）；*/
//                        String code = bundle.getString("code");
//                        String scene = bundle.getString("scene");
//                        String desc = bundle.getString("desc");
//                        if (code.equals("1")) {
//                            Intent intent = new Intent(CashierActivity.this, MyOrderActivity.class);
//                            startActivity(intent);
//                            finish();
//                            overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
//                        } else {
//                            gotoActivityByType(2);
//                        }
//                    }
//                };
//                mWalletCore.useWallet(userId, orderSn, iWalletListener);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                loadingDialog.dismiss();
//                Util.showToast(CashierActivity.this, Util.checkErrorType(error));
//            }
//        });
//    }

    private void alipay(String ordersn, String subject, String body, double price, boolean isHB) {
        if (!Util.checkNetwork(this)) {
            Util.showToast(CashierActivity.this, R.string.net_disconnect);
            return;
        }
        final String orderInfo;
        if (isHB) {
            orderInfo = PayUtil.getInstance().getAlipayHBInfo(ordersn, subject, payType, price, hbNum, isSellPayCharge);
        } else {
            orderInfo = PayUtil.getInstance().getAlipayInfo(ordersn, subject, payType, price);
        }
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                if (Util.isEmpty(orderInfo)) {
                    return;
                }
                PayTask alipay = new PayTask(CashierActivity.this);
                String result = alipay.pay(orderInfo);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
        loadingDialog.dismiss();
    }

    private void wxpay(final String ordersn, final String body, final int price) {
        final String ip = Util.getIp(this);
        if (!Util.isEmpty(ip)) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        requestPayTask(ordersn, body, price, ip);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            };
            thread.start();
        }
    }

    private void requestPayTask(String ordersn, String body, int price, String ip) throws Throwable {
        if (body.length() > 40) {
            body = body.substring(0, 30);
        }
        String prepay_id = PayUtil.getInstance().getPrepayId(ordersn, payType, body, price, ip);
        if (!Util.isEmpty(prepay_id)) {
            PayReq request = new PayReq();
            request.appId = Constants.WEIXINAPPID;
            request.nonceStr = Util.getRandomString(15);
            request.packageValue = "Sign=WXPay";
            request.partnerId = Constants.WXMCHID;
            request.prepayId = prepay_id;
            request.timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
            StringBuilder builder = new StringBuilder();
            builder.append("appid=").append(request.appId).append("&").append("noncestr=").
                    append(request.nonceStr).append("&").append("package=").append(request.packageValue).
                    append("&").append("partnerid=").append(request.partnerId).append("&")
                    .append("prepayid=").append(request.prepayId).append("&").append("timestamp=").append(request.timeStamp);
            String temp = builder.toString() + "&key=" + Constants.WXPARTNERID;
            request.sign = Util.getMD5(temp).toUpperCase();
            mApp.sendReq(request);
        }
        loadingDialog.dismiss();
    }

    private void gotoActivityByType(int type) {
        stat(type);
        if (isCouponPay) {
            if (type == 1) {
                Intent intent = new Intent(this, MyCouponsActivity.class);
                startActivity(intent);
                finish();
                return;
            } else {
                finish();
                return;
            }
        }
        if ("COLLAGE".equals(payType)){
            finish();
            return;
        }
        EventBus.getDefault().post(new ActionBean(Constants.ActionType.REFRESH_CART));
        Intent intent = null;
        if (type == 1) {
            intent = new Intent(this, PaySuccessActivity.class);
            if (!Util.isEmpty(orderId)) {
                intent.putExtra("id", orderId);
            }
        } else {
            intent = new Intent(this, PayFailedActivity.class);
            intent.putExtra("subject", subject);
            intent.putExtra("orderSn", orderSn);
            intent.putExtra("isApply", isApply);
            intent.putExtra("payStyle", payStyle);
        }
        startActivity(intent);
        finish();
    }

    private void stat(int type){
        if (type==1){
            stat("收银台下单","付款成功",null);
        }else {
            stat("收银台下单","付款失败",null);
        }
    }

    private void requestMyPacketInfoTask() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.MY_WALLET_INFO_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MyPacketBean>() {
            @Override
            public void onResponse(MyPacketBean myPacketBean) {
                progressBar.setVisibility(View.GONE);
                contentLayout.setVisibility(View.VISIBLE);
                if (myPacketBean.getData().getAccount().isSetPassword() && !channels.contains("WALLET")) {
                    d2cpayLl.setVisibility(View.VISIBLE);
                    d2cPayLine.setVisibility(View.VISIBLE);
                    availableAmount = myPacketBean.getData().getAccount().getAvailableAmount();
                    if (availableAmount < totalPrice) {
                        d2cBalance.setText(getString(R.string.label_money_less,
                                getNumberFormat(myPacketBean.getData().getAccount().getAvailableAmount())));
                        d2cpayBox.setEnabled(false);
                        d2cpayLl.setEnabled(false);
                        d2cpayLl.setBackgroundResource(R.color.enable_color);
                    } else {
                        d2cBalance.setText(getString(R.string.label_price, getNumberFormat(myPacketBean.getData().getAccount().getAvailableAmount())));
                    }
                } else {
                    d2cpayLl.setVisibility(View.GONE);
                    d2cPayLine.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                contentLayout.setVisibility(View.VISIBLE);
                d2cpayLl.setVisibility(View.GONE);
                d2cPayLine.setVisibility(View.GONE);
                //Util.showToast(CashierActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void callBack(String string) {
        final String md5edPayPassword = Util.getMD5(string.trim());
        CheckPayPwdApi api = new CheckPayPwdApi();
        api.setPassWord(md5edPayPassword);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                pay(md5edPayPassword, "WALLET", 3);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof HttpError) {
                    HttpError error1 = (HttpError) error;
                    if (error1.getStatus() == -1) {
                        passwordErrorNum++;
                        showTipPop();
                    }
                } else {
                    Util.showToast(CashierActivity.this, Util.checkErrorType(error));
                }
            }
        });
    }

    private void showTipPop() {
        TipPop tipPop = new TipPop(this, false);
        tipPop.getContentView().setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        tipPop.getBtnCancelView().setTextColor(Color.parseColor("#007aff"));
        tipPop.getBtnSureView().setTextColor(Color.parseColor("#007aff"));
        if (passwordErrorNum < 5) {
            tipPop.setBtnCancel(R.string.label_input_again);
            if (passwordErrorNum >= 3) {
                tipPop.setContent("输入密码有误，您还可以输入" + (5 - passwordErrorNum) + "次," + (5 - passwordErrorNum) + "次错误后您的密码将被锁定2小时");
            } else {
                tipPop.setContent("输入密码有误，您还可以输入" + (5 - passwordErrorNum) + "次");
            }
        } else {
            tipPop.setBtnCancel(R.string.action_cancel);
            tipPop.setContent(R.string.msg_pay_error);
        }
        tipPop.setBack(new TipPop.CallBack() {
            @Override
            public void sure() {
                Intent intent = new Intent(CashierActivity.this, SetPayPasswordActivity.class);
                intent.putExtra("type", 3);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            }

            @Override
            public void cancel() {
                if (passwordErrorNum < 5) {
                    showDialog();
                }
            }
        });
        tipPop.setBtnSure(R.string.label_forget_pay_password);
        tipPop.show(btnPay);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    String resultStatus = payResult.getResultStatus();
                    switch (resultStatus) {
                        case "9000":
                            publishPayInfo(orderSn, totalPrice, "ALIPAY");
                            gotoActivityByType(1);
                            finish();
                            break;
                        case "6001":
                            if (isCouponPay) {
                                Toast.makeText(CashierActivity.this, "支付取消", Toast.LENGTH_SHORT).show();
                            }
                            gotoActivityByType(3);
                            break;
                        default:
                            gotoActivityByType(2);
                            break;
                    }
                }
            }
        }
    };

    private void publishPayInfo(String orderId, double total, String payType) {
        if (memberEntity == null) {
            memberEntity = Session.getInstance().getUserFromFile(this);
        }
        TalkingDataAppCpa.onOrderPaySucc(String.valueOf(memberEntity.getMemberId()), orderId, (int) total * 100, "CNY", payType);
        ZampAppAnalytics.onPay(this, memberEntity.getMemberId() + "", orderId, (float) total, "RMB", payType, true);
    }

    @Override
    protected void onResume() {
        Util.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Util.onPause(this);
        super.onPause();
    }

    private void showDialog() {
        if (pwdDialog != null && pwdDialog.isShowing()) {
            return;
        }
        pwdDialog = new Dialog(this, R.style.bubble_vis_dialog);
        Point point = Util.getDeviceSize(this);
        View dialogView = getLayoutInflater().inflate(R.layout.layout_password_dialog, null);
        final ClearEditText etNumber = (ClearEditText) dialogView.findViewById(R.id.et_number);
        dialogView.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwdDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.confirm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = etNumber.getText().toString();
                if (Util.isEmpty(pwd)) {
                    Util.showToast(CashierActivity.this, R.string.msg_password_empty);
                } else {
                    pwdDialog.dismiss();
                    callBack(pwd);
                }
            }
        });
        pwdDialog.setContentView(dialogView);
        Window win = pwdDialog.getWindow();
        ViewGroup.LayoutParams params = win.getAttributes();
        params.width = Math.round(point.x * 3 / 4);
        win.setGravity(Gravity.CENTER);
        pwdDialog.show();
    }

    public String getNumberFormat(double value) {
        if (value % 1 == 0) {
            return String.valueOf((int) value);
        } else {
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            nf.setRoundingMode(RoundingMode.HALF_UP);
            nf.setGroupingUsed(true);
            return nf.format(value);
        }
    }

    @Subscribe
    public void onEvent(ActionBean bean) {
        if (bean.type == Constants.ActionType.WXPAYRESULT) {
            int code = (int) bean.get("code");
            Intent intent = null;
            if (isCouponPay) {
                if (code == 0) {
                    intent = new Intent(this, MyCouponsActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                } else {
                    finish();
                    return;
                }
            }
            if ("COLLAGE".equals(payType)){
                finish();
                return;
            }
            switch (code) {
                case 0: //支付成功
                    publishPayInfo(orderSn, totalPrice, "WXAPPPAY");
                    intent = new Intent(this, PaySuccessActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("id", orderId);
                    break;
                case -1: //支付失败
                    intent = new Intent(this, PayFailedActivity.class);
                    intent.putExtra("type", 2);
                    intent.putExtra("subject", subject);
                    intent.putExtra("orderSn", orderSn);
                    intent.putExtra("isApply", isApply);
                    intent.putExtra("payStyle", payStyle);
                    break;
                case -2: //支付取消
                    intent = new Intent(this, PayFailedActivity.class);
                    intent.putExtra("type", 3);
                    intent.putExtra("subject", subject);
                    intent.putExtra("orderSn", orderSn);
                    intent.putExtra("isApply", isApply);
                    intent.putExtra("payStyle", payStyle);
                    break;
            }
            EventBus.getDefault().post(new ActionBean(Constants.ActionType.REFRESH_CART));
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler=null;
        }
        super.onDestroy();
    }
}
