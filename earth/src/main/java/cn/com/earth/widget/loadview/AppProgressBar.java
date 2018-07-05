package cn.com.earth.widget.loadview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import cn.com.earth.R;


/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  16/7/28 下午8:48
 */
public class AppProgressBar extends RelativeLayout {
    private static int CIRCLE_DIAMETER = 60;
    private static final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;
    private AppCircleImageView circleView;
    private MaterialProgressDrawable progressDrawable;
    private boolean isLoading;

    public static int[] MD_RF_COLOR = {R.color.sfw_blue, R.color.sfw_green, R.color.sfw_red, R.color.sfw_yellow};


    public AppProgressBar(Context context) {
        super(context);
        init();
    }

    public AppProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AppProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        CIRCLE_DIAMETER = (int) getContext().getResources().getDimension(R.dimen.q60);
        circleView = new AppCircleImageView(getContext(), CIRCLE_BG_LIGHT, CIRCLE_DIAMETER / 2);
        progressDrawable = new MaterialProgressDrawable(getContext(), this);
        progressDrawable.setBackgroundColor(CIRCLE_BG_LIGHT);
        circleView.setImageDrawable(progressDrawable);
        circleView.setVisibility(View.VISIBLE);
        progressDrawable.setAlpha(255);
//        progressDrawable.setAlpha(0);

        if (null != MD_RF_COLOR) {
            final Resources res = getResources();
            int[] colorRes = new int[MD_RF_COLOR.length];
            for (int i = 0; i < MD_RF_COLOR.length; i++) {
                colorRes[i] = res.getColor(MD_RF_COLOR[i]);
            }
            progressDrawable.setColorSchemeColors(colorRes);
        }
        setProgressBarBackgroundColor(Color.WHITE);
        addView(circleView);
        startAnimation();
    }


    public void setColorSchemeResources(int... colors) {
        if (null != progressDrawable) {
            final Resources res = getResources();
            int[] colorRes = new int[colors.length];
            for (int i = 0; i < colors.length; i++) {
                colorRes[i] = res.getColor(colors[i]);
            }
            progressDrawable.setColorSchemeColors(colorRes);
        }
    }

    public void setProgressBarBackgroundColor(int color) {
        if (null != circleView) {
            circleView.setBackgroundColor(color);
        }
    }

    public void startAnimation() {
        if (null != progressDrawable) {
            progressDrawable.start();
            isLoading = true;
        }
    }

    public void stopAnimation() {
        if (null != progressDrawable) {
            progressDrawable.stop();
            isLoading = false;
        }
    }

    public void setInnerVisibility(int innerVisibility) {
        if (null != circleView) {
            circleView.setVisibility(innerVisibility);
            if (innerVisibility == VISIBLE) {
                startAnimation();
            } else {
                stopAnimation();
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (progressDrawable != null){
            startAnimation();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (progressDrawable != null) {
            stopAnimation();
        }
    }

    public boolean isLoading() {
        return isLoading;
    }
}
