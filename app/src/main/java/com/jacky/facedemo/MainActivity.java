package com.jacky.facedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.gthanos.mface.FaceEngineManager;

import cn.com.earth.base.BaseActivity;
import cn.com.earth.permission.PermissionCallback;
import cn.com.earth.permission.RunTimePermissionUtils;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/6/25 下午1:47
 */

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RunTimePermissionUtils.onStorage(this, new PermissionCallback() {
            @Override
            public void onPermissionSuccess() {
                FaceEngineManager.getInstance().init(MainActivity.this);
            }

            @Override
            public void onPermissionFailure() {

            }
        });
    }

    @Override
    protected Fragment getFirstFragment() {
        return new HomeFragment();
    }
}
