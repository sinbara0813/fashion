package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.ScreenUtil;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class D2CFrameLayout extends FrameLayout implements PtrUIHandler {

    private ImageView imgProgress;
    private TextView tvLabel;
    private AnimationDrawable animationDrawable;
    private int[] animImgIndexs= {R.drawable.ic_loading_logo1,R.drawable.ic_loading_logo2,R.drawable.ic_loading_logo3};
    private int animImgIndex=0;
    private ArrayList<String> lables;

    public D2CFrameLayout(Context context) {
        super(context);
        init(context);
    }

    public D2CFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public D2CFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View headView = View.inflate(context, R.layout.layout_rotate_header, this);
        imgProgress = (ImageView) headView.findViewById(R.id.iv_loading);
        tvLabel = (TextView) headView.findViewById(R.id.tv_loading);
        animationDrawable = (AnimationDrawable) imgProgress.getDrawable();
        animationDrawable.stop();
    }

    public void setLabel(String label) {
        tvLabel.setText(label);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        if(animationDrawable != null){
            animationDrawable.stop();
        }
        tvLabel.setText("下拉即可刷新...");
        if(animImgIndex<2){
            animImgIndex++;
        }else {
            animImgIndex=0;
        }
        imgProgress.setImageResource(animImgIndexs[animImgIndex]);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        if(lables!=null && lables.size()>2){
            tvLabel.setText(lables.get(2));
        }else{
            tvLabel.setText("刷新加载中...");
        }
        startMyAnimation();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        if(animationDrawable != null){
            animationDrawable.stop();
        }
        if(lables!=null && lables.size()>0){
            tvLabel.setText(lables.get(0));
        }else{
            tvLabel.setText("下拉即可刷新...");
        }
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int currentPosition = ptrIndicator.getCurrentPosY();
        if (isUnderTouch && currentPosition > ScreenUtil.dip2px(70)) {
            if(lables!=null && lables.size()>1){
                tvLabel.setText(lables.get(1));
            }else{
                tvLabel.setText("释放即可刷新...");
            }
        } else if (isUnderTouch && currentPosition < ScreenUtil.dip2px(70)) {
            if(lables!=null && lables.size()>0){
                tvLabel.setText(lables.get(0));
            }else{
                tvLabel.setText("下拉即可刷新...");
            }
        }

        if (!isUnderTouch) {
            imgProgress.clearAnimation();
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_LOADING) {
                if(lables!=null && lables.size()>2){
                    tvLabel.setText(lables.get(2));
                }else{
                    tvLabel.setText("刷新加载中...");
                }
            }
        }
        invalidate();
    }

    private void startMyAnimation() {
        if (imgProgress != null) {
            imgProgress.clearAnimation();
            imgProgress.setImageResource(R.drawable.loading_anim);
            animationDrawable = (AnimationDrawable) imgProgress.getDrawable();
            animationDrawable.start();
        }
    }

    public void setRefreshLable(ArrayList<String> strings,String bgColor) {
        lables = strings;
        if(imgProgress!=null){
            imgProgress.clearAnimation();
            imgProgress.setEnabled(false);
            imgProgress.setVisibility(GONE);
        }
        tvLabel.setTextColor(getResources().getColor(R.color.color_white));
        this.setBackgroundColor(Color.parseColor(bgColor));
    }
}
