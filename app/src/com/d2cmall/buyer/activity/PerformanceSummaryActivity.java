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
import com.d2cmall.buyer.Decoration.PowerfulStickyDecoration;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.PerformanceAdapter;
import com.d2cmall.buyer.api.MonthSummaryApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.MonthSummaryBean;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.PowerGroupListener;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2018/4/17.
 */

public class PerformanceSummaryActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.iv_error)
    ImageView ivError;
    @Bind(R.id.ll_empty)
    LinearLayout ll_empty;
    @Bind(R.id.ll_content)
    RelativeLayout ll_content;
    private PartnerMemberBean.DataBean.PartnerBean partnerBean;
    private PerformanceAdapter performanceAdapter;
    private List<MonthSummaryBean.ListBean> list;
    private DelegateAdapter delegateAdapter;
    private VirtualLayoutManager virtualLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_layout);
        ButterKnife.bind(this);
        partnerBean = Session.getInstance().getPartnerFromFile(this);
        list = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);
        recycleView.setVisibility(View.GONE);

        performanceAdapter = new PerformanceAdapter(this, list);
        virtualLayoutManager = new VirtualLayoutManager(this);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        delegateAdapter.addAdapter(performanceAdapter);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);

        PowerfulStickyDecoration decoration = PowerfulStickyDecoration.Builder
                .init(new PowerGroupListener() {
                    @Override
                    public String getGroupName(int position) {
                        //获取组名，用于判断是否是同一组
                        if (list.size() > position) {
                            return list.get(position).getMonth().substring(0, 4);
                        }
                        return null;
                    }

                    @Override
                    public View getGroupView(int position) {
                        //获取自定定义的组View
                        if (list.size() > position) {
                            View view = getLayoutInflater().inflate(R.layout.layout_summary_title, null, false);
                            ((TextView) view.findViewById(R.id.tv)).setText(list.get(position).getMonth().substring(0, 4));
                            return view;
                        } else {
                            return null;
                        }
                    }
                })   //设置高度
                .build();
        recycleView.addItemDecoration(decoration);

        MonthSummaryApi api = new MonthSummaryApi();
        api.setInterPath(String.format(Constants.BUYER_SUMMARY_MONTH, partnerBean.getId()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MonthSummaryBean>() {
            @Override
            public void onResponse(MonthSummaryBean response) {
                if (response.getList() != null && response.getList().size() > 0) {
                    list.addAll(response.getList());
                    list.get(0).isFirst = true;
                    performanceAdapter.notifyDataSetChanged();
                }
                if (list.size() > 0) {
                    ll_content.setBackgroundColor(getResources().getColor(R.color.bg_color));
                    progressBar.setVisibility(View.GONE);
                    recycleView.setVisibility(View.VISIBLE);
                    ll_empty.setVisibility(View.GONE);
                } else if (list.size() == 0) {
                    ll_content.setBackgroundColor(getResources().getColor(R.color.color_white));
                    progressBar.setVisibility(View.GONE);
                    recycleView.setVisibility(View.GONE);
                    ll_empty.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(PerformanceSummaryActivity.this, Util.checkErrorType(error));
                if (list.size() == 0) {
                    ll_content.setBackgroundColor(getResources().getColor(R.color.color_white));
                    progressBar.setVisibility(View.GONE);
                    recycleView.setVisibility(View.GONE);
                    ll_empty.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
