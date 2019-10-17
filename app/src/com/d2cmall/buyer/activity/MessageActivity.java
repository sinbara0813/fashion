package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
import com.d2cmall.buyer.adapter.MessageCategoryAdapter;
import com.d2cmall.buyer.api.MessageApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.MessageCategoryBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
 * Date: 2017/7/21 13:39
 * Copyright (c) 2016 d2cmall. All rights reserved.
 * 消息列表页面
 */
public class MessageActivity extends BaseActivity {


    @Bind(R.id.back_iv)
    ImageView mBackIv;
    @Bind(R.id.name_tv)
    TextView mNameTv;
    @Bind(R.id.title_layout)
    RelativeLayout mTitleLayout;
    @Bind(R.id.open_tv)
    TextView mOpenTv;
    @Bind(R.id.close_iv)
    ImageView mCloseIv;
    @Bind(R.id.open_iv)
    ImageView mOpenIv;
    @Bind(R.id.message_tip_rl)
    RelativeLayout mMessageTipRl;
    @Bind(R.id.message_category_recycler)
    RecyclerView mMessageCategoryRecycler;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout mPtr;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    private List<MessageCategoryBean.DataBean.MessagesBean> messages;
    private boolean hasAdapter = false;
    private boolean isOpen;
    private MessageCategoryAdapter mMessageCategoryAdapter;
    private VirtualLayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        doBusiness();
        initListener();
    }



    public void doBusiness() {
        if(isFinishing()) {
            return;
        }
        TitleUtil.setTitle(this, R.string.label_message_maven);
        messages = new ArrayList<>();
        mLayoutManager = new VirtualLayoutManager(MessageActivity.this);
        mMessageCategoryAdapter = new MessageCategoryAdapter(this, messages);
        mMessageCategoryRecycler.setLayoutManager(mLayoutManager);
        DelegateAdapter delegateAdapter = new DelegateAdapter(mLayoutManager, true);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0,2);
        mMessageCategoryRecycler.setRecycledViewPool(recycledViewPool);
        mMessageCategoryRecycler.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(mMessageCategoryAdapter);
        isOpen = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.PUSH, true);
        if (isOpen) {
            mMessageTipRl.setVisibility(View.GONE);
        } else {
            mMessageTipRl.setVisibility(View.VISIBLE);
        }
        mProgressBar.setVisibility(View.VISIBLE);

        loadData();
    }
    private void initListener() {
        mPtr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadData();
            }
        });

    }


    private void loadData() {
        MessageApi api = new MessageApi();
        long type6=D2CApplication.mSharePref.getSharePrefLong(SharePrefConstant.MESSAGE_YYPE6,System.currentTimeMillis());
        long type7=D2CApplication.mSharePref.getSharePrefLong(SharePrefConstant.MESSAGE_YYPE6,System.currentTimeMillis());
        api.majorTypeTime6=type6;
        api.majorTypeTime7=type7;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MessageCategoryBean>() {
            @Override
            public void onResponse(MessageCategoryBean messageCategoryBean) {
                mProgressBar.setVisibility(View.GONE);
                mPtr.refreshComplete();
                if (messageCategoryBean.getData().getMessages() != null && messageCategoryBean.getData().getMessages().size() > 0) {
                    if (messages.size() > 0) {
                        messages.clear();
                    }
                    messages.addAll(messageCategoryBean.getData().getMessages());
                }

                DateSortUtil dateSortUtil = new DateSortUtil();
                Collections.sort(messages, dateSortUtil);

                if (mMessageCategoryAdapter != null) {
                    mMessageCategoryAdapter.notifyDataSetChanged();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //获取消息失败
                mProgressBar.setVisibility(View.GONE);
                mPtr.refreshComplete();
                Util.showToast(MessageActivity.this, Util.checkErrorType(error));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(GlobalTypeBean event) {
        int type = event.getType();
        if (type == Constants.GlobalType.MARK_MESSAGE_READED || type == Constants.GlobalType.MESSAGE_DELETE) {
            doBusiness();
        }
        //消息通知View的显示隐藏
        if (type == Constants.GlobalType.COLSE_MESSAGE_NOTIFY) {
            mMessageTipRl.setVisibility(View.VISIBLE);
        }
        if (type == Constants.GlobalType.OPEN_MESSAGE_NOTIFY) {
            mMessageTipRl.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.back_iv, R.id.close_iv, R.id.open_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.close_iv:
                mMessageTipRl.setVisibility(View.GONE);
                break;
            case R.id.open_iv:
                //  开启通知
                startActivity(new Intent(MessageActivity.this, SettingActivity.class));
                break;
        }
    }

    //消息排序
    public class DateSortUtil implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            MessageCategoryBean.DataBean.MessagesBean messagesBean0 = (MessageCategoryBean.DataBean.MessagesBean) o1;
            MessageCategoryBean.DataBean.MessagesBean messagesBean1 = (MessageCategoryBean.DataBean.MessagesBean) o2;
            int flag = messagesBean0.getCreateDate().compareTo(messagesBean1.getCreateDate());
            return -flag;
        }
    }
}
