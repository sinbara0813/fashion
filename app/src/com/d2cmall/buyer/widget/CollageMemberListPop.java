package com.d2cmall.buyer.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.CollageMemberListAdapter;
import com.d2cmall.buyer.bean.CollageDetailBean;
import com.d2cmall.buyer.bean.CombProductBean;

import java.util.List;

/**
 * 拼团成员列表pop
 * Date: 2016/11/05 12:59
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class CollageMemberListPop implements TransparentPop.InvokeListener {
    private Context context;
    private TransparentPop mPop;
    private View rootView;
    private RecyclerView recyclerView;
    private VirtualLayoutManager mVirtualLayoutManager;
    private CombProductBean mCombProductBean;
    private CollageMemberListAdapter mCollageMemberListAdapter;
    private TextView tvSure;
    private  List<CollageDetailBean.DataBean.CollageGroupBean.CollageOrder> mCollageOrders;

    public CollageMemberListPop(Context context, List<CollageDetailBean.DataBean.CollageGroupBean.CollageOrder> collageOrders) {
        this.context = context;
        //过滤失败成员,失败成员不显示在拼团成员列表
        filterFailOrders(collageOrders);
        init();
    }

    private void filterFailOrders(List<CollageDetailBean.DataBean.CollageGroupBean.CollageOrder> collageOrders) {
        if (collageOrders == null) {
            return;
        }
        for (int i = 0; i < collageOrders.size(); i++) {
            if (collageOrders.get(i).getStatus() < 0) {
                collageOrders.remove(i);
            }
        }
        this.mCollageOrders = collageOrders;
    }

    private void init() {
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_collage_member_recycleview, new RelativeLayout(context), false);
//        ViewGroup.LayoutParams layoutParams = rootView.getLayoutParams();
//        layoutParams.width=ScreenUtil.getDisplayWidth();
//        layoutParams.height=ScreenUtil.dip2px(370);
//        rootView.setLayoutParams(layoutParams);
        mPop = new TransparentPop(context,this);
        mPop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPop.setFocusable(true);
        mPop.setOutsideTouchable(true);
        mPop.dismissFromOut();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycle_view);
        tvSure = (TextView) rootView.findViewById(R.id.tv_sure);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //空方法拦截事件
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView() {
        mVirtualLayoutManager = new VirtualLayoutManager(context);
        mCollageMemberListAdapter = new CollageMemberListAdapter(context, mCollageOrders);
        recyclerView.setLayoutManager(mVirtualLayoutManager);
        DelegateAdapter delegateAdapter = new DelegateAdapter(mVirtualLayoutManager, false);
        recyclerView.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(mCollageMemberListAdapter);
    }

    public void show(View parent) {
        mPop.show(parent, true);
    }
    public void dismiss() {
        if (mPop != null) {
            mPop.dismiss(true);
        }
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.BOTTOM);
        v.addView(rootView);
    }


    @Override
    public void releaseView(LinearLayout v) {

    }
    public void setDismissListener(TransparentPop.DismissListener dismissListener) {
        mPop.setDismissListener(dismissListener);
    }
}
