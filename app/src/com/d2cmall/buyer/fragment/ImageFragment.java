package com.d2cmall.buyer.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.LooksDetailActivity;
import com.d2cmall.buyer.adapter.ImageAdapter;
import com.d2cmall.buyer.base.*;
import com.d2cmall.buyer.bean.MatchDetailBean;
import com.d2cmall.buyer.holder.WearChioceHolder;
import com.d2cmall.buyer.listener.DisposeListener;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayer;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者:Created by sinbara on 2018/11/20.
 * 邮箱:hrb940258169@163.com
 */

public class ImageFragment extends com.d2cmall.buyer.base.BaseFragment implements DisposeListener{

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private VirtualLayoutManager manager;
    private DelegateAdapter adapter;

    private ImageAdapter imageAdapter;
    private List<MatchDetailBean> list;
    private boolean isSelf=true;
    private int id;
    private boolean isRemove;
    private PagerSnapHelper helper;
    private int currentIndex;
    public boolean isDestroy;
    private int dy;

    public static ImageFragment newInstance(List<MatchDetailBean> list,boolean isSelf,int id){
        ImageFragment imageFragment=new ImageFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("list", (Serializable) list);
        bundle.putBoolean("isSelf",isSelf);
        bundle.putInt("id",id);
        imageFragment.setArguments(bundle);
        return imageFragment;
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void prepareView() {
        List<MatchDetailBean> local= (List<MatchDetailBean>) getArguments().getSerializable("list");
        id=getArguments().getInt("id");
        isSelf=getArguments().getBoolean("isSelf");
        HashMap<Integer,List<MatchDetailBean>> map=new HashMap<>();
        List<Integer> index=new ArrayList<>();
        list=new ArrayList<>();
        int size=local.size();
        for (int i=0;i<size;i++){
            if (!index.contains(local.get(i).id)){
                index.add(local.get(i).id);
            }
            if (!map.containsKey(local.get(i).id)){
                List<MatchDetailBean> detailBeans=new ArrayList<>();
                detailBeans.add(local.get(i));
                map.put(local.get(i).id,detailBeans);
            }else {
                map.get(local.get(i).id).add(local.get(i));
            }
        }
        for (int i=index.size()-1;i>=0;i--){
            list.addAll(map.get(index.get(i)));
        }
        for (int i=0;i<size;i++){
            if (list.get(i).id==id){
                currentIndex=i;
                break;
            }
        }
        map.clear();
        map=null;
        index.clear();
        index=null;
    }

    @Override
    public void doBusiness() {
        if (adapter==null){
            manager=new VirtualLayoutManager(getActivity());
            recyclerView.setLayoutManager(manager);
            adapter=new DelegateAdapter(manager,true);
            recyclerView.setAdapter(adapter);
            helper=new PagerSnapHelper();
            helper.attachToRecyclerView(recyclerView);
            imageAdapter=new ImageAdapter(getActivity(),this,list,isSelf);
            adapter.addAdapter(imageAdapter);
            if (currentIndex!=0){
                recyclerView.scrollToPosition(currentIndex);
            }
            initListener();
        }else {
            if (isRemove){
                adapter.addAdapter(imageAdapter);
                isRemove=false;
            }
        }
    }

    @Override
    public void releaseOnInVisible() {
        View recycleView=recyclerView.getChildAt(0);
        if (recycleView!=null){
            Log.e("han","releaseOnInVisible");
            NiceVideoPlayer niceVideoPlayer=recycleView.findViewById(R.id.nice_video_player);
            if (niceVideoPlayer.getVisibility()==View.VISIBLE){
                niceVideoPlayer.stop();
            }
        }
        adapter.removeAdapter(imageAdapter);
        isRemove=true;
        super.releaseOnInVisible();
    }

    private void initListener() {
        recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                if(holder instanceof WearChioceHolder){
                    WearChioceHolder wearChioceHolder = (WearChioceHolder) holder;
                    NiceVideoPlayer niceVideoPlayer = wearChioceHolder.niceVideoPlayer;
                    if (niceVideoPlayer == com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                        Log.e("han","recycler");
                        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                    }
                }

            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                ImageFragment.this.dy=dy;
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState==RecyclerView.SCROLL_STATE_IDLE){
                    View view=helper.findSnapView(manager);
                    Log.e("han","isDragPlay");
                    autoPlayVideo(view);
                }
            }
        });

        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                int position=manager.getPosition(view);
                if (currentIndex==position){
                    Log.e("han","isFirstPlay");
                    autoPlayVideo(view);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                View recycleView=null;
                if (dy>=0){
                    recycleView=recyclerView.getChildAt(0);
                }else {
                    recycleView=recyclerView.getChildAt(1);
                }
                if (recycleView!=null){
                    Log.e("han","recycler>>>"+"dy="+dy);
                    NiceVideoPlayer niceVideoPlayer=recycleView.findViewById(R.id.nice_video_player);
                    if (niceVideoPlayer.getVisibility()==View.VISIBLE){
                        niceVideoPlayer.stop();
                    }
                }
            }
        });
    }

    private void autoPlayVideo(View view){
        if (view!=null){
            NiceVideoPlayer niceVideoPlayer = (NiceVideoPlayer) recyclerView.getChildAt(0).findViewById(R.id.nice_video_player);
            if (niceVideoPlayer.getVisibility()==View.VISIBLE){
                if (!niceVideoPlayer.isPlaying()){
                    niceVideoPlayer.start();
                }
            }
        }
    }

    @Override
    public void dispose(boolean is) {
        isDestroy=is;
        if (getActivity() instanceof LooksDetailActivity){
            ((LooksDetailActivity)getActivity()).refresh();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

}
