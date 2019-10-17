package com.d2cmall.buyer.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.Decoration.StickyDecoration;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.FootMarkAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.FootMarkBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.GroupListener;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static com.d2cmall.buyer.Constants.GET_FOOTMARK_PRODUCT_LIST;

/**
 * Created by rookie on 2018/3/2.
 */

public class FootMarkActivity extends BaseActivity {


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
    @Bind(R.id.iv_error)
    ImageView ivError;
    @Bind(R.id.tv_error)
    TextView tvError;
    @Bind(R.id.ll_error)
    LinearLayout llError;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;
    private ArrayList<FootMarkBean.DataBean.ProductsBean.ListBean> list;
    private FootMarkAdapter adapter;
    private LinearLayoutManager manager;
    private boolean hasNext;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footmark_layout);
        ButterKnife.bind(this);
        nameTv.setText("浏览足迹");
        list = new ArrayList<>();
        initView();
        getFootMarkData(true);
    }

    private void initView() {
        adapter = new FootMarkAdapter(list, this);
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        StickyDecoration decoration = StickyDecoration.Builder
                .init(new GroupListener() {
                    @Override
                    public String getGroupName(int position) {
                        //组名回调
                        if (list.size() > position) {
                            //获取组名，用于判断是否是同一组
                            return list.get(position).getEventDay();
                        }
                        return null;
                    }
                })
                .setGroupBackground(Color.parseColor("#FFFFFF"))        //背景色
                .setGroupHeight(ScreenUtil.dip2px(35))     //高度
                .setDivideColor(Color.parseColor("#FFFFFF"))            //分割线颜色
                .setDivideHeight(ScreenUtil.dip2px(1))     //分割线高度 (默认没有分割线)
                .setGroupTextColor(Color.BLACK)                                    //字体颜色 （默认）
                .setGroupTextSize(ScreenUtil.sp2px(16))    //字体大小
                .setTextSideMargin(ScreenUtil.dip2px(16))  // 边距   靠左时为左边距  靠右时为右边距
                .build();
        recycleView.setLayoutManager(manager);
        recycleView.setAdapter(adapter);
        recycleView.addItemDecoration(decoration);
        ptr.setHeadLabel(getString(R.string.label_d2c_go));
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                currentPage=1;
                getFootMarkData(false);
            }
        });
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = manager.findLastVisibleItemPosition();
                        if (last > adapter.getItemCount() - 3 && hasNext) {
                            currentPage++;
                            getFootMarkData(false);
                        }
                }
            }
        });
    }


    private void getFootMarkData(final boolean isFirstTime) {
        if (isFirstTime) {
            progressBar.setVisibility(View.VISIBLE);
            recycleView.setVisibility(View.GONE);
        }
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.GET);
        api.setInterPath(GET_FOOTMARK_PRODUCT_LIST);
        api.setP(currentPage);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<FootMarkBean>() {
            @Override
            public void onResponse(FootMarkBean response) {
                ptr.refreshComplete();
                if (isFirstTime) {
                    progressBar.setVisibility(View.GONE);
                }
                if (currentPage == 1) {
                    list.clear();
                }
                hasNext = response.getData().getProducts().isNext();
                if (response.getData().getProducts().getList() != null && response.getData().getProducts().getList().size() > 0) {
                    list.addAll(response.getData().getProducts().getList());
                    adapter.notifyDataSetChanged();
                    if (isFirstTime) {
                        recycleView.setVisibility(View.VISIBLE);
                        llError.setVisibility(View.GONE);
                    }
                } else {
                    if (isFirstTime) {
                        recycleView.setVisibility(View.GONE);
                        llError.setVisibility(View.VISIBLE);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ptr.refreshComplete();
                if (isFirstTime) {
                    progressBar.setVisibility(View.GONE);
                    recycleView.setVisibility(View.GONE);
                    llError.setVisibility(View.VISIBLE);
                }
                Util.showToast(FootMarkActivity.this, Util.checkErrorType(error));
            }
        });
    }

    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }


    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}
