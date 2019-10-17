package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.NormalCustomerLikeBean;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.NormalCustomerBean;
import com.d2cmall.buyer.holder.NormalCustomerProductItemHolder;
import com.d2cmall.buyer.holder.NormalCustormerHeaderHolder;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Created by Administrator on 2018/5/19.
 * Description : NormalCustomerCenterAdapter
 */

public class NormalCustomerCenterAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private  List<NormalCustomerLikeBean.ListBean> mProductList;
    private NormalCustomerLikeBean.ListBean listBean;

    public NormalCustomerCenterAdapter(Context context, List<NormalCustomerLikeBean.ListBean> productList) {
        this.mContext=context;
        mProductList=productList;
    }
    private NormalCustomerBean mNormalCustomerBean;
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setMarginBottom(ScreenUtil.dip2px(16));
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        RecyclerView.ViewHolder holder=null;
        if(viewType==0){
            view=LayoutInflater.from(mContext).inflate(R.layout.layout_custormer_header,parent,false);
            holder=new NormalCustormerHeaderHolder(view);
        }else{
            view=LayoutInflater.from(mContext).inflate(R.layout.layout_normal_custormer_center_product_item,parent,false);
            holder=new NormalCustomerProductItemHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(position==0){
            NormalCustormerHeaderHolder normalCustormerHeaderHolder = (NormalCustormerHeaderHolder) holder;
            if(mNormalCustomerBean!=null){
                UniversalImageLoader.displayImage(mContext,mNormalCustomerBean.getList().get(0).getHeadPic(),normalCustormerHeaderHolder.ivBuyerHead,R.mipmap.ic_default_avatar);
                if(mNormalCustomerBean.getList().get(0).getConsumeDate()!=null){
                    normalCustormerHeaderHolder.tvEnterDate.setText(mContext.getString(R.string.buyer_consume_date, DateUtil.getFriendlyTime9(DateUtil.ConverToString(mNormalCustomerBean.getList().get(0).getConsumeDate()))));
                }
                if(!Util.isEmpty(mNormalCustomerBean.getList().get(0).getNickname())){
                    normalCustormerHeaderHolder.tvBuyerName.setText(mNormalCustomerBean.getList().get(0).getNickname());
                }else{
                    normalCustormerHeaderHolder.tvBuyerName.setText("匿名_"+ mNormalCustomerBean.getList().get(0).getId());
                }
                normalCustormerHeaderHolder.tvBuyerCode.setText(mContext.getString(R.string.buyer_code, mNormalCustomerBean.getList().get(0).getId()));
                String str = "¥"+ Util.getNumberFormat(mNormalCustomerBean.getList().get(0).getPayAmount());
                int length = str.length();
                SpannableString textSpan = new SpannableString(str);
                textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)),0,1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(20)),1,length,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                normalCustormerHeaderHolder.tvIncomeMoney.setText(textSpan);
            }
        }else{
            NormalCustomerProductItemHolder normalCustomerProductItemHolder = (NormalCustomerProductItemHolder) holder;
            listBean = mProductList.get(position -1);
            UniversalImageLoader.displayImage(mContext,listBean.getImg(),normalCustomerProductItemHolder.ivProduct,R.mipmap.ic_logo_empty5);
            normalCustomerProductItemHolder.tvProductName.setText(listBean.getName());
            String str = "¥"+ Util.getNumberFormat(listBean.getPrice());
            int length = str.length();
            SpannableString textSpan = new SpannableString(str);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)),0,1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(18)),1,length,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            normalCustomerProductItemHolder.tvPrice.setText(textSpan);
            if(listBean.getPromotionId()>0) {     //价格显示
                if(listBean.getSalePrice()>listBean.getPrice()){
                    normalCustomerProductItemHolder.tvOldPrice.setVisibility(View.VISIBLE);
                    normalCustomerProductItemHolder.tvOldPrice.setText("¥"+Util.getNumberFormat(listBean.getSalePrice()));
                    normalCustomerProductItemHolder.tvOldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
                }else{
                    normalCustomerProductItemHolder.tvOldPrice.setVisibility(View.GONE);
                }

            }else{
                if(listBean.getPrice()<listBean.getOriginalPrice()) {
                    normalCustomerProductItemHolder.tvOldPrice.setVisibility(View.VISIBLE);
                    normalCustomerProductItemHolder.tvOldPrice.setText("¥"+Util.getNumberFormat(listBean.getOriginalPrice()));
                    normalCustomerProductItemHolder.tvOldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
                }else{
                    normalCustomerProductItemHolder.tvOldPrice.setVisibility(View.GONE);
            }
            }
            normalCustomerProductItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, ProductDetailActivity.class).putExtra("id", mProductList.get(position-1).getId()));
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return mProductList==null?1:mProductList.size()+1;
    }

    public void setHeadBean(NormalCustomerBean normalCustomerBean) {
        this.mNormalCustomerBean=normalCustomerBean;
        notifyDataSetChanged();
    }
}
