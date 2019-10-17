package com.d2cmall.buyer.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import com.d2cmall.buyer.activity.ChangeLogisticsInfoActivity;
import com.d2cmall.buyer.adapter.ReshipListAdapter;
import com.d2cmall.buyer.api.CancleReshipApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.ReshipInfoBean;
import com.d2cmall.buyer.bean.ReshipsBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.ReshipListener;
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
 * 退款退货列表的Fragment
 */

public class Reship1Fragment extends BaseFragment implements ReshipListener {
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

    private ArrayList<ReshipsBean.DataBean.ReshipsDataBean.ListBean> listEntities;
    private boolean isLoad;
    private int currentPage = 1;
    private boolean hasNext;
    private DelegateAdapter delegateAdapter;
    private VirtualLayoutManager virtualLayoutManager;
    private Dialog sureDialog, loadingDialog;
    private ReshipListAdapter reshipListAdapter;

    public static Reship1Fragment newInstance() {
        return new Reship1Fragment();
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void prepareView() {
        listEntities = new ArrayList<>();
        loadingDialog = DialogUtil.createLoadingDialog(getActivity());
        virtualLayoutManager = new VirtualLayoutManager(getActivity());
        progressBar.setVisibility(View.VISIBLE);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        reshipListAdapter = new ReshipListAdapter(getActivity(), listEntities, this);
        delegateAdapter.addAdapter(reshipListAdapter);
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
                            requestReshipsTask();
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
                    requestReshipsTask();
                }
            }
        });
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void requestReshipsTask() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.RESHIPS_URL);
        api.setP(currentPage);
        api.setPageSize(20);
        isLoad = true;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ReshipsBean>() {
            @Override
            public void onResponse(ReshipsBean reshipsBean) {
                if (progressBar != null && ptr != null) {
                    progressBar.setVisibility(View.GONE);
                    ptr.refreshComplete();
                }
                if (currentPage == 1) {
                    listEntities.clear();
                }
                int size = reshipsBean.getData().getReships().getList().size();
                if (size > 0) {
                    listEntities.addAll(reshipsBean.getData().getReships().getList());
                }
                reshipListAdapter.notifyDataSetChanged();
                hasNext = reshipsBean.getData().getReships().isNext();
                isLoad = false;
                setEmptyView(Constants.NO_DATA);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(getActivity(), Util.checkErrorType(error));
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
            requestReshipsTask();
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

    private void requestCancelAfterSaleTask(ReshipsBean.DataBean.ReshipsDataBean.ListBean listEntity) {
        CancleReshipApi api = new CancleReshipApi();
        api.setReshipId((int) listEntity.getId());
        api.setInterPath(Constants.CANCEL_RESHIP_URL);
        loadingDialog.show();
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ReshipInfoBean>() {
            @Override
            public void onResponse(ReshipInfoBean reshipInfoBean) {
                loadingDialog.dismiss();
                Util.showToast(getActivity(), R.string.msg_cancel_after_sale_ok);
                EventBus.getDefault().post(reshipInfoBean.getData().getReship());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Util.showToast(getActivity(), Util.checkErrorType(error));
            }
        });
    }

    //取消申请
    @Override
    public void cancelReshipClick(final ReshipsBean.DataBean.ReshipsDataBean.ListBean listEntity) {
        sureDialog = DialogUtil.showMsgDialog(getActivity(), "确定取消申请?", "确定", R.color.color_black60, "取消", R.color.color_black60, true, new DialogClickListener() {
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

    @Subscribe
    public void onEvent(ReshipsBean.DataBean.ReshipsDataBean.ListBean reship) {
        boolean exist = false;
        int i = 0;
        for (; i < listEntities.size(); i++) {
            ReshipsBean.DataBean.ReshipsDataBean.ListBean res = listEntities.get(i);
            if (res.getId() == reship.getId()) {
                exist = true;
                break;
            }
        }
        if (exist) {
            listEntities.set(i, reship);
        }
        reshipListAdapter.notifyDataSetChanged();
        if (listEntities.isEmpty()) {
            setEmptyView(Constants.NO_DATA);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case Constants.RequestCode.WRITE_LOGSTICS:
                    currentPage = 1;
                    progressBar.setVisibility(View.VISIBLE);
                    requestReshipsTask();
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void editReshipClick(ReshipsBean.DataBean.ReshipsDataBean.ListBean listEntity) {
        //编辑物流信息
        int reshipId= (int) listEntity.getId();
        Intent intent = new Intent(getActivity(), ChangeLogisticsInfoActivity.class)
                .putExtra("id", reshipId)
                .putExtra("img", listEntity.getProductImg())
                .putExtra("name", listEntity.getProductName())
                .putExtra("color", listEntity.getProductColor())
                .putExtra("action", "填写物流信息")
                .putExtra("type", 0)
                .putExtra("size", listEntity.getProductSize());
        startActivityForResult(intent, Constants.RequestCode.WRITE_LOGSTICS);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
