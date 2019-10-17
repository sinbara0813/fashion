package com.d2cmall.buyer.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.VideoSource;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayer;
import com.d2cmall.buyer.widget.nicevideo.TxVideoPlayerController;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2018/1/4 11:36
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class VideoBanner1 extends DefineBaseIndicatorBanner<VideoSource,VideoBanner1> {

    private NiceVideoPlayer niceVideoPlayer;
    private boolean showNewYear;
    private ImageView.ScaleType scaleType;
    private double collagePrice;
    private int collageId;
    private long productId;
    private String contentStr;
    private RequestManager requestManager;

    public VideoBanner1(Context context) {
        this(context,null);
    }

    public VideoBanner1(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VideoBanner1(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
    }

    @Override
    public View onCreateItemView(final int position) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_video_banner_item,null);
        ImageView imageView= (ImageView) view.findViewById(R.id.image);
        ImageView imageView1= (ImageView) view.findViewById(R.id.image_new_year);
        NiceVideoPlayer niceVideoPlayer= (NiceVideoPlayer) view.findViewById(R.id.nice_video_player);
        if (collageId>0){
            RelativeLayout collageLL=view.findViewById(R.id.collage_ll);
            TextView collagePriceTv=view.findViewById(R.id.collage_price);
            showCollageLayout(collageLL,collagePriceTv);
        }
        if (!Util.isEmpty(contentStr)){
            TextView time=view.findViewById(R.id.time);
            time.setVisibility(VISIBLE);
            time.setText(contentStr);
        }
        if (position<mDatas.size()){
            if (showNewYear){
                if (position==0){
                    if (requestManager!=null){
                        requestManager.load("https://static.d2c.cn/img/promo/icon_mark_bigspring.png").into(imageView1);
                    }else {
                        UniversalImageLoader.displayImage(mContext,"https://static.d2c.cn/img/promo/icon_mark_bigspring.png",imageView1);
                    }
                    imageView1.setVisibility(VISIBLE);
                }else {
                    imageView1.setVisibility(GONE);
                }
            }
            if (!Util.isEmpty(mDatas.get(position).videoUrl)){
                imageView.setVisibility(GONE);
                niceVideoPlayer.setVisibility(VISIBLE);
                String videoUrl=mDatas.get(position).videoUrl;
                if (!mDatas.get(position).videoUrl.startsWith("http")) {
                    videoUrl = Constants.IMG_HOST + mDatas.get(position).videoUrl;
                }
                TxVideoPlayerController txVideoPlayerController = new TxVideoPlayerController(mContext);
                niceVideoPlayer.setController(txVideoPlayerController);
                if (requestManager!=null){
                    requestManager.load(Util.getD2cPicUrl(mDatas.get(position).picUrl)).into(txVideoPlayerController.getImage());
                }else {
                    UniversalImageLoader.displayImage(mContext, Util.getD2cPicUrl(mDatas.get(position).picUrl), txVideoPlayerController.getImage());
                }
                niceVideoPlayer.setUp(videoUrl, null);
                this.niceVideoPlayer=niceVideoPlayer;
            }else {
                imageView.setVisibility(VISIBLE);
                niceVideoPlayer.setVisibility(GONE);
                if (requestManager!=null){
                    requestManager.load(Util.getD2cPicUrl(mDatas.get(position).picUrl)).skipMemoryCache(true).placeholder(mDatas.get(position).loading).error(mDatas.get(position).loading).into(imageView);
                }else {
                    UniversalImageLoader.displayImage(mContext,mDatas.get(position).picUrl,imageView
                            ,mDatas.get(position).loading,mDatas.get(position).loading);
                }
            }
        }
        return view;
    }

    public void showCollageLayout(RelativeLayout collageLl,TextView collagePriceTv) {
        collageLl.setVisibility(View.VISIBLE);
        collageLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,ProductDetailActivity.class);
                intent.putExtra("id",productId);
                intent.putExtra("collageId",collageId);
                mContext.startActivity(intent);
            }
        });
        StringBuilder builder=new StringBuilder();
        builder.append("拼团价: ¥").append(Util.getNumberFormat(collagePrice)).append("\n").append("跟好友一起买更划算");
        int length=Util.getNumberFormat(collagePrice).length();
        SpannableString sb=new SpannableString(builder.toString());
        RelativeSizeSpan sizeSpan=new RelativeSizeSpan((float)1.6);
        sb.setSpan(sizeSpan,5,6+length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        StyleSpan styleSpan=new StyleSpan(Typeface.BOLD);
        sb.setSpan(styleSpan,5,5+length,Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        collagePriceTv.setText(sb);
    }

    public void start(){
        if (niceVideoPlayer!=null&&niceVideoPlayer.isIdle()){
            niceVideoPlayer.start();
        }
    }

    public VideoBanner1 setNewYear(boolean is){
        this.showNewYear=is;
        return this;
    }

    public VideoBanner1 setScaleType(ImageView.ScaleType scaleType){
        this.scaleType=scaleType;
        return this;
    }

    public VideoBanner1 setCollagePrice(double collagePrice) {
        this.collagePrice = collagePrice;
        return this;
    }

    public VideoBanner1 setRequestManager(RequestManager requestManager) {
        this.requestManager = requestManager;
        return this;
    }

    public VideoBanner1 setCollageId(int collageId) {
        this.collageId = collageId;
        return this;
    }

    public VideoBanner1 setProductId(long productId) {
        this.productId = productId;
        return this;
    }

    public VideoBanner1 setContentStr(String contentStr) {
        this.contentStr = contentStr;
        return this;
    }

    public NiceVideoPlayer getNiceVideoPlayer() {
        return niceVideoPlayer;
    }
}
