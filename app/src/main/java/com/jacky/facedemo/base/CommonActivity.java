package com.jacky.facedemo.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import cn.com.earth.base.BaseActivity;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/5/30 下午1:42
 */

public class CommonActivity extends BaseActivity {

    @Override
    protected Fragment getFirstFragment() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String fragmentName = bundle.getString(KEY_FRAGMENT_NAME);
            return Fragment.instantiate(this, fragmentName, getIntent().getExtras());
        } else {
            return null;
        }
    }
}
