package com.d2cmall.buyer.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.d2cmall.buyer.R;

public class DialogUtil {

    public static Dialog createLoadingDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_loading, null);
        ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(v, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        return loadingDialog;
    }

    public static Dialog showMsgDialog(Context context, String contentStr, String confirmStr,
                                     int confirmColor, String cancelStr, int cancelColor,
                                     boolean isCanCancel, final DialogClickListener listener) {
        final Dialog dialog = new Dialog(context, R.style.bubble_dialog);
        Point point = Util.getDeviceSize(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.layout_dialog_msg, null);
        TextView tvContent = (TextView) dialogView.findViewById(R.id.tv_content);
        TextView btnCancel = (TextView) dialogView.findViewById(R.id.btn_cancel);
        TextView btnConfirm = (TextView) dialogView.findViewById(R.id.btn_confirm);
        tvContent.setText(contentStr);
        if (!Util.isEmpty(cancelStr)) {
            btnCancel.setText(cancelStr);
        }
        if (cancelColor > 0) {
            btnCancel.setTextColor(ContextCompat.getColor(context, cancelColor));
        }
        if (!Util.isEmpty(confirmStr)) {
            btnConfirm.setText(confirmStr);
        }
        if (confirmColor > 0) {
            btnConfirm.setTextColor(ContextCompat.getColor(context, confirmColor));
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onCancel();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onConfirm();
            }
        });
        dialog.setContentView(dialogView);
        Window win = dialog.getWindow();
        ViewGroup.LayoutParams params = win.getAttributes();
        params.width = Math.round(point.x * 3 / 4);
        win.setGravity(Gravity.CENTER);
        dialog.setCancelable(isCanCancel);
        dialog.setCanceledOnTouchOutside(isCanCancel);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                listener.onDismiss();
            }
        });
        dialog.show();
        return dialog;
    }

    public static Dialog showMsgDialog(Context context, String contentStr, final DialogClickListener listener) {
        return showMsgDialog(context, contentStr, null, 0, null, 0, true, listener);
    }

    public static Dialog showMsgDialog(Context context, int contentStr, final DialogClickListener listener) {
        return showMsgDialog(context, context.getString(contentStr), listener);
    }

}
