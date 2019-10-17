package com.d2cmall.buyer.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.cheilpengtai.supertshirt16library.communicator.ble.ConnectionState;
import com.cheilpengtai.supertshirt16library.communicator.ble.ConnectionStateListener;
import com.cheilpengtai.supertshirt16library.communicator.ble.RequestError;
import com.cheilpengtai.supertshirt16library.communicator.ble.SuperTShirtDevice;
import com.cheilpengtai.supertshirt16library.ledprotocol.BTMessenger;
import com.cheilpengtai.supertshirt16library.ledprotocol.ResultListener;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.Util;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TshirtActivity extends BaseActivity {

    private Dialog loadingDialog;
    public static SuperTShirtDevice superTShirtDevice;
    public static BTMessenger bTMessenger;
    public static boolean isConnected;
    private TshirtHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tshirt);
        ButterKnife.bind(this);
        loadingDialog = DialogUtil.createLoadingDialog(this);
        handler = new TshirtHandler(this);
    }

    public void onBackPressed(View v) {
        super.onBackPressed();
    }

    @OnClick({R.id.img_blueteeth, R.id.img_photo, R.id.img_diy, R.id.img_word})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.img_blueteeth:
                if (isConnected) {
                    Util.showToast(TshirtActivity.this, R.string.msg_blue_connected);
                } else {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT ||
                            !getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
                        Util.showToast(TshirtActivity.this, R.string.msg_unsupport_blue);
                        return;
                    }
                    intent = new Intent(TshirtActivity.this, BlueDevicesActivity.class);
                    startActivityForResult(intent, Constants.RequestCode.SELECT_DEVICE);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                }
                break;
            case R.id.img_photo:
                intent = new Intent(TshirtActivity.this, GIFActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
            case R.id.img_diy:
                intent = new Intent(TshirtActivity.this, DIYActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
            case R.id.img_word:
                Util.urlAction(this, "/page/Tuse");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.RequestCode.SELECT_DEVICE:
                    String deviceAddress = data.getStringExtra("address");
                    superTShirtDevice = new SuperTShirtDevice(this, deviceAddress, new ConnectionStateListener() {
                        @Override
                        public void onConnectionStateChanged(ConnectionState connectionState) {
                            if (connectionState == ConnectionState.CONNECTED) {
                                //获取设备信号控制器
                                BTMessenger messenger = superTShirtDevice.getBTMessenger();
                                if (messenger != null) {
                                    showLoadingDialog();
                                    //设置时间，延时4秒启动
                                    Message message = new Message();
                                    message.what = 100;
                                    message.obj = messenger;
                                    handler.sendMessageDelayed(message, 4000);
                                } else {
                                    showToastMessage("请求失败");
                                    superTShirtDevice.disconnect();
                                }
                            } else if (connectionState == ConnectionState.DISCONNECTED) {
                                dismissLoadingDialog();
                                bTMessenger = null;
                                isConnected = false;
                            }
                        }
                    });
                    superTShirtDevice.connect();//连接蓝牙
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showToastMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Util.showToast(TshirtActivity.this, message, 3);
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

    @Override
    protected void onDestroy() {
        if (superTShirtDevice != null) {
            superTShirtDevice.disconnect();//断开蓝牙
            superTShirtDevice = null;
        }
        super.onDestroy();
    }

    static class TshirtHandler extends Handler {
        private WeakReference<TshirtActivity> mOuter;

        TshirtHandler(TshirtActivity activity) {
            mOuter = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final TshirtActivity outer = mOuter.get();
            if (outer != null) {
                switch (msg.what) {
                    case 100:
                        bTMessenger = (BTMessenger) msg.obj;
                        bTMessenger.setTime(System.currentTimeMillis(), new ResultListener<Boolean>() {
                            @Override
                            public void onResponse(final Boolean response1) {
                                if (response1) {
                                    //授权设备
                                    bTMessenger.authenticate(new ResultListener<Boolean>() {
                                        @Override
                                        public void onResponse(final Boolean response2) {
                                            outer.dismissLoadingDialog();
                                            if (response2) {
                                                //设置亮度，默认为3
                                                bTMessenger.setBrightness(3, new ResultListener<Boolean>() {
                                                    @Override
                                                    public void onResponse(final Boolean response3) {
                                                    }

                                                    @Override
                                                    public void onError(RequestError requestError) {
                                                    }
                                                });
                                                isConnected = true;
                                                outer.showToastMessage("授权成功");
                                            } else {
                                                outer.showToastMessage("授权失败");
                                                superTShirtDevice.disconnect();
                                            }
                                        }

                                        @Override
                                        public void onError(RequestError requestError) {
                                            outer.dismissLoadingDialog();
                                            outer.showToastMessage(Util.getBlueteethMessagerError(requestError));
                                            superTShirtDevice.disconnect();
                                        }
                                    });
                                } else {
                                    outer.dismissLoadingDialog();
                                    outer.showToastMessage("设置时间失败");
                                    superTShirtDevice.disconnect();
                                }
                            }

                            @Override
                            public void onError(RequestError requestError) {
                                outer.dismissLoadingDialog();
                                outer.showToastMessage(Util.getBlueteethMessagerError(requestError));
                                superTShirtDevice.disconnect();
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
