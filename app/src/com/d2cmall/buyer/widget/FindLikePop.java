package com.d2cmall.buyer.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.PhotoBuyAdapter;
import com.d2cmall.buyer.api.MyCollectProductBean;
import com.d2cmall.buyer.api.SearchPicApi;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.bean.PhotoProductBean;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.zmxy.ZMCertification;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: d2c
 * 衣橱找相似
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FindLikePop implements TransparentPop.InvokeListener {


    @Bind(R.id.iv_colse)
    ImageView ivColse;
    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.rl_root)
    RelativeLayout rlRoot;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;

    private View rootView;
    private TransparentPop pop;
    private Context mContext;
    private PhotoBuyAdapter photoBuyAdapter;
    private String picUrl;
    private ArrayList<MyCollectProductBean.DataBean.MyCollectionsBean.ListBean> collectProductList = new ArrayList<>();
    private VirtualLayoutManager mLayoutManager;
    private DelegateAdapter delegateAdapter;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志
    private ZMCertification zmCertification;
    private GridLayoutHelper gridLayoutHelper;
    private List<GoodsBean.DataBean.ProductsBean.ListBean> productList;

    public FindLikePop(Context context, String picUrl) {
        this.mContext = context;
        this.picUrl = picUrl;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_find_like_pop, new LinearLayout(context), false);
        ButterKnife.bind(this, rootView);
        pop = new TransparentPop(context, this);
        pop.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_up));
        pop.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_down));
        pop.getContentView().setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        init();
    }

    private void init() {
        ivColse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        productList = new ArrayList<>();
        ViewGroup.LayoutParams layoutParams = rlRoot.getLayoutParams();
        layoutParams.height = ScreenUtil.getDisplayHeight() - ScreenUtil.dip2px(80);
        rlRoot.setLayoutParams(layoutParams);
        mLayoutManager = new VirtualLayoutManager(mContext);
        gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setPaddingLeft(ScreenUtil.dip2px(16));
        gridLayoutHelper.setPaddingRight(ScreenUtil.dip2px(16));
        gridLayoutHelper.setHGap(ScreenUtil.dip2px(16));
        gridLayoutHelper.setMarginTop(ScreenUtil.dip2px(16));
        int itemWidth = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(48)) / 2;
        photoBuyAdapter = new PhotoBuyAdapter(mContext, itemWidth, productList);
        recyclerView.setLayoutManager(mLayoutManager);
        delegateAdapter = new DelegateAdapter(mLayoutManager, true);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0, 2);
        recyclerView.setRecycledViewPool(recycledViewPool);
        recyclerView.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(photoBuyAdapter);
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnReload.setVisibility(View.GONE);
                imgHint.setVisibility(View.GONE);
                requestFindLike();
            }
        });
        requestFindLike();
    }


    private void requestFindLike() {
        progressBar.setVisibility(View.VISIBLE);
        SearchPicApi api = new SearchPicApi();
        api.setPicUrl(picUrl);
        api.pageNumber = 40;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PhotoProductBean>() {
            @Override
            public void onResponse(PhotoProductBean response) {
                progressBar.setVisibility(View.GONE);
                if(response.getData().getProducts().getList()==null || response.getData().getProducts().getList().size()==0){
                    setEmptyView(Constants.NO_DATA);
                    return;
                }
                productList.addAll(response.getData().getProducts().getList());
                photoBuyAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                setEmptyView(Constants.NET_DISCONNECT);
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }
    private void setEmptyView(int type) {
        if (type == Constants.NO_DATA) {
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.icon_empty_default);
            btnReload.setVisibility(View.VISIBLE);
            btnReload.setText("暂无数据");
            btnReload.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        } else {
            btnReload.setText("重新加载");
            btnReload.setBackgroundResource(R.drawable.sp_line);
            btnReload.setVisibility(View.VISIBLE);
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.ic_no_net);
        }

    }
    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.BOTTOM);
        v.addView(rootView);
    }

    public void show(View view) {
        pop.show(view);
    }

    public void dismiss() {
        pop.dismiss(true);
    }

    public boolean isShow() {
        return pop.isShowing();
    }

    public void setDissMissListener(TransparentPop.DismissListener dissMissListener) {
        pop.setDismissListener(dissMissListener);
    }

    @Override
    public void releaseView(LinearLayout v) {
        v.removeAllViews();
        ((ViewGroup) rootView).removeAllViews();
        rootView = null;
    }

}
