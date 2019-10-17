package com.d2cmall.buyer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ImagePreviewActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.adapter.ProductCommentAdapter;
import com.d2cmall.buyer.adapter.ProductConsultAdapter;
import com.d2cmall.buyer.adapter.ProductRecommend1Adapter;
import com.d2cmall.buyer.adapter.ProductRelationAdapter;
import com.d2cmall.buyer.adapter.ProductShowAdapter;
import com.d2cmall.buyer.adapter.ProductSpoorAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.ComdityCommentListApi;
import com.d2cmall.buyer.api.ProductRecommendApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.AdShareBean;
import com.d2cmall.buyer.bean.CartRecommendBean;
import com.d2cmall.buyer.bean.CollageProductDetailBean;
import com.d2cmall.buyer.bean.CommentCountBean;
import com.d2cmall.buyer.bean.ConsultListBean;
import com.d2cmall.buyer.bean.GroupListBean;
import com.d2cmall.buyer.bean.ProductCommentListBean;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.bean.ProductRelationBean;
import com.d2cmall.buyer.bean.ProductShowBean;
import com.d2cmall.buyer.bean.SumBean;
import com.d2cmall.buyer.binder.PackProductInfoBinder;
import com.d2cmall.buyer.binder.ProductInfoBinder;
import com.d2cmall.buyer.binder.ProductTitleBinder;
import com.d2cmall.buyer.binder.ProductWebBinder;
import com.d2cmall.buyer.binder.VideoBannerBinder;
import com.d2cmall.buyer.holder.ProductItemTitleHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.OnItemClickListener;
import com.d2cmall.buyer.util.HttpUtils;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.d2cmall.buyer.widget.ninegrid.ImageInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/26 16:15
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductFragment extends BaseFragment{

    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;

    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;

    private List<String> bannerItems;
    private VideoBannerBinder bannerBinder;
    private BaseVirtualAdapter<? extends RecyclerView.ViewHolder> bannerAdapter;


    private ProductInfoBinder productInfoBinder;
    private PackProductInfoBinder packProductInfoBinder;
    private BaseVirtualAdapter<? extends RecyclerView.ViewHolder> productInfoAdapter;
    private BaseVirtualAdapter<? extends RecyclerView.ViewHolder> webAdapter;

    private List<ProductShowBean.DataEntity.MembersharesEntity.ListEntity> showList;
    private List<ProductCommentListBean.DataEntity.CommentsEntity.ListEntity> commentList;
    private ProductShowAdapter showAdapter;
    private ProductCommentAdapter commentAdapter;
    private boolean isLoadShow;

    private List<ConsultListBean.DataEntity.ConsultsEntity.ListEntity> consultList;
    private ProductConsultAdapter consultAdapter;

    private List<ProductRelationBean.DataBean.RelationProductsBean> relationList;
    private BaseVirtualAdapter<ProductItemTitleHolder> relationTitleAdapter;
    private ProductRelationAdapter relationAdapter;

    private List<ProductDetailBean.DataBean.RecommendProductsBean> recommendList;
    private BaseVirtualAdapter<ProductItemTitleHolder> recommendTitleAdapter;
    private ProductRecommend1Adapter productRecommendAdapter;

    private ProductSpoorAdapter spoorAdapter;

    private List<ProductRelationBean.DataBean.ProductCombBean> combList;

    private int lastPosition = -1;
    private int lastOffer;
    private boolean hasSetAdapter;
    private boolean isReFresh;

    private long id;
    private int collageId;
    private ProductDetailBean.DataBean.ProductBean productDetailBean;
    public static int totalCount;
    public static int consultCount;
    public static int picCount;
    public static int shareCount;
    private AdShareBean.DataBean.AdResourceBean adResourceBean;
    private AdShareBean.DataBean.AdResourceBean promotionAd;
    public boolean hasAddZu;

    public static ProductFragment newInstance(long id,int collageId) {
        ProductFragment productFragment = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        bundle.putInt("collageId",collageId);
        productFragment.setArguments(bundle);
        return productFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bannerItems = new ArrayList<>();
        showList = new ArrayList<>();
        commentList=new ArrayList<>();
        consultList = new ArrayList<>();
        relationList = new ArrayList<>();
        recommendList = new ArrayList<>();
        combList=new ArrayList<>();
        if (getArguments() != null) {
            id = getArguments().getLong("id");
            collageId=getArguments().getInt("collageId");
        }
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product, container, false);
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
        virtualLayoutManager = new VirtualLayoutManager(mContext);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);

        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        viewPool.setMaxRecycledViews(1, 0);
        viewPool.setMaxRecycledViews(2, 0);
        viewPool.setMaxRecycledViews(3, 0);
        viewPool.setMaxRecycledViews(12, 0);
        viewPool.setMaxRecycledViews(13, 0);
        viewPool.setMaxRecycledViews(14, 0);
        viewPool.setMaxRecycledViews(15, 2);
        viewPool.setMaxRecycledViews(16, 0);
        viewPool.setMaxRecycledViews(17, 0);
        viewPool.setMaxRecycledViews(18, 2);
        viewPool.setMaxRecycledViews(19, 0);
        viewPool.setMaxRecycledViews(20, 2);
        viewPool.setMaxRecycledViews(29, 0);
        viewPool.setMaxRecycledViews(30, 2);
        recycleView.setRecycledViewPool(viewPool);
        if (lastPosition >= 0) {
            virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
        }
        hasSetAdapter = true;
        initListener();
    }

    private void initListener() {
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (virtualLayoutManager.findFirstVisibleItemPosition()==0){
                    bannerBinder.setTranslationY(dy);
                }
                if (isVisibleToUser) {
                    float alpha = virtualLayoutManager.getOffsetToStart() / (float) ScreenUtil.dip2px(200);
                    ((ProductDetailActivity) mContext).changeAlpha(alpha);
                }
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                getLastLocation();
                int first=virtualLayoutManager.findFirstVisibleItemPosition();
                if (first>=2){
                    if (!hasAddZu){
                        spoorAdapter =new ProductSpoorAdapter(mContext, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                virtualLayoutManager.smoothScrollToPosition(recycleView, null,0);
                            }
                        });
                        delegateAdapter.addAdapter(spoorAdapter);
                        hasAddZu=true;
                    }
                }else {
                    if (hasAddZu&&spoorAdapter!=null){
                        delegateAdapter.removeAdapter(spoorAdapter);
                        hasAddZu=false;
                    }
                }
                if (productDetailBean!=null && virtualLayoutManager.findLastVisibleItemPosition() == virtualLayoutManager.getItemCount() - 1) {
                    loadNextData();
                }
            }
        });
    }

    private void loadNextData() {
        if (!isLoadShow) {
            isLoadShow = true;
            SimpleApi api = new SimpleApi();
            api.setInterPath(String.format(Constants.GET_PRODUCT_SHOW_LIST, id));
            api.setP(1);
            api.setPageSize(1);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ProductShowBean>() {
                @Override
                public void onResponse(ProductShowBean response) {
                    if (!isVisibleToUser) {
                        return;
                    }
                    if (response.getData().getMembershares() != null && response.getData().getMembershares().getList().size() > 0) {
                        showList.addAll(response.getData().getMembershares().getList());
                        showAdapter = new ProductShowAdapter(mContext, showList);
                        if (adResourceBean != null) {
                            showAdapter.setAdResource(adResourceBean);
                        }
                        showAdapter.isShort(true);
                        showAdapter.setId(productDetailBean.getId());
                        if (productDetailBean.getImgs().size() > 0) {
                            showAdapter.setPic(productDetailBean.getImgs().get(0));
                        }
                        showAdapter.setName(productDetailBean.getName());
                        delegateAdapter.addAdapter(showAdapter);
                        loadConsult();
                    } else {
                        loadComment();
                    }
                    //loadConsult();
                }
            });
        }
    }

    private void loadComment(){
        ComdityCommentListApi api = new ComdityCommentListApi();
        api.setP(1);
        api.setPageSize(1);
        api.setInterPath(String.format(Constants.GET_PRODUCT_COMMENT_LIST, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ProductCommentListBean>() {
            @Override
            public void onResponse(ProductCommentListBean response) {
                if (!isVisibleToUser) {
                    return;
                }
                if (response.getData().getComments().getList().size() > 0) {
                    commentList.addAll(response.getData().getComments().getList());
                    commentAdapter = new ProductCommentAdapter(getActivity(), commentList);
                    commentAdapter.setHasTitle(true);
                    delegateAdapter.addAdapter(commentAdapter);
                }
                if (adResourceBean != null) {
                    showAdapter = new ProductShowAdapter(mContext, showList);
                    showAdapter.setAdResource(adResourceBean);
                    showAdapter.isShort(true);
                    showAdapter.setId(productDetailBean.getId());
                    if (productDetailBean.getImgs().size() > 0) {
                        showAdapter.setPic(productDetailBean.getImgs().get(0));
                    }
                    showAdapter.setName(productDetailBean.getName());
                    delegateAdapter.addAdapter(showAdapter);
                }
                loadConsult();
            }
        });
    }

    private void loadPromotionAd(){
        SimpleApi api=new SimpleApi();
        api.setInterPath(Constants.GET_PRODUCT_REC_AD);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<AdShareBean>() {
            @Override
            public void onResponse(AdShareBean response) {
                if (response.getData().getAdResource() != null && !Util.isEmpty(response.getData().getAdResource().getPic())) {
                    promotionAd = response.getData().getAdResource();
                    if (productInfoAdapter!=null){
                        productDetailBean.setAdPic(promotionAd.getPic());
                        productDetailBean.setAdUrl(promotionAd.getUrl());
                        delegateAdapter.notifyItemChanged(1);
                    }
                }
            }
        });
    }

    private void loadCount() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GET_PRODUCT_COUNT, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CommentCountBean>() {
            @Override
            public void onResponse(CommentCountBean response) {
                totalCount = response.getData().getTotalCount();
                picCount = response.getData().getPicCount();
                shareCount = response.getData().getShareCount();
                consultCount = response.getData().getConsultCount();
            }
        });
    }

    private void loadConsult() {
        SimpleApi api = new SimpleApi();
        api.setP(1);
        api.setPageSize(5);
        api.setInterPath(String.format(Constants.GET_PRODUCT_CONSULT_LIST, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ConsultListBean>() {
            @Override
            public void onResponse(ConsultListBean response) {
                if (response.getData().getConsults() != null && response.getData().getConsults().getList().size() > 0) {
                    consultList.add(response.getData().getConsults().getList().get(0));
                    consultAdapter = new ProductConsultAdapter(mContext, consultList);
                    consultAdapter.isShort(true);
                    delegateAdapter.addAdapter(consultAdapter);
                }
                if (collageId>0){
                    loadCollageRecommend();
                }else {
                    loadRelation();
                }
            }
        });
    }

    private void loadCollageRecommend(){
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

    private void loadRelation() {
        ProductRecommendApi api = new ProductRecommendApi();
        api.productId = id;
        api.p = 1;
        api.pageSize = 5;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ProductRelationBean>() {
            @Override
            public void onResponse(ProductRelationBean response) {
                if (response.getData().getRelationProducts() != null && response.getData().getRelationProducts().size() > 0) {
                    ProductTitleBinder titleBinder = new ProductTitleBinder(mContext, "最IN搭配");
                    titleBinder.setClickMoreListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActionBean actionBean = new ActionBean(Constants.ActionType.CHANGE_PRODUCT_PAGE);
                            actionBean.put("position", 3);
                            EventBus.getDefault().post(actionBean);
                        }
                    });
                    relationTitleAdapter = new BaseVirtualAdapter<ProductItemTitleHolder>(titleBinder, 19);
                    delegateAdapter.addAdapter(relationTitleAdapter);
                    relationList.clear();
                    int size = response.getData().getRelationProducts().size() > 2 ? 2 : response.getData().getRelationProducts().size();
                    List<ProductRelationBean.DataBean.RelationProductsBean> list = new ArrayList<ProductRelationBean.DataBean.RelationProductsBean>();
                    for (int i = 0; i < size; i++) {
                        list.add(response.getData().getRelationProducts().get(i));
                    }
                    relationList.addAll(list);
                    relationAdapter = new ProductRelationAdapter(mContext, relationList);
                    delegateAdapter.addAdapter(relationAdapter);
                }
                loadRecommend();
            }
        });
    }

    private void loadRecommend() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GET_PRODUCT_MATCH_LIST, id, 10));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CartRecommendBean>() {
            @Override
            public void onResponse(CartRecommendBean response) {
                if (response.getList() != null && response.getList().size() > 0) {
                    ProductTitleBinder titleBinder = new ProductTitleBinder(mContext, "为你推荐");
                    titleBinder.setClickMoreListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActionBean actionBean = new ActionBean(Constants.ActionType.CHANGE_PRODUCT_PAGE);
                            actionBean.put("position", 3);
                            EventBus.getDefault().post(actionBean);
                        }
                    });
                    recommendTitleAdapter = new BaseVirtualAdapter<ProductItemTitleHolder>(titleBinder, 29);
                    delegateAdapter.addAdapter(recommendTitleAdapter);
                    recommendList.clear();
                    int size = response.getList().size() > 8 ? 8 : response.getList().size();
                    List<ProductDetailBean.DataBean.RecommendProductsBean> list = new ArrayList<ProductDetailBean.DataBean.RecommendProductsBean>();
                    for (int i = 0; i < size; i++) {
                        list.add(response.getList().get(i));
                    }
                    recommendList.addAll(list);
                    int itemWidth = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(48)) / 2;
                    productRecommendAdapter = new ProductRecommend1Adapter(mContext, recommendList, itemWidth, 0, id, "商品详情_商品tab");
                    delegateAdapter.addAdapter(productRecommendAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    @Override
    public void doBusiness() {
        if (productDetailBean == null) {
            if (collageId>0){
                loadCollageData();
            }else {
                loadData();
            }
            loadPromotionAd();

            loadCount();
        } else {
            if (!hasSetAdapter) {
                delegateAdapter.addAdapter(bannerAdapter);
                if (productInfoAdapter != null) {
                    delegateAdapter.addAdapter(productInfoAdapter);
                    delegateAdapter.addAdapter(webAdapter);
                }
                if (commentAdapter!=null){
                    delegateAdapter.addAdapter(commentAdapter);
                }
                if (showAdapter!=null) {
                    delegateAdapter.addAdapter(showAdapter);
                }
                if (consultList.size() > 0) {
                    delegateAdapter.addAdapter(consultAdapter);
                }
                if (relationList.size() > 0) {
                    delegateAdapter.addAdapter(relationTitleAdapter);
                    delegateAdapter.addAdapter(relationAdapter);
                }
                if (productRecommendAdapter!=null) {
                    delegateAdapter.addAdapter(recommendTitleAdapter);
                    delegateAdapter.addAdapter(productRecommendAdapter);
                }
                if (lastPosition >= 0) {
                    virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
                }
                hasSetAdapter = true;
            }
        }
    }

    private void loadData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.PRODUCT_URL, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ProductDetailBean>() {
            @Override
            public void onResponse(ProductDetailBean detailBean) {
                if (!isVisibleToUser||mContext==null) {
                    return;
                }
                if (isReFresh && ptr != null) {
                    ptr.refreshComplete();
                }
                productDetailBean = detailBean.getData().getProduct();
                if (detailBean.getData().getCollagePromotion()!=null){
                    productDetailBean.setCollageId(detailBean.getData().getCollagePromotion().getId());
                }
                if (promotionAd!=null){
                    productDetailBean.setAdPic(promotionAd.getPic());
                    productDetailBean.setAdUrl(promotionAd.getUrl());
                }
                checkSum();
                ProductCommendFragment.name = productDetailBean.getName();
                if (productDetailBean.getImgs().size() > 0) {
                    ProductCommendFragment.productPic = productDetailBean.getImgs().get(0);
                }
                if (mContext instanceof ProductDetailActivity) {
                    if (detailBean.getData().getFlashPromotion()!=null)productDetailBean.setFlash(true);
                    if (detailBean.getData().getCrowdItem() != null) {
                        ((ProductDetailActivity) mContext).setProduct(detailBean.getData().getProduct(), detailBean.getData().getBrand().getId(), detailBean.getData().getCrowdItem().getId());
                    } else {
                        ((ProductDetailActivity) mContext).setProduct(detailBean.getData().getProduct(), detailBean.getData().getBrand().getId(), 0);
                    }
                }
                setFitting(detailBean);
                if (detailBean.getData().getProduct().getImgs().size() > 0) {
                    int imgSize = detailBean.getData().getProduct().getImgs().size();
                    bannerItems.clear();
                    for (int i = 0; i < imgSize; i++) {
                        bannerItems.add(detailBean.getData().getProduct().getImgs().get(i));
                    }
                    setBanner();

                    productInfoBinder = new ProductInfoBinder(mContext, detailBean);
                    /*if (productInfoBinder==null){
                    }else {
                        productInfoBinder.hasCombProduct(detailBean.getData().getProductComb()!=null&&detailBean.getData().getProductComb().size()>0);
                    }*/
                    LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
                    //linearLayoutHelper.setMarginTop(-ScreenUtil.dip2px(38));
                    productInfoAdapter = new BaseVirtualAdapter<>(productInfoBinder, linearLayoutHelper, 2);
                    delegateAdapter.addAdapter(productInfoAdapter);
                    delegateAdapter.notifyItemChanged(1);

                    ProductWebBinder webBinder = new ProductWebBinder(mContext, id);
                    webAdapter = new BaseVirtualAdapter<>(webBinder, 3);
                    delegateAdapter.addAdapter(webAdapter);
                }
                if ("CROSS".equals(productDetailBean.getProductTradeType())){
                    loadAd(true);
                }else {
                    loadAd(false);
                }
                upLoadBehaviorEvent(detailBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!isVisibleToUser){
                    return;
                }
                if (isReFresh && ptr != null&&mContext!=null) {
                    ptr.refreshComplete();
                    Util.showToast(mContext, Util.checkErrorType(error));
                }
            }
        });
    }

    private void loadCollageData(){
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.COLLAGE_DETAIL, collageId));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CollageProductDetailBean>() {
            @Override
            public void onResponse(CollageProductDetailBean detailBean) {
                if (!isVisibleToUser||mContext==null) {
                    return;
                }
                if (isReFresh && ptr != null) {
                    ptr.refreshComplete();
                }
                productDetailBean = detailBean.getData().getProduct();
                if (detailBean.getData().getCollagePromotion()!=null){
                    productDetailBean.setCollageNum(detailBean.getData().getCollagePromotion().getMemberCount());
                    productDetailBean.setCollageId(detailBean.getData().getCollagePromotion().getId());
                    productDetailBean.setPromotionStatus(detailBean.getData().getCollagePromotion().getPromotionStatus());
                    productDetailBean.setCollageStore(getCollageStore(detailBean.getData().getSkuList()));
                    productDetailBean.setCollageName(detailBean.getData().getCollagePromotion().getName());
                }
                if (detailBean.getData().getGroupList()!=null&&detailBean.getData().getGroupList().size()>0){
                    productDetailBean.setHasGroup(true);
                }
                checkSum();
                ProductCommendFragment.name = productDetailBean.getName();
                if (productDetailBean.getImgs().size() > 0) {
                    ProductCommendFragment.productPic = productDetailBean.getImgs().get(0);
                }
                if (mContext instanceof ProductDetailActivity) {
                    ((ProductDetailActivity) mContext).setProduct(detailBean.getData().getProduct(), 0, 0);
                }
                if (detailBean.getData().getProduct().getImgs().size() > 0) {
                    int imgSize = detailBean.getData().getProduct().getImgs().size();
                    bannerItems.clear();
                    for (int i = 0; i < imgSize; i++) {
                        bannerItems.add(detailBean.getData().getProduct().getImgs().get(i));
                    }
                    setBanner();

                    packProductInfoBinder = new PackProductInfoBinder(mContext, detailBean);
                    LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
                    linearLayoutHelper.setMarginTop(-ScreenUtil.dip2px(18));
                    productInfoAdapter = new BaseVirtualAdapter<>(packProductInfoBinder, linearLayoutHelper, 2);
                    delegateAdapter.addAdapter(productInfoAdapter);

                    ProductWebBinder webBinder = new ProductWebBinder(mContext, id);
                    webAdapter = new BaseVirtualAdapter<>(webBinder, 3);
                    delegateAdapter.addAdapter(webAdapter);
                }
                if ("CROSS".equals(productDetailBean.getProductTradeType())){
                    loadAd(true);
                }else {
                    loadAd(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!isVisibleToUser){
                    return;
                }
                if (isReFresh && ptr != null) {
                    ptr.refreshComplete();
                }
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    private int getCollageStore(List<CollageProductDetailBean.DataBean.SkuListBean> skuList) {
        int store=0;
        if (skuList==null||skuList.size()==0){
            return 0;
        }
        int size=skuList.size();
        for (int i=0;i<size;i++){
            if (store>0){
                return 1;
            }
            store+=skuList.get(i).getFlashStore();
        }
        return store;
    }

    private void upLoadBehaviorEvent(ProductDetailBean detailBean) {
        String url = Constants.API_URL + Constants.POST_BEHAVIOR_EVENT_URL;
        JSONObject tmpObj = null;
        tmpObj=new JSONObject();
        JSONObject data = null;
        data=new JSONObject();
        try {
            tmpObj.put("event" , "打开商品详情");
            data.put("targetId" , detailBean.getData().getProduct().getId());
            data.put("targetName" , detailBean.getData().getProduct().getName());
            data.put("targetImg" , detailBean.getData().getProduct().getImg());
            tmpObj.put("data",data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String personInfos = tmpObj.toString(); // 将JSONArray转换得到String
        Charset chrutf = Charset.forName("UTF-8");
        String params = new String(personInfos.getBytes(), chrutf);
        try {
            HttpUtils.doPostAsyn(url,params, new HttpUtils.CallBack() {
                @Override
                public void onRequestComplete(final String result) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkSum(){
        long partnerId=0;
        if (Session.getInstance().getUserFromFile(mContext)!=null){
            partnerId=Session.getInstance().getUserFromFile(mContext).getPartnerId();
        }
        if (partnerId>0){
            SimpleApi api=new SimpleApi();
            api.setInterPath("/v3/api/product/detail/"+id);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<SumBean>() {
                @Override
                public void onResponse(SumBean response) {
                    if (!Util.isEmpty(response.getData().getProductData().getSummary())){
                        String summary=response.getData().getProductData().getSummary();
                        try {
                            JSONObject jsonObject = new JSONObject(summary);
                            JSONArray img=jsonObject.optJSONArray("summaryImg");
                            JSONArray text=jsonObject.optJSONArray("summaryText");
                            JSONArray video=jsonObject.optJSONArray("summaryVideo");
                            if ((img!=null&&img.length()>0)||(text!=null&&text.length()>0)||(video!=null&&video.length()>0)){
                                if (collageId>0){
                                    packProductInfoBinder.hasSum=true;
                                }else {
                                    productInfoBinder.hasSum=true;
                                }
                                productInfoAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    private void loadAd(boolean is) {
        SimpleApi api = new SimpleApi();
        if (is){
            api.setInterPath(Constants.GET_PRODUCT_CROSS_AD);
        }else {
            api.setInterPath(Constants.GET_PRODUCT_AD_REPORT);
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<AdShareBean>() {
            @Override
            public void onResponse(AdShareBean response) {
                if (response.getData().getAdResource() != null && !Util.isEmpty(response.getData().getAdResource().getPic())) {
                    adResourceBean = response.getData().getAdResource();
                }
            }
        });
    }

    private void setBanner() {
        int width = ScreenUtil.getDisplayWidth();
        bannerBinder= new VideoBannerBinder(mContext,getRequestManager(), R.mipmap.ic_logo_empty5, bannerItems);
        bannerBinder.needSetBottom(true);
        bannerBinder.setVideoUrl(productDetailBean.getVideo());
        bannerBinder.setShowNewYear(productDetailBean.isSpot());
        bannerBinder.setCollageId(collageId>0?0:productDetailBean.getCollageId());
        bannerBinder.setProductId(productDetailBean.getId());
        bannerBinder.setCollagePrice(productDetailBean.getCollagePrice());
        bannerBinder.setContentStr(getPressCustomContent());
        bannerBinder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClick(View v, int position) {
                Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                Bundle bundle = new Bundle();
                ArrayList<ImageInfo> imageInfos = new ArrayList<>();
                List<String> imgList = productDetailBean.getImgs();
                if (imgList != null) {
                    for (String picUrl : imgList) {
                        ImageInfo info = new ImageInfo();
                        info.isProduct = true;
                        long partnerId=0;
                        if (Session.getInstance().getUserFromFile(mContext)!=null){
                            partnerId=Session.getInstance().getUserFromFile(mContext).getPartnerId();
                        }
                        if (partnerId>0){
                            info.productUrl=Constants.SHARE_URL +"/product/"+id+"?parent_id="+partnerId;
                        }else {
                            info.productUrl = Constants.SHARE_URL + "/product/" + id;
                        }
                        info.setBigImageUrl(Util.getD2cPicUrl(picUrl));//大图
                        imageInfos.add(info);
                    }
                }
                bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfos);
                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, position);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
            }
        });
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        if (productDetailBean.getPicStyle()==1){
            singleLayoutHelper.setAspectRatio((float)1);
        }else {
            singleLayoutHelper.setAspectRatio(width / (float) ScreenUtil.dip2px(480-18));
        }
        bannerAdapter = new BaseVirtualAdapter<>(bannerBinder, singleLayoutHelper, 1);

        delegateAdapter.addAdapter(bannerAdapter);
    }

    private String getPressCustomContent(){
        String result="";
        if (productDetailBean.getEstimateDate() != null || productDetailBean.getEstimateDay() > 0) {
            if ("PRESELL".equals(productDetailBean.getProductSellType())) {
                if(productDetailBean.getEstimateDay()>0){
                    result="预售商品,预计"+productDetailBean.getEstimateDay()+"天后发货";
                }else if (productDetailBean.getEstimateDate()!=null){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(productDetailBean.getEstimateDate());
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    result="预售商品,预计" + year + "-" + addZero(month) + "-" + addZero(day) + "发货";
                }
            } else if ("CUSTOM".equals(productDetailBean.getProductSellType())) {
                if(productDetailBean.getEstimateDay()>0){
                    result="定制商品,预计"+productDetailBean.getEstimateDay()+"天后发货";
                }else if (productDetailBean.getEstimateDate()!=null){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(productDetailBean.getEstimateDate());
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    result="定制商品,预计" + year + "-" + addZero(month) + "-" + addZero(day) + "发货";
                }
            }
        }
        return result;
    }

    private String addZero(int num) {
        if (num < 10) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }

    private void setFitting(ProductDetailBean productDetailBean) {
        boolean isFit;
        if (productDetailBean.getData().getBrand() != null) {
            if (productDetailBean.getData().getBrand().getIsSubscribe() && productDetailBean.getData().getProduct().getIsSubscribe()) {
                isFit = true;
            } else {
                isFit = false;
            }
        } else {
            if (productDetailBean.getData().getProduct().getIsSubscribe()) {
                isFit = true;
            } else {
                isFit = false;
            }
        }
        if (mContext instanceof ProductDetailActivity) {
            if (isFit) {
                ((ProductDetailActivity) mContext).changeBottom(true);
            }else {
                if (productDetailBean.getData().getBrand()!=null&&!Util.isEmpty(productDetailBean.getData().getBrand().getMark())&&productDetailBean.getData().getBrand().getMark().equals("0")){
                    ((ProductDetailActivity) mContext).changeBottom(false);
                }
            }
        }
    }

    public void changeCollectState(){
        productInfoBinder.changeCollectState();
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
    @Subscribe
    public void onEvent(ActionBean bean) {
        if (bean.type == Constants.ActionType.SELECTSTANDARD) {
            String str= (String) bean.get("standard");
            productInfoBinder.setStandardInfo(str);
        }
    }

    public void refresh() {
        if (bannerAdapter != null) {
            if (bannerBinder!=null){
                bannerBinder=null;
            }
            delegateAdapter.removeAdapter(bannerAdapter);
        }
        if (productInfoAdapter != null) {
            if (productInfoBinder!=null){
                productInfoBinder=null;
            }
            if (packProductInfoBinder!=null){
                packProductInfoBinder=null;
            }
            delegateAdapter.removeAdapter(productInfoAdapter);
            productInfoAdapter = null;
            delegateAdapter.removeAdapter(webAdapter);
            webAdapter = null;
        }
        if (commentAdapter!=null){
            delegateAdapter.removeAdapter(commentAdapter);
            commentList.clear();
        }
        if (showAdapter!=null) {
            delegateAdapter.removeAdapter(showAdapter);
            showAdapter = null;
            showList.clear();
        }
        if (consultList.size() > 0) {
            delegateAdapter.removeAdapter(consultAdapter);
            consultAdapter = null;
            consultList.clear();
        }
        if (relationList.size() > 0) {
            delegateAdapter.removeAdapter(relationTitleAdapter);
            delegateAdapter.removeAdapter(relationAdapter);
            relationList.clear();
            relationTitleAdapter = null;
            relationAdapter = null;
        }
        if (productRecommendAdapter !=null) {
            delegateAdapter.removeAdapter(recommendTitleAdapter);
            delegateAdapter.removeAdapter(productRecommendAdapter);
            recommendList.clear();
            recommendTitleAdapter = null;
            productRecommendAdapter = null;
        }
        isReFresh = true;
        isLoadShow = false;
        if (collageId>0){
            loadCollageData();
        }else {
            loadData();
        }
    }

    @Override
    public void releaseOnInVisible() {
        if (delegateAdapter==null)return;
        delegateAdapter.removeAdapter(bannerAdapter);
        if (productInfoAdapter != null) {
            delegateAdapter.removeAdapter(productInfoAdapter);
            delegateAdapter.removeAdapter(webAdapter);
        }
        if (showAdapter!=null) {
            delegateAdapter.removeAdapter(showAdapter);
        }
        if (commentAdapter!=null){
            delegateAdapter.removeAdapter(commentAdapter);
        }
        if (consultList.size() > 0) {
            delegateAdapter.removeAdapter(consultAdapter);
        }
        if (relationList.size() > 0) {
            delegateAdapter.removeAdapter(relationTitleAdapter);
            delegateAdapter.removeAdapter(relationAdapter);
        }
        if (productRecommendAdapter !=null) {
            delegateAdapter.removeAdapter(recommendTitleAdapter);
            delegateAdapter.removeAdapter(productRecommendAdapter);
        }
        hasSetAdapter = false;
    }

    @Override
    public void onDestroyView() {
        if (productInfoBinder!=null){
            productInfoBinder.release();
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
