package com.d2cmall.buyer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.ProductRecommend1Adapter;
import com.d2cmall.buyer.adapter.ProductRecommendCombAdapter;
import com.d2cmall.buyer.adapter.ProductRelationAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.ProductRecommendApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.CartRecommendBean;
import com.d2cmall.buyer.bean.GroupListBean;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.bean.ProductRelationBean;
import com.d2cmall.buyer.binder.ProductTitleBinder;
import com.d2cmall.buyer.holder.ProductItemTitleHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/26 16:14
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductMatchFragment extends BaseFragment {


    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;

    private List<ProductRelationBean.DataBean.ProductCombBean> combList;
    private BaseVirtualAdapter<ProductItemTitleHolder> combTitleAdapter;
    private ProductRecommendCombAdapter combAdapter;

    private List<ProductRelationBean.DataBean.RelationProductsBean> relationList;
    private BaseVirtualAdapter<ProductItemTitleHolder> relationTitleAdapter;
    private ProductRelationAdapter relationAdapter;

    private List<ProductDetailBean.DataBean.RecommendProductsBean> recommendList;
    private BaseVirtualAdapter<ProductItemTitleHolder> recommendTitleAdapter;
    private ProductRecommend1Adapter productRecommendAdapter;

    private long id;
    private int collageId;

    private int lastPosition = -1;
    private int lastOffer;

    private boolean hasSetAdapter;
    private boolean isRefresh;

    public static ProductMatchFragment newInstance(long id,int collageId) {
        ProductMatchFragment webFragment = new ProductMatchFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        bundle.putInt("collageId",collageId);
        webFragment.setArguments(bundle);
        return webFragment;
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_refresh_recycleview, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
            collageId=getArguments().getInt("collageId");
        }
        combList=new ArrayList<>();
        relationList=new ArrayList<>();
        recommendList = new ArrayList<>();
    }

    @Override
    public void prepareView() {
        RelativeLayout.LayoutParams ll= (RelativeLayout.LayoutParams) ptr.getLayoutParams();
        ll.setMargins(0, ScreenUtil.dip2px(42),0,0);
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
        virtualLayoutManager = new VirtualLayoutManager(getActivity());
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        viewPool.setMaxRecycledViews(1, 0);
        viewPool.setMaxRecycledViews(2,2);
        viewPool.setMaxRecycledViews(3, 0);
        viewPool.setMaxRecycledViews(20, 2);
        viewPool.setMaxRecycledViews(29,0);
        viewPool.setMaxRecycledViews(30, 2);
        recycleView.setRecycledViewPool(viewPool);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                getLastLocation();
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void getLastLocation() {
        View topView = virtualLayoutManager.getChildAt(0);
        if (topView != null) {
            //获取与该view的顶部的偏移量
            lastOffer = topView.getTop();
            //得到该View的数组位置
            lastPosition = virtualLayoutManager.getPosition(topView);
        }
    }

    @Override
    public void doBusiness() {
        if (recommendList.size()==0) {
            if (collageId>0){
                loadCollageData();
            }else {
                loadData();
            }
        }else {
            if (!hasSetAdapter){
                if (combList.size()>0){
                    delegateAdapter.addAdapter(combTitleAdapter);
                    delegateAdapter.addAdapter(combAdapter);
                }
                if (relationList.size()>0){
                    delegateAdapter.addAdapter(relationTitleAdapter);
                    delegateAdapter.addAdapter(relationAdapter);
                }
                if (productRecommendAdapter!=null){
                    delegateAdapter.addAdapter(recommendTitleAdapter);
                    delegateAdapter.addAdapter(productRecommendAdapter);
                }
                if (lastPosition >= 0) {
                    virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
                }
                hasSetAdapter=true;
            }
        }
    }

    private void loadCollageData(){
        SimpleApi api = new SimpleApi();
        api.setP(1);
        api.setPageSize(20);
        api.setMethod(BaseApi.Method.GET);
        api.setInterPath(Constants.GROUP_BUY_PRODUCT_LIST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<GroupListBean>() {
            @Override
            public void onResponse(GroupListBean groupListBean) {
                if (ptr != null) {
                    ptr.refreshComplete();
                } else {
                    return;
                }
                if (groupListBean.getData().getCollageList()!=null&&groupListBean.getData().getCollageList().getList().size()>0){
                    ProductTitleBinder titleBinder = new ProductTitleBinder(getActivity(), "为你推荐");
                    titleBinder.hasMore=false;
                    recommendTitleAdapter = new BaseVirtualAdapter<ProductItemTitleHolder>(titleBinder,29);
                    delegateAdapter.addAdapter(recommendTitleAdapter);
                    int itemWidth=(ScreenUtil.getDisplayWidth()-ScreenUtil.dip2px(48))/2;
                    productRecommendAdapter=new ProductRecommend1Adapter(getActivity(),null,itemWidth,groupListBean.getData().getCollageList().getList().size(),id,"商品详情_搭配tab");
                    productRecommendAdapter.setCollageData(groupListBean.getData().getCollageList().getList());
                    delegateAdapter.addAdapter(productRecommendAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (ptr != null) {
                    ptr.refreshComplete();
                } else {
                    return;
                }
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    private void loadData() {
        ProductRecommendApi api = new ProductRecommendApi();
        api.productId = id;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ProductRelationBean>() {
            @Override
            public void onResponse(ProductRelationBean response) {
                if (isRefresh) {
                    ptr.refreshComplete();
                }
                List<ProductRelationBean.DataBean.ProductCombBean> productCombList=checkComb(response.getData().getProductComb());
                if (productCombList != null && productCombList.size() > 0) {
                    ProductTitleBinder titleBinder = new ProductTitleBinder(getActivity(), "推荐组合");
                    titleBinder.hasMore=false;
                    titleBinder.hasTop=false;
                    combTitleAdapter = new BaseVirtualAdapter<ProductItemTitleHolder>(titleBinder,1);
                    delegateAdapter.addAdapter(combTitleAdapter);
                    combList.clear();
                    combList.addAll(productCombList);
                    combAdapter = new ProductRecommendCombAdapter(getActivity(), combList);
                    delegateAdapter.addAdapter(combAdapter);
                }
                /*else {
                    TextView textView= (TextView) getActivity().findViewById(R.id.match_tv);
                    textView.setText("推荐");
                }*/
                if (response.getData().getRelationProducts() != null && response.getData().getRelationProducts().size() > 0) {
                    ProductTitleBinder titleBinder = new ProductTitleBinder(getActivity(), "最IN搭配");
                    titleBinder.hasMore=false;
                    if (productCombList != null && productCombList.size() > 0){
                        titleBinder.hasTop=true;
                    }else {
                        titleBinder.hasTop=false;
                    }
                    relationTitleAdapter = new BaseVirtualAdapter<ProductItemTitleHolder>(titleBinder,3);
                    delegateAdapter.addAdapter(relationTitleAdapter);
                    relationList.clear();
                    relationList.addAll(response.getData().getRelationProducts());
                    relationAdapter = new ProductRelationAdapter(getActivity(), relationList);
                    delegateAdapter.addAdapter(relationAdapter);
                }
                loadRecommend();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (isRefresh){
                    ptr.refreshComplete();
                }
            }
        });
    }

    private List<ProductRelationBean.DataBean.ProductCombBean> checkComb(List<ProductRelationBean.DataBean.ProductCombBean> list){
        List<ProductRelationBean.DataBean.ProductCombBean> result=new ArrayList<>();
        int size=list.size();
        for (int i=0;i<size;i++){
            boolean isAdd=true;
            if (list.get(i).getMark()==0){
                continue;
            }
            int productSize=list.get(i).getProducts().size();
            for (int j=0;j<productSize;j++){
                if (list.get(i).getProducts().get(j).getMark()==0){
                    isAdd=false;
                    break;
                }
            }
            if (isAdd){
                result.add(list.get(i));
            }
        }
        return result;
    }

    private void loadRecommend() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GET_PRODUCT_MATCH_LIST, id, 30));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CartRecommendBean>() {
            @Override
            public void onResponse(CartRecommendBean response) {
                if (response.getList()!=null&&response.getList().size()>0){
                    ProductTitleBinder titleBinder = new ProductTitleBinder(getActivity(), "为你推荐");
                    titleBinder.hasMore=false;
                    recommendTitleAdapter = new BaseVirtualAdapter<ProductItemTitleHolder>(titleBinder,29);
                    delegateAdapter.addAdapter(recommendTitleAdapter);
                    recommendList.clear();
                    recommendList.addAll(response.getList());
                    int itemWidth=(ScreenUtil.getDisplayWidth()-ScreenUtil.dip2px(48))/2;
                    productRecommendAdapter=new ProductRecommend1Adapter(getActivity(),recommendList,itemWidth,0,id,"商品详情_搭配tab");
                    delegateAdapter.addAdapter(productRecommendAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void  refresh() {
        isRefresh=true;
        if (combList.size()>0){
            delegateAdapter.removeAdapter(combTitleAdapter);
            delegateAdapter.removeAdapter(combAdapter);
        }
        if (relationList.size()>0){
            delegateAdapter.removeAdapter(relationTitleAdapter);
            delegateAdapter.removeAdapter(relationAdapter);
        }
        if (productRecommendAdapter!=null){
            delegateAdapter.removeAdapter(recommendTitleAdapter);
            delegateAdapter.removeAdapter(productRecommendAdapter);
        }
        if (collageId>0){
            loadCollageData();
        }else {
            loadData();
        }
    }

    @Override
    public void releaseOnInVisible() {
        if (combList.size()>0){
            delegateAdapter.removeAdapter(combTitleAdapter);
            delegateAdapter.removeAdapter(combAdapter);
        }
        if (relationList.size()>0){
            delegateAdapter.removeAdapter(relationTitleAdapter);
            delegateAdapter.removeAdapter(relationAdapter);
        }
        if (productRecommendAdapter!=null){
            delegateAdapter.removeAdapter(recommendTitleAdapter);
            delegateAdapter.removeAdapter(productRecommendAdapter);
        }
        hasSetAdapter=false;
    }
}
