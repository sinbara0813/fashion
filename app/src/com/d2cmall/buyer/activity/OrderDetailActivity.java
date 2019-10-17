package com.d2cmall.buyer.activity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.CancelOrderApi;
import com.d2cmall.buyer.api.ConfirmReceiveApi;
import com.d2cmall.buyer.api.DeleteOrderApi;
import com.d2cmall.buyer.api.InsertCartApi;
import com.d2cmall.buyer.api.OrderAddCart;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.CheckOrderSkuBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.OrderInfoBean;
import com.d2cmall.buyer.bean.OrdersBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.DialogListener;
import com.d2cmall.buyer.util.CenterAlignImageSpan;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.AddCartDialog;
import com.d2cmall.buyer.widget.TaxDetialPop;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by rookie on 2017/9/2.
 * 未拆单的订单详情
 */

public class OrderDetailActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    ImageView titleRight;
    @Bind(R.id.title_layout)
    LinearLayout titleLayout;
    @Bind(R.id.tv_status)
    TextView tvStatus;
    @Bind(R.id.tv_declare)
    TextView tvDeclare;
    @Bind(R.id.iv_status)
    ImageView ivStatus;
    @Bind(R.id.tv_receive_name)
    TextView tvReceiveName;
    @Bind(R.id.tv_receive_contact)
    TextView tvReceiveContact;
    @Bind(R.id.tv_receive_address)
    TextView tvReceiveAddress;
    @Bind(R.id.order_container_layout)
    LinearLayout orderContainerLayout;
    @Bind(R.id.tv_order_id)
    TextView tvOrderId;
    @Bind(R.id.tv_copy)
    TextView tvCopy;
    @Bind(R.id.tv_create_time)
    TextView tvCreateTime;
    @Bind(R.id.tv_pay_way)
    TextView tvPayWay;
    @Bind(R.id.tv_copy_water_id)
    TextView tvCopyWaterId;
    @Bind(R.id.tv_remark)
    TextView tvRemark;
    @Bind(R.id.tv_order_total)
    TextView tvOrderTotal;
    @Bind(R.id.tv_order_discount)
    TextView tvOrderDiscount;
    @Bind(R.id.tv_order_promotion)
    TextView tvOrderPromotion;
    @Bind(R.id.tv_order_coupon)
    TextView tvOrderCoupon;
    @Bind(R.id.tv_order_pay_real)
    TextView tvOrderPayReal;
    @Bind(R.id.line_layout)
    View lineLayout;
    @Bind(R.id.tv_left_button)
    TextView tvLeftButton;
    @Bind(R.id.tv_right_button)
    TextView tvRightButton;
    @Bind(R.id.ll_bottom_button)
    LinearLayout llBottomButton;
    @Bind(R.id.btn_review)
    Button btnReview;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.tv_order_freight)
    TextView tvOrderFreight;
    @Bind(R.id.tv_bill)
    TextView tvBill;
    @Bind(R.id.ll_bill)
    LinearLayout llBill;
    @Bind(R.id.tv_order_red)
    TextView tvOrderRed;
    @Bind(R.id.ll_red)
    LinearLayout llRed;
    @Bind(R.id.tv_oversea_notice)
    TextView tv_notice;
    @Bind(R.id.ll_partner)
    LinearLayout ll_partner;
    @Bind(R.id.tv_order_partner)
    TextView tvPartner;
    @Bind(R.id.ll_help)
    LinearLayout llHelp;
    @Bind(R.id.ll_complain)
    LinearLayout llComplain;
    @Bind(R.id.btn_buy_again)
    TextView btnBuyAgain;
    @Bind(R.id.btn_review_order)
    TextView btnReviewOrder;
    @Bind(R.id.tv_tax_desc)
    TextView tvTaxDesc;
    @Bind(R.id.iv_tax_question)
    ImageView ivTaxQuestion;
    @Bind(R.id.tv_order_tax)
    TextView tvOrderTax;
    @Bind(R.id.ll_tax)
    LinearLayout llTax;
    @Bind(R.id.btn_cancel_order)
    TextView btnCancelOrder;
    @Bind(R.id.another_pay_tv)
    TextView anotherPayTv;
    private OrdersBean.DataEntity.OrdersEntity.ListEntity listEntity;
    private Dialog loadingDialog;
    private String orderSn;
    private TaxDetialPop taxDetialPop;
    private HashMap<Long, List<OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity>> orderItemByWarehouse;//将订单商品按照仓分组
    private String bargainEndTime;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (OrderDetailActivity.this.isFinishing()) { //Activity正在停止，则不再后续处理
                return;
            }
            String endTime = (String) msg.obj;
            showTimeDown(endTime);
            Message msg2 = Message.obtain();
            msg2.obj = endTime;
            handler.sendMessage(msg2);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        nameTv.setText("订单详情");
        orderSn = getIntent().getStringExtra("orderSn");
        //砍价待付款订单的倒计时,详情页的接口只抛了普通订单付款倒计时
        bargainEndTime = getIntent().getStringExtra("bargainEndTime");
        loadingDialog = DialogUtil.createLoadingDialog(this);
        progressBar.setVisibility(View.VISIBLE);
        orderItemByWarehouse = new HashMap<Long, List<OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity>>();
        requestOrderInfoTask();
    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        super.onDestroy();
    }

    private void requestOrderInfoTask() {
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.POST);
        api.setInterPath(String.format(Constants.ORDER_INFO_URL, orderSn));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderInfoBean>() {
            @Override
            public void onResponse(OrderInfoBean orderInfoBean) {
                progressBar.setVisibility(View.GONE);
                listEntity = orderInfoBean.getData().getOrder();
                if (listEntity.getItems() != null && listEntity.getItems().size() > 0) {//遍历商品,按仓分组
                    for (int i = 0; i < listEntity.getItems().size(); i++) { //遍历数据,按仓分组
                        if (!orderItemByWarehouse.containsKey(listEntity.getItems().get(i).getWarehouseId())) {
                            ArrayList<OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity> itemsEntities = new ArrayList<>();
                            itemsEntities.add(listEntity.getItems().get(i));
                            orderItemByWarehouse.put(listEntity.getItems().get(i).getWarehouseId(), itemsEntities);
                        } else {
                            orderItemByWarehouse.get(listEntity.getItems().get(i).getWarehouseId()).add(listEntity.getItems().get(i));
                        }
                    }
                }
                setOrderInfo();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(OrderDetailActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void showTimeDown(String endTime) {
        if (endTime != null) {//订单超时还没结束
            Date date = new Date();
            Date timeoutDate = Util.getDate(endTime);
            if (timeoutDate == null) {
                return;
            }
            if (date.before(timeoutDate)) {
                tvDeclare.setVisibility(View.VISIBLE);
                long millisUntil = timeoutDate.getTime() - date.getTime();
                int hours = (int) (millisUntil / (60 * 60 * 1000));
                int minutes = (int) ((millisUntil / (60 * 1000)) % 60);
                int seconds = (int) ((millisUntil / 1000) % 60);
                tvDeclare.setText(getString(R.string.label_order_detail_timeout,
                        hours, minutes / 10, minutes % 10, seconds / 10, seconds % 10));
            } else {//已超时，隐藏底部两个按钮
                tvDeclare.setText(R.string.label_order_timeover);
                tvStatus.setText("已取消");
                ivStatus.setImageResource(R.mipmap.icon_orderdetail_cancel);
                tvLeftButton.setVisibility(View.GONE);
                tvRightButton.setVisibility(View.GONE);
                anotherPayTv.setVisibility(View.GONE);
            }
        } else {//没有超时字段返回，走正常路径
            tvDeclare.setVisibility(View.GONE);
        }
    }

    private void setOrderInfo() {
        if (listEntity != null) {
            String provinceName = listEntity.getProvince();
            if (provinceName.equals("国外地区") || provinceName.equals("香港特别行政区") ||
                    provinceName.equals("澳门特别行政区") || provinceName.equals("台湾省")) {
                tv_notice.setVisibility(View.VISIBLE);
            } else {
                tv_notice.setVisibility(View.GONE);
            }
            tvOrderId.setText(listEntity.getOrderSn());
            tvReceiveName.setText(listEntity.getReciver());
            tvReceiveContact.setText(Util.hidePhoneNum(listEntity.getContact()));
            tvReceiveAddress.setText(listEntity.getAddress());
            if (listEntity.getPartnerAmount() > 0) {
                ll_partner.setVisibility(View.VISIBLE);
                tvPartner.setText(String.format(getString(R.string.label_price), Util.getNumberFormat(listEntity.getPartnerAmount())));
            } else {
                ll_partner.setVisibility(View.GONE);
            }

            if (listEntity.getTaxAmount() > 0) {
                llTax.setVisibility(View.VISIBLE);
                tvOrderTax.setText("+" + String.format(getString(R.string.label_price), Util.getNumberFormat(listEntity.getTaxAmount())));
            }
            if (!Util.isEmpty(listEntity.getDrawee())) {
                llBill.setVisibility(View.VISIBLE);
                tvBill.setText(getString(R.string.label_invoice_top_content, listEntity.getDrawee()));
            } else {
                llBill.setVisibility(View.GONE);
            }
            llBottomButton.setVisibility(View.GONE);
            tvStatus.setText(listEntity.getItems().get(0).getItemStatus());
//            switch (code){
//                case -3:
//                    status="平台取消";
//                    break;
//                case -2:
//                    status="售后完成";
//                    break;
//                case -1:
//                    status="用户取消";
//                    break;
//                case 0:
//                    status="待付款";
//                    break;
//                case 1:
//                    status="待发货";
//                    break;
//                case 2:
//                    status="已发货";
//                    break;
//                case 7:
//                    status="已签收";
//                    break;
//                case 8:
//                    status="交易完成";
//                    break;
//                default:
//                    status="未知";
//                    break;
//            }
            tvDeclare.setVisibility(View.GONE);

            if (listEntity.getOrderStatusName().equals(getString(R.string.label_waiting_payment))) {
                ivStatus.setImageResource(R.mipmap.icon_orderdetail_pay);
                //llBottomButton.setVisibility(View.VISIBLE);
                Message msg = Message.obtain();
                //砍价单使用砍价的倒计时
                if("bargain".equals(listEntity.getType()) && bargainEndTime!=null){
                    msg.obj = bargainEndTime;
                }else{
                    msg.obj = listEntity.getEndTime();
                }
                handler.sendMessage(msg);
//                tvLeftButton.setText("取消订单");
//                tvRightButton.setText("立即付款");

            }
            int j = 1;
            if (listEntity.isCancel()&&!tvStatus.getText().toString().equals("已取消")) {
                tvLeftButton.setText("取消订单");
                tvLeftButton.setVisibility(View.VISIBLE);
                j++;
            }
            if (listEntity.getOrderStatusCode() == 1&&!tvStatus.getText().toString().equals("已取消")) {
                j++;
                tvRightButton.setText("立即付款");
                anotherPayTv.setVisibility(View.VISIBLE);
                tvRightButton.setVisibility(View.VISIBLE);
            }
            if (listEntity.getOrderStatusCode() == -1 || listEntity.getOrderStatusCode() == -2 || listEntity.getOrderStatusCode() == -3) {
                j++;
                tvLeftButton.setText("删除订单");
                tvLeftButton.setVisibility(View.VISIBLE);
            }

            //Log.e("test","OrderDetailActivity:"+listEntity.getOrderStatusCode());
            if (listEntity.getOrderStatusCode() == 3) {
                ivStatus.setImageResource(R.mipmap.icon_orderdetail_send);
                if (listEntity.getItems().get(0).getEstimateDate() != null && !(Util.isEmpty(listEntity.getItems().get(0).getEstimateDate()))) {
                    Date d = DateUtil.strToDateLong(listEntity.getItems().get(0).getEstimateDate());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(d);
                    tvDeclare.setText(getString(R.string.label_deliver_time, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)));
                    tvDeclare.setVisibility(View.VISIBLE);
                }
            }
            if (listEntity.getOrderStatusName().equals("已发货")) {
                ivStatus.setImageResource(R.mipmap.icon_orderdetail_receive);
//                llBottomButton.setVisibility(View.VISIBLE);
//                tvLeftButton.setText("查看物流");
//                tvRightButton.setText("确认收货");
            }
            if (listEntity.getOrderStatusName().equals(getString(R.string.label_waiting_comment))) {
                ivStatus.setImageResource(R.mipmap.icon_orderdetail_recommend);
                llBottomButton.setVisibility(View.GONE);
                btnReviewOrder.setVisibility(View.VISIBLE);
                j++;
            }

            if (listEntity.getOrderStatusName().equals("售后")) {
                ivStatus.setImageResource(R.mipmap.icon_orderdetail_aftersale);
            }

            if (listEntity.getOrderStatusName().equals("超时关闭") || listEntity.getOrderStatusName().equals("用户取消")) {
                ivStatus.setImageResource(R.mipmap.icon_orderdetail_cancel);
            }

            if (j > 0) {
                llBottomButton.setVisibility(View.VISIBLE);
                //拼团中的订单隐藏再次购买按钮
                if("collage".equals(listEntity.getType()) && listEntity.getCollageStatus() == 1){
                    llBottomButton.setVisibility(View.GONE);
                }
            }
            orderContainerLayout.removeAllViews();

            final List<String> imgs = new ArrayList<>();
            if (listEntity.getItems() != null && listEntity.getItems().size() > 0) {
                orderContainerLayout.setVisibility(View.VISIBLE);
                Iterator iter = orderItemByWarehouse.entrySet().iterator();
                while (iter.hasNext()) {    //按仓遍历,动态添加布局
                    Map.Entry entry = (Map.Entry) iter.next();
                    loopAddOrderItemList(imgs, (List<OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity>) entry.getValue());    //
                }
            } else {
                orderContainerLayout.setVisibility(View.GONE);
            }

            tvCreateTime.setText(listEntity.getCreateDate());
            tvPayWay.setText(listEntity.getPaymentTypeName());//支付方式
            if (listEntity.getOrderStatusName().equals(getString(R.string.label_waiting_payment)) ||
                    listEntity.getPaymentType().equals("COD") ||
                    Util.isEmpty(listEntity.getPaymentSn())) {//待付款状态或货到付款或流水号为空
                tvCopyWaterId.setVisibility(View.GONE);
            } else {
                tvCopyWaterId.setVisibility(View.VISIBLE);
            }
            tvRemark.setText(listEntity.getMemo());
            tvOrderTotal.setText(Html.fromHtml(String.format(getString(R.string.label_order_total2), listEntity.getItems().size(),
                    Util.getNumberFormat(listEntity.getTotalAmount() + listEntity.getPromotionAmount()))));
            if (listEntity.getShippingRates() > 0) {
                tvOrderFreight.setText(String.format(getString(R.string.label_price_plus), Util.getNumberFormat(listEntity.getShippingRates())));
            } else {
                tvOrderFreight.setText(String.format(getString(R.string.label_price), Util.getNumberFormat(listEntity.getShippingRates())));
            }
            if (listEntity.getPromotionAmount() > 0) {
                tvOrderPromotion.setText(String.format(getString(R.string.label_price_minus), Util.getNumberFormat(listEntity.getPromotionAmount())));
            } else {
                tvOrderPromotion.setText(String.format(getString(R.string.label_price), Util.getNumberFormat(listEntity.getPromotionAmount())));
            }
            if (listEntity.getCouponAmount() > 0) {
                tvOrderCoupon.setText(String.format(getString(R.string.label_price_minus), Util.getNumberFormat(listEntity.getCouponAmount())));
            } else {
                tvOrderCoupon.setText(String.format(getString(R.string.label_price), Util.getNumberFormat(listEntity.getCouponAmount())));
            }
            if (listEntity.getRedAmount() > 0) {
                llRed.setVisibility(View.VISIBLE);
                tvOrderRed.setText(String.format(getString(R.string.label_price_minus), Util.getNumberFormat(listEntity.getRedAmount())));
            } else {
                llRed.setVisibility(View.GONE);
            }
            if (listEntity.getTotalPay() < 0) {
                tvOrderPayReal.setText(String.format(getString(R.string.label_price), Util.getNumberFormat(0)));
            } else {
                tvOrderPayReal.setText(String.format(getString(R.string.label_price), Util.getNumberFormat(listEntity.getTotalPay())));
            }

            tvOrderId.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (!Util.isEmpty(listEntity.getOrderSn())) {
                        ClipboardManager cm = (ClipboardManager) OrderDetailActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setPrimaryClip(ClipData.newPlainText("orderId", listEntity.getOrderSn()));
                        Util.showToast(OrderDetailActivity.this, R.string.label_order_id_copy);
                    }
                    return true;
                }
            });
            tvCopyWaterId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager cm = (ClipboardManager) OrderDetailActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText("paymentId", listEntity.getPaymentSn()));
                    Util.showToast(OrderDetailActivity.this, R.string.label_order_payment_id_copy);
                }
            });
            btnBuyAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkOrderSku(listEntity);
                }
            });
            tvLeftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tvLeftButton.getText().equals("取消订单")) {
                        requestCancelOrderTask();
                    } else if (tvLeftButton.getText().equals("查看物流")) {
                        if("KAOLA".equals(listEntity.getProductSource())){
                            Util.urlAction(OrderDetailActivity.this, String.format(Constants.KAOLA_WULIU_URL,
                                    listEntity.getItems().get(0).getDeliverySn(), listEntity.getItems().get(0).getDeliveryCorpCode(), listEntity.getItems().get(0).getProductImg(), listEntity.getItems().get(0).getId()));
                        }else{
                            Util.urlAction(OrderDetailActivity.this, String.format(Constants.WULIU_URL,
                                    listEntity.getItems().get(0).getDeliverySn(), listEntity.getItems().get(0).getDeliveryCorpCode(), listEntity.getItems().get(0).getProductImg()));
                        }

                    } else if (tvLeftButton.getText().equals("删除订单")) {
                        new AlertDialog.Builder(OrderDetailActivity.this)
                                .setMessage("确认删除该订单吗?")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestDeleteOrderTask((int) listEntity.getId());
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                    }
                }
            });
            tvRightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tvRightButton.getText().equals("立即付款")) {
                        gotoBuy(listEntity);
                    } else if (tvRightButton.getText().equals("确认收货")) {
                        new AlertDialog.Builder(OrderDetailActivity.this)
                                .setMessage(R.string.msg_confirm_receive)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ConfirmReceiveApi api = new ConfirmReceiveApi();
                                        api.setOrderItemId(listEntity.getItems().get(0).getId());
                                        loadingDialog.show();
                                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderInfoBean>() {
                                            @Override
                                            public void onResponse(OrderInfoBean orderInfoBean) {
                                                loadingDialog.dismiss();
                                                Util.showToast(OrderDetailActivity.this, R.string.msg_confirm_receive_ok);
                                                listEntity = orderInfoBean.getData().getOrder();
                                                setOrderInfo();
                                                EventBus.getDefault().post(listEntity);
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                loadingDialog.dismiss();
                                                Util.showToast(OrderDetailActivity.this, Util.checkErrorType(error));
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                    }
                }
            });
            tvCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager cm = (ClipboardManager) OrderDetailActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText("orderId", listEntity.getOrderSn()));
                    Util.showToast(OrderDetailActivity.this, R.string.label_order__id_copy);
                }
            });
        }
        anotherPayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderDetailActivity.this,AnotherPayActivity.class);
                intent.putExtra("orderSn",orderSn);
                startActivity(intent);
            }
        });
    }

    //订单按仓添加布局,非跨境订单添加布局
    private void loopAddOrderItemList(List<String> imgs, List<OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity> itemBean) {
        for (int j = 0; j < itemBean.size(); j++) {
            final OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity itemsEntity = itemBean.get(j);
            imgs.add(itemsEntity.getProductImg());
            View itemView = getLayoutInflater().inflate(R.layout.layout_order_detail_content, orderContainerLayout, false);
            ImageView imgItemPic = (ImageView) itemView.findViewById(R.id.iv_image);
            TextView btnShowAfterSale = (TextView) itemView.findViewById(R.id.btn_show_after_sale);
            TextView btnApplyAfterSale = (TextView) itemView.findViewById(R.id.btn_apply_after_sale);
            TextView btnShowLogistical = (TextView) itemView.findViewById(R.id.btn_show_logistical);
            TextView btnConfirmReceive = (TextView) itemView.findViewById(R.id.btn_confirm_receive);
            TextView btnShowComment = (TextView) itemView.findViewById(R.id.btn_show_comment);
            TextView btnGotoComment = (TextView) itemView.findViewById(R.id.btn_goto_comment);
            LinearLayout llBottom = (LinearLayout) itemView.findViewById(R.id.ll_bottom);
            imgItemPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OrderDetailActivity.this, ProductDetailActivity.class);
                    intent.putExtra("id", itemsEntity.getProductId());
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                }
            });
            TextView tvProductTitle = (TextView) itemView.findViewById(R.id.tv_product_name);
            tvProductTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OrderDetailActivity.this, ProductDetailActivity.class);
                    intent.putExtra("id", itemsEntity.getProductId());
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                }
            });
            TextView tvProductStyle = (TextView) itemView.findViewById(R.id.tv_product_style);
            TextView tvProductPrice = (TextView) itemView.findViewById(R.id.tv_price);
            TextView tvProductQuantity = (TextView) itemView.findViewById(R.id.tv_count);
            View emptyView = itemView.findViewById(R.id.empty_view);
            if (itemsEntity.getPromotionPrice() > 0) {
                tvProductPrice.setText(getString(R.string.label_price, Util.getNumberFormat(itemsEntity.getProductPrice() -
                        itemsEntity.getPromotionPrice() / itemsEntity.getQuantity())));
            } else {
                tvProductPrice.setText(getString(R.string.label_price, Util.getNumberFormat(itemsEntity.getProductPrice())));
            }
            if (listEntity.getItems().size() > 1) {
                for (int i = 0; i < listEntity.getItems().size(); i++) {
                    if (i != 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    }
                }
            }
            UniversalImageLoader.displayImage(this, Util.getD2cProductPicUrl(
                    itemsEntity.getProductImg(), ScreenUtil.dip2px(72), ScreenUtil.dip2px(108)), imgItemPic,
                    R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
            if (itemsEntity.getFlashPromotionId() != null && itemsEntity.getFlashPromotionId() > 0) {
                StringBuilder builder = new StringBuilder();
                if (!Util.isEmpty(itemsEntity.getProductName())) {
                    builder.append("   " + itemsEntity.getProductName());
                }
                SpannableString sb = new SpannableString(builder.toString());
                Drawable d = getResources().getDrawable(R.mipmap.icon_shopcart_xsg);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                sb.setSpan(new CenterAlignImageSpan(d), 0, 1, ImageSpan.ALIGN_BASELINE);
                tvProductTitle.setText(sb);
            } else if (itemsEntity.getIsTaxation() == 1 && "CROSS".equals(itemsEntity.getProductTradeType())) {
                StringBuilder builder = new StringBuilder();
                if (!Util.isEmpty(itemsEntity.getProductName())) {
                    builder.append("   " + itemsEntity.getProductName());
                }
                SpannableString sb = new SpannableString(builder.toString());
                Drawable d = getResources().getDrawable(R.mipmap.icon_freetax);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                sb.setSpan(new CenterAlignImageSpan(d), 0, 1, ImageSpan.ALIGN_BASELINE);
                tvProductTitle.setText(sb);
            } else if (itemsEntity.getType().equals("collage")) {
                //拼团商品
                StringBuilder builder = new StringBuilder();
                if (!Util.isEmpty(itemsEntity.getProductName())) {
                    builder.append("   " + itemsEntity.getProductName());
                }
                SpannableString sb = new SpannableString(builder.toString());
                Drawable d;
                //参与拼团就有订单但是不一定成团
                if(itemsEntity.getCollageStatus()==1  &&  !(itemsEntity.getItemStatusCode()<0)){
                    d = getResources().getDrawable(R.mipmap.icon_pintuanzhong);
                }else{
                    d = getResources().getDrawable(R.mipmap.icon_pintuan);
                }
                d.setBounds(0, -ScreenUtil.dip2px(6), d.getIntrinsicWidth()-ScreenUtil.dip2px(4), d.getIntrinsicHeight()-ScreenUtil.dip2px(10));
                sb.setSpan(new CenterAlignImageSpan(d), 0, 1, ImageSpan.ALIGN_BASELINE);
                tvProductTitle.setText(sb);
            } else {
                tvProductTitle.setText(itemsEntity.getProductName());
            }
            tvProductStyle.setText(getString(R.string.label_kongge, itemsEntity.getColor(),
                    itemsEntity.getSize()));
            tvProductQuantity.setText(getString(R.string.label_product_quantity, itemsEntity.getQuantity()));

            int i = 0;
            //确认收货
            if (itemsEntity.getItemStatusCode() == 2) {
                i++;
                btnConfirmReceive.setVisibility(View.VISIBLE);
            } else {
                btnConfirmReceive.setVisibility(View.GONE);
            }
            //评价晒单
            if (itemsEntity.getIsComment() == 1 && itemsEntity.getAftering().equals("none")) {
                i++;
                btnGotoComment.setVisibility(View.VISIBLE);
                if (listEntity.getItems().size() == 1) {
                    //llBottomButton.setVisibility(View.GONE);
                    btnGotoComment.setVisibility(View.GONE);
                    i--;
                    btnReviewOrder.setVisibility(View.VISIBLE);
                }
            } else {
                btnGotoComment.setVisibility(View.GONE);
            }
            //查看评价
            if (itemsEntity.getCommentId() > 0) {
                i++;
                btnShowComment.setVisibility(View.VISIBLE);
            } else {
                btnShowComment.setVisibility(View.GONE);
            }
            //查看物流
            if (!Util.isEmpty(itemsEntity.getDeliverySn())) {
                i++;
                btnShowLogistical.setVisibility(View.VISIBLE);
            } else {
                btnShowLogistical.setVisibility(View.GONE);
            }
            //申请售后
            boolean IsApplyAfterSale = false;
            if (!listEntity.getType().equals("distribution")) {
                if (listEntity.getOrderStatusCode() >= 2) {
                    if (listEntity.getPaymentType().equals("COD")) {
                        if (itemsEntity.getItemStatusCode() >= 7) {
                            IsApplyAfterSale = true;
                        }
                    } else if (itemsEntity.getItemStatusCode() >= 1) {
                        IsApplyAfterSale = true;
                    }
                }
            }
            if (IsApplyAfterSale && itemsEntity.getAftering().equals("none") &&
                    itemsEntity.getItemStatusCode() != 8 && itemsEntity.isAfterAvail()) {
                i++;
                btnApplyAfterSale.setVisibility(View.VISIBLE);
            } else {
                btnApplyAfterSale.setVisibility(View.GONE);
            }
            //售后跟踪
            if (!listEntity.getType().equals("distribution") &&
                    !Util.isEmpty(itemsEntity.getAftering()) && !itemsEntity.getAftering().equals("none")) {
                i++;
                btnShowAfterSale.setVisibility(View.VISIBLE);
            } else {
                btnShowAfterSale.setVisibility(View.GONE);
            }
            if (i > 0) {
                llBottom.setVisibility(View.VISIBLE);
            } else {
                llBottom.setVisibility(View.GONE);
            }
            //拼团单拼团中底部按钮不可操作
            if (itemsEntity.getType().equals("collage")) {
                if(itemsEntity.getCollageStatus()==1){
                    llBottom.setVisibility(View.GONE);
                    llBottomButton.setVisibility(View.GONE);
                }
            }

            btnConfirmReceive.setOnClickListener(new ConfirmReceiveListener(itemsEntity));
            btnShowLogistical.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if("KAOLA".equals(itemsEntity.getProductSource())){
                        Util.urlAction(OrderDetailActivity.this, String.format(Constants.KAOLA_WULIU_URL,
                                itemsEntity.getDeliverySn(), itemsEntity.getDeliveryCorpCode(), itemsEntity.getProductImg(), itemsEntity.getId()));
                    }else{
                        Util.urlAction(OrderDetailActivity.this, String.format(Constants.WULIU_URL,
                                itemsEntity.getDeliverySn(), itemsEntity.getDeliveryCorpCode(), itemsEntity.getProductImg()));
                    }

                }
            });
            btnGotoComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OrderDetailActivity.this, ReviewOrderActivity.class);
                    intent.putExtra("id", itemsEntity.getId());
                    intent.putExtra("productUrl", itemsEntity.getProductImg());
                    intent.putExtra("productInfo", itemsEntity.getProductName());
                    intent.putExtra("price", itemsEntity.getProductPrice());
                    intent.putExtra("style", getString(R.string.label_kongge, itemsEntity.getColor(),
                            itemsEntity.getSize()));
                    intent.putExtra("intentFlag", 1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                }
            });
            btnShowComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OrderDetailActivity.this, ShowCommendActivity.class);
                    intent.putExtra("id", itemsEntity.getCommentId());
                    intent.putExtra("productId", itemsEntity.getProductId());
                    intent.putExtra("price", String.valueOf(itemsEntity.getTotalPrice()));
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                }
            });
            btnApplyAfterSale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OrderDetailActivity.this, ApplyAfterSaleActivity.class);
                    intent.putExtra("orderSn", listEntity.getOrderSn());
                    intent.putExtra("orderId", listEntity.getId());
                    intent.putExtra("statusCode", listEntity.getOrderStatusCode());
                    intent.putExtra("bean", itemsEntity);
                    intent.putExtra("intentFlag", 1);
                    intent.putExtra("isKaoLa", "KAOLA".equals(itemsEntity.getProductSource()));//是不是考拉
                    startActivityForResult(intent, 999);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                }
            });
            btnShowAfterSale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemsEntity.getAftering().equals("exchange")) {
                        gotoAfterSale(1);
                    } else if (itemsEntity.getAftering().equals("reship")) {
                        gotoAfterSale(0);
                    } else if (itemsEntity.getAftering().equals("refund")) {
                        gotoAfterSale(2);
                    }
                }
            });
            //如果是考拉费跨境和D2C商品一起下单,WarehouseId=0的是D2C商品
            if (j == 0 && itemBean.get(0).getWarehouseId() > 0) {  //按仓分组每组第一条
                RelativeLayout orderGroupTopView = (RelativeLayout) LayoutInflater.from(this).inflate(
                        R.layout.layout_order_group_top_view, null);
                TextView tvWarehouseName = (TextView) orderGroupTopView.findViewById(R.id.tv_warehouse_name);
                tvWarehouseName.setText(itemsEntity.getWarehouseName());
                orderContainerLayout.addView(orderGroupTopView);
            }

            orderContainerLayout.addView(itemView);//动态添加的每个Item布局

            if (j == itemBean.size() - 1) { //按仓分组每组最后一条或部分仓的最后一条
                View view = new View(this);
                int displayWidth = ScreenUtil.getDisplayWidth();
                int hight = ScreenUtil.dip2px(10);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(displayWidth, hight);
                layoutParams.width = ScreenUtil.getDisplayWidth();
                layoutParams.height = ScreenUtil.dip2px(10);
                view.setLayoutParams(layoutParams);
                view.setBackgroundColor(getResources().getColor(R.color.bg_color));
                orderContainerLayout.addView(view);
            }
        }
    }

    private void checkOrderSku(final OrdersBean.DataEntity.OrdersEntity.ListEntity listEntity) {
        if (listEntity.getItems().size() == 1) {//直接加入购物车
            InsertCartApi api = new InsertCartApi();
            api.setNum(listEntity.getItems().get(0).getQuantity());
            api.setSkuId(listEntity.getItems().get(0).getSkuId());
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    Intent intent = new Intent(OrderDetailActivity.this, CartActivity.class);
                    final ArrayList<Integer> skuids = new ArrayList<>();
                    skuids.add((int) listEntity.getItems().get(0).getSkuId());
                    intent.putIntegerArrayListExtra("skuid", skuids);
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Util.showToast(OrderDetailActivity.this, Util.checkErrorType(error));
                }
            });
        } else {
            SimpleApi api = new SimpleApi();
            api.setInterPath(String.format(Constants.CHECK_ORDER_SKU, orderSn));
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CheckOrderSkuBean>() {
                @Override
                public void onResponse(CheckOrderSkuBean response) {
                    if (response.getData().getDisable() != null && response.getData().getDisable().size() > 0) {
                        if (response.getData().getAvailable() == null || response.getData().getAvailable().size() == 0) {
                            Toast.makeText(OrderDetailActivity.this, "订单中的商品卖光啦，再看看其它商品吧~", Toast.LENGTH_SHORT).show();
                        } else {
                            int size = response.getData().getDisable().size();
                            int size1 = response.getData().getAvailable().size();
                            List<String> imgs = new ArrayList<>();
                            final ArrayList<Integer> skuids = new ArrayList<>();
                            final ArrayList<Integer> nums = new ArrayList<>();
                            for (int i = 0; i < size; i++) {
                                imgs.add(response.getData().getDisable().get(i).getImg());
                            }
                            for (int i = 0; i < size1; i++) {
                                skuids.add((int) response.getData().getAvailable().get(i).getSkuId());
                                nums.add(getNum(response.getData().getAvailable().get(i).getSkuId()));
                            }
                            AddCartDialog addCartDialog = new AddCartDialog(OrderDetailActivity.this);
                            addCartDialog.show();
                            addCartDialog.setImageUrl(imgs);
                            addCartDialog.setDialogListener(new DialogListener() {
                                @Override
                                public void showDialog() {
                                    addCartForOrder(nums, skuids);
                                }

                                @Override
                                public void dismissDialog() {

                                }
                            });
                        }
                    } else {
                        int size = response.getData().getAvailable().size();
                        final ArrayList<Integer> skuids = new ArrayList<>();
                        final ArrayList<Integer> nums = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
                            skuids.add((int) response.getData().getAvailable().get(i).getSkuId());
                            nums.add(getNum(response.getData().getAvailable().get(i).getSkuId()));
                        }
                        addCartForOrder(nums, skuids);
                    }
                }
            });
        }
    }

    private int getNum(long skuid) {
        int size = listEntity.getItems().size();
        for (int i = 0; i < size; i++) {
            if (skuid == listEntity.getItems().get(i).getSkuId()) {
                return listEntity.getItems().get(i).getQuantity();
            }
        }
        return 1;
    }

    private void addCartForOrder(final ArrayList<Integer> nums, final ArrayList<Integer> skuids) {
        OrderAddCart addCart = new OrderAddCart();
        StringBuilder builder = new StringBuilder();
        StringBuilder builder1 = new StringBuilder();
        int size = nums.size();
        for (int i = 0; i < size; i++) {
            builder.append(nums.get(i));
            builder1.append(skuids.get(i));
            if (i != size - 1) {
                builder.append(",");
                builder1.append(",");
            }
        }
        addCart.nums = builder.toString();
        addCart.skuIds = builder1.toString();
        D2CApplication.httpClient.loadingRequest(addCart, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                Intent intent = new Intent(OrderDetailActivity.this, CartActivity.class);
                intent.putIntegerArrayListExtra("skuid", skuids);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(OrderDetailActivity.this, Util.checkErrorType(error));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999 && resultCode == RESULT_OK) {
            progressBar.setVisibility(View.VISIBLE);
            requestOrderInfoTask();
            Intent intent = new Intent(this, AllAfterSaleActivity.class);
            intent.putExtra("position", 0);
            startActivity(intent);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void requestDeleteOrderTask(final int id) {
        DeleteOrderApi api = new DeleteOrderApi();
        api.setOrderId(id);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                Util.showToast(OrderDetailActivity.this, "订单已删除");
                EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.DELETE_ORDER));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(OrderDetailActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void requestCancelOrderTask() {
        CancelOrderApi api = new CancelOrderApi();
        api.setOrderSn(listEntity.getOrderSn());
        loadingDialog.show();
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderInfoBean>() {
            @Override
            public void onResponse(OrderInfoBean orderInfoBean) {
                loadingDialog.dismiss();
                Util.showToast(OrderDetailActivity.this, R.string.msg_cancel_order_ok);
                listEntity = orderInfoBean.getData().getOrder();
                setOrderInfo();
                EventBus.getDefault().post(listEntity);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Util.showToast(OrderDetailActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void gotoBuy(OrdersBean.DataEntity.OrdersEntity.ListEntity listEntity) {
        Intent intent = new Intent(this, CashierActivity.class);
        StringBuilder designId = new StringBuilder();
        if (listEntity.getItems() != null && listEntity.getItems().size() > 0) {
            for (int i = 0; i < listEntity.getItems().size(); i++) {
                designId.append(listEntity.getItems().get(i).getDesignerId());
                if (i != listEntity.getItems().size() - 1) {
                    designId.append(",");
                }
            }
        }
        boolean isCod = false;
        if (listEntity.getItems() != null && listEntity.getItems().size() > 0) {
            for (int i = 0; i < listEntity.getItems().size(); i++) {
                if (listEntity.getItems().get(i).getIsCod() == 0) {
                    isCod = true;
                }
            }
        }
        if (listEntity.getAddress().matches("(台湾" + "|香港" + "|澳门" + "|国外)\\S+")) {
            intent.putExtra("isSupportAddress", true);
        }
        intent.putExtra("isCod", isCod);
        intent.putExtra("designId", designId.toString());
        intent.putExtra("id", listEntity.getOrderSn());
        intent.putExtra("price", listEntity.getTotalPay());
        intent.putExtra("subject", getBillSubject());
        intent.putExtra("payStyle", listEntity.getPayParams());
        startActivity(intent);
    }

    public String getBillSubject() {
        String productName = listEntity.getItems().get(0).getProductName();
        if (productName.length() > 20) {
            productName = productName.substring(0, 20);
        }
        String productSn = listEntity.getItems().get(0).getProductSn();
        String subject = "D2C-" + productSn + "-" + productName;
        return subject;
    }

    private void gotoAfterSale(int position) {
        Intent intent = new Intent(OrderDetailActivity.this, AllAfterSaleActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
    }

    private class ConfirmReceiveListener implements View.OnClickListener {

        private OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity itemsEntity;

        public ConfirmReceiveListener(OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity itemsEntity) {
            super();
            this.itemsEntity = itemsEntity;
        }

        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(OrderDetailActivity.this)
                    .setMessage(R.string.msg_confirm_receive)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestConfirmReceiveTask();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }

        private void requestConfirmReceiveTask() {
            ConfirmReceiveApi api = new ConfirmReceiveApi();
            api.setOrderItemId(itemsEntity.getId());
            loadingDialog.show();
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderInfoBean>() {
                @Override
                public void onResponse(OrderInfoBean orderInfoBean) {
                    loadingDialog.dismiss();
                    Util.showToast(OrderDetailActivity.this, R.string.msg_confirm_receive_ok);
                    listEntity = orderInfoBean.getData().getOrder();
                    setOrderInfo();
                    EventBus.getDefault().post(listEntity);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loadingDialog.dismiss();
                    Util.showToast(OrderDetailActivity.this, Util.checkErrorType(error));
                }
            });
        }
    }

    @OnClick({R.id.ll_help, R.id.ll_complain, R.id.back_iv, R.id.title_right, R.id.tv_copy, R.id.tv_copy_water_id
            , R.id.tv_left_button, R.id.tv_right_button, R.id.btn_review_order, R.id.tv_tax_desc, R.id.iv_tax_question})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_help:
                Intent intent2 = new Intent(this, HelpActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_complain:
                Intent intent1 = new Intent(this, ComplaintActivity.class);
                startActivity(intent1);
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_right:
                if (Util.loginChecked(this, 999)) {
                    String title = "线上客服";
                    String url = "http://www.d2cmall.com";
                    ConsultSource source = new ConsultSource(url, title, "订单详情");
                    source.groupId = Constants.QIYU_AF_GROUP_ID;
                    source.robotFirst = true;
                    Unicorn.openServiceActivity(this, "D2C客服", source);
                    //合力亿捷
//                    Intent intent = new Intent(this,CustomServiceActivity.class);
//                    intent.putExtra("skillGroupId", Constants.HLYJ_BF_AF_GROUP_ID);
//                    startActivity(intent);
                }
                break;
            case R.id.tv_copy:
                break;
            case R.id.tv_copy_water_id:
                break;
            case R.id.tv_left_button:
                break;
            case R.id.tv_right_button:
                break;
            case R.id.btn_review_order:
                Intent intent = new Intent(OrderDetailActivity.this, ReviewOrderActivity.class);
                intent.putExtra("id", listEntity.getItems().get(0).getId());
                intent.putExtra("productUrl", listEntity.getItems().get(0).getProductImg());
                intent.putExtra("productInfo", listEntity.getItems().get(0).getProductName());
                intent.putExtra("price", listEntity.getItems().get(0).getProductPrice());
                intent.putExtra("style", getString(R.string.label_kongge, listEntity.getItems().get(0).getColor(),
                        listEntity.getItems().get(0).getSize()));
                intent.putExtra("intentFlag", 1);
                startActivity(intent);
                break;
            case R.id.tv_tax_desc:  //税费
            case R.id.iv_tax_question:
//                tvTaxDesc.setEnabled(false);
//                ivTaxQuestion.setEnabled(false);
//                if(taxDetialPop==null){
//                    taxDetialPop = new TaxDetialPop(this);
//                }
//                taxDetialPop.show(getWindow().getDecorView());
//                ivTaxQuestion.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(taxDetialPop!=null){
//                            taxDetialPop.dismiss();
//                            tvTaxDesc.setEnabled(true);
//                            ivTaxQuestion.setEnabled(true);
//                        }
//                    }
//                },2000);
                break;
        }
    }
}
