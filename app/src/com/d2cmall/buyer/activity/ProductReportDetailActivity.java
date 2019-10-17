package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.ClickNineGridViewAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.ProductReportBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ninegrid.ImageInfo;
import com.d2cmall.buyer.widget.ninegrid.NineGridView;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 商品报告详情页
 * Author: LWJ
 * Date: 17/9/6 16:55
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class ProductReportDetailActivity extends BaseActivity  {

    @Bind(R.id.back_iv)
    ImageView mBackIv;
    @Bind(R.id.name_tv)
    TextView mNameTv;
    @Bind(R.id.title_right)
    TextView mTitleRight;
    @Bind(R.id.tag)
    View mTag;
    @Bind(R.id.title_layout)
    RelativeLayout mTitleLayout;
    @Bind(R.id.img_avatar)
    ImageView mImgAvatar;
    @Bind(R.id.img_tag)
    ImageView mImgTag;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_check_statue)
    TextView mTvCheckStatue;
    @Bind(R.id.rl_title)
    RelativeLayout mRlTitle;
    @Bind(R.id.content_tv)
    TextView mContentTv;
    @Bind(R.id.nineGrid)
    NineGridView mNineGrid;
    @Bind(R.id.tv_reason)
    TextView mTvReason;
    @Bind(R.id.tv_reason_desc)
    TextView mTvReasonDesc;
    @Bind(R.id.rl_result)
    RelativeLayout mRlResult;
    @Bind(R.id.tv_report_time)
    TextView mTvReportTime;
    @Bind(R.id.tv_cancle_commit)
    TextView mTvCancleCommit;
    @Bind(R.id.re_edit)
    TextView mReEdit;
    @Bind(R.id.rl_user_action)
    RelativeLayout mRlUserAction;
    private ProductReportBean.DataBean.ReportBean.ListBean mReportItem;
    private long mStatus;
    private View mContentView;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_report_detail);
        ButterKnife.bind(this);
        mReportItem = (ProductReportBean.DataBean.ReportBean.ListBean) getIntent().getSerializableExtra("item");
        if (mReportItem == null) {
            return;
        } else {
            initView();
        }
    }

    private void initView() {
        mNameTv.setText("商品报告详情");
        mTitleRight.setBackgroundResource(R.mipmap.icon_all_mineshare);
        mTvName.setText(mReportItem.getProductName());
        UniversalImageLoader.displayImage(this, mReportItem.getProductImg(), mImgAvatar);
        setPictures(mReportItem, mNineGrid, mReportItem.getPic());
        mContentTv.setText(mReportItem.getContent());
        mTvCheckStatue.setText(mReportItem.getStatusName());
        mTvReasonDesc.setText(mReportItem.getVerifyReason());
        SimpleDateFormat df3 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String time = df3.format(mReportItem.getSubmitDate());
        mTvReportTime.setText(time);
    }

    private void setPictures(final ProductReportBean.DataBean.ReportBean.ListBean listEntity, NineGridView nineGridView, List<String> imgList) {

        ArrayList<ImageInfo> imageInfos = new ArrayList<>();
        if (imgList != null) {
            for (String picUrl : imgList) {
                ImageInfo info = new ImageInfo();
                info.setSingleUrl(Util.getD2cPicUrl(picUrl));//单张图
                info.setFourUrl(Util.getD2cPicUrl(picUrl));//四张图
                info.setThumbnailUrl(Util.getD2cPicUrl(picUrl));//多张缩略图
                info.setBigImageUrl(Util.getD2cPicUrl(picUrl));//大图
                String pic = Util.getD2cPicUrl(picUrl);
                imageInfos.add(info);
            }
        }
        nineGridView.setAdapter(new ClickNineGridViewAdapter(this, imageInfos));
    }

    @OnClick({R.id.img_avatar, R.id.tv_name, R.id.tv_cancle_commit,R.id.back_iv,R.id.title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_avatar:
                jumpToProductDetail();
                break;
            case R.id.tv_name:
                jumpToProductDetail();
                break;
            case R.id.tv_cancle_commit:
                deleteCommit();
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_right://删除的弹窗
                showDeletePop();
                break;
        }
    }
    private void showDeletePop() {
        initPopupWindow();
        fitPopupWindowOverStatusBar(true);
        View rootview = LayoutInflater.from(ProductReportDetailActivity.this).inflate(R.layout.activity_product_report_detail, null);
        mPopupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }
    private void initPopupWindow() {
        //要在布局中显示的布局
        mContentView = LayoutInflater.from(this).inflate(R.layout.layout_bottom_delete_pop, null, false);
        //实例化PopupWindow并设置宽高
        View viewDismiss = mContentView.findViewById(R.id.tv_dismiss);
        TextView tvDelete = (TextView) mContentView.findViewById(R.id.tv_delete);
        TextView tvCancle = (TextView) mContentView.findViewById(R.id.tv_cancel);
        viewDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPopupWindow!=null) {
                    mPopupWindow.dismiss();
                }
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCommit();
            }
        });
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPopupWindow!=null) {
                    mPopupWindow.dismiss();
                }
            }
        });
        mPopupWindow = new PopupWindow(mContentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow.setTouchable(true);
    }
    //poopWindow覆盖状态栏
    public void fitPopupWindowOverStatusBar(boolean needFullScreen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                //利用反射重新设置mLayoutInScreen的值，当mLayoutInScreen为true时则PopupWindow覆盖全屏。
                Field mLayoutInScreen = PopupWindow.class.getDeclaredField("mLayoutInScreen");
                mLayoutInScreen.setAccessible(true);
                mLayoutInScreen.set(mPopupWindow, needFullScreen);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void jumpToProductDetail() {
        Intent intent = new Intent(this,ProductDetailActivity.class);
        intent.putExtra("id",(long) mReportItem.getProductId());
        startActivity(intent);
    }

    private void deleteCommit() {

        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.PRODUCT_REPORT_DELETE_URL,mReportItem.getId()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ProductReportBean>() {
            @Override
            public void onResponse(ProductReportBean reportBean) {
                Util.showToast(ProductReportDetailActivity.this,"删除成功");
                EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.PRODUCT_REPORT_COUNT_CHANGE));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(ProductReportDetailActivity.this, Util.checkErrorType(error));

            }
        });
    }
}
