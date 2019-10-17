package com.d2cmall.buyer.behavior;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.AppProfile;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;


/**
 * Created by rookie on 18/02/28.
 */

public class FloatingHeaderTitleBehavior extends CoordinatorLayout.Behavior<View> {
    private ArgbEvaluator mArgbEvaluator;
    /**
     * Title 的折叠高度
     */
    private int mTitleCollapsedHeight;
    /**
     * titile 初始化Y轴的位置
     */
    private int mTitleInitY, baseHeight, expandHeight;

    private int mMargin;

    private Context context;


    public FloatingHeaderTitleBehavior(Context context, AttributeSet attributeSet) {
        this.context = context;
        mArgbEvaluator = new ArgbEvaluator();
        mTitleCollapsedHeight = context.getResources().getDimensionPixelOffset(R.dimen.collapsedTitleHeight);
        mTitleInitY = context.getResources().getDimensionPixelOffset(R.dimen.title_init_y3);
        mMargin = context.getResources().getDimensionPixelOffset(R.dimen.title_margin_left);
        baseHeight = getCollapsedHeight();
        expandHeight = getExpandHeight();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return isDependent(dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        float progress = 1.0f - Math.abs(dependency.getTranslationY() / (dependency.getHeight() - baseHeight));

        float translationY = (mTitleInitY - mTitleCollapsedHeight) * progress;

        child.setTranslationY(translationY+20);

        // background change
        int startColor = AppProfile.getContext().getResources().getColor(R.color.transparent);
        int endColor = AppProfile.getContext().getResources().getColor(R.color.transparent);
        child.setBackgroundColor((Integer) mArgbEvaluator.evaluate(progress, endColor, startColor));
        //set margin
        int margin = (int) (mMargin * progress);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        //params.setMargins(margin,0,margin,0);
        params.topMargin = margin;
        params.height = (int) (expandHeight * progress + baseHeight);
        child.setLayoutParams(params);
        LinearLayout linearLayout = (LinearLayout) child;
        LinearLayout p = (LinearLayout) linearLayout.getChildAt(0);
        LinearLayout bottom = (LinearLayout) linearLayout.getChildAt(1);
        bottom.setAlpha(progress);
        RelativeLayout imageView = (RelativeLayout) p.getChildAt(0);
        TextView name = (TextView) p.getChildAt(1);
        ImageView level = (ImageView) p.getChildAt(2);
        float scale = 0.6f + (0.4f * progress);
        float mTranslationX = -ScreenUtil.dip2px(24) * (1f - progress);
        float mTranslationY = -ScreenUtil.dip2px(12) * (1f - progress);
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(context);
        if (user != null) {
            level.setTranslationX(mTranslationX);
            level.setTranslationY(mTranslationY);
            level.setAlpha(progress);
            if (progress == 0) {
                level.setVisibility(View.INVISIBLE);
            } else if (progress > 0) {
                level.setVisibility(View.VISIBLE);
            }
        }
        name.setTranslationX(mTranslationX);
        name.setTranslationY(mTranslationY);
        if (progress == 0) {
            bottom.setVisibility(View.INVISIBLE);
        } else if (progress > 0) {
            bottom.setVisibility(View.VISIBLE);
        }
        imageView.setPivotX(1);
        imageView.setPivotY(0.6f);
        imageView.setScaleX(scale);
        imageView.setScaleY(scale);
        return true;
    }


    private boolean isDependent(View dependency) {

        return dependency != null && dependency.getId() == R.id.header;
    }

    private int getCollapsedHeight() {
        return AppProfile.getContext().getResources().getDimensionPixelOffset(R.dimen.collapsedTitleHeight);
    }

    private int getExpandHeight() {
        return AppProfile.getContext().getResources().getDimensionPixelOffset(R.dimen.title_expand);
    }

}
