package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.FlashProductListBean;
import com.d2cmall.buyer.holder.AloneHolder;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * 作者:Created by sinbara on 2019/9/12.
 * 邮箱:hrb940258169@163.com
 */
public class AloneAdapter extends DelegateAdapter.Adapter<AloneHolder>  {

    private Context context;
    private List<FlashProductListBean.DataBean.ProductsBean.ListBean> data;
    private boolean isStart;
    private FlashBuyAdapter.FlashProductRemindCallBack callBack;//提醒我回调
    private long startTime;
    private long endTime;
    private Handler handler;
    private TextView timeTv;

    public void setStart(boolean start) {
        isStart = start;
    }

    public void setCallBack(FlashBuyAdapter.FlashProductRemindCallBack callBack) {
        this.callBack = callBack;
    }

    public AloneAdapter(Context context, List<FlashProductListBean.DataBean.ProductsBean.ListBean> data,long start,long end){
        this.context=context;
        this.data=data;
        startTime=start;
        endTime=end;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public AloneHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_alone_item,parent,false);
        return new AloneHolder(view);
    }

    @Override
    public void onBindViewHolder(AloneHolder holder, int position) {
        if (position==0){
            setNormalProduct(holder,data.get(position),true);
        }else {
            setNormalProduct(holder,data.get(position),false);
        }
    }

    private void setNormalProduct(AloneHolder flashNewProductHolder, final FlashProductListBean.DataBean.ProductsBean.ListBean data,boolean is) {
        if (is){
            flashNewProductHolder.tv1.setVisibility(View.VISIBLE);
            //设置结束时间
            setTime(flashNewProductHolder.timeTv);
            timeTv=flashNewProductHolder.timeTv;
            startTip();
        }else {
            flashNewProductHolder.tv1.setVisibility(View.GONE);
            flashNewProductHolder.timeTv.setVisibility(View.GONE);
        }
        UniversalImageLoader.displayImage(context, data.getImg(), flashNewProductHolder.iv, R.mipmap.ic_logo_empty5);
        flashNewProductHolder.tvName.setText(data.getName());
        if (!Util.isEmpty(data.getOrderPromotionTypeName())) {
            flashNewProductHolder.promotionTv.setText(data.getOrderPromotionTypeName());
            flashNewProductHolder.promotionTv.setVisibility(View.VISIBLE);
        } else {
            flashNewProductHolder.promotionTv.setVisibility(View.GONE);
        }

        Integer flashSellStock = data.getFlashSellStock();
        Integer flashStock = data.getFlashStock();
        int left = flashStock - flashSellStock;
        if (left > 0) {
            int progress;
            if (flashStock == 0) {
                progress = 0;
            } else {
                flashNewProductHolder.numTv.setText("仅剩" + left + "件");
            }
            flashNewProductHolder.buyTv.setBackgroundResource(R.drawable.sp_alone_bg);
            flashNewProductHolder.buyTv.setText("马上抢");
        } else {
            flashNewProductHolder.buyTv.setText("");
            flashNewProductHolder.buyTv.setBackgroundResource(R.mipmap.icon_shouwan);
        }
        if (isStart) {
            flashNewProductHolder.buyTv.setText("");
            flashNewProductHolder.buyTv.setBackgroundResource(R.mipmap.icon_tixingwo);
        }
        flashNewProductHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDetailActivity(data.getId());
            }
        });
        flashNewProductHolder.buyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStart) {
                    callBack.notice(data.getId());
                } else {
                    toDetailActivity(data.getId());
                }
            }
        });
        StringBuilder builder = new StringBuilder("¥" + data.getFlashPrice());
        SpannableString spannableString = new SpannableString(builder.toString());
        RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(0.5f);
        spannableString.setSpan(sizeSpan1, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        flashNewProductHolder.promotionPriceTv.setText(spannableString);
        if (data.getFlashPrice() < data.getSalePrice()) {
            flashNewProductHolder.priceTv.setVisibility(View.VISIBLE);
            flashNewProductHolder.priceTv.setText("¥" + Util.getNumberFormat(data.getSalePrice()));
            flashNewProductHolder.priceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            flashNewProductHolder.priceTv.setVisibility(View.GONE);
        }
    }

    private void setTime(TextView textView){
        if (endTime < System.currentTimeMillis()) {//到时间了,已经结束了
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }
        StringBuilder builder=new StringBuilder();
        if (startTime - System.currentTimeMillis() > 0) {//未开始
            endTime = startTime;
            builder.append("距开始").append(" ");
        } else {
            builder.append("距结束").append(" ");
        }
        long offer = endTime - System.currentTimeMillis();
        long hour = offer / (60 * 60 * 1000);
        long minute = (offer / (60 * 1000)) % 60;
        long mouse = (offer / 1000) % 60;
        builder.append(addZero((int) hour)).append(":").append(addZero((int) minute)).append(":").append(addZero((int) mouse));
        textView.setText(builder.toString());
    }

    private void startTip(){
        if (handler==null){
            handler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (timeTv!=null){
                        setTime(timeTv);
                        handler.sendEmptyMessageDelayed(1,1000);
                    }
                }
            };
        }
        handler.sendEmptyMessageDelayed(1,1000);
    }

    private String addZero(int num) {
        if (num < 10) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }

    public void stopHandler(){
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler=null;
        }
    }

    private void toDetailActivity(long id) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
