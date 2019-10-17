package com.d2cmall.buyer.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.DCoinProductDetaiActivity;
import com.d2cmall.buyer.activity.DCoinRecordConversionActivity;
import com.d2cmall.buyer.activity.MyCouponsActivity;
import com.d2cmall.buyer.activity.OrderDetailActivity;
import com.d2cmall.buyer.activity.RedPacketActivity;
import com.d2cmall.buyer.bean.DCionExchangeHistoryBean;
import com.d2cmall.buyer.holder.DCoinRecordConversionHolder;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/6.
 * Description : DCoinShopAdapter
 */

public class DCoinRecordConversionAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private ArrayList<DCionExchangeHistoryBean.DataBean.RecordsBean.ListBean> listBeans;
    private int itemWidth;
    public DCoinRecordConversionAdapter(Context context, ArrayList<DCionExchangeHistoryBean.DataBean.RecordsBean.ListBean> listBeans, int itemWidth) {
        super();
        this.mContext = context;
        this.listBeans = listBeans;
        this.itemWidth=itemWidth;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_dcoin_record_conversion, parent, false);
                return new DCoinRecordConversionHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        DCoinRecordConversionHolder dCoinShopProductHolder = (DCoinRecordConversionHolder) holder;
        UniversalImageLoader.displayImage(mContext, listBeans.get(position).getPic(),dCoinShopProductHolder.image,R.mipmap.ic_logo_empty5);
        dCoinShopProductHolder.tvTitle.setText(listBeans.get(position).getProductName());
        if("RED".equals(listBeans.get(position).getProductType())){
            dCoinShopProductHolder.tvPrice.setText(mContext.getString(R.string.label_red_price,listBeans.get(position).getPoint(),Util.getNumberFormat(listBeans.get(position).getAmount()*listBeans.get(position).getProductQuantity())));
        }else{
            dCoinShopProductHolder.tvPrice.setText(mContext.getString(R.string.label_dcion_price,listBeans.get(position).getPoint()));
        }
        dCoinShopProductHolder.tvDate.setText(DateUtil.toString(listBeans.get(position).getTransactionTime()));
        if(Util.isEmpty(listBeans.get(position).getCode()) || "RED".equals(listBeans.get(position).getProductType())){
            dCoinShopProductHolder.tvConversionCode.setVisibility(View.GONE);
        }else{
            dCoinShopProductHolder.tvConversionCode.setVisibility(View.VISIBLE);
            dCoinShopProductHolder.tvConversionCode.setText("兑换码:" + listBeans.get(position).getCode());
        }


        dCoinShopProductHolder.tvConversionCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setPrimaryClip(ClipData.newPlainText("code", listBeans.get(position).getCode()));
                Util.showToast(mContext, "复制成功");
            }
        });



            dCoinShopProductHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if("COUPON".equals(listBeans.get(position).getProductType())) {     //优惠券跳转
                        mContext.startActivity(new Intent(mContext, MyCouponsActivity.class));
                    }else if("RED".equals(listBeans.get(position).getProductType())){       //红包
                        mContext.startActivity(new Intent(mContext, RedPacketActivity.class));
                    }
                }
            });

        dCoinShopProductHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, DCoinProductDetaiActivity.class).putExtra("id", listBeans.get(position).getProductId()));
            }
        });

    }



    @Override
    public int getItemCount() {
        return listBeans ==null?0: listBeans.size();
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return linearLayoutHelper;
    }
}
