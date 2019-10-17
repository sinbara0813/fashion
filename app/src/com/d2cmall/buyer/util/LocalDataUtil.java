package com.d2cmall.buyer.util;

import android.content.Context;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.bean.HotBean;
import com.d2cmall.buyer.bean.KindTagBean;
import com.d2cmall.buyer.bean.SplashUrlBean;
import com.d2cmall.buyer.widget.ninegrid.ImageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 存储或取出热门搜索，商品首页数据，
 * 全局消息是否已读、是否已删的工具类
 * Author: Blend
 * Date: 16/4/27 11:11
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LocalDataUtil {

    public static List<HotBean.DataEntity.MemberSearchSumListEntity> getHotsList(Context context) {
        List<HotBean.DataEntity.MemberSearchSumListEntity> memberSearchSumListEntities = new ArrayList<>();
        try {
            if (context.getFileStreamPath(Constants.HOT_SEARCH_FILE) != null && context.getFileStreamPath
                    (Constants.HOT_SEARCH_FILE).exists()) {
                InputStream in = context.openFileInput(Constants.HOT_SEARCH_FILE);
                String jsonStr = Util.readStreamToString(in);
                Gson gson = new Gson();
                List<HotBean.DataEntity.MemberSearchSumListEntity> list = gson.fromJson(jsonStr,
                        new TypeToken<List<HotBean.DataEntity.MemberSearchSumListEntity>>() {
                        }.getType());
                memberSearchSumListEntities.addAll(list);
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return memberSearchSumListEntities;
    }

    public static List<KindTagBean.DataEntity.NavigationsEntity> getGoodTagsList(Context context) {
        List<KindTagBean.DataEntity.NavigationsEntity> navigationsEntities = new ArrayList<>();
        try {
            if (context.getFileStreamPath(Constants.GOOD_TAG_FILE) != null && context.getFileStreamPath
                    (Constants.GOOD_TAG_FILE).exists()) {
                InputStream in = context.openFileInput(Constants.GOOD_TAG_FILE);
                String jsonStr = Util.readStreamToString(in);
                Gson gson = new Gson();
                List<KindTagBean.DataEntity.NavigationsEntity> list = gson.fromJson(jsonStr,
                        new TypeToken<List<KindTagBean.DataEntity.NavigationsEntity>>() {
                        }.getType());
                navigationsEntities.addAll(list);
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return navigationsEntities;
    }

    public static List<ImageInfo> getImageInfo(Context context, long id) {
        List<ImageInfo> mImageInfo = new ArrayList<>();
        try {
            if (context.getFileStreamPath(String.format(Constants.TAG_FILE, id)) != null && context.getFileStreamPath(String.format(Constants.TAG_FILE, id)).exists()) {
                InputStream in = context.openFileInput(String.format(Constants.TAG_FILE, id));
                String jsonStr = Util.readStreamToString(in);
                Gson gson = new Gson();
                mImageInfo = gson.fromJson(jsonStr, new TypeToken<List<ImageInfo>>() {
                }.getType());
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mImageInfo;
    }

    public static void storeImageInfoList(Context context, List<ImageInfo> imageInfos) {
        if (imageInfos == null || imageInfos.size() == 0) return;
        long id = 0;
        Gson gson = new Gson();
        String jsonStr = gson.toJson(imageInfos);
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(String.format(Constants.TAG_FILE, id), Context.MODE_PRIVATE);
            if (fileOutputStream != null) {
                OutputStreamWriter out = new OutputStreamWriter(fileOutputStream);
                out.write(jsonStr);
                out.flush();
                out.close();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getReadedGlobalMsgList(Context context) {
        List<String> readedglobalMsgBeanList = new ArrayList<>();
        try {
            if (context.getFileStreamPath(Constants.READED_GLOBAL_MSG_FILE) != null && context.getFileStreamPath
                    (Constants.READED_GLOBAL_MSG_FILE).exists()) {
                InputStream in = context.openFileInput(Constants.READED_GLOBAL_MSG_FILE);
                String jsonStr = Util.readStreamToString(in);
                Gson gson = new Gson();
                List<String> list = gson.fromJson(jsonStr,
                        new TypeToken<List<String>>() {
                        }.getType());
                readedglobalMsgBeanList.addAll(list);
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readedglobalMsgBeanList;
    }

    /**
     * 判断是否存在文件
     *
     * @param context
     * @param name
     * @return
     */
    public static boolean isExit(Context context, String name) {
        File root = context.getExternalCacheDir();
        File folder = new File(root, "file");
        File file = new File(folder, name);
        return file.exists();
    }

    public static void storeReadedGlobalMsgList(Context context, List<String> readedglobalMsgList) {
        Gson gson = new Gson();
        String globalMsgsJson = gson.toJson(readedglobalMsgList, new TypeToken<List<String>>() {
        }.getType());
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(Constants.READED_GLOBAL_MSG_FILE, Context.MODE_PRIVATE);
            if (fileOutputStream != null) {
                OutputStreamWriter out = new OutputStreamWriter(fileOutputStream);
                out.write(globalMsgsJson);
                out.flush();
                out.close();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void storeHotsList(Context context, List<HotBean.DataEntity.MemberSearchSumListEntity> memberSearchSumListEntities) {
        Gson gson = new Gson();
        String hotsJson = gson.toJson(memberSearchSumListEntities, new TypeToken<List<HotBean.DataEntity.MemberSearchSumListEntity>>() {
        }.getType());
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(Constants.HOT_SEARCH_FILE, Context.MODE_PRIVATE);
            if (fileOutputStream != null) {
                OutputStreamWriter out = new OutputStreamWriter(fileOutputStream);
                out.write(hotsJson);
                out.flush();
                out.close();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void storeGoodTagsList(Context context, List<KindTagBean.DataEntity.NavigationsEntity> navigationsEntities) {
        Gson gson = new Gson();
        String goodTagsJson = gson.toJson(navigationsEntities, new TypeToken<List<KindTagBean.DataEntity.NavigationsEntity>>() {
        }.getType());
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(Constants.GOOD_TAG_FILE, Context.MODE_PRIVATE);
            if (fileOutputStream != null) {
                OutputStreamWriter out = new OutputStreamWriter(fileOutputStream);
                out.write(goodTagsJson);
                out.flush();
                out.close();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getDeletedGlobalMsgList(Context context) {
        List<String> deletedGlobalMsgBeanList = new ArrayList<>();
        try {
            if (context.getFileStreamPath(Constants.DELETED_GLOBAL_MSG_FILE) != null && context.getFileStreamPath
                    (Constants.DELETED_GLOBAL_MSG_FILE).exists()) {
                InputStream in = context.openFileInput(Constants.DELETED_GLOBAL_MSG_FILE);
                String jsonStr = Util.readStreamToString(in);
                Gson gson = new Gson();
                List<String> list = gson.fromJson(jsonStr,
                        new TypeToken<List<String>>() {
                        }.getType());
                deletedGlobalMsgBeanList.addAll(list);
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deletedGlobalMsgBeanList;
    }

    public static void storeDeletedGlobalMsgList(Context context, List<String> deletedGlobalMsgList) {
        Gson gson = new Gson();
        String globalMsgsJson = gson.toJson(deletedGlobalMsgList, new TypeToken<List<String>>() {
        }.getType());
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(Constants.DELETED_GLOBAL_MSG_FILE, Context.MODE_PRIVATE);
            if (fileOutputStream != null) {
                OutputStreamWriter out = new OutputStreamWriter(fileOutputStream);
                out.write(globalMsgsJson);
                out.flush();
                out.close();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<SplashUrlBean> getSplashList(Context context) {
        List<SplashUrlBean> splashUrlList = new ArrayList<>();
        try {
            if (context.getFileStreamPath(Constants.SPLASH_URL_FILE) != null
                    && context.getFileStreamPath(Constants.SPLASH_URL_FILE).exists()) {
                InputStream in = context.openFileInput(Constants.SPLASH_URL_FILE);
                String jsonStr = Util.readStreamToString(in);
                Gson gson = new Gson();
                List<SplashUrlBean> list = gson.fromJson(jsonStr,
                        new TypeToken<List<SplashUrlBean>>() {
                        }.getType());
                splashUrlList.addAll(list);
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return splashUrlList;
    }

    public static void storeSplashList(Context context, List<SplashUrlBean> splashUrlList) {
        Gson gson = new Gson();
        String splashJson = gson.toJson(splashUrlList, new TypeToken<List<SplashUrlBean>>() {
        }.getType());
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(Constants.SPLASH_URL_FILE, Context.MODE_PRIVATE);
            if (fileOutputStream != null) {
                OutputStreamWriter out = new OutputStreamWriter(fileOutputStream);
                out.write(splashJson);
                out.flush();
                out.close();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}