package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.TopicListActivity;
import com.d2cmall.buyer.bean.TopicBean;
import com.d2cmall.buyer.holder.TopicTextHolder;

import java.util.List;

/**
 * Created by rookie on 2017/9/4.
 */

public class PublishTopicListAdapter extends DelegateAdapter.Adapter<TopicTextHolder> {
    private Context context;
    private List<TopicBean.DataBean.TopicsBean.ListBean> list;

    public PublishTopicListAdapter(Context context, List<TopicBean.DataBean.TopicsBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public TopicTextHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_topic_text_item, parent, false);
        return new TopicTextHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TopicTextHolder holder, final int position) {
        holder.textView.setText("#" + list.get(position).getTitle() + "#");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topicName = holder.textView.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("topic", list.get(position));
                ((TopicListActivity) context).setResult(Activity.RESULT_OK, intent);
                ((TopicListActivity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
