package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.FeedBackDetialBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.RoundedImageView;
import com.d2cmall.buyer.widget.ninegrid.ImageInfo;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Name: d2c
 * Anthor: lwj
 * Date: 2017/7/21 13:39
 * Copyright (c) 2016 d2cmall. All rights reserved.
 * 消息反馈详情页
 */

public class ComplainBackDetialActivity extends BaseActivity {

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
    @Bind(R.id.img_avatar)
    RoundedImageView imgAvatar;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.tv_complain_content)
    TextView tvComplainContent;
    @Bind(R.id.tv_back_content)
    TextView tvBackContent;
    @Bind(R.id.ll_pics)
    LinearLayout llPics;
    private long id = -1;
    private ArrayList<ImageInfo> imageInfos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_back);
        ButterKnife.bind(this);
        id = getIntent().getLongExtra("id", -1);
        doBusiness();
        loadData();

    }

    private void loadData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.COMPLAIN_BACK_DETIAL, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<FeedBackDetialBean>() {
            @Override
            public void onResponse(FeedBackDetialBean feedBackDetialBean) {
                setViewValue(feedBackDetialBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.checkErrorType(error);
            }
        });
    }


    private void doBusiness() {
        nameTv.setText("反馈与投诉回复");

    }

    private void setViewValue(FeedBackDetialBean feedBackDetialBean) {
        String str = "<font color='#FF0000'>回复</font>:" + feedBackDetialBean.getData().getFeedBack().getReply();
        tvBackContent.setText(Html.fromHtml(str));
        tvUserName.setText(feedBackDetialBean.getData().getFeedBack().getNickName());
        tvComplainContent.setText(feedBackDetialBean.getData().getFeedBack().getContent());
        UniversalImageLoader.displayImage(this, feedBackDetialBean.getData().getFeedBack().getHeadPic(), imgAvatar, R.mipmap.ic_default_avatar);
        if( feedBackDetialBean.getData().getFeedBack().getPic()==null|| feedBackDetialBean.getData().getFeedBack().getPic().size()==0){
            llPics.setVisibility(View.GONE);
        }else{
            initImageInfo(feedBackDetialBean);
            llPics.setVisibility(View.VISIBLE);

            for (int i = 0; i <feedBackDetialBean.getData().getFeedBack().getPic().size(); i++) {
                ImageView imageView = new ImageView(this);
                LinearLayout.LayoutParams ll= new LinearLayout.LayoutParams(-2,-2);
                ll.width= ScreenUtil.dip2px(60);
                ll.height=ScreenUtil.dip2px(90);
                ll.setMargins(ScreenUtil.dip2px(16),0,0,0);
                imageView.setLayoutParams(ll);
                final int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ComplainBackDetialActivity.this, ImagePreviewActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfos);
                        bundle1.putInt(ImagePreviewActivity.CURRENT_ITEM, finalI);
                        intent.putExtras(bundle1);
                        startActivity(intent);
                    }
                });
                llPics.addView(imageView);
            }
            int childCount = llPics.getChildCount();
            for (int i = 0; i < childCount; i++) {
                UniversalImageLoader.displayImage(this, feedBackDetialBean.getData().getFeedBack().getPic().get(i), (ImageView) llPics.getChildAt(i));
            }
        }
    }

    private void initImageInfo(FeedBackDetialBean feedBackDetialBean) {
        imageInfos=new ArrayList<>();
        for (int i = 0; i <feedBackDetialBean.getData().getFeedBack().getPic().size() ; i++) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setBigImageUrl(Util.getD2cPicUrl(feedBackDetialBean.getData().getFeedBack().getPic().get(i)));
            imageInfos.add(imageInfo);
        }
    }


    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}
