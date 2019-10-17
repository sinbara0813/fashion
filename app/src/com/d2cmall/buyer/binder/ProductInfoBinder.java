package com.d2cmall.buyer.binder;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BrandDetailActivity;
import com.d2cmall.buyer.activity.CombProductActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.activity.SimpleImageActivity;
import com.d2cmall.buyer.adapter.ProductCombRvAdapter;
import com.d2cmall.buyer.adapter.ProductDesignRecommendAdapter;
import com.d2cmall.buyer.api.ProductCollectApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.base.BaseViewBinder;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.BrandRecommendBean;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.holder.ProductInfoHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.CenterAlignImageSpan;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.FullyLinearLayoutManager;
import com.d2cmall.buyer.util.RoundBgSpan1;
import com.d2cmall.buyer.util.RoundBgSpan2;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.CouponPop;
import com.d2cmall.buyer.widget.PromotionPop;
import com.d2cmall.buyer.widget.ServicePop;
import com.d2cmall.buyer.widget.ShiliPop;
import com.d2cmall.buyer.widget.ShowPopImageView;
import com.tendcloud.tenddata.TCAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/28 17:15
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductInfoBinder implements BaseViewBinder<ProductInfoHolder> {

    private Context mContext;
    private ProductDetailBean productDetail;
    private int lineCount;
    private String code = "D2C8001";
    private Handler mHandler;
    private long startTime;
    private long endTime;
    private int IMAGE_SIZE=0;
    private long parentId;
    public boolean hasSum;
    private ImageView collectIv;
    private TextView selectStandardTv;

    public ProductInfoBinder(Context context, ProductDetailBean detailBean) {
        this.mContext = context;
        this.productDetail = detailBean;
        if (Session.getInstance().getUserFromFile(context)!=null){
            parentId=Session.getInstance().getUserFromFile(context).getPartnerId();
        }
    }

    @Override
    public ProductInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_product_info, parent, false);
        //View view= LayoutInflater.from(mContext).inflate(R.layout.layout_product_info,null); //这种方式获取layoutParam的宽高为负数
        return new ProductInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductInfoHolder productInfoHolder, int position) {
        setData(productInfoHolder);
    }

    @Override
    public void onBindViewHolderWithOffer(ProductInfoHolder productInfoHolder, int position, int offsetTotal) {

    }

    public void setData(final ProductInfoHolder productInfoHolder) {
        //商品名称
        setProductName(productInfoHolder);
        //显示跨境标识
        setCrossTag(productInfoHolder);
        //规格设置
        setStandard(productInfoHolder);
        //设置价格
        setPrice(productInfoHolder);
        if (productDetail.getData().getFlashPromotion()!=null){
            //设置限时购活动
            setFlashPromotion(productInfoHolder);
        }else if (productDetail.getData().getSoonPromotion()!=null&&productDetail.getData().getSoonPromotion().getAdvance()==1){
            //设置限时活动
            setSoonPromotion(productInfoHolder);
        }
        //设置收藏状态
        setCollect(productInfoHolder);
        //设置分销返利
        setRebate(productInfoHolder);
        //设置施力说
        setIntroduce(productInfoHolder);
        //设置活动优惠券
        setCouponPromotion(productInfoHolder);
        //设置服务
        setServer(productInfoHolder);
        //设置品牌
        setBrand(productInfoHolder);
        //设置组合商品
        setComb(productInfoHolder);
    }

    private void setComb(ProductInfoHolder productInfoHolder) {
        if(productDetail.getData().getProductComb()!=null && productDetail.getData().getProductComb().size()>0
                && productDetail.getData().getProductComb().get(0)!=null && productDetail.getData().getProductComb().get(0).getProducts()!=null && productDetail.getData().getProductComb().get(0).getProducts().size()>0){
            productInfoHolder.llComb.setVisibility(View.VISIBLE);
            productInfoHolder.tvCombTitle.setText(productDetail.getData().getProductComb().get(0).getName());
            productInfoHolder.tvCombTitle.setTextSize(16);
            productInfoHolder.rlTitle.setVisibility(View.VISIBLE);
            productInfoHolder.titleName.setText("组合商品");
            productInfoHolder.tvCombPrice.setText("¥"+ Util.getNumberFormat(productDetail.getData().getProductComb().get(0).getPrice()));
            productInfoHolder.topLine.setVisibility(View.GONE);
            productInfoHolder.combBottomLine.setVisibility(View.VISIBLE);
            if(productDetail.getData().getProductComb().size()>1){
                productInfoHolder.titleMore.setVisibility(View.VISIBLE);
                productInfoHolder.titleMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //切换tab到搭配
                        ActionBean actionBean=new ActionBean(Constants.ActionType.CHANGE_PRODUCT_PAGE);
                        actionBean.put("position",3);
                        EventBus.getDefault().post(actionBean);
                    }
                });
            }else{
                productInfoHolder.titleMore.setVisibility(View.GONE);
            }
            if (productDetail.getData().getProductComb().get(0).getOriginalCost()>productDetail.getData().getProductComb().get(0).getPrice()){
                productInfoHolder.tvOriginPrice.setVisibility(View.VISIBLE);
                productInfoHolder.tvOriginPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
                productInfoHolder.tvOriginPrice.setText("¥"+ Util.getNumberFormat(productDetail.getData().getProductComb().get(0).getOriginalCost()));
                productInfoHolder.tvGapPrice.setText("立省"+(productDetail.getData().getProductComb().get(0).getOriginalCost()-productDetail.getData().getProductComb().get(0).getPrice())+" >");
            }else{
                productInfoHolder.tvOriginPrice.setVisibility(View.INVISIBLE);
                productInfoHolder.tvGapPrice.setText("立即购买");
            }
            if (productDetail.getData().getProductComb().get(0).getProducts()==null){
                return;
            }

            if (productDetail.getData().getProductComb().get(0).getProducts().size() > 0) {
                ProductCombRvAdapter adapter = new ProductCombRvAdapter(mContext, productDetail.getData().getProductComb().get(0).getProducts());
                FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                productInfoHolder.productCombRv.requestDisallowInterceptTouchEvent(true);
                productInfoHolder.productCombRv.setLayoutManager(layoutManager);
                productInfoHolder.productCombRv.setAdapter(adapter);
            }
            productInfoHolder.llComb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, CombProductActivity.class);
                    intent.putExtra("productCombId",productDetail.getData().getProductComb().get(0).getId());
                    mContext.startActivity(intent);
                }
            });
        }else {
            productInfoHolder.llComb.setVisibility(View.GONE);
        }
    }

    /**
     * 设置商品名称
     * @param productInfoHolder
     */
    private void setProductName(ProductInfoHolder productInfoHolder){
        if (productDetail.getData().getProduct().getIsTaxation()==1&&"CROSS".equals(productDetail.getData().getProduct().getProductTradeType())){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("包税").append(productDetail.getData().getProduct().getName());
            SpannableString spannableString = new SpannableString(stringBuilder.toString());
            spannableString.setSpan(new RoundBgSpan1(), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            productInfoHolder.productName.setText(spannableString);
        }else {
            productInfoHolder.productName.setText(productDetail.getData().getProduct().getName());
        }
        if (parentId>0){
            productInfoHolder.productName.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText("name", productDetail.getData().getProduct().getName()+Constants.SHARE_URL+"/product/"+productDetail.getData().getProduct().getId()+
                            (parentId>0?"?parent_id="+parentId:"")));
                    Toast.makeText(mContext,"内容已复制",Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }
        if (!Util.isEmpty(productDetail.getData().getProduct().getRemark())){
            productInfoHolder.remark.setVisibility(View.VISIBLE);
            productInfoHolder.remark.setText(productDetail.getData().getProduct().getRemark());
        }else {
            productInfoHolder.remark.setVisibility(View.GONE);
        }
    }

    /**
     * 设置跨境标识
     * @param productInfoHolder
     */
    private void setCrossTag(ProductInfoHolder productInfoHolder){
        if ("CROSS".equals(productDetail.getData().getProduct().getProductTradeType())&&productDetail.getData().getCountry()!=null){
            productInfoHolder.countryLl.setVisibility(View.VISIBLE);
            if (!Util.isEmpty(productDetail.getData().getCountry().getPic2())){
                UniversalImageLoader.displayImage(mContext,productDetail.getData().getCountry().getPic2(),productInfoHolder.countryPic);
            }
            productInfoHolder.countryName.setText(productDetail.getData().getCountry().getName());
            productInfoHolder.globalIv.setVisibility(View.VISIBLE);
            if ("CAOMEI".equals(productDetail.getData().getProduct().getProductSource())){
                productInfoHolder.tvCaoMei.setVisibility(View.VISIBLE);
            }
        }else {
            productInfoHolder.countryLl.setVisibility(View.GONE);
        }
    }

    /**
     * 设置规格
     * @param productInfoHolder
     */
    private void setStandard(ProductInfoHolder productInfoHolder){
        if (productDetail.getData().getProduct().getMark()!=0&&productDetail.getData().getProduct().getStore()>0){
            selectStandardTv=productInfoHolder.selectStandardTv;
            int colorSize=0;
            int sizeSize=0;
            if (productDetail.getData().getProduct().getColors()!=null){
                colorSize=productDetail.getData().getProduct().getColors().size();
            }
            if (productDetail.getData().getProduct().getSizes()!=null){
                sizeSize=productDetail.getData().getProduct().getSizes().size();
            }
            if(colorSize==0||sizeSize==0){ //单规格
                if (colorSize==0){
                    if (sizeSize==1){
                        productInfoHolder.selectStandardTv.setText(productDetail.getData().getProduct().getSizes().get(0).getValue());
                    }else {
                        productInfoHolder.selectStandardTv.setText("选择 "+productDetail.getData().getProduct().getSizes().get(0).getName());
                    }
                }else if (sizeSize==0){
                    if (colorSize==1){
                        productInfoHolder.selectStandardTv.setText(productDetail.getData().getProduct().getColors().get(0).getValue());
                    }else {
                        productInfoHolder.selectStandardTv.setText("选择 "+productDetail.getData().getProduct().getColors().get(0).getName());
                    }
                }
            }else {
                if (colorSize==1&&sizeSize==1){
                    productInfoHolder.selectStandardTv.setText(productDetail.getData().getProduct().getColors().get(0).getValue()+" "
                            +productDetail.getData().getProduct().getSizes().get(0).getValue());
                }else if (colorSize==1){
                    productInfoHolder.selectStandardTv.setText("选择 "+productDetail.getData().getProduct().getSizes().get(0).getName());
                }else if (sizeSize==1){
                    productInfoHolder.selectStandardTv.setText("选择 "+productDetail.getData().getProduct().getColors().get(0).getName());
                }else {
                    productInfoHolder.selectStandardTv.setText("选择 "+productDetail.getData().getProduct().getColors().get(0).getName()+
                            productDetail.getData().getProduct().getSizes().get(0).getName());
                }
            }

            productInfoHolder.standardLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActionBean actionBean=new ActionBean(Constants.ActionType.STANDARDMORE);
                    actionBean.put("id",productDetail.getData().getProduct().getId());
                    EventBus.getDefault().post(actionBean);
                }
            });
        }else {
            productInfoHolder.standardLl.setVisibility(View.GONE);
        }
    }

    /**
     * 设置价格
     * @param productInfoHolder
     */
    private void setPrice(ProductInfoHolder productInfoHolder){
        if (productDetail.getData().getProduct().getMark()==0){//已下架
            productInfoHolder.productPrice.setText("暂无报价");
        } else if (productDetail.getData().getCrowdItem() != null) {
            if (productDetail.getData().getCrowdItem().getOriginalCost() > productDetail.getData().getCrowdItem().getCurrentPrice()) {
                setPrice(productInfoHolder.productPrice,productDetail.getData().getCrowdItem().getCurrentPrice(),productDetail.getData().getCrowdItem().getOriginalCost(),productDetail.getData().getProduct().getTaxPrice()>0&&productDetail.getData().getProduct().getIsTaxation()==0,"#DA000000");
            } else {
                setPrice(productInfoHolder.productPrice,productDetail.getData().getCrowdItem().getCurrentPrice(),0,productDetail.getData().getProduct().getTaxPrice()>0&&productDetail.getData().getProduct().getIsTaxation()==0,"#DA000000");
            }
        } else {
            int type = checkPromotion();
            setPrice(productInfoHolder.productPrice, productDetail.getData().getProduct().getMinPrice(),0,productDetail.getData().getProduct().getTaxPrice()>0&&productDetail.getData().getProduct().getIsTaxation()==0,"#DA000000");
            if (type == 0) {//没有商品活动
                if (productDetail.getData().getProduct().getOriginalPrice() > productDetail.getData().getProduct().getMinPrice()) {
                    setPrice(productInfoHolder.productPrice, productDetail.getData().getProduct().getMinPrice(),productDetail.getData().getProduct().getOriginalPrice(),productDetail.getData().getProduct().getTaxPrice()>0&&productDetail.getData().getProduct().getIsTaxation()==0,"#DA000000");
                }
            } else if (type == 1) { //一口价
                if (productDetail.getData().getProduct().getSellPrice() > productDetail.getData().getProduct().getMinPrice()) {
                    setPrice(productInfoHolder.productPrice, productDetail.getData().getProduct().getMinPrice(),productDetail.getData().getProduct().getSellPrice(),productDetail.getData().getProduct().getTaxPrice()>0&&productDetail.getData().getProduct().getIsTaxation()==0,"#DA000000");
                }
            } else if (type == 2) { //折扣
                if (productDetail.getData().getProduct().getSellPrice() > productDetail.getData().getProduct().getMinPrice()) {
                    setPrice(productInfoHolder.productPrice, productDetail.getData().getProduct().getMinPrice(),productDetail.getData().getProduct().getSellPrice(),productDetail.getData().getProduct().getTaxPrice()>0&&productDetail.getData().getProduct().getIsTaxation()==0,"#DA000000");
                }
            }
            if (productDetail.getData().getProduct().getTaxPrice()>0&&productDetail.getData().getProduct().getIsTaxation()==0){
                productInfoHolder.productPrice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Util.showToast(mContext,"该商品预计税费：¥"+Util.getNumberFormat(productDetail.getData().getProduct().getTaxPrice()*productDetail.getData().getProduct().getMinPrice())+"，实际结算税费以提交订单时应付总额明细为准");
                    }
                });
            }
        }
    }

    /**
     * 设置限时购活动
     * @param productInfoHolder
     */
    private void setFlashPromotion(final ProductInfoHolder productInfoHolder){
        startTime=productDetail.getData().getFlashPromotion().getStartDate().getTime();
        endTime=productDetail.getData().getFlashPromotion().getEndDate().getTime();
        if (startTime>0||endTime>0){
            //设置初始值
            long nowTime=System.currentTimeMillis();
            long startOffer=startTime-nowTime;
            long endOffer=endTime-nowTime;
            if (endOffer>0){
                if (startOffer>0){//活动还没开始
                    setNotStartStyle(productInfoHolder,true);
                }else{  //活动还没结束
                    setNotEndStyle(productInfoHolder,true);
                }
                if (mHandler != null) {
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler=null;
                }
                mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        //更改时间
                        long nowTime=System.currentTimeMillis();
                        long startOffer=startTime-nowTime;
                        long endOffer=endTime-nowTime;
                        if (startOffer>0){//活动还没开始
                            setEndTime(startTime,productInfoHolder.timeHour, productInfoHolder.timeMinute, productInfoHolder.timeMouse);
                        }else if (endOffer>0){  //活动还没结束
                            setNotEndStyle(productInfoHolder,true);
                        }else { //活动已结束
                            productInfoHolder.hFlashLL.setVisibility(View.GONE);
                            if (mHandler!=null){
                                mHandler.removeCallbacksAndMessages(null);
                                mHandler=null;
                            }
                        }
                        if (mHandler!=null){
                            mHandler.sendEmptyMessageDelayed(1, 1000);
                        }
                    }
                };
                mHandler.sendEmptyMessageDelayed(1, 1000);
            }
        }
        if (!Util.isEmpty(productDetail.getData().getFlashPromotion().getPriceBackPic())){
            Glide.with(mContext).load(productDetail.getData().getFlashPromotion().getPriceBackPic()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    if (productInfoHolder.hFlashLL.getVisibility()==View.VISIBLE){
                        productInfoHolder.hFlashLL.setBackground(new BitmapDrawable(mContext.getResources(),resource));
                    }
                    if (productInfoHolder.productFlashSale.getVisibility()==View.VISIBLE){
                        productInfoHolder.productFlashSale.setBackground(new BitmapDrawable(mContext.getResources(),resource));
                    }
                }
            });
        }
    }

    /**
     * 设置限时活动
     * @param productInfoHolder
     */
    private void setSoonPromotion(final ProductInfoHolder productInfoHolder){
        startTime=productDetail.getData().getSoonPromotion().getStartTime().getTime();
        endTime=productDetail.getData().getSoonPromotion().getEndTime().getTime();
        if (startTime>0||endTime>0) {
            //设置初始值
            long nowTime = System.currentTimeMillis();
            long startOffer = startTime - nowTime;
            long endOffer = endTime - nowTime;
            if (endOffer > 0) {
                if (startOffer > 0) {//活动还没开始
                    setNotStartStyle(productInfoHolder,false);
                } else {  //活动还没结束
                    setNotEndStyle(productInfoHolder,false);
                    if (mHandler != null) {
                        mHandler.removeCallbacksAndMessages(null);
                        mHandler = null;
                    }
                    mHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            //更改时间
                            long nowTime = System.currentTimeMillis();
                            long startOffer = startTime - nowTime;
                            long endOffer = endTime - nowTime;
                            if (startOffer > 0) {//活动还没开始
                            } else if (endOffer > 0) {  //活动还没结束
                                setNotEndStyle(productInfoHolder,false);
                            } else { //活动已结束
                                productInfoHolder.hFlashLL.setVisibility(View.GONE);
                                if (mHandler != null) {
                                    mHandler.removeCallbacksAndMessages(null);
                                    mHandler = null;
                                }
                            }
                            if (mHandler!=null){
                                mHandler.sendEmptyMessageDelayed(1, 1000);
                            }
                        }
                    };
                    mHandler.sendEmptyMessageDelayed(1, 1000);
                }
            }
        }
        if (!Util.isEmpty(productDetail.getData().getSoonPromotion().getPriceBackPic())){
            Glide.with(mContext).load(Util.getD2cPicUrl(productDetail.getData().getSoonPromotion().getPriceBackPic())).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    if (productInfoHolder.hFlashLL.getVisibility()==View.VISIBLE){
                        productInfoHolder.hFlashLL.setBackground(new BitmapDrawable(mContext.getResources(),resource));
                    }
                    if (productInfoHolder.productFlashSale.getVisibility()==View.VISIBLE){
                        productInfoHolder.productFlashSale.setBackground(new BitmapDrawable(mContext.getResources(),resource));
                    }
                }
            });
        }
    }

    /**
     * 设置未结束
     * @param productInfoHolder
     * @param isFlash
     */
    private void setNotEndStyle(ProductInfoHolder productInfoHolder,boolean isFlash){
        productInfoHolder.productPrice.setVisibility(View.GONE);
        RelativeLayout.LayoutParams rl= (RelativeLayout.LayoutParams) productInfoHolder.productName.getLayoutParams();
        rl.setMargins(0,ScreenUtil.dip2px(16),ScreenUtil.dip2px(38),ScreenUtil.dip2px(10));
        productInfoHolder.hFlashLL.setVisibility(View.VISIBLE);
        productInfoHolder.productFlashSale.setVisibility(View.GONE);
        if (isFlash){
            if (productDetail.getData().getProduct().getOriginalPrice() > productDetail.getData().getProduct().getFlashPrice()){
                setPrice(productInfoHolder.hPrice,productDetail.getData().getProduct().getFlashPrice(),productDetail.getData().getProduct().getOriginalPrice(),false,"#ffffff");
            }else {
                setPrice(productInfoHolder.hPrice,productDetail.getData().getProduct().getFlashPrice(),0,false,"#ffffff");
            }//设置价格
            productInfoHolder.hFlashLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.urlAction(mContext,productDetail.getData().getFlashPromotion().getFlashUrl());
                }
            });
        }else {
            if (productDetail.getData().getProduct().getOriginalPrice() > productDetail.getData().getProduct().getSoonPrice().doubleValue()){
                setPrice(productInfoHolder.hPrice,productDetail.getData().getProduct().getSoonPrice().doubleValue(),productDetail.getData().getProduct().getOriginalPrice(),false,"#ffffff");
            }else {
                setPrice(productInfoHolder.hPrice,productDetail.getData().getProduct().getSoonPrice().doubleValue(),0,false,"#ffffff");
            }//设置价格
            productInfoHolder.hIv.setVisibility(View.GONE);
            productInfoHolder.hTv.setVisibility(View.VISIBLE);
            productInfoHolder.hTv.setText(productDetail.getData().getSoonPromotion().getPrefix());
            productInfoHolder.hFlashLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.urlAction(mContext, productDetail.getData().getSoonPromotion().getPromotionUrl());
                }
            });
        }
        setPromotionTime(endTime,productInfoHolder.hDay,productInfoHolder.hDayTag,productInfoHolder.hHour, productInfoHolder.hMinute, productInfoHolder.hMouse);
    }

    /**
     * 活动未开始样式
     * @param productInfoHolder
     * @param isFlash
     */
    private void setNotStartStyle(ProductInfoHolder productInfoHolder,boolean isFlash){
        productInfoHolder.productFlashSale.setVisibility(View.VISIBLE);
        if (isFlash){
            productInfoHolder.timeTag.setText("距开始");
            productInfoHolder.flashPrice.setText("限时价:¥"+Util.getNumberFormat(productDetail.getData().getProduct().getFlashPrice()));
            setPromotionTime(startTime,productInfoHolder.timeDay,productInfoHolder.timeDayTag,productInfoHolder.timeHour, productInfoHolder.timeMinute, productInfoHolder.timeMouse);
            productInfoHolder.productFlashSale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.urlAction(mContext,productDetail.getData().getFlashPromotion().getFlashUrl());
                }
            });
        }else {
            productInfoHolder.promotionIv.setVisibility(View.GONE);
            productInfoHolder.promotionTv.setVisibility(View.VISIBLE);
            productInfoHolder.promotionTv.setText(productDetail.getData().getSoonPromotion().getPrefix());
            productInfoHolder.flashPrice.setText("¥" + Util.getNumberFormat(productDetail.getData().getProduct().getSoonPrice().doubleValue()));
            productInfoHolder.promotionTimeLl.setVisibility(View.GONE);
            productInfoHolder.promotionStartTime.setVisibility(View.VISIBLE);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日 HH:mm");
            productInfoHolder.promotionStartTime.setText(simpleDateFormat.format(productDetail.getData().getSoonPromotion().getStartTime()) + "开抢");
            productInfoHolder.productFlashSale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.urlAction(mContext, productDetail.getData().getSoonPromotion().getPromotionUrl());
                }
            });
        }
    }

    /**
     * 设置收藏
     * @param productInfoHolder
     */
    private void setCollect(final ProductInfoHolder productInfoHolder){
        collectIv=productInfoHolder.productCollectIv;
        if ("1".equals(productDetail.getData().getProduct().getCollectioned())) {
            productInfoHolder.productCollectIv.setImageResource(R.mipmap.icon_collect_red);
        } else {
            productInfoHolder.productCollectIv.setImageResource(R.mipmap.icon_collect_black);
        }
        productInfoHolder.productCollectIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, 0)) {
                    collect(productInfoHolder.productCollectIv);
                }
            }
        });
    }

    /**
     * 设置分销返利
     * @param productInfoHolder
     */
    private void setRebate(final ProductInfoHolder productInfoHolder){
        if (productDetail.getData().getProduct().getMark()==1&&parentId>0&&productDetail.getData().getProduct().getSecondRatio()>0&&productDetail.getData().getProduct().getGrossRatio()>0){
            double system=1.0;
            try {
                system=Double.valueOf(productDetail.getData().getRatio());
            }catch (Exception e){
                e.printStackTrace();
            }
            double bi= productDetail.getData().getProduct().getSecondRatio()*productDetail.getData().getProduct().getGrossRatio()*system*100;
            String bis=String.valueOf((int)(bi+0.5));
            productInfoHolder.fxRl.setVisibility(View.VISIBLE);
            if (hasSum){
                productInfoHolder.fxMatter.setVisibility(View.VISIBLE);
            }
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("可获得实付").append(bis).append("%返利").append("\n").append("最高可返¥").append(Util.getNumberFormat((int)(bi+0.5)*productDetail.getData().getProduct().getMinPrice()/100));
            SpannableString sb=new SpannableString(stringBuilder.toString());
            int index=stringBuilder.toString().indexOf("最");
            ForegroundColorSpan colorSpan=new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_red));
            sb.setSpan(colorSpan,index,stringBuilder.toString().length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.75f);
            sb.setSpan(sizeSpan, index, stringBuilder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            productInfoHolder.fxTv.setText(sb);
            productInfoHolder.fxMatter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.urlAction(mContext,"/product/detail/"+productDetail.getData().getProduct().getId()+"?type=summary&parent_id="+parentId);
                }
            });
            productInfoHolder.fxShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadPics();
                }
            });
            productInfoHolder.fxQr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toSimpleActivity(productInfoHolder.productPrice.getText().toString());
                }
            });
        }
    }

    /**
     * 设置施力说
     * @param productInfoHolder
     */
    private void setIntroduce(final ProductInfoHolder productInfoHolder){
        if (!Util.isEmpty(productDetail.getData().getProduct().getAdPic())){
            Glide.with(mContext).load(Util.getD2cPicUrl(productDetail.getData().getProduct().getAdPic())).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    productInfoHolder.adIv.getLayoutParams().height=resource.getHeight()*(ScreenUtil.getDisplayWidth()-ScreenUtil.dip2px(32))/resource.getWidth();
                    productInfoHolder.adIv.setImageBitmap(resource);
                    productInfoHolder.adIv.setVisibility(View.VISIBLE);
                }
            });
            if (!Util.isEmpty(productDetail.getData().getProduct().getAdUrl())){
                productInfoHolder.adIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Util.urlAction(mContext,productDetail.getData().getProduct().getAdUrl());
                    }
                });
            }
        }else {
            productInfoHolder.adIv.setVisibility(View.GONE);
        }
        String data = productDetail.getData().getWeixin();
        if (!Util.isEmpty(data)) {
            try {
                JSONObject object = new JSONObject(data);
                code = object.optString("code");
                productInfoHolder.bossChat.setText("施力个人微信号：" + object.optString("code") + "(点击复制)");
                productInfoHolder.bossContact.setText(object.optString("word"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        productInfoHolder.bossChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setPrimaryClip(ClipData.newPlainText("code", code));
                //弹窗提示是否跳转微信
                new AlertDialog.Builder(mContext)
                        .setMessage("复制成功，请打开微信添加好友")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                jumpToWX();
                            }
                        })
                        .show();
            }
        });
        if (Util.isEmpty(productDetail.getData().getProduct().getRecommendation())) {
            productInfoHolder.productInfoTvLl.setVisibility(View.GONE);
        } else {
            productInfoHolder.productInfoTvLl.setVisibility(View.VISIBLE);
            final String textInfo = productDetail.getData().getProduct().getRecommendation();
            SpannableString sb=new SpannableString("X "+textInfo);
            Drawable drawable=mContext.getResources().getDrawable(R.mipmap.pic_goodsdetail_shilishuo);
            drawable.setBounds(0,0,ScreenUtil.dip2px(50),ScreenUtil.dip2px(20));
            ImageSpan imageSpan=new ImageSpan(drawable);
            sb.setSpan(imageSpan,0,1,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            productInfoHolder.productInfoTv.setText(sb);
        }
    }


    private void showSLPop(View view){
        String code="";
        if (!Util.isEmpty(productDetail.getData().getWeixin())) {
            try {
                JSONObject object = new JSONObject(productDetail.getData().getWeixin());
                code = object.optString("code");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("code", code));
        StringBuilder builder=new StringBuilder();
        builder.append("微信号").append(code).append("已复制").append("\n")
                .append("请去微信添加好友  领取福利");
        ShiliPop shiliPop=new ShiliPop(mContext,builder.toString());
        shiliPop.show(view);
    }

    /**
     * 设置活动优惠券
     * @param productInfoHolder
     */
    private void setCouponPromotion(ProductInfoHolder productInfoHolder){
        setCoupon(productInfoHolder);
        setPromotion(productInfoHolder);
    }

    /**
     * 设置活动
     * @param productInfoHolder
     */
    private void setPromotion(ProductInfoHolder productInfoHolder){
        if (productDetail.getData().getPromotions().size() > 0) {
            productInfoHolder.productPromotionLl.setVisibility(View.VISIBLE);
            int size = productDetail.getData().getPromotions().size();
            productInfoHolder.productPromotionContentLl.removeAllViews();
            for (int i = 0; i < size; i++) {
                TextView textView=new TextView(mContext);
                textView.setTextColor(mContext.getResources().getColor(R.color.color_black85));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setMaxLines(1);
                textView.setText(getPromotionContent(productDetail.getData().getPromotions().get(i)));
                if (i!=0){
                    textView.setPadding(0,ScreenUtil.dip2px(10),0,0);
                }
                productInfoHolder.productPromotionContentLl.addView(textView);
            }
            productInfoHolder.productPromotionContentLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PromotionPop promotionPop = new PromotionPop(mContext, productDetail.getData().getPromotions());
                    promotionPop.show(v);
                }
            });
        } else {
            productInfoHolder.promotionLine.setVisibility(View.GONE);
            productInfoHolder.productPromotionLl.setVisibility(View.GONE);
            if (productInfoHolder.productCouponLl.getVisibility()==View.GONE){
                productInfoHolder.middleLl.setVisibility(View.GONE);
            }
        }
    }

    private SpannableString getPromotionContent(ProductDetailBean.DataBean.PromotionsBean promotionsBean){
        StringBuilder builder=new StringBuilder();
        builder.append(promotionsBean.getPromotionTypeName());
        if (promotionsBean.getPromotionScope()==0){
            builder.append(promotionsBean.getPromotionName());
        }else {
            builder.append(promotionsBean.getPromotionSulo());
        }
        int index=promotionsBean.getPromotionTypeName().length();
        SpannableString sb=new SpannableString(builder.toString());
        sb.setSpan(new RoundBgSpan2(),0,index,Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return sb;
    }

    /**
     * 设置优惠券
     * @param productInfoHolder
     */
    private void setCoupon(ProductInfoHolder productInfoHolder){
        if (productDetail.getData().getCoupons().size() > 0) {
            final List<ProductDetailBean.DataBean.CouponsEntity> couponList = new ArrayList<>();
            productInfoHolder.productCouponLl.setVisibility(View.VISIBLE);
            productInfoHolder.promotionLine.setVisibility(View.VISIBLE);
            productInfoHolder.productCouponContentLl.removeAllViews();
            int size = productDetail.getData().getCoupons().size();
            for (int i = 0; i < size; i++) {
                if (!productDetail.getData().getCoupons().get(i).isClaim()) {
                    continue;
                }
                couponList.add(productDetail.getData().getCoupons().get(i));
                if (productInfoHolder.productCouponContentLl.getChildCount() == 2) {
                    continue;
                }
                TextView text = new TextView(mContext);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
                lp.setMargins(0, 0, ScreenUtil.dip2px(8), 0);
                text.setMaxWidth(Util.dip2px(mContext, 90));
                text.setMaxLines(1);
                text.setGravity(Gravity.CENTER);
                text.setEllipsize(TextUtils.TruncateAt.END);
                text.setLayoutParams(lp);
                text.setPadding(ScreenUtil.dip2px(5), ScreenUtil.dip2px(2), ScreenUtil.dip2px(5), ScreenUtil.dip2px(2));
                text.setTextColor(Color.WHITE);
                text.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);
                if (productDetail.getData().getCoupons().get(i).getType().equals("PASSWORD")||productDetail.getData().getCoupons().get(i).getType().equals("CASH")){
                    text.setText("满"+productDetail.getData().getCoupons().get(i).getNeedAmount()+"减"+productDetail.getData().getCoupons().get(i).getAmount());
                }else if (productDetail.getData().getCoupons().get(i).getType().equals("DISCOUNT")){
                    text.setText("满"+productDetail.getData().getCoupons().get(i).getNeedAmount()+"打"+(float)productDetail.getData().getCoupons().get(i).getAmount()/10+"折");
                } else {
                    text.setText(productDetail.getData().getCoupons().get(i).getCouponName());
                }
                text.setBackgroundResource(R.mipmap.pic_goodsdetrail_smallyhq);
                productInfoHolder.productCouponContentLl.addView(text);
            }
            if (productInfoHolder.productCouponContentLl.getChildCount() == 0) {
                productInfoHolder.productCouponLl.setVisibility(View.GONE);
            } else {
                productInfoHolder.productCouponLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CouponPop couponPop = new CouponPop(mContext, couponList);
                        couponPop.show(v);
                    }
                });
            }
        } else {
            productInfoHolder.promotionLine.setVisibility(View.GONE);
            productInfoHolder.productCouponLl.setVisibility(View.GONE);
        }
    }

    /**
     * 设置品牌
     * @param productInfoHolder
     */
    private void setBrand(ProductInfoHolder productInfoHolder){
        if (productDetail.getData().getBrand() == null) {
            productInfoHolder.productDesign.setVisibility(View.GONE);
        } else {
            productInfoHolder.productDesign.setVisibility(View.VISIBLE);
            UniversalImageLoader.displayImage(mContext, productDetail.getData().getBrand().getHeadPic(), productInfoHolder.productDesignerIv);
            productInfoHolder.productDesignerName.setText(productDetail.getData().getBrand().getName());
            productInfoHolder.productDesignInfo.setText(productDetail.getData().getBrand().getSalesCount()+"件商品|"+productDetail.getData().getBrand().getLikeCount()+"人关注");
            if (productDetail.getData().getRecommendProducts().size() > 0) {
                ProductDesignRecommendAdapter adapter = new ProductDesignRecommendAdapter(mContext, productDetail.getData().getRecommendProducts());
                FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                productInfoHolder.productDesignRecommend.requestDisallowInterceptTouchEvent(true);
                productInfoHolder.productDesignRecommend.setLayoutManager(layoutManager);
                productInfoHolder.productDesignRecommend.setAdapter(adapter);
                changeData(productDetail.getData().getBrand().getId(),adapter);//替换数据
            }
            if (productDetail.getData().getBrand().getMark()==null||"1".equals(productDetail.getData().getBrand().getMark())){
                productInfoHolder.productDesignerLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stat("V3商品详情", "品牌");
                        Intent intent = new Intent(mContext, BrandDetailActivity.class);
                        intent.putExtra("id", productDetail.getData().getBrand().getId());
                        mContext.startActivity(intent);
                    }
                });
            }else {
                productInfoHolder.designValidTag.setVisibility(View.GONE);
            }
        }
    }

    private void changeData(int brandId,ProductDesignRecommendAdapter adapter) {
        SimpleApi api=new SimpleApi();
        api.setInterPath(String.format(Constants.PRODUCT_RECOM_URL,brandId,6));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BrandRecommendBean>() {
            @Override
            public void onResponse(BrandRecommendBean response) {
                if (response!=null&&response.getList()!=null&&response.getList().size()>0){
                    adapter.changeData(response.getList());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 设置服务
     * @param productInfoHolder
     */
    private void setServer(ProductInfoHolder productInfoHolder) {
        if ("KAOLA".equals(productDetail.getData().getProduct().getProductSource())){
            //textView.setText("不支持无理由退换");
            addServerItem(productInfoHolder.productServerContentLl,R.mipmap.icon_goodsdetail_serve02,"不支持无理由退换",true);
        }else if (!productDetail.getData().getProduct().isAfter()){
            addServerItem(productInfoHolder.productServerContentLl,R.mipmap.icon_goodsdetail_serve02,"不支持无理由退换",true);
        }else {
            //textView.setText("七天无理由退换");
            addServerItem(productInfoHolder.productServerContentLl,R.mipmap.icon_goodsdetail_serve01,"七天无理由退换",true);
        }
        if (productDetail.getData().getProduct().getIsSubscribe()){
            //textView.setText("门店试衣");
            addServerItem(productInfoHolder.productServerContentLl,R.mipmap.icon_goodsdetail_serve03,"门店试衣");
        }
        if ("CROSS".equals(productDetail.getData().getProduct().getProductTradeType())){
            if (productDetail.getData().getProduct().getIsTaxation()==1){
                //textView.setText("税费补贴");
                addServerItem(productInfoHolder.productServerContentLl,R.mipmap.icon_goodsdetail_serve07,"税费补贴");
            }else {
                //textView.setText("商品税费");
                addServerItem(productInfoHolder.productServerContentLl,R.mipmap.icon_goodsdetail_serve08,"商品税费");
            }
        }
        //贸易方式 0:直邮，1、保税，2、海淘 ，3、国内贸易 ，4、个人清关
        if ("CROSS".equals(productDetail.getData().getProduct().getProductTradeType())){
            switch (productDetail.getData().getProduct().getImportType()){
                case 0:
                    addServerItem(productInfoHolder.productServerContentLl,R.mipmap.icon_goodsdetail_serve10,"直邮仓发货");
                    break;
                case 1:
                    addServerItem(productInfoHolder.productServerContentLl,R.mipmap.icon_goodsdetail_serve10,"保税仓发货");
                    break;
                case 2:
                    addServerItem(productInfoHolder.productServerContentLl,R.mipmap.icon_goodsdetail_serve10,"海淘");
                    break;
                case 3:
                    addServerItem(productInfoHolder.productServerContentLl,R.mipmap.icon_goodsdetail_serve10,"国内仓发货");
                    break;
                case 4:
                    addServerItem(productInfoHolder.productServerContentLl,R.mipmap.icon_goodsdetail_serve10,"个人清关");
                    break;
            }
        }
        //addServerItem(productInfoHolder.productServerContentLl,5);
        if ("KAOLA".equals(productDetail.getData().getProduct().getProductSource())){
            addServerItem(productInfoHolder.productServerContentLl,R.mipmap.icon_goodsdetail_serve05,"满88包邮");
        }else if ("CAOMEI".equals(productDetail.getData().getProduct().getProductSource())){
            addServerItem(productInfoHolder.productServerContentLl,R.mipmap.icon_goodsdetail_serve05,"满210包邮");
        }else if ("HISTREET".equals(productDetail.getData().getProduct().getProductSource())){
            addServerItem(productInfoHolder.productServerContentLl,R.mipmap.icon_goodsdetail_serve05,"满299包邮");
        }else {
            if (productDetail.getData().getProduct().getIsShipping()==1){
                addServerItem(productInfoHolder.productServerContentLl,R.mipmap.icon_goodsdetail_serve05,"包邮");
            }else {
                addServerItem(productInfoHolder.productServerContentLl,R.mipmap.icon_goodsdetail_serve05,"满299包邮");
            }
        }
        if (!"CROSS".equals(productDetail.getData().getProduct().getProductTradeType())){
            addServerItem(productInfoHolder.productServerContentLl,R.mipmap.icon_goodsdetail_serve09,"正品保证");
        }
        addServerItem(productInfoHolder.productServerContentLl,R.mipmap.icon_goodsdetail_serve06,"慢必赔");
        productInfoHolder.productServerContentLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServicePop servicePop = new ServicePop(mContext, productDetail);
                servicePop.show(v);
            }
        });
    }

    private void addServerItem(LinearLayout contentLl,int resourceId,String tagName){
        addServerItem(contentLl,resourceId,tagName,false);
    }

    private void addServerItem(LinearLayout contentLl,int resourceId,String tagName,boolean isFrist){
        View view=LayoutInflater.from(mContext).inflate(R.layout.layout_product_server_item,new LinearLayout(mContext),false);
        ImageView image=view.findViewById(R.id.image);
        TextView tagNameTv=view.findViewById(R.id.tag_name_tv);
        image.setImageResource(resourceId);
        tagNameTv.setText(tagName);
        int width=ScreenUtil.dip2px(360-32)/4;
        LinearLayout.LayoutParams LL=new LinearLayout.LayoutParams(width,-1);
        if (isFrist){
            int textWidth= (int) tagNameTv.getPaint().measureText(tagName);
            int offer=ScreenUtil.dip2px(16)-(width-textWidth)/2;
            LL.setMargins(offer,0,0,0);
        }
        contentLl.addView(view,LL);
    }

    public void setStandardInfo(String str){
        if (selectStandardTv!=null){
            selectStandardTv.setText(str);
        }
    }

    private void setPrice(TextView textView,double price,double orgPrice,boolean hasTax,String color){
        StringBuilder builder=new StringBuilder();
        builder.append("¥").append(Util.getNumberFormat(price));
        SpannableString spannableString;
        if (orgPrice>0){
            builder.append(" ¥").append(Util.getNumberFormat(orgPrice));
            if (hasTax){
                builder.append("   ").append("预估税费:").append("¥").append(Util.getNumberFormat(productDetail.getData().getProduct().getTaxPrice()*productDetail.getData().getProduct().getMinPrice())).append("x");
            }
            spannableString = new SpannableString(builder.toString());
            int first=Util.getNumberFormat(price).length();
            int second=Util.getNumberFormat(orgPrice).length();

            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.3f);
            spannableString.setSpan(sizeSpan, 1, first+1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(0.6f);
            spannableString.setSpan(sizeSpan1, first+2, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor(color));
            spannableString.setSpan(colorSpan, first+2, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
            spannableString.setSpan(strikethroughSpan, first+2, first+2+second+1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            StyleSpan styleSpan=new StyleSpan(Typeface.BOLD);
            spannableString.setSpan(styleSpan, 0, first+1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            if (hasTax){
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.icon_wenhao);
                drawable.setBounds(ScreenUtil.dip2px(2), -ScreenUtil.dip2px(5), ScreenUtil.dip2px(14), ScreenUtil.dip2px(7));
                CenterAlignImageSpan imageSpan = new CenterAlignImageSpan(drawable);
                spannableString.setSpan(imageSpan,builder.toString().length()-1,builder.toString().length(),ImageSpan.ALIGN_BASELINE);
            }
        }else {
            if (hasTax){
                builder.append("   ").append("预估税费:").append("¥").append(Util.getNumberFormat(productDetail.getData().getProduct().getTaxPrice()*productDetail.getData().getProduct().getMinPrice())).append("x");
            }
            spannableString = new SpannableString(builder.toString());
            int first=Util.getNumberFormat(price).length();
            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.3f);
            spannableString.setSpan(sizeSpan, 1, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            if (hasTax){
                RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(0.6f);
                spannableString.setSpan(sizeSpan1, first+2, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor(color));
                spannableString.setSpan(colorSpan, first+2, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.icon_wenhao);
                drawable.setBounds(ScreenUtil.dip2px(2), -ScreenUtil.dip2px(5), ScreenUtil.dip2px(14), ScreenUtil.dip2px(7));
                CenterAlignImageSpan imageSpan = new CenterAlignImageSpan(drawable);
                spannableString.setSpan(imageSpan,builder.toString().length()-1,builder.toString().length(),ImageSpan.ALIGN_BASELINE);
            }

            StyleSpan styleSpan=new StyleSpan(Typeface.BOLD);
            spannableString.setSpan(styleSpan, 0, first+1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        textView.setText(spannableString);
    }

    private void setTime(long startTime,TextView hourTv, TextView minuteTv, TextView mouseTv, TextView msTv) {
        long offer = startTime - System.currentTimeMillis();
        long hour = offer / (60 * 60 * 1000);
        long minute = (offer / (60 * 1000)) % 60;
        long mouse = (offer / 1000) % 60;
        long ms = (offer % 1000) / 100;
        hourTv.setText(addZero((int) hour));
        minuteTv.setText(addZero((int) minute));
        mouseTv.setText(addZero((int) mouse));
        msTv.setText(String.valueOf(ms));
    }

    private void setPromotionTime(long startTime,TextView dayTv,TextView dayTvTag,TextView hourTv, TextView minuteTv, TextView mouseTv){
        long offer =startTime-System.currentTimeMillis();
        long day = offer /(24*60*60*1000);
        long hour = (offer/(60*60*1000))%24;
        long minute = (offer / (60 * 1000)) % 60;
        long mouse = (offer / 1000) % 60;
        if (day>0){
            dayTv.setText(addZero((int)day));
            dayTv.setVisibility(View.VISIBLE);
            dayTvTag.setVisibility(View.VISIBLE);
        }else {
            dayTv.setVisibility(View.GONE);
            dayTvTag.setVisibility(View.GONE);
        }
        hourTv.setText(addZero((int) hour));
        minuteTv.setText(addZero((int) minute));
        mouseTv.setText(addZero((int) mouse));
    }

    private void setEndTime(long endTime,TextView hourTv, TextView minuteTv, TextView mouseTv){
        long offer = endTime - System.currentTimeMillis();
        long hour = offer / (60 * 60 * 1000);
        long minute = (offer / (60 * 1000)) % 60;
        long mouse = (offer / 1000) % 60;
        hourTv.setText(addZero((int) hour));
        minuteTv.setText(addZero((int) minute));
        mouseTv.setText(addZero((int) mouse));
    }

    private void stat(String event, String label) {
        StatService.onEvent(mContext, event, label);
        TCAgent.onEvent(mContext, event, label);
    }

    private void collect(final ShowPopImageView imageView) {
        imageView.setEnabled(false);
        final boolean is;
        ProductCollectApi api = new ProductCollectApi();
        api.productId = productDetail.getData().getProduct().getId();
        if ("0".equals(productDetail.getData().getProduct().getCollectioned())) {
            api.setInterPath(Constants.COLLECT_PRODUCT_URL);
            is = true;
        } else {
            api.setInterPath(Constants.CANCEL_COLLECT_URL);
            is = false;
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                imageView.setEnabled(true);
                if (is) {
                    imageView.setImageResource(R.mipmap.icon_collect_red);
                    productDetail.getData().getProduct().setCollectioned("1");
                    //开启消息推送行为节点
                    imageView.showMsgPop((Activity) mContext,mContext.getString(R.string.label_pop_focus_product));
                    Util.showToast(mContext, "收藏成功");
                    stat("V3商品详情","收藏");
                } else {
                    imageView.setImageResource(R.mipmap.icon_collect_black);
                    productDetail.getData().getProduct().setCollectioned("0");
                    Util.showToast(mContext, "取消收藏成功");
                }
                ((ProductDetailActivity)mContext).changeCollectState();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imageView.setEnabled(true);
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }


    /**
     * 1 是一口价活动 2 是折扣活动 0 是没有活动
     *
     * @return
     */
    private int checkPromotion() {
        int type = 0;
        if (productDetail.getData().getPromotions() != null && productDetail.getData().getPromotions().size() > 0) {
            int size = productDetail.getData().getPromotions().size();
            for (int i = 0; i < size; i++) {
                ProductDetailBean.DataBean.PromotionsBean promotionsBean = productDetail.getData().getPromotions().get(i);
                if (promotionsBean.getPromotionScope() == 0) {//属于商品活动
                    if ("0".equals(promotionsBean.getPromotionType())) { //折扣活动
                        type = 2;
                    } else if ("4".equals(promotionsBean.getPromotionType())) { //一口价活动
                        type = 1;
                    }else {
                        type =3;
                    }
                }
            }
        }
        if (productDetail.getData().getFlashPromotion()!=null||productDetail.getData().getSoonPromotion()!=null){
            type=1;
        }
        return type;
    }

    private void toSimpleActivity(CharSequence priceStr){
        String url=Constants.SHARE_URL +"/product/"+productDetail.getData().getProduct().getId()+"?parent_id="+parentId;
        Intent intent=new Intent(mContext, SimpleImageActivity.class);
        intent.putExtra("info",productDetail.getData().getProduct().getName());
        intent.putExtra("promotion",getPromotionTypeName());
        intent.putExtra("price",priceStr);
        intent.putExtra("url",url);
        intent.putStringArrayListExtra("picUrl", (ArrayList<String>) productDetail.getData().getProduct().getImgs());
        mContext.startActivity(intent);
    }

    private String getPromotionTypeName(){
        StringBuilder builder=new StringBuilder();
        int size=productDetail.getData().getPromotions().size();
        for (int i=0;i<size;i++){
            builder.append(productDetail.getData().getPromotions().get(i).getPromotionTypeName());
            if (i!=size-1){
                builder.append(",");
            }
        }
        if (productDetail.getData().getProduct().getStatus()==0&&productDetail.getData().getProduct().getProductStatus()==5){
            if (builder.toString().length()>0){
                builder.append(",");
            }
            builder.append("秒杀");
        }
        if (productDetail.getData().getSoonPromotion()!=null&&productDetail.getData().getSoonPromotion().getAdvance()==1){
            if (builder.toString().length()>0){
                builder.append(",").append("秒杀");
            }
        }
        if (productDetail.getData().getFlashPromotion()!=null){
            if (builder.toString().length()>0){
                builder.append(",");
            }
            builder.append("限时购");
        }
        return builder.toString();
    }

    /**
     * 下载图片
     */
    private void loadPics(){
        IMAGE_SIZE=0;
        final Dialog dialog= DialogUtil.createLoadingDialog(mContext);
        dialog.show();
        int size=productDetail.getData().getProduct().getImgs().size();
        final List<File> files=new ArrayList<>();
        for (int i=0;i<size;i++){
            int index=productDetail.getData().getProduct().getImgs().get(i).lastIndexOf(".");
            int type=1;
            if (index+2<productDetail.getData().getProduct().getImgs().get(i).length()){
                String s=productDetail.getData().getProduct().getImgs().get(i).substring(index+1,index+2);
                if (s.equals("j")){
                    type=2;
                }
            }
            final int finalType = type;
            Glide.with(mContext).load(Util.getD2cPicUrl(productDetail.getData().getProduct().getImgs().get(i))).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    if (resource!=null){
                        IMAGE_SIZE++;
                    }
                    String url=null;
                    long partnerId=0;
                    if (Session.getInstance().getUserFromFile(mContext)!=null){
                        partnerId=Session.getInstance().getUserFromFile(mContext).getPartnerId();
                    }
                    if (partnerId>0){
                        url=Constants.SHARE_URL +"/product/"+productDetail.getData().getProduct().getId()+"?parent_id="+partnerId;
                    }else {
                        url = Constants.SHARE_URL + "/product/" + productDetail.getData().getProduct().getId();
                    }
                    Bitmap bitmap=resource.copy(Bitmap.Config.RGB_565,true);
                    files.add(saveFile(bitmap,url, finalType));
                    if (IMAGE_SIZE==productDetail.getData().getProduct().getImgs().size()){
                        dialog.dismiss();
                        try {
                            Intent intent = new Intent();
                            ComponentName comp = new ComponentName("com.tencent.mm",
                                    "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                            intent.setComponent(comp);
                            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                            intent.setType("image/*");

                            ArrayList<Uri> imageUris = new ArrayList<Uri>();
                            for (File f : files) {
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
                                    imageUris.add(Uri.fromFile(f));
                                }else {
                                    Uri uri =Uri.parse(android.provider.MediaStore.Images.Media.insertImage(mContext.getContentResolver(), f.getAbsolutePath(), f.getName(), null));
                                    imageUris.add(uri);
                                }
                            }
                            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                            String webUrl=Constants.SHARE_URL +"/product/"+productDetail.getData().getProduct().getId()+"?parent_id="+parentId;
                            intent.putExtra("Kdescription", productDetail.getData().getProduct().getName()+webUrl);
                            mContext.startActivity(intent);
                        }catch (Exception e){
                            Toast.makeText(mContext,"抱歉您尚未安装微信",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    /**
     * 添加二维码并保存到文件
     * @param bigBitmap
     * @param url
     * @return
     */
    private File saveFile(Bitmap bigBitmap,String url,int type){
        if (bigBitmap.getByteCount()>8485760){
            float scale=1;
            while (bigBitmap.getByteCount()> 8485760) {
                scale -= 0.2;
                bigBitmap= BitmapUtils.getScaleBitmap(bigBitmap, scale, scale);
            }
        }
        Bitmap scanBitmap = null;
        Canvas canvas = new Canvas(bigBitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        if (canvas.getWidth() <= 100) {
            bigBitmap.recycle();
            return null;
        }
        Paint p = new Paint();
        p.setAntiAlias(true);
        int scanWidth = canvas.getWidth() / 4;
        scanBitmap = BitmapUtils.createWhiteQRImage(url, scanWidth, scanWidth,10);
        canvas.drawBitmap(scanBitmap, canvas.getWidth()- ScreenUtil.dip2px(16)-scanBitmap.getWidth(), canvas.getHeight() - scanBitmap.getHeight() - canvas.getWidth() / 100, p);

        File root = mContext.getExternalCacheDir();
        File dir = new File(root, "wx");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file=null;
        if (type==1){
            file=new File(dir,IMAGE_SIZE+".png");
        }else {
            file=new File(dir,IMAGE_SIZE+".jpg");
        }
        if (file.exists()){
            file.delete();
        }
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if (type==1){
                bigBitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
            }else {
                bigBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            }

            FileOutputStream os = new FileOutputStream(file);
            os.write(stream.toByteArray());
            stream.flush();
            stream.close();
            os.flush();
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (bigBitmap != null) {
                bigBitmap.recycle();
                bigBitmap=null;
            }
            if (scanBitmap != null) {
                scanBitmap.recycle();
                scanBitmap=null;
            }
            canvas = null;
        }
        return file;
    }

    public void setOpenListener(final TextView iv, final TextView tv) {
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setVisibility(View.GONE);
                final int startValue = tv.getHeight();
                final int deltaValue = tv.getLineHeight() * lineCount - startValue;
                Animation animation = new Animation() {
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        tv.setHeight((int) (startValue + deltaValue * interpolatedTime));
                        if (interpolatedTime == 1) {
                            tv.setMaxLines(lineCount);
                        }
                    }
                };
                animation.setDuration(350);
                animation.setFillAfter(true);
                tv.startAnimation(animation);
            }
        });
    }

    private String addZero(int num) {
        if (num < 10) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }

    public class MyImageSpan extends ImageSpan {
        public MyImageSpan(Context arg0, int arg1) {
            super(arg0, arg1);
        }

        public int getSize(Paint paint, CharSequence text, int start, int end,
                           Paint.FontMetricsInt fm) {
            Drawable d = getDrawable();
            Rect rect = d.getBounds();
            if (fm != null) {
                Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
                int fontHeight = fmPaint.bottom - fmPaint.top;
                int drHeight = rect.bottom - rect.top;

                int top = drHeight / 2 - fontHeight / 4;
                int bottom = drHeight / 2 + fontHeight / 4;

                fm.ascent = -bottom;
                fm.top = -bottom;
                fm.bottom = top;
                fm.descent = top;
            }
            return rect.right;
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end,
                         float x, int top, int y, int bottom, Paint paint) {
            Drawable b = getDrawable();
            canvas.save();
            int transY = 0;
            //transY = ((bottom-top) - b.getBounds().bottom)/2+top;
            transY = top;
            canvas.translate(x, transY);
            b.draw(canvas);
            canvas.restore();
        }
    }

    public void release() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler=null;
        }
    }

    public void changeCollectState(){
        if ("1".equals(productDetail.getData().getProduct().getCollectioned())) {
            collectIv.setImageResource(R.mipmap.icon_collected);
        } else {
            collectIv.setImageResource(R.mipmap.icon_collect);
        }
    }

    private void jumpToWX() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");

            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Util.showToast(mContext, "抱歉您尚未安装微信");
        }
    }
}
