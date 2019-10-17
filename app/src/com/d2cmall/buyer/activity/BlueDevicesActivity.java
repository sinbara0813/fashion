package com.d2cmall.buyer.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BlueDevicesActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private LeDeviceListAdapter leDeviceListAdapter;
    private Handler handler;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean scanning;
    private static final long SCAN_PERIOD = 10000;

    @Override
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_device);
        ButterKnife.bind(this);
        initTitle();
        handler = new Handler();
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Util.showToast(BlueDevicesActivity.this, "不支持蓝牙BLE协议");
            finish();
            return;
        }
        if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF ||
                mBluetoothAdapter.getState() == BluetoothAdapter.STATE_TURNING_OFF) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, Constants.RequestCode.BLUE_BEETH);
        }
        leDeviceListAdapter = new LeDeviceListAdapter();
        listView.setAdapter(leDeviceListAdapter);
        listView.setOnItemClickListener(this);
        checkPermission();
    }

    private void initTitle() {
        TitleUtil.setBack(this);
        TitleUtil.setTitle(this, R.string.label_blue_device);
    }

    private class LeDeviceListAdapter extends BaseAdapter {

        private ArrayList<BluetoothDevice> bluetoothDevices;

        private LeDeviceListAdapter() {
            super();
            bluetoothDevices = new ArrayList<>();
        }

        private void addDevice(BluetoothDevice device) {
            if (!bluetoothDevices.contains(device)) {
                bluetoothDevices.add(device);
                notifyDataSetChanged();
            }
        }

        public void clear() {
            bluetoothDevices.clear();
        }

        public BluetoothDevice getDevice(int position) {
            return bluetoothDevices.get(position);
        }

        @Override
        public int getCount() {
            return bluetoothDevices == null ? 0 : bluetoothDevices.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BluetoothDevice bd = bluetoothDevices.get(position);
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(BlueDevicesActivity.this).inflate(R.layout.list_item_blue_device, null);
                holder.tvDeviceName = (TextView) convertView.findViewById(R.id.tv_device_name);
                holder.tvDeviceAddress = (TextView) convertView.findViewById(R.id.tv_device_address);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (!Util.isEmpty(bd.getName())) {
                holder.tvDeviceName.setText(bd.getName());
            } else {
                holder.tvDeviceName.setText(R.string.label_no_know_device);
            }
            holder.tvDeviceAddress.setText(bd.getAddress());
            return convertView;
        }

        class ViewHolder {
            TextView tvDeviceName;
            TextView tvDeviceAddress;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final BluetoothDevice device = leDeviceListAdapter.getDevice(position);
        if (device == null) return;
        Intent intent = getIntent();
        intent.putExtra("address", device.getAddress());
        stopScan();
        setResult(RESULT_OK, intent);
        BlueDevicesActivity.super.onBackPressed();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void stopScan() {
        if (scanning) {
            mBluetoothAdapter.stopLeScan(null);
            scanning = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.RequestCode.BLUE_BEETH && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scanning = false;
                    mBluetoothAdapter.stopLeScan(null);
                }
            }, SCAN_PERIOD);
            scanning = true;
            mBluetoothAdapter.startLeScan(new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            leDeviceListAdapter.addDevice(device);
                        }
                    });
                }
            });
        } else {
            scanning = false;
            mBluetoothAdapter.stopLeScan(null);
        }
    }

    @Override
    protected void onResume() {
        Util.onResume(this);
        if (!mBluetoothAdapter.isEnabled() &&
                mBluetoothAdapter.getState() != BluetoothAdapter.STATE_TURNING_ON) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, Constants.RequestCode.BLUE_BEETH);
        }
        scanLeDevice(true);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Util.onPause(this);
        scanLeDevice(false);
        leDeviceListAdapter.clear();
        super.onPause();
    }

    private void checkPermission() {
        int pcStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int pcLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (pcStorage != PackageManager.PERMISSION_GRANTED || pcLocation != PackageManager.PERMISSION_GRANTED) {
            List<String> permissionList = new ArrayList<>();
            if (pcStorage != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (pcLocation != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), Constants.RequestCode.PERMISSION);
        } else {
            onPermissionGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.RequestCode.PERMISSION) {
            if (grantResults.length > 0) {
                for (int result : grantResults) {
                    if (result == PackageManager.PERMISSION_DENIED) {
                        onPermissionDenied();
                        return;
                    }
                }
            }
            onPermissionGranted();
        }
    }

    private void onPermissionGranted() {
    }

    private void onPermissionDenied() {
        Util.showToast(this, "未获得权限，即将退出");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }
}
