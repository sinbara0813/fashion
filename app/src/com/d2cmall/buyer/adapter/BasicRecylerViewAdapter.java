package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.Identifiable;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.RecylerViewHolder;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayer;
import com.d2cmall.buyer.widget.nicevideo.TxVideoPlayerController;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.zxinsight.MWConfiguration.getContext;

/**
 * recyclerview 适配器
 * Author: YH
 * Date: 2017/07/10 09:55
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class BasicRecylerViewAdapter<T extends Identifiable> extends RecyclerView.Adapter<RecylerViewHolder> {
    protected List<T> list;
    private int itmemLayoutId;
    private WeakReference<Context> context;
    protected ViewBinder<T> viewBinder;
    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFooterViews = new ArrayList<>();
    private final int HEADTYPE = 100;
    private final int ITEMTYPE = 200;
    private final int FOOTTYPE = 300;

    public BasicRecylerViewAdapter(Context context, List<T> list, int itmemLayoutId) {
        if (list == null) {
            throw new NullPointerException("BasicRecylerViewAdapter :list 为空");
        }
        this.context = new WeakReference<Context>(context);
        this.list = list;
        this.itmemLayoutId = itmemLayoutId;
    }

    public void setViewBinder(ViewBinder<T> viewBinder) {
        this.viewBinder = viewBinder;
    }

    @Override
    public RecylerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecylerViewHolder recylerViewHolder = null;
        switch (viewType) {
            case HEADTYPE:
                if (getHeaderView().getParent() != null) {
                    ((ViewGroup) getHeaderView().getParent()).removeView(getHeaderView());
                }
                recylerViewHolder = new RecylerViewHolder(getHeaderView());
                break;
            case ITEMTYPE:
                recylerViewHolder = new RecylerViewHolder(View.inflate(context.get(), itmemLayoutId, null));
                if (recylerViewHolder.getView(R.id.nice_video_player) != null) {
                    if (recylerViewHolder.getView(R.id.nice_video_player) instanceof NiceVideoPlayer) {
                        TxVideoPlayerController controller = new TxVideoPlayerController(context.get());
                        recylerViewHolder.setController(controller);
                        ((NiceVideoPlayer) recylerViewHolder.getView(R.id.nice_video_player)).setController(controller);
                    }
                }
                break;
            case FOOTTYPE:
                if (getFooterView().getParent() != null) {
                    ((ViewGroup) getFooterView().getParent()).removeView(getFooterView());
                }
                recylerViewHolder = new RecylerViewHolder(getFooterView());
                break;
        }

        return recylerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecylerViewHolder holder, int position) {
        if (viewBinder != null && getHeaderViewsCount() <= position
                && position < (getHeaderViewsCount() + list.size())) {
            viewBinder.setViewValue(holder, list.get(position - getHeaderViewsCount()), position);
        } else if (position == (getHeaderViewsCount() + list.size())) {
            if (holder.getView(R.id.loading) != null) {
                holder.getView(R.id.loading).getLayoutParams().width = Util.getDeviceSize(getContext()).x;
            }
            if (holder.getView(R.id.no_more) != null) {
                holder.getView(R.id.no_more).getLayoutParams().width = Util.getDeviceSize(getContext()).x;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getHeaderViewsCount()) {
            return HEADTYPE;
        } else if (getHeaderViewsCount() <= position
                && position < (getHeaderViewsCount() + list.size())) {
            return ITEMTYPE;
        } else {
            return FOOTTYPE;
        }
    }

    @Override
    public int getItemCount() {
        return getHeaderViewsCount() + list.size() + getFooterViewsCount();
    }

    public void addHeaderView(View header) {

        if (header == null) {
            throw new RuntimeException("header is null");
        }

        mHeaderViews.add(header);
        this.notifyDataSetChanged();
    }

    public void addFooterView(View footer) {

        if (footer == null) {
            throw new RuntimeException("footer is null");
        }
        mFooterViews.add(footer);
        this.notifyDataSetChanged();
    }

    /**
     * 返回第一个FootView
     *
     * @return
     */
    public View getFooterView() {
        return getFooterViewsCount() > 0 ? mFooterViews.get(0) : null;
    }

    /**
     * 返回第一个HeaderView
     *
     * @return
     */
    public View getHeaderView() {
        return getHeaderViewsCount() > 0 ? mHeaderViews.get(0) : null;
    }

    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    public int getFooterViewsCount() {
        return mFooterViews.size();
    }

    public interface ViewBinder<T extends Identifiable> {
        void setViewValue(RecylerViewHolder holder, T t, int position);
    }
}
