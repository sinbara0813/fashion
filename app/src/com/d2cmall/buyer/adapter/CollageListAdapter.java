package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.CollageDetailActivity;
import com.d2cmall.buyer.bean.CollageListBean;
import com.d2cmall.buyer.holder.CollageListHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2018/12/28.
 * Description : CollageListAdapter
 */

public class CollageListAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private SparseArray<CountDownTimer> countDownMap;
    private List<CollageListBean.DataBean.GroupListBean.ListBean> groupList;

    public CollageListAdapter(Context mContext, List<CollageListBean.DataBean.GroupListBean.ListBean> groupList) {
        this.mContext = mContext;
        this.groupList = groupList;
        countDownMap=new SparseArray<>();
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setPaddingBottom(ScreenUtil.dip2px(10));
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_collage_list_item, parent, false);
        return new CollageListHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CollageListHolder collageListHolder = (CollageListHolder) holder;
        UniversalImageLoader.displayRoundImage(mContext,groupList.get(position).getHeadPic(),collageListHolder.avatar,R.mipmap.ic_default_avatar);
        collageListHolder.name.setText(groupList.get(position).getInitiatorName());
        int offerMember=groupList.get(position).getMemberCount()-groupList.get(position).getCurrentMemberCount();
        StringBuilder builder=new StringBuilder();
        builder.append("还差").append(offerMember).append("人成团");
        SpannableString sb=new SpannableString(builder.toString());
        ForegroundColorSpan colorSpan=new ForegroundColorSpan(Color.parseColor("#fff23365"));
        sb.setSpan(colorSpan,2,4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        collageListHolder.num.setText(sb);
        collageListHolder.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, CollageDetailActivity.class);
                intent.putExtra("collageId",groupList.get(position).getId());
                mContext.startActivity(intent);
            }
        });
        //倒计时
        if (collageListHolder.countDownTimer!= null) {
            collageListHolder.countDownTimer.cancel();
        }
        long offerTime = groupList.get(position).getEndDate().getTime() - System.currentTimeMillis();
        if (offerTime > 0) {
            collageListHolder.countDownTimer = new CountDownTimer(offerTime, 1000) {
                public void onTick(long millisUntilFinished) {
                    long hour = offerTime / (60 * 60 * 1000);
                    long minute = (offerTime / (60 * 1000)) % 60;
                    long mouse = (offerTime / 1000) % 60;
                    StringBuilder builder = new StringBuilder();
                    builder.append("仅剩 ");
                    builder.append(addZero((int) hour)).append(":")
                            .append(addZero((int) minute)).append(":")
                            .append(addZero((int) mouse));
                    collageListHolder.time.setText(builder.toString());
                }
                public void onFinish() {
                    collageListHolder.time.setText("已结束");
                }
            }.start();
            countDownMap.put(collageListHolder.time.hashCode(), collageListHolder.countDownTimer);
        } else {
            collageListHolder.time.setText("已结束");
        }

    }

    /**
     * 清空资源
     */
    public void cancelAllTimers() {
        if (countDownMap == null) {
            return;
        }
        for (int i = 0,length = countDownMap.size(); i < length; i++) {
            CountDownTimer cdt = countDownMap.get(countDownMap.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
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
        return groupList==null?0:groupList.size();
    }
}
