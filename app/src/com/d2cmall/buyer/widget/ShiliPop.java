package com.d2cmall.buyer.widget;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class ShiliPop implements TransparentPop.InvokeListener {

    @Bind(R.id.cancel)
    TextView cancel;
    @Bind(R.id.sure)
    TextView sure;
    @Bind(R.id.code_tv)
    TextView codeTv;
    private TransparentPop pop;
    private View rootView;

    public ShiliPop(final Context context,String code) {
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_shili_pop, new LinearLayout(context),false);
        ButterKnife.bind(this,rootView);
        pop = new TransparentPop(context,false,this);
        pop.setBackGroundResource(R.color.trans_60_color_black);
        pop.setFocusable(false);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pop!=null){
                    pop.dismiss(false);
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");

                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cmp);
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Util.showToast(context, "抱歉您尚未安装微信");
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pop!=null){
                    pop.dismiss(false);
                }
            }
        });
        codeTv.setText(code);
    }

    public void show(View view) {
        pop.show(view, false);
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.CENTER);
        v.addView(rootView);
    }

    @Override
    public void releaseView(LinearLayout v) {
        v.removeAllViews();
        rootView = null;
    }
}
