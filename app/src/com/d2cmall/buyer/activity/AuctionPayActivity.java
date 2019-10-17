package com.d2cmall.buyer.activity;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.CheckPayPwdApi;
import com.d2cmall.buyer.api.PayOrderApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.MyPacketBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Base64;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.PayResult;
import com.d2cmall.buyer.util.PayUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;
import com.d2cmall.buyer.widget.MyCheckBox;
import com.d2cmall.buyer.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tendcloud.appcpa.TalkingDataAppCpa;
import com.zamplus.businesstrack.ZampAppAnalytics;

import java.math.RoundingMode;
import java.text.NumberFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * 拍卖付款
 * Author: hrb
 * Date: 2016/08/25 20:39
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class AuctionPayActivity extends BaseActivity {

    @Bind(R.id.price_tag)
    TextView priceTag;
    @Bind(R.id.tv_account)
    TextView tvAccount;
    @Bind(R.id.alipay_box)
    MyCheckBox alipayBox;
    @Bind(R.id.alipay_ll)
    LinearLayout alipayLl;
    @Bind(R.id.ali_pay_line)
    View aliPayLine;
    @Bind(R.id.wxpay_box)
    MyCheckBox wxpayBox;
    @Bind(R.id.wxpay_ll)
    LinearLayout wxpayLl;
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
    @Bind(R.id.pay_style_ll)
    LinearLayout payStyleLl;
    @Bind(R.id.content_layout)
    LinearLayout contentLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.btn_pay)
    Button btnPay;

    private Dialog loadingDialog;
    private IWXAPI mApp;
    private static final int SDK_PAY_FLAG = 1;
    private UserBean.DataEntity.MemberEntity memberEntity;
    private double availableAmount;
    private String url;
    private String billId = null;
    private String billSn = null;
    private String billType = null;
    private String billlSubject = null;
    private String billTotalFee = null;
    private String billPayTypes = null;
    private String backUrl = null;
    private String sign = null;
    private String good_type = null;
    private Dialog pwdDialog;
    private boolean canUseWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_pay);
        ButterKnife.bind(this);
        initTitle();

        openHandle.setVisibility(View.GONE);
        contentLayout.setVisibility(View.GONE);
        stagesPayLl.setVisibility(View.GONE);
        stagesPayLine.setVisibility(View.GONE);
        hbPayLl.setVisibility(View.GONE);
        hbPayLine.setVisibility(View.GONE);
        alipayLl.setVisibility(View.GONE);
        aliPayLine.setVisibility(View.GONE);
        wxpayLl.setVisibility(View.GONE);
        wxPayLine.setVisibility(View.GONE);
        d2cpayLl.setVisibility(View.GONE);
        d2cPayLine.setVisibility(View.GONE);
        if (Util.loginChecked(this, 0)) {
            if (checkDataFromIntent()) {//数据正常
                String[] pays = billPayTypes.split(",");
                for (String pay : pays) {
                    checkChannel(pay);
                }
                mApp = WXAPIFactory.createWXAPI(this, Constants.WEIXINAPPID, false);
                mApp.registerApp(Constants.WEIXINAPPID);
                if (!mApp.isWXAppInstalled()) {
                    wxpayLl.setVisibility(View.GONE);
                    wxPayLine.setVisibility(View.GONE);
                }
                priceTag.setVisibility(View.VISIBLE);
                tvAccount.setText(getString(R.string.label_price1, Util.getNumberFormat(Double.valueOf(billTotalFee))));
                memberEntity=Session.getInstance().getUserFromFile(this);
                if (canUseWallet&&memberEntity.isD2c()){
                    progressBar.setVisibility(View.VISIBLE);
                    requestMyPacketInfoTask();
                }else {
                    progressBar.setVisibility(View.GONE);
                    d2cpayLl.setVisibility(View.GONE);
                    d2cPayLine.setVisibility(View.GONE);
                    contentLayout.setVisibility(View.VISIBLE);
                }
                loadingDialog = DialogUtil.createLoadingDialog(this);
                initListener();
            } else { //数据错误
                Util.showToast(this, "数据解析错误");
            }
        }
    }

    private void initListener() {
        alipayLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alipayBox.isChecked()) {
                    return;
                }
                alipayBox.setChecked(true);
                changePayStyle(1);
            }
        });

        wxpayLl.setOnClickListener(new View.OnClickListener() {
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
                d2cBalance.setText("支付后还剩 ￥" + getNumberFormat(availableAmount - Double.valueOf(billTotalFee)));
                break;
            case 5:
                alipayBox.setChecked(false);
                wxpayBox.setChecked(false);
                godBox.setChecked(false);
                d2cpayBox.setChecked(false);
                hbPayBox.setChecked(false);
                break;
        }
        if (type != 4 && d2cpayLl.getVisibility()==View.VISIBLE) {
            if (availableAmount < Double.valueOf(billTotalFee)) {
                d2cBalance.setText(getString(R.string.label_money_less,
                        getNumberFormat(availableAmount)));
            } else {
                d2cBalance.setText("￥" + getNumberFormat(availableAmount));
            }
        }
    }

    private void checkChannel(String channel) {
        if (channel.equals("ALIPAY")) {
            alipayLl.setVisibility(View.VISIBLE);
            aliPayLine.setVisibility(View.VISIBLE);
        } else if (channel.equals("WXAPPPAY")) {
            wxpayLl.setVisibility(View.VISIBLE);
            wxPayLine.setVisibility(View.VISIBLE);
        } else if (channel.equals("WALLET")) {
            canUseWallet=true;
            d2cpayLl.setVisibility(View.VISIBLE);
            d2cPayLine.setVisibility(View.VISIBLE);
        }
    }

    public boolean checkDataFromIntent() {
        boolean isVal = false;
        url = getIntent().getStringExtra("url");
        url = new String(Base64.decode(url));
        String[] params = url.split("&");
        for (int i = 0; i < params.length; i++) {
            int start = params[i].indexOf("=");
            if (start < params[i].length() - 1) {
                if (params[i].contains("billId_")) {
                    billId = params[i].substring(start + 1);
                } else if (params[i].contains("billSn_")) {
                    billSn = params[i].substring(start + 1);
                } else if (params[i].contains("billType_")) {
                    billType = params[i].substring(start + 1);
                } else if (params[i].contains("billSubject_")) {
                    billlSubject = params[i].substring(start + 1);
                } else if (params[i].contains("billTotalFee_")) {
                    billTotalFee = params[i].substring(start + 1);
                } else if (params[i].contains("billPayTypes_")) {
                    billPayTypes = params[i].substring(start + 1);
                } else if (params[i].contains("backUrl_")) {
                    backUrl = params[i].substring(start + 1);
                } else if (params[i].contains("sign")) {
                    sign = params[i].substring(start + 1);
                } else if (params[i].contains("goodsType_")) {
                    good_type = params[i].substring(start + 1);
                }
            }
        }
        if (!Util.isEmpty(billId) && !Util.isEmpty(billSn) && !Util.isEmpty(billType) && !Util.isEmpty(billlSubject) && !Util.isEmpty(billTotalFee)
                && !Util.isEmpty(billPayTypes)) {
            if (!Util.isEmpty(sign)) {
                int index = url.lastIndexOf("&");
                String string1 = url.substring(0, index) + Constants.APP_SECRET;
                if (sign.equals(Util.getMD5(string1))) {
                    return true;
                }
            }
        }
        return isVal;
    }

    private void initTitle() {
        TitleUtil.setBack(this);
        TitleUtil.setTitle(this, "支付");
    }

    @OnClick(R.id.btn_pay)
    void onPay() {
        hideKeyboard(null);
        if (alipayBox.isChecked()){
            pay(null, "ALIPAY", 1);
        }else if (wxpayBox.isChecked()){
            pay(null, "WXAPPPAY", 2);
        }else if (d2cpayBox.isChecked()){
            showDialog();
        }
    }

    /**
     * 提交支付
     *
     * @param passWord
     * @param payType
     * @param type     1 表示支付宝 2微信
     */
    private void pay(String passWord, String payType, final int type) {
        publishPayInfo(billSn, Double.valueOf(billTotalFee), payType);
        if (type==1||type==2){
            switch (type) {
                case 1:
                    if (Double.valueOf(billTotalFee) > 0) {
                        loadingDialog.show();
                        alipay(billSn, billlSubject, billType, Double.valueOf(billTotalFee));
                    }
                    break;
                case 2:
                    if (Double.valueOf(billTotalFee) > 0) {
                        loadingDialog.show();
                        wxpay(billSn, billlSubject, (int) (Double.valueOf(billTotalFee) * 100+0.5));
                    }
                    break;
            }
        }else {
            loadingDialog.show();
            PayOrderApi payOrderApi = new PayOrderApi();
            payOrderApi.setOrderSn(billSn);
            payOrderApi.setPaymentType(payType);
            payOrderApi.setOrderType(billType);
            if (!Util.isEmpty(passWord)) {
                payOrderApi.setPassword(passWord);
            }
            D2CApplication.httpClient.loadingRequest(payOrderApi, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    loadingDialog.dismiss();
                    EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.AUCTIONPAY, 1));
                    switch (type) {
                        case 3:
                            if (!Util.isEmpty(backUrl)) { //重定向地址
                                Util.urlAction(AuctionPayActivity.this, backUrl);
                            }
                            finish();
                            break;
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loadingDialog.dismiss();
                }
            });
        }
    }

    private void alipay(String ordersn, String subject, String body, double price) {
        if (!Util.checkNetwork(this)) {
            Util.showToast(AuctionPayActivity.this, R.string.net_disconnect);
            return;
        }
        boolean virtual;
        if (good_type.equals("1")) {
            virtual = true;
        } else {
            virtual = false;
        }
        final String orderInfo = PayUtil.getInstance().getAlipayInfo(ordersn, subject, billType, price, virtual);
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                if (Util.isEmpty(orderInfo)) {
                    return;
                }
                PayTask alipay = new PayTask(AuctionPayActivity.this);
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
        String prepay_id = PayUtil.getInstance().getPrepayId(ordersn, billType, body, price, ip);
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

    private void requestMyPacketInfoTask() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.MY_WALLET_INFO_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MyPacketBean>() {
            @Override
            public void onResponse(MyPacketBean myPacketBean) {
                progressBar.setVisibility(View.GONE);
                contentLayout.setVisibility(View.VISIBLE);
                if (myPacketBean.getData().getAccount().isSetPassword()){
                    d2cpayLl.setVisibility(View.VISIBLE);
                    d2cPayLine.setVisibility(View.VISIBLE);
                    availableAmount = myPacketBean.getData().getAccount().getAvailableAmount();
                    if (availableAmount < Double.valueOf(billTotalFee)) {
                        d2cBalance.setText(getString(R.string.label_money_less,
                                getNumberFormat(myPacketBean.getData().getAccount().getAvailableAmount())));
                        d2cpayBox.setEnabled(false);
                        d2cpayLl.setEnabled(false);
                        d2cpayLl.setBackgroundResource(R.color.enable_color);
                    } else {
                        d2cBalance.setText(getString(R.string.label_price, getNumberFormat(myPacketBean.getData().getAccount().getAvailableAmount())));
                    }
                }else {
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
                Util.showToast(AuctionPayActivity.this, Util.checkErrorType(error));
            }
        });
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
                            publishPayInfo(billSn, Double.valueOf(billTotalFee), "ALIPAY");
                            EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.AUCTIONPAY, 1));
                            if (!Util.isEmpty(backUrl)) {
                                Util.urlAction(AuctionPayActivity.this, backUrl);
                            }
                            finish();
                            break;
                        case "6001":
                            finish();
                            break;
                        default:
                            finish();
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
                    Util.showToast(AuctionPayActivity.this, R.string.msg_password_empty);
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
        if (bean.type==Constants.ActionType.WXPAYRESULT){
            EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.AUCTIONPAY,1));
            if (!Util.isEmpty(backUrl)) {
                Util.urlAction(this, backUrl);
            }
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
