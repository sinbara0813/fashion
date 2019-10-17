package com.d2cmall.buyer.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.BuyNowApi;
import com.d2cmall.buyer.api.CartConfirmApi;
import com.d2cmall.buyer.api.CheckPayPwdApi;
import com.d2cmall.buyer.api.CollageCreateOrderApi;
import com.d2cmall.buyer.api.CollageOrderConfirmApi;
import com.d2cmall.buyer.api.CombBuyApi;
import com.d2cmall.buyer.api.CreateOrderApi;
import com.d2cmall.buyer.api.KaolaConfirmOrderApi;
import com.d2cmall.buyer.api.PayOrderApi;
import com.d2cmall.buyer.api.SelectCouponApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.CheckPayBean;
import com.d2cmall.buyer.bean.CreateOrderBean;
import com.d2cmall.buyer.bean.KaoLaConfirmBean;
import com.d2cmall.buyer.bean.MeWalletBean;
import com.d2cmall.buyer.bean.OrderConfirmBean;
import com.d2cmall.buyer.bean.PackOrderBean;
import com.d2cmall.buyer.bean.SelectCouponBean;
import com.d2cmall.buyer.bean.StageItemBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.http.HttpError;
import com.d2cmall.buyer.util.CenterAlignImageSpan;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.LoctionUtil;
import com.d2cmall.buyer.util.PayResult;
import com.d2cmall.buyer.util.PayUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;
import com.d2cmall.buyer.widget.MyCheckBox;
import com.d2cmall.buyer.widget.SelectHbPop;
import com.d2cmall.buyer.widget.SimpleDialog;
import com.d2cmall.buyer.widget.TipPop;
import com.d2cmall.buyer.widget.TransparentPop;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tendcloud.appcpa.Order;
import com.tendcloud.appcpa.TalkingDataAppCpa;
import com.zamplus.businesstrack.ZampAppAnalytics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class OrderBalanceActivity extends BaseActivity {

    public static int FROMCAR = 2;
    public static int BUYNOW = 1;
    public static int COMBBUYNOW = 3;
    @Bind(R.id.address_name)
    TextView addressName;
    @Bind(R.id.address_phone)
    TextView addressPhone;
    @Bind(R.id.address_info)
    TextView addressInfo;
    @Bind(R.id.address_ll)
    RelativeLayout addressLL;
    @Bind(R.id.edit_address_rl)
    RelativeLayout editAddress;
    @Bind(R.id.order_list_content)
    LinearLayout orderListContent;
    @Bind(R.id.mark)
    EditText mark;
    @Bind(R.id.coupon_ll)
    LinearLayout couponLl;
    @Bind(R.id.coupon_num)
    TextView couponNum;
    @Bind(R.id.coupon_name)
    TextView couponName;
    @Bind(R.id.order_num)
    TextView orderNum;
    @Bind(R.id.order_total)
    TextView orderTotal;
    @Bind(R.id.order_freight)
    TextView orderFreight;
    @Bind(R.id.order_coupon_money_ll)
    LinearLayout orderCouponMoneyLl;
    @Bind(R.id.order_coupon_money)
    TextView orderCouponMoney;
    @Bind(R.id.order_promotion_money_ll)
    LinearLayout orderPromotionMoneyLl;
    @Bind(R.id.order_promotion_money)
    TextView orderPromotionMoney;
    @Bind(R.id.order_actual_money)
    TextView orderActualMoney;
    @Bind(R.id.alipay_ll)
    LinearLayout alipayll;
    @Bind(R.id.alipay_box)
    MyCheckBox alipayBox;
    @Bind(R.id.wxpay_ll)
    LinearLayout wxpayll;
    @Bind(R.id.wxpay_box)
    MyCheckBox wxpayBox;
    @Bind(R.id.god_ll)
    LinearLayout godll;
    @Bind(R.id.god_box)
    MyCheckBox godBox;
    @Bind(R.id.d2cpay_ll)
    LinearLayout d2cpayLl;
    @Bind(R.id.d2c_balance)
    TextView d2cBalance;
    @Bind(R.id.d2cpay_box)
    MyCheckBox d2cpayBox;
    @Bind(R.id.hide_layout_tag)
    TextView hideLayoutTag;
    @Bind(R.id.pay_btn)
    TextView payBtn;
    @Bind(R.id.my_scroll_view)
    ScrollView scrollView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.tv_invoice_top)
    TextView tvInvoiceTop;
    @Bind(R.id.stages_pay_box)
    MyCheckBox stagesPayBox;
    @Bind(R.id.select_stages_ll)
    LinearLayout selectStagesLl;
    @Bind(R.id.stages_pay_ll)
    LinearLayout stagesPayLl;
    @Bind(R.id.stage_item_info)
    TextView stageItemInfo;
    @Bind(R.id.ali_pay_line)
    View aliPayLine;
    @Bind(R.id.wx_pay_line)
    View wxPayLine;
    @Bind(R.id.d2c_pay_line)
    View d2cPayLine;
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
    @Bind(R.id.red_packet_amount)
    TextView redPacketAmount;
    @Bind(R.id.toggle_button)
    Switch toggleButton;
    @Bind(R.id.red_packet_ll)
    LinearLayout redPacketLl;
    @Bind(R.id.order_red_money_ll)
    LinearLayout orderRedMoneyLl;
    @Bind(R.id.order_red_money)
    TextView orderRedMoney;
    @Bind(R.id.pay_style_ll)
    LinearLayout payStyleLL;
    @Bind(R.id.address_tip)
    LinearLayout addressTip;
    @Bind(R.id.delete_iv)
    ImageView deleteIv;
    @Bind(R.id.self_buy_ll)
    LinearLayout selfBuyLl;
    @Bind(R.id.order_self_buy_money)
    TextView orderSelfTv;
    @Bind(R.id.coupon_line)
    View couponLine;
    @Bind(R.id.red_packet_line)
    View redPacketLine;
    @Bind(R.id.actual_mount_tv)
    TextView actualMountTv;
    @Bind(R.id.large_amount_tip_tv)
    TextView largeAmountTipTv;
    @Bind(R.id.another_pay_switch)
    Switch anotherPaySwitch;
    @Bind(R.id.order_another_pay_ll)
    LinearLayout orderAnotherPayLl;
    @Bind(R.id.another_pay_btn)
    TextView anotherPayBtn;
    private int type;//创建订单类型
    //单个商品购买
    private long skuId;
    private long crowId;
    //组合商品购买
    private long productCombId;
    private long[] skuIds;
    private int num;//购买数量
    //从购物车进来购买
    private long[] cartId;
    private long[] goodPromationId;
    private long[] orderPromationId;

    private OrderConfirmBean bean;
    private OrderConfirmBean.DataEntity.AddressBean addresslistEntity;
    private OrderConfirmBean.DataEntity.OrderEntity orderEntity;
    private List<OrderConfirmBean.DataEntity.OrderEntity.ItemsEntity> orderItems, kaolaOrderItems;
    private List<Integer> kaolaItemIndex;
    private CreateOrderApi createOrderApi;
    private CollageCreateOrderApi collageCreateOrderApi;
    private PayOrderApi payOrderApi;
    private IWXAPI mApp;
    private static final int SDK_PAY_FLAG = 1;
    private Dialog loddingDialog;
    private StringBuilder designId;
    private boolean isOpen;
    private LinearLayout showLL;
    private String checkCouponCode;
    private String orderSn;
    private String anotherOrderSn;
    private double price;
    private String productName;
    private boolean isGod;
    private boolean isSupportAddress;
    private boolean isSupportStagesPay = true;
    private ArrayList<String> channels = new ArrayList<>();
    private UserBean.DataEntity.MemberEntity user;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    String resultStatus = payResult.getResultStatus();
                    switch (resultStatus) {
                        case "9000":
                            publishPayInfo(orderSn, price, "ALIPAY");
                            gotoActivityByType(1);
                            break;
                        case "6001":
                            gotoActivityByType(3);
                            break;
                        default:
                            gotoActivityByType(2);
                            break;
                    }
                    break;
                }
            }
        }
    };
    private Dialog pwdDialog;
    private boolean isApply;
    private String orderId;
    private int passwordErrorNum;
    private boolean isSellPayCharge;
    private double hbPrice;
    private int hbNum;
    private String prepay_id;
    private double[] rates = new double[3];
    private long[] skulds;
    private long[] productlds;
    private long[] designerlds;
    private boolean isPartener;
    private double partenerPrice;
    private double couponPrice;
    private String nowProvinceName, nowCityName, nowDistrictName, nowStreet, nowName;
    private boolean isPack; //是否是参团
    private int groupId;//拼团id
    private int collageId; //拼团活动id
    private boolean packBuy; //是否建团

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        user = Session.getInstance().getUserFromFile(this);
        loddingDialog = DialogUtil.createLoadingDialog(this);
        kaolaOrderItems = new ArrayList<>();
        kaolaItemIndex = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        initTitle();
        createOrderApi = new CreateOrderApi();
        payOrderApi = new PayOrderApi();
        mApp = WXAPIFactory.createWXAPI(this, Constants.WEIXINAPPID, false);
        mApp.registerApp(Constants.WEIXINAPPID);
        if (!mApp.isWXAppInstalled()) {
            wxpayll.setVisibility(View.GONE);
            wxPayLine.setVisibility(View.GONE);
        }
        type = getIntent().getIntExtra("source", 0);
        //是否是拼团购买
        isPack = getIntent().getBooleanExtra("isPack", false);
        packBuy = getIntent().getBooleanExtra("packBuy", false);
        groupId = getIntent().getIntExtra("groupId", 0);
        collageId = getIntent().getIntExtra("collageId", 0);
        if (user.getPartnerId() > 0) {
            if (!packBuy&&!isPack){
                isPartener = true;
            }
        } else {
            isPartener = false;
        }
        if (isPack || packBuy) {//走拼团接口
            collageCreateOrderApi = new CollageCreateOrderApi();
            collageCreateOrderApi.collageId = collageId;
            couponLl.setVisibility(View.GONE);
            couponLine.setVisibility(View.GONE);
            skuId = getIntent().getLongExtra("skuId", 0);
            num = getIntent().getIntExtra("num", 0);
            getOrderDataFromCollage();
            orderAnotherPayLl.setVisibility(View.GONE);
        } else {
            if (type == BUYNOW) { //立即购买
                crowId = getIntent().getLongExtra("crowId", 0);
                skuId = getIntent().getLongExtra("skuId", 0);
                num = getIntent().getIntExtra("num", 0);
                getOrderDataFromBuy();
            } else if (type == FROMCAR) {
                cartId = getIntent().getExtras().getLongArray("cartId");
                goodPromationId = getIntent().getLongArrayExtra("goodPromationId");
                orderPromationId = getIntent().getLongArrayExtra("orderPromationId");
                getOrderDataFromCart();
            } else if (type == COMBBUYNOW) {
                productCombId = getIntent().getLongExtra("productCombId", 0);
                skuIds = getIntent().getLongArrayExtra("skuId");
                num = getIntent().getIntExtra("num", 0);
                getOrderDataFromCombBuy();
            }
        }
        initListener();
    }

    private void initTitle() {
        TitleUtil.setBack(this);
        TitleUtil.setTitle(this, "确认下单");
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

        godll.setOnClickListener(new View.OnClickListener() {
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

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                createOrderApi.setRedPacket(isChecked ? 1 : 0);
                if (isChecked) {
                    orderRedMoneyLl.setVisibility(View.VISIBLE);
                    orderRedMoney.setText(String.format(getString(R.string.label_price_minus), getNumberFormat(bean.getData().getAccount().getActualRed())));
                    price = price - bean.getData().getAccount().getActualRed();
                } else {
                    orderRedMoneyLl.setVisibility(View.GONE);
                    orderRedMoney.setText(String.format(getString(R.string.label_price_minus), getNumberFormat(0)));
                    price = price + bean.getData().getAccount().getActualRed();
                }
                calculatePartner();
                orderActualMoney.setText(getString(R.string.label_no_sign_price, getNumberFormat(price - partenerPrice)));
                if (!channels.contains("HBPay") && hbNum > 0) {
                    if (isSellPayCharge) {
                        rates[0] = 0.018;
                    } else {
                        rates[0] = 0.023;
                    }
                    rates[1] = 0.045;
                    rates[2] = 0.075;
                    hbPrice = Util.calculate((int) (price * 100), hbNum, rates[hbNum / 6]);
                    hbCount.setText(Util.getNumberFormat(hbPrice) + "元" + " x " + hbNum + "期");
                }

                if (showLL == d2cpayLl) {
                    if (bean.getData().getAccount().getAvailableAmount() < price-partenerPrice) {
                        d2cBalance.setText(getString(R.string.label_money_less,
                                getNumberFormat(bean.getData().getAccount().getAvailableAmount())));
                        d2cpayBox.setChecked(false);
                        d2cpayBox.setEnabled(false);
                        d2cpayLl.setEnabled(false);
                        d2cpayLl.setBackgroundResource(R.color.enable_color);
                    } else {
                        d2cpayBox.setEnabled(true);
                        d2cpayLl.setEnabled(true);
                        d2cpayLl.setBackgroundResource(R.color.color_white);
                        d2cBalance.setText("支付后还剩 ￥" + getNumberFormat(bean.getData().getAccount().getAvailableAmount() - price+partenerPrice));
                    }
                } else {
                    if (bean.getData().getAccount() == null) {
                        return;
                    }
                    if (bean.getData().getAccount().getAvailableAmount() < price-partenerPrice) {
                        d2cpayBox.setEnabled(false);
                        d2cpayLl.setEnabled(false);
                        d2cpayLl.setBackgroundResource(R.color.enable_color);
                        d2cBalance.setText(getString(R.string.label_money_less,
                                getNumberFormat(bean.getData().getAccount().getAvailableAmount())));
                    } else {
                        d2cpayBox.setEnabled(true);
                        d2cpayLl.setEnabled(true);
                        d2cpayLl.setBackgroundResource(R.color.color_white);
                        d2cBalance.setText("￥" + getNumberFormat(bean.getData().getAccount().getAvailableAmount()));
                    }
                }
                if (price == 0) {
                    payStyleLL.setVisibility(View.GONE);
                    payBtn.setText("确定");
                } else {
                    payStyleLL.setVisibility(View.VISIBLE);
                }
            }
        });
        anotherPaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    payStyleLL.setVisibility(View.INVISIBLE);
                    payBtn.setVisibility(View.GONE);
                    anotherPayBtn.setVisibility(View.VISIBLE);
                }else {
                    payStyleLL.setVisibility(View.VISIBLE);
                    payBtn.setVisibility(View.VISIBLE);
                    anotherPayBtn.setVisibility(View.GONE);
                }
            }
        });
    }

    private void showSelectHB(TextView textView) {
        final SelectHbPop selectHbPop = new SelectHbPop(OrderBalanceActivity.this, price, isSellPayCharge, hbPrice, hbNum, textView);
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

    private void changePayStyle(int type) {
        switch (type) { //1支付宝 2 微信 3货到付款 4 微信支付 5 分期付款 6蚂蚁花呗
            case 1:
                wxpayBox.setChecked(false);
                d2cpayBox.setChecked(false);
                godBox.setChecked(false);
                stagesPayBox.setChecked(false);
                hbPayBox.setChecked(false);
                payBtn.setText("去支付宝付款");
                showLL = alipayll;
                break;
            case 2:
                alipayBox.setChecked(false);
                d2cpayBox.setChecked(false);
                godBox.setChecked(false);
                stagesPayBox.setChecked(false);
                hbPayBox.setChecked(false);
                payBtn.setText("去微信付款");
                showLL = wxpayll;
                break;
            case 3:
                alipayBox.setChecked(false);
                wxpayBox.setChecked(false);
                d2cpayBox.setChecked(false);
                stagesPayBox.setChecked(false);
                hbPayBox.setChecked(false);
                payBtn.setText("确认");
                showLL = godll;
                break;
            case 4:
                alipayBox.setChecked(false);
                wxpayBox.setChecked(false);
                godBox.setChecked(false);
                stagesPayBox.setChecked(false);
                hbPayBox.setChecked(false);
                payBtn.setText("确定");
                showLL = d2cpayLl;
                if (bean != null && bean.getData().getAccount() != null) {
                    d2cBalance.setText("支付后还剩 ￥" + getNumberFormat(bean.getData().getAccount().getAvailableAmount() - (price - partenerPrice)));
                }
                break;
            case 5:
                alipayBox.setChecked(false);
                wxpayBox.setChecked(false);
                godBox.setChecked(false);
                d2cpayBox.setChecked(false);
                hbPayBox.setChecked(false);
                payBtn.setText("确定");
                showLL = stagesPayLl;
                break;
            case 6:
                alipayBox.setChecked(false);
                wxpayBox.setChecked(false);
                godBox.setChecked(false);
                d2cpayBox.setChecked(false);
                stagesPayBox.setChecked(false);
                payBtn.setText("确定");
                showLL = hbPayLl;
                if (hbNum == 0) {
                    showSelectHB(hbCount);
                }
                selectHbRl.setVisibility(View.VISIBLE);
                break;
        }
        if (type != 4 && bean != null && bean.getData().getAccount() != null) {
            if (bean.getData().getAccount().getAvailableAmount() < (price - partenerPrice)) {
                d2cBalance.setText(getString(R.string.label_money_less,
                        getNumberFormat(bean.getData().getAccount().getAvailableAmount())));
            } else {
                d2cBalance.setText("￥" + getNumberFormat(bean.getData().getAccount().getAvailableAmount()));
            }
        }
        if (type != 6 && selectHbRl.getVisibility() == View.VISIBLE) {
            selectHbRl.setVisibility(View.GONE);
        }
    }

    /**
     * 拼团确认订单
     */
    private void getOrderDataFromCollage() {
        CollageOrderConfirmApi api = new CollageOrderConfirmApi();
        api.skuId = (int) skuId;
        api.num = num;
        api.collageId = collageId;
        if (isPack) {
            collageCreateOrderApi.groupId = groupId;
            api.groupId = groupId;
        }
        getData(api);
    }

    private void getOrderDataFromCart() {
        CartConfirmApi api = new CartConfirmApi();
        api.setCartItemIds(cartId);
        /*api.setGoodPromotionId(goodPromationId);
        api.setOrderPromotionId(orderPromationId);*/
        getData(api);
    }

    private void getOrderDataFromBuy() {
        BuyNowApi api = new BuyNowApi();
        if (crowId > 0) {
            api.setCrowdItemId((int) crowId);
        }
        api.setSkuId((int) skuId);
        api.setNum(num);
        getData(api);
    }

    private void getOrderDataFromCombBuy() {
        CombBuyApi api = new CombBuyApi();
        api.setProductCombId(productCombId);
        int length = skuIds.length;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(skuIds[i]);
            if (i != length - 1) {
                builder.append(",");
            }
        }
        api.setSkuId(builder.toString());
        api.setNum(num);
        getData(api);
    }

    private void getData(BaseApi api) {
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderConfirmBean>() {
            @Override
            public void onResponse(final OrderConfirmBean response) {
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        publishThird(response);
                    }
                }, 1000);
                bean = response;
                createOrderApi.setTempId(response.getData().getOrder().getTempId());
                if (collageCreateOrderApi != null) {
                    collageCreateOrderApi.tempId = response.getData().getOrder().getTempId();
                }
                int cartItemSize = response.getData().getOrder().getCartItemIds().size();
                if (cartItemSize > 0) {
                    StringBuilder cartItemIds = new StringBuilder();
                    for (int i = 0; i < cartItemSize; i++) {
                        cartItemIds.append(response.getData().getOrder().getCartItemIds().get(i));
                        if (i != cartItemSize - 1) {
                            cartItemIds.append(",");
                        }
                    }
                    createOrderApi.setCartItemIds(cartItemIds.toString());
                }
                addresslistEntity = bean.getData().getAddresses();
                orderEntity = bean.getData().getOrder();
                orderItems = bean.getData().getOrder().getItems();
                limitPayMode();
                setData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(OrderBalanceActivity.this, Util.checkErrorType(error));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                }, 1000);
            }
        });
    }

    private void limitPayMode() {
        List<String> list = new ArrayList<>();
        list.add("ALIPAY");
        list.add("WXAPPPAY");
        list.add("WALLET");
        list.add("MIMEPAY");
        list.add("HBPAY");
        if (!Util.isEmpty(bean.getData().getOrder().getPayParams())) {
            String[] styles = bean.getData().getOrder().getPayParams().split(",");
            int size = styles.length;
            for (int i = 0; i < 5; i++) {
                boolean isContain = false;
                for (int j = 0; j < size; j++) {
                    if (list.get(i).equals(styles[j])) {
                        isContain = true;
                        break;
                    }
                }
                if (!isContain) {
                    channels.add(list.get(i));
                    checkChannel(list.get(i));
                }
            }
        }
    }

    private void publishThird(OrderConfirmBean bean) {
        Order order = Order.createOrder(bean.getData().getOrder().getOrderSn(), (int) bean.getData().getOrder().getTotalPay() * 100, "CNY");
        for (OrderConfirmBean.DataEntity.OrderEntity.ItemsEntity itemsEntity : bean.getData().getOrder().getItems()) {
            order.addItem("", itemsEntity.getProductName(), (int) itemsEntity.getProductPrice() * 100, itemsEntity.getQuantity());
        }
        TalkingDataAppCpa.onPlaceOrder(String.valueOf(user.getMemberId()), order);
        ZampAppAnalytics.onOrderSubmit(this, String.valueOf(user.getMemberId()), bean.getData().getOrder().getOrderSn(), (float) (bean.getData().getOrder().getTotalPay()),
                "RMB", false);
        int size = bean.getData().getOrder().getItems().size();
        StringBuilder ids = new StringBuilder();
        for (int i = 0; i < size; i++) {
            ids.append(bean.getData().getOrder().getItems().get(i).getProductId());
            ids.append(",");
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("stock", "1"); //是否有库存，1：有；0：无
        if (ids.toString().length() > 1) {
            params.put("productId_list", ids.toString().substring(0, ids.toString().length() - 1)); //商品ID列表
        }
        ZampAppAnalytics.onRemarketingEvent(this, "ad-order-smt ", params);

        HashMap<String, String> param2 = new HashMap<>();
        param2.put("info", bean.getData().getOrder().getOrderSn() + "," + bean.getData().getOrder().getTotalPay()); //订单ID和订单金额，二者以西文逗号分隔拼接成一个字符串,订单金额中请勿包含西文逗号
        ZampAppAnalytics.onConversion(this, "99d459f555cdf22d9f05d85eb262952f", param2);
    }

    private void setData() {
        if (addresslistEntity != null) {
            createOrderApi.setAddressId(addresslistEntity.getId());
            if (collageCreateOrderApi != null) {
                collageCreateOrderApi.addressId = addresslistEntity.getId();
            }
            addressName.setText(addresslistEntity.getName());
            nowName = addresslistEntity.getName();
            addressPhone.setText(addresslistEntity.getMobile());
            nowProvinceName = addresslistEntity.getProvince();
            nowCityName = addresslistEntity.getCity();
            nowDistrictName = addresslistEntity.getDistrict();
            nowStreet = addresslistEntity.getStreet();
            addressInfo.setText(addresslistEntity.getProvince() + addresslistEntity.getCity()
                    + addresslistEntity.getDistrict() + addresslistEntity.getStreet());
            if (addresslistEntity.getLatitude() == 0 || addresslistEntity.getLongitude() == 0) {
                LoctionUtil.getLatLonPoint(this, addressInfo.getText().toString(), addresslistEntity.getCity(), new GeocodeSearch.OnGeocodeSearchListener() {
                    @Override
                    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

                    }

                    @Override
                    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                        if (geocodeResult.getGeocodeAddressList() != null && geocodeResult.getGeocodeAddressList().size() > 0) {
                            double lat = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLatitude();
                            double logt = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLongitude();
                            createOrderApi.setLatitude(lat);
                            createOrderApi.setLongitude(logt);
                            if (collageCreateOrderApi != null) {
                                collageCreateOrderApi.latitude = lat;
                                collageCreateOrderApi.longitude = logt;
                            }
                        }
                    }
                });
            }
            if (addressInfo.getText().toString().trim().matches("(台湾|香港|澳门|国外)(\\S|\\s)+")) {
                isSupportAddress = true;
                addressTip.setVisibility(View.VISIBLE);
            }
        } else {
            addressLL.setVisibility(View.GONE);
            editAddress.setVisibility(View.VISIBLE);
        }
        int size = orderItems.size();
        View content;
        StringBuilder skuId = new StringBuilder();
        StringBuilder quantity = new StringBuilder();
        StringBuilder price = new StringBuilder();
        StringBuilder goodPromotionId = new StringBuilder();
        StringBuilder orderPromotionId = new StringBuilder();
        StringBuilder productCombId = new StringBuilder();
        StringBuilder crowdItemId = new StringBuilder();
        designId = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (skulds == null) {
                skulds = new long[size];
            }
            if (productlds == null) {
                productlds = new long[size];
            }
            if (designerlds == null) {
                designerlds = new long[size];
            }
            if (orderItems.get(i).getProductSource().equals("KAOLA")) {//这个商品是考拉的商品
                kaolaOrderItems.add(orderItems.get(i));//放入考拉的list
                kaolaItemIndex.add(i);//把考拉的下标记一下
            }
            skulds[i] = orderItems.get(i).getSkuId();
            productlds[i] = orderItems.get(i).getProductId();
            designerlds[i] = orderItems.get(i).getDesignerId();
            skuId.append(orderItems.get(i).getSkuId());
            quantity.append(orderItems.get(i).getQuantity());
            price.append(orderItems.get(i).getProductPrice());
            goodPromotionId.append(orderItems.get(i).getGoodPromotionId());
            orderPromotionId.append(orderItems.get(i).getOrderPromotionId());
            productCombId.append(orderItems.get(i).getProductCombId());
            crowdItemId.append(orderItems.get(i).getCrowdItemId());
            designId.append(orderItems.get(i).getDesignerId());
            if (i != size - 1) {
                skuId.append(",");
                quantity.append(",");
                price.append(",");
                goodPromotionId.append(",");
                orderPromotionId.append(",");
                productCombId.append(",");
                crowdItemId.append(",");
                designId.append(",");
            }
            content = LayoutInflater.from(this).inflate(R.layout.layout_order_item_content, null);
            ImageView orderItemIv = (ImageView) content.findViewById(R.id.content_iv);
            TextView orderItemInfo = (TextView) content.findViewById(R.id.good_info);
            TextView itemProductPrice = (TextView) content.findViewById(R.id.good_price);
            TextView itemOrgistProductPrice = (TextView) content.findViewById(R.id.good_drop_price);
            TextView itemProductNum = (TextView) content.findViewById(R.id.good_num);
            TextView orderItemSize = (TextView) content.findViewById(R.id.good_size);
            TextView goodAfter = (TextView) content.findViewById(R.id.good_after);
            if (orderItems.get(i).getIsCod() == 0) {
                //orderItemTag.setVisibility(View.VISIBLE);
                isGod = true;
            } else {
                //orderItemTag.setVisibility(View.GONE);
            }
            if (orderItems.get(i).getAfter() == 0) {
                goodAfter.setVisibility(View.VISIBLE);
            } else {
                goodAfter.setVisibility(View.GONE);
            }
            UniversalImageLoader.displayImage(this, Util.getD2cPicUrl(orderItems.get(i).getProductImg()), orderItemIv);

            if (orderItems.get(i).getFlashPromotionId() != null && orderItems.get(i).getFlashPromotionId() > 0) {
                StringBuilder builder = new StringBuilder();
                if (!Util.isEmpty(orderItems.get(i).getProductName())) {
                    builder.append("   " + orderItems.get(i).getProductName());
                }
                SpannableString sb = new SpannableString(builder.toString());
                Drawable d = getResources().getDrawable(R.mipmap.icon_shopcart_xsg);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                sb.setSpan(new CenterAlignImageSpan(d), 0, 1, ImageSpan.ALIGN_BASELINE);
                orderItemInfo.setText(sb);
            } else {
                orderItemInfo.setText(orderItems.get(i).getProductName());
            }
            if (orderItems.get(i).getPromotionPrice() > 0) {
                itemProductPrice.setText("¥" + getNumberFormat(orderItems.get(i).getProductPrice() - orderItems.get(i).getPromotionPrice() / orderItems.get(i).getQuantity()));
                itemOrgistProductPrice.setText("¥" + getNumberFormat(orderItems.get(i).getProductPrice()));
                itemOrgistProductPrice.getPaint().setAntiAlias(true);
                itemOrgistProductPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint
                        .ANTI_ALIAS_FLAG);
            } else {
                itemProductPrice.setText("¥" + getNumberFormat(orderItems.get(i).getProductPrice()));
            }
            itemProductNum.setText("X" + orderItems.get(i).getQuantity());
            orderItemSize.setText(orderItems.get(i).getColor() + " " + orderItems.get(i).getSize());
            orderListContent.addView(content);
        }
        if (kaolaOrderItems.size() > 0) {//说明有考拉的商品,需要去请求考拉的接口
            getKaoLaInfo();
        } else {
            setKaolaParams();
        }
        createOrderApi.setSkuId(skuId.toString());
        if (collageCreateOrderApi != null) {
            collageCreateOrderApi.skuId = skuId.toString();
            collageCreateOrderApi.quantity = quantity.toString();
        }
        createOrderApi.setQuantity(quantity.toString());
        //createOrderApi.setCrowdItemId(crowdItemId.toString());
        createOrderApi.setGoodPromotionId(goodPromotionId.toString());
        createOrderApi.setOrderPromotionId(orderPromotionId.toString());
        createOrderApi.setProductCombId(productCombId.toString());
        //createOrderApi.setPrice(price.toString());
        if (bean.getData().getCoupons() != null && bean.getData().getCoupons().size() >= 0) {
            couponNum.setVisibility(View.VISIBLE);
            couponNum.setText(bean.getData().getCoupons().size() + "张可用");
        }
        orderNum.setText("共计" + orderEntity.getItemTotal() + "件");
        orderTotal.setText(getString(R.string.label_price, getNumberFormat(orderEntity.getTotalAmount() + orderEntity.getPromotionAmount())));
        if (orderEntity.getShippingRates() > 0) {
            orderFreight.setText(String.format(getString(R.string.label_price_plus), getNumberFormat(orderEntity.getShippingRates())));
        } else {
            orderFreight.setText(String.format(getString(R.string.label_price), getNumberFormat(orderEntity.getShippingRates())));
        }
        if (orderEntity.getPromotionAmount() > 0) {
            orderPromotionMoneyLl.setVisibility(View.VISIBLE);
            orderPromotionMoney.setText(String.format(getString(R.string.label_price_minus), getNumberFormat(orderEntity.getPromotionAmount())));
        } else {
            orderPromotionMoneyLl.setVisibility(View.GONE);
            orderPromotionMoney.setText(String.format(getString(R.string.label_price), getNumberFormat(orderEntity.getPromotionAmount())));
        }
        if (orderEntity.getCouponAmount() > 0) {
            orderCouponMoneyLl.setVisibility(View.VISIBLE);
            orderCouponMoney.setText(String.format(getString(R.string.label_price_minus), getNumberFormat(orderEntity.getCouponAmount())));
        } else {
            orderCouponMoneyLl.setVisibility(View.GONE);
            orderCouponMoney.setText(String.format(getString(R.string.label_price), getNumberFormat(orderEntity.getCouponAmount())));
        }
        orderActualMoney.setText(getString(R.string.label_no_sign_price, getNumberFormat(orderEntity.getTotalPay())));
        this.price = orderEntity.getTotalPay();
        String mode = D2CApplication.mSharePref.getSharePrefString(SharePrefConstant.PAY_MODE, "");
        if (!Util.isEmpty(mode) && !"FREEPAY".equals(mode)) {
            if (mode.equals("ALIPAY")) {
                alipayBox.setChecked(true);
                payBtn.setText(R.string.label_go_alipay);
                showLL = alipayll;
            } else if (mode.equals("WXAPPPAY")) {
                wxpayBox.setChecked(true);
                payBtn.setText(R.string.label_go_weixinpay);
                showLL = wxpayll;
            } else if (mode.equals("COD")) {
                if (!isGod && !isSupportAddress) {
                    godBox.setChecked(true);
                    payBtn.setText(R.string.action_ok);
                    showLL = godll;
                } else {
                    showLL = alipayll;
                    alipayBox.setChecked(true);
                    payBtn.setText(R.string.label_go_alipay);
                }
            } else if (mode.equals("WALLET")) {
                if (bean.getData().getAccount() != null && bean.getData().getAccount().getAvailableAmount() > orderEntity.getTotalPay()) {
                    d2cpayBox.setChecked(true);
                    payBtn.setText(R.string.action_ok);
                    showLL = d2cpayLl;
                } else {
                    alipayBox.setChecked(true);
                    payBtn.setText(R.string.label_go_alipay);
                    showLL = alipayll;
                }
            } else if (mode.equals("MIMEPAY")) {
                stagesPayBox.setChecked(true);
                payBtn.setText(R.string.action_ok);
                showLL = stagesPayLl;
            } else if (mode.equals("HBPAY")) {
                hbPayBox.setChecked(true);
                payBtn.setText("确定");
                showLL = hbPayLl;
            }
            clickOpen(showLL);
        } else {
            showLL = alipayll;
            alipayBox.setChecked(true);
            payBtn.setText(R.string.label_go_alipay);
            if (isGod || isSupportAddress) {
                godll.setVisibility(View.GONE);
            }
        }
        if (bean.getData().getAccount() != null && bean.getData().getAccount().isSetPassword()) {
            if (bean.getData().getAccount().getAvailableAmount() < orderEntity.getTotalPay()) {
                /*if (bean.getData().getAccount().getAvailableAmount() == 0) {
                    d2cpayLl.setVisibility(View.GONE);
                } else {
                    d2cBalance.setText(getString(R.string.label_money_less,
                            getNumberFormat(bean.getData().getAccount().getAvailableAmount())));
                    d2cpayBox.setEnabled(false);
                    d2cpayLl.setEnabled(false);
                    d2cpayLl.setBackgroundResource(R.color.enable_color);
                }*/
                d2cBalance.setText(getString(R.string.label_money_less,
                        getNumberFormat(bean.getData().getAccount().getAvailableAmount())));
                d2cpayBox.setEnabled(false);
                d2cpayLl.setEnabled(false);
                d2cpayLl.setBackgroundResource(R.color.enable_color);
            } else {
                d2cBalance.setText(getString(R.string.label_price, getNumberFormat(bean.getData().getAccount().getAvailableAmount())));
            }
        } else {
            d2cpayLl.setVisibility(View.GONE);
            d2cPayLine.setVisibility(View.GONE);
        }
        if (isPartener) {
            selfBuyLl.setVisibility(View.VISIBLE);
        } else {
            selfBuyLl.setVisibility(View.GONE);
        }
        if (bean.getData().getCoupons() != null && bean.getData().getCoupons().size() > 0) {
            selectCoupon(true, bean.getData().getCoupons().get(0).getCode(), bean.getData().getCoupons().get(0).getAmount(), bean.getData().getCoupons().get(0).getType());
        } else {
            if (this.price > 10000 && orderEntity.getItemTotal() >= 2) {
                RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) actualMountTv.getLayoutParams();
                rl.setMargins(0, ScreenUtil.dip2px(16), 0, ScreenUtil.dip2px(6));
                largeAmountTipTv.setVisibility(View.VISIBLE);
            }
            selectCoupon(false, "", 0, "");
        }
        if (bean.getData().getAccount() != null && !isPack && !packBuy) {
            if (!bean.getData().getAccount().isRed() || bean.getData().getAccount().getRedAmount() == 0) {
                redPacketLl.setVisibility(View.GONE);
                redPacketLine.setVisibility(View.GONE);
            } else {
                redPacketLl.setVisibility(View.VISIBLE);
                redPacketLine.setVisibility(View.VISIBLE);
                bean.getData().getAccount().setActualRed(Math.min(bean.getData().getAccount().getRedAmount(), this.price - orderEntity.getShippingRates()));
                setRedTv();
            }
        }
        if (this.price == 0) {
            payStyleLL.setVisibility(View.GONE);
            payBtn.setText("确定");
        } else {
            payStyleLL.setVisibility(View.VISIBLE);
        }
    }

    private void setKaolaParams() {
        StringBuilder wareHouseId = new StringBuilder();
        StringBuilder wareHouseName = new StringBuilder();
        StringBuilder taxAmount = new StringBuilder();
        for (int i = 0; i < orderItems.size(); i++) {
            wareHouseId.append(orderItems.get(i).getWareHouseId());
            if (Util.isEmpty(orderItems.get(i).getWareHouseName())) {
                wareHouseName.append("0");
            } else {
                wareHouseName.append(orderItems.get(i).getWareHouseName());
            }
            taxAmount.append(orderItems.get(i).getTaxAmount());
            if (i != orderItems.size() - 1) {
                wareHouseId.append(",");
                wareHouseName.append(",");
                taxAmount.append(",");
            }
        }
        createOrderApi.setWarehouseName(wareHouseName.toString());
        createOrderApi.setWarehouseId(wareHouseId.toString());
        createOrderApi.setTaxAmount(taxAmount.toString());
    }

    private void getKaoLaInfo() {
        if (kaolaOrderItems == null || kaolaOrderItems.size() == 0) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        StringBuilder kaolaGoodsId = new StringBuilder();
        StringBuilder kaolaSkuId = new StringBuilder();
        StringBuilder kaolaBuyAmounts = new StringBuilder();
        StringBuilder kaolaChannelSalePrices = new StringBuilder();

        for (int i = 0; i < kaolaOrderItems.size(); i++) {
            kaolaGoodsId.append(kaolaOrderItems.get(i).getProductSn());
            kaolaSkuId.append(kaolaOrderItems.get(i).getSkuSn());
            kaolaBuyAmounts.append(kaolaOrderItems.get(i).getQuantity());
            kaolaChannelSalePrices.append((kaolaOrderItems.get(i).getTotalPrice() - kaolaOrderItems.get(i).getPromotionAmount() - kaolaOrderItems.get(i).getOrderPromotionAmount()) / kaolaOrderItems.get(i).getQuantity());
            if (i != kaolaOrderItems.size() - 1) {
                kaolaGoodsId.append(",");
                kaolaSkuId.append(",");
                kaolaBuyAmounts.append(",");
                kaolaChannelSalePrices.append(",");
            }
        }
        KaolaConfirmOrderApi kaolaCreatOrderApi = new KaolaConfirmOrderApi();
        kaolaCreatOrderApi.setGoodsId(kaolaGoodsId.toString());
        kaolaCreatOrderApi.setSkuId(kaolaSkuId.toString());
        kaolaCreatOrderApi.setBuyAmount(kaolaBuyAmounts.toString());
        kaolaCreatOrderApi.setChannelSalePrice(kaolaChannelSalePrices.toString());
        kaolaCreatOrderApi.setName(nowName);
        kaolaCreatOrderApi.setProvinceName(nowProvinceName);
        kaolaCreatOrderApi.setCityName(nowCityName);
        kaolaCreatOrderApi.setDistrictName(nowDistrictName);
        kaolaCreatOrderApi.setAddress(nowStreet);
        D2CApplication.httpClient.loadingRequest(kaolaCreatOrderApi, new BeanRequest.SuccessListener<KaoLaConfirmBean>() {
            @Override
            public void onResponse(KaoLaConfirmBean response) {
                progressBar.setVisibility(View.GONE);
                if (response.getData().getKaola() != null && response.getData().getKaola().getRecCode() == 200) {//当返回码为200时才代表着考拉那边返回成功
                    for (int i = 0; i < response.getData().getKaola().getOrderForm().getPackageList().size(); i++) {
                        KaoLaConfirmBean.DataBean.KaolaBean.OrderFormBean.PackageListBean packageListBean = response.getData().getKaola().getOrderForm().getPackageList().get(i);
                        for (int j = 0; j < packageListBean.getGoodsList().size(); j++) {
                            KaoLaConfirmBean.DataBean.KaolaBean.OrderFormBean.PackageListBean.GoodsListBean goodsListBean = packageListBean.getGoodsList().get(j);
                            KaoLaConfirmBean.DataBean.KaolaBean.OrderFormBean.PackageListBean.WarehouseBean warehouseBean = packageListBean.getWarehouse();
                            for (int k = 0; k < orderItems.size(); k++) {
                                if (orderItems.get(k).getSkuSn().equals(goodsListBean.getSkuId())) {
                                    orderItems.get(k).setWareHouseId(warehouseBean.getWarehouseId());
                                    orderItems.get(k).setWareHouseName(warehouseBean.getWarehouseName());
                                    orderItems.get(k).setTaxAmount(String.valueOf(goodsListBean.getGoodsTaxAmount()));
                                }
                            }
                        }
                    }
                    setKaolaParams();
                } else {
                    if (response.getData().getKaola() != null) {
                        if (response.getData().getKaola() != null) {
                            final SimpleDialog simpleDialog = new SimpleDialog(OrderBalanceActivity.this);
                            simpleDialog.show();
                            simpleDialog.setTitle("提醒")
                                    .hasRightButtom(false)
                                    .setTvMessage(response.getData().getKaola().getRecMeg())
                                    .setLeftText("返回")
                                    .setLeftClick(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            finish();
                                        }
                                    });
                            if ((response.getData().getKaola().getRecCode() == -107 && response.getData().getKaola().getSubCode() == -211)) {//地址校验不成功,弹出弹窗并且显示显示修改地址按钮
                                simpleDialog.setRightText("修改地址")
                                        .hasRightButtom(true)
                                        .setTvMessage(getResources().getString(R.string.label_kaola_address))
                                        .setRightClick(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(OrderBalanceActivity.this, ReceiveAddressActivity.class);
                                                intent.putExtra("type", 1);
                                                startActivityForResult(intent, Constants.RequestCode.SELECT_ADDRESS);
                                                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                                                simpleDialog.dismiss();
                                            }
                                        });
                            } else if ((response.getData().getKaola().getRecCode() == -107 && response.getData().getKaola().getSubCode() == -3)) {//商品价格小于供货价格
                                simpleDialog.setTvMessage(getResources().getString(R.string.label_kaola_price_low));
                            }
                        } else {
                            Util.showToast(OrderBalanceActivity.this, "数据异常,等会儿来下单哦~");
                            finish();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(OrderBalanceActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void setRedTv() {
        String redString = "使用红包抵用" + Util.getNumberFormat(bean.getData().getAccount().getActualRed()) + "元";
        if (toggleButton.isChecked()) {
            orderRedMoneyLl.setVisibility(View.VISIBLE);
            orderRedMoney.setText(String.format(getString(R.string.label_price_minus), getNumberFormat(bean.getData().getAccount().getActualRed())));
        } else {
            orderRedMoneyLl.setVisibility(View.GONE);
            orderRedMoney.setText(String.format(getString(R.string.label_price_minus), getNumberFormat(0)));
        }
        SpannableString spannableString = new SpannableString(redString);
        ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.color_red));
        spannableString.setSpan(span, 6, redString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        redPacketAmount.setText(spannableString);
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

    @OnClick({R.id.delete_iv, R.id.add_address_ll, R.id.coupon_ll, R.id.hide_layout_tag, R.id.pay_btn, R.id.address_ll, R.id.invoice_layout,R.id.another_pay_btn})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.delete_iv:
                addressTip.setVisibility(View.GONE);
                break;
            case R.id.add_address_ll:
                Intent i = new Intent(this, EditAddressActivity.class);
                i.putExtra("type", 2);//下单页面进编辑地址
                startActivityForResult(i, Constants.RequestCode.ADDRESS);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
            case R.id.coupon_ll: //选择优惠券
                intent = new Intent(this, SelectCouponActivity.class);
                intent.putExtra("coupon", (Serializable) bean.getData().getCoupons());
                intent.putExtra("discoupon", (Serializable) bean.getData().getDisableCoupons());
                intent.putExtra("reason", bean.getData().getReason());
                if (!Util.isEmpty(checkCouponCode)) {
                    intent.putExtra("code", checkCouponCode);
                }
                startActivityForResult(intent, Constants.RequestCode.SELECT_COUPON);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
            case R.id.hide_layout_tag:
                clickOpen(showLL);
                break;
            case R.id.pay_btn:
                if (bean == null) {
                    return;
                }
                String markStr = mark.getText().toString().trim();
                if (createOrderApi.getAddressId() == 0) {
                    Util.showToast(this, getString(R.string.msg_append_address));
                    return;
                }
                if (markStr.length() > 100) {
                    Util.showToast(this, getString(R.string.msg_mark_error));
                    return;
                }
                if (!Util.isEmpty(markStr)) {
                    createOrderApi.setMemo(markStr);
                }
                HashMap<String, String> map = new HashMap<>();
                if (alipayBox.isChecked() && this.price > 0) {
                    payOrderApi.setPaymentType("ALIPAY");
                    map.put("立即付款_支付方式", "支付宝");
                } else if (wxpayBox.isChecked() && this.price > 0) {
                    map.put("立即付款_支付方式", "微信");
                    if (!Util.isEmpty(prepay_id)) {
                        wxPay(prepay_id);
                        return;
                    }
                    payOrderApi.setPaymentType("WXAPPPAY");
                } else if (godBox.isChecked() || this.price <= 0) {
                    payOrderApi.setPaymentType("FREEPAY");
                } else if (stagesPayBox.isChecked() && this.price > 0) {
                    payOrderApi.setPaymentType("MIMEPAY");
                    map.put("立即付款_支付方式", "米么");
                } else if (hbPayBox.isChecked() && this.price > 0) {
                    payOrderApi.setPaymentType("ALIPAY");
                    if (hbNum == 0) {
                        Util.showToast(OrderBalanceActivity.this, "请先选择期数");
                        return;
                    }
                    map.put("立即付款_支付方式", "花呗");
                } else { //钱包支付
                    payOrderApi.setPaymentType("WALLET");
                    map.put("立即付款_支付方式", "钱包");
                    showDialog();
                    return;
                }
                stat("下单", "立即付款", map);
                loddingDialog.show();
                createOrder();
                break;
            case R.id.another_pay_btn:
                if (bean == null) {
                    return;
                }
                String m = mark.getText().toString().trim();
                if (createOrderApi.getAddressId() == 0) {
                    Util.showToast(this, getString(R.string.msg_append_address));
                    return;
                }
                if (m.length() > 100) {
                    Util.showToast(this, getString(R.string.msg_mark_error));
                    return;
                }
                if (!Util.isEmpty(m)) {
                    createOrderApi.setMemo(m);
                }
                payOrderApi.setPaymentType("ALIPAY");
                loddingDialog.show();
                createAnotherPayOrder();
                break;
            case R.id.address_ll:
                intent = new Intent(this, ReceiveAddressActivity.class);
                intent.putExtra("type", 1);
                startActivityForResult(intent, Constants.RequestCode.SELECT_ADDRESS);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
            case R.id.invoice_layout:
                String invoiceTopContent = tvInvoiceTop.getText().toString();
                intent = new Intent(this, CreateInvoiceActivity.class);
                if (!Util.isEmpty(invoiceTopContent) && !invoiceTopContent.equals(getString(R.string.label_not_need))) {
                    intent.putExtra("content", invoiceTopContent);
                }
                startActivityForResult(intent, Constants.RequestCode.INVOICE);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
        }
    }

    private void queryWallet(final String orderId) {
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.POST);
        api.setInterPath(Constants.QUERY_MEME_WALLET_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MeWalletBean>() {
            @Override
            public void onResponse(MeWalletBean response) {
                payBtn.setEnabled(true);
                String code = response.getData().getCode();
                switch (code) {
                    case "1001":
                    case "1002":
                    case "1003":
                    case "1004":
                    case "1006":
                    case "1005":
                        //MIMO分期,sdk删除了
//                        stagesPay(orderId);
                        break;
                    case "1007":
                        loddingDialog.dismiss();
                        Toast.makeText(OrderBalanceActivity.this, "您的申请在审核中,请耐心等待", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                payBtn.setEnabled(true);
                loddingDialog.dismiss();
                Util.showToast(OrderBalanceActivity.this, Util.checkErrorType(error));
            }
        });
    }

//    private void stagesPay(final String orderId) {
//        /*AuctionPayApi api = new AuctionPayApi();
//        api.setProductId(orderId);
//        api.setPaymentType("MIMEPAY");
//        api.setOrderType("order");
//        api.bank = Session.getInstance().getUserFromFile(this).getLoginCode();*/
//        payOrderApi.setPaymentType("MIMEPAY");
//        payOrderApi.setLoginCode(Session.getInstance().getUserFromFile(this).getLoginCode());
//        D2CApplication.httpClient.loadingRequest(payOrderApi, new BeanRequest.SuccessListener<UserSignBean>() {
//            @Override
//            public void onResponse(UserSignBean response) {
//                loddingDialog.dismiss();
//                String userId = response.getDatas().getThdUserId();
//                if (Util.isEmpty(userId))
//                    return;
//                if (BuildConfig.DEBUG) {
//                    WalletManager.enableDebug();
//                }
//                IWalletCore mWalletCore = WalletManager.createWalletCore(OrderBalanceActivity.this);
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
//                            EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.REFRESH_CART));
//                            EventBus.getDefault().post(bean);
//                            Intent intent = new Intent(OrderBalanceActivity.this, MyOrderActivity.class);
//                            startActivity(intent);
//                            finish();
//                            overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
//                        } else {
//                            isApply = true;
//                            gotoActivityByType(2);
//                        }
//                    }
//                };
//                mWalletCore.useWallet(userId, orderSn, iWalletListener);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                loddingDialog.dismiss();
//                Util.showToast(OrderBalanceActivity.this, Util.checkErrorType(error));
//            }
//        });
//    }

    private void clickOpen(View view) {
        isOpen = !isOpen;
        if (isOpen) {
            showView(view, View.GONE);
            hideLayoutTag.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.tab_all_down_b, 0, 0, 0);
            hideLayoutTag.setText(R.string.label_more_payment);
        } else {
            showView(view, View.VISIBLE);
            hideLayoutTag.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.tab_all_up_b, 0, 0, 0);
            hideLayoutTag.setText(R.string.label_close_it);
        }
    }

    private void showView(View except, int vis) {
        except.setVisibility(View.VISIBLE);
        if (alipayll != except) {
            if (channels.contains("ALIPAY")) {
                alipayll.setVisibility(View.GONE);
                aliPayLine.setVisibility(View.GONE);
            } else {
                alipayll.setVisibility(vis);
                aliPayLine.setVisibility(vis);
            }
        } else {
            aliPayLine.setVisibility(View.VISIBLE);
        }
        if (wxpayll != except) {
            if (!mApp.isWXAppInstalled() || channels.contains("WXAPPPAY")) {
                wxpayll.setVisibility(View.GONE);
                wxPayLine.setVisibility(View.GONE);
            } else {
                wxpayll.setVisibility(vis);
                wxPayLine.setVisibility(vis);
            }
        } else {
            wxPayLine.setVisibility(View.VISIBLE);
        }
        if (godll != except) {
            if (isGod || isSupportAddress) {
                godll.setVisibility(View.GONE);
            } else {
                // godll.setVisibility(vis);
            }
        }
        if (d2cpayLl != except) {
            if (vis == View.VISIBLE) {
                if (bean != null && bean.getData().getAccount() != null && bean.getData().getAccount().getAvailableAmount() > 0 && bean.getData().getAccount().isSetPassword()) {
                    d2cpayLl.setVisibility(View.VISIBLE);
                    d2cPayLine.setVisibility(View.VISIBLE);
                }
                if (channels.contains("WALLET")) {
                    d2cpayLl.setVisibility(View.GONE);
                    d2cPayLine.setVisibility(View.GONE);
                }
            } else {
                d2cpayLl.setVisibility(vis);
                d2cPayLine.setVisibility(vis);
            }
        } else {
            d2cPayLine.setVisibility(View.VISIBLE);
        }
        if (stagesPayLl != except) {
            if (channels.contains("MIMEPAY")) {
                stagesPayLl.setVisibility(View.GONE);
                stagesPayLine.setVisibility(View.GONE);
            } else {
                stagesPayLl.setVisibility(vis);
                stagesPayLl.setVisibility(vis);
            }
        } else {
            stagesPayLine.setVisibility(View.VISIBLE);
        }
        if (hbPayLl != except) {
            if (channels.contains("HBPAY")) {
                hbPayLl.setVisibility(View.GONE);
                hbPayLine.setVisibility(View.GONE);
                selectHbRl.setVisibility(View.GONE);
            } else {
                hbPayLl.setVisibility(vis);
                hbPayLine.setVisibility(vis);
                selectHbRl.setVisibility(vis);
            }
        } else {
            hbPayLine.setVisibility(View.VISIBLE);
            selectHbRl.setVisibility(View.VISIBLE);
        }
    }

    private void createAnotherPayOrder(){
        if (!Util.isEmpty(anotherOrderSn)){
            loddingDialog.dismiss();
            toAnotherPayActivity();
            return;
        }
        int parentId = D2CApplication.mSharePref.getSharePrefInteger(SharePrefConstant.PARENT_ID, 0);
        if (parentId > 0) {
            createOrderApi.setParent_id(parentId);
        }
        anotherPayBtn.setEnabled(false);
        D2CApplication.httpClient.loadingRequest(createOrderApi, new BeanRequest.SuccessListener<CreateOrderBean>() {
            @Override
            public void onResponse(CreateOrderBean response) {
                anotherPayBtn.setEnabled(true);
                loddingDialog.dismiss();
                anotherOrderSn = response.getData().getOrder().getOrderSn();
                toAnotherPayActivity();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                anotherPayBtn.setEnabled(true);
                loddingDialog.dismiss();
                Util.showToast(OrderBalanceActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void toAnotherPayActivity(){
        Intent intent=new Intent(this,AnotherPayActivity.class);
        intent.putExtra("orderSn",anotherOrderSn);
        startActivity(intent);
    }

    private void createOrder() {
        if (!Util.isEmpty(orderSn)) {
            payOrder(orderSn);
            return;
        }
        int parentId = D2CApplication.mSharePref.getSharePrefInteger(SharePrefConstant.PARENT_ID, 0);
        if (parentId > 0) {
            createOrderApi.setParent_id(parentId);
            if (collageCreateOrderApi != null) {
                collageCreateOrderApi.parent_id = parentId;
            }
        }
        payBtn.setEnabled(false);
        if (isPack || packBuy) {
            collageCreateOrder();
        } else {
            D2CApplication.httpClient.loadingRequest(createOrderApi, new BeanRequest.SuccessListener<CreateOrderBean>() {
                @Override
                public void onResponse(CreateOrderBean response) {
                    payBtn.setEnabled(true);
                    EventBus.getDefault().post(new ActionBean(Constants.ActionType.REFRESH_CART));
                    final String ordersn = response.getData().getOrder().getOrderSn();
                    orderSn = ordersn;
                    orderId = response.getData().getOrder().getId() + "";
                    payOrderApi.setOrderType("ORDER");
                    payOrderApi.setOrderSn(ordersn);
                    if (stagesPayBox.isChecked()) {
                        productName = response.getData().getOrder().getBillSubject();
                        //stagesPay(orderId);
                        queryWallet(orderId);
                        return;
                    }
                    final double totalPrice = response.getData().getOrder().getTotalPay();
                    final String subject = response.getData().getOrder().getBillSubject();
                    productName = subject;
                    productName = productName.replace("&", "a");
                    price = totalPrice;
                    if (Util.isEmpty(subject)) {
                        loddingDialog.dismiss();
                        EventBus.getDefault().post(new ActionBean(Constants.ActionType.REFRESH_CART));
                        Util.showToast(OrderBalanceActivity.this, "订单支付失败,请去订单中支付");
                        payBtn.setEnabled(true);
                        return;
                    }
                    payOrder(orderSn);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    payBtn.setEnabled(true);
                    loddingDialog.dismiss();
                    Util.showToast(OrderBalanceActivity.this, Util.checkErrorType(error));
                }
            });
        }
    }

    private void collageCreateOrder() {
        D2CApplication.httpClient.loadingRequest(collageCreateOrderApi, new BeanRequest.SuccessListener<PackOrderBean>() {
            @Override
            public void onResponse(PackOrderBean response) {
                final String ordersn = response.getData().getCollageOrder().getOrderSn();
                orderSn = ordersn;
                orderId = response.getData().getCollageOrder().getId() + "";
                groupId = response.getData().getCollageOrder().getGroupId();
                payOrderApi.setOrderType("COLLAGE");
                payOrderApi.setOrderSn(ordersn);
                if (stagesPayBox.isChecked()) {
                    productName = response.getData().getCollageOrder().getProductName();
                    //stagesPay(orderId);
                    queryWallet(orderId);
                    return;
                }
                final double totalPrice = response.getData().getCollageOrder().getTotalPay();
                final String subject = response.getData().getCollageOrder().getProductName();
                productName = subject;
                price = totalPrice;
                if (Util.isEmpty(subject)) {
                    loddingDialog.dismiss();
                    EventBus.getDefault().post(new ActionBean(Constants.ActionType.REFRESH_CART));
                    Util.showToast(OrderBalanceActivity.this, "订单支付失败,请去订单中支付");
                    payBtn.setEnabled(true);
                    return;
                }
                payOrder(orderSn);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                payBtn.setEnabled(true);
                loddingDialog.dismiss();
                Util.showToast(OrderBalanceActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void payOrder(final String ordersn) {
        if ((alipayBox.isChecked() || wxpayBox.isChecked() || hbPayBox.isChecked()) && price > 0) {
                            /*double totalPrice = response.getData().getOrder().getTotalPay();
                            String subject = response.getData().getOrder().getBillSubject();*/
            if (alipayBox.isChecked()) {
                if (price > 0) {
                    loddingDialog.dismiss();
                    String des;
                    if (productName.length() > 50) {
                        des = productName.substring(0, 45);
                    } else {
                        des = productName;
                    }
                    String body;
                    if (isPack || packBuy) {
                        body = "COLLAGE";
                    } else {
                        body = "ORDER";
                    }
                    alipay(ordersn, des, body, price, false);
                    loddingDialog.dismiss();
                } else if (price == 0) {
                    gotoActivityByType(1);
                }
            } else if (wxpayBox.isChecked()) {
                if (price > 0) {
                    wxPay(ordersn, productName, (int) (price * 100+0.5));
                    loddingDialog.dismiss();
                } else if (price == 0) {
                    gotoActivityByType(1);
                }
            } else if (hbPayBox.isChecked()) {
                if (price > 0) {
                    String des;
                    if (productName.length() > 50) {
                        des = productName.substring(0, 45);
                    } else {
                        des = productName;
                    }
                    String body;
                    if (isPack || packBuy) {
                        body = "COLLAGE";
                    } else {
                        body = "ORDER";
                    }
                    alipay(ordersn, des, body, price, true);
                    loddingDialog.dismiss();
                } else if (price == 0) {
                    gotoActivityByType(1);
                }
            }
        } else {
            payOrderApi.setLoginCode(user.getLoginCode());
            D2CApplication.httpClient.loadingRequest(payOrderApi, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    payBtn.setEnabled(true);
                    if (godBox.isChecked()) {
                        publishPayInfo(orderSn, price, "COD");
                    } else {
                        publishPayInfo(orderSn, price, "WALLET");
                    }
                    loddingDialog.dismiss();
                    gotoActivityByType(1);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    payBtn.setEnabled(true);
                    loddingDialog.dismiss();
                    EventBus.getDefault().post(new ActionBean(Constants.ActionType.REFRESH_CART));
                    Util.showToast(OrderBalanceActivity.this, Util.checkErrorType(error));
                }
            });
        }
    }

    private void publishPayInfo(String orderId, double total, String payType) {
        TalkingDataAppCpa.onOrderPaySucc(String.valueOf(user.getMemberId()), orderId, (int) total * 100, "CNY", payType);
        ZampAppAnalytics.onPay(this, String.valueOf(user.getMemberId()), orderId, (float) (total * 100), "RMB", payType, true);
        HashMap<String, String> params = new HashMap<>();
        params.put("orderId", orderId); //订单ID
        params.put("totalPrice", total + ""); //订单总金额,金额中请勿包含西文逗号
        ZampAppAnalytics.onRemarketingEvent(this, "ad-order-paid ", params);
        HashMap<String, String> param2 = new HashMap<>();
        param2.put("info", orderId + "," + total); //订单ID和订单金额，二者以西文逗号分隔拼接成一个字符串,订单金额中请勿包含西文逗号
        ZampAppAnalytics.onConversion(this, "6fb3a850c6b19334ac3627e7ae6b0a5d", param2);
    }

    private void godPay() {
        D2CApplication.httpClient.loadingRequest(createOrderApi, new BeanRequest.SuccessListener<CreateOrderBean>() {
            @Override
            public void onResponse(CreateOrderBean response) {
                loddingDialog.dismiss();
                gotoActivityByType(1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loddingDialog.dismiss();
                Util.showToast(OrderBalanceActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void gotoActivityByType(int type) {
        stat(type);
        if (isPack) {
            Intent intent = new Intent();
            if (type == 1) {
                intent.putExtra("success", 1);
            } else {
                intent.putExtra("success", 0);
            }
            setResult(RESULT_OK, intent);
            finish();
            return;
        }
        if (packBuy) {
            if (type == 1) {//跳拼单邀请界面
                Intent intent = new Intent(this, CollageDetailActivity.class);
                intent.putExtra("collageId", groupId);
                startActivity(intent);
            }
            finish();
            return;
        }
        Intent intent = null;
        if (type == 1) {
            D2CApplication.mSharePref.putSharePrefString(SharePrefConstant.PAY_MODE, payOrderApi.getPaymentType());
            intent = new Intent(this, PaySuccessActivity.class);
            intent.putExtra("id", orderId);
        } else {
            intent = new Intent(this, PayFailedActivity.class);
            intent.putExtra("subject", productName);
            intent.putExtra("orderSn", orderSn);
            intent.putExtra("isApply", isApply);
            intent.putExtra("payStyle", orderEntity.getPayParams());
        }
        startActivity(intent);
        finish();
    }

    private void stat(int type) {
        if (orderItems == null || orderItems.size() == 0) {
            return;
        }
        int size = orderItems.size();
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            if (type == 1) {
                map.put("付款成功_商品id", orderItems.get(i).getProductId() + "");
                stat("下单", "付款成功", map);
            } else {
                map.put("付款失败_商品id", orderItems.get(i).getProductId() + "");
                stat("下单", "付款失败", map);
            }
            map.clear();
        }
    }

    private void alipay(String ordersn, String subject, String body, double price, boolean isHB) {
        if (!Util.checkNetwork(this)) {
            Util.showToast(OrderBalanceActivity.this, R.string.net_disconnect);
            return;
        }
        final String orderInfo;
        if (isHB) {
            orderInfo = PayUtil.getInstance().getAlipayHBInfo(ordersn, subject, body, price, hbNum, isSellPayCharge);
        } else {
            orderInfo = PayUtil.getInstance().getAlipayInfo(ordersn, subject, body, price);
        }
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                if (Util.isEmpty(orderInfo)) {
                    return;
                }
                PayTask alipay = new PayTask(OrderBalanceActivity.this);
                String result = alipay.pay(orderInfo);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void wxPay(final String ordersn, final String body, final int price) {
        final String ip = Util.getIp(this);
        if (!Util.isEmpty(ip)) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        payTask(ordersn, body, price, ip);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            };
            thread.start();
        } else {
            Util.showToast(this, "支付失败,无法获取手机ip,请确认wify是否连接!");
        }
    }

    private void payTask(String ordersn, String body, int price, String ip) throws Throwable {
        if (body.length() > 40) {
            body = body.substring(0, 30);
        }
        String orderType = "ORDER";
        if (isPack || packBuy) {
            orderType = "COLLAGE";
        }
        prepay_id = PayUtil.getInstance().getPrepayId(ordersn, orderType, body, price, ip);
        if (!Util.isEmpty(prepay_id)) {
            wxPay(prepay_id);
        }
    }

    private void wxPay(String prepay_id) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.RequestCode.ADDRESS:
                    changeAddress(data);
                    break;
                case Constants.RequestCode.SELECT_COUPON:
                    if (!data.getBooleanExtra("used", false)) {
                        selectCoupon(false, "", 0, "");
                    } else {
                        String code = data.getStringExtra("code");
                        long amount = data.getLongExtra("amount", 0);
                        String type = data.getStringExtra("type");
                        selectCoupon(true, code, amount, type);
                    }
                    break;
                case Constants.RequestCode.SELECT_ADDRESS:
                    changeAddress(data);
                    break;
                case Constants.RequestCode.INVOICE:
                    String invoiceTopContent = data.getStringExtra("invoice");
                    if (!Util.isEmpty(invoiceTopContent)) {
                        tvInvoiceTop.setText(invoiceTopContent);
                        createOrderApi.setDrawee(invoiceTopContent);
                        if (collageCreateOrderApi != null) {
                            collageCreateOrderApi.drawee = invoiceTopContent;
                        }
                    } else {
                        tvInvoiceTop.setText("");
                        createOrderApi.setDrawee(null);
                        if (collageCreateOrderApi != null) {
                            collageCreateOrderApi.drawee = null;
                        }
                    }
                    break;
                case Constants.RequestCode.SELECT_STAGES:
                    StageItemBean stageItemBean = (StageItemBean) data.getSerializableExtra("stageItemBean");
                    selectStagesLl.setVisibility(View.VISIBLE);
                    String format = "￥%1sX%2s,手续费%3s";
                    stageItemInfo.setText(String.format(format, stageItemBean.amount, stageItemBean.periods, stageItemBean.poundage));
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void selectCoupon(boolean isUse, String code, long amount, String type) {
        price = orderEntity.getTotalPay();
        if (isUse) { //使用优惠券
            checkCouponCode = code;
            createOrderApi.setCoupons(code);
            if (type.equals("DISCOUNT")) {//折扣券
                double zhekou = amount * 1.0 / 100;
                /*orderCouponMoneyLl.setVisibility(View.VISIBLE);
                orderCouponMoney.setText(getString(R.string.label_price_minus,
                        getNumberFormat((1 - zhekou) * orderEntity.getTotalAmount())));
                couponName.setText(getString(R.string.label_discount_price1, amount % 10 == 0 ? amount / 10 + "" : (double) amount / 10 + ""));
                couponPrice = (1 - zhekou) * orderEntity.getTotalAmount();
                double totalPay = price - (1 - zhekou) * orderEntity.getTotalAmount();
                if (totalPay < 0) {
                    totalPay = orderEntity.getShippingRates();
                }
                this.price = totalPay;*/
                calculateDiscountCoupon(checkCouponCode,zhekou);
            } else { //现金券
                orderCouponMoneyLl.setVisibility(View.VISIBLE);
                orderCouponMoney.setText(getString(R.string.label_price_minus, getNumberFormat(amount)));
                couponName.setText(getString(R.string.label_discount_price, getNumberFormat(amount)));
                couponPrice = amount;
                this.price = price - amount;
                if (this.price < 0) {
                    this.price = orderEntity.getShippingRates();
                }
            }
        } else { //不使用优惠券
            checkCouponCode = "";
            couponName.setText("");
            createOrderApi.setCoupons("");
            orderCouponMoneyLl.setVisibility(View.GONE);
            orderCouponMoney.setText(getString(R.string.label_price, getNumberFormat(0)));
            price = orderEntity.getTotalPay();
        }

        if (bean.getData().getAccount() != null && bean.getData().getAccount().getRedAmount() > 0 && bean.getData().getAccount().isRed()) {
            bean.getData().getAccount().setActualRed(Math.min(price - orderEntity.getShippingRates(), bean.getData().getAccount().getRedAmount()));
            if (toggleButton.isChecked()) {
                price = price - bean.getData().getAccount().getActualRed();
            }
            setRedTv();
        }
        calculatePartner();
        if (bean.getData().getAccount() != null && !Util.isEmpty(bean.getData().getAccount().getAvailableAmount() + "")) {
            if (bean.getData().getAccount().getAvailableAmount() >= price-partenerPrice) {
                if (!d2cpayBox.isEnabled()) {
                    d2cBalance.setText(getString(R.string.label_price, getNumberFormat(bean.getData().getAccount().getAvailableAmount())));
                    d2cpayBox.setEnabled(true);
                    d2cpayLl.setEnabled(true);
                    d2cpayLl.setBackgroundResource(R.color.color_white);
                }
            }
        }
        orderActualMoney.setText(getString(R.string.label_no_sign_price, getNumberFormat(this.price - partenerPrice)));
        if (!channels.contains("HBPay") && hbNum > 0) {
            if (isSellPayCharge) {
                rates[0] = 0.018;
            } else {
                rates[0] = 0.023;
            }
            rates[1] = 0.045;
            rates[2] = 0.075;
            hbPrice = Util.calculate((int) (price * 100), hbNum, rates[hbNum / 6]);
            hbCount.setText(Util.getNumberFormat(hbPrice) + "元" + " x " + hbNum + "期");
        }
        if (this.price == 0) {
            payStyleLL.setVisibility(View.GONE);
            payBtn.setText("确定");
        } else {
            payStyleLL.setVisibility(View.VISIBLE);
        }
        checkD2cPay();
        if (this.price > 10000 && orderEntity.getItemTotal() >= 2) {
            RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) actualMountTv.getLayoutParams();
            rl.setMargins(0, ScreenUtil.dip2px(16), 0, ScreenUtil.dip2px(6));
            largeAmountTipTv.setVisibility(View.VISIBLE);
        } else {
            RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) actualMountTv.getLayoutParams();
            rl.setMargins(0, ScreenUtil.dip2px(16), 0, ScreenUtil.dip2px(16));
            largeAmountTipTv.setVisibility(View.GONE);
        }
    }

    private void checkD2cPay(){
        if (showLL == d2cpayLl) {
            if (bean.getData().getAccount().getAvailableAmount() < price-partenerPrice) {
                d2cBalance.setText(getString(R.string.label_money_less,
                        getNumberFormat(bean.getData().getAccount().getAvailableAmount())));
                d2cpayBox.setChecked(false);
                d2cpayBox.setEnabled(false);
                d2cpayLl.setEnabled(false);
                d2cpayLl.setBackgroundResource(R.color.enable_color);
            } else {
                d2cpayBox.setEnabled(true);
                d2cpayLl.setEnabled(true);
                d2cpayLl.setBackgroundResource(R.color.color_white);
                d2cBalance.setText("支付后还剩 ￥" + getNumberFormat(bean.getData().getAccount().getAvailableAmount() - price+partenerPrice));
            }
        } else {
            if (bean.getData().getAccount() == null) {
                return;
            }
            if (bean.getData().getAccount().getAvailableAmount() < price-partenerPrice) {
                d2cpayBox.setEnabled(false);
                d2cpayLl.setEnabled(false);
                d2cpayLl.setBackgroundResource(R.color.enable_color);
                d2cBalance.setText(getString(R.string.label_money_less,
                        getNumberFormat(bean.getData().getAccount().getAvailableAmount())));
            } else {
                d2cpayBox.setEnabled(true);
                d2cpayLl.setEnabled(true);
                d2cpayLl.setBackgroundResource(R.color.color_white);
                d2cBalance.setText("￥" + getNumberFormat(bean.getData().getAccount().getAvailableAmount()));
            }
        }
    }

    private void calculateDiscountCoupon(String couponCode,double zhekou){
        SelectCouponApi api = new SelectCouponApi();
        api.skuIds = skulds;
        api.productIds = productlds;
        api.designerIds = designerlds;
        api.coupons = couponCode;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<SelectCouponBean>() {
            @Override
            public void onResponse(SelectCouponBean response) {
                double totalCoupon=0;
                int size = orderItems.size();
                for (int i = 0; i < size; i++) {
                    int valSize = response.getData().getSkuIds().size();
                    for (int j = 0; j < valSize; j++) {
                        if (orderItems.get(i).getSkuId() == response.getData().getSkuIds().get(j)) {
                            totalCoupon+=(1 - zhekou)*orderItems.get(i).getActualAmount();
                            break;
                        }
                    }
                }
                orderCouponMoneyLl.setVisibility(View.VISIBLE);
                orderCouponMoney.setText(getString(R.string.label_price_minus,
                        getNumberFormat(totalCoupon)));
                couponName.setText(getString(R.string.label_discount_price1,Util.getNumberFormat(zhekou*10,1)));
                couponPrice = totalCoupon;
                double totalPay = price - couponPrice;
                if (totalPay < 0) {
                    totalPay = orderEntity.getShippingRates();
                }
                price = totalPay;
                checkD2cPay();
                orderActualMoney.setText(getString(R.string.label_no_sign_price, getNumberFormat(price)));
            }
        });
    }

    private void calculatePartner() {
        if (isPartener) {
            if (!Util.isEmpty(checkCouponCode)) {//使用优惠券
                SelectCouponApi api = new SelectCouponApi();
                api.skuIds = skulds;
                api.productIds = productlds;
                api.designerIds = designerlds;
                api.coupons = checkCouponCode;
                D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<SelectCouponBean>() {
                    @Override
                    public void onResponse(SelectCouponBean response) {
                        if (response.getData().getSkuIds() != null && response.getData().getSkuIds().size() > 0) {
                            BigDecimal allProductPrice =new BigDecimal(orderEntity.getTotalAmount());
                            Map<Integer, BigDecimal> map = new HashMap<>();
                            int size = orderItems.size();
                            for (int i = 0; i < size; i++) {
                                orderItems.get(i).setLocalAmount(0);
                                int valSize = response.getData().getSkuIds().size();
                                for (int j = 0; j < valSize; j++) {
                                    if (orderItems.get(i).getSkuId() == response.getData().getSkuIds().get(j)) {
                                        map.put(i, new BigDecimal(orderItems.get(i).getActualAmount()));
                                    }
                                }
                            }
                            Log.e("han",""+allProductPrice.doubleValue());
                            int len=map.size();
                            int i=1;
                            double leftCoupon=0;
                            for (Map.Entry<Integer, BigDecimal> entry : map.entrySet()) {
                                if (i==len){
                                    orderItems.get(entry.getKey()).setLocalAmount(new BigDecimal(orderItems.get(entry.getKey()).getActualAmount()-(couponPrice-leftCoupon)).setScale(2,RoundingMode.HALF_UP).doubleValue());
                                }else {
                                    BigDecimal cou=entry.getValue().multiply(new BigDecimal(couponPrice)).divide(allProductPrice,2).setScale(2,RoundingMode.HALF_UP);
                                    //double cou=new BigDecimal(entry.getValue() * couponPrice / allProductPrice).setScale(2,RoundingMode.HALF_UP).doubleValue();
                                    Log.e("han",entry.getKey()+"__couprice=="+cou.doubleValue());
                                    orderItems.get(entry.getKey()).setLocalAmount(new BigDecimal(orderItems.get(entry.getKey()).getActualAmount() - cou.doubleValue()).setScale(2,RoundingMode.HALF_UP).doubleValue());
                                    leftCoupon+=cou.doubleValue();
                                }
                                i++;
                            }
                            orderEntity.setLocalAmount(new BigDecimal(orderEntity.getTotalAmount() - couponPrice).setScale(2,RoundingMode.HALF_UP).doubleValue());
                            calculateRed(true);
                        } else {
                            calculateRed(false);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        calculateRed(false);
                    }
                });
            } else {
                orderEntity.setLocalAmount(0);
                int size = orderItems.size();
                for (int i = 0; i < size; i++) {
                    orderItems.get(i).setLocalAmount(0);
                }
                calculateRed(false);
            }
        } else {
            orderActualMoney.setText(getString(R.string.label_no_sign_price, getNumberFormat(this.price)));
        }
    }

    /**
     * 是否存在优惠券
     *
     * @param hasCoupon
     */
    private void calculateRed(boolean hasCoupon) {
        if (toggleButton.isChecked()) {
            double redPrice = bean.getData().getAccount().getActualRed();
            int size = orderItems.size();
            partenerPrice = 0;
            double leftRedPrice=0;
            for (int i = 0; i < size; i++) {
                double actualAmount = orderItems.get(i).getLocalAmount() == 0 ? orderItems.get(i).getActualAmount() : orderItems.get(i).getLocalAmount();
                double totalAmount = orderEntity.getLocalAmount() == 0 ? orderEntity.getTotalAmount() : orderEntity.getLocalAmount();
                double red=actualAmount*redPrice/totalAmount;
                double actualPrice=0;
                if (i==size-1){
                    actualPrice=actualAmount-(redPrice-leftRedPrice);
                }else {
                    actualPrice = actualAmount - red;
                }
                leftRedPrice+=red;
                partenerPrice += actualPrice * orderItems.get(i).getPartnerRatio();
            }
            partenerPrice=new BigDecimal(partenerPrice).setScale(2,RoundingMode.HALF_UP).doubleValue();
            orderSelfTv.setText(String.format(getString(R.string.label_price_minus), getNumberFormat(partenerPrice)));
            orderActualMoney.setText(getString(R.string.label_no_sign_price, getNumberFormat(this.price - partenerPrice)));
        } else {
            if (hasCoupon) {
                partenerPrice = 0;
                int size = orderItems.size();
                for (int i = 0; i < size; i++) {
                    Log.e("han","name=="+orderItems.get(i).getProductName());
                    double actualAmount = orderItems.get(i).getLocalAmount() == 0 ? orderItems.get(i).getActualAmount() : orderItems.get(i).getLocalAmount();
                    Log.e("han","actualAmount=="+actualAmount);
                    partenerPrice += new BigDecimal(actualAmount * orderItems.get(i).getPartnerRatio()).setScale(2,RoundingMode.HALF_UP).doubleValue();
                    Log.e("han","partenerPrice=="+actualAmount * orderItems.get(i).getPartnerRatio());
                    Log.e("han","---------------");
                }
                partenerPrice=new BigDecimal(partenerPrice).setScale(2,RoundingMode.HALF_UP).doubleValue();
                orderSelfTv.setText(String.format(getString(R.string.label_price_minus), getNumberFormat(partenerPrice)));
                orderActualMoney.setText(getString(R.string.label_no_sign_price, getNumberFormat(this.price - partenerPrice)));
            } else {
                partenerPrice = 0;
                int size = orderItems.size();
                for (int i = 0; i < size; i++) {
                    partenerPrice += orderItems.get(i).getActualAmount() * orderItems.get(i).getPartnerRatio();
                }
                partenerPrice=new BigDecimal(partenerPrice).setScale(2,RoundingMode.HALF_UP).doubleValue();
                orderSelfTv.setText(String.format(getString(R.string.label_price_minus), getNumberFormat(partenerPrice)));
                orderActualMoney.setText(getString(R.string.label_no_sign_price, getNumberFormat(this.price - partenerPrice)));
            }
        }
        if (bean.getData().getAccount() != null && !Util.isEmpty(bean.getData().getAccount().getAvailableAmount() + "")) {
            if (bean.getData().getAccount().getAvailableAmount() >= price-partenerPrice) {
                if (!d2cpayBox.isEnabled()) {
                    d2cBalance.setText(getString(R.string.label_price, getNumberFormat(bean.getData().getAccount().getAvailableAmount())));
                    d2cpayBox.setEnabled(true);
                    d2cpayLl.setEnabled(true);
                    d2cpayLl.setBackgroundResource(R.color.color_white);
                }
            }
        }
    }

    private void changeAddress(Intent data) {
        String name = data.getStringExtra("name");
        nowName = name;
        nowProvinceName = data.getStringExtra("province");
        nowCityName = data.getStringExtra("city");
        nowDistrictName = data.getStringExtra("district");
        nowStreet = data.getStringExtra("street");
        String phone = data.getStringExtra("phone");
        String address = data.getStringExtra("address");
        long id = data.getLongExtra("id", 0);
        boolean isDefault = data.getBooleanExtra("isDefault", false);
        createOrderApi.setAddressId(id);
        if (collageCreateOrderApi != null) {
            collageCreateOrderApi.addressId = id;
        }
        addressLL.setVisibility(View.VISIBLE);
        editAddress.setVisibility(View.GONE);
        addressName.setText(name);
        addressPhone.setText(phone);
        addressInfo.setText(address);
        if (addressInfo.getText().toString().matches("(台湾|香港|澳门|国外)(\\S|\\s)+")) {
            isSupportAddress = true;
            if (!isGod) {
                if (showLL == godll) {
                    showLL = alipayll;
                }
                godll.setVisibility(View.GONE);
            }
            addressTip.setVisibility(View.VISIBLE);
        } else {
            isSupportAddress = false;
            if (!isGod) {
                //godll.setVisibility(View.VISIBLE);
            }
            addressTip.setVisibility(View.GONE);
        }
        double lat = data.getDoubleExtra("lat", 0);
        double lon = data.getDoubleExtra("lon", 0);
        if (lat == 0 || lon == 0) {
            LoctionUtil.getLatLonPoint(this, addressInfo.getText().toString(), nowCityName, new GeocodeSearch.OnGeocodeSearchListener() {
                @Override
                public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

                }

                @Override
                public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                    if (geocodeResult.getGeocodeAddressList() != null && geocodeResult.getGeocodeAddressList().size() > 0) {
                        double lat = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLatitude();
                        double logt = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLongitude();
                        createOrderApi.setLatitude(lat);
                        createOrderApi.setLongitude(logt);
                    }
                }
            });
        } else {
            createOrderApi.setLatitude(lat);
            createOrderApi.setLongitude(lon);
        }
        getKaoLaInfo();
    }

    private void callBack(String password) {
        String psd = Util.getMD5(password.trim());
        payOrderApi.setPassword(psd);
        CheckPayPwdApi api = new CheckPayPwdApi();
        api.setPassWord(psd);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CheckPayBean>() {
            @Override
            public void onResponse(CheckPayBean response) {
                loddingDialog.show();
                user.setPayDanger(response.getData().getDanger());
                createOrder();
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
                    Util.showToast(OrderBalanceActivity.this, Util.checkErrorType(error));
                }
            }
        });
    }

    private void showTipPop() {
        TipPop tipPop = new TipPop(OrderBalanceActivity.this, false);
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
                Intent intent = new Intent(OrderBalanceActivity.this, SetPayPasswordActivity.class);
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
        tipPop.show(payBtn);
    }

    @Subscribe
    public void onEvent(ActionBean bean) {
        if (bean.type == Constants.ActionType.WXPAYRESULT) {
            int code = (int) bean.get("code");
            if (isPack) {
                Intent intent = new Intent();
                if (code == 0) {
                    intent.putExtra("success", 1);
                } else {
                    intent.putExtra("success", 0);
                }
                setResult(RESULT_OK, intent);
                finish();
                return;
            }
            if (packBuy) {
                if (code == 0) {//跳拼单邀请界面
                    Intent intent = new Intent(this, CollageDetailActivity.class);
                    intent.putExtra("collageId", groupId);
                    startActivity(intent);
                }
                finish();
                return;
            }
            Intent intent = null;
            switch (code) {
                case 0: //支付成功
                    publishPayInfo(orderSn, price, "WXAPPPAY");
                    intent = new Intent(this, PaySuccessActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("id", orderId);
                    break;
                case -1: //支付失败
                    intent = new Intent(this, PayFailedActivity.class);
                    intent.putExtra("type", 2);
                    intent.putExtra("subject", productName);
                    intent.putExtra("orderSn", orderSn);
                    intent.putExtra("isApply", isApply);
                    intent.putExtra("payStyle", orderEntity.getPayParams());
                    break;
                case -2: //支付取消
                    intent = new Intent(this, PayFailedActivity.class);
                    intent.putExtra("type", 3);
                    intent.putExtra("subject", productName);
                    intent.putExtra("orderSn", orderSn);
                    intent.putExtra("isApply", isApply);
                    intent.putExtra("payStyle", orderEntity.getPayParams());
                    break;
            }
            EventBus.getDefault().post(new ActionBean(Constants.ActionType.REFRESH_CART));
            startActivity(intent);
            finish();
        }
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

    @Override
    public void onBackPressed() {
        finish();
    }

    private void showDialog() {
        if (pwdDialog != null && pwdDialog.isShowing()) {
            return;
        }
        pwdDialog = new Dialog(this, R.style.bubble_vis_dialog);
        Point point = Util.getDeviceSize(this);
        View dialogView = getLayoutInflater().inflate(R.layout.layout_password_dialog, null);
        final ClearEditText etNumber = (ClearEditText) dialogView.findViewById(R.id.et_number);
        etNumber.requestFocus();
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
                    Util.showToast(OrderBalanceActivity.this, R.string.msg_password_empty);
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
        if (getCurrentFocus() != null) {
            KeyboardUtil.showKeyboard(getCurrentFocus());
        }
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

    public double getTwoDouble(double d){
        String s=String.valueOf(d);
        int index=s.indexOf(".");
        if (s.length()>(index+3)){
            return (int)(d*100+0.5)/100f;
        }else {
            return d;
        }
    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        super.onDestroy();
    }
}
