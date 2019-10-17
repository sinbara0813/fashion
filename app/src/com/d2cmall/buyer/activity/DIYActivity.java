package com.d2cmall.buyer.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheilpengtai.supertshirt16library.communicator.ble.RequestError;
import com.cheilpengtai.supertshirt16library.ledprotocol.ResultListener;
import com.cheilpengtai.supertshirt16library.view.PaintMode;
import com.cheilpengtai.supertshirt16library.view.PaintView;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.util.Util;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DIYActivity extends BaseActivity {


    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.paintView)
    PaintView paintView;
    @Bind(R.id.img_color)
    ImageView imgColor;
    @Bind(R.id.img_style)
    ImageView imgStyle;
    @Bind(R.id.img_complete)
    ImageView imgComplete;
    private Dialog loadingDialog;
    private int initialColor = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy);
        ButterKnife.bind(this);
        initTitle();
        loadingDialog = DialogUtil.createLoadingDialog(this);
    }

    private void initTitle() {
        TitleUtil.setBack(this);
        TitleUtil.setTitle(this, R.string.label_diy);
    }

    @OnClick({R.id.img_color, R.id.img_style, R.id.img_complete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_color:
                ColorPickerDialogBuilder
                        .with(this)
                        .initialColor(initialColor)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setPositiveButton(R.string.action_ok, new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                initialColor = selectedColor;
                                paintView.setPaintColor(initialColor);
                                paintView.setPaintMode(PaintMode.PAINT);
                                imgStyle.setImageResource(R.mipmap.ic_diy_erase);
                                Util.showToast(DIYActivity.this, R.string.msg_color_selected);
                            }
                        })
                        .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
                break;
            case R.id.img_style:
                if (paintView.getPaintMode() == PaintMode.ERASE) {
                    paintView.setPaintMode(PaintMode.PAINT);
                    imgStyle.setImageResource(R.mipmap.ic_diy_erase);
                    Util.showToast(this, R.string.msg_brush_selected);
                } else {
                    paintView.setPaintMode(PaintMode.ERASE);
                    imgStyle.setImageResource(R.mipmap.ic_diy_brush);
                    Util.showToast(this, R.string.msg_erase_selected);
                }
                break;
            case R.id.img_complete:
                if (TshirtActivity.isConnected) {
                    showLoadingDialog();
                    TshirtActivity.bTMessenger.sendCustomPicture(paintView.getImageContent(), new ResultListener<Boolean>() {
                        @Override
                        public void onResponse(final Boolean response) {
                            if (response) {
                                TshirtActivity.bTMessenger.displayCustomPicture(new ResultListener<Boolean>() {
                                    @Override
                                    public void onResponse(final Boolean response) {
                                        dismissLoadingDialog();
                                        showToastMessage("请求" + (response ? "成功" : "失败"));
                                    }

                                    @Override
                                    public void onError(RequestError requestError) {
                                        dismissLoadingDialog();
                                        showToastMessage(Util.getBlueteethMessagerError(requestError));
                                    }
                                });
                            } else {
                                dismissLoadingDialog();
                                showToastMessage("连接失败");
                            }
                        }

                        @Override
                        public void onError(RequestError requestError) {
                            dismissLoadingDialog();
                            showToastMessage(Util.getBlueteethMessagerError(requestError));
                        }
                    });
                } else {
                    Util.showToast(this, R.string.msg_blue_connect_first);
                }
                break;
        }
    }

    private void showToastMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Util.showToast(DIYActivity.this, message, 3);
            }
        });
    }

    private void showLoadingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingDialog.show();
            }
        });
    }

    private void dismissLoadingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        Util.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Util.onPause(this);
        super.onPause();
    }

}
