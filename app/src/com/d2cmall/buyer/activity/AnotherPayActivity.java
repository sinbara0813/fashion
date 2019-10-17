package com.d2cmall.buyer.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.OrderInfoBean;
import com.d2cmall.buyer.bean.OrdersBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.CenterAlignImageSpan;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.SharePop;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.d2cmall.buyer.util.Util.getNumberFormat;

/**
 * 作者:Created by sinbara on 2018/11/19.
 * 邮箱:hrb940258169@163.com
 * 好友代付
 */

public class AnotherPayActivity extends BaseActivity {

    @Bind(R.id.price_tv)
    TextView priceTv;
    @Bind(R.id.state_tv)
    TextView stateTv;
    @Bind(R.id.order_content)
    LinearLayout orderContent;
    @Bind(R.id.another_pay_btn)
    TextView anotherPayBtn;

    private OrdersBean.DataEntity.OrdersEntity.ListEntity listEntity;
    private String orderSn;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            showTime();
            sendEmptyMessageDelayed(1,1000);
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_pay);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        TitleUtil.setBack(this);
        TitleUtil.setTitle(this,"发起代付");
        orderSn=getIntent().getStringExtra("orderSn");
        loadData();
        anotherPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
    }

    private void share(){
        if (listEntity==null)return;
        if (Session.getInstance().getUser()==null) return;
        SharePop sharePop=new SharePop(this);
        sharePop.setTitle("我在D2C看中了一款商品，快来帮我付款吧~");
        sharePop.setDescription(listEntity.getItems().get(0).getProductName());
        if (!Util.isEmpty(listEntity.getItems().get(0).getProductImg())) {
            sharePop.setImage(Util.getD2cPicUrl(listEntity.getItems().get(0).getProductImg(), 100, 100), false);
            sharePop.setImage(Util.getD2cPicUrl(listEntity.getItems().get(0).getProductImg(), 360, 500), true);
        }
        sharePop.setWebUrl("/order/payment/substitute/"+orderSn);
        sharePop.show(getWindow().getDecorView());
    }

    private void loadData() {
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.POST);
        api.setInterPath(String.format(Constants.ORDER_INFO_URL, orderSn));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderInfoBean>() {
            @Override
            public void onResponse(OrderInfoBean orderInfoBean) {
                listEntity = orderInfoBean.getData().getOrder();
                setOrderInfo();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(AnotherPayActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void setOrderInfo(){
        priceTv.setText("¥"+getNumberFormat(listEntity.getTotalPay()));
        if (listEntity.getOrderStatusCode()==3||listEntity.getOrderStatusCode()==4||listEntity.getOrderStatusCode()==8){//订单已完成支付
            stateTv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_fkcg,0,0,0);
            stateTv.setCompoundDrawablePadding(10);
            stateTv.setText("您的好友已帮您付款");
        }else {
            if (listEntity.getEndTime() != null) {
                showTime();
                handler.sendEmptyMessageDelayed(1,1000);
            }else {
                stateTv.setText("剩余支付时间  未知");
            }
        }
        int size=listEntity.getItems().size();
        List<OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity> orderItems=listEntity.getItems();
        for (int i=0;i<size;i++){
            View content = LayoutInflater.from(this).inflate(R.layout.layout_order_item_content, null);
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
            orderItemSize.setText(orderItems.get(i).getColor() + " " + orderItems.get(i).getSize());
            orderContent.addView(content);
        }
    }

    private void showTime(){
        Date date = new Date();
        Date timeoutDate = Util.getDate(listEntity.getEndTime());
        if (date.before(timeoutDate)) {//显示倒计时
            StringBuilder builder=new StringBuilder();
            long millisUntil = timeoutDate.getTime() - date.getTime();
            int hours = (int) (millisUntil / (60 * 60 * 1000));
            int minutes = (int) ((millisUntil / (60 * 1000)) % 60);
            int seconds = (int) ((millisUntil / 1000) % 60);
            builder.append("剩余支付时间  ").append(addZero(hours)).append(":").append(addZero(minutes)).append(":").append(addZero(seconds));
            int length=builder.toString().length();
            SpannableString sb=new SpannableString(builder.toString());
            BackgroundColorSpan colorSpan=new BackgroundColorSpan(Color.parseColor("#111111"));
            sb.setSpan(colorSpan,length-8,length-6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ForegroundColorSpan foreColorSpan=new ForegroundColorSpan(Color.parseColor("#ffffff"));
            sb.setSpan(foreColorSpan,length-8,length-6,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            BackgroundColorSpan colorSpan1=new BackgroundColorSpan(Color.parseColor("#111111"));
            sb.setSpan(colorSpan1,length-5,length-3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ForegroundColorSpan foreColorSpan1=new ForegroundColorSpan(Color.parseColor("#ffffff"));
            sb.setSpan(foreColorSpan1,length-5,length-3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            BackgroundColorSpan colorSpan2=new BackgroundColorSpan(Color.parseColor("#e83333"));
            sb.setSpan(colorSpan2,length-2,length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ForegroundColorSpan foreColorSpan2=new ForegroundColorSpan(Color.parseColor("#ffffff"));
            sb.setSpan(foreColorSpan2,length-2,length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            stateTv.setText(sb);
        }else {//显示超时
            stateTv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_fksb,0,0,0);
            stateTv.setCompoundDrawablePadding(10);
            stateTv.setText("支付时间结束订单已关闭");
            anotherPayBtn.setVisibility(View.GONE);
            anotherPayBtn.setEnabled(false);
            anotherPayBtn.setTextColor(getResources().getColor(R.color.enable_color));
        }
    }

    private String addZero(int count){
        if (count==0){
            return "00";
        }
        if (count<10){
            return "0"+count;
        }
        return String.valueOf(count);
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
