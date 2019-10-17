package com.d2cmall.buyer.listener;

import com.d2cmall.buyer.bean.ReshipsBean;

/**
 * Created by rookie on 2017/9/9.
 */

public interface ReshipListener {
    void cancelReshipClick(ReshipsBean.DataBean.ReshipsDataBean.ListBean listEntity);
    void editReshipClick(ReshipsBean.DataBean.ReshipsDataBean.ListBean listEntity);
}
