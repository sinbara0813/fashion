package com.d2cmall.buyer.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.widget.RemoteViews;

import com.d2cmall.buyer.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadService extends Service {
    private final String TAG = "downloadService";
    /**
     * 超时
     */
    private static final int TIMEOUT = 10 * 1000;
    /**
     * 下载地址
     */
    public static String down_url = "http://app.d2cmall.com/download/d2cmall-install.apk";
    /**
     * 下载成功
     */
    private static final int DOWN_OK = 1;
    /**
     * 下载失败
     */
    private static final int DOWN_ERROR = 0;
    /***
     * 创建通知栏
     */
    RemoteViews mViews;
    /**
     * 应用名称
     */
    private String app_name;
    /**
     * 通知管理器
     */
    private NotificationManager notificationManager;
    /**
     * 通知
     */
    private Notification notification;
    /**
     * 点击通知跳转
     */
    private Intent mUpdateIntent;
    /**
     * 等待跳转
     */
    private PendingIntent mPendingIntent;
    /**
     * 通知ID
     */
    private final int notification_id = 0;

    private String updateapkurl = "";

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "UpdateService+onStartCommand");
        if (intent != null) {
            app_name = intent.getStringExtra("name");
        } else {
            app_name = getString(R.string.app_name);
        }
        File root = getExternalCacheDir();
        File file = new File(root, "apk");
        if (!file.exists()) {
            if (file.mkdirs()) {
                updateFile(file);
            }
        } else {
            File[] files=file.listFiles();
            for (File f: files) {
                f.delete();
            }
            file.listFiles();
            updateFile(file);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateFile(File file) {
        updateapkurl = file.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".apk";
        FileUtil.createFile(updateapkurl);
        createNotification();
        createThread();
    }

    /**
     * @Description: 创建通知
     * [url=home.php?mod=space&uid=309376]@return[/url] void
     */
    public void createNotification() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Builder builder = new Builder(this);
        mViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        mViews.setImageViewResource(R.id.notificationImage, R.mipmap.ic_update);
        mViews.setTextViewText(R.id.notificationTitle, "D2C正在下载...");
        mViews.setTextViewText(R.id.notificationPercent, "0%");
        mViews.setProgressBar(R.id.notificationProgress, 100, 0, false);
        builder.setContent(mViews);
        mUpdateIntent = new Intent(Intent.ACTION_MAIN);
        mUpdateIntent.addCategory(Intent.CATEGORY_HOME);
        mPendingIntent = PendingIntent.getActivity(this, 0, mUpdateIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(mPendingIntent);
        builder.setTicker("开始下载，点击可查看");
        builder.setSmallIcon(R.mipmap.ic_update).setWhen(System.currentTimeMillis()).setAutoCancel(true);// 设置可以清除
        notification = builder.build();
        notificationManager.notify(notification_id, notification);
    }

    /***
     * 开线程下载
     */
    public void createThread() {
        /***
         * 更新UI
         */
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case DOWN_OK:
                        InstallationAPK();
                        break;
                    case DOWN_ERROR:
                        break;

                    default:
                        stopSelf();
                        break;
                }

            }

        };

        final Message message = new Message();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    long downloadSize = downloadUpdateFile(down_url, updateapkurl);
                    if (downloadSize > 0) {
                        // 下载成功
                        message.what = DOWN_OK;
                        handler.sendMessage(message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    message.what = DOWN_ERROR;
                    handler.sendMessage(message);
                }

            }
        }).start();
    }

    /**
     * @return void
     * @Description: 自动安装
     */
    private void InstallationAPK() {
        notificationManager.cancel(notification_id);
        // 停止服务
        stopSelf();
        // 下载完成，点击安装
        Uri uri = Uri.fromFile(new File(updateapkurl));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(intent);

    }

    /***
     * 下载文件
     *
     * @return
     * @throws MalformedURLException
     */
    public long downloadUpdateFile(String down_url, String file) throws Exception {
        int down_step = 1;// 提示step
        int totalSize;// 文件总大小
        int downloadCount = 0;// 已经下载好的大小
        int updateCount = 0;// 已经上传的文件大小
        InputStream inputStream;
        OutputStream outputStream;
        URL url = new URL(down_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(TIMEOUT);
        httpURLConnection.setReadTimeout(TIMEOUT);
        httpURLConnection.setUseCaches(false);
        // 获取下载文件的size
        totalSize = httpURLConnection.getContentLength();
        Log.d("han", "totalsize==" + totalSize);
        if (httpURLConnection.getResponseCode() == 404) {
            throw new Exception("fail!");
        }
        inputStream = httpURLConnection.getInputStream();
        File file2 = new File(file);
        outputStream = new FileOutputStream(file2);// 文件存在则覆盖掉
        byte buffer[] = new byte[1024];
        int readsize = 0;
        while ((readsize = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, readsize);
            downloadCount += readsize;// 时时获取下载到的大小
            /**
             * 每次增张5%
             */
            if (updateCount == 0 || (downloadCount * 100 / totalSize - down_step) >= updateCount) {
                updateCount += down_step;
                mViews.setTextViewText(R.id.notificationPercent, updateCount + "%");
                mViews.setProgressBar(R.id.notificationProgress, 100, updateCount, false);
                notificationManager.notify(notification_id, notification);

            }
        }
        Log.d("han", "file.size==" + file2.length());
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        inputStream.close();
        outputStream.flush();
        outputStream.close();

        return downloadCount;

    }

    public void setDown_url(String down_url) {
        this.down_url = down_url;
    }
}
