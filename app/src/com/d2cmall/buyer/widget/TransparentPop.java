package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.d2cmall.buyer.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TransparentPop extends PopupWindow {
    private Context mContext;
    private InvokeListener mInvokeListener;
    private DismissListener dismissListener;
    private View contentView;
    private boolean defineWidth = false;
    private int mWidth;
    private boolean defineHeight = false;
    private int mHeight;
    private LinearLayout parent;
    private RelativeLayout rootLayout;
    private Animation inAnimation;
    private Animation outAnimation;
    private boolean hasAnimation = true;
    private boolean isClickDismiss = true;

    public TransparentPop(Context context, InvokeListener listener) {
        this(context, -1, -1, true, listener);
    }

    public TransparentPop(Context context, boolean isClickDismiss, InvokeListener listener) {
        this(context, -1, -1, isClickDismiss, listener);
    }

    public TransparentPop(Context context, int width, int height, boolean isClickDismiss, InvokeListener listener) {
        super(context);
        this.mContext = context.getApplicationContext();
        this.isClickDismiss=isClickDismiss;
        this.mInvokeListener = listener;
        if (width > 0) {
            defineWidth = true;
            mWidth = width;
        }
        if (height > 0) {
            defineHeight = true;
            mHeight = height;
        }
        init();
    }

    private void init() {
        contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_transparent_pop, new RelativeLayout(mContext), false);
        parent = (LinearLayout) contentView.findViewById(R.id.content);
        rootLayout = (RelativeLayout) contentView.findViewById(R.id.root);
        if (mInvokeListener != null) {
            mInvokeListener.invokeView(parent);
        }
        if (defineWidth) {
            setWidth(mWidth);
        } else {
            setWidth(LayoutParams.MATCH_PARENT);
        }
        if (defineHeight) {
            setHeight(mHeight);
        } else {
            setHeight(LayoutParams.MATCH_PARENT);
        }
        setContentView(contentView);
        setFocusable(true);
        setOutsideTouchable(false);
        setBackgroundDrawable(new ColorDrawable());
        if (isClickDismiss){
            contentView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    dismiss(hasAnimation);
                }
            });
        }
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mInvokeListener!=null){
                    mInvokeListener.releaseView(parent);
                }
                if (dismissListener!=null){
                    dismissListener.dismissEnd();
                }
            }
        });
    }

    public void setOnkey(View.OnKeyListener onkey){
        rootLayout.setOnKeyListener(onkey);
    }

    private void setAnimationListener() {
        outAnimation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                if (dismissListener!=null){
                    dismissListener.dismissStart();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                contentView.post(new Runnable() {

                    @Override
                    public void run() {
                        dismiss();
                    }
                });
            }
        });
    }

    public void setRootLayoutBackground(int id) {
        if (rootLayout != null) {
            rootLayout.setBackgroundResource(id);
        }
    }

    public void dismissFromOut() {
        contentView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss(hasAnimation);
            }
        });
    }

    public interface InvokeListener {
        void invokeView(LinearLayout v);
        void releaseView(LinearLayout v);
    }

    public void show(View reference) {
        show(reference, true);
    }

    public void show(View reference, boolean hasAnimation) {
        show(reference, 0, 0, hasAnimation);
    }

    public void show(View reference, int width, int height, boolean hasAnimation) {
        if (hasAnimation&&inAnimation!=null) {
            int childCount=parent.getChildCount();
            if (childCount>0){
               parent.getChildAt(0).startAnimation(inAnimation);
            }else {
                parent.startAnimation(inAnimation);
            }
        }
        showAtLocation(reference, Gravity.CENTER, width, height);
    }

    public void showAsParent(View view,boolean hasAnimation){
        if (hasAnimation&&inAnimation!=null) {
            int childCount=parent.getChildCount();
            if (childCount>0){
                parent.getChildAt(0).startAnimation(inAnimation);
            }else {
                parent.startAnimation(inAnimation);
            }
        }
        showAsDropDown(this,view);
    }

    public void dismiss(boolean hasAnimation) {
        if (hasAnimation&&outAnimation!=null) {
            int childCount=parent.getChildCount();
            if (childCount>0){
                parent.getChildAt(0).startAnimation(outAnimation);
            }else {
                parent.startAnimation(outAnimation);
            }
        } else {
            dismiss();
        }
    }

    public void setInAnimation(Animation inAnimation) {
        this.inAnimation = inAnimation;
    }

    public void setOutAnimation(Animation outAnimation) {
        this.outAnimation = outAnimation;
        setAnimationListener();
    }

    public void setBackGroundResource(int id) {
        if (contentView != null) {
            contentView.setBackgroundResource(id);
        }
    }

    public View getParent() {
        return parent;
    }

    public void setDismissListener(DismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    public interface DismissListener{
        void dismissStart();
        void dismissEnd();
    }

    public void showAsDropDown(PopupWindow pw, View anchor) {
        //7.0以上popwindow在高度matchparent,并且弹窗的Gravity不是 Gravity.START | Gravity.TOP,弹窗的显示效果没有在锚点View下的bug兼容
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            pw.setHeight(height);
            pw.showAsDropDown(anchor);
        } else {
            pw.showAsDropDown(anchor);
        }
    }
}
