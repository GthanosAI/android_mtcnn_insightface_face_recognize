package com.jacky.facedemo.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 介绍: 自定义相机预览页面
 * 作者: jacky
 * 时间: 2018/4/13 上午11:19
 */

public class CameraSurfaceView extends SurfaceView {

    ICameraController mController;

    private SurfaceHolder mHolder;
    private Context context;

    public CameraSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        this.mHolder = getHolder();
        mController = new CameraController();

        this.mHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                mController.openCamera();
                mController.setDisplayOrientation(context);

                if (updatePreviewSizeListener != null) {
                    updatePreviewSizeListener.onUpdate(mController.getDegree());
                }

                mController.startPreview(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (mController == null) {
                    mController = new CameraController();
                }

                mController.startPreview(holder);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mController.stopPreview();
            }
        });
    }


    public void switchCamera(Context context) {
        mController.switchCamera(context);
    }


    public int getDegree() {
        return mController.getDegree();
    }

    public void setDataCallback(IYuvDataCallback dataCallback) {
        if (mController != null) {


            mController.setImageDataCallBack(dataCallback);
        }
    }

    private UpdatePreviewSizeListener updatePreviewSizeListener;

    public CameraSurfaceView setUpdatePreviewSizeListener(UpdatePreviewSizeListener updatePreviewSizeListener) {
        this.updatePreviewSizeListener = updatePreviewSizeListener;
        return this;
    }

    public interface UpdatePreviewSizeListener {
        void onUpdate(int degree);
    }

}
