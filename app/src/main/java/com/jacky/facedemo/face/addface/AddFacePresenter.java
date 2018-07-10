package com.jacky.facedemo.face.addface;

import com.gthanos.mface.FaceEngineManager;
import com.gthanos.mface.FaceFileConfig;
import com.gthanos.mface.demo.FaceEditor;
import com.gthanos.mface.model.PersonModel;
import com.jacky.facedemo.base.AsyncProcessor;
import com.jacky.facedemo.widget.YuvData;

import cn.com.earth.mvp.MvpBasePresenter;
import cn.com.earth.tools.logger.LogUtils;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/7/3 下午8:35
 */

public class AddFacePresenter extends MvpBasePresenter<IAddFaceView> {

    private PersonModel model;
    private int faceNum;

    private volatile boolean isFinish;
    static final int MAX_FACE = 3;

    public AddFacePresenter(String name) {
        faceNum = 0;
        isFinish = false;
        model = FaceEditor.getInstance().newPersonModel();
        model.modifyName(name);
        model.setSourceName(FaceFileConfig.FACE_PERSON_DIR);
    }

    void faceAdd(YuvData data) {

        if (isFinish) {
            return;
        }
        AsyncProcessor.getInstance().execute(() -> {
            boolean ret = false;

            if (faceNum < MAX_FACE -1){
                ret = FaceEngineManager.getInstance().addFace(data.getBuf(), model.getNativePtr(),
                        data.getWidth(), data.getHeight(), data.getDegree(), data.isMirror(), false);
            }

            if (ret) {
                faceNum++;
            }

            if (faceNum >= MAX_FACE - 1) {
                ret = FaceEngineManager.getInstance().addFace(data.getBuf(), model.getNativePtr(),
                        data.getWidth(), data.getHeight(), data.getDegree(), data.isMirror(), true);

                if (ret){
                    faceNum ++;
                    if (isAvailable()) {
                        isFinish = true;
                        AsyncProcessor.getInstance().clearAll();
                        LogUtils.e("dump file");
                        model.dump();
                        get().getHandler().post(() -> get().successAddFace());
                    }
                }
            }
        });

    }


    void clear() {
        faceNum = 0;
    }

    @Override
    public void destroy() {
        super.destroy();
        model.release();
    }
}
