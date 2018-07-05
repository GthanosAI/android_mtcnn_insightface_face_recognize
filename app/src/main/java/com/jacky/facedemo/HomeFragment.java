package com.jacky.facedemo;

import android.view.View;

import cn.com.earth.base.BaseFragmentWithToolBar;
import cn.com.earth.widget.AppToolBar;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/7/3 下午8:55
 */

public class HomeFragment extends BaseFragmentWithToolBar {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void bindDataAndListener() {
        findViewById(R.id.btnFaceAdd).setOnClickListener(v -> Router.toFaceInput(v.getContext()));
        findViewById(R.id.btnDemo).setOnClickListener(v -> Router.toDemo(v.getContext()));
        findViewById(R.id.btnCheckIn).setOnClickListener(v -> Router.toFaceVerify(v.getContext()));
        findViewById(R.id.btnCompare).setOnClickListener(v -> Router.toCompare(v.getContext()));
    }

    @Override
    protected void initToolBarView(View view) {
        super.initToolBarView(view);
    }

    @Override
    protected AppToolBar.AppToolbarConfig getAppToolBarConfig() {
        return new AppToolBar.AppToolbarConfig().setCenterText("首页");
    }
}
