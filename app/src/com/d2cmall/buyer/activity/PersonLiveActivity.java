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
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.LiveAdapter;
import com.d2cmall.buyer.adapter.LivePreviewAdapter;
import com.d2cmall.buyer.api.LiveListApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.LiveListBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by rookie on 2017/9/11.
 */

public class PersonLiveActivity extends BaseActivity {

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
    private List<LiveListBean.DataBean.LivesBean.ListBean> liveList;
    private List<LiveListBean.DataBean.LivesBean.ListBean> previewList;
    private List<LiveListBean.DataBean.LivesBean.ListBean> oldLiveList;
    private List<LiveListBean.DataBean.LivesBean.ListBean> allLiveList;
    private long memberId;
    private UserBean.DataEntity.MemberEntity user;
    private int p = 1;
    private boolean isEnd;
    private boolean isLoad;
    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;
    private LiveAdapter liveAdapter;
    private LivePreviewAdapter livePreviewAdapter;
    private LiveAdapter oldLiveAdapter;
    private boolean hasNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recyclerview);
        ButterKnife.bind(this);
        memberId = getIntent().getLongExtra("memberId", 0);
        nameTv.setText("直播");
        user = Session.getInstance().getUserFromFile(this);
        liveList = new ArrayList<>();
        previewList = new ArrayList<>();
        oldLiveList = new ArrayList<>();
        allLiveList = new ArrayList<>();
        ptr.setHeadLabel(getString(R.string.label_d2c_go));
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptr.refreshComplete();
            }
        });
        virtualLayoutManager = new VirtualLayoutManager(this);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        recycleView.setLayoutManager(virtualLayoutManager);
        List<DelegateAdapter.Adapter> list = new ArrayList<>();
        liveAdapter = new LiveAdapter(this, liveList, 1);
        list.add(liveAdapter);
        livePreviewAdapter = new LivePreviewAdapter(this, previewList);
        list.add(livePreviewAdapter);
        oldLiveAdapter = new LiveAdapter(this, oldLiveList, 2);
        list.add(oldLiveAdapter);
        delegateAdapter.setAdapters(list);
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
                        if (last > delegateAdapter.getItemCount() - 3 && hasNext) {
                            p++;
                            requestExploreInfoTask();
                        }
                }
            }
        });
        requestExploreInfoTask();
    }

    private void requestExploreInfoTask() {
        LiveListApi api = new LiveListApi();
        api.setP(p);
        api.setPageSize(20);
        api.setMemberId(memberId);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<LiveListBean>() {
            @Override
            public void onResponse(LiveListBean exploreInfoBean) {
                ptr.refreshComplete();
                if (p == 1) {
                    allLiveList.clear();
                }
                if (exploreInfoBean.getData().getLives().getList() != null && exploreInfoBean.getData().getLives().getList().size() > 0) {
                    allLiveList.addAll(exploreInfoBean.getData().getLives().getList());
                }
                for (int i = 0; i < allLiveList.size(); i++) {
                    switch (allLiveList.get(i).getStatus()) {
                        case 0:
                            previewList.add(allLiveList.get(i));
                            break;
                        case 8:
                            oldLiveList.add(allLiveList.get(i));
                            break;
                        case 4:
                            liveList.add(allLiveList.get(i));
                            break;
                    }
                }
                hasNext = exploreInfoBean.getData().getLives().isNext();
                isLoad = false;
                oldLiveAdapter.notifyDataSetChanged();
                liveAdapter.notifyDataSetChanged();
                livePreviewAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ptr.refreshComplete();
                isLoad = false;
                Util.showToast(PersonLiveActivity.this, Util.checkErrorType(error));
            }
        });
    }

    @OnClick({R.id.back_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
        }
    }
}
