package com.jacky.facedemo.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 介绍: 带有检测框的和帧率信息的Preview
 * 作者: jacky
 * 时间: 2018/4/13 下午3:44
 */

public class FaceDetectPreview extends FrameLayout {
    private Context context;
    private CameraSurfaceView surfaceView;
    private RectImageView rectImageView;

    public FaceDetectPreview(@NonNull Context context) {
        super(context);
        init(context);
    }

    public FaceDetectPreview(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FaceDetectPreview(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FaceDetectPreview(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        // add surfaceView
        this.context = context;
        surfaceView = new CameraSurfaceView(context);
        LayoutParams surfaceLayoutParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(surfaceView, surfaceLayoutParam);

        // add drawView;
        rectImageView = new RectImageView(context);
        LayoutParams rectViewLayout = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(rectImageView, rectViewLayout);
    }

    public void updateFaceInfo(int[] faceInfo) {
        if (rectImageView != null) {
            if (!rectImageView.hasUpdateShowSize()) {
                rectImageView.setTargetSize(surfaceView.mController.getPreviewSize());
            }

            rectImageView.setFaceInfo(faceInfo);
        }
    }

    public CameraSurfaceView getSurfaceView() {
        return surfaceView;
    }

    public void updateDisplayByWidth(int width) {
        int[] size = surfaceView.mController.getPreviewSize();
        if (size == null || size.length != 2 || size[0] == 0 || size[1] == 0) {
            return;
        }

        int height;
        height = (int) ((float) (size[1]) / size[0] * width);

        ViewGroup.LayoutParams params = getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(width, height);
        } else {
            params.height = height;
            params.width = width;
        }
        setLayoutParams(params);
    }

    public void updateDisplayByHeight(int height) {
        int[] size = surfaceView.mController.getPreviewSize();
        if (size == null || size.length != 2 || size[0] == 0 || size[1] == 0) {
            return;
        }

        int width = (int) ((float) (size[0]) / size[1] * height);

        ViewGroup.LayoutParams params = getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(width, height);
        } else {
            params.height = height;
            params.width = width;
        }
        setLayoutParams(params);
    }
}
