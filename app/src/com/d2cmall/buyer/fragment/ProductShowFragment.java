package com.d2cmall.buyer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.NoShowAdapter;
import com.d2cmall.buyer.adapter.ProductCommentAdapter;
import com.d2cmall.buyer.adapter.ProductShowAdapter;
import com.d2cmall.buyer.api.ComdityCommentListApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.AdShareBean;
import com.d2cmall.buyer.bean.ProductCommentListBean;
import com.d2cmall.buyer.bean.ProductShowBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/5 18:50
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductShowFragment extends BaseFragment implements ProductShowAdapter.ClickListener {

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

    private long id;
    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;
    private ProductShowAdapter showAdapter;
    private List<ProductShowBean.DataEntity.MembersharesEntity.ListEntity> showList;
    private List<ProductCommentListBean.DataEntity.CommentsEntity.ListEntity> commentList;
    private ProductCommentAdapter commentAdapter;
    private int lastPosition = -1;
    private int lastOffer;
    private int showP = 1;
    private int commentP = 1;
    private boolean hasSetAdapter;
    private boolean hasShow;
    private boolean hasComment;
    private boolean isLoad;
    private boolean onlyShow = false;

    private boolean hasPic;  //是否有图买家秀
    private boolean needLoadComment = true;
    private boolean onlyComment;
    private AdShareBean.DataBean.AdResourceBean adResource;
    private NoShowAdapter noShowAdapter;

    public static ProductShowFragment newInstance(long id) {
        ProductShowFragment productShowFragment = new ProductShowFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        productShowFragment.setArguments(bundle);
        return productShowFragment;
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
        }
        showList = new ArrayList<>();
        commentList = new ArrayList<>();
    }

    @Override
    public void prepareView() {
        ptr.setHeadLabel(getString(R.string.label_d2c_go));
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, final View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }
        });
        virtualLayoutManager = new VirtualLayoutManager(getActivity());
        recycleView.setLayoutManager(virtualLayoutManager);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, false);
        recycleView.setAdapter(delegateAdapter);
        showAdapter = new ProductShowAdapter(getActivity(), showList);
        noShowAdapter = new NoShowAdapter(getActivity());//没买家秀有广告位时候空白页显示加在列表里
        showAdapter.setClickListener(this);
        delegateAdapter.addAdapter(showAdapter);
        if (lastPosition >= 0) {
            virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
        }
        initListener();
        hasSetAdapter = true;
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
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoad && virtualLayoutManager.findLastVisibleItemPosition() > 1 && virtualLayoutManager.findLastVisibleItemPosition() > virtualLayoutManager.getItemCount() - 3) {
                    if (hasShow || hasComment) {
                        if (hasShow) {
                            showP++;
                        } else {
                            commentP++;
                        }
                        loadNextData();
                    }
                }
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
        if (showList.size() == 0 && commentList.size() == 0 && !showAdapter.hasAd()) {
            loadAdvert();
        } else {
            if (!hasSetAdapter) {
                delegateAdapter.addAdapter(showAdapter);
                showAdapter.notifyDataSetChanged();
                if (commentAdapter != null) {
                    delegateAdapter.addAdapter(commentAdapter);
                    commentAdapter.notifyDataSetChanged();
                }
                if (showList.size() == 0 && commentList.size() == 0) {
                    if (showAdapter.hasAd()) {
                        if(noShowAdapter!=null){
                            delegateAdapter.addAdapter(noShowAdapter);
                        }
                    }
                }
                if (lastPosition >= 0) {
                    virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
                }
                hasSetAdapter = true;
            }
        }
    }

    private void loadNextData() {
        isLoad = true;
        if (hasShow) {
            loadShow();
        } else {
            if (needLoadComment) {
                if (onlyComment) {
                    loadComment(true);
                } else {
                    loadComment(false);
                }
            }
        }
    }

    //加载广告位
    private void loadAdvert() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GET_PRODUCT_AD_SHARE));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<AdShareBean>() {
            @Override
            public void onResponse(AdShareBean response) {
                if (!isVisibleToUser) {
                    return;
                }
                adResource = response.getData().getAdResource();
                if (adResource != null && !Util.isEmpty(adResource.getPic())) {
                    showAdapter.setAdResource(adResource);
                    showAdapter.notifyDataSetChanged();
                }
                loadShow();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadShow();
            }
        });
    }

    private void loadShow() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GET_PRODUCT_SHOW_LIST, id));
        api.setP(showP);
        api.setPageSize(10);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ProductShowBean>() {
            @Override
            public void onResponse(ProductShowBean response) {
                if (getActivity() == null || getActivity().isFinishing()) {
                    return;
                }
                ptr.refreshComplete();
                isLoad = false;
                hasShow = response.getData().getMembershares().isNext();
                if (response.getData().getMembershares().getList().size() > 0) {
                    if (showP == 1) {
                        showList.clear();
                    }
                    showList.addAll(response.getData().getMembershares().getList());
                    showAdapter.notifyDataSetChanged();
                    if (showList.size() < 10) {
                        loadComment(false);
                    }
                } else {
                    if (showList.size() == 0) {
                        loadComment(false);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ptr.refreshComplete();
                isLoad = false;
                imgHint.setVisibility(View.VISIBLE);
                imgHint.setImageResource(R.mipmap.ic_no_net);
                recycleView.setVisibility(View.GONE);
            }
        });
    }

    private void loadComment(boolean hasPic) {
        ComdityCommentListApi api = new ComdityCommentListApi();
        api.setP(commentP);
        if (hasPic) {
            api.setHasPic(1);
        }
        api.setPageSize(20);
        api.setInterPath(String.format(Constants.GET_PRODUCT_COMMENT_LIST, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ProductCommentListBean>() {
            @Override
            public void onResponse(ProductCommentListBean response) {
                if (!isVisibleToUser) {
                    return;
                }
                if (ptr == null) {
                    return;
                }
                ptr.refreshComplete();
                isLoad = false;
                hasComment = response.getData().getComments().isNext();
                if (response.getData().getComments().getList().size() > 0) {
                    commentList.addAll(response.getData().getComments().getList());
                    if (commentAdapter == null) {
                        commentAdapter = new ProductCommentAdapter(getActivity(), commentList);
                        delegateAdapter.addAdapter(commentAdapter);
                    } else {
                        commentAdapter.notifyDataSetChanged();
                    }
                } else {
                    if (showList.size() == 0 && commentList.size() == 0) {
                        if (!showAdapter.hasAd()) {
                            imgHint.setVisibility(View.VISIBLE);
                            imgHint.setImageResource(R.mipmap.ic_empty_sun);
                            recycleView.setVisibility(View.GONE);
                        } else {
                            if(noShowAdapter!=null){
                                delegateAdapter.addAdapter(noShowAdapter);
                            }
                            emptyHintLayout.setPadding(0, 0, 0, 0);
                            emptyHintLayout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                        }
                    }
                }
                if (commentAdapter != null) {
                    commentAdapter.notifyDataSetChanged();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isLoad = false;
                if (ptr != null) {
                    ptr.refreshComplete();
                }
            }
        });
    }

    public void refresh() {
        if(noShowAdapter!=null){
            delegateAdapter.removeAdapter(noShowAdapter);
        }
        if (commentAdapter != null) {
            delegateAdapter.removeAdapter(commentAdapter);
            commentAdapter = null;
            commentList.clear();
        }
        showP = 1;
        commentP = 1;
        loadShow();
        /*if (!onlyComment){
        }else {
            loadComment(true);
        }*/
    }

    @Override
    public void releaseOnInVisible() {
        delegateAdapter.removeAdapter(showAdapter);
        if(noShowAdapter!=null){
            delegateAdapter.removeAdapter(noShowAdapter);
        }
        if (commentAdapter != null) {
            delegateAdapter.removeAdapter(commentAdapter);
        }
        hasSetAdapter = false;
    }

    @OnClick(R.id.btn_reload)
    public void onViewClicked() {
        refresh();
    }

    @Override
    public void clickAll() {
        needLoadComment = true;
        onlyComment = false;
        if (showAdapter != null) {
            if (showList.size() == 0) {
                if (commentAdapter != null) {
                    delegateAdapter.removeAdapter(commentAdapter);
                    commentAdapter = null;
                    commentList.clear();
                    commentP = 1;
                }
                loadComment(false);
            } else {
                showAdapter.setHideList(false);
                showAdapter.notifyDataSetChanged();
                if (commentAdapter != null) {
                    delegateAdapter.removeAdapter(commentAdapter);
                    commentAdapter = null;
                    commentList.clear();
                    commentP = 1;
                }
                if (showList.size() < 5) {
                    loadComment(false);
                }
            }
        } else {
            loadComment(false);
        }
    }

    @Override
    public void clickHasPic() {
        needLoadComment = true;
        onlyComment = true;
        /*if (showAdapter!=null){
            showAdapter.setHideList(true);
            showAdapter.notifyDataSetChanged();
        }*/
        if (commentAdapter != null) {
            delegateAdapter.removeAdapter(commentAdapter);
            commentAdapter = null;
            commentList.clear();
        }
        commentP = 1;
        loadComment(true);
    }

    @Override
    public void clickOnlyShow() {
        needLoadComment = false;
        onlyComment = false;
        if (showList.size() == 0) {
            loadAdvert();
        } else {
            if (showAdapter != null) {
                showAdapter.setHideList(false);
                showAdapter.notifyDataSetChanged();
            }
            if (commentAdapter != null) {
                delegateAdapter.removeAdapter(commentAdapter);
                commentAdapter = null;
                commentList.clear();
            }
            virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
        }
    }
}
