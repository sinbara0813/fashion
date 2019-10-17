package com.d2cmall.buyer.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import com.d2cmall.buyer.adapter.OrderListAdapter;
import com.d2cmall.buyer.api.OrdersApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.OrdersBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.DialogListener;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static com.d2cmall.buyer.Constants.GlobalType.APPLY_AFTER;


/**
 * Created by rookie on 2017/8/18.
 */

public class Order1Fragment extends BaseFragment {


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
    RelativeLayout rl_layout;
    private ArrayList<OrdersBean.DataEntity.OrdersEntity.ListEntity> listEntities;
    private OrdersBean.DataEntity.OrdersEntity.ListEntity listEntity;
    private OrderListAdapter orderListAdapter;
    private boolean isEnd;
    private boolean isLoad;
    private int index;
    private int id;
    private String keyword;
    private int currentPage = 1;
    private DelegateAdapter delegateAdapter;
    private VirtualLayoutManager virtualLayoutManager;
    private Dialog loadingDialog;
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

    private ConfirmRefresh confirmRefresh = new ConfirmRefresh() {
        @Override
        public void clickRefresh() {
            if (index == 0) {
                refresh("");
            }
        }
    };

    public static Order1Fragment newInstance(int position) {
        Order1Fragment fragment = new Order1Fragment();
        Bundle args = new Bundle();
        args.putInt("id", position);
        fragment.setArguments(args);
        return fragment;
    }

    public static Order1Fragment newInstance(String keyword) {
        Order1Fragment fragment = new Order1Fragment();
        Bundle args = new Bundle();
        args.putString("keyword", keyword);
        fragment.setArguments(args);
        return fragment;
    }


    public interface ConfirmRefresh {
        void clickRefresh();
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void prepareView() {
        listEntities = new ArrayList<>();
        orderListAdapter = new OrderListAdapter(getActivity(), listEntities, dialogListener);
        orderListAdapter.setConfirmRefresh(confirmRefresh);
        virtualLayoutManager = new VirtualLayoutManager(getActivity());
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        recycleView.setLayoutManager(virtualLayoutManager);
        delegateAdapter.addAdapter(orderListAdapter);
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
                        if (last > delegateAdapter.getItemCount() - 3 && !isEnd && !isLoad) {
                            currentPage++;
                            isLoad = true;
                            requestOrdersTask();
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
                requestOrdersTask();
            }
        });
        loadingDialog = DialogUtil.createLoadingDialog(getActivity());

    }

    public void refresh(String keyword2) {
        keyword = keyword2;
        currentPage = 1;
        requestOrdersTask();
    }


    private void requestOrdersTask() {
        OrdersApi api = new OrdersApi();
        api.setP(currentPage);
        api.setPageSize(20);
        if (!Util.isEmpty(keyword)) {
            api.setProductName(keyword);
        } else {
            api.setIndex(index);
        }
        isLoad = true;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrdersBean>() {
            @Override
            public void onResponse(OrdersBean ordersBean) {
                if (!isVisibleToUser) {
                    return;
                }
                if (rl_layout != null) {
                    rl_layout.setBackgroundColor(Color.parseColor("#f0f0f0"));
                }
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                if (ptr != null) {
                    ptr.refreshComplete();
                }
                if (currentPage == 1) {
                    listEntities.clear();
                }
                int size = ordersBean.getData().getOrders().getList().size();

                if (size > 0) {
                    List<OrdersBean.DataEntity.OrdersEntity.ListEntity> netData = ordersBean.getData().getOrders().getList();
                    for (int i = 0; i < size; i++) {
                        OrdersBean.DataEntity.OrdersEntity.ListEntity parentData = netData.get(i);
                        if (parentData.getOrderStatusName().equals("待付款") || parentData.getOrderStatusName().equals("平台取消") || parentData.getOrderStatusName().equals("用户取消") || parentData.getOrderStatusName().equals("超时关闭") || parentData.getItems().size() == 1) {
                            parentData.setExcrete(false);
                            listEntities.add(parentData);
                        } else {
                            if (parentData.getItems().size() > 0) {
                                for (int j = 0; j < parentData.getItems().size(); j++) {
                                    OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity itemsEntity = parentData.getItems().get(j);
                                    List<OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity> itemList = new ArrayList<>();
                                    itemList.add(itemsEntity);
                                    parentData.setExcrete(true);
                                    OrdersBean.DataEntity.OrdersEntity.ListEntity childData = (OrdersBean.DataEntity.OrdersEntity.ListEntity) parentData.clone();
                                    childData.setItems(itemList);
                                    childData.setOrderStatusName(itemsEntity.getItemStatus());
                                    childData.setTotalPay(itemsEntity.getActualAmount());
                                    childData.setDeliverySn(itemsEntity.getDeliverySn());
                                    listEntities.add(childData);
                                }
                            }
                        }
                    }
                }
                if (recycleView != null) {
                    recycleView.setVisibility(View.VISIBLE);
                }
                if (imgHint != null) {
                    imgHint.setVisibility(View.GONE);
                }

                orderListAdapter.notifyDataSetChanged();
                if (!ordersBean.getData().getOrders().isNext()) {
                    isEnd = true;
                } else {
                    isEnd = false;
                }
                isLoad = false;
                setEmptyView(Constants.NO_DATA);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                ptr.refreshComplete();
                setEmptyView(Constants.NET_DISCONNECT);
            }
        });
    }

    private void setEmptyView(int type) {
        if (listEntities.isEmpty()) {
            recycleView.setVisibility(View.GONE);
            if (emptyHintLayout != null && imgHint != null) {
                emptyHintLayout.setVisibility(View.VISIBLE);
                if (rl_layout != null) {
                    rl_layout.setBackgroundColor(Color.WHITE);
                }
                imgHint.setVisibility(View.VISIBLE);
                if (type == Constants.NO_DATA) {
                    if (!Util.isEmpty(keyword)) {
                        imgHint.setImageResource(R.mipmap.ic_empty_search);
                    } else {
                        imgHint.setImageResource(R.mipmap.ic_empty_order);
                    }
                } else {
                    imgHint.setImageResource(R.mipmap.ic_no_net);
                }
            }

        }
    }


    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            index = getArguments().getInt("id");
            keyword = getArguments().getString("keyword");
        }
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void doBusiness() {
        if (listEntities.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            requestOrdersTask();
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

    @Subscribe
    public void onOrderReview(GlobalTypeBean typeBean) {
        //订单评价 删除订单 申请了售后刷新页面
        if (typeBean.getType() == Constants.GlobalType.ORDER_TALK || typeBean.getType() == Constants.GlobalType.DELETE_ORDER || typeBean.getType() == APPLY_AFTER) {
            currentPage = 1;
            requestOrdersTask();
        }
    }

    @Subscribe
    public void onPayOver(ActionBean typeBean) {
        if (typeBean.type == Constants.ActionType.REFRESH_CART) {
            currentPage = 1;
            requestOrdersTask();
        }
    }


    @Subscribe
    public void onEvent(OrdersBean.DataEntity.OrdersEntity.ListEntity order) {
        if (listEntities==null)return;
        boolean exist = false;
        int i = 0;
        for (; i < listEntities.size(); i++) {
            OrdersBean.DataEntity.OrdersEntity.ListEntity o = listEntities.get(i);
            if (o.getId() == order.getId()) {
                exist = true;
                break;
            }
        }
        int x = calcStatus(index, order.getOrderStatusCode());
        if (x > 0) {
            if (exist) {
                listEntities.set(i, order);
            } else {
                listEntities.add(0, order);
            }
        } else {
            if (exist) {
                listEntities.remove(i);
            }
        }
        orderListAdapter.notifyDataSetChanged();
        setEmptyView(Constants.NO_DATA);
    }

    /**
     * 根据现在这个列表中显示的状态和新的订单信息中的status判断这个订单该怎么更新
     *
     * @param index
     * @param newStatus
     * @return 1:添加到当前列表或者刷新,-1:从当前列表中移除
     */
    private int calcStatus(int index, int newStatus) {
        // 当前列表是所有订单，都是更新
        if (index == 0) {
            return 1;
        }
        // 当前列表是待付款列表，取消订单，从当前列表中移除
        if (index == 1 && (newStatus == -1 || newStatus == -2)) {
            return -1;
        }
        return 0;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.RequestCode.ORDER:
                    listEntities.clear();
                    orderListAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.VISIBLE);
                    requestOrdersTask();
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (orderListAdapter != null) {
            orderListAdapter.destroy();
        }
        super.onDestroy();
    }

}
