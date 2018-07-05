package cn.com.earth.base;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import cn.com.earth.R;
import cn.com.earth.widget.loadview.AppProgressBar;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  2017/6/19 下午4:02
 */

public class DefaultLoadingView extends FrameLayout implements ILoadAnimation {
    private AppProgressBar appProgressBar;
    public DefaultLoadingView(@NonNull Context context) {
        super(context);
        init();
    }

    public DefaultLoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DefaultLoadingView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DefaultLoadingView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    public void start() {
        appProgressBar.startAnimation();
    }

    @Override
    public void stop() {
        appProgressBar.stopAnimation();
    }

    private void init() {
        setBackgroundColor(Color.WHITE);
        LayoutInflater.from(getContext()).inflate(R.layout.earth_app_loading_layout, this);
        appProgressBar = (AppProgressBar) findViewById(R.id.app_progressbar);
    }
}
