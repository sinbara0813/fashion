package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Fixme
 * Author: Blend
 * Date: 2016/08/03 17:59
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class SigBean extends BaseBean {

    /**
     * sig : eJxljl1vgjAUhu-5FaS3W2bLxypLvFAgw01CiCaLV021Rc8EZG2nuGX-fYomI9l7*zzvec*3Zds2WszmD3y93n-WhplTI5H9ZCOM7v9g04Bg3DBXiX9Qtg0oyXhhpOog8X3fwbjvgJC1gQJuxvCSPtdix7qR6wHv3CaYBkFfgU0H0zgPp3G2olHVHqM6KHLhxryhoVNuI5qWwEN9aJNBnJHH7CO8G8NEbaK3gYe3S*859ZT-utLj0yR-UcnuOJsmevlVwXs593i8yEej3qSBSt4eotghQ5e4PXqQSsO*7gQHE584Lr4EWT-WL0qNW8A_
     * id : 888880
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String sig;
        private int id;

        public String getSig() {
            return sig;
        }

        public void setSig(String sig) {
            this.sig = sig;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
