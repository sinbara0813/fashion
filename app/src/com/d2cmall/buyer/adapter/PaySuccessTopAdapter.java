package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.HomeActivity;
import com.d2cmall.buyer.activity.PaySuccessActivity;
import com.d2cmall.buyer.holder.PaySuccessTopHolder;

/**
 * Created by rookie on 2017/8/29.
 */

public class PaySuccessTopAdapter extends DelegateAdapter.Adapter<PaySuccessTopHolder> {
    private Context context;
    private LayoutHelper layoutHelper;

    public PaySuccessTopAdapter(Context context,LayoutHelper layoutHelper){
        this.context=context;
        this.layoutHelper=layoutHelper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public PaySuccessTopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.layout_pay_success_top,parent,false);
        return new PaySuccessTopHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PaySuccessTopHolder holder, int position) {
        holder.llBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, HomeActivity.class);
                context.startActivity(intent);
                ((PaySuccessActivity)context).finish();
            }
        });
        holder.llLookOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
