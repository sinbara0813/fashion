package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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
import com.d2cmall.buyer.adapter.PublishTopicListAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.TopicBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/9/4.
 * 发布动态选取话题页面
 */

public class TopicListActivity extends BaseActivity {
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private DelegateAdapter delegateAdapter;
    private List<TopicBean.DataBean.TopicsBean.ListBean> list;
    private PublishTopicListAdapter publishTopicListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);
        ButterKnife.bind(this);
        nameTv.setText("添加话题");
        list=new ArrayList<>();
        initRecyclerView();
        loadData();
    }

    private void loadData() {
        SimpleApi api=new SimpleApi();
        api.setInterPath(Constants.TOPIC_LIST_REAL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<TopicBean>() {
            @Override
            public void onResponse(TopicBean response) {
                list.addAll(response.getData().getTopics().getList());
                publishTopicListAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(TopicListActivity.this,Util.checkErrorType(error));
            }
        });
    }

    private void initRecyclerView() {
        publishTopicListAdapter=new PublishTopicListAdapter(this,list);
        VirtualLayoutManager virtualLayout=new VirtualLayoutManager(this);
        recycleView.setLayoutManager(virtualLayout);
        delegateAdapter=new DelegateAdapter(virtualLayout,true);
        delegateAdapter.addAdapter(publishTopicListAdapter);
        recycleView.setAdapter(delegateAdapter);
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}
