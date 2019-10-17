package com.d2cmall.buyer.behavior;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.OverScroller;

import com.d2cmall.buyer.AppProfile;
import com.d2cmall.buyer.R;

import java.lang.ref.WeakReference;


/**
 * Created by rookie on 18/02/28.
 */

public class MineScrollBehavior extends CoordinatorLayout.Behavior<View> {
    public static final String TAG = "HeaderScrollBehavior";

    private WeakReference<View> mDependencyView;

    private OverScroller mOverScroller;

    private Handler mHandler;

    private  boolean isExpand = false;

    private boolean isScrolling = false;

    public MineScrollBehavior(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        mOverScroller = new OverScroller(context);
        mHandler = new Handler();

    }
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if(isDepend(dependency)){
            mDependencyView = new WeakReference<View>(dependency);
            return true;
        }

        return false;
    }
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Resources resources = getDependencyView().getResources();
        final float progress = 1.f -
                Math.abs(dependency.getTranslationY() / (dependency.getHeight() - resources.getDimension(R.dimen.titlebar_height)));
        child.setTranslationY(dependency.getHeight() + dependency.getTranslationY());

        float scale = 1 + 0.4f * (1.f - progress);
        dependency.setScaleX(scale);
        dependency.setScaleY(scale);

        //dependency.setAlpha(progress);


        return true;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if(params!=null && params.height == CoordinatorLayout.LayoutParams.MATCH_PARENT){
            child.layout(0,0,parent.getWidth(),parent.getHeight()- getHeaderCollspateHeight());
            return true;
        }

        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        isScrolling = false;
        mOverScroller.abortAnimation();
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View get, int dx, int dy, int[] consumed) {
        if(dy < 0){
            return;
        }
        View dependentView = getDependencyView();
        float newTranslationY = dependentView.getTranslationY() - dy;
        float minHeaderTranslation = -(dependentView.getHeight() - getHeaderCollspateHeight());
        if(newTranslationY > minHeaderTranslation){
            dependentView.setTranslationY(newTranslationY);
            consumed[1] = dy;
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if(dyUnconsumed > 0){
            return;
        }
        View dependentView = getDependencyView();
//        if (dyUnconsumed < 0) {
//            float newTranslateY = dependentView.getTranslationY() - dyUnconsumed;
//            int dy = -dyUnconsumed;
//            if (dy > 30) {
//                return;
//            }
//            float value = dy / 100;
//            ViewCompat.setScaleX(dependentView, 1f + value);
//            ViewCompat.setScaleY(dependentView, 1f + value);
//            dependentView.setTranslationY(newTranslateY);
//            //needOrigin = true;
//        }

        float newTranslateY = dependentView.getTranslationY() - dyUnconsumed;
        final float maxHeaderTranslate = 0;
        if (newTranslateY < maxHeaderTranslate) {
            dependentView.setTranslationY(newTranslateY);
        }
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        return onUserStopDragging(velocityY);
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        if(!isScrolling){
            onUserStopDragging(500);
        }
    }

    private boolean onUserStopDragging(float velocity) {
        View dependentView = getDependencyView();
        float translateY = dependentView.getTranslationY();
        float minHeaderTranslate = -(dependentView.getHeight() - getHeaderCollspateHeight());

        if (translateY == 0 || translateY == minHeaderTranslate) {
            return false;
        }

        boolean targetState; // Flag indicates whether to expand the content.
        if (Math.abs(velocity) <= 500) {
            if (Math.abs(translateY) < Math.abs(translateY - minHeaderTranslate)) {
                targetState = false;
            } else {
                targetState = true;
            }
            velocity = 500; // Limit velocity's minimum value.
        } else {
            if (velocity > 0) {
                targetState = true;
            } else {
                targetState = false;
            }
        }

        float targetTranslateY = targetState ? minHeaderTranslate : 0;
        mOverScroller.startScroll(0, (int) translateY, 0, (int) (targetTranslateY - translateY), (int) (300000 / Math.abs(velocity)));
        mHandler.post(flingRunnable);
        isScrolling = true;

        return true;
    }


    private View getDependencyView (){
        return mDependencyView.get();
    }
    /**
     * header 折叠高度
     * @return
     */
    public int getHeaderCollspateHeight(){
        return AppProfile.getContext().getResources().getDimensionPixelOffset(R.dimen.titlebar_height);
    }

    public boolean isDepend(View dependency){
        return dependency!=null && dependency.getId() == R.id.header;
    }

    private Runnable flingRunnable = new Runnable() {
        @Override
        public void run() {
            if(mOverScroller.computeScrollOffset()){
                getDependencyView().setTranslationY(mOverScroller.getCurrY());
                mHandler.post(this);
            } else{
                isExpand = getDependencyView().getTranslationX() != 0;
                isScrolling = false;
            }
        }
    };
}
