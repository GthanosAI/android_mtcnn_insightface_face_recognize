package com.jacky.facedemo;

import android.content.Context;

import cn.com.earth.tools.EAppEnv;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/5/28 下午2:03
 */

public class InitUtils {
    public static void init(Context context, boolean isDebug) {
        EAppEnv.init(context, isDebug);
    }
}
