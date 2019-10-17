package com.d2cmall.buyer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.d2cmall.buyer.fragment.ProductCommendFragment;
import com.d2cmall.buyer.fragment.ProductFragment;
import com.d2cmall.buyer.fragment.ProductMatchFragment;
import com.d2cmall.buyer.fragment.ProductWebFragment;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/26 18:11
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductAdapter extends FragmentPagerAdapter {

    private ProductFragment productFragment;
    private ProductWebFragment productWebFragment;
    private ProductCommendFragment productCommendFragment;
    private ProductMatchFragment productMatchFragment;
    private long id;
    private int collageId;//拼团id

    public ProductAdapter(FragmentManager fm,long id,int collageId) {
        super(fm);
        this.id=id;
        this.collageId=collageId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                if (productFragment==null){
                    productFragment=ProductFragment.newInstance(id,collageId);
                }
                return productFragment;
            case 1:
                if (productWebFragment==null){
                    productWebFragment=ProductWebFragment.newInstance(id);
                }
                return productWebFragment;
            case 2:
                if (productCommendFragment==null){
                    productCommendFragment=ProductCommendFragment.newInstance(id);
                }
                return productCommendFragment;
            case 3:
                if (productMatchFragment==null){
                    productMatchFragment=ProductMatchFragment.newInstance(id,collageId);
                }
                return productMatchFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
