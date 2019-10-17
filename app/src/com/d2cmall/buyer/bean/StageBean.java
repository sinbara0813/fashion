package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Fixme
 * Author: YH
 * Date: 2017/04/20 13:32
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class StageBean extends BaseBean implements Serializable{
    //订单总金额
    public double orderAllMoney;
    //分期总金额
    public double stagesAllMoney;
    //可用分期金额
    public double stagesValidMoney;
    //分期列表
    public List<StageItemBean> stageItems;
}
