package com.d2cmall.buyer.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.AddCertificationApi;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.BizNoApi;
import com.d2cmall.buyer.api.BuyNowApi;
import com.d2cmall.buyer.api.CartConfirmApi;
import com.d2cmall.buyer.api.CombBuyApi;
import com.d2cmall.buyer.api.CreateOrderApi;
import com.d2cmall.buyer.api.IdentityCardAddApi;
import com.d2cmall.buyer.api.KaolaConfirmOrderApi;
import com.d2cmall.buyer.api.PayOrderApi;
import com.d2cmall.buyer.api.SelectCouponApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.api.UploadIDCardPicsApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.BizNoBean;
import com.d2cmall.buyer.bean.CreateOrderBean;
import com.d2cmall.buyer.bean.IdentityCardBean;
import com.d2cmall.buyer.bean.KaoLaConfirmBean;
import com.d2cmall.buyer.bean.OrderConfirmBean;
import com.d2cmall.buyer.bean.SelectCouponBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.http.HttpError;
import com.d2cmall.buyer.util.Base64;
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
import com.d2cmall.buyer.widget.MyCheckBox;
import com.d2cmall.buyer.widget.OverRatePop;
import com.d2cmall.buyer.widget.RealNamePop;
import com.d2cmall.buyer.widget.SelectHbPop;
import com.d2cmall.buyer.widget.SimpleDialog;
import com.d2cmall.buyer.widget.TransparentPop;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tendcloud.appcpa.Order;
import com.tendcloud.appcpa.TalkingDataAppCpa;
import com.upyun.library.common.Params;
import com.upyun.library.common.UploadManager;
import com.upyun.library.listener.SignatureListener;
import com.upyun.library.listener.UpCompleteListener;
import com.upyun.library.utils.UpYunUtils;
import com.zamplus.businesstrack.ZampAppAnalytics;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zmxy.ZMCertification;
import com.zmxy.ZMCertificationListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
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
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

import static com.d2cmall.buyer.activity.OrderBalanceActivity.BUYNOW;
import static com.d2cmall.buyer.activity.OrderBalanceActivity.COMBBUYNOW;
import static com.d2cmall.buyer.activity.OrderBalanceActivity.FROMCAR;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/6/4 15:10
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class GlobalOrderBalanceActivity extends BaseActivity {


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
    @Bind(R.id.address_name)
    TextView addressName;
    @Bind(R.id.address_phone)
    TextView addressPhone;
    @Bind(R.id.address_info)
    TextView addressInfo;
    @Bind(R.id.address_ll)
    RelativeLayout addressLl;
    @Bind(R.id.add_address_ll)
    LinearLayout addAddressLl;
    @Bind(R.id.edit_address_rl)
    RelativeLayout editAddressRl;
    @Bind(R.id.order_list_content)
    LinearLayout orderListContent;
    @Bind(R.id.mark)
    EditText mark;
    @Bind(R.id.coupon_num)
    TextView couponNum;
    @Bind(R.id.coupon_name)
    TextView couponName;
    @Bind(R.id.coupon_ll)
    LinearLayout couponLl;
    @Bind(R.id.red_packet_amount)
    TextView redPacketAmount;
    @Bind(R.id.toggle_button)
    Switch toggleButton;
    @Bind(R.id.red_packet_ll)
    LinearLayout redPacketLl;
    @Bind(R.id.order_num)
    TextView orderNum;
    @Bind(R.id.order_total)
    TextView orderTotal;
    @Bind(R.id.order_freight)
    TextView orderFreight;
    @Bind(R.id.order_promotion_money)
    TextView orderPromotionMoney;
    @Bind(R.id.order_promotion_money_ll)
    LinearLayout orderPromotionMoneyLl;
    @Bind(R.id.order_coupon_money)
    TextView orderCouponMoney;
    @Bind(R.id.order_coupon_money_ll)
    LinearLayout orderCouponMoneyLl;
    @Bind(R.id.order_self_buy_money)
    TextView orderSelfBuyMoney;
    @Bind(R.id.self_buy_ll)
    LinearLayout selfBuyLl;
    @Bind(R.id.order_red_money)
    TextView orderRedMoney;
    @Bind(R.id.order_red_money_ll)
    LinearLayout orderRedMoneyLl;
    @Bind(R.id.ll_rate_wenhao)
    LinearLayout llRateWenhao;
    @Bind(R.id.order_rate_money)
    TextView orderRateMoney;
    @Bind(R.id.order_rate_ll)
    LinearLayout orderRateLl;
    @Bind(R.id.order_actual_money)
    TextView orderActualMoney;
    @Bind(R.id.alipay_box)
    MyCheckBox alipayBox;
    @Bind(R.id.alipay_ll)
    LinearLayout alipayll;
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
    @Bind(R.id.my_scroll_view)
    ScrollView scrollView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.pay_btn)
    TextView payBtn;
    @Bind(R.id.delete_iv)
    ImageView deleteIv;
    @Bind(R.id.over_rate_tip)
    LinearLayout overRateTip;
    @Bind(R.id.rl_content)
    RelativeLayout all;
    @Bind(R.id.cover)
    View cover;
    @Bind(R.id.coupon_line)
    View couponLine;
    @Bind(R.id.red_packet_line)
    View redPacketLine;
    @Bind(R.id.identity_et)
    EditText identityEt;
    @Bind(R.id.tag_auth)
    ImageView tagAuth;
    @Bind(R.id.identity_line)
    View identityLine;
    @Bind(R.id.tag_tv)
    TextView tagTv;
    @Bind(R.id.identity_rl)
    RelativeLayout identityRl;
    @Bind(R.id.identity_select_z_iv)
    ImageView identitySelectZIv;
    @Bind(R.id.identity_select_f_iv)
    ImageView identitySelectFIv;
    @Bind(R.id.identity_select_z_tv)
    TextView identitySelectZTv;
    @Bind(R.id.identity_select_f_tv)
    TextView identitySelectFTv;
    @Bind(R.id.identity_pic_ll)
    LinearLayout identityPicLl;
    @Bind(R.id.actual_mount_tv)
    TextView actualMountTv;
    @Bind(R.id.large_amount_tip_tv)
    TextView largeAmountTipTv;
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
    private long[] skus;
    private long[] productlds;
    private long[] designerlds;

    private double totalTaxMoney = 0;

    private OrderConfirmBean bean;
    private OrderConfirmBean.DataEntity.AddressBean addresslistEntity;
    private OrderConfirmBean.DataEntity.OrderEntity orderEntity;
    private List<OrderConfirmBean.DataEntity.OrderEntity.ItemsEntity> orderItems;

    private CreateOrderApi createOrderApi;
    private PayOrderApi payOrderApi;
    private IWXAPI mApp;
    private static final int SDK_PAY_FLAG = 1;
    private Dialog loddingDialog;
    private StringBuilder designId;
    private boolean isOpen;
    private LinearLayout showLL;
    private String checkCouponCode;
    private String orderSn;
    private double price;
    private double logisticsPayAmount = 0;
    private String productName;
    private boolean isSupportAddress;
    private boolean isSupportStagesPay = true;
    private RealNamePop realNamePop;
    private OverRatePop overRatePop;
    private ArrayList<String> channels = new ArrayList<>();
    private UserBean.DataEntity.MemberEntity user;
    private boolean needPicRealName = true;
    private boolean hasUploadPicRealName = false;
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
    private int kaolaSize;
    private boolean isPartener;
    private double partenerPrice;
    private double couponPrice;
    private StringBuilder kaolaGoodsId, kaolaSkuId, kaolaBuyAmounts, kaolaChannelSalePrices, taxAmounts, warehouseIds, warehouseNames;
    private String nowProvinceName, nowCityName, nowDistrictName, nowStreet, nowName;
    private List<KaoLaConfirmBean.DataBean.KaolaBean.OrderFormBean.PackageListBean> OverRateList;
    private ZMCertification zmCertification;

    private String zUploadUrl;
    private String fUploadUrl;
    private boolean hasCaoMei;
    private int caoMeiSize;
    private double caoMeiAmount;
    private boolean hasMilan;
    private boolean hasHit;
    private int miLanSize;
    private double miLanAmount;
    private int hitSize;
    private double hitAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_order_confirm);
        ButterKnife.bind(this);
        init();
    }

    private void showOverRatePop(OrderConfirmBean response, List<KaoLaConfirmBean.DataBean.KaolaBean.OrderFormBean.PackageListBean> OverRateList) {
        if (overRatePop == null) {
            overRatePop = new OverRatePop(this);
            overRatePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    cover.setVisibility(View.GONE);
                }
            });

            overRatePop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        cover.setVisibility(View.VISIBLE);
        overRatePop.setData(response, OverRateList);
        overRatePop.showAtLocation(all, Gravity.BOTTOM, 0, 0);
    }

    private void init() {
        user = Session.getInstance().getUserFromFile(this);
        loddingDialog = DialogUtil.createLoadingDialog(this);
        progressBar.setVisibility(View.VISIBLE);
        initTitle();
        createOrderApi = new CreateOrderApi();
        payOrderApi = new PayOrderApi();
        OverRateList = new ArrayList<>();
        mApp = WXAPIFactory.createWXAPI(this, Constants.WEIXINAPPID, false);
        mApp.registerApp(Constants.WEIXINAPPID);
        if (!mApp.isWXAppInstalled()) {
            wxpayLl.setVisibility(View.GONE);
        }
        type = getIntent().getIntExtra("source", 0);
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
        if (user.getPartnerId() > 0) {
            isPartener = true;
        } else {
            isPartener = false;
        }
        initListener();
    }

    @OnClick({R.id.over_rate_tip, R.id.add_address_ll, R.id.coupon_ll, R.id.hide_layout_tag, R.id.pay_btn, R.id.address_ll, R.id.cover, R.id.identity_select_z_iv, R.id.identity_select_f_iv})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.over_rate_tip:
                if (bean == null) {
                    return;
                }
                if (OverRateList == null || OverRateList.size() == 0) {
                    return;
                }
                showOverRatePop(bean, OverRateList);
                break;
            case R.id.cover:
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
                if (OverRateList.size() > 0) {//说明超过限额了
                    showOverRatePop(bean, OverRateList);
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
                if (bean.getData().getMemberCertification() == null || Util.isEmpty(bean.getData().getMemberCertification().getIdentityCard())) {
                    String identity = identityEt.getText().toString();
                    if (Util.isEmpty(identity) || !Util.isIDCard(identity)) {
                        Util.showToast(this, "请填写正确的身份证号!");
                        return;
                    }
                }
                HashMap<String, String> map = new HashMap<>();
                if (alipayBox.isChecked() && this.price > 0) {
                    payOrderApi.setPaymentType("ALIPAY");
                    map.put("立即付款_支付方式", "支付宝");
                } else {
                    map.put("立即付款_支付方式", "微信");
                    if (!Util.isEmpty(prepay_id)) {
                        wxPay(prepay_id);
                        return;
                    }
                    payOrderApi.setPaymentType("WXAPPPAY");
                }
                stat("V3下单", "立即付款", map);
                if (bean.getData().getMemberCertification() != null && !Util.isEmpty(bean.getData().getMemberCertification().getIdentityCard())) {
                    loddingDialog.show();
                    createOrder();
                } else {
                    //liveAttestation();
                    attestation();
                }
                break;
            case R.id.address_ll:
                intent = new Intent(this, ReceiveAddressActivity.class);
                intent.putExtra("type", 1);
                startActivityForResult(intent, Constants.RequestCode.SELECT_ADDRESS);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
            case R.id.identity_select_z_iv:
                showPop(Constants.RequestCode.ID_CARD_FRONT);
                break;
            case R.id.identity_select_f_iv:
                showPop(Constants.RequestCode.ID_CARD_OPPOSITE);
                break;
        }
    }

    private void attestation(){
        loddingDialog.show();
        AddCertificationApi api=new AddCertificationApi();
        api.setRealName(addressName.getText().toString());
        api.setIsdefault(1);
        api.setIdentityCard(identityEt.getText().toString());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                createOrder();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loddingDialog.dismiss();
                Util.showToast(GlobalOrderBalanceActivity.this,Util.checkErrorType(error));
            }
        });
    }

  private void liveAttestation() {
        if (identityPicLl.getVisibility() == View.VISIBLE && (Util.isEmpty(zUploadUrl) || Util.isEmpty(fUploadUrl))) {
            Util.showToast(this, "请先上传身份证正反面");
            return;
        }
        payBtn.setEnabled(false);
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.CERTIFICATION_CHECK);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                int id = 0;
                String code = "";
                if (bean.getData().getMemberCertification() != null) {
                    code = bean.getData().getMemberCertification().getCardNo();
                }
                if (Util.isEmpty(code)) {
                    try {
                        code = Base64.encode(identityEt.getText().toString().getBytes("utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    id = bean.getData().getMemberCertification().getId();
                }
                getBizNo(addressName.getText().toString(), code, id);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                payBtn.setEnabled(true);
                Util.showToast(GlobalOrderBalanceActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void getBizNo(final String name, final String code, final int id) {
        BizNoApi api = new BizNoApi();
        api.setName(name);
        api.setCertNo(code);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BizNoBean>() {
            @Override
            public void onResponse(BizNoBean response) {
                if (!Util.isEmpty(response.getData().getBizNo())) {
                    zmCertification = ZMCertification.getInstance();
                    zmCertification.setZMCertificationListener(new ZMCertificationListener() {
                        @Override
                        public void onFinish(boolean isCanceled, boolean isPassed, int errorCode) {
                            if (isCanceled) {
                                payBtn.setEnabled(true);
                                Util.showToast(GlobalOrderBalanceActivity.this, "认证被取消!");
                            } else {
                                if (isPassed) {//通过认证
                                    addLiveAttestation(name, code, id);
                                } else {
                                    payBtn.setEnabled(true);
                                    Util.showToast(GlobalOrderBalanceActivity.this, "认证失败!");
                                }
                            }
                        }
                    });
                    zmCertification.startCertification(GlobalOrderBalanceActivity.this, response.getData().getBizNo(), Constants.ALIPAYPARTNER, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                payBtn.setEnabled(true);
                Util.showToast(GlobalOrderBalanceActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void addLiveAttestation(String name, final String code, int id) {
        IdentityCardAddApi addApi = new IdentityCardAddApi();
        addApi.realName = name;
        addApi.identityCard = code;
        if (id > 0) {
            addApi.id = id;
        }
        D2CApplication.httpClient.loadingRequest(addApi, new BeanRequest.SuccessListener<IdentityCardBean>() {
            @Override
            public void onResponse(IdentityCardBean response) {
                payBtn.setEnabled(true);
                tagAuth.setVisibility(View.VISIBLE);
                identityEt.setEnabled(false);
                identityLine.setVisibility(View.GONE);
                tagTv.setVisibility(View.GONE);
                bean.getData().setMemberCertification(response.getData().getMemberCertification());
                if (identityPicLl.getVisibility() == View.VISIBLE) {
                    requestUploadIDCardPic(response.getData().getMemberCertification().getId());
                }
                Util.showToast(GlobalOrderBalanceActivity.this, "认证成功!");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                payBtn.setEnabled(true);
                Util.showToast(GlobalOrderBalanceActivity.this, Util.checkErrorType(error));
            }
        });
    }

    //重新上传身份证照片
    private void requestUploadIDCardPic(int id) {
        UploadIDCardPicsApi api = new UploadIDCardPicsApi();
        if (!Util.isEmpty(zUploadUrl)) {
            api.setFrontPic(zUploadUrl);
        }
        if (!Util.isEmpty(fUploadUrl)) {
            api.setBehindPic(fUploadUrl);
        }
        api.setId(id);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    private void clickOpen(View view) {
        if (view == null) {
            return;
        }
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
        if (wxpayLl != except) {
            if (!mApp.isWXAppInstalled()) {
                wxpayLl.setVisibility(View.GONE);
                wxPayLine.setVisibility(View.GONE);
            } else {
                wxpayLl.setVisibility(vis);
                wxPayLine.setVisibility(vis);
            }
        } else {
            wxPayLine.setVisibility(View.VISIBLE);
        }
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
                if (list.get(i).equals("WALLET")) {
                    continue;
                }
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

    private void checkChannel(String channel) {
        if (channel.equals("ALIPAY")) {
            alipayll.setVisibility(View.GONE);
            aliPayLine.setVisibility(View.GONE);
        } else if (channel.equals("WXAPPPAY")) {
            wxpayLl.setVisibility(View.GONE);
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

    private void getOrderDataFromBuy() {
        BuyNowApi api = new BuyNowApi();
        if (crowId > 0) {
            api.setCrowdItemId((int) crowId);
        }
        api.setSkuId((int) skuId);
        api.setNum(num);
        getData(api);
    }

    private void getOrderDataFromCart() {
        CartConfirmApi api = new CartConfirmApi();
        api.setCartItemIds(cartId);
        /*api.setGoodPromotionId(goodPromationId);
        api.setOrderPromotionId(orderPromationId);*/
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
                scrollView.setVisibility(View.VISIBLE);
                scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        publishThird(response);
                    }
                }, 1000);
                bean = response;
                limitPayMode();//获取支付方式
                createOrderApi.setTempId(response.getData().getOrder().getTempId());
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
                price = orderEntity.getTotalAmount();
                getOrderInfo();
                setDataWithoutAddress();
                progressBar.setVisibility(View.GONE);
                if (addresslistEntity == null) {//如果没有地址的话先不去求求关于仓位数据,将选择地址的布局显示出来
                    addressLl.setVisibility(View.GONE);
                    //llIndentifitionInfo.setVisibility(View.GONE);
                    editAddressRl.setVisibility(View.VISIBLE);
                } else {//如果有地址的话,直接带着地址去请求关于仓位的数据(因为仓位和收货地址有关系的,并且每改变一次地址都要去请求一次)
                    //llIndentifitionInfo.setVisibility(View.VISIBLE);
                    createOrderApi.setAddressId(addresslistEntity.getId());
                    addressName.setText(addresslistEntity.getName());
                    nowName = addresslistEntity.getName();
                    addressPhone.setText(addresslistEntity.getMobile());
                    addressInfo.setText(addresslistEntity.getProvince() + addresslistEntity.getCity()
                            + addresslistEntity.getDistrict() + addresslistEntity.getStreet());
                    nowProvinceName = addresslistEntity.getProvince();
                    nowCityName = addresslistEntity.getCity();
                    nowDistrictName = addresslistEntity.getDistrict();
                    nowStreet = addresslistEntity.getStreet();
                    //显示身份证信息
                    identityRl.setVisibility(View.VISIBLE);
                    if (bean.getData().getMemberCertification() != null && !Util.isEmpty(bean.getData().getMemberCertification().getCardNo())) {
                        identityEt.setText(bean.getData().getMemberCertification().getIdentityCard());
                        identityEt.setEnabled(false);
                        tagAuth.setVisibility(View.VISIBLE);
                        identityLine.setVisibility(View.GONE);
                        tagTv.setVisibility(View.GONE);
                        /*if (bean.getData().getMemberCertification().getAuthenticate() == 1) {

                        } else {
                            tagAuth.setVisibility(View.GONE);
                            identityLine.setVisibility(View.VISIBLE);
                            tagTv.setVisibility(View.VISIBLE);
                        }*/
                    } else {
                        identityEt.setEnabled(true);
                        identityLine.setVisibility(View.VISIBLE);
                        tagTv.setVisibility(View.VISIBLE);
                    }

                    if (addresslistEntity.getLatitude() == 0 || addresslistEntity.getLongitude() == 0) {
                        LoctionUtil.getLatLonPoint(GlobalOrderBalanceActivity.this, addressInfo.getText().toString(), addresslistEntity.getCity(), new GeocodeSearch.OnGeocodeSearchListener() {
                            @Override
                            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

                            }

                            @Override
                            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                                if (i == 1000) {
                                    if (geocodeResult.getGeocodeAddressList() != null && geocodeResult.getGeocodeAddressList().size() > 0) {
                                        double lat = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLatitude();
                                        double logt = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLongitude();
                                        createOrderApi.setLatitude(lat);
                                        createOrderApi.setLongitude(logt);
                                    }
                                }
                                if (!Util.isEmpty(kaolaSkuId.toString())) {
                                    getKaoLaInfo();
                                }
                            }
                        });
                    } else {
                        createOrderApi.setLatitude(addresslistEntity.getLatitude());
                        createOrderApi.setLongitude(addresslistEntity.getLongitude());
                        if (!Util.isEmpty(kaolaSkuId.toString())) {
                            getKaoLaInfo();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(GlobalOrderBalanceActivity.this, Util.checkErrorType(error));
                if (error.getMessage() != null && error.getMessage().contains("地址")) {
                    final SimpleDialog simpleDialog = new SimpleDialog(GlobalOrderBalanceActivity.this);
                    simpleDialog.show();
                    simpleDialog.setRightText("修改地址")
                            .hasRightButtom(true)
                            .setTvMessage(getResources().getString(R.string.label_kaola_address))
                            .setRightClick(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(GlobalOrderBalanceActivity.this, ReceiveAddressActivity.class);
                                    intent.putExtra("type", 1);
                                    startActivityForResult(intent, Constants.RequestCode.SELECT_ADDRESS);
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                                    simpleDialog.dismiss();
                                }
                            });
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onBackPressed();
                        }
                    }, 1000);
                }
            }
        });
    }

    private void getOrderInfo() {//这个方法获取api的参数的,类似于goodid,skuId什么的
        caoMeiAmount = 0;
        miLanAmount = 0;
        int size = orderItems.size();
        kaolaSkuId = new StringBuilder();//skuid
        kaolaBuyAmounts = new StringBuilder();//购买数量
        kaolaGoodsId = new StringBuilder();//商品款号
        kaolaChannelSalePrices = new StringBuilder();//活动后的价格
        StringBuilder price = new StringBuilder();
        StringBuilder goodPromotionId = new StringBuilder();
        StringBuilder orderPromotionId = new StringBuilder();
        StringBuilder productCombId = new StringBuilder();
        StringBuilder crowdItemId = new StringBuilder();
        StringBuilder oldSkuId = new StringBuilder();
        StringBuilder quantitys = new StringBuilder();
        designId = new StringBuilder();
        for (int i = 0; i < size; i++) {
            oldSkuId.append(orderItems.get(i).getSkuId());
            if ("KAOLA".equals(orderItems.get(i).getProductSource())) {
                kaolaSize++;
                kaolaGoodsId.append(orderItems.get(i).getProductSn());
                kaolaChannelSalePrices.append((orderItems.get(i).getTotalPrice() - orderItems.get(i).getPromotionAmount() - orderItems.get(i).getOrderPromotionAmount()) / orderItems.get(i).getQuantity());
                kaolaBuyAmounts.append(orderItems.get(i).getQuantity());
                kaolaSkuId.append(orderItems.get(i).getSkuSn());
                kaolaSkuId.append(",");
                kaolaBuyAmounts.append(",");
                kaolaGoodsId.append(",");
                kaolaChannelSalePrices.append(",");
            }
            if ("CAOMEI".equals(orderItems.get(i).getProductSource())) {
                hasCaoMei = true;
                caoMeiSize++;
                totalTaxMoney+=orderItems.get(i).getTaxPrice()*orderItems.get(i).getActualAmount();
                if (taxAmounts==null){
                    taxAmounts=new StringBuilder();
                }
                if (!Util.isEmpty(taxAmounts.toString())){
                    taxAmounts.append(",");
                }
                taxAmounts.append(getNumberFormat(orderItems.get(i).getTaxPrice()*orderItems.get(i).getActualAmount()));
                caoMeiAmount += (orderItems.get(i).getTotalPrice() - orderItems.get(i).getPromotionAmount());
            }
            if ("MILAN".equals(orderItems.get(i).getProductSource())) {
                hasMilan = true;
                miLanSize++;
                miLanAmount += (orderItems.get(i).getTotalPrice() - orderItems.get(i).getPromotionAmount());
            }
            if("HISTREET".equals(orderItems.get(i).getProductSource())){
                hasHit=true;
                hitSize++;
                hitAmount+=(orderItems.get(i).getTotalPrice()-orderItems.get(i).getPromotionAmount());
            }
            quantitys.append(orderItems.get(i).getQuantity());
            price.append(orderItems.get(i).getProductPrice());
            goodPromotionId.append(orderItems.get(i).getGoodPromotionId());
            orderPromotionId.append(orderItems.get(i).getOrderPromotionId());
            productCombId.append(orderItems.get(i).getProductCombId());
            crowdItemId.append(orderItems.get(i).getCrowdItemId());
            designId.append(orderItems.get(i).getDesignerId());

            if (i != size - 1) {
                price.append(",");
                oldSkuId.append(",");
                quantitys.append(",");
                goodPromotionId.append(",");
                orderPromotionId.append(",");
                productCombId.append(",");
                crowdItemId.append(",");
                designId.append(",");
            }
        }
        createOrderApi.setSkuId(oldSkuId.toString());
        createOrderApi.setQuantity(quantitys.toString());
        createOrderApi.setGoodPromotionId(goodPromotionId.toString());
        createOrderApi.setOrderPromotionId(orderPromotionId.toString());
        createOrderApi.setProductCombId(productCombId.toString());
        if (taxAmounts!=null&&!Util.isEmpty(taxAmounts.toString())){
            createOrderApi.setTaxAmount(taxAmounts.toString());
        }
    }

    private void getKaoLaInfo() {
        progressBar.setVisibility(View.VISIBLE);
        KaolaConfirmOrderApi kaolaCreatOrderApi = new KaolaConfirmOrderApi();
        kaolaCreatOrderApi.setGoodsId(kaolaGoodsId.toString().substring(0, kaolaGoodsId.toString().length() - 1));
        kaolaCreatOrderApi.setSkuId(kaolaSkuId.toString().substring(0, kaolaSkuId.toString().length() - 1));
        kaolaCreatOrderApi.setBuyAmount(kaolaBuyAmounts.toString().substring(0, kaolaBuyAmounts.toString().length() - 1));
        kaolaCreatOrderApi.setChannelSalePrice(kaolaChannelSalePrices.toString().substring(0, kaolaChannelSalePrices.toString().length() - 1));
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
                    logisticsPayAmount = orderEntity.getShippingRates() + response.getData().getKaola().getOrderForm().getLogisticsPayAmount();
                    getWareHouseWithGoodId(response);//因为考拉给的数据只有goodid,所以要通过goodid找到仓位信息从而分仓
                    if (needPicRealName) {//是否需要上传图片的实名认证，需要去拉取身份信息看有没有上传图片
                        //requestRealNameData();
                        if (bean.getData().getMemberCertification() == null || bean.getData().getMemberCertification().getIsUpload() == 0) {
                            identityLine.setVisibility(View.VISIBLE);
                            identityPicLl.setVisibility(View.VISIBLE);
                        }
                    }
                    setWithWareHouseData(response);
                } else {
                    if (response.getData().getKaola() != null) {
                        final SimpleDialog simpleDialog = new SimpleDialog(GlobalOrderBalanceActivity.this);
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
                                            Intent intent = new Intent(GlobalOrderBalanceActivity.this, ReceiveAddressActivity.class);
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
                        Util.showToast(GlobalOrderBalanceActivity.this, "数据异常,等会儿来下单哦~");
                        finish();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                final SimpleDialog simpleDialog = new SimpleDialog(GlobalOrderBalanceActivity.this);
                simpleDialog.show();
                simpleDialog.setTitle("提醒")
                        .hasRightButtom(false)
                        .setTvMessage(Util.checkErrorType(error) + "或地址有误!")
                        .setLeftText("返回")
                        .setLeftClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        });
                simpleDialog.setRightText("修改地址")
                        .hasRightButtom(true)
                        .setTvMessage(getResources().getString(R.string.label_kaola_address))
                        .setRightClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(GlobalOrderBalanceActivity.this, ReceiveAddressActivity.class);
                                intent.putExtra("type", 1);
                                startActivityForResult(intent, Constants.RequestCode.SELECT_ADDRESS);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                                simpleDialog.dismiss();
                            }
                        });
            }
        });
    }

    private void getWareHouseWithGoodId(KaoLaConfirmBean response) {
        String skuSn[] = kaolaSkuId.toString().substring(0, kaolaSkuId.toString().length() - 1).split(",");
        String wareHouseId[] = new String[kaolaSize];//创建一个相同长度的数组
        String wareHouseName[] = new String[kaolaSize];//创建一个相同长度的数组
        String taxAmount[] = new String[kaolaSize];
        List<KaoLaConfirmBean.DataBean.KaolaBean.OrderFormBean.PackageListBean> packageListBeanList = response.getData().getKaola().getOrderForm().getPackageList();
        if (packageListBeanList.size() > 0) {
            for (int i = 0; i < packageListBeanList.size(); i++) {
                List<KaoLaConfirmBean.DataBean.KaolaBean.OrderFormBean.PackageListBean.GoodsListBean> goodsListBeanList = packageListBeanList.get(i).getGoodsList();
                KaoLaConfirmBean.DataBean.KaolaBean.OrderFormBean.PackageListBean.WarehouseBean warehouseBean = packageListBeanList.get(i).getWarehouse();
                if (packageListBeanList.get(i).getNeedVerifyLevel() > 1) {//大于1就是需要上传身份证照片的
                    needPicRealName = true;
                } else {
                    needPicRealName = false;
                }
                for (int j = 0; j < goodsListBeanList.size(); j++) {
                    String id = String.valueOf(goodsListBeanList.get(j).getSkuId());
                    for (int k = 0; k < skuSn.length; k++) {
                        if (id.equals(skuSn[k])) {
                            wareHouseId[k] = String.valueOf(warehouseBean.getWarehouseId());
                            wareHouseName[k] = warehouseBean.getWarehouseName();
                            taxAmount[k] = String.valueOf(goodsListBeanList.get(j).getGoodsTaxAmount());
                        }
                    }
                }
            }
        }
        taxAmounts = new StringBuilder();
        warehouseIds = new StringBuilder();
        warehouseNames = new StringBuilder();
        for (int p = 0; p < kaolaSize; p++) {
            taxAmounts.append(taxAmount[p]);
            warehouseIds.append(wareHouseId[p]);
            warehouseNames.append(wareHouseName[p]);
            if (p != kaolaSize - 1) {
                taxAmounts.append(",");
                warehouseIds.append(",");
                warehouseNames.append(",");
            }
        }
        createOrderApi.setTaxAmount(taxAmounts.toString());
        createOrderApi.setWarehouseId(warehouseIds.toString());
        createOrderApi.setWarehouseName(warehouseNames.toString());
        createOrderApi.setFreight(response.getData().getKaola().getOrderForm().getLogisticsPayAmount());
    }

    private void setCaoMeiData() {
        if (hasCaoMei) {
            tagTv.setText(getResources().getString(R.string.label_tag_identity1));
            View rootView = LayoutInflater.from(this).inflate(R.layout.layout_confirm_global_order, orderListContent, false);
            TextView wareName = (TextView) rootView.findViewById(R.id.tv_ware_house);//仓库名称
            TextView wareNotice = (TextView) rootView.findViewById(R.id.tv_ware_house_notice);//超过限额的提醒(就仓库名下边的红字)
            wareName.setText("香港直邮仓");
            if (caoMeiAmount < 210) {
                wareNotice.setText("未达该仓包邮条件,需付运费60元");
                wareNotice.setVisibility(View.VISIBLE);
            } else {
                wareNotice.setVisibility(View.GONE);
            }
            LinearLayout contentView = (LinearLayout) rootView.findViewById(R.id.ll_order_content);//商品的那个LinearLayout
            for (int i = 0; i < orderItems.size(); i++) {
                if ("CAOMEI".equals(orderItems.get(i).getProductSource())) {
                    OrderConfirmBean.DataEntity.OrderEntity.ItemsEntity data = orderItems.get(i);
                    View productView = LayoutInflater.from(this).inflate(R.layout.layout_order_global_item, contentView, false);
                    ImageView productImage = (ImageView) productView.findViewById(R.id.content_iv);//商品图片
                    UniversalImageLoader.displayImage(this, Util.getD2cPicUrl(data.getProductImg()), productImage);
                    TextView productName = (TextView) productView.findViewById(R.id.good_info);//商品名称
                    productName.setText(data.getProductName());
                    TextView productSku = (TextView) productView.findViewById(R.id.good_size);//颜色尺码
                    productSku.setText(data.getColor() + "  " + data.getSize());
                    TextView prodcutCount = (TextView) productView.findViewById(R.id.good_num);//商品数量
                    prodcutCount.setText("x" + data.getQuantity());
                    TextView productPrice = (TextView) productView.findViewById(R.id.good_price);//商品价格
                    productPrice.setText("¥" + getNumberFormat((orderItems.get(i).getTotalPrice() - orderItems.get(i).getPromotionAmount()) / orderItems.get(i).getQuantity()));
                    TextView productDropPrice = (TextView) productView.findViewById(R.id.good_drop_price);//商品划掉的价格
                    View spaceView = productView.findViewById(R.id.view_space);//底下的那根线
                    TextView goodAfter=productView.findViewById(R.id.good_after);
                    if (orderItems.get(i).getAfter() == 0) {
                        goodAfter.setVisibility(View.VISIBLE);
                    } else {
                        goodAfter.setVisibility(View.GONE);
                    }
                    if (i == caoMeiSize - 1) {
                        spaceView.setVisibility(View.GONE);
                    } else {
                        spaceView.setVisibility(View.VISIBLE);
                    }
                    contentView.addView(productView);
                }
            }
            orderListContent.addView(rootView);
        }

    }

    private void setMiLanData() {
        if (hasMilan) {
            View rootView = LayoutInflater.from(this).inflate(R.layout.layout_confirm_global_order, orderListContent, false);
            TextView wareName = (TextView) rootView.findViewById(R.id.tv_ware_house);//仓库名称
            TextView wareNotice = (TextView) rootView.findViewById(R.id.tv_ware_house_notice);//超过限额的提醒(就仓库名下边的红字)
            wareName.setText("海外直邮仓");
            if (miLanAmount < 210) {
                wareNotice.setText("未达该仓包邮条件,需付运费60元");
                wareNotice.setVisibility(View.VISIBLE);
            } else {
                wareNotice.setVisibility(View.GONE);
            }
            LinearLayout contentView = (LinearLayout) rootView.findViewById(R.id.ll_order_content);//商品的那个LinearLayout
            for (int i = 0; i < orderItems.size(); i++) {
                if ("MILAN".equals(orderItems.get(i).getProductSource())) {
                    OrderConfirmBean.DataEntity.OrderEntity.ItemsEntity data = orderItems.get(i);

                    View productView = LayoutInflater.from(this).inflate(R.layout.layout_order_global_item, contentView, false);
                    ImageView productImage = (ImageView) productView.findViewById(R.id.content_iv);//商品图片
                    UniversalImageLoader.displayImage(this, Util.getD2cPicUrl(data.getProductImg()), productImage);
                    TextView productName = (TextView) productView.findViewById(R.id.good_info);//商品名称
                    productName.setText(data.getProductName());
                    TextView productSku = (TextView) productView.findViewById(R.id.good_size);//颜色尺码
                    productSku.setText(data.getColor() + "  " + data.getSize());
                    TextView prodcutCount = (TextView) productView.findViewById(R.id.good_num);//商品数量
                    prodcutCount.setText("x" + data.getQuantity());
                    TextView productPrice = (TextView) productView.findViewById(R.id.good_price);//商品价格
                    productPrice.setText("¥" + getNumberFormat((orderItems.get(i).getTotalPrice() - orderItems.get(i).getPromotionAmount()) / orderItems.get(i).getQuantity()));
                    TextView productDropPrice = (TextView) productView.findViewById(R.id.good_drop_price);//商品划掉的价格
                    View spaceView = productView.findViewById(R.id.view_space);//底下的那根线
                    TextView goodAfter=productView.findViewById(R.id.good_after);
                    if (orderItems.get(i).getAfter() == 0) {
                        goodAfter.setVisibility(View.VISIBLE);
                    } else {
                        goodAfter.setVisibility(View.GONE);
                    }
                    if (i == miLanSize - 1) {
                        spaceView.setVisibility(View.GONE);
                    } else {
                        spaceView.setVisibility(View.VISIBLE);
                    }
                    contentView.addView(productView);
                }
            }
            orderListContent.addView(rootView);
        }

    }

    private void setHitData() {
        if (hasHit) {
            View rootView = LayoutInflater.from(this).inflate(R.layout.layout_confirm_global_order, orderListContent, false);
            TextView wareName = (TextView) rootView.findViewById(R.id.tv_ware_house);//仓库名称
            TextView wareNotice = (TextView) rootView.findViewById(R.id.tv_ware_house_notice);//超过限额的提醒(就仓库名下边的红字)
            wareName.setText("海外直邮仓");
            if (hitAmount < 299) {
                wareNotice.setText("未达该仓包邮条件,需付运费10元");
                wareNotice.setVisibility(View.VISIBLE);
            } else {
                wareNotice.setVisibility(View.GONE);
            }
            LinearLayout contentView = (LinearLayout) rootView.findViewById(R.id.ll_order_content);//商品的那个LinearLayout
            for (int i = 0; i < orderItems.size(); i++) {
                if ("HISTREET".equals(orderItems.get(i).getProductSource())) {
                    OrderConfirmBean.DataEntity.OrderEntity.ItemsEntity data = orderItems.get(i);

                    View productView = LayoutInflater.from(this).inflate(R.layout.layout_order_global_item, contentView, false);
                    ImageView productImage = (ImageView) productView.findViewById(R.id.content_iv);//商品图片
                    UniversalImageLoader.displayImage(this, Util.getD2cPicUrl(data.getProductImg()), productImage);
                    TextView productName = (TextView) productView.findViewById(R.id.good_info);//商品名称
                    productName.setText(data.getProductName());
                    TextView productSku = (TextView) productView.findViewById(R.id.good_size);//颜色尺码
                    productSku.setText(data.getColor() + "  " + data.getSize());
                    TextView prodcutCount = (TextView) productView.findViewById(R.id.good_num);//商品数量
                    prodcutCount.setText("x" + data.getQuantity());
                    TextView productPrice = (TextView) productView.findViewById(R.id.good_price);//商品价格
                    productPrice.setText("¥" + getNumberFormat((orderItems.get(i).getTotalPrice() - orderItems.get(i).getPromotionAmount()) / orderItems.get(i).getQuantity()));
                    TextView productDropPrice = (TextView) productView.findViewById(R.id.good_drop_price);//商品划掉的价格
                    View spaceView = productView.findViewById(R.id.view_space);//底下的那根线
                    TextView goodAfter=productView.findViewById(R.id.good_after);
                    if (orderItems.get(i).getAfter() == 0) {
                        goodAfter.setVisibility(View.VISIBLE);
                    } else {
                        goodAfter.setVisibility(View.GONE);
                    }
                    if (i == hitSize - 1) {
                        spaceView.setVisibility(View.GONE);
                    } else {
                        spaceView.setVisibility(View.VISIBLE);
                    }
                    contentView.addView(productView);
                }
            }
            orderListContent.addView(rootView);
        }

    }

    private void setWithWareHouseData(KaoLaConfirmBean kaoLaConfirmBean) {
        double taxMoney = 0;
        createOrderApi.setQuantity(kaolaBuyAmounts.toString().substring(0, kaolaBuyAmounts.toString().length() - 1));
        List<KaoLaConfirmBean.DataBean.KaolaBean.OrderFormBean.PackageListBean> packageListBeans = kaoLaConfirmBean.getData().getKaola().getOrderForm().getPackageList();
        //带仓位的设置布局
        orderListContent.removeAllViews();
        setCaoMeiData();
        setMiLanData();
        setHitData();
        int overWareNum = 0;//超过限额的仓库的总数量
        Log.e("tag9", packageListBeans.size() + "packageListBeans");
        for (int i = 0; i < packageListBeans.size(); i++) {
            KaoLaConfirmBean.DataBean.KaolaBean.OrderFormBean.PackageListBean packageListBean = packageListBeans.get(i);
            View rootView = LayoutInflater.from(this).inflate(R.layout.layout_confirm_global_order, orderListContent, false);
            TextView wareName = (TextView) rootView.findViewById(R.id.tv_ware_house);//仓库名称
            TextView wareNotice = (TextView) rootView.findViewById(R.id.tv_ware_house_notice);//超过限额的提醒(就仓库名下边的红字)
            wareName.setText(packageListBean.getWarehouse().getWarehouseName());
            double goodAmount = packageListBean.getPayAmount() - packageListBean.getTaxPayAmount() - packageListBean.getLogisticsPayAmount();
            int buyNumber = 0;//这个仓位的所有商品数量
            for (int p = 0; p < packageListBean.getGoodsList().size(); p++) {
                buyNumber = buyNumber + packageListBean.getGoodsList().get(p).getGoodsBuyNumber();
            }
            if (goodAmount > 2000 && buyNumber != 1) {
                overWareNum++;
                wareNotice.setText(getResources().getString(R.string.global_warehouse_notice));
                wareNotice.setVisibility(View.VISIBLE);
            } else {
                wareNotice.setVisibility(View.GONE);
            }

            if (packageListBean.getLogisticsPayAmount() > 0) {
                wareNotice.setVisibility(View.VISIBLE);
                wareNotice.setText("未达该仓包邮条件,需付运费" + packageListBean.getLogisticsPayAmount() + "元");
            }
            LinearLayout contentView = (LinearLayout) rootView.findViewById(R.id.ll_order_content);//商品的那个LinearLayout
            for (int j = 0; j < packageListBean.getGoodsList().size(); j++) {
                Log.e("tag9", packageListBean.getGoodsList().size() + "GoodsListBean");
                KaoLaConfirmBean.DataBean.KaolaBean.OrderFormBean.PackageListBean.GoodsListBean goodsListBean = packageListBean.getGoodsList().get(j);
                for (int k = 0; k < orderItems.size(); k++) {
                    if (goodsListBean.getSkuId().equals(orderItems.get(k).getSkuSn())) {
                        OrderConfirmBean.DataEntity.OrderEntity.ItemsEntity data = orderItems.get(k);
                        StringBuilder stringBuilder = new StringBuilder();
                        SpannableString sb = null;
                        if (data.getIsTaxation() == 0) {
                            stringBuilder.append(data.getProductName());
                            sb = new SpannableString(stringBuilder.toString());
                        } else if (data.getIsTaxation() == 1) {//包税
                            stringBuilder.append(" " + data.getProductName());
                            sb = new SpannableString(stringBuilder.toString());
                            Drawable d = getResources().getDrawable(R.mipmap.icon_freetax);
                            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                            sb.setSpan(new CenterAlignImageSpan(d), 0, 1, ImageSpan.ALIGN_BASELINE);
                        }
                        View productView = LayoutInflater.from(this).inflate(R.layout.layout_order_global_item, contentView, false);
                        ImageView productImage = (ImageView) productView.findViewById(R.id.content_iv);//商品图片
                        UniversalImageLoader.displayImage(this, Util.getD2cPicUrl(data.getProductImg()), productImage);
                        TextView productName = (TextView) productView.findViewById(R.id.good_info);//商品名称
                        if (sb != null) {
                            productName.setText(sb);
                        }
                        TextView productSku = (TextView) productView.findViewById(R.id.good_size);//颜色尺码
                        productSku.setText(data.getColor() + "  " + data.getSize());
                        TextView prodcutCount = (TextView) productView.findViewById(R.id.good_num);//商品数量
                        prodcutCount.setText("x" + data.getQuantity());
                        TextView productPrice = (TextView) productView.findViewById(R.id.good_price);//商品价格
                        productPrice.setText("¥" + getNumberFormat((orderItems.get(k).getTotalPrice() - orderItems.get(k).getPromotionAmount()) / orderItems.get(k).getQuantity()));
                        TextView productDropPrice = (TextView) productView.findViewById(R.id.good_drop_price);//商品划掉的价格
                        View spaceView = productView.findViewById(R.id.view_space);//底下的那根线
                        TextView goodAfter=productView.findViewById(R.id.good_after);
                        if (orderItems.get(i).getAfter() == 0) {
                            goodAfter.setVisibility(View.VISIBLE);
                        } else {
                            goodAfter.setVisibility(View.GONE);
                        }
                        if (j == packageListBean.getGoodsList().size() - 1) {
                            spaceView.setVisibility(View.GONE);
                        } else {
                            spaceView.setVisibility(View.VISIBLE);
                        }
                        contentView.addView(productView);
                    }
                }
            }
            orderListContent.addView(rootView);
        }

        if (kaoLaConfirmBean.getData().getKaola().getOrderForm().getLogisticsPayAmount() > 0) {
            orderFreight.setText(String.format(getString(R.string.label_price_plus), getNumberFormat(logisticsPayAmount)));
        } else {
            orderFreight.setText(String.format(getString(R.string.label_price), getNumberFormat(logisticsPayAmount)));
        }


        taxMoney = kaoLaConfirmBean.getData().getKaola().getOrderForm().getTaxPayAmount();
        overRateTip.setVisibility(overWareNum > 0 ? View.VISIBLE : View.GONE);
        BigDecimal b = new BigDecimal(taxMoney);
        totalTaxMoney = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        price = orderEntity.getTotalAmount() + totalTaxMoney + logisticsPayAmount;
        if (bean.getData().getCoupons() != null && bean.getData().getCoupons().size() > 0) {
            selectCoupon(true, bean.getData().getCoupons().get(0).getCode(), bean.getData().getCoupons().get(0).getAmount(), bean.getData().getCoupons().get(0).getType());
        }
        orderRateMoney.setText(String.format(getResources().getString(R.string.label_price_plus), String.valueOf(totalTaxMoney)));
        orderActualMoney.setText(getString(R.string.label_no_sign_price, getNumberFormat(price - partenerPrice)));
        isOverRate(kaoLaConfirmBean);//是否超过限额

    }

    private void isOverRate(KaoLaConfirmBean kaoLaConfirmBean) {
        OverRateList.clear();
        List<KaoLaConfirmBean.DataBean.KaolaBean.OrderFormBean.PackageListBean> packageListBeans = kaoLaConfirmBean.getData().getKaola().getOrderForm().getPackageList();
        for (int i = 0; i < packageListBeans.size(); i++) {
            KaoLaConfirmBean.DataBean.KaolaBean.OrderFormBean.PackageListBean packageListBean = packageListBeans.get(i);
            //商品的总额
            double goodAmount = packageListBean.getPayAmount() - packageListBean.getTaxPayAmount() - packageListBean.getLogisticsPayAmount();
            int buyNumber = 0;//这个仓位的所有商品数量
            for (int j = 0; j < packageListBean.getGoodsList().size(); j++) {
                buyNumber = buyNumber + packageListBean.getGoodsList().get(j).getGoodsBuyNumber();
            }
            if (goodAmount > 2000 && buyNumber != 1) {//超过2000元且购买数量不为1,就算是超过限额
                OverRateList.add(packageListBean);
            }
        }

    }

    private void setDataWithoutAddress() {//不带地址的展示数据
        int size = orderItems.size();
        orderListContent.removeAllViews();
        setCaoMeiData();
        setMiLanData();
        setHitData();
        View content;
        TextView foot;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, Util.dip2px(this, 25));
        LinearLayout.LayoutParams lineLp = new LinearLayout.LayoutParams(-1, Util.dip2px(this, (float) 0.5));
        designId = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (skus == null) {
                skus = new long[size];
            }
            if (productlds == null) {
                productlds = new long[size];
            }
            if (designerlds == null) {
                designerlds = new long[size];
            }
            skus[i] = orderItems.get(i).getSkuId();
            productlds[i] = orderItems.get(i).getProductId();
            designerlds[i] = orderItems.get(i).getDesignerId();
            if ("CAOMEI".equals(orderItems.get(i).getProductSource()) || "MILAN".equals(orderItems.get(i).getProductSource())||"HISTREET".equals(orderItems.get(i).getProductSource())) {
                continue;
            }
            content = LayoutInflater.from(this).inflate(R.layout.layout_order_item_content, null);
            ImageView orderItemIv = (ImageView) content.findViewById(R.id.content_iv);
            TextView orderItemInfo = (TextView) content.findViewById(R.id.good_info);
            TextView itemProductPrice = (TextView) content.findViewById(R.id.good_price);
            TextView itemOrgistProductPrice = (TextView) content.findViewById(R.id.good_drop_price);
            TextView itemProductNum = (TextView) content.findViewById(R.id.good_num);
            TextView orderItemSize = (TextView) content.findViewById(R.id.good_size);
            TextView goodAfter = (TextView) content.findViewById(R.id.good_after);
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
            orderItemSize.setText("颜色：" + orderItems.get(i).getColor() + " 尺寸：" + orderItems.get(i).getSize());
            orderListContent.addView(content);
        }
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
        if (totalTaxMoney>0){
            orderRateMoney.setText(String.format(getResources().getString(R.string.label_price_plus), getNumberFormat(totalTaxMoney)));
        }
        orderActualMoney.setText(getString(R.string.label_no_sign_price, getNumberFormat(orderEntity.getTotalAmount() + totalTaxMoney + orderEntity.getShippingRates())));
        logisticsPayAmount = orderEntity.getShippingRates();
        this.price = orderEntity.getTotalAmount() + totalTaxMoney + logisticsPayAmount;
        String mode = D2CApplication.mSharePref.getSharePrefString(SharePrefConstant.PAY_MODE, "");
        if (!Util.isEmpty(mode) && !"FREEPAY".equals(mode)) {
            if (mode.equals("ALIPAY")) {
                alipayBox.setChecked(true);
                payBtn.setText(R.string.label_go_alipay);
                showLL = alipayll;
            } else if (mode.equals("WXAPPPAY")) {
                wxpayBox.setChecked(true);
                payBtn.setText(R.string.label_go_weixinpay);
                showLL = wxpayLl;
            } else if (mode.equals("WALLET")) {
                /*if (bean.getData().getAccount() != null && bean.getData().getAccount().getAvailableAmount() > orderEntity.getTotalAmount()) {
                    d2cpayBox.setChecked(true);
                    payBtn.setText(R.string.action_ok);
                    showLL = d2cpayLl;
                } else {
                    alipayBox.setChecked(true);
                    payBtn.setText(R.string.label_go_alipay);
                    showLL = alipayll;
                }*/
                alipayBox.setChecked(true);
                payBtn.setText(R.string.label_go_alipay);
                showLL = alipayll;
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
        }
        if (bean.getData().getAccount() != null && bean.getData().getAccount().isSetPassword()) {
            if (bean.getData().getAccount().getAvailableAmount() < orderEntity.getTotalAmount()) {
                /*if (bean.getData().getAccount().getAvailableAmount() == 0) {
                    d2cpayLl.setVisibility(View.GONE);
                } else {
                    d2cBalance.setText(getString(R.string.label_money_less,
                            getNumberFormat(bean.getData().getAccount().getAvailableAmount())));
                    d2cpayBox.setEnabled(false);
                    d2cpayLl.setEnabled(false);
                    d2cpayLl.setBackgroundResource(R.color.enable_color);
                }*/
                d2cBalance.setText("全球购商品不支持使用D2C钱包支付");
                d2cpayBox.setEnabled(false);
                d2cpayLl.setEnabled(false);
                d2cpayLl.setBackgroundResource(R.color.enable_color);
            } else {
                d2cBalance.setText("全球购商品不支持使用D2C钱包支付");
                d2cpayBox.setEnabled(false);
                d2cpayLl.setEnabled(false);
                d2cpayLl.setBackgroundResource(R.color.enable_color);
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
            if (isPartener) {
                calculatePartner();
            }
        }
        if (bean.getData().getAccount() != null) {
            if (!bean.getData().getAccount().isRed() || bean.getData().getAccount().getRedAmount() == 0) {
                redPacketLl.setVisibility(View.GONE);
                redPacketLine.setVisibility(View.GONE);
            } else {
                redPacketLl.setVisibility(View.VISIBLE);
                redPacketLine.setVisibility(View.VISIBLE);
                bean.getData().getAccount().setActualRed(Math.min(bean.getData().getAccount().getRedAmount(), price-partenerPrice-orderEntity.getShippingRates()));
                setRedTv();
            }
        }
        if (this.price == 0) {
            payStyleLl.setVisibility(View.GONE);
            payBtn.setText("确定");
        } else {
            payStyleLl.setVisibility(View.VISIBLE);
        }
    }


    private void publishThird(OrderConfirmBean bean) {
        Order order = Order.createOrder(bean.getData().getOrder().getOrderSn(), (int) bean.getData().getOrder().getTotalAmount() * 100, "CNY");
        for (OrderConfirmBean.DataEntity.OrderEntity.ItemsEntity itemsEntity : bean.getData().getOrder().getItems()) {
            order.addItem("", itemsEntity.getProductName(), (int) itemsEntity.getProductPrice() * 100, itemsEntity.getQuantity());
        }
        TalkingDataAppCpa.onPlaceOrder(String.valueOf(user.getMemberId()), order);
        ZampAppAnalytics.onOrderSubmit(this, String.valueOf(user.getMemberId()), bean.getData().getOrder().getOrderSn(), (float) (bean.getData().getOrder().getTotalAmount()),
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
        param2.put("info", bean.getData().getOrder().getOrderSn() + "," + bean.getData().getOrder().getTotalAmount()); //订单ID和订单金额，二者以西文逗号分隔拼接成一个字符串,订单金额中请勿包含西文逗号
        ZampAppAnalytics.onConversion(this, "99d459f555cdf22d9f05d85eb262952f", param2);
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
                //orderActualMoney.setText(getString(R.string.label_no_sign_price, getNumberFormat(price)));
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

                if (price == 0) {
                    payStyleLl.setVisibility(View.GONE);
                    payBtn.setText("确定");
                } else {
                    payStyleLl.setVisibility(View.VISIBLE);
                }
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
                showLL = wxpayLl;
                break;
            case 3:
                alipayBox.setChecked(false);
                wxpayBox.setChecked(false);
                d2cpayBox.setChecked(false);
                stagesPayBox.setChecked(false);
                hbPayBox.setChecked(false);
                payBtn.setText("确认");
                showLL = godLl;
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

    private void showSelectHB(TextView textView) {
        final SelectHbPop selectHbPop = new SelectHbPop(this, price, isSellPayCharge, hbPrice, hbNum, textView);
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

    private void createOrder() {
        if (!Util.isEmpty(orderSn)) {
            payOrder(orderSn);
            return;
        }
        int parentId = D2CApplication.mSharePref.getSharePrefInteger(SharePrefConstant.PARENT_ID, 0);
        if (parentId > 0) {
            createOrderApi.setParent_id(parentId);
        }
        payBtn.setEnabled(false);
        /*if (needPicRealName && !hasUploadPicRealName) {
            payBtn.setEnabled(true);
            loddingDialog.dismiss();
            Util.showToast(this, "您需要去补充实名认证");
            Intent intent = new Intent(this, CertificationActivity.class);
            startActivityForResult(intent, 999);
            return;
        }*/
        D2CApplication.httpClient.loadingRequest(createOrderApi, new BeanRequest.SuccessListener<CreateOrderBean>() {
            @Override
            public void onResponse(CreateOrderBean response) {
                EventBus.getDefault().post(new ActionBean(Constants.ActionType.REFRESH_CART));
                final String ordersn = response.getData().getOrder().getOrderSn();
                payBtn.setEnabled(true);
                orderSn = ordersn;
                orderId = response.getData().getOrder().getId() + "";
                payOrderApi.setOrderType("ORDER");
                payOrderApi.setOrderSn(ordersn);
                final double totalPrice = response.getData().getOrder().getTotalPay();
                final String subject = response.getData().getOrder().getBillSubject();
                productName = subject;
                productName = productName.replace("&", "a");
                price = totalPrice;
                if (Util.isEmpty(subject)) {
                    loddingDialog.dismiss();
                    EventBus.getDefault().post(new ActionBean(Constants.ActionType.REFRESH_CART));
                    Util.showToast(GlobalOrderBalanceActivity.this, "订单支付失败,请去订单中支付");
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
                Util.showToast(GlobalOrderBalanceActivity.this, Util.checkErrorType(error));
                if (error instanceof HttpError) {
                    HttpError httpError = (HttpError) error;
                    int status = httpError.getErrorBean().getStatus();
                    if (status == -3) {
                    } else {
                        Util.showToast(GlobalOrderBalanceActivity.this, Util.checkErrorType(error));
                    }
                } else {
                    Util.showToast(GlobalOrderBalanceActivity.this, Util.checkErrorType(error));
                }
            }
        });
    }

   /* private void requestRealNameData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.CERTIFICATION_LIST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CertificationListBean>() {
            @Override
            public void onResponse(CertificationListBean response) {
                if (response.getData().getCertifications().getList() != null && response.getData().getCertifications().getList().size() > 0) {
                    CertificationListBean.DataBean.CertificationsBean.ListBean realNameBean = response.getData().getCertifications().getList().get(0);
                    if (realNameBean.getIsUpload() > 0) {//说明上传了照片的实名认证
                        hasUploadPicRealName = true;
                    } else {//没有上传图片
                        hasUploadPicRealName = false;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }*/

    private void payOrder(final String ordersn) {
        if ((alipayBox.isChecked() || wxpayBox.isChecked() || hbPayBox.isChecked()) && price > 0) {
            if (alipayBox.isChecked()) {
                if (price > 0) {
                    loddingDialog.dismiss();
                    String des;
                    if (productName.length() > 50) {
                        des = productName.substring(0, 45);
                    } else {
                        des = productName;
                    }
                    alipay(ordersn, des, des, price, false);
                    loddingDialog.dismiss();
                } else if (price == 0) {
                    gotoActivityByType(1);
                }
            } else if (wxpayBox.isChecked()) {
                if (price > 0) {
                    wxPay(ordersn, productName, getPrice(price));
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
                    alipay(ordersn, des, des, price, true);
                    loddingDialog.dismiss();
                } else if (price == 0) {
                    gotoActivityByType(1);
                }
            }
        }else {
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
                    Util.showToast(GlobalOrderBalanceActivity.this, Util.checkErrorType(error));
                }
            });
        }
    }

    private int getPrice(double price) {
        double result = price * 100;
        return (int) (result + 0.5);
    }

    private void alipay(String ordersn, String subject, String body, double price, boolean isHB) {
        if (!Util.checkNetwork(this)) {
            Util.showToast(GlobalOrderBalanceActivity.this, R.string.net_disconnect);
            return;
        }
        final String orderInfo;
        BigDecimal b = new BigDecimal(price);
        price = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (isHB) {
            orderInfo = PayUtil.getInstance().getAlipayHBInfo(ordersn, subject, "ORDER", price, hbNum, isSellPayCharge);
        } else {
            orderInfo = PayUtil.getInstance().getAlipayInfo(ordersn, subject, "ORDER", price);
        }
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                if (Util.isEmpty(orderInfo)) {
                    return;
                }
                PayTask alipay = new PayTask(GlobalOrderBalanceActivity.this);
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
            Util.showToast(this, "支付失败,无法获取手机ip,请确认wifi是否连接!");
        }
    }

    private void payTask(String ordersn, String body, int price, String ip) throws Throwable {
        if (body.length() > 40) {
            body = body.substring(0, 30);
        }
        prepay_id = PayUtil.getInstance().getPrepayId(ordersn, "ORDER", body, price, ip);
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

    private List<Uri> uriList;
    private List<String> obtainPathResult;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.RequestCode.ID_CARD_FRONT:
                    uriList = Matisse.obtainResult(data);
                    obtainPathResult = Matisse.obtainPathResult(data);
                    Intent intent = new Intent(this, CropActivity.class);
                    intent.setData(uriList.get(0));
                    intent.putExtra("mode", 6);
                    intent.putExtra("path", obtainPathResult.get(0));
                    startActivityForResult(intent, 111);
                    break;
                case Constants.RequestCode.ID_CARD_OPPOSITE:
                    uriList = Matisse.obtainResult(data);
                    obtainPathResult = Matisse.obtainPathResult(data);
                    intent = new Intent(this, CropActivity.class);
                    intent.setData(uriList.get(0));
                    intent.putExtra("mode", 6);
                    intent.putExtra("path", obtainPathResult.get(0));
                    startActivityForResult(intent, 112);
                    break;
                case 111:
                    if (data != null) {
                        Uri uri = data.getData();
                        String cropPath = data.getStringExtra("cropPath");
                        Glide.with(this)
                                .load(uri)
                                .error(R.mipmap.btn_renxiang)
                                .crossFade()
                                .into(identitySelectZIv);
                        identitySelectZTv.setVisibility(View.GONE);
                        uploadFile(cropPath, 1);
                    }
                    break;
                case 112:
                    if (data != null) {
                        Uri uri = data.getData();
                        String cropPath = data.getStringExtra("cropPath");
                        Glide.with(this)
                                .load(uri)
                                .error(R.mipmap.btn_guohui)
                                .crossFade()
                                .into(identitySelectFIv);
                        identitySelectFTv.setVisibility(View.GONE);
                        uploadFile(cropPath, 2);
                    }
                    break;
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
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void selectCoupon(boolean isUse, String code, long amount, String type) {
        price = orderEntity.getTotalAmount() + totalTaxMoney + logisticsPayAmount;
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
                    totalPay = logisticsPayAmount;
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
                    this.price = logisticsPayAmount;
                }
            }
        } else { //不使用优惠券
            checkCouponCode = "";
            couponName.setText("");
            createOrderApi.setCoupons("");
            orderCouponMoneyLl.setVisibility(View.GONE);
            orderCouponMoney.setText(getString(R.string.label_price, getNumberFormat(0)));
            price = orderEntity.getTotalAmount() + totalTaxMoney + logisticsPayAmount;
        }

        if (bean.getData().getAccount() != null && bean.getData().getAccount().getRedAmount() > 0 && bean.getData().getAccount().isRed()) {
            bean.getData().getAccount().setActualRed(Math.min(price - totalTaxMoney - logisticsPayAmount, bean.getData().getAccount().getRedAmount()));
            if (toggleButton.isChecked()) {
                price = price - bean.getData().getAccount().getActualRed();
            }
            setRedTv();
        }
        calculatePartner();
        orderActualMoney.setText(getString(R.string.label_no_sign_price, getNumberFormat(this.price - partenerPrice)));
        if (this.price == 0) {
            payStyleLl.setVisibility(View.GONE);
            payBtn.setText("确定");
        } else {
            payStyleLl.setVisibility(View.VISIBLE);
        }
    }

    private void calculateDiscountCoupon(String couponCode,double zhekou){
        SelectCouponApi api = new SelectCouponApi();
        api.skuIds = skus;
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
                couponName.setText(getString(R.string.label_discount_price1, Util.getNumberFormat(zhekou*10,1)));
                couponPrice = totalCoupon;
                double totalPay = price - couponPrice;
                if (totalPay < 0) {
                    totalPay = orderEntity.getShippingRates();
                }
                price = totalPay;
                orderActualMoney.setText(getString(R.string.label_no_sign_price, getNumberFormat(price)));
            }
        });
    }

    private void calculatePartner() {
        if (isPartener) {
            if (!Util.isEmpty(checkCouponCode)) {//使用优惠券
                SelectCouponApi api = new SelectCouponApi();
                api.skuIds = skus;
                api.productIds = productlds;
                api.designerIds = designerlds;
                api.coupons = checkCouponCode;
                D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<SelectCouponBean>() {
                    @Override
                    public void onResponse(SelectCouponBean response) {
                        if (response.getData().getSkuIds() != null && response.getData().getSkuIds().size() > 0) {
                            double allProductPrice = 0;
                            Map<Integer, Double> map = new HashMap<>();
                            int size = orderItems.size();
                            for (int i = 0; i < size; i++) {
                                orderItems.get(i).setLocalAmount(0);
                                int valSize = response.getData().getSkuIds().size();
                                for (int j = 0; j < valSize; j++) {
                                    if (orderItems.get(i).getSkuId() == response.getData().getSkuIds().get(j)) {
                                        map.put(i, orderItems.get(i).getActualAmount());
                                        allProductPrice += orderItems.get(i).getActualAmount();
                                    }
                                }
                            }
                            for (Map.Entry<Integer, Double> entry : map.entrySet()) {
                                orderItems.get(entry.getKey()).setLocalAmount(getTwoDouble(orderItems.get(entry.getKey()).getActualAmount() - entry.getValue() * couponPrice / allProductPrice));
                            }
                            orderEntity.setLocalAmount(getTwoDouble(orderEntity.getTotalAmount() - couponPrice));
                            calculateRed(true);
                        }else {
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
    }

    /**
     * 是否存在优惠券
     *
     * @param hasCoupon
     */
    private void calculateRed(boolean hasCoupon) {
        /*if (hasCoupon) {
            partenerPrice = 0;
            int size = orderItems.size();
            for (int i = 0; i < size; i++) {
                double actualAmount = orderItems.get(i).getLocalAmount() == 0 ? orderItems.get(i).getActualAmount() : orderItems.get(i).getLocalAmount();
                partenerPrice += getTwoDouble(actualAmount * orderItems.get(i).getPartnerRatio());
            }
            orderSelfBuyMoney.setText(String.format(getString(R.string.label_price_minus), getNumberFormat(partenerPrice)));
            orderActualMoney.setText(getString(R.string.label_no_sign_price, getNumberFormat(this.price - partenerPrice)));
        } else {
            partenerPrice = 0;
            int size = orderItems.size();
            for (int i = 0; i < size; i++) {
                partenerPrice += getTwoDouble(orderItems.get(i).getActualAmount() * orderItems.get(i).getPartnerRatio());
            }
            orderSelfBuyMoney.setText(String.format(getString(R.string.label_price_minus), getNumberFormat(partenerPrice)));
            orderActualMoney.setText(getString(R.string.label_no_sign_price, getNumberFormat(this.price - partenerPrice)));
        }*/
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
            orderSelfBuyMoney.setText(String.format(getString(R.string.label_price_minus), getNumberFormat(partenerPrice)));
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
                orderSelfBuyMoney.setText(String.format(getString(R.string.label_price_minus), getNumberFormat(partenerPrice)));
                orderActualMoney.setText(getString(R.string.label_no_sign_price, getNumberFormat(this.price - partenerPrice)));
            } else {
                partenerPrice = 0;
                int size = orderItems.size();
                for (int i = 0; i < size; i++) {
                    partenerPrice += orderItems.get(i).getActualAmount() * orderItems.get(i).getPartnerRatio();
                }
                partenerPrice=new BigDecimal(partenerPrice).setScale(2,RoundingMode.HALF_UP).doubleValue();
                orderSelfBuyMoney.setText(String.format(getString(R.string.label_price_minus), getNumberFormat(partenerPrice)));
                orderActualMoney.setText(getString(R.string.label_no_sign_price, getNumberFormat(this.price - partenerPrice)));
            }
        }
        if (this.price-partenerPrice > 10000 && orderEntity.getItemTotal() >= 2) {
            RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) actualMountTv.getLayoutParams();
            rl.setMargins(0, ScreenUtil.dip2px(16), 0, ScreenUtil.dip2px(6));
            largeAmountTipTv.setVisibility(View.VISIBLE);
        } else {
            RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) actualMountTv.getLayoutParams();
            rl.setMargins(0, ScreenUtil.dip2px(16), 0, ScreenUtil.dip2px(16));
            largeAmountTipTv.setVisibility(View.GONE);
        }
    }

    private void gotoActivityByType(int type) {
        stat(type);
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
        addressLl.setVisibility(View.VISIBLE);
        editAddressRl.setVisibility(View.GONE);
        addressName.setText(name);
        addressPhone.setText(phone);
        addressInfo.setText(address);
        //这里要重新请求一下数据,因为地址变动
        if (!Util.isEmpty(kaolaSkuId.toString())) {
            getKaoLaInfo();
        }
        getIdentityFromAddress(String.valueOf(id));
    }

    private void getIdentityFromAddress(String addressId) {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.CERTIFICATION_ADDRESS, addressId));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<IdentityCardBean>() {
            @Override
            public void onResponse(IdentityCardBean response) {
                identityRl.setVisibility(View.VISIBLE);
                if (response.getData().getMemberCertification() != null && !Util.isEmpty(response.getData().getMemberCertification().getCardNo())) {
                    identityEt.setText(response.getData().getMemberCertification().getIdentityCard());
                    identityEt.setEnabled(false);
                    if (response.getData().getMemberCertification().getAuthenticate() == 1) {
                        tagAuth.setVisibility(View.VISIBLE);
                        identityLine.setVisibility(View.GONE);
                        tagTv.setVisibility(View.GONE);
                    } else {
                        tagAuth.setVisibility(View.GONE);
                        identityLine.setVisibility(View.VISIBLE);
                        tagTv.setVisibility(View.VISIBLE);
                    }
                } else {
                    identityEt.setEnabled(true);
                    identityEt.setText("");
                    tagAuth.setVisibility(View.GONE);
                    identityLine.setVisibility(View.VISIBLE);
                    tagTv.setVisibility(View.VISIBLE);
                }
                bean.getData().setMemberCertification(response.getData().getMemberCertification());
            }
        });
    }

    @Subscribe
    public void onEvent(ActionBean bean) {
        if (bean.type == Constants.ActionType.WXPAYRESULT) {
            int code = (int) bean.get("code");
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

    //检查权限
    public void showPop(int type) {//
        PackageManager pkgManager = getPackageManager();
        boolean cameraPermission =
                pkgManager.checkPermission(Manifest.permission.CAMERA, getPackageName()) == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= 23 && !cameraPermission) {
            requestPermission(type);
        } else {
            choosePic(type);
        }
    }

    private void requestPermission(int type) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                type);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.RequestCode.REQUEST_PERMISSION) {
            if ((grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                choosePic(requestCode);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void choosePic(int type) {
        Matisse.from(GlobalOrderBalanceActivity.this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .theme(R.style.Matisse_Dracula)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.d2cmall.buyer.fileprovider"))
                .maxSelectable(1)
                .gridExpectedSize(ScreenUtil.dip2px(120))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(type);
    }

    private void uploadFile(String filePath, int type) {
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        final Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(Params.BUCKET, Constants.UPYUN_SPACE);
        paramsMap.put(Params.SAVE_KEY, Util.getUPYunSavePath(user.getId(), Constants.TYPE_CUSTOMER));
        paramsMap.put(Params.RETURN_URL, "httpbin.org/post");
        UpCompleteListener completeListener = new UpCompleteListener() {
            @Override
            public void onComplete(boolean isSuccess, String result) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String url = jsonObject.optString("url");
                    if (type == 1) {
                        zUploadUrl = url;
                    } else {
                        fUploadUrl = url;
                    }
                    if (!Util.isEmpty(zUploadUrl) && !Util.isEmpty(fUploadUrl) && bean.getData().getMemberCertification() != null && bean.getData().getMemberCertification().getIsUpload() == 0) {
                        requestUploadIDCardPic(bean.getData().getMemberCertification().getId());
                    }
                } catch (JSONException e) {
                }
            }
        };
        SignatureListener signatureListener = new SignatureListener() {
            @Override
            public String getSignature(String raw) {
                return UpYunUtils.md5(raw + Constants.UPYUN_KEY);
            }
        };
        UploadManager.getInstance().blockUpload(file, paramsMap, signatureListener, completeListener, null);
    }

    @Override
    protected void onDestroy() {
        if (zmCertification != null) {
            zmCertification.setZMCertificationListener(null);
            zmCertification = null;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        super.onDestroy();
    }
}
