package com.d2cmall.buyer.bean;

import java.io.Serializable;

/**
 * Fixme
 * Author: YH
 * Date: 2017/04/20 13:35
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class StageItemBean implements Serializable {
    //期数
    public int  periods;
    //每期金额
    public double amount;
    //每期手续费
    public double poundage;
}
