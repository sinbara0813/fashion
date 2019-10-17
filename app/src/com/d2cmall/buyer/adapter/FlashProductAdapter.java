package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.FlashProductActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.FlashProductListBean;
import com.d2cmall.buyer.holder.FlashBrandItemHolder;
import com.d2cmall.buyer.holder.FlashProductHeadHolder;
import com.d2cmall.buyer.holder.FlashProductHolder;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.tendcloud.tenddata.TCAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/12/12 14:07
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FlashProductAdapter extends DelegateAdapter.Adapter {

    private Context context;
    private List<FlashProductListBean.DataBean.ProductsBean.ListBean> list;
    private FlashProductListBean.DataBean.FlashPromotionBean flashPromotionBean;
    private List<FlashProductListBean.DataBean.BrandFlashPromotionsBean> brandFlashPromotions;
    private int type;
    private CallBack callBack;
    private Handler mHandler;
    private long endTime;
    private long startTime;

    public FlashProductAdapter(Context context,List<FlashProductListBean.DataBean.ProductsBean.ListBean> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper=new LinearLayoutHelper();
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType==5){
            view=LayoutInflater.from(context).inflate(R.layout.layout_flash_product_head,parent,false);
            return new FlashProductHeadHolder(view);
        }else if (viewType==3){
            view= LayoutInflater.from(context).inflate(R.layout.layout_flash_product,parent,false);
            return new FlashProductHolder(view);
        }else {
            view= LayoutInflater.from(context).inflate(R.layout.layout_flash_brand_item,parent,false);
            return new FlashBrandItemHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position)==3){
            int offer=flashPromotionBean!=null?1:0;
            if (brandFlashPromotions!=null){
                offer+=Math.min(brandFlashPromotions.size(),position/5);
            }
            final int index=position-offer;
            FlashProductHolder flashProductViewHolder= (FlashProductHolder) holder;
            UniversalImageLoader.displayImage(context,list.get(index).getImg(),flashProductViewHolder.productIv,R.mipmap.ic_logo_empty5,R.mipmap.ic_logo_empty5);
            flashProductViewHolder.productInfoTv.setText(list.get(index).getName());
            if (type==0){
                flashProductViewHolder.productNoticeTv.setText("提醒我");
            }else {
                flashProductViewHolder.productNoticeTv.setText("马上抢");
            }
            list.get(index).setPv(list.get(index).getPv()+1);
            if (list.get(index).getStore()<=0){
                flashProductViewHolder.saleOutTag.setVisibility(View.VISIBLE);
                flashProductViewHolder.productNoticeTv.setText("去看看");
            }else {
                flashProductViewHolder.saleOutTag.setVisibility(View.GONE);
            }
            StringBuilder builder=new StringBuilder();
            double price;
            if (list.get(index).getPromotionId()>0){
                price=list.get(index).getSellPrice();
            }else {
                price=list.get(index).getSellPrice();
            }

            if (price>list.get(index).getFlashPrice()){
                builder.append("原价 ¥").append(Util.getNumberFormat(price))
                        .append("  限时价 ¥").append(Util.getNumberFormat(list.get(index).getFlashPrice()));
                int first=builder.toString().indexOf("¥");
                int second=builder.toString().indexOf("限");
                int third=builder.toString().lastIndexOf("¥");
                SpannableString spannableString = new SpannableString(builder.toString());

                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#DE000000"));
                spannableString.setSpan(colorSpan, second, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.5f);
                spannableString.setSpan(sizeSpan, third+1, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                StyleSpan styleSpan  = new StyleSpan(Typeface.BOLD);
                spannableString.setSpan(styleSpan, third+1, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
                spannableString.setSpan(strikethroughSpan, first+1, first+1+Util.getNumberFormat(list.get(index).getPrice()).length()+1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                flashProductViewHolder.productPrice.setText(spannableString);
            }else {
                builder.append("限时价 ¥").append(Util.getNumberFormat(list.get(index).getFlashPrice()));
                int second=builder.toString().indexOf("限");
                int third=builder.toString().lastIndexOf("¥");
                SpannableString spannableString = new SpannableString(builder.toString());

                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#DE000000"));
                spannableString.setSpan(colorSpan, second, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.5f);
                spannableString.setSpan(sizeSpan, third+1, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                StyleSpan styleSpan  = new StyleSpan(Typeface.BOLD);
                spannableString.setSpan(styleSpan, third+1, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                flashProductViewHolder.productPrice.setText(spannableString);
            }

            flashProductViewHolder.productNoticeTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callBack!=null){
                        if (list.get(index).getStore()<=0){
                            toDetailActivity(list.get(index).getId());
                        }else {
                            callBack.notice(type,list.get(index).getId());
                        }
                    }
                }
            });
            flashProductViewHolder.productIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toDetailActivity(list.get(index).getId());
                }
            });
            flashProductViewHolder.productInfoTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toDetailActivity(list.get(index).getId());
                }
            });
        }else if (getItemViewType(position)==5){
            flashPromotionBean.setPv(flashPromotionBean.getPv()+1);
            final FlashProductHeadHolder headHolder= (FlashProductHeadHolder) holder;
            UniversalImageLoader.displayImage(context,flashPromotionBean.getBrandPic(),headHolder.headIv);
            startTime=flashPromotionBean.getStartTimeStamp();
            endTime=flashPromotionBean.getEndTimeStamp();
            if (startTime-System.currentTimeMillis()>0){//未开始
                endTime=startTime;
                headHolder.tag.setText("距开始");
            }
            setPromotionTime(endTime,headHolder.flashHourTv,headHolder.flashMinuteTv,headHolder.flashMouseTv);
            if (mHandler != null) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler = null;
            }
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    //更改时间
                    setPromotionTime(endTime,headHolder.flashHourTv,headHolder.flashMinuteTv,headHolder.flashMouseTv);
                    mHandler.sendEmptyMessageDelayed(1, 1000);
                }
            };
            mHandler.sendEmptyMessageDelayed(1, 1000);
        }else if (getItemViewType(position)==6){
            FlashBrandItemHolder itemHolder= (FlashBrandItemHolder) holder;
            final FlashProductListBean.DataBean.BrandFlashPromotionsBean brandBean=brandFlashPromotions.get((position-4)/5);
            brandBean.setPv(brandBean.getPv()+1);
            UniversalImageLoader.displayBlurImage(context,brandBean.getBrandPic(),itemHolder.bg,90);
            itemHolder.name.setText(brandBean.getName());
            itemHolder.rl1.setVisibility(View.GONE);
            itemHolder.rl2.setVisibility(View.GONE);
            itemHolder.rl3.setVisibility(View.GONE);
            if (brandBean.getProducts().size()>0){
                itemHolder.rl1.setVisibility(View.VISIBLE);
                UniversalImageLoader.displayImage(context,brandBean.getProducts().get(0).getImg(),itemHolder.image1);
                if (brandBean.getProducts().get(0).getSalePrice()>brandBean.getProducts().get(0).getFlashPrice()){
                    setPrice(brandBean.getProducts().get(0).getFlashPrice(),brandBean.getProducts().get(0).getSalePrice(),itemHolder.price1);
                }else {
                    itemHolder.price1.setText("¥"+Util.getNumberFormat(brandBean.getProducts().get(0).getFlashPrice()));
                }
                if (brandBean.getProducts().size()>1){
                    itemHolder.rl2.setVisibility(View.VISIBLE);
                    UniversalImageLoader.displayImage(context,brandBean.getProducts().get(1).getImg(),itemHolder.image2);
                    if (brandBean.getProducts().get(1).getSalePrice()>brandBean.getProducts().get(1).getFlashPrice()){
                        setPrice(brandBean.getProducts().get(1).getFlashPrice(),brandBean.getProducts().get(1).getSalePrice(),itemHolder.price2);
                    }else {
                        itemHolder.price2.setText("¥"+Util.getNumberFormat(brandBean.getProducts().get(1).getFlashPrice()));
                    }
                    if (brandBean.getProducts().size()>2){
                        itemHolder.rl3.setVisibility(View.VISIBLE);
                        UniversalImageLoader.displayImage(context,brandBean.getProducts().get(2).getImg(),itemHolder.image3);
                        if (brandBean.getProducts().get(2).getSalePrice()>brandBean.getProducts().get(2).getFlashPrice()){
                            setPrice(brandBean.getProducts().get(2).getFlashPrice(),brandBean.getProducts().get(2).getSalePrice(),itemHolder.price3);
                        }else {
                            itemHolder.price3.setText("¥"+Util.getNumberFormat(brandBean.getProducts().get(2).getFlashPrice()));
                        }
                    }
                }
            }
            itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, FlashProductActivity.class);
                    intent.putExtra("promotionId",brandBean.getId());
                    context.startActivity(intent);
                }
            });
        }
    }

    private void setPrice(double flashPrice,double price,TextView priceTv){
        StringBuilder builder=new StringBuilder();
        builder.append("¥").append(Util.getNumberFormat(flashPrice)).append(" ")
                .append("¥").append(Util.getNumberFormat(price));
        int index=builder.toString().lastIndexOf("¥");
        SpannableString spannableString = new SpannableString(builder.toString());

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#61000000"));
        spannableString.setSpan(colorSpan, index, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan,index,builder.toString().length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        priceTv.setText(spannableString);
    }

    private void setPromotionTime(long startTime, TextView hourTv, TextView minuteTv, TextView mouseTv){
        long offer = startTime - System.currentTimeMillis();
        long hour = offer / (60 * 60 * 1000);
        long minute = (offer / (60 * 1000)) % 60;
        long mouse = (offer / 1000) % 60;
        hourTv.setText(addZero((int) hour));
        minuteTv.setText(addZero((int) minute));
        mouseTv.setText(addZero((int) mouse));
    }

    private String addZero(int num) {
        if (num < 10) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }

    public void recycle(){
        if (mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    private void toDetailActivity(long id){
        Intent intent=new Intent(context,ProductDetailActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        int count=0;
        if (flashPromotionBean!=null){
            count++;
        }
        int size=list.size();
        int maxNum=size/4;
        count+=list.size();
        if (brandFlashPromotions!=null){
            count+=Math.min(Math.min(maxNum,brandFlashPromotions.size()),3);
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (flashPromotionBean!=null&&position==0){
            return 5;
        }else {
            if (brandFlashPromotions!=null){
                int offer=flashPromotionBean!=null?0:1;
                if ((position+offer)%5==0&&brandFlashPromotions.size()>=(position+offer)/5){
                    return 6;
                }else {
                    return 3;
                }
            }else {
                return 3;
            }
        }
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setFlashPromotionBean(FlashProductListBean.DataBean.FlashPromotionBean flashPromotionBean) {
        this.flashPromotionBean = flashPromotionBean;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack{
        void notice(int type,long id);
    }

    public void setBrandFlashPromotions(List<FlashProductListBean.DataBean.BrandFlashPromotionsBean> brandFlashPromotions) {
        this.brandFlashPromotions = brandFlashPromotions;
    }
}
