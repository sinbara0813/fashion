package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.SerieBean;
import com.d2cmall.buyer.bean.SortWayBean;
import com.d2cmall.buyer.listener.OnItemClickListener;
import com.d2cmall.buyer.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rookie on 2018/4/16.
 */

public class SortPop extends PopupWindow {
    private Context context;
    private int height;
    private View rootView;
    private RecyclerView recyclerView;
    private List<SortWayBean> list;
    private SortPopAdapter adapter;
    private int lastChoosePosition = 0;
    private OnItemClickListener onItemClickListener;

    public SortPop(Context context, int height) {
        this.context = context;
        this.height = height;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_sort_pop, null);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycle_view);
        this.setContentView(rootView);
        this.setWidth(ScreenUtil.screenWidth - ScreenUtil.dip2px(0));
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setTouchable(true);
        ColorDrawable cd = new ColorDrawable();
        this.setBackgroundDrawable(cd);
        this.setOutsideTouchable(true);
        this.update();
        list = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<SortWayBean> outList) {
        list.addAll(outList);
        adapter = new SortPopAdapter(context, list);
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(context);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        delegateAdapter.addAdapter(adapter);
        recyclerView.setLayoutManager(virtualLayoutManager);
        recyclerView.setAdapter(delegateAdapter);
    }

    public void fakeClick(int position) {
        adapter.fakeClick(position);
    }


    class SortPopAdapter extends DelegateAdapter.Adapter<MyViewHolder> {
        private Context context;
        private List<SortWayBean> list;

        public SortPopAdapter(Context context, List<SortWayBean> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public LayoutHelper onCreateLayoutHelper() {
            return new LinearLayoutHelper();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_pop_buyer_item, parent, false);
            return new MyViewHolder(view);
        }

        public void fakeClick(int position) {
            list.get(lastChoosePosition).setChoose(false);
            list.get(position).setChoose(true);
            lastChoosePosition = position;
            notifyDataSetChanged();
            if (onItemClickListener != null) {
                onItemClickListener.itemClick(new View(context), position);
            }
            dismiss();
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.textView.setText(list.get(position).getName());
            if (list.get(position).isChoose()) {
                holder.textView.setTextColor(Color.parseColor("#f5a323"));
            } else {
                holder.textView.setTextColor(Color.parseColor("#666666"));
            }
            if (position != list.size()) {
                holder.line.setVisibility(View.VISIBLE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.get(lastChoosePosition).setChoose(false);
                    list.get(position).setChoose(true);
                    lastChoosePosition = position;
                    notifyDataSetChanged();
                    if (onItemClickListener != null) {
                        onItemClickListener.itemClick(v, position);
                    }
                    dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        View line;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_sort_name);
            line = itemView.findViewById(R.id.line);
        }
    }
}
