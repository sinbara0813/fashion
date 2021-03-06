package com.d2cmall.buyer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.d2cmall.buyer.R;

import java.lang.reflect.Field;

/**
 * 带分割线的GridView，自适应高度
 * Author: Blend
 * Date: 2016/08/30 14:34
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class WrapLineGridView extends GridView {

    private int lineColor;

    public WrapLineGridView(Context context) {
        this(context, null);
    }

    public WrapLineGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WrapLineGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WrapLineGridView);
        lineColor = a.getColor(R.styleable.WrapLineGridView_wrapLineColor, getResources().getColor(R.color.trans_25_color_white));
        a.recycle();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        int column = 1;
        try {
            //通过反射拿到列数
            Field field = GridView.class.getDeclaredField("mNumColumns");
            field.setAccessible(true);
            column = field.getInt(this);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int childCount = getChildCount();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(lineColor);
        for (int i = 0; i < childCount; i++) {
            View cellView = getChildAt(i);
            if (cellView.getTop() != 0) {
                //顶部线，坐标+1是为了画在cellView上
                canvas.drawLine(cellView.getLeft(), cellView.getTop() + 1, cellView.getRight(), cellView.getTop() + 1, paint);
            }
            if ((i + column) >= childCount) {//最后column个单元格画上底边线
                canvas.drawLine(cellView.getLeft(), cellView.getBottom() + 1, cellView.getRight(), cellView.getBottom() + 1, paint);
            }
            //左边线
            canvas.drawLine(cellView.getLeft() + 1, cellView.getTop(), cellView.getLeft() + 1, cellView.getBottom(), paint);
            if ((i + 1) % column == 0) {//最右边一列单元格画上右边线
                canvas.drawLine(cellView.getRight(), cellView.getTop() + 1, cellView.getRight(), cellView.getBottom() + 1, paint);
            }
            if (childCount % column != 0 && i == childCount - 1) {//如果最后一个单元格不在最右一列，单独为它画上右边线
                canvas.drawLine(cellView.getRight() + 1, cellView.getTop() + 1, cellView.getRight() + 1, cellView.getBottom() + 1, paint);
            }
        }
    }

}
