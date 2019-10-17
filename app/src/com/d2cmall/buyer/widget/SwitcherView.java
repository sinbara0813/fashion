package com.d2cmall.buyer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.d2cmall.buyer.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by maning on 16/7/17.
 * 垂直滚动的广告栏
 */
public class SwitcherView extends TextSwitcher implements ViewSwitcher.ViewFactory, View.OnTouchListener {


    private Handler handler = null;

    private ArrayList<String> dataSource = new ArrayList<>();  //数据源
    private int currentIndex = 0;   //滚动的位置
    private int textSize = 0;    //文字大小
    private static final int defaultTextSize = 14;    //默认文字大小
    private int textColor = 0xDE000000; //默认颜色
    private int timePeriod = 3000;  //时间周期
    private boolean flag = true;
    private boolean hasSetFactory = false;
    private TextView tView;

    public SwitcherView(Context context) {
        this(context, null);
    }

    public SwitcherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SwitcherView);
        textColor = ta.getColor(R.styleable.SwitcherView_switcherTextColor, textColor);
        timePeriod = ta.getInt(R.styleable.SwitcherView_switcherRollingTime, timePeriod);
        textSize = ta.getDimensionPixelSize(R.styleable.SwitcherView_switcherTextSize, sp2px(defaultTextSize));
        Log.i("----", textSize + "");
        textSize = px2sp(textSize);
        Log.i("----", textSize + "");
        ta.recycle();
        setOnTouchListener(this);
    }

    @Override
    public View makeView() {
        tView = new TextView(getContext());
        tView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tView.setTextColor(textColor);
        tView.setSingleLine();
        tView.setPadding(10, 5, 10, 5);
        tView.setEllipsize(TextUtils.TruncateAt.END);
        return tView;
    }

    public void setResource(ArrayList<String> dataSource) {
        this.dataSource = dataSource;
    }


    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                updateTextSwitcher();
                if(dataSource!=null && dataSource.size()>1){
                    handler.postDelayed(this, timePeriod);
                }
            } catch (Exception e) {
            }
        }
    };

    private void updateTextSwitcher() {
        if (dataSource != null && dataSource.size() ==1 ) {
            this.setText(dataSource.get(0));
            flag=false;
        }
        if (dataSource != null && dataSource.size() > 1) {
            this.setText(dataSource.get(currentIndex));
            currentIndex++;
            if (currentIndex > dataSource.size() - 1) {
                currentIndex = 0;
            }
        }
    }

    public void startRolling() {
        if(handler==null){
            if(!hasSetFactory){
                this.setFactory(this);
            }
            hasSetFactory=true;
            this.setInAnimation(getContext(), R.anim.m_switcher_vertical_in);
            this.setOutAnimation(getContext(), R.anim.m_switcher_vertical_out);
        }
        if(handler==null){
            handler=new Handler();
        }
        handler.postDelayed(runnable,0);
    }

    public String getCurrentItem() {
        if (dataSource != null && dataSource.size() > 0) {
            return dataSource.get(getCurrentIndex());
        } else {
            return "";
        }
    }

    public int getCurrentIndex() {
        int index = currentIndex - 1;
        if (index < 0) {
            index = dataSource.size() - 1;
        }
        return index;
    }

    public void destroySwitcher() {
            if(handler!=null){
                handler.removeCallbacksAndMessages(null);
                handler=null;
            }
            if (dataSource != null && dataSource.size() > 0) {
                dataSource.clear();
                dataSource = null;
             }
    }
    public void reset() {
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            flag = false;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            flag = true;
        }
        return false;
    }

    public int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }

    public int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());

    }

    public int px2sp(float pxValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

}