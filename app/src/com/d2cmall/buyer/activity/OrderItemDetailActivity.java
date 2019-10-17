package com.d2cmall.buyer.activity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.ConfirmReceiveApi;
import com.d2cmall.buyer.api.InsertCartApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.OrderInfoBean;
import com.d2cmall.buyer.bean.OrderItemBean;
import com.d2cmall.buyer.bean.OrdersBean;
import com.d2cmall.buyer.bean.TaskPointBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.CenterAlignImageSpan;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.TaxDetialPop;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by rookie on 2017/9/27.
 * 拆完单的订单详情
 */

public class OrderItemDetailActivity extends BaseActivity {

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
    @Bind(R.id.tv_bill)
    TextView tvBill;
    @Bind(R.id.ll_bill)
    LinearLayout llBill;
    @Bind(R.id.tv_remark)
    TextView tvRemark;
    @Bind(R.id.tv_order_total)
    TextView tvOrderTotal;
    @Bind(R.id.tv_order_freight)
    TextView tvOrderFreight;
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
    @Bind(R.id.btn_cancel_order)
    TextView btnCancelOrder;
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
    @Bind(R.id.tv_order_red)
    TextView tvOrderRed;
    @Bind(R.id.ll_red)
    LinearLayout llRed;
    @Bind(R.id.tv_oversea_notice)
    TextView tv_notice;
    @Bind(R.id.tv_order_partner)
    TextView tvOrderPartner;
    @Bind(R.id.ll_partner)
    LinearLayout llPartner;
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
    private String orderSn;
    private OrderItemBean.DataBean.ItemBean listEntity;
    private Dialog loadingDialog;
    private OrderItemBean.DataBean.ItemBean.OrderBean data;
    private TaxDetialPop taxDetialPop;
    private int point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item_detail);
        ButterKnife.bind(this);
        nameTv.setText("订单详情");
        orderSn = getIntent().getStringExtra("orderSn");
        //data= (OrdersBean.DataEntity.OrdersEntity.ListEntity) getIntent().getSerializableExtra("orderDetail");
        loadingDialog = DialogUtil.createLoadingDialog(this);
        requestOrderInfoTask();
    }


    private void requestOrderInfoTask() {
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.POST);
        api.setInterPath(String.format(Constants.ORDER_ITEM_DETAIL_URL, orderSn));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderItemBean>() {
            @Override
            public void onResponse(OrderItemBean bean) {
                progressBar.setVisibility(View.GONE);
                listEntity = bean.getData().getItem();
                data = listEntity.getOrder();
                if (isFinishing()) {
                    return;
                }
                if(listEntity.getIsComment()>0 && listEntity.getAftering().equals("none")){
                    loadPoint();
                }else{
                    setOrderInfo();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(OrderItemDetailActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void loadPoint() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.COMMENT_ORDER_POINT,"PRODUCT_COMMENT"));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<TaskPointBean>() {
            @Override
            public void onResponse(TaskPointBean taskPointBean) {
                if (taskPointBean != null && taskPointBean.getList() != null && taskPointBean.getList().get(0) != null ) {
                    point = taskPointBean.getList().get(0).getPoint();
                }
                if(isFinishing()){
                    return;
                }
                setOrderInfo();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(isFinishing()){
                    return;
                }
                setOrderInfo();
            }
        });
    }

    private void setOrderInfo() {
        if (listEntity != null) {
            String provinceName = listEntity.getOrder().getProvince();
            if (provinceName.equals("国外地区") || provinceName.equals("香港特别行政区") ||
                    provinceName.equals("澳门特别行政区") || provinceName.equals("台湾省")) {
                tv_notice.setVisibility(View.VISIBLE);
            } else {
                tv_notice.setVisibility(View.GONE);
            }
            tvOrderId.setText(listEntity.getOrderSn());
            tvReceiveName.setText(data.getReciver());//收货人
            tvReceiveContact.setText(Util.hidePhoneNum(data.getContact()));//电话
            tvReceiveAddress.setText(data.getProvince() + data.getCity() + data.getStreet());//地址
            if (!Util.isEmpty(data.getDrawee())) {//发票
                llBill.setVisibility(View.VISIBLE);
                tvBill.setText(getString(R.string.label_invoice_top_content, data.getDrawee()));
            } else {
                llBill.setVisibility(View.GONE);
            }
            if (listEntity.getPartnerAmount() > 0) {
                llPartner.setVisibility(View.VISIBLE);
                tvOrderPartner.setText(String.format(getString(R.string.label_price), Util.getNumberFormat(listEntity.getPartnerAmount())));
            } else {
                llPartner.setVisibility(View.GONE);
            }
            llBottomButton.setVisibility(View.GONE);
            tvStatus.setText(listEntity.getItemStatus());
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
            int j = 1;
            if (data.isCancel()) {
                tvLeftButton.setText("取消订单");
                tvLeftButton.setVisibility(View.VISIBLE);
                j++;
            }
//            if (data.getOrderStatusCode() == -1 || data.getOrderStatusCode() == -2 ||data.getOrderStatusCode() == -3) {
//                j++;
//                tvLeftButton.setText("删除订单");
//                tvLeftButton.setVisibility(View.VISIBLE);
//            }
            //拼团中的订单隐藏再次购买按钮
            if("collage".equals(listEntity.getType()) && listEntity.getCollageStatus() == 1){
                btnBuyAgain.setVisibility(View.GONE);
                j--;
            }
            if (j > 0) {
                llBottomButton.setVisibility(View.VISIBLE);
            }
            if (listEntity.getItemStatusCode() == 1) {
                ivStatus.setImageResource(R.mipmap.icon_orderdetail_send);
                if (listEntity.getEstimateDate() != null && !(Util.isEmpty(listEntity.getEstimateDate()))) {
                    Date d = DateUtil.strToDateLong(listEntity.getEstimateDate());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(d);
                    tvDeclare.setText(getString(R.string.label_deliver_time, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)));
                    tvDeclare.setVisibility(View.VISIBLE);
                }
            }

            if (listEntity.getItemStatus().equals("已发货")) {
                ivStatus.setImageResource(R.mipmap.icon_orderdetail_receive);
            }
            if (listEntity.getItemStatus().equals(getString(R.string.label_waiting_comment))) {
                ivStatus.setImageResource(R.mipmap.icon_orderdetail_recommend);
                llBottomButton.setVisibility(View.GONE);
                if(point>0){
                    ViewGroup.LayoutParams layoutParams = btnReviewOrder.getLayoutParams();
                    layoutParams.width= ScreenUtil.dip2px(100);
                    btnReviewOrder.setText(getString(R.string.label_comment_reward,point));
                    btnReviewOrder.setLayoutParams(layoutParams);
                }else{
                    ViewGroup.LayoutParams layoutParams = btnReviewOrder.getLayoutParams();
                    layoutParams.width= ScreenUtil.dip2px(80);
                    btnReviewOrder.setLayoutParams(layoutParams);
                    btnReviewOrder.setText(getString(R.string.label_goto_comment));
                }
                btnReviewOrder.setVisibility(View.VISIBLE);
            } else {
                btnReviewOrder.setVisibility(View.GONE);
            }

            if (listEntity.getItemStatus().equals("售后")) {
                ivStatus.setImageResource(R.mipmap.icon_orderdetail_aftersale);
            }

            if (listEntity.getItemStatus().equals("超时关闭") || listEntity.getItemStatus().equals("用户取消")) {
                ivStatus.setImageResource(R.mipmap.icon_orderdetail_cancel);
            }
            //税费
            if (listEntity.getTaxAmount() > 0) {
                llTax.setVisibility(View.VISIBLE);
                tvOrderTax.setText(String.format(getString(R.string.label_price_plus), Util.getNumberFormat(listEntity.getTaxAmount())));
            }
            orderContainerLayout.removeAllViews();
            orderContainerLayout.setVisibility(View.VISIBLE);
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
                    Intent intent = new Intent(OrderItemDetailActivity.this, ProductDetailActivity.class);
                    intent.putExtra("id", (long) listEntity.getProductId());
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                }
            });
            TextView tvProductTitle = (TextView) itemView.findViewById(R.id.tv_product_name);
            tvProductTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OrderItemDetailActivity.this, ProductDetailActivity.class);
                    intent.putExtra("id", (long) listEntity.getProductId());
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                }
            });
            TextView tvProductStyle = (TextView) itemView.findViewById(R.id.tv_product_style);
            TextView tvProductPrice = (TextView) itemView.findViewById(R.id.tv_price);
            TextView tvProductQuantity = (TextView) itemView.findViewById(R.id.tv_count);
            View emptyView = itemView.findViewById(R.id.empty_view);
            if (listEntity.getPromotionPrice() > 0) {
                tvProductPrice.setText(getString(R.string.label_price, Util.getNumberFormat(listEntity.getProductPrice() -
                        listEntity.getPromotionPrice() / listEntity.getQuantity())));
            } else {
                tvProductPrice.setText(getString(R.string.label_price, Util.getNumberFormat(listEntity.getProductPrice())));
            }
            UniversalImageLoader.displayImage(this, Util.getD2cProductPicUrl(
                    listEntity.getProductImg(), ScreenUtil.dip2px(72), ScreenUtil.dip2px(108)), imgItemPic,
                    R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
            if (listEntity.getFlashPromotionId() != null && listEntity.getFlashPromotionId() > 0) {
                StringBuilder builder = new StringBuilder();
                if (!Util.isEmpty(listEntity.getProductName())) {
                    builder.append("   " + listEntity.getProductName());
                }
                SpannableString sb = new SpannableString(builder.toString());
                Drawable d = getResources().getDrawable(R.mipmap.icon_shopcart_xsg);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                sb.setSpan(new CenterAlignImageSpan(d), 0, 1, ImageSpan.ALIGN_BASELINE);
                tvProductTitle.setText(sb);
            } else if (listEntity.getIsTaxation() == 1 && "CROSS".equals(listEntity.getProductTradeType())) {
                StringBuilder builder = new StringBuilder();
                if (!Util.isEmpty(listEntity.getProductName())) {
                    builder.append("   " + listEntity.getProductName());
                }
                SpannableString sb = new SpannableString(builder.toString());
                Drawable d = getResources().getDrawable(R.mipmap.icon_freetax);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                sb.setSpan(new CenterAlignImageSpan(d), 0, 1, ImageSpan.ALIGN_BASELINE);
                tvProductTitle.setText(sb);
            } else if (listEntity.getType().equals("collage")) {
                StringBuilder builder = new StringBuilder();
                if (!Util.isEmpty(listEntity.getProductName())) {
                    builder.append("   " + listEntity.getProductName());
                }
                SpannableString sb = new SpannableString(builder.toString());
                Drawable d;
                //参与拼团就有订单但是不一定成团
                if(listEntity.getCollageStatus()==1  &&  !(listEntity.getItemStatusCode()<0)){
                    d = getResources().getDrawable(R.mipmap.icon_pintuanzhong);
                }else{
                    d = getResources().getDrawable(R.mipmap.icon_pintuan);
                }
                d.setBounds(0, -ScreenUtil.dip2px(6), d.getIntrinsicWidth()-ScreenUtil.dip2px(4), d.getIntrinsicHeight()-ScreenUtil.dip2px(10));
                sb.setSpan(new CenterAlignImageSpan(d), 0, 1, ImageSpan.ALIGN_BASELINE);
                tvProductTitle.setText(sb);
            } else {
                tvProductTitle.setText(listEntity.getProductName());
            }
            tvProductStyle.setText(getString(R.string.label_kongge, listEntity.getColor(),
                    listEntity.getSize()));
            tvProductQuantity.setText(getString(R.string.label_product_quantity, listEntity.getQuantity()));

            int i = 0;
            //确认收货
            if (listEntity.getItemStatusCode() == 2) {
                i++;
                btnConfirmReceive.setVisibility(View.VISIBLE);
            } else {
                btnConfirmReceive.setVisibility(View.GONE);
            }
            //评价晒单
            if (listEntity.getIsComment() == 1 && listEntity.getAftering().equals("none")) {
                i++;
                if(point>0){
                    ViewGroup.LayoutParams layoutParams = btnGotoComment.getLayoutParams();
                    layoutParams.width= ScreenUtil.dip2px(100);
                    btnGotoComment.setText(getString(R.string.label_comment_reward,point));
                    btnGotoComment.setLayoutParams(layoutParams);
                }else{
                    ViewGroup.LayoutParams layoutParams = btnGotoComment.getLayoutParams();
                    layoutParams.width= ScreenUtil.dip2px(80);
                    btnGotoComment.setLayoutParams(layoutParams);
                    btnGotoComment.setText(getString(R.string.label_goto_comment));
                }
                btnGotoComment.setVisibility(View.VISIBLE);
                if (listEntity != null) {
                    btnGotoComment.setVisibility(View.GONE);
                    i--;
                    btnReviewOrder.setVisibility(View.VISIBLE);
                    if(point>0){
                        ViewGroup.LayoutParams layoutParams = btnReviewOrder.getLayoutParams();
                        layoutParams.width= ScreenUtil.dip2px(100);
                        btnReviewOrder.setText(getString(R.string.label_comment_reward,point));
                        btnReviewOrder.setLayoutParams(layoutParams);
                    }else{
                        ViewGroup.LayoutParams layoutParams = btnReviewOrder.getLayoutParams();
                        layoutParams.width= ScreenUtil.dip2px(80);
                        btnReviewOrder.setLayoutParams(layoutParams);
                        btnReviewOrder.setText(getString(R.string.label_goto_comment));
                    }
                }
            } else {
                btnGotoComment.setVisibility(View.GONE);
                btnReviewOrder.setVisibility(View.GONE);
            }
            //查看评价
            if (listEntity.getCommentId() > 0) {
                i++;
                btnShowComment.setVisibility(View.VISIBLE);
            } else {
                btnShowComment.setVisibility(View.GONE);
            }
            //查看物流
            if (!Util.isEmpty(listEntity.getDeliverySn())) {
                i++;
                btnShowLogistical.setVisibility(View.VISIBLE);
            } else {
                btnShowLogistical.setVisibility(View.GONE);
            }
            //申请售后
            boolean IsApplyAfterSale = false;
            if (!listEntity.getType().equals("distribution")) {
                if (listEntity.getItemStatusCode() >= 1) {
                    if ((!Util.isEmpty(listEntity.getPaymentType())) && listEntity.getPaymentType().equals("COD")) {
                        if (listEntity.getItemStatusCode() >= 7) {
                            IsApplyAfterSale = true;
                        }
                    } else if (listEntity.getItemStatusCode() >= 1) {
                        IsApplyAfterSale = true;
                    }
                }
            }
            if (IsApplyAfterSale && listEntity.getAftering().equals("none") &&
                    listEntity.getItemStatusCode() != 8 && (listEntity.getAfter() == 1)) {
                i++;
                btnApplyAfterSale.setVisibility(View.VISIBLE);
            } else {
                btnApplyAfterSale.setVisibility(View.GONE);
            }

            //代发货的考拉订单判断不了是否支持售后,展示申请售后让用户自己操作
            if ("KAOLA".equals(listEntity.getProductSource()) && listEntity.getItemStatusCode() == 1) {
                i++;
                btnApplyAfterSale.setVisibility(View.VISIBLE);
            }

            if ("KAOLA".equals(listEntity.getProductSource()) && listEntity.getIsComment() == 1) {
                btnApplyAfterSale.setVisibility(View.GONE);
            }

            //售后跟踪
            if (!listEntity.getType().equals("distribution") &&
                    !Util.isEmpty(listEntity.getAftering()) && !listEntity.getAftering().equals("none")) {
                i++;
                btnShowAfterSale.setVisibility(View.VISIBLE);
                btnApplyAfterSale.setVisibility(View.GONE);     //避免考拉商品申请售后和售后跟踪同时出现
            } else {
                btnShowAfterSale.setVisibility(View.GONE);
            }
            if (i > 0) {
                llBottom.setVisibility(View.VISIBLE);
                //拼团单拼团中底部按钮不可操作
                if (listEntity.getType().equals("collage")) {
                    if(listEntity.getCollageStatus()==1){
                        llBottom.setVisibility(View.GONE);
                    }
                }
            } else {
                llBottom.setVisibility(View.GONE);
            }

            btnConfirmReceive.setOnClickListener(new ConfirmReceiveListener(listEntity));
            btnShowLogistical.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if("KAOLA".equals(listEntity.getProductSource())){
                        Util.urlAction(OrderItemDetailActivity.this, String.format(Constants.KAOLA_WULIU_URL,
                                listEntity.getDeliverySn(), listEntity.getDeliveryCorpCode(), listEntity.getProductImg(), listEntity.getId()));
                    }else{
                        Util.urlAction(OrderItemDetailActivity.this, String.format(Constants.WULIU_URL,
                                listEntity.getDeliverySn(), listEntity.getDeliveryCorpCode(), listEntity.getProductImg()));
                    }

                }
            });
            btnGotoComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OrderItemDetailActivity.this, ReviewOrderActivity.class);
                    intent.putExtra("id", (long) listEntity.getId());
                    intent.putExtra("productUrl", listEntity.getProductImg());
                    intent.putExtra("productInfo", listEntity.getProductName());
                    intent.putExtra("price", listEntity.getProductPrice());
                    intent.putExtra("style", getString(R.string.label_kongge, listEntity.getColor(),
                            listEntity.getSize()));
                    intent.putExtra("intentFlag", 1);
                    startActivityForResult(intent, 666);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                }
            });
            btnShowComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OrderItemDetailActivity.this, ShowCommendActivity.class);
                    intent.putExtra("id", listEntity.getCommentId());
                    intent.putExtra("productId", listEntity.getProductId());
                    intent.putExtra("price", String.valueOf(listEntity.getTotalPrice()));
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                }
            });
            btnApplyAfterSale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OrderItemDetailActivity.this, ApplyAfterSaleActivity.class);
                    intent.putExtra("orderSn", listEntity.getOrderSn());
                    intent.putExtra("orderId", listEntity.getId());
                    OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity bean = new OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity();
                    bean.setAfterApply(listEntity.getAfterApply());
                    bean.setProductImg(listEntity.getProductImg());
                    bean.setItemStatus(listEntity.getItemStatus());
                    bean.setProductName(listEntity.getProductName());
                    bean.setColor(listEntity.getColor());
                    bean.setSize(listEntity.getSize());
                    bean.setActualAmount(listEntity.getActualAmount());
                    bean.setProductId((long) listEntity.getProductId());
                    bean.setId((long) listEntity.getId());
                    bean.setSkuSn(listEntity.getSkuSn());
                    bean.setQuantity(listEntity.getQuantity());
                    bean.setPaymentType(listEntity.getPaymentType());
                    bean.setReminded(listEntity.getReminded());
                    intent.putExtra("bean", bean);
                    intent.putExtra("intentFlag", 1);
                    intent.putExtra("statusCode", listEntity.getItemStatusCode());
                    intent.putExtra("isKaoLa", "KAOLA".equals(listEntity.getProductSource()));//是不是考拉
                    startActivityForResult(intent, 999);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                }
            });
            btnShowAfterSale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listEntity.getAftering().equals("exchange")) {
                        gotoAfterSale(1);
                    } else if (listEntity.getAftering().equals("reship")) {
                        gotoAfterSale(0);
                    } else if (listEntity.getAftering().equals("refund")) {
                        gotoAfterSale(2);
                    }
                }
            });
            if (listEntity.getWarehouseId() != null && listEntity.getWarehouseId() > 0 && !Util.isEmpty(listEntity.getWarehouseName())) {  //按仓分组每组第一条
                RelativeLayout orderGroupTopView = (RelativeLayout) LayoutInflater.from(this).inflate(
                        R.layout.layout_order_group_top_view, null);
                TextView tvWarehouseName = (TextView) orderGroupTopView.findViewById(R.id.tv_warehouse_name);
                tvWarehouseName.setText(listEntity.getWarehouseName());
                orderContainerLayout.addView(orderGroupTopView);
            }
            orderContainerLayout.addView(itemView);

            tvCreateTime.setText(data.getCreateDate());
            tvPayWay.setText(listEntity.getPaymentTypeName());//支付方式
            if ((!Util.isEmpty(listEntity.getPaymentType())) && (listEntity.getItemStatus().equals(getString(R.string.label_waiting_payment)) ||
                    listEntity.getPaymentType().equals("COD") ||
                    Util.isEmpty(data.getPaymentSn()))) {//待付款状态或货到付款或流水号为空
                tvCopyWaterId.setVisibility(View.GONE);
            } else {
                tvCopyWaterId.setVisibility(View.VISIBLE);
            }
            tvRemark.setText(data.getMemo());
            tvOrderTotal.setText(Html.fromHtml(String.format(getString(R.string.label_order_total2), listEntity.getQuantity(),
                    Util.getNumberFormat(listEntity.getTotalPrice() - listEntity.getPromotionPrice()))));
            if (data.getShippingRates() > 0) {
                tvOrderFreight.setText(String.format(getString(R.string.label_price_plus), Util.getNumberFormat(data.getShippingRates())));
            } else {
                tvOrderFreight.setText(String.format(getString(R.string.label_price), Util.getNumberFormat(data.getShippingRates())));
            }
            //tvOrderFreight.setText(String.format(getString(R.string.label_price), Util.getNumberFormat(listEntity.getOrder().getShippingRates())));
            if (listEntity.getPromotionPrice() > 0) {
                tvOrderPromotion.setText(String.format(getString(R.string.label_price_minus), Util.getNumberFormat(listEntity.getOrderPromotionAmount())));
            } else {
                tvOrderPromotion.setText(String.format(getString(R.string.label_price), Util.getNumberFormat(listEntity.getOrderPromotionAmount())));
            }
            if (listEntity.getRedAmount() > 0) {
                llRed.setVisibility(View.VISIBLE);
                tvOrderRed.setText(String.format(getString(R.string.label_price_minus), Util.getNumberFormat(listEntity.getRedAmount())));
            } else {
                llRed.setVisibility(View.GONE);
            }
            if (listEntity.getCouponAmount() > 0) {
                tvOrderCoupon.setText(String.format(getString(R.string.label_price_minus), Util.getNumberFormat(listEntity.getCouponAmount())));
            } else {
                tvOrderCoupon.setText(String.format(getString(R.string.label_price), Util.getNumberFormat(listEntity.getCouponAmount())));
            }
            if (listEntity.getActualAmount() < 0) {
                tvOrderPayReal.setText(String.format(getString(R.string.label_price), Util.getNumberFormat(0)));
            } else {
                tvOrderPayReal.setText(String.format(getString(R.string.label_price), Util.getNumberFormat(listEntity.getActualAmount())));
            }
            tvOrderId.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (!Util.isEmpty(listEntity.getOrderSn())) {
                        ClipboardManager cm = (ClipboardManager) OrderItemDetailActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setPrimaryClip(ClipData.newPlainText("orderId", listEntity.getOrderSn()));
                        Util.showToast(OrderItemDetailActivity.this, R.string.label_order_id_copy);
                    }
                    return true;
                }
            });

            final List<String> imgs = new ArrayList<>();
            imgs.add(listEntity.getProductImg());

            btnBuyAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkOrderSku(listEntity);
                }
            });
            tvCopyWaterId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager cm = (ClipboardManager) OrderItemDetailActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText("paymentId", data.getPaymentSn()));
                    Util.showToast(OrderItemDetailActivity.this, R.string.label_order_payment_id_copy);
                }
            });
            tvLeftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tvLeftButton.getText().equals("取消订单")) {
                        //requestCancelOrderTask();
                    } else if (tvLeftButton.getText().equals("查看物流")) {
                        if("KAOLA".equals(listEntity.getProductSource())){
                            Util.urlAction(OrderItemDetailActivity.this, String.format(Constants.KAOLA_WULIU_URL,
                                    listEntity.getDeliverySn(), listEntity.getDeliveryCorpCode(), listEntity.getProductImg(), listEntity.getId()));
                        }else{
                            Util.urlAction(OrderItemDetailActivity.this, String.format(Constants.WULIU_URL,
                                    listEntity.getDeliverySn(), listEntity.getDeliveryCorpCode(), listEntity.getProductImg()));
                        }
                    } else if (tvLeftButton.getText().equals("删除订单")) {
                        //requestDeleteOrderTask((int)listEntity.getId());
                    }
                }
            });
            tvRightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tvRightButton.getText().equals("立即付款")) {
                        //gotoBuy(listEntity);
                    } else if (tvRightButton.getText().equals("确认收货")) {
                        new AlertDialog.Builder(OrderItemDetailActivity.this)
                                .setMessage(R.string.msg_confirm_receive)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ConfirmReceiveApi api = new ConfirmReceiveApi();
                                        api.setOrderItemId(listEntity.getId());
                                        loadingDialog.show();
                                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderInfoBean>() {
                                            @Override
                                            public void onResponse(OrderInfoBean orderInfoBean) {
                                                loadingDialog.dismiss();
                                                Util.showToast(OrderItemDetailActivity.this, R.string.msg_confirm_receive_ok);
                                                //listEntity = orderInfoBean.getData().getOrder();
                                                setOrderInfo();
                                                EventBus.getDefault().post(data);
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                loadingDialog.dismiss();
                                                Util.showToast(OrderItemDetailActivity.this, Util.checkErrorType(error));
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
                    ClipboardManager cm = (ClipboardManager) OrderItemDetailActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText("orderId", listEntity.getOrderSn()));
                    Util.showToast(OrderItemDetailActivity.this, R.string.label_order__id_copy);
                }
            });
        }
    }

    private void checkOrderSku(final OrderItemBean.DataBean.ItemBean listEntity) {
        InsertCartApi api = new InsertCartApi();
        api.setNum(listEntity.getQuantity());
        api.setSkuId(listEntity.getSkuId());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                Intent intent = new Intent(OrderItemDetailActivity.this, CartActivity.class);
                final ArrayList<Integer> skuids = new ArrayList<>();
                skuids.add(listEntity.getSkuId());
                intent.putIntegerArrayListExtra("skuid", skuids);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(OrderItemDetailActivity.this, Util.checkErrorType(error));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999 && resultCode == RESULT_OK) {
            progressBar.setVisibility(View.VISIBLE);
            requestOrderInfoTask();
            gotoAfterSale(data.getIntExtra("position", 0));//position =0 跳退货,2跳退款(RefundReship界面携带回来的数据)
        } else if (requestCode == 666 && resultCode == RESULT_OK) {
            progressBar.setVisibility(View.VISIBLE);
            requestOrderInfoTask();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void gotoAfterSale(int position) {
        Intent intent = new Intent(OrderItemDetailActivity.this, AllAfterSaleActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
    }

    @OnClick({R.id.back_iv, R.id.title_right, R.id.btn_review_order, R.id.ll_help, R.id.ll_complain, R.id.tv_tax_desc, R.id.iv_tax_question})
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
            case R.id.btn_review_order:
                Intent intent = new Intent(OrderItemDetailActivity.this, ReviewOrderActivity.class);
                intent.putExtra("id", (long) listEntity.getId());
                intent.putExtra("productUrl", listEntity.getProductImg());
                intent.putExtra("productInfo", listEntity.getProductName());
                intent.putExtra("price", listEntity.getProductPrice());
                intent.putExtra("style", getString(R.string.label_kongge, listEntity.getColor(),
                        listEntity.getSize()));
                intent.putExtra("intentFlag", 1);
                startActivityForResult(intent, 666);
                break;
            case R.id.tv_tax_desc:  //税费
            case R.id.iv_tax_question:
//                tvTaxDesc.setEnabled(false);
//                ivTaxQuestion.setEnabled(false);
//                if (taxDetialPop == null) {
//                    taxDetialPop = new TaxDetialPop(this);
//                }
//                taxDetialPop.show(getWindow().getDecorView());
//                ivTaxQuestion.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (taxDetialPop != null) {
//                            taxDetialPop.dismiss();
//                            tvTaxDesc.setEnabled(true);
//                            ivTaxQuestion.setEnabled(true);
//                        }
//                    }
//                }, 2000);
                break;
        }
    }

    private class ConfirmReceiveListener implements View.OnClickListener {

        private OrderItemBean.DataBean.ItemBean itemsEntity;

        public ConfirmReceiveListener(OrderItemBean.DataBean.ItemBean itemsEntity) {
            super();
            this.itemsEntity = itemsEntity;
        }

        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(OrderItemDetailActivity.this)
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
                    Util.showToast(OrderItemDetailActivity.this, R.string.msg_confirm_receive_ok);
                    //listEntity = orderInfoBean.getData().getOrder();
                    setOrderInfo();
                    EventBus.getDefault().post(data);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loadingDialog.dismiss();
                    Util.showToast(OrderItemDetailActivity.this, Util.checkErrorType(error));
                }
            });
        }
    }
}
