package com.d2cmall.buyer.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.SharePrefConstant;

/**
 * Created by Administrator on 2018/5/17.
 */

public class ShowPopImageView extends android.support.v7.widget.AppCompatImageView {
    public ShowPopImageView(Context context) {
        super(context);
    }

    public ShowPopImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShowPopImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void showMsgPop(Activity context, String content){
        Boolean isMsgPushOpen = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.IS_REMIND_OPEN_MSG, false);
        NotificationManagerCompat manager = NotificationManagerCompat.from(context.getApplicationContext());
        boolean isOpened = manager.areNotificationsEnabled();
        if(!isMsgPushOpen && !isOpened){
            D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.IS_REMIND_OPEN_MSG,true);
            OpenMsgPushPop openMsgPushPop = new OpenMsgPushPop(context);
            openMsgPushPop.setContent(content);
            openMsgPushPop.show(context.getWindow().getDecorView());
        }
    }
}
