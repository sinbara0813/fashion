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

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.Util;

import java.math.BigDecimal;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/10/17 14:12
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class SelectHbPop implements TransparentPop.InvokeListener {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.close_iv)
    ImageView closeIv;
    @Bind(R.id.three_box)
    MyCheckBox threeBox;
    @Bind(R.id.three_tv)
    TextView threeTv;
    @Bind(R.id.three_hint)
    TextView threeHint;
    @Bind(R.id.six_box)
    MyCheckBox sixBox;
    @Bind(R.id.six_tv)
    TextView sixTv;
    @Bind(R.id.six_hint)
    TextView sixHint;
    @Bind(R.id.twelve_box)
    MyCheckBox twelveBox;
    @Bind(R.id.twelve_tv)
    TextView twelveTv;
    @Bind(R.id.twelve_hint)
    TextView twelveHint;
    @Bind(R.id.three_rl)
    RelativeLayout threeRl;
    @Bind(R.id.six_rl)
    RelativeLayout sixRl;
    @Bind(R.id.twelve_rl)
    RelativeLayout twelveRl;

    private TransparentPop pop;
    private View rootView;
    private int currentCount;
    private double currentPrice;
    private double[] rates=new double[3];
    private TextView outTv;

    public SelectHbPop(Context context,double totalPrice, boolean isSellPayCharge,double hbPrice,int count) {
        this.currentCount=count;
        this.currentPrice=hbPrice;
        if (isSellPayCharge){
            rates[0]=1.8;
        }else {
            rates[0]=2.3;
        }
        rates[1]=4.5;
        rates[2]=7.5;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_hb, new LinearLayout(context),false);
        ButterKnife.bind(this, rootView);
        calculate(totalPrice);
        pop = new TransparentPop(context, this);
        Animation inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_up);
        Animation outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_down);
        pop.setInAnimation(inAnimation);
        pop.setOutAnimation(outAnimation);
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        threeRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCount=3;
                currentPrice= (double) v.getTag();
                dismiss();
            }
        });
        sixRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCount=6;
                currentPrice= (double) v.getTag();
                dismiss();
            }
        });
        twelveRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCount=12;
                currentPrice= (double) v.getTag();
                dismiss();
            }
        });
    }

    public SelectHbPop(Context context,double totalPrice, boolean isSellPayCharge,double hbPrice,int count,TextView textView) {
        this.currentCount=count;
        this.currentPrice=hbPrice;
        this.outTv=textView;
        if (isSellPayCharge){
            rates[0]=1.8;
        }else {
            rates[0]=2.3;
        }
        rates[1]=4.5;
        rates[2]=7.5;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_hb, new LinearLayout(context),false);
        ButterKnife.bind(this, rootView);
        calculate(totalPrice);
        pop = new TransparentPop(context, this);
        Animation inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_up);
        Animation outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_down);
        pop.setInAnimation(inAnimation);
        pop.setOutAnimation(outAnimation);
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        threeRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCount=3;
                currentPrice= (double) v.getTag();
                dismiss();
            }
        });
        sixRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCount=6;
                currentPrice= (double) v.getTag();
                dismiss();
            }
        });
        twelveRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCount=12;
                currentPrice= (double) v.getTag();
                dismiss();
            }
        });
    }

    private void calculate(double price){
        int payAmount= (int) (price*100);
        calculate(payAmount,3);
        calculate(payAmount,6);
        calculate(payAmount,12);
    }

    private void calculate(int payAmount,int count){
        BigDecimal eachPrin = BigDecimal.valueOf(payAmount).divide(new BigDecimal(count), BigDecimal.ROUND_DOWN);//本金
        BigDecimal totalFeeInDecimal = BigDecimal.valueOf(payAmount).multiply(BigDecimal.valueOf(rates[count/6]/100));
        long totalFeeInLong = totalFeeInDecimal.setScale(0, BigDecimal.ROUND_HALF_EVEN).longValue();
        BigDecimal eachFee = BigDecimal.valueOf(totalFeeInLong).divide(new BigDecimal(count), BigDecimal.ROUND_DOWN);
        BigDecimal prinAndFee = eachFee.add(eachPrin);
        double price=prinAndFee.doubleValue()/100;//每期费用
        double chargePrice=totalFeeInDecimal.doubleValue()/100; //手续费
        if (count==3){
            if (currentCount==3){
                threeBox.setChecked(true);
            }
            threeRl.setTag(price);
            threeTv.setText(Util.getNumberFormat(price)+"元 X "+count+"期");
            if (outTv!=null){
                threeBox.setChecked(true);
                currentCount=3;
                currentPrice=price;
                outTv.setText(Util.getNumberFormat(price)+"元 X "+count+"期");
            }
            threeHint.setText("手续费 "+Util.getNumberFormat(chargePrice)+"元，费率"+rates[count/6]+"%");
        }else if (count==6){
            if (currentCount==6){
                sixBox.setChecked(true);
            }
            sixRl.setTag(price);
            sixTv.setText(Util.getNumberFormat(price)+"元 X "+count+"期");
            sixHint.setText("手续费 "+Util.getNumberFormat(chargePrice)+"元，费率"+rates[count/6]+"%");
        }else if (count==12){
            if (currentCount==12){
                twelveBox.setChecked(true);
            }
            twelveRl.setTag(price);
            twelveTv.setText(Util.getNumberFormat(price)+"元 X "+count+"期");
            twelveHint.setText("手续费 "+Util.getNumberFormat(chargePrice)+"元，费率"+rates[count/6]+"%");
        }
    }

    public void setDissMissListener(TransparentPop.DismissListener dissMissListener) {
        pop.setDismissListener(dissMissListener);
    }

    public void show(View view) {
        pop.show(view, true);
    }

    public void dismiss() {
        pop.dismiss(true);
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.BOTTOM);
        v.addView(rootView);
    }

    @Override
    public void releaseView(LinearLayout v) {
        ((ViewGroup) rootView).removeAllViews();
        rootView = null;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
