package com.jacky.facedemo.face.checkface;


import android.view.View;

import com.gthanos.mface.FaceFileConfig;
import com.gthanos.mface.model.SearchResult;
import com.jacky.facedemo.R;
import com.jacky.facedemo.base.BaseMvpDsFragment;
import com.jacky.facedemo.databinding.FragmentFaceCheckBinding;

import cn.com.earth.tools.LoadImageUtils;
import cn.com.earth.widget.AppToolBar;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/7/4 上午10:58
 */

public class CheckFaceFragment extends BaseMvpDsFragment<CheckFacePresenter, FragmentFaceCheckBinding> implements ICheckFaceView {
    private CheckFaceVm faceVm;

    @Override
    protected CheckFacePresenter createPresenter() {
        return new CheckFacePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_face_check;
    }

    @Override
    protected void bindDataAndListener() {
        super.bindDataAndListener();
        mDataBinding.cameraview.getSurfaceView().setDataCallback(data -> p.faceCheck(data));
        faceVm = new CheckFaceVm().setMsg("正在验证人脸...").setLogin(true);
        mDataBinding.setVm(faceVm);
    }

    @Override
    public void checkSuccess(SearchResult searchResult) {
        mDataBinding.cameraview.getSurfaceView().setDataCallback(null);
        mDataBinding.cameraview.setVisibility(View.GONE);
        faceVm.setMsg("验证人脸成功").setLogin(false)
                .setInfo(String.format("name:%s, id:%d".toLowerCase(), searchResult.getName(), searchResult.getId()));
        LoadImageUtils.loadIcon(rootView.findViewById(R.id.avatar), FaceFileConfig.FACE_IMG_DIR + searchResult.getAvatar());

    }

    @Override
    protected AppToolBar.AppToolbarConfig getAppToolBarConfig() {
        return new AppToolBar.AppToolbarConfig().setCenterText("人脸解锁")
                .setLeftText("返回")
                .setLeftAction((v) -> finishAll());
    }
}
