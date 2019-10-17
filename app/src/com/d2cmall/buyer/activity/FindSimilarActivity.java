package com.d2cmall.buyer.activity;

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
import com.d2cmall.buyer.adapter.FindSimilarGridAdapter;
import com.d2cmall.buyer.adapter.FindSimilarTopAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.FindSimilarBean;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2018/3/21.
 */

public class FindSimilarActivity extends BaseActivity {

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
    private long id;
    private GoodsBean.DataBean.ProductsBean.ListBean data;
    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;
    private FindSimilarTopAdapter topAdapter;
    private FindSimilarGridAdapter gridAdapter;
    private ArrayList<GoodsBean.DataBean.ProductsBean.ListBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_similar);
        ButterKnife.bind(this);
        id = getIntent().getLongExtra("id", 0);
        data = (GoodsBean.DataBean.ProductsBean.ListBean) getIntent().getSerializableExtra("data");
        nameTv.setText("相似商品");
        list=new ArrayList<>();
        virtualLayoutManager = new VirtualLayoutManager(this);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        topAdapter = new FindSimilarTopAdapter(this, data);
        int itemWidth = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(48)) / 2;
        gridAdapter = new FindSimilarGridAdapter(this, list, itemWidth);
        delegateAdapter.addAdapter(topAdapter);
        delegateAdapter.addAdapter(gridAdapter);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        initData();
    }

    private void initData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.FIND_SAME_PRODUCT, String.valueOf(id), String.valueOf(40)));
        api.setMethod(BaseApi.Method.GET);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<FindSimilarBean>() {
            @Override
            public void onResponse(FindSimilarBean response) {
                if (response.getList() != null && response.getList().size() > 0) {
                    list.addAll(response.getList());
                }
                gridAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(FindSimilarActivity.this,Util.checkErrorType(error));
            }
        });
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}
