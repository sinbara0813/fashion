package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.CouponRangeBean;
import com.d2cmall.buyer.holder.CouponRelationHolder;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/6.
 * Description : DCoinShopAdapter
 */

public class CouponRelationAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private CouponRangeBean.DataBean.CouponBean couponBean;
    private long endTime;
    private Handler mHandler;
    private long startTime;
    public CouponRelationAdapter(Context context, CouponRangeBean.DataBean.CouponBean couponBean) {
        super();
        this.mContext = context;
        this.couponBean = couponBean;
        if(couponBean.getExpirestamp()!=null){
            endTime = couponBean.getExpirestamp().getTime();
        }
        if(couponBean.getEnablestamp()!=null){
            startTime = couponBean.getEnablestamp().getTime();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_coupon_relation, parent, false);
        return new CouponRelationHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,  int position) {
        final CouponRelationHolder couponRelationHolder = (CouponRelationHolder) holder;
        if(System.currentTimeMillis()<endTime){
            if("DISCOUNT".equals(couponBean.getType())){
                couponRelationHolder.tvCouponName.setText(mContext.getString(R.string.label_discount_coupon_desc,couponBean.getNeedAmount(),couponBean.getAmount()/10));
            }else{
                couponRelationHolder.tvCouponName.setText(mContext.getString(R.string.label_money_coupon_desc,couponBean.getNeedAmount(),couponBean.getAmount()));
            }

            if(mHandler!=null){
                mHandler.removeCallbacksAndMessages(null);
            }
            //设置初始值
            setTime(couponRelationHolder.timeTag,couponRelationHolder.timeDay,couponRelationHolder.timeHour,couponRelationHolder.timeMinute,couponRelationHolder.timeMouse,couponRelationHolder.timeMs,couponRelationHolder.llDownTime,couponRelationHolder.tvCouponName);
            mHandler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    //更改时间
                    setTime(couponRelationHolder.timeTag,couponRelationHolder.timeDay,couponRelationHolder.timeHour,couponRelationHolder.timeMinute,couponRelationHolder.timeMouse,couponRelationHolder.timeMs,couponRelationHolder.llDownTime, couponRelationHolder.tvCouponName);
                    mHandler.sendEmptyMessageDelayed(1,100);
                }
            };
            mHandler.sendEmptyMessageDelayed(1,100);
        }else{
            couponRelationHolder.tvCouponName.setText("优惠券已过期");
        }

    }

    private void setTime(TextView timeTag, TextView dayTv, TextView hourTv, TextView minuteTv, TextView mouseTv, TextView msTv, LinearLayout llTime,  TextView couponName){
        if(System.currentTimeMillis()<startTime){
            timeTag.setText("距开始");
        }else if(System.currentTimeMillis()>startTime && System.currentTimeMillis()<endTime){
            timeTag.setText("距结束");
        }
        long offer=startTime-System.currentTimeMillis();
        if(System.currentTimeMillis()<startTime){
            offer=startTime-System.currentTimeMillis();
            timeTag.setText("距开始");
        }else if(System.currentTimeMillis()>startTime && System.currentTimeMillis()<endTime){
            timeTag.setText("距结束");
            offer=endTime-System.currentTimeMillis();
        }else if( System.currentTimeMillis()>endTime){
            llTime.setVisibility(View.GONE);
            couponName.setText("优惠券已过期");
            couponName.setVisibility(View.VISIBLE);
        }

        long day=offer/(24*60*60*1000);
        long hour=offer/(60*60*1000)%24;
        long minute=(offer/(60*1000))%60;
        long mouse=(offer/1000)%60;
        long ms=(offer%1000)/100;
        dayTv.setText(addZero((int)day));
        hourTv.setText(addZero((int)hour));
        minuteTv.setText(addZero((int)minute));
        mouseTv.setText(addZero((int)mouse));
        msTv.setText(String.valueOf(ms));
    }

    private String addZero(int num) {
        if (num < 10) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }

    @Override
    public int getItemCount() {
     return 1;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return linearLayoutHelper;
    }
}
