package com.d2cmall.buyer.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.AllAfterSaleActivity;
import com.d2cmall.buyer.activity.ApplyAfterSaleActivity;
import com.d2cmall.buyer.activity.MyOrderActivity;
import com.d2cmall.buyer.activity.OrderItemDetailActivity;
import com.d2cmall.buyer.activity.ReviewOrderActivity;
import com.d2cmall.buyer.adapter.ObjectBindAdapter;
import com.d2cmall.buyer.api.ConfirmReceiveApi;
import com.d2cmall.buyer.api.OrderItemsApi;
import com.d2cmall.buyer.bean.OrderInfoBean;
import com.d2cmall.buyer.bean.OrderItemsBean;
import com.d2cmall.buyer.bean.OrdersBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.CenterAlignImageSpan;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class OtherOrder1Fragment extends BaseFragment implements
        ObjectBindAdapter.ViewBinder<OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity>,
        AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    private ListView listView;
    private PtrStoreHouseFrameLayout ptr;
    private RelativeLayout rl_layout;
    private View progressBar;
    private View rootView;
    private ArrayList<OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity> listEntities;
    private ObjectBindAdapter<OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity> adapter;
    private View endView;
    private View loadView;
    private View footView;
    private static final String ARG_PARAM1 = "param1";
    private int index;
    private boolean isEnd;
    private boolean isLoad;
    private int currentPage = 1;
    private Dialog loadingDialog;
    private MyOrderActivity myOrdersActivity;
    private boolean isPrepared;
    private String keyword;

    public static OtherOrder1Fragment newInstance(int index) {
        OtherOrder1Fragment fragment = new OtherOrder1Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listEntities = new ArrayList<>();
        adapter = new ObjectBindAdapter<>(getActivity(), listEntities, R.layout.layout_single_order);
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
        rl_layout = (RelativeLayout) rootView.findViewById(R.id.rl_layout);
        progressBar = rootView.findViewById(R.id.progressBar);
        listView.addFooterView(footView);
        adapter.setViewBinder(this);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);
        listView.setAdapter(adapter);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_PARAM1);
        }
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
                    isLoad = true;
                    requestOrderItemsTask();
                }
            }
        });
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
            loadView.setVisibility(View.GONE);
            endView.setVisibility(View.GONE);
            requestOrderItemsTask();
        }
    }

    @Override
    public void refresh(Object... params) {
        View emptyView = listView.getEmptyView();
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }
        currentPage = 1;
        loadView.setVisibility(View.GONE);
        isLoad = true;
        requestOrderItemsTask();
    }

    private void requestOrderItemsTask() {
        OrderItemsApi api = new OrderItemsApi();
        api.setItemIndex(index);
        api.setP(currentPage);
        api.setPageSize(20);
        if (index == 4) {
            api.setCommented(0);
        }
        isLoad = true;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderItemsBean>() {
            @Override
            public void onResponse(OrderItemsBean orderItemsBean) {
                if (rl_layout != null) {
                    rl_layout.setBackgroundColor(Color.parseColor("#f0f0f0"));
                }
                progressBar.setVisibility(View.GONE);
                ptr.refreshComplete();
                if (currentPage == 1) {
                    listEntities.clear();
                }
                int size = orderItemsBean.getData().getItems().getList().size();
                if (size > 0) {
                    listEntities.addAll(orderItemsBean.getData().getItems().getList());
                }
                adapter.notifyDataSetChanged();
                if (!orderItemsBean.getData().getItems().isNext()) {
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
            if (rl_layout != null) {
                rl_layout.setBackgroundColor(Color.WHITE);
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
    public void setViewValue(View view, final OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity listEntity, int position) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        UniversalImageLoader.displayImage(getActivity(), Util.getD2cPicUrl(
                listEntity.getProductImg()), holder.ivImage
                , R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        if (listEntity.getFlashPromotionId() != null && listEntity.getFlashPromotionId() > 0) {
            StringBuilder builder = new StringBuilder();
            if (!Util.isEmpty(listEntity.getProductName())) {
                builder.append("   " + listEntity.getProductName());
            }
            SpannableString sb = new SpannableString(builder.toString());
            Drawable d = getResources().getDrawable(R.mipmap.icon_shopcart_xsg);
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            sb.setSpan(new CenterAlignImageSpan(d), 0, 1, ImageSpan.ALIGN_BASELINE);
            holder.tvProductName.setText(sb);
        } else {
            holder.tvProductName.setText(listEntity.getProductName());
        }
        holder.tvProductStyle.setText(getString(R.string.label_kongge, listEntity.getColor(),
                listEntity.getSize()));
        String status;
        int code = listEntity.getItemStatusCode();
        switch (code) {
            case -3:
                status = "超时关闭";
                break;
            case -1:
                status = "用户取消";
                break;
            case 0:
                status = "未处理";
                break;
            case 1:
                status = "待付款";
                break;
            case 3:
                status = "待发货";
                break;
            case 4:
                status = "已发货";
                break;
            case 8:
                status = "订单已完成";
                break;
            default:
                status = "未知";

        }
        holder.tvStatus.setText(listEntity.getItemStatus());
        double lessPrice = listEntity.getProductPrice() - (listEntity.getPromotionPrice() / listEntity.getQuantity());
        if (lessPrice < listEntity.getProductPrice()) {
            if (lessPrice < 0) {
                holder.tvPrice.setText(String.valueOf(0));
            } else {
                holder.tvPrice.setText(String.format(getString(R.string.label_price), String.valueOf(lessPrice)));
            }
        } else {
            holder.tvPrice.setText(String.format(getString(R.string.label_price), String.valueOf(listEntity.getProductPrice())));
        }
        holder.tvOrderId.setText(getString(R.string.label_order_id, listEntity.getOrderSn()));
        holder.tvOrderTotal.setText(getString(R.string.label_product_total, listEntity.getQuantity(),
                getString(R.string.label_no_sign_price, Util.getNumberFormat(listEntity.getActualAmount()))));
        holder.tvCount.setText(getString(R.string.label_product_quantity, listEntity.getQuantity()));
        holder.btnShowLogistical.setVisibility(View.GONE);
        holder.btnConfirmReceive.setVisibility(View.GONE);
        holder.btnGotoComment.setVisibility(View.GONE);
        holder.btnShowAfterSale.setVisibility(View.GONE);
        holder.btnApplyAfterSale.setVisibility(View.GONE);

        int i = 0;
        //确认收货
        if (listEntity.getItemStatusCode() == 2 ) {
            i++;
            holder.btnConfirmReceive.setVisibility(View.VISIBLE);
        }
        //查看物流
        if (!Util.isEmpty(listEntity.getDeliverySn())) {//换货成功时候隐藏查看物流,显示评价晒单
            i++;
            holder.btnShowLogistical.setVisibility(View.VISIBLE);
        }
        //评价晒单
        if (listEntity.getIsComment() == 1) {
            i++;
            holder.btnGotoComment.setVisibility(View.VISIBLE);
        }
        if (!listEntity.getType().equals("distribution")) {
            //售后跟踪
            if (!Util.isEmpty(listEntity.getAftering()) && !listEntity.getAftering().equals("none")) {
                holder.btnShowAfterSale.setVisibility(View.VISIBLE);
                i++;
            } else if (listEntity.getItemStatusCode() >= 1 && listEntity.getItemStatusCode() != 8 && listEntity.getAfter() == 1 && listEntity.getAftering().equals("none")) {
                holder.btnApplyAfterSale.setVisibility(View.VISIBLE);
                i++;
            }
        }

        if (i > 0) {
            holder.orderBottomLayout.setVisibility(View.VISIBLE);
        } else {
            holder.orderBottomLayout.setVisibility(View.GONE);
        }

        holder.btnConfirmReceive.setOnClickListener(new ConfirmReceiveListener(listEntity));
        holder.btnShowLogistical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("KAOLA".equals(listEntity.getProductSource())){
                    Util.urlAction(getActivity(), String.format(Constants.KAOLA_WULIU_URL,
                            listEntity.getDeliverySn(), listEntity.getDeliveryCorpCode(), listEntity.getProductImg(), listEntity.getId()));
                }else{
                    Util.urlAction(getActivity(), String.format(Constants.WULIU_URL,
                            listEntity.getDeliverySn(), listEntity.getDeliveryCorpCode(), listEntity.getProductImg()));
                }

            }
        });
        holder.btnGotoComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReviewOrderActivity.class);
                intent.putExtra("id", listEntity.getId());
                intent.putExtra("productUrl", listEntity.getProductImg());
                intent.putExtra("productInfo", listEntity.getProductName());
                intent.putExtra("intentFlag", 0);
                intent.putExtra("price", listEntity.getProductPrice());
                intent.putExtra("style", getString(R.string.label_kongge, listEntity.getColor(),
                        listEntity.getSize()));
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            }
        });
        holder.btnApplyAfterSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ApplyAfterSaleActivity.class);
                intent.putExtra("orderSn", listEntity.getOrderSn());
                intent.putExtra("orderId", listEntity.getId());
                intent.putExtra("statusCode", listEntity.getItemStatusCode());
                intent.putExtra("bean", listEntity);
                intent.putExtra("intentFlag", 0);
                intent.putExtra("isKaoLa", "KAOLA".equals(listEntity.getProductSource()));//是不是考拉
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            }
        });
        holder.btnShowAfterSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listEntity.getAftering().equals("exchange")) {
                    gotoAfterSale(1);
                } else if (listEntity.getAftering().equals("reship")) {
                    gotoAfterSale(0);
                } else if (listEntity.getAftering().equals("refund")) {
                    gotoAfterSale(2);
                }
            }
        });
    }

    private void gotoAfterSale(int position) {
        Intent intent = new Intent(getActivity(), AllAfterSaleActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
    }

    private class ConfirmReceiveListener implements View.OnClickListener {

        private OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity itemEntity;

        public ConfirmReceiveListener(OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity itemEntity) {
            super();
            this.itemEntity = itemEntity;
        }

        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(getContext())
                    .setMessage(R.string.msg_confirm_receive)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestConfirmReceiveTask();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }

        private void requestConfirmReceiveTask() {
            adapter.notifyDataSetChanged();
            loadView.setVisibility(View.GONE);
            setEmptyView(Constants.NO_DATA);
            ConfirmReceiveApi api = new ConfirmReceiveApi();
            api.setOrderItemId(itemEntity.getId());
            loadingDialog.show();
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderInfoBean>() {
                @Override
                public void onResponse(OrderInfoBean orderInfoBean) {
                    loadingDialog.dismiss();
                    listEntities.remove(itemEntity);
                    Util.showToast(getActivity(), R.string.msg_confirm_receive_ok);
                    getMyOrdersActivity().refreshList(4);//刷新待评价列表
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
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_IDLE:
                if (view.getLastVisiblePosition() >= (view.getCount() - 5) && !isEnd && !isLoad) {
                    loadView.setVisibility(View.VISIBLE);
                    currentPage++;
                    isLoad = true;
                    requestOrderItemsTask();
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity itemsEntity = (OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity)
                parent.getAdapter().getItem(position);
        if (itemsEntity != null) {
//            Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
//            intent.putExtra("orderSn", itemsEntity.getOrderSn());
            Intent intent = new Intent(getActivity(), OrderItemDetailActivity.class);
            intent.putExtra("orderSn", String.valueOf(itemsEntity.getId()));
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
        }
    }

    static class ViewHolder {
        @Bind(R.id.tv_order_timeout)
        TextView tvOrderTimeout;
        @Bind(R.id.timeout_layout)
        LinearLayout timeoutLayout;
        @Bind(R.id.tv_order_id)
        TextView tvOrderId;
        @Bind(R.id.tv_status)
        TextView tvStatus;
        @Bind(R.id.empty_view)
        View emptyView;
        @Bind(R.id.iv_image)
        ImageView ivImage;
        @Bind(R.id.tv_product_name)
        TextView tvProductName;
        @Bind(R.id.tv_product_style)
        TextView tvProductStyle;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_count)
        TextView tvCount;
        @Bind(R.id.order_container_layout)
        LinearLayout orderContainerLayout;
        @Bind(R.id.line_layout)
        View lineLayout;
        @Bind(R.id.tv_counter)
        TextView tvCounter;
        @Bind(R.id.tv_order_total)
        TextView tvOrderTotal;
        @Bind(R.id.btn_show_after_sale)
        TextView btnShowAfterSale;
        @Bind(R.id.btn_apply_after_sale)
        TextView btnApplyAfterSale;
        @Bind(R.id.btn_show_logistical)
        TextView btnShowLogistical;
        @Bind(R.id.btn_confirm_receive)
        TextView btnConfirmReceive;
        @Bind(R.id.btn_goto_comment)
        TextView btnGotoComment;
        @Bind(R.id.cancel_order_layout)
        LinearLayout cancelOrderLayout;
        @Bind(R.id.order_bottom_layout)
        LinearLayout orderBottomLayout;
        @Bind(R.id.order_item)
        LinearLayout orderItem;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private MyOrderActivity getMyOrdersActivity() {
        if (myOrdersActivity == null) {
            myOrdersActivity = (MyOrderActivity) getActivity();
        }
        return myOrdersActivity;
    }

}