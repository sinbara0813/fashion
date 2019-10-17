package com.d2cmall.buyer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.d2cmall.buyer.R;

import java.util.ArrayList;

public class FlowLayout extends ViewGroup {
    private int mHorizontalSpacing;
    private int mVerticalSpacing;
    private OnChildCheckedChangeListener onChildCheckedChangeListener;
    private Paint mPaint;
    private ArrayList<CheckBox> checkedChilds;

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        try {
            mHorizontalSpacing = a.getDimensionPixelSize(R.styleable.FlowLayout_horizontalSpacing, 0);
            mVerticalSpacing = a.getDimensionPixelSize(R.styleable.FlowLayout_verticalSpacing, 0);
        } finally {
            a.recycle();
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xffff0000);
        mPaint.setStrokeWidth(2.0f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec) - getPaddingRight();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        boolean growHeight = widthMode != MeasureSpec.UNSPECIFIED;

        int width = 0;
        int height = getPaddingTop();
        int currentWidth = getPaddingLeft();
        int currentHeight = 0;

        boolean breakLine = false;
        int spacing = 0;

        final int count = getChildCount();
        if (count == 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            spacing = mHorizontalSpacing;
            if (growHeight && (breakLine || currentWidth + child.getMeasuredWidth() >= widthSize)) {
                //高度换一行了
                height += currentHeight + mVerticalSpacing;
                width = Math.max(width, currentWidth - spacing);
                //currentHeight = 0;
                currentWidth = getPaddingLeft();

            }

            lp.x = currentWidth;
            lp.y = height;

            currentWidth += child.getMeasuredWidth() + spacing;
            currentHeight = Math.max(currentHeight, child.getMeasuredHeight());

            breakLine = lp.breakLine;
        }

        {
            height += currentHeight;
            width = Math.max(width, currentWidth - spacing);
        }


        width += getPaddingRight();
        height += getPaddingBottom();

        int mDestW = resolveSize(width, widthMeasureSpec);
        int mDestH = resolveSize(height, heightMeasureSpec);
        setMeasuredDimension(mDestW, mDestH);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            child.layout(lp.x, lp.y, lp.x + child.getMeasuredWidth(), lp.y + child.getMeasuredHeight());
        }
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean more = super.drawChild(canvas, child, drawingTime);
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        if (lp.horizontalSpacing > 0) {
            float x = child.getRight();
            float y = child.getTop() + child.getHeight() / 2.0f;
            canvas.drawLine(x, y - 4.0f, x, y + 4.0f, mPaint);
            canvas.drawLine(x, y, x + lp.horizontalSpacing, y, mPaint);
            canvas.drawLine(x + lp.horizontalSpacing, y - 4.0f, x + lp.horizontalSpacing, y + 4.0f, mPaint);
        }
        if (lp.breakLine) {
            float x = child.getRight();
            float y = child.getTop() + child.getHeight() / 2.0f;
            canvas.drawLine(x, y, x, y + 6.0f, mPaint);
            canvas.drawLine(x, y + 6.0f, x + 6.0f, y + 6.0f, mPaint);
        }
        return more;
    }

    @Override
    public void addView(final View child) {
        super.addView(child);
        if (child instanceof CheckBox) {
            if (checkedChilds == null) {
                checkedChilds = new ArrayList<>();
            }
            ((CheckBox) child).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        if (checkedChilds != null && !checkedChilds.isEmpty()) {
                            for (CheckBox checkBox : checkedChilds) {
                                if (checkBox.equals(child)) {
                                    checkBox.setChecked(true);
                                    checkBox.setEnabled(false);
                                } else {
                                    checkBox.setChecked(false);
                                    checkBox.setEnabled(true);
                                }
                            }
                        }
                        if (onChildCheckedChangeListener != null) {
                            onChildCheckedChangeListener.onCheckedChange(child, checkedChilds.isEmpty() ? 0 : checkedChilds.indexOf(child));
                        }
                    }
                }
            });
            if (checkedChilds.isEmpty()) {
                ((CheckBox) child).setChecked(true);
                child.setEnabled(false);
            }
            checkedChilds.add((CheckBox) child);
        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p.width, p.height);
    }

    public void setOnChildCheckedChangeListener(OnChildCheckedChangeListener onChildCheckedChangeListener) {
        this.onChildCheckedChangeListener = onChildCheckedChangeListener;
    }

    public void setCheckedChild(int index) {
        if (checkedChilds != null && (checkedChilds.size() > index)) {
            checkedChilds.get(index).setChecked(true);
        }
    }

    public int getCheckedIndex() {
        if (checkedChilds != null && !checkedChilds.isEmpty()) {
            for (CheckBox checkBox : checkedChilds) {
                if (checkBox.isChecked()) {
                    return checkedChilds.indexOf(checkBox);
                }
            }
        }
        return 0;
    }

    public interface OnChildCheckedChangeListener {
        void onCheckedChange(View childView, int index);
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        public int horizontalSpacing;
        public boolean breakLine;
        int x;
        int y;

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout_LayoutParams);
            try {
                horizontalSpacing = a.getDimensionPixelSize(R.styleable.FlowLayout_LayoutParams_layout_horizontalSpacing, -1);
                breakLine = a.getBoolean(R.styleable.FlowLayout_LayoutParams_layout_breakLine, false);
            } finally {
                a.recycle();
            }
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }
    }
}
