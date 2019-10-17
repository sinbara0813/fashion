package com.d2cmall.buyer.fragment;

import android.app.Dialog;
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
import com.d2cmall.buyer.adapter.RefundListAdapter;
import com.d2cmall.buyer.api.CancelRefundApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.RefundInfoBean;
import com.d2cmall.buyer.bean.RefundsBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.RefundListener;
import com.d2cmall.buyer.util.DialogClickListener;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by rookie on 2017/9/9.
 * 退款列表Fragment
 */

public class Refund1Fragment extends BaseFragment implements RefundListener {

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
    private ArrayList<RefundsBean.DataEntity.RefundsEntity.ListEntity> listEntities;
    private boolean isLoad;
    private int currentPage = 1;
    private boolean hasNext;
    private DelegateAdapter delegateAdapter;
    private VirtualLayoutManager virtualLayoutManager;
    private Dialog sureDialog, loadingDialog;
    private RefundListAdapter refundListAdapter;

    public static Refund1Fragment newInstance() {
        return new Refund1Fragment();
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void prepareView() {
        listEntities = new ArrayList<>();
        loadingDialog = DialogUtil.createLoadingDialog(getActivity());
        progressBar.setVisibility(View.VISIBLE);
        virtualLayoutManager = new VirtualLayoutManager(getActivity());
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        refundListAdapter = new RefundListAdapter(getActivity(), listEntities, this);
        delegateAdapter.addAdapter(refundListAdapter);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = virtualLayoutManager.findLastVisibleItemPosition();
                        if (last > delegateAdapter.getItemCount() - 3 && hasNext) {
                            currentPage++;
                            requestRefundsTask();
                        }
                        break;
                }
            }
        });
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (!isLoad) {
                    currentPage = 1;
                    requestRefundsTask();
                }
            }
        });
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void requestRefundsTask() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.REFUNDS_URL);
        api.setP(currentPage);
        api.setPageSize(20);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RefundsBean>() {
            @Override
            public void onResponse(RefundsBean refundsBean) {
                if (progressBar != null && ptr != null) {
                    progressBar.setVisibility(View.GONE);
                    ptr.refreshComplete();
                }
                if (currentPage == 1) {
                    listEntities.clear();
                }
                if (refundsBean.getData().getRefunds().getList() != null) {
                    if (refundsBean.getData().getRefunds().getList().size() > 0) {
                        listEntities.addAll(refundsBean.getData().getRefunds().getList());
                    }
                }

                refundListAdapter.notifyDataSetChanged();
                hasNext = refundsBean.getData().getRefunds().isNext();
                isLoad = false;
                setEmptyView(Constants.NO_DATA);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressBar != null && ptr != null) {
                    progressBar.setVisibility(View.GONE);
                    ptr.refreshComplete();
                }
                setEmptyView(Constants.NET_DISCONNECT);
            }
        });
    }

    private void setEmptyView(int type) {
        if (listEntities.isEmpty()) {
            imgHint.setVisibility(View.VISIBLE);
            if (type == Constants.NO_DATA) {
                imgHint.setImageResource(R.mipmap.ic_empty_after);
            } else {
                imgHint.setImageResource(R.mipmap.ic_no_net);
            }
        }
    }


    @Override
    public void doBusiness() {
        if (listEntities.size() == 0) {
            requestRefundsTask();
        }
    }

    @OnClick({R.id.btn_reload, R.id.btn_back_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reload:
                break;
            case R.id.btn_back_top:
                break;
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(RefundsBean.DataEntity.RefundsEntity.ListEntity refund) {
        boolean exist = false;
        int i = 0;
        for (; i < listEntities.size(); i++) {
            RefundsBean.DataEntity.RefundsEntity.ListEntity ref = listEntities.get(i);
            if (ref.getId() == refund.getId()) {
                exist = true;
                break;
            }
        }
        if (exist) {
            listEntities.set(i, refund);
        }
        refundListAdapter.notifyDataSetChanged();
        if (listEntities.isEmpty()) {
            setEmptyView(Constants.NO_DATA);
        }
    }

    private void requestCancelAfterSaleTask(RefundsBean.DataEntity.RefundsEntity.ListEntity listEntity) {
        CancelRefundApi api = new CancelRefundApi();
        api.setInterPath(Constants.CANCEL_REFUND_URL);
        api.setRefundId((int) listEntity.getId());
        loadingDialog.show();
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RefundInfoBean>() {
            @Override
            public void onResponse(RefundInfoBean refundInfoBean) {
                loadingDialog.dismiss();
                Util.showToast(getActivity(), R.string.msg_cancel_after_sale_ok);
                EventBus.getDefault().post(refundInfoBean.getData().getRefund());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Util.showToast(getActivity(), Util.checkErrorType(error));
            }
        });
    }

    @Override
    public void cancelRefund(final RefundsBean.DataEntity.RefundsEntity.ListEntity listEntity) {
        sureDialog = DialogUtil.showMsgDialog(getActivity(), "确定要取消退款吗?", "确定", R.color.color_black60, "取消", R.color.color_black60, true, new DialogClickListener() {
            @Override
            public void onConfirm() {
                requestCancelAfterSaleTask(listEntity);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onDismiss() {

            }
        });
        sureDialog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
