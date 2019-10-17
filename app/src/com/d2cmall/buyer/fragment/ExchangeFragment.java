package com.d2cmall.buyer.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.d2cmall.buyer.activity.ChangeLogisticsInfoActivity;
import com.d2cmall.buyer.activity.ExchangeDetailActivity;
import com.d2cmall.buyer.adapter.ObjectBindAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.CancleExchangeApi;
import com.d2cmall.buyer.api.ConfirmReceiveApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.bean.ExchangeInfoBean;
import com.d2cmall.buyer.bean.ExchangesBean;
import com.d2cmall.buyer.bean.OrderInfoBean;
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

import static android.app.Activity.RESULT_OK;
/**
 * Created by rookie.
 * 换货Fragment,换货列表
 */
public class ExchangeFragment extends BaseFragment implements
        ObjectBindAdapter.ViewBinder<ExchangesBean.DataBean.ExchangeListBean.ListBean>,
        AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    private ListView listView;
    private PtrStoreHouseFrameLayout ptr;
    private View progressBar;
    private View rootView;
    private ArrayList<ExchangesBean.DataBean.ExchangeListBean.ListBean> listEntities;
    private ObjectBindAdapter<ExchangesBean.DataBean.ExchangeListBean.ListBean> adapter;
    private View endView;
    private View loadView;
    private View footView;
    private boolean isEnd;
    private boolean isLoad;
    private int currentPage = 1;
    private Dialog loadingDialog;
    private boolean isPrepared;

    public static ExchangeFragment newInstance() {
        return new ExchangeFragment();
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
        rootView = inflater.inflate(R.layout.fragment_list_white, container, false);
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
                    requestExchangesTask();
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
        //如果数据源为空,加载数据
        if (listEntities.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            endView.setVisibility(View.GONE);
            loadView.setVisibility(View.GONE);
            requestExchangesTask();
        }
    }

    @Override
    public void refresh(Object... params) {
    }

    private void requestExchangesTask() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.EXCHANGES_URL);
        api.setP(currentPage);
        api.setPageSize(20);
        isLoad = true;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ExchangesBean>() {
            @Override
            public void onResponse(ExchangesBean exchangesBean) {
                progressBar.setVisibility(View.GONE);
                ptr.refreshComplete();
                if (currentPage == 1) {
                    listEntities.clear();
                }
                int size = exchangesBean.getData().getExchanges().getList().size();
                if (size > 0) {
                    listEntities.addAll(exchangesBean.getData().getExchanges().getList());
                }
                adapter.notifyDataSetChanged();
                if (!exchangesBean.getData().getExchanges().isNext()) {
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

    //设置空页面
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

    //把各个数据绑定到ItemView上
    @Override
    public void setViewValue(View view, final ExchangesBean.DataBean.ExchangeListBean.ListBean listEntity, int position) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder.tvAfterSaleId.setText(String.format(getString(R.string.label_order_id), listEntity.getOrderSn()));
        UniversalImageLoader.displayImage(getActivity(), Util.getD2cPicUrl(listEntity.getProductImg()), holder.imgProduct
                , R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        holder.tvOrderId.setText(String.format(getString(R.string.label_order_id), listEntity.getOrderSn()));
        holder.tvProductTitle.setText(listEntity.getProductName());
        holder.tvProductStyle.setText(String.format(getString(R.string.label_product_style), listEntity.getProductColor(),
                listEntity.getProductSize()));
        holder.tvProductStatus.setText(listEntity.getStatusName());
        holder.paymentLayout.setVisibility(View.GONE);
        int i = 0;//记录
        if (listEntity.getStatusCode() == 1 && listEntity.getStatusCode()==1 ) {
            holder.btnEditAfterSale.setVisibility(View.VISIBLE);
            i++;
        } else {
            holder.btnEditAfterSale.setVisibility(View.GONE);
        }
        if (listEntity.getStatusCode() < 3 && listEntity.getStatusCode() > -1) {
            holder.btnCancelAfterSale.setVisibility(View.VISIBLE);
            i++;
        } else {
            holder.btnCancelAfterSale.setVisibility(View.GONE);
        }
        if (listEntity.getStatusCode() == 4) {
            holder.btnConfirmAfterSale.setVisibility(View.VISIBLE);
            i++;
        } else {
            holder.btnConfirmAfterSale.setVisibility(View.GONE);
        }
        if (i > 0) {
            holder.afterSaleBottomLayout.setVisibility(View.VISIBLE);
        } else {
            holder.afterSaleBottomLayout.setVisibility(View.GONE);
        }
        holder.btnConfirmAfterSale.setOnClickListener(new ConfirmAfterSaleClickListener(listEntity));
        holder.btnCancelAfterSale.setOnClickListener(new CancelAfterSaleClickListener(listEntity));
        holder.btnEditAfterSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //编辑物流信息
                int exchangeId= (int) listEntity.getId();
                Intent intent = new Intent(getActivity(), ChangeLogisticsInfoActivity.class)
                        .putExtra("id", exchangeId)
                        .putExtra("img", listEntity.getProductImg())
                        .putExtra("name", listEntity.getProductName())
                        .putExtra("color", listEntity.getProductColor())
                        .putExtra("action", "填写物流信息")
                        .putExtra("type",1)
                        .putExtra("size", listEntity.getProductSize());
                startActivityForResult(intent, Constants.RequestCode.WRITE_LOGSTICS);
            }
        });
    }

    //下滑自动加载更多
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_IDLE:
                if (view.getLastVisiblePosition() >= (view.getCount() - 5) && !isEnd && !isLoad) {
                    loadView.setVisibility(View.VISIBLE);
                    currentPage++;
                    requestExchangesTask();
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
        @Bind(R.id.payment_layout)
        LinearLayout paymentLayout;
        @Bind(R.id.btn_edit_after_sale)
        TextView btnEditAfterSale;
        @Bind(R.id.btn_cancel_after_sale)
        TextView btnCancelAfterSale;
        @Bind(R.id.btn_confirm_after_sale)
        TextView btnConfirmAfterSale;
        @Bind(R.id.after_sale_bottom_layout)
        LinearLayout afterSaleBottomLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    //确认收货
    private class ConfirmAfterSaleClickListener implements View.OnClickListener {
        private ExchangesBean.DataBean.ExchangeListBean.ListBean listEntity;

        public ConfirmAfterSaleClickListener(ExchangesBean.DataBean.ExchangeListBean.ListBean listEntity) {
            super();
            this.listEntity = listEntity;
        }

        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(getContext())
                    .setMessage(R.string.msg_confirm_receive)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestConfirmAfterSaleTask();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }
        private void requestConfirmReceiveTask() {
            listEntities.remove(listEntity);
            adapter.notifyDataSetChanged();
            loadView.setVisibility(View.GONE);
            setEmptyView(Constants.NO_DATA);
            ConfirmReceiveApi api = new ConfirmReceiveApi();
            api.setOrderItemId(listEntity.getId());
            loadingDialog.show();
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderInfoBean>() {
                @Override
                public void onResponse(OrderInfoBean orderInfoBean) {
                    loadingDialog.dismiss();
                    Util.showToast(getActivity(), R.string.msg_confirm_receive_ok);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loadingDialog.dismiss();
                    Util.showToast(getActivity(), Util.checkErrorType(error));
                }
            });
        }
        private void requestConfirmAfterSaleTask() {
            SimpleApi api = new SimpleApi();
            api.setMethod(BaseApi.Method.POST);
            api.setInterPath(String.format(Constants.CONFIRM_EXCHANGE_URL, listEntity.getId()));
            loadingDialog.show();
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ExchangeInfoBean>() {
                @Override
                public void onResponse(ExchangeInfoBean exchangeInfoBean) {
                    loadingDialog.dismiss();
                    Util.showToast(getActivity(), R.string.msg_confirm_receive_ok);
                    EventBus.getDefault().post(exchangeInfoBean.getData().getExchange());
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

    //取消申请换货
    private class CancelAfterSaleClickListener implements View.OnClickListener {
        private ExchangesBean.DataBean.ExchangeListBean.ListBean listEntity;

        public CancelAfterSaleClickListener(ExchangesBean.DataBean.ExchangeListBean.ListBean listEntity) {
            super();
            this.listEntity = listEntity;
        }

        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(getContext())
                    .setMessage(R.string.msg_cancel_after_sale)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestCancelAfterSaleTask();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }

        private void requestCancelAfterSaleTask() {
            CancleExchangeApi api = new CancleExchangeApi();
            api.setExchangeId((int) listEntity.getId());
            api.setInterPath(Constants.CANCEL_EXCHANGE_URL);
            loadingDialog.show();
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ExchangeInfoBean>() {
                @Override
                public void onResponse(ExchangeInfoBean exchangeInfoBean) {
                    loadingDialog.dismiss();
                    Util.showToast(getActivity(), R.string.msg_cancel_after_sale_ok);
                    EventBus.getDefault().post(exchangeInfoBean.getData().getExchange());
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

    //每个item的点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        ExchangesBean.DataBean.ExchangeListBean.ListBean listEntity = (ExchangesBean.DataBean.ExchangeListBean.ListBean)
                parent.getAdapter().getItem(position);
        if (listEntity != null) {
            startActivity(new Intent(getActivity(), ExchangeDetailActivity.class).putExtra("exchangeId",listEntity.getId()));
//            Util.urlAction(getActivity(), String.format(Constants.EXCHANGE_DETAIL_URL, listEntity.getId()), true);
        }
    }

    /**
     * 更新该换货单状态
     */
    @Subscribe
    public void onEvent(ExchangesBean.DataBean.ExchangeListBean.ListBean exchange) {
        boolean exist = false;
        int i = 0;
        for (; i < listEntities.size(); i++) {
            ExchangesBean.DataBean.ExchangeListBean.ListBean ex = listEntities.get(i);
            if (ex.getId() == exchange.getId()) {
                exist = true;
                break;
            }
        }
        if (exist) {
            listEntities.set(i, exchange);
        }
        adapter.notifyDataSetChanged();
        if (listEntities.isEmpty()) {
            loadView.setVisibility(View.GONE);
            setEmptyView(Constants.NO_DATA);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case Constants.RequestCode.WRITE_LOGSTICS:
                    currentPage = 1;
                    progressBar.setVisibility(View.VISIBLE);
                    requestExchangesTask();
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
        super.onDestroy();
    }
}