package com.d2cmall.buyer.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

public class SingleSelectPop implements TransparentPop.InvokeListener, NumberPicker.OnValueChangeListener {

    private TransparentPop mPop;
    private Context mContext;
    private View bottomView;
    private TextView cancel, sure;
    private NumberPicker firstNumber;
    private String[] firstValues;
    private String currentFirstValue;
    private int currentFirstIndex;
    private CallBack mCallBack;
    private View mtrigger;

    public SingleSelectPop(Context context, String[] strings) {
        mContext = context;
        if (strings.length < 3) return;
        firstValues = strings;
        init();
    }

    private void init() {
        bottomView = LayoutInflater.from(mContext).inflate(R.layout.layout_single_select_pop, new LinearLayout(mContext), false);
        cancel = (TextView) bottomView.findViewById(R.id.pop_cancel);
        sure = (TextView) bottomView.findViewById(R.id.pop_sure);
        firstNumber = (NumberPicker) bottomView.findViewById(R.id.number_picker);
        if (firstValues == null || firstValues.length < 2) {
            throw new NullPointerException("values is not be null or length<2");
        } else {
            currentFirstIndex = 0;
            firstNumber.setDisplayedValues(firstValues);
            firstNumber.setMaxValue(firstValues.length - 1);
            firstNumber.setMinValue(0);
            firstNumber.setValue(currentFirstIndex);
            currentFirstValue = firstValues[currentFirstIndex];
            firstNumber.setOnValueChangedListener(this);
        }
        mPop = new TransparentPop(mContext, this);
        Animation inAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_up);
        Animation outAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_down);
        mPop.setInAnimation(inAnimation);
        mPop.setOutAnimation(outAnimation);
        mPop.dismissFromOut();
        initListener();
    }

    private void initListener() {
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mPop.dismiss(true);
                if (mCallBack != null) {
                    mCallBack.callback(mtrigger, currentFirstIndex, currentFirstValue);
                }
            }
        });
    }

    public void show(View parent, View trigger) {
        this.mtrigger = trigger;
        mPop.show(parent, true);
    }

    public void dismiss() {
        if (mPop != null) {
            mPop.dismiss(true);
        }
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.BOTTOM);
        v.addView(bottomView);
    }

    @Override
    public void releaseView(LinearLayout v) {

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        if (picker == firstNumber) {
            currentFirstIndex = newVal;
            currentFirstValue = firstValues[currentFirstIndex];
            if (firstPickChangeListener != null) {
                firstPickChangeListener.onFirstValueChange(firstValues[oldVal], currentFirstValue);
            }
        }
    }

    public CallBack getmCallBack() {
        return mCallBack;
    }

    public void setCallBack(CallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    public interface CallBack {
        void callback(View trigger, int index, String value);
    }

    public interface FirstPickChangeListener {
        void onFirstValueChange(String oldStr, String newStr);
    }

    private FirstPickChangeListener firstPickChangeListener;

    public void setFirstChangeListener(FirstPickChangeListener listener) {
        this.firstPickChangeListener = listener;
    }

    public String getCurrentFirstValue() {
        return currentFirstValue;
    }

    public int getCurrentFirstIndex() {
        return currentFirstIndex;
    }
}
