package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.LiveAudienceInBean;
import com.d2cmall.buyer.holder.LiveHeadHolder;
import com.d2cmall.buyer.util.UniversalImageLoader;

import java.util.ArrayList;

/**
 * Created by rookie on 2018/1/2.
 */

public class LiveHeanAdapter extends RecyclerView.Adapter<LiveHeadHolder>{

    private ArrayList<LiveAudienceInBean.DataBean.RealtimeBean.HeadersBean> list;
    private Context context;

    public LiveHeanAdapter(ArrayList<LiveAudienceInBean.DataBean.RealtimeBean.HeadersBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public LiveHeadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.layout_live_head,parent,false);
        return new LiveHeadHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LiveHeadHolder holder, int position) {
        String url=list.get(position).getHeadPic();
        final long id=list.get(position).getMemberId();
        UniversalImageLoader.displayRoundImage(context,url,holder.ivHead,R.mipmap.ic_default_avatar);
//        holder.ivHead.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context, PersonInfoActivity.class);
//                intent.putExtra("memberId",id);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
