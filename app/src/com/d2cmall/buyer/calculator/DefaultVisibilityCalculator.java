package com.d2cmall.buyer.calculator;

import android.view.View;

import com.d2cmall.buyer.scrollUtils.ItemsPositionGetter;
import com.d2cmall.buyer.scrollUtils.Logger;
import com.d2cmall.buyer.scrollUtils.ScrollDirectionDetector;


/**
 * Name: nice
 * Anthor: hrb
 * Date: 2018/1/31 13:18
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class DefaultVisibilityCalculator extends BaseItemsVisibilityCalculator {

    private static final boolean SHOW_LOGS = true;
    private static final String TAG = "han";
    private static final int VISIBILITY_PERCENTS = 65;
    private int mScrollDirection;
    private ListItemData mCurrentPlay;
    private ItemsPositionGetter mPositionGetter;

    public DefaultVisibilityCalculator() {
        this.mScrollDirection = ScrollDirectionDetector.UP;
        this.mCurrentPlay = new ListItemData();
    }

    /**
     * 快速滑动recyclerView
     * @param var1
     */
    @Override
    protected void onStateFling(ItemsPositionGetter var1) {
        //如果recyclerView已经做监听释放播放器 这里可以不做处理
    }

    @Override
    protected void onStateTouchScroll(ItemsPositionGetter var1) {
        mPositionGetter=var1;
        Logger.v(TAG, ">> onStateTouchScroll, mScrollDirection " + this.mScrollDirection);
        //ListItemData listItemData = this.mCurrentItem;
        //this.calculateActiveItem(itemsPositionGetter, listItemData);
        if (mCurrentPlay.getView()!=null&&mCurrentPlay.hasPlayer()){
            calculateActiveItem(var1);
        }
        Logger.v(TAG, "<< onStateTouchScroll, mScrollDirection " + this.mScrollDirection);
    }

    private void calculateActiveItem(ItemsPositionGetter positionGetter){
        int currentPercent=mCurrentPlay.getVisibilityPercents();
        ListItemData neighborPlayData=null;
        if (mScrollDirection== ScrollDirectionDetector.UP){//手势往下滑
            if (currentPercent<100){
                neighborPlayData=getPreviousPlayer(positionGetter);
            }
        }else if (mScrollDirection==ScrollDirectionDetector.DOWN){ //手势往上滑
            if (currentPercent<100){
                neighborPlayData=getNextPlayer(positionGetter);
            }
        }
        if (neighborPlayData!=null&&neighborPlayData.hasPlayer()){
            if (mCurrentPlay.isCompleted()||currentPercent<VISIBILITY_PERCENTS){
                mCurrentPlay=neighborPlayData;
            }
        }
    }

    private ListItemData getNextPlayer(ItemsPositionGetter itemsPositionGetter) {
        ListItemData neighborPlayData=new ListItemData();
        int indexOfCurrentItem = itemsPositionGetter.indexOfChild(mCurrentPlay.getView());
        for(int indexOfCurrentView = indexOfCurrentItem+1; indexOfCurrentView <=lastVisibleItem; indexOfCurrentView++) {
            View next=itemsPositionGetter.getChildAt(indexOfCurrentView);
            neighborPlayData.fillWithData(indexOfCurrentView,next);
            if (neighborPlayData.hasPlayer()){
                int percent=neighborPlayData.getVisibilityPercents();
                if (percent >VISIBILITY_PERCENTS) { //符合显示条件
                    return neighborPlayData;
                }
            }
        }
        return null;
    }

    private ListItemData getPreviousPlayer(ItemsPositionGetter itemsPositionGetter){
        ListItemData neighborPlayData=new ListItemData();
        int indexOfCurrentItem = itemsPositionGetter.indexOfChild(mCurrentPlay.getView());
        for(int indexOfCurrentView = indexOfCurrentItem-1; indexOfCurrentView >=0; indexOfCurrentView--) {
            View previous=itemsPositionGetter.getChildAt(indexOfCurrentView);
            neighborPlayData.fillWithData(indexOfCurrentView,previous);
            if (neighborPlayData.hasPlayer()){
                int percent=neighborPlayData.getVisibilityPercents();
                if (percent >VISIBILITY_PERCENTS) { //符合显示条件
                    return neighborPlayData;
                }
            }
        }
        return null;
    }

    private ListItemData getFirstPlayer(ItemsPositionGetter itemsPositionGetter){
        return new ListItemData().fillWithData(firstVisibleItem,itemsPositionGetter.getChildAt(firstVisibleItem));
    }

    @Override
    public void onScrollStateIdle(ItemsPositionGetter var1, int var2, int var3,int state) {
        //滑动状态回调
        scrollState=state;
        if (scrollState==0){
            if (mCurrentPlay.hasPlayer()&&mCurrentPlay.isAvailable()){
                mCurrentPlay.start();
            }else {
                ListItemData first=getFirstPlayer(var1);
                if (first!=null){
                    if (!first.hasPlayer()){
                        first=getNextPlayer(var1);
                    }
                    if (first!=null){
                        int percent=first.getVisibilityPercents();
                        if (percent>VISIBILITY_PERCENTS){
                            first.start();
                            mCurrentPlay=first;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onScrollDirectionChanged(int scrollDirection) {
        this.mScrollDirection=scrollDirection;
    }
}
