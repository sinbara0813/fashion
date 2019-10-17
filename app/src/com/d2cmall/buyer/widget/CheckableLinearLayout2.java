package com.d2cmall.buyer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;

import com.d2cmall.buyer.R;


public class CheckableLinearLayout2 extends LinearLayout implements Checkable {

    private boolean isChecked;
    private boolean uncheckable;
    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};
    private static final int[] ENABLED_STATE_SET = {android.R.attr.state_enabled};
    private OnCheckedChangeListener onCheckedChangeListener;

    public CheckableLinearLayout2(Context context) {
        this(context, null);
    }

    public CheckableLinearLayout2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckableLinearLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CheckableLinearLayout2, 0, 0);
        try {
            setChecked(ta.getBoolean(R.styleable.CheckableLinearLayout2_isChecked, false));
            uncheckable = ta.getBoolean(R.styleable.CheckableLinearLayout2_uncheckable, false);
        } finally {
            ta.recycle();
        }

    }


    @Override
    public void setChecked(boolean checked) {
        if (isChecked != checked) {
            isChecked = checked;
            refreshDrawableState();
            if (onCheckedChangeListener != null) {
                onCheckedChangeListener.onCheckedChange(this, isChecked);
            }
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        if (isEnabled() && !uncheckable) {
            setChecked(!isChecked);
        }
    }


    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isEnabled()) {
            if (isChecked()) {
                mergeDrawableStates(drawableState, CHECKED_STATE_SET);
            } else {
                mergeDrawableStates(drawableState, ENABLED_STATE_SET);
            }
        }
        return drawableState;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public interface OnCheckedChangeListener {
        public void onCheckedChange(View view, boolean checked);
    }
}
