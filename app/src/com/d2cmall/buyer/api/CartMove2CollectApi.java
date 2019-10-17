package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Fixme
 * Author: YH
 * Date: 2017/04/24 16:41
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class CartMove2CollectApi extends CartDeleteApi {
    @Override
    protected String getPath() {
        return Constants.MOVE_COLLECT;
    }

}
