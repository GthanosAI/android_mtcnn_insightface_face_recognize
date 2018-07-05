package com.jacky.facedemo.base;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.com.earth.base.BaseDataBindingFragment;
import cn.com.earth.mvp.MvpBasePresenter;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/4/18 下午5:51
 */

public abstract class BaseMvpDsFragment<P extends MvpBasePresenter, T extends ViewDataBinding>
        extends BaseDataBindingFragment<T> {

    protected P p;

    protected abstract P createPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void bindDataAndListener() {
        p = createPresenter();
        if (p != null) {
            p.attachView(this);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (p != null) {
            p.detachView(this);
        }
    }
}
