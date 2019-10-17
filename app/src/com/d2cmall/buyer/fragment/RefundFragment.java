package com.d2cmall.buyer.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.ObjectBindAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.bean.RefundInfoBean;
import com.d2cmall.buyer.bean.RefundsBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class RefundFragment extends BaseFragment implements
        ObjectBindAdapter.ViewBinder<RefundsBean.DataEntity.RefundsEntity.ListEntity>,
        AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    private ListView listView;
    private PtrStoreHouseFrameLayout ptr;
    private View progressBar;
    private View rootView;
    private ArrayList<RefundsBean.DataEntity.RefundsEntity.ListEntity> listEntities;
    private ObjectBindAdapter<RefundsBean.DataEntity.RefundsEntity.ListEntity> adapter;
    private View endView;
    private View loadView;
    private View footView;
    private boolean isEnd;
    private boolean isLoad;
    private int currentPage = 1;
    private Dialog loadingDialog;
    private boolean isPrepared;

    public static RefundFragment newInstance() {
        return new RefundFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listEntities = new ArrayList<>();
        adapter = new ObjectBindAdapter<>(getActivity(), listEntities, R.layout.list_item_after_sale);
        footView = getActivity().getLayoutInflater().inflate(R.layout.list_foot_no_more2, null);
        endView = footView.findViewById(R.id.no_more);
        loadView = footView.findViewById(R.id.loading);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_listview, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        ptr = (PtrStoreHouseFrameLayout) rootView.findViewById(R.id.ptr);
        progressBar = rootView.findViewById(R.id.progressBar);
        listView.addFooterView(footView);
        adapter.setViewBinder(this);
        listView.setOnScrollListener(this);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
        isPrepared = true;
        lazyLoad();
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
        loadingDialog = DialogUtil.createLoadingDialog(getActivity());
        return rootView;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        if (listEntities.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            endView.setVisibility(View.GONE);
            loadView.setVisibility(View.GONE);
            requestRefundsTask();
        }
    }

    @Override
    public void refresh(Object... params) {
    }

    private void requestRefundsTask() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.REFUNDS_URL);
        api.setP(currentPage);
        api.setPageSize(20);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RefundsBean>() {
            @Override
            public void onResponse(RefundsBean refundsBean) {
                progressBar.setVisibility(View.GONE);
                ptr.refreshComplete();
                if (currentPage == 1) {
                    listEntities.clear();
                }
                int size = refundsBean.getData().getRefunds().getList().size();
                if (size > 0) {
                    listEntities.addAll(refundsBean.getData().getRefunds().getList());
                }
                adapter.notifyDataSetChanged();
                if (!refundsBean.getData().getRefunds().isNext()) {
                    isEnd = true;
                    loadView.setVisibility(View.GONE);
                } else {
                    isEnd = false;
                    loadView.setVisibility(View.INVISIBLE);
                }
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
            View emptyView = listView.getEmptyView();
            if (emptyView == null) {
                emptyView = rootView.findViewById(R.id.empty_hint_layout);
                listView.setEmptyView(emptyView);
            }
            emptyView.setVisibility(View.VISIBLE);
            ImageView imgHint = (ImageView) emptyView.findViewById(R.id.img_hint);
            imgHint.setVisibility(View.VISIBLE);
            if (type == Constants.NO_DATA) {
                imgHint.setImageResource(R.mipmap.ic_empty_order);
            } else {
                imgHint.setImageResource(R.mipmap.ic_no_net);
            }
        }
    }

    @Override
    public void setViewValue(View view, final RefundsBean.DataEntity.RefundsEntity.ListEntity listEntity, int position) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder.tvAfterSaleId.setText(getString(R.string.label_sale_id, listEntity.getRefundSn()));
        UniversalImageLoader.displayImage(getActivity(),Util.getD2cPicUrl(listEntity.getProductImg()), holder.imgProduct
                , R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        holder.tvOrderId.setText(getString(R.string.label_order_id, listEntity.getOrderSn()));
        holder.tvProductTitle.setText(listEntity.getProductName());
        holder.tvProductStyle.setText(getString(R.string.label_product_style, listEntity.getProductColor(),
                listEntity.getProductSize()));
        holder.tvProductStatus.setText(listEntity.getStatusName());
        holder.paymentLayout.setVisibility(View.VISIBLE);
        holder.tvPaymodeName.setText(getString(R.string.label_order_payment, listEntity.getPayType()));
        if (listEntity.getPayType().equals("货到付款")) {
            //不显示退回方式
            holder.tvPaymentDescribe.setText(getString(R.string.label_after_sale_payment2,
                    Util.getNumberFormat(listEntity.getTotalAmount())));
        } else {
            holder.tvPaymentDescribe.setText(getString(R.string.label_after_sale_payment,
                    Util.getNumberFormat(listEntity.getTotalAmount())));
        }
        holder.btnCancelAfterSale.setVisibility(View.VISIBLE);
        if (listEntity.getStatusCode() == 1) {
            holder.afterSaleBottomLayout.setVisibility(View.VISIBLE);
        } else {
            holder.afterSaleBottomLayout.setVisibility(View.GONE);
        }
        holder.btnCancelAfterSale.setOnClickListener(new CancelAfterSaleClickListener(listEntity));
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_IDLE:
                if (view.getLastVisiblePosition() >= (view.getCount() - 5) && !isEnd && !isLoad) {
                    loadView.setVisibility(View.VISIBLE);
                    currentPage++;
                    requestRefundsTask();
                } else {
                    break;
                }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        if ((totalItemCount > visibleItemCount) && isEnd) {
            endView.setVisibility(View.VISIBLE);
        } else {
            endView.setVisibility(View.GONE);
        }
    }

    static class ViewHolder {
        @Bind(R.id.tv_after_sale_id)
        TextView tvAfterSaleId;
        @Bind(R.id.img_product)
        ImageView imgProduct;
        @Bind(R.id.tv_order_id)
        TextView tvOrderId;
        @Bind(R.id.tv_product_title)
        TextView tvProductTitle;
        @Bind(R.id.tv_product_style)
        TextView tvProductStyle;
        @Bind(R.id.tv_product_status)
        TextView tvProductStatus;
        @Bind(R.id.tv_paymode_name)
        TextView tvPaymodeName;
        @Bind(R.id.tv_payment_describe)
        TextView tvPaymentDescribe;
        @Bind(R.id.payment_layout)
        LinearLayout paymentLayout;
        @Bind(R.id.btn_cancel_after_sale)
        TextView btnCancelAfterSale;
        @Bind(R.id.after_sale_bottom_layout)
        LinearLayout afterSaleBottomLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private class CancelAfterSaleClickListener implements View.OnClickListener {
        private RefundsBean.DataEntity.RefundsEntity.ListEntity listEntity;

        public CancelAfterSaleClickListener(RefundsBean.DataEntity.RefundsEntity.ListEntity listEntity) {
            super();
            this.listEntity = listEntity;
        }

        @Override
        public void onClick(View v) {
            new  AlertDialog.Builder(getContext())
                    .setMessage(R.string.msg_cancel_after_sale)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestCancelAfterSaleTask();
                        }
                    })
                    .setNegativeButton("取消" , null)
                    .show();
        }

        private void requestCancelAfterSaleTask() {
            SimpleApi api = new SimpleApi();
            api.setMethod(BaseApi.Method.POST);
            api.setInterPath(String.format(Constants.CANCEL_REFUND_URL, listEntity.getId()));
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
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        RefundsBean.DataEntity.RefundsEntity.ListEntity listEntity = (RefundsBean.DataEntity.RefundsEntity.ListEntity)
                parent.getAdapter().getItem(position);
        if (listEntity != null) {
            Util.urlAction(getActivity(), String.format(Constants.REFUND_DETAIL_URL, listEntity.getId()), true);
        }
    }

    /**
     * 更新该退款单状态
     */
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
        adapter.notifyDataSetChanged();
        if (listEntities.isEmpty()) {
            loadView.setVisibility(View.GONE);
            setEmptyView(Constants.NO_DATA);
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}