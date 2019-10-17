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
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.MyFollowAdapter;
import com.d2cmall.buyer.api.MemberIdApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.FollowsBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by rookie on 2017/9/14.
 */

public class MyFollowsActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.dividing)
    View dividing;
    @Bind(R.id.tag)
    View tag;
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
    private DelegateAdapter mDelegateAdapter;
    private boolean hasNext;
    private VirtualLayoutManager mLinearLayoutManager;
    private List<FollowsBean.DataBean.MyFollowsBean.ListBean> listEntities;
    private int currentPage=1;
    private long memberId;
    private MyFollowAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans1);
        ButterKnife.bind(this);
        nameTv.setText("关注列表");
        memberId = getIntent().getLongExtra("memberId", -1);
        listEntities = new ArrayList<>();
        doBusiness();
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return false;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = mLinearLayoutManager.findLastVisibleItemPosition();
                        if (last > myAdapter.getItemCount() - 3 && hasNext) {
                            currentPage++;
                            requestFansTask();
                        }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void requestFansTask() {
        MemberIdApi api = new MemberIdApi();
        api.setInterPath(Constants.MY_FOLLOW_URL);
        if (memberId > -1) {
            api.setMemberId(memberId);
        }
        api.setP(currentPage);
        api.setPageSize(12);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<FollowsBean>() {
            @Override
            public void onResponse(FollowsBean followsBean) {
                if (currentPage == 1) {
                    listEntities.clear();
                }
                int size = followsBean.getData().getMyFollows().getList().size();
                if (size > 0) {
                    listEntities.addAll(followsBean.getData().getMyFollows().getList());
                }
                myAdapter.notifyDataSetChanged();
                hasNext = followsBean.getData().getMyFollows().isNext();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(MyFollowsActivity.this, Util.checkErrorType(error));
            }
        });
    }


    private void doBusiness() {
        //
        myAdapter = new MyFollowAdapter(listEntities, this);
        mLinearLayoutManager = new VirtualLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mDelegateAdapter = new DelegateAdapter(mLinearLayoutManager, true);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0, 2);
        mRecyclerView.setRecycledViewPool(recycledViewPool);
        mDelegateAdapter.addAdapter(myAdapter);
        mRecyclerView.setAdapter(mDelegateAdapter);
        requestFansTask();
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}
