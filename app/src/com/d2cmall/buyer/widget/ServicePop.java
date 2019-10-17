package com.d2cmall.buyer.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.ProductDetailBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/14 11:58
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ServicePop implements TransparentPop.InvokeListener {

    @Bind(R.id.content_ll)
    LinearLayout contentLl;
    @Bind(R.id.sure_tv)
    TextView sureTv;

    private ProductDetailBean productDetail;
    private TransparentPop pop;
    private Context context;
    private View rootView;

    public ServicePop(Context context, ProductDetailBean detailBean) {
        this.context=context;
        this.productDetail=detailBean;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_service, new LinearLayout(context), false);
        ButterKnife.bind(this,rootView);
        pop = new TransparentPop(context, this);
        Animation inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_up);
        Animation outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_down);
        pop.setInAnimation(inAnimation);
        pop.setOutAnimation(outAnimation);
        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void show(View view){
        pop.show(view,true);
    }

    public void dismiss(){
        pop.dismiss(true);
    }

    @Override
    public void invokeView(LinearLayout v) {
        View view=getItemView();
        TextView tv1= (TextView) view.findViewById(R.id.tv1);
        TextView tv2= (TextView) view.findViewById(R.id.tv2);
        if ("KAOLA".equals(productDetail.getData().getProduct().getProductSource())){
            tv1.setText("不支持无理由退换");
            tv2.setText("定制商品、全球购商品或其它特殊商品非货品质量原因不支持7天无理由退换货");
        }else if (!productDetail.getData().getProduct().isAfter()){
            tv1.setText("不支持无理由退换");
            tv2.setText(productDetail.getData().getProduct().getAfterMemo());
        } else {
            tv1.setText("七天无理由退换");
            tv2.setText(productDetail.getData().getProduct().getAfterMemo());
        }
        contentLl.addView(view);
        if (productDetail.getData().getProduct().getIsSubscribe()){
            view=getItemView();
            tv1= (TextView) view.findViewById(R.id.tv1);
            tv2= (TextView) view.findViewById(R.id.tv2);
            tv1.setText("门店试衣");
            tv2.setText("线上预约线下试衣的新零售购买体验，门店试衣服务仅限现货商品（定制及预售商品除外）");
            contentLl.addView(view);
        }
        if ("CROSS".equals(productDetail.getData().getProduct().getProductTradeType())){
            if (productDetail.getData().getProduct().getIsTaxation()==1){
                view=getItemView();
                tv1= (TextView) view.findViewById(R.id.tv1);
                tv2= (TextView) view.findViewById(R.id.tv2);
                tv1.setText("税费补贴");
                tv2.setText("本商品税费由商家承担");
                contentLl.addView(view);
            }else {
                view=getItemView();
                tv1= (TextView) view.findViewById(R.id.tv1);
                tv2= (TextView) view.findViewById(R.id.tv2);
                tv1.setText("商品税费");
                tv2.setText("据国家政策规定，本商品适用于“跨境综合税”，实际结算税费请以提交订单时应付总额明细为准。");
                contentLl.addView(view);
            }
        }
        //贸易方式 0:直邮，1、保税，2、海淘 ，3、国内贸易 ，4、个人清关
        if ("CROSS".equals(productDetail.getData().getProduct().getProductTradeType())){
            view=getItemView();
            tv1= (TextView) view.findViewById(R.id.tv1);
            tv2= (TextView) view.findViewById(R.id.tv2);
            switch (productDetail.getData().getProduct().getImportType()){
                case 0:
                    tv1.setText("发货信息");
                    tv2.setText("本商品由直邮仓发货");
                    break;
                case 1:
                    tv1.setText("发货信息");
                    tv2.setText("本商品由保税仓发货");
                    break;
                case 2:
                    tv1.setText("发货信息");
                    tv2.setText("海淘");
                    break;
                case 3:
                    tv1.setText("发货信息");
                    tv2.setText("本商品由国内仓发货");
                    break;
                case 4:
                    tv1.setText("发货信息");
                    tv2.setText("个人清关");
                    break;
            }
            contentLl.addView(view);
        }
        view=getItemView();
        tv1= (TextView) view.findViewById(R.id.tv1);
        tv2= (TextView) view.findViewById(R.id.tv2);
        if ("KAOLA".equals(productDetail.getData().getProduct().getProductSource())){
            tv1.setText("88包邮");
            tv2.setText("本商品满88元包邮（不支持港澳台及国外地方购买）");
        }else if ("CAOMEI".equals(productDetail.getData().getProduct().getProductSource())){
            tv1.setText("210包邮");
            tv2.setText("本商品满210元包邮（不支持港澳台及国外地方购买）");
        }else if ("HISTREET".equals(productDetail.getData().getProduct().getProductSource())){
            tv1.setText("299包邮");
            tv2.setText("本商品满299元包邮（不支持港澳台及国外地方购买）");
        } else {
            if (productDetail.getData().getProduct().getIsShipping()==1){
                tv1.setText("包邮");
                tv2.setText("本商品包邮（港澳台及国外除外）");
            }else {
                tv1.setText("299包邮");
                tv2.setText("本商品满299元包邮（港澳台及国外除外）");
            }
        }
        contentLl.addView(view);
        if (!"CROSS".equals(productDetail.getData().getProduct().getProductTradeType())){
            view=getItemView();
            tv1= (TextView) view.findViewById(R.id.tv1);
            tv2= (TextView) view.findViewById(R.id.tv2);
            tv1.setText("正品保证");
            tv2.setText("100%正品，设计师品牌由设计师直接授权，线上线下销售的所有商品均为正品");
            contentLl.addView(view);
        }
        view=getItemView();
        tv1= (TextView) view.findViewById(R.id.tv1);
        tv2= (TextView) view.findViewById(R.id.tv2);
        tv1.setText("慢必赔");
        StringBuilder builder=new StringBuilder();
        if ("KAOLA".equals(productDetail.getData().getProduct().getProductSource())){
            builder.append("发货慢必赔：现货商品订单生成第三天24点前发货（订单生成次日为第一天）" +
                    "订单状态变更为“设计师已发货”、“已发货”即为已发货状态，如未能及时发货，第一天赔偿10%，之后每延期一天递增3%，赔偿金额不超过商品实付金额30%，总金额不超过300元。");
        }else {
            if ("SPOT".equals(productDetail.getData().getProduct().getProductSellType())){
                builder.append("发货慢必赔：现货商品订单生成次日24点前发货（如遇非工作日或者法定节假日顺延）" +
                        "订单状态变更为“设计师已发货”、“已发货”即为已发货状态，如未能及时发货，第一天赔偿10%，之后每延期一天递增3%，赔偿金额不超过商品实付金额30%，总金额不超过300元。");
            }else if ("PRESELL".equals(productDetail.getData().getProduct().getProductSellType())){
                builder.append("发货慢必赔：预售商品在预定发货日24点前发出（如遇非工作日或者法定节假日顺延）" +
                        "订单状态变更为“设计师已发货”、“已发货”即为已发货状态，如未能及时发货，第一天赔偿10%，之后每延期一天递增3%，赔偿金额不超过商品实付金额30%，总金额不超过300元。");
            }else if ("CUSTOM".equals(productDetail.getData().getProduct().getProductSellType())){
                builder.append("发货慢必赔：定制商品在预定发货日24点前发出（如遇非工作日或者法定节假日顺延）" +
                        "订单状态变更为“设计师已发货”、“已发货”即为已发货状态，如未能及时发货，第一天赔偿10%，之后每延期一天递增3%，赔偿金额不超过商品实付金额30%，总金额不超过300元");
            }else {
            }
        }
        if ("KAOLA".equals(productDetail.getData().getProduct().getProductSource())||productDetail.getData().getProduct().isAfter()){
            if ("KAOLA".equals(productDetail.getData().getProduct().getProductTradeType())){
                builder.append("\n").append("\n");
                builder.append("退款慢必赔：未发货退款平台方将在7个工作日内将退款原路退回用户账户，以上时效规定在遇非工作日或者法定节假日则顺延，超过一个工作日每天赔偿10元，总金额不超过300元。注：所有用户赔付金额均打入用户在D2C的钱包账户，不支持提现。");
            }else {
                builder.append("\n").append("\n");
                builder.append("退款慢必赔：客户自助退换货发起后，客服将在一个工作日之内反馈；" +
                        "未发货退款或者在收到退货商品且确认可退款情况下，平台方将在7个工作日内将退款原路退回用户账户；" +
                        "以上时效规定在遇非工作日或者法定节假日则顺延，超过一个工作日每天赔偿10元，总金额不超过300元。注：所有用户赔付金额均打入用户在D2C的钱包账户，不支持提现。");
            }
        }
        tv2.setText(builder.toString());
        contentLl.addView(view);

        v.setGravity(Gravity.BOTTOM);
        v.addView(rootView);
    }

    private View getItemView(){
        View view=LayoutInflater.from(context).inflate(R.layout.layout_server_item,new RelativeLayout(context),false);
        return view;
    }

    @Override
    public void releaseView(LinearLayout v) {
        ((ViewGroup)rootView).removeAllViews();
        rootView=null;
    }
}
