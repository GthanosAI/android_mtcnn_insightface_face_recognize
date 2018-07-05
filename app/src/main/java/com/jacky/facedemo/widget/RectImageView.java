package com.jacky.facedemo.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/4/13 下午3:54
 */

@SuppressLint("AppCompatCustomView")
public class RectImageView extends ImageView {

    private int[] faceInfo;
    private float rateX = 1f;
    private float rateY = 1f;

    private boolean hasUpdateShowSize = false;

    public RectImageView(Context context) {
        super(context);
    }

    public RectImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RectImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setFaceInfo(int[] faceInfo) {
        this.faceInfo = faceInfo;
        postInvalidate();
    }

    private void drawRect(Canvas canvas) {

        if (faceInfo == null || faceInfo.length <= 1) {
            return;
        }

        int faceNum = faceInfo[0];
        for (int i = 0; i < faceNum; i++) {
            int left, top, right, bottom;
            left = (int) (faceInfo[1 + 14 * i] * rateX);
            top = (int) (faceInfo[2 + 14 * i] * rateY);
            right = (int) (faceInfo[3 + 14 * i] * rateX);
            bottom = (int) (faceInfo[4 + 14 * i] * rateY);
            canvas.drawRect(left, top, right, bottom, paint);
        }
    }

    public RectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    Paint paint = new Paint();

    {
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6f);//设置线宽
        paint.setAlpha(255);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRect(canvas);
    }

    public void setTargetSize(int[] previewSize) {

        int height = getHeight();
        int width = getWidth();

        if (height == 0 || width == 0) {
            return;
        }

        if (previewSize == null
                || previewSize.length != 2
                || previewSize[0] <= 0
                || previewSize[1] <= 0) {
            return;
        }

        rateX = width / (float)previewSize[0];
        rateY = height / (float)previewSize[1];

        hasUpdateShowSize = true;
    }

    public boolean hasUpdateShowSize() {
        return hasUpdateShowSize;
    }
}
