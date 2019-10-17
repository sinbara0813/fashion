package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.holder.CouponHolder;

/**
 * Created by rookie on 2017/8/17.
 */

public class CouponAdapter extends DelegateAdapter.Adapter {
    private Context context;
    private LayoutHelper layoutHelper;
    private int id;

    public CouponAdapter(Context context, LayoutHelper layoutHelper, int id){
        this.context=context;
        this.layoutHelper=layoutHelper;
        this.id=id;
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.layout_coupon_item,parent,false);
        return new CouponHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final CouponHolder viewHolder= (CouponHolder) holder;
//        switch (id){
//            case 0:
//                viewHolder.tvRemind.setBackgroundResource(R.mipmap.bg_coupon_newmiddle);
//                break;
//            case 1:
//                viewHolder.tvRemind.setBackgroundResource(R.mipmap.bg_coupon_oldmiddle);
//                viewHolder.ivState.setVisibility(View.VISIBLE);
//                viewHolder.ivState.setImageResource(R.mipmap.icon_coupon_used);
//                viewHolder.tvPrice.setTextColor(Color.parseColor("#61000000"));
//                viewHolder.tvCouponName.setTextColor(Color.parseColor("#61000000"));
//                viewHolder.tvRemind.setTextColor(Color.parseColor("#61000000"));
//                viewHolder.tvLimit.setTextColor(Color.parseColor("#61000000"));
//                viewHolder.tvUseCondition.setTextColor(Color.parseColor("#61000000"));
//                viewHolder.rlMain.setBackgroundResource(R.mipmap.bg_coupon_oldtop);
//                viewHolder.viewBottom.setBackgroundResource(R.mipmap.bg_coupon_oldbottom);
//                break;
//            case 2:
//                viewHolder.tvRemind.setBackgroundResource(R.mipmap.bg_coupon_oldmiddle);
//                viewHolder.ivState.setVisibility(View.VISIBLE);
//                viewHolder.tvRemind.setTextColor(Color.parseColor("#61000000"));
//                viewHolder.tvPrice.setTextColor(Color.parseColor("#61000000"));
//                viewHolder.tvCouponName.setTextColor(Color.parseColor("#61000000"));
//                viewHolder.tvLimit.setTextColor(Color.parseColor("#61000000"));
//                viewHolder.tvUseCondition.setTextColor(Color.parseColor("#61000000"));
//                viewHolder.ivState.setImageResource(R.mipmap.icon_coupon_overdue);
//                viewHolder.rlMain.setBackgroundResource(R.mipmap.bg_coupon_oldtop);
//                viewHolder.viewBottom.setBackgroundResource(R.mipmap.bg_coupon_oldbottom);
//                break;
//        }
//        viewHolder.ivOpen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(viewHolder.isOpen){
//                    viewHolder.isOpen=false;
//                    viewHolder.ivOpen.setImageResource(R.mipmap.icon_coupon_down);
//                    viewHolder.llRemind.setVisibility(View.GONE);
//                }else{
//                    viewHolder.isOpen=true;
//                    viewHolder.ivOpen.setImageResource(R.mipmap.icon_coupon_up);
//                    viewHolder.llRemind.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
