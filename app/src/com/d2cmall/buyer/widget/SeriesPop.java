package com.d2cmall.buyer.widget;

import android.content.Context;
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
import com.d2cmall.buyer.listener.OnItemClickListener;
import com.d2cmall.buyer.util.ScreenUtil;

import java.util.List;

/**
 * Created by rookie on 2017/10/20.
 */

public class SeriesPop implements TransparentPop.InvokeListener{
    private Context context;
    private int height;
    private View rootView;
    private RecyclerView recyclerView;
    private TransparentPop pop;
    private boolean isCallDismiss;
    private SeriesPopAdapter adapter;
    private List<SerieBean.DataBean.SeriesBean.ListBean> list;
    private OnItemClickListener onClickListener;

    public SeriesPop(Context context, int height){
        this.context=context;
        this.height=height;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_series_pop, null);
        recyclerView= (RecyclerView) rootView.findViewById(R.id.recycle_view);
        //int height2= ScreenUtil.screenHeight-ScreenUtil.statusbarheight-ScreenUtil.dip2px(height);
        //int height2= ScreenUtil.dip2px(264);
        int height2= ScreenUtil.screenHeight-ScreenUtil.statusbarheight-ScreenUtil.dip2px(height);
        pop=new TransparentPop(context,-1,height2,true,this);
        pop.setFocusable(true); //获取焦点
        pop.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.top_slide_in));
        pop.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.top_slide_out));
        pop.setDismissListener(new TransparentPop.DismissListener() {
            @Override
            public void dismissStart() {
                dismiss();
            }

            @Override
            public void dismissEnd() {
                dismiss();
            }
        });
    }

    public void setOnDissmissListener(PopupWindow.OnDismissListener onDissmissListener){
        pop.setOnDismissListener(onDissmissListener);
    }

    public void show(View view){
        pop.showAsParent(view,true);
    }

    public boolean isShow(){
        if (pop!=null){
            return pop.isShowing();
        }
        return false;
    }

    public void setOnClick(OnItemClickListener onClickListener){
        this.onClickListener=onClickListener;
    }

    public void dismiss(){
        if (isCallDismiss)
            return;
        isCallDismiss=true;
    }

    public void setData(List<SerieBean.DataBean.SeriesBean.ListBean> list, int position){
        this.list=list;
        adapter=new SeriesPopAdapter(context,list);
        VirtualLayoutManager virtualLayoutManager=new VirtualLayoutManager(context);
        DelegateAdapter delegateAdapter=new DelegateAdapter(virtualLayoutManager);
        delegateAdapter.addAdapter(adapter);
        recyclerView.setLayoutManager(virtualLayoutManager);
        recyclerView.setAdapter(delegateAdapter);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        View line;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.tv_series);
            line=itemView.findViewById(R.id.line);
        }
    }

    class SeriesPopAdapter extends DelegateAdapter.Adapter<MyViewHolder>{
        private Context context;
        private List<SerieBean.DataBean.SeriesBean.ListBean> list;

        public SeriesPopAdapter(Context context, List<SerieBean.DataBean.SeriesBean.ListBean> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public LayoutHelper onCreateLayoutHelper() {
            return new LinearLayoutHelper();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(context).inflate(R.layout.layout_series_item,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.textView.setText(list.get(position).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.itemClick(v,position);
                    pop.dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.TOP);
        v.addView(rootView);
    }

    @Override
    public void releaseView(LinearLayout v) {
        v.removeAllViews();
        rootView=null;
    }
}
