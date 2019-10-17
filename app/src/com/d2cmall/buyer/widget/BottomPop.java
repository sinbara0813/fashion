package com.d2cmall.buyer.widget;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

public class BottomPop implements TransparentPop.InvokeListener, NumberPicker.OnValueChangeListener {
    private String TAG = BottomPop.class.getSimpleName();
    private TransparentPop mPop;
    private Context mContext;
    private View bottomView;
    private Animation inAnimation;
    private Animation outAnimation;
    private TextView cancel, sure;
    private NumberPicker firstNumber;
    private NumberPicker secondNumber;
    private NumberPicker thirdNumber;
    private String[] firstValues;
    private String[] secondValues;
    private String[] thirdValues;
    private String currentFirstValue;
    private String currentSecondValue;
    private String currentThirdValue;
    private int currentFirstIndex;
    private int currentSecondIndex;
    private int currentThirdIndex;
    private boolean debug = true;
    private CallBack mCallBack;
    private View mtrigger;

    public BottomPop(Context context, String[][] strings) {
        mContext = context;
        if (strings.length < 3) return;
        firstValues = strings[0];
        secondValues = strings[1];
        thirdValues = strings[2];
        init();
    }

    public BottomPop(Context context, String[][] strings,int[] defaultValue) {
        mContext = context;
        if (strings.length < 3) return;
        firstValues = strings[0];
        secondValues = strings[1];
        thirdValues = strings[2];
        currentFirstIndex=defaultValue[0];
        currentSecondIndex=defaultValue[1];
        currentThirdIndex=defaultValue[2];
        init();
    }

    private void init() {
        bottomView = LayoutInflater.from(mContext).inflate(R.layout.layout_bottom_pop, new LinearLayout(mContext), false);
        cancel = (TextView) bottomView.findViewById(R.id.pop_cancel);
        sure = (TextView) bottomView.findViewById(R.id.pop_sure);
        firstNumber = (NumberPicker) bottomView.findViewById(R.id.first_number);
        secondNumber = (NumberPicker) bottomView.findViewById(R.id.second_number);
        thirdNumber = (NumberPicker) bottomView.findViewById(R.id.third_number);
        if (firstValues == null || firstValues.length < 2 || secondValues == null || secondValues.length < 2 || thirdValues == null || thirdValues.length < 2) {
            throw new NullPointerException("values is not be null or length<3");
        } else {
            if (currentFirstIndex==0&&currentSecondIndex==0&&currentThirdIndex==0){
                currentFirstIndex = firstValues.length / 2;
                currentSecondIndex = secondValues.length / 2;
                currentThirdIndex = thirdValues.length / 2;
            }
            firstNumber.setDisplayedValues(firstValues);
            firstNumber.setMaxValue(firstValues.length - 1);
            firstNumber.setMinValue(0);
            firstNumber.setValue(currentFirstIndex);
            currentFirstValue = firstValues[currentFirstIndex];
            firstNumber.setOnValueChangedListener(this);
            secondNumber.setDisplayedValues(secondValues);
            secondNumber.setMaxValue(secondValues.length - 1);
            secondNumber.setMinValue(0);
            secondNumber.setValue(currentSecondIndex);
            currentSecondValue = secondValues[currentSecondIndex];
            secondNumber.setOnValueChangedListener(this);
            thirdNumber.setDisplayedValues(thirdValues);
            thirdNumber.setMaxValue(thirdValues.length - 1);
            thirdNumber.setMinValue(0);
            thirdNumber.setValue(currentThirdIndex);
            currentThirdValue = thirdValues[currentThirdIndex];
            thirdNumber.setOnValueChangedListener(this);
        }
        mPop = new TransparentPop(mContext, this);
        inAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_up);
        outAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_down);
        mPop.setInAnimation(inAnimation);
        mPop.setOutAnimation(outAnimation);
        mPop.dismissFromOut();
        initListener();
    }

    public void setSecondValue(String[] values) {
        if (secondNumber != null) {
            secondValues = values;
            secondNumber.setMinValue(0);
            if (currentSecondIndex > values.length - 4) {
                currentSecondIndex = values.length - 4;
            }
            secondNumber.setValue(currentSecondIndex);
            secondNumber.setDisplayedValues(values);
            secondNumber.setMaxValue(values.length - 1);
            currentSecondValue = values[currentSecondIndex];
            secondNumber.setValue(currentSecondIndex);
        }
    }

    public void setThirdValue(String[] values) {
        if (thirdNumber != null) {
            thirdValues = values;
            thirdNumber.setMinValue(0);
            if (currentThirdIndex > values.length - 4) {
                currentThirdIndex = values.length - 4;
            }

            if (currentThirdIndex < 2){
                currentThirdIndex=2;
            }
            thirdNumber.setValue(currentThirdIndex);
            thirdNumber.setDisplayedValues(values);
            thirdNumber.setMaxValue(values.length - 1);
            currentThirdValue = values[currentThirdIndex];
            thirdNumber.setValue(currentThirdIndex);
        }
    }

    private void initListener() {
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        sure.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mPop.dismiss(true);
                if (mCallBack != null) {
                    mCallBack.callback(mtrigger, new int[]{currentFirstIndex, currentSecondIndex, currentThirdIndex}, new String[]{currentFirstValue, currentSecondValue, currentThirdValue});
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
        if (debug) {
            Log.d(TAG, "newVal==" + newVal);
            Log.d(TAG, "oldVal==" + oldVal);
        }
        if (picker == firstNumber) {
            currentFirstIndex = newVal;
            currentFirstValue = firstValues[currentFirstIndex];
            if (firstPickChangeListener != null) {
                firstPickChangeListener.onFirstValueChange(firstValues[oldVal], currentFirstValue);
            }
        } else if (picker == secondNumber) {
            currentSecondIndex = newVal;
            currentSecondValue = secondValues[currentSecondIndex];
            if (secondPickChangeListener != null) {
                secondPickChangeListener.onSecondValueChange(secondValues[oldVal], currentSecondValue);
            }
        } else if (picker == thirdNumber) {
            currentThirdIndex = newVal;
            currentThirdValue = thirdValues[currentThirdIndex];
        }
    }

    public CallBack getmCallBack() {
        return mCallBack;
    }

    public void setCallBack(CallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    public interface CallBack {
        void callback(View trigger, int[] index, String[] value);
    }

    public interface FirstPickChangeListener {
        void onFirstValueChange(String oldStr, String newStr);
    }

    private FirstPickChangeListener firstPickChangeListener;

    public void setFirstChangeListener(FirstPickChangeListener listener) {
        this.firstPickChangeListener = listener;
    }

    public interface SecondPickChangeListener {
        void onSecondValueChange(String oldStr, String newStr);
    }

    private SecondPickChangeListener secondPickChangeListener;

    public void setSecondChangeListener(SecondPickChangeListener listener) {
        this.secondPickChangeListener = listener;
    }

    public String getCurrentFirstValue() {
        return currentFirstValue;
    }

    public String getCurrentSecondValue() {
        return currentSecondValue;
    }

    public String getCurrentThirdValue() {
        return currentThirdValue;
    }

    public int getCurrentFirstIndex() {
        return currentFirstIndex;
    }

    public int getCurrentSecondIndex() {
        return currentSecondIndex;
    }

    public int getCurrentThirdIndex() {
        return currentThirdIndex;
    }
}
