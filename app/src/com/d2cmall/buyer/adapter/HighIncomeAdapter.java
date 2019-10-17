package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.OpenOrderListBean;
import com.d2cmall.buyer.bean.PartnerSaleListBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.HighIncomeHolder;
import com.d2cmall.buyer.holder.PartnerBillHolder;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/31 15:52
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class HighIncomeAdapter extends DelegateAdapter.Adapter {

    private Context mContext;
    private List<OpenOrderListBean.DataBean.ProductsBean.ListBean> datas;
    private UserBean.DataEntity.MemberEntity user;
    private double globalRadio;

    public HighIncomeAdapter(Context context, List<OpenOrderListBean.DataBean.ProductsBean.ListBean> datas) {
        this.mContext = context;
        this.datas = datas;
        user = Session.getInstance().getUserFromFile(context);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper=new LinearLayoutHelper();
                linearLayoutHelper.setPaddingTop(ScreenUtil.dip2px(16));
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_open_order_item, new LinearLayout(mContext), false);
                return new HighIncomeHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        HighIncomeHolder highIncomeHolder = (HighIncomeHolder) holder;
        final OpenOrderListBean.DataBean.ProductsBean.ListBean listBean = datas.get(position);
        UniversalImageLoader.displayImage(mContext, Util.getD2cProductPicUrl(listBean.getImg(),ScreenUtil.dip2px(72),ScreenUtil.dip2px(108)), highIncomeHolder.ivProduct,R.mipmap.ic_logo_empty5);
        highIncomeHolder.tvProductName.setText(listBean.getName());
        highIncomeHolder.tvPrice.setText(" ¥"+Util.getNumberFormat(listBean.getMinPrice()));
        if(listBean.getPromotionId()>0) {
            highIncomeHolder.tvOldPrice.setVisibility(View.VISIBLE);
            highIncomeHolder.tvOldPrice.setText("¥"+Util.getNumberFormat(listBean.getSalePrice()));
            highIncomeHolder.tvOldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        }else{
            if(listBean.getMinPrice()<listBean.getOriginalPrice()) {
                highIncomeHolder.tvOldPrice.setVisibility(View.VISIBLE);
                highIncomeHolder.tvOldPrice.setText("¥"+Util.getNumberFormat(listBean.getOriginalPrice()));
                highIncomeHolder.tvOldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            }else{
                highIncomeHolder.tvOldPrice.setVisibility(View.GONE);
            }
        }
//        msg_partner_rebate_rate
//        highIncomeHolder.tvDate.setText("可获实付"+listBean.getSecondRatio()*100+"%返利");
        if(listBean.getGrossRatio()*listBean.getSecondRatio()*globalRadio==0){      // 商品总返利比*直接返利比*全场返利比>0 (注释与字段名依次对应)
            highIncomeHolder.tvDate.setText("分享有机会获得高额返利");
        }else{
            double rebeatRatio= listBean.getGrossRatio() * listBean.getSecondRatio() * globalRadio * 100;
            String rebeatRatio1=Util.getNumberFormat(rebeatRatio);
            rebeatRatio=Double.valueOf(rebeatRatio1);
            rebeatRatio1=String.valueOf((int)(rebeatRatio+0.5));
            highIncomeHolder.tvDate.setText(mContext.getString(R.string.msg_partner_rebate_rate,rebeatRatio1));
        }

        highIncomeHolder.ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("id",(long) listBean.getId());
                mContext.startActivity(intent);
            }
        });

}



    public void setGlobalRadio(double globalRadio) {//设置全局的返利比
        this.globalRadio = globalRadio;
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


}
