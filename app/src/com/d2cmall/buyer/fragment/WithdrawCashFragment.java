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
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.ProductReportAdapter;
import com.d2cmall.buyer.api.ProductReportListApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.ProductReportBean;
import com.d2cmall.buyer.bean.UnFocusMemberBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.holder.ShowItemHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayer;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/21 13:39
 * Copyright (c) 2016 d2cmall. All rights reserved.
 * 商品报告
 */
public class WithdrawCashFragment extends BaseFragment {

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

    private List<ProductReportBean.DataBean.ReportBean.ListBean> reportList;
    public List<UnFocusMemberBean.DataBean.ActiveMemberBean> unFocusBean;
    private ProductReportAdapter mProductReportAdapter;

    private int lastPosition = -1;
    private int lastOffer;

    private long id; //买家秀的tagId
    private int p = 1;
    private boolean hasNext; //是否有下一页数据
    private boolean isFresh;

    private boolean hasSetAdapter; //是否设置过设配器
    private UserBean.DataEntity.MemberEntity mUser;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志

    public static WithdrawCashFragment newInstance(long id) {
        WithdrawCashFragment productReportFragment = new WithdrawCashFragment();
        Bundle args = new Bundle();
        args.putLong("id", id);
        productReportFragment.setArguments(args);
        return productReportFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
        }
        reportList = new ArrayList<>(); //需要缓存的数据在此处实例
        unFocusBean=new ArrayList<>();
        EventBus.getDefault().register(this);
}

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_refresh_recycleview, container, false);
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
        virtualLayoutManager = new VirtualLayoutManager(getActivity());
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0,2);
        recycleView.setRecycledViewPool(recycledViewPool);
        mProductReportAdapter = new ProductReportAdapter(getActivity(), reportList,id);
        if (unFocusBean!=null&&unFocusBean.size()>0){
            mProductReportAdapter.unFocusBean=unFocusBean;
        }
        delegateAdapter.addAdapter(mProductReportAdapter);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        if (lastPosition >= 0) {
            virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
        }
        initListener();
        hasSetAdapter = true;
    }

    private void initListener(){
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisPosition=virtualLayoutManager.findLastVisibleItemPosition();
                int itemCount=virtualLayoutManager.getItemCount();
                if (lastVisPosition>=itemCount-3 && !hasNext && p>1){
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

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                getLastLocation();
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
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgHint.setVisibility(View.GONE);
                btnReload.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                loadData();
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
        if (reportList.size() == 0) {
            progressBar.setVisibility(View.VISIBLE);
            loadData();
        } else {
            if (!hasSetAdapter) {
                delegateAdapter.addAdapter(mProductReportAdapter);
                mProductReportAdapter.notifyDataSetChanged();
                if (lastPosition >= 0) {
                    virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
                }
                hasSetAdapter = true;
            }
        }
    }



    private void loadData() {

        ProductReportListApi api = new ProductReportListApi();
        if (Session.getInstance().getUserFromFile(getContext())==null){
            return;
        }else{
            mUser = Session.getInstance().getUserFromFile(getContext());
        }
        if(mUser!=null) {
            api.setMemberId(mUser.getMemberId());
        }

        api.setStatus((int) id);
        api.setP(p);
        api.setPageSize(10);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ProductReportBean>() {
            @Override
            public void onResponse(ProductReportBean reportBean) {
                if (!isVisibleToUser){
                    return;
                }
                progressBar.setVisibility(View.GONE);
                if (isFresh){
                    ptr.refreshComplete();
                }
                if (p == 1) {
                    reportList.clear();
                }
                hasNext = reportBean.getData().getReport().isNext();
                if(reportBean.getData().getReport()!=null&&reportBean.getData().getReport().getList().size()>0) {
                    imgHint.setVisibility(View.GONE);
                    imgHint.setImageResource(R.mipmap.ic_no_net);
                    recycleView.setVisibility(View.VISIBLE);
                    btnReload.setVisibility(View.GONE);
                    reportList.addAll(reportBean.getData().getReport().getList());
                    mProductReportAdapter.notifyDataSetChanged();
                }else{
                        imgHint.setVisibility(View.VISIBLE);
                        imgHint.setImageResource(R.mipmap.icon_empty_default);
                        btnReload.setVisibility(View.VISIBLE);
                        btnReload.setText("暂无数据");
                        btnReload.setBackgroundColor(getResources().getColor(R.color.transparent));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                if (isFresh){
                    ptr.refreshComplete();
                }
                btnReload.setText("重新加载");
                btnReload.setBackgroundResource(R.drawable.sp_line);
                btnReload.setVisibility(View.VISIBLE);
                imgHint.setVisibility(View.VISIBLE);
                imgHint.setImageResource(R.mipmap.ic_no_net);
            }
        });
    }


    public void refresh() {
        p = 1;
        loadData();
        isFresh=true;
    }

    @Override
    public void releaseOnInVisible() {
        if (delegateAdapter != null) {
            delegateAdapter.removeAdapter(mProductReportAdapter);
            hasSetAdapter = false;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }


    @Override
    public void onDestroyView() {
        mProductReportAdapter = null;
        delegateAdapter = null;
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(GlobalTypeBean event) {
        int type = event.getType();
        if (type == Constants.GlobalType.PRODUCT_REPORT_COUNT_CHANGE) {
            p=1;
            loadData();
        }
    }
}
