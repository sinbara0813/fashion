package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.d2cmall.buyer.Decoration.MessageDecoration;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.MessageListAdapter;
import com.d2cmall.buyer.api.MessageListApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.MessageListBean;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/24 10:11
 * Copyright (c) 2016 d2cmall. All rights reserved.
 * 二级消息页面
 */
public class MessageListActivity extends BaseActivity {
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
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
    private int mMajorType;
    private boolean hasDecoration = false;
    private int pageNumber = 1;
    private boolean isLoad = false;
    private MessageListAdapter messageListAdapter = null;
    private MessageListBean.DataBean.MessagesBean mMessages;
    private List<MessageListBean.DataBean.MessagesBean.ListBean> mMessageList = new ArrayList<>();
    private boolean hasNext = true;
    private String mTypeName;
    private VirtualLayoutManager mLayoutManager;
    private DelegateAdapter delegateAdapter;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recyclerview);
        ButterKnife.bind(this);
        recycleView.setBackgroundColor(getResources().getColor(R.color.bg_color));
        mTypeName = getIntent().getStringExtra("typeName");
        mMajorType = getIntent().getIntExtra("majorType", -1);
        doBusiness();
        addListener();

    }

    private void addListener() {
        if (isFinishing()) {
            return;
        }
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (!isLoad) {
                    pageNumber = 1;
                    recycleView.setBackgroundColor(getResources().getColor(R.color.bg_color));
                    loadData(mMajorType);
                }
            }
        });
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = mLayoutManager.findLastVisibleItemPosition();
                        if (last > messageListAdapter.getItemCount() - 3 && hasNext) {
                            pageNumber++;
                            loadData(mMajorType);
                        }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisPosition = mLayoutManager.findLastVisibleItemPosition();
                int itemCount = mLayoutManager.getItemCount();
                if (lastVisPosition >= itemCount - 3 && !hasNext && pageNumber>1) {
                    if (endAdapter == null) {
                        ScrollEndBinder endBinder = new ScrollEndBinder();
                        endAdapter = new BaseVirtualAdapter<>(endBinder, 100);
                        delegateAdapter.addAdapter(endAdapter);
                    }
                } else {
                    if (endAdapter != null) {
                        delegateAdapter.removeAdapter(endAdapter);
                        endAdapter = null;
                    }
                }
            }
        });
    }


    public void doBusiness() {
        nameTv.setText(mTypeName);
        if (mMajorType == -1) {
            return;
        }
        if (hasDecoration == false) {
            MessageDecoration messageDecoration = new MessageDecoration(getResources().getColor(R.color.bg_color), getResources().getColor(R.color.trans_40_color_black));
            //广场动态不加间隔
            if (!"广场动态".equals(mTypeName)) {
                recycleView.addItemDecoration(messageDecoration);
            }

            hasDecoration = true;

            mLayoutManager = new VirtualLayoutManager(MessageListActivity.this);
            messageListAdapter = new MessageListAdapter(this, mMajorType, mMessageList);
            recycleView.setLayoutManager(mLayoutManager);
            RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
            recycleView.setRecycledViewPool(viewPool);
            viewPool.setMaxRecycledViews(26, 3);
            viewPool.setMaxRecycledViews(10, 3);
            viewPool.setMaxRecycledViews(21, 3);
            viewPool.setMaxRecycledViews(27, 3);
            viewPool.setMaxRecycledViews(29, 3);
            viewPool.setMaxRecycledViews(31, 3);
            viewPool.setMaxRecycledViews(44, 3);
            viewPool.setMaxRecycledViews(51, 3);
            viewPool.setMaxRecycledViews(61, 3);
            viewPool.setMaxRecycledViews(71, 3);
            viewPool.setMaxRecycledViews(72, 3);
            delegateAdapter = new DelegateAdapter(mLayoutManager, true);
            recycleView.setAdapter(delegateAdapter);
            delegateAdapter.addAdapter(messageListAdapter);
            progressBar.setVisibility(View.VISIBLE);
        }
        loadData(mMajorType);
    }

    private void loadData(int majorType) {
        ptr.refreshComplete();
        MessageListApi messageListApi = new MessageListApi();
        messageListApi.setP(pageNumber);
        messageListApi.setPageSize(20);
        messageListApi.setMajorType(majorType);
        long type6 = D2CApplication.mSharePref.getSharePrefLong(SharePrefConstant.MESSAGE_YYPE6, System.currentTimeMillis());
        long type7 = D2CApplication.mSharePref.getSharePrefLong(SharePrefConstant.MESSAGE_YYPE7, System.currentTimeMillis());
        messageListApi.majorTypeTime6 = type6;
        messageListApi.majorTypeTime7 = type7;
        isLoad = true;
        D2CApplication.httpClient.loadingRequest(messageListApi, new BeanRequest.SuccessListener<MessageListBean>() {


            @Override
            public void onResponse(MessageListBean messageListBean) {
                if (isFinishing()) {
                    return;
                }
                ptr.refreshComplete();
                btnReload.setVisibility(View.GONE);
                imgHint.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                isLoad = false;
                mMessages = messageListBean.getData().getMessages();
                if (mMessages == null) {
                    return;
                }
                if (pageNumber == 1) {
                    mMessageList.clear();
                }
                int size = mMessages.getList().size();
                if (size > 0) {
                    mMessageList.addAll(mMessages.getList());
                } else {
                    if (mMessageList.size() == 0) {
                        setEmptyView(Constants.NO_DATA);
                    }
                }
                if (messageListAdapter != null) {
                    messageListAdapter.notifyDataSetChanged();
                    hasNext = mMessages.isNext();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ptr.refreshComplete();
                progressBar.setVisibility(View.GONE);
                Util.showToast(MessageListActivity.this, Util.checkErrorType(error));
                setEmptyView(Constants.NET_DISCONNECT);
                isLoad = false;
            }
        });

    }

    @OnClick({R.id.back_iv, R.id.btn_reload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_reload:
                pageNumber = 1;
                btnReload.setVisibility(View.GONE);
                imgHint.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                recycleView.setBackgroundColor(getResources().getColor(R.color.bg_color));
                loadData(mMajorType);
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(GlobalTypeBean event) {

        int type = event.getType();
        if (type == Constants.GlobalType.MARK_MESSAGE_READED) {
            doBusiness();
        } else if (type == Constants.GlobalType.MESSAGE_DELETE) {
            int deletePosition = event.getIntValue();
            recycleView.removeViewAt(deletePosition);
            recycleView.getAdapter().notifyDataSetChanged();
        }
    }


    private void setEmptyView(int type) {
        recycleView.setBackgroundColor(getResources().getColor(R.color.color_white));
        if (type == Constants.NO_DATA) {
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.ic_empty_message);
        } else {
            btnReload.setVisibility(View.VISIBLE);
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.ic_no_net);
        }

    }
}