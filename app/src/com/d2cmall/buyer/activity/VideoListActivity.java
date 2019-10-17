package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.VideoListAdapter;
import com.d2cmall.buyer.api.SharesApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.ShareBean;
import com.d2cmall.buyer.calculator.DefaultVisibilityCalculator;
import com.d2cmall.buyer.holder.ShowItemHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.scrollUtils.ItemsPositionGetter;
import com.d2cmall.buyer.scrollUtils.RecyclerViewItemsPositionGetter;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayer;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.d2cmall.buyer.util.Util.INFINITE;

/**
 * Created by rookie on 2017/9/27.
 */

public class VideoListActivity extends BaseActivity {

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
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;
    private VideoListAdapter videoListAdapter;
    private List<ShareBean.DataEntity.MemberSharesEntity.ListEntity> showList;
    private int id = -1;
    private String title= "";
    private int p = 1;
    private boolean isFresh,hasNext;
    private VirtualLayoutManager linearLayoutManager;
    private DefaultVisibilityCalculator defaultVisibilityCalculator;
    private ItemsPositionGetter mItemsPositionGetter;
    private int mScrollState = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        nameTv.setText(title);
        showList = new ArrayList<>(); //需要缓存的数据在此处实例
        defaultVisibilityCalculator=new DefaultVisibilityCalculator();
        linearLayoutManager=new VirtualLayoutManager(this);
        LinearLayoutHelper layoutHelper=new LinearLayoutHelper();
        layoutHelper.setMarginTop(ScreenUtil.dip2px(16));
        videoListAdapter=new VideoListAdapter(this,showList,layoutHelper);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(videoListAdapter);
        mItemsPositionGetter = new RecyclerViewItemsPositionGetter(linearLayoutManager, recycleView);
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
        recycleView.setHasFixedSize(true);
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
        if (showList.size() == 0) {
            progressBar.setVisibility(View.VISIBLE);
            loadData();
        }
        initListener();
    }

    public void refresh() {
        p = 1;
        loadData();
        isFresh=true;
    }

    private void initListener(){
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                defaultVisibilityCalculator.onScroll(mItemsPositionGetter,
                        linearLayoutManager.findFirstVisibleItemPosition(),
                        linearLayoutManager.findLastVisibleItemPosition() ,mScrollState);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mScrollState=newState;
                if (Util.getNetWorkType(VideoListActivity.this)==INFINITE&&mScrollState==SCROLL_STATE_IDLE){
                    defaultVisibilityCalculator.onScrollStateIdle(mItemsPositionGetter,linearLayoutManager.findFirstVisibleItemPosition(),linearLayoutManager.findLastVisibleItemPosition(),newState);
                }
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = linearLayoutManager.findLastVisibleItemPosition();
                        if (last > videoListAdapter.getItemCount() - 3 && hasNext) {
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
            }
        });
    }

    private void loadData() {
        SharesApi api = new SharesApi();
        api.setTagId(id);
        api.setP(p);
        api.setPageSize(10);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ShareBean>() {
            @Override
            public void onResponse(ShareBean showListBean) {
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
                videoListAdapter.notifyDataSetChanged();
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

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }

    @OnClick({R.id.back_iv, R.id.btn_reload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_reload:
                break;
        }
    }

    @Override
    protected void onResume() {
        recycleView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Util.getNetWorkType(VideoListActivity.this)==INFINITE){
                    defaultVisibilityCalculator.onScrollStateIdle(mItemsPositionGetter,linearLayoutManager.findFirstVisibleItemPosition(),linearLayoutManager.findLastVisibleItemPosition(),0);
                }
            }
        },1000);
        super.onResume();
    }
}
