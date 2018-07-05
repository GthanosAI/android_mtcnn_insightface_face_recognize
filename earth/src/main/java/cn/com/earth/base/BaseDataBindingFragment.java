package cn.com.earth.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import cn.com.earth.R;

/**
 * 介绍: databinding 基类
 * 作者: jacky
 * 邮箱: none
 * 时间:  2017/6/19 下午5:39
 */

public abstract class BaseDataBindingFragment<T extends ViewDataBinding> extends BaseFragmentWithToolBar {
    protected T mDataBinding;

    @Override
    protected void initView(Bundle savedInstance) {
        super.initView(savedInstance);
        mDataBinding = DataBindingUtil.bind(contentView);
        if (-1 != getBackgroundColor()) {
            mDataBinding.getRoot().setBackgroundColor(getResources().getColor(getBackgroundColor()));
        }
    }

    protected int getBackgroundColor() {
        return R.color.main_color;
    }

}
