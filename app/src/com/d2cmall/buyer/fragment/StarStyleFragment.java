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
import com.d2cmall.buyer.adapter.StarStyleAdapter;
import com.d2cmall.buyer.api.StarStyleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.StarStyleBean;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
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
 * 明星风范fragment
 */
public class StarStyleFragment extends BaseFragment {

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

    private StarStyleAdapter mStarStyleAdapter;

    private int lastPosition = -1;
    private int lastOffer;
    private boolean hasNext=true;
    private int p=1;
    private boolean hasSetAdapter; //是否设置过设配器
    private int tagId;
    private List<StarStyleBean.DataBean.ProductsBean.ListBean> starStlyeBeanList;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志
    public static StarStyleFragment newInstance(int tagId, boolean hasHead, boolean hasFocus) {
        StarStyleFragment topRecommendFragment = new StarStyleFragment();
        Bundle args = new Bundle();
        args.putInt("tagId", tagId);
        topRecommendFragment.setArguments(args);
        return topRecommendFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            tagId = getArguments().getInt("tagId");
        }
        starStlyeBeanList =new ArrayList<>();//需要缓存的数据在此处实例
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
        if(mStarStyleAdapter==null ) {
            int itemWidth= (ScreenUtil.getDisplayWidth()-ScreenUtil.dip2px(48))/2;
            mStarStyleAdapter = new StarStyleAdapter(getActivity(), starStlyeBeanList, itemWidth);
        }
        delegateAdapter.addAdapter(mStarStyleAdapter);
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
        if (starStlyeBeanList.size() == 0) {
            progressBar.setVisibility(View.VISIBLE);
            loadData();
        } else {
            if (!hasSetAdapter) {
                if ( mStarStyleAdapter != null) {
                    delegateAdapter.addAdapter(mStarStyleAdapter);
                }
                if (lastPosition >= 0) {
                    virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
                }
                hasSetAdapter = true;
            }
        }
    }


    private void loadData() {
        StarStyleApi api = new StarStyleApi();
        api.setPageSize(20);
        api.setPageNumber(p);
        api.setTagId(tagId);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<StarStyleBean>() {
            @Override
            public void onResponse(StarStyleBean starStyleBean) {
                progressBar.setVisibility(View.GONE);
                ptr.refreshComplete();
                if(starStyleBean==null){
                    return;
                }
                hasNext=starStyleBean.getData().getProducts().isNext();
                if (starStyleBean.getData().getProducts().getList().size() > 0) {
                    if(p==1 ) {
                        starStlyeBeanList.clear();
                    }
                    if (starStyleBean != null && starStyleBean.getData().getProducts().getList().size() > 0) {
                        starStlyeBeanList.addAll(starStyleBean.getData().getProducts().getList());
                    }
                    if (mStarStyleAdapter != null) {
                        mStarStyleAdapter.notifyDataSetChanged();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    public void refresh() {
        p = 1;
        loadData();
    }

    @Override
    public void releaseOnInVisible() {
        if (delegateAdapter != null) {
            if (mStarStyleAdapter != null) {
                delegateAdapter.removeAdapter(mStarStyleAdapter);
            }
            delegateAdapter.removeAdapter(mStarStyleAdapter);
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
        EventBus.getDefault().unregister(this);
        mStarStyleAdapter = null;
        super.onDestroyView();
    }
}
