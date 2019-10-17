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
import com.d2cmall.buyer.adapter.WardrobeAdapter;
import com.d2cmall.buyer.api.WardrobeListApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.WardrobeListItemBean;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

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
 * LWJ
 * 我的衣橱列表fragment
 */
public class WardrobeListFragment extends BaseFragment {

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

    private WardrobeAdapter wardrobeAdapter;

    private int lastPosition = -1;
    private int lastOffer;
    private boolean hasNext = true;
    private int p = 1;
    private boolean hasSetAdapter; //是否设置过设配器


    private String categoryName;
    private String topName;
    private List<WardrobeListItemBean.DataBean.MyWardrobesBean.ListBean> wardrobeList;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志

    public static WardrobeListFragment newInstance(String categoryName,String topName, boolean hasHead, boolean hasFocus) {
        WardrobeListFragment topRecommendFragment = new WardrobeListFragment();
        Bundle args = new Bundle();
        args.putString("categoryName", categoryName);
        args.putString("topName", topName);
        topRecommendFragment.setArguments(args);
        return topRecommendFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            categoryName = getArguments().getString("categoryName");
            topName = getArguments().getString("topName");
        }
        EventBus.getDefault().register(this);
        wardrobeList = new ArrayList<>();//需要缓存的数据在此处实例
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_starstyle_recycleview, container, false);
    }

    @Override
    public void prepareView() {
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
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0, 3);
        recycleView.setRecycledViewPool(recycledViewPool);
        if (wardrobeAdapter == null) {
            int itemWidth = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(24)) / 2;
            wardrobeAdapter = new WardrobeAdapter(getActivity(), itemWidth, wardrobeList);
        }
        delegateAdapter.addAdapter(wardrobeAdapter);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        if (lastPosition >= 0) {
            virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
        }
        initListener();
        hasSetAdapter = true;
    }

    private void initListener() {
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisPosition = virtualLayoutManager.findLastVisibleItemPosition();
                int itemCount = virtualLayoutManager.getItemCount();
                if (lastVisPosition >= itemCount - 3 && !hasNext && p > 1) {
                    if (endAdapter == null) {
                        ScrollEndBinder endBinder = new ScrollEndBinder();
                        endAdapter = new BaseVirtualAdapter<>(endBinder, 100);
                        delegateAdapter.addAdapter(endAdapter);
                    }
                } else {
                    if (endAdapter != null) {
                        delegateAdapter.removeAdapter(endAdapter);
                        endAdapter = null;
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
        if (wardrobeList.size() == 0) {
            progressBar.setVisibility(View.VISIBLE);
            loadData();
        } else {
            if (!hasSetAdapter) {
                if (wardrobeAdapter != null) {
                    delegateAdapter.addAdapter(wardrobeAdapter);
                }
                if (lastPosition >= 0) {
                    virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
                }
                hasSetAdapter = true;
            }
        }
    }


    private void loadData() {
        recycleView.setBackgroundColor(getResources().getColor(R.color.bg_color));
        WardrobeListApi api = new WardrobeListApi();
        api.setPageSize(20);
        api.setPageNumber(p);
        if(!Util.isEmpty(categoryName) && "全部".equals(categoryName)){
            api.setTopName(topName);
        }else{
            api.setTopName(topName);
            api.setCategoryName(categoryName);
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<WardrobeListItemBean>() {
            @Override
            public void onResponse(WardrobeListItemBean starStyleBean) {
                if(mContext==null || progressBar==null){
                    return;
                }
                progressBar.setVisibility(View.GONE);
                ptr.refreshComplete();
                if (starStyleBean == null) {
                    return;
                }
                hasNext = starStyleBean.getData().getMyWardrobes().isNext();
                if (p == 1) {
                    wardrobeList.clear();
                }
                if (starStyleBean.getData().getMyWardrobes().getList().size() > 0) {
                    if (starStyleBean != null && starStyleBean.getData().getMyWardrobes().getList().size() > 0) {
                        wardrobeList.addAll(starStyleBean.getData().getMyWardrobes().getList());
                    }
                } else {
                    setEmptyView(Constants.NO_DATA);
                }
                if (wardrobeAdapter != null) {
                    wardrobeAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                setEmptyView(Constants.NET_DISCONNECT);
            }
        });
    }

    private void setEmptyView(int type) {
        recycleView.setBackgroundColor(getResources().getColor(R.color.color_white));
        if (type == Constants.NO_DATA) {
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.icon_empty_default);
            btnReload.setVisibility(View.VISIBLE);
            btnReload.setText("暂无数据");
            btnReload.setBackgroundColor(getResources().getColor(R.color.transparent));
        } else {
            btnReload.setText("重新加载");
            btnReload.setBackgroundResource(R.drawable.sp_line);
            btnReload.setVisibility(View.VISIBLE);
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.ic_no_net);
        }

    }

    public void refresh() {
        p = 1;
        loadData();
    }


    @Override
    public void releaseOnInVisible() {
        if (delegateAdapter != null) {
            if (wardrobeAdapter != null) {
                delegateAdapter.removeAdapter(wardrobeAdapter);
            }
            delegateAdapter.removeAdapter(wardrobeAdapter);
            hasSetAdapter = false;
        }
    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(GlobalTypeBean bean) {
        if (bean.getType() == Constants.GlobalType.WARDROBE_DELETE) {
            refresh();
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
