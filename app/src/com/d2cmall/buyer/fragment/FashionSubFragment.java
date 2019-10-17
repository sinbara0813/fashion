package com.d2cmall.buyer.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.SearchAdressActivity;
import com.d2cmall.buyer.activity.VideoEditActivity;
import com.d2cmall.buyer.adapter.FashionHeadAdapter;
import com.d2cmall.buyer.adapter.ShowAdapter;
import com.d2cmall.buyer.api.SharesApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.AddressEntity;
import com.d2cmall.buyer.bean.FashionShowHeadBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.ShareBean;
import com.d2cmall.buyer.bean.TopicBean;
import com.d2cmall.buyer.bean.UnFocusMemberBean;
import com.d2cmall.buyer.calculator.DefaultVisibilityCalculator;
import com.d2cmall.buyer.holder.ShowItemHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.ProgressCallBack;
import com.d2cmall.buyer.scrollUtils.ItemsPositionGetter;
import com.d2cmall.buyer.scrollUtils.RecyclerViewItemsPositionGetter;
import com.d2cmall.buyer.shareplatform.WxHandle;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.InitializeService;
import com.d2cmall.buyer.util.PictureUtils;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.CustomProgress;
import com.d2cmall.buyer.widget.ProgressDialog;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayer;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.d2cmall.buyer.Constants.GlobalType.SHOW_DELETE;
import static com.d2cmall.buyer.util.Util.INFINITE;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/31 16:48
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FashionSubFragment extends BaseFragment {

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

    private FashionShowHeadBean fashionShowHeadBean;
    private FashionHeadAdapter fashionHeadAdapter;

    private int lastPosition = -1;
    private int lastOffer;

    private long id; //买家秀的tagId
    private int p = 1;
    private boolean hasNext; //是否有下一页数据
    private boolean hasHead; //是否有头部
    private boolean hasFocus; //是否中间插关注数据
    private boolean isFresh;

    private boolean hasSetAdapter=true; //是否设置过设配器

    private String videoUrl;
    private String videoContent;
    private long duration;
    public static boolean isUpload;
    TopicBean.DataBean.TopicsBean.ListBean topicBean;
    AddressEntity addressEntity;
    private ProgressDialog progressDialog;
    private Dialog systemProgress;

    public static FashionSubFragment newInstance(long id, boolean hasHead, boolean hasFocus) {
        FashionSubFragment fashionSubFragment = new FashionSubFragment();
        Bundle args = new Bundle();
        args.putLong("id", id);
        args.putBoolean("hasHead", hasHead);
        args.putBoolean("hasFocus", hasFocus);
        fashionSubFragment.setArguments(args);
        return fashionSubFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
            hasHead = getArguments().getBoolean("hasHead");
            hasFocus = getArguments().getBoolean("hasFocus");
        }
        progressDialog = new ProgressDialog(mContext);
        systemProgress = DialogUtil.createLoadingDialog(mContext);
        showList = new ArrayList<>(); //需要缓存的数据在此处实例
        unFocusBean = new ArrayList<>();
        EventBus.getDefault().register(this);
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
        virtualLayoutManager = new VirtualLayoutManager(mContext);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        viewPool.setMaxRecycledViews(1, 0);
        viewPool.setMaxRecycledViews(2, 0);
        viewPool.setMaxRecycledViews(3, 0);
        viewPool.setMaxRecycledViews(4, 0);
        viewPool.setMaxRecycledViews(5, 0);
        viewPool.setMaxRecycledViews(6, 1);
        recycleView.setRecycledViewPool(viewPool);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        if (!hasSetAdapter){
            addAdapter();
        }
        initListener();
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
                        if (last > delegateAdapter.getItemCount() - 3 && hasNext) {
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
                if (hasHead) {
                    loadHeadData();
                }
                loadData();
                if (hasFocus) {
                    loadUnFocus();
                }
            }
        });
    }

    private void getLastLocation() {
        if (virtualLayoutManager==null)return;
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
        if (showList.size() == 0) {
            progressBar.setVisibility(View.VISIBLE);
            if (hasHead) {
                loadHeadData();
            }
            loadData();
            if (hasFocus) {
                loadUnFocus();
            }
        } else {

        }
    }

    private void addAdapter(){
        if (!hasSetAdapter&&showAdapter!=null) {
            if (hasHead && fashionHeadAdapter != null) {
                delegateAdapter.addAdapter(fashionHeadAdapter);
            }
            delegateAdapter.addAdapter(showAdapter);
            showAdapter.notifyDataSetChanged();
            if (lastPosition >= 0) {
                virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
            }
            hasSetAdapter = true;
        }
    }

    private void loadHeadData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.GET_SHOW_HEAD);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<FashionShowHeadBean>() {
            @Override
            public void onResponse(FashionShowHeadBean headBean) {
                if (headBean.getData().getTopics().size() > 0) {
                    fashionShowHeadBean = headBean;
                    setHeadAdapter();
                }
            }
        });
    }

    private void setHeadAdapter() {
        if (fashionHeadAdapter == null) {
            fashionHeadAdapter = new FashionHeadAdapter(mContext);
            fashionHeadAdapter.setData(fashionShowHeadBean);
            if (isUpload) {
                fashionHeadAdapter.duration = duration;
                fashionHeadAdapter.url = videoUrl;
                fashionHeadAdapter.content = videoContent;
                fashionHeadAdapter.topicBean = topicBean;
                fashionHeadAdapter.addressEntity = addressEntity;
                fashionHeadAdapter.type = 1;
            }
            delegateAdapter.addAdapter(0, fashionHeadAdapter);
        } else {
            if (isUpload) {
                fashionHeadAdapter.duration = duration;
                fashionHeadAdapter.url = videoUrl;
                fashionHeadAdapter.content = videoContent;
                fashionHeadAdapter.topicBean = topicBean;
                fashionHeadAdapter.addressEntity = addressEntity;
                fashionHeadAdapter.type = 1;
            }
            fashionHeadAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe
    public void onEvent(GlobalTypeBean bean) {
        if (bean.getType() == SHOW_DELETE) {
            loadData();
        } else if (bean.getType() == Constants.GlobalType.LOGOUT || bean.getType() == Constants.GlobalType.LOGIN_OK) {//登陆成功刷新下
            if (showAdapter != null) {
                showAdapter.refreshUser();
                refresh();
            }
        }
        if (!isVisibleToUser || !hasHead || fashionHeadAdapter == null) {
            return;
        }
        if (bean.getType() == Constants.GlobalType.UPLOAD_PROGRESS) {
            isUpload = true;
            Object progress = bean.getValue("progress");
            String url = (String) bean.getValue("url");
            fashionHeadAdapter.showProgress = true;
            fashionHeadAdapter.type = 0;
            fashionHeadAdapter.url = url;
            fashionHeadAdapter.progress = (int) progress;
            if (progress instanceof Integer) {
                if (((int) progress) == 100) {
                    recycleView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isUpload = false;
                            fashionHeadAdapter.showProgress = false;
                            fashionHeadAdapter.notifyDataSetChanged();
                        }
                    }, 1000);
                    PictureUtils.deleteFile(new File(VideoEditActivity.mFinalPath));
                }
            }
            if (!isUpload) {
                fashionHeadAdapter.notifyDataSetChanged();
            } else {
                fashionHeadAdapter.updateProgress((Integer) progress);
            }
            isUpload = true;
        } else if (bean.getType() == Constants.GlobalType.UPLOAD_FAIL) { //上传失败 显示重新上传和取消
            isUpload = true;
            final String url = (String) bean.getValue("url");
            final long duration = (long) bean.getValue("duration");
            final String content = (String) bean.getValue("content");
            if (bean.getValue("topicBean") != null) {
                topicBean = (TopicBean.DataBean.TopicsBean.ListBean) bean.getValue("topicBean");
            }
            if (bean.getValue(SearchAdressActivity.ADDRESS) != null) {
                addressEntity = (AddressEntity) bean.getValue(SearchAdressActivity.ADDRESS);
            }
            videoUrl = url;
            videoContent = content;
            this.duration = duration;
            fashionHeadAdapter.duration = duration;
            fashionHeadAdapter.url = url;
            fashionHeadAdapter.content = content;
            fashionHeadAdapter.topicBean = topicBean;
            fashionHeadAdapter.addressEntity = addressEntity;
            fashionHeadAdapter.type = 1;
            fashionHeadAdapter.notifyDataSetChanged();
            ;
        } else if (bean.getType() == Constants.GlobalType.UPLOAD_AGAIN) {
            if (Util.isEmpty(videoUrl)) {
                Util.showToast(mContext, "正在上传中");
                return;
            }
            fashionHeadAdapter.showProgress = false;
            fashionHeadAdapter.notifyDataSetChanged();

            Intent intent = new Intent(mContext, InitializeService.class);
            intent.putExtra("url", videoUrl);
            intent.putExtra("duration", duration);
            intent.putExtra("content", videoContent);
            if (topicBean != null) {
                intent.putExtra("topicBean", topicBean);
            }
            if (addressEntity != null) {
                intent.putExtra(SearchAdressActivity.ADDRESS, addressEntity);
            }
            intent.setAction("upload");
            mContext.startService(intent);
        } else if (bean.getType() == Constants.GlobalType.UPLOAD_DELETE) {
            if (Util.isEmpty(videoUrl)) {
                Util.showToast(mContext, "正在上传中");
                return;
            }
            isUpload = false;
            fashionHeadAdapter.showProgress = false;
            fashionHeadAdapter.notifyDataSetChanged();
        }
    }

    private void loadData() {
        SharesApi api = new SharesApi();
        api.setTagId(id);
        api.setP(p);
        api.setPageSize(10);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ShareBean>() {
            @Override
            public void onResponse(ShareBean showListBean) {
                if (!isVisibleToUser) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                if (isFresh) {
                    ptr.refreshComplete();
                }
                if (p == 1) {
                    showList.clear();
                }
                hasNext = showListBean.getData().getMemberShares().isNext();
                if (showListBean.getData().getMemberShares() != null && showListBean.getData().getMemberShares().getList() != null && showListBean.getData().getMemberShares().getList().size() > 0) {
                    imgHint.setVisibility(View.GONE);
                    imgHint.setImageResource(R.mipmap.ic_no_net);
                    recycleView.setVisibility(View.VISIBLE);
                    btnReload.setVisibility(View.GONE);
                    showList.addAll(showListBean.getData().getMemberShares().getList());
                }
                setShowAdapter();
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
                recycleView.setVisibility(View.GONE);
                btnReload.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setShowAdapter() {
        if (showAdapter == null) {
            showAdapter = new ShowAdapter(mContext, showList, hasFocus, false);
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
            if (!hasHead) {
                showAdapter.hasPadding = true;
            }
            if (unFocusBean != null && unFocusBean.size() > 0) {
                showAdapter.unFocusBean = unFocusBean;
            }
            delegateAdapter.addAdapter(showAdapter);
        } else {
            showAdapter.notifyDataSetChanged();
        }
    }

    private void loadUnFocus() {
        SimpleApi simpleApi = new SimpleApi();
        simpleApi.setInterPath(Constants.GET_UN_FOCUS_MEMBER);
        D2CApplication.httpClient.loadingRequest(simpleApi, new BeanRequest.SuccessListener<UnFocusMemberBean>() {
            @Override
            public void onResponse(UnFocusMemberBean response) {
                if (response.getData().getActiveMember() != null && response.getData().getActiveMember().size() > 0) {
                    unFocusBean.clear();
                    unFocusBean.addAll(response.getData().getActiveMember());
                    if (showAdapter != null) {
                        showAdapter.unFocusBean = unFocusBean;
                    }
                }
            }
        });
    }

    public void refresh() {
        p = 1;
        if (hasHead) {
            loadHeadData();
        }
        loadData();
        if (hasFocus) {
            loadUnFocus();
        }
        isFresh = true;
    }

    @Override
    public void releaseOnInVisible() {
        /*if (delegateAdapter != null) {
            if (hasHead && fashionHeadAdapter != null) {
                delegateAdapter.removeAdapter(fashionHeadAdapter);
            }
            delegateAdapter.removeAdapter(showAdapter);
            hasSetAdapter = false;
        }*/
    }

    @Override
    public void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }


    @Override
    public void onDestroyView() {
        hasSetAdapter=false;
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (showAdapter != null) {
            showAdapter.destroy();
        }
        super.onDestroy();
    }
}
