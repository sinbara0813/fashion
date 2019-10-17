package com.d2cmall.buyer.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.d2cmall.buyer.adapter.ShowAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.MyFollowBean;
import com.d2cmall.buyer.bean.ShareBean;
import com.d2cmall.buyer.bean.UnFocusMemberBean;
import com.d2cmall.buyer.holder.ShowItemHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.ProgressCallBack;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ProgressDialog;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayer;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/7 20:28
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MyFollowFragment extends BaseFragment {

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

    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;

    private List<ShareBean.DataEntity.MemberSharesEntity.ListEntity> showList;
    public List<UnFocusMemberBean.DataBean.ActiveMemberBean> unFocusBean;
    private ShowAdapter showAdapter;

    private int lastPosition = -1;
    private int lastOffer;

    private int p = 1;
    private boolean hasNext;
    private boolean isFresh;

    private boolean hasSetAdapter;
    private ProgressDialog progressDialog;
    private Dialog systemProgress;

    public static MyFollowFragment newInstance(List<ShareBean.DataEntity.MemberSharesEntity.ListEntity> list) {
        MyFollowFragment fashionSubFragment = new MyFollowFragment();
        Bundle args = new Bundle();
        args.putSerializable("list", (Serializable) list);
        fashionSubFragment.setArguments(args);
        return fashionSubFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            showList = (List<ShareBean.DataEntity.MemberSharesEntity.ListEntity>) getArguments().getSerializable("list");
        }
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_refresh_recycleview, container, false);
    }

    @Override
    public void prepareView() {
        ptr.setHeadLabel(getString(R.string.label_d2c_go));
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }
        });
        recycleView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                if (holder instanceof ShowItemHolder) {
                    ShowItemHolder showItemHolder = (ShowItemHolder) holder;
                    NiceVideoPlayer niceVideoPlayer = showItemHolder.niceVideoPlayer;
                    if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                    }
                }
            }
        });
        progressDialog = new ProgressDialog(mContext);
        systemProgress = DialogUtil.createLoadingDialog(mContext);
        virtualLayoutManager = new VirtualLayoutManager(getActivity());
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        showAdapter = new ShowAdapter(getActivity(), showList, true, false);
        showAdapter.setProgressCallBack(new ProgressCallBack() {
            @Override
            public void setProgress(int progress, boolean isShare) {
                if (progress == 100) {
                    progressDialog.setProgress(0);
                    progressDialog.dismiss();
                    if (isShare) {
                        new android.support.v7.app.AlertDialog.Builder(mContext)
                                .setMessage(R.string.msg_go_wx)
                                .setPositiveButton("去上传", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        PackageManager packageManager = mContext.getPackageManager();
                                        String packageName = "com.tencent.mm";
                                        Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(packageName);
                                        if (launchIntentForPackage != null)
                                            startActivity(launchIntentForPackage);
                                        else
                                            Util.showToast(mContext, "未安装微信");
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                    }
                } else {
                    if (!progressDialog.isShowing()) {
                        progressDialog.show();
                    }
                    progressDialog.setProgress(progress);
                }

            }

            @Override
            public void letProgressGone() {
                systemProgress.dismiss();
            }

            @Override
            public void letProgressVisible() {
                systemProgress.show();
            }
        });
        showAdapter.hasPadding = true;
        delegateAdapter.addAdapter(showAdapter);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        if (lastPosition >= 0) {
            virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
        }
        initListener();
        hasSetAdapter = true;
    }

    private void initListener() {
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
                        if (last > 0 && last > delegateAdapter.getItemCount() - 3 && hasNext) {
                            p++;
                            loadData();
                        }
                }
            }
        });
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgHint.setVisibility(View.GONE);
                btnReload.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                loadData();
                loadUnFocus();
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

    @Override
    public void doBusiness() {
        if (showList.size() == 0 || unFocusBean == null) {
            progressBar.setVisibility(View.VISIBLE);
            if (showList.size() == 0) {
                loadData();
            }
            if (unFocusBean == null) {
                loadUnFocus();
            }
        } else {
            if (!hasSetAdapter) {
                delegateAdapter.addAdapter(showAdapter);
                showAdapter.notifyDataSetChanged();
                if (lastPosition >= 0) {
                    virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
                }
                hasSetAdapter = true;
            }
        }
    }

    private void loadData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.GET_MY_FOLLOW_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MyFollowBean>() {
            @Override
            public void onResponse(MyFollowBean showListBean) {
                progressBar.setVisibility(View.GONE);
                if (isFresh) {
                    ptr.refreshComplete();
                }
                if (p == 1) {
                    showList.clear();
                }
                hasNext = showListBean.getData().getMemberShares().isNext();
                if (showListBean.getData().getMemberShares() != null && showListBean.getData().getMemberShares().getList() != null && showListBean.getData().getMemberShares().getList().size() > 0) {
                    showList.addAll(showListBean.getData().getMemberShares().getList());
                }
                showAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                if (isFresh) {
                    ptr.refreshComplete();
                }
                imgHint.setVisibility(View.VISIBLE);
                imgHint.setImageResource(R.mipmap.ic_no_net);
                btnReload.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loadUnFocus() {
        SimpleApi simpleApi = new SimpleApi();
        simpleApi.setInterPath(Constants.GET_UN_FOCUS_MEMBER);
        D2CApplication.httpClient.loadingRequest(simpleApi, new BeanRequest.SuccessListener<UnFocusMemberBean>() {
            @Override
            public void onResponse(UnFocusMemberBean response) {
                progressBar.setVisibility(View.GONE);
                if (response.getData().getActiveMember() != null && response.getData().getActiveMember().size() > 0) {
                    unFocusBean = response.getData().getActiveMember();
                    showAdapter.unFocusBean = unFocusBean;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void refresh() {
        if (Session.getInstance().getUserFromFile(getActivity()) == null) {
            Util.showToast(getActivity(), "请先登录");
            ptr.refreshComplete();
            return;
        }
        p = 1;
        loadData();
        loadUnFocus();
        isFresh = true;
    }

    @Override
    public void releaseOnInVisible() {
        if (delegateAdapter != null) {
            delegateAdapter.removeAdapter(showAdapter);
            hasSetAdapter = false;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onDestroyView() {
        showAdapter = null;
        delegateAdapter = null;
        super.onDestroyView();
    }
}
