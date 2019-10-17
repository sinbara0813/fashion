package com.d2cmall.buyer.widget;

import android.content.Context;
import android.util.AttributeSet;


public class CheckableLinearLayoutButton extends CheckableLinearLayout2 {

    public CheckableLinearLayoutButton(Context context) {
        super(context);
    }

    public CheckableLinearLayoutButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckableLinearLayoutButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void toggle() {
        if (!isChecked()) {
            super.toggle();
        }
    }

}
