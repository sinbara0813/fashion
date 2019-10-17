package com.d2cmall.buyer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.TopicBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/8/14.
 * 热门话题页面
 */

public class HotTopicActivity extends BaseActivity {


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
    @Bind(R.id.error_image)
    ImageView errorImage;
    private List<TopicBean.DataBean.TopicsBean.ListBean> list = new ArrayList<>();
    private Context context = this;
    private HotPicAdapter hotPicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_topic);
        ButterKnife.bind(this);
        nameTv.setText("热门话题");
        doBusiness();
    }


    public void doBusiness() {
        initData();
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(this);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager,true);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2, list.size());
        gridLayoutHelper.setGap(Util.dip2px(this, 8));
        gridLayoutHelper.setAutoExpand(false);
        hotPicAdapter = new HotPicAdapter(gridLayoutHelper);
        adapters.add(hotPicAdapter);
        delegateAdapter.setAdapters(adapters);
    }

    private void initData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.TOPIC_LIST_REAL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<TopicBean>() {
            @Override
            public void onResponse(TopicBean response) {
                if(response.getData().getTopics().getList()!=null&&response.getData().getTopics().getList().size()>0){
                    list.addAll(response.getData().getTopics().getList());
                    errorImage.setVisibility(View.GONE);
                    recycleView.setVisibility(View.VISIBLE);
                }else {
                    errorImage.setVisibility(View.VISIBLE);
                    recycleView.setVisibility(View.GONE);
                }
                hotPicAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorImage.setVisibility(View.VISIBLE);
                errorImage.setImageResource(R.mipmap.ic_no_net);
                recycleView.setVisibility(View.GONE);
                Util.showToast(HotTopicActivity.this, Util.checkErrorType(error));
            }
        });
    }


    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }

    private class HotPicAdapter extends DelegateAdapter.Adapter {
        LayoutHelper layoutHelper;

        public HotPicAdapter(LayoutHelper layoutHelper) {
            this.layoutHelper = layoutHelper;
        }

        @Override
        public LayoutHelper onCreateLayoutHelper() {
            return layoutHelper;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.layout_hot_topic_item, parent, false);
            return new HotTopicViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            HotTopicViewHolder viewHolder = (HotTopicViewHolder) holder;
            viewHolder.setIsRecyclable(false);
            if (position == 0 || position == 1) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, ScreenUtil.dip2px(16), 0, 0);//4个参数按顺序分别是左上右下
                viewHolder.itemView.setLayoutParams(layoutParams);
            }
            UniversalImageLoader.displayImage(HotTopicActivity.this, list.get(position).getPic(), viewHolder.iv);
            viewHolder.tv_people.setText(list.get(position).getShareCount() + "人参与");
            viewHolder.tv_topic_name.setText("#" + list.get(position).getTitle() + "#");
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TopicDetailActivity.class);
                    intent.putExtra("id", (long) list.get(position).getId());
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class HotTopicViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv_topic_name, tv_people;

        HotTopicViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_topic_image);
            tv_topic_name = (TextView) itemView.findViewById(R.id.tv_topic_name);
            tv_people = (TextView) itemView.findViewById(R.id.tv_join_people);
        }
    }
}
