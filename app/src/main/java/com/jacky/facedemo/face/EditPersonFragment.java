package com.jacky.facedemo.face;

import android.text.TextUtils;

import com.jacky.facedemo.R;
import com.jacky.facedemo.Router;
import com.jacky.facedemo.databinding.FragmentEditPersonBinding;

import cn.com.earth.base.BaseDataBindingFragment;
import cn.com.earth.permission.PermissionCallback;
import cn.com.earth.permission.RunTimePermissionUtils;
import cn.com.earth.tools.toast.Toast;
import cn.com.earth.widget.AppToolBar;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/7/3 下午10:21
 */

public class EditPersonFragment extends BaseDataBindingFragment<FragmentEditPersonBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_person;
    }

    @Override
    protected void bindDataAndListener() {

        mDataBinding.btnNext.setOnClickListener(v -> {
            String name = mDataBinding.etName.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                Toast.showWarning("名称不能为空！");
                return;
            }

            RunTimePermissionUtils.onCamera(mActivity, new PermissionCallback() {
                @Override
                public void onPermissionSuccess() {
                    Router.toFaceAdd(v.getContext(), name);
                }

                @Override
                public void onPermissionFailure() {

                }
            });

        });
    }

    @Override
    protected AppToolBar.AppToolbarConfig getAppToolBarConfig() {
        return new AppToolBar.AppToolbarConfig()
                .setCenterText("编辑个人信息")
                .setLeftText("返回")
                .setLeftAction(v -> finishAll());
    }
}
