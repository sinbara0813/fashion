package com.d2cmall.buyer.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.BrandProductAdapter;
import com.d2cmall.buyer.adapter.BrandUpdateProductAdapter;
import com.d2cmall.buyer.adapter.FindSimilarGridAdapter;
import com.d2cmall.buyer.api.UpGoodApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.base.HeaderViewPagerFragment;
import com.d2cmall.buyer.bean.FlashProductListBean;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.bean.UpGoodBean;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.ExpandListener;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.widget.headerViewPager.HeaderScrollHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/12/28 11:56
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class BrandProductFragment extends HeaderViewPagerFragment{

    @Bind(R.id.recycle_view)
    RecyclerView recycleView;

    private VirtualLayoutManager layoutManage;
    private DelegateAdapter delegateAdapter;
    private ArrayList<GoodsBean.DataBean.ProductsBean.ListBean> list=new ArrayList<>();
    private FindSimilarGridAdapter productAdapter;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志

    private boolean hasSetAdapter; //是否设置了设配器
    private int lastPosition = -1; //页面location
    private int lastOffer;
    private int index=1;
    private boolean hasNext;

    private int id;

    public static BrandProductFragment newInstance(int id){
        BrandProductFragment productFragment=new BrandProductFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("id",id);
        productFragment.setArguments(bundle);
        return productFragment;
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_unrefresh_recyclerview, container, false);
    }

    @Override
    public void prepareView() {
        if (getArguments()!=null){
            id=getArguments().getInt("id");
        }
        layoutManage = new VirtualLayoutManager(mContext);
        delegateAdapter = new DelegateAdapter(layoutManage, true);
        recycleView.setLayoutManager(layoutManage);
        recycleView.setAdapter(delegateAdapter);
        initListener();
    }

    private void setAdapter(){
        if (!isVisibleToUser) return;
        if (productAdapter==null){
            GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
            gridLayoutHelper.setBgColor(Color.WHITE);
            gridLayoutHelper.setAutoExpand(false);
            gridLayoutHelper.setPaddingLeft(ScreenUtil.dip2px(16));
            gridLayoutHelper.setPaddingRight(ScreenUtil.dip2px(16));
            gridLayoutHelper.setPaddingTop(ScreenUtil.dip2px(16));
            gridLayoutHelper.setHGap(ScreenUtil.dip2px(16));
            int itemWidth = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(48)) / 2;
            productAdapter = new FindSimilarGridAdapter(mContext,list,gridLayoutHelper,itemWidth);
            delegateAdapter.addAdapter(productAdapter);
        }else {
            productAdapter.notifyDataSetChanged();
        }
        hasSetAdapter=true;
    }

    private void initListener() {
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                getLastLocation();
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisPosition=layoutManage.findLastVisibleItemPosition();
                int itemCount=layoutManage.getItemCount();
                if (lastVisPosition>0&&lastVisPosition>itemCount-3){
                    if (hasNext){
                        index++;
                        loadData();
                    }else {
                        if (endAdapter==null){
                            ScrollEndBinder endBinder = new ScrollEndBinder();
                            endAdapter = new BaseVirtualAdapter<>(endBinder,100);
                            delegateAdapter.addAdapter(endAdapter);
                            getLastLocation();
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void getLastLocation() {
        View topView = layoutManage.getChildAt(0);
        if (topView != null) {
            //获取与该view的顶部的偏移量
            lastOffer = topView.getTop();
            //得到该View的数组位置
            lastPosition = layoutManage.getPosition(topView);
        }
    }

    @Override
    public void doBusiness() {
        if (list.size()==0){
            loadData();
        }else {
            if (!hasSetAdapter){
                delegateAdapter.addAdapter(productAdapter);
                if (endAdapter!=null){
                    delegateAdapter.addAdapter(endAdapter);
                }
                layoutManage.scrollToPositionWithOffset(lastPosition, lastOffer);
                hasSetAdapter=true;
            }
        }
    }

    private void loadData() {
        UpGoodApi api = new UpGoodApi();
        api.setPageSize(10);
        api.setPageNumber(index);
        api.topId = id;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<UpGoodBean>() {
            @Override
            public void onResponse(UpGoodBean upGoodBean) {
                if (upGoodBean != null) {
                    hasNext=upGoodBean.getData().getProducts().isNext();
                    if (upGoodBean.getData().getProducts().getList().size() > 0) {
                        list.addAll(upGoodBean.getData().getProducts().getList());
                    }
                    setAdapter();
                }
            }
        });
    }

    @Override
    public void releaseOnInVisible() {
        if (delegateAdapter!=null){
            delegateAdapter.removeAdapter(productAdapter);
            if (endAdapter!=null){
                delegateAdapter.removeAdapter(endAdapter);
            }
            hasSetAdapter=false;
        }
    }

    @Override
    public void onDestroyView() {
        layoutManage=null;
        delegateAdapter=null;
        super.onDestroyView();
    }

    @Override
    public View getScrollableView() {
        return recycleView;
    }
}
