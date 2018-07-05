package cn.com.earth.base;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import cn.com.earth.R;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  2017/6/19 下午4:01
 */

public class DefaultErrorView extends FrameLayout implements IErrorView {

    private TextView tvError;

    public DefaultErrorView(@NonNull Context context) {
        super(context);
        init();
    }

    public DefaultErrorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DefaultErrorView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DefaultErrorView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setBackgroundColor(Color.WHITE);
        LayoutInflater.from(getContext()).inflate(R.layout.earth_app_error_view, this);
        tvError = (TextView) findViewById(R.id.tv_err_msg);
    }

    public void setErrorInfo(int errorCode, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            tvError.setText(msg);
        }
    }
}
