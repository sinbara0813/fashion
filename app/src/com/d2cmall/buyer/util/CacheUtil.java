package com.d2cmall.buyer.util;

import android.content.Context;


import com.d2cmall.buyer.widget.ninegrid.ImageInfo;

import java.util.HashMap;
import java.util.List;

/**
 * Author: hrb
 * Date: 2016/07/18 16:56
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CacheUtil {

    private static CacheUtil cache;

    private HashMap<Long, List<ImageInfo>> imageCache;

    private CacheUtil() {
        imageCache = new HashMap<>();
    }

    public static CacheUtil getInstance() {
        if (cache == null) {
            cache = new CacheUtil();
        }
        return cache;
    }

    public List<ImageInfo> getImageInfos(Context context, long id) {
        if (imageCache.containsKey(id)) {
            return imageCache.get(id);
        }
        List<ImageInfo> images = LocalDataUtil.getImageInfo(context, id);
        if (images != null && images.size() > 0) {
            imageCache.put(id, images);
        }
        return images;
    }

}
