package com.jacky.facedemo.face.checkface;

import com.gthanos.mface.FaceEngineManager;
import com.gthanos.mface.FaceFileConfig;
import com.gthanos.mface.model.SearchResult;
import com.jacky.facedemo.base.AsyncProcessor;
import com.jacky.facedemo.widget.YuvData;

import cn.com.earth.mvp.MvpBasePresenter;
import cn.com.earth.tools.logger.LogUtils;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/7/4 上午1:20
 */

public class CheckFacePresenter extends MvpBasePresenter<ICheckFaceView> {
    private volatile boolean isFinish;

    void faceCheck(YuvData data) {

        if (isFinish) {
            return;
        }
        AsyncProcessor.getInstance().execute(() -> {
            SearchResult searchResult = new SearchResult();
            boolean ret = FaceEngineManager.getInstance().search(data.getBuf(), data.getWidth(),
                    data.getHeight(), data.getDegree(), data.isMirror(), FaceFileConfig.FACE_PERSON_DIR, searchResult);

            if (ret) {
                isFinish = true;
                LogUtils.e("result ---" + searchResult.toString());
                AsyncProcessor.getInstance().clearAll();
                if (isAvailable()) {
                    get().getHandler().post(() -> get().checkSuccess(searchResult));
                }
            }
        });

    }

}
