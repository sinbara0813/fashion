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
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.UpdateBrandListAdapter;
import com.d2cmall.buyer.api.UpdateBrandApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.UpdateBrandCategoryListBean;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2018/9/10.
 */

public class BrandUpdateListFragment extends BaseFragment {

    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;

    private VirtualLayoutManager layoutManager;
    private DelegateAdapter delegateAdapter;

    private UpdateBrandListAdapter brandListAdapter;
    private List<UpdateBrandCategoryListBean.DataBean.BrandsBean.ListBean> list=new ArrayList<>();

    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志

    private boolean hasSetAdapter; //是否设置了设配器
    private int lastPosition = -1; //页面location
    private int lastOffer;
    private boolean hasNext;
    private int index = 1;
    private boolean isRefresh;
    private int topId;

    public static BrandUpdateListFragment newInstance(int topId){
        BrandUpdateListFragment fragment=new BrandUpdateListFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("topId",topId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_refresh_recycleview, container, false);
    }

    @Override
    public void prepareView() {
        if (getArguments()!=null){
            topId=getArguments().getInt("topId");
        }
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
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                getLastLocation();
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisPosition = layoutManager.findLastVisibleItemPosition();
                int itemCount = layoutManager.getItemCount();
                if (lastVisPosition > 0 && lastVisPosition > itemCount - 3) {
                    if (hasNext) {
                        index++;
                        loadData();
                    } else {
                        if (endAdapter == null) {
                            ScrollEndBinder endBinder = new ScrollEndBinder();
                            endAdapter = new BaseVirtualAdapter<>(endBinder, 100);
                            delegateAdapter.addAdapter(endAdapter);
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        layoutManager = new VirtualLayoutManager(mContext);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(delegateAdapter);
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

    private void loadData(){
        UpdateBrandApi api=new UpdateBrandApi();
        api.topId=topId;
        api.pageNumber=index;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<UpdateBrandCategoryListBean>() {
            @Override
            public void onResponse(UpdateBrandCategoryListBean response) {
                if (!isVisibleToUser)
                    return;
                if (isRefresh) {
                    isRefresh=false;
                    ptr.refreshComplete();
                }
                if (response.getData().getBrands() != null && response.getData().getBrands().getList() != null && response.getData().getBrands().getList().size() > 0) {
                    hasNext = response.getData().getBrands().isNext();
                    if (index == 1) {
                        list.clear();
                    }
                    list.addAll(response.getData().getBrands().getList());
                    setAdapter();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!isVisibleToUser)
                    return;
                if (isRefresh) {
                    isRefresh=false;
                    ptr.refreshComplete();
                }
            }
        });
    }

    private void setAdapter() {
        if (!isVisibleToUser) return;
        if (brandListAdapter==null){
            brandListAdapter=new UpdateBrandListAdapter(mContext,list);
            delegateAdapter.addAdapter(brandListAdapter);
        }else {
            brandListAdapter.notifyDataSetChanged();
        }
        hasSetAdapter=true;
    }

    private void refresh(){
        index=1;
        if (endAdapter!=null){
            delegateAdapter.removeAdapter(endAdapter);
        }
        isRefresh=true;
        loadData();
    }

    @Override
    public void doBusiness() {
        if (list.size()==0){
            loadData();
        }else {
            if (!hasSetAdapter){
                delegateAdapter.addAdapter(brandListAdapter);
                if (endAdapter!=null){
                    delegateAdapter.addAdapter(endAdapter);
                }
                layoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
                hasSetAdapter=true;
            }
        }
    }

    @Override
    public void releaseOnInVisible() {
        delegateAdapter.removeAdapter(brandListAdapter);
        if (endAdapter!=null){
            delegateAdapter.removeAdapter(endAdapter);
        }
        hasSetAdapter=false;
    }

    @Override
    public void onDestroyView() {
        layoutManager =null;
        delegateAdapter=null;
        super.onDestroyView();
    }
}
