package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.activity.ProductReportDetailActivity;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.bean.ProductReportBean;
import com.d2cmall.buyer.bean.UnFocusMemberBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.ProductReportHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ninegrid.ImageInfo;
import com.d2cmall.buyer.widget.ninegrid.NineGridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/31 15:52
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductReportAdapter extends DelegateAdapter.Adapter {

    private Context mContext;
    public boolean hasPadding;
    public int padTop;
    private List<ProductReportBean.DataBean.ReportBean.ListBean> datas;
    public List<UnFocusMemberBean.DataBean.ActiveMemberBean> unFocusBean;
    private UserBean.DataEntity.MemberEntity user;
    private long status;
    public ProductReportAdapter(Context context, List<ProductReportBean.DataBean.ReportBean.ListBean> datas, long id) {
        this.mContext = context;
        this.datas = datas;
        status=id;
        user = Session.getInstance().getUserFromFile(context);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper=new LinearLayoutHelper();
                linearLayoutHelper.setPaddingTop(ScreenUtil.dip2px(6));
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_product_report_item, new LinearLayout(mContext), false);
                return new ProductReportHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

                int index = 0;
                    index = position - 1;
                    index = position;
                final ProductReportBean.DataBean.ReportBean.ListBean reportItem = datas.get(index);
                final ProductReportHolder reportHolder = (ProductReportHolder) holder;
                if (user != null) {
                }
                reportHolder.mImgAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ProductDetailActivity.class);
                        intent.putExtra("id", (long) reportItem.getProductId());
                        mContext.startActivity(intent);
                    }
                });
                UniversalImageLoader.displayImage(mContext,reportItem.getProductImg(),reportHolder.mImgAvatar);
                reportHolder.mContentTv.setText(reportItem.getContent());
                reportHolder.mNameTv.setText(reportItem.getProductName());
                reportHolder.mTvCheckStatue.setText(reportItem.getStatusName());
                SimpleDateFormat df3 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                String time = df3.format(reportItem.getSubmitDate());
                reportHolder.mTvReportTime.setText(time);
                reportHolder.mTvReasonDesc.setText(reportItem.getVerifyReason());
                setPictures(reportItem,reportHolder.mNineGrid,reportItem.getPic());
                if(status==1) {//待审核
                    reportHolder.mRlUserAction.setVisibility(View.VISIBLE);
                    reportHolder.mRlResult.setVisibility(View.GONE);
                }else if(status==-1) {//审核不通过
                    reportHolder.mRlUserAction.setVisibility(View.VISIBLE);
                    reportHolder.mTvCancleCommit.setVisibility(View.GONE);
                    reportHolder.mRlResult.setVisibility(View.VISIBLE);
                }else if(status==2) {//审核通过
                    reportHolder.mRlResult.setVisibility(View.GONE);
                    reportHolder.mTvReportTime.setTextColor(mContext.getResources().getColor(R.color.color_black87));
                    reportHolder.mTvReportTime.setText(reportItem.getVerifyReason());
                    reportHolder.mTvCancleCommit.setVisibility(View.GONE);
                }else {//已取消
                    reportHolder.mRlResult.setVisibility(View.GONE);
                    reportHolder.mTvCancleCommit.setVisibility(View.GONE);
                }
                reportHolder.mTvCancleCommit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelCommit(reportItem,position);

                    }
                });
            if(position==datas.size()-1 ) {
                reportHolder.viewInterval.setVisibility(View.INVISIBLE);
                }else{
                reportHolder.viewInterval.setVisibility(View.VISIBLE);
            }
                reportHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ProductReportDetailActivity.class);
                        intent.putExtra("status",status);
                        intent.putExtra("item",reportItem);
                        mContext.startActivity(intent);
                    }
                });

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
        nineGridView.setAdapter(new ClickNineGridViewAdapter(mContext, imageInfos) {
            @Override
            protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List<ImageInfo> imageInfo) {
                if (index == 2) {
                    Intent intent = new Intent(mContext, ProductReportDetailActivity.class);
                    intent.putExtra("item", listEntity);
                    intent.putExtra("status",status);
                    mContext.startActivity(intent);
                } else {
                    super.onImageItemClick(context, nineGridView, index, imageInfo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    private void cancelCommit(ProductReportBean.DataBean.ReportBean.ListBean reportItem, final int position) {

        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.PRODUCT_REPORT_CANCEL_URL,reportItem.getId()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ProductReportBean>() {
            @Override
            public void onResponse(ProductReportBean reportBean) {
                Util.showToast(mContext,"取消成功");
                datas.remove(position);
                notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(mContext, Util.checkErrorType(error));

            }
        });
    }

}
