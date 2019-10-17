package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.CollageListAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.CollageListBean;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * 拼团列表弹窗
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CollageListPop implements TransparentPop.InvokeListener {

    private TransparentPop mPop;
    private Context mContext;
    private View rootView;
    private Animation inAnimation;
    private Animation outAnimation;
    private TextView sureTv;
    private RecyclerView recyclerView;
    private List<CollageListBean.DataBean.GroupListBean.ListBean> groupList;
    private List<TextView> timeTvs;
    private CountDownTimer countDownTimer;
    private int groupId;
    private int currentP=1;
    private VirtualLayoutManager mLayoutManager;
    private DelegateAdapter delegateAdapter;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志
    private CollageListAdapter collageListAdapter;
    private boolean hasNext =true;
    public CollageListPop(Context context, int id) {
        this.mContext = context;
        groupId=id;
        this.groupList = new ArrayList<>();
        init();
    }

    private void init() {
        rootView = LayoutInflater.from(mContext).inflate(R.layout.collage_list_pop, null);
        sureTv=rootView.findViewById(R.id.sure_tv);
        recyclerView=rootView.findViewById(R.id.recycler_view);
        mPop = new TransparentPop(mContext, this);
        timeTvs=new ArrayList<>();
        inAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_up);
        inAnimation.setInterpolator(new DecelerateInterpolator());
        outAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_down);
        outAnimation.setInterpolator(new AccelerateInterpolator());
        mPop.setInAnimation(inAnimation);
        mPop.setOutAnimation(outAnimation);
        mPop.dismissFromOut();
        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mLayoutManager = new VirtualLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        delegateAdapter = new DelegateAdapter(mLayoutManager, true);
        collageListAdapter = new CollageListAdapter(mContext, groupList);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0,2);
        recyclerView.setRecycledViewPool(recycledViewPool);
        recyclerView.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(collageListAdapter);
        initListener();
        loadGroupsData();
    }
    private void initListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = mLayoutManager.findLastVisibleItemPosition();
                        if (last > collageListAdapter.getItemCount() - 3 && hasNext) {
                            currentP++;
                            loadGroupsData();
                        }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisPosition=mLayoutManager.findLastVisibleItemPosition();
                int itemCount=mLayoutManager.getItemCount();
                if (lastVisPosition>=itemCount-3 && !hasNext && currentP>1){
                    if (endAdapter==null){
                        ScrollEndBinder endBinder = new ScrollEndBinder();
                        endAdapter = new BaseVirtualAdapter<>(endBinder,100);
                        delegateAdapter.addAdapter(endAdapter);
                    }
                }else {
                    if (endAdapter!=null){
                        delegateAdapter.removeAdapter(endAdapter);
                        endAdapter=null;
                    }
                }
            }
        });
    }

    private void loadGroupsData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GROUP_LIST,groupId));
        api.setP(currentP);
        api.setPageSize(20);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CollageListBean>() {
            @Override
            public void onResponse(CollageListBean collageListBean) {
                if (currentP==1) {
                    groupList.clear();
                }
                if(collageListBean!=null && collageListBean.getData().getGroupList()!=null && collageListBean.getData().getGroupList().getList().size()>0){
                    groupList.addAll(collageListBean.getData().getGroupList().getList());
                }
                hasNext=collageListBean.getData().getGroupList().isNext();
                collageListAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(mContext,Util.checkErrorType(error));
            }
        });
    }



    public void show(View parent) {
        mPop.show(parent);
    }

    public void dismiss() {
        if (mPop != null) {
            mPop.dismiss(true);
        }
        if(collageListAdapter!=null){
            collageListAdapter.cancelAllTimers();
        }
    }

    public boolean isShow() {
        return mPop.isShowing();
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.BOTTOM);
        Point p = Util.getDeviceSize(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, p.y * 2 / 3);
        v.addView(rootView, lp);
    }

    @Override
    public void releaseView(LinearLayout v) {
        ((ViewGroup) rootView).removeAllViews();
        if(countDownTimer!=null){
            countDownTimer.cancel();
            countDownTimer=null;
        }
        rootView = null;
    }

}
