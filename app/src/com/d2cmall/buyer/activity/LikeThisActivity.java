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
import com.d2cmall.buyer.adapter.BrandFollowAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.BrandFollowBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.ShowLikeBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static com.d2cmall.buyer.Constants.BRAND_FOLLOW_URL;
import static com.d2cmall.buyer.Constants.SHOW_LIKE_LIST;

/**
 * Created by rookie on 2017/9/18.
 * 关注列表以及点赞列表
 */

public class LikeThisActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.dividing)
    View dividing;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;
    private DelegateAdapter mDelegateAdapter;
    private boolean hasNext;
    private VirtualLayoutManager virtualayout;
    private List<Object> listEntities;
    private int currentPage;
    private BrandFollowBean brandFollowBean;
    private int type = -1;
    private BrandFollowAdapter brandAdapter;
    private long showId = -1;
    private int brandId = -1;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans1);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", -1);
        init();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = virtualayout.findLastVisibleItemPosition();
                        if (last > brandAdapter.getItemCount() - 3 && hasNext) {
                            page++;
                            if (type == 0) {
                                initBrandIdFollow();
                            } else {
                                initShowLikeData();
                            }
                        }
                }
            }
        });
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return false;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

            }
        });
        if (type == 0) {//品牌点赞列表
            brandId = getIntent().getIntExtra("brandId", 0);
            nameTv.setText("关注列表");
            initBrandIdFollow();

        } else {//买家秀点赞列表
            nameTv.setText("点赞列表");
            showId = getIntent().getLongExtra("showId", -1);
            initShowLikeData();
        }
    }

    private void initBrandIdFollow() {
        SimpleApi api = new SimpleApi();
        api.setP(page);
        api.setInterPath(String.format(BRAND_FOLLOW_URL, brandId));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BrandFollowBean>() {
            @Override
            public void onResponse(BrandFollowBean response) {
                ptr.refreshComplete();
                if (page == 1) {
                    listEntities.clear();
                }
                hasNext = response.getData().getAttentions().isNext();
                listEntities.addAll(response.getData().getAttentions().getList());
                brandAdapter.notifyDataSetChanged();
                if (listEntities.size() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    imgHint.setVisibility(View.VISIBLE);
                    imgHint.setImageResource(R.mipmap.ic_empty_follow);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    imgHint.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void initShowLikeData() {
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.GET);
        api.setInterPath(String.format(SHOW_LIKE_LIST, showId));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ShowLikeBean>() {
            @Override
            public void onResponse(ShowLikeBean response) {
                ptr.refreshComplete();
                if (response.getData().getLikes().getList() != null && response.getData().getLikes().getList().size() > 0) {
                    listEntities.addAll(response.getData().getLikes().getList());
                    recyclerView.setVisibility(View.VISIBLE);
                    imgHint.setVisibility(View.GONE);
                    btnReload.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    imgHint.setVisibility(View.VISIBLE);
                    imgHint.setImageResource(R.mipmap.ic_empty_follow);
                }
                brandAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ptr.refreshComplete();
                Util.showToast(LikeThisActivity.this, Util.checkErrorType(error));
                recyclerView.setVisibility(View.GONE);
                imgHint.setVisibility(View.VISIBLE);
                btnReload.setVisibility(View.VISIBLE);
                imgHint.setImageResource(R.mipmap.ic_no_net);
            }
        });
    }

    private void init() {
        listEntities = new ArrayList<>();
        virtualayout = new VirtualLayoutManager(this);
        mDelegateAdapter = new DelegateAdapter(virtualayout, true);
        recyclerView.setLayoutManager(virtualayout);
        brandAdapter = new BrandFollowAdapter(listEntities, this);
        mDelegateAdapter.addAdapter(brandAdapter);
        recyclerView.setAdapter(mDelegateAdapter);
    }


    @OnClick({R.id.back_iv, R.id.btn_reload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_reload:
                page = 1;
                if (type == 0) {
                    initBrandIdFollow();
                } else {
                    initShowLikeData();
                }
                break;
        }
    }

    //用户详情也点击关注按钮,刷新列表数据
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(GlobalTypeBean event) {

        int type = event.getType();
        if (type == Constants.GlobalType.LIKETHIS_LIST_CHANGE) {
            initShowLikeData();
        }
    }
}
