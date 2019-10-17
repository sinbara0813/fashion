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
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.CouponAdapter;
import com.d2cmall.buyer.api.CouponApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.CouponsBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by rookie on 2017/8/17.
 */

public class CouponFragment extends BaseFragment {

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
    private CouponAdapter couponAdapter;
    private int id;
    private ArrayList<CouponsBean.DataEntity.MyCouponsEntity.ListEntity> listEntities;
    private static final String ARG_PARAM1 = "param1";
    private String status;
    private boolean isEnd;
    private boolean isLoad;
    private int currentPage = 1;
    private boolean isPrepared;
    private boolean hasNext;

    public static CouponFragment newInstance(String status) {
        CouponFragment fragment = new CouponFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coupon, container, false);
        if (getArguments() != null) {
            status = getArguments().getString(ARG_PARAM1);
        }
        return view;
    }

    @Override
    public void prepareView() {
        listEntities=new ArrayList<>();
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getActivity());
        recycleView.setLayoutManager(virtualLayoutManager);
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        if(status.equals("CLAIMED")){//可使用
            id=0;
        }else if(status.equals("CLAIMED")){

        }
        couponAdapter = new CouponAdapter(getActivity(), linearLayoutHelper, id);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager,true);
        recycleView.setAdapter(delegateAdapter);
        LinkedList<DelegateAdapter.Adapter> adapters = new LinkedList<DelegateAdapter.Adapter>();
        adapters.add(couponAdapter);
        delegateAdapter.setAdapters(adapters);
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (!isLoad) {
                    currentPage = 1;
                    requestCouponsTask();
                }
            }
        });
    }

    private void requestCouponsTask() {
        CouponApi api = new CouponApi();
        api.setP(currentPage);
        api.setPageSize(20);
        api.setStatus(status);

        isLoad = true;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CouponsBean>() {
            @Override
            public void onResponse(CouponsBean couponsBean) {
                progressBar.setVisibility(View.GONE);
                ptr.refreshComplete();
                if (currentPage == 1) {
                    listEntities.clear();
                }
                int size = couponsBean.getData().getMyCoupons().getList().size();
                if (size > 0) {
                    listEntities.addAll(couponsBean.getData().getMyCoupons().getList());
                }
                couponAdapter.notifyDataSetChanged();
                hasNext=couponsBean.getData().getMyCoupons().isNext();
                isLoad = false;
                setEmptyView(Constants.NO_DATA);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                ptr.refreshComplete();
                setEmptyView(Constants.NET_DISCONNECT);
            }
        });
    }

    private void setEmptyView(int type) {
        if (listEntities.isEmpty()) {
            imgHint.setVisibility(View.VISIBLE);
            if (type == Constants.NO_DATA) {
                imgHint.setImageResource(R.mipmap.ic_empty_coupon);
            } else {
                imgHint.setImageResource(R.mipmap.ic_no_net);
            }
        }
    }

    @Override
    public void doBusiness() {

    }

}
