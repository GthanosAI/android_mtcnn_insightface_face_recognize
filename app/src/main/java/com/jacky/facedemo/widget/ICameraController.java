package com.jacky.facedemo.widget;

import android.content.Context;
import android.view.SurfaceHolder;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/4/13 上午11:26
 */

public interface ICameraController {

    void openCamera();

    void startPreview(SurfaceHolder surfaceHolder);

    void setDisplayOrientation(Context context);

    void stopPreview();

    void switchCamera(Context context);

    void destroy();

    void setImageDataCallBack(IYuvDataCallback callBack);

    int[] getPreviewSize();

    int getDegree();
}
