package com.d2cmall.buyer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.LiveAdapter;
import com.d2cmall.buyer.api.LiveListApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.LiveListBean;
import com.d2cmall.buyer.bean.SquareBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Name: 时尚圈直播页
 * Anthor: hrb
 * Date: 2017/8/16 13:37
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FashionLiveFragment extends BaseFragment {

    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;

    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;

    private LiveAdapter liveAdapter;
    private LiveAdapter oldLiveAdapter;
    private boolean hasSetAdapter=true;
    private int lastPosition=-1;
    private int lastOffer;

    private List<LiveListBean.DataBean.LivesBean.ListBean> liveList;
    private List<LiveListBean.DataBean.LivesBean.ListBean> previewList;
    private List<LiveListBean.DataBean.LivesBean.ListBean> oldLiveList;
    private List<String> pics;
    private List<String> picUrls;

    private int p=1;
    private boolean hasNext;

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_refresh_recycleview, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        liveList=new ArrayList<>();
        previewList=new ArrayList<>();
        oldLiveList=new ArrayList<>();
        pics=new ArrayList<>();
        picUrls=new ArrayList<>();
    }

    @Override
    public void prepareView() {
        ptr.setHeadLabel(getString(R.string.label_d2c_go));
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }
        });
        virtualLayoutManager=new VirtualLayoutManager(getActivity());
        delegateAdapter=new DelegateAdapter(virtualLayoutManager,true);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                getLastLocation();
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last=virtualLayoutManager.findLastVisibleItemPosition();
                        if (last>delegateAdapter.getItemCount()-3&&hasNext){
                            p++;
                            loadLiveData();
                        }
                }
            }
        });
        if (!hasSetAdapter){
            addAdapter();
        }
    }

    private void getLastLocation(){
        View topView = virtualLayoutManager.getChildAt(0);
        if(topView != null) {
            //获取与该view的顶部的偏移量
            lastOffer = topView.getTop();
            //得到该View的数组位置
            lastPosition = virtualLayoutManager.getPosition(topView);
        }
    }

    @Override
    public void doBusiness() {
        if (liveList.size()==0&&previewList.size()==0&&oldLiveList.size()==0){
            loadPic();
        }else {

        }
    }

    private void addAdapter(){
        if (!hasSetAdapter&&liveAdapter!=null&&oldLiveAdapter!=null){
            delegateAdapter.addAdapter(liveAdapter);
            delegateAdapter.addAdapter(oldLiveAdapter);
            if (lastPosition>=0){
                virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
            }
            hasSetAdapter=true;
        }
    }

    private void loadPic(){
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.LIVE_LIST_BANNER);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<SquareBean>() {
            @Override
            public void onResponse(SquareBean spuareCarouseBean) {
                if (spuareCarouseBean.getData().getContent() != null && spuareCarouseBean.getData().getContent().size() > 0) {
                    if (spuareCarouseBean.getData().getContent().get(0).getSectionValues() != null && spuareCarouseBean.getData().getContent().get(0).getSectionValues().size() > 0) {
                        int size = spuareCarouseBean.getData().getContent().get(0).getSectionValues().size();
                        pics.clear();
                        picUrls.clear();
                        for (int i = 0; i < size; i++) {
                            pics.add(spuareCarouseBean.getData().getContent().get(0).getSectionValues().get(i).getFrontPic());
                            picUrls.add(spuareCarouseBean.getData().getContent().get(0).getSectionValues().get(i).getUrl());
                        }
                    }
                }
                loadLiveData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadLiveData();
            }
        });
    }

    private void loadLiveData(){
        LiveListApi api = new LiveListApi();
        api.setP(p);
        api.setPageSize(10);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<LiveListBean>() {
            @Override
            public void onResponse(LiveListBean liveListBean) {
                ptr.refreshComplete();
                hasNext=liveListBean.getData().getLives().isNext();
                if(p==1){
                    liveList.clear();
                    oldLiveList.clear();
                }
                if(liveListBean.getData().getLives().getList()!=null&&liveListBean.getData().getLives().getList().size()>0){
                    for(int i=0;i<liveListBean.getData().getLives().getList().size();i++){
                        LiveListBean.DataBean.LivesBean.ListBean bean=liveListBean.getData().getLives().getList().get(i);
                        if(bean.getStatus()==4){//正在直播
                            liveList.add(bean);
                        }else if(liveListBean.getData().getLives().getList().get(i).getStatus()==8){//往期回顾
                            oldLiveList.add(bean);
                        }
                    }
                }
                setLiveAdapter();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (ptr!=null){
                    ptr.refreshComplete();
                }
            }
        });
    }

    private void setLiveAdapter(){
        if (liveAdapter==null){
            liveAdapter=new LiveAdapter(mContext,liveList,1);
            liveAdapter.setPic(pics);
            liveAdapter.setPicUrls(picUrls);
            delegateAdapter.addAdapter(liveAdapter);
        }else {
            liveAdapter.notifyDataSetChanged();
        }
        if (oldLiveAdapter==null){
            oldLiveAdapter=new LiveAdapter(mContext,oldLiveList,2);
            delegateAdapter.addAdapter(oldLiveAdapter);
        }else {
            oldLiveAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void releaseOnInVisible() {
        /*if (delegateAdapter!=null){
            delegateAdapter.removeAdapter(liveAdapter);
            delegateAdapter.removeAdapter(oldLiveAdapter);
            hasSetAdapter=false;
        }*/
    }

    private void refresh(){
        p=1;
        loadPic();
    }

    @Override
    public void onDestroyView() {
        virtualLayoutManager=null;
        delegateAdapter=null;
        hasSetAdapter=false;
        super.onDestroyView();
    }
}
