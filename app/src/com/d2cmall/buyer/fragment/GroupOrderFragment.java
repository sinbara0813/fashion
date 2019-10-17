package com.d2cmall.buyer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.GroupOrderAdapter;
import com.d2cmall.buyer.api.MineGroupApi;
import com.d2cmall.buyer.base.*;
import com.d2cmall.buyer.bean.GroupOrderListBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by rookie on 2018/6/21.
 */

public class GroupOrderFragment extends com.d2cmall.buyer.base.BaseFragment {


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
    @Bind(R.id.btn_back_top)
    ImageView btnBackTop;
    @Bind(R.id.rl_layout)
    RelativeLayout rlLayout;
    private GroupOrderAdapter groupOrderAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int status;
    private int page = 1;
    private boolean hasNext;
    private List<GroupOrderListBean.DataBean.CollageOrders.ListBean> list;

    public static GroupOrderFragment newInstance(int index) {
        GroupOrderFragment groupOrderFragment = new GroupOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("status", index);
        groupOrderFragment.setArguments(bundle);
        return groupOrderFragment;
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    public void prepareView() {
        status = getArguments().getInt("status");
        list = new ArrayList<>();
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
        groupOrderAdapter = new GroupOrderAdapter(mContext, list);
        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recycleView.setAdapter(groupOrderAdapter);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int last = linearLayoutManager.findLastVisibleItemPosition();
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        if (last > groupOrderAdapter.getItemCount() - 3 && hasNext) {
                            page++;
                            loadData();
                        }
                }
            }
        });
    }

    private void refresh() {
        page = 1;
        loadData();
    }

    private void loadData() {
        MineGroupApi api = new MineGroupApi();
        api.setPageNumber(page);
        api.setPageSize(20);
        api.setIndex(status);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<GroupOrderListBean>() {
            @Override
            public void onResponse(GroupOrderListBean response) {
                if (ptr != null) {
                    ptr.refreshComplete();
                } else {
                    return;
                }
                hasNext = response.getData().getMyCollageList().isNext();
                if (page == 1) {
                    list.clear();
                }

                list.addAll(response.getData().getMyCollageList().getList());
                progressBar.setVisibility(View.GONE);
                if (list.size() == 0) {//没有数据
                    rlLayout.setBackgroundColor(getResources().getColor(R.color.color_white));
                    recycleView.setVisibility(View.GONE);
                    imgHint.setVisibility(View.VISIBLE);
                    imgHint.setImageResource(R.mipmap.ic_empty_order);
                } else {
                    rlLayout.setBackgroundColor(getResources().getColor(R.color.bg_white));
                    recycleView.setVisibility(View.VISIBLE);
                    imgHint.setVisibility(View.GONE);
                }
                groupOrderAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ptr.refreshComplete();
                progressBar.setVisibility(View.GONE);
                if (list.size() == 0) {//没有数据
                    recycleView.setVisibility(View.GONE);
                    imgHint.setVisibility(View.VISIBLE);
                    imgHint.setImageResource(R.mipmap.ic_no_net);
                }
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    @Override
    public void onDestroyView() {
        if (groupOrderAdapter != null) {
            groupOrderAdapter.destroy();
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void doBusiness() {
        if (list.size() == 0) {
            progressBar.setVisibility(View.VISIBLE);
            loadData();
        }
    }

}
