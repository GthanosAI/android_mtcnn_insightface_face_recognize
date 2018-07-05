package com.jacky.facedemo.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.view.Surface;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.List;

import cn.com.earth.tools.logger.LogUtils;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/4/13 上午11:26
 */

public class CameraController implements ICameraController {

    final static String Tag = "CameraController";

    private CameraConfig mCameraConfig;
    private boolean isPreviewing = false;
    private Camera mCamera;
    private SurfaceHolder mHolder;

    public CameraController() {
        this.mCameraConfig = new CameraConfig();
        this.forSpecialAngle = 0;
    }

    @Override
    public void openCamera() {
        mCamera = Camera.open(mCameraConfig.getCameraId());
    }

    @Override
    public void startPreview(SurfaceHolder surfaceHolder) {
        if (mCamera == null) {
            return;
        }

        mHolder = surfaceHolder;
        if (isPreviewing) {
            mCamera.stopPreview();
            return;
        }

        mCameraConfig.resetCameraInfo(mCamera);

        try {
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.setPreviewCallback((data, camera) -> {
                if (dataCallback != null) {
                    dataCallback.onCallback(new YuvData(
                            data,
                            mCameraConfig.degree,
                            mCameraConfig.previewFormat,
                            mCameraConfig.isNeedMirror(),
                            mCameraConfig.getPreviewWidth(),
                            mCameraConfig.getPreviewHeight()));
                }
            });

            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stopPreview() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            isPreviewing = false;
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void switchCamera(Context context) {
        if (mCamera != null) {
            stopPreview();
            mCameraConfig.switchCamera();
            openCamera();
            setDisplayOrientation(context);
            startPreview(mHolder);
        }
    }

    @Override
    public void destroy() {
        mCamera = null;
        mCameraConfig = null;
        mHolder = null;
    }

    @Override
    public void setImageDataCallBack(IYuvDataCallback callBack) {
        this.dataCallback = callBack;
    }

    @Override
    public int[] getPreviewSize() {
        if (mCameraConfig.degree == 0) {
            return new int[]{CameraConfig.previewWidth, CameraConfig.previewHeight};
        } else {
            return new int[]{CameraConfig.previewHeight, CameraConfig.previewWidth};
        }
    }

    @Override
    public int getDegree() {
        return mCameraConfig.degree;
    }

    static class CameraConfig {
        static final int previewWidth = 640;
        static final int previewHeight = 480;
        static final int previewRate = 15;

        public int getPreviewWidth() {
            return previewWidth;
        }

        public int getPreviewHeight() {
            return previewHeight;
        }

        public int degree = 0;

        public CameraConfig() {
            cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        }

        public CameraConfig setDegree(int degree) {
            this.degree = degree;

            return this;
        }

        public boolean isNeedMirror() {
            return cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT;
        }

        private int previewFormat = ImageFormat.NV21;

        private int cameraId;

        public int getCameraId() {
            return cameraId;
        }

        public CameraConfig switchCamera() {
            if (cameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
            } else {
                cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
            }
            return this;
        }

        public void resetCameraInfo(Camera camera) {
            Camera.Parameters params = camera.getParameters();
            params.setPictureFormat(PixelFormat.JPEG);

            params.setPreviewSize(previewWidth, previewHeight);
            params.setPreviewFormat(previewFormat);

            List<String> focusModes = params.getSupportedFocusModes();
            if (focusModes.contains("continuous-video")) {
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            }
//            params.setPreviewFrameRate(previewRate);
//            showSupportList(camera);
            camera.setParameters(params);
        }
    }


    private static void showSupportList(Camera camera) {
        Camera.Parameters params = camera.getParameters();
        List<Camera.Size> previewSizes = params.getSupportedPreviewSizes();
        for (int i = 0; i < previewSizes.size(); i++) {
            LogUtils.e(Tag + "preview width: %d, preview height: %d", previewSizes.get(i).width, previewSizes.get(i).height);
        }
    }

    public void setDisplayOrientation(Context context) {

        if (mCamera == null) return;

        if (!(context instanceof Activity)) {
            return;
        }

        Camera.CameraInfo info =
                new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraConfig.getCameraId(), info);
        int rotation = ((Activity) context).getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        int displatyAdjut = 0;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            displatyAdjut = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
            displatyAdjut = result;
        }

        // 调整相机角度
        if (forSpecialAngle != 0) {
            mCamera.setDisplayOrientation(forSpecialAngle);
        } else {
            mCamera.setDisplayOrientation(displatyAdjut);
        }

        // 需要旋转的角度
        mCameraConfig.setDegree(result + forSpecialAngle);
    }

    private int forSpecialAngle = 90;

    private IYuvDataCallback dataCallback;

}
