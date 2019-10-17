package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.CollageDetailBean;
import com.d2cmall.buyer.holder.CollageMemberListHolder;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Created by Administrator on 2018/6/22.
 * Description : CollageMemberListAdapter
 */

public class CollageMemberListAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private  List<CollageDetailBean.DataBean.CollageGroupBean.CollageOrder> collageOrders;
    public CollageMemberListAdapter(Context context, List<CollageDetailBean.DataBean.CollageGroupBean.CollageOrder> collageOrders) {
        this.mContext=context;
        this.collageOrders=collageOrders;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
//        linearLayoutHelper.setPaddingTop(ScreenUtil.dip2px(8));
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_collage_member_list_item, parent, false);
        return new CollageMemberListHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CollageMemberListHolder collageMemberListHolder = (CollageMemberListHolder) holder;
        UniversalImageLoader.displayImage(mContext,collageOrders.get(position).getHeadPic(),collageMemberListHolder.ivMemeberHead,R.mipmap.ic_default_avatar);
        collageMemberListHolder.tvName.setText(Util.isEmpty(collageOrders.get(position).getMemberName())?"匿名_"+collageOrders.get(position).getMemberId():collageOrders.get(position).getMemberName());
        collageMemberListHolder.tvTime.setText(DateUtil.toString(collageOrders.get(position).getCreateDate()));
        if(collageOrders.get(position).getType()==1){
            collageMemberListHolder.ivColonel.setVisibility(View.VISIBLE);
        }else{
            collageMemberListHolder.ivColonel.setVisibility(View.GONE);
        }
        if(collageOrders.get(position).getType()==1){
            collageMemberListHolder.tvStatus.setText("开团成功");
        }else if(collageOrders.get(position).getStatus()==1){
            collageMemberListHolder.tvStatus.setText("待支付");
        }else{
            collageMemberListHolder.tvStatus.setText("参团成功");
        }

    }

    @Override
    public int getItemCount() {
        return collageOrders==null?0:collageOrders.size();
    }
}
