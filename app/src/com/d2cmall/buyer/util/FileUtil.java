package com.d2cmall.buyer.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

public class FileUtil {

    public static long getFolderSize(java.io.File file) {
        long size = 0;
        try {
            java.io.File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);

                } else {
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public static boolean deleteFolderFile(String filePath, boolean deleteThisPath) {
        boolean result = false;
        if (!Util.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
                result = false;
            }
        }
        return result;
    }

    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return "0M";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "K";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "M";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "G";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "T";
    }

    public static boolean createFile(String filePath) {
        boolean result = true;
        if (Util.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        } else {
            String pth = filePath.substring(0, filePath.lastIndexOf("/"));
            boolean ret = createDirectory(pth);
            if (!ret) {
                return false;
            }
        }
        try {
            file.createNewFile();
            result = true;
        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public static boolean createDirectory(String filePath) {
        if (null == filePath) {
            return false;
        }
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        }
        return file.mkdirs();
    }

    public static int save2File(Context context, String path, String fileName, InputStream input) {
        File file = null;
        File folder = null;
        OutputStream output = null;
        try {
            folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            file = new File(folder, fileName);
            if (file.exists()) {
                return 1;
            }
            file.createNewFile();
            output = new FileOutputStream(file);
            byte buffer[] = new byte[1024];
            int len = 0;
            while ((len = input.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
            input.close();
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        } finally {
            if (file != null && file.exists()) {
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                context.sendBroadcast(intent);
            }
        }
        return 0;
    }

    public static boolean saveSplash(Context context, String path, String fileName, InputStream input) {
        File file = null;
        File folder = null;
        OutputStream output = null;
        try {
            folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            file = new File(folder, fileName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            output = new FileOutputStream(file);
            byte buffer[] = new byte[1024];
            int len = 0;
            while ((len = input.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
            input.close();
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (file != null && file.exists()) {
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                context.sendBroadcast(intent);
            }
        }
        return true;
    }

    public static boolean isFileExist(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //Glide保存图片
    public void savePicture(final Context context, final String fileName, String url){
        Glide.with(context).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
            @Override
            public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                try {
                    saveFileToSD(context,fileName,bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //往SD卡写入文件的方法
    public static File saveFileToSD(Context context,String filename, byte[] bytes) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        File root = context.getExternalCacheDir();
        File dir = new File(root, "wx");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String path=dir.getAbsolutePath()+"/"+filename+".jpg";
        //这里就不要用openFileOutput了,那个是往手机内存中写数据的
        FileOutputStream output = new FileOutputStream(path);
        output.write(bytes);
        //将bytes写入到输出流中
        output.close();
        //关闭输出流
        return new File(path );
    }


    public static boolean copyFile(String oldPath$Name, String newPath$Name) {
        try {
            File oldFile = new File(oldPath$Name);
            File newFile = new File(newPath$Name);
            if (!oldFile.exists() || !oldFile.isFile() || !oldFile.canRead()) {
                return false;
            }

            FileInputStream fileInputStream = new FileInputStream(oldFile);
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int byteRead;
            while (-1 != (byteRead = fileInputStream.read(buffer))) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
