package com.d2cmall.buyer.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.DepositApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.DepositBean;
import com.d2cmall.buyer.bean.RuleBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.CashierInputFilter;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.PayResult;
import com.d2cmall.buyer.util.PayUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.CheckableLinearLayoutButton;
import com.d2cmall.buyer.widget.CheckableLinearLayoutGroup;
import com.d2cmall.buyer.widget.ClearEditText;
import com.d2cmall.buyer.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dreamtobe.kpswitch.widget.KPSwitchPanelLinearLayout;
import de.greenrobot.event.Subscribe;

/**
 * Created by rookie on 2017/9/7.
 * 在线充值钱包页面
 */

public class RechargeActivity extends BaseActivity {
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.et_deposit)
    ClearEditText etDeposit;
    @Bind(R.id.tv_recharge_rule)
    TextView tvRechargeRule;
    @Bind(R.id.alipay_layout)
    CheckableLinearLayoutButton alipayLayout;
    @Bind(R.id.wxpay_layout)
    CheckableLinearLayoutButton wxpayLayout;
    @Bind(R.id.deposit_menu)
    CheckableLinearLayoutGroup depositMenu;
    @Bind(R.id.btn_deposit)
    Button btnDeposit;
    @Bind(R.id.panel_layout)
    KPSwitchPanelLinearLayout panelLayout;
    @Bind(R.id.ali_is_select)
    ImageView aliIsSelect;
    @Bind(R.id.wx_is_select)
    ImageView wxIsSelect;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.tv_gift)
    TextView tvGift;

    private Dialog loadingDialog;
    private String orderSn;
    private IWXAPI mApp;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((String) msg.obj);
            String resultStatus = payResult.getResultStatus();
            switch (resultStatus) {
                case "9000":
                    Util.showToast(RechargeActivity.this, "充值成功");
                    setResult(RESULT_OK);
                    finish();
                    break;
                case "6001":
                    Util.showToast(RechargeActivity.this, "充值取消");
                    break;
                default:
                    Util.showToast(RechargeActivity.this, "充值失败");
                    break;
            }
        }
    };
    private TextWatcher textWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

            boolean isMoney = false;
            if (!Util.isEmpty(etDeposit.getText().toString())) {
                double moneyDouble = Double.parseDouble(etDeposit.getText().toString());
                if (moneyDouble > 0) {
                    isMoney = true;
                }
            }
            if (isMoney) {
                btnDeposit.setEnabled(true);
            } else {
                btnDeposit.setEnabled(false);
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_layout);
        ButterKnife.bind(this);
        nameTv.setText("在线充值");
        mApp = WXAPIFactory.createWXAPI(this, Constants.WEIXINAPPID, false);
        mApp.registerApp(Constants.WEIXINAPPID);
        if (mApp.isWXAppInstalled()) {
            wxpayLayout.setVisibility(View.VISIBLE);
        } else {
            wxpayLayout.setVisibility(View.GONE);
        }
        loadingDialog = DialogUtil.createLoadingDialog(this);
        InputFilter[] filters = {new CashierInputFilter()};
        etDeposit.setFilters(filters);//小数点后只能输两位
        etDeposit.addTextChangedListener(textWatcher);
        getRule();
    }

    private void wxPay(final int price) {
        final String ip = Util.getIp(this);
        if (!Util.isEmpty(ip)) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        payTask(orderSn, getString(R.string.label_wx_recharge), price, ip);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            };
            thread.start();
        }
    }

    private void payTask(String ordersn, String body, int price, String ip) throws Throwable {
        if (body.length() > 40) {
            body = body.substring(0, 30);
        }
        String prepay_id = PayUtil.getInstance().getPrepayId(ordersn, "recharge".toUpperCase(), body, price, ip);
        if (!Util.isEmpty(prepay_id)) {
            PayReq request = new PayReq();
            request.appId = Constants.WEIXINAPPID;
            request.nonceStr = Util.getRandomString(15);
            request.packageValue = "Sign=WXPay";
            request.partnerId = Constants.WXMCHID;
            request.prepayId = prepay_id;
            request.timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
            StringBuilder builder = new StringBuilder();
            builder.append("appid=").append(request.appId).append("&").append("noncestr=").append(request.nonceStr).append("&").append("package=").append(request.packageValue).append("&").append("partnerid=").append(request.partnerId).append("&")
                    .append("prepayid=").append(request.prepayId).append("&").append("timestamp=").append(request.timeStamp);
            String temp = builder.toString() + "&key=" + Constants.WXPARTNERID;
            String s = Util.getMD5(temp).toUpperCase();
            request.sign = s;
            mApp.sendReq(request);
        }
    }

    private void alipay(double price) {
        if (!Util.checkNetwork(this)) {
            Util.showToast(RechargeActivity.this, R.string.net_disconnect);
            return;
        }
        final String orderInfo = PayUtil.getInstance().getAlipayInfo(orderSn, getString(R.string.label_alipay_recharge), "recharge".toUpperCase(), price, false);
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(RechargeActivity.this);
                String result = alipay.pay(orderInfo);
                Message msg = new Message();
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void getRule() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.DEPOSIT_RULE_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RuleBean>() {
            @Override
            public void onResponse(RuleBean response) {
                if (response.getData().getRechargeRule() != null) {
                    String name = response.getData().getRechargeRule().getName();
                    if (name == null) {
                        name = "充值规则";
                    }
                    String solution = response.getData().getRechargeRule().getSolution();
                    if (Util.isEmpty(solution)) {
                        tvRechargeRule.setVisibility(View.INVISIBLE);
                    } else {
                        tvRechargeRule.setVisibility(View.VISIBLE);
                        String[] rules = solution.split(",");
                        double one;
                        double two;
                        if (rules.length > 0) {
                            StringBuilder ruleStr = new StringBuilder();
                            ruleStr.append("*" + name).append("：");
                            for (int i = 0; i < rules.length; i++) {
                                String[] s = rules[i].split("\\+");
                                if (s.length == 2) {
                                    one = Double.valueOf(s[0]);
                                    two = Double.valueOf(s[1]);
                                    ruleStr.append("充值" +
                                            Util.getNumberFormat(one, false) + "送" +
                                            Util.getNumberFormat(two, false));
                                    if (i != rules.length - 1) {
                                        ruleStr.append("，");
                                    }
                                }
                            }
                            ruleStr.append("," + response.getData().getRechargeRule().getRuleTypeName());
                            if (response.getData().getRechargeRule().getLimited() == 1) {
                                tvGift.setVisibility(View.VISIBLE);
                            } else {
                                tvGift.setVisibility(View.GONE);
                            }
                            tvRechargeRule.setText(ruleStr);
                        }

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    private void pay() {
        String depositNum = etDeposit.getText().toString().trim();
        final double moneyDouble = Double.parseDouble(depositNum);
        loadingDialog.show();
        DepositApi api = new DepositApi();
        String payType;
        if (depositMenu.getCheckedRadioButtonId() == R.id.alipay_layout) {
            payType = "ALIPAY";
        } else {
            payType = "WXAPPPAY";
        }
        api.setPayChannel(payType);
        api.setRechargeAmount(moneyDouble);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<DepositBean>() {
            @Override
            public void onResponse(DepositBean response) {
                loadingDialog.dismiss();
                orderSn = response.getData().getSn();
                switch (depositMenu.getCheckedRadioButtonId()) {
                    case R.id.alipay_layout:
                        alipay(moneyDouble);
                        break;
                    case R.id.wxpay_layout:
                        wxPay((int) (moneyDouble * 100+0.5));
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Util.showToast(RechargeActivity.this, Util.checkErrorType(error));
            }
        });
    }


    @OnClick({R.id.back_iv, R.id.btn_deposit, R.id.alipay_layout, R.id.wxpay_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_deposit:
                pay();
                break;
            case R.id.wxpay_layout:
                depositMenu.check(R.id.wxpay_layout);
                wxIsSelect.setImageResource(R.mipmap.icon_shopcart_aselected);
                aliIsSelect.setImageResource(R.mipmap.icon_shopcart_unaselected);
                break;
            case R.id.alipay_layout:
                depositMenu.check(R.id.alipay_layout);
                wxIsSelect.setImageResource(R.mipmap.icon_shopcart_unaselected);
                aliIsSelect.setImageResource(R.mipmap.icon_shopcart_aselected);
                break;
        }
    }

    @Subscribe
    public void onEvent(ActionBean bean) {
        if (bean.type==Constants.ActionType.WXPAYRESULT){
            setResult(RESULT_OK);
            finish();
        }
    }
}
