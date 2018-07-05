package com.jacky.facedemo.face.addface;

import com.jacky.facedemo.R;
import com.jacky.facedemo.Router;
import com.jacky.facedemo.base.BaseMvpDsFragment;
import com.jacky.facedemo.databinding.FragmentAddFaceBinding;

import cn.com.earth.widget.AppToolBar;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/7/3 下午4:47
 */

public class AddFaceFragment extends BaseMvpDsFragment<AddFacePresenter, FragmentAddFaceBinding>
        implements IAddFaceView {

    private AddFaceVm addFaceVm;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_face;
    }

    @Override
    protected AddFacePresenter createPresenter() {
        String name = "";
        if (getArguments() != null) {
            name = getArguments().getString(KEY_STRING);
        }
        return new AddFacePresenter(name);
    }

    @Override
    protected void bindDataAndListener() {
        super.bindDataAndListener();
        mDataBinding.cameraview.getSurfaceView().setDataCallback(data -> {
            p.faceAdd(data);
        });
//
        addFaceVm = new AddFaceVm().init();
        mDataBinding.setVm(addFaceVm);
    }


    @Override
    public void successAddFace() {
        addFaceVm.setShowMsg("添加成功")
                .setLogin(false);

        handler.postDelayed(() -> {
            Router.toHome(mActivity);
        }, 1000);
    }

    @Override
    protected AppToolBar.AppToolbarConfig getAppToolBarConfig() {
        return new AppToolBar.AppToolbarConfig()
                .setCenterText("录入人脸")
                .setLeftText("首页")
                .setLeftAction(v -> finishAll());
    }
}
