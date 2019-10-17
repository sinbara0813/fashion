package com.d2cmall.buyer.fragment;

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
import com.d2cmall.buyer.adapter.FashionFocusAdapter;
import com.d2cmall.buyer.adapter.FashionHotAdapter;
import com.d2cmall.buyer.adapter.FocusHeadAdapter;
import com.d2cmall.buyer.adapter.ShowAdapter;
import com.d2cmall.buyer.api.HotApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.FashionShowHeadBean;
import com.d2cmall.buyer.bean.FocusBannerBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.MyFollowBean;
import com.d2cmall.buyer.bean.ShareBean;
import com.d2cmall.buyer.bean.UnFocusMemberBean;
import com.d2cmall.buyer.bean.UnFollowBean;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 作者:Created by sinbara on 2019/1/14.
 * 邮箱:hrb940258169@163.com
 */

public class FashionFocusFragment extends BaseFragment{

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

    private FashionShowHeadBean fashionShowHeadBean;//头部
    private FocusHeadAdapter focusHeadAdapter;

    private List<ShareBean.DataEntity.MemberSharesEntity.ListEntity> showList;//我的关注
    public List<UnFocusMemberBean.DataBean.ActiveMemberBean> unFocusBean;
    private ShowAdapter showAdapter;

    private List<UnFollowBean.DataBean.ActiveMemberBean.ListBean> unFollowList; //推荐设计师达人
    private FashionHotAdapter fashionHotAdapter;

    private int lastPosition = -1;
    private int lastOffer;

    private int p=1;

    private boolean isLogin;
    private boolean isFresh;
    private boolean hasNext;
    private boolean loadD;
    private boolean hasSetAdapter=true; //是否设置过设配器
    private ProgressDialog progressDialog;
    private Dialog systemProgress;

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_refresh_recycleview,container,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unFollowList=new ArrayList<>();
        showList=new ArrayList<>();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        progressDialog = new ProgressDialog(mContext);
        systemProgress = DialogUtil.createLoadingDialog(mContext);
    }

    @Override
    public void prepareView() {
        isLogin= Session.getInstance().getUser()!=null;
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
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        if (!hasSetAdapter){
            addAdapter();
        }
        initListener();
    }

    private void initListener(){
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
                        if (last > delegateAdapter.getItemCount() - 3 ) {
                            if (isLogin){
                                if (hasNext){
                                    p++;
                                    loadFocusData();
                                }
                            }else {
                                if (!loadD){
                                    loadD=true;
                                    loadHot("member");
                                }
                            }
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
                loadHeadData();
                if (isLogin){
                    loadFocusData();
                    loadRecFocus();
                }else {
                    loadHot("designer");
                }
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
        if (fashionShowHeadBean==null){
            loadHeadData();
        }else {

        }
        if (isLogin&&showList.size()==0){
            loadFocusData();
            loadRecFocus();
        }else if (!isLogin&&unFollowList.size()==0){
            loadHot("designer");
        }else {

        }
    }

    private void addAdapter(){
        if (!hasSetAdapter&&fashionShowHeadBean!=null){
            if (focusHeadAdapter!=null){
                delegateAdapter.addAdapter(focusHeadAdapter);
            }
        }
        if (!hasSetAdapter){

            if (fashionHotAdapter!=null&&!isLogin){
                delegateAdapter.addAdapter(fashionHotAdapter);
            }
            if (showAdapter!=null&&isLogin){
                delegateAdapter.addAdapter(showAdapter);
                showAdapter.notifyDataSetChanged();
            }
            if (lastPosition >= 0) {
                virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
            }
            hasSetAdapter=true;
        }
    }

    @Override
    public void releaseOnInVisible() {
        /*if (delegateAdapter!=null){
            if (focusHeadAdapter!=null){
                delegateAdapter.removeAdapter(focusHeadAdapter);
            }
            if (fashionHotAdapter!=null){
                delegateAdapter.removeAdapter(fashionHotAdapter);
            }
            if (showAdapter!=null){
                delegateAdapter.removeAdapter(showAdapter);
            }
            hasSetAdapter=false;
        }*/
    }

    @Subscribe
    public void onEvent(GlobalTypeBean bean) {
        if (bean.getType() == Constants.GlobalType.LOGOUT) {//登出操作
            isLogin=false;
        }else if (bean.getType() == Constants.GlobalType.LOGIN_OK){
            isLogin=true;
        }
    }

    private void refresh(){
        isFresh=true;
        if (isLogin){
            p=1;
            loadFocusData();
        }else {
            loadHot("designer");
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
                    loadBanner();
                }
            }
        });
    }

    private void loadBanner(){
        SimpleApi api = new SimpleApi();
        api.setInterPath("/v3/api/page/submodule/3");
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<FocusBannerBean>() {
            @Override
            public void onResponse(FocusBannerBean response) {
                if (response.getData()!=null&&response.getData().getContent()!=null&&response.getData().getContent().size()>0){
                    fashionShowHeadBean.getData().setContent(response.getData().getContent());
                    setHeadAdapter();
                }
            }
        });
    }

    private void setHeadAdapter(){
        if (focusHeadAdapter==null){
            focusHeadAdapter=new FocusHeadAdapter(mContext);
            focusHeadAdapter.setHeadBean(fashionShowHeadBean);
            if (isVisibleToUser){
                delegateAdapter.addAdapter(0,focusHeadAdapter);
            }
        }
    }

    private void loadHot(String type) {
        HotApi api = new HotApi();
        api.type = type;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<UnFollowBean>() {
            @Override
            public void onResponse(UnFollowBean response) {
                if (isFresh){
                    isFresh=false;
                    ptr.refreshComplete();
                    unFollowList.clear();
                }
                if (response.getData().getActiveMember().getList() != null && response.getData().getActiveMember().getList().size() > 0) {
                    unFollowList.addAll(response.getData().getActiveMember().getList());
                }else {
                    loadHot("member");
                }
                setHotAdapter();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (isFresh){
                    isFresh=false;
                    ptr.refreshComplete();
                }
            }
        });
    }

    private void setHotAdapter(){
        if (fashionHotAdapter==null){
            fashionHotAdapter=new FashionHotAdapter(mContext,unFollowList);
            fashionHotAdapter.setDesignSize(unFollowList.size());
            if (isVisibleToUser){
                if (showAdapter!=null){
                    delegateAdapter.removeAdapter(showAdapter);
                    showAdapter=null;
                }
                delegateAdapter.addAdapter(fashionHotAdapter);
            }
        }else {
            fashionHotAdapter.notifyDataSetChanged();
        }
    }

    private void loadFocusData() {
        SimpleApi api = new SimpleApi();
        api.setP(p);
        api.setInterPath(Constants.GET_MY_FOLLOW_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MyFollowBean>() {
            @Override
            public void onResponse(MyFollowBean showListBean) {
                progressBar.setVisibility(View.GONE);
                if (isFresh) {
                    isFresh=false;
                    ptr.refreshComplete();
                    showList.clear();
                }
                hasNext = showListBean.getData().getMemberShares().isNext();
                if (showListBean.getData().getMemberShares() != null && showListBean.getData().getMemberShares().getList() != null && showListBean.getData().getMemberShares().getList().size() > 0) {
                    showList.addAll(showListBean.getData().getMemberShares().getList());
                }
                setShowAdapter();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                if (isFresh) {
                    isFresh=false;
                    ptr.refreshComplete();
                }
                imgHint.setVisibility(View.VISIBLE);
                imgHint.setImageResource(R.mipmap.ic_no_net);
                btnReload.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setShowAdapter() {
        if (showAdapter == null) {
            showAdapter = new ShowAdapter(mContext, showList, true, false);
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
            if (unFocusBean != null && unFocusBean.size() > 0) {
                showAdapter.unFocusBean = unFocusBean;
            }
            if (isVisibleToUser){
                if (fashionHotAdapter!=null){
                    delegateAdapter.removeAdapter(fashionHotAdapter);
                    fashionHotAdapter=null;
                }
                delegateAdapter.addAdapter(showAdapter);
            }
        } else {
            showAdapter.notifyDataSetChanged();
        }
    }

    private void loadRecFocus() {
        SimpleApi simpleApi = new SimpleApi();
        simpleApi.setInterPath(Constants.GET_UN_FOCUS_MEMBER);
        D2CApplication.httpClient.loadingRequest(simpleApi, new BeanRequest.SuccessListener<UnFocusMemberBean>() {
            @Override
            public void onResponse(UnFocusMemberBean response) {
                progressBar.setVisibility(View.GONE);
                if (response.getData().getActiveMember() != null && response.getData().getActiveMember().size() > 0) {
                    unFocusBean = response.getData().getActiveMember();
                    if (showAdapter!=null){
                        showAdapter.unFocusBean = unFocusBean;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        virtualLayoutManager=null;
        delegateAdapter=null;
        hasSetAdapter=false;
        super.onDestroyView();
    }
}
