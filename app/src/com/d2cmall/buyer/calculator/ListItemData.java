package com.d2cmall.buyer.calculator;

import android.view.View;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.scrollUtils.Logger;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayer;


/**
 * Name: nice
 * Anthor: hrb
 * Date: 2018/1/31 13:42
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ListItemData {

    private View mView;
    private String TAG = ListItemData.class.getSimpleName();
    private Integer mIndexInAdapter;

    public ListItemData() {
    }

    public int getIndex() {
        return this.mIndexInAdapter.intValue();
    }

    public View getView() {
        return this.mView;
    }

    public ListItemData fillWithData(int indexInAdapter, View view) {
        this.mIndexInAdapter = Integer.valueOf(indexInAdapter);
        this.mView = view;
        return this;
    }

    public boolean isAvailable() {
        boolean isAvailable = this.mIndexInAdapter != null && this.mView != null;
        Logger.v(TAG, "isAvailable " + isAvailable);
        return isAvailable;
    }

    public int getVisibilityPercents() {
        NiceVideoPlayer videoPlayer=getPlayer();
        if (videoPlayer!=null){
            int visibilityPercents = videoPlayer.getVisibilityPercents();
            Logger.v(TAG, "getVisibilityPercents, visibilityPercents " + visibilityPercents);
            return visibilityPercents;
        }else {
            return 0;
        }
    }

    public String toString() {
        return "ListItemData{mIndexInAdapter=" + this.mIndexInAdapter + ", mView=" + this.mView + "}";
    }

    public boolean hasPlayer(){
        return getPlayer()!=null;
    }

    public NiceVideoPlayer getPlayer(){
        if (mView!=null){
            NiceVideoPlayer niceVideoPlayer= (NiceVideoPlayer) mView.findViewById(R.id.nice_video_player);
            if (niceVideoPlayer!=null&&niceVideoPlayer.getVisibility()==View.VISIBLE){
                return niceVideoPlayer;
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

    public boolean isCompleted(){
        NiceVideoPlayer niceVideoPlayer=getPlayer();
        return niceVideoPlayer.isCompleted();
    }

    public void start(){
        NiceVideoPlayer niceVideoPlayer=getPlayer();
        if (niceVideoPlayer.isIdle()) {
            niceVideoPlayer.start();
        }
    }
}
