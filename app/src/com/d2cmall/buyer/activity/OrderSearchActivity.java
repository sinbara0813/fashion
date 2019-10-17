package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.OrderListAdapter;
import com.d2cmall.buyer.api.OrdersApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.OrdersBean;
import com.d2cmall.buyer.fragment.Order1Fragment;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.DialogListener;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class OrderSearchActivity extends BaseActivity {


    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.et_account)
    ClearEditText etAccount;
    @Bind(R.id.iv_reset)
    ImageView ivReset;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.ll_search)
    LinearLayout llSearch;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.error_image)
    ImageView errorImage;
    private String keyword;
    private ArrayList<OrdersBean.DataEntity.OrdersEntity.ListEntity> listEntities;
    private OrdersBean.DataEntity.OrdersEntity.ListEntity listEntity;
    private OrderListAdapter orderListAdapter;
    private DelegateAdapter delegateAdapter;
    private VirtualLayoutManager virtualLayoutManager;
    private boolean isEnd;
    private boolean isLoad;
    int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_search);
        ButterKnife.bind(this);
        keyword = etAccount.getText().toString();
        etAccount.setText(keyword);
        listEntities = new ArrayList<>();
        orderListAdapter = new OrderListAdapter(this, listEntities, new DialogListener() {
            @Override
            public void showDialog() {

            }

            @Override
            public void dismissDialog() {

            }
        });
        virtualLayoutManager = new VirtualLayoutManager(this);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager,true);
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
    }

    private void requestOrdersTask() {
        OrdersApi api = new OrdersApi();
        api.setP(currentPage);
        api.setPageSize(20);
        if (!Util.isEmpty(keyword)) {
            api.setProductName(keyword);
        }
        isLoad = true;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrdersBean>() {
            @Override
            public void onResponse(OrdersBean ordersBean) {
                if (currentPage == 1) {
                    listEntities.clear();
                }
                int size = ordersBean.getData().getOrders().getList().size();
                if (size > 0) {
                    listEntities.addAll(ordersBean.getData().getOrders().getList());
                    errorImage.setVisibility(View.GONE);
                    recycleView.setVisibility(View.VISIBLE);
                }else {
                    errorImage.setVisibility(View.VISIBLE);
                    recycleView.setVisibility(View.GONE);
                }
                orderListAdapter.notifyDataSetChanged();
                if (!ordersBean.getData().getOrders().isNext()) {
                    isEnd = true;
                } else {
                    isEnd = false;
                }
                isLoad = false;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorImage.setVisibility(View.VISIBLE);
                errorImage.setImageResource(R.mipmap.ic_no_net);
                recycleView.setVisibility(View.GONE);
                Util.showToast(OrderSearchActivity.this, Util.checkErrorType(error));
            }
        });
    }

//    @OnClick(R.id.tv_order_search)
//    void clickOrderSearch() {
    //   Intent intent = new Intent(OrderSearchActivity.this, OrderSearchPreviewActivity.class);
//        intent.putExtra("keyword", keyword);
//        startActivity(intent);
//        overridePendingTransition(0, 0);
//    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @OnClick({R.id.back_iv, R.id.iv_reset, R.id.title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.iv_reset:
                etAccount.setText("");
                break;
            case R.id.title_right:
                keyword = etAccount.getText().toString().trim();
                currentPage = 1;
                requestOrdersTask();
                break;
        }
    }
}
