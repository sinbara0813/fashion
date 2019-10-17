package com.d2cmall.buyer.listener;

import com.d2cmall.buyer.bean.RefundsBean;

/**
 * Created by rookie on 2017/9/9.
 */

public interface RefundListener {
    void cancelRefund(RefundsBean.DataEntity.RefundsEntity.ListEntity listEntity);
}
