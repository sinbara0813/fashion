package com.d2cmall.buyer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
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
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.BrandShowAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.BrandShowBean;
import com.d2cmall.buyer.holder.ShowItemHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayer;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static com.d2cmall.buyer.Constants.BRAND_SHOW_URL;

/**
 * Created by rookie on 2017/9/7.
 * 店铺买家秀Fragment
 */

public class BrandFashionFragment extends BaseFragment {


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
    @Bind(R.id.nest_scroll)
    NestedScrollView nest_scroll;
    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;
    private BrandShowAdapter showAdapter;
    private List<BrandShowBean.DataBean.MembersharesBean.ListBean> showList;
    private long id;
    private int p = 1;
    private boolean hasNext;

    private boolean hasSetAdapter;

    public static BrandFashionFragment newInstance(long id) {
        BrandFashionFragment brandFashionFragment = new BrandFashionFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        brandFashionFragment.setArguments(bundle);
        return brandFashionFragment;
    }


    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_fashion_brand, container, false);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
        }
        return view;
    }

    @Override
    public void prepareView() {
        showList = new ArrayList<>();
        ptr.setHeadLabel(getString(R.string.label_d2c_go));
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return false;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }
        });
        recycleView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                if (holder instanceof ShowItemHolder) {
                    ShowItemHolder showItemHolder = (ShowItemHolder) holder;
                    NiceVideoPlayer niceVideoPlayer = showItemHolder.niceVideoPlayer;
                    if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                    }
                }
            }
        });
        virtualLayoutManager = new VirtualLayoutManager(mContext);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        showAdapter = new BrandShowAdapter(mContext, showList);
        delegateAdapter.addAdapter(showAdapter);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = virtualLayoutManager.findLastVisibleItemPosition();
                        if (last > delegateAdapter.getItemCount() - 3 && hasNext) {
                            p++;
                            loadData();
                        }
                }
            }
        });
        hasSetAdapter = true;
    }

    private void loadData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(BRAND_SHOW_URL, id));
        api.setP(p);
        api.setPageSize(20);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BrandShowBean>() {
            @Override
            public void onResponse(BrandShowBean showListBean) {
                if(!isVisibleToUser){
                    return;
                }
                if( ptr!=null){
                    ptr.refreshComplete();
                }

                if (p == 1) {
                    showList.clear();
                }
                if (showListBean.getData().getMembershares() != null) {
                    hasNext = showListBean.getData().getMembershares().isNext();
                }
                if (showListBean.getData().getMembershares() != null && showListBean.getData().getMembershares().getList() != null && showListBean.getData().getMembershares().getList().size() > 0) {
                    showList.addAll(showListBean.getData().getMembershares().getList());
                }
                if (showListBean.getData().getMembershares() == null || showListBean.getData().getMembershares().getList().size() == 0 || showListBean.getData().getMembershares().getList() == null) {
                    recycleView.setVisibility(View.GONE);
                    imgHint.setVisibility(View.VISIBLE);
                    nest_scroll.setVisibility(View.VISIBLE);
                    imgHint.setImageResource(R.mipmap.ic_empty_my_show);
                } else {
                    recycleView.setVisibility(View.VISIBLE);
                    imgHint.setVisibility(View.GONE);
                    nest_scroll.setVisibility(View.GONE);
                }
                showAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (ptr != null) {
                    ptr.refreshComplete();
                }
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    public void refresh() {
        p = 1;
        loadData();
    }

    @Override
    public void doBusiness() {
        if (showList!=null&&showList.size()==0){
            loadData();
        }
    }

}
