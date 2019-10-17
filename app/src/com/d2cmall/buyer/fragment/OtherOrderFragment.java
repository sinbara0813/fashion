package com.d2cmall.buyer.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.OtherOrderAdapter;
import com.d2cmall.buyer.api.OrderItemsApi;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.OrderItemsBean;
import com.d2cmall.buyer.bean.OrdersBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.DialogListener;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static com.d2cmall.buyer.Constants.GlobalType.APPLY_AFTER;


/**
 * Created by rookie on 2018/3/20.
 */

public class OtherOrderFragment extends com.d2cmall.buyer.base.BaseFragment {

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
    private int index;
    private int currentPage = 1;
    private ArrayList<OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity> listEntities;
    private OtherOrderAdapter adapter;
    private DelegateAdapter delegateAdapter;
    private VirtualLayoutManager virtualLayoutManager;
    private Dialog loadingDialog;
    private boolean hasNext;
    private int lastPosition = -1;
    private int lastOffer;
    private DialogListener dialogListener = new DialogListener() {
        @Override
        public void showDialog() {
            loadingDialog.show();
        }

        @Override
        public void dismissDialog() {
            loadingDialog.dismiss();
        }
    };

    public static OtherOrderFragment newInstance(int position) {
        OtherOrderFragment fragment = new OtherOrderFragment();
        Bundle args = new Bundle();
        args.putInt("id", position);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            index = getArguments().getInt("id");
        }
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void prepareView() {
        listEntities = new ArrayList<>();
        adapter = new OtherOrderAdapter(listEntities, mContext, dialogListener);
        adapter.setIndex(index);
        loadingDialog = DialogUtil.createLoadingDialog(mContext);
        virtualLayoutManager = new VirtualLayoutManager(getActivity());
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        recycleView.setLayoutManager(virtualLayoutManager);
        delegateAdapter.addAdapter(adapter);
        recycleView.setAdapter(delegateAdapter);
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
                            currentPage++;
                            requestOrderItemsTask();
                        }
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
                currentPage = 1;
                requestOrderItemsTask();
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

    private void requestOrderItemsTask() {
        OrderItemsApi api = new OrderItemsApi();
        api.setItemIndex(index);
        api.setP(currentPage);
        api.setPageSize(20);
        if (index == 4) {
            api.setCommented(0);
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderItemsBean>() {
            @Override
            public void onResponse(OrderItemsBean orderItemsBean) {
                if (!isVisibleToUser) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                if (rlLayout != null) {
                    rlLayout.setBackgroundColor(Color.parseColor("#f0f0f0"));
                }
                ptr.refreshComplete();
                if (currentPage == 1) {
                    listEntities.clear();
                }
                int size = orderItemsBean.getData().getItems().getList().size();
                if (size > 0) {
                    listEntities.addAll(orderItemsBean.getData().getItems().getList());
                }
                adapter.notifyDataSetChanged();
                hasNext = orderItemsBean.getData().getItems().isNext();

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

    @Subscribe
    public void onEvent(GlobalTypeBean event) {
        //订单评价 申请了售后刷新页面
        if (event.getType() == APPLY_AFTER || event.getType() == Constants.GlobalType.ORDER_TALK) {
            currentPage = 1;
            requestOrderItemsTask();
        }
    }

    private void setEmptyView(int type) {
        if (listEntities.isEmpty()) {
            recycleView.setVisibility(View.GONE);
            if (emptyHintLayout != null && imgHint != null) {
                emptyHintLayout.setVisibility(View.VISIBLE);
                if (rlLayout != null) {
                    rlLayout.setBackgroundColor(Color.WHITE);
                }
                imgHint.setVisibility(View.VISIBLE);
                if (type == Constants.NO_DATA) {
                    imgHint.setImageResource(R.mipmap.ic_empty_order);
                } else {
                    imgHint.setImageResource(R.mipmap.ic_no_net);
                }
            }

        } else {
            recycleView.setVisibility(View.VISIBLE);
            emptyHintLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void doBusiness() {
        if (listEntities.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            requestOrderItemsTask();
        }
    }

}
