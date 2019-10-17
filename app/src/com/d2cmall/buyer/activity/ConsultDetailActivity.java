package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.ConsultDetailBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/12/26 10:03
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ConsultDetailActivity extends BaseActivity {

    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.product_name)
    TextView productName;
    @Bind(R.id.price)
    TextView priceTv;
    @Bind(R.id.portrait)
    ImageView portrait;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.consult_content)
    TextView consultContent;
    @Bind(R.id.relay_content)
    TextView relayContent;

    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        TitleUtil.setBack(this);
        TitleUtil.setTitle(this,"咨询回复");
        int id=getIntent().getIntExtra("id",0);
        loadData(id);
    }

    private void loadData(int id) {
        SimpleApi api=new SimpleApi();
        api.setInterPath("/v3/api/consult/"+id);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ConsultDetailBean>() {
            @Override
            public void onResponse(ConsultDetailBean response) {
                if (response!=null){
                    setData(response);
                }
            }
        });
    }

    private void setData(ConsultDetailBean detailBean){
        id=detailBean.getData().getProduct().getId();
        UniversalImageLoader.displayImage(this,detailBean.getData().getProduct().getImg(),image);
        productName.setText(detailBean.getData().getProduct().getName());
        if (detailBean.getData().getProduct().getPromotionId()>0){
            if (detailBean.getData().getProduct().getSellPrice()>detailBean.getData().getProduct().getMinPrice()){
                setPrice(detailBean.getData().getProduct().getMinPrice(),detailBean.getData().getProduct().getSellPrice());
            }else {
                priceTv.setText("¥"+Util.getNumberFormat(detailBean.getData().getProduct().getMinPrice()));
            }
        }else {
            if (detailBean.getData().getProduct().getOriginalPrice()>detailBean.getData().getProduct().getMinPrice()){
                setPrice(detailBean.getData().getProduct().getMinPrice(),detailBean.getData().getProduct().getOriginalPrice());
            }else {
                priceTv.setText("¥"+Util.getNumberFormat(detailBean.getData().getProduct().getMinPrice()));
            }
        }
        UniversalImageLoader.displayRoundImage(this,detailBean.getData().getConsult().getHeadPic(),portrait,R.mipmap.ic_default_avatar);
        name.setText(detailBean.getData().getConsult().getNickName());
        consultContent.setText(detailBean.getData().getConsult().getQuestion());
        setReplay(detailBean.getData().getConsult().getReply());
    }

    private void setReplay(String replay){
        StringBuilder builder=new StringBuilder();
        builder.append("回复: ").append(replay);
        ForegroundColorSpan colorSpan=new ForegroundColorSpan(getResources().getColor(R.color.color_red));
        SpannableString spannableString=new SpannableString(builder.toString());
        spannableString.setSpan(colorSpan,0,2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        relayContent.setText(spannableString);
    }

    private void setPrice(double price,double orgPrice){
        StringBuilder builder=new StringBuilder();
        builder.append("¥").append(Util.getNumberFormat(price)).append(" ")
                .append("¥").append(Util.getNumberFormat(orgPrice));
        int index=builder.toString().lastIndexOf("¥");
        SpannableString spannableString = new SpannableString(builder.toString());

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#61000000"));
        spannableString.setSpan(colorSpan, index, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan sizeSpan=new RelativeSizeSpan(0.8f);
        spannableString.setSpan(sizeSpan,index,builder.toString().length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan,index,builder.toString().length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        priceTv.setText(spannableString);
    }

    @OnClick({R.id.image,R.id.product_name})
    public void click(View view){
        switch (view.getId()){
            case R.id.image:
            case R.id.product_name:
                toDetailActivity();
                break;
        }
    }

    private void toDetailActivity(){
        Intent intent=new Intent(this,ProductDetailActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

}
