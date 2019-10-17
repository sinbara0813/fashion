package com.d2cmall.buyer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.BrandListAdapter;
import com.d2cmall.buyer.api.DesignersApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.DesignerBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by rookie on 2017/8/2.
 */

public class BrandListFragment extends BaseFragment {

    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private DelegateAdapter delegateAdapter;
    private String type = null;
    private String keyword = null;
    private int id = 0;
    private int p = 1;
    private List<DesignerBean.DataEntity.DesignersEntity.ListEntity> list;
    private BrandListAdapter brandListAdapter;
    private VirtualLayoutManager layoutManager;
    private boolean hasSetAdapter;
    private int lastPosition = -1;
    private int lastOffer;
    private boolean hasNext;

    public static BrandListFragment newInstance(String type2, int id) {
        BrandListFragment fragment = new BrandListFragment();
        Bundle args = new Bundle();
        args.putString("type", type2);
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    public static BrandListFragment newInstance(String keyword) {
        BrandListFragment fragment = new BrandListFragment();
        Bundle args = new Bundle();
        args.putString("keyword", keyword);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_specific_brand, container, false);
    }

    @Override
    public void prepareView() {
        progressBar.setVisibility(View.VISIBLE);
        recycleView.setVisibility(View.GONE);
        initRecycle();
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
                        int last = layoutManager.findLastVisibleItemPosition();
                        if (delegateAdapter != null) {
                            if (last > delegateAdapter.getItemCount() - 3 && hasNext) {
                                p++;
                                loadData();
                            }
                        }
                }
            }
        });
    }

    private void getLastLocation() {
        View topView = layoutManager.getChildAt(0);
        if (topView != null) {
            //获取与该view的顶部的偏移量
            lastOffer = topView.getTop();
            //得到该View的数组位置
            lastPosition = layoutManager.getPosition(topView);
        }
    }

    @Override
    public void doBusiness() {
        if (list.size() == 0) {
            loadData();
        } else {
            if (!hasSetAdapter) {
                delegateAdapter.addAdapter(brandListAdapter);
                if (lastPosition >= 0) {
                    layoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
                }
                hasSetAdapter = true;
            }
            progressBar.setVisibility(View.GONE);
            recycleView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString("type");
            id = getArguments().getInt("id");
            keyword = getArguments().getString("keyword");
        }
        list = new ArrayList<>();
    }

    @Override
    public void releaseOnInVisible() {
        if (delegateAdapter != null) {
            delegateAdapter.removeAdapter(brandListAdapter);
            hasSetAdapter = false;
        }
    }

    private void loadData() {
        if (id != 0 && keyword == null) {
            DesignersApi api = new DesignersApi();
            if (type.equals("style")) {
                api.setStyle(String.valueOf(id));
            } else if (type.equals("country")) {
                api.setCountry(String.valueOf(id));
            } else if (type.equals("designArea")) {
                api.setDesignArea(String.valueOf(id));
            }
            if (Util.isEmpty(type)) {
                api.setTagId(id + "");
            }
            api.setP(p);
            api.setPageSize(20);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<DesignerBean>() {
                @Override
                public void onResponse(DesignerBean designerBean) {
                    if (!isVisibleToUser){
                        return;
                    }
                    if (progressBar == null) {
                        return;
                    }
                    hasNext = designerBean.getData().getDesigners().isNext();
                    if (p==1){
                        list.clear();
                    }
                    if (designerBean.getData().getDesigners().getList()!=null&&designerBean.getData().getDesigners().getList().size()>0){
                        list.addAll(designerBean.getData().getDesigners().getList());
                    }
                    progressBar.setVisibility(View.GONE);
                    recycleView.setVisibility(View.VISIBLE);
                    setAdapter();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (progressBar == null) {
                        return;
                    }
                    progressBar.setVisibility(View.GONE);
                    recycleView.setVisibility(View.VISIBLE);
                    Util.showToast(mContext, Util.checkErrorType(error));
                }
            });
        }
    }

    private void setAdapter(){
        if (brandListAdapter==null){
            brandListAdapter=new BrandListAdapter(mContext,list);
            delegateAdapter.addAdapter(brandListAdapter);
        }else {
            brandListAdapter.notifyDataSetChanged();
        }
    }

    private void initRecycle() {
        layoutManager = new VirtualLayoutManager(getContext());
        recycleView.setLayoutManager(layoutManager);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        recycleView.setAdapter(delegateAdapter);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        viewPool.setMaxRecycledViews(0, 3);
        recycleView.setRecycledViewPool(viewPool);
    }

}
