package com.d2cmall.buyer.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;

public class TitleUtil {

    public static void setTitle(BaseActivity activity, int resource) {
        TextView tvTitle = (TextView) activity.findViewById(R.id.name_tv);
        if (tvTitle != null) {
            tvTitle.setText(resource);
        }
    }

    public static void setTitle(BaseActivity activity, String resource) {
        TextView tvTitle = (TextView) activity.findViewById(R.id.name_tv);
        if (tvTitle != null) {
            tvTitle.setText(resource);
        }
    }

    public static void setBack(final BaseActivity activity) {
        ImageView backBtn = (ImageView) activity.findViewById(R.id.back_iv);
        if (backBtn != null) {
            backBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    activity.onBackPressed();
                }
            });
        }
    }

    public static void setOkText(final BaseActivity activity, int resource) {
        TextView tvOk = (TextView) activity.findViewById(R.id.title_right);
        if (tvOk != null) {
            if (resource != 0) {
                tvOk.setText(resource);
            }
            tvOk.setVisibility(View.VISIBLE);
            tvOk.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    activity.onOkButtonClick();
                }
            });
        }
    }

    public static void setOkText(final BaseActivity activity, String resource) {
        TextView tvOk = (TextView) activity.findViewById(R.id.title_right);
        if (tvOk != null) {
            if (!Util.isEmpty(resource)) {
                tvOk.setText(resource);
            }
            tvOk.setVisibility(View.VISIBLE);
            tvOk.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    activity.onOkButtonClick();
                }
            });
        }
    }

}
