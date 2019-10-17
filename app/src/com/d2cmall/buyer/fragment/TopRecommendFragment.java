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
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.TopRecommendAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.TopRecommendBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * LWJ
 * 榜单推荐fragment
 */
public class TopRecommendFragment extends BaseFragment {

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

    private TopRecommendAdapter mTopRecommendAdapter;

    private int lastPosition = -1;
    private int lastOffer;
    private boolean hasNext;
    private boolean isFresh;
    private int p;
    private boolean hasSetAdapter; //是否设置过设配器
    private int mCategoryId;
    private List<TopRecommendBean.ListBean> mTopRecommendBeanList;

    public static TopRecommendFragment newInstance(int categoryId, boolean hasHead, boolean hasFocus) {
        TopRecommendFragment topRecommendFragment = new TopRecommendFragment();
        Bundle args = new Bundle();
        args.putInt("categoryIds", categoryId);
        args.putBoolean("hasHead", hasHead);
        args.putBoolean("hasFocus", hasFocus);
        topRecommendFragment.setArguments(args);
        return topRecommendFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCategoryId = getArguments().getInt("categoryIds");
        }
        mTopRecommendBeanList=new ArrayList<>();//需要缓存的数据在此处实例
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
        recycledViewPool.setMaxRecycledViews(0,3);
        recycleView.setRecycledViewPool(recycledViewPool);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        initListener();
    }

    private void initListener(){
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
        if (mTopRecommendBeanList.size() == 0) {
            progressBar.setVisibility(View.VISIBLE);
            loadData();
        } else {
            if (!hasSetAdapter) {
                if ( mTopRecommendAdapter != null) {
                    delegateAdapter.addAdapter(mTopRecommendAdapter);
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
        String format = String.format("/v3/api/similar/category/%d/top/16", mCategoryId);
        api.setInterPath(format);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<TopRecommendBean>() {
            @Override
            public void onResponse(TopRecommendBean topRecommendBean) {
                if (!isVisibleToUser){
                    return;
                }
                progressBar.setVisibility(View.GONE);
                ptr.refreshComplete();
                if (topRecommendBean.getList().size() > 0) {
                    if(mTopRecommendBeanList!=null&&mTopRecommendBeanList.size()>0) {
                        mTopRecommendBeanList.clear();
                    }
                    if (topRecommendBean != null && topRecommendBean.getList().size() > 0) {
                        mTopRecommendBeanList.addAll(topRecommendBean.getList());
                    }
                    setAdapter();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ptr.refreshComplete();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setAdapter(){
        if (mTopRecommendAdapter==null){
            int itemWidth= (ScreenUtil.getDisplayWidth()-ScreenUtil.dip2px(48))/2;
            mTopRecommendAdapter = new TopRecommendAdapter(getActivity(),mTopRecommendBeanList, itemWidth);
            delegateAdapter.addAdapter(mTopRecommendAdapter);
        }else {
            mTopRecommendAdapter.notifyDataSetChanged();
        }
    }

    public void refresh() {
        p = 1;
        loadData();
        isFresh=true;
    }

    @Override
    public void releaseOnInVisible() {
        if (delegateAdapter != null) {
            if (mTopRecommendAdapter != null) {
                delegateAdapter.removeAdapter(mTopRecommendAdapter);
            }
            hasSetAdapter = false;
        }
    }

    @Override
    public void onDestroyView() {
        virtualLayoutManager=null;
        delegateAdapter=null;
        super.onDestroyView();
    }

}
