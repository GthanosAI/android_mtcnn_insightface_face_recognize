package com.jacky.facedemo;

import android.app.Application;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/6/25 下午1:53
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        InitUtils.init(this, true);
    }
}
