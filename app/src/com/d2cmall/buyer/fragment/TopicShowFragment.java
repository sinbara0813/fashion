package com.d2cmall.buyer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.TopicDetailActivity;
import com.d2cmall.buyer.adapter.TpoicShowAdapter;
import com.d2cmall.buyer.api.TopicDetailApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.TopicDetailBean;
import com.d2cmall.buyer.holder.ShowItemHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayer;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static com.d2cmall.buyer.Constants.TOPIC_DETAIL_URL;

/**
 * Created by rookie on 2017/8/12.
 */

public class TopicShowFragment extends BaseFragment {


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
    @Bind(R.id.ll_empty)
    LinearLayout llEmpty;
    private long id;
    private int p = 1;
    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;
    private TpoicShowAdapter showAdapter;
    private List<TopicDetailBean.DataBean.MemberSharesBean.ListBean> showList;
    private int lastPosition = -1;
    private int lastOffer;

    private boolean hasNext;
    private boolean isNew;
    private float lastY;
    private float viewSlop;
    //记录手指是否向上滑动
    private boolean isUpSlide;
    //我要加入是否是隐藏状态
    private boolean isToolHide;

    public static TopicShowFragment newInstance(long id, boolean isNew) {
        TopicShowFragment fashionSubFragment = new TopicShowFragment();
        Bundle args = new Bundle();
        args.putLong("id", id);
        args.putBoolean("hasHead", isNew);
        fashionSubFragment.setArguments(args);
        return fashionSubFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getLong("id");
        isNew = getArguments().getBoolean("hasHead");
        viewSlop = ViewConfiguration.get(getActivity()).getScaledTouchSlop();
        showList = new ArrayList<>(); //需要缓存的数据在此处实例
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_show, container, false);
    }

    @Override
    public void prepareView() {
        ptr.setHeadLabel(getString(R.string.label_d2c_go));
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return ((TopicDetailActivity) getActivity()).getCanRefresh();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }
        });
//        recycleView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        lastY = event.getY();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        float disY = event.getY() - lastY;
//                        //垂直方向滑动
//                        if (Math.abs(disY) > viewSlop) {
//                            //是否向上滑动
//                            isUpSlide = disY > 0;
//                            //实现底部tools的显示与隐藏
//                            if (isUpSlide) {
//                                if (isToolHide)
//                                    showView();
//                            } else {
//                                if (!isToolHide)
//                                    hideView();
//                            }
//                        }
//                        break;
//                }
//                return false;
//            }
//        });
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
        virtualLayoutManager = new VirtualLayoutManager(getActivity());
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        showAdapter = new TpoicShowAdapter(getActivity(), showList);
        delegateAdapter.addAdapter(showAdapter);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        if (lastPosition >= 0) {
            virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
        }
        virtualLayoutManager.scrollToPositionWithOffset(1, 0);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if(dy<0){//上滑
//                    if (isToolHide)
//                        showView();
//                }else {//下滑
//                    if (!isToolHide)
//                        hideView();
//                }
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
    }

    public void refresh() {
        p = 1;
        loadData();
    }

    private void showView() {
        ((TopicDetailActivity) getActivity()).showView();
        isToolHide = false;
    }

    private void hideView() {
        ((TopicDetailActivity) getActivity()).hideView();
        isToolHide = true;
    }

    private void loadData() {
        TopicDetailApi api = new TopicDetailApi();
        api.setPage(p);
        api.setInterPath(String.format(TOPIC_DETAIL_URL, id));
        if (isNew) {
            api.setOrderType("new");
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<TopicDetailBean>() {
            @Override
            public void onResponse(TopicDetailBean topicDetailBean) {
                ptr.refreshComplete();
                if (p == 1) {
                    showAdapter.setUser();
                    showList.clear();
                }
                if (topicDetailBean.getData().getMemberShares().getList() != null) {
                    showList.addAll(topicDetailBean.getData().getMemberShares().getList());
                }
                if (showList.size() == 0) {
                    recycleView.setVisibility(View.GONE);
                    llEmpty.setVisibility(View.VISIBLE);
                    imgHint.setVisibility(View.VISIBLE);
                    imgHint.setImageResource(R.mipmap.ic_empty_my_show);
                } else {
                    recycleView.setVisibility(View.VISIBLE);
                    imgHint.setVisibility(View.GONE);
                    llEmpty.setVisibility(View.GONE);
                    btnReload.setVisibility(View.GONE);
                }
                showAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (ptr != null) {
                    ptr.refreshComplete();
                }
                recycleView.setVisibility(View.GONE);
                imgHint.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.VISIBLE);
                imgHint.setImageResource(R.mipmap.ic_no_net);
                btnReload.setVisibility(View.VISIBLE);
                Util.showToast(getActivity(), Util.checkErrorType(error));
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
        if (showList.size() == 0) {
            loadData();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @OnClick(R.id.btn_reload)
    public void onViewClicked() {
        loadData();
    }
}
