package com.d2cmall.buyer.bean;

/**
 * 标示每项数据
 * Author: hrb
 * Date: 2016/10/25 14:07
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class YSFItemBean {

   /* key: 数据项的名称，用于区别不同的数据。
    index: 用于排序，显示数据时数据项按index值升序排列；不设定index的数据项将排在后面；index相同或未设定的数据项将按照其在 JSON 中出现的顺序排列。
    label: 该项数据显示的名称。
    value: 该数据显示的值，类型不做限定，根据实际需要进行设定。
    href: 超链接地址。若指定该值，则该项数据将显示为超链接样式，点击后跳转到其值所指定的 URL 地址。
    hidden: 是否隐藏该item。目前仅对mobile和email有效。
    现在，以下3个key由七鱼使用，他们的排序和标签名是固定的，不能指定index和label。

    real_name: 用户姓名。
    mobile_phone：用户手机号，可以隐藏。
    email：用户的邮箱账号，可以隐藏。*/
    public String key;
    public int index;
    public String label;
    public String value;
    public String href;
    public boolean hidden;
}
